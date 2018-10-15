/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.menu;

import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 * It contains all the panels of the menu
 *
 * @author Nellybett
 */
public class MenuView extends JDialog {

    /**
     * Reference to the view that contains the panels of the menu
     */
    private StartMenuView startMenu;

    /**
     * Constructor
     *
     * @param startMenu the principal panel of the menu
     * @param owner the frame that owns the JDialog
     * @param title tittle of the JDialog
     */
    public MenuView(StartMenuView startMenu, JFrame owner, String title) {
        super(owner, title);
        this.startMenu = startMenu;
    }

    /**
     * Getter of the startMenu attribute
     *
     * @return the startMenu
     */
    public StartMenuView getStartMenu() {
        return startMenu;
    }

    /**
     * Setter of the startMenu attribute
     *
     * @param startMenu the startMenu to set
     */
    public void setStartMenu(StartMenuView startMenu) {
        this.startMenu = startMenu;
    }

}
