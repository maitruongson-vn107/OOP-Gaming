package com.company.skyfall.model;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class Board extends Parent {
    private VBox rows = new VBox();
    private boolean enemy ;
    public int  airCrafts = 3;

    private Random random = new Random();

    // xay dung bang
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

    // lay vi tri o tren bang tai o (x,y)
    public Cell getCell(int x, int y) {
        return (Cell)((HBox)rows.getChildren().get(y)).getChildren().get(x);
    }

    // diem (x,y) co thuoc bang hay khong ?
    private boolean isValidPoint(int x, int y){
        if (0 <= x && x < 10 && 0 <= y && y < 10) return true;
        return false;
    }

    // diem (x,y) co ke canh voi may bay nao khac hay khong?
    public boolean checkFourDirection(int x, int y){
        int[] dx = {0, 0, 1, -1};
        int[] dy = {-1, 1, 0, 0};

        for(int i = 0; i < 3; i++){
            int xx = x + dx[i];
            int yy = y + dy[i];

            if ((isValidPoint(xx,yy))){
                Cell cell = getCell(xx,yy);
                if (cell.airCraft != null) return false;
            }
        }
        return true;
    }

    // diem (x,y) co dat duoc may bay hay khong?
    public boolean isOkToSetAirCraft(AirCraft airCraft, int x, int y){
        int type = airCraft.type;

        if (airCraft.vertical){
            for (int j = y; j < y + type; j++) {
                if (!isValidPoint(x, j)) return false;

                Cell cell = getCell(x,j);
                if (cell.airCraft != null) return false;

                if (!checkFourDirection(x,j)) return false;
            }
        }
        else {
            for (int i = x; i < x + type; i++) {
                if (!isValidPoint(i, y)) return false;

                Cell cell = getCell(i, y);
                if (cell.airCraft != null) return false;

                if (!checkFourDirection(i, y)) return false;
            }
        }
        return true;
    }

    //dat may bay tai o (x,y)
    public boolean setAirCraft(AirCraft airCraft, int x, int y){
        if (isOkToSetAirCraft(airCraft, x, y)){
            int type = airCraft.type;

            if (airCraft.vertical){
                for (int j = y; j < y + type; j++){
                    Cell cell = getCell(x, j);
                    cell.airCraft = airCraft;
                    if (!enemy){
                        cell.setFill(Color.WHITE);
                        cell.setStroke(Color.GREEN);
                    }
                }
            }
            else {
                for (int i = x; i < x + type; i++){
                    Cell cell = getCell(i, y);
                    cell.airCraft = airCraft;
                    if (!enemy){
                        cell.setFill(Color.WHITE);
                        cell.setStroke(Color.GREEN);
                    }
                }
            }
            return true;
        }
        return false;
    }

    public class Cell extends Rectangle {
        public int x, y;
        public AirCraft airCraft = null;
        public boolean wasShot = false;

        private Board board;

        public Cell(int x, int y, Board board) {
            super(30, 30);
            this.x = x;
            this.y = y;
            this.board = board;
            setFill(Color.TRANSPARENT);
            setStroke(Color.WHITE);
        }

        //DAN THUONG
        public boolean shoot_type1() {
            wasShot = true;
            setFill(Color.rgb(33, 233, 255));

            if (airCraft != null) {
                airCraft.shoot_center();
                setFill(Color.rgb(255, 74, 54));
                if (!airCraft.isAlive()) {
                    board.airCrafts--;
                }
                return true;
            }
            return false;
        }

        //DAN TOA
        public boolean shoot_type2(){
            //xet tam diem ban
            wasShot = true;
            boolean da_ban_trung = false;
            setFill(Color.rgb(33, 233, 255));

            if (airCraft != null) {
                da_ban_trung = true;
                airCraft.shoot_neighbor();
                setFill(Color.rgb(255, 74, 54));
                if (!airCraft.isAlive())
                    board.airCrafts--;
            }

            //xet 8 o ke canh
            int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
            int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

            for(int i = 0; i < 7; i++){
                int xx = x + dx[i];
                int yy = y + dy[i];

                if ((isValidPoint(xx,yy))) {
                    Cell cell = getCell(xx, yy);
                    cell.wasShot = true;
                    setFill(Color.rgb(33, 233, 255));

                    if (cell.airCraft != null) {
                        da_ban_trung = true;
                        cell.airCraft.shoot_neighbor();
                        setFill(Color.rgb(255, 74, 54));
                        if (!airCraft.isAlive())
                            board.airCrafts--;
                    }
                }
            }
            return da_ban_trung;
        }

        //DAN NO
        public boolean shoot_type3() {
            wasShot = true;
            setFill(Color.rgb(33, 233, 255));

            if (airCraft != null) {
                setFill(Color.rgb(255, 74, 54));
                while (airCraft.isAlive()) {
                    airCraft.shoot_center();
                }
                board.airCrafts--;
                return true;
            }
            return false;
        }
    }
}
