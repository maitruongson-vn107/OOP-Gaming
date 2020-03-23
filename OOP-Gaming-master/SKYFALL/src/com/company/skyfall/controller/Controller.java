
package com.company.skyfall.controller;

import com.company.skyfall.view.PlayLayout;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    //switch to PlayLayout when "Play" button is clicked
    public void play(ActionEvent event) throws Exception{
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(PlayLayout.createContent());
        stage.setTitle("Play");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    //switch to HowToPlayLayout when "How To Play" button is clicked
    public void howToPlay(ActionEvent event) throws  Exception{
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/HowToPlayLayout.fxml"));
        Parent parent = loader.load();
        Scene HowToPlayScene = new Scene(parent);
        HowToPlayScene.getStylesheets().add(getClass().getResource("../view/Style.css").toExternalForm());
        stage.setScene(HowToPlayScene);
        stage.setFullScreen(true);
    }

    //alert a confirmation when exiting
    public void exit(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
       ButtonType ExitYesBtn = new ButtonType("Yes");
       ButtonType ExitNoBtn = new ButtonType("No");
      
       Alert ExitAlert = new Alert(Alert.AlertType.CONFIRMATION);
       ExitAlert.setTitle("Exit Confirmation");
       ExitAlert.setHeaderText("Are you sure to exit now?");
       ExitAlert.getButtonTypes().setAll(ExitYesBtn,ExitNoBtn);

        Optional<ButtonType> ExitAlertResult=ExitAlert.showAndWait();
        if (ExitAlertResult.get()==ExitYesBtn) stage.close();    
    }


    //switch back to MainMenuLayout when "Main Menu" button is clicked
    public static void backToMainMenuFromPlay(ActionEvent event) throws Exception{
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Controller.class.getResource("../View/MainMenuLayout.fxml"));
        Parent parent = loader.load();
        Scene MainMenuScene = new Scene(parent);
        MainMenuScene.getStylesheets().add(Controller.class.getResource("../view/Style.css").toExternalForm());
        stage.setScene(MainMenuScene);
        stage.setFullScreen(true);
    }
    public void backToMainMenuFromHowToPlay(ActionEvent event) throws Exception{
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/MainMenuLayout.fxml"));
        Parent parent = loader.load();
        Scene MainMenuScene = new Scene(parent);
        MainMenuScene.getStylesheets().add(getClass().getResource("../view/Style.css").toExternalForm());
        stage.setScene(MainMenuScene);
        stage.setFullScreen(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
