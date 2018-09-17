/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views;

import com.risk.models.Player;
import javax.swing.JPanel;

/**
 *
 * @author Nellybett
 */
public class PlayerPanel extends JPanel {
    
    private Player currentPlayer;

    public PlayerPanel() {
    }
    
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
    

}
