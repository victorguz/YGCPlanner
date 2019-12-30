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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

/**
 *
 * @author 201621279487
 */
public class ClienteController extends Controller<Cliente> {

    @FXML
    private TextField textNombre;

    @FXML
    private TextField textApellido;

    @FXML
    private TextField textDocumento;

    @FXML
    private ComboBox<String> comboTipoDoc;

    @FXML
    private ToggleButton buttonDieta;

    @FXML
    private ToggleButton buttonRutina;

    @FXML
    private ToggleButton buttonBienvenida;

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

    @Override
    public Cliente captar() throws DAOException {
        Cliente a = new Cliente();
        if (textNombre.getText().isEmpty()) {
            mensaje("Digite un nombre", "vacio");
        } else {
            a.setNombre(textNombre.getText());
        }
        if (textApellido.getText().isEmpty()) {
            mensaje("Digite un apellido", "vacio");
        } else {
            a.setApellido(textApellido.getText());
        }
        if (textEdad.getText().isEmpty()) {
            mensaje("Digite una edad", "vacio");
        } else {
            a.setEdad(Integer.parseInt(textEdad.getText()));
        }
        a.setTipoIdentificacion(comboTipoDoc.getSelectionModel().getSelectedItem());
        if (textDocumento.getText().isEmpty()) {
            mensaje("Digite un numero de documento", "vacio");
        } else {
            a.setIdentificacion(textDocumento.getText());
        }
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
            textNombre.setText(getCliente().getNombre());
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
                mensaje("A este cliente le faltan datos", "aviso");
            } else {
                getClientes().insertar(c);
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
                getClientes().eliminar(getCliente());
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
                getClientes().modificar(c);
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
            if (listView.getSelectionModel().getSelectedIndex() == -1) {
                mensaje("Seleccione un documento en la lista", "aviso");
            } else {
                PDF f = new PDF(listView.getSelectionModel().getSelectedItem());
                f.createPDF();
                if (!(buttonRutina.isSelected() && buttonDieta.isSelected() && buttonBienvenida.isSelected())) {
                    mensaje("Seleccione lo que desea ver en el documento", "aviso");
                } else {
                    if (buttonBienvenida.isSelected()) {
                        f.addBienvenida();
                    }
                    if (buttonRutina.isSelected()) {
                        f.addRutina();
                    }
                    if (buttonDieta.isSelected()) {
                        f.addDieta();
                    }
                    f.close();
                }
            }
        } catch (DocumentException | IOException ex) {
            excepcion(ex);
        } catch (DAOException ex) {
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    }

    public void obtenerMedidas() {
        if (!getCliente().isEmpty()) {
            if (!medidas.isEmpty()) {
                listView.setItems(medidas);
            }
        }
    }

}
