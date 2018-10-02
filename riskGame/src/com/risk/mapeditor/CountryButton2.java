/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.mapeditor;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;

/**
 *
 * @author timot
 */
public class CountryButton2 extends JLabel {

    private Dimension buttonSize;
    private static int positionX;
    private static int positionY;
    private String name;
    private String continentName;

    /**
     * Constructor
     * @param x position in x
     * @param y position y
     * @param name name of the country
     * @param dim
     */
    public CountryButton2(int x, int y, String name, Dimension dim) {
        super(name);
        this.name = name;
        this.setBackground(Color.WHITE);
        this.setOpaque(true);
        this.setHorizontalAlignment(JLabel.CENTER);
    
        this.buttonSize = dim;
        
        this.positionX = x;
        this.positionY = y;

        this.setSize(buttonSize.width, buttonSize.height);
        this.setBounds(x-buttonSize.width/2, y-buttonSize.height/2, buttonSize.width, buttonSize.height);
    }

    /**
     * Getter of the name attribute
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter of the name attribute
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * It gives a new location for the country in the map
     * @param x position in x
     * @param y position in y
     */
    public void updateLocation(int x, int y) {
        this.positionX = x;
        this.positionY = y;
    }
    
    public void updateContinentName(String newName) {
        this.continentName = newName;
    }

}
