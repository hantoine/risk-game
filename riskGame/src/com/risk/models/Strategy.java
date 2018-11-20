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
public interface Strategy {

    public enum Type {
        HUMAN,
        AGGRESSIVE,
        BENEVOLENT,
        RANDOM,
        CHEATER
    }
}
