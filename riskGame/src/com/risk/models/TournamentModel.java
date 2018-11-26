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
import java.util.UUID;
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
     * Prefix for all log file produced during this tournament
     */
    String logFileIdentifier;

    /**
     * Map containing RiskModels for each game of the tournament
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
        logFileIdentifier = UUID.randomUUID().toString();
    }

    public Set<MapPath> getMapsPaths() {
        return Collections.unmodifiableSet(mapsPaths);
    }

    public void addMapsPath(MapPath mapPath) {
        if (this.mapsPaths.size() >= 5) {
            throw new IllegalStateException("Cannot add map, the maximum "
                    + "number of map for a tournament is already reached");
        }

        MapModel testMap = new MapModel();
        try {
            MapFileManagement.createBoard(mapPath.getPath(), testMap);
        } catch (MapFileManagement.MapFileManagementException ex) {
            throw new IllegalStateException("The map file" + mapPath.toString()
                    + " is not valid");
        }

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
        if (maximumTurnPerGame > 50) {
            throw new IllegalStateException("Cannot set the maximum number of "
                    + "turn per game, it cannot exceed 50 turns.");
        }

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
            gamesOnMap.add(this.playGame(mapPath, gamesOnMap.size()));
        }

        return gamesOnMap;
    }

    private RiskModel playGame(MapPath mapPath, int index)
            throws MapFileManagement.MapFileManagementException {
        RiskModel rm = new RiskModel();

        MapModel map = new MapModel();
        MapFileManagement.createBoard(mapPath.getPath(), map);

        rm.setMap(map);
        rm.setPlayerList(preparePlayers());
        rm.setInterPhaseTime(0);
        rm.setNbTurnBeforeDraw(this.maximumTurnPerGame);

        LogWriter newLogWriter = new LogWriter(String.format("%s-%s-%d.log",
                logFileIdentifier, mapPath.toString(), index));
        rm.setLogWriter(newLogWriter);
        newLogWriter.openFile();

        rm.startGame();
        rm.finishPhase(); //skip startup phase

        return rm;
    }

    public String getLogFile(int i, int j) {
        String mapName = this.mapsPaths.stream().skip(i).findFirst().get()
                .toString();
        return String.format("%s-%s-%d.log", logFileIdentifier, mapName, j);
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
        return this.mapsPaths.size() + 1;
    }

    @Override
    public int getColumnCount() {
        return this.nbGamePerMap + 1;
    }

    @Override
    public String getColumnName(int i) {
        return String.format("Game %d", i + 1);
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
        if (i == 0 && j == 0) {
            return "";
        }
        if (i == 0) {
            return this.getColumnName(j - 1);
        }
        if (j == 0) {
            return this.mapsPaths.stream().skip(i - 1).findFirst()
                    .get().toString();
        }

        MapPath ithMapPath = this.mapsPaths.stream().skip(i - 1).findFirst().get();
        PlayerModel winner = this.games.get(ithMapPath).get(j - 1).getWinningPlayer();

        return winner != null ? winner.getName() : "DRAW";
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
                        (g) -> (g.getWinningPlayer() != null || g.isDraw())
                )
        );
    }

}
