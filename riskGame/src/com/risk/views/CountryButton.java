/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views;


import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;


/**
 *
 * @author hantoine
 */
public class CountryButton extends JLabel {

 
    private static final int buttonSize = 25;
    private static int positionX;
    private static int positionY;
    private String name;
    
 
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
    
    
    public CountryButton(int x,int y, String name) {
        super("0");
        this.name=name;
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