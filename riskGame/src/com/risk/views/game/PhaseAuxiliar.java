/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.game;

import com.risk.views.attack.ArmiesLeft;
import com.risk.views.attack.AttackView;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 *
 * @author Nellybett
 */
public class PhaseAuxiliar extends JPanel {
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
        attackPanel=null;
        armiesLeft=null;
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    }
    
    /**
     * Changes the panel to show
     * @param phaseAuxiliarPanel the panel to be shown 
     */
    public void updatePanel(JPanel phaseAuxiliarPanel){
        this.removeAll();
        this.add(phaseAuxiliarPanel);
        this.repaint();
        this.revalidate();
    }

    /**
     * Getter of the playerHandPanel attribute
     * @return the playerHandPanel
     */
    public PlayerGameHandPanel getPlayerHandPanel() {
        return playerHandPanel;
    }

    /**
     * Setter of the playerHandPanel attribute
     * @param playerHandPanel the playerHandPanel to set
     */
    public void setPlayerHandPanel(PlayerGameHandPanel playerHandPanel) {
        this.playerHandPanel = playerHandPanel;
    }

    /**
     * Getter of the attack panel
     * @return the attackPanel
     */
    public AttackView getAttackPanel() {
        return attackPanel;
    }

    /**
     * Setter of the attackPanel
     * @param attackPanel the attackPanel to set
     */
    public void setAttackPanel(AttackView attackPanel) {
        this.attackPanel = attackPanel;
    }

    /**
     * Getter of the armiesLeft panel
     * @return the armiesLeft
     */
    public ArmiesLeft getArmiesLeft() {
        return armiesLeft;
    }

    /**
     * Setter of the armiesLeft panel
     * @param armiesLeft the armiesLeft to set
     */
    public void setArmiesLeft(ArmiesLeft armiesLeft) {
        this.armiesLeft = armiesLeft;
    }
   
}
