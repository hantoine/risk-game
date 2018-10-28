/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import com.risk.controllers.GameController;
import java.awt.Color;
import java.util.LinkedList;

/**
 * It represents an AI player (child of PlayerModel)
 *
 * @author Nellybett
 */
public class AIPlayerModel extends PlayerModel {

    /**
     * Constructor from Player Model
     *
     * @param name name of the player
     * @param color color of the player
     * @param game Game in which this players belongs
     */
    public AIPlayerModel(String name, Color color, RiskModel game) {
        super(name, color, false, game);
    }

    /**
     * Reinforcement phase for AI Player
     */
    @Override
    public void reinforcement(GameController playGame) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Fortification phase for AI Player
     */
    @Override
    public void fortification(GameController playGame) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Attack phase for AI Player
     */
    @Override
    public void attack(GameController playGame) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean exchangeCardsToArmies(LinkedList<String> selectedCards) {
        int[] cardDuplicates = this.getCardsOwned().getCardDuplicates();

        if (cardDuplicates[0] >= 3) {
            this.getCardsOwned().removeCards("infantry", super.game.getDeck());
        } else if (cardDuplicates[1] >= 3) {
            this.getCardsOwned().removeCards("cavalry", super.game.getDeck());
        } else if (cardDuplicates[2] >= 3) {
            this.getCardsOwned().removeCards("artillery", super.game.getDeck());
        } else {
            this.getCardsOwned().removeCards("different", super.game.getDeck());
        }
        armiesCardAssignation();
        return true;
    }

}
