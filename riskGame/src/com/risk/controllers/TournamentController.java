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
 *
 * @author hantoine
 */
public class TournamentController implements StrategyListPanelListener, MapPathListPanelListener {

    TournamentMenuView tmv;
    TournamentModel tm;
    TournamentResultsView trv;

    public TournamentController(TournamentModel tm, TournamentMenuView tmv) {
        this.tm = tm;
        this.tmv = tmv;
    }

    @Override
    public void strategyAdded(Strategy.Type strategyType) {
        if (strategyType == Strategy.Type.HUMAN) {
            throw new IllegalStateException("The tournament mode does not "
                    + "support the human strategy.");
        }

        tm.addPlayerStategies(strategyType);
    }

    @Override
    public void strategyRemoved(Strategy.Type strategyType) {
        tm.removePlayerStategies(strategyType);
    }

    @Override
    public void mapAdded(MapPath mapPath) {
        try {
            tm.addMapsPath(mapPath);
        } catch (IllegalStateException e) {
            this.tmv.showError(e.getMessage());
        }
    }

    @Override
    public void mapRemoved(MapPath mapPath) {
        tm.removeMapsPath(mapPath);
    }

    public void playTournament() {
        try {
            tm.playTournament();
            checkTournamentFinished();

        } catch (MapFileManagement.MapFileManagementException ex) {
            tmv.showError(ex.getMessage());
        }
    }

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

    public void nbMaximumTurnPerGameChanged(int value) {
        tm.setMaximumTurnPerGame(value);
    }

    public void nbGamePerMapChanged(int value) {
        if (value > 5) {
            throw new IllegalStateException("The number of games per map "
                    + "cannot exceed 5.");
        }
        tm.setNbGamePerMap(value);
    }

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
