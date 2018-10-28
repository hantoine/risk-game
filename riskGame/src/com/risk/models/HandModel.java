/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * It represents the group of cards that is owned by a player
 *
 * @author l_yixu
 */
public class HandModel {

    /**
     * cards it is the group of cards of the hand
     */
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
        //this.cards.add(new CardModel("Africa", "artillery"));
        //this.cards.add(new CardModel("Va", "infantry"));
        //this.cards.add(new CardModel("Fe", "infantry"));
        //this.cards.add(new CardModel("Ca", "infantry"));
        //this.cards.add(new CardModel("Ia", "artillery"));
        //this.cards.add(new CardModel("Aa", "artillery"));
        //this.cards.add(new CardModel("Vela", "infantry"));
        //this.cards.add(new CardModel("Fnce", "infantry"));
        //this.cards.add(new CardModel("Cina", "infantry"));
        //this.cards.add(new CardModel("Idia", "artillery"));
        //this.cards.add(new CardModel("Arica", "artillery"));
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

    /**
     * Function to check if the player has repeated cards
     * @return whether there is any duplication
     */
    public int[] getCardDuplicates() {
        int[] cardDuplicates = new int[3];
        cardDuplicates[0] = cardDuplicates[1] = cardDuplicates[2] = 0;

        this.getCards().stream()
                .forEach((c) -> {

                    if (c.getTypeOfArmie().equals("infantry")) {
                        cardDuplicates[0] = cardDuplicates[0] + 1;
                    } else if (c.getTypeOfArmie().equals("cavalry")) {
                        cardDuplicates[1] = cardDuplicates[1] + 1;
                    } else if (c.getTypeOfArmie().equals("artillery")) {
                        cardDuplicates[2] = cardDuplicates[2] + 1;
                    }
                });

        return cardDuplicates;
    }

    /**
     * Function that return if the hand button should be displayed: if the
     * player has 3 different cards or if it has 3 equal cards
     *
     * @return true if the player has 3 different cards or 3 identical ones
     */
    public boolean threeDifferentCardsOrThreeEqualCards() {
        int[] cardDuplicates = this.getCardDuplicates();

        return cardDuplicates[0] >= 3
                || cardDuplicates[1] >= 3
                || cardDuplicates[2] >= 3
                || (cardDuplicates[0] >= 1 && cardDuplicates[1] >= 1 && cardDuplicates[2] >= 1);
    }

    /**
     * Removes the cards from a players hand depending on their type
     *
     * @param selectedCards cards to be eliminated
     * @param deck deck of card in which to put the card removed
     */
    public void removeCards(LinkedList<String> selectedCards, LinkedList<CardModel> deck) {
        this.getCards().stream()
                .filter(c -> selectedCards.contains(c.getCountryName()))
                .forEach(cs -> deck.addFirst(cs));
        
        this.getCards().removeIf(c -> selectedCards.contains(c.getCountryName()));
    }
    
    public void removeCards(String typeOfArmie, LinkedList<CardModel> deck) {
        String[] typeOfArmieDum = {"infantry", "artillery", "cavalry"};

        if (typeOfArmie.equals("different")) {

            Arrays.stream(typeOfArmieDum).forEach(typeA -> {
                CardModel card = this.getCards().stream()
                        .filter(c -> c.getTypeOfArmie().equals(typeA))
                        .findFirst()
                        .get();

                deck.addFirst(card);
                this.getCards().remove(card);
            });

        } else {
            for (Iterator<CardModel> iterator = this.getCards().iterator(); iterator.hasNext();) {
                CardModel card = iterator.next();
                if (card.getTypeOfArmie().equals(typeOfArmie)) {
                    deck.addFirst(card);
                    iterator.remove();
                }
            }
        }
    }

}
