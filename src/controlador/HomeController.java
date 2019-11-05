/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import DAO.DAOException;
import static controlador.Controller.isOnEjercicios;
import static controlador.Controller.setOnClientes;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
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

    protected void setFooter(Node node) {
        footer.getChildren().clear();
        footer.getChildren().add((Node) node);
        fadeTransition(node, 700);
    }

    @FXML
    private void switchClientes() {
        //Forms
        setOnClientes(true);
        //Buttons
        setButtonsMedidas(false);
        setButtonsDietas(false);
        setButtonsRutinas(false);
        setButtonsAlimentos(false);
        setButtonsEjercicios(false);
        setButtonsConfig(false);
        setButtonsClientes(true);
    }

    @FXML
    private void switchMedidas() {
        //Forms
        setOnMedidas(true);
        //Buttons
        setButtonsClientes(false);
        setButtonsDietas(false);
        setButtonsRutinas(false);
        setButtonsAlimentos(false);
        setButtonsEjercicios(false);
        setButtonsConfig(false);
        setButtonsMedidas(true);
    }

    @FXML
    private void switchDietas() {
        //Forms
        setOnDietas(true);
        //Buttons
        setButtonsClientes(false);
        setButtonsMedidas(false);
        setButtonsRutinas(false);
        setButtonsAlimentos(false);
        setButtonsEjercicios(false);
        setButtonsConfig(false);
        setButtonsDietas(true);
    }

    @FXML
    private void switchRutinas() {
        //Forms
        setOnRutinas(true);
        //Buttons
        setButtonsClientes(false);
        setButtonsMedidas(false);
        setButtonsDietas(false);
        setButtonsAlimentos(false);
        setButtonsEjercicios(false);
        setButtonsConfig(false);
        setButtonsRutinas(true);
    }

    @FXML
    private void switchAlimentos() {
        //Forms
        setOnAlimentos(true);
        //Buttons
        setButtonsMedidas(false);
        setButtonsClientes(false);
        setButtonsDietas(false);
        setButtonsRutinas(false);
        setButtonsEjercicios(false);
        setButtonsConfig(false);
        setButtonsAlimentos(true);
    }

    @FXML
    private void switchEjercicios() {
        //Forms
        setOnEjercicios(true);
        //Buttons
        setButtonsMedidas(false);
        setButtonsClientes(false);
        setButtonsDietas(false);
        setButtonsRutinas(false);
        setButtonsAlimentos(false);
        setButtonsConfig(false);
        setButtonsEjercicios(true);
    }

    @FXML
    private void switchConfig() {
        //Forms
        setOnConfig(true);
        //Buttons
        setButtonsEjercicios(false);
        setButtonsMedidas(false);
        setButtonsClientes(false);
        setButtonsDietas(false);
        setButtonsRutinas(false);
        setButtonsAlimentos(false);
        setButtonsConfig(true);
    }

    /**
     * Si se presiona un botón del home, actualiza el estado correspondiente
     * para que los otros formularios respondan
     */
    @Override
    public void updated() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Runnable updater = new Runnable() {
                    @Override
                    public void run() {
                        if (isOnClientes()) {
                            mostrar(Clientes);
                            setOnClientes(false);
                        } else if (isOnMedidas()) {
                            mostrar(Medidas);
                            setOnMedidas(false);
                        } else if (isOnDietas()) {
                            mostrar(Dietas);
                            setOnDietas(false);
                        } else if (isOnRutinas()) {
                            mostrar(Rutinas);
                            setOnRutinas(false);
                        } else if (isOnAlimentos()) {
                            mostrar(Alimentos);
                            setOnAlimentos(false);
                        } else if (isOnEjercicios()) {
                            mostrar(Ejercicios);
                            setOnEjercicios(false);
                        } else if (isOnConfig()) {
                            mostrar(Config);
                            setOnConfig(false);
                        }
                    }
                };
                while (true) {
                    try {
                        Thread.sleep(10);
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

    public void mostrar(StackPane node) {
        if(node==null){
            node=new StackPane();
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
            Clientes = FXMLLoader.load(new File("src/vista/fxml/Clientes.fxml").toURL());
            FooterClientes = FXMLLoader.load(new File("src/vista/fxml/FooterClientes.fxml").toURL());
            Medidas = FXMLLoader.load(new File("src/vista/fxml/Medidas.fxml").toURL());
            Dietas = FXMLLoader.load(new File("src/vista/fxml/Dietas.fxml").toURL());
            Rutinas = FXMLLoader.load(new File("src/vista/fxml/Rutinas.fxml").toURL());
            Alimentos = FXMLLoader.load(new File("src/vista/fxml/Alimentos.fxml").toURL());
            Ejercicios = new StackPane();
            Config = new StackPane();
            setFooter(FooterClientes);
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
        setOnRegistrar(true);
    }

    @Override
    public void modificar() {
        setOnModificar(true);
    }

    @Override
    public void eliminar() {
        setOnEliminar(true);
    }

    @Override
    public void limpiar() {
        setOnLimpiar(true);
    }

    @Override
    public void buscar() {
        mensaje("Hola, soy el método buscar del formulario Home", "aviso", null);
    }

    public void imprimir() {
    }

    @Override
    public void mostrar() {
    }
}
