/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Observable;
import java.util.Set;

/**
 * Model for the tournament mode menu
 *
 * @author hantoine
 */
public class TournamentModel extends Observable {

    /**
     * Set of the paths of the map to use in the tournament
     */
    Set<MapPath> mapsPaths;
    /**
     * Set of the player strategies to play in the tournament
     */
    Set<Strategy.Type> playerStategies;
    /**
     * Number of games that should be played in the tournament
     */
    int nbGamePerMap;
    /**
     * Maximum number of turn in a game of the tournament after which the game
     * stops with a draw
     */
    int maximumTurnPerGame;

    /**
     * Constructor
     */
    public TournamentModel() {
        mapsPaths = new LinkedHashSet<>();
        playerStategies = new LinkedHashSet<>();
        nbGamePerMap = 4;
        maximumTurnPerGame = 40;
    }

    public Set<MapPath> getMapsPaths() {
        return Collections.unmodifiableSet(mapsPaths);
    }

    public void addMapsPath(MapPath mapPath) {
        this.mapsPaths.add(mapPath);

        setChanged();
        notifyObservers();
    }

    public void removeMapsPath(MapPath mapPath) {
        this.mapsPaths.remove(mapPath);

        setChanged();
        notifyObservers();
    }

    public Set<Strategy.Type> getPlayerStategies() {
        return Collections.unmodifiableSet(playerStategies);
    }

    public void addPlayerStategies(Strategy.Type playerStategy) {
        this.playerStategies.add(playerStategy);

        setChanged();
        notifyObservers();
    }

    public void removePlayerStategies(Strategy.Type playerStategy) {
        this.playerStategies.remove(playerStategy);

        setChanged();
        notifyObservers();
    }

    public int getNbGamePerMap() {
        return nbGamePerMap;
    }

    public void setNbGamePerMap(int nbGamePerMap) {
        this.nbGamePerMap = nbGamePerMap;

        setChanged();
        notifyObservers();
    }

    public int getMaximumTurnPerGame() {
        return maximumTurnPerGame;
    }

    public void setMaximumTurnPerGame(int maximumTurnPerGame) {
        this.maximumTurnPerGame = maximumTurnPerGame;

        setChanged();
        notifyObservers();
    }
}
