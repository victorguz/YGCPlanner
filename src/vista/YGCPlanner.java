/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import archivo.PDF;
import controlador.Controller;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author 201621279487
 */
public class YGCPlanner extends Application {

    public static Stage stagestatic;

    @Override
    public void start(Stage stage) {
        Font.loadFont(getClass().getResource
            ("/fonts/Roboto-Black.ttf").toExternalForm(), 10);
        stagestatic = stage;
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("fxml/Home.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Dieta y ejercicio - Planificador Yezid Guzman Coach");
            stage.getIcons().add(new Image("/imagen/icono/icono24.png"));
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(YGCPlanner.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
