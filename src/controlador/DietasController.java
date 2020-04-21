/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import DAO.DAOException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import modelo.Referencia;
import modelo.plan.Alimento;
import modelo.plan.AlxDiet;
import modelo.plan.BasePlan;
import modelo.plan.Plan;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author 201621279487
 */
public class DietasController extends Controller<Plan> {

    @FXML
    Button buttonCal;
    @FXML
    private TextField textBuscarAlx;
    @FXML
    private ComboBox<Plan> comboDieta;
    @FXML
    private TextField textNombre;
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
    private TextField textBuscarAlimento;
    @FXML
    private TextField textNombreAlimento;
    @FXML
    private TextField textPluralAlimento;
    @FXML
    private TextField textKilocaloriasAlimentos;
    @FXML
    private TextField textProteinaAlimentos;
    @FXML
    private TextField textGrasasAlimentos;
    @FXML
    private TextField textCarbosAlimentos;
    @FXML
    private TextField textKcalMenu;

    @FXML
    private TextField textGramosMenu;

    @FXML
    private TextField textProteinasMenu;

    @FXML
    private TextField textCarbosMenu;

    @FXML
    private TextField textGrasasMenu;

    @FXML
    private Spinner<Integer> spinnerCantidad;
    @FXML
    private Spinner<Integer> spinnerGramos;
    @FXML
    private ToggleButton buttonLunes;

    @FXML
    private ToggleButton buttonMartes;

    @FXML
    private ToggleButton buttonMiercoles;

    @FXML
    private ToggleButton buttonJueves;

    @FXML
    private ToggleButton buttonViernes;

    @FXML
    private ToggleButton buttonSabado;

    @FXML
    private ToggleButton buttonDomingo;

    @FXML
    private ToggleButton buttonDesayuno;

    @FXML
    private ToggleButton buttonAM;

    @FXML
    private ToggleButton buttonAlmuerzo;

    @FXML
    private ToggleButton buttonPM;

    @FXML
    private ToggleButton buttonCena;

    @FXML
    private ToggleButton buttonPre;

    @FXML
    private ToggleButton buttonPost;

    @FXML
    private TextField textNombreAlx;
    @FXML
    private TextField textPluralAlx;
    @FXML
    private Label labelPresentacion;
    @FXML
    private Label labelUnidad;
    @FXML
    private ComboBox<String> comboUnidades;

    private AutoCompletionBinding autoCompletionAlx;

    private AutoCompletionBinding autoCompletionAlimentos;
    private AutoCompletionBinding autoCompletionPresentaciones;
    private Alimento alimento;
    @FXML
    private TextField textPresentacion;
    private ObservableList<Referencia> presentaciones;

    public void initialize(URL location, ResourceBundle resources) {
        obtener();
        obtenerAlimentos();
        getMenu();
        calcular();
        spinnerCantidad.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 0));
        spinnerGramos.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10000, 0));
        comboUnidades.getItems().setAll(AlxDiet.UNIDADES);
        comboUnidades.getSelectionModel().select(0);
        cambiarLabelUnidad();
    }

    public void setKcal() {
        if (getMedida().isEmpty()) {
            mensaje("Este cliente no tiene medidas", "aviso");
        } else {
            textKcal.setText(Operacion.formatear(getMedida().getSuperavitODeficit()));
            calcular();
        }
    }

    /**
     * Este método calcula los macronutrientes calculando la distribución
     * porcentual digitada por el usuario.
     */
    public void calcular() {
        if (textKcal.getText().isEmpty()) {
            textKcal.setText("2000");
        }
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
        double proteinas = (cal * pro) / 4;
        double grasas = (cal * gra) / 9;
        double carbohidratos = (cal * car) / 4;
        //Los ponemos en las etiquetas asignadas
        proteinasDistribucion.setText(proteinas + "");
        grasasDistribucion.setText(grasas + "");
        carbosDistribucion.setText(carbohidratos + "");

    }

    public Plan captar() throws DAOException {
        Plan d = new Plan();
        d.setTipo("dieta");
        d.setNombre(textNombre.getText());
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
        getMenu();
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
                if (comboDieta.getSelectionModel().getSelectedItem().getPlankey() == 2) {
                    mensaje("El plan de ejemplo no se puede modificar", "aviso");
                } else {
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
    /*Frame de alimentos:*/

    public void eliminar() {
        try {
            if (!comboDieta.getItems().isEmpty()) {
                if (comboDieta.getSelectionModel().getSelectedItem().getPlankey() == 2) {
                    mensaje("El plan de ejemplo no se puede eliminar", "aviso");
                } else {
                    eliminarAlxDiets();
                    getPlanes().delete(comboDieta.getSelectionModel().getSelectedItem());
                    obtener();
                    mensaje("Plan de alimentación eliminado", "exito");
                }
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
        textBuscar.setText("");
        textKcal.setText("");
        textProteinas.setText("");
        textCarbohidratos.setText("");
        textGrasas.setText("");
        listView.getItems().clear();
        textNombreAlimento.setText("");
        textProteinaAlimentos.setText("");
        textGrasasAlimentos.setText("");
        textCarbosAlimentos.setText("");
        textKilocaloriasAlimentos.setText("");
        textBuscarAlx.setText("");
        textNombreAlx.setText("");
        textPluralAlx.setText("");
        textPluralAlimento.setText("");
        spinnerCantidad.getEditor().setText("1");
        spinnerGramos.getEditor().setText("1");

    }

    public void mostrar() {
        if (!comboDieta.getItems().isEmpty()) {
            Plan d = comboDieta.getSelectionModel().getSelectedItem();
            textNombre.setText(Operacion.toCamelCase(d.getNombre()));
            getMenu();
        }
    }

    public void select(int i) {
        if (!comboDieta.getItems().isEmpty()) {
            comboDieta.getSelectionModel().select(i);
            mostrar();
        }
    }

    public AlxDiet getAlxDiet() throws DAOException {
        AlxDiet a = new AlxDiet();
        a.setPlan(getDieta());
        a.setAlimento(getAlimento());
        a.setCantidad(spinnerCantidad.getValue());
        a.setDia(getDia());
        a.setMomento(getMomento());
        a.setCantidad(spinnerCantidad.getValue());
        a.setGramos(spinnerGramos.getValue());
        a.setUnidad(comboUnidades.getSelectionModel().getSelectedItem());
        if(textPresentacion.getText().isEmpty()){
            System.out.println("sin presentacion");
            throw new DAOException("Digite primero una presentación");
        }
            a.setPresentacion(textPresentacion.getText());
        return a;
    }

    public Alimento getAlimento() throws DAOException {
        if (alimento == null) {
            throw new DAOException("No se ha seleccionado ningun alimento");
        }
        return alimento;
    }

    public void setAlimento(Alimento alimento) throws DAOException {
        this.alimento = alimento;
        if (alimento != null) {
            mostrarAlimento();
        }
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
        getMacrosMenu();
    }

    public void addAlimento() {
        if (comboDieta.getItems().isEmpty()) {
            mensaje("Primero guarde un plan de alimentacion", "aviso");
        } else {
            if (comboDieta.getSelectionModel().getSelectedItem().getPlankey() == 2) {
                mensaje("El plan de ejemplo no se puede modificar", "aviso");
            } else {
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
        }
        getMacrosMenu();
    }

    public void actualizarUso(Alimento a) {
        try {
            getAlimentos().update(a);
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    public String getDia() throws DAOException {
        if (buttonLunes.isSelected()) {
            return BasePlan.LUNES;
        } else if (buttonMartes.isSelected()) {
            return BasePlan.MARTES;
        } else if (buttonMiercoles.isSelected()) {
            return BasePlan.MIERCOLES;
        } else if (buttonJueves.isSelected()) {
            return BasePlan.JUEVES;
        } else if (buttonViernes.isSelected()) {
            return BasePlan.VIERNES;
        } else if (buttonSabado.isSelected()) {
            return BasePlan.SABADO;
        } else if (buttonDomingo.isSelected()) {
            return BasePlan.DOMINGO;
        } else {
            throw new DAOException("Debe seleccionar un día");
        }
    }

    public String getMomento() throws DAOException {
        if (buttonDesayuno.isSelected()) {
            return AlxDiet.DESAYUNO;
        } else if (buttonAM.isSelected()) {
            return AlxDiet.SNACKAM;
        } else if (buttonAlmuerzo.isSelected()) {
            return AlxDiet.ALMUERZO;
        } else if (buttonPM.isSelected()) {
            return AlxDiet.SNACKPM;
        } else if (buttonCena.isSelected()) {
            return AlxDiet.CENA;
        } else if (buttonPre.isSelected()) {
            return AlxDiet.PREENTRENO;
        } else if (buttonPost.isSelected()) {
            return AlxDiet.POSTENTRENO;
        } else {
            throw new DAOException("Debe seleccionar un momento");
        }
    }

    public void getMenu() {
        listView.getItems().clear();
        if (!getDieta().isEmpty()) {
            try {
                listView.setItems(getAlxdiets().where(getDieta().getPlankey(), getDia(), getMomento()));
                getMacrosMenu();
            } catch (DAOException e) {
                excepcion(e);
            }
        }
    }

    public void getMacrosMenu() {
        if (!listView.getItems().isEmpty()) {
            double gramos = 0;
            double prote = 0;
            double carbos = 0;
            double grasas = 0;
            double kcal = 0;
            for (AlxDiet alx :
                    listView.getItems()) {
                gramos += alx.getCantidad();
                prote += alx.getProteinasxpeso();
                carbos += alx.getCarbohidratosxpeso();
                grasas += alx.getGrasasxpeso();
                kcal += alx.getKilocaloriasxpeso();
            }
            textGramosMenu.setText(Operacion.formatear(gramos));
            textProteinasMenu.setText(Operacion.formatear(prote));
            textCarbosMenu.setText(Operacion.formatear(carbos));
            textGrasasMenu.setText(Operacion.formatear(grasas));
            textKcalMenu.setText(Operacion.formatear(kcal));
        }
    }

    public void duplicarPlan() {
        if (!comboDieta.getItems().isEmpty()) {
            try {
                Plan copy = getDieta();
                copy.setNombre(getDieta().getNombre() + " - copia");
                getPlanes().insert(copy);
                ObservableList<AlxDiet> LUNES = getAlxdiets().where(getDieta().getPlankey(), BasePlan.LUNES);
                ObservableList<AlxDiet> MARTES = getAlxdiets().where(getDieta().getPlankey(), BasePlan.MARTES);
                ObservableList<AlxDiet> MIERCOLES = getAlxdiets().where(getDieta().getPlankey(), BasePlan.MIERCOLES);
                ObservableList<AlxDiet> JUEVES = getAlxdiets().where(getDieta().getPlankey(), BasePlan.JUEVES);
                ObservableList<AlxDiet> VIERNES = getAlxdiets().where(getDieta().getPlankey(), BasePlan.VIERNES);
                ObservableList<AlxDiet> SABADO = getAlxdiets().where(getDieta().getPlankey(), BasePlan.SABADO);
                ObservableList<AlxDiet> DOMINGO = getAlxdiets().where(getDieta().getPlankey(), BasePlan.DOMINGO);
                obtener();
                for (int i = 0; i < comboDieta.getItems().size(); i++) {
                    if (comboDieta.getItems().get(i).getNombre().equalsIgnoreCase(copy.getNombre())) {
                        comboDieta.getSelectionModel().select(i);
                        break;
                    }
                }
                for (AlxDiet item :
                        LUNES) {
                    item.setPlan(getDieta());
                    getAlxdiets().insert(item);
                }
                for (AlxDiet item :
                        MARTES) {
                    item.setPlan(getDieta());
                    getAlxdiets().insert(item);
                }
                for (AlxDiet item :
                        MIERCOLES) {
                    item.setPlan(getDieta());
                    getAlxdiets().insert(item);
                }
                for (AlxDiet item :
                        JUEVES) {
                    item.setPlan(getDieta());
                    getAlxdiets().insert(item);
                }
                for (AlxDiet item :
                        VIERNES) {
                    item.setPlan(getDieta());
                    getAlxdiets().insert(item);
                }
                for (AlxDiet item :
                        SABADO) {
                    item.setPlan(getDieta());
                    getAlxdiets().insert(item);
                }
                for (AlxDiet item :
                        DOMINGO) {
                    item.setPlan(getDieta());
                    getAlxdiets().insert(item);
                }
                mensaje("El plan ha sido duplicado", "exito");
            } catch (DAOException e) {
                excepcion(e);
            }

        }
        getMenu();
    }

    public void registrarAlimento() {
        try {
            Alimento c = captarAlimento();
            getAlimentos().insert(c);
            mensaje("Alimento registrado", "exito");
            obtenerAlimentos();
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    public void modificarAlimento(ActionEvent e) {
        try {
            Control t = (Control) e.getSource();
            String source = e.getSource().toString();
            Alimento a = getAlimento();
            Alimento c = null;
            if (source.contains("textNombreAlx") || source.contains("textPluralAlx")) {
                c = captarAlimentoAlx(textBuscarAlx.getText());
            } else if (source.contains("button")) {
                c = captarAlimento();
            }
            c.setAlimentokey(a.getAlimentokey());
            getAlimentos().update(c);
            obtenerAlimentos();
            mensaje("Alimento modificado", "exito");
            mostrarPresentaciones();
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    public void eliminarAlimento() {
        try {
            Alimento a = captarAlimento();
            getAlimentos().delete(a);
            mensaje("Alimento eliminado", "exito");
            obtenerAlimentos();
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    public void mostrarAlimento() throws DAOException {
        if (!getAlimento().isEmpty()) {
            textNombreAlimento.setText(getAlimento().toString());
            textPluralAlimento.setText(Operacion.inicialMayuscula(getAlimento().getPlural()));
            textProteinaAlimentos.setText(Operacion.formatear(getAlimento().getProteinas()));
            textGrasasAlimentos.setText(Operacion.formatear(getAlimento().getGrasas()));
            textCarbosAlimentos.setText(Operacion.formatear(getAlimento().getCarbohidratos()));
            textKilocaloriasAlimentos.setText(Operacion.formatear(getAlimento().getKilocalorias()));

            textBuscarAlimento.setText(getAlimento().toString());
            textBuscarAlx.setText(getAlimento().toString());
            textNombreAlx.setText(getAlimento().toString());
            textPluralAlx.setText(Operacion.inicialMayuscula(getAlimento().getPlural()));
            //mostrarPresentaciones();
        } else {
            limpiar();
        }
    }

    public Alimento captarAlimento() throws DAOException {
        Alimento c = new Alimento();
        if (textNombreAlimento.getText().isEmpty()) {
            throw new DAOException("Digite el nombre del alimento");
        }
        if (textPluralAlimento.getText().isEmpty()) {
            throw new DAOException("Digite el nombre plural para el alimento");
        }
        c.setNombre(textNombreAlimento.getText());
        c.setPlural(textPluralAlimento.getText());
        c.setProteinas((textProteinaAlimentos.getText().isEmpty()) ? 0 : Double.parseDouble(textProteinaAlimentos.getText()));
        c.setGrasas((textGrasasAlimentos.getText().isEmpty()) ? 0 : Double.parseDouble(textGrasasAlimentos.getText()));
        c.setCarbohidratos((textCarbosAlimentos.getText().isEmpty()) ? 0 : Double.parseDouble(textCarbosAlimentos.getText()));
        return c;
    }

    public Alimento captarAlimentoAlx(String buscar) throws DAOException {
        Alimento c = getAlimento();
        if (textNombreAlx.getText().isEmpty()) {
            throw new DAOException("Digite el nombre del alimento");
        }
        if (textPluralAlx.getText().isEmpty()) {
            throw new DAOException("Digite el nombre plural para el alimento");
        }
        c.setNombre(textNombreAlx.getText());
        c.setPlural(textPluralAlx.getText());
        c.setProteinas((textProteinaAlimentos.getText().isEmpty()) ? 0 : Double.parseDouble(textProteinaAlimentos.getText()));
        c.setGrasas((textGrasasAlimentos.getText().isEmpty()) ? 0 : Double.parseDouble(textGrasasAlimentos.getText()));
        c.setCarbohidratos((textCarbosAlimentos.getText().isEmpty()) ? 0 : Double.parseDouble(textCarbosAlimentos.getText()));
        return c;
    }

    public void obtenerAlimentos() {
        try {
            alimentos = getAlimentos().all();

            if (autoCompletionAlimentos != null) {
                autoCompletionAlimentos.dispose();
            }

            if (autoCompletionAlx != null) {
                autoCompletionAlx.dispose();
            }

            autoCompletionAlimentos=TextFields.bindAutoCompletion(textBuscarAlimento, alimentos);
            autoCompletionAlx=TextFields.bindAutoCompletion(textBuscarAlx, alimentos);
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    public void buscarAlimento(ActionEvent e) {
        TextField t = (TextField) e.getSource();
        if (t.getText().isEmpty()) {
            mensaje("Digite el nombre del alimento a buscar", "aviso");
        } else {
            for (Alimento item : alimentos) {
                if (item.getNombre().equals(t.getText().toLowerCase())) {
                    try {
                        setAlimento(item);
                    } catch (DAOException ex) {
                        excepcion(ex);
                    }
                    break;
                }
            }
        }
    }

    public void mostrarPresentaciones() {
        try {
            AlxDiet alx = getAlxDiet();
            labelPresentacion.setText(alx.calcularPresentacion());
        } catch (DAOException e) {
            excepcion(e);
        }
    }

    public void cambiarLabelUnidad() {
        labelUnidad.setText(comboUnidades.getSelectionModel().getSelectedItem());
    }

    public void insertarPresentacion() {
        String presentacion = textPresentacion.getText();
        if (presentacion.isEmpty()) {
            mensaje("Digite primero una presentación", "aviso");
        } else {
            if (!(presentacion.contains("alimento") && presentacion.contains("cantidad") && presentacion.contains("unidad"))) {
                mensaje("Debe usar al menos una palara clave: CANTIDAD, UNIDAD, ALIMENTO", "aviso");
            } else {
                Referencia ref = new Referencia();
                ref.setNombre(presentacion.toLowerCase());
                ref.setDescripcion("presentacion");
                ref.setDato("alimento");
                obtenerPresentaciones();
                mensaje("Presentación guardada","exito");
            }
        }
    }

    public void eliminarPresentacion() {
        String presentacion = textPresentacion.getText();
        if (presentacion.isEmpty()) {
            mensaje("Digite primero una presentación", "aviso");
        } else {
                Referencia ref;
                for (Referencia item : presentaciones) {
                    if (item.getNombre().equals(presentacion)) {
                        try {
                            getReferencias().delete(item);
                            obtenerPresentaciones();
                            mensaje("Presentación eliminada","exito");
                            break;
                        } catch (DAOException e) {
                            excepcion(e);
                        }
                    }
            }
        }
    }

    public void obtenerPresentaciones() {
        try {
            presentaciones = getReferencias().obtenerPresentaciones("alimento");

            if (autoCompletionPresentaciones != null) {
                autoCompletionPresentaciones.dispose();
            }

            autoCompletionPresentaciones= TextFields.bindAutoCompletion(textPresentacion, presentaciones);
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

}
