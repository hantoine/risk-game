/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

/**
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

    private static final GamePhase[] vals = values();

    public GamePhase next() {
        return vals[(this.ordinal() + 1) % vals.length];
    }
}
