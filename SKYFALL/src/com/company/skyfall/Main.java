/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.skyfall;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import javafx.stage.Stage;

import java.io.File;

/**
 *
 * @author admin
 */
public class Main extends Application {

    public static MediaPlayer musicPlayer;

    @Override
    public void start(Stage stage) throws Exception {
        musicPlayer = new MediaPlayer(new Media(
                new File(new File("src/com/company/skyfall/resources/music_and_sound/music.mp3").getAbsolutePath()).toURI().toString()
        ));
        musicPlayer.play();
        stage.setTitle("WARSHIP BATTLE");
        stage.setResizable(true);
        Parent MainMenuLayout = FXMLLoader.load(getClass().getResource("view/MainMenuLayout.fxml"));
        Scene MainMenuScene = new Scene(MainMenuLayout,1080,630);
        MainMenuScene.getStylesheets().add(getClass().getResource("view/Style.css").toExternalForm());
        stage.setScene(MainMenuScene);
        stage.getIcons().add(new Image(new File("src/com/company/skyfall/resources/images/game_icon.png").toURI().toString()));
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
    
}

