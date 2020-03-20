package com.company.skyfall.view.play;

import com.company.skyfall.view.play.Board.Cell;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Random;

public class PlayLayout extends Application {

    private static boolean running = false;
    private static Board enemyBoard;
    private static Board playerBoard;

    private static int shipsToPlace = 5;

    private static boolean enemyTurn = false;

    private static Random random = new Random();

    public static Parent createContent() {
        BorderPane root = new BorderPane();
        root.setPrefSize(1920, 1080);
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
            if (playerBoard.placeShip(new AirCraft(shipsToPlace, event.getButton() == MouseButton.PRIMARY), cell.x, cell.y)) {
                if (--shipsToPlace == 0) {
                    startGame();
                }
            }
        });

        //create Play Layout
        Label enemyBoardLabel = new Label("Computer Board");
        Label playerBoardLabel = new Label("Player Board");
        HBox labels = new HBox(600, enemyBoardLabel, playerBoardLabel);
        labels.setPadding(new Insets(100, 50, 50, 400));
        HBox hBox = new HBox(100, enemyBoard, playerBoard);
        hBox.setPadding(new Insets(100, 50, 50,400));
        Button mainMenuBtn = new Button("Main Menu");
        mainMenuBtn.setOnAction(e -> {
            try {
                com.company.skyfall.controller.Controller.backToMainMenuFromPlay(e);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        root.setTop(labels);
        root.setCenter(hBox);
        root.setBottom(mainMenuBtn);

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
        // place enemy ships
        int type = 5;

        while (type > 0) {
            int x = random.nextInt(10);
            int y = random.nextInt(10);

            if (enemyBoard.placeShip(new AirCraft(type, Math.random() < 0.5), x, y)) {
                type--;
            }
        }

        running = true;
    }

    @Override
    public void start(Stage primaryStage) {
        //just for testing
        Scene scene = new Scene(com.company.skyfall.view.play.PlayLayout.createContent(), 1500, 900);
        primaryStage.setTitle("Play");
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
