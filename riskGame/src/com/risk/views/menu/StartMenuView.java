/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.menu;

import com.risk.mapeditor.MapEditorPanel;
import com.risk.controllers.MenuListener;
import com.risk.models.RiskModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * View that contains the different tabs of the menu
 * @author timot
 */
public class StartMenuView extends JPanel {

    JPanel introPanel;
    private JTabbedPane tabbedPane;

    /**
     * Constructor
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
        this.tabbedPane = new JTabbedPane();
        tabbedPane.setSize(300, 500);

        //get tabbed pane content
        
        //temporary (tim) : commented because I don't know where to put the map editor
        //String[] titles = {"New Game", "Map Editor"};
        //JComponent[] components = {new NewGamePanel(riskModel, menuAction), new MapEditorPanel()};
        String[] titles = {"New Game"};
        JComponent[] components = {new NewGamePanel(riskModel, menuAction)};

        for (int i = 0; i < components.length; i++) {
            components[i].setSize(200, 200);
            tabbedPane.addTab(titles[i], components[i]);
        }

        //add panes to current JFrame
        this.add(BorderLayout.PAGE_START, introPanel);
        this.add(BorderLayout.CENTER, tabbedPane);

    }

    /**
     * Getter of the tabbedPane attribute
     * @return the tabbedPane
     */
    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    /**
     * Setter of the tabbedPane attribute
     * @param tabbedPane the tabbedPane to set
     */
    public void setTabbedPane(JTabbedPane tabbedPane) {
        this.tabbedPane = tabbedPane;
    }
}
