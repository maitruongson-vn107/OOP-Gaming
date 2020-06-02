package com.company.skyfall.view;

import com.company.skyfall.Main;
import com.company.skyfall.controller.BoardController;
import com.company.skyfall.controller.BulletController;
import com.company.skyfall.controller.MainMenuController;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import com.company.skyfall.model.AirCraft;
import com.company.skyfall.model.Board;
import com.company.skyfall.model.Board.Cell;
import com.company.skyfall.model.LogList;
import com.company.skyfall.model.PlayLog;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;


import java.io.FileInputStream;
import java.util.Random;

import static com.company.skyfall.model.HighScoreHandler.*;


public class PlayLayout {

    public static boolean running = false;
    private static Board enemyBoard;
    private static Board playerBoard;
    public static int airCraftsToPlace = 4;
    public static boolean enemyTurn = false;
    private static Random random = new Random();
    public static int time = 0;
    public static boolean easyMode = false;
    public static int usedBullets = 0;
    private static boolean overGame = false;
    private static Text timeText = new Text("");
    public static byte typeOfBullet = 1;
    public static StackPane centerStack = new StackPane();
    public static HBox boards = new HBox();
    public static Label ytlb = new Label();
    public static Label etlb = new Label();
    public static Label stlb = new Label();
    public static boolean[] acSet = {false, false, false};
    public static AirCraft[] AC = new AirCraft[3];
    public static StackPane acBox = (StackPane) ACToSet.acBox();
    public static VBox acVBox = (VBox) ((HBox) acBox.getChildren().get(1)).getChildren().get(0);
    public static HBox acHBox = (HBox) ((HBox) acBox.getChildren().get(1)).getChildren().get(1);
    private static HBox btmHbox;
    public static StackPane[] acHP = new StackPane[3];
    static LogList logList;
    static int turn;
    static VBox plBox;


    public static void logging(Cell cell) {
        PlayLog playLog = new PlayLog(cell, typeOfBullet, turn, "Player");
        logList.add(playLog);
        Label pl = new Label(playLog.getTurn() + "\t\t" + playLog.getPlayer() + "\t\t\t " + playLog.convertCellName(cell) + "\t\t\t" + typeOfBullet + "\t\t\t\t" + playLog.getDamage() + "\t\t" + playLog.status());
        plBox.getChildren().add(pl);
    }

    //Make time counter appearing in root.top
    private static Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
        String min = (time / 60 < 10 ? "0" : "") + String.valueOf(time / 60);
        String sec = (time % 60 < 10 ? "0" : "") + String.valueOf(time % 60);
        timeText.setText(min + ":" + sec);
        timeText.setFont(Font.font(25));
        timeText.setFill(Color.YELLOW);
        time++;
    }));

    public static Parent createContent(boolean level) throws Exception {
        // set up status variables to default value
        for (int i = 0; i < 3; i++)
            acSet[i] = false;
        centerStack = new StackPane();
        boards = new HBox();
        ytlb = new Label();
        stlb = new Label();
        etlb = new Label();
        typeOfBullet = 1;
        turn = 1;
        overGame = false;
        running = false;
        airCraftsToPlace = 4;
        time = 0;
        usedBullets = 0;
        timeText.setText("");
        easyMode = level;
        logList = new LogList();
        for (int i = 0; i <= 2; i++) {
            acHP[i] = (StackPane) ACHPBox.createHPBox(i + 2);
        }
        plBox = new VBox();
        Label pl = new Label("Turn" + "\t\t" + "Player/Enemy" + "\t\t" + "Cell" + "\t\t" + "Bullet Type" + "\t\t" + "Damage" + "\t\t" + "Status" + "\n-------------------------");
        plBox.getChildren().add(pl);

        //sound and music in game
        Button musicBtn = new Button("Music: On");
        musicBtn.setOnAction(event -> {
            if (Main.musicPlayer.getVolume() == 0) {
                Main.musicPlayer.setVolume(1);
                musicBtn.setText("Music: On");
            } else {
                Main.musicPlayer.setVolume(0);
                musicBtn.setText("Music: Off");
            }
        });
        musicBtn.setStyle("-fx-background-color: #ffe957");
        Button soundBtn = new Button("Sound: On");
        soundBtn.setOnAction(event -> {
            if (Board.soundLevel == 0) {
                Board.soundLevel = 1;
                soundBtn.setText("Sound: On");
            } else {
                Board.soundLevel = 0;
                soundBtn.setText("Sound: Off");
            }
        });
        soundBtn.setStyle("-fx-background-color: #ffe957");
        HBox musicAndSound = new HBox(musicBtn, soundBtn);


        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefWidth(300);
        Label txt = new Label("Turn\t\t" + "Player/Enemy");
        scrollPane.setContent(txt);
        scrollPane.setContent(plBox);
        plBox.setStyle("-fx-background-color:transparent;");
        scrollPane.setStyle("-fx-background-color:transparent;");
        AC[0] = AC[1] = AC[2] = null;
        acVBox.getChildren().clear();
        acHBox.getChildren().clear();
        acVBox.getChildren().addAll(ACToSet.h2, ACToSet.h3, ACToSet.h4);
        acHBox.getChildren().addAll(ACToSet.v2, ACToSet.v3, ACToSet.v4);

        VBox rightPane = new VBox(musicAndSound, scrollPane);
        rightPane.setSpacing(50);

        BorderPane root = new BorderPane();
        VBox bulletBox = new VBox(50);
        bulletBox.setAlignment(Pos.CENTER_LEFT);
        bulletBox.setPrefWidth(333);
        bulletBox.setPrefHeight(518);
        bulletBox.setPadding(new Insets(0, 0, 0, 20));

        //create Main Menu Button in Play Scene
        Button mainMenuBtn = new Button("Main Menu");
        mainMenuBtn.setPrefSize(225, 100);

        //set backgr for main menu button
        FileInputStream btnInput = new FileInputStream("src/com/company/skyfall/resources/images/BackToMainMenuButtonBackgr.png");
        Image btnBackgrImage = new Image(btnInput);
        BackgroundSize btnBackgrSize = new BackgroundSize(200, 100, false, false, false, false);
        BackgroundImage btnBackgr = new BackgroundImage(btnBackgrImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                btnBackgrSize);
        mainMenuBtn.setBackground(new Background(btnBackgr));
        mainMenuBtn.setFont(Font.font(25));
        mainMenuBtn.setTextFill(Color.rgb(245, 214, 157));

        mainMenuBtn.setOnAction(e -> {
            try {
                timeline.stop();
                MainMenuController.backToMainMenuFromPlay(e);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        btmHbox = new HBox(273, mainMenuBtn, acBox);
        root.setBottom(btmHbox);
        btmHbox.setPrefHeight(150);
        btmHbox.setAlignment(Pos.BOTTOM_LEFT);
        //create bullet type 1 button
        Button bullet1Btn = new Button();
        bullet1Btn.setPrefSize(225, 100);

        String bullet1Image = PlayLayout.class.getResource("../resources/images/bullet1.png").toExternalForm();
        bullet1Btn.setStyle("-fx-background-image: url('" + bullet1Image + "');-fx-background-color:transparent; -fx-background-size:100% 100%;");
        //create bullet type 2 button
        Button bullet2Btn = new Button();
        bullet2Btn.setPrefSize(150, 66.7);
        String bullet2Image = PlayLayout.class.getResource("../resources/images/bullet2.png").toExternalForm();
        bullet2Btn.setStyle("-fx-background-image: url('" + bullet2Image + "');-fx-background-color:transparent; -fx-background-size:100% 100%;");


        HBox bullet2Hbox = new HBox();
        Label bullet2Label = new Label("x3");
        bullet2Label.setFont(Font.font(50));
        bullet2Label.setTextFill(Color.YELLOW);
        bullet2Hbox.getChildren().addAll(bullet2Btn, bullet2Label);

        //create bullet type 3 button
        Button bullet3Btn = new Button();
        bullet3Btn.setPrefSize(150, 66.7);
        String bullet3Image = PlayLayout.class.getResource("../resources/images/bullet3.png").toExternalForm();
        bullet3Btn.setStyle("-fx-background-image: url('" + bullet3Image + "');-fx-background-color:transparent; -fx-background-size:100% 100%;");

        HBox bullet3Hbox = new HBox();
        Label bullet3Label = new Label("x1");
        bullet3Label.setFont(Font.font(50));
        bullet3Label.setTextFill(Color.YELLOW);
        bullet3Hbox.getChildren().addAll(bullet3Btn, bullet3Label);
        //set onAction Handler for bullet type 1 button
        bullet1Btn.setOnAction(e -> BulletController.enlargeBulletButton1(e, typeOfBullet));
        //set onAction Handler for bullet type 2 button
        bullet2Btn.setOnAction(e -> BulletController.enlargeBulletButton2(e, typeOfBullet, (byte) playerBoard.getNumBulletType2()));
        //set onAction Handler for bullet type 3 button
        bullet3Btn.setOnAction(e -> BulletController.enlargeBulletButton3(e, typeOfBullet, (byte) playerBoard.getNumBulletType3()));

        bulletBox.getChildren().addAll(bullet1Btn, bullet2Hbox, bullet3Hbox);
        root.setLeft(bulletBox);
        //create enemy board
        enemyBoard = new Board(true, event -> BoardController.enemyCellClick(event, running, overGame, enemyBoard, playerBoard, typeOfBullet,
                bullet2Label, bullet3Label, bullet1Btn, bullet2Btn, bullet3Btn));

        //create player board and set up
        playerBoard = new Board(false, event -> BoardController.clickSetUp(event, running, enemyBoard, playerBoard));

        playerBoard.acToMove = null;
        enemyBoard.acToMove = null;
        playerBoard.dragEffect(); //add drag-repo drag-setup affect for cell
        mouseOver(playerBoard);
        mouseExit(playerBoard);

        //add drag-setup affect for imageview
        ACToSet.v2.setOnDragDetected(playerBoard.onDragDetected);
        ACToSet.h2.setOnDragDetected(playerBoard.onDragDetected);
        ACToSet.v3.setOnDragDetected(playerBoard.onDragDetected);
        ACToSet.h3.setOnDragDetected(playerBoard.onDragDetected);
        ACToSet.v4.setOnDragDetected(playerBoard.onDragDetected);
        ACToSet.h4.setOnDragDetected(playerBoard.onDragDetected);

        //create Play Layout

        //create Labels
        Label enemyBoardLabel = new Label("Computer Board");
        enemyBoardLabel.setTextFill(Color.YELLOW);
        enemyBoardLabel.setFont(Font.font(25));
        enemyBoardLabel.setLabelFor(enemyBoard);

        Label playerBoardLabel = new Label("Player Board");
        playerBoardLabel.setTextFill(Color.YELLOW);
        playerBoardLabel.setFont(Font.font(25));
        playerBoardLabel.setLabelFor(playerBoard);

        HBox labels = new HBox(225, enemyBoardLabel, playerBoardLabel);
        labels.setPrefHeight(130);
        labels.setAlignment(Pos.BOTTOM_CENTER);
        labels.setPadding(new Insets(70, 50, 0, 0));
        //create row name Vbox
        VBox rowLabels = new VBox(0);
        HBox rowNameLabels[] = new HBox[10];
        for (int i = 0; i <= 9; i++) {
            rowNameLabels[i] = new HBox();
            rowNameLabels[i].setPrefHeight(31);
            rowNameLabels[i].setPrefWidth(100);
            Label rowName = new Label("0" + String.valueOf(i + 1));
            if (i == 9) rowName.setText("10");
            rowName.setFont(Font.font(15));
            rowName.setTextFill(Color.YELLOW);
            rowNameLabels[i].getChildren().add(rowName);
            rowNameLabels[i].setAlignment(Pos.CENTER);
            rowLabels.getChildren().add(rowNameLabels[i]);
        }
        rowLabels.setPrefWidth(100);
        //create Boards
        boards = new HBox(enemyBoard, rowLabels, playerBoard);
        //create column name HBox
        HBox colLabels = new HBox(100);
        HBox colLabelsLeft = new HBox();
        HBox colLabelsRight = new HBox();
        HBox[] colNameLabels1 = new HBox[10];
        HBox[] colNameLabels2 = new HBox[10];
        for (int i = 0; i <= 9; i++) {
            Label colName1 = new Label();
            Label colName2 = new Label();
            colNameLabels1[i] = new HBox();
            colNameLabels2[i] = new HBox();
            colNameLabels1[i].setPrefWidth(31);
            colNameLabels2[i].setPrefWidth(31);
            colNameLabels1[i].setAlignment(Pos.CENTER);
            colNameLabels2[i].setAlignment(Pos.CENTER);
            colName1.setText(Character.toString((char) (i + 65)));
            colName2.setText(Character.toString((char) (i + 65)));
            colName1.setFont(Font.font(15));
            colName2.setFont(Font.font(15));
            colName1.setTextFill(Color.YELLOW);
            colName2.setTextFill(Color.YELLOW);
            colNameLabels1[i].getChildren().add(colName1);
            colLabelsLeft.getChildren().add(colNameLabels1[i]);
            colNameLabels2[i].getChildren().add(colName2);
            colLabelsRight.getChildren().add(colNameLabels2[i]);
        }
        colLabels.getChildren().addAll(colLabelsLeft, colLabelsRight);

        //boards.setPadding(new Insets(0, 0, 0, 0));
        VBox centerBox = new VBox(0, labels, colLabels, boards);
        centerBox.setPrefWidth(700);
        centerBox.setPrefHeight(548);
        centerStack.getChildren().add(centerBox);
        //create turn changing label
        ytlb.setText("--YOUR TURN--");
        ytlb.setTextFill(Color.YELLOW);
        ytlb.setFont(Font.font("Arial Black", FontWeight.LIGHT, 50));

        etlb.setText("--ENEMY'S TURN--");
        etlb.setTextFill(Color.YELLOW);
        etlb.setFont(Font.font("Arial Black", FontWeight.LIGHT, 50));

        stlb.setText("--START GAME--");
        stlb.setTextFill(Color.YELLOW);
        stlb.setFont(Font.font("Arial Black", FontWeight.LIGHT, 50));
        //create Time counter
        timeText.setFont(Font.font(25));
        timeText.setFill(Color.YELLOW);
        Text subtext = new Text("TIME:");
        subtext.setFont(Font.font(25));
        subtext.setFill(Color.YELLOW);

        HBox timeBox = new HBox(100, subtext, timeText);
        timeBox.setPrefHeight(100);
        timeBox.setPrefWidth(1366);
        timeBox.setPadding(new Insets(50, 50, 0, 570));

        FileInputStream playBackgrInput = new FileInputStream("src/com/company/skyfall/resources/images/PlayBackgr.jpg");
        Image playBackgrImage = new Image(playBackgrInput);
        BackgroundSize playBackgrSize = new BackgroundSize(1280, 720, true, true, true, true);
        BackgroundImage playBackgr = new BackgroundImage(playBackgrImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                playBackgrSize);
        //create Play Layout
        root.setBackground(new Background(playBackgr));
        root.setTop(timeBox);
        root.setCenter(centerStack);
        root.setRight(rightPane);
        return root;
    }

    public static void enemyMoveEasy() {
        if (overGame) return;
        while (enemyTurn) {
            if (playerBoard.getAirCrafts() == 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("You lose");
                alert.setHeaderText("Game over!");
                alert.setContentText("YOU LOSE!");
                alert.showAndWait();
                overGame = true;
            }
            if (overGame) break;

            int x = random.nextInt(10);
            int y = random.nextInt(10);

            Cell cell = playerBoard.getCell(x, y);
            if (playerBoard.preCell.equals(cell)) continue;
            playerBoard.preCell = cell;

            boards.setDisable(true);
            PauseTransition pause1 = new PauseTransition(Duration.seconds(1));
            pause1.setOnFinished(ex -> boards.setDisable(false));
            pause1.play();

            //choose type of bullet and move
            int typeOfBullet;
            while (true) {
                typeOfBullet = random.nextInt(3) + 1;

                if (typeOfBullet == 1) {
                    enemyTurn = cell.shootType1();
                    updateHP();
                    break;
                }
                if (typeOfBullet == 2 && enemyBoard.getNumBulletType2() > 0) {
                    enemyTurn = cell.shootType2();
                    updateHP();
                    enemyBoard.setNumBulletType2(enemyBoard.getNumBulletType2() - 1);
                    break;
                }
                if (typeOfBullet == 3 && enemyBoard.getNumBulletType3() > 0) {
                    enemyTurn = cell.shootType3();
                    updateHP();
                    enemyBoard.setNumBulletType3(enemyBoard.getNumBulletType3() - 1);
                    break;
                }
            }
            PlayLog enemyLog = new PlayLog(cell, (byte) typeOfBullet, turn, "Enemy");
            Label pl = new Label(enemyLog.getTurn() + "\t\t" + enemyLog.getPlayer() + "\t\t\t " + enemyLog.convertCellName(cell) + "\t\t\t" + typeOfBullet + "\t\t\t\t" + enemyLog.getDamage() + "\t\t" + enemyLog.status());
            plBox.getChildren().add(pl);
            if (!overGame) {
                centerStack.getChildren().add(ytlb);
                boards.setDisable(true);
                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                pause.setOnFinished(ex -> {
                    turn++;

                    boards.setDisable(false);
                    centerStack.getChildren().remove(1);
                });
                pause.play();
            }
        }

    }

    public static void enemyMoveHard() {
        if (overGame) return;
        while (enemyTurn) {

            if (playerBoard.getAirCrafts() == 0) {
                overGame = true;
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("You lose");
                alert.setHeaderText("Game over!");
                alert.setContentText("YOU LOSE!");
                alert.show();
                overGame = true;
            }
            if (overGame) break;


            boards.setDisable(true);
            PauseTransition pause1 = new PauseTransition(Duration.seconds(1));
            pause1.setOnFinished(ex -> boards.setDisable(false));
            pause1.play();


            Cell lastAC = enemyBoard.lastAC();
            if (lastAC != null && !enemyBoard.didRepo) {
                int x, y;
                while (true) {
                    x = random.nextInt(10);
                    y = random.nextInt(10);

                    if (enemyBoard.isOkToSetAirCraft(lastAC.getAirCraft(), x, y)) break;

                }

                enemyBoard.reposAirCraft(lastAC.getAirCraft(), x, y);

                enemyTurn = false;
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {
                        Cell cell = enemyBoard.getCell(i, j);
                        if (cell.getAirCraft() == null) {
                            cell.setFill(Color.TRANSPARENT);
                            cell.wasShot = false;
                        }
                    }
                }
                break;
            }


            //find Alive AC of playerBoard
            Cell cell = playerBoard.findAliveAirCraft();
            //found
            if (cell.x != 10) {
                // System.out.println("Tim dc thang con song,bi thuong la ac"+cell.getAirCraft().getType()+" o "+cell.x+cell.y);
                if (cell.equals(playerBoard.preCell)) {
                    // shot on edge shared cell
                    Cell cellTmp = playerBoard.findEdgeSharedCell(cell.x, cell.y);
                    //  System.out.println("tim dc thang ben canh la"+cellTmp.x+cellTmp.y);
                    if (cellTmp.wasShot && enemyBoard.getNumBulletType3() > 0) {
                        PlayLog enemyLogHard = new PlayLog(cellTmp, (byte) 3, turn, "Enemy");
                        Label pl = new Label(enemyLogHard.getTurn() + "\t\t" + enemyLogHard.getPlayer() + "\t\t\t " + enemyLogHard.convertCellName(cellTmp) + "\t\t\t" + 3 + "\t\t\t\t" + enemyLogHard.getDamage() + "\t\t" + enemyLogHard.status());
                        plBox.getChildren().add(pl);

                        enemyTurn = cellTmp.shootType3();

                        //    System.out.println("ban dan 3 vao ac"+cellTmp.getAirCraft().getType()+" o "+cellTmp.x+cellTmp.y);
                        updateHP();
                        enemyBoard.setNumBulletType3(enemyBoard.getNumBulletType3() - 1);
                        playerBoard.preCell = cellTmp;

                    } else {
                        enemyTurn = cellTmp.shootType1();

                        //  System.out.println("ban dan 1 vao ac"+" o "+cellTmp.x+cellTmp.y);
                        PlayLog enemyLogHard = new PlayLog(cellTmp, (byte) 1, turn, "Enemy");
                        Label pl = new Label(enemyLogHard.getTurn() + "\t\t" + enemyLogHard.getPlayer() + "\t\t\t " + enemyLogHard.convertCellName(cellTmp) + "\t\t\t" + 1 + "\t\t\t\t" + enemyLogHard.getDamage() + "\t\t" + enemyLogHard.status());
                        plBox.getChildren().add(pl);

                        updateHP();
                        playerBoard.preCell = cellTmp;

                    }
                } else {
                    //shot on cell
                    if (enemyBoard.getNumBulletType3() > 0) {
                        PlayLog enemyLogHard = new PlayLog(cell, (byte) 3, turn, "Enemy");
                        Label pl = new Label(enemyLogHard.getTurn() + "\t\t" + enemyLogHard.getPlayer() + "\t\t\t " + enemyLogHard.convertCellName(cell) + "\t\t\t" + 3 + "\t\t\t\t" + enemyLogHard.getDamage() + "\t\t" + enemyLogHard.status());
                        plBox.getChildren().add(pl);
                        enemyTurn = cell.shootType3();

                        //  System.out.println("ban dan 3 vao ac"+ cell.getAirCraft().getType()+" o "+cell.x+cell.y);

                        updateHP();
                        enemyBoard.setNumBulletType3(enemyBoard.getNumBulletType3() - 1);
                        playerBoard.preCell = cell;

                    } else {
                        enemyTurn = cell.shootType1();

                        //    System.out.println("ban dan 1 vao ac"+cell.getAirCraft().getType()+" o "+cell.x+cell.y);
                        PlayLog enemyLogHard = new PlayLog(cell, (byte) 1, turn, "Enemy");
                        Label pl = new Label(enemyLogHard.getTurn() + "\t\t" + enemyLogHard.getPlayer() + "\t\t\t " + enemyLogHard.convertCellName(cell) + "\t\t\t" + 1 + "\t\t\t\t" + enemyLogHard.getDamage() + "\t\t" + enemyLogHard.status());
                        plBox.getChildren().add(pl);
                        updateHP();
                        playerBoard.preCell = cell;

                    }
                }
                continue;
            }

            //shot by bullet 2
            if (enemyBoard.getNumBulletType2() > 0) {
                while (true) {
                    int x = random.nextInt(9);
                    int y = random.nextInt(9);
                    if (x == 0) x++;
                    if (y == 0) y++;
                    if (playerBoard.isAbleToShotThisCell(x, y)) {
                        //   System.out.println("ban dan 2 vao o "+x+y);
                        Cell cellTmp = playerBoard.getCell(x, y);
                        enemyTurn = cellTmp.shootType2();

                        PlayLog enemyLogHard = new PlayLog(cellTmp, (byte) 2, turn, "Enemy");
                        Label pl = new Label(enemyLogHard.getTurn() + "\t\t" + enemyLogHard.getPlayer() + "\t\t\t " + enemyLogHard.convertCellName(cellTmp) + "\t\t\t" + 2 + "\t\t\t\t" + enemyLogHard.getDamage() + "\t\t" + enemyLogHard.status());
                        plBox.getChildren().add(pl);
                        updateHP();
                        enemyBoard.setNumBulletType2(enemyBoard.getNumBulletType2() - 1);
                        playerBoard.preCell = cellTmp;

                        break;
                    }
                }
                continue;
            }

            //shot by bullet 1
            int edge = playerBoard.findMaxNumberOfEdgeShared();
            while (true) {
                int x = random.nextInt(10);
                int y = random.nextInt(10);
                if (playerBoard.getCell(x, y).wasShot) continue;
                int count = playerBoard.countNumberOfEdgeShared(x, y);
                if (count == edge) {
                    Cell cellTmp = playerBoard.getCell(x, y);
                    enemyTurn = cellTmp.shootType1();


                    //    System.out.println("ban dan 1 vao "+" o "+cellTmp.x+cellTmp.y);
                    PlayLog enemyLogHard = new PlayLog(cellTmp, (byte) 1, turn, "Enemy");
                    Label pl = new Label(enemyLogHard.getTurn() + "\t\t" + enemyLogHard.getPlayer() + "\t\t\t " + enemyLogHard.convertCellName(cellTmp) + "\t\t\t" + 1 + "\t\t\t\t" + enemyLogHard.getDamage() + "\t\t" + enemyLogHard.status());
                    plBox.getChildren().add(pl);
                    updateHP();
                    playerBoard.preCell = cellTmp;

                    break;
                }
            }

        }

        if (!overGame) {
            centerStack.getChildren().add(ytlb);
            boards.setDisable(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(ex -> {
                turn++;
                boards.setDisable(false);
                centerStack.getChildren().remove(1);
            });
            pause.play();
        }
    }

    public static void startGame() {
        //start the time counter
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        btmHbox.getChildren().removeAll(acBox);
        btmHbox.setSpacing(208);
        // place enemy air crafts
        int type = 4;

        while (type > 1) {
            int x = random.nextInt(10);
            int y = random.nextInt(10);

            if (enemyBoard.setAirCraft(new AirCraft(type, Math.random() < 0.5), x, y)) {
                type--;
            }
        }
        running = true;
        centerStack.getChildren().add(stlb);
        boards.setDisable(true);
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(ex -> {
            boards.setDisable(false);
            centerStack.getChildren().removeAll(stlb);
        });
        pause.play();
    }

    public static void updateHP() {
        String[] hp = new String[3];
        String[] status = new String[3];
        for (int i = 0; i <= 2; i++) {
            hp[i] = String.valueOf(PlayLayout.AC[i].getHP());
            hp[i] = (hp[i].length() > 2) ? hp[i] : ((hp[i].length() > 1) ? "  " + hp[i] : "    " + hp[i]);
            hp[i] = "  HP:             " + hp[i] + "/" + (i + 2) + "00  ";
            status[i] = "  STATUS:       " + (PlayLayout.AC[i].getHP() > 0 ? "ALIVE  " : " DEAD  ");
            ((Label) ((VBox) ((HBox) acHP[i].getChildren().get(1)).getChildren().get(2)).getChildren().get(1)).setText(hp[i]);
            ((Label) ((VBox) ((HBox) acHP[i].getChildren().get(1)).getChildren().get(2)).getChildren().get(0)).setText(status[i]);
        }
    }

    public static void mouseOver(Board board) {
        for (int i = 0; i <= 9; i++)
            for (int j = 0; j <= 9; j++) {
                Cell c = board.getCell(i, j);
                c.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (!running) return;
                        if (btmHbox.getChildren().size() > 1) btmHbox.getChildren().remove(1);
                        if (c.getAirCraft() != null) try {
                            btmHbox.getChildren().addAll(acHP[c.getAirCraft().getType() - 2]);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    }
                });
            }
    }

    public static void mouseExit(Board board) {
        for (int i = 0; i <= 9; i++)
            for (int j = 0; j <= 9; j++) {
                Cell c = board.getCell(i, j);
                c.setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (!running) return;
                        if (btmHbox.getChildren().size() > 1) btmHbox.getChildren().remove(1);
                    }
                });
            }
    }
}
