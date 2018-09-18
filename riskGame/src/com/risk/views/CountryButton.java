/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views;


import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;


/**
 *
 * @author hantoine
 */
public class CountryButton extends JLabel {
    
    private static final int buttonSize = 25;
    private static int positionX;
    private static int positionY;
    
    public CountryButton(int x,int y) {
        super("0");
        
        this.setBackground(Color.WHITE);
        this.setOpaque(true);
        this.setHorizontalAlignment(JLabel.CENTER);
        //this.setEditable(false);
      
        //this.setTransferHandler(new TransferHandler("text"));
        this.positionX=x;
        this.positionY=y;
        
        this.setLocation(positionX- buttonSize / 2, positionY - buttonSize / 2);
        this.setSize(buttonSize, buttonSize);
    }
    
    public void updateArmies(String numberOfArmies){
        
    }
    
    public void updateLocation(int x, int y){
        
    }

}