/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

/**
 *
 * @author l_yixu
 */
public class Hand {
    // a group of cards
    
    private Card[] hand;
    
    public Hand() {
        this.hand = new Card[5];
        this.hand[0] = new Card("Venezuela", "infantry");
        this.hand[1] = new Card("France", "infantry");
        this.hand[2] = new Card("China", "infantry");
    }

    /**
     * @return the hand
     */
    public Card[] getHand() {
        return hand;
    }

    /**
     * @param hand the hand to set
     */
    public void setHand(Card[] hand) {
        this.hand = hand;
    }
    
    
    
}
