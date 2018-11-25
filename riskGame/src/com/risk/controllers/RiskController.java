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
import com.risk.views.menu.MenuView;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

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
     * territoryListener it is a reference to the listener of territory events
     */
    private MapListener territoryListener;
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

        this.territoryListener = new MapListener(this);
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
        this.viewRisk.observeModel(modelRisk);
        this.modelRisk.startGame();
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
     * Getter of the territoryListener attribute
     *
     * @return the territoryListener
     */
    public MapListener getTerritoryListener() {
        return territoryListener;
    }

    /**
     * @return the playGame
     */
    public GameController getGameController() {
        return gameController;
    }

    /**
     * Method to save the state of the current game being played
     */
    public void saveGame() {
        try (FileOutputStream fileOut = new FileOutputStream("game.ser"); ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(this.modelRisk);

        } catch (IOException i) {
        }
    }

    /**
     * Load a new game from backup file
     */
    public void loadGame() {
        //load the saved model
        try (FileInputStream fileIn = new FileInputStream("game.ser"); ObjectInputStream in = new ObjectInputStream(fileIn)) {
            this.modelRisk = (RiskModel) in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e);
            return;
        }
        
        //set buffered image as it is not serializable
        String imagePath = modelRisk.getMap().getConfigurationInfo().getImagePath();
        System.out.println(imagePath);
        try {
            BufferedImage image = ImageIO.read(new File("maps/" + imagePath));
            this.modelRisk.getMap().setImage(image);
        } catch (IOException ex) {
            Logger.getLogger(RiskController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //setup listeners
        this.territoryListener = new MapListener(this);
        this.menuListener = new MenuListener(getModelRisk(), getViewRisk(), this);
        this.gameController = new GameController(this.modelRisk);
        
        //setup observers + update the view
        this.viewRisk.observeModel(modelRisk);
        viewRisk.setController(this);
    }
}
