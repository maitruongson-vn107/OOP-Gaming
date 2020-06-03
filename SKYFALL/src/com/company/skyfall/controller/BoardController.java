package com.company.skyfall.controller;

import com.company.skyfall.model.AirCraft;
import com.company.skyfall.model.Board;
import com.company.skyfall.view.ACToSet;
import com.company.skyfall.view.PlayLayout;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import javafx.scene.control.*;


import static com.company.skyfall.model.HighScoreHandler.*;

    public class BoardController {
    public static void enemyCellClick(MouseEvent event, boolean running,
                                      boolean overGame, Board enemyBoard,
                                      Board playerBoard, byte typeOfBullet, Label bullet2Label,
                                      Label bullet3Label, Button bullet1Btn, Button bullet2Btn, Button bullet3Btn) {
        if (!running)
            return;
        if (overGame) return;
        Board.Cell cell = (Board.Cell) event.getSource();
        if (enemyBoard.preCell.equals(cell)) return;
        enemyBoard.preCell = cell;
        PlayLayout.usedBullets++;
        //logging(cell); running,overGame,preCell,typeOfBullet
        PlayLayout.logging(cell);
        //choose type of bullet
        while (true) {
            if (typeOfBullet == 1) {
                PlayLayout.enemyTurn = !cell.shootType1();
                break;
            }
            if (typeOfBullet == 2) {
                PlayLayout.enemyTurn = !cell.shootType2();
                playerBoard.setNumBulletType2(playerBoard.getNumBulletType2() - 1);
                bullet2Label.setText("x" + String.valueOf(playerBoard.getNumBulletType2()));
                //set bullet type to 1 as default
                try {
                    bullet1Btn.setPrefSize(225, 100);
                    bullet2Btn.setPrefSize(150, 66.7);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                PlayLayout.typeOfBullet = 1;
                break;
            }
            if (typeOfBullet == 3) {
                PlayLayout.enemyTurn = !cell.shootType3();
                playerBoard.setNumBulletType3(playerBoard.getNumBulletType3() - 1);
                bullet3Label.setText("x" + String.valueOf(playerBoard.getNumBulletType3()));
                //set bullet type to 1 as default
                try {
                    bullet1Btn.setPrefSize(225, 100);
                    bullet3Btn.setPrefSize(150, 66.7);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                PlayLayout.typeOfBullet = 1;
                break;
            }
        }

        if (enemyBoard.getAirCrafts() == 0) {
            Alert winAlert = new Alert(Alert.AlertType.INFORMATION);
            winAlert.setTitle("You win");
            winAlert.setHeaderText("Congrats!");
            winAlert.setContentText("YOU WIN!");
            winAlert.showAndWait();
            TextField nameField;
            overGame = true;
            try {
                // if player got high score
                // make a dialog enter player's name
                if (isTop(PlayLayout.usedBullets, PlayLayout.time, PlayLayout.easyMode)) {
                    TextInputDialog dialog = new TextInputDialog();
                    dialog.setTitle("Enter your name");
                    dialog.setHeaderText("You got a high score\nPlease enter your name:");
                    dialog.setContentText("Your name:");
                    dialog.showAndWait();
                    nameField = dialog.getEditor();
                    if (!PlayLayout.easyMode) {
                        writeHighScoreHard(nameField.getText(), PlayLayout.usedBullets, PlayLayout.time);
                    } else {
                        writeHighScoreEasy(nameField.getText(), PlayLayout.usedBullets, PlayLayout.time);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!overGame && PlayLayout.enemyTurn) {
            PlayLayout.centerStack.getChildren().add(PlayLayout.etlb);
            enemyBoard.setDisable(true);
            playerBoard.setDisable(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(ex -> {
                enemyBoard.setDisable(false);
                playerBoard.setDisable(false);
                PlayLayout.centerStack.getChildren().remove(1);
                if (PlayLayout.enemyTurn && PlayLayout.easyMode)
                    PlayLayout.enemyMoveEasy();
                else if (PlayLayout.enemyTurn && !PlayLayout.easyMode) PlayLayout.enemyMoveHard();

            });
            pause.play();
        }
    }

    public static void clickSetUp(MouseEvent event, boolean running, Board enemyBorad, Board playerBoard) {
        if (running)
            return;
        PlayLayout.airCraftsToPlace = 4;
        Board.Cell cell = (Board.Cell) event.getSource();
        while (PlayLayout.acSet[PlayLayout.airCraftsToPlace - 2] && PlayLayout.airCraftsToPlace >= 2)
            PlayLayout.airCraftsToPlace--;
        if (playerBoard.setAirCraft(new AirCraft(PlayLayout.airCraftsToPlace, event.getButton() == MouseButton.PRIMARY), cell.x, cell.y)) {
            PlayLayout.AC[PlayLayout.airCraftsToPlace - 2] = cell.getAirCraft();
            switch (PlayLayout.airCraftsToPlace) {
                case 4:
                    PlayLayout.acHBox.getChildren().removeAll(ACToSet.v4);
                    PlayLayout.acVBox.getChildren().removeAll(ACToSet.h4);
                    break;
                case 3:
                    PlayLayout.acHBox.getChildren().removeAll(ACToSet.v3);
                    PlayLayout.acVBox.getChildren().removeAll(ACToSet.h3);
                    break;
                case 2:
                    PlayLayout.acHBox.getChildren().removeAll(ACToSet.v2);
                    PlayLayout.acVBox.getChildren().removeAll(ACToSet.h2);
                    break;

            }
            PlayLayout.acSet[PlayLayout.airCraftsToPlace - 2] = true;
            boolean start = true;
            for (int k = 0; k <= 2; k++) {
                if (!PlayLayout.acSet[k]) start = false;
            }
            if (start) {
                PlayLayout.startGame();
            }

        }
    }
}