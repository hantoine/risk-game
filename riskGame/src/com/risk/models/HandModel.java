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
    /**
     * Owner of the hand
     */
    private PlayerModel owner;
    /**
     * Current cards show
     */
    private boolean current;
    /**
     * if have handed cards
     */
    private boolean handed;
    /**
     * Cards selected to be handed
     */
    List<String> selectedCards;

    /**
     * Constructor
     */
    HandModel() {
        this.cards = new LinkedList();
        this.selectedCards = new LinkedList<>();
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
     * @return list of cards
     */
    public List<CardModel> getCards() {
        return Collections.unmodifiableList(cards);
    }

    /**
     * Adds a card to the player hand
     * @param card the card to add
     */
    public void addCardToPlayerHand(CardModel card){
        cards.add(card);
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

    /**
     * 
     * @return 
     */
    public PlayerModel getOwner() {
        return owner;
    }

    /**
     * 
     * @param owner 
     */
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
    public boolean cardHandingPossible() {
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
    public void removeCards(List<String> selectedCards, List<CardModel> deck) {
        this.getCards().stream()
                .filter(c -> selectedCards.contains(c.getCountryName()))
                .forEach(cs -> deck.add(0, cs));

        this.cards.removeIf(c -> selectedCards.contains(c.getCountryName()));
    }

    /**
     * Getter for current attribute
     *
     * @return whether or not this Hand is the Hand of the current player
     */
    public boolean isCurrent() {
        return current;
    }

    /**
     * 
     * @param current 
     */
    void setCurrent(boolean current) {
        this.current = current;

        setChanged();
        notifyObservers();
    }

    /**
     * 
     * @param typeOfArmie
     * @param deck 
     */
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

    /**
     * 
     * @return 
     */
    public boolean isHanded() {
        return handed;
    }

    /**
     * 
     * @param handed 
     */
    public void setHanded(boolean handed) {
        this.handed = handed;

        setChanged();
        notifyObservers();
    }

    /**
     * 
     * @return 
     */
    public int getNbSelectedCards() {
        return this.selectedCards.size();
    }

    /**
     * 
     */
    public void unselectAllCards() {
        this.selectedCards.clear();

        setChanged();
        notifyObservers();
    }

    /**
     * 
     * @return 
     */
    public List<String> getSelectedCards() {
        return Collections.unmodifiableList(this.selectedCards);
    }

    /**
     * 
     * @param cardName
     * @return 
     */
    public boolean isCardSelected(String cardName) {
        return this.selectedCards.contains(cardName);
    }

    /**
     * 
     * @param cardName 
     */
    public void unselectCard(String cardName) {
        this.selectedCards.remove(cardName);

        setChanged();
        notifyObservers();
    }

    /**
     * 
     * @param cardName 
     */
    public void selectCard(String cardName) {
        this.selectedCards.add(cardName);

        setChanged();
        notifyObservers();
    }
}
