package com.company.skyfall.model;

import javafx.scene.Parent;

public class AirCraft extends Parent {
    public int type;
    public boolean vertical;
    public int HP;

    public AirCraft(int model_type, boolean vertical){
        this.type = model_type;
        this.vertical = vertical;
        this.HP = model_type*100;
    }

    public void shoot_neighbor(){
        HP = HP - 50;
    }

    public void shoot_center(){
        HP = HP - 100;
    }

    public boolean isAlive(){
        if (HP > 0) return true;
        return false;
    }
}
