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
import com.risk.views.TournamentResultsView;
import com.risk.views.menu.MapPathListPanel.MapPathListPanelListener;
import com.risk.views.menu.StrategyListPanel.StrategyListPanelListener;
import com.risk.views.menu.TournamentMenuView;
import java.awt.event.ActionEvent;
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
        tm.addPlayerStategies(strategyType);
    }

    @Override
    public void strategyRemoved(Strategy.Type strategyType) {
        tm.removePlayerStategies(strategyType);
    }

    @Override
    public void mapAdded(MapPath mapPath) {
        tm.addMapsPath(mapPath);
    }

    @Override
    public void mapRemoved(MapPath mapPath) {
        tm.removeMapsPath(mapPath);
    }

    public void playTournament() {
        try {
            tm.playTournament();
            tmv.setVisible(false);
            tmv.dispose();
            checkTournamentFinished();

        } catch (MapFileManagement.MapFileManagementException ex) {
            tmv.showError(ex.getMessage());
        }
    }

    private void checkTournamentFinished() {
        if (tm.isTournamentFinished()) {
            trv = new TournamentResultsView(tm);
        } else {
            Timer timer = new Timer(3000, (ActionEvent ae) -> {
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
        tm.setNbGamePerMap(value);
    }

}
