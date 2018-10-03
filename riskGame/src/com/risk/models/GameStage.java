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
public enum GameStage {
    START,
    REINFORCEMENT,
    ATTACK,
    FORTIFICATION{
        @Override
        public GameStage next() {
            return GameStage.REINFORCEMENT;
        }
    };

    private static final GameStage[] vals = values();

    public GameStage next() {
        return vals[(this.ordinal() + 1) % vals.length];
    }
}
