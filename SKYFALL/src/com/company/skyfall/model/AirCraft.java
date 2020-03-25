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

    /** Hàm xử lý HP khi BỊ TRÚNG ĐẠN */

    //hit by bullet type 1
    public void hitType1(){
        if(HP > 100) HP -= 100;
        else HP = 0;
    }
    //hit by bullet type 2
    public void hitType2(){
        if(HP > 50) HP -= 50;
        else HP = 0;
    }
    //hit by bullet type 3
    public void hitType3(){
        HP = 0;
    }
    //kiem tra dieu kien song
    public boolean isAlive(){
        return HP > 0;
    }
}
