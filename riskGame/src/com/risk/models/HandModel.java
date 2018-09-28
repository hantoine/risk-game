/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import java.util.LinkedList;

/**
 *
 * @author l_yixu
 */
public class HandModel {
    // a group of cards

    private LinkedList<CardModel> hand;

    public HandModel() {
        this.hand = new LinkedList();
        this.hand.add(new CardModel("Venezuela", "infantry"));
        this.hand.add(new CardModel("France", "infantry"));
        this.hand.add(new CardModel("China", "infantry"));
        this.hand.add(new CardModel("Canada", "cavalry"));
        this.hand.add(new CardModel("India", "artillery"));
        this.hand.add(new CardModel("Africa", "artillery"));
    }


    /**
     * @return the hand
     */
    public LinkedList<CardModel> getHand() {
        return hand;
    }

    /**
     * @param hand the hand to set
     */
    public void setHand(LinkedList<CardModel> hand) {
        this.hand = hand;
    }

}
