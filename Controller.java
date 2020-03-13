package com.company;

import java.util.Scanner;

import com.company.model.AirCraft;
import com.company.model.Board;
import com.company.model.Target;


public class Controller {
    Scanner scanner = new Scanner(System.in);

    //Constant
    final int MAX_COLUMN = 10;
    final int MAX_ROW = 10;
    public Board comBoard;          //Computer Board (left)
    public Board playerBoard;       //Player Board (right)

    /** default setting for comBoard and playerBoard **/
    public void defaultBoard() {
        comBoard.setMap(new boolean[MAX_ROW][MAX_COLUMN]);
        playerBoard.setMap(new boolean[MAX_ROW][MAX_COLUMN]);
    }



    /** set up position for player's aircraft (model 1/ 2/ 3) **/
    public void squad(Target target1, boolean vertical1, Target target2, boolean vertical2, Target target3, boolean vertical3) {

        //Set up model 1
        AirCraft airCraft1 = new AirCraft(target1, 1, vertical1);
        airCraft1.setArea(target1, 1, vertical1);
        playerBoard.setPosition(playerBoard, airCraft1.getArea());

        //Set up model 2
        AirCraft airCraft2 = new AirCraft(target2, 2, vertical2);
        airCraft2.setArea(target2, 2, vertical2);
        playerBoard.setPosition(playerBoard, airCraft2.getArea());

        //Set up model 3
        AirCraft airCraft3 = new AirCraft(target2, 2, true);
        playerBoard.getMap()[target2.getRow()][target2.getColumn()] = true;
        if(airCraft2.isVertical())
            playerBoard.getMap()[target2.getRow()][target2.getColumn()+1]
                    = playerBoard.getMap()[target2.getRow()][target2.getColumn()+2] = true;
        else
            playerBoard.getMap()[target2.getRow()+1][target2.getColumn()]
                    = playerBoard.getMap()[target2.getRow()+2][target2.getColumn()+2] = true;
    }


    public void shot(Target target, int bulletType) {

    }

    public void setBoard() {

    }

    public int gameOver() {
        return 1;
    }

    public static void main(String[] args) {

    }
}