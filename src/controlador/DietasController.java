/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import DAO.DAOException;
import static controlador.Controller.alimentos;
import static controlador.Controller.excepcion;
import static controlador.Controller.getAlimentos;
import static controlador.Controller.isAlimentosUpdated;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import modelo.plan.Alimento;
import modelo.plan.AlxDiet;
import modelo.plan.Plan;

/**
 *
 * @author 201621279487
 */
public class DietasController extends Controller<Plan> {

    @FXML
    private ComboBox<Alimento> comboAlimentos;

    @FXML
    private ComboBox<String> comboOpciones;

    @FXML
    private TextField textBuscarAlimento;

    @FXML
    private ComboBox<Plan> comboDieta;

    @FXML
    private TextField textNombre;

    @FXML
    private ComboBox<String> comboObjetivos;

    @FXML
    private ComboBox<String> comboSexo;

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
    private Label carbosDistribucion;

    @FXML
    private Label proteinasDistribucion;

    @FXML
    private Label grasasDistribucion;

    @FXML
    private Label gramosPlan;

    @FXML
    private Label kcalPlan;

    @FXML
    private Label carbosPlan;

    @FXML
    private Label proteinasPlan;

    @FXML
    private Label grasasPlan;

    @FXML
    private Label gramosMenu;

    @FXML
    private Label kcalMenu;

    @FXML
    private Label carbosMenu;

    @FXML
    private Label proteinasMenu;

    @FXML
    private Label grasasMenu;

    @FXML
    private ListView<AlxDiet> listView;

    @FXML
    private ToggleButton buttonDomingo;

    @FXML
    private ToggleGroup dias;

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
    private ToggleButton buttonDesayuno;

    @FXML
    private ToggleGroup momento;

    @FXML
    private ToggleButton buttonAlmuerzo;

    @FXML
    private ToggleButton buttonCena;

    @FXML
    private ToggleButton buttonPre;

    @FXML
    private ToggleButton buttonPost;

    @FXML
    private ToggleButton buttonMer;

    @FXML
    private ToggleButton buttonExtra;

    @FXML
    private TextField textCantidad;

    @FXML
    CheckBox checkCal;

    @FXML
    private Label labelunidad;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboObjetivos.setItems(objetivos);
        comboObjetivos.getSelectionModel().select(0);
        comboSexo.setItems(sexos);
        comboSexo.getSelectionModel().select(0);
        comboOpciones.getItems().setAll("Opcion 1", "Opcion 2", "Opcion 3");
        comboOpciones.getSelectionModel().select(0);
        setDietasUpdated(true);
        updated();
        setAlimentosUpdated(true);
    }

    @Override
    public void updated() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Runnable updater = new Runnable() {
                    @Override
                    public void run() {
                        if (isDietasUpdated()) {
                            obtener();
                        }
                        if (isMedidaUpdated()) {
                            activateCheck();
                        }
                        if (isAlimentosUpdated()) {
                            try {
                                comboAlimentos.getItems().clear();
                                if (textBuscarAlimento.getText().isEmpty()) {
                                    alimentos = getAlimentos().obtenerTodos();
                                } else {
                                    alimentos = getAlimentos().obtenerTodos(textBuscarAlimento.getText());
                                }
                                if (!alimentos.isEmpty()) {
                                    comboAlimentos.setItems(alimentos);
                                    comboAlimentos.getSelectionModel().select(0);
                                }
                            } catch (DAOException ex) {
                                excepcion(ex);
                            }
                            setAlimentosUpdated(false);
                        }
                    }
                };
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                    }
                    // UI update is run on the Application thread
                    Platform.runLater(updater);
                }
            }
        });
        t.setDaemon(true);
        t.start();
    }

    public void activateCheck() {
        if (medidas.isEmpty()) {
            checkCal.setDisable(true);
        } else {
            checkCal.setDisable(false);
        }
        checkCal.setSelected(false);
        textKcal.setDisable(false);
    }

    public void setKcal() {
        if (checkCal.isSelected()) {
            textKcal.setText(getMedida().getSuperavitODeficit() + "");
            textKcal.setDisable(true);
        } else {
            if (textKcal.getText().isEmpty()) {
                textKcal.setText("2000");
            }
            textKcal.setDisable(false);
        }
    }

    /**
     * Este método calcula los macronutrientes calculando la distribución
     * porcentual digitada por el usuario.
     *
     * PD: Se puede hacer así: que al ingresar un numero en un textfield y la
     * suma de lo que hay en los tres se pasa de 100, entonces la diferencia se
     * divide entre dos y se le resta a los que no se están modificando. Si la
     * resta excede el monto de 0 en uno, se le debe restar al otro y si el otro
     * llega a cero, querrá decir que el que se está modificando llegó al tope
     * máximo de 100
     *
     *
     */
    public void calcular() {
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
    }

    @Override
    public Plan captar() throws DAOException {
        Plan d = new Plan();
        d.setTipo("dieta");
        d.setNombre(textNombre.getText());
        d.setObjetivo(comboObjetivos.getSelectionModel().getSelectedItem());
        d.setDescripcion(textDescripcion.getText());
        d.setEdad((textEdad.getText().isEmpty()) ? 0 : Integer.parseInt(textEdad.getText()));
        d.setSexo(comboSexo.getSelectionModel().getSelectedItem());
        return d;
    }

    @Override
    public void obtener() {
        try {
            comboDieta.getItems().clear();
            if (textBuscar.getText().isEmpty()) {
                dietas = getPlanes().obtenerDietas();
            } else {
                dietas = getPlanes().obtenerDietas(textBuscar.getText());
            }
            if (!dietas.isEmpty()) {
                comboDieta.setItems(dietas);
                select(0);
            }
        } catch (DAOException ex) {
            excepcion(ex);
        }
        setDietasUpdated(false);
    }

    public void obtenerAlimentos() {
        try {
            comboAlimentos.getItems().clear();
            alimentos = getAlimentos().obtenerTodos(textBuscarAlimento.getText());
            if (!alimentos.isEmpty()) {
                comboAlimentos.setItems(alimentos);
                comboAlimentos.getSelectionModel().select(0);
                selectAlimento();
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

    @Override
    public void registrar() {
        try {
            Plan m = captar();
            if (m != null) {
                if (m.isEmpty()) {
                    mensaje("Los campos señalados con asterisco son obligatorios.", "aviso");
                } else {
                    getPlanes().insertar(m);
                    obtener();
                    mensaje("Plan de alimentación registrado", "exito");
                }
            }
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    @Override
    public void modificar() {
        try {
            if (!comboDieta.getItems().isEmpty()) {
                Plan m = captar();
                if (m != null) {
                    m.setPlankey(comboDieta.getSelectionModel().getSelectedItem().getPlankey());
                    if (m.isEmpty()) {
                        mensaje("Los campos señalados con asterisco son obligatorios.", "aviso");
                    } else {
                        getPlanes().modificar(m);
                        textBuscar.setText(textNombre.getText());
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

    @Override
    public void eliminar() {
        try {
            if (!comboDieta.getItems().isEmpty()) {
                getPlanes().eliminar(comboDieta.getSelectionModel().getSelectedItem());
                obtener();
                mensaje("Plan de alimentación eliminado", "exito");
            } else {
                mensaje("No hay planes de alimentación que eliminar", "aviso");
            }
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    @Override
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

    @Override
    public void mostrar() {
        if (!comboDieta.getItems().isEmpty()) {
            Plan d = comboDieta.getSelectionModel().getSelectedItem();
            textNombre.setText(d.getNombre());
            selectCombo(comboObjetivos, d.getObjetivo());
            textDescripcion.setText(d.getDescripcion());
            textEdad.setText(d.getEdad() + "");
            selectCombo(comboSexo, comboSexo.getSelectionModel().getSelectedItem());
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
            a.setCombinacion(comboOpciones.getSelectionModel().getSelectedItem());
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
                AlxDiet b = getAlxdiets().obtener(a.getAlimento().getAlimentokey(), a.getDia(), a.getMomento());
                getAlxdiets().eliminar(b);
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
                    getAlxdiets().insertar(a);
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
            getAlimentos().modificar(a);
            setAlimentosUpdated(true);
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    public String getDia() {
        if (buttonDomingo.isSelected()) {
            return AlxDiet.DOMINGO;
        }
        if (buttonLunes.isSelected()) {
            return AlxDiet.LUNES;
        }
        if (buttonMartes.isSelected()) {
            return AlxDiet.MARTES;
        }
        if (buttonMiercoles.isSelected()) {
            return AlxDiet.MIERCOLES;
        }
        if (buttonJueves.isSelected()) {
            return AlxDiet.JUEVES;
        }
        if (buttonViernes.isSelected()) {
            return AlxDiet.VIERNES;
        }
        if (buttonSabado.isSelected()) {
            return AlxDiet.SABADO;
        }
        mensaje("Seleccione un día de la semana", "aviso");
        return null;
    }

    public String getMomento() {
        if (buttonDesayuno.isSelected()) {
            return AlxDiet.DESAYUNO;
        }
        if (buttonAlmuerzo.isSelected()) {
            return AlxDiet.ALMUERZO;
        }
        if (buttonCena.isSelected()) {
            return AlxDiet.CENA;
        }
        if (buttonPost.isSelected()) {
            return AlxDiet.POSTENTRENO;
        }
        if (buttonPre.isSelected()) {
            return AlxDiet.PREENTRENO;
        }
        if (buttonMer.isSelected()) {
            return AlxDiet.MERIENDA;
        }
        if (buttonExtra.isSelected()) {
            return AlxDiet.EXTRA;
        }
        mensaje("Seleccione el momento del menú", "aviso");
        return null;
    }

    public void getDesayuno() throws DAOException {
        String dia = getDia();
        if (getDieta().isEmpty()) {
            mensaje("Registre primero un plan", "aviso");
        } else {
            if (buttonDesayuno.isSelected()) {
                if (dia.isEmpty()) {
                    mensaje("Seleccione un día primero", "aviso");
                    buttonDesayuno.setSelected(false);
                } else {
                    listView.setItems(getAlxdiets().obtenerTodos(getDieta()
                            .getPlankey(), getDia(), AlxDiet.DESAYUNO));
                }
            } else {
                listView.getItems().clear();
            }
        }
    }

    public void getAlmuerzo() throws DAOException {
        String dia = getDia();
        if (getDieta().isEmpty()) {
            mensaje("Registre primero un plan", "aviso");
        } else {
            if (buttonAlmuerzo.isSelected()) {
                if (dia.isEmpty()) {
                    mensaje("Seleccione un día primero", "aviso");
                    buttonAlmuerzo.setSelected(false);
                } else {
                    listView.setItems(getAlxdiets().obtenerTodos(getDieta()
                            .getPlankey(), getDia(), AlxDiet.ALMUERZO));
                }
            } else {
                listView.getItems().clear();
            }
        }
    }

    public void getCena() throws DAOException {
        String dia = getDia();
        if (getDieta().isEmpty()) {
            mensaje("Registre primero un plan", "aviso");
        } else {
            if (buttonCena.isSelected()) {
                if (dia.isEmpty()) {
                    mensaje("Seleccione un día primero", "aviso");
                    buttonCena.setSelected(false);
                } else {
                    listView.setItems(getAlxdiets().obtenerTodos(getDieta()
                            .getPlankey(), getDia(), AlxDiet.CENA));
                }
            } else {
                listView.getItems().clear();
            }
        }
    }

    public void getMerienda() throws DAOException {
        String dia = getDia();
        if (getDieta().isEmpty()) {
            mensaje("Registre primero un plan", "aviso");
        } else {
            if (buttonMer.isSelected()) {
                if (dia.isEmpty()) {
                    mensaje("Seleccione un día primero", "aviso");
                    buttonMer.setSelected(false);
                } else {
                    listView.setItems(getAlxdiets().obtenerTodos(getDieta()
                            .getPlankey(), getDia(), AlxDiet.MERIENDA));
                }
            } else {
                listView.getItems().clear();
            }
        }
    }

    public void getExtra() throws DAOException {
        String dia = getDia();
        if (getDieta().isEmpty()) {
            mensaje("Registre primero un plan", "aviso");
        } else {
            if (buttonExtra.isSelected()) {
                if (dia.isEmpty()) {
                    mensaje("Seleccione un día primero", "aviso");
                    buttonExtra.setSelected(false);
                } else {
                    listView.setItems(getAlxdiets().obtenerTodos(getDieta()
                            .getPlankey(), getDia(), AlxDiet.EXTRA));
                }
            } else {
                listView.getItems().clear();
            }
        }
    }

    public void getPre() throws DAOException {
        String dia = getDia();
        if (getDieta().isEmpty()) {
            mensaje("Registre primero un plan", "aviso");
        } else {
            if (buttonPre.isSelected()) {
                if (dia.isEmpty()) {
                    mensaje("Seleccione un día primero", "aviso");
                    buttonPre.setSelected(false);
                } else {
                    listView.setItems(getAlxdiets().obtenerTodos(getDieta()
                            .getPlankey(), getDia(), AlxDiet.PREENTRENO));
                }
            } else {
                listView.getItems().clear();
            }
        }
    }

    public void getPost() throws DAOException {
        String dia = getDia();
        if (getDieta().isEmpty()) {
            mensaje("Registre primero un plan", "aviso");
        } else {
            if (buttonPost.isSelected()) {
                if (dia.isEmpty()) {
                    mensaje("Seleccione un día primero", "aviso");
                    buttonPost.setSelected(false);
                } else {
                    listView.setItems(getAlxdiets().obtenerTodos(getDieta()
                            .getPlankey(), getDia(), AlxDiet.POSTENTRENO));
                }
            } else {
                listView.getItems().clear();
            }
        }
    }
}
