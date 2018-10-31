/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

/**
 * It represents the group of cards that is owned by a player
 *
 * @author l_yixu
 */
public class HandModel extends Observable {

    /**
     * cards it is the group of cards of the hand
     */
    private LinkedList<CardModel> cards;
    private PlayerModel owner;
    private boolean current;

    /**
     * Constructor
     */
    HandModel() {
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
    LinkedList<CardModel> getCardsList() {
        return cards;
    }

    /**
     * Read-only Getter for the cards attribute
     *
     * @return
     */
    public List<CardModel> getCards() {
        return Collections.unmodifiableList(cards);
    }

    /**
     * Returns the number of cards in this hand
     *
     * @return the number of cards in this hand
     */
    public int getNbCards() {
        return cards.size();
    }

    /**
     * Setter for the cards attribute
     *
     * @param cards the hand to set
     */
    void setCards(LinkedList<CardModel> cards) {
        this.cards = cards;

        setChanged();
        notifyObservers();
    }

    public PlayerModel getOwner() {
        return owner;
    }

    void setOwner(PlayerModel owner) {
        this.owner = owner;

        setChanged();
        notifyObservers();
    }

    /**
     * Function to check if the player has repeated cards
     *
     * @return whether there is any duplication
     */
    public int[] getCardDuplicates() {
        int[] cardDuplicates = new int[3];
        cardDuplicates[0] = cardDuplicates[1] = cardDuplicates[2] = 0;

        this.getCards().forEach((c) -> {
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
     * @param typeOfArmie type of card
     * @param deck deck of card in which to put the card removed
     */
    void removeCards(String typeOfArmie, LinkedList<CardModel> deck) {
        String[] typeOfArmieDum = {"infantry", "artillery", "cavalry"};

        if (typeOfArmie.equals("different")) {

            Arrays.stream(typeOfArmieDum).forEach(typeA -> {
                CardModel card = this.getCardsList().stream()
                        .filter(c -> c.getTypeOfArmie().equals(typeA))
                        .findFirst()
                        .get();

                deck.addFirst(card);
                this.cards.remove(card);
            });

        } else {
            for (Iterator<CardModel> iterator = this.cards.iterator(); iterator.hasNext();) {
                CardModel card = iterator.next();
                if (card.getTypeOfArmie().equals(typeOfArmie)) {
                    deck.addFirst(card);
                    iterator.remove();
                }
            }
        }

        setChanged();
        notifyObservers();
    }

    public boolean isCurrent() {
        return current;
    }

    void setCurrent(boolean current) {
        this.current = current;

        setChanged();
        notifyObservers();
    }

}
