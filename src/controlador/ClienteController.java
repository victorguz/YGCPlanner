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
import static controlador.Controller.isClienteUpdated;
import static controlador.Controller.isMedidasUpdated;
import static controlador.Controller.mensaje;
import static controlador.Controller.setClienteUpdated;
import static controlador.Controller.setClientesUpdated;
import static controlador.Controller.sexos;
import java.io.IOException;
import modelo.cliente.Cliente;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import modelo.cliente.Medida;

/**
 *
 * @author 201621279487
 */
public class ClienteController extends Controller<Cliente> {

    @FXML
    protected ListView<Medida> listView;

    @FXML
    private TextField textNombre;

    @FXML
    private TextField textApellido;

    @FXML
    private TextField textDocumento;

    @FXML
    private ComboBox<String> comboTipoDoc;

    @FXML
    private ToggleButton buttonBienvenida;

    @FXML
    private ToggleButton buttonMedidas;

    @FXML
    private ToggleButton buttonRutina;

    @FXML
    private ToggleButton buttonDieta;

    @FXML
    private ComboBox<String> comboSexo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setCombos();
        updated();
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

    private void setCombos() {
        comboSexo.setItems(sexos);
        comboSexo.getSelectionModel().select(0);
        comboTipoDoc.getItems().addAll("Cédula de ciudadanía",
                "Tarjeta de identidad", "Cédula extranjera");
        comboTipoDoc.getSelectionModel().select(0);
    }

    @Override
    public Cliente captar() throws DAOException {
        Cliente a = new Cliente();
        a.setNombre(textNombre.getText());
        a.setApellido(textApellido.getText());
        a.setEdad(Integer.parseInt(textEdad.getText()));
        a.setTipoIdentificacion(comboTipoDoc.getSelectionModel().getSelectedItem());
        a.setIdentificacion(textDocumento.getText());
        a.setSexo(comboSexo.getSelectionModel().getSelectedItem());
        return a;
    }

    @Override
    public void limpiar() {
        textNombre.setText("");
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
            textNombre.setText(Operacion.nombreCamelCase(getCliente().getNombre()));
            textApellido.setText(Operacion.nombreCamelCase(getCliente().getApellido()));
            selectCombo(comboTipoDoc, getCliente().getTipoIdentificacion());
            textDocumento.setText(getCliente().getIdentificacion());
            textEdad.setText(getCliente().getEdad() + "");
            selectCombo(comboSexo, getCliente().getSexo());
        }
    }

    @Override
    public void registrar() {
        try {
            Cliente c = captar();
            if (c.isEmpty()) {
                mensaje("Los campos señalados con asterisco son obligatorios.", "aviso");
            } else {
                getClientes().insert(c);
                setClientesUpdated(true);
                mensaje("Cliente registrado", "exito");
            }
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    @Override
    public void eliminar() {
        if (!getCliente().isEmpty()) {
            try {
                getClientes().delete(getCliente());
                setClientesUpdated(true);
                mensaje("Cliente eliminado", "exito");
            } catch (DAOException ex) {
                excepcion(ex);
            }
        } else {
            mensaje("Seleccione primero un cliente", "aviso");
        }

    }

    @Override
    public void modificar() {
        if (!getCliente().isEmpty()) {
            try {
                Cliente c = captar();
                c.setClienteKey(getCliente().getClienteKey());
                getClientes().update(c);
                setClientesUpdated(true);
                mensaje("Cliente modificado", "exito");
            } catch (DAOException ex) {
                excepcion(ex);
            }
        } else {
            mensaje("Seleccione primero un cliente", "aviso");
        }
    }

    public void imprimir() {
        try {
            PDF f = new PDF();
            f.createPDF();
            f.setCliente(getCliente());
            if (buttonBienvenida.isSelected()) {
                f.addBienvenida();
            }
            if (medidas.isEmpty()) {
                mensaje("Este cliente no tiene medidas, ni planes asignados", "error");
            } else {
                if (buttonMedidas.isSelected()) {
                    f.addMedidas();
                }
                if (buttonRutina.isSelected()) {
                    f.addRutina();
                }
                if (buttonDieta.isSelected()) {
                    f.addDieta();
                }
            }
            f.close();
        } catch (DocumentException | IOException | DAOException ex) {
            excepcion(ex);
        }
    }

    @Override
    public void obtener() {
    }

}
