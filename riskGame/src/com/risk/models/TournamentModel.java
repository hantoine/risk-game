/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import com.risk.controllers.TournamentSaverInterface;
import java.awt.Color;
import java.io.Serializable;
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
public class TournamentModel extends Observable implements TableModel, Serializable {

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
     * Prefix for all file produced during this tournament
     */
    String fileIdentifier;

    /**
     * Map containing RiskModels for each game of the tournament
     */
    Map<MapPath, List<RiskModel>> games;

    /**
     * Object in charge of saving the tournament progress
     */
    transient TournamentSaverInterface tournamentSaver;

    /**
     * Constructor
     */
    public TournamentModel() {
        mapsPaths = new LinkedHashSet<>();
        playerStategies = new LinkedHashSet<>();
        nbGamePerMap = 4;
        maximumTurnPerGame = 40;
        fileIdentifier = UUID.randomUUID().toString();
    }

    /**
     * Get collections of map paths
     *
     * @return Collections of map paths
     */
    public Set<MapPath> getMapsPaths() {
        return Collections.unmodifiableSet(mapsPaths);
    }

    /**
     * Add a map to the set of map path
     *
     * @param mapPath map path
     */
    public void addMapsPath(MapPath mapPath) {
        if (this.mapsPaths.size() >= 5) {
            throw new IllegalStateException("Cannot add map, the maximum "
                    + "number of map for a tournament is already reached");
        }

        if (this.mapsPaths.stream()
                .anyMatch((mp) -> mp.getPath().equals(mapPath.getPath()))) {
            throw new IllegalStateException("Cannot add the same map twice");
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

    /**
     * Remove a map from the set of map path
     *
     * @param mapPath Map path
     */
    public void removeMapsPath(MapPath mapPath) {
        this.mapsPaths.remove(mapPath);

        setChanged();
        notifyObservers();
    }

    /**
     * Getter of set of player strategies
     *
     * @return Set of player strategies
     */
    public Set<Strategy.Type> getPlayerStategies() {
        return Collections.unmodifiableSet(playerStategies);
    }

    /**
     * Add a computer player to the set of tournament player
     *
     * @param playerStategy Player strategy
     */
    public void addPlayerStategies(Strategy.Type playerStategy) {
        if (playerStategy == Strategy.Type.HUMAN) {
            throw new IllegalStateException("The tournament mode does not "
                    + "support the human strategy.");
        }

        this.playerStategies.add(playerStategy);

        setChanged();
        notifyObservers();
    }

    /**
     * Remove a computer player
     *
     * @param playerStategy Player strategy
     */
    public void removePlayerStategies(Strategy.Type playerStategy) {
        this.playerStategies.remove(playerStategy);

        setChanged();
        notifyObservers();
    }

    /**
     * Getter of number of games
     *
     * @return Number of games
     */
    public int getNbGamePerMap() {
        return nbGamePerMap;
    }

    /**
     * Setter of number of games
     *
     * @param nbGamePerMap number of games
     */
    public void setNbGamePerMap(int nbGamePerMap) {
        if (nbGamePerMap > 5) {
            throw new IllegalStateException("The number of games per map "
                    + "cannot exceed 5.");
        }
        this.nbGamePerMap = nbGamePerMap;

        setChanged();
        notifyObservers();
    }

    /**
     * Getter of maximum number of turn
     *
     * @return maximum number of turn
     */
    public int getMaximumTurnPerGame() {
        return maximumTurnPerGame;
    }

    /**
     * Setter of maximum number of turn
     *
     * @param maximumTurnPerGame maximum number of turn
     */
    public void setMaximumTurnPerGame(int maximumTurnPerGame) {
        if (maximumTurnPerGame > 50) {
            throw new IllegalStateException("Cannot set the maximum number of "
                    + "turn per game, it cannot exceed 50 turns.");
        }

        this.maximumTurnPerGame = maximumTurnPerGame;

        setChanged();
        notifyObservers();
    }

    /**
     * Play the tournament
     *
     * @throws com.risk.models.MapFileManagement.MapFileManagementException
     * MapFileManagementException
     */
    public void playTournament()
            throws MapFileManagement.MapFileManagementException {

        if (this.mapsPaths.isEmpty() || this.playerStategies.size() < 2) {
            return;
        }
        games = new HashMap<>();

        for (MapPath mapPath : this.mapsPaths) {
            this.games.put(mapPath, this.playGamesOnMap(mapPath));
        }

        setChanged();
        notifyObservers();
    }

    /**
     * Play each map
     *
     * @param mapPath Map path
     * @return list of risk model
     * @throws com.risk.models.MapFileManagement.MapFileManagementException
     * MapFileManagementException
     */
    private List<RiskModel> playGamesOnMap(MapPath mapPath)
            throws MapFileManagement.MapFileManagementException {
        List<RiskModel> gamesOnMap = new ArrayList<>(this.nbGamePerMap);

        while (gamesOnMap.size() < this.nbGamePerMap) {
            gamesOnMap.add(this.playGame(mapPath, gamesOnMap.size()));
        }

        return gamesOnMap;
    }

    /**
     * Play each game
     *
     * @param mapPath map path
     * @param index log identifier
     * @return risk model
     * @throws com.risk.models.MapFileManagement.MapFileManagementException
     * MapFileManagementException
     */
    private RiskModel playGame(MapPath mapPath, int index)
            throws MapFileManagement.MapFileManagementException {
        RiskModel rm = new RiskModel();

        MapModel map = new MapModel();
        MapFileManagement.createBoard(mapPath.getPath(), map);

        rm.setMap(map);
        rm.setPlayerList(preparePlayers());
        rm.setInterPhaseTime(0);
        rm.setNbTurnBeforeDraw(this.maximumTurnPerGame);
        rm.setTournamentSaver(this.tournamentSaver);

        LogWriter newLogWriter = new LogWriter(String.format("%s-%s-%d.log",
                fileIdentifier, mapPath.toString(), index));
        rm.setLogWriter(newLogWriter);
        newLogWriter.openFile();

        rm.startGame();
        rm.finishPhase(); //skip startup phase

        return rm;
    }

    /**
     * return the log file corresponding to the game with ith map and the jth
     * game
     *
     * @param i the index of map
     * @param j the index of game
     * @return log file corresponding to the game with ith map and the jth
     */
    public String getLogFile(int i, int j) {
        String mapName = this.mapsPaths.stream().skip(i).findFirst().get()
                .toString();
        return String.format("%s-%s-%d.log", fileIdentifier, mapName, j);
    }

    public String getFileIdentifier() {
        return fileIdentifier;
    }

    /**
     * Getter of list of players
     *
     * @return list of players
     */
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

    /**
     * Getter of map of games
     *
     * @return map of games
     */
    public Map<MapPath, List<RiskModel>> getGames() {
        return Collections.unmodifiableMap(games);
    }

    /**
     * Getter of row number
     *
     * @return row number
     */
    @Override
    public int getRowCount() {
        return this.mapsPaths.size() + 1;
    }

    /**
     * Getter of column count
     *
     * @return column number
     */
    @Override
    public int getColumnCount() {
        return this.nbGamePerMap + 1;
    }

    /**
     * Getter of column name
     *
     * @param i column number
     * @return column name
     */
    @Override
    public String getColumnName(int i) {
        return String.format("Game %d", i + 1);
    }

    /**
     * Getter of type of values in the column
     *
     * @param i column number
     * @return type of values in the column
     */
    @Override
    public Class<?> getColumnClass(int i) {
        return String.class;
    }

    /**
     * If cell is editable
     *
     * @param i row number
     * @param j column number
     * @return if the cell is editable, return true, otherwise, return false
     */
    @Override
    public boolean isCellEditable(int i, int j) {
        return false;
    }

    /**
     * Get the value of the cell
     *
     * @param i row number
     * @param j column number
     * @return value in the cell
     */
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

    /**
     * Set cell value
     *
     * @param o value
     * @param i row number
     * @param j column number
     */
    @Override
    public void setValueAt(Object o, int i, int j) {
    }

    /**
     * Add table listener
     *
     * @param tl TableModelListener
     */
    @Override
    public void addTableModelListener(TableModelListener tl) {
    }

    /**
     * Remove table listener
     *
     * @param tl TableModelListener
     */
    @Override
    public void removeTableModelListener(TableModelListener tl) {
    }

    /**
     * If tournament is finished
     *
     * @return if the tournament is finished, return true, if not, return false
     */
    public boolean isTournamentFinished() {
        return this.games.values().stream().allMatch(
                (lg) -> lg.stream().allMatch(
                        (g) -> (g.getWinningPlayer() != null || g.isDraw())
                )
        );
    }

    /**
     * Return whether or not this tournament is in a consistent state and can be
     * saved
     *
     * @return whether or not this tournament is in a consistent state and can
     * be saved
     */
    public boolean isSavable() {
        return this.games.values().stream().allMatch((lg)
                -> (lg.stream().allMatch((g)
                        -> (g.getCurrentPlayer().getCurrentAttack() == null)
                ))
        );
    }

    /**
     * Setter for the tournament saver attribute
     *
     * @param tournamentSaver The tournament saver to be set
     */
    public void setTournamentSaver(TournamentSaverInterface tournamentSaver) {
        this.tournamentSaver = tournamentSaver;
    }

    /**
     * Resume the tournament
     */
    public void resume() {
        this.games.values().forEach((lg) -> {
            lg.stream().forEach((g) -> {
                g.continueGame();
            });
        });
    }

}
