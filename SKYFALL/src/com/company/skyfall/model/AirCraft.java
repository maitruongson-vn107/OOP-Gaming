package com.company.skyfall.model;

import javafx.scene.Parent;

public class AirCraft extends Parent {
    public int type;
    public boolean vertical;
    public int HP;
    public boolean die = false;

    public AirCraft(int type, boolean vertical){
        this.type = type;
        this.vertical = vertical;
        this.HP = type*100;
    }

    /** Hàm xử lý HP khi BỊ TRÚNG ĐẠN */

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
    //kiem tra dieu kien song
    public boolean isAlive(){

        if (HP > 0 ) return true;
        if (die == false) {
            die = true;
            return false;
        }
        return true;
    }
}
