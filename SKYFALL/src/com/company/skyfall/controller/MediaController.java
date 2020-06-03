package com.company.skyfall.controller;

import com.company.skyfall.Main;

import com.company.skyfall.model.Board;
import javafx.scene.control.*;

public class MediaController {
    public static void musicControl(Button musicBtn){
        if (Main.musicPlayer.getVolume() == 0) {
            Main.musicPlayer.setVolume(1);
            musicBtn.setText("Music: On");
        } else {
            Main.musicPlayer.setVolume(0);
            musicBtn.setText("Music: Off");
        }
    }
    public static  void soundControl(Button soundBtn){
        if (Board.soundLevel == 0) {
            Board.soundLevel = 1;
            soundBtn.setText("Sound: On");
        } else {
            Board.soundLevel = 0;
            soundBtn.setText("Sound: Off");
        }
    }
}