/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.game;

import java.awt.Color;
import javax.swing.JLabel;

/**
 * JLabel that represents a territory in the map
 *
 * @author hantoine
 */
public class TerritoryLabel extends JLabel {

    /**
     * Size of the button that represents the territory
     */
    private static final int buttonSize = 25;
    /**
     * Position in the x axe
     */
    private static int positionX;
    /**
     * Position in the y axe
     */
    private static int positionY;
    /**
     * Name of the territory in the button
     */
    private String name;

    /**
     * Constructor
     *
     * @param x position in x
     * @param y position y
     * @param name name of the territory
     */
    public TerritoryLabel(int x, int y, String name) {
        super("0");
        this.name = name;
        this.setBackground(Color.WHITE);
        this.setOpaque(true);
        this.setHorizontalAlignment(JLabel.CENTER);

        this.positionX = x;
        this.positionY = y;

        this.setLocation(positionX - buttonSize / 2, positionY - buttonSize / 2);
        this.setSize(buttonSize, buttonSize);
    }

    /**
     * Getter of the name attribute
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter of the name attribute
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * It increase or decrease the armies that a player has in a territory
     *
     * @param numberOfArmies new number
     */
    public void updateArmies(String numberOfArmies) {

    }

    /**
     * It gives a new location for the territory in the map
     *
     * @param x position in x
     * @param y position in y
     */
    public void updateLocation(int x, int y) {

    }

}
