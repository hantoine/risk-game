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
 *
 * @author Nellybett
 */
public class PlayerPanel extends JPanel {
    private String name;
    private JTextField playerNameTextField;
    private JButton colorButton;
    private DeletableButton delButton;

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

    /**
     * @return the playerNameTextField
     */
    public JTextField getPlayerNameTextField() {
        return playerNameTextField;
    }

    /**
     * @param playerNameTextField the playerNameTextField to set
     */
    public void setPlayerNameTextField(JTextField playerNameTextField) {
        this.playerNameTextField = playerNameTextField;
    }

    /**
     * @return the colorButton
     */
    public JButton getColorButton() {
        return colorButton;
    }

    /**
     * @param colorButton the colorButton to set
     */
    public void setColorButton(JButton colorButton) {
        this.colorButton = colorButton;
    }

    /**
     * @return the delButton
     */
    public DeletableButton getDelButton() {
        return delButton;
    }

    /**
     * @param delButton the delButton to set
     */
    public void setDelButton(DeletableButton delButton) {
        this.delButton = delButton;
    }

}
