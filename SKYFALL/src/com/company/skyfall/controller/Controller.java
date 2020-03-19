
package com.company.skyfall.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class Controller<VBOX> implements Initializable {

    //chuyen sang man hinh Play khi an nut Play
    public void play(ActionEvent event) throws Exception{
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(battleship.BattleshipMain.createContent(), 1500, 900);
        stage.setTitle("Play");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    //chuyen sang man hinh How to Play khi an nut How To Play
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

    //hien ra thong bao khi an nut exit
    public void exit(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
       ButtonType ExitYesBtn = new ButtonType("Yes");
       ButtonType ExitNoBtn = new ButtonType("No");
      
       Alert ExitAlert = new Alert(Alert.AlertType.CONFIRMATION);
       ExitAlert.setTitle("Exit Confirmation");
       ExitAlert.setHeaderText("Do you really want to exit now?");
       ExitAlert.getButtonTypes().setAll(ExitYesBtn,ExitNoBtn);

        Optional<ButtonType> ExitAlertResult=ExitAlert.showAndWait();
        if (ExitAlertResult.get()==ExitYesBtn) stage.close();    
    }



    public static void backToMainMenuFromPlay(ActionEvent event) throws Exception{
        //chuyen ve Main Menu Scene khi an nut Main Menu
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
        //chuyen ve Main Menu Scene khi an nut Main Menu
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/MainMenuLayout.fxml"));
        Parent parent = loader.load();
        Scene MainMenuScene = new Scene(parent);
        MainMenuScene.getStylesheets().add(getClass().getResource("../view/Style.css").toExternalForm());
        stage.setScene(MainMenuScene);
        stage.setFullScreen(true);
    }
    @FXML
    private VBox enemyBoard;



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
