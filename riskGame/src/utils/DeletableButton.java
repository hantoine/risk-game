/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import javax.swing.JButton;

/**
 *
 * @author timot
 */
public class DeletableButton extends JButton {

    int ID;

    public DeletableButton(String text, int ID) {
        this.ID = ID;
        this.setText(text);
    }

    public int getID() {
        return this.ID;
    }
}
