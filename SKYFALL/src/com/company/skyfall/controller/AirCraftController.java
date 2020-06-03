package com.company.skyfall.controller;

import com.company.skyfall.model.AirCraft;
import com.company.skyfall.model.Board;
import com.company.skyfall.view.ACToSet;
import com.company.skyfall.view.OptionLayout;
import com.company.skyfall.view.PlayLayout;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.paint.Color;

public class AirCraftController {
    public static EventHandler onDragDetected = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            Dragboard db;
            ClipboardContent ct = new ClipboardContent();
            try {
                Board.Cell c = (Board.Cell) (event.getSource());
                Board playerBoard = c.getBoard();
                if (!PlayLayout.running) return;
                db = c.startDragAndDrop(TransferMode.ANY);
                if (!playerBoard.didRepo && c.airCraft != null && c.airCraft.getHP() < c.airCraft.getType() * 100 && c.airCraft.getHP() > 0) {
                    int gap = (c.airCraft.isVertical()) ? (c.y - c.airCraft.getHead().y) : (c.x - c.airCraft.getHead().x);
                    ct.putString(String.valueOf(gap) + "00");
                    playerBoard.acToMove = c.airCraft;
                    db.setContent(ct);
                    event.consume();
                }
            } catch (Exception ex) {
                if (PlayLayout.running) return;
                ImageView imgv = (ImageView) (event.getSource());
                db = imgv.startDragAndDrop(TransferMode.ANY);
                String dragInfor = "";
                if (imgv == ACToSet.v2) dragInfor = String.valueOf((int) (event.getSceneY() - 648) / 30) + "2" + "1";
                if (imgv == ACToSet.h2) dragInfor = String.valueOf((int) (event.getSceneX() - 525) / 30) + "2" + "0";
                if (imgv == ACToSet.v3) dragInfor = String.valueOf((int) (event.getSceneY() - 648) / 30) + "3" + "1";
                if (imgv == ACToSet.h3) dragInfor = String.valueOf((int) (event.getSceneX() - 525) / 30) + "3" + "0";
                if (imgv == ACToSet.v4) dragInfor = String.valueOf((int) (event.getSceneY() - 648) / 30) + "4" + "1";
                if (imgv == ACToSet.h4) dragInfor = String.valueOf((int) (event.getSceneX() - 525) / 30) + "4" + "0";
                ct.putString(dragInfor);
                db.setContent(ct);
                event.consume();
            }
        }
    };
    public static EventHandler onDragOver = new EventHandler<DragEvent>() {
        @Override
        public void handle(DragEvent event) {
            Board.Cell c = (Board.Cell) event.getSource();
            Board playerBoard = c.getBoard();
            event.acceptTransferModes(TransferMode.ANY);
            String dragInfor = event.getDragboard().getString();
            if (dragInfor.charAt(1) == '0') {
                if (playerBoard.acToMove != null) {
                    boolean ver = playerBoard.acToMove.isVertical();
                    int gap = Integer.parseInt(String.valueOf(dragInfor.charAt(0)));
                    for (int k = 0; k <= 2; k++) {
                        if (PlayLayout.AC[k] != null && PlayLayout.AC[k] != playerBoard.acToMove)
                            try {
                                if (PlayLayout.AC[k].getHP() > 0) playerBoard.changeImagePlayerAlive(PlayLayout.AC[k].getHead());
                                else playerBoard.changeImagePlayerDead(PlayLayout.AC[k].getHead());
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                    }
                    for (int k = 0; k < 10; k++)
                        for (int h = 0; h < 10; h++)
                            if (playerBoard.getCell(k, h).getAirCraft() == null || playerBoard.getCell(k, h).getAirCraft() == playerBoard.acToMove) {
                                playerBoard.getCell(k, h).setFill(Color.TRANSPARENT);
                                playerBoard.getCell(k, h).wasShot = false;
                            }
                    if (ver)
                        try {
                            playerBoard.changeImagePlayerAlive(c.x, c.y - gap, true, playerBoard.acToMove.getType());
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    else {
                        try {
                            playerBoard.changeImagePlayerAlive(c.x - gap, c.y, false, playerBoard.acToMove.getType());
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            } else {
                boolean ver = (dragInfor.charAt(2) == '1');
                int gap = Integer.parseInt(String.valueOf(dragInfor.charAt(0)));
                int type = Integer.parseInt(String.valueOf(dragInfor.charAt(1)));
                for (int k = 0; k <= 2; k++) {
                    if (PlayLayout.AC[k] != null && PlayLayout.AC[k] != playerBoard.acToMove)
                        try {
                            playerBoard.changeImagePlayerAlive(PlayLayout.AC[k].getHead());
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                }
                for (int k = 0; k < 10; k++)
                    for (int h = 0; h < 10; h++)
                        if (playerBoard.getCell(k, h).getAirCraft() == null) {
                            playerBoard.getCell(k, h).setFill(Color.TRANSPARENT);
                            playerBoard.getCell(k, h).wasShot = false;
                        }
                if (ver)
                    try {
                        playerBoard.changeImagePlayerAlive(c.x, c.y - gap, true, type);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                else {
                    try {
                        playerBoard.changeImagePlayerAlive(c.x - gap, c.y, false, type);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

            }
            event.consume();
        }
    };
    public static EventHandler onDragDropped = new EventHandler<DragEvent>() {
        @Override
        public void handle(DragEvent event) {
            Board.Cell c = (Board.Cell) event.getSource();
            Board playerBoard = c.getBoard();
            boolean suc = false;
            boolean ver = true;
            int gap;
            String dragInfor = event.getDragboard().getString();
            if (dragInfor.charAt(1) == '0') {
                if (playerBoard.acToMove != null) {
                    ver = playerBoard.acToMove.isVertical();
                    gap = Integer.parseInt(String.valueOf(dragInfor.charAt(0)));
                    if (ver)
                        suc = playerBoard.reposAirCraft(playerBoard.acToMove, c.x, c.y - gap);
                    else suc = playerBoard.reposAirCraft(playerBoard.acToMove, c.x - gap, c.y);
                }
                event.setDropCompleted(true);
                event.consume();
                if (!suc) {
                    for (int k = 0; k <= 2; k++) {
                        try {
                            if (PlayLayout.AC[k].getHP() > 0) playerBoard.changeImagePlayerAlive(PlayLayout.AC[k].getHead());
                            else playerBoard.changeImagePlayerDead(PlayLayout.AC[k].getHead());
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                }
                if (suc) {
                    PlayLayout.enemyTurn = true;
                    if (OptionLayout.getLevel()) {
                        PlayLayout.enemyMoveEasy();
                    } else {
                        PlayLayout.enemyMoveHard();
                    }
                }

                for (int k = 0; k < 10; k++)
                    for (int h = 0; h < 10; h++)
                        if (playerBoard.getCell(k, h).getAirCraft() == null) {
                            playerBoard.getCell(k, h).setFill(Color.TRANSPARENT);
                            playerBoard.getCell(k, h).wasShot = false;
                        }

            } else {
                gap = Integer.parseInt(String.valueOf(dragInfor.charAt(0)));
                ver = (dragInfor.charAt(2) == '1');
                int type = Integer.parseInt(String.valueOf(dragInfor.charAt(1)));
                AirCraft aircr = new AirCraft(type, ver);
                if (ver) {
                    if (playerBoard.isOkToSetAirCraft(aircr, c.x, c.y - gap)) {
                        playerBoard.setAirCraft(aircr, c.x, c.y - gap);
                        PlayLayout.AC[type - 2] = playerBoard.getCell(c.x, c.y - gap).getAirCraft();
                        PlayLayout.acSet[type - 2] = true;
                        switch (type) {
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
                    }
                } else {
                    if (playerBoard.isOkToSetAirCraft(aircr, c.x - gap, c.y)) {
                        playerBoard.setAirCraft(aircr, c.x - gap, c.y);
                        PlayLayout.AC[type - 2] = aircr;
                        PlayLayout.acSet[type - 2] = true;
                        switch (type) {
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
                    }
                }
                for (int k = 0; k <= 2; k++) {
                    if (PlayLayout.AC[k] != null)
                        try {
                            playerBoard.changeImagePlayerAlive(PlayLayout.AC[k].getHead());
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                }
                for (int k = 0; k < 10; k++)
                    for (int h = 0; h < 10; h++)
                        if (playerBoard.getCell(k, h).getAirCraft() == null) {
                            playerBoard.getCell(k, h).setFill(Color.TRANSPARENT);
                            playerBoard.getCell(k, h).wasShot = false;
                        }
                boolean start = true;
                for (int k = 0; k <= 2; k++) {
                    if (!PlayLayout.acSet[k]) start = false;
                }
                if (start) PlayLayout.startGame();
                event.setDropCompleted(true);
                event.consume();
            }
        }
    };
    public static void dragEffect(ImageView ...list){
        for(ImageView i:list){
            i.setOnDragDetected(onDragDetected);
        }
    }
}