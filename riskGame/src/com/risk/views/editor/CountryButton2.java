/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.editor;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * Button representing a territory in the map editor.
 *
 * @author timot
 */
public class CountryButton2 extends JButton {

    /**
     * Dimension of the button
     */
    private Dimension buttonSize;

    /**
     * X position on the map panel.
     */
    private static int positionX;

    /**
     * Y position on the map panel.
     */
    private static int positionY;

    /**
     * Name of the territory that will be displayed on the button.
     */
    private String name;

    /**
     * Constructor.
     *
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
        this.setBounds(x - buttonSize.width / 2, y - buttonSize.height / 2, buttonSize.width, buttonSize.height);
    }

    /**
     * Getter of the name attribute
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    public void setPosition(int x, int y) {
        this.setBounds(x, y, this.getWidth(), this.getHeight());
    }

    /**
     * Setter of the name attribute
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
        this.setText(name);
    }

    /**
     * It gives a new location for the country in the map
     *
     * @param x position in x
     * @param y position in y
     */
    public void updateLocation(int x, int y) {
        this.positionX = x;
        this.positionY = y;
    }

    /**
     * Getter for the dimension of the button.
     *
     * @return the dimension of the button.
     */
    public Dimension getSize() {
        return this.buttonSize;
    }
}
