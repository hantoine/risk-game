/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.phases;

import com.risk.models.RiskModel;
import com.risk.models.interfaces.PlayerModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Nellybett
 */
public class PhasePanel extends JPanel {

    final private JButton endPhase;
    final private JLabel text;

    public PhasePanel() {
        this.text = new JLabel();
        this.endPhase = new JButton("End Phase");

        this.text.setVisible(false);
        this.endPhase.setVisible(false);

        this.add(text);
        this.add(this.endPhase);
    }

    public void updateView(RiskModel rm) {
        PlayerModel currentPlayer = rm.getCurrentPlayer();
        switch (rm.getPhase()) {
            case STARTUP:
                this.endPhase.setVisible(false);
                this.text.setText(String.format("<html>Startup phase: Click on one of your territory to place an army on it. <br />Remaining armies to be placed: %d</html>",
                        currentPlayer.getNumArmiesAvailable()));
                this.text.setVisible(true);
                break;
            case REINFORCEMENT:
                this.endPhase.setVisible(false);
                this.text.setText(String.format("<html>Reinforcement phase: Click on one of your territory to place an army on it. <br />Remaining armies to be placed: %d</html>",
                        currentPlayer.getNumArmiesAvailable()));
                this.text.setVisible(true);
                break;
            case ATTACK:
                this.endPhase.setVisible(true);
                this.text.setText("Attack phase:");
                this.text.setVisible(true);
                break;
            case FORTIFICATION:
                this.endPhase.setVisible(true);
                this.text.setText("<html>Fortification phase: You can drag'n'drop between territories your own to move your armies <br /></html>");
                this.text.setVisible(true);
                break;
            default:
                break;
        }

        this.setVisible(true);
    }

    public JButton getEndPhase() {
        return endPhase;
    }
}
