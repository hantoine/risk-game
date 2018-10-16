/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.controllers;

import com.risk.models.FortificationMove;
import com.risk.models.PlayerModel;
import com.risk.models.RiskModel;
import com.risk.models.TerritoryModel;
import com.risk.views.RiskView;
import java.util.LinkedList;
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
     * riskView It is an attribute that represents a reference to the view
     */
    RiskView riskView;

    /**
     * Constructor
     *
     * @param riskModel model of the game
     * @param riskView view of the game
     */
    public GameController(RiskModel riskModel, RiskView riskView) {
        this.modelRisk = riskModel;
        this.riskView = riskView;

    }

    /**
     * Finish the current stage of the game and initialize for the next stage of
     * the game
     *
     */
    public void finishPhase() {
        if (modelRisk.getWinningPlayer() != null) {
            riskView.showMessage("The player " + this.modelRisk.getWinningPlayer().getName() + " has won the game");
            return;
        }

        executeEndOfPhaseSteps();
        modelRisk.nextPhase();
        executeBeginningOfPhaseSteps();

        riskView.updateView(modelRisk);
    }

    /**
     * Final steps after finishing a phase
     */
    private void executeEndOfPhaseSteps() {
        switch (modelRisk.getPhase()) {
            case STARTUP:
                break;
            case REINFORCEMENT:
                break;
            case ATTACK:
                checkForDeadPlayers();
                break;
            case FORTIFICATION:
                modelRisk.getCurrentPlayer().setCurrentFortificationMove(null);
                modelRisk.nextTurn();
                break;
        }
    }

    /**
     * Steps at the beginning of a phase
     */
    private void executeBeginningOfPhaseSteps() {
        switch (modelRisk.getPhase()) {
            case STARTUP:
                break;
            case REINFORCEMENT:
                modelRisk.getCurrentPlayer().reinforcement(this);
                break;
            case ATTACK:
                try {
                    modelRisk.getCurrentPlayer().attack(this);
                } catch (UnsupportedOperationException e) {
                    //since attack is not implemented yet, we skip it
                    this.finishPhase();
                }

                modelRisk.getCurrentPlayer().addCardToPlayerHand();
                break;
            case FORTIFICATION:
                modelRisk.getCurrentPlayer().fortification(this);
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
    public void clickOnTerritory(String territoryClickedName) {
        TerritoryModel territoryClicked = this.modelRisk.getMap().getGraphTerritories().get(territoryClickedName);
        PlayerModel currentPlayer = this.modelRisk.getCurrentPlayer();

        switch (this.modelRisk.getPhase()) {
            case STARTUP:
                if (tryPlaceArmy(currentPlayer, territoryClicked) != true) {
                    break;
                }
                this.modelRisk.nextTurn();
                if (currentPlayer.getNumArmiesAvailable() == 0 && modelRisk.getTurn() == 0) {
                    this.finishPhase();
                }
                riskView.updateView(modelRisk);
                break;
            case REINFORCEMENT:
                if (currentPlayer.getCardsOwned().getCards().size() == 5) {
                    riskView.showMessage("You have 5 cards. Please hand some cards");
                } else {
                    if (tryPlaceArmy(currentPlayer, territoryClicked) != true) {
                        break;
                    }
                    if (currentPlayer.getNumArmiesAvailable() == 0) {
                        this.finishPhase();
                    }
                    riskView.updateView(modelRisk);
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
        PlayerModel currentPlayer = this.modelRisk.getCurrentPlayer();

        switch (this.modelRisk.getPhase()) {
            case FORTIFICATION:
                if (!sourceTerritory.getAdj().contains(destTerritory)) {
                    break;
                }
                if (!currentPlayer.getContriesOwned().contains(sourceTerritory)
                        || !currentPlayer.getContriesOwned().contains(destTerritory)) {
                    this.riskView.showMessage("You don't own this country !");
                    break;
                }
                FortificationMove attemptedMove = new FortificationMove(sourceTerritory, destTerritory);
                FortificationMove lastMove = currentPlayer.getCurrentFortificationMove();
                if (lastMove != null && !lastMove.equals(attemptedMove)) {
                    this.riskView.showMessage("You can only make one move !");
                    break;
                }

                try {
                    sourceTerritory.decrementNumArmies();
                    destTerritory.incrementNumArmies();
                    currentPlayer.setCurrentFortificationMove(attemptedMove);
                    riskView.updateView(modelRisk);
                } catch (IllegalStateException e) {
                    this.riskView.showMessage("There is no armies in the source country !");
                }
                break;
        }

    }

    /**
     * Place an army from the given player on the given territory.
     *
     * @param player Player whose army is going to be taken
     * @param territory Territory on which the army will be added
     * @return True if the army placement worked, false otherwise
     */
    private boolean tryPlaceArmy(PlayerModel player, TerritoryModel territory) {
        if (player.getNumArmiesAvailable() <= 0) { //should never happen
            this.riskView.showMessage("You have no armies left to deploy !");
            return false;
        }
        if (!player.getContriesOwned().contains(territory)) {
            this.riskView.showMessage("You don't own this country !");
            return false;
        }

        territory.incrementNumArmies();
        player.decrementNumArmiesAvailable();

        return true;
    }

    /**
     * Check if any player has no more territories owned and remove these player
     * from the game
     */
    private void checkForDeadPlayers() {
        List<PlayerModel> currentPlayerList = new LinkedList(this.modelRisk.getPlayerList());
        currentPlayerList.stream()
                .filter(p -> p.getContriesOwned().isEmpty())
                .forEach((p) -> {
                    this.riskView.showMessage(String.format(
                            "The player %s has no more territories, it is eliminated from the game !",
                            p.getName())
                    );
                    this.modelRisk.removePlayer(p);
                });
    }

    /**
     * Called when the player click on the Hand cards button during the
     * reinforcement phase
     */
    public void clickHand() {

        modelRisk.getCurrentPlayer().exchangeCardsToArmies();
        riskView.updateView(modelRisk);
    }
}
