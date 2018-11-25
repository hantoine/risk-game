/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Set;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 * Model for the tournament mode menu
 *
 * @author hantoine
 */
public class TournamentModel extends Observable implements TableModel {

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
     *
     */
    Map<MapPath, List<RiskModel>> games;

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

    public void playTournament()
            throws MapFileManagement.MapFileManagementException {
        games = new HashMap<>();

        for (MapPath mapPath : this.mapsPaths) {
            this.games.put(mapPath, this.playGamesOnMap(mapPath));
        }

        setChanged();
        notifyObservers();
    }

    private List<RiskModel> playGamesOnMap(MapPath mapPath)
            throws MapFileManagement.MapFileManagementException {
        List<RiskModel> gamesOnMap = new ArrayList<>(this.nbGamePerMap);

        while (gamesOnMap.size() < this.nbGamePerMap) {
            gamesOnMap.add(this.playGame(mapPath));
        }

        return gamesOnMap;
    }

    private RiskModel playGame(MapPath mapPath)
            throws MapFileManagement.MapFileManagementException {
        RiskModel rm = new RiskModel();

        MapModel map = new MapModel();
        MapFileManagement.createBoard(mapPath.getPath(), map);

        rm.setMap(map);
        rm.setPlayerList(preparePlayers());
        rm.startGame();
        rm.finishPhase(); //skip startup phase

        return rm;
    }

    private LinkedList<PlayerModel> preparePlayers() {
        LinkedList<PlayerModel> players = new LinkedList<>();

        for (Strategy.Type strategy : this.playerStategies) {
            players.add(
                    PlayerFactory.getPlayer(
                            strategy.toString(),
                            strategy.toString(),
                            Color.black
                    )
            );
        }

        return players;
    }

    public Map<MapPath, List<RiskModel>> getGames() {
        return Collections.unmodifiableMap(games);
    }

    @Override
    public int getRowCount() {
        return this.mapsPaths.size();
    }

    @Override
    public int getColumnCount() {
        return this.nbGamePerMap;
    }

    @Override
    public String getColumnName(int i) {
        return String.format("Game %d", i);
    }

    @Override
    public Class<?> getColumnClass(int i) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int i, int j) {
        return false;
    }

    @Override
    public Object getValueAt(int i, int j) {
        MapPath ithMapPath = this.mapsPaths.stream().skip(i).findFirst().get();
        PlayerModel winner = this.games.get(ithMapPath).get(j).getWinningPlayer();

        return winner != null ? winner.getName() : "Draw";
    }

    @Override

    public void setValueAt(Object o, int i, int j) {
    }

    @Override
    public void addTableModelListener(TableModelListener tl) {
    }

    @Override
    public void removeTableModelListener(TableModelListener tl) {
    }

    public boolean isTournamentFinished() {
        return this.games.values().stream().allMatch(
                (lg) -> lg.stream().allMatch(
                        (g) -> g.getWinningPlayer() != null
                )
        );
    }

}
