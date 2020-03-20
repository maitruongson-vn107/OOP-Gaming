package com.company.skyfall.model;

import javafx.scene.Parent;

public class AirCraft extends Parent {
    public int type;
    public boolean vertical;

    private int health;

    public AirCraft(int type, boolean vertical) {
        this.type = type;
        this.vertical = vertical;
        health = type;
    }

    public void hit() {
        health--;
    }

    public boolean isAlive() {
        return health > 0;
    }
}