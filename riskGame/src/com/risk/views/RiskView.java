/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views;

import com.risk.models.RiskModel;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author n_irahol
 */
public class RiskView extends javax.swing.JFrame {

    /**
     * Creates new form MainView
     */
    
    JPanel menuPanel;
    JPanel playersPanel;
    MapPanel mapPanel;
    
    public RiskView() {
        super("Risk Game");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
        Container cp=getContentPane();
        cp.setLayout(new BorderLayout());
        
        // Menu Panel
        this.menuPanel=new JPanel();
        this.menuPanel.setSize(300,50);
        JButton newGame=new JButton("New Game");
        JButton saveGame=new JButton("Create Map File");
        
        menuPanel.setLayout(new FlowLayout());
        menuPanel.add(newGame);
        menuPanel.add(saveGame);
                
        // Players Panel
        this.playersPanel=new PlayersPanel();
        this.playersPanel.setSize(300,50);
        JButton playerOne=new JButton("Player 1");
        JButton playerTwo=new JButton("Player 2");
        JButton playerThree=new JButton("Player 3");
        
        playersPanel.setLayout(new FlowLayout());
        playersPanel.add(playerOne);
        playersPanel.add(playerTwo);
        playersPanel.add(playerThree);
        
        //Adding the Panels
        cp.add(playersPanel, BorderLayout.SOUTH);
        cp.add(menuPanel, BorderLayout.NORTH);
        
    }
    
    public void initialMap(RiskModel riskModel){
        Container cp=getContentPane();
        mapPanel=new MapPanel(riskModel.getBoard());
        setSize(mapPanel.getWidth(),mapPanel.getHeight()+playersPanel.getHeight()+menuPanel.getHeight());
        cp.add(mapPanel, BorderLayout.CENTER);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dimension.width/2-this.getSize().width/2, dimension.height/2-this.getSize().height/2);
    }
       
}