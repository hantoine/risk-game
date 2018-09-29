/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import java.util.LinkedList;

/**
 * It represents the group of cards that is owned by a player
 * @author l_yixu
 */
public class HandModel {
 

    private LinkedList<CardModel> hand;

    /**
     * Constructor
     */
    public HandModel() {
        this.hand = new LinkedList();
        this.hand.add(new CardModel("Venezuela", "infantry"));
        this.hand.add(new CardModel("France", "infantry"));
        this.hand.add(new CardModel("China", "infantry"));
        this.hand.add(new CardModel("India", "artillery"));
        this.hand.add(new CardModel("Africa", "artillery"));
    }


    /**
     * Getter for the hand attribute
     * @return the hand
     */
    public LinkedList<CardModel> getHand() {
        return hand;
    }

    /**
     * Setter for the hand attribute
     * @param hand the hand to set
     */
    public void setHand(LinkedList<CardModel> hand) {
        this.hand = hand;
    }

}
