package com.company.skyfall.model;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.File;

import java.util.Random;

public class Board extends Parent {
    public VBox rows = new VBox();
    private boolean enemy;
    public static double soundLevel = 1;
    private int airCrafts = 3;
    private int numBulletType2 = 3;
    private int numBulletType3 = 1;
    public AirCraft acToMove = null;

    public Cell preCell = new Cell(10, 10, this);

    private Random random = new Random();
    private static   MediaPlayer soundPlayer = new MediaPlayer(new Media(
            new File(new File("src/com/company/skyfall/view/explosion.mp3").getAbsolutePath()).toURI().toString()
    ));    
    public static void playSound() {

        soundPlayer.stop();
        soundPlayer.play();
        soundPlayer.setVolume(Board.soundLevel);
//        try {
//            TimeUnit.MILLISECONDS.sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    }

    /**
     * Setting up and checking condition for Board
     */

    // Build Board
    public Board(boolean enemy, EventHandler<? super MouseEvent> handler) {
        this.enemy = enemy;
        for (int y = 0; y < 10; y++) {
            HBox row = new HBox();
            for (int x = 0; x < 10; x++) {
                Cell c = new Cell(x, y, this);
                c.setOnMouseClicked(handler);
                row.getChildren().add(c);
            }
            rows.getChildren().add(row);
        }

        getChildren().add(rows);
    }
    public void dragEffect(){
        for (int i = 0; i <= 9; i++)
            for (int j = 0; j <= 9; j++){
                Cell c = getCell(i,j);
                c.setOnDragDetected(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        Dragboard db = c.startDragAndDrop(TransferMode.ANY);
                        ClipboardContent ct = new ClipboardContent();
                        if (c.airCraft != null) {
                        int gap = (c.airCraft.isVertical())?(c.y-c.airCraft.getHead().y):(c.x-c.airCraft.getHead().x);
                        ct.putString(String.valueOf(gap));
                        acToMove = c.airCraft;
                        }
                        db.setContent(ct);
                        event.consume();
                    }
                });
                c.setOnDragOver(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {
                        event.acceptTransferModes(TransferMode.ANY);
                        event.consume();
                    }
                });
                c.setOnDragDropped(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {
                        if (acToMove != null) {
                            boolean ver = acToMove.isVertical();
                            int gap = Integer.parseInt(event.getDragboard().getString());
                            if (ver)
                            reposAirCraft(acToMove, c.x, c.y-gap);
                            else reposAirCraft(acToMove,c.x-gap,c.y);
                        }
                        event.setDropCompleted(true);
                        event.consume();
                    }
                });
            }
    }



    // Get position (x,y) on Board
    public Cell getCell(int x, int y) {
        return (Cell) ((HBox) rows.getChildren().get(y)).getChildren().get(x);
    }

    // Check validity of point (x,y)
    private boolean isValidPoint(int x, int y) {
        return 0 <= x && x < 10 && 0 <= y && y < 10;
    }

    // Check edge-shared cells around point (x,y)
    private boolean checkFourDirection(int x, int y) {
        int[] dx = {0, 0, 1, -1};
        int[] dy = {1, -1, 0, 0};

        for (int i = 0; i < 4; i++) {
            int xx = x + dx[i];
            int yy = y + dy[i];

            if ((isValidPoint(xx, yy))) {
                Cell cell = getCell(xx, yy);
                if (cell.airCraft != null) return false;
            }
        }
        return true;
    }

    // Check condition to set AC on (x,y)
    private boolean isOkToSetAirCraft(AirCraft airCraft, int x, int y) {
        int type = airCraft.getType();

        if (airCraft.isVertical()) {
            for (int j = y; j < y + type; j++) {
                if (!isValidPoint(x, j)) return false;

                Cell cell = getCell(x, j);
                if (cell.airCraft != null) return false;

                if (!checkFourDirection(x, j)) return false;
            }
        } else {
            for (int i = x; i < x + type; i++) {
                if (!isValidPoint(i, y)) return false;

                Cell cell = getCell(i, y);
                if (cell.airCraft != null) return false;

                if (!checkFourDirection(i, y)) return false;
            }
        }
        return true;
    }

    // Set AC on point (x,y)
    public boolean setAirCraft(AirCraft airCraft, int x, int y) {
        if (isOkToSetAirCraft(airCraft, x, y)) {
            int type = airCraft.getType();
            airCraft.setHead(getCell(x, y));
            if (airCraft.isVertical()) {
                for (int j = y; j < y + type; j++) {
                    Cell cell = getCell(x, j);
                    cell.airCraft = airCraft;
                    if (!enemy) {
                        cell.setFill(Color.WHITE);
                        cell.setStroke(Color.GREEN);
                    }
                }
            } else {
                for (int i = x; i < x + type; i++) {
                    Cell cell = getCell(i, y);
                    cell.airCraft = airCraft;
                    if (!enemy) {
                        cell.setFill(Color.WHITE);
                        cell.setStroke(Color.GREEN);
                    }
                }
            }
            return true;
        }
        return false;
    }

    //Reposition of AC
    public boolean reposAirCraft(AirCraft airCraft, int x, int y) {
        //AC being shot && difference of head position && reposition
        if (airCraft.isAlive() && airCraft.getHP() < airCraft.getType() * 100
                && airCraft.getHead() != getCell(x, y)
                && !airCraft.wasRepos()) {

            //check new position's conditions
            if (isOkToSetAirCraft(airCraft, x, y)) {
                Cell cell;
                //reset color and aircraft
                if (airCraft.isVertical()) {           

                    for (int i = 0; i < airCraft.getType(); i++) {
                        cell = getCell(airCraft.getHead().x, airCraft.getHead().y + i);
                        cell.airCraft = null;
                        cell.setFill(Color.TRANSPARENT);
                        cell.setStroke(Color.WHITE);
                    }
                } else {
                    for (int i = 0; i < airCraft.getType(); i++) {
                        cell = getCell(airCraft.getHead().x + i, airCraft.getHead().y);
                        cell.airCraft = null;
                        cell.setFill(Color.TRANSPARENT);
                        cell.setStroke(Color.WHITE);

                    }

                }

                setAirCraft(airCraft, x, y);
                airCraft.setRepos(true);
                return true;
            }
            return false;
        }
        return false;
    }

    public class Cell extends Rectangle {
        public int x, y;
        public AirCraft airCraft = null;

        private Board board;

        public Board getBoard() {
            return board;
        }

        Cell(int x, int y, Board board) {
            super(30, 30);
            this.x = x;
            this.y = y;
            this.board = board;
            setFill(Color.TRANSPARENT);
            setStroke(Color.WHITE);
        }

        /**
         * Shoot methods
         */
        //Bullet type 1
        public boolean shootType1() {
            Board.playSound();
            if (airCraft != null) {
                if (airCraft.isDie()) return false;
                airCraft.hitType1();
                if (this.getBoard().enemy )
                setFill(Color.rgb(255, 74, 54));
                 else setStroke(Color.rgb(255, 74, 54));
                if (!airCraft.isAlive()) {
                    board.airCrafts--;
                    changeImage(this);
                }

                return true;
            }
            setFill(Color.rgb(33, 233, 255));
            return false;
        }

        //Bullet type 2
        public boolean shootType2() {
            Board.playSound();
            boolean isShot = false;
            // 3*3 block
            int[] dx = {-1, -1, -1, 0, 0, 0, 1, 1, 1};
            int[] dy = {-1, 0, 1, -1, 0, 1, -1, 0, 1};

            for (int i = 0; i < 9; i++) {
                int xx = x + dx[i];
                int yy = y + dy[i];

                if ((isValidPoint(xx, yy))) {
                    Cell cell = getCell(xx, yy);

                    if (cell.airCraft != null) {
                        if (cell.airCraft.isDie()) continue;
                        isShot = true;
                        cell.airCraft.hitType2();
                        if (cell.getBoard().enemy)
                            cell.setFill(Color.rgb(255, 233, 33));
                        else cell.setStroke(Color.rgb(255, 233, 33));
                        if (!cell.airCraft.isAlive()) {
                            board.airCrafts--;
                            changeImage(this);
                        }
                    } else
                        cell.setFill(Color.rgb(33, 233, 255));
                }
            }
            return isShot;
        }

        // Bullet type 3
        public boolean shootType3() {
            Board.playSound();
            if (airCraft != null) {
                if (airCraft.isDie()) return false;
                airCraft.setDie(true);
                board.airCrafts--;
                airCraft.hitType3();
                changeImage(this);
                return true;
            } else
                   setFill(Color.rgb(44, 255, 47));
            return false;
        }
    }


    private void changeImage(Cell cell) {
        if (cell.airCraft != null) {
            Cell head = cell.airCraft.getHead();
            if (cell.airCraft.isVertical()) {
                switch (cell.airCraft.getType()) {
                    case 2:
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/AC dead/2/v2.1.png").toString()))));
                        head = getCell(head.x, head.y + 1);
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/AC dead/2/v2.2.png").toString()))));
                        break;
                    case 3:
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/AC dead/3/v3.1.png").toString()))));
                        head = getCell(head.x, head.y + 1);
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/AC dead/3/v3.2.png").toString()))));
                        head = getCell(head.x, head.y + 1);
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/AC dead/3/v3.3.png").toString()))));
                        break;
                    case 4:
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/AC dead/4/v4.1.png").toString()))));
                        head = getCell(head.x, head.y + 1);
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/AC dead/4/v4.2.png").toString()))));
                        head = getCell(head.x, head.y + 1);
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/AC dead/4/v4.3.png").toString()))));
                        head = getCell(head.x, head.y + 1);
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/AC dead/4/v4.4.png").toString()))));
                        break;
                }
            } else {
                switch (cell.airCraft.getType()) {
                    case 2:
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/AC dead/2/h2.1.png").toString()))));
                        head = getCell(head.x + 1, head.y);
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/AC dead/2/h2.2.png").toString()))));
                        break;
                    case 3:
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/AC dead/3/h3.1.png").toString()))));
                        head = getCell(head.x + 1, head.y);
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/AC dead/3/h3.2.png").toString()))));
                        head = getCell(head.x + 1, head.y);
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/AC dead/3/h3.3.png").toString()))));
                        break;
                    case 4:
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/AC dead/4/h4.1.png").toString()))));
                        head = getCell(head.x + 1, head.y);
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/AC dead/4/h4.2.png").toString()))));
                        head = getCell(head.x + 1, head.y);
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/AC dead/4/h4.3.png").toString()))));
                        head = getCell(head.x + 1, head.y);
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/AC dead/4/h4.4.png").toString()))));
                        break;
                }
            }
        }
    }

    public int getAirCrafts() {
        return airCrafts;
    }

    public int getNumBulletType2() {
        return numBulletType2;
    }

    public void setNumBulletType2(int numBulletType2) {
        this.numBulletType2 = numBulletType2;
    }

    public int getNumBulletType3() {
        return numBulletType3;
    }

    public void setNumBulletType3(int numBulletType3) {
        this.numBulletType3 = numBulletType3;
    }

}


