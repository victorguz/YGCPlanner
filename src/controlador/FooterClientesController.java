/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import DAO.DAOException;
import archivo.PDF;
import com.itextpdf.text.DocumentException;
import static controlador.Controller.isClientesUpdated;
import static controlador.Controller.setClientesUpdated;
import java.io.IOException;
import modelo.cliente.Cliente;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.application.Application.STYLESHEET_CASPIAN;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import modelo.cliente.Medida;

/**
 *
 * @author 201621279487
 */
public class FooterClientesController extends Controller {

    @FXML
    private ComboBox<Cliente> comboClientes;

    @FXML
    private ComboBox<Medida> comboMedidas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updated();
        setClientesUpdated(true);
        setMedidasUpdated(true);
    }

    public void select(int i) {
        if (!comboClientes.getItems().isEmpty()) {
            comboClientes.getSelectionModel().select(i);
            setCliente(comboClientes.getSelectionModel().getSelectedItem());
        } else {
            setCliente(new Cliente());
        }
        obtenerMedidas();
    }

    public void select() {
        if (!comboClientes.getItems().isEmpty()) {
            setCliente(comboClientes.getSelectionModel().getSelectedItem());
        } else {
            setCliente(new Cliente());
        }
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

    @Override
    public void obtener() {
        try {
            comboClientes.getItems().clear();
            if (textBuscar.getText().isEmpty()) {
                clientes = getClientes().obtenerTodos();
            } else {
                clientes = getClientes().obtenerTodos(textBuscar.getText());
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
                medidas = getMedidas().obtenerTodos("" + getCliente().getClienteKey());
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
    @Override
    public void updated() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Runnable updater = new Runnable() {
                    @Override
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

    @Override
    public void mostrar() {
      
    }

    @Override
    public Object captar() {
        return null;
    }

    @Override
    public void registrar() {
    }

    @Override
    public void modificar() {
    }

    @Override
    public void eliminar() {
    }

    @Override
    public void limpiar() {
    }
}
