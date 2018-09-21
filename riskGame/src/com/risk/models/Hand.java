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
public class Hand {
    // a group of cards

    private LinkedList<Card> hand;

    public Hand() {
        this.hand = new LinkedList();
        this.hand.add(new Card("Venezuela", "infantry"));
        this.hand.add(new Card("France", "infantry"));
        this.hand.add(new Card("China", "infantry"));

    }

    /**
     * @return the hand
     */
    public LinkedList<Card> getHand() {
        return hand;
    }

    /**
     * @param hand the hand to set
     */
    public void setHand(LinkedList<Card> hand) {
        this.hand = hand;
    }

}
