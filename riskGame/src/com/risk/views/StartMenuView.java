/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views;

import com.risk.models.Player;
import com.risk.models.RiskModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
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

public class StartMenuView extends javax.swing.JFrame{
    JPanel introPanel;
    JTabbedPane tabbedPane;
            
    public StartMenuView(RiskModel riskModel) {
        //JFrame's params
        super("Risk Game");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setSize(400,450);
        this.getContentPane().setLayout(new BorderLayout());
        
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
        JComponent[] components = {new NewGamePanel(riskModel), new MapEditorPanel()};

        for (int i = 0; i < components.length; i++){
            components[i].setSize(200,200);
            tabbedPane.addTab(titles[i], components[i]);
        }
        
        //add panes to current JFrame
        this.getContentPane().add(BorderLayout.PAGE_START, introPanel);
        this.getContentPane().add(BorderLayout.CENTER, tabbedPane);
        
        this.setVisible(true);
    }
    
}
