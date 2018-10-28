/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import com.risk.controllers.GameController;
import java.awt.Color;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Observable;

/**
 * It represents a Player in the game It is the parent of HumanPlayerModel and
 * AIPlayerModel
 *
 * @author n_irahol
 */
public abstract class PlayerModel extends Observable {

    /**
     * name the name of the player color the color of the player contriesOwned
     * countries owned by a player cardsOwned cards owned by a player
     * numArmiesAvailable the number of armies available to place returnedCards
     * the number of cards that have been returned game the game in which this
     * player belongs to currentFortificationMove the current movement in the
     * fortification phase
     */
    private String name;
    private Color color;
    private Collection<TerritoryModel> contriesOwned;
    private Collection<ContinentModel> continentsOwned;
    private HandModel cardsOwned;
    private int numArmiesAvailable;
    private int returnedCards;
    private RiskModel game;
    private FortificationMove currentFortificationMove;
    private boolean handed;
    /**
     * Constructor
     *
     * @param name name of a player
     * @param color color of a player
     * @param isHuman true if the player is human
     * @param game Game in which this player belongs
     */
    public PlayerModel(String name, Color color, boolean isHuman, RiskModel game) {
        this.name = name;
        this.color = color;
        this.contriesOwned = new LinkedList<>();
        this.continentsOwned = new LinkedList<>();
        this.cardsOwned = new HandModel();
        this.numArmiesAvailable = 0;
        this.returnedCards = 0;
        this.game = game;
    }

    /**
     * Definition of the reinforcement phase. Called at the beginning of the
     * phase. Depending on the type of player it will either initialize and
     * update the UI for the human player to play or execute the action with the
     * artificial intelligence
     *
     * @param playGame GameController reference used to access game informations
     * and methods
     */
    public abstract void reinforcement(GameController playGame);

    /**
     * Definition of the fortification phase. Called at the beginning of the
     * phase. Depending on the type of player it will either initialize and
     * update the UI for the human player to play or execute the action with the
     * artificial intelligence
     *
     * @param playGame GameController reference used to access game informations
     * and methods
     */
    public abstract void fortification(GameController playGame);

    /**
     * Definition of the attack phase. Called at the beginning of the phase.
     * Depending on the type of player it will either initialize and update the
     * UI for the human player to play or execute the action with the artificial
     * intelligence
     *
     * @param playGame GameController reference used to access game informations
     * and methods
     */
    public abstract void attack(GameController playGame);

    /**
     * Getter of the name attribute
     *
     * @return the name of this player
     */
    public String getName() {
        return name;
    }

    /**
     * Setter of the name attribute
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter of the color attribute
     *
     * @return the color
     */
    public Color getColor() {
        return this.color;
    }

    public RiskModel getGame() {
        return game;
    }

    public void setGame(RiskModel game) {
        this.game = game;
    }

    /**
     * Setter of the color attribute
     *
     * @param color the color to set
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Getter of the countriesOwned attribute
     *
     * @return the contriesOwned
     */
    public Collection<TerritoryModel> getContriesOwned() {
        return contriesOwned;
    }

    /**
     * Setter of the countriesOwned attribute
     *
     * @param contriesOwned the contriesOwned to set
     */
    public void setContriesOwned(Collection<TerritoryModel> contriesOwned) {

        this.contriesOwned.stream()
                .filter(c -> c.getOwner() != null)
                .forEach((c) -> {
                    c.getOwner().removeCountryOwned(c);
                });
        this.contriesOwned = new LinkedList(contriesOwned);

        this.contriesOwned.stream().forEach((c) -> {
            c.setOwner(this);
        });
    }

    /**
     * Add a country to the list of countries owned by this player
     *
     * @param countryOwned the additional country owned by this player
     */
    public void addCountryOwned(TerritoryModel countryOwned) {
        if (countryOwned.getOwner() != null) {
            countryOwned.getOwner().removeCountryOwned(countryOwned);
        }
        this.contriesOwned.add(countryOwned);
        countryOwned.setOwner(this);
    }

    /**
     * Remove a country from the list of countries owned by this player
     *
     * @param countryOwned the country no longer owned by this player
     */
    public void removeCountryOwned(TerritoryModel countryOwned) {
        this.contriesOwned.remove(countryOwned);
        countryOwned.setOwner(this);
    }

    /**
     * Getter of the continentsOwned attribute
     *
     * @return the continentsOwned
     */
    public Collection<ContinentModel> getContinentsOwned() {
        return continentsOwned;
    }

    /**
     * Setter of the continentsOwned attribute
     *
     * @param continentsOwned the continentsOwned to set
     */
    public void setContinentsOwned(Collection<ContinentModel> continentsOwned) {
        this.continentsOwned = continentsOwned;
    }

    /**
     * Add a continent in the list of continents owned by this player
     *
     * @param newContinentOwned New continent owned by this player
     */
    public void addContinentOwned(ContinentModel newContinentOwned) {
        if (this.continentsOwned == null) {
            this.continentsOwned = new LinkedList<>();
        }

        this.continentsOwned.add(newContinentOwned);
    }

    /**
     * Getter of the numArmiesAvailable attribute
     *
     * @return the numArmies
     */
    public int getNumArmiesAvailable() {
        return numArmiesAvailable;
    }

    /**
     * Setter of the numArmiesAvailable attribute
     *
     * @param numArmies the numArmies to set
     */
    private void setNumArmiesAvailable(int numArmies) {
        this.numArmiesAvailable = numArmies;
    }

    /**
     * Decrease by one the number of armies this player has available
     *
     * @return the new number of armies available for this player
     */
    public int decrementNumArmiesAvailable() {
        return --this.numArmiesAvailable;
    }

    /**
     * Increase the number of armies this player has available
     *
     * @param i the number of the armies
     */
    public void addNumArmiesAvailable(int i) {
        this.setNumArmiesAvailable(this.getNumArmiesAvailable() + i);
    }

    /**
     * Initialize the number of initial armies of this player depending on the
     * number of players in the game
     *
     * @param nbPlayers number of players in the game
     */
    public void initializeArmies(int nbPlayers) {
        switch (nbPlayers) {
            case 2:
                this.setNumArmiesAvailable(40);
                break;
            case 3:
                this.setNumArmiesAvailable(35);
                break;
            case 4:
                this.setNumArmiesAvailable(30);
                break;
            case 5:
                this.setNumArmiesAvailable(25);
                break;
            case 6:
                this.setNumArmiesAvailable(20);
                break;
            default:
                throw new IllegalArgumentException("Invalid number of players");
        }
    }

    /**
     * Assign new armies to the player. Called at each reinforcement phase.
     *
     */
    public void assignNewArmies() {
        this.setNumArmiesAvailable(this.armiesAssignation());
    }

    /**
     * Getter of the cardsOwned attribute
     *
     * @return the cardsOwned
     */
    public HandModel getCardsOwned() {
        return cardsOwned;
    }

    /**
     * Setter of the cardsOwned attribute
     *
     * @param cardsOwned the cardsOwned to set
     */
    public void setCardsOwned(HandModel cardsOwned) {
        this.cardsOwned = cardsOwned;
    }

    /**
     * Getter of the returnedCards attribute
     *
     * @return the returnedCards
     */
    public int getReturnedCards() {
        return returnedCards;
    }

    /**
     * Setter of the returnedCards attribute
     *
     * @param returnedCards the returnedCards to set
     */
    public void setReturnedCards(int returnedCards) {
        this.returnedCards = returnedCards;
    }

    /**
     * Return the total number of armies owned by this player
     *
     * @return Total number of armies owned by this player
     */
    public int getNumArmiesOwned() {
        int numArmiesDeployed = this.getContriesOwned().stream()
                .mapToInt((country) -> country.getNumArmies()).sum();

        return numArmiesDeployed + this.getNumArmiesAvailable();
    }

    /**
     * Assign the armies for reinforcement phase
     *
     * @return number of armies to deploy
     */
    public int armiesAssignation() {
        int extraCountries = (int) Math.floor(this.getContriesOwned().size() / 3);
        int extraContinent = 0;
        for (ContinentModel continent : this.getContinentsOwned()) {
            extraContinent += continent.getBonusScore();
        }

        System.out.println(extraContinent + extraCountries);
        if (extraContinent + extraCountries < 3) {
            return 3;
        } else {
            return extraContinent + extraCountries;
        }
    }

    /**
     * Calls the function to add the armies of the handed cards
     */
    public void armiesCardAssignation() {
        int numberArmiesCard = this.armiesAssignationCards();
        this.addNumArmiesAvailable(numberArmiesCard);
    }

    /**
     * Assign extra armies depending on handed cards
     *
     * @return number of extra armies according to handed cards
     */
    public int armiesAssignationCards() {
        this.setReturnedCards(this.getReturnedCards() + 3);

        switch (this.getReturnedCards()) {
            case 3:
                return 4;
            case 6:
                return 6;
            case 9:
                return 8;
            case 12:
                return 10;
            case 15:
                return 12;
            case 18:
                return 15;
            default:
                return 15 + (((this.getReturnedCards() - 18) / 3) * 5); //after 18 you get 5 more for every 3 cards returned
        }

    }

    /**
     * Function that removes the cards and calls a function that assigns armies
     * depending on the number of cards the player has handed
     * @return 
     */
    public boolean exchangeCardsToArmies(LinkedList<String> selectedCards) {
        LinkedList<String> typeOfArmie=new LinkedList<>();
        this.getCardsOwned().getCards().stream()
                .filter(c -> selectedCards.contains(c.getCountryName()))
                .forEach(cs -> typeOfArmie.add(cs.getTypeOfArmie()));
                    
        boolean areEqual= typeOfArmie.stream()
                            .allMatch(a -> a.equals(typeOfArmie.getFirst()));
        boolean different=!(typeOfArmie.get(0).equals(typeOfArmie.get(1))) && !(typeOfArmie.get(0).equals(typeOfArmie.get(2))) && !(typeOfArmie.get(2).equals(typeOfArmie.get(1)));
        
        System.out.println("igual: "+areEqual+" diferente: "+different);
        if (areEqual || different) {
            this.getCardsOwned().removeCards(selectedCards, this.game.getDeck());
            armiesCardAssignation();
            this.setChanged();
            this.notifyObservers(this.game);
            return true;
        }else
            return false;
       
    }

    /**
     * Adds a card to the player's hand from the deck
     */
    public void addCardToPlayerHand() {
        LinkedList<String> cardsOperation = new LinkedList<>();
        HandModel handCurrentPlayer = this.getCardsOwned();
        CardModel card = this.game.getDeck().getLast();

        cardsOperation.add("add");
        cardsOperation.add(card.getCountryName());
        cardsOperation.add(card.getTypeOfArmie());
        handCurrentPlayer.getCards().add(card);
        this.game.getDeck().removeLast();

        this.setChanged();
        this.notifyObservers(cardsOperation);
    }

    /**
     * Getter of the currentFortificationMove attribute
     *
     * @return the current movement 
     */
    public FortificationMove getCurrentFortificationMove() {
        return currentFortificationMove;
    }

    /**
     * Setter of the currentFortificationMove attribute
     *
     * @param currentFortificationMove the current move
     */
    public void setCurrentFortificationMove(FortificationMove currentFortificationMove) {
        this.currentFortificationMove = currentFortificationMove;
    }
    
     /**
     * @return the handed
     */
    public boolean isHanded() {
        return handed;
    }

    /**
     * @param handed the handed to set
     */
    public void setHanded(boolean handed) {
        this.handed = handed;
    }
    
}
