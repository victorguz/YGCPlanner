/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author 201621279487
 */
public class Main extends Application {

    public static Stage stagestatic;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stagestatic = stage;
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("fxml/Home.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("YGCPlanner - Planificador Yezid Guzman Coach");
            stage.setScene(scene);
            stage.getIcons().add(new Image("/imagen/icono/icono16.png"));
            stage.setMinHeight(450);
            stage.setMinWidth(780);
            stage.setHeight(550);
            stage.setWidth(780);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
