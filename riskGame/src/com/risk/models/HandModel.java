/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import java.io.Serializable;
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
public class HandModel extends Observable implements Serializable {

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
     *
     * @param card the card to add
     */
    public void addCardToPlayerHand(CardModel card) {
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
     * This method is for get the current owner
     *
     * @return the owner
     */
    public PlayerModel getOwner() {
        return owner;
    }

    /**
     * This method is for set the current owner
     *
     * @param owner the owner of the hand
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
                .filter(c -> selectedCards.contains(c.getTerritoryName()))
                .forEach(cs -> deck.add(0, cs));

        this.cards.removeIf(c -> selectedCards.contains(c.getTerritoryName()));
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
     * This method is for set current attribute
     *
     * @param current the current status
     */
    void setCurrent(boolean current) {
        this.current = current;

        setChanged();
        notifyObservers();
    }

    /**
     * This method is for removing card
     *
     * @param typeOfArmie the type of the armies
     * @param deck the list of the card
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
    }

    /**
     * This method is for get the status of card
     *
     * @return the status of the card
     */
    public boolean isHanded() {
        return handed;
    }

    /**
     * This method is for set the handed boolean
     *
     * @param handed the status of the card
     */
    public void setHanded(boolean handed) {
        this.handed = handed;

        setChanged();
        notifyObservers();
    }

    /**
     * This method is to get the number of selected cards
     *
     * @return the cards size
     */
    public int getNbSelectedCards() {
        return this.selectedCards.size();
    }

    /**
     * This method is for unselect all cards
     */
    public void unselectAllCards() {
        this.selectedCards.clear();

        setChanged();
        notifyObservers();
    }

    /**
     * This method is for get the list of selected card
     *
     * @return the list of selected card
     */
    public List<String> getSelectedCards() {
        return Collections.unmodifiableList(this.selectedCards);
    }

    /**
     * This method is for check the card is selected or not
     *
     * @param cardName the name of the card
     * @return if the card is selected
     */
    public boolean isCardSelected(String cardName) {
        return this.selectedCards.contains(cardName);
    }

    /**
     * This method is for unselect the card
     *
     * @param cardName the name of the card
     */
    public void unselectCard(String cardName) {
        this.selectedCards.remove(cardName);

        setChanged();
        notifyObservers();
    }

    /**
     * This method is for select the card
     *
     * @param cardName the name of the card
     */
    public void selectCard(String cardName) {
        this.selectedCards.add(cardName);

        setChanged();
        notifyObservers();
    }
}
