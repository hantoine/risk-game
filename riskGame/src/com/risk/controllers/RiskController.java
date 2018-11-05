/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.controllers;

import com.risk.models.MapModel;
import com.risk.models.RiskModel;
import com.risk.views.RiskView;
import com.risk.views.editor.MapEditorView;

/**
 * It is the Game-driver
 *
 * @author Nellybett
 */
public final class RiskController {

    /**
     * viewRisk it is a reference to the view of the game
     *
     *
     *
     * mapEditorView the reference to the map editor
     */
    private RiskView viewRisk;
    /**
     * modelRisk it is a reference to the model of the game
     */
    private RiskModel modelRisk;
    /**
     * countryListener it is a reference to the listener of country events
     */
    private MapListener countryListener;
    /**
     * playGame it is a reference to the controller of the game flow
     */
    private GameController gameController;
    /**
     * Listener for the menu events
     */
    private MenuListener menuListener;

    /**
     * mapEditor reference to the map editor view
     */
    private MapEditorView mapEditor;

    /**
     * Constructor
     *
     * @param riskModel the model of the game
     * @param riskView the view of the game
     */
    public RiskController(RiskModel riskModel, RiskView riskView) {
        this.modelRisk = riskModel;
        this.viewRisk = riskView;

        this.countryListener = new MapListener(this);
        this.menuListener = new MenuListener(getModelRisk(), getViewRisk(), this);
        this.gameController = new GameController(this.modelRisk);
        viewRisk.initialMenu(modelRisk, menuListener);
        viewRisk.setVisible(true);
    }

    /**
     * Opens a new map editor view.
     */
    public void openMapEditor() {
        MapModel newMap = new MapModel();
        MapEditorController editorController = new MapEditorController(newMap);
        this.mapEditor = new MapEditorView(1000, 600, editorController, newMap);
        this.mapEditor.setVisible(true);
        newMap.addObserver(mapEditor);
        newMap.addObserver(mapEditor.getMapView());
        newMap.addObserver(mapEditor.getContinentListPanel());
    }

    /**
     * Display the NewGame Menu Called when user press on New Game MenuItem.
     *
     */
    public void newGameMenuItemPressed() {
        getViewRisk().initialMenu(getModelRisk(), getMenuListener());
    }

    /**
     * It executes a thread with the different phases of the game. It is called
     * after setting the players and board information
     */
    void playGame() {
        this.modelRisk.initializePlayersArmies();
        this.modelRisk.assignCoutriesToPlayers();
        this.viewRisk.observeModel(modelRisk);
    }

    /**
     * Getter of the viewRisk attribute
     *
     * @return the viewRisk
     */
    public RiskView getViewRisk() {
        return viewRisk;
    }

    /**
     * Getter of the modelRisk attribute
     *
     * @return the modelRisk
     */
    public RiskModel getModelRisk() {
        return modelRisk;
    }

    /**
     * Getter of the menuListener attribute
     *
     * @return the menuListener
     */
    public MenuListener getMenuListener() {
        return menuListener;
    }

    /**
     * Getter of the countryListener attribute
     *
     * @return the countryListener
     */
    public MapListener getCountryListener() {
        return countryListener;
    }

    /**
     * @return the playGame
     */
    public GameController getGameController() {
        return gameController;
    }
}
