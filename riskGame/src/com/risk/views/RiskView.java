/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views;

import com.risk.models.Card;
import com.risk.models.Continent;
import com.risk.models.Country;
import com.risk.models.Player;
import com.risk.models.RiskModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.MouseListener;
import java.util.LinkedList;
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
    JPanel battlePanel;
    MapPanel mapPanel;
    PlayerPanel playerPanel;
    

    public RiskView() {
        super("Risk Game");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
     
        // Menu Panel
        this.menuPanel = new JPanel();
        this.menuPanel.setSize(300, 50);
        JButton newGame = new JButton("New Game");
        JButton saveGame = new JButton("Create Map File");

        menuPanel.setLayout(new FlowLayout());
        menuPanel.add(newGame);
        menuPanel.add(saveGame);
 
        //Battle panel
        this.battlePanel = new JPanel();
        this.battlePanel.setSize(300, 50);
        JButton playerOne = new JButton("Player 1");
        JButton playerTwo = new JButton("Player 2");
        JButton playerThree = new JButton("Player 3");

        battlePanel.setLayout(new FlowLayout());
        battlePanel.add(playerOne);
        battlePanel.add(playerTwo);
        battlePanel.add(playerThree);

      
        //Adding Panels
        
        cp.add(battlePanel, BorderLayout.SOUTH);
        cp.add(menuPanel, BorderLayout.NORTH);
                
    }

    public void initialMap(RiskModel riskModel, MouseListener countryListener) {
        Container cp = getContentPane();
        mapPanel = new MapPanel(riskModel.getBoard(),countryListener);
        playerPanel = new PlayerPanel(riskModel.getCurrentPlayer());
        cp.add(mapPanel, BorderLayout.CENTER);
        cp.add(playerPanel, BorderLayout.EAST);
        this.setSize(mapPanel.getWidth()+playerPanel.getWidth(),  mapPanel.getHeight() + battlePanel.getHeight() + menuPanel.getHeight());
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dimension.width / 2 - this.getSize().width / 2, dimension.height / 2 - this.getSize().height / 2);
    }
    

}
