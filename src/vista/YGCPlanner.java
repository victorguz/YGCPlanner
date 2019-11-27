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

/**
 *
 * @author 201621279487
 */
public class YGCPlanner extends Application {

    public static Stage stagestatic;

    @Override
    public void start(Stage stage) throws Exception {
        stagestatic = stage;
        Parent root = FXMLLoader.load(getClass().getResource("fxml/Home.fxml"));
        Scene scene = new Scene(root);
        //stage.setResizable(false);
        stage.setTitle("Dieta y ejercicio - Planificador Yezid Guzman Coach");
        stage.getIcons().add(new Image("/imagen/icono/icono24.png"));
        stage.setScene(scene);
        stage.show();
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
