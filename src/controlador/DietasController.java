/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import DAO.DAOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import modelo.plan.Alimento;
import modelo.plan.AlxDiet;
import modelo.plan.Plan;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author 201621279487
 */
public class DietasController extends Controller<Plan> {

    @FXML
    CheckBox checkCal;
    @FXML
    private ComboBox<Alimento> comboAlimentos;
    @FXML
    private ComboBox<String> comboDia;
    @FXML
    private ComboBox<String> comboMomento;
    @FXML
    private TextField textBuscarAlimento;
    @FXML
    private ComboBox<Plan> comboDieta;
    @FXML
    private TextField textNombre;
    @FXML
    private TextArea textDescripcion;
    @FXML
    private TextField textKcal;
    @FXML
    private TextField textCarbohidratos;
    @FXML
    private TextField textGrasas;
    @FXML
    private TextField textProteinas;
    @FXML
    private TextField carbosDistribucion;
    @FXML
    private TextField proteinasDistribucion;
    @FXML
    private TextField grasasDistribucion;
    @FXML
    private ListView<AlxDiet> listView;
    @FXML
    private TextField textCantidad;
    @FXML
    private Label labelunidad;


    public void initialize(URL location, ResourceBundle resources) {
        obtener();
        obtenerAlimentos();
        setCombos();
        getMenu();
    }

    public void setCombos(){
        comboDia.getItems().setAll("Domingo","Lunes","Martes","Miercoles","Jueves","Viernes","Sabado");
        comboDia.getSelectionModel().select(0);
        comboMomento.getItems().setAll("Desayuno","Almuerzo","Cena","Preentreno","Postentreno","Snack AM", "Snack PM");
        comboMomento.getSelectionModel().select(0);
    }


    public void setKcal() {
        if (checkCal.isSelected()) {
            if(!getMedida().isEmpty()) {
                textKcal.setText(getMedida().getSuperavitODeficit() + "");
                textKcal.setDisable(true);
            }else{
                if (textKcal.getText().isEmpty()) {
                    textKcal.setText("2000");
                }
                mensaje("Este cliente no tiene medidas","aviso");
            }
        }
    }

    /**
     * Este método calcula los macronutrientes calculando la distribución
     * porcentual digitada por el usuario.
     * <p>
     * PD: Se puede hacer así: que al ingresar un numero en un textfield y la
     * suma de lo que hay en los tres se pasa de 100, entonces la diferencia se
     * divide entre dos y se le resta a los que no se están modificando. Si la
     * resta excede el monto de 0 en uno, se le debe restar al otro y si el otro
     * llega a cero, querrá decir que el que se está modificando llegó al tope
     * máximo de 100
     */
    public void calcular() {
        if(textKcal.getText().isEmpty()) {
            textKcal.setText("2000");
        }else{
        //Obtener calorías
        double cal = (textProteinas.getText().isEmpty()) ? 0
                : Double.parseDouble(textKcal.getText());
        //Obtener porcentajes
        double pro = ((textProteinas.getText().isEmpty()) ? 0
                : Double.parseDouble(textProteinas.getText())) / 100;
        double gra = ((textGrasas.getText().isEmpty()) ? 0
                : Double.parseDouble(textGrasas.getText())) / 100;
        double car = ((textCarbohidratos.getText().isEmpty()) ? 0
                : Double.parseDouble(textCarbohidratos.getText())) / 100;
        //Utilizar porcentajes para calcular gramos de macronutrientes
        double proteinas = Operacion.redondear((cal * pro) / 4);
        double grasas = Operacion.redondear((cal * gra) / 9);
        double carbohidratos = Operacion.redondear((cal * car) / 4);
        //Los ponemos en las etiquetas asignadas
        proteinasDistribucion.setText(proteinas + "");
        grasasDistribucion.setText(grasas + "");
        carbosDistribucion.setText(carbohidratos + "");
    }}


    public Plan captar() throws DAOException {
        Plan d = new Plan();
        d.setTipo("dieta");
        d.setNombre(textNombre.getText());
        d.setDescripcion(textDescripcion.getText());
        return d;
    }


    public void obtener() {
        try {
            comboDieta.getItems().clear();
            if (textBuscar.getText().isEmpty()) {
                dietas = getPlanes().allDietas();
            } else {
                dietas = getPlanes().whereDietas(textBuscar.getText());
            }
            if (!dietas.isEmpty()) {
                comboDieta.setItems(dietas);
                select(0);
            }
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    public void selectAlimento() {
        if (!comboAlimentos.getItems().isEmpty()) {
            String a = Operacion.inicialMayuscula(
                    comboAlimentos.getSelectionModel()
                            .getSelectedItem().getUnidad());
            labelunidad.setText(a);
            textCantidad.setPromptText(a);
        }
    }


    public void registrar() {
        try {
            Plan m = captar();
            if (m != null) {
                if (m.isEmpty()) {
                    mensaje("Los campos señalados con asterisco son obligatorios.", "aviso");
                } else {
                    getPlanes().insert(m);
                    obtener();
                    mensaje("Plan de alimentación registrado", "exito");
                }
            }
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }


    public void modificar() {
        try {
            if (!comboDieta.getItems().isEmpty()) {
                Plan m = captar();
                if (m != null) {
                    m.setPlankey(comboDieta.getSelectionModel().getSelectedItem().getPlankey());
                    if (m.isEmpty()) {
                        mensaje("Los campos señalados con asterisco son obligatorios.", "aviso");
                    } else {
                        getPlanes().update(m);
                        obtener();
                        mensaje("Plan de alimentación modificado", "exito");
                    }
                }
            } else {
                mensaje("No hay planes de alimentación que modificar", "aviso");
            }
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    private void eliminarAlxDiets() {
        if (!listView.getItems().isEmpty()) {
            for (AlxDiet a :
                    listView.getItems()) {
                try {
                    getAlxdiets().delete(a);
                } catch (DAOException e) {
                    excepcion(e);
                }
            }
        }
    }


    public void eliminar() {
        try {
            if (!comboDieta.getItems().isEmpty()) {
                eliminarAlxDiets();
                getPlanes().delete(comboDieta.getSelectionModel().getSelectedItem());
                obtener();
                mensaje("Plan de alimentación eliminado", "exito");
            } else {
                mensaje("No hay planes de alimentación que eliminar", "aviso");
            }
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }


    public void limpiar() {
        textBuscar.setText("");
        textNombre.setText("");
        textDescripcion.setText("");
        textBuscar.setText("");
        textCantidad.setText("");
        textKcal.setText("");
        textProteinas.setText("");
        textCarbohidratos.setText("");
        textGrasas.setText("");
    }


    public void mostrar() {
        if (!comboDieta.getItems().isEmpty()) {
            Plan d = comboDieta.getSelectionModel().getSelectedItem();
            textNombre.setText(Operacion.camelCase(d.getNombre()));
            textDescripcion.setText(d.getDescripcion());
        }
    }

    public void select(int i) {
        if (!comboDieta.getItems().isEmpty()) {
            comboDieta.getSelectionModel().select(i);
            mostrar();
        }
    }

    public AlxDiet getAlxDiet() {
        if (!comboDieta.getItems().isEmpty()) {
            AlxDiet a = new AlxDiet();
            a.setPlan(getDieta());
            a.setAlimento(comboAlimentos.getSelectionModel().getSelectedItem());
            if (textCantidad.getText().isEmpty()) {
                mensaje("Digite la cantidad de " + a.getAlimento().getNombre().toLowerCase(), "aviso");
                return null;
            } else {
                a.setCantidad(Double.parseDouble(textCantidad.getText()));
            }
            a.setDia(getDia());
            a.setMomento(getMomento());
            return a;
        } else {
            mensaje("Primero debe registrar un plan", "aviso");
        }
        return null;
    }

    public Plan getDieta() {
        return comboDieta.getSelectionModel().getSelectedItem();
    }

    @FXML
    void deleteAlx(ActionEvent event) {
        if (listView.getSelectionModel().getSelectedIndex() == -1) {
            mensaje("Seleccione un alimento en el menú", "aviso");
        } else {
            try {
                AlxDiet a = listView.getSelectionModel().getSelectedItem();
                getAlxdiets().delete(a);
                listView.getItems().remove(a);
            } catch (DAOException ex) {
                excepcion(ex);
            }
        }
    }

    public void addAlimento() {
        try {
            AlxDiet a = getAlxDiet();
            if (a != null) {
                if (!a.isEmpty()) {
                    getAlxdiets().insert(a);
                    actualizarUso(a.getAlimento());
                    listView.getItems().add(a);
                }
            }
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    public void actualizarUso(Alimento a) {
        try {
            getAlimentos().update(a);
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    public String getDia() {
        return comboDia.getSelectionModel().getSelectedItem();
    }

    public String getMomento() {
        return comboMomento.getSelectionModel().getSelectedItem();
    }

    public void getMenu() {
        listView.getItems().clear();
        if (!getDieta().isEmpty()) {
            try {
                listView.setItems(getAlxdiets().where(getDieta().getPlankey(), getDia(), getMomento()));
            } catch (DAOException e) {
                excepcion(e);
            }
        }
    }

    /*Frame de alimentos:*/

    @FXML
    private ComboBox<Alimento> comboAlimentosAlimentos;

    @FXML
    private TextField textBuscarAlimentoAlimentos;

    @FXML
    private TextField textNombreAlimento;

    @FXML
    private TextField textKilocaloriasAlimentos;

    @FXML
    private TextField textProteinaAlimentos;

    @FXML
    private TextField textGrasasAlimentos;

    @FXML
    private TextField textCarbosAlimentos;
    @FXML
    private TextField textUnidad;

    public void registrarAlimento() {
        try {
            Alimento c = captarAlimento();
            if (c.isEmpty()) {
                mensaje("Los campos señalados con asterisco son obligatorios.", "aviso");
            } else {
                getAlimentos().insert(c);
                mensaje("Alimento registrado", "exito");
                obtenerAlimentos();
            }
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }


    public void modificarAlimento() {
        if (!comboAlimentosAlimentos.getItems().isEmpty()) {
            try {
                Alimento a = captarAlimento();
                if (!a.isEmpty()) {
                    a.setAlimentokey(comboAlimentosAlimentos.getSelectionModel().getSelectedItem().getAlimentokey());
                    getAlimentos().update(a);
                    textBuscarAlimentoAlimentos.setText(textNombreAlimento.getText());
                    obtenerAlimentos();
                    mensaje("Alimento modificado", "exito");
                } else {
                    mensaje("Los campos señalados con asterisco son obligatorios.", "aviso");
                }
            } catch (DAOException ex) {
                excepcion(ex);
            }
        } else {
            mensaje("Seleccione un alimento", "aviso");
        }
    }


    public void eliminarAlimento() {
        if (!comboAlimentosAlimentos.getItems().isEmpty()) {
            try {
                Alimento a = captarAlimento();
                if (!a.isEmpty()) {
                    a.setAlimentokey(comboAlimentosAlimentos.getSelectionModel().getSelectedItem().getAlimentokey());
                    getAlimentos().delete(a);
                    mensaje("Alimento eliminado", "exito");
                    obtenerAlimentos();
                } else {
                    mensaje("Los campos señalados con asterisco son obligatorios.", "aviso");
                }
            } catch (DAOException ex) {
                excepcion(ex);
            }
        } else {
            mensaje("Seleccione un alimento", "aviso");

        }
    }


    public void limpiarAlimento() {
        textNombreAlimento.setText("");
        textProteinaAlimentos.setText("");
        textGrasasAlimentos.setText("");
        textCarbosAlimentos.setText("");
        textKilocaloriasAlimentos.setText("");
    }


    public void mostrarAlimento() {
        if (!comboAlimentosAlimentos.getItems().isEmpty()) {
            Alimento c = comboAlimentosAlimentos.getSelectionModel().getSelectedItem();
            textNombreAlimento.setText(Operacion.camelCase(c.getNombre()));
            textProteinaAlimentos.setText("" + c.getProteinas());
            textGrasasAlimentos.setText("" + c.getGrasas());
            textCarbosAlimentos.setText("" + c.getCarbohidratos());
            textKilocaloriasAlimentos.setText("" + Operacion.redondear(c.getKilocalorias()));
            textUnidad.setText(c.getUnidad());
        }
    }


    public Alimento captarAlimento() throws DAOException {
        Alimento c = new Alimento();
        c.setNombre(textNombreAlimento.getText());
        c.setProteinas((textProteinaAlimentos.getText().isEmpty()) ? 0 : Double.parseDouble(textProteinaAlimentos.getText()));
        c.setGrasas((textGrasasAlimentos.getText().isEmpty()) ? 0 : Double.parseDouble(textGrasasAlimentos.getText()));
        c.setCarbohidratos((textCarbosAlimentos.getText().isEmpty()) ? 0 : Double.parseDouble(textCarbosAlimentos.getText()));
        c.setUnidad((textUnidad.getText().isEmpty()) ? "gramos" : textUnidad.getText());
        return c;
    }

String buscar="";
    public void buscarAlimento(ActionEvent e){
        TextField t= (TextField) e.getSource();
        buscar=t.getText();
        obtenerAlimentos();
    }
    public void obtenerAlimentos() {
        try {
            comboAlimentosAlimentos.getItems().clear();
            comboAlimentos.getItems().clear();
            if (buscar.isEmpty()) {
                alimentos = getAlimentos().all();
            } else {
                alimentos = getAlimentos().where(buscar);
            }
            if (!alimentos.isEmpty()) {
                comboAlimentosAlimentos.setItems(alimentos);
                comboAlimentos.setItems(alimentos);
                selectAlimento(0);
            }
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    public void selectAlimento(int i) {
        if (!comboAlimentosAlimentos.getItems().isEmpty()) {
            comboAlimentosAlimentos.getSelectionModel().select(i);
            comboAlimentos.getSelectionModel().select(i);
            mostrarAlimento();
        }
    }








}
