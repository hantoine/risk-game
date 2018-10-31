/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.controllers;

import com.risk.models.PlayerModel;
import com.risk.models.RiskModel;
import com.risk.models.TerritoryModel;
import com.risk.views.RiskViewInterface;

/**
 * It represents the game process. It contains all methods corresponding to the
 * execution of the Game
 *
 * @author Nellybett
 */
public class GameController {

    /**
     * modelRisk It is an attribute that represents a reference to the model
     */
    RiskModel modelRisk;
    /**
     * riskView It is an attribute that represents a reference to the view
     */
    RiskViewInterface riskView;

    /**
     * Constructor
     *
     * @param riskModel model of the game
     * @param riskView view of the game
     */
    public GameController(RiskModel riskModel, RiskViewInterface riskView) {
        this.modelRisk = riskModel;
        this.riskView = riskView;

    }

    /**
     * Called when the player click on a territory, the action performed depends
     * on the current game stage
     *
     * @param territoryClickedName Name of the territory on which the player
     * clicked
     */
    public void clickOnTerritory(String territoryClickedName) {
        TerritoryModel territoryClicked = this.modelRisk.getMap().getTerritoryByName(territoryClickedName);
        PlayerModel currentPlayer = this.modelRisk.getCurrentPlayer();

        switch (this.modelRisk.getPhase()) {
            case STARTUP:
                try {
                    this.modelRisk.placeArmy(currentPlayer, territoryClicked);
                } catch (RiskModel.ArmyPlacementImpossible ex) {
                    this.riskView.showMessage(ex.getReason());
                }
                this.modelRisk.nextTurn();
                if (currentPlayer.getNumArmiesAvailable() == 0 && modelRisk.getTurn() == 0) {
                    this.modelRisk.finishPhase();
                }
                break;
            case REINFORCEMENT:
                if (currentPlayer.getHand().getNbCards() == 5) {
                    riskView.showMessage("You have 5 cards. Please hand some cards");
                    break;
                }
                try {
                    this.modelRisk.placeArmy(currentPlayer, territoryClicked);
                } catch (RiskModel.ArmyPlacementImpossible ex) {
                    this.riskView.showMessage(ex.getReason());
                }
                if (currentPlayer.getNumArmiesAvailable() == 0) {
                    this.modelRisk.finishPhase();
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
        TerritoryModel sourceTerritory = this.modelRisk.getMap().getTerritoryByName(sourceTerritoryName);
        TerritoryModel destTerritory = this.modelRisk.getMap().getTerritoryByName(destTerritoryName);
        PlayerModel currentPlayer = this.modelRisk.getCurrentPlayer();

        switch (this.modelRisk.getPhase()) {
            case FORTIFICATION: {
                try {
                    this.modelRisk.tryFortificationMove(sourceTerritory, destTerritory);
                } catch (RiskModel.FortificationMoveImpossible ex) {
                    if (ex.getReason() != null) {
                        this.riskView.showMessage(ex.getReason());
                    }
                }
            }
            break;
        }

    }

    /**
     * Called when the player click on the Hand cards button during the
     * reinforcement phase
     */
    public void clickHand() {
        modelRisk.exchangeCardsWithArmiesForCurrentPlayer();
    }

    /**
     * Called when the end of phase button is pressed in the UI
     */
    public void endPhaseButtonPressed() {
        this.modelRisk.finishPhase();
    }
}
