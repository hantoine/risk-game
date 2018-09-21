/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.map;

import com.risk.models.Board;
import com.risk.models.Country;
import com.risk.views.menu.PlayerPanel;
import java.awt.Color;
import java.awt.geom.Line2D;
import java.util.LinkedList;
import javax.swing.JLabel;


/**
 *
 * @author hantoine
 */
public class CountryButton extends JLabel {

    LinkedList<Line2D> adj;
    private static final int buttonSize = 25;
    private static int positionX;
    private static int positionY;
    private String name;

    public void uploadAdj(Board b) {

        for (Country c : b.getGraphTerritories().values()) {
            
        }

    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    public CountryButton(int x, int y, String name) {
        super("0");
        this.name = name;
        this.setBackground(Color.WHITE);
        this.setOpaque(true);
        this.setHorizontalAlignment(JLabel.CENTER);
        //this.setEditable(false);

        //this.setTransferHandler(new TransferHandler("text"));
        this.positionX = x;
        this.positionY = y;

        this.setLocation(positionX - buttonSize / 2, positionY - buttonSize / 2);
        this.setSize(buttonSize, buttonSize);
    }

    public void updateArmies(String numberOfArmies) {

    }

    public void updateLocation(int x, int y) {

    }

}
