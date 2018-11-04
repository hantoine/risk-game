/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import java.awt.Color;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Observable;
import java.util.stream.Collectors;

/**
 * It represents a Player in the game It is the parent of HumanPlayerModel and
 * AIPlayerModel
 *
 * @author n_irahol
 */
public abstract class PlayerModel extends Observable {

    /**
     * name the name of the player color the color of the player contriesOwned
     */
    private String name;
    /**
     * color of the player
     */
    private Color color;
    /**
     * cardsOwned cards owned by a player
     */
    private List<TerritoryModel> contriesOwned;
    /**
     * continents owned by a player 
     */
    private List<ContinentModel> continentsOwned;
    /**
     * hand of the player
     */
    private HandModel hand;
    /**
     * The number of armies available to place returnedCards
     */
    private int numArmiesAvailable;
    /**
     * the number of cards that have been returned game the game in which this
     */
    private int returnedCards;
    /**
     * the model of the game
     */
    protected RiskModel game;
    /**
     * the current movement in the fortification phase
     */
    private FortificationMove currentFortificationMove;
    /**
     * The current attack in the attack phase
     */
    private AttackMove currentAttack;
    /**
     * the current player
     */
    private boolean currentPlayer;

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
        this.hand = new HandModel();
        this.hand.setOwner(this);
        this.numArmiesAvailable = 0;
        this.returnedCards = 0;
        this.game = game;
        this.currentAttack = null;
        this.currentPlayer = false;
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
    public abstract void reinforcement(RiskModel playGame);

    /**
     * Definition of the fortification phase. Called at the beginning of the
     * phase. Depending on the type of player it will either initialize and
     * update the UI for the human player to play or execute the action with the
     * artificial intelligence
     *
     * @param playGame GameController reference used to access game informations
     * and methods
     */
    public abstract void fortification(RiskModel playGame);

    /**
     * Definition of the attack phase. Called at the beginning of the phase.
     * Depending on the type of player it will either initialize and update the
     * UI for the human player to play or execute the action with the artificial
     * intelligence
     *
     * @param playGame GameController reference used to access game informations
     * and methods
     */
    public abstract void attack(RiskModel playGame);

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
    void setName(String name) {
        this.name = name;

        setChanged();
        notifyObservers();
    }

    /**
     * Getter of the currentPlayer attribute
     *
     * @return whether this player is the current player or not
     */
    public boolean isCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Setter of the currentPlayer attribute
     *
     * @param currentPlayer whether this player is the current player or not
     */
    void setCurrentPlayer(boolean currentPlayer) {
        this.currentPlayer = currentPlayer;
        this.hand.setCurrent(currentPlayer);

        if (currentPlayer) {
            addNewLogEvent(String.format(
                    "%s starts its turn",
                    getName()
            ));
        } else {
            setChanged();
            notifyObservers();
        }
    }

    /**
     * Getter of the color attribute
     *
     * @return the color
     */
    public Color getColor() {
        return this.color;
    }

    RiskModel getGame() {
        return game;
    }

    void setGame(RiskModel game) {
        this.game = game;

        setChanged();
        notifyObservers();
    }

    /**
     * Setter of the color attribute
     *
     * @param color the color to set
     */
    void setColor(Color color) {
        this.color = color;

        setChanged();
        notifyObservers();
    }

    /**
     * Getter of the countriesOwned attribute
     *
     * @return the contriesOwned
     */
    public List<TerritoryModel> getContriesOwned() {
        return Collections.unmodifiableList(this.contriesOwned);
    }

    public boolean checkOwnTerritory(TerritoryModel territory) {
        return contriesOwned.contains(territory);
    }

    /**
     * Return the number of countries owned by this player
     *
     * @return the number of countries owned by this player
     */
    public int getNbCountriesOwned() {
        return contriesOwned.size();
    }

    /**
     * Return the number of countries owned by this player
     *
     * @return the number of countries owned by this player
     */
    public int getNbContinentsOwned() {
        return continentsOwned.size();
    }

    /**
     * Setter of the countriesOwned attribute
     *
     * @param contriesOwned the contriesOwned to set
     */
    void setContriesOwned(Collection<TerritoryModel> contriesOwned) {

        this.contriesOwned.stream()
                .filter(c -> c.getOwner() != null)
                .forEach((c) -> {
                    c.getOwner().removeCountryOwned(c);
                });
        this.contriesOwned = new LinkedList(contriesOwned);

        this.contriesOwned.stream().forEach((c) -> {
            c.setOwner(this);
        });

        updateContinentsOwned();

        setChanged();
        notifyObservers();
    }

    /**
     * Add a country to the list of countries owned by this player
     *
     * @param countryOwned the additional country owned by this player
     */
    void addCountryOwned(TerritoryModel countryOwned) {
        if (countryOwned.getOwner() != null) {
            countryOwned.getOwner().removeCountryOwned(countryOwned);
        }
        this.contriesOwned.add(countryOwned);
        countryOwned.setOwner(this);

        updateContinentsOwned();

        setChanged();
        notifyObservers();
    }

    /**
     * Remove a country from the list of countries owned by this player
     *
     * @param countryOwned the country no longer owned by this player
     */
    void removeCountryOwned(TerritoryModel countryOwned) {
        this.contriesOwned.remove(countryOwned);
        countryOwned.setOwner(this);

        updateContinentsOwned();

        setChanged();
        notifyObservers();
    }

    /**
     * Getter of the continentsOwned attribute
     *
     * @return the continentsOwned
     */
    public Collection<ContinentModel> getContinentsOwned() {
        return Collections.unmodifiableCollection(this.continentsOwned);
    }

    /**
     * Setter of the continentsOwned attribute
     *
     * @param continentsOwned the continentsOwned to set
     */
    void setContinentsOwned(List<ContinentModel> continentsOwned) {
        this.continentsOwned = continentsOwned;

        setChanged();
        notifyObservers();
    }

    /**
     * Add a continent in the list of continents owned by this player
     *
     * @param newContinentOwned New continent owned by this player
     */
    void addContinentOwned(ContinentModel newContinentOwned) {
        if (this.continentsOwned == null) {
            this.continentsOwned = new LinkedList<>();
        }

        this.continentsOwned.add(newContinentOwned);

        setChanged();
        notifyObservers();
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
    void setNumArmiesAvailable(int numArmies) {
        this.numArmiesAvailable = numArmies;

        setChanged();
        notifyObservers();
    }

    /**
     * Decrease by one the number of armies this player has available
     *
     * @return the new number of armies available for this player
     */
    int decrementNumArmiesAvailable() {
        this.numArmiesAvailable--;

        setChanged();
        notifyObservers();

        return this.numArmiesAvailable;
    }

    /**
     * Increase the number of armies this player has available
     *
     * @param i the number of the armies
     */
    void addNumArmiesAvailable(int i) {
        this.setNumArmiesAvailable(this.getNumArmiesAvailable() + i);
    }

    /**
     * Get the number of initial armies players should get depending on the
     * number of players in the game
     *
     * @param nbPlayers number of players in the game
     * @return number of initial armies
     */
    static int getNbInitialArmies(int nbPlayers) {
        switch (nbPlayers) {
            case 2:
                return 40;
            case 3:
                return 35;
            case 4:
                return 30;
            case 5:
                return 25;
            case 6:
                return 20;
            default:
                throw new IllegalArgumentException("Invalid number of players");
        }
    }

    /**
     * Assign new armies to the player. Called at each reinforcement phase.
     *
     */
    void assignNewArmies() {
        int newArmies = this.armiesAssignation();
        this.setNumArmiesAvailable(newArmies);
        addNewLogEvent(String.format(
                "%s receives %d new armies",
                getName(),
                newArmies
        ));
    }

    /**
     * Getter of the cardsOwned attribute
     *
     * @return the cardsOwned
     */
    public HandModel getHand() {
        return hand;
    }

    /**
     * Setter of the cardsOwned attribute
     *
     * @param hand the cardsOwned to set
     */
    void setHand(HandModel hand) {
        this.hand = hand;

        setChanged();
        notifyObservers();
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
    void setReturnedCards(int returnedCards) {
        this.returnedCards = returnedCards;

        setChanged();
        notifyObservers();
    }

    /**
     * Return the total number of armies owned by this player
     *
     * @return Total number of armies owned by this player
     */
    public int getNbArmiesOwned() {
        int numArmiesDeployed = this.contriesOwned.stream()
                .mapToInt((country) -> country.getNumArmies()).sum();

        return numArmiesDeployed + this.getNumArmiesAvailable();
    }

    /**
     * Assign the armies for reinforcement phase
     *
     * @return number of armies to deploy
     */
    int armiesAssignation() {
        int extraCountries = (int) Math.floor(this.getNbCountriesOwned() / 3);
        int extraContinent = 0;
        for (ContinentModel continent : this.getContinentsOwned()) {
            extraContinent += continent.getBonusScore();
        }

        if (extraContinent + extraCountries < 3) {
            return 3;
        } else {
            return extraContinent + extraCountries;
        }
    }

    /**
     * Calls the function to add the armies of the handed cards
     */
    void armiesCardAssignation() {
        int numberArmiesCard = this.armiesAssignationCards();
        this.addNumArmiesAvailable(numberArmiesCard);
    }

    /**
     * Assign extra armies depending on handed cards
     *
     * @return number of extra armies according to handed cards
     */
    int armiesAssignationCards() {
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
     *
     * @return true if the cards are equal or different; false in other case
     */
    abstract boolean exchangeCardsToArmies();

    /**
     * Adds a card to the player's hand from the deck
     */
    void addCardToPlayerHand() {
        HandModel handCurrentPlayer = this.getHand();
        try {
            CardModel card = this.game.getDeck().getLast();
            handCurrentPlayer.getCardsList().add(card);
            this.game.getDeck().removeLast();

            addNewLogEvent(String.format(
                    "%s receives a new card",
                    getName()
            ));
        } catch (NoSuchElementException ex) {
            System.out.println("No card left in deck");
        }
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
     *@param src source territory
     *@param dest destiny of the fortification move
     */
    void setCurrentFortificationMove(TerritoryModel src, TerritoryModel dest) {
        this.currentFortificationMove = new FortificationMove(src, dest);

        setChanged();
        notifyObservers();
    }

    /**
     * @return the handed
     */
    public boolean isHanded() {
        return hand.isHanded();
    }

    /**
     * @param handed the handed to set
     */
    public void setHanded(boolean handed) {
        this.hand.setHanded(handed);

        setChanged();
        notifyObservers();
    }

    /**
     * @return the currentAttack
     */
    public AttackMove getCurrentAttack() {
        return currentAttack;
    }

    /**
     * @param currentAttack the currentAttack to set
     */
    public void setCurrentAttack(AttackMove currentAttack) {
        this.currentAttack = currentAttack;

        setChanged();
        notifyObservers();
    }

    /**
     * Perform the current attack of this player with the given number of dice
     * The value -1 correspond to the special mode in which battles are made
     * until one of the territory has no more armies
     *
     * @param diceAttack number of dices selected by the attacker
     * @param diceAttacked number of dices selected by the attacked player
     */
    public void performCurrentAttack(int diceAttack, int diceAttacked) {
        if (this.getCurrentAttack() == null) {
            return;
        }
      
        this.getCurrentAttack().perform(diceAttack, diceAttacked);

        /*
        when the battle is finished if there is still armies on the attacked
        territory, then this attack is terminated. If not the the attacked
        territory needs to be conquered before the attack terminates
         */
        if (this.currentAttack.getDest().getNumArmies() != 0) {
            this.setCurrentAttack(null);
        } else {
            addNewLogEvent(String.format(
                    "%s conquered the territory %s",
                    getName(),
                    this.currentAttack.getDest().getName()
            ));
        }
    }

    /**
     * Conquer a country after an attack
     *
     * @param armies number of armies to move to the new conquered country
     * @return -1 error; 0 success
     */
    public int conquerCountry(int armies) {
        if(armies<this.getCurrentAttack().getDiceAttack() || armies>=this.getCurrentAttack().getSource().getNumArmies())
            return -1;

        int newArmies = this.getCurrentAttack().getSource().getNumArmies();
        this.getCurrentAttack().getSource().setNumArmies(newArmies - armies);
        this.getCurrentAttack().getDest().setNumArmies(armies);

        addCountryOwned(this.getCurrentAttack().getDest());
        this.setCurrentAttack(null);
        return 0;
    }

    /**
     *  It verifies that the current attack is valid
     * @param sourceTerritory source territory of attack
     * @param destTerritory territory attacked
     * @return -1 error;0 success
     */
    public int validateAttack(TerritoryModel sourceTerritory, TerritoryModel destTerritory) {
        if (!sourceTerritory.getAdj().contains(destTerritory)) {
            return -1;
        }
        if (this.getCurrentAttack() != null) {
            return -2;
        }
        if (!this.getContriesOwned().contains(sourceTerritory)
                || this.getContriesOwned().contains(destTerritory)) {
            return -3;
        }
        if (sourceTerritory.getNumArmies() < 2) {
            return -4;
        }

        return 0;
    }
    
    /**
     * Set fortification move to null
     */
    void resetCurrentFortificationMove() {
        this.currentFortificationMove = null;

        setChanged();
        notifyObservers();
    }

    /**
     * Verifies that a continent is not owned
     * @param continent continent to be verified
     * @return true id it is owned; false in other case
     */
    boolean checkOwnContinent(ContinentModel continent) {
        return this.continentsOwned.contains(continent);
    }

    /**
     * Calculates the % of countries owned
     * @return % of countries owned
     */
    public int getPercentMapControlled() {
        int nbTerrInMap = this.getGame().getMap().getTerritories().size();
        return (100 * this.getNbCountriesOwned()) / nbTerrInMap;
    }

    /**
     * Addition of an event in the log
     * @param logMessage message to add to the log
     */
    void addNewLogEvent(String logMessage) {
        setChanged();
        notifyObservers(new LogEvent(logMessage));
    }

    /**
     * Setter of the current attack
     * @param src source country
     * @param dest country attacked
     */
    void startAttackMove(TerritoryModel src, TerritoryModel dest) {
        AttackMove attack = new AttackMove(this, src, dest);
        this.setCurrentAttack(attack);
    }

    /**
     * Update the list of continents owned by this players from the list of
     * territories owned by this player
     */
    private void updateContinentsOwned() {
        List<ContinentModel> newContinentsOwned;
        newContinentsOwned = this.game.getMap().getContinents().stream()
                .filter((c) -> c.getMembers().stream()
                .allMatch((t) -> this.contriesOwned.contains(t)))
                .collect(Collectors.toList());
        this.setContinentsOwned(newContinentsOwned);
    }
}
