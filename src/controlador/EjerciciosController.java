/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import DAO.DAOException;
import static controlador.Controller.getEjercicios;
import static controlador.Controller.mensaje;
import static controlador.Controller.setEjerciciosUpdated;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import modelo.plan.Ejercicio;

public class EjerciciosController extends Controller<Ejercicio> {

    @FXML
    private ComboBox<Ejercicio> comboEjercicios;

    @FXML
    private TextField textBuscarEjercicio;
    @FXML
    private TextField textNombre;

    @FXML
    private TextArea textDescripcion;

    @FXML
    private TextArea textComentarios;

    @Override
    public void updated() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        obtener();
        updated();
    }

    @Override
    public void registrar() {
        try {
            Ejercicio c = captar();
            if (c != null) {
                if (c.isEmpty()) {
                    mensaje("Los campos señalados con asterisco son obligatorios.", "aviso");
                } else {
                    getEjercicios().insertar(c);
                    mensaje("Ejercicio registrado", "exito");
                    obtener();
                    setEjerciciosUpdated(true);
                }
            }
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    @Override
    public void modificar() {
        if (!comboEjercicios.getItems().isEmpty()) {
            try {
                Ejercicio a = captar();
                if (!a.isEmpty()) {
                    a.setEjerciciokey(comboEjercicios.getSelectionModel().getSelectedItem().getEjerciciokey());
                    getEjercicios().modificar(a);
                    mensaje("Ejercicio modificado", "exito");
                    textBuscarEjercicio.setText(textNombre.getText());
                    obtener();
                    setEjerciciosUpdated(true);
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

    @Override
    public void eliminar() {
        if (!comboEjercicios.getItems().isEmpty()) {
            try {
                Ejercicio a = captar();
                if (!a.isEmpty()) {
                    a.setEjerciciokey(comboEjercicios.getSelectionModel().getSelectedItem().getEjerciciokey());
                    getEjercicios().eliminar(a);
                    mensaje("Ejercicio eliminado", "exito");
                    obtener();
                    setEjerciciosUpdated(true);
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

    @Override
    public void limpiar() {
        textNombre.setText("");
        textDescripcion.setText("");
        textComentarios.setText("");
    }

    @Override
    public void mostrar() {
        if (!comboEjercicios.getItems().isEmpty()) {
            Ejercicio c = comboEjercicios.getSelectionModel().getSelectedItem();
            textNombre.setText(c.getNombre());
            textComentarios.setText(c.getComentarios());
            textDescripcion.setText(c.getDescripcion());
        }
    }

    @Override
    public Ejercicio captar() throws DAOException {
        Ejercicio c = new Ejercicio();
            c.setNombre(textNombre.getText());
        c.setDescripcion(textDescripcion.getText());
        c.setComentarios(textComentarios.getText());
        return c;
    }

    @Override
    public void obtener() {
        try {
            comboEjercicios.getItems().clear();
            if (textBuscarEjercicio.getText().isEmpty()) {
                ejercicios = getEjercicios().obtenerTodos();
            } else {
                ejercicios = getEjercicios().obtenerTodos(textBuscarEjercicio.getText());
            }
            if (!ejercicios.isEmpty()) {
                comboEjercicios.setItems(ejercicios);
                selectEjercicio(0);
            }
        } catch (DAOException ex) {
            excepcion(ex);
        }
        setEjerciciosUpdated(true);
    }

    public void selectEjercicio(int i) {
        if (!comboEjercicios.getItems().isEmpty()) {
            comboEjercicios.getSelectionModel().select(i);
            mostrar();
        }
    }

}
