/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author admin
 */
public class Main extends Application {
    
    @Override
    public void start(Stage stage)throws Exception {
       stage.setTitle("Battle ship");
       Parent MainMenuLayout = FXMLLoader.load(getClass().getResource("view/MainMenuLayout.fxml"));
       Scene MainMenuScene = new Scene(MainMenuLayout);
       MainMenuScene.getStylesheets().add(getClass().getResource("view/Style.css").toExternalForm());
       stage.setScene(MainMenuScene);
       stage.show();  
    }

  
    public static void main(String[] args) {
        launch(args);
    }
    
}
