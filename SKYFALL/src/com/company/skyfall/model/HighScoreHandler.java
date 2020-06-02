package com.company.skyfall.model;

import java.io.*;
import java.lang.NullPointerException;
public class HighScoreHandler  {
    //Take high score infor from hard-level-high-score file
    public static String[][] hardReader() throws  Exception{
        String[][] topDet = new String[5][3];
        BufferedReader bufReader = new BufferedReader(new FileReader("src/com/company/skyfall/resources/highscorefiles/hard_high_score.txt"));
        for (int i = 0;i<=4;i++) {
            for (int j = 0; j <= 2; j++)
                topDet[i][j] = bufReader.readLine();
        }
        bufReader.close();
        return topDet;
    }
    //Take high score infor from easy-level-high-score file
    public static String[][] easyReader()throws  Exception{
        String[][] topDet = new String[5][3];
        BufferedReader br = new BufferedReader(new FileReader("src/com/company/skyfall/resources/highscorefiles/easy_high_score.txt"));
        for (int i = 0;i<=4;i++) {
            for (int j = 0; j <= 2; j++)
                topDet[i][j] = br.readLine();
        }
        br.close();
        return topDet;
    }
    //Check whether a result with certain turn, time, easyMode is high score or not
    public static boolean isTop(int turn,int time,boolean easyMode) throws  Exception{
        if (!easyMode) {
            String[][] topDet =  hardReader();
            if (Integer.parseInt(topDet[4][1]) == 0) return true;
            if (turn < Integer.parseInt(topDet[4][1])) return true;
            else if (turn == Integer.parseInt(topDet[4][1]) && time < Integer.parseInt(topDet[4][2])) return true;
            return false;
        }
        else {
            String[][] topDet = easyReader();
            if (Integer.parseInt(topDet[4][1]) == 0) return true;
            if (turn < Integer.parseInt(topDet[4][1])) return true;
            else if (turn == Integer.parseInt(topDet[4][1]) && time < Integer.parseInt(topDet[4][2])) return true;
            return false;
        }
    };
    //save the high score infor into file
    public static void writeHighScoreHard(String name,int turn,int time)throws  Exception{
        String[][] topDet = hardReader();
        BufferedWriter bufWriter = new BufferedWriter(new FileWriter("src/com/company/skyfall/resources/highscorefiles/hard_high_score.txt"));
        for(int i = 0; i <= 4; i++){
            int cmpTurn = Integer.parseInt(topDet[i][1]);
            int cmpTime = Integer.parseInt(topDet[i][2]);
            if (cmpTurn == 0 || turn < cmpTurn || (turn == cmpTurn && time < cmpTime))
            {
                for (int j = 4; j >= i+1; j--){
                    topDet[j][0] = topDet[j-1][0];
                    topDet[j][1] = topDet[j-1][1];
                    topDet[j][2] = topDet[j-1][2];
                }
                topDet[i][0] = name;
                topDet[i][1] = String.valueOf(turn);
                topDet[i][2] = String.valueOf(time);
                break;
            }
        }
        for(int j = 0; j <=4; j++){
            bufWriter.write(topDet[j][0]+"\n");
            bufWriter.write(topDet[j][1]+"\n");
            bufWriter.write(topDet[j][2]+"\n");
        }
        bufWriter.close();
    }
    //save the high score infor into file
    public static void writeHighScoreEasy(String name,int turn,int time)throws  Exception{
        String[][] topDet = easyReader();
        BufferedWriter bufWriter = new BufferedWriter(new FileWriter("src/com/company/skyfall/resources/highscorefiles/easy_high_score.txt"));
        for(int i = 0; i <= 4; i++){
            int cmpTurn = Integer.parseInt(topDet[i][1]);
            int cmpTime = Integer.parseInt(topDet[i][2]);
            if (cmpTurn == 0 || turn < cmpTurn || (turn == cmpTurn && time < cmpTime))
            {
                for (int j = 4; j >= i+1; j--){
                    topDet[j][0] = topDet[j-1][0];
                    topDet[j][1] = topDet[j-1][1];
                    topDet[j][2] = topDet[j-1][2];
                }
                topDet[i][0] = name;
                topDet[i][1] = String.valueOf(turn);
                topDet[i][2] = String.valueOf(time);
                break;
            }
        }
        for(int j = 0; j <=4; j++){
            bufWriter.write(topDet[j][0]+"\n");
            bufWriter.write(topDet[j][1]+"\n");
            bufWriter.write(topDet[j][2]+"\n");
        }
        bufWriter.close();
    }

}