/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.controllers;

import com.risk.views.editor.MapEditorView;
import com.risk.models.editor.EditableMapModel;
import com.risk.models.RiskModel;
import com.risk.views.RiskView;

/**
 * It is the Game-driver
 *
 * @author Nellybett
 */
public final class RiskController {

    private RiskView viewRisk;
    private RiskModel modelRisk;
    private MenuListener menuListener;
    private MapListener countryListener;
    private GameController playGame;

    //temporary (tim)
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
        viewRisk.initialMenu(modelRisk, menuListener);
        viewRisk.setVisible(true);
  
    }

    public void openMapEditor() {
        EditableMapModel newMap = new EditableMapModel();
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
        this.setPlayGame(new GameController(this.modelRisk, this.viewRisk));
        this.modelRisk.initializePlayersArmies();
        this.modelRisk.assignCoutriesToPlayers();
        this.viewRisk.updateViewWithNewMap(modelRisk);
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
     * Setter of the viewRisk attribute
     *
     * @param viewRisk the viewRisk to set
     */
    public void setViewRisk(RiskView viewRisk) {
        this.viewRisk = viewRisk;
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
     * Setter of the modelRisk attribute
     *
     * @param modelRisk the modelRisk to set
     */
    public void setModelRisk(RiskModel modelRisk) {
        this.modelRisk = modelRisk;
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
     * Setter of the menuListener attribute
     *
     * @param menuListener the menuListener to set
     */
    public void setMenuListener(MenuListener menuListener) {
        this.menuListener = menuListener;
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
     * Setter of the countryListener attribute
     *
     * @param countryListener the countryListener to set
     */
    public void setCountryListener(MapListener countryListener) {
        this.countryListener = countryListener;
    }

    /**
     * @return the playGame
     */
    public GameController getPlayGame() {
        return playGame;
    }

    /**
     * @param playGame the playGame to set
     */
    public void setPlayGame(GameController playGame) {
        this.playGame = playGame;
    }
}
