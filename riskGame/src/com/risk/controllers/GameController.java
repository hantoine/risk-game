/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.controllers;

import com.risk.models.AttackMove;
import com.risk.models.GamePhase;
import com.risk.models.HandModel;
import com.risk.models.PlayerModel;
import com.risk.models.RiskModel;
import com.risk.models.TerritoryModel;
import com.risk.views.game.AttackView;
import com.risk.views.game.CardExchangeView;
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
    /**
     *
     */
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
                    if (currentPlayer.getNbArmiesAvailable() == 0 && rm.getTurn() == 0) {
                        this.rm.finishPhase();
                    }
                } catch (RiskModel.ArmyPlacementImpossible ex) {
                    this.rm.addNewEvent(ex.getReason());
                }
                break;
            case REINFORCEMENT:
                try {
                    this.rm.placeArmy(currentPlayer, territoryClicked);
                    if (currentPlayer.getNbArmiesAvailable() == 0) {
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
                int result = rm.getCurrentPlayer().validateAttack(sourceTerritory, destTerritory);
                if (result == 0) {
                    this.rm.attackMove(sourceTerritory, destTerritory);
                } else {
                    exceptionManagerAttack(result);
                }
                break;
        }

    }

    /**
     * Function that shows errors from an attack
     *
     * @param e event to manage
     */
    public void exceptionManagerAttack(int e) {
        switch (e) {
            case -1:
                this.rm.addNewEvent("The territory is not adjacent.");
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
     * @param nbDice the number of dices
     */
    public void clickAttack(int nbDice) {
        AttackMove attackMove = rm.getCurrentPlayer().getCurrentAttack();

        if (nbDice == -1 //battleAll
                || attackMove.getDest().getNumArmies() == 1) {

                rm.getCurrentPlayer().setAttackValues(nbDice);
                rm.getCurrentPlayer().setDefenseValues(1); 
                rm.performAttack(this.rm.getCurrentPlayer());
                
        } else {
            rm.getCurrentPlayer().setAttackValues(nbDice);
            rm.setAttackPhase(false);
        }

    }

    public void clickDefense(int nbDice){
        rm.getCurrentPlayer().setDefenseValues(nbDice);
        rm.performAttack(this.rm.getCurrentPlayer());
        rm.setAttackPhase(true);
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
    public void closeCardExchangeView() {
        HandModel hand = rm.getCurrentPlayer().getHand();
        if(this.exchangeView!=null){
            this.exchangeView.setVisible(false);
            this.exchangeView = null;
            hand.unselectAllCards();
            rm.getCurrentPlayer().setHanded(false);
        }
        
        
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

    /**
     * This method is for add the observer
     * @param attackView the view which is added to observer
     */
    public void addObserverToAttack(AttackView attackView) {

        rm.addObserverToAttack(attackView);
    }
}
