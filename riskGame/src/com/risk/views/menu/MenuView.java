/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.menu;


import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 *
 * @author Nellybett
 */
public class MenuView extends JDialog{
    private StartMenuView startMenu;

    public MenuView(StartMenuView startMenu, JFrame owner, String title) {
        super(owner, title);
        this.startMenu = startMenu;
    }

    

    /**
     * @return the startMenu
     */
    public StartMenuView getStartMenu() {
        return startMenu;
    }

    /**
     * @param startMenu the startMenu to set
     */
    public void setStartMenu(StartMenuView startMenu) {
        this.startMenu = startMenu;
    }
    
    
}
