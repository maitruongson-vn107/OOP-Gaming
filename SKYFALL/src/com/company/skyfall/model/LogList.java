package com.company.skyfall.model;

import java.util.ArrayList;

public class LogList {
    ArrayList<PlayLog> arrayLog;
    public LogList(){
        arrayLog = new ArrayList<>();
    }
    public void add(PlayLog playlog){
        arrayLog.add(playlog);

    }

}
