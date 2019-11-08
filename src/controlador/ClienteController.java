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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

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


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setCombos();
        updated();
    }
    
    private void setCombos() {
        if (comboSexo.getItems().isEmpty()) {
            comboSexo.setItems(sexos);
            comboSexo.getSelectionModel().select(0);
        }
        if (comboTipoDoc.getItems().isEmpty()) {
            ObservableList<String> tiposDeDocumentos = FXCollections.observableArrayList();
            tiposDeDocumentos.addAll(
                    "CEDULA DE CIUDADANIA",
                    "TARJETA DE IDENTIDAD",
                    "PASAPORTE",
                    "CEDULA EXTRANJERA"
            );
            comboTipoDoc.setItems(tiposDeDocumentos);
            comboTipoDoc.getSelectionModel().select(0);
        }
    }

    /**
     *
     * @return @throws DAOException
     */
    @Override
    public Cliente captar() throws DAOException {
        Cliente a = new Cliente();
        a.setNombre(textNombre.getText());
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
                setClientesUpdated(true);
                mensaje("Cliente actualizado", "exito", null);
            } catch (DAOException ex) {
                mensaje("Condición", "error", ex);
            }
        } else {
            mensaje("Seleccione primero un cliente", "aviso", null);
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
        if (isClienteUpdated()) {
            mostrar();
            setClienteUpdated(false);
        }
    }

    @Override
    public void obtener() {
    }

    @Override
    public void buscar() {
    }
}
