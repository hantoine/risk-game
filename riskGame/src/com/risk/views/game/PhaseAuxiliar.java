/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.game;

import com.risk.controllers.GameController;
import com.risk.models.AttackMove;
import com.risk.models.RiskModel;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * View storing aux information that need to be displayed depending on the game
 * phase. Currently used for attack phase only
 *
 * @author Nellybett
 */
public class PhaseAuxiliar extends JPanel implements Observer {

    /**
     * Panel with the cards
     */
    private HandPanel playerHandPanel;
    /**
     * Panel with the dice for attacking
     */
    private AttackView attackPanel;
    /**
     * Panel with moving armies in an attack
     */
    private ArmiesLeftPanel armiesLeft;
    /**
     * 
     */
    private DefenseView defensePanel;
    /**
     * Constructor
     */
    public PhaseAuxiliar() {
        attackPanel = new AttackView();
        armiesLeft = new ArmiesLeftPanel();
        defensePanel=new DefenseView();
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
    HandPanel getPlayerHandPanel() {
        return playerHandPanel;
    }

    /**
     * Setter of the playerHandPanel attribute
     *
     * @param playerHandPanel the playerHandPanel to set
     */
    private void setPlayerHandPanel(HandPanel playerHandPanel) {
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
    ArmiesLeftPanel getArmiesLeft() {
        return armiesLeft;
    }

    public void setListeners(GameController gc) {
        this.getAttackPanel().setListeners(gc);
        this.getArmiesLeft().setListener(gc);
        this.getDefensePanel().setListeners(gc);
        
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

    /**
     * This method is for attach the observer
     *
     * @param o The Observable
     * @param o1 The Object
     */
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
            int nbArmiesDef=currentAttack.getDest().getNumArmies();

            if (currentAttack.getDest().getNumArmies() == 0) {
                this.selectPanel(this.getArmiesLeft());
                int diceAttack = rm.getCurrentPlayer().getCurrentAttack().getNbDiceAttack();
                this.armiesLeft.update(srcTerritory, destTerritory, nbArmies, diceAttack);
            } else {
                if(rm.isAttackPhase()){
                    this.selectPanel(this.getAttackPanel());
                    this.attackPanel.update(srcTerritory, destTerritory, nbArmies);
                }else{
                    this.selectPanel(this.getDefensePanel());
                    this.defensePanel.update(srcTerritory, destTerritory, nbArmiesDef);
                }
            }
        }
    }

    /**
     * @return the defensePanel
     */
    public DefenseView getDefensePanel() {
        return defensePanel;
    }

    /**
     * @param defensePanel the defensePanel to set
     */
    public void setDefensePanel(DefenseView defensePanel) {
        this.defensePanel = defensePanel;
    }
}
