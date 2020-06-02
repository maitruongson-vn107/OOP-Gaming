package com.company.skyfall.controller;


import com.company.skyfall.view.PlayLayout;
import javafx.event.ActionEvent;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BulletController {
    public static int chosenBullet = 1;
    public static void enlargeBulletButton1(ActionEvent event,byte typeOfBullet) {
        try {
            VBox bulletBox =(VBox) ((Button) event.getSource()).getParent();
            Button bulletButton1 = (Button) bulletBox.getChildren().get(0);
            Button bulletButton2 = (Button) ((HBox) bulletBox.getChildren().get(1)).getChildren().get(0);
            Button bulletButton3 = (Button) ((HBox) bulletBox.getChildren().get(2)).getChildren().get(0);
            if (typeOfBullet == 2) {
                bulletButton2.setPrefSize(150, 66.7);
            }
            if (typeOfBullet == 3) {
                bulletButton3.setPrefSize(150, 66.7);
            }
            if (typeOfBullet != 1) {
                bulletButton1.setPrefSize(225, 100);
            }
            PlayLayout.typeOfBullet = 1;
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public static void enlargeBulletButton2(ActionEvent event,byte typeOfBullet,byte numBulletType2) {
        try{
            VBox bulletBox =(VBox) ((HBox) ((Button) event.getSource()).getParent()).getParent();
            Button bulletButton1 = (Button) bulletBox.getChildren().get(0);
            Button bulletButton2 = (Button) ((HBox) bulletBox.getChildren().get(1)).getChildren().get(0);
            Button bulletButton3 = (Button) ((HBox) bulletBox.getChildren().get(2)).getChildren().get(0);
            if (numBulletType2 == 0) return;
            if (typeOfBullet == 1) {
                bulletButton1.setPrefSize(150, 66.7);
            }
            if (typeOfBullet == 3) {
                bulletButton3.setPrefSize(150, 66.7);
            }
            if (typeOfBullet != 2) {
                bulletButton2.setPrefSize(225, 100);
            }
            PlayLayout.typeOfBullet = 2;
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public static void enlargeBulletButton3(ActionEvent event,byte typeOfBullet,byte numBulletType3) {
        try{
            VBox bulletBox =(VBox) ((HBox) ((Button) event.getSource()).getParent()).getParent();
            Button bulletButton1 = (Button) bulletBox.getChildren().get(0);
            Button bulletButton2 = (Button) ((HBox) bulletBox.getChildren().get(1)).getChildren().get(0);
            Button bulletButton3 = (Button) ((HBox) bulletBox.getChildren().get(2)).getChildren().get(0);
            if (numBulletType3 == 0) return;
            if (typeOfBullet == 1) {
                bulletButton1.setPrefSize(150, 66.7);
            }
            if (typeOfBullet == 2) {
                bulletButton2.setPrefSize(150, 66.7);
            }
            if (typeOfBullet != 3) {
                bulletButton3.setPrefSize(225, 100);
            }
            PlayLayout.typeOfBullet = 3;
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

}
