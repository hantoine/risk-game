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
    
    private PlayerGameHandPanel playerHandPanel;
    private AttackView attackPanel;
    private ArmiesLeft armiesLeft;

    public PhaseAuxiliar() {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    }
    
    public void updatePanel(JPanel phaseAuxiliarPanel){
        this.removeAll();
        this.add(phaseAuxiliarPanel);
    }

    /**
     * @return the playerHandPanel
     */
    public PlayerGameHandPanel getPlayerHandPanel() {
        return playerHandPanel;
    }

    /**
     * @param playerHandPanel the playerHandPanel to set
     */
    public void setPlayerHandPanel(PlayerGameHandPanel playerHandPanel) {
        this.playerHandPanel = playerHandPanel;
    }

    /**
     * @return the attackPanel
     */
    public AttackView getAttackPanel() {
        return attackPanel;
    }

    /**
     * @param attackPanel the attackPanel to set
     */
    public void setAttackPanel(AttackView attackPanel) {
        this.attackPanel = attackPanel;
    }

    /**
     * @return the armiesLeft
     */
    public ArmiesLeft getArmiesLeft() {
        return armiesLeft;
    }

    /**
     * @param armiesLeft the armiesLeft to set
     */
    public void setArmiesLeft(ArmiesLeft armiesLeft) {
        this.armiesLeft = armiesLeft;
    }
   
}
