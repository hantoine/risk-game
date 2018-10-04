/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.menu;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * It represents the view for the attributes of a player
 * @author Nellybett
 */
public class PlayerPanel extends JPanel {

    private String name;
    private JTextField playerNameTextField;
    private JButton colorButton;
    private DeletableButton delButton;
    
    public PlayerPanel() {
        setSize(200, 500);
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
     * Getter of the playerNameTextField attribute
     * @return the playerNameTextField
     */
    public JTextField getPlayerNameTextField() {
        return playerNameTextField;
    }

    /**
     * Setter of the playerNameTextField attribute
     * @param playerNameTextField the playerNameTextField to set
     */
    public void setPlayerNameTextField(JTextField playerNameTextField) {
        this.playerNameTextField = playerNameTextField;
    }

    /**
     * Getter of the colorButton attribute
     * @return the colorButton
     */
    public JButton getColorButton() {
        return colorButton;
    }

    /**
     * Setter of the colorButton attribute
     * @param colorButton the colorButton to set
     */
    public void setColorButton(JButton colorButton) {
        this.colorButton = colorButton;
    }

    /**
     * Getter of the delButton attribute 
     * @return the delButton
     */
    public DeletableButton getDelButton() {
        return delButton;
    }

    /**
     * Setter of the delButton attribute
     * @param delButton the delButton to set
     */
    public void setDelButton(DeletableButton delButton) {
        this.delButton = delButton;
    }

}
