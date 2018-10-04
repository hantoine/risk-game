/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.phases;

import com.risk.models.GameStage;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Nellybett
 */
public class StagePanel extends JPanel {

    private JTextField armies;
    private JButton endPhase;
    private JLabel armiesText;

    public StagePanel() {
        this.armiesText = new JLabel("Armies to deploy");
        this.armies = new JTextField("0");
        this.armies.setEditable(false);
        this.endPhase = new JButton("End Phase");

        this.armiesText.setVisible(false);
        this.armies.setVisible(false);
        this.endPhase.setVisible(false);

        this.add(armiesText);
        this.add(this.armies);
        this.add(this.endPhase);
    }

    public void updatePhase(GameStage phase, int armies) {
        switch (phase) {
            case INITIAL_ARMY_PLACEMENT:
                this.endPhase.setVisible(false);
                this.armies.setText(Integer.toString(armies));
                this.armiesText.setVisible(true);
                this.armies.setVisible(true);
                break;
            case REINFORCEMENT:
                this.armies.setText(Integer.toString(armies));
                this.armiesText.setVisible(true);
                this.armies.setVisible(true);
                break;
            case ATTACK:
                break;
            case FORTIFICATION:
                break;
            default:
                break;
        }

        this.setVisible(true);
    }

    /**
     * @return the armies
     */
    public JTextField getArmies() {
        return armies;
    }

    /**
     * @param armies the armies to set
     */
    public void setArmies(JTextField armies) {
        this.armies = armies;
    }

}
