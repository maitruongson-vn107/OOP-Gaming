package com.company.skyfall.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

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

    public boolean shoot() {
        wasShot = true;
        setFill(Color.rgb(33, 233, 255));

        if (airCraft != null) {
            airCraft.hit();
            setFill(Color.rgb(255, 74, 54));
            if (!airCraft.isAlive()) {
                board.airCrafts--;
            }
            return true;
        }

        return false;
    }
}
