/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.controllers;

import com.risk.models.GameStage;
import com.risk.models.RiskModel;
import com.risk.models.TerritoryModel;
import com.risk.models.interfaces.PlayerModel;
import com.risk.views.RiskView;

/**
 * It represents the game process. It is execute in a thread in the
 * RiskController
 *
 * @author Nellybett
 */
public class GameController {

    RiskModel modelRisk;
    RiskView riskView;
    MapListener countryListener;

    /**
     * Constructor
     *
     * @param riskModel model of the game
     * @param riskView view of the game
     * @param countryListener listener to mouse events
     */
    public GameController(RiskModel riskModel, RiskView riskView, MapListener countryListener) {
        this.countryListener = countryListener;
        this.modelRisk = riskModel;
        this.riskView = riskView;
    }

    /**
     * Finish the current stage of the game and initialize for the next stage of
     * the game
     *
     */
    public void finishStage() {
        if (modelRisk.getMap().getGraphTerritories().values().stream()
                .allMatch((t) -> (t.getOwner() == modelRisk.getCurrentPlayer()))) {
            modelRisk.setWinningPlayer(modelRisk.getCurrentPlayer());
            return;
        }

        //Finish steps of current stage
        switch (modelRisk.getStage()) {
            case START:
                modelRisk.nextTurn();
                modelRisk.initializePlayers();
                break;
            case REINFORCEMENT:
                break;
            case ATTACK:
                break;
            case FORTIFICATION:
                break;
        }

        modelRisk.nextStage();

        // Beginning steps of new stage
        switch (modelRisk.getStage()) {
            case START:
                break;
            case REINFORCEMENT:
                modelRisk.getCurrentPlayer().reinforcement(this);
                riskView.updateView(modelRisk);
                break;
            case ATTACK:
                modelRisk.getCurrentPlayer().attack(this);
                break;
            case FORTIFICATION:
                modelRisk.nextTurn();
                riskView.updateView(modelRisk);
                break;
        }
    }

    /**
     * Called when the player click on a territory, the action performed depends
     * on the current game stage
     *
     * @param territoryClickedName Name of the territory on which the player
     * clicked
     */
    public void clickOnTerriroty(String territoryClickedName) {
        TerritoryModel territoryClicked = this.modelRisk.getMap().getGraphTerritories().get(territoryClickedName);

        switch (this.modelRisk.getStage()) {
            case REINFORCEMENT:
                PlayerModel currentPlayer = this.modelRisk.getCurrentPlayer();

                if (currentPlayer.getArmiesDeploy() <= 0) { //should never happen
                    this.riskView.showMessage("You have no more armies to deploy");
                }
                if (!currentPlayer.getContriesOwned().contains(territoryClicked)) {
                    this.riskView.showMessage("You don't own this country");
                }

                territoryClicked.incrementNumArmies();
                currentPlayer.decrementNumArmies();
                this.riskView.initStagePanel(GameStage.REINFORCEMENT, currentPlayer.getArmiesDeploy());

                if (currentPlayer.getArmiesDeploy() == 0) {
                    this.finishStage();
                }

                break;
        }
    }

    /**
     * Called when the player drag a territory and drop it to another territory,
     * the action performed depends on the current game stage
     *
     * @param sourceTerritoryName Name of the territory source of the drag and
     * drop operation
     * @param destTerritoryName Name of the territory destination of the drag
     * and drop operation
     */
    public void dragNDropTerritory(String sourceTerritoryName, String destTerritoryName) {
        TerritoryModel sourceTerritory = this.modelRisk.getMap().getGraphTerritories().get(sourceTerritoryName);
        TerritoryModel destTerritory = this.modelRisk.getMap().getGraphTerritories().get(destTerritoryName);

    }
}
