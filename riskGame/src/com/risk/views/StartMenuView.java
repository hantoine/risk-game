/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views;

import com.risk.controllers.MenuListener;
import com.risk.models.Player;
import com.risk.models.RiskModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.lang.reflect.Array;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author timot
 */

public class StartMenuView extends JPanel{
    JPanel introPanel;
    JTabbedPane tabbedPane;
            
    public StartMenuView(RiskModel riskModel, MenuListener menuAction) {
        //JFrame's params
       
        this.setLayout(new BorderLayout());
        
        //get intro pane
        this.introPanel = new JPanel();
        this.introPanel.setSize(400,50);
        this.introPanel.setBackground(Color.LIGHT_GRAY);
        JLabel introLabel = new JLabel("WELCOME TO THE RISK GAME"); 
        this.introPanel.setLayout(new FlowLayout());
        this.introPanel.add(introLabel);
            
        //get tabbed pane
        this.tabbedPane = new JTabbedPane();
        tabbedPane.setSize(300,500);
        
        //get tabbed pane content
        String[] titles = {"New Game", "Map Editor"};
        JComponent[] components = {new NewGamePanel(riskModel, menuAction), new MapEditorPanel()};

        for (int i = 0; i < components.length; i++){
            components[i].setSize(200,200);
            tabbedPane.addTab(titles[i], components[i]);
        }
        
        //add panes to current JFrame
        this.add(BorderLayout.PAGE_START, introPanel);
        this.add(BorderLayout.CENTER, tabbedPane);
        
    }
    
}
