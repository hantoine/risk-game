/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.controllers;

import com.risk.models.MapPath;
import com.risk.models.Strategy;
import com.risk.models.TournamentModel;
import com.risk.views.menu.MapPathListPanel.MapPathListPanelListener;
import com.risk.views.menu.StrategyListPanel.StrategyListPanelListener;
import com.risk.views.menu.TournamentMenuView;

/**
 *
 * @author hantoine
 */
public class TournamentController implements StrategyListPanelListener, MapPathListPanelListener {

    TournamentMenuView tmv;
    TournamentModel tm;

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
        tm.playTournament();
        tmv.setVisible(false);
        tmv.dispose();

        //open result view
    }

}
