/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import DAO.DAOException;
import archivo.PDF;
import com.itextpdf.text.DocumentException;
import static controlador.Controller.getCliente;
import static controlador.Controller.getClientes;
import static controlador.Controller.getMedidas;
import static controlador.Controller.isClienteUpdated;
import static controlador.Controller.isClientesUpdated;
import static controlador.Controller.isMedidasUpdated;
import static controlador.Controller.mensaje;
import static controlador.Controller.setBuscar;
import static controlador.Controller.setClienteUpdated;
import static controlador.Controller.setClientesUpdated;
import java.io.IOException;
import modelo.cliente.Cliente;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import modelo.cliente.Medida;

/**
 *
 * @author 201621279487
 */
public class FooterGrandeClientesController extends Controller<Cliente> {

    @FXML
    private ComboBox<Cliente> comboClientes;

    @FXML
    private ComboBox<Medida> comboMedidas;

    @FXML
    private TextField textBuscar;

    @FXML
    private TextField textApellido;

    @FXML
    private TextField textDocumento;

    @FXML
    private ComboBox<String> comboTipoDoc;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setCombos();
        obtenerMedidas();
        updated();
    }

    private void setCombos() {
        comboSexo.setItems(sexos);
        comboSexo.getSelectionModel().select(0);
        comboTipoDoc.getItems().addAll("C.C",
                "T.I", "C.Ext");
        comboTipoDoc.getSelectionModel().select(0);
    }

    public void select(int i) {
        if (!comboClientes.getItems().isEmpty()) {
            comboClientes.getSelectionModel().select(i);
            setCliente(comboClientes.getSelectionModel().getSelectedItem());
        } else {
            setCliente(new Cliente());
        }
        setClienteUpdated(true);
        obtenerMedidas();
    }

    public void select() {
        if (!comboClientes.getItems().isEmpty()) {
            setCliente(comboClientes.getSelectionModel().getSelectedItem());
        } else {
            setCliente(new Cliente());
        }
        setClienteUpdated(true);
        obtenerMedidas();
    }

    public void selectMedida(int i) {
        if (!comboMedidas.getItems().isEmpty()) {
            comboMedidas.getSelectionModel().select(i);
            setMedida(comboMedidas.getSelectionModel().getSelectedItem());
        } else {
            setMedida(new Medida());
        }
        setMedidaUpdated(true);
    }

    public void selectMedida() {
        if (!comboMedidas.getItems().isEmpty()) {
            setMedida(comboMedidas.getSelectionModel().getSelectedItem());
        } else {
            setMedida(new Medida());
        }
        setMedidaUpdated(true);
    }

    @Override
    public Cliente captar() throws DAOException {
        Cliente a = new Cliente();
        a.setNombre(textBuscar.getText());
        a.setApellido(textApellido.getText());
        if (textEdad.getText().isEmpty()) {
            a.setEdad(0);
        } else {
            a.setEdad(Integer.parseInt(textEdad.getText()));
        }
        a.setTipoIdentificacion(comboTipoDoc.getSelectionModel().getSelectedItem());
        a.setIdentificacion(textDocumento.getText());
        a.setSexo(comboSexo.getSelectionModel().getSelectedItem());
        return a;
    }

    @Override
    public void limpiar() {
        textBuscar.setText("");
        textApellido.setText("");
        textDocumento.setText("");
        textEdad.setText("");
        setCombos();
    }

    /**
     * Llena los campos de la vista con los datos del cliente
     *
     */
    @Override
    public void mostrar() {
        if (!getCliente().isEmpty()) {
            textBuscar.setText(getCliente().getNombre());
            textApellido.setText(getCliente().getApellido());
            setTipoDocumento(getCliente().getTipoIdentificacion());
            textDocumento.setText(getCliente().getIdentificacion());
            textEdad.setText(getCliente().getEdad() + "");
            setSexo(getCliente().getSexo());
        }
    }

    @Override
    public void registrar() {
        try {
            Cliente c = captar();
            if (c.isEmpty()) {
                mensaje("A este cliente le faltan datos", "aviso", null);
            } else {
                getClientes().insertar(c);
                setClientesUpdated(true);
                mensaje("Cliente registrado", "exito", null);
            }
        } catch (DAOException ex) {
            mensaje("Condición", "error", ex);
        }
    }

    @Override
    public void eliminar() {
        if (!getCliente().isEmpty()) {
            try {
                getClientes().eliminar(getCliente());
                setClientesUpdated(true);
                mensaje("Cliente eliminado", "exito", null);
            } catch (DAOException ex) {
                mensaje("Condición", "error", ex);
            }
        } else {
            mensaje("Seleccione primero un cliente", "aviso", null);
        }

    }

    @Override
    public void modificar() {
        if (!getCliente().isEmpty()) {
            try {
                Cliente c = captar();
                c.setClienteKey(getCliente().getClienteKey());
                getClientes().modificar(c);
                setBuscar(textBuscar.getText());
                setClientesUpdated(true);
                mensaje("Cliente modificado", "exito", null);
            } catch (DAOException ex) {
                mensaje("Condición", "error", ex);
            }
        } else {
            mensaje("Seleccione primero un cliente", "aviso", null);
        }
    }
public void imprimir(){
    
}
    public void setTipoDocumento(String documento) {
        for (int i = 0; i < comboTipoDoc.getItems().size(); i++) {
            if (comboTipoDoc.getItems().get(i).equalsIgnoreCase(documento)) {
                comboTipoDoc.getSelectionModel().select(i);
            }
        }
    }

    /**
     * Si se selecciona un cliente, este método lo muestra.
     */
    @Override
    public void updated() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Runnable updater = new Runnable() {
                    @Override
                    public void run() {
                        if (isClienteUpdated()) {
                            mostrar();
                            setClienteUpdated(false);
                            obtenerMedidas();
                        }
                        if (isMedidasUpdated()) {
                            obtenerMedidas();
                        }
                    }
                };
                while (true) {
                    try {
                        Thread.sleep(100);
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
    public void obtener() {
        if (!clientes.isEmpty()) {
            comboClientes.setItems(clientes);
        }
    }

    public void obtenerMedidas() {
            if (!getCliente().isEmpty()) {
                if (!medidas.isEmpty()) {
                    comboMedidas.setItems(medidas);
                }
            }
    }

}
