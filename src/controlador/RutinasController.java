/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import DAO.DAOException;
import static controlador.Controller.isEjerciciosUpdated;
import static controlador.Controller.mensaje;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import modelo.plan.Ejercicio;
import modelo.plan.EjxRut;
import modelo.plan.Plan;

/**
 *
 * @author 201621279487
 */
public class RutinasController extends Controller<Plan> {

    @FXML
    private ToggleGroup filtro;

    @FXML
    private ComboBox<String> comboObjetivos;

    @FXML
    private ComboBox<Plan> comboRutina;

    @FXML
    private ComboBox<Integer> comboSeries;

    @FXML
    private TextField textNombre;

    @FXML
    private TextArea textDescripcion;

    @FXML
    private TextField textRepeticiones;

    @FXML
    private ListView<EjxRut> listDomingo;

    @FXML
    private ListView<EjxRut> listLunes;

    @FXML
    private ListView<EjxRut> listMartes;

    @FXML
    private ListView<EjxRut> listMiercoles;

    @FXML
    private ListView<EjxRut> listJueves;

    @FXML
    private ListView<EjxRut> listViernes;

    @FXML
    private ListView<EjxRut> listSabado;

    @FXML
    private ComboBox<String> comboSexo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboObjetivos.setItems(objetivos);
        comboObjetivos.getSelectionModel().select(0);
        obtener();
        comboSexo.setItems(sexos);
        comboSexo.getSelectionModel().select(0);
        comboSeries.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        comboSeries.getSelectionModel().select(0);
        comboObjetivos.setItems(objetivos);
        comboObjetivos.getSelectionModel().select(0);
        comboEjercicios.setItems(ejercicios);
        comboEjercicios.getSelectionModel().select(0);
        updated();
    }

    @Override
    public Plan captar() throws DAOException {
        Plan d = new Plan();
        d.setTipo("DIETA");
        d.setNombre(textNombre.getText());
        d.setObjetivo(comboObjetivos.getSelectionModel().getSelectedItem());
        d.setDescripcion(textDescripcion.getText());
        if (textEdad.getText().isEmpty()) {
            d.setEdad(0);
        } else {
            d.setEdad(Integer.parseInt(textEdad.getText()));
        }
        d.setSexo(comboSexo.getSelectionModel().getSelectedItem());
        return d;
    }

    @Override
    public void obtener() {
        try {
            comboRutina.getItems().clear();
            if (textBuscar.getText().isEmpty()) {
                rutinas = getRutinas().obtenerTodos();
            } else {
                rutinas = getRutinas().obtenerTodos(textBuscar.getText());
            }
            if (!rutinas.isEmpty()) {
                comboRutina.setItems(rutinas);
                select(0);
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
                    mensaje("Aún faltan algunos datos en el plan de entrenamiento", "aviso");
                } else {
                    getRutinas().insertar(m);
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
            if (!comboRutina.getItems().isEmpty()) {
                Plan m = captar();
                if (m != null) {
                    m.setPlankey(comboRutina.getSelectionModel().getSelectedItem().getPlankey());
                    if (m.isEmpty()) {
                        mensaje("Aún faltan algunos datos en el plan de entrenamiento", "aviso");
                    } else {
                        getRutinas().modificar(m);
                        textBuscar.setText(textNombre.getText());
                        obtener();
                        mensaje("Plan de entrenamiento modificado", "exito");
                    }
                }
            } else {
                mensaje("No hay planes de entrenamiento que modificar, registre uno", "aviso");
            }
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    @Override
    public void eliminar() {
        try {
            if (!comboRutina.getItems().isEmpty()) {
                getRutinas().eliminar(comboRutina.getSelectionModel().getSelectedItem());
                obtener();
                mensaje("Plan de entrenamiento eliminado", "exito");
            } else {
                mensaje("No hay planes de entrenamiento que eliminar, registre uno", "aviso");
            }
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    @Override
    public void limpiar() {
    }

    @Override
    public void mostrar() {
        if (!comboRutina.getItems().isEmpty()) {
            Plan d = comboRutina.getSelectionModel().getSelectedItem();
            textNombre.setText(d.getNombre());
            selectCombo(comboObjetivos, d.getObjetivo());
            textDescripcion.setText(d.getDescripcion());
            textEdad.setText(d.getEdad() + "");
            selectCombo(comboSexo, comboSexo.getSelectionModel().getSelectedItem());
        }
    }

    public void select(int i) {
        if (!comboRutina.getItems().isEmpty()) {
            comboRutina.getSelectionModel().select(i);
            mostrar();
        }
    }

    @Override
    public void updated() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Runnable updater = new Runnable() {
                    @Override
                    public void run() {
                        if (isEjerciciosUpdated()) {
                            comboEjercicios.setItems(ejercicios);
                            comboEjercicios.getSelectionModel().select(0);
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

    public Plan getRutina() {
        return comboRutina.getSelectionModel().getSelectedItem();
    }

    public EjxRut getEjxRut() throws DAOException {
        if (!comboRutina.getItems().isEmpty()) {
            EjxRut a = new EjxRut();
            a.setPlan(getRutina());
            a.setEjercicio(comboEjercicios.getSelectionModel().getSelectedItem());
            if (textRepeticiones.getText().isEmpty()) {
                a.setRepeticiones(0);
            } else {
                a.setRepeticiones(Integer.parseInt(textRepeticiones.getText()));
            }
            a.setSeries(comboSeries.getSelectionModel().getSelectedItem());
            return a;
        }
        return null;
    }

    public void getDomingo() {
        try {
            EjxRut a = getEjxRut();
            a.setDia(EjxRut.DOMINGO);
            a.setMomento(EjxRut.ENTRENO);
            a.setEjercicio(comboEjercicios.getSelectionModel().getSelectedItem());
            getEjxruts().insertar(a);
            actualizarUso(a.getEjercicio());
            listDomingo.getItems().add(a);
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    public void deleteDomingo() {
        if (listDomingo.getSelectionModel().getSelectedIndex() != -1) {
            try {
                getEjxruts().eliminar(listDomingo.getSelectionModel().getSelectedItem());
                listDomingo.getItems().remove(listDomingo.getSelectionModel().getSelectedItem());
            } catch (DAOException ex) {
                excepcion(ex);
            }
        }
    }

    public void getLunes() {
        try {
            EjxRut a = getEjxRut();
            a.setDia(EjxRut.DOMINGO);
            a.setMomento(EjxRut.ENTRENO);
            a.setEjercicio(comboEjercicios.getSelectionModel().getSelectedItem());
            getEjxruts().insertar(a);
            actualizarUso(a.getEjercicio());
            listDomingo.getItems().add(a);
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    public void deleteLunes() {
        if (listDomingo.getSelectionModel().getSelectedIndex() != -1) {
            try {
                getEjxruts().eliminar(listDomingo.getSelectionModel().getSelectedItem());
                listDomingo.getItems().remove(listDomingo.getSelectionModel().getSelectedItem());
            } catch (DAOException ex) {
                excepcion(ex);
            }
        }
    }

    public void getMartes() {
        try {
            EjxRut a = getEjxRut();
            a.setDia(EjxRut.DOMINGO);
            a.setMomento(EjxRut.ENTRENO);
            a.setEjercicio(comboEjercicios.getSelectionModel().getSelectedItem());
            getEjxruts().insertar(a);
            actualizarUso(a.getEjercicio());
            listDomingo.getItems().add(a);
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    public void deleteMartes() {
        if (listDomingo.getSelectionModel().getSelectedIndex() != -1) {
            try {
                getEjxruts().eliminar(listDomingo.getSelectionModel().getSelectedItem());
                listDomingo.getItems().remove(listDomingo.getSelectionModel().getSelectedItem());
            } catch (DAOException ex) {
                excepcion(ex);
            }
        }
    }

    public void getMiercoles() {
        try {
            EjxRut a = getEjxRut();
            a.setDia(EjxRut.DOMINGO);
            a.setMomento(EjxRut.ENTRENO);
            a.setEjercicio(comboEjercicios.getSelectionModel().getSelectedItem());
            getEjxruts().insertar(a);
            actualizarUso(a.getEjercicio());
            listDomingo.getItems().add(a);
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    public void deleteMiercoles() {
        if (listDomingo.getSelectionModel().getSelectedIndex() != -1) {
            try {
                getEjxruts().eliminar(listDomingo.getSelectionModel().getSelectedItem());
                listDomingo.getItems().remove(listDomingo.getSelectionModel().getSelectedItem());
            } catch (DAOException ex) {
                excepcion(ex);
            }
        }
    }

    public void getJueves() {
        try {
            EjxRut a = getEjxRut();
            a.setDia(EjxRut.DOMINGO);
            a.setMomento(EjxRut.ENTRENO);
            a.setEjercicio(comboEjercicios.getSelectionModel().getSelectedItem());
            getEjxruts().insertar(a);
            actualizarUso(a.getEjercicio());
            listDomingo.getItems().add(a);
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    public void deleteJueves() {
        if (listDomingo.getSelectionModel().getSelectedIndex() != -1) {
            try {
                getEjxruts().eliminar(listDomingo.getSelectionModel().getSelectedItem());
                listDomingo.getItems().remove(listDomingo.getSelectionModel().getSelectedItem());
            } catch (DAOException ex) {
                excepcion(ex);
            }
        }
    }

    public void getViernes() {
        try {
            EjxRut a = getEjxRut();
            a.setDia(EjxRut.DOMINGO);
            a.setMomento(EjxRut.ENTRENO);
            a.setEjercicio(comboEjercicios.getSelectionModel().getSelectedItem());
            getEjxruts().insertar(a);
            actualizarUso(a.getEjercicio());
            listDomingo.getItems().add(a);
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    public void deleteViernes() {
        if (listDomingo.getSelectionModel().getSelectedIndex() != -1) {
            try {
                getEjxruts().eliminar(listDomingo.getSelectionModel().getSelectedItem());
                listDomingo.getItems().remove(listDomingo.getSelectionModel().getSelectedItem());
            } catch (DAOException ex) {
                excepcion(ex);
            }
        }
    }

    public void getSabado() {
        try {
            EjxRut a = getEjxRut();
            a.setDia(EjxRut.DOMINGO);
            a.setMomento(EjxRut.ENTRENO);
            a.setEjercicio(comboEjercicios.getSelectionModel().getSelectedItem());
            getEjxruts().insertar(a);
            actualizarUso(a.getEjercicio());
            listDomingo.getItems().add(a);
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    public void deleteSabado() {
        if (listDomingo.getSelectionModel().getSelectedIndex() != -1) {
            try {
                getEjxruts().eliminar(listDomingo.getSelectionModel().getSelectedItem());
                listDomingo.getItems().remove(listDomingo.getSelectionModel().getSelectedItem());
            } catch (DAOException ex) {
                excepcion(ex);
            }
        }
    }

    public void setFiltro() {
        int i = filtro.getSelectedToggle().toString().indexOf("'");
        setFiltro(filtro.getSelectedToggle().toString().substring(i));
        System.out.println(getFiltro());
    }

    public void actualizarUso(Ejercicio a) {
        a.usar();
        try {
            getEjercicios().modificar(a);
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }
}
