package com.company.skyfall.model;

public class PlayLog {
    private Board.Cell cell;
    private int turn;
    private int damage;
    private String player;
    private byte typeOfBullet;
    private Board board;

    public PlayLog() {
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public PlayLog(Board.Cell cell, byte typeOfBullet, int turn, String player) {
        this.cell = cell;
        this.damage = 0;
        this.turn = turn;
        this.player = player;
        this.typeOfBullet = typeOfBullet;

        switch (typeOfBullet) {
            case 1:
                if (cell.getAirCraft() != null) {
                    if (cell.airCraft.getHP() >= 100)
                        this.damage = 100;
                    else if ((cell.getAirCraft().getHP() > 0) && (cell.getAirCraft().getHP() < 100))
                        this.damage = cell.getAirCraft().getHP();
                }
                break;
            case 2:
                this.damage = 50 * cell.countNumberOfCellHaveAirCraft();
                break;
            case 3:
                if (cell.getAirCraft() != null) {
                    this.damage = cell.getAirCraft().getHP();

                }
                break;
        }
    }

    public String getPlayer() {
        return player;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public String convertCellName(Board.Cell cell) {
        String name = "";
        char a = (char) ('A' + cell.x);
        name = a + String.valueOf(cell.y + 1);
        return name;
    }

    public int getTurn() {
        return turn;
    }

    public String status() {
        if (damage == 0) return "Miss";
        else {
            if (typeOfBullet == 3) {
                return "Kill airCraft type: " + cell.airCraft.getType();
            } else if (typeOfBullet == 2) {

                return "On target";
            } else {
                //dan 1
                if (cell.airCraft.getHP() <= 100) {
                    return "Kill airCraft type: " + cell.airCraft.getType();
                } else {
                    return "On target";
                }
            }
        }
    }

    public int getDamage() {
        return damage;
    }


}

