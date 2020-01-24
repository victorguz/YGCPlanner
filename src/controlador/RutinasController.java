/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import DAO.DAOException;
import static controlador.Controller.ejercicios;
import static controlador.Controller.getEjercicios;
import static controlador.Controller.isEjerciciosUpdated;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import modelo.plan.Ejercicio;
import modelo.plan.EjxRut;
import modelo.plan.Plan;

/**
 *
 * @author 201621279487
 */
public class RutinasController extends Controller<Plan> {

    @FXML
    private ComboBox<Plan> comboRutinas;

    @FXML
    private TextField textNombre;

    @FXML
    private ComboBox<String> comboObjetivos;

    @FXML
    private ComboBox<String> comboSexo;

    @FXML
    private TextArea textDescripcion;

    @FXML
    private TextField textBuscarEjercicio;

    @FXML
    private ComboBox<Ejercicio> comboEjercicios;

    @FXML
    private ComboBox<Integer> comboSeries;

    @FXML
    private ComboBox<Integer> comboRepeticiones;

    @FXML
    private ToggleButton buttonDomingo;

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
    private ToggleButton buttonPre;

    @FXML
    private ToggleButton buttonEntreno;

    @FXML
    private ToggleButton buttonPost;

    @FXML
    private ListView<EjxRut> listView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboObjetivos.setItems(objetivos);
        comboObjetivos.getSelectionModel().select(0);
        comboSexo.setItems(sexos);
        comboSexo.getSelectionModel().select(0);
        for (int i = 5; i <= 200; i = i + 5) {
            comboRepeticiones.getItems().add(i);
        }
        comboRepeticiones.getSelectionModel().select(0);
        comboSeries.getItems().setAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        comboSeries.getSelectionModel().select(0);
        setRutinasUpdated(true);
        updated();
        setEjerciciosUpdated(true);
    }

    @Override
    public void updated() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Runnable updater = new Runnable() {
                    @Override
                    public void run() {
                        if (isRutinasUpdated()) {
                            obtener();
                        }
                        if (isEjerciciosUpdated()) {
                            comboEjercicios.setItems(ejercicios);
                            comboEjercicios.getSelectionModel().select(0);
                            setEjerciciosUpdated(false);
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

    @Override
    public Plan captar() throws DAOException {
        Plan d = new Plan();
        d.setTipo("rutina");
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
            comboRutinas.getItems().clear();
            if (textBuscar.getText().isEmpty()) {
                rutinas = getPlanes().obtenerRutinas();
            } else {
                rutinas = getPlanes().obtenerRutinas(textBuscar.getText());
            }
            if (!rutinas.isEmpty()) {
                comboRutinas.setItems(rutinas);
                select(0);
            }
        } catch (DAOException ex) {
            excepcion(ex);
        }
        setRutinasUpdated(false);
    }

    public void obtenerEjercicios() {
        try {
            comboEjercicios.getItems().clear();
            ejercicios = getEjercicios().obtenerTodos(textBuscarEjercicio.getText());
            if (!ejercicios.isEmpty()) {
                comboEjercicios.setItems(ejercicios);
                comboEjercicios.getSelectionModel().select(0);
            }
        } catch (DAOException ex) {
            excepcion(ex);
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
                    mensaje("Plan de entrenamiento registrado", "exito");
                }
            }
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    @Override
    public void modificar() {
        try {
            if (!comboRutinas.getItems().isEmpty()) {
                Plan m = captar();
                if (m != null) {
                    m.setPlankey(comboRutinas.getSelectionModel().getSelectedItem().getPlankey());
                    if (m.isEmpty()) {
                        mensaje("Los campos señalados con asterisco son obligatorios.", "aviso");
                    } else {
                        getPlanes().modificar(m);
                        textBuscar.setText(textNombre.getText());
                        obtener();
                        mensaje("Plan de entrenamiento modificado", "exito");
                    }
                }
            } else {
                mensaje("No hay planes de entrenamiento que modificar", "aviso");
            }
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    @Override
    public void eliminar() {
        try {
            if (!comboRutinas.getItems().isEmpty()) {
                getPlanes().eliminar(comboRutinas.getSelectionModel().getSelectedItem());
                obtener();
                mensaje("Plan de entrenamiento eliminado", "exito");
            } else {
                mensaje("No hay planes de entrenamiento que eliminar", "aviso");
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
    }

    @Override
    public void mostrar() {
        if (!comboRutinas.getItems().isEmpty()) {
            Plan d = comboRutinas.getSelectionModel().getSelectedItem();
            textNombre.setText(d.getNombre());
            selectCombo(comboObjetivos, d.getObjetivo());
            textDescripcion.setText(d.getDescripcion());
            textEdad.setText(d.getEdad() + "");
            selectCombo(comboSexo, comboSexo.getSelectionModel().getSelectedItem());
        }
    }

    public void select(int i) {
        if (!comboRutinas.getItems().isEmpty()) {
            comboRutinas.getSelectionModel().select(i);
            mostrar();
        }
    }

    public EjxRut getEjxRut() {
        if (!comboRutinas.getItems().isEmpty()) {
            EjxRut a = new EjxRut();
            a.setPlan(getRutina());
            a.setEjercicio(comboEjercicios.getSelectionModel().getSelectedItem());
            a.setSeries(comboSeries.getSelectionModel().getSelectedItem());
            a.setRepeticiones(comboRepeticiones.getSelectionModel().getSelectedItem());
            a.setDia(getDia());
            a.setMomento(getMomento());
            return a;
        } else {
            mensaje("Primero debe registrar un plan", "aviso");
        }
        return null;
    }

    public Plan getRutina() {
        if (comboRutinas.getItems().isEmpty()) {
            return null;
        } else {
            return comboRutinas.getSelectionModel().getSelectedItem();
        }
    }

    @FXML
    void deleteEjx(ActionEvent event) {
        if (listView.getSelectionModel().getSelectedIndex() == -1) {
            mensaje("Seleccione un ejercicio en el menú", "aviso");
        } else {
            try {
                EjxRut a = listView.getSelectionModel().getSelectedItem();
                EjxRut b = getEjxruts().obtener(a.getEjercicio().getEjerciciokey(), a.getDia(), a.getMomento());
                getEjxruts().eliminar(b);
                listView.getItems().remove(a);
            } catch (DAOException ex) {
                excepcion(ex);
            }
        }
    }

    public void addEjercicio() {
        try {
            EjxRut a = getEjxRut();
            if (a != null) {
                if (!a.isEmpty()) {
                    getEjxruts().insertar(a);
                    actualizarUso(a.getEjercicio());
                    listView.getItems().add(a);
                } else {
                    mensaje("EjxRut vacío", "aviso");
                }
            } else {
                mensaje("EjxRut nulo", "aviso");
            }
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    public void actualizarUso(Ejercicio a) {
        try {
            getEjercicios().modificar(a);
            setEjerciciosUpdated(true);
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    public String getDia() {
        if (buttonDomingo.isSelected()) {
            return EjxRut.DOMINGO;
        }
        if (buttonLunes.isSelected()) {
            return EjxRut.LUNES;
        }
        if (buttonMartes.isSelected()) {
            return EjxRut.MARTES;
        }
        if (buttonMiercoles.isSelected()) {
            return EjxRut.MIERCOLES;
        }
        if (buttonJueves.isSelected()) {
            return EjxRut.JUEVES;
        }
        if (buttonViernes.isSelected()) {
            return EjxRut.VIERNES;
        }
        if (buttonSabado.isSelected()) {
            return EjxRut.SABADO;
        }
        mensaje("Seleccione un día de la semana", "aviso");
        return null;
    }

    public String getMomento() {
        if (buttonEntreno.isSelected()) {
            return EjxRut.ENTRENO;
        }
        if (buttonPre.isSelected()) {
            return EjxRut.PREENTRENO;
        }
        if (buttonPost.isSelected()) {
            return EjxRut.POSTENTRENO;
        }
        mensaje("Seleccione el momento del menú", "aviso");
        return null;
    }

    public void getEntreno() throws DAOException {
        if (getRutina() == null) {
            mensaje("Registre primero un plan", "aviso");
            buttonEntreno.setSelected(false);
        } else {
            String dia = getDia();
            if (dia.isEmpty()) {
                mensaje("Seleccione un día primero", "aviso");
                buttonEntreno.setSelected(false);
            } else {
                listView.setItems(getEjxruts().obtenerTodos(getRutina()
                        .getPlankey(), getDia(), EjxRut.ENTRENO));
            }
        }
    }

    public void getPre() throws DAOException {
        if (getRutina() == null) {
            mensaje("Registre primero un plan", "aviso");
            buttonPre.setSelected(false);
        } else {
            String dia = getDia();
            if (dia.isEmpty()) {
                mensaje("Seleccione un día primero", "aviso");
                buttonPre.setSelected(false);
            } else {
                ObservableList<EjxRut> l = getEjxruts().obtenerTodos(getRutina()
                        .getPlankey(), getDia(), EjxRut.PREENTRENO);
                listView.setItems(l);
            }
        }
    }

    public void getPost() throws DAOException {
        if (getRutina() == null) {
            mensaje("Registre primero un plan", "aviso");
            buttonPost.setSelected(false);
        } else {
            String dia = getDia();
            if (dia.isEmpty()) {
                mensaje("Seleccione un día primero", "aviso");
                buttonPost.setSelected(false);
            } else {
                listView.setItems(getEjxruts().obtenerTodos(getRutina()
                        .getPlankey(), getDia(), EjxRut.POSTENTRENO));
            }
        }
    }
}
