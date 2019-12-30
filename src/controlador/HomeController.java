/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import DAO.DAOException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Victor
 */
public class HomeController extends Controller<StackPane> {

    /**
     * StackPane principal, contiene todos los demás frames
     */
    @FXML
    protected StackPane holderPane;
    /**
     * StackPane que está ubicado en la parte inferior
     */
    @FXML
    public StackPane footer;

    //
    //Los siguientes son los frames o escenas a los módulos que hacen
    //referencia sus nombres
    //
    public static StackPane Clientes;
    public static StackPane FooterClientes;
    public static StackPane Dietas;
    public static StackPane Medidas;
    public static StackPane Rutinas;
    public static StackPane Alimentos;
    public static StackPane Ejercicios;
    public static StackPane Config;
    public static StackPane Dash;

    @FXML
    private ToggleButton buttonClientes;

    @FXML
    private ToggleButton buttonMedidas;

    @FXML
    private ToggleButton buttonDietas;

    @FXML
    private ToggleButton buttonRutinas;

    @FXML
    private ToggleButton buttonAlimentos;

    @FXML
    private ToggleButton buttonEjercicios;

    @FXML
    private ToggleButton buttonConfig;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        obtener();
        updated();
        try {
            if (getReferencias().obtener("DASH").getDescripcion().equalsIgnoreCase("ACTIVADO")) {
                switchClientes();
            } else {
                buttonClientes.setSelected(true);
                switchClientes();
            }
        } catch (DAOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        modificar();
    }

    @Override
    public void obtener() {
        try {
            Config = FXMLLoader.load(new File("src/vista/fxml/Config.fxml").toURL());
            Dash = FXMLLoader.load(new File("src/vista/fxml/Dash.fxml").toURL());
            FooterClientes = FXMLLoader.load(new File("src/vista/fxml/FooterClientes.fxml").toURL());
            setFooter(FooterClientes);
            Clientes = FXMLLoader.load(new File("src/vista/fxml/Clientes.fxml").toURL());
            Alimentos = FXMLLoader.load(new File("src/vista/fxml/Alimentos.fxml").toURL());
            Dietas = FXMLLoader.load(new File("src/vista/fxml/Dietas.fxml").toURL());
            Ejercicios = FXMLLoader.load(new File("src/vista/fxml/Ejercicios.fxml").toURL());
            Rutinas = FXMLLoader.load(new File("src/vista/fxml/Rutinas.fxml").toURL());
            Medidas = FXMLLoader.load(new File("src/vista/fxml/Medidas.fxml").toURL());
        } catch (MalformedURLException ex) {
            excepcion(ex);
        } catch (IOException ex) {
            excepcion(ex);
        } finally {
            mostrar(new StackPane());
        }
    }

    protected void setFooter(StackPane node) {
        footer.getChildren().clear();
        footer.getChildren().add(node);
        fadeTransition(node, 700);
    }

    @FXML
    private void switchClientes() {
        if (buttonClientes.isSelected()) {
            mostrar(Clientes);
        } else {
            mostrar(Dash);
        }
    }

    @FXML
    private void switchMedidas() {
        if (buttonMedidas.isSelected()) {
            mostrar(Medidas);
        } else {
            mostrar(Dash);
        }
    }

    @FXML
    private void switchDietas() {
        if (buttonDietas.isSelected()) {
            mostrar(Dietas);
        } else {
            mostrar(Dash);
        }
    }

    @FXML
    private void switchRutinas() {
        if (buttonRutinas.isSelected()) {
            mostrar(Rutinas);
        } else {
            mostrar(Dash);
        }
    }

    @FXML
    private void switchAlimentos() {
        if (buttonAlimentos.isSelected()) {
            mostrar(Alimentos);
        } else {
            mostrar(Dash);
        }
    }

    @FXML
    private void switchEjercicios() {
        if (buttonEjercicios.isSelected()) {
            mostrar(Ejercicios);
        } else {
            mostrar(Dash);
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

    /**
     * Si se presiona un botón del home, actualiza el estado correspondiente
     * para que los otros formularddios respondan
     */
    @Override
    public void updated() {
    }

    public void mostrar(StackPane node) {
        if (node == null) {
            node = new StackPane();
        }
        holderPane.getChildren().clear();
        holderPane.getChildren().add((Node) node);
        fadeTransition(node, 700);
    }

    @Override
    public StackPane captar() throws DAOException {
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

    @Override
    public void mostrar() {
    }
}
