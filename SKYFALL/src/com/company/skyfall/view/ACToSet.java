package com.company.skyfall.view;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

    public class ACToSet {
        public static Parent creat(){
            HBox acVbox = new HBox();
            VBox hACVbox = new VBox();
            HBox vACHbox = new HBox();
            ImageView v2 = new ImageView(new Image("com/company/skyfall/view/aircraft/Player AC/Player AC alive/2/v2 full.png"));
            ImageView h2 = new ImageView(new Image("com/company/skyfall/view/aircraft/Player AC/Player AC alive/2/h2 full.png"));
            ImageView v3 = new ImageView(new Image("com/company/skyfall/view/aircraft/Player AC/Player AC alive/3/v3 full.png"));
            ImageView h3 = new ImageView(new Image("com/company/skyfall/view/aircraft/Player AC/Player AC alive/3/h3 full.png"));
            ImageView v4 = new ImageView(new Image("com/company/skyfall/view/aircraft/Player AC/Player AC alive/4/v4 full.png"));
            ImageView h4 = new ImageView(new Image("com/company/skyfall/view/aircraft/Player AC/Player AC alive/4/h4 full.png"));
            v2.setFitHeight(v2.getFitHeight()/2);
            h2.setFitHeight(h2.getFitHeight()/2);
            v3.setFitHeight(v3.getFitHeight()/2);
            h3.setFitHeight(h3.getFitHeight()/2);
            h4.setFitHeight(h4.getFitHeight()/2);
            v4.setFitHeight(v4.getFitHeight()/2);
//            v2.setPreserveRatio(true);
//            h2.setPreserveRatio(true);
//            v3.setPreserveRatio(true);
//            h3.setPreserveRatio(true);
//            h4.setPreserveRatio(true);
//            v4.setPreserveRatio(true);
            hACVbox.getChildren().addAll(h2,h3,h4);
            vACHbox.getChildren().addAll(v2,v3,v4);
            acVbox.getChildren().addAll(hACVbox,vACHbox);
            return  acVbox;
        }
    }