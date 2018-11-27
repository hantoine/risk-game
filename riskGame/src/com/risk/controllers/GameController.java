/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.controllers;

import com.risk.models.GamePhase;
import com.risk.models.HandModel;
import com.risk.models.HumanStrategy;
import com.risk.models.PlayerModel;
import com.risk.models.RiskModel;
import com.risk.models.TerritoryModel;
import com.risk.views.game.CardExchangeView;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Observable;
import java.util.Observer;

/**
 * It represents the game process. It contains all methods corresponding to the
 * execution of the Game
 *
 * @author Nellybett
 */
public class GameController implements Observer {

    /**
     * modelRisk It is an attribute that represents a reference to the model
     */
    RiskModel rm;
    /**
     * Card exchange panel
     */
    private CardExchangeView exchangeView;
    /**
     * Card exchange listener
     */
    private CardExchangeListener cardExchangeListener;

    /**
     * Constructor
     *
     * @param riskModel Model of the Risk game
     */
    public GameController(RiskModel riskModel) {
        this.rm = riskModel;
        this.rm.addObserver(this);
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
                rm.startupMove(territoryClicked);
                break;
            case REINFORCEMENT:
                rm.reinforcementIntent(territoryClicked);
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
                rm.fortificationIntent(sourceTerritory, destTerritory);
                break;
            case ATTACK:
                rm.attackIntent(sourceTerritory, destTerritory);
                break;
        }

    }

    /**
     * Press one of the dices
     *
     * @param nbDice the number of dices
     */
    public void clickAttack(int nbDice) {
        this.rm.continueAttack(nbDice);
    }

    /**
     * Defense movement
     *
     * @param nbDice number of dice selected
     */
    public void clickDefense(int nbDice) {
        rm.getCurrentPlayer().setDefenseValues(nbDice);
        rm.performAttack(this.rm.getCurrentPlayer());

        rm.getCurrentPlayer().moveArmies();
        rm.setAttackPhase(true);
        if (rm.getPhase() == GamePhase.ATTACK) {
            rm.executeAttack();
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
    }

    /**
     * Destroy card exchange view
     */
    public void closeCardExchangeView() {
        HandModel hand = rm.getCurrentPlayer().getHand();
        if (this.exchangeView != null) {
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
        if (this.exchangeView != null) {
            return;
        }
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
     * Observe the risk model
     *
     * @param o observable
     * @param o1 parameters
     */
    @Override
    public void update(Observable o, Object o1) {
        PlayerModel currentPlayer = this.rm.getCurrentPlayer();
        if (this.rm.getPhase() == GamePhase.REINFORCEMENT
                && currentPlayer.getHand().cardHandingPossible()
                && currentPlayer.getStrategy() instanceof HumanStrategy
                && !currentPlayer.isCardExchangeOffered()) {
            openCardExchangeView();
        }
    }
}
