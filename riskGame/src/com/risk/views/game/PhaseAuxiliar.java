/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.game;

import com.risk.controllers.GameController;
import com.risk.models.AttackMove;
import com.risk.models.RiskModel;
import com.risk.views.attack.ArmiesLeft;
import com.risk.views.attack.AttackView;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 *
 * @author Nellybett
 */
public class PhaseAuxiliar extends JPanel implements Observer {

    /**
     * Panel with the cards
     */
    private PlayerGameHandPanel playerHandPanel;
    /**
     * Panel with the dice for attacking
     */
    private AttackView attackPanel;
    /**
     * Panel with moving armies in an attack
     */
    private ArmiesLeft armiesLeft;

    /**
     * Constructor
     */
    public PhaseAuxiliar() {
        attackPanel = new AttackView();
        armiesLeft = new ArmiesLeft();
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    }

    /**
     * Changes the panel to show
     *
     * @param phaseAuxiliarPanel the panel to be shown
     */
    void selectPanel(JPanel phaseAuxiliarPanel) {
        this.removeAll();
        if (phaseAuxiliarPanel != null) {
            this.add(phaseAuxiliarPanel);
        }
        this.repaint();
        this.revalidate();
    }

    /**
     * Getter of the playerHandPanel attribute
     *
     * @return the playerHandPanel
     */
    PlayerGameHandPanel getPlayerHandPanel() {
        return playerHandPanel;
    }

    /**
     * Setter of the playerHandPanel attribute
     *
     * @param playerHandPanel the playerHandPanel to set
     */
    private void setPlayerHandPanel(PlayerGameHandPanel playerHandPanel) {
        this.playerHandPanel = playerHandPanel;
    }

    /**
     * Getter of the attack panel
     *
     * @return the attackPanel
     */
    AttackView getAttackPanel() {
        return attackPanel;
    }

    /**
     * Getter of the armiesLeft panel
     *
     * @return the armiesLeft
     */
    ArmiesLeft getArmiesLeft() {
        return armiesLeft;
    }

    public void setListeners(GameController gc) {
        this.getAttackPanel().setListeners(gc);
        this.getArmiesLeft().setListener(gc);
    }

    /**
     * Hide the panels
     */
    public void hideAttack() {
        if (this.getAttackPanel() != null) {
            this.getAttackPanel().setVisible(false);
        }

        if (this.getArmiesLeft() != null) {
            this.getArmiesLeft().setVisible(false);
        }
    }

    @Override
    public void update(Observable o, Object o1) {
        if (o instanceof RiskModel) {
            RiskModel rm = (RiskModel) o;

            AttackMove currentAttack = rm.getCurrentPlayer().getCurrentAttack();
            if (currentAttack == null) {
                this.selectPanel(null);
                return;
            }

            String srcTerritory = currentAttack.getSource().getName();
            String destTerritory = currentAttack.getDest().getName();
            int nbArmies = currentAttack.getSource().getNumArmies();

            if (currentAttack.getDest().getNumArmies() == 0) {
                this.selectPanel(this.getArmiesLeft());
                this.armiesLeft.update(srcTerritory, destTerritory, nbArmies);
            } else {
                this.selectPanel(this.getAttackPanel());
                this.attackPanel.update(srcTerritory, destTerritory, nbArmies);
            }
        }
    }
}
