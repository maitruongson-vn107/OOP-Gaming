package com.company.skyfall.view;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ACHPBox {
    public static ImageView h2 = ACToSet.h2;
    public static ImageView h3 = ACToSet.h3;
    public static ImageView h4 = ACToSet.h4;
    public static Label     hp2 = new Label();
    public static Label     hp3 = new Label();
    public static Label     hp4 = new Label();
    public static Parent createHPBox(){
        hp2.setText("     200 HP");
        hp3.setText("     300 HP");
        hp4.setText("     400 HP");
        h2.setFitWidth(60);
        h3.setFitWidth(90);
        h4.setFitWidth(120);
        h2.setPreserveRatio(true);
        h3.setPreserveRatio(true);
        h4.setPreserveRatio(true);
        hp2.setTextFill(Color.YELLOW);  hp2.setFont(Font.font(25));
        hp3.setTextFill(Color.YELLOW);  hp3.setFont(Font.font(25));
        hp4.setTextFill(Color.YELLOW);  hp4.setFont(Font.font(25));
        VBox vBox2 = new VBox(5,hp2,hp3,hp4);
        VBox vBox1 = new VBox(15,h2,h3,h4);
        vBox1.setAlignment(Pos.BASELINE_RIGHT);
        vBox2.setAlignment(Pos.CENTER_RIGHT);
        HBox hBox = new HBox(vBox1,vBox2);
        return hBox;
    }
}