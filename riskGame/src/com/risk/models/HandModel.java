/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import java.util.LinkedList;

/**
 * It represents the group of cards that is owned by a player
 *
 * @author l_yixu
 */
public class HandModel {

    private LinkedList<CardModel> cards;

    /**
     * Constructor
     */
    public HandModel() {
        this.cards = new LinkedList();
        this.cards.add(new CardModel("Venezuela", "infantry"));
        this.cards.add(new CardModel("France", "infantry"));
        this.cards.add(new CardModel("China", "infantry"));
        this.cards.add(new CardModel("India", "artillery"));
        this.cards.add(new CardModel("Africa", "artillery"));
    }

    /**
     * Getter for the cards attribute
     *
     * @return the cards
     */
    public LinkedList<CardModel> getCards() {
        return cards;
    }

    /**
     * Setter for the cards attribute
     *
     * @param cards the hand to set
     */
    public void setCards(LinkedList<CardModel> cards) {
        this.cards = cards;
    }

}
