/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

/**
 * This class represents the different phases of the game
 *
 * @author hantoine
 */
public enum GamePhase {
    STARTUP,
    REINFORCEMENT,
    ATTACK,
    FORTIFICATION {
        @Override
        public GamePhase next() {
            return GamePhase.REINFORCEMENT;
        }
    };

    /**
     * Constant for the game phases
     */
    private static final GamePhase[] vals = values();

    /**
     * This method change the execution order of the phases
     *
     * @return the order of the phrase
     */
    public GamePhase next() {
        return vals[(this.ordinal() + 1) % vals.length];
    }
}
