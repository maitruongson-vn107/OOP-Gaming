<<<<<<< HEAD
package com.company.skyfall.view;


import com.company.skyfall.Main;
import com.company.skyfall.model.Board;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class OptionLayout {


    public static Parent createOptionLayout(){

        //create layout
        BorderPane root = new BorderPane();

        Label soundLabel = new Label("Sound");
        soundLabel.setTextFill(Color.rgb(245, 214, 157));
        soundLabel.setFont(new Font("Arial", 32));
        soundLabel.setStyle("-fx-background-color: #521a1a");

        Label musicLabel = new Label("Music");
        musicLabel.setTextFill(Color.rgb(245, 214, 157));
        musicLabel.setFont(new Font("Arial", 32));
        musicLabel.setStyle("-fx-background-color: #521a1a");

        HBox labels = new HBox(125, soundLabel, musicLabel);
        labels.setPadding(new Insets(200, 0, 0,550));

        Slider soundSlider = new Slider();
        soundSlider.setMax(1);
        soundSlider.setMin(0);
        soundSlider.setValue(Board.soundLevel);
        soundSlider.valueProperty().addListener(observable ->
                Board.soundLevel = soundSlider.getValue()
        );

        Slider musicSlider = new Slider(0,1,1);
        musicSlider.setMax(1);
        musicSlider.setMin(0);
        musicSlider.setValue(Main.musicPlayer.getVolume());
        musicSlider.valueProperty().addListener(observable ->
                Main.musicPlayer.setVolume(musicSlider.getValue())
        );


        HBox sliders = new HBox(75, soundSlider, musicSlider);
        sliders.setPadding(new Insets(0, 0 ,0 ,550));

        VBox center = new VBox(50, labels, sliders);

        Button mainMenuBtn = new Button("Main Menu");
        mainMenuBtn.setId("MainMenuBtn");
        mainMenuBtn.setOnAction(e -> {
            try {
                com.company.skyfall.controller.Controller.backToMainMenuFromPlay(e);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });


        root.setCenter(center);
        root.setBottom(mainMenuBtn);
        return root;
    }
}
=======
package com.company.skyfall.view;


import com.company.skyfall.Main;
import com.company.skyfall.model.Board;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class OptionLayout {


    public static Parent createOptionLayout(){

        //create layout
        BorderPane root = new BorderPane();

        Label soundLabel = new Label("Sound");
        soundLabel.setTextFill(Color.rgb(245, 214, 157));
        soundLabel.setFont(new Font("Arial", 32));
        soundLabel.setStyle("-fx-background-color: #521a1a");

        Label musicLabel = new Label("Music");
        musicLabel.setTextFill(Color.rgb(245, 214, 157));
        musicLabel.setFont(new Font("Arial", 32));
        musicLabel.setStyle("-fx-background-color: #521a1a");

        HBox labels = new HBox(125, soundLabel, musicLabel);
        labels.setPadding(new Insets(200, 0, 0,550));

        Slider soundSlider = new Slider();
        soundSlider.setMax(1);
        soundSlider.setMin(0);
        soundSlider.setValue(Board.soundLevel);
        soundSlider.valueProperty().addListener(observable ->
                Board.soundLevel = soundSlider.getValue()
        );

        Slider musicSlider = new Slider(0,1,1);
        musicSlider.setMax(1);
        musicSlider.setMin(0);
        musicSlider.setValue(Main.musicPlayer.getVolume());
        musicSlider.valueProperty().addListener(observable ->
                Main.musicPlayer.setVolume(musicSlider.getValue())
        );


        HBox sliders = new HBox(75, soundSlider, musicSlider);
        sliders.setPadding(new Insets(0, 0 ,0 ,550));

        VBox center = new VBox(50, labels, sliders);

        Button mainMenuBtn = new Button("Main Menu");
        mainMenuBtn.setId("MainMenuBtn");
        mainMenuBtn.setOnAction(e -> {
            try {
                com.company.skyfall.controller.Controller.backToMainMenuFromPlay(e);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });


        root.setCenter(center);
        root.setBottom(mainMenuBtn);
        return root;
    }
}
>>>>>>> 2afc9e14f2afb88b9fcbb22cc95347f63d295664
