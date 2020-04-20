package com.company.skyfall.view;

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
import javafx.scene.control.TextField;
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


public class PlayLayout  {

    public static boolean running = false;
    private static Board enemyBoard;
    private static Board playerBoard;
    private static int airCraftsToPlace = 4;
    public static boolean enemyTurn = false;
    private static Random random = new Random();
    private static int time = 0;
    public static boolean easyMode=false;
    private static int usedBullets = 0;
    private static boolean overGame = false;
    private static Text timeText = new Text("");
    private static byte typeOfBullet = 1;
    public static StackPane centerStack = new StackPane();
    public static HBox boards = new HBox();
    public static Label ytlb = new Label();
    public static Label etlb = new Label();
    public static Label stlb = new Label();
    public static boolean[] acSet = {false,false,false};
    public static AirCraft[] AC = new AirCraft[3];
    public static HBox acBox = (HBox) ACToSet.acBox();
    public static HBox acHBox = ((HBox) (acBox.getChildren().get(1)));
    public static VBox acVBox = ((VBox) acBox.getChildren().get(0));
    private static HBox btmHbox;

    static LogList logList;
    //Make time counter appearing in root.top
    private static Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1),ev->{
        String min = (time/60<10?"0":"") + String.valueOf(time/60) ;
        String sec = (time%60<10?"0":"") + String.valueOf(time%60);
        timeText.setText(min+":"+sec);
        timeText.setFont(Font.font(25));
        timeText.setFill(Color.YELLOW);
        time++;
    }));

    public static Parent createContent(boolean level) throws Exception {
        for (int i = 0;i < 3;i++)
            acSet[i] = false;
        centerStack = new StackPane();
        boards = new HBox();
        ytlb = new Label();
        stlb = new Label();
        etlb = new Label();
        typeOfBullet = 1;
        overGame = false;
        running = false;
        airCraftsToPlace = 4;
        time = 0;
        usedBullets = 0;
        timeText.setText("");
        easyMode = level;
        logList= new LogList();
        AC[0] = AC[1] = AC[2] = null;
        acVBox.getChildren().clear();
        acHBox.getChildren().clear();
        acVBox.getChildren().addAll(ACToSet.h2, ACToSet.h3, ACToSet.h4);
        acHBox.getChildren().addAll(ACToSet.v2, ACToSet.v3, ACToSet.v4);

        BorderPane root = new BorderPane();
        VBox bulletBox = new VBox(50);
        bulletBox.setAlignment(Pos.CENTER_LEFT);
        bulletBox.setPrefWidth(333);
        bulletBox.setPrefHeight(518);
        bulletBox.setPadding(new Insets(0,0,0,20));

        //create Main Menu Button in Play Scene
        Button mainMenuBtn = new Button("Main Menu");
        mainMenuBtn.setPrefSize(225,100);

        //set backgr for main menu button
        FileInputStream btnInput = new FileInputStream("src/com/company/skyfall/view/BackToMainMenuButtonBackgr.png"  );
        Image btnBackgrImage = new Image(btnInput);
        BackgroundSize btnBackgrSize = new BackgroundSize(200,100,false,false,false,false);
        BackgroundImage btnBackgr = new BackgroundImage(btnBackgrImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                btnBackgrSize);
        mainMenuBtn.setBackground(new Background(btnBackgr));
        mainMenuBtn.setFont(Font.font(25));
        mainMenuBtn.setTextFill(Color.rgb(245,214,157));

        mainMenuBtn.setOnAction(e -> {
            try {
                timeline.stop();
                com.company.skyfall.controller.Controller.backToMainMenuFromPlay(e);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        btmHbox = new HBox(500,mainMenuBtn, acBox);
        root.setBottom(btmHbox);
        btmHbox.setPrefHeight(120);

        //create bullet type 1 button
        Button bullet1Btn = new Button();
        bullet1Btn.setPrefSize(225,100);
        String  bullet1Imgae = PlayLayout.class.getResource("bullet1.png").toExternalForm();
        bullet1Btn.setStyle("-fx-background-image: url('" + bullet1Imgae + "');-fx-background-color:transparent; -fx-background-size:100% 100%;");


        //create bullet type 2 button
        Button bullet2Btn = new Button();
        bullet2Btn.setPrefSize(150,66.7);
        String  bullet2Imgae = PlayLayout.class.getResource("bullet2.png").toExternalForm();
        bullet2Btn.setStyle("-fx-background-image: url('" + bullet2Imgae + "');-fx-background-color:transparent; -fx-background-size:100% 100%;");
        HBox bullet2Hbox = new HBox();
        Label bullet2Label = new Label("x3");
        bullet2Label.setFont(Font.font(50));
        bullet2Label.setTextFill(Color.YELLOW);
        bullet2Hbox.getChildren().addAll(bullet2Btn,bullet2Label);

        //creat bullet type 3 button
        Button bullet3Btn = new Button();
        bullet3Btn.setPrefSize(150,66.7);
        String  bullet3Imgae= PlayLayout.class.getResource("bullet3.png").toExternalForm();
        bullet3Btn.setStyle("-fx-background-image: url('" + bullet3Imgae + "');-fx-background-color:transparent; -fx-background-size:100% 100%;");
        HBox bullet3Hbox = new HBox();
        Label bullet3Label = new Label("x1");
        bullet3Label.setFont(Font.font(50));
        bullet3Label.setTextFill(Color.YELLOW);
        bullet3Hbox.getChildren().addAll(bullet3Btn,bullet3Label);

        //set onAction Handler for bullet type 1 button
        bullet1Btn.setOnAction(e->{
            try{
                if (typeOfBullet == 2) {
                    bullet2Btn.setPrefSize(150,66.7);
                }
                if (typeOfBullet == 3 ) {
                    bullet3Btn.setPrefSize(150, 66.7);
                }
                if (typeOfBullet != 1) {
                    bullet1Btn.setPrefSize(225,100);
                }
                typeOfBullet = 1;
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }
        });

        //set onAction Handler for bullet type 2 button
        bullet2Btn.setOnAction(e->{
            try{
                if (playerBoard.getNumBulletType2() == 0 ) return;
                if (typeOfBullet == 1) {
                    bullet1Btn.setPrefSize(150,66.7);
                }
                if (typeOfBullet == 3 ){
                    bullet3Btn.setPrefSize(150,66.7);
                }
                if (typeOfBullet != 2) {
                    bullet2Btn.setPrefSize(225,100);
                }
                typeOfBullet = 2;
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }
        });

        //set onAction Handler for bullet type 3 button
        bullet3Btn.setOnAction(e->{
            try{
                if (playerBoard.getNumBulletType3() == 0) return;
                if (typeOfBullet == 1) {
                    bullet1Btn.setPrefSize(150,66.7);
                }
                if (typeOfBullet == 2 ){
                    bullet2Btn.setPrefSize(150,66.7);
                }
                if (typeOfBullet != 3)
                {
                    bullet3Btn.setPrefSize(225,100);
                }
                typeOfBullet = 3;
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }
        });

        bulletBox.getChildren().addAll(bullet1Btn,bullet2Hbox,bullet3Hbox);
        root.setLeft(bulletBox);

        enemyBoard = new Board(true, event -> {
            if (!running)
                return;
            if (overGame) return;
            Cell cell = (Cell) event.getSource();
            if (enemyBoard.preCell.equals(cell)) return;
            enemyBoard.preCell = cell;
            usedBullets++;

            //choose type of bullet
            while (true) {
                logList.add(new PlayLog(cell,typeOfBullet));

                if (typeOfBullet == 1) {
                    enemyTurn = !cell.shootType1();
                    break;
                }
                if (typeOfBullet == 2) {
                    enemyTurn = !cell.shootType2();
                    playerBoard.setNumBulletType2(playerBoard.getNumBulletType2() - 1);
                    bullet2Label.setText("x" + String.valueOf(playerBoard.getNumBulletType2()));
                    //set bullet type to 1 as default
                    try {
                        bullet1Btn.setPrefSize(225, 100);
                        bullet2Btn.setPrefSize(150, 66.7);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    typeOfBullet = 1;
                    break;
                }
                if (typeOfBullet == 3) {
                    enemyTurn = !cell.shootType3();
                    playerBoard.setNumBulletType3(playerBoard.getNumBulletType3() - 1);
                    bullet3Label.setText("x" + String.valueOf(playerBoard.getNumBulletType3()));
                    //set bullet type to 1 as default
                    try {
                        bullet1Btn.setPrefSize(225, 100);
                        bullet3Btn.setPrefSize(150, 66.7);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    typeOfBullet = 1;
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
                    if (isTop(usedBullets, time, easyMode)) {
                        TextInputDialog dialog = new TextInputDialog();
                        dialog.setTitle("Enter your name");
                        dialog.setHeaderText("You got a high score\nPlease enter your name:");
                        dialog.setContentText("Your name:");
                        dialog.showAndWait();
                        nameField = dialog.getEditor();
                        if (!easyMode) {
                            writeHighScoreHard(nameField.getText(), usedBullets, time);
                        } else {
                            writeHighScoreEasy(nameField.getText(), usedBullets, time);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (!overGame && enemyTurn) {
                centerStack.getChildren().add(etlb);
                boards.setDisable(true);
                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                pause.setOnFinished(ex -> {
                    boards.setDisable(false);
                    centerStack.getChildren().remove(1);
                    if (enemyTurn && easyMode)
                        enemyMoveEasy();
                    else if (enemyTurn && !easyMode) enemyMoveHard();
                });
                pause.play();
            };
        });

        //create player board and set up
        playerBoard = new Board(false, event -> {
            if (running)
                return;
            airCraftsToPlace = 4;
            Cell cell = (Cell) event.getSource();
            while (acSet[airCraftsToPlace-2] && airCraftsToPlace >=2 )  airCraftsToPlace--;
            if (playerBoard.setAirCraft(new AirCraft(airCraftsToPlace, event.getButton() == MouseButton.PRIMARY), cell.x, cell.y)) {
                AC[airCraftsToPlace-2] = cell.getAirCraft();
                switch (airCraftsToPlace) {
                    case 4: acHBox.getChildren().removeAll(ACToSet.v4);
                        acVBox.getChildren().removeAll(ACToSet.h4);
                        break;
                    case 3: acHBox.getChildren().removeAll(ACToSet.v3);
                        acVBox.getChildren().removeAll(ACToSet.h3);
                        break;
                    case 2:
                        acHBox.getChildren().removeAll(ACToSet.v2);
                        acVBox.getChildren().removeAll(ACToSet.h2);
                        break;

                }
                acSet[airCraftsToPlace-2] = true;
                boolean start = true;
                for (int k = 0; k <= 2; k++){
                    if (!PlayLayout.acSet[k]) start = false;
                }
                if (start) {
                    //start the time counter
                    timeline.setCycleCount(Animation.INDEFINITE);
                    timeline.play();
                    startGame();
                }

            }
        });
        playerBoard.acToMove = null;
        enemyBoard.acToMove = null;
        playerBoard.dragEffect();
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
        labels.setPrefHeight(100);
        labels.setPadding(new Insets(70, 50, 0, 0));
        // labels.setStyle("-fx-border-color:red;");

        //create Boards
        boards = new HBox(100, enemyBoard, playerBoard);
        boards.setPadding(new Insets(0, 0, 0,0));
        VBox centerBox = new VBox(0, labels, boards);
        centerBox.setPrefWidth(700);
        centerBox.setPrefHeight(518);


        //create turn changing label
        centerStack.getChildren().add(centerBox);
        ytlb.setText("--YOUR TURN--");
        ytlb.setTextFill(Color.YELLOW);
        ytlb.setFont(Font.font("Arial Black", FontWeight.LIGHT,50));

        etlb.setText("--ENEMY'S TURN--");
        etlb.setTextFill(Color.YELLOW);
        etlb.setFont(Font.font("Arial Black",FontWeight.LIGHT,50));

        stlb.setText("--START GAME--");
        stlb.setTextFill(Color.YELLOW);
        stlb.setFont(Font.font("Arial Black",FontWeight.LIGHT,50));

        //create Time counter
        timeText.setFont(Font.font(25));
        timeText.setFill(Color.YELLOW);
        Text subtext=new Text("TIME:");
        subtext.setFont(Font.font(25));
        subtext.setFill(Color.YELLOW);

        HBox timeBox = new HBox(100,subtext, timeText);
        timeBox.setPrefHeight(100);
        timeBox.setPrefWidth(1366);
        timeBox.setPadding(new Insets(50,50,0,400));



        //set background gif for Play Layout
        FileInputStream playBackgrInput = new FileInputStream("src/com/company/skyfall/view/PlayBackgr.jpg");
        Image playBackgrImage = new Image(playBackgrInput);
        BackgroundSize playBackgrSize = new BackgroundSize(1280,720,true,true,true,true);
        BackgroundImage playBackgr = new BackgroundImage(playBackgrImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                playBackgrSize);

        //create Play Layout
        root.setBackground(new Background(playBackgr));
        root.setTop(timeBox);
        root.setCenter(centerStack);
        Pane rightPane = new Pane();
        rightPane.setPrefWidth(300);
        root.setRight(rightPane);
//        root.getTop().setStyle("-fx-border-color:red;");
//        bulletBox.setStyle("-fx-border-color:red;");
//        boards.setStyle("-fx-border-color:red;");
//        root.getCenter().setStyle("-fx-border-color:red;");
//        root.getBottom().setStyle("-fx-border-color:red;");
//        root.getRight().setStyle("-fx-border-color:red;");
        return root;
    }

    public static void enemyMoveEasy() {
        if (overGame) return;
        while (enemyTurn) {
            if (playerBoard.getAirCrafts() == 0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("You lose");
                alert.setHeaderText("Game over!");
                alert.setContentText("YOU LOSE!");
                alert.showAndWait();
                overGame = true;
            }
            if (overGame) break;;
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
            while (true) {
                int typeOfBullet = random.nextInt(3) + 1;

                if (typeOfBullet == 1){
                    enemyTurn = cell.shootType1();
                    updateHP();
                    break;
                }
                if (typeOfBullet == 2 && enemyBoard.getNumBulletType2() > 0)
                {
                    enemyTurn = cell.shootType2();
                    updateHP();
                    enemyBoard.setNumBulletType2(enemyBoard.getNumBulletType2() - 1);
                    break;
                }
                if (typeOfBullet == 3 && enemyBoard.getNumBulletType3() > 0){
                    enemyTurn = cell.shootType3();
                    updateHP();
                    enemyBoard.setNumBulletType3(enemyBoard.getNumBulletType3() - 1);
                    break;
                }
            }
            if (!overGame) {
                centerStack.getChildren().add(ytlb);
                boards.setDisable(true);
                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                pause.setOnFinished(ex -> {boards.setDisable(false);centerStack.getChildren().remove(1);});
                pause.play();
            }
        }
    }

    public static void enemyMoveHard() {
        if (overGame) return;
        while (enemyTurn) {

            if (playerBoard.getAirCrafts() == 0){
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

            AirCraft lastAC = enemyBoard.lastAC();
            if (lastAC != null) {
                int x = random.nextInt(10);
                int y = random.nextInt(10);
                System.out.println("Thu di chuyen ac"+lastAC.getType()+" den "+x+y);
                boolean b = enemyBoard.reposAirCraft(lastAC, x, y);
                // System.out.println(b);
                if (b) {
                    enemyTurn = false;
                    for (int i = 0; i < 10 ; i++) {
                        for (int j = 0; j < 10; j++) {
                            Cell cell = enemyBoard.getCell(i, j);
                            if (cell.getAirCraft() == null)
                                cell.setFill(Color.TRANSPARENT);
                        }
                    }
                    System.out.println("repo thanh cong den");
                    return;
                }
                System.out.println(" repo k thanh cong");
            }


            //find Alive AC of playerBoard
            Cell cell = playerBoard.findAliveAirCraft();
            //found
            if (cell.x != 10){
                System.out.println("Tim dc thang con song,bi thuong la ac"+cell.getAirCraft().getType()+" o "+cell.x+cell.y);
                if (cell.equals(playerBoard.preCell)){
                    // shot on edge shared cell
                    Cell cellTmp = playerBoard.findEdgeSharedCell(cell.x, cell.y);
                    System.out.println("tim dc thang ben canh la"+cellTmp.x+cellTmp.y);
                    if (cellTmp.wasShot && enemyBoard.getNumBulletType3() > 0){
                        enemyTurn = cellTmp.shootType3();
                        System.out.println("ban dan 3 vao ac"+cellTmp.getAirCraft().getType()+" o "+cellTmp.x+cellTmp.y);
                        updateHP();
                        enemyBoard.setNumBulletType3(enemyBoard.getNumBulletType3() - 1);
                        playerBoard.preCell = cellTmp;
                    }
                    else{
                        enemyTurn = cellTmp.shootType1();
                        System.out.println("ban dan 1 vao ac"+" o "+cellTmp.x+cellTmp.y);
                        updateHP();
                        playerBoard.preCell = cellTmp;
                    }
                }
                else {
                    //shot on cell
                    if (enemyBoard.getNumBulletType3() > 0){
                        enemyTurn = cell.shootType3();
                        System.out.println("ban dan 3 vao ac"+ cell.getAirCraft().getType()+" o "+cell.x+cell.y);
                        updateHP();
                        enemyBoard.setNumBulletType3(enemyBoard.getNumBulletType3() - 1);
                        playerBoard.preCell = cell;
                    }
                    else {
                        enemyTurn = cell.shootType1();
                        System.out.println("ban dan 1 vao ac"+cell.getAirCraft().getType()+" o "+cell.x+cell.y);
                        updateHP();
                        playerBoard.preCell = cell;
                    }
                }
                continue;
            }

            //shot by bullet 2
            if (enemyBoard.getNumBulletType2() > 0){
                while (true){
                    int x = random.nextInt(9);
                    int y = random.nextInt(9);
                    if (x==0) x++;
                    if (y==0) y++;
                    if (playerBoard.isAbleToShotThisCell(x,y)){
                        System.out.println("ban dan 2 vao o "+x+y);
                        Cell cellTmp = playerBoard.getCell(x,y);
                        enemyTurn = cellTmp.shootType2();
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
            while (true){
                int x = random.nextInt(10);
                int y = random.nextInt(10);
                if (playerBoard.getCell(x,y).wasShot) continue;
                int count = playerBoard.countNumberOfEdgeShared(x, y);
                if (count == edge){
                    Cell cellTmp = playerBoard.getCell(x, y);
                    enemyTurn = cellTmp.shootType1();
                    System.out.println("ban dan 1 vao "+" o "+cellTmp.x+cellTmp.y);
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
            pause.setOnFinished(ex -> {boards.setDisable(false);centerStack.getChildren().remove(1);});
            pause.play();
        }
    }

    public static void startGame() {
        btmHbox.getChildren().removeAll(acBox);
        btmHbox.getChildren().addAll(ACHPBox.createHPBox());
        // place enemy air crafts
        int type = 4;

        while (type > 1) {
            int x = random.nextInt(10);
            int y = random.nextInt(10);

            if (enemyBoard.setAirCraft(new AirCraft(type, Math.random() < 0.5), x, y)) {
                type--;
                System.out.println(x+" "+ y);
            }
        }
        running = true;
        centerStack.getChildren().add(stlb);
        boards.setDisable(true);
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(ex -> {boards.setDisable(false);centerStack.getChildren().removeAll(stlb);});
        pause.play();
    }
    public static void updateHP(){
            String hp2 = String.valueOf(PlayLayout.AC[0].getHP());
            String hp3 = String.valueOf(PlayLayout.AC[1].getHP());
            String hp4 = String.valueOf(PlayLayout.AC[2].getHP());
            hp2 = (hp2.length()>2)?hp2:((hp2.length()>1)?" "+hp2:"  "+hp2);
            hp3 = (hp3.length()>2)?hp3:((hp3.length()>1)?" "+hp3:"  "+hp3);
            hp4 = (hp4.length()>2)?hp4:((hp4.length()>1)?" "+hp4:"  "+hp4);
            ACHPBox.hp2.setText("     "+hp2+" HP");
            ACHPBox.hp3.setText("     "+hp3+" HP");
            ACHPBox.hp4.setText("     "+hp4+" HP");
    }
}
