/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import DAO.DAOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Victor
 */
public class HomeController extends Controller<StackPane> {

    //
    //Los siguientes son los frames o escenas a los módulos que hacen
    //referencia sus nombres
    //
    public static StackPane Clientes;
    public static StackPane Dietas;
    public static StackPane Rutinas;
    public static StackPane Config;
    public static StackPane Dash;
    /**
     * StackPane que está ubicado en la parte inferior
     */
    @FXML
    public StackPane footer;
    /**
     * StackPane principal, contiene todos los demás frames
     */
    @FXML
    protected StackPane holderPane;
    @FXML
    private ToggleButton buttonClientes;

    @FXML
    private ToggleButton buttonDietas;

    @FXML
    private ToggleButton buttonRutinas;

    @FXML
    private ToggleButton buttonConfig;

    @FXML
    private ScrollPane scroll;

    public void initialize(URL url, ResourceBundle rb) {
        cargar();
        try {
            if (getReferencias().select("dash").getDato().equalsIgnoreCase("false")) {
                buttonClientes.setSelected(true);
                mostrar(Clientes);
            } else {
                buttonClientes.setSelected(false);
                mostrar(Dash);
            }
        } catch (DAOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cargar() {
        try {
            Config = FXMLLoader.load(new File("src/vista/fxml/Config.fxml").toURL());
            Dash = FXMLLoader.load(new File("src/vista/fxml/Dash.fxml").toURL());
            Dietas = FXMLLoader.load(new File("src/vista/fxml/Dietas.fxml").toURL());
            Rutinas = FXMLLoader.load(new File("src/vista/fxml/Rutinas.fxml").toURL());
            Clientes = FXMLLoader.load(new File("src/vista/fxml/Clientes.fxml").toURL());
        } catch (MalformedURLException ex) {
            excepcion(ex);
        } catch (IOException ex) {
            excepcion(ex);
        } finally {
            mostrar(new StackPane());
        }
    }

    @FXML
    private void switchClientes() {
        if (buttonClientes.isSelected()) {
            mostrar(Clientes);
        } else {
            buttonClientes.setSelected(true);
            mostrar(Clientes);
        }
    }

    @FXML
    private void switchDietas() {
        if (buttonDietas.isSelected()) {
            mostrar(Dietas);
        } else {
            buttonClientes.setSelected(true);
            mostrar(Clientes);
        }
    }

    @FXML
    private void switchRutinas() {
        if (buttonRutinas.isSelected()) {
            mostrar(Rutinas);
        } else {
            buttonClientes.setSelected(true);
            mostrar(Clientes);
        }
    }

    @FXML
    private void switchConfig() {
        if (buttonConfig.isSelected()) {
            mostrar(Config);
        } else {
            mostrar(Dash);
        }
    }

    public void mostrar(StackPane node) {
        if (node == null) {
            node = new StackPane();
        }
        holderPane.getChildren().clear();
        holderPane.getChildren().add(node);
        fadeTransition(node, 700);
    }


}