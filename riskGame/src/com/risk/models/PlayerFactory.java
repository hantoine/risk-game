/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import java.awt.Color;

/**
 *
 * @author Nellybett
 */
public class PlayerFactory {

    private PlayerFactory() {
    }

    static public PlayerModel getPlayer(String typeOfPlayer, String name, Color color) {
        PlayerModel currentPlayer;
        currentPlayer = new PlayerModel(name, color);

        Strategy strategy = null;
        switch (typeOfPlayer) {
            case "BENEVOLENT":
                strategy = new BenevolentStrategy();
                break;
            case "AGGRESSIVE":
                strategy = new AggressiveStrategy();
                break;
            case "CHEATER":
                strategy = new CheaterStrategy();
                break;
            case "RANDOM":
                strategy = new RandomStrategy();
                break;
            case "HUMAN":
                strategy = new HumanStrategy();
                break;
        }
        currentPlayer.setStrategy(strategy);

        return currentPlayer;
    }

}
