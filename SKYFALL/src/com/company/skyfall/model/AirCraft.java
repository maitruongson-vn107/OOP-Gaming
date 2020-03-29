package com.company.skyfall.model;

import javafx.scene.Parent;

public class AirCraft extends Parent {
    public int type;
    public boolean vertical;
    private int HP;
    public boolean die = false;
    public Board.Cell head;

    public AirCraft(int type, boolean vertical){
        this.type = type;
        this.vertical = vertical;
        this.HP = type*100;
    }

    /** AC being hit by each type of bullet */

    //hit by bullet type 1
    public void hitType1(){
        if (HP > 100) HP -= 100;
        else HP = 0;
    }
    //hit by bullet type 2
    public void hitType2(){
        if (HP > 50) HP -= 50;
        else HP = 0;
    }
    //hit by bullet type 3
    public void hitType3(){
        HP = 0;
    }
    //AC was really ALive or not
    public boolean isAlive(){
        if (HP > 0 ) return true;
        if (die == false) {
            die = true;
            return false;
        }
        return true;
    }
}
