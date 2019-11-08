/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import DAO.DAOException;
import modelo.cliente.Cliente;
import java.net.URL;
import java.util.ResourceBundle;
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
        obtener();
        obtenerMedidas();
        updated();
    }

    @Override
    public void buscar() {
        if (textBuscar.getText().isEmpty()) {
            obtener();
        } else {
            try {
                clientes = getClientes().obtenerTodos(textBuscar.getText());
                comboClientes.getItems().clear();
                if (clientes.isEmpty()) {
                    mensaje("No se encontraron clientes", "aviso", null);
                } else {
                    comboClientes.setItems(clientes);
                    select(0);
                    mensaje("Se encontraron " + clientes.size() + " clientes", "exito", null);
                }
            } catch (DAOException ex) {
                mensaje("Condición", "error", ex);
            }
        }
    }

    public void select(int i) {
        if (!comboClientes.getItems().isEmpty()) {
            comboClientes.getSelectionModel().select(i);
            setCliente(comboClientes.getSelectionModel().getSelectedItem());
            setClienteUpdated(true);
        } else {
            setCliente(new Cliente());
            setClienteUpdated(true);
        }
    }

    public void select() {
        if (!comboClientes.getItems().isEmpty()) {
            setCliente(comboClientes.getSelectionModel().getSelectedItem());
            setClienteUpdated(true);
            setMedidasUpdated(true);
        } else {
            setCliente(new Cliente());
            setClienteUpdated(true);
            setMedidasUpdated(true);
        }
    }

    public void selectMedida(int i) {
        if (!comboMedidas.getItems().isEmpty()) {
            comboMedidas.getSelectionModel().select(i);
            setMedida(comboMedidas.getSelectionModel().getSelectedItem());
            setMedidaUpdated(true);
        }
    }

    public void selectMedida() {
        if (!comboMedidas.getItems().isEmpty()) {
            setMedida(comboMedidas.getSelectionModel().getSelectedItem());
            setMedidaUpdated(true);
        }
    }

    @Override
    public void obtener() {
        try {
            clientes = getClientes().obtenerTodos();
            comboClientes.getItems().clear();
            if (clientes.isEmpty()) {
                setCliente(new Cliente());
                setClienteUpdated(true);
            } else {
                comboClientes.setItems(clientes);
                select(0);
            }
            setClientesUpdated(false);
        } catch (DAOException ex) {
            mensaje("Condición", "error", ex);
        }
    }

    public void obtenerMedidas() {
        if (!comboClientes.getItems().isEmpty()) {
            try {
                medidas = getMedidas().obtenerTodos("" + getCliente().getClienteKey());
                comboMedidas.getItems().clear();
                if (medidas.isEmpty()) {
                    setMedida(new Medida());
                    setMedidaUpdated(true);
                } else {
                    comboMedidas.setItems(medidas);
                    selectMedida(0);
                }
                setMedidasUpdated(false);
            } catch (DAOException ex) {
                mensaje("Condición", "error", ex);
            }
        }
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
