
package com.company.skyfall.controller;

import com.company.skyfall.view.HighScoreLayout;
import com.company.skyfall.view.PlayLayout;
import com.company.skyfall.view.OptionLayout;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class MainMenuController implements Initializable {
    //switch to PlayLayout when "Play" button is clicked
    public void play(ActionEvent event) throws Exception{
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(PlayLayout.createContent(!OptionLayout.getLevel()),1366,768);
        stage.setTitle("WARSHIP BATTLE-PLAY");
        stage.setScene(scene);
        if (Screen.getPrimary().getBounds().getMaxX() <= 1366) stage.setFullScreen(true);
        scene.getStylesheets().add(getClass().getResource("../view/Style.css").toExternalForm());
        stage.show();
    }

    //switch to HowToPlayLayout when "How To Play" button is clicked
    public void howToPlay(ActionEvent event) throws  Exception{
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/HowToPlayLayout.fxml"));
        Parent parent = loader.load();
        Scene HowToPlayScene = new Scene(parent,1080,630);
        HowToPlayScene.getStylesheets().add(getClass().getResource("../view/Style.css").toExternalForm());
        stage.setScene(HowToPlayScene);
        stage.setTitle("WARSHIP BATTLE-HOW TO PLAY");
    }


    //switch to "OptionLayout" when "Option" button is clicked
    public void option(ActionEvent event) throws Exception{
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(OptionLayout.createOptionLayout(),1080,630);
        scene.getStylesheets().add(getClass().getResource("../view/Style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("WARSHIP BATTLE-OPTION");
    }

    //switch to HighScoreLayout when "High score" button is clicked
    public void highScore(ActionEvent event) throws  Exception{
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(HighScoreLayout.createHighScoreLayout(),1080,630);
        stage.setTitle("WARSHIP BATTLE-HIGH SCORE");
        stage.setScene(scene);
      //  if (Screen.getPrimary().getBounds().getMaxX() <= 1366) stage.setFullScreen(true);
        scene.getStylesheets().add(getClass().getResource("../view/Style.css").toExternalForm());
        stage.show();
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
    public static void backToMainMenuFromPlay(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainMenuController.class.getResource("../View/MainMenuLayout.fxml"));
        Parent parent = loader.load();
        Scene MainMenuScene = new Scene(parent,1080,630);
        MainMenuScene.getStylesheets().add(MainMenuController.class.getResource("../view/Style.css").toExternalForm());
        stage.setScene(MainMenuScene);
        stage.setTitle("WARSHIP BATTLE");}
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public void backToMainMenu(ActionEvent event) throws Exception{
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/MainMenuLayout.fxml"));
        Parent parent = loader.load();
        Scene MainMenuScene = new Scene(parent,1080,630);
        MainMenuScene.getStylesheets().add(getClass().getResource("../view/Style.css").toExternalForm());
        stage.setScene(MainMenuScene);
        stage.setTitle("WARSHIP BATTLE");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
