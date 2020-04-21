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

    @FXML
    private TextField textBuscar;

    @FXML
    private ComboBox<Plan> comboRutinas;

    @FXML
    private TextField textNombre;

    @FXML
    private TextField textBuscarEjercicio;

    @FXML
    private TextField textNombreEjercicio;

    @FXML
    private TextField textPluralEjercicio;

    @FXML
    private ToggleButton buttonLunes;

    @FXML
    private ToggleGroup dias;

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
    private ToggleButton buttonB1;

    @FXML
    private ToggleGroup momentos;

    @FXML
    private ToggleButton buttonB2;

    @FXML
    private ToggleButton buttonB3;

    @FXML
    private ToggleButton buttonB4;

    @FXML
    private ToggleButton buttonB5;

    @FXML
    private Label labelPresentacion;

    @FXML
    private TextField textBuscarEjx;

    @FXML
    private Label labelUnidad;

    @FXML
    private Spinner<Integer> spinnerRepeticiones;

    @FXML
    private Spinner<Integer> spinnerSeries;

    @FXML
    private ComboBox<String> comboUnidades;

    @FXML
    private TextField textNombreEjx;

    @FXML
    private TextField textPluralEjx;

    @FXML
    private ListView<EjxRut> listView;

    @FXML
    private TextField textPresentacion;

    private AutoCompletionBinding autoCompletionEjx;
    private AutoCompletionBinding autoCompletionEjercicios;
    private Ejercicio ejercicio;
    private AutoCompletionBinding autoCompletionPresentaciones;
    private ObservableList<Referencia> presentaciones;

    public void initialize(URL location, ResourceBundle resources) {

        obtener();
        obtenerEjercicios();
        getBloque();
        spinnerRepeticiones.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10000, 0));
        spinnerSeries.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 0));
        comboUnidades.getItems().setAll(EjxRut.UNIDADES);
        comboUnidades.getSelectionModel().select(0);
        cambiarLabelUnidad();
    }

    public Plan captar() throws DAOException {
        Plan d = new Plan();
        d.setTipo("rutina");
        d.setNombre(textNombre.getText());
        return d;
    }

    public void obtener() {
        try {
            comboRutinas.getItems().clear();
            if (textBuscar.getText().isEmpty()) {
                rutinas = getPlanes().allRutinas();
            } else {
                rutinas = getPlanes().whereRutinas(textBuscar.getText());
            }
            if (!rutinas.isEmpty()) {
                comboRutinas.setItems(rutinas);
                select(0);
            }
        } catch (DAOException ex) {
            excepcion(ex);
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
                    mensaje("Plan de entrenamiento registrado", "exito");
                }
            }
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    public void modificar() {
        try {
            if (!comboRutinas.getItems().isEmpty()) {

                if (comboRutinas.getSelectionModel().getSelectedItem().getPlankey() == 2) {
                    mensaje("El plan de ejemplo no se puede modificar", "aviso");
                } else {
                    Plan m = captar();
                    if (m != null) {
                        m.setPlankey(comboRutinas.getSelectionModel().getSelectedItem().getPlankey());
                        if (m.isEmpty()) {
                            mensaje("Los campos señalados con asterisco son obligatorios.", "aviso");
                        } else {
                            getPlanes().update(m);
                            textBuscar.setText(textNombre.getText());
                            obtener();
                            mensaje("Plan de entrenamiento modificado", "exito");
                        }
                    }
                }
            } else {
                mensaje("No hay planes de entrenamiento que modificar", "aviso");
            }
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    private void eliminarEjxRuts() {
        if (!listView.getItems().isEmpty()) {
            for (EjxRut a :
                    listView.getItems()) {
                try {
                    getEjxruts().delete(a);
                } catch (DAOException e) {
                    excepcion(e);
                }
            }
        }
    }

    public void eliminar() {
        try {
            if (!comboRutinas.getItems().isEmpty()) {
                if (comboRutinas.getSelectionModel().getSelectedItem().getPlankey() == 1) {
                    mensaje("El plan de ejemplo no se puede eliminar", "aviso");
                } else {
                    eliminarEjxRuts();
                    getPlanes().delete(comboRutinas.getSelectionModel().getSelectedItem());
                    obtener();
                    mensaje("Plan de entrenamiento eliminado", "exito");
                }
            } else {
                mensaje("No hay planes de entrenamiento que eliminar", "aviso");
            }
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    public void limpiar() {
        textBuscar.setText("");
        textNombre.setText("");
        listView.getItems().clear();
    }

    public void mostrar() {
        if (!comboRutinas.getItems().isEmpty()) {
            Plan d = comboRutinas.getSelectionModel().getSelectedItem();
            textNombre.setText(d.getNombre());
            getBloque();
        }
    }

    public void select(int i) {
        if (!comboRutinas.getItems().isEmpty()) {
            comboRutinas.getSelectionModel().select(i);
            mostrar();
        }
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
                getEjxruts().delete(a);
                listView.getItems().remove(a);
            } catch (DAOException ex) {
                excepcion(ex);
            }
        }
    }

    public void addEjercicio() {
        if (comboRutinas.getItems().isEmpty()) {
            mensaje("Primero guarde un plan de alimentacion", "aviso");
        } else {
            if (comboRutinas.getSelectionModel().getSelectedItem().getPlankey() == 2) {
                mensaje("El plan de ejemplo no se puede modificar", "aviso");
            } else {
                try {
                    EjxRut a = getEjxRut();
                    if (a != null) {
                        if (!a.isEmpty()) {
                            getEjxruts().insert(a);
                            actualizarUso(a.getEjercicio());
                            listView.getItems().add(a);
                        }
                    }
                } catch (DAOException ex) {
                    excepcion(ex);
                }
            }
        }
    }

    public void actualizarUso(Ejercicio a) {
        try {
            getEjercicios().update(a);
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
        if (buttonB1.isSelected()) {
            return EjxRut.BLOQUE1;
        } else if (buttonB2.isSelected()) {
            return EjxRut.BLOQUE2;
        } else if (buttonB3.isSelected()) {
            return EjxRut.BLOQUE3;
        } else if (buttonB4.isSelected()) {
            return EjxRut.BLOQUE4;
        } else if (buttonB5.isSelected()) {
            return EjxRut.BLOQUE5;
        } else {
            throw new DAOException("Debe seleccionar un bloque");
        }
    }

    public EjxRut getEjxRut() throws DAOException {
        EjxRut a = new EjxRut();
        a.setPlan(getRutina());
        a.setEjercicio(getEjercicio());
        a.setSeries(spinnerSeries.getValue());
        a.setRepeticiones(spinnerRepeticiones.getValue());
        a.setDia(getDia());
        a.setMomento(getMomento());
        a.setUnidad(comboUnidades.getSelectionModel().getSelectedItem());
        if(textPresentacion.getText().isEmpty()){
            System.out.println("sin presentacion");
            throw new DAOException("Digite primero una presentación");
        }
        a.setPresentacion(textPresentacion.getText());
        return a;
    }

    public void duplicarPlan() {
        if (!comboRutinas.getItems().isEmpty()) {
            try {
                Plan copy = getRutina();
                copy.setNombre(getRutina().getNombre() + " - copia");
                getPlanes().insert(copy);
                ObservableList<EjxRut> LUNES = getEjxruts().where(getRutina().getPlankey(), BasePlan.LUNES);
                ObservableList<EjxRut> MARTES = getEjxruts().where(getRutina().getPlankey(), BasePlan.MARTES);
                ObservableList<EjxRut> MIERCOLES = getEjxruts().where(getRutina().getPlankey(), BasePlan.MIERCOLES);
                ObservableList<EjxRut> JUEVES = getEjxruts().where(getRutina().getPlankey(), BasePlan.JUEVES);
                ObservableList<EjxRut> VIERNES = getEjxruts().where(getRutina().getPlankey(), BasePlan.VIERNES);
                ObservableList<EjxRut> SABADO = getEjxruts().where(getRutina().getPlankey(), BasePlan.SABADO);
                ObservableList<EjxRut> DOMINGO = getEjxruts().where(getRutina().getPlankey(), BasePlan.DOMINGO);
                obtener();
                for (int i = 0; i < comboRutinas.getItems().size(); i++) {
                    if (comboRutinas.getItems().get(i).getNombre().equalsIgnoreCase(copy.getNombre())) {
                        comboRutinas.getSelectionModel().select(i);
                        break;
                    }
                }
                for (EjxRut item :
                        LUNES) {
                    item.setPlan(getRutina());
                    getEjxruts().insert(item);
                }
                for (EjxRut item :
                        MARTES) {
                    item.setPlan(getRutina());
                    getEjxruts().insert(item);
                }
                for (EjxRut item :
                        MIERCOLES) {
                    item.setPlan(getRutina());
                    getEjxruts().insert(item);
                }
                for (EjxRut item :
                        JUEVES) {
                    item.setPlan(getRutina());
                    getEjxruts().insert(item);
                }
                for (EjxRut item :
                        VIERNES) {
                    item.setPlan(getRutina());
                    getEjxruts().insert(item);
                }
                for (EjxRut item :
                        SABADO) {
                    item.setPlan(getRutina());
                    getEjxruts().insert(item);
                }
                for (EjxRut item :
                        DOMINGO) {
                    item.setPlan(getRutina());
                    getEjxruts().insert(item);
                }
                mensaje("El plan ha sido duplicado", "exito");
            } catch (DAOException e) {
                excepcion(e);
            }

        }
    }

    public void registrarEjercicio() {
        try {
            Ejercicio c = captarEjercicio();
            if (c != null) {
                if (c.isEmpty()) {
                    mensaje("Los campos señalados con asterisco son obligatorios.", "aviso");
                } else {
                    getEjercicios().insert(c);
                    mensaje("Ejercicio registrado", "exito");
                    obtenerEjercicios();
                }
            }
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    public void modificarEjercicio(ActionEvent e) {
        try {
            Control t = (Control) e.getSource();
            String source = e.getSource().toString();
            Ejercicio a = getEjercicio();
            Ejercicio c = null;
            if (source.contains("textNombreEjx") || source.contains("textPluralEjx")) {
                c = captarEjercicioEjx(textBuscarEjx.getText());
            } else if (source.contains("button")) {
                c = captarEjercicio();
            }
            c.setEjerciciokey(a.getEjerciciokey());
            getEjercicios().update(c);
            obtenerEjercicios();
            mensaje("Ejercicio modificado", "exito");
            //mostrarPresentaciones();
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    public void eliminarEjercicio() {
        try {
            Ejercicio a = captarEjercicio();
            getEjercicios().delete(a);
            mensaje("Ejercicio eliminado", "exito");
            obtenerEjercicios();
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    public void limpiarEjercicio() {
        textNombreEjercicio.setText("");
    }

    public void mostrarEjercicio() throws DAOException {
        if (!getEjercicio().isEmpty()) {
            textNombreEjercicio.setText(getEjercicio().toString());
            textPluralEjercicio.setText(Operacion.inicialMayuscula(getEjercicio().getPlural()));

            textBuscarEjercicio.setText(getEjercicio().toString());
            textBuscarEjx.setText(getEjercicio().toString());
            textNombreEjx.setText(getEjercicio().toString());
            textPluralEjx.setText(Operacion.inicialMayuscula(getEjercicio().getPlural()));
            mostrarPresentaciones();
        } else {
            limpiar();
        }
    }

    public Ejercicio captarEjercicioEjx(String buscar) throws DAOException {
        Ejercicio c = getEjercicio();
        if (textNombreEjx.getText().isEmpty()) {
            throw new DAOException("Digite el nombre del ejercicio");
        }
        if (textPluralEjx.getText().isEmpty()) {
            throw new DAOException("Digite el nombre plural para el ejercicio");
        }
        c.setNombre(textNombreEjx.getText());
        c.setPlural(textPluralEjx.getText());
        return c;
    }

    public Ejercicio captarEjercicio() throws DAOException {
        Ejercicio c = getEjercicio();
        if (textNombreEjercicio.getText().isEmpty()) {
            throw new DAOException("Digite el nombre del ejercicio");
        }
        if (textPluralEjx.getText().isEmpty()) {
            throw new DAOException("Digite el nombre plural para el ejercicio");
        }
        c.setNombre(textNombreEjx.getText());
        c.setPlural(textPluralEjx.getText());
        return c;
    }

    public void getBloque() {
        listView.getItems().clear();
        if (!getRutina().isEmpty()) {
            try {
                listView.setItems(getEjxruts().where(getRutina()
                        .getPlankey(), getDia(), getMomento()));
            } catch (DAOException e) {
                excepcion(e);
            }
        }
    }

    public Ejercicio getEjercicio() throws DAOException {
        if (ejercicio == null) {
            throw new DAOException("No se ha seleccionado ningun ejercicio");
        }
        return ejercicio;
    }

    public void setEjercicio(Ejercicio ejercicio) throws DAOException {
        this.ejercicio = ejercicio;
        if (ejercicio != null) {
            mostrarEjercicio();
        }
    }

    public void obtenerEjercicios() {
        try {
            ejercicios = getEjercicios().all();

            if (autoCompletionEjercicios != null) {
                autoCompletionEjercicios.dispose();
            }

            if (autoCompletionEjx != null) {
                autoCompletionEjx.dispose();
            }

            autoCompletionEjercicios = TextFields.bindAutoCompletion(textBuscarEjercicio, ejercicios);
            autoCompletionEjx = TextFields.bindAutoCompletion(textBuscarEjx, ejercicios);
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    public void mostrarPresentaciones() {
        try {
            EjxRut ejx = getEjxRut();
            labelPresentacion.setText(ejx.calcularPresentacion());
        } catch (DAOException e) {
            excepcion(e);
        }
    }

    public void buscarEjercicio(ActionEvent e) {
        TextField t = (TextField) e.getSource();
        if (t.getText().isEmpty()) {
            mensaje("Digite el nombre del ejercicio a buscar", "aviso");
        } else {
            for (Ejercicio item : ejercicios) {
                if (item.getNombre().equals(t.getText().toLowerCase())) {
                    try {
                        setEjercicio(item);
                    } catch (DAOException ex) {
                        excepcion(ex);
                    }
                    break;
                }
            }
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
                ref.setDato("ejercicio");
                obtenerPresentaciones();
                mensaje("Presentación guardada", "exito");
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
                        mensaje("Presentación eliminada", "exito");
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
            presentaciones = getReferencias().obtenerPresentaciones("ejercicio");

            if (autoCompletionPresentaciones != null) {
                autoCompletionPresentaciones.dispose();
            }

            autoCompletionPresentaciones = TextFields.bindAutoCompletion(textPresentacion, presentaciones);
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

}
