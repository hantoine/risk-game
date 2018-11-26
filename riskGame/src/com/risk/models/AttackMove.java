/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import java.io.Serializable;
import static java.lang.Integer.min;
import java.util.Arrays;
import java.util.Observable;

/**
 * Class to represent an attack
 *
 * @author Nellybett
 */
public class AttackMove extends Observable implements Serializable {

    /**
     * Source of the attack
     */
    private TerritoryModel source;
    /**
     * Destiny of the attack
     */
    private TerritoryModel dest;
    /**
     * Player performing the attack
     */
    private PlayerModel attacker;
    /**
     * Number of dice selected by the attacker
     */
    private int nbDiceAttack;
    /**
     * Number of dice selected by the attacked
     */
    private int nbDiceDefense;
    /**
     *
     */
    private boolean choiceNbDefenseDiceNeeded;

    /**
     * Constructor
     *
     * @param attacker player attacking
     * @param source source of the attack
     * @param dest destiny of the attack
     */
    public AttackMove(PlayerModel attacker, TerritoryModel source, TerritoryModel dest) {
        this.choiceNbDefenseDiceNeeded = false;
        this.source = source;
        this.dest = dest;
        this.attacker = attacker;
        this.nbDiceAttack = -1;
        this.nbDiceDefense = 100;
    }

    /**
     * Getter of source attribute
     *
     * @return the source
     */
    public TerritoryModel getSource() {
        return source;
    }

    /**
     * Setter of source attribute
     *
     * @param source the source to set
     */
    public void setSource(TerritoryModel source) {
        this.source = source;
    }

    /**
     * Getter of dest attribute
     *
     * @return the dest
     */
    public TerritoryModel getDest() {
        return dest;
    }

    /**
     * Setter of dest attribute
     *
     * @param dest the dest to set
     */
    public void setDest(TerritoryModel dest) {
        this.dest = dest;
    }

    /**
     * Battle between territories in an attack move
     *
     * @param diceAttack number of dices selected by the attacker
     * @param diceAttacked number of dices selected by the attacked
     */
    private void battle(int diceAttack, int diceAttacked) {
        String looser1 = null, looser2 = null;
        int[] attack = createDice(diceAttack);
        int[] defense = createDice(diceAttacked);

        Arrays.sort(attack);
        Arrays.sort(defense);

        if(diceAttack>= diceAttacked){
            if (diceAttacked == 1) {
                looser1 = compareDice(attack, defense, attack.length - 1, 0);
            } else {
                looser1 = compareDice(attack, defense, attack.length - 1, 1);
                looser2 = compareDice(attack, defense, attack.length - 2, 0);
            }
        }
        if(diceAttack<diceAttacked && diceAttacked!=100){
            looser1 = compareDice(attack, defense, 0, 1);
        }

        this.attacker.addNewLogEvent(getBattleLogMsg(looser1, looser2));
    }

    /**
     * this method is to get battle log message
     * @param firstLooser the first looser
     * @param secondLooser the second looser
     * @return the message
     */
    private String getBattleLogMsg(String firstLooser, String secondLooser) {
        String logMessage = String.format(
                "%s launch battle between %s and %s",
                this.attacker.getName(),
                this.source.getName(),
                this.dest.getName()
        );

        if (secondLooser == null) {
            logMessage += String.format(
                    ", %s loose 1 army",
                    firstLooser
            );
            return logMessage;
        }

        if (firstLooser.equals(secondLooser)) {
            logMessage += String.format(
                    ", %s loose 2 armies",
                    firstLooser
            );

        } else {
            logMessage += String.format(", both territories loose 1 army");
        }

        return logMessage;
    }

    /**
     * Perform the attack of this player with the given number of diceAttack The
     * value -1 correspond to the special mode in which battles are made until
     * one of the territory has no more armies
     *
     * @param diceAttack number of dices selected by attacker
     * @param diceAttacked number of dices selected by attacked
     */
    public void perform(int diceAttack, int diceAttacked) {
        if (diceAttack == -1) {
            battleAll();
        } else {
            battle(diceAttack, diceAttacked);
        }
    }

    /**
     * Compare results of rolling the dices
     *
     * @param attacker attacker dices results
     * @param defense defense dices results
     * @param j position for attacker
     * @param i position for defense
     * @return the loosing territory name
     */
    public String compareDice(int[] attacker, int[] defense, int j, int i) {
        if (attacker[j] <= defense[i]) {
            this.getSource().setNumArmies(this.getSource().getNumArmies() - 1);
            return this.getSource().getName();
        } else {
            if (this.getDest().getNumArmies() > 0) {
                this.getDest().setNumArmies(this.getDest().getNumArmies() - 1);
            }
            return this.getDest().getName();
        }
    }

    /**
     * Uses all the armies in an attack
     */
    private void battleAll() {

        while (this.getDest().getNumArmies() != 0 && this.getSource().getNumArmies() > 1) {
            int nbArmiesInSrc = this.getSource().getNumArmies();
            int defenseArmies;

            if (nbArmiesInSrc > 3) {
                this.setNbDiceAttack(3);
                defenseArmies = min(this.getDest().getNumArmies(), this.getNbDiceAttack());
                defenseArmies = min(defenseArmies, 2);
                this.setNbDiceDefense(defenseArmies);
                battle(3, defenseArmies);
            } else {
                this.setNbDiceAttack(nbArmiesInSrc - 1);
                defenseArmies = min(this.getDest().getNumArmies(), this.getNbDiceAttack());
                defenseArmies = min(defenseArmies, 2);
                this.setNbDiceDefense(defenseArmies);
                battle((nbArmiesInSrc - 1), defenseArmies);
            }

        }
    }

    /**
     * Create an array with different number of dices
     *
     * @param dice number of dices
     * @return the array
     */
    public int[] createDice(int dice) {
        int[] dices = new int[dice];
        int i = 0;

        while (i < dices.length) {
            dices[i] = roolDice();
            i++;
        }
        return dices;
    }

    /**
     * Random value after rolling the diceAttack
     *
     * @return random value
     */
    int roolDice() {
        int range = 6;
        return (int) (Math.random() * range) + 1;
    }

    /**
     * @return the diceAttack
     */
    public int getNbDiceAttack() {
        return nbDiceAttack;
    }

    /**
     * @param diceAttack the diceAttack to set
     */
    public void setNbDiceAttack(int diceAttack) {
        this.nbDiceAttack = diceAttack;
    }

    /**
     * @return the number of defense dice
     */
    public int getNbDiceDefense() {
        return nbDiceDefense;
    }

    /**
     * @param nbDiceDefense the number of defense dice to set
     */
    public void setNbDiceDefense(int nbDiceDefense) {
        this.nbDiceDefense = nbDiceDefense;
    }

    /**
     * @return the attackDefense
     */
    public boolean getChoiceNbDefenseDiceNeeded() {
        return choiceNbDefenseDiceNeeded;
    }

    /**
     * @param attackDefense the attackDefense to set
     */
    public void setChoiceNbDefenseDiceNeeded(boolean attackDefense) {
        this.choiceNbDefenseDiceNeeded = attackDefense;

        setChanged();
        notifyObservers(this);
    }

}
