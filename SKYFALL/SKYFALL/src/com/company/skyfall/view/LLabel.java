package com.company.skyfall.view;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class LLabel extends Label {
    public LLabel(){
        setFont(Font.font(25));
        setTextFill(Color.YELLOW);
    }
    public  LLabel(String s){
        setFont(Font.font(25));
        setTextFill(Color.RED);
        setText(s);
    }
}