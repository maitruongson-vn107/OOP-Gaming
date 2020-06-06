package com.company.skyfall.view;

import com.company.skyfall.Main;
import com.company.skyfall.controller.*;
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
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;


import java.io.FileInputStream;
import java.security.Policy;
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
    static int turn, currentTurn;
    static VBox plBox;
    public static StackPane turn_label;

    public static void logging(Cell cell) {
        PlayLog playLog = new PlayLog(cell, typeOfBullet, turn, "Player");
        logList.add(playLog);
        Label pl = new Label((currentTurn != turn ? playLog.getTurn() : "") + "\t\t" + playLog.getPlayer() + "\t\t\t " + playLog.convertCellName(cell) + "\t\t\t" + typeOfBullet + "\t\t\t\t" + playLog.getDamage() + "\t\t" + playLog.status());
        pl.setTextFill(Color.ORANGE);
        if (currentTurn != turn){
            currentTurn = turn;
        }
        plBox.getChildren().add(pl);
    }

    //Make time counter appearing in root.top
    private static Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
        String min = (time / 60 < 10 ? "0" : "") + time / 60;
        String sec = (time % 60 < 10 ? "0" : "") + time % 60;
        timeText.setText(min + ":" + sec);
        timeText.setFont(Font.font(25));
        timeText.setFill(Color.YELLOW);
        time++;
    }));
    public static void resetStatusVariable(boolean level){
        for (int i = 0; i < 3; i++)
            acSet[i] = false;
        centerStack = new StackPane();
        boards = new HBox();
        ytlb = new Label();
        stlb = new Label();
        etlb = new Label();
        typeOfBullet = 1;
        turn = 1;
       currentTurn=0;
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
        pl.setTextFill(Color.ORANGE);
        AC[0] = AC[1] = AC[2] = null;
        acVBox.getChildren().clear();
        acHBox.getChildren().clear();
        acVBox.getChildren().addAll(ACToSet.h2, ACToSet.h3, ACToSet.h4);
        acHBox.getChildren().addAll(ACToSet.v2, ACToSet.v3, ACToSet.v4);
    }
    public static Parent createContent(boolean level) {
        // set up status variables to default value
        resetStatusVariable(level);
        //sound and music in game
        Button musicBtn = new Button(Main.musicPlayer.getVolume() == 0?"Music: Off":"Music: On");
        musicBtn.setOnAction(event -> MediaController.musicControl(musicBtn));

        Button soundBtn = new Button(Board.soundLevel == 0?"Sound: Off":"Sound: On");
        soundBtn.setOnAction(event -> MediaController.soundControl(soundBtn));

        HBox musicAndSound = new HBox(musicBtn, soundBtn);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefWidth(300);
        Label txt = new Label("Turn\t\t" + "Player/Enemy");
        scrollPane.setContent(txt);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setContent(plBox);

        VBox rightPane = new VBox(musicAndSound, scrollPane);

        BorderPane root = new BorderPane();
        VBox bulletBox = new VBox();

        //create Main Menu Button in Play Scene
        Button mainMenuBtn = new Button("Main Menu");

        //set backgr for main menu button
        mainMenuBtn.setOnAction(e -> {
                timeline.stop();
                MainMenuController.backToMainMenuFromPlay(e); });

        btmHbox = new HBox( mainMenuBtn, acBox);


        //create bullet type 1 button
        Button bullet1Btn = new Button();
        bullet1Btn.setPrefSize(225,100);
        //create bullet type 2 button
        Button bullet2Btn = new Button();
        bullet2Btn.setPrefSize(150,66.7);
        Label bullet2Label = new Label("x3");

        HBox bullet2Hbox = new HBox();
        HBox bullet3Hbox = new HBox();
        Label bullet3Label = new Label("x1");

        bullet2Hbox.getChildren().addAll(bullet2Btn, bullet2Label);

        //create bullet type 3 button
        Button bullet3Btn = new Button();
        bullet3Btn.setPrefSize(150,66.7);
        bullet3Hbox.getChildren().addAll(bullet3Btn, bullet3Label);
        //set onAction Handler for bullet type 1 button
        bullet1Btn.setOnAction(e -> BulletController.enlargeBulletButton1(e, typeOfBullet));
        //set onAction Handler for bullet type 2 button
        bullet2Btn.setOnAction(e -> BulletController.enlargeBulletButton2(e, typeOfBullet, (byte) playerBoard.getNumBulletType2()));
        //set onAction Handler for bullet type 3 button
        bullet3Btn.setOnAction(e -> BulletController.enlargeBulletButton3(e, typeOfBullet, (byte) playerBoard.getNumBulletType3()));

        bulletBox.getChildren().addAll(bullet1Btn, bullet2Hbox, bullet3Hbox);

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
        AirCraftController.dragEffect(ACToSet.v2,ACToSet.v3,ACToSet.v4,ACToSet.h2,ACToSet.h3,ACToSet.h4);

        //create Play Layout

        //create Labels
        Label enemyBoardLabel = new Label("Computer Board");
        enemyBoardLabel.setLabelFor(enemyBoard);

        Label playerBoardLabel = new Label("Player Board");
        playerBoardLabel.setLabelFor(playerBoard);

        turn_label = new StackPane();

        HBox labels = new HBox( enemyBoardLabel, playerBoardLabel);


        //create row name Vbox
        VBox rowLabels = new VBox(0);
        HBox[] rowNameLabels = new HBox[10];
        for (int i = 0; i <= 9; i++) {
            rowNameLabels[i] = new HBox();
            rowNameLabels[i].getStyleClass().add("RowNameBox");
            Label rowName = new Label("0" + String.valueOf(i + 1));
            if (i == 9) rowName.setText("10");
            rowNameLabels[i].getChildren().add(rowName);
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
            colNameLabels1[i].getStyleClass().add("ColNameBox");
            colNameLabels2[i].getStyleClass().add("ColNameBox");
            colName1.setText(Character.toString((char) (i + 65)));
            colName2.setText(Character.toString((char) (i + 65)));
            colNameLabels1[i].getChildren().add(colName1);
            colLabelsLeft.getChildren().add(colNameLabels1[i]);
            colNameLabels2[i].getChildren().add(colName2);
            colLabelsRight.getChildren().add(colNameLabels2[i]);
        }
        colLabels.getChildren().addAll(colLabelsLeft, colLabelsRight);

        VBox centerBox = new VBox(0,turn_label,labels, colLabels, boards);
        centerStack.getChildren().add(centerBox);
        //create turn changing label
        ytlb.setText("--YOUR TURN--");
        etlb.setText("--ENEMY'S TURN--");
        stlb.setText("--START GAME--");
        //create Time counter
        Text subtext = new Text("TIME:");
        HBox timeBox = new HBox( subtext, timeText);

        root.setTop(timeBox);
        root.setCenter(centerStack);
        root.setRight(rightPane);
        root.setLeft(bulletBox);
        root.setBottom(btmHbox);

//        plBox.setStyle("-fx-background-color:transparent;");
//        scrollPane.setStyle("-fx-background-color:transparent;");

        turn_label.getStyleClass().add("TurnLabelSTPane");
        bulletBox.getStyleClass().add("BulletBox");
        rightPane.getStyleClass().add("RightPane");
        bullet1Btn.getStyleClass().add("Bullet1Btn");
        bullet2Btn.getStyleClass().add("Bullet2Btn");
        bullet3Btn.getStyleClass().add("Bullet3Btn");
        mainMenuBtn.setId("mainMenuBtn");
        root.getStyleClass().setAll("PlayLayout");
        soundBtn.getStyleClass().add("Music_Sound_Btn");
        musicBtn.getStyleClass().add("Music_Sound_Btn");
        bullet2Label.getStyleClass().add("BulletLabel");
        bullet3Label.getStyleClass().add("BulletLabel");
        enemyBoardLabel.getStyleClass().add("EnemyBoardLabel");
        playerBoardLabel.getStyleClass().add("EnemyBoardLabel");
        btmHbox.getStyleClass().add("BtmHbox");
        timeBox.getStyleClass().add("TimeBox");
        labels.getStyleClass().add("Labels");
        centerBox.getStyleClass().add("CenterBox");
        timeText.getStyleClass().add("TimeText");
        subtext.getStyleClass().add("TimeText");
        stlb.getStyleClass().add("TurnLabel");
        ytlb.getStyleClass().add("TurnLabel");
        etlb.getStyleClass().add("TurnLabel");

        return root;
    }

    public static void enemyMoveEasy() {
        int shoot_count = -1;
        if (overGame) return;
        while (enemyTurn) {
            PauseTransition pause = new PauseTransition(Duration.seconds(shoot_count + 1));
            if (playerBoard.getAirCrafts() == 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("You lose");
                alert.setHeaderText("Game over!");
                alert.setContentText("YOU LOSE!");
                overGame = true;
                pause.setOnFinished(e -> {
                    alert.show();
                });
                pause.play();
            }

            if (overGame) break;

            int x = random.nextInt(10);
            int y = random.nextInt(10);

            Cell cell = playerBoard.getCell(x, y);
            if (playerBoard.preCell.equals(cell)) continue;
            playerBoard.preCell = cell;
            shoot_count++;
            boards.getChildren().get(0).setDisable(true);
            boards.getChildren().get(2).setDisable(true);

            //choose type of bullet and move
            int typeOfBullet;
            while (true) {
                typeOfBullet = random.nextInt(3) + 1;

                if (typeOfBullet == 1) {
                    PlayLog enemyLog = new PlayLog(cell, (byte) typeOfBullet, turn, "Enemy");
                    Label pl = new Label((currentTurn != turn ? enemyLog.getTurn() : "") + "\t\t" + enemyLog.getPlayer() + "\t\t\t " + enemyLog.convertCellName(cell) + "\t\t\t" + typeOfBullet + "\t\t\t\t" + enemyLog.getDamage() + "\t\t" + enemyLog.status());
                    pl.setTextFill(Color.YELLOW);
                    plBox.getChildren().add(pl);
                    int[] status = cell.dealDame1();
                    enemyTurn = (status[1] == 1);
                    pause.setOnFinished(ex -> {
                        cell.shootEffect1(status);
                        updateHP();
                    });
                    pause.play();
                    break;
                }
                if (typeOfBullet == 2 && enemyBoard.getNumBulletType2() > 0) {
                    PlayLog enemyLog = new PlayLog(cell, (byte) typeOfBullet, turn, "Enemy");
                    Label pl = new Label((currentTurn != turn ? enemyLog.getTurn() : "") + "\t\t" + enemyLog.getPlayer() + "\t\t\t " + enemyLog.convertCellName(cell) + "\t\t\t" + typeOfBullet + "\t\t\t\t" + enemyLog.getDamage() + "\t\t" + enemyLog.status());
                    pl.setTextFill(Color.YELLOW);
                    plBox.getChildren().add(pl);
                    int[] status = cell.dealDame2();
                    enemyTurn = (status[9] == 1);
                    enemyBoard.setNumBulletType2(enemyBoard.getNumBulletType2() - 1);
                    pause.setOnFinished(e -> {
                        cell.shootEffect2(status);
                        updateHP();
                    });
                    pause.play();
                    break;
                }
                if (typeOfBullet == 3 && enemyBoard.getNumBulletType3() > 0) {
                    PlayLog enemyLog = new PlayLog(cell, (byte) typeOfBullet, turn, "Enemy");
                    Label pl = new Label((currentTurn != turn ? enemyLog.getTurn() : "") + "\t\t" + enemyLog.getPlayer() + "\t\t\t " + enemyLog.convertCellName(cell) + "\t\t\t" + typeOfBullet + "\t\t\t\t" + enemyLog.getDamage() + "\t\t" + enemyLog.status());
                    pl.setTextFill(Color.YELLOW);
                    plBox.getChildren().add(pl);
                    int[] status = cell.dealDame3();
                    enemyTurn = (status[1] == 1);
                    enemyBoard.setNumBulletType3(enemyBoard.getNumBulletType3() - 1);
                    pause.setOnFinished(e -> {
                        cell.shootEffect3(status);
                        updateHP();
                    });
                    pause.play();
                    break;
                }
            }

            }
            if (!overGame) {

                PauseTransition pause1 = new PauseTransition(Duration.seconds(shoot_count));
                PauseTransition pause2 = new PauseTransition(Duration.seconds(shoot_count+1));
                pause1.setOnFinished(ex -> {
                    if (!turn_label.getChildren().isEmpty()) turn_label.getChildren().clear();
                    turn_label.getChildren().add(ytlb);
                    turn++;
                });
                pause2.setOnFinished(ex->{
                    boards.getChildren().get(0).setDisable(false);
                    boards.getChildren().get(2).setDisable(false);
                    if (!turn_label.getChildren().isEmpty()) turn_label.getChildren().clear();
                });
                pause1.play();
                pause2.play();
            }
        }
    public static void enemyMoveHard() {
        int shoot_count = -1;
        if (overGame) return;
        while (enemyTurn) {

            PauseTransition pause = new PauseTransition(Duration.seconds(shoot_count+1));
            if (playerBoard.getAirCrafts() == 0) {
                overGame = true;
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("You lose");
                alert.setHeaderText("Game over!");
                alert.setContentText("YOU LOSE!");
                overGame = true;
                pause.setOnFinished(e->{
                    alert.show();
                });
                pause.play();
            }
            if (overGame) break;
            shoot_count++;
            boards.getChildren().get(0).setDisable(true);
            boards.getChildren().get(2).setDisable(true);

            Cell lastAC = enemyBoard.lastAC();
            if (lastAC != null && !enemyBoard.didRepo) {
                pause.setOnFinished(e->{
                    int x, y;
                    do {
                        x = random.nextInt(10);
                        y = random.nextInt(10);
                    } while (!enemyBoard.isOkToSetAirCraft(lastAC.getAirCraft(), x, y));
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
                });
                pause.play();
                break;
            }

            //find Alive AC of playerBoard
            Cell cell = playerBoard.findAliveAirCraft();
            //found
            if (cell.x != 10) {
                if (cell.equals(playerBoard.preCell)) {
                    // shot on edge shared cell
                    Cell cellTmp = playerBoard.findEdgeSharedCell(cell.x, cell.y);
                    if (cellTmp.wasShot && enemyBoard.getNumBulletType3() > 0) {
                        PlayLog enemyLogHard = new PlayLog(cellTmp, (byte) 3, turn, "Enemy");
                        Label pl = new Label((currentTurn != turn ? enemyLogHard.getTurn() : "") + "\t\t" + enemyLogHard.getPlayer() + "\t\t\t " + enemyLogHard.convertCellName(cellTmp) + "\t\t\t" + 3 + "\t\t\t\t" + enemyLogHard.getDamage() + "\t\t" + enemyLogHard.status());
                        int[] status = cellTmp.dealDame3();
                        enemyTurn = (status[1] == 1);
                        updateHP();
                        playerBoard.preCell = cellTmp;
                        enemyBoard.setNumBulletType3(enemyBoard.getNumBulletType3() - 1);
                        pause.setOnFinished(e->{
                            cellTmp.shootEffect3(status);
                            pl.setTextFill(Color.YELLOW);
                            plBox.getChildren().add(pl);
                        });
                        pause.play();
                    } else {
                        PlayLog enemyLogHard = new PlayLog(cellTmp, (byte) 1, turn, "Enemy");
                        Label pl = new Label((currentTurn != turn ? enemyLogHard.getTurn() :"") + "\t\t" + enemyLogHard.getPlayer() + "\t\t\t " + enemyLogHard.convertCellName(cellTmp) + "\t\t\t" + 1 + "\t\t\t\t" + enemyLogHard.getDamage() + "\t\t" + enemyLogHard.status());
                        int[] status = cellTmp.dealDame1();
                        enemyTurn = (status[1] == 1);

                        updateHP();
                        playerBoard.preCell = cellTmp;
                        pause.setOnFinished(e->{
                            cellTmp.shootEffect1(status);
                            pl.setTextFill(Color.YELLOW);
                            plBox.getChildren().add(pl);

                        });
                        pause.play();
                    }
                } else {
                    //shot on cell
                    if (enemyBoard.getNumBulletType3() > 0) {
                        PlayLog enemyLogHard = new PlayLog(cell, (byte) 3, turn, "Enemy");
                        Label pl = new Label((currentTurn != turn ? enemyLogHard.getTurn() :"") + "\t\t" + enemyLogHard.getPlayer() + "\t\t\t " + enemyLogHard.convertCellName(cell) + "\t\t\t" + 3 + "\t\t\t\t" + enemyLogHard.getDamage() + "\t\t" + enemyLogHard.status());
                        int[] status = cell.dealDame3();
                        enemyTurn = (status[1] == 1);
                        updateHP();
                        enemyBoard.setNumBulletType3(enemyBoard.getNumBulletType3() - 1);
                        pause.setOnFinished(e->{
                            cell.shootEffect3(status);
                            pl.setTextFill(Color.YELLOW);
                            plBox.getChildren().add(pl);
                        });
                        pause.play();
                    } else {
                        PlayLog enemyLogHard = new PlayLog(cell, (byte) 1, turn, "Enemy");
                        Label pl = new Label((currentTurn != turn ? enemyLogHard.getTurn() : "")+ "\t\t" + enemyLogHard.getPlayer() + "\t\t\t " + enemyLogHard.convertCellName(cell) + "\t\t\t" + 1 + "\t\t\t\t" + enemyLogHard.getDamage() + "\t\t" + enemyLogHard.status());
                        int[] status = cell.dealDame1();
                        enemyTurn = (status[1] == 1);
                        updateHP();
                        pause.setOnFinished(e->{
                            cell.shootEffect1(status);
                            pl.setTextFill(Color.YELLOW);
                            plBox.getChildren().add(pl);
                        });
                        pause.play();
                    }
                    playerBoard.preCell = cell;
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
                        Cell cellTmp = playerBoard.getCell(x, y);
                        PlayLog enemyLogHard = new PlayLog(cellTmp, (byte) 2, turn, "Enemy");
                        Label pl = new Label((currentTurn != turn ? enemyLogHard.getTurn() : "") + "\t\t" + enemyLogHard.getPlayer() + "\t\t\t " + enemyLogHard.convertCellName(cellTmp) + "\t\t\t" + 2 + "\t\t\t\t" + enemyLogHard.getDamage() + "\t\t" + enemyLogHard.status());
                        int[] status = cellTmp.dealDame2();
                        enemyTurn = (status[9] == 1);
                        updateHP();
                        enemyBoard.setNumBulletType2(enemyBoard.getNumBulletType2() - 1);
                        playerBoard.preCell = cellTmp;
                        pause.setOnFinished(e->{
                            cellTmp.shootEffect2(status);
                            pl.setTextFill(Color.YELLOW);
                            plBox.getChildren().add(pl);
                        });
                        pause.play();
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
                    PlayLog enemyLogHard = new PlayLog(cellTmp, (byte) 1, turn, "Enemy");
                    Label pl = new Label((currentTurn != turn ? enemyLogHard.getTurn() : "" )+ "\t\t" + enemyLogHard.getPlayer() + "\t\t\t " + enemyLogHard.convertCellName(cellTmp) + "\t\t\t" + 1 + "\t\t\t\t" + enemyLogHard.getDamage() + "\t\t" + enemyLogHard.status());
                    int[] status = cellTmp.dealDame1();
                    enemyTurn = (status[1] == 1);
                    updateHP();
                    playerBoard.preCell = cellTmp;
                    pause.setOnFinished(e->{
                        cellTmp.shootEffect1(status);
                        pl.setTextFill(Color.YELLOW);
                        plBox.getChildren().add(pl);
                    });
                    pause.play();
                    break;
                }
            }
        }

        if (!overGame) {
            PauseTransition pause1 = new PauseTransition(Duration.seconds(shoot_count));
            PauseTransition pause2 = new PauseTransition(Duration.seconds(shoot_count+1));
            pause1.setOnFinished(ex -> {
                if (!turn_label.getChildren().isEmpty()) turn_label.getChildren().clear();
                turn_label.getChildren().add(ytlb);
                turn++;
            });
            pause2.setOnFinished(ex->{
                boards.getChildren().get(0).setDisable(false);
                boards.getChildren().get(2).setDisable(false);
                if (!turn_label.getChildren().isEmpty()) turn_label.getChildren().clear();
            });
            pause1.play();
            pause2.play();
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
        turn_label.getChildren().add(stlb);
        boards.getChildren().get(0).setDisable(true);
        boards.getChildren().get(2).setDisable(true);
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(ex -> {
            boards.getChildren().get(0).setDisable(false);
            boards.getChildren().get(2).setDisable(false);
            if (!turn_label.getChildren().isEmpty()) turn_label.getChildren().clear();
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
                c.setOnMouseEntered(event -> {
                    if (!running) return;
                    if (btmHbox.getChildren().size() > 1) btmHbox.getChildren().remove(1);
                    if (c.getAirCraft() != null) try {
                        btmHbox.getChildren().addAll(acHP[c.getAirCraft().getType() - 2]);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                });
            }
    }

    public static void mouseExit(Board board) {
        for (int i = 0; i <= 9; i++)
            for (int j = 0; j <= 9; j++) {
                Cell c = board.getCell(i, j);
                c.setOnMouseExited(event -> {
                    if (!running) return;
                    if (btmHbox.getChildren().size() > 1) btmHbox.getChildren().remove(1);
                });
            }
    }
}
