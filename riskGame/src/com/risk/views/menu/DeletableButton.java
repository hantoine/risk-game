/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.menu;

import javax.swing.JButton;

/**
 * It represents the button that eliminates player from the menu
 *
 * @author timot
 */
public class DeletableButton extends JButton {

    int ID;

    /**
     * Constructor
     *
     * @param text text in the button
     * @param ID position in the list
     */
    public DeletableButton(String text, int ID) {
        this.ID = ID;
        this.setText(text);
    }

    /**
     * Getter of the id attribute
     *
     * @return id
     */
    public int getID() {
        return this.ID;
    }
}
