/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import static java.lang.Integer.min;
import java.util.Arrays;

/**
 * Class to represent an attack
 *
 * @author Nellybett
 */
public class AttackMove {

    /**
     * Source of the attack
     */
    private TerritoryModel source;
    /**
     * Destiny of the attack
     */
    private TerritoryModel dest;

    /**
     * Constructor
     *
     * @param source source of the attack
     * @param dest destiny of the attack
     */
    public AttackMove(TerritoryModel source, TerritoryModel dest) {
        this.source = source;
        this.dest = dest;

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
     * Battle between countries in an attack move
     *
     * @param dice number of dices
     */
    private void battle(int dice) {

        int[] attacker = createDice(dice);
        int defenseArmies = min(this.getDest().getNumArmies(), dice);
        int[] defense = createDice(min(defenseArmies, 2));

        Arrays.sort(attacker);
        Arrays.sort(defense);

        if (defenseArmies == 1) {
            compareDice(attacker, defense, attacker.length - 1, 0);
        } else {
            compareDice(attacker, defense, attacker.length - 1, 1);
            compareDice(attacker, defense, attacker.length - 2, 0);
        }

    }

    /**
     * Perform the attack of this player with the given number of dice The value
     * -1 correspond to the special mode in which battles are made until one of
     * the territory has no more armies
     *
     * @param dice the number of dice to use to perform the attack
     */
    public void perform(int dice) {

        if (dice == -1) {
            battleAll();
        } else {
            battle(dice);
        }
    }

    /**
     * Compare results of rolling the dices
     *
     * @param attacker attacker dices results
     * @param defense defense dices results
     * @param j position for attacker
     * @param i position for defense
     */
    public void compareDice(int[] attacker, int[] defense, int j, int i) {
        if (attacker[j] <= defense[i]) {
            this.getSource().setNumArmies(this.getSource().getNumArmies() - 1);
        } else {
            this.getDest().setNumArmies(this.getDest().getNumArmies() - 1);
        }
    }

    /**
     * Uses all the armies in an attack
     */
    private void battleAll() {
        while (this.getDest().getNumArmies() != 0
                && this.getSource().getNumArmies() > 1) {
            int nbArmiesInSrc = this.getSource().getNumArmies();
            if (nbArmiesInSrc > 3) {
                battle(3);
            } else {
                battle(nbArmiesInSrc - 1);
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
     * Random value after rolling the dice
     *
     * @return random value
     */
    int roolDice() {
        int range = (6 - 0) + 1;
        return (int) (Math.random() * range) + 0;
    }
}
