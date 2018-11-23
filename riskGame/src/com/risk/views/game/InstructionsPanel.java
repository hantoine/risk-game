/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.game;

import com.risk.models.PlayerModel;
import com.risk.models.RiskModel;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class represents the phase panel
 *
 * @author Nellybett
 */
public class InstructionsPanel extends JPanel implements Observer {

    /**
     * endPhase button to finish a phase
     */
    final private JButton endPhase;

    /**
     * text information that informs a player about the current phase
     */
    final private JLabel text;

    /**
     * Constructor
     */
    public InstructionsPanel() {
        this.text = new JLabel();
        this.endPhase = new JButton("End Phase");

        this.text.setVisible(false);
        this.endPhase.setVisible(false);

        this.add(text);
        this.add(this.endPhase);

    }

    /**
     * Updates the view to the current phase
     *
     * @param rm receives a model
     */
    public void updateView(RiskModel rm) {
        PlayerModel currentPlayer = rm.getCurrentPlayer();

        if (rm.getWinningPlayer() != null) {
            this.endPhase.setVisible(false);
            this.text.setText("<html> The player " + rm.getWinningPlayer() + " won the game. <br /> It conquered all the territories.</html>");
            this.text.setVisible(true);
            return;
        }

        switch (rm.getPhase()) {
            case STARTUP:
                this.endPhase.setVisible(true);
                this.endPhase.setText("Place armies for all players");
                this.text.setText(String.format("<html>Startup phase: Click on one of your territory to place an army on it. <br />Remaining armies to be placed: %d</html>",
                        currentPlayer.getNbArmiesAvailable()));
                this.text.setVisible(true);
                break;
            case REINFORCEMENT:
                this.endPhase.setVisible(false);
                this.text.setText(String.format("<html>Reinforcement phase: Click on one of your territory to place an army on it. <br />Remaining armies to be placed: %d</html>",
                        currentPlayer.getNbArmiesAvailable()));
                this.text.setVisible(true);
                break;
            case ATTACK:
                this.endPhase.setVisible(true);
                this.endPhase.setText("End Phase");
                this.text.setText("<html>Attack phase: You can drag'n'drop between a territory you own <br /> and a territory you do not own.</html>");
                this.text.setVisible(true);
                break;
            case FORTIFICATION:
                this.endPhase.setVisible(true);
                this.endPhase.setText("End Phase");
                this.text.setText("<html>Fortification phase: You can drag'n'drop between territories you own. <br />Move your armies <br /></html>");
                this.text.setVisible(true);
                break;
            default:
                break;
        }

        this.setVisible(true);
    }

    /**
     * Getter of the endPhase attribute
     *
     * @return endPhase
     */
    public JButton getEndPhase() {
        return endPhase;
    }

    /**
     * This method is for updating the view
     *
     * @param o Observable
     * @param o1 Object
     */
    @Override
    public void update(Observable o, Object o1) {
        if (o instanceof RiskModel) {
            this.updateView((RiskModel) o);
        }
    }
}
