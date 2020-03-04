/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import DAO.DAOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import modelo.cliente.Cliente;
import modelo.cliente.Medida;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author 201621279487
 */
public class FooterClientesController extends Controller {

    @FXML
    private ImageView img;

    @FXML
    private ComboBox<Cliente> comboClientes;

    @FXML
    private ComboBox<Medida> comboMedidas;


    public void initialize(URL url, ResourceBundle rb) {
        updated();
        setClientesUpdated(true);
        setMedidasUpdated(true);
    }

    public void cambiarImagen(String sexo) {
        if (sexo.equalsIgnoreCase("hombre")) {
            img.setImage(new Image("/imagen/icono/man.png"));
        } else {
            img.setImage(new Image("/imagen/icono/woman.png"));
        }
    }

    public void select(int i) {
        if (!comboClientes.getItems().isEmpty()) {
            comboClientes.getSelectionModel().select(i);
            setCliente(comboClientes.getSelectionModel().getSelectedItem());
        } else {
            setCliente(new Cliente());
        }
        cambiarImagen(getCliente().getSexo());
        obtenerMedidas();
    }

    public void select() {
        if (!comboClientes.getItems().isEmpty()) {
            setCliente(comboClientes.getSelectionModel().getSelectedItem());
        } else {
            setCliente(new Cliente());
        }
        cambiarImagen(getCliente().getSexo());
        obtenerMedidas();
    }

    public void selectMedida(int i) {
        if (!comboMedidas.getItems().isEmpty()) {
            comboMedidas.getSelectionModel().select(i);
            setMedida(comboMedidas.getSelectionModel().getSelectedItem());
        } else {
            setMedida(new Medida());
        }
    }

    public void selectMedida() {
        if (!comboMedidas.getItems().isEmpty()) {
            setMedida(comboMedidas.getSelectionModel().getSelectedItem());
        } else {
            setMedida(new Medida());
        }
    }


    public void obtener() {
        try {
            comboClientes.getItems().clear();
            if (textBuscar.getText().isEmpty()) {
                clientes = getClientes().all();
            } else {
                clientes = getClientes().where(textBuscar.getText());
            }
            if (!clientes.isEmpty()) {
                comboClientes.setItems(clientes);
                select(0);
                obtenerMedidas();
            }
        } catch (DAOException ex) {
            excepcion(ex);
        }
        setClientesUpdated(false);
        setClienteUpdated(true);
    }

    public void obtenerMedidas() {
        comboMedidas.getItems().clear();
        if (!getCliente().isEmpty()) {
            try {
                medidas = getMedidas().where("" + getCliente().getClienteKey());
                if (!medidas.isEmpty()) {
                    comboMedidas.setItems(medidas);
                    selectMedida(0);
                }
            } catch (DAOException ex) {
                excepcion(ex);
            }
        }
        setMedidasUpdated(false);
        setMedidaUpdated(true);
    }

    /**
     * Si se actualiza o elimina un cliente, este método actualiza el combobox
     * que contiene los clientes.
     */

    public void updated() {
        Thread t = new Thread(new Runnable() {

            public void run() {
                Runnable updater = new Runnable() {

                    public void run() {
                        if (isClientesUpdated()) {
                            obtener();
                        }
                        if (isMedidasUpdated()) {
                            obtenerMedidas();
                        }
                    }
                };
                while (true) {
                    try {
                        Thread.sleep(500);
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

}
