package com.company.skyfall.view;


import com.company.skyfall.Main;
import com.company.skyfall.controller.MainMenuController;
import com.company.skyfall.model.Board;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class OptionLayout {
    private static RadioButton easyBtn = new RadioButton("Easy");
    private static RadioButton hardBtn = new RadioButton("Hard");

    public static boolean getLevel(){
        if (hardBtn.isSelected()){
            return true;
        } else {
            return false;
        }
    }

    public static Parent createOptionLayout(){

        //create layout
        BorderPane root = new BorderPane();


        // sound & music
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


        // choose difficulty
        Label difficultyLabel = new Label("Difficulty");
        difficultyLabel.setTextFill(Color.rgb(245, 214, 157));
        difficultyLabel.setFont(new Font("Arial", 32));
        difficultyLabel.setStyle("-fx-background-color: #521a1a");
        difficultyLabel.setTranslateX(550);
        difficultyLabel.setTranslateY(50);

        ToggleGroup difficultyBtns = new ToggleGroup();

        easyBtn.setTextFill(Color.rgb(245, 214, 157));
        easyBtn.setFont(new Font("Arial", 24));
        easyBtn.setStyle("-fx-background-color: #521a1a");
        easyBtn.setToggleGroup(difficultyBtns);
        easyBtn.setSelected(true);

        hardBtn.setTextFill(Color.rgb(245, 214, 157));
        hardBtn.setFont(new Font("Arial", 24));
        hardBtn.setStyle("-fx-background-color: #521a1a");
        hardBtn.setToggleGroup(difficultyBtns);

        HBox difficultyOption = new HBox(75, easyBtn, hardBtn);
        difficultyOption.setPadding(new Insets(50, 0, 0,550));


        VBox center = new VBox(50, labels, sliders, difficultyLabel, difficultyOption);

        Button mainMenuBtn = new Button("Main Menu");
        mainMenuBtn.setId("MainMenuBtn");
        mainMenuBtn.setOnAction(e -> { MainMenuController.backToMainMenuFromPlay(e);
        });


        root.setCenter(center);
        root.setBottom(mainMenuBtn);
        return root;
    }
}
