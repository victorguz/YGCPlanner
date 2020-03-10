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
import modelo.plan.Ejercicio;
import modelo.plan.EjxRut;
import modelo.plan.Plan;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author 201621279487
 */
public class RutinasController extends Controller<Plan> {

    @FXML
    private ComboBox<Plan> comboRutinas;

    @FXML
    private TextField textNombre;

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
    private ToggleButton buttonBloque1;

    @FXML
    private ToggleButton buttonBloque2;

    @FXML
    private ToggleButton buttonBloque3;

    @FXML
    private ToggleButton buttonBloque4;

    @FXML
    private ToggleButton buttonBloque5;

    @FXML
    private ListView<EjxRut> listView;
    @FXML
    private ComboBox<String> comboDia;
    @FXML
    private ComboBox<String> comboBloques;

    public void initialize(URL location, ResourceBundle resources) {

        obtener();
        obtenerEjercicios();
    }

    public void setCombos(){
        for (int i = 5; i <= 200; i = i + 5) {
            comboRepeticiones.getItems().add(i);
        }
        comboRepeticiones.getSelectionModel().select(0);
        comboSeries.getItems().setAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        comboSeries.getSelectionModel().select(0);
        comboDia.getItems().setAll("Domingo","Lunes","Martes","Miercoles","Jueves","Viernes","Sabado");
        comboDia.getSelectionModel().select(0);
        comboBloques.getItems().setAll("Bloque 1","Bloque 2","Bloque 3","Bloque 4","Bloque 5");
        comboBloques.getSelectionModel().select(0);
    }

    public Plan captar() throws DAOException {
        Plan d = new Plan();
        d.setTipo("rutina");
        d.setNombre(textNombre.getText());
        d.setDescripcion(textDescripcion.getText());
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
                eliminarEjxRuts();
                getPlanes().delete(comboRutinas.getSelectionModel().getSelectedItem());
                obtener();
                mensaje("Plan de entrenamiento eliminado", "exito");
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
        textDescripcion.setText("");
        textBuscar.setText("");
    }


    public void mostrar() {
        if (!comboRutinas.getItems().isEmpty()) {
            Plan d = comboRutinas.getSelectionModel().getSelectedItem();
            textNombre.setText(d.getNombre());
            textDescripcion.setText(d.getDescripcion());
        }
    }

    public void select(int i) {
        if (!comboRutinas.getItems().isEmpty()) {
            comboRutinas.getSelectionModel().select(i);
            mostrar();
        }
    }

    public EjxRut captarEjxRut() {
        if (!comboRutinas.getItems().isEmpty()) {
            EjxRut a = new EjxRut();
            a.setPlan(getRutina());
            a.setEjercicio(comboEjercicios.getSelectionModel().getSelectedItem());
            a.setSeries(comboSeries.getSelectionModel().getSelectedItem());
            a.setRepeticiones(comboRepeticiones.getSelectionModel().getSelectedItem());
            a.setDia(getDia());
            a.setDia(getMomento());
            return a;
        } else {
            mensaje("Primero debe registrar un plan", "aviso");
        }
        return null;
    }

    public String getMomento() {
        if (buttonBloque1.isSelected()) {
            return EjxRut.BLOQUE1;
        }
        if (buttonBloque2.isSelected()) {
            return EjxRut.BLOQUE2;
        }
        if (buttonBloque3.isSelected()) {
            return EjxRut.BLOQUE3;
        }
        if (buttonBloque5.isSelected()) {
            return EjxRut.BLOQUE4;
        }
        if (buttonBloque5.isSelected()) {
            return EjxRut.BLOQUE5;
        }
        mensaje("Seleccione el bloque para el ejercicio", "aviso");
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
                getEjxruts().delete(a);
                listView.getItems().remove(a);
            } catch (DAOException ex) {
                excepcion(ex);
            }
        }
    }

    public void addEjercicio() {
        try {
            EjxRut a = captarEjxRut();
            if (a != null) {
                if (!a.isEmpty()) {
                    getEjxruts().insert(a);
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
            getEjercicios().update(a);
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

    public void getBloque1() throws DAOException {
        String dia = getDia();
        if (getRutina().isEmpty()) {
            mensaje("Registre primero un plan", "aviso");
        } else {
            if (buttonBloque1.isSelected()) {
                if (dia.isEmpty()) {
                    mensaje("Seleccione un día primero", "aviso");
                    buttonBloque1.setSelected(false);
                } else {
                    listView.setItems(getEjxruts().where(getRutina()
                            .getPlankey(), getDia(), EjxRut.BLOQUE1));
                }
            } else {
                listView.getItems().clear();
            }
        }
    }

    public void getBloque2() throws DAOException {
        String dia = getDia();
        if (getRutina().isEmpty()) {
            mensaje("Registre primero un plan", "aviso");
        } else {
            if (buttonBloque2.isSelected()) {
                if (dia.isEmpty()) {
                    mensaje("Seleccione un día primero", "aviso");
                    buttonBloque2.setSelected(false);
                } else {
                    listView.setItems(getEjxruts().where(getRutina()
                            .getPlankey(), getDia(), EjxRut.BLOQUE2));
                }
            } else {
                listView.getItems().clear();
            }
        }
    }

    public void getBloque3() throws DAOException {
        String dia = getDia();
        if (getRutina().isEmpty()) {
            mensaje("Registre primero un plan", "aviso");
        } else {
            if (buttonBloque3.isSelected()) {
                if (dia.isEmpty()) {
                    mensaje("Seleccione un día primero", "aviso");
                    buttonBloque3.setSelected(false);
                } else {
                    listView.setItems(getEjxruts().where(getRutina()
                            .getPlankey(), getDia(), EjxRut.BLOQUE3));
                }
            } else {
                listView.getItems().clear();
            }
        }
    }

    public void getBloque4() throws DAOException {
        String dia = getDia();
        if (getRutina().isEmpty()) {
            mensaje("Registre primero un plan", "aviso");
        } else {
            if (buttonBloque4.isSelected()) {
                if (dia.isEmpty()) {
                    mensaje("Seleccione un día primero", "aviso");
                    buttonBloque4.setSelected(false);
                } else {
                    listView.setItems(getEjxruts().where(getRutina()
                            .getPlankey(), getDia(), EjxRut.BLOQUE4));
                }
            } else {
                listView.getItems().clear();
            }
        }
    }

    public void getBloque5() throws DAOException {
        String dia = getDia();
        if (getRutina().isEmpty()) {
            mensaje("Registre primero un plan", "aviso");
        } else {
            if (buttonBloque5.isSelected()) {
                if (dia.isEmpty()) {
                    mensaje("Seleccione un día primero", "aviso");
                    buttonBloque5.setSelected(false);
                } else {
                    listView.setItems(getEjxruts().where(getRutina()
                            .getPlankey(), getDia(), EjxRut.BLOQUE5));
                }
            } else {
                listView.getItems().clear();
            }
        }
    }

    /*Panel de ejercicios*/

    @FXML
    private ComboBox<Ejercicio> comboEjerciciosEjercicios;

    @FXML
    private TextField textBuscarEjercicioEjercicio;

    @FXML
    private TextField textNombreEjercicio;

    @FXML
    private TextArea textDescripcionEjercicio;

    @FXML
    private TextArea textComentariosEjercicio;

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

    public void modificarEjercicio() {
        if (!comboEjerciciosEjercicios.getItems().isEmpty()) {
            try {
                Ejercicio a = captarEjercicio();
                if (!a.isEmpty()) {
                    a.setEjerciciokey(comboEjerciciosEjercicios.getSelectionModel().getSelectedItem().getEjerciciokey());
                    getEjercicios().update(a);
                    mensaje("Ejercicio modificado", "exito");
                    obtenerEjercicios();
                } else {
                    mensaje("Los campos señalados con asterisco son obligatorios.", "aviso");
                }
            } catch (DAOException ex) {
                excepcion(ex);
            }
        } else {
            mensaje("Seleccione un ejercicio", "aviso");

        }
    }

    public void eliminarEjercicio() {
        if (!comboEjerciciosEjercicios.getItems().isEmpty()) {
            try {
                Ejercicio a = captarEjercicio();
                if (!a.isEmpty()) {
                    a.setEjerciciokey(comboEjerciciosEjercicios.getSelectionModel().getSelectedItem().getEjerciciokey());
                    getEjercicios().delete(a);
                    mensaje("Ejercicio eliminado", "exito");
                    obtenerEjercicios();
                } else {
                    mensaje("Los campos señalados con asterisco son obligatorios.", "aviso");
                }
            } catch (DAOException ex) {
                excepcion(ex);
            }
        } else {
            mensaje("Seleccione un ejercicio", "aviso");

        }
    }

    public void limpiarEjercicio() {
        textNombreEjercicio.setText("");
        textDescripcionEjercicio.setText("");
        textComentariosEjercicio.setText("");
    }

    public void mostrarEjercicio() {
        if (!comboEjerciciosEjercicios.getItems().isEmpty()) {
            Ejercicio c = comboEjerciciosEjercicios.getSelectionModel().getSelectedItem();
            textNombreEjercicio.setText(c.getNombre());
            textComentariosEjercicio.setText(c.getComentarios());
            textDescripcionEjercicio.setText(c.getDescripcion());
        }
    }

    public Ejercicio captarEjercicio() throws DAOException {
        Ejercicio c = new Ejercicio();
        c.setNombre(textNombreEjercicio.getText());
        c.setDescripcion(textDescripcionEjercicio.getText());
        c.setComentarios(textComentariosEjercicio.getText());
        return c;
    }

    public void obtenerEjercicios() {
        try {
            comboEjerciciosEjercicios.getItems().clear();
            if (textBuscarEjercicio.getText().isEmpty()) {
                ejercicios = getEjercicios().all();
            } else {
                ejercicios = getEjercicios().where(textBuscarEjercicio.getText());
            }
            if (!ejercicios.isEmpty()) {
                comboEjerciciosEjercicios.setItems(ejercicios);
                comboEjercicios.setItems(ejercicios);
                selectEjercicio(0);
            }
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    public void selectEjercicio(int i) {
        if (!comboEjerciciosEjercicios.getItems().isEmpty()) {
            comboEjerciciosEjercicios.getSelectionModel().select(i);
            comboEjercicios.getSelectionModel().select(i);
            mostrarEjercicio();
        }
    }


}
