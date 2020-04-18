package com.company.skyfall.model;

import javafx.scene.Parent;

public class AirCraft extends Parent {
    private int type;
    private boolean vertical;
    private int HP;
    private boolean die = false;
    private Board.Cell head;
  //  private  boolean repos = false;

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

    /** getter and setter */
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isVertical() {
        return vertical;
    }

    public void setVertical(boolean vertical) {
        this.vertical = vertical;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public boolean isDie() {
        return die;
    }

    public void setDie(boolean die) {
        this.die = die;
    }

    public Board.Cell getHead() {
        return head;
    }

    public void setHead(Board.Cell head) {
        this.head = head;
    }
    public boolean lostHP() {
        return HP < type*100;
    }
//    public boolean wasRepos() {
//        return repos;
//    }
//
//    public void setRepos(boolean repos) {
//        this.repos = repos;
//    }
}
