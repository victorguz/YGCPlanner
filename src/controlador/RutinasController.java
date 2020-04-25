/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import DAO.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import modelo.plan.BasePlan;
import modelo.plan.Ejercicio;
import modelo.plan.EjxRut;
import modelo.plan.Plan;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author 201621279487
 */
public class RutinasController extends Controller<Plan> {
    public static ObservableList<Ejercicio> ejercicios = FXCollections.observableArrayList();
    @FXML
    private TextField textRutina;

    @FXML
    private TextField textBuscarEjercicio;

    @FXML
    private TextField textNombreEjercicio;

    @FXML
    private TextField textPluralEjercicio;

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
    private Label labelUnidad;

    @FXML
    private TextField textSeries;

    @FXML
    private ComboBox<String> comboUnidades;

    @FXML
    private HBox boxRepeticiones;

    @FXML
    private TextField textRepeticiones;

    @FXML
    private TextField textBuscarEjx;

    @FXML
    private TextField textPresentacion;

    @FXML
    private Label labelPresentacion;

    @FXML
    private ListView<EjxRut> listBloque1;

    @FXML
    private ListView<EjxRut> listBloque2;

    @FXML
    private ListView<EjxRut> listBloque3;

    @FXML
    private ListView<EjxRut> listBloque4;

    @FXML
    private ListView<EjxRut> listBloque5;


    private AutoCompletionBinding autoCompletionAlx;

    private AutoCompletionBinding autoCompletionEjercicios;
    private AutoCompletionBinding autoCompletionRutinas;

    public void initialize(URL location, ResourceBundle resources) {
        obtener();
        obtenerEjercicios();
        textRepeticiones.setText("1");
        textSeries.setText("1");
        comboUnidades.getItems().setAll(EjxRut.UNIDADES);
        comboUnidades.getSelectionModel().select(0);
        mostrarPresentaciones();
        setTooltips();
    }

    public Plan captar() throws SimpleException {
        Plan d = new Plan("rutina");
        if (textRutina.getText().isEmpty()) {
            throw new SimpleException("Seleccione un nombre de plan");
        }
        d.setNombre(textRutina.getText());
        return d;
    }

    public void obtener() {
        try {
            rutinas = getPlanes().allRutinas();
            if (autoCompletionRutinas != null) {
                autoCompletionRutinas.dispose();
            }

            autoCompletionRutinas = TextFields.bindAutoCompletion(textRutina, rutinas);
        } catch (DAOException ex) {
            excepcion(ex);
        }
        getMenu();
    }

    public Plan buscarPlan(String e) throws SimpleException {
        if (e.isEmpty()) {
            throw new SimpleException("Digite el nombre del plan a buscar");
        } else {
            for (Plan item : rutinas) {
                if (item.getNombre().equalsIgnoreCase(e)) {
                    return item;
                }
            }
        }
        throw new SimpleException("No se encontró un plan con este nombre: " + e);
    }

    public Plan buscarPlanSilencioso(String e) {
        if (!e.isEmpty()) {
            for (Plan item : rutinas) {
                if (item.getNombre().equalsIgnoreCase(e)) {
                    return item;
                }
            }
        }
        return null;
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
                    mensaje("Plan de entrenamiento registrado", "exito");
                }
            }
        } catch (DAOException | SimpleException ex) {
            excepcion(ex);
        }
    }


    private void eliminarEjxRuts() {
        if (!listBloque1.getItems().isEmpty()) {
            for (EjxRut a :
                    listBloque1.getItems()) {
                try {
                    getEjxruts().delete(a);
                } catch (DAOException e) {
                    excepcion(e);
                }
            }
        }
    }

    public void eliminar() {
        if (opcion(
                "¿Está seguro que desea eliminar el plan "
                        + textRutina.getText().toUpperCase() + " ? \n\nNo podrás recuperarlo más tarde", "error"
        )) {
            eliminarEjxRuts();
            try {
                getPlanes().delete(captar());
            } catch (DAOException | SimpleException e) {
                excepcion(e);
            }
            obtener();
            mensaje("Plan de entrenamiento eliminado", "exito");
        }
    }

    public void limpiar() {
        textRutina.setText("");
        textRutina.setText("");
        listBloque1.getItems().clear();
        textNombreEjercicio.setText("");
        textBuscarEjx.setText("");
        textPluralEjercicio.setText("");
        textRepeticiones.setText("1");
        textSeries.setText("1");

    }

    public EjxRut getEjxRut(String momento) throws DAOException, SimpleException {
        EjxRut a = new EjxRut();
        a.setPlan(buscarPlan(textRutina.getText()));
        a.setEjercicio(buscarEjercicio(textBuscarEjx.getText()));
        a.setRepeticiones(Integer.parseInt(textRepeticiones.getText()));
        a.setSeries(Integer.parseInt(textSeries.getText()));
        a.setDia(getDia());
        a.setMomento(momento);
        a.setUnidad(comboUnidades.getSelectionModel().getSelectedItem());
        a.setPresentacion(textPresentacion.getText());
        return a;
    }

    public void deleteBloque1(ActionEvent event) {
        if (listBloque1.getSelectionModel().getSelectedIndex() == -1) {
            mensaje("Seleccione un ejercicio en el menú", "aviso");
        } else {
            try {
                EjxRut a = listBloque1.getSelectionModel().getSelectedItem();
                getEjxruts().delete(a);
                listBloque1.getItems().remove(a);
            } catch (DAOException ex) {
                excepcion(ex);
            }
        }
    }

    public void deleteAm(ActionEvent event) {
        if (listBloque2.getSelectionModel().getSelectedIndex() == -1) {
            mensaje("Seleccione un ejercicio en el menú", "aviso");
        } else {
            try {
                EjxRut a = listBloque2.getSelectionModel().getSelectedItem();
                getEjxruts().delete(a);
                listBloque2.getItems().remove(a);
            } catch (DAOException ex) {
                excepcion(ex);
            }
        }
    }

    public void deleteBloque3(ActionEvent event) {
        if (listBloque3.getSelectionModel().getSelectedIndex() == -1) {
            mensaje("Seleccione un ejercicio en el menú", "aviso");
        } else {
            try {
                EjxRut a = listBloque3.getSelectionModel().getSelectedItem();
                getEjxruts().delete(a);
                listBloque3.getItems().remove(a);
            } catch (DAOException ex) {
                excepcion(ex);
            }
        }
    }

    public void deletePm(ActionEvent event) {
        if (listBloque4.getSelectionModel().getSelectedIndex() == -1) {
            mensaje("Seleccione un ejercicio en el menú", "aviso");
        } else {
            try {
                EjxRut a = listBloque4.getSelectionModel().getSelectedItem();
                getEjxruts().delete(a);
                listBloque4.getItems().remove(a);
            } catch (DAOException ex) {
                excepcion(ex);
            }
        }
    }

    public void deleteBloque5(ActionEvent event) {
        if (listBloque5.getSelectionModel().getSelectedIndex() == -1) {
            mensaje("Seleccione un ejercicio en el menú", "aviso");
        } else {
            try {
                EjxRut a = listBloque5.getSelectionModel().getSelectedItem();
                getEjxruts().delete(a);
                listBloque5.getItems().remove(a);
            } catch (DAOException ex) {
                excepcion(ex);
            }
        }
    }

    public void addBloque1() {
        try {
            EjxRut a = getEjxRut(EjxRut.BLOQUE1);
            if (a != null) {
                if (!a.isEmpty()) {
                    getEjxruts().insert(a);
                    actualizarUso(a.getEjercicio());
                    listBloque1.getItems().add(a);
                    listBloque1.getSelectionModel().select(listBloque1.getItems().size() - 1);
                }
            }
        } catch (DAOException | SimpleException ex) {
            excepcion(ex);
        }
    }

    public void addAm() {
        try {
            EjxRut a = getEjxRut(EjxRut.BLOQUE2);
            if (a != null) {
                if (!a.isEmpty()) {
                    getEjxruts().insert(a);
                    actualizarUso(a.getEjercicio());
                    listBloque2.getItems().add(a);
                    listBloque2.getSelectionModel().select(listBloque2.getItems().size() - 1);
                }
            }
        } catch (DAOException | SimpleException ex) {
            excepcion(ex);
        }
    }

    public void addBloque3() {

        try {
            EjxRut a = getEjxRut(EjxRut.BLOQUE3);
            if (a != null) {
                if (!a.isEmpty()) {
                    getEjxruts().insert(a);
                    actualizarUso(a.getEjercicio());
                    listBloque3.getItems().add(a);
                    listBloque3.getSelectionModel().select(listBloque3.getItems().size() - 1);
                }
            }
        } catch (DAOException | SimpleException ex) {
            excepcion(ex);
        }
    }

    public void addPm() {

        try {
            EjxRut a = getEjxRut(EjxRut.BLOQUE4);
            if (a != null) {
                if (!a.isEmpty()) {
                    getEjxruts().insert(a);
                    actualizarUso(a.getEjercicio());
                    listBloque4.getItems().add(a);
                    listBloque4.getSelectionModel().select(listBloque4.getItems().size() - 1);
                }
            }
        } catch (DAOException | SimpleException ex) {
            excepcion(ex);
        }
    }

    public void addBloque5() {

        try {
            EjxRut a = getEjxRut(EjxRut.BLOQUE5);
            if (a != null) {
                if (!a.isEmpty()) {
                    getEjxruts().insert(a);
                    actualizarUso(a.getEjercicio());
                    listBloque5.getItems().add(a);
                    listBloque5.getSelectionModel().select(listBloque5.getItems().size() - 1);
                }
            }
        } catch (DAOException | SimpleException ex) {
            excepcion(ex);
        }
    }

    public void actualizarUso(Ejercicio a) {
        try {
            getEjercicios().update(a);
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    public String getDia() throws SimpleException {
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
            throw new SimpleException("Debe seleccionar un día");
        }
    }


    public void getMenu() {
        if (!textRutina.getText().isEmpty()) {
            try {
                Plan rutina = buscarPlanSilencioso(textRutina.getText());
                if (rutina != null) {
                    listBloque1.setItems(getEjxruts().where(rutina.getPlankey(), getDia(), EjxRut.BLOQUE1));
                    listBloque2.setItems(getEjxruts().where(rutina.getPlankey(), getDia(), EjxRut.BLOQUE2));
                    listBloque3.setItems(getEjxruts().where(rutina.getPlankey(), getDia(), EjxRut.BLOQUE3));
                    listBloque4.setItems(getEjxruts().where(rutina.getPlankey(), getDia(), EjxRut.BLOQUE4));
                    listBloque5.setItems(getEjxruts().where(rutina.getPlankey(), getDia(), EjxRut.BLOQUE5));
                }
            } catch (DAOException | SimpleException e) {
                excepcion(e);
            }
        }
    }

    public void duplicarPlan() {
        try {
            Plan org = buscarPlan(textRutina.getText());
            Plan copy = new Plan("rutina");
            copy.setNombre(org.getNombre() + " - copia");
            getPlanes().insert(copy);
            getEjxruts().whereCopy(org.getNombre(), copy.getNombre());
            obtener();
            mensaje("El plan ha sido duplicado", "exito");
        } catch (DAOException | SimpleException e) {
            excepcion(e);
        }
        getMenu();
    }

    public void registrarEjercicio() {
        try {
            Ejercicio c = captarEjercicio();
            getEjercicios().insert(c);
            mensaje("Ejercicio registrado", "exito");
            obtenerEjercicios();
        } catch (DAOException | SimpleException ex) {
            excepcion(ex);
        }
    }

    public void modificarEjercicio() {
        if (opcion("Está a punto de modificar el ejercicio \"" + textBuscarEjercicio.getText().toUpperCase() + "\"."
                + " \n\n¿Está seguro de esto?", "error")) {
            try {
                Ejercicio a = buscarEjercicio(textBuscarEjercicio.getText());
                Ejercicio c = captarEjercicio();
                c.setEjerciciokey(a.getEjerciciokey());
                getEjercicios().update(c);
                obtenerEjercicios();
                mensaje("Ejercicio modificado", "exito");
                mostrarPresentaciones();
            } catch (DAOException | SimpleException ex) {
                excepcion(ex);
            }
        }
    }

    public void eliminarEjercicio() {
        if (opcion("Está a punto de eliminar el ejercicio \"" + textBuscarEjercicio.getText().toUpperCase() + "\"."
                + " \n\n¿Está seguro de esto?", "error")) {
            try {
                Ejercicio a = captarEjercicio();
                getEjercicios().delete(a);
                mensaje("Ejercicio eliminado", "exito");
                obtenerEjercicios();
            } catch (DAOException | SimpleException ex) {
                excepcion(ex);
            }
        }
    }

    public void mostrarEjercicio(Ejercicio getEjercicio) throws DAOException {
        if (!getEjercicio.isEmpty()) {
            textNombreEjercicio.setText(getEjercicio.toString());
            textPluralEjercicio.setText(Operacion.inicialMayuscula(getEjercicio.getPlural()));

            textBuscarEjercicio.setText(getEjercicio.toString());
            textBuscarEjx.setText(getEjercicio.toString());
            //mostrarPresentaciones();
        } else {
            limpiar();
        }
    }

    public Ejercicio captarEjercicio() throws SimpleException {
        Ejercicio c = new Ejercicio();
        if (textNombreEjercicio.getText().isEmpty()) {
            throw new SimpleException("Digite el nombre del ejercicio");
        }
        if (textPluralEjercicio.getText().isEmpty()) {
            throw new SimpleException("Digite el nombre plural para el ejercicio");
        }
        c.setNombre(textNombreEjercicio.getText());
        c.setPlural(textPluralEjercicio.getText());
        return c;
    }

    public void obtenerEjercicios() {
        try {
            ejercicios = getEjercicios().all();

            if (autoCompletionEjercicios != null) {
                autoCompletionEjercicios.dispose();
            }

            if (autoCompletionAlx != null) {
                autoCompletionAlx.dispose();
            }

            autoCompletionEjercicios = TextFields.bindAutoCompletion(textBuscarEjercicio, ejercicios);
            autoCompletionAlx = TextFields.bindAutoCompletion(textBuscarEjx, ejercicios);
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    public Ejercicio buscarEjercicio(String e) throws DAOException, SimpleException {
        if (e.isEmpty()) {
            throw new SimpleException("Digite el nombre del ejercicio a buscar");
        } else {
            for (Ejercicio item : ejercicios) {
                if (item.getNombre().equalsIgnoreCase(e)) {
                    mostrarEjercicio(item);
                    return item;
                }
            }
        }
        throw new SimpleException("No se encontró un ejercicio con este nombre: " + e);
    }

    public void buscarEjercicio() throws DAOException, SimpleException {
        if (!textBuscarEjercicio.getText().isEmpty()) {
            for (Ejercicio item : ejercicios) {
                if (item.getNombre().equalsIgnoreCase(textBuscarEjercicio.getText())) {
                    mostrarEjercicio(item);
                    break;
                }
            }
        }
    }

    public void mostrarPresentaciones() {
        cambiarLabelUnidad();
        EjxRut a = new EjxRut();
        Ejercicio k = new Ejercicio();
        k.setNombre(textBuscarEjx.getText());
        a.setEjercicio(k);
        a.setRepeticiones(Integer.parseInt(textRepeticiones.getText()));
        a.setSeries(Integer.parseInt(textSeries.getText()));
        a.setUnidad(comboUnidades.getSelectionModel().getSelectedItem());
        a.setPresentacion(textPresentacion.getText());
        labelPresentacion.setText(a.calcularPresentacion());
    }

    public void cambiarLabelUnidad() {
        if (comboUnidades.getSelectionModel().getSelectedItem().equalsIgnoreCase("gramos")) {
            boxRepeticiones.setDisable(true);
            textRepeticiones.setText(textSeries.getText());
        } else if (comboUnidades.getSelectionModel().getSelectedItem().equalsIgnoreCase("libras")) {
            boxRepeticiones.setDisable(true);
            textRepeticiones.setText((Double.parseDouble(textSeries.getText()) / 453.592) + "");
        } else if (comboUnidades.getSelectionModel().getSelectedItem().equalsIgnoreCase("onzas")) {
            boxRepeticiones.setDisable(true);
            textRepeticiones.setText((Double.parseDouble(textSeries.getText()) / 28.3495) + "");
        } else {
            boxRepeticiones.setDisable(false);
            textRepeticiones.setText("0");
        }
        labelUnidad.setText(comboUnidades.getSelectionModel().getSelectedItem());
    }

    public void increaseGrams() {
        if (textSeries.getText().isEmpty()) {
            textSeries.setText("1");
        } else {
            if (textSeries.getText().contains(".")) {
                textSeries.setText((Double.parseDouble(textSeries.getText()) + 0.1) + "");
            } else {
                textSeries.setText((Integer.parseInt(textSeries.getText()) + 1) + "");
            }
        }
        mostrarPresentaciones();
    }

    public void increaseCant() {
        if (textRepeticiones.getText().isEmpty()) {
            textRepeticiones.setText("1");
        } else {
            if (textRepeticiones.getText().contains(".")) {
                textRepeticiones.setText((Integer.parseInt(textRepeticiones.getText()) + 0.1) + "");
            } else {
                textRepeticiones.setText((Integer.parseInt(textRepeticiones.getText()) + 1) + "");
            }
        }
        mostrarPresentaciones();
    }

    public void decreaseGrams() {
        if (textSeries.getText().isEmpty()) {
            textSeries.setText("1");
        } else {
            if (Double.parseDouble(textSeries.getText()) > 1.999999999999) {
                if (textSeries.getText().contains(".")) {
                    textSeries.setText((Double.parseDouble(textSeries.getText()) - 0.1) + "");
                } else {
                    textSeries.setText((Integer.parseInt(textSeries.getText()) - 1) + "");
                }
            }
        }
        mostrarPresentaciones();
    }

    public void decreaseCant() {
        if (textRepeticiones.getText().isEmpty()) {
            textRepeticiones.setText("1");
        } else {
            if (Integer.parseInt(textRepeticiones.getText()) > 1.999999999999) {
                if (textRepeticiones.getText().contains(".")) {
                    textRepeticiones.setText((Integer.parseInt(textRepeticiones.getText()) - 0.1) + "");
                } else {
                    textRepeticiones.setText((Integer.parseInt(textRepeticiones.getText()) - 1) + "");
                }
            }
        }
        mostrarPresentaciones();
    }

    public void forTextCantAndGrams(KeyEvent e) {
        increaseOrDecrease(e);
        mostrarPresentaciones();
    }

    public void setTooltips() {
        Tooltip tool = new Tooltip();
        tool.setGraphicTextGap(0);
        tool.setText("En la presentación de los datos puedes usar las palabras" +
                "\nclave \"#cantidad\", \"#series\", \"#unidad\" y \"#ejercicio\" para hacer " +
                "\nuso de los datos del ejercicio de referencia que deseas añadir");
        tool.setWrapText(true);
        textPresentacion.setTooltip(tool);

    }

}
