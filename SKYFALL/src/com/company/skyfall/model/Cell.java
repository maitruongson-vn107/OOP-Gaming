package com.company.skyfall.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell extends Rectangle {
    public int x, y;
    public AirCraft airCraft = null;
    public boolean wasShot = false;

    private Board board;

    public Cell(int x, int y, Board board) {
        super(50, 50);
        this.x = x;
        this.y = y;
        this.board = board;
        setFill(Color.LIGHTGRAY);
        setStroke(Color.BLACK);
    }

    public boolean shoot() {
        wasShot = true;
        setFill(Color.BLACK);

        if (airCraft != null) {
            airCraft.hit();
            setFill(Color.RED);
            if (!airCraft.isAlive()) {
                board.airCrafts--;
            }
            return true;
        }

        return false;
    }
}
