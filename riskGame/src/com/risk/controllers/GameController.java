/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.controllers;

import com.risk.models.HandModel;
import com.risk.models.RiskModel;
import com.risk.models.TerritoryModel;
import com.risk.models.interfaces.PlayerModel;
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

    RiskModel modelRisk;
    RiskView riskView;
    int[] cardDuplicates=new int[3];

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
        if (modelRisk.getMap().getGraphTerritories().values().stream()
                .allMatch((t) -> (t.getOwner() == modelRisk.getCurrentPlayer()))) {
            modelRisk.setWinningPlayer(modelRisk.getCurrentPlayer());
            return;
        }

        // Finishing steps of current stage
        switch (modelRisk.getPhase()) {
            case STARTUP:
                break;
            case REINFORCEMENT:
                break;
            case ATTACK:
                checkForDeadPlayers();
                break;
            case FORTIFICATION:
                modelRisk.nextTurn();
                break;
        }

        modelRisk.nextPhase();

        // Beginning steps of new stage
        switch (modelRisk.getPhase()) {
            case STARTUP:
                break;
            case REINFORCEMENT:
                modelRisk.getCurrentPlayer().reinforcement(this);
                this.validateHand();
                this.showHandButton();
                break;
            case ATTACK:
                try {
                    modelRisk.getCurrentPlayer().attack(this);
                } catch (UnsupportedOperationException e) {
                    //since attack is not implemented yet, we skip it 
                    this.finishPhase();
                }
                break;
            case FORTIFICATION:
                modelRisk.getCurrentPlayer().fortification(this);
                break;
        }

        riskView.updateView(modelRisk);
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
                if (tryPlaceArmy(currentPlayer, territoryClicked) != true) {
                    break;
                }
                if (currentPlayer.getNumArmiesAvailable() == 0) {
                    this.finishPhase();
                }
                riskView.updateView(modelRisk);
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

                try {
                    sourceTerritory.decrementNumArmies();
                    destTerritory.incrementNumArmies();
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
    
    private void validateHand(){
        cardDuplicates[0]=cardDuplicates[1]=cardDuplicates[2]=0;
        HandModel handCurrentPlayer=this.modelRisk.getCurrentPlayer().getCardsOwned();
        
        handCurrentPlayer.getCards().stream()
                .forEach((c) ->{
                    
                    if(c.getTypeOfArmie().equals("infantry")){
                        cardDuplicates[0]=cardDuplicates[0]+1;
                    }else if(c.getTypeOfArmie().equals("cavalry")){
                        cardDuplicates[1]=cardDuplicates[1]+1;
                    }else if(c.getTypeOfArmie().equals("artillery")){
                        cardDuplicates[2]=cardDuplicates[2]+1;
                    }
                });
                    
    }
    
    public void showHandButton(){
        if((cardDuplicates[0]>=3 || cardDuplicates[1]>=3 || cardDuplicates[2]>=3) || (cardDuplicates[0]>=1 && cardDuplicates[1]>=1 && cardDuplicates[2]>=1)){
            riskView.getStagePanel().getHandCards().setVisible(true);
            riskView.updateView(modelRisk);
        }
    }
    
    public void clickHand(){
        
        this.assignArmiesToPlayerFromCards();
        riskView.getStagePanel().getHandCards().setVisible(false);
        riskView.updateView(modelRisk);
    }
    
    public void assignArmiesToPlayerFromCards(){
        if(cardDuplicates[0]>=3){
            modelRisk.getCurrentPlayer().removeCards("infantry");
        }else if(cardDuplicates[1]>=3){
            modelRisk.getCurrentPlayer().removeCards("cavalry");
        }else if(cardDuplicates[2]>=3){
            modelRisk.getCurrentPlayer().removeCards("artillery");
        }else{
            modelRisk.getCurrentPlayer().removeCards("different");
        }
        this.modelRisk.getCurrentPlayer().armiesCardAssignation();
    }
    
}



