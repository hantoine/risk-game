/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.controllers;

import com.risk.models.PlayerModel;
import com.risk.models.RiskModel;
import com.risk.models.TerritoryModel;
import java.util.List;

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
     * Constructor
     *
     * @param riskModel Model of the Risk game
     */
    public GameController(RiskModel riskModel) {
        this.modelRisk = riskModel;
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
                    this.modelRisk.nextTurn();
                    if (currentPlayer.getNumArmiesAvailable() == 0 && modelRisk.getTurn() == 0) {
                        this.modelRisk.finishPhase();
                    }
                } catch (RiskModel.ArmyPlacementImpossible ex) {
                    this.modelRisk.addNewEvent(ex.getReason());
                }
                break;
            case REINFORCEMENT:
                try {
                    this.modelRisk.placeArmy(currentPlayer, territoryClicked);
                    if (currentPlayer.getNumArmiesAvailable() == 0) {
                        this.modelRisk.finishPhase();
                    }
                } catch (RiskModel.ArmyPlacementImpossible ex) {
                    this.modelRisk.addNewEvent(ex.getReason());
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
            case FORTIFICATION:
                try {
                    this.modelRisk.tryFortificationMove(sourceTerritory, destTerritory);
                } catch (RiskModel.FortificationMoveImpossible ex) {
                    if (ex.getReason() != null) {
                        this.modelRisk.addNewEvent(ex.getReason());
                    }
                }
                break;
            case ATTACK:
                if (!sourceTerritory.getAdj().contains(destTerritory)) {
                    break;
                }
                if (modelRisk.getCurrentPlayer().getCurrentAttack() != null) {
                    this.modelRisk.addNewEvent("You are already attacking.");
                    break;
                }
                if (!currentPlayer.getContriesOwned().contains(sourceTerritory)
                        || currentPlayer.getContriesOwned().contains(destTerritory)) {
                    this.modelRisk.addNewEvent("Invalid movement");
                    break;
                }
                if (sourceTerritory.getNumArmies() < 2) {
                    this.modelRisk.addNewEvent("You can't attack with only one armie");
                    break;
                }

                this.modelRisk.attackMove(sourceTerritory, destTerritory);
                break;
        }

    }

    /**
     * Called when the player click on the Hand cards button during the
     * reinforcement phase
     *
     * @param selectedCards
     * @return
     */
    public boolean clickHand(List<String> selectedCards) {
        return this.modelRisk.exchangeCardsWithArmiesForCurrentPlayer(selectedCards);

    }

    /**
     * Press one of the dices
     *
     * @param source source of attack
     * @param dest destiny of attack
     * @param dice the number of dices
     */
    public void clickAttack(int dice) {
        modelRisk.performAttack(this.modelRisk.getCurrentPlayer(), dice);
    }

    /**
     * Move the given number of armies from the source Territory to the
     * destination territory of the attack move of the current player
     *
     * @param armies the number of armies to move to the newly conquered
     * territory
     */
    public void moveArmiesToConqueredTerritory(int armies) {
        this.modelRisk.moveArmiesToConqueredTerritory(armies);
    }

    /**
     * Called when the end of phase button is pressed in the UI
     */
    public void endPhaseButtonPressed() {
        this.modelRisk.finishPhase();
    }
}
