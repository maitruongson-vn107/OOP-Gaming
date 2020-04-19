package com.company.skyfall.model;

import java.io.*;
import java.lang.NullPointerException;
public class HighScoreHandler  {
    //Take high score infor from hard-level-high-score file
    public static String[][] hardReader()throws  Exception{
        String[][] s2 = new String[5][3];
        BufferedReader br = new BufferedReader(new FileReader("src/com/company/skyfall/view/hard_high_score.txt"));
        for (int i = 0;i<=4;i++) {
            for (int j = 0; j <= 2; j++)
                s2[i][j] = br.readLine();
        }
        br.close();
        return s2;
    }
    //Take high score infor from easy-level-high-score file
    public static String[][] easyReader()throws  Exception{
        String[][] s2 = new String[5][3];
        BufferedReader br = new BufferedReader(new FileReader("src/com/company/skyfall/view/easy_high_score.txt"));
        for (int i = 0;i<=4;i++) {
            for (int j = 0; j <= 2; j++)
                s2[i][j] = br.readLine();
        }
        br.close();
        return s2;
    }
    //Check whether a result with certain turn, time, easyMode is high score or not
    public static boolean isTop(int turn,int time,boolean easyMode)throws  Exception{
        if (!easyMode) {
            String[][] topdet = new String[5][3];
            topdet=hardReader();
            if (Integer.parseInt(topdet[4][1]) == 0) return true;
            if (turn < Integer.parseInt(topdet[4][1])) return true;
            else if (turn == Integer.parseInt(topdet[4][1]) && time < Integer.parseInt(topdet[4][2])) return true;
            return false;
        }
        else {
            String[][] topdet = new String[5][3];
            topdet=easyReader();
            if (Integer.parseInt(topdet[4][1]) == 0) return true;
            if (turn < Integer.parseInt(topdet[4][1])) return true;
            else if (turn == Integer.parseInt(topdet[4][1]) && time < Integer.parseInt(topdet[4][2])) return true;
            return false;
        }
    };
    //save the high score infor into file
    public static void writeHighScoreHard(String name,int turn,int time)throws  Exception{
        String[][] topdet = new String[5][3];
        topdet = hardReader();
        BufferedWriter bw = new BufferedWriter(new FileWriter("src/com/company/skyfall/view/hard_high_score.txt"));
        int i=4;
        while (Integer.parseInt(topdet[i][1]) == 0) {
            i--; if (i == -1) break;
        }
        if (i < 4) {
            i++;
            topdet[i][0]=name;
            topdet[i][1]=String.valueOf(turn);
            topdet[i][2]=String.valueOf(time);
            for(i = 0; i <=4; i++){
                bw.write(topdet[i][0]+"\n");
                bw.write(topdet[i][1]+"\n");
                bw.write(topdet[i][2]+"\n");
                //  bw.write(topdet[i][0]+ " " + topdet[i][1] + " " + topdet[i][2] + "\n");
            }
            bw.close();
            return;
        }
        while (turn < Integer.parseInt(topdet[i][1]) || (turn == Integer.parseInt(topdet[i][1]) && time < Integer.parseInt(topdet[i][2])) )
        { i--; if (i == -1) break; } ;
        for (int j = 4; j>= i+2; j--){
            topdet[j][0] = topdet[j-1][0];
            topdet[j][1] = topdet[j-1][1];
            topdet[j][2] = topdet[j-1][2];
        }
        topdet[i+1][0] = name;
        topdet[i+1][1] = String.valueOf(turn);
        topdet[i+1][2] = String.valueOf(time);
        for(i = 0; i <=4; i++){
            bw.write(topdet[i][0]+"\n");
            bw.write(topdet[i][1]+"\n");
            bw.write(topdet[i][2]+"\n");
            //   bw.write(topdet[i][0]+ " " + topdet[i][1] + " " + topdet[i][2] + "\n");
        }
        bw.close();
    }
    //save the high score infor into file
    public static void writeHighScoreEasy(String name,int turn,int time)throws  Exception{
        String[][] topdet = new String[5][3];
        topdet = easyReader();
        BufferedWriter bw = new BufferedWriter(new FileWriter("src/com/company/skyfall/view/easy_high_score.txt"));
        int i=4;
        while (Integer.parseInt(topdet[i][1]) == 0) {
            i--; if (i == -1) break;
        }
        if (i < 4) {
            i++;
            topdet[i][0]=name;
            topdet[i][1]=String.valueOf(turn);
            topdet[i][2]=String.valueOf(time);
            for(i = 0; i <=4; i++){
                bw.write(topdet[i][0]+"\n");
                bw.write(topdet[i][1]+"\n");
                bw.write(topdet[i][2]+"\n");;
                // bw.write(topdet[i][0]+ " " + topdet[i][1] + " " + topdet[i][2] + "\n");
            }
            bw.close();
            return;
        }
        while (turn < Integer.parseInt(topdet[i][1]) || (turn == Integer.parseInt(topdet[i][1]) && time < Integer.parseInt(topdet[i][2])) )
        { i--; if (i == -1) break; } ;
        for (int j = 4; j>= i+2; j--){
            topdet[j][0] = topdet[j-1][0];
            topdet[j][1] = topdet[j-1][1];
            topdet[j][2] = topdet[j-1][2];
        }
        topdet[i+1][0] = name;
        topdet[i+1][1] = String.valueOf(turn);
        topdet[i+1][2] = String.valueOf(time);
        for(i = 0; i <=4; i++){
            bw.write(topdet[i][0]+"\n");
            bw.write(topdet[i][1]+"\n");
            bw.write(topdet[i][2]+"\n");
            // bw.write(topdet[i][0]+ " " + topdet[i][1] + " " + topdet[i][2] + "\n");
        }
        bw.close();
    }

//    public static void main(String[] args)throws Exception {
//        writeHighScoreEasy("huy",3,3);
//    }

}