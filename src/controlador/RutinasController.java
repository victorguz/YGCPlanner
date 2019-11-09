/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import DAO.DAOException;
import static controlador.Controller.mensaje;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import modelo.plan.Ejercicio;
import modelo.plan.EjxRut;
import modelo.plan.Plan;

/**
 *
 * @author 201621279487
 */
public class RutinasController extends Controller<Plan> {

    @FXML
    private TextField textBuscarRutina;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
comboObjetivo.setItems(objetivos); 
        obtener();
        comboSexo.setItems(sexos);
        comboSexo.getSelectionModel().select(0);
        comboSeries.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        comboSeries.getSelectionModel().select(0);
        updated();
    }

    @Override
    public Plan captar() throws DAOException {
        Plan d = new Plan();
        d.setTipo("DIETA");
        d.setNombre(textNombre.getText());
        d.setObjetivo(comboObjetivo.getSelectionModel().getSelectedItem());
        d.setDescripcion(textDescripcion.getText());
        d.setEdad(Integer.parseInt(textEdad.getText()));
        d.setSexo(comboSexo.getSelectionModel().getSelectedItem());
        return d;
    }

    @Override
    public void obtener() {
        try {
            rutinas = getRutinas().obtenerTodos();
            comboRutina.getItems().clear();
            if (!rutinas.isEmpty()) {
                comboRutina.setItems(rutinas);
                select(0);
            }
        } catch (DAOException ex) {
            mensaje("Condición", "error", ex);
        }
    }

    @Override
    public void registrar() {
        try {
            Plan m = captar();
            if (m != null) {
                if (m.isEmpty()) {
                    mensaje("Aún faltan algunos datos en el plan de entrenamiento", "aviso", null);
                } else {
                    getRutinas().insertar(m);
                    obtener();
                    mensaje("Plan de entrenamiento registrado", "exito", null);
                }
            }
        } catch (DAOException ex) {
            mensaje("Condición", "error", ex);
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
                        mensaje("Aún faltan algunos datos en el plan de entrenamiento", "aviso", null);
                    } else {
                        getRutinas().modificar(m);
                        mensaje("Plan de entrenamiento actualizado", "exito", null);
                        obtener();
                    }
                }
            } else {
                mensaje("No hay planes de entrenamiento que modificar, registre uno", "aviso", null);
            }
        } catch (DAOException ex) {
            mensaje("Condición", "error", ex);
        }
    }

    @Override
    public void eliminar() {
        try {
            if (!comboRutina.getItems().isEmpty()) {
                getRutinas().eliminar(comboRutina.getSelectionModel().getSelectedItem());
                obtener();
                mensaje("Plan de entrenamiento eliminado", "exito", null);
            } else {
                mensaje("No hay planes de entrenamiento que eliminar, registre uno", "aviso", null);
            }
        } catch (DAOException ex) {
            mensaje("Condición", "error", ex);
        }
    }

    @Override
    public void buscar() {
        if (textBuscarRutina.getText().isEmpty()) {
            obtener();
        } else {
            try {
                rutinas = getRutinas().obtenerTodos(textBuscarRutina.getText());
                comboRutina.getItems().clear();
                if (rutinas.isEmpty()) {
                    mensaje("No se encontraron planes de alimentación", "aviso", null);
                } else {
                    comboRutina.setItems(rutinas);
                    select(0);
                    mensaje("Se encontraron " + rutinas.size() + " planes de entrenamiento", "exito", null);
                }
            } catch (DAOException ex) {
                mensaje("Condición", "error", ex);
            }
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
            selectObjetivo(d.getObjetivo());
            textDescripcion.setText(d.getDescripcion());
            textEdad.setText(d.getEdad() + "");
            selectSexo(comboSexo.getSelectionModel().getSelectedItem());
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
        if (isEjerciciosUpdated()) {
            obtenerEjercicios();
        }
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
            mensaje("Condición", "error", ex);
        }
    }

    public void deleteDomingo() {
        if (listDomingo.getSelectionModel().getSelectedIndex() != -1) {
            try {
                getEjxruts().eliminar(listDomingo.getSelectionModel().getSelectedItem());
                listDomingo.getItems().remove(listDomingo.getSelectionModel().getSelectedItem());
            } catch (DAOException ex) {
                mensaje("Condición", "error", ex);
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
            mensaje("Condición", "error", ex);
        }
    }

    public void deleteLunes() {
        if (listDomingo.getSelectionModel().getSelectedIndex() != -1) {
            try {
                getEjxruts().eliminar(listDomingo.getSelectionModel().getSelectedItem());
                listDomingo.getItems().remove(listDomingo.getSelectionModel().getSelectedItem());
            } catch (DAOException ex) {
                mensaje("Condición", "error", ex);
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
            mensaje("Condición", "error", ex);
        }
    }

    public void deleteMartes() {
        if (listDomingo.getSelectionModel().getSelectedIndex() != -1) {
            try {
                getEjxruts().eliminar(listDomingo.getSelectionModel().getSelectedItem());
                listDomingo.getItems().remove(listDomingo.getSelectionModel().getSelectedItem());
            } catch (DAOException ex) {
                mensaje("Condición", "error", ex);
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
            mensaje("Condición", "error", ex);
        }
    }

    public void deleteMiercoles() {
        if (listDomingo.getSelectionModel().getSelectedIndex() != -1) {
            try {
                getEjxruts().eliminar(listDomingo.getSelectionModel().getSelectedItem());
                listDomingo.getItems().remove(listDomingo.getSelectionModel().getSelectedItem());
            } catch (DAOException ex) {
                mensaje("Condición", "error", ex);
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
            mensaje("Condición", "error", ex);
        }
    }

    public void deleteJueves() {
        if (listDomingo.getSelectionModel().getSelectedIndex() != -1) {
            try {
                getEjxruts().eliminar(listDomingo.getSelectionModel().getSelectedItem());
                listDomingo.getItems().remove(listDomingo.getSelectionModel().getSelectedItem());
            } catch (DAOException ex) {
                mensaje("Condición", "error", ex);
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
            mensaje("Condición", "error", ex);
        }
    }

    public void deleteViernes() {
        if (listDomingo.getSelectionModel().getSelectedIndex() != -1) {
            try {
                getEjxruts().eliminar(listDomingo.getSelectionModel().getSelectedItem());
                listDomingo.getItems().remove(listDomingo.getSelectionModel().getSelectedItem());
            } catch (DAOException ex) {
                mensaje("Condición", "error", ex);
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
            mensaje("Condición", "error", ex);
        }
    }

    public void deleteSabado() {
        if (listDomingo.getSelectionModel().getSelectedIndex() != -1) {
            try {
                getEjxruts().eliminar(listDomingo.getSelectionModel().getSelectedItem());
                listDomingo.getItems().remove(listDomingo.getSelectionModel().getSelectedItem());
            } catch (DAOException ex) {
                mensaje("Condición", "error", ex);
            }
        }
    }

    public void actualizarUso(Ejercicio a) {
        a.usar();
        try {
            getEjercicios().modificar(a);
        } catch (DAOException ex) {
            mensaje("Condición", "error", ex);
        }
    }
}
