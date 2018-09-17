/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views;

import com.risk.models.Country;
import java.awt.Insets;
import java.awt.datatransfer.Transferable;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.TransferHandler;
import javax.swing.text.JTextComponent;

/**
 *
 * @author hantoine
 */
public class CountryButton extends JTextField {
    
    private static final int buttonSize = 25;
    private static int positionX;
    private static int positionY;
    
    public CountryButton(int x,int y) {
        super("0");
        
        
        this.setEditable(false);
        this.setDragEnabled(true);
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