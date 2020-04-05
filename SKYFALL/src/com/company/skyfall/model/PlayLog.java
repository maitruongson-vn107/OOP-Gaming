package com.company.skyfall.model;

public class PlayLog {
    private Board.Cell cell;
    int damage;

    public PlayLog() {
    }

    public PlayLog(Board.Cell cell, byte typeOfBullet) {
        this.cell = cell;
        this.damage = 0;
        switch (typeOfBullet) {
            case 1:
                if (cell.getAirCraft() != null)
                    this.damage = 100;
                break;
            case 2:
                if (cell.getAirCraft() != null)
                    this.damage = 50;
                break;
            case 3:
                if (cell.getAirCraft() != null)
                    this.damage = cell.getAirCraft().getHP();
                break;

        }
    }

   @Override
   public String toString() {
      return "PlayLog{cell: "+ cell.getX()+":"+cell.getY()+"} damage: "+damage;
   }
}

