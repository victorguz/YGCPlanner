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
import javafx.scene.control.TextField;
import modelo.plan.Alimento;

public class AlimentosController extends Controller<Alimento> {

    @FXML
    private TextField textNombre;

    @FXML
    private TextField textKilocalorias;

    @FXML
    private TextField textProteina;

    @FXML
    private TextField textGrasas;

    @FXML
    private TextField textCarbos;

    @FXML
    private TextField textKilocalorias1;

    @FXML
    private TextField textProteina1;

    @FXML
    private TextField textGrasas1;

    @FXML
    private TextField textCarbos1;

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
            Alimento c = captar();
            if (c != null) {
                if (c.isEmpty()) {
                    mensaje("A este alimento le faltan datos", "aviso", null);
                } else {
                    getAlimentos().insertar(c);
                    mensaje("Alimento registrado", "exito", null);
                    obtener();
                }
            }
        } catch (DAOException ex) {
            mensaje("Condición", "error", ex);
        }
    }

    @Override
    public void modificar() {
        if (!comboAlimentos.getItems().isEmpty()) {
            try {
                getAlimentos().modificar(comboAlimentos.getSelectionModel().getSelectedItem());
                mensaje("Alimento actualizado", "exito", null);
                obtener();
            } catch (DAOException ex) {
                mensaje("Condición", "error", ex);
            }
        } else {
            mensaje("Seleccione un alimento", "aviso", null);

        }
    }

    @Override
    public void eliminar() {
        if (!comboAlimentos.getItems().isEmpty()) {
            try {
                getAlimentos().eliminar(comboAlimentos.getSelectionModel().getSelectedItem());
                mensaje("Alimento eliminado", "exito", null);
                obtener();
            } catch (DAOException ex) {
                mensaje("Condición", "error", ex);
            }
        } else {
            mensaje("Seleccione un alimento", "aviso", null);

        }
    }

    @Override
    public void buscar() {
        buscarAlimento();
        mostrar();
    }

    @Override
    public void limpiar() {
        textNombre.setText("");
        textProteina.setText("");
        textGrasas.setText("");
        textCarbos.setText("");
        textKilocalorias.setText("");
        textProteina1.setText("");
        textGrasas1.setText("");
        textCarbos1.setText("");
        textKilocalorias1.setText("");
    }

    @Override
    public void mostrar() {
        if (!comboAlimentos.getItems().isEmpty()) {
            Alimento c = comboAlimentos.getSelectionModel().getSelectedItem();
            textNombre.setText(c.getNombre());
            textProteina.setText("" + c.getProteinas());
            textGrasas.setText("" + c.getGrasas());
            textCarbos.setText("" + c.getCarbohidratos());
            textKilocalorias.setText("" + c.getKilocalorias());
            //1 gramo
            textProteina1.setText("" + c.getProteinas() / 100);
            textGrasas1.setText("" + c.getGrasas() / 100);
            textCarbos1.setText("" + c.getCarbohidratos() / 100);
            textKilocalorias1.setText("" + c.getKilocalorias() / 100);
        }
    }

    @Override
    public Alimento captar() throws DAOException {
        Alimento c = new Alimento();
        c.setNombre(textNombre.getText());
        c.setProteinas(Double.parseDouble(textProteina.getText()));
        c.setGrasas(Double.parseDouble(textGrasas.getText()));
        c.setCarbohidratos(Double.parseDouble(textCarbos.getText()));
        return c;
    }

    @Override
    public void obtener() {
        obtenerAlimentos();
        mostrar();
    }
    
}
