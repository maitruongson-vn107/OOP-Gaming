package com.company.skyfall.model;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

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
                if (cell.getAirCraft() != null) {
                    if (cell.getAirCraft().getHP() >= 100)
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

    @Override
    public String toString() {
        return "Shoot in {cell: " + cell.x + ":" + cell.y + "} damage: " + damage;
    }


}

