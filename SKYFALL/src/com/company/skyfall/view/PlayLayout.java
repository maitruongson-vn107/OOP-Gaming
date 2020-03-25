package com.company.skyfall.view;

import com.company.skyfall.model.AirCraft;
import com.company.skyfall.model.Board;
import com.company.skyfall.model.Board.Cell;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.util.Random;

import static com.company.skyfall.model.HighScoreHandler.*;
import com.company.skyfall.controller.*;


public class PlayLayout  {

    private static boolean running = false;
    private static Board enemyBoard;
    private static Board playerBoard;
    private static int airCraftsToPlace = 3;
    private static boolean enemyTurn = false;
    private static Random random = new Random();
    private static int time = 0;
    private static boolean easyMode=true;
    private static int turn = 0;
    private static Text timeText = new Text("");

    //Make time counter appearing in root.Left
    private static Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1),ev->{
        String min = (time/60<10?"0":"") + String.valueOf(time/60) ;
        String sec = (time%60<10?"0":"") + String.valueOf(time%60);
        timeText.setText(min+":"+sec);
        timeText.setFont(Font.font(25));
        timeText.setFill(Color.YELLOW);
        time++;
    }));

    public static Parent createContent(boolean level) throws Exception {
        time = 0;
        easyMode = level;
        BorderPane root = new BorderPane();
        enemyBoard = new Board(true, event -> {
            if (!running)
                return;

            Cell cell = (Cell) event.getSource();
            if (playerBoard.preCell.equals(cell)) return;
            turn++;

            //choose type of bullet
            int typeOfBullet;
            while (true) {
                typeOfBullet = random.nextInt(3) + 1;
                if (typeOfBullet == 1){
                    enemyTurn = !cell.shootType1();
                    break;
                }
                if (typeOfBullet == 2 && playerBoard.numBulletType2 > 0)
                {
                    enemyTurn = !cell.shootType2();
                    break;
                }
                if (typeOfBullet == 3 && playerBoard.numBulletType3 > 0){
                    enemyTurn = !cell.shootType3();
                    break;
                }
            }

            if (enemyBoard.airCrafts == 0){
                Alert winAlert = new Alert(Alert.AlertType.INFORMATION);
                winAlert.setTitle("You win");
                winAlert.setHeaderText("Congrats!");
                winAlert.setContentText("YOU WIN!");
                winAlert.showAndWait();
                TextField nameField;
                try {
                    // if player got high score
                    // make a dialog enter player's name
                    if(isTop(turn,time,easyMode)) {
                        TextInputDialog dialog = new TextInputDialog();
                        dialog.setTitle("Enter your name");
                        dialog.setHeaderText("You got a high score\nPlease enter your name with no space");
                        dialog.setContentText("Your name:");
                        dialog.showAndWait();
                        nameField = dialog.getEditor();
                        if (!easyMode){
                            writeHighScoreHard(nameField.getText(),turn,time);
                        } else {
                            writeHighScoreEasy(nameField.getText(),turn,time);
                        }
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            if (enemyTurn)
                enemyMove();
        });

        playerBoard = new Board(false, event -> {
            if (running)
                return;

            Cell cell = (Cell) event.getSource();
            if (playerBoard.setAirCraft(new AirCraft(airCraftsToPlace, event.getButton() == MouseButton.PRIMARY), cell.x, cell.y)) {
                if (--airCraftsToPlace == 0) {
                    startGame();
                }
            }
        });

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
        labels.setPadding(new Insets(50, 50, 0, 400));

        //create Main Menu Button in Play Scene
        Button mainMenuBtn = new Button("Main Menu");
        mainMenuBtn.setPrefSize(225,100);

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
                com.company.skyfall.controller.Controller.backToMainMenuFromPlay(e);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        //create Boards
        HBox boards = new HBox(100, enemyBoard, playerBoard);
        boards.setPadding(new Insets(75, 50, 50,400));
        VBox centerBox = new VBox(0, labels, boards);

        //create Time counter
        timeText.setFont(Font.font(25));
        timeText.setFill(Color.YELLOW);
        Text subtext=new Text("TIME:");
        subtext.setFont(Font.font(25));
        subtext.setFill(Color.YELLOW);

        HBox timeBox = new HBox(100,subtext, timeText,mainMenuBtn);
        timeBox.setPadding(new Insets(100,50,0,400));


        //set background gif for Play Layout
        FileInputStream playBackgrInput = new FileInputStream("src/com/company/skyfall/view/PlayBackgr.jpg"  );
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
        root.setCenter(centerBox);
        root.setBottom(mainMenuBtn);

        return root;
    }

    private static void enemyMove() {
        while (enemyTurn) {
            int x = random.nextInt(10);
            int y = random.nextInt(10);

            Cell cell = playerBoard.getCell(x, y);
            if (enemyBoard.preCell.equals(cell)) continue;
            enemyBoard.preCell = cell;

            //choose type of bullet and move
            int typeOfBullet;
            while (true) {
                typeOfBullet = random.nextInt(3) + 1;
                if (typeOfBullet == 1){
                    enemyTurn = !cell.shootType1();
                    break;
                }
                if (typeOfBullet == 2 && enemyBoard.numBulletType2 > 0)
                {
                    enemyTurn = !cell.shootType2();
                    break;
                }
                if (typeOfBullet == 3 && enemyBoard.numBulletType3 > 0){
                    enemyTurn = !cell.shootType3();
                    break;
                }
            }


            if (playerBoard.airCrafts == 0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("You lose");
                alert.setHeaderText("Game over!");
                alert.setContentText("YOU LOSE!");
                alert.showAndWait();
            }
        }
    }

    private static void startGame() {
        // place enemy air crafts
        int type = 3;

        while (type > 0) {
            int x = random.nextInt(10);
            int y = random.nextInt(10);

            if (enemyBoard.setAirCraft(new AirCraft(type, Math.random() < 0.5), x, y)) {
                type--;
            }
        }

        //start the time counter
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        running = true;
    }
}
