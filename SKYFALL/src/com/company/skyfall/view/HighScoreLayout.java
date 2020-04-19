package com.company.skyfall.view;


import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.FileInputStream;

import static com.company.skyfall.model.HighScoreHandler.easyReader;
import static com.company.skyfall.model.HighScoreHandler.hardReader;
public class HighScoreLayout {
    public static Parent createHighScoreLayout()throws Exception{
        String[][] easyTop;
        String[][] hardTop;
        easyTop = easyReader();
        hardTop = hardReader();
        StackPane root = new StackPane();
        VBox hsvbox =new VBox(100);
        hsvbox.setAlignment(Pos.TOP_CENTER);
        root.getChildren().add(hsvbox);

        LLabel hslabel = new LLabel();
        hslabel.setText("HIGH SCORE");
        hslabel.setFont(Font.font(100));
        HBox e_h_hbox = new HBox(300);
        Button mainMenuBtn = new Button("Main Menu");

        FileInputStream btnInput = new FileInputStream("src/com/company/skyfall/view/BackToMainMenuButtonBackgr.png"  );
        Image btnBackgrImage = new Image(btnInput);
        BackgroundSize btnBackgrSize = new BackgroundSize(200,100,false,false,false,false);
        BackgroundImage btnBackgr = new BackgroundImage(btnBackgrImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                btnBackgrSize);
        mainMenuBtn.setPrefSize(225,100);
        mainMenuBtn.setBackground(new Background(btnBackgr));
        mainMenuBtn.setFont(Font.font(25));
        mainMenuBtn.setTextFill(Color.rgb(245,214,157));

        mainMenuBtn.setOnAction(e -> {
            try {
                com.company.skyfall.controller.Controller.backToMainMenuFromPlay(e);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        AnchorPane btnpane = new AnchorPane();
        btnpane.getChildren().add(mainMenuBtn);
        hsvbox.getChildren().addAll(hslabel,e_h_hbox,btnpane);
        e_h_hbox.setAlignment(Pos.CENTER);
        VBox easyvbox = new VBox();
        easyvbox.setAlignment(Pos.CENTER);
        VBox hardvbox = new VBox();
        hardvbox.setAlignment(Pos.CENTER);
        e_h_hbox.getChildren().addAll(easyvbox,hardvbox);

        LLabel ealabel = new LLabel();
        ealabel.setText("Easy");
        ealabel.setFont(Font.font(50));
        HBox eainfhbox = new HBox(50);
        eainfhbox.setAlignment(Pos.CENTER);
        easyvbox.getChildren().addAll(ealabel,eainfhbox);
        VBox eanamevbox = new VBox();
        eanamevbox.setAlignment(Pos.BASELINE_LEFT);
        VBox eaturnvbox = new VBox();
        eaturnvbox.setAlignment(Pos.BASELINE_CENTER);
        VBox eatimevbox = new VBox();
        eatimevbox.setAlignment(Pos.BASELINE_CENTER);
        eainfhbox.getChildren().addAll(eanamevbox,eaturnvbox,eatimevbox);

        LLabel name1 = new LLabel();
        name1.setText("Name");
        LLabel name11 = new LLabel(easyTop[0][0]);
        LLabel name12 = new LLabel(easyTop[1][0]);
        LLabel name13 = new LLabel(easyTop[2][0]);
        LLabel name14 = new LLabel(easyTop[3][0]);
        LLabel name15 = new LLabel(easyTop[4][0]);
        eanamevbox.getChildren().addAll(name1,name11,name12,name13,name14,name15);

        LLabel turn1 = new LLabel();
        turn1.setText("Turns");
        LLabel turn11 = new LLabel(easyTop[0][1]);
        LLabel turn12 = new LLabel(easyTop[1][1]);
        LLabel turn13 = new LLabel(easyTop[2][1]);
        LLabel turn14 = new LLabel(easyTop[3][1]);
        LLabel turn15 = new LLabel(easyTop[4][1]);
        eaturnvbox.getChildren().addAll(turn1,turn11,turn12,turn13,turn14,turn15);

        LLabel time1 = new LLabel();
        time1.setText("Time");
        LLabel time11 = new LLabel((Integer.parseInt(easyTop[0][2])/60<10?"0":"") + String.valueOf(Integer.parseInt(easyTop[0][2])/60) +":" +(Integer.parseInt(easyTop[0][2])%60<10?"0":"") + String.valueOf(Integer.parseInt(easyTop[0][2])%60));
        LLabel time12 = new LLabel((Integer.parseInt(easyTop[1][2])/60<10?"0":"") + String.valueOf(Integer.parseInt(easyTop[1][2])/60) +":" +(Integer.parseInt(easyTop[1][2])%60<10?"0":"") + String.valueOf(Integer.parseInt(easyTop[1][2])%60));
        LLabel time13 = new LLabel((Integer.parseInt(easyTop[2][2])/60<10?"0":"") + String.valueOf(Integer.parseInt(easyTop[2][2])/60) +":" +(Integer.parseInt(easyTop[2][2])%60<10?"0":"") + String.valueOf(Integer.parseInt(easyTop[2][2])%60));
        LLabel time14 = new LLabel((Integer.parseInt(easyTop[3][2])/60<10?"0":"") + String.valueOf(Integer.parseInt(easyTop[3][2])/60) +":" +(Integer.parseInt(easyTop[3][2])%60<10?"0":"") + String.valueOf(Integer.parseInt(easyTop[3][2])%60));
        LLabel time15 = new LLabel((Integer.parseInt(easyTop[4][2])/60<10?"0":"") + String.valueOf(Integer.parseInt(easyTop[4][2])/60) +":" +(Integer.parseInt(easyTop[4][2])%60<10?"0":"") + String.valueOf(Integer.parseInt(easyTop[4][2])%60));
        eatimevbox.getChildren().addAll(time1,time11,time12,time13,time14,time15);

        LLabel halabel = new LLabel();
        halabel.setText("Hard");
        halabel.setFont(Font.font(50));
        HBox hainfhbox = new HBox(50);
        hainfhbox.setAlignment(Pos.CENTER);
        hardvbox.getChildren().addAll(halabel,hainfhbox);
        VBox hanamevbox = new VBox();
        hanamevbox.setAlignment(Pos.BASELINE_LEFT);
        VBox haturnvbox = new VBox();
        haturnvbox.setAlignment(Pos.BASELINE_CENTER);
        VBox hatimevbox = new VBox();
        hatimevbox.setAlignment(Pos.BASELINE_CENTER);
        hainfhbox.getChildren().addAll(hanamevbox,haturnvbox,hatimevbox);

        LLabel name2 = new LLabel();
        name2.setText("Name");
        LLabel name21 = new LLabel(hardTop[0][0]);
        LLabel name22 = new LLabel(hardTop[1][0]);
        LLabel name23 = new LLabel(hardTop[2][0]);
        LLabel name24 = new LLabel(hardTop[3][0]);
        LLabel name25 = new LLabel(hardTop[4][0]);
        hanamevbox.getChildren().addAll(name2,name21,name22,name23,name24,name25);

        LLabel turn2 = new LLabel();
        turn2.setText("Turns");
        LLabel turn21 = new LLabel(hardTop[0][1]);
        LLabel turn22 = new LLabel(hardTop[1][1]);
        LLabel turn23 = new LLabel(hardTop[2][1]);
        LLabel turn24 = new LLabel(hardTop[3][1]);
        LLabel turn25 = new LLabel(hardTop[4][1]);
        haturnvbox.getChildren().addAll(turn2,turn21,turn22,turn23,turn24,turn25);

        LLabel time2 = new LLabel();
        time2.setText("Time");
        LLabel time21 = new LLabel((Integer.parseInt(hardTop[0][2])/60<10?"0":"") + String.valueOf(Integer.parseInt(hardTop[0][2])/60) +":" +(Integer.parseInt(hardTop[0][2])%60<10?"0":"") + String.valueOf(Integer.parseInt(hardTop[0][2])%60)) ;
        LLabel time22 = new LLabel((Integer.parseInt(hardTop[1][2])/60<10?"0":"") + String.valueOf(Integer.parseInt(hardTop[1][2])/60) +":" +(Integer.parseInt(hardTop[1][2])%60<10?"0":"") + String.valueOf(Integer.parseInt(hardTop[1][2])%60));
        LLabel time23 = new LLabel((Integer.parseInt(hardTop[2][2])/60<10?"0":"") + String.valueOf(Integer.parseInt(hardTop[2][2])/60) +":" +(Integer.parseInt(hardTop[2][2])%60<10?"0":"") + String.valueOf(Integer.parseInt(hardTop[2][2])%60));
        LLabel time24 = new LLabel((Integer.parseInt(hardTop[3][2])/60<10?"0":"") + String.valueOf(Integer.parseInt(hardTop[3][2])/60) +":" +(Integer.parseInt(hardTop[3][2])%60<10?"0":"") + String.valueOf(Integer.parseInt(hardTop[3][2])%60));
        LLabel time25 = new LLabel((Integer.parseInt(hardTop[4][2])/60<10?"0":"") + String.valueOf(Integer.parseInt(hardTop[4][2])/60) +":" +(Integer.parseInt(hardTop[4][2])%60<10?"0":"") + String.valueOf(Integer.parseInt(hardTop[4][2])%60));
        hatimevbox.getChildren().addAll(time2,time21,time22,time23,time24,time25);

        FileInputStream playBackgrInput = new FileInputStream("src/com/company/skyfall/view/HighScoreBackgr.jpg"  );
        Image playBackgrImage = new Image(playBackgrInput);
        BackgroundSize playBackgrSize = new BackgroundSize(1280,720,true,true,true,true);
        BackgroundImage playBackgr = new BackgroundImage(playBackgrImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                playBackgrSize);
        root.setBackground(new Background(playBackgr));
        return root;
    }
}