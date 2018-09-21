/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Nellybett
 */
public class RiskModel {

    private Board board;
    private LinkedList<Player> players;
    private Player currentPlayer;

    public RiskModel() {

    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setBoard(String path) {
        board = FileManagement.createBoard(path);
    }

    public void createFile(String fileContent) {
        FileManagement.generateBoardFile(fileContent);
    }

    public Board getBoard() {
        return board;
    }

    /**
     * Assigns random countries to players
     */
    public void assignCoutriesToPlayers() {
        if (players == null) {
            throw new IllegalArgumentException();
        }

        List<Country> countriesLeft = new ArrayList<>(board.getGraphTerritories().values());
        Collections.shuffle(countriesLeft);

        int countriesPerPlayer = (countriesLeft.size() / players.size());

        players.stream().forEach((player) -> {
            List<Country> ownedCountries = countriesLeft.subList(0, countriesPerPlayer);
            player.setContriesOwned(ownedCountries);
            countriesLeft.removeAll(ownedCountries);
        });

        Random rnd = new Random();
        while (!countriesLeft.isEmpty()) {
            int playerIndex = rnd.nextInt(players.size());
            players.get(playerIndex).addCountryOwned(countriesLeft.remove(0));
        }
    }
}
