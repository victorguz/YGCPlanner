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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Victor
 */
public class HomeController extends Controller<StackPane> {

    @FXML
    public StackPane footer;
    public static StackPane Clientes;
    public static StackPane FooterClientes;
    public static StackPane Dietas;
    public static StackPane Medidas;
    public static StackPane Rutinas;
    public static StackPane Alimentos;
    public static StackPane Ejercicios;
    public static StackPane Config;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        obtener();
        updated();
        switchClientes();
        setFooter(FooterClientes);
    }

    protected void setFooter(StackPane node) {
        footer.getChildren().clear();
        footer.getChildren().add(node);
        fadeTransition(node, 700);
    }

    @FXML
    private void switchClientes() {
        mostrar(Clientes);
    }

    @FXML
    private void switchMedidas() {
        mostrar(Medidas);
    }

    @FXML
    private void switchDietas() {
        mostrar(Dietas);
    }

    @FXML
    private void switchRutinas() {
        mostrar(Rutinas);
    }

    @FXML
    private void switchAlimentos() {
        mostrar(Alimentos);
    }

    @FXML
    private void switchEjercicios() {
        mostrar(Ejercicios);
    }

    @FXML
    private void switchConfig() {
        mostrar(Config);
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
    public void obtener() {
        try {
            FooterClientes = FXMLLoader.load(new File("src/vista/fxml/FooterClientes.fxml").toURL());
            setFooter(FooterClientes);
            Clientes = FXMLLoader.load(new File("src/vista/fxml/Clientes.fxml").toURL());
            Medidas = FXMLLoader.load(new File("src/vista/fxml/Medidas.fxml").toURL());
            Dietas = FXMLLoader.load(new File("src/vista/fxml/Dietas.fxml").toURL());
            Rutinas = FXMLLoader.load(new File("src/vista/fxml/Rutinas.fxml").toURL());
            Alimentos = FXMLLoader.load(new File("src/vista/fxml/Alimentos.fxml").toURL());
            Ejercicios = FXMLLoader.load(new File("src/vista/fxml/Ejercicios.fxml").toURL());
            Config = new StackPane();
        } catch (MalformedURLException ex) {
            mensaje("Condición", "error", new DAOException(ex));
        } catch (IOException ex) {
            mensaje("Condición", "error", new DAOException(ex));
        } finally {
            setFooter(new StackPane());
            mostrar(new StackPane());
        }
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
    public void buscar() {
        mensaje("Hola, soy el método buscar del formulario Home", "aviso", null);
    }

    @Override
    public void mostrar() {
    }
}
