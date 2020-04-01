package com.company.skyfall.view;

import com.company.skyfall.model.AirCraft;
import com.company.skyfall.model.Board;
import com.company.skyfall.model.Board.Cell;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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


public class PlayLayout  {

    private static boolean running = false;
    private static Board enemyBoard;
    private static Board playerBoard;
    private static int airCraftsToPlace = 4;
    private static boolean enemyTurn = false;
    private static Random random = new Random();
    private static int time = 0;
    private static boolean easyMode=true;
    private static int turn = 0;
    private static boolean overGame = false;
    private static Text timeText = new Text("");
    private static byte typeOfBullet = 1;

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
        typeOfBullet = 1;
        overGame = false;
        running = false;
        airCraftsToPlace = 4;
        time = 0;
        timeText.setText("");
        easyMode = level;
        BorderPane root = new BorderPane();

        VBox bulletBox = new VBox(50);
        bulletBox.setAlignment(Pos.CENTER);
        bulletBox.setPrefWidth(333);

        //create bullet type 1 button
        Button gun1Btn = new Button();
        gun1Btn.setPrefSize(225,100);
        FileInputStream gun1Input;
        gun1Input = new FileInputStream("src/com/company/skyfall/view/gun1.png"  );
        Image gun1Image;
        gun1Image = new Image(gun1Input);
        BackgroundSize gun1BtnBackgrSize;
        gun1BtnBackgrSize = new BackgroundSize(225,100,false,false,false,false);
        BackgroundImage gun1BtnBackgr;
        gun1BtnBackgr = new BackgroundImage(gun1Image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                gun1BtnBackgrSize);
        gun1Btn.setBackground(new Background(gun1BtnBackgr));

        //create bullet type 2 button
        Button gun2Btn = new Button();
        gun2Btn.setPrefSize(150,66.7);
        FileInputStream gun2Input;
        gun2Input = new FileInputStream("src/com/company/skyfall/view/gun2.png"  );
        Image gun2Image;
        gun2Image = new Image(gun2Input);
        BackgroundSize gun2BtnBackgrSize;
        gun2BtnBackgrSize = new BackgroundSize(150,66.7,false,false,false,false);
        BackgroundImage gun2BtnBackgr;
        gun2BtnBackgr = new BackgroundImage(gun2Image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                gun2BtnBackgrSize);
        gun2Btn.setBackground(new Background(gun2BtnBackgr));

        //creat bullet type 3 button
        Button gun3Btn = new Button();
        gun3Btn.setPrefSize(150,66.7);
        FileInputStream gun3Input;
        gun3Input = new FileInputStream("src/com/company/skyfall/view/gun3.png"  );
        Image gun3Image;
        gun3Image = new Image(gun3Input);
        BackgroundSize gun3BtnBackgrSize;
        gun3BtnBackgrSize = new BackgroundSize(150,66.7,false,false,false,false);
        BackgroundImage gun3BtnBackgr;
        gun3BtnBackgr = new BackgroundImage(gun3Image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                gun3BtnBackgrSize);
        gun3Btn.setBackground(new Background(gun3BtnBackgr));

        //set onAction Handler for bullet type 1 button
        gun1Btn.setOnAction(e->{
            try{
                if (typeOfBullet == 2) {
                    gun2Btn.setPrefSize(150,66.7);
                    FileInputStream gun22Input;
                    gun22Input = new FileInputStream("src/com/company/skyfall/view/gun2.png"  );
                    Image gun22Image;
                    gun22Image = new Image(gun22Input);
                    BackgroundSize gun22BtnBackgrSize;
                    gun22BtnBackgrSize = new BackgroundSize(150,66.7,false,false,false,false);
                    BackgroundImage gun22BtnBackgr;
                    gun22BtnBackgr = new BackgroundImage(gun22Image,
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundPosition.DEFAULT,
                            gun22BtnBackgrSize);
                    gun2Btn.setBackground(new Background(gun22BtnBackgr));

                }
                if (typeOfBullet == 3 ){
                    gun3Btn.setPrefSize(150,66.7);
                    FileInputStream gun32Input;
                    gun32Input = new FileInputStream("src/com/company/skyfall/view/gun3.png"  );
                    Image gun32Image;
                    gun32Image = new Image(gun32Input);
                    BackgroundImage gun32BtnBackgr;
                    gun32BtnBackgr = new BackgroundImage(gun32Image,
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundPosition.DEFAULT,
                            gun3BtnBackgrSize);
                    gun3Btn.setBackground(new Background(gun32BtnBackgr));
                }
                if (typeOfBullet != 1) {
                    gun1Btn.setPrefSize(225,100);
                    FileInputStream gun12Input;
                    gun12Input = new FileInputStream("src/com/company/skyfall/view/gun1.png"  );
                    Image gun12Image;
                    gun12Image = new Image(gun12Input);
                    BackgroundSize gun12BtnBackgrSize;
                    gun12BtnBackgrSize = new BackgroundSize(225,100,false,false,false,false);
                    BackgroundImage gun12BtnBackgr;
                    gun12BtnBackgr = new BackgroundImage(gun12Image,
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundPosition.DEFAULT,
                            gun12BtnBackgrSize);
                    gun1Btn.setBackground(new Background(gun12BtnBackgr));
                }
                typeOfBullet = 1;
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }
        });
        //set onAction Handler for bullet type 2 button
        gun2Btn.setOnAction(e->{
            try{
                if (playerBoard.getNumBulletType2() == 0 ) return;
                if (typeOfBullet == 1) {
                    gun1Btn.setPrefSize(150,66.7);
                    FileInputStream gun12Input;
                    gun12Input = new FileInputStream("src/com/company/skyfall/view/gun1.png"  );
                    Image gun12Image;
                    gun12Image = new Image(gun12Input);
                    BackgroundSize gun12BtnBackgrSize;
                    gun12BtnBackgrSize = new BackgroundSize(150,66.7,false,false,false,false);
                    BackgroundImage gun12BtnBackgr;
                    gun12BtnBackgr = new BackgroundImage(gun12Image,
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundPosition.DEFAULT,
                            gun12BtnBackgrSize);
                    gun1Btn.setBackground(new Background(gun12BtnBackgr));

                }
                if (typeOfBullet == 3 ){
                    gun3Btn.setPrefSize(150,66.7);
                    FileInputStream gun32Input;
                    gun32Input = new FileInputStream("src/com/company/skyfall/view/gun3.png"  );
                    Image gun32Image;
                    gun32Image = new Image(gun32Input);
                    BackgroundImage gun32BtnBackgr;
                    gun32BtnBackgr = new BackgroundImage(gun32Image,
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundPosition.DEFAULT,
                            gun3BtnBackgrSize);
                    gun3Btn.setBackground(new Background(gun32BtnBackgr));
                }
                if (typeOfBullet != 2) {
                    gun2Btn.setPrefSize(225,100);
                    FileInputStream gun22Input;
                    gun22Input = new FileInputStream("src/com/company/skyfall/view/gun2.png"  );
                    Image gun22Image;
                    gun22Image = new Image(gun22Input);
                    BackgroundSize gun22BtnBackgrSize;
                    gun22BtnBackgrSize = new BackgroundSize(225,100,false,false,false,false);
                    BackgroundImage gun22BtnBackgr;
                    gun22BtnBackgr = new BackgroundImage(gun22Image,
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundPosition.DEFAULT,
                            gun22BtnBackgrSize);
                    gun2Btn.setBackground(new Background(gun22BtnBackgr));
                }
                typeOfBullet = 2;
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }
        });
        //set onAction Handler for bullet type 3 button
        gun3Btn.setOnAction(e->{
            try{
                if (playerBoard.getNumBulletType3() == 0) return;
                if (typeOfBullet == 1) {
                    gun1Btn.setPrefSize(150,66.7);
                    FileInputStream gun12Input;
                    gun12Input = new FileInputStream("src/com/company/skyfall/view/gun1.png"  );
                    Image gun12Image;
                    gun12Image = new Image(gun12Input);
                    BackgroundSize gun12BtnBackgrSize;
                    gun12BtnBackgrSize = new BackgroundSize(150,66.7,false,false,false,false);
                    BackgroundImage gun12BtnBackgr;
                    gun12BtnBackgr = new BackgroundImage(gun12Image,
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundPosition.DEFAULT,
                            gun12BtnBackgrSize);
                    gun1Btn.setBackground(new Background(gun12BtnBackgr));

                }
                if (typeOfBullet == 2 ){
                    gun2Btn.setPrefSize(150,66.7);
                    FileInputStream gun22Input;
                    gun22Input = new FileInputStream("src/com/company/skyfall/view/gun2.png"  );
                    Image gun22Image;
                    gun22Image = new Image(gun22Input);
                    BackgroundSize gun22BtnBackgrSize;
                    gun22BtnBackgrSize = new BackgroundSize(150,66.7,false,false,false,false);
                    BackgroundImage gun22BtnBackgr;
                    gun22BtnBackgr = new BackgroundImage(gun22Image,
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundPosition.DEFAULT,
                            gun22BtnBackgrSize);
                    gun2Btn.setBackground(new Background(gun22BtnBackgr));
                }
                if (typeOfBullet != 3)
                {
                    gun3Btn.setPrefSize(225,100);
                    FileInputStream gun32Input;
                    gun32Input = new FileInputStream("src/com/company/skyfall/view/gun3.png"  );
                    Image gun32Image;
                    gun32Image = new Image(gun32Input);
                    BackgroundImage gun32BtnBackgr;
                    gun32BtnBackgr = new BackgroundImage(gun32Image,
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundPosition.DEFAULT,
                            gun3BtnBackgrSize);
                    gun3Btn.setBackground(new Background(gun32BtnBackgr));
                }
                typeOfBullet = 3;
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }
        });

        bulletBox.getChildren().addAll(gun1Btn,gun2Btn,gun3Btn);
        root.setLeft(bulletBox);

        enemyBoard = new Board(true, event -> {
            if (!running)
                return;
            if (overGame) return;
            Cell cell = (Cell) event.getSource();
            if (enemyBoard.preCell.equals(cell)) return;
            enemyBoard.preCell = cell;
            turn++;

            //choose type of bullet

            while (true) {

                if (typeOfBullet == 1){
                    enemyTurn = !cell.shootType1();
                    break;
                }
                if (typeOfBullet == 2 )
                {
                    enemyTurn = !cell.shootType2();
                    playerBoard.setNumBulletType3(playerBoard.getNumBulletType2() - 1);
                    //set bullet type to 1 as default
                    try {
                        gun1Btn.setPrefSize(225,100);
                        FileInputStream gun12Input;
                        gun12Input = new FileInputStream("src/com/company/skyfall/view/gun1.png"  );
                        Image gun12Image;
                        gun12Image = new Image(gun12Input);
                        BackgroundSize gun12BtnBackgrSize;
                        gun12BtnBackgrSize = new BackgroundSize(225,100,false,false,false,false);
                        BackgroundImage gun12BtnBackgr;
                        gun12BtnBackgr = new BackgroundImage(gun12Image,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.DEFAULT,
                                gun12BtnBackgrSize);
                        gun1Btn.setBackground(new Background(gun12BtnBackgr));

                        gun2Btn.setPrefSize(150,66.7);
                        FileInputStream gun22Input;
                        gun22Input = new FileInputStream("src/com/company/skyfall/view/gun2.png"  );
                        Image gun22Image;
                        gun22Image = new Image(gun22Input);
                        BackgroundSize gun22BtnBackgrSize;
                        gun22BtnBackgrSize = new BackgroundSize(150,66.7,false,false,false,false);
                        BackgroundImage gun22BtnBackgr;
                        gun22BtnBackgr = new BackgroundImage(gun22Image,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.DEFAULT,
                                gun22BtnBackgrSize);
                        gun2Btn.setBackground(new Background(gun22BtnBackgr));
                    }
                    catch (Exception ex){
                        ex.printStackTrace();
                    }
                    typeOfBullet = 1;
                    break;
                }
                if (typeOfBullet == 3 ){
                    enemyTurn = !cell.shootType3();
                    playerBoard.setNumBulletType3(playerBoard.getNumBulletType3() - 1);
                    //set bullet type to 1 as default
                    try {
                    gun1Btn.setPrefSize(225,100);
                    FileInputStream gun12Input;
                    gun12Input = new FileInputStream("src/com/company/skyfall/view/gun1.png"  );
                    Image gun12Image;
                    gun12Image = new Image(gun12Input);
                    BackgroundSize gun12BtnBackgrSize;
                    gun12BtnBackgrSize = new BackgroundSize(225,100,false,false,false,false);
                    BackgroundImage gun12BtnBackgr;
                    gun12BtnBackgr = new BackgroundImage(gun12Image,
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundPosition.DEFAULT,
                            gun12BtnBackgrSize);
                    gun1Btn.setBackground(new Background(gun12BtnBackgr));

                    gun3Btn.setPrefSize(150,66.7);
                    FileInputStream gun32Input;
                    gun32Input = new FileInputStream("src/com/company/skyfall/view/gun3.png"  );
                    Image gun32Image;
                    gun32Image = new Image(gun32Input);
                    BackgroundImage gun32BtnBackgr;
                    gun32BtnBackgr = new BackgroundImage(gun32Image,
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundPosition.DEFAULT,
                            gun3BtnBackgrSize);
                    gun3Btn.setBackground(new Background(gun32BtnBackgr));}
                    catch (Exception ex){
                        ex.printStackTrace();
                    }
                    typeOfBullet = 1;
                    break;
                }
            }
            if (enemyBoard.getAirCrafts() == 0){
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

        //create player board and set up
        playerBoard = new Board(false, event -> {
            if (running)
                return;

            Cell cell = (Cell) event.getSource();
            if (playerBoard.setAirCraft(new AirCraft(airCraftsToPlace, event.getButton() == MouseButton.PRIMARY), cell.x, cell.y)) {
                if (--airCraftsToPlace == 1) {
                    //start the time counter
                    timeline.setCycleCount(Animation.INDEFINITE);
                    timeline.play();
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
        labels.setPadding(new Insets(50, 50, 0, 0));

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

        //create Boards

        HBox boards = new HBox(100, enemyBoard, playerBoard);
        boards.setPadding(new Insets(75, 50, 0,0));
        VBox centerBox = new VBox(0, labels, boards);
        centerBox.setPrefHeight(500);
        //create Time counter
        timeText.setFont(Font.font(25));
        timeText.setFill(Color.YELLOW);
        Text subtext=new Text("TIME:");
        subtext.setFont(Font.font(25));
        subtext.setFill(Color.YELLOW);

        HBox timeBox = new HBox(100,subtext, timeText,mainMenuBtn);
        timeBox.setPadding(new Insets(100,50,0,400));


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
        root.setCenter(centerBox);
        root.setBottom(mainMenuBtn);

        return root;
    }

    private static void enemyMove() {
        while (enemyTurn) {
            int x = random.nextInt(10);
            int y = random.nextInt(10);

            Cell cell = playerBoard.getCell(x, y);
            if (playerBoard.preCell.equals(cell)) continue;
            playerBoard.preCell = cell;

            //choose type of bullet and move
           while (true) {
                int typeOfBullet = random.nextInt(3) + 1;

                if (typeOfBullet == 1){
                    enemyTurn = cell.shootType1();
                    break;
                }
                if (typeOfBullet == 2 && enemyBoard.getNumBulletType2() > 0)
                {
                    enemyTurn = cell.shootType2();
                    enemyBoard.setNumBulletType2(enemyBoard.getNumBulletType2() - 1);
                    break;
                }
                if (typeOfBullet == 3 && enemyBoard.getNumBulletType3() > 0){
                    enemyTurn = cell.shootType3();
                    enemyBoard.setNumBulletType3(enemyBoard.getNumBulletType3() - 1);
                    break;
                }
            }


            if (playerBoard.getAirCrafts() == 0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("You lose");
                alert.setHeaderText("Game over!");
                alert.setContentText("YOU LOSE!");
                alert.showAndWait();
                overGame = true;
            }
        }
    }

    private static void startGame() {
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
    }
}
