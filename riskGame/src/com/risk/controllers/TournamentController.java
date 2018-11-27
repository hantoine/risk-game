/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.controllers;

import com.risk.models.MapFileManagement;
import com.risk.models.MapPath;
import com.risk.models.Strategy;
import com.risk.models.TournamentModel;
import com.risk.views.LogViewer;
import com.risk.views.TournamentResultsView;
import com.risk.views.menu.MapPathListPanel.MapPathListPanelListener;
import com.risk.views.menu.StrategyListPanel.StrategyListPanelListener;
import com.risk.views.menu.TournamentMenuView;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import javax.swing.Timer;

/**
 * Tournament mode controller
 *
 * @author hantoine
 */
public class TournamentController implements StrategyListPanelListener, MapPathListPanelListener {

    /**
     * View of tournament mode
     */
    TournamentMenuView tmv;
    /**
     * Attribute of tournament mode
     */
    TournamentModel tm;
    /**
     * Show the tournament result view
     */
    TournamentResultsView trv;

    /**
     * Constructor
     *
     * @param tm model of tournament mode
     * @param tmv view of tournament mode
     */
    public TournamentController(TournamentModel tm, TournamentMenuView tmv) {
        this.tm = tm;
        this.tmv = tmv;
    }

    /**
     * Add strategy to tournament players
     *
     * @param strategyType strategy of player
     */
    @Override
    public void strategyAdded(Strategy.Type strategyType) {
        tm.addPlayerStategies(strategyType);
    }

    /**
     * Remove strategy from tournament players
     *
     * @param strategyType strategy of player
     */
    @Override
    public void strategyRemoved(Strategy.Type strategyType) {
        tm.removePlayerStategies(strategyType);
    }

    /**
     * Choose maps that players will play
     *
     * @param mapPath map path
     */
    @Override
    public void mapAdded(MapPath mapPath) {
        try {
            tm.addMapsPath(mapPath);
        } catch (IllegalStateException e) {
            this.tmv.showError(e.getMessage());
        }
    }

    /**
     * Remove maps
     *
     * @param mapPath map path
     */
    @Override
    public void mapRemoved(MapPath mapPath) {
        tm.removeMapsPath(mapPath);
    }

    /**
     * Play the tournament
     */
    public void playTournament() {
        try {
            tm.playTournament();
            checkTournamentFinished();

        } catch (MapFileManagement.MapFileManagementException ex) {
            tmv.showError(ex.getMessage());
        }
    }

    /**
     * Check if tournament is finished every 300 milliseconds if finished, show
     * the result view
     */
    private void checkTournamentFinished() {
        if (tm.isTournamentFinished()) {
            trv = new TournamentResultsView(tm);
            tmv.setVisible(false);
            tmv.dispose();
            trv.setController(this);
        } else {
            Timer timer = new Timer(300, (ActionEvent ae) -> {
                this.checkTournamentFinished();
            });
            timer.setRepeats(false); // Only execute once
            timer.start();
        }
    }

    /**
     * Set maximum number of turn in a game
     *
     * @param value number of turn per map
     */
    public void nbMaximumTurnPerGameChanged(int value) {
        tm.setMaximumTurnPerGame(value);
    }

    /**
     * Set number of game for each map
     *
     * @param value number of game per map
     */
    public void nbGamePerMapChanged(int value) {
        tm.setNbGamePerMap(value);
    }

    /**
     * click the result cell and check the log of the game
     *
     * @param i row number
     * @param j column number
     */
    public void clickResultCell(int i, int j) {
        if (i == 0 || j == 0) {
            return;
        }

        //create view from file
        List<String> logs;
        try {
            logs = Files.readAllLines(
                    Paths.get("logs", tm.getLogFile(j - 1, i - 1))
            );
            LogViewer lv = new LogViewer(logs);
        } catch (IOException ex) {
            this.tmv.showError("Failed to open log file");
        }
    }

}
