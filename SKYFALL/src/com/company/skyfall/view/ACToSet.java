package com.company.skyfall.view;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

    public class ACToSet {
        public static ImageView v2 = new ImageView(new Image("com/company/skyfall/view/aircraft/Player AC/Player AC alive/2/v2 full.png"));
        public static ImageView h2 = new ImageView(new Image("com/company/skyfall/view/aircraft/Player AC/Player AC alive/2/h2 full.png"));
        public static ImageView v3 = new ImageView(new Image("com/company/skyfall/view/aircraft/Player AC/Player AC alive/3/v3 full.png"));
        public static ImageView h3 = new ImageView(new Image("com/company/skyfall/view/aircraft/Player AC/Player AC alive/3/h3 full.png"));
        public static ImageView v4 = new ImageView(new Image("com/company/skyfall/view/aircraft/Player AC/Player AC alive/4/v4 full.png"));
        public static ImageView h4 = new ImageView(new Image("com/company/skyfall/view/aircraft/Player AC/Player AC alive/4/h4 full.png"));
        public static Parent acBox(){
            HBox acVbox = new HBox(100);
            VBox hACVbox = new VBox(15);
            HBox vACHbox = new HBox(30);

            v2.setFitHeight(60);
            h2.setFitWidth(60);
            v3.setFitHeight(90);
            h3.setFitWidth(90);
            h4.setFitWidth(120);
            v4.setFitHeight(120);
            v2.setPreserveRatio(true);
            h2.setPreserveRatio(true);
            v3.setPreserveRatio(true);
            h3.setPreserveRatio(true);
            h4.setPreserveRatio(true);
            v4.setPreserveRatio(true);
            hACVbox.getChildren().addAll(h2,h3,h4);
            vACHbox.getChildren().addAll(v2,v3,v4);
            acVbox.getChildren().addAll(hACVbox,vACHbox);
            return  acVbox;
        }
    }