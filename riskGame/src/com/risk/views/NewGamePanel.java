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
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author timot
 */
public class NewGamePanel extends JPanel {
    JPanel playersPanel;
    JPanel mapSelector;

    
    public NewGamePanel(RiskModel riskModel, MenuListener menuAction){
        //panel's params 
        //this.setSize(500,100);
        this.setLayout(new BorderLayout());
        
        //map selector
        this.mapSelector = new JPanel();
        this.mapSelector.setSize(400,50);
        JButton selectFileButton = new JButton("Select a Map");
        JTextField selectFileTextField= new JTextField(20);
        selectFileTextField.setText("No file selected");
        
        selectFileButton.addActionListener(new ActionListener()
        {
          @Override
          public void actionPerformed(ActionEvent e)
          {
            JFileChooser fileChooser;
            fileChooser = new JFileChooser();

            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                String fileName = fileChooser.getSelectedFile().getName();
                selectFileTextField.setText(fileName);
            }
            else{
                selectFileTextField.setText("No file selected");
            }
          }
        });
        
        this.mapSelector.setLayout(new FlowLayout());
        this.mapSelector.add(selectFileButton);
        this.mapSelector.add(selectFileTextField);
        
        //players panel
        playersPanel = new PlayerListPanel(riskModel,menuAction);
        
        //start game button
        JButton startGameButton = new JButton("PLAY");
        startGameButton.addMouseListener(menuAction);
        
        //add content to panel
        this.add(BorderLayout.PAGE_START, mapSelector);
        this.add(BorderLayout.CENTER, playersPanel);
        this.add(BorderLayout.PAGE_END, startGameButton);
    }
    
}
