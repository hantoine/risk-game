/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.menu;

import com.risk.controllers.MenuListener;
import com.risk.models.RiskModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * View that contains the different tabs of the menu
 *
 * @author timot
 */
public class StartMenuView extends JPanel {

    /**
     * Panel that says a message to welcome the user.
     */
    JPanel introPanel;
    
    /**
     * Panel that contains the features to create a new game and launch it.
     */
    final private NewGamePanel newGamePanel;

    /**
     * Constructor
     *
     * @param riskModel model of the game
     * @param menuAction mouse event listener
     */
    public StartMenuView(RiskModel riskModel, MenuListener menuAction) {
        //JFrame's params

        this.setLayout(new BorderLayout());

        //get intro pane
        this.introPanel = new JPanel();
        this.introPanel.setSize(400, 50);
        this.introPanel.setBackground(Color.LIGHT_GRAY);
        JLabel introLabel = new JLabel("WELCOME TO THE RISK GAME");
        this.introPanel.setLayout(new FlowLayout());
        this.introPanel.add(introLabel);

        //get tabbed pane
        this.newGamePanel = new NewGamePanel(riskModel, menuAction);
        this.newGamePanel.setSize(200, 200);

        //add panes to current JFrame
        this.add(BorderLayout.PAGE_START, introPanel);
        this.add(BorderLayout.CENTER, newGamePanel);

    }

    /**
     * Getter of the new game panel.
     * @return 
     */
    public NewGamePanel getNewGamePanel() {
        return newGamePanel;
    }
}
