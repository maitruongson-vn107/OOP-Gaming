package com.company.skyfall.view;

import com.company.skyfall.model.AirCraft;
import com.company.skyfall.model.Board;
import com.company.skyfall.model.Cell;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class PlayLayout  {

    private static boolean running = false;
    private static Board enemyBoard;
    private static Board playerBoard;

    private static int airCraftsToPlace = 3;

    private static boolean enemyTurn = false;

    private static Random random = new Random();

    private static int time = 0;
    private static BorderPane root;

    private static Text timetext=new Text("00:00");
    //Make time counter appearing in root.Left
    private static Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1),ev->{
        String min = (time/60<10?"0":"") + String.valueOf(time/60) ;
        String sec = (time%60<10?"0":"") + String.valueOf(time%60);
        timetext.setText(min+":"+sec);
        timetext.setFont(Font.font(25));
        timetext.setFill(Color.YELLOW);
      //  root.setLeft(timetext);
        time++;
    }));

    public static Parent createContent()throws Exception {

        root = new BorderPane();
       // root.setPrefSize(1920, 1080);
        enemyBoard = new Board(true, event -> {
            if (!running)
                return;

            Cell cell = (Cell) event.getSource();
            if (cell.wasShot)
                return;

            enemyTurn = !cell.shoot();

            if (enemyBoard.airCrafts == 0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("You win");
                alert.setHeaderText("Congrats!");
                alert.setContentText("YOU WIN!");
                alert.showAndWait();
            }
            if (enemyTurn)
                enemyMove();
        });

        playerBoard = new Board(false, event -> {
            if (running)
                return;

            Cell cell = (Cell) event.getSource();
            if (playerBoard.placeAirCraft(new AirCraft(airCraftsToPlace, event.getButton() == MouseButton.PRIMARY), cell.x, cell.y)) {
                if (--airCraftsToPlace == 0) {
                    startGame();
                }
            }
        });

        //create Play Layout
        Label enemyBoardLabel = new Label("Computer Board");
        enemyBoardLabel.setTextFill(Color.YELLOW);
        enemyBoardLabel.setFont(Font.font(25));
        enemyBoardLabel.setLabelFor(enemyBoard);

        Label playerBoardLabel = new Label("Player Board");
        playerBoardLabel.setTextFill(Color.YELLOW);
        playerBoardLabel.setFont(Font.font(25));
        playerBoardLabel.setLabelFor(enemyBoard);

        //Make Main Menu Button in Play Scene
        Button mainMenuBtn = new Button("Main Menu");
        mainMenuBtn.setPrefSize(260,100);

        //Set background image for MainMenu Button in Play Scene and format the text insides
        FileInputStream btnInput = new FileInputStream("src/com/company/skyfall/view/ButtonBackgr.png"  );
        Image btnBackgrImage = new Image(btnInput);
        BackgroundSize btnBackgrSize=new BackgroundSize(260,100,false,false,false,false);
        BackgroundImage btnBackgr= new BackgroundImage(btnBackgrImage,
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
        HBox labels = new HBox(250, enemyBoardLabel, playerBoardLabel);
        labels.setPadding(new Insets(0, 50, 0, 400));

        timetext.setFont(Font.font(25));
        timetext.setFill(Color.YELLOW);

        Text subtext=new Text("TIME:");
        subtext.setFont(Font.font(25));
        subtext.setFill(Color.YELLOW);

        HBox timebox=new HBox(100,subtext,timetext,mainMenuBtn);
        timebox.setPadding(new Insets(100,50,0,400));
        VBox topbox=new VBox(timebox,labels);
        HBox hBox = new HBox(100, enemyBoard, playerBoard);
        hBox.setPadding(new Insets(100, 50, 50,400));



        // Set Background gif for Play Scene
        FileInputStream playBackgrInput = new FileInputStream("src/com/company/skyfall/view/PlayBackgr.gif"  );
        Image playBackgrImage = new Image(playBackgrInput);
        BackgroundSize playBackgrSize=new BackgroundSize(1366,768,true,true,true,true);
        BackgroundImage playBackgr = new BackgroundImage(playBackgrImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                playBackgrSize);
        root.setBackground(new Background(playBackgr));
        root.setTop(topbox);
        root.setCenter(hBox);
   //     root.setBottom(mainMenuBtn);

        return root;
    }

    private static void enemyMove() {
        while (enemyTurn) {
            int x = random.nextInt(10);
            int y = random.nextInt(10);

            Cell cell = playerBoard.getCell(x, y);
            if (cell.wasShot)
                continue;

            enemyTurn = cell.shoot();

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

            if (enemyBoard.placeAirCraft(new AirCraft(type, Math.random() < 0.5), x, y)) {
                type--;
            }
        };

        //start the time counter
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        running = true;

    }

//    @Override
//    public void start(Stage primaryStage) {
//        //just for testing
//        Scene scene = new Scene(PlayLayout.createContent());
//        primaryStage.setTitle("Play");
//        primaryStage.setScene(scene);
//        primaryStage.setFullScreen(true);
//        primaryStage.show();
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
}
