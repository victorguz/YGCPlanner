/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import DAO.DAOException;
import static controlador.Controller.isButtonsClientes;
import static controlador.Controller.isOnEliminar;
import static controlador.Controller.isOnLimpiar;
import static controlador.Controller.isOnModificar;
import static controlador.Controller.isOnRegistrar;
import static controlador.Controller.mensaje;
import static controlador.Controller.setOnEliminar;
import static controlador.Controller.setOnLimpiar;
import static controlador.Controller.setOnModificar;
import static controlador.Controller.setOnRegistrar;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import modelo.plan.Ejercicio;
import modelo.plan.Ejercicio;

public class EjerciciosController extends Controller<Ejercicio> {

    @FXML
    private TextField textNombre;

    @FXML
    private TextArea textDescripcion;

    @FXML
    private TextArea textComentarios;
    @Override
    public void updated() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Runnable updater = new Runnable() {
                    @Override
                    public void run() {
                        if (isButtonsClientes()) {
                            if (isOnRegistrar()) {
                                setOnRegistrar(false);
                                registrar();
                            } else if (isOnModificar()) {
                                setOnModificar(false);
                                modificar();
                            } else if (isOnEliminar()) {
                                setOnEliminar(false);
                                eliminar();
                            } else if (isOnLimpiar()) {
                                setOnLimpiar(false);
                                limpiar();
                            }
                        }
                    }
                };
                while (true) {
                    try {
                        Thread.sleep(1);
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
                    mensaje("A este ejercicio le faltan datos", "aviso", null);
                } else {
                    getEjercicios().insertar(c);
                    mensaje("Ejercicio registrado", "exito", null);
                    obtener();
                }
            }
        } catch (DAOException ex) {
            mensaje("Condición", "error", ex);
        }
    }

    @Override
    public void modificar() {
        if (!comboEjercicios.getItems().isEmpty()) {
            try {
                getEjercicios().modificar(comboEjercicios.getSelectionModel().getSelectedItem());
                mensaje("Ejercicio actualizado", "exito", null);
                obtener();
            } catch (DAOException ex) {
                mensaje("Condición", "error", ex);
            }
        } else {
            mensaje("Seleccione un ejercicio", "aviso", null);

        }
    }

    @Override
    public void eliminar() {
        if (!comboEjercicios.getItems().isEmpty()) {
            try {
                getEjercicios().eliminar(comboEjercicios.getSelectionModel().getSelectedItem());
                mensaje("Ejercicio eliminado", "exito", null);
                obtener();
            } catch (DAOException ex) {
                mensaje("Condición", "error", ex);
            }
        } else {
            mensaje("Seleccione un ejercicio", "aviso", null);

        }
    }

    @Override
    public void buscar() {
        buscarEjercicio();
        mostrar();
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
        obtenerEjercicios();
        mostrar();
    }
    
}
