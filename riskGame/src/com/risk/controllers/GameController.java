/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.controllers;

import com.risk.models.GamePhase;
import com.risk.models.PlayerModel;
import com.risk.models.RiskModel;
import com.risk.models.TerritoryModel;
import com.risk.views.reinforcement.CardExchangeView;
import java.awt.Dimension;
import java.awt.Toolkit;

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
    RiskModel rm;
    /**
     * Card exchange panel
     */
    private CardExchangeView exchangeView;

    private CardExchangeListener cardExchangeListener;

    /**
     * Constructor
     *
     * @param riskModel Model of the Risk game
     */
    public GameController(RiskModel riskModel) {
        this.rm = riskModel;
    }

    /**
     * Called when the player click on a territory, the action performed depends
     * on the current game stage
     *
     * @param territoryClickedName Name of the territory on which the player
     * clicked
     */
    public void clickOnTerritory(String territoryClickedName) {
        TerritoryModel territoryClicked = this.rm.getMap().getTerritoryByName(territoryClickedName);
        PlayerModel currentPlayer = this.rm.getCurrentPlayer();

        switch (this.rm.getPhase()) {
            case STARTUP:
                try {
                    this.rm.placeArmy(currentPlayer, territoryClicked);
                    this.rm.nextTurn();
                    if (currentPlayer.getNumArmiesAvailable() == 0 && rm.getTurn() == 0) {
                        this.rm.finishPhase();
                    }
                } catch (RiskModel.ArmyPlacementImpossible ex) {
                    this.rm.addNewEvent(ex.getReason());
                }
                break;
            case REINFORCEMENT:
                try {
                    this.rm.placeArmy(currentPlayer, territoryClicked);
                    if (currentPlayer.getNumArmiesAvailable() == 0) {
                        this.rm.finishPhase();
                    }
                } catch (RiskModel.ArmyPlacementImpossible ex) {
                    this.rm.addNewEvent(ex.getReason());
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
        TerritoryModel sourceTerritory = this.rm.getMap().getTerritoryByName(sourceTerritoryName);
        TerritoryModel destTerritory = this.rm.getMap().getTerritoryByName(destTerritoryName);
        PlayerModel currentPlayer = this.rm.getCurrentPlayer();

        switch (this.rm.getPhase()) {
            case FORTIFICATION:
                try {
                    this.rm.tryFortificationMove(sourceTerritory, destTerritory);
                } catch (RiskModel.FortificationMoveImpossible ex) {
                    if (ex.getReason() != null) {
                        this.rm.addNewEvent(ex.getReason());
                    }
                }
                break;
            case ATTACK:
                int result=rm.getCurrentPlayer().validateAttack(sourceTerritory,destTerritory);
                if(result==0){               
                    this.rm.attackMove(sourceTerritory, destTerritory);
                }else
                    exceptionManagerAttack(result);
                break;
        }

    }
    
    /**
     * Function that shows errors from an attack
     * @param e event to manage
     */
    public void exceptionManagerAttack(int e){
        switch(e){
            case -1:
                this.rm.addNewEvent("The country is not adjacent.");
                break;
            case -2:
                this.rm.addNewEvent("You are already attacking.");
                break;
            case -3:
                this.rm.addNewEvent("Invalid movement");
                break;
            case -4:
                this.rm.addNewEvent("You can't attack with only one armie");
                break;
            default:
                break;
        }
    }
    
    /**
     * Press one of the dices
     *
     * @param dice the number of dices
     */
    public void clickAttack(int dice) {
        if(rm.getCurrentPlayer().getCurrentAttack().getDiceAttack()!=-1 || dice==-1 || rm.getCurrentPlayer().getCurrentAttack().getDest().getNumArmies()==1){
            
            if(rm.getCurrentPlayer().getCurrentAttack().getDiceAttack()!=-1)
                rm.getCurrentPlayer().getCurrentAttack().setDiceAttacked(dice);
            else{
                rm.getCurrentPlayer().getCurrentAttack().setDiceAttack(dice);
                rm.getCurrentPlayer().getCurrentAttack().setDiceAttacked(1);
            }
            //System.out.println("atacante: "+rm.getCurrentPlayer().getCurrentAttack().getDiceAttack()+", atacado: "+rm.getCurrentPlayer().getCurrentAttack().getDiceAttacked());
            rm.performAttack(this.rm.getCurrentPlayer());
        }else{
            rm.getCurrentPlayer().getCurrentAttack().setDiceAttack(dice);
            rm.getCurrentPlayer().getCurrentAttack().setAttackDefense(1);
        }
            
        
        
    }

    /**
     * Move the given number of armies from the source Territory to the
     * destination territory of the attack move of the current player
     *
     * @param armies the number of armies to move to the newly conquered
     * territory
     */
    public void moveArmiesToConqueredTerritory(int armies) {
         
        this.rm.moveArmiesToConqueredTerritory(armies);
    }

    /**
     * Called when the end of phase button is pressed in the UI
     */
    public void endPhaseButtonPressed() {
        this.rm.finishPhase();
        if (this.rm.getPhase() == GamePhase.REINFORCEMENT
                && this.rm.getCurrentPlayer().getHand().cardHandingPossible()) {
            openCardExchangeView();
        }
    }

    /**
     * Destroy card exchange view
     */
    void closeCardExchangeView() {
        this.exchangeView.setVisible(false);
        this.exchangeView = null;
    }
    
    /**
     * Shows and creates a card exchange view
     */
    void openCardExchangeView() {
        this.exchangeView = new CardExchangeView();
        this.exchangeView.updateView(this.rm.getCurrentPlayer().getHand());
        this.exchangeView.observe(rm);
        this.cardExchangeListener = new CardExchangeListener(this.rm, this);
        this.exchangeView.setListener(cardExchangeListener);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        exchangeView.setLocation(
                dimension.width / 2 - (this.exchangeView.getWidth()) / 2,
                dimension.height / 2 - 500 / 2
        );
        exchangeView.setVisible(true);
    }
}
