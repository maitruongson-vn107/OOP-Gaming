package com.company.skyfall.view;


import com.company.skyfall.Main;
import com.company.skyfall.controller.MainMenuController;
import com.company.skyfall.model.Board;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

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

    public static Parent createOptionLayout() throws FileNotFoundException {

        //create layout
        BorderPane root = new BorderPane();


        // sound & music
        Label soundLabel = new Label("Sound");

        Label musicLabel = new Label("Music");

        HBox labels = new HBox( soundLabel, musicLabel);

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


      HBox sliders = new HBox( soundSlider, musicSlider);

        // choose difficulty

        Label difficultyLabel = new Label("Difficulty");
        HBox hbox = new HBox(difficultyLabel);

        ToggleGroup difficultyBtns = new ToggleGroup();


        easyBtn.setToggleGroup(difficultyBtns);
        easyBtn.setSelected(true);

        hardBtn.setToggleGroup(difficultyBtns);

        HBox difficultyOption = new HBox(easyBtn, hardBtn);

        VBox center = new VBox( labels, sliders, hbox, difficultyOption);

        Button mainMenuBtn = new Button("Main Menu");

        mainMenuBtn.setOnAction(e -> { MainMenuController.backToMainMenuFromPlay(e);
        });

        root.setCenter(center);
        HBox hBox = new HBox(mainMenuBtn);
        hBox.setAlignment(Pos.BOTTOM_LEFT);
        root.setBottom(hBox);

        root.getStyleClass().setAll("OptionLayout");
        mainMenuBtn.setId("mainMenuBtn");
        difficultyOption.getStyleClass().add("Slider_DifficultyOption");
        easyBtn.getStyleClass().add("TextLabel");
        easyBtn.setId("levelBtn");
        hardBtn.getStyleClass().add("TextLabel");
        hardBtn.setId("levelBtn");
        difficultyLabel.getStyleClass().add("TextLabel");
        hbox.getStyleClass().add("Hbox");
        sliders.getStyleClass().add("Slider_DifficultyOption");
        musicLabel.getStyleClass().add("TextLabel");
        soundLabel.getStyleClass().add("TextLabel");
        labels.getStyleClass().add("Labels");
        center.getStyleClass().add("Center");
        root.setPrefSize(1080,630);
        //stage.getIcons().add(new Image(new File("src/com/company/skyfall/resources/images/game_icon.png").toURI().toString()));

        Image img = new Image(new File("src/resources/images/PlayBackgr.jpg").toURI().toString());
        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO,
                false, false, true, false);
        root.setBackground(new Background(new BackgroundImage(img,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize)));
        return root;
    }
}
