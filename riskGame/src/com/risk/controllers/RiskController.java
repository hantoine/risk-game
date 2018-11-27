/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.controllers;

import com.risk.models.HumanStrategy;
import com.risk.models.LogWriter;
import com.risk.models.PlayerModel;
import com.risk.models.RiskModel;
import com.risk.views.RiskView;
import com.risk.views.editor.MapEditorView;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.imageio.ImageIO;

/**
 * It is the Game-driver
 *
 * @author Nellybett
 */
public final class RiskController {

    /**
     * viewRisk it is a reference to the view of the game mapEditorView the
     * reference to the map editor
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
     * Display the NewGame Menu Called when user press on New Game MenuItem.
     */
    public void newGameMenuItemPressed() {
        this.modelRisk.reset();
        getViewRisk().initialMenu(getModelRisk(), getMenuListener());
    }

    /**
     * It executes a thread with the different phases of the game. It is called
     * after setting the players and board information
     */
    void playGame() {
        LogWriter newLogWriter = new LogWriter();
        this.modelRisk.setLogWriter(newLogWriter);
        newLogWriter.openFile();
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
     *
     * @param filePath
     */
    public void saveGame(String filePath) {
        PlayerModel currentPlayer = this.modelRisk.getCurrentPlayer();
        if (currentPlayer.getCurrentAttack() != null
                || !(currentPlayer.getStrategy() instanceof HumanStrategy)) {
            this.viewRisk.showError("Cannot save while a battle is in progress "
                    + "or while the computer is playing");
            return;
        }
        this.modelRisk.setSavedLogs(this.viewRisk.getLogs());

        try (FileOutputStream fileOut = new FileOutputStream(filePath); ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(this.modelRisk);
        } catch (IOException e) {
            this.viewRisk.showError("An error occured while attempting to save the game.");
            System.out.println(e);
        }
    }

    /**
     * Load a new game from backup file
     *
     * @param filePath path to the file containing a saved game to load
     */
    public void loadGame(String filePath) {
        //load the saved model
        RiskModel newModel;
        BufferedImage image;
        try (FileInputStream fileIn = new FileInputStream(filePath); ObjectInputStream in = new ObjectInputStream(fileIn)) {
            newModel = (RiskModel) in.readObject();

            //load buffered image as it is not serializable
            String imagePath = newModel.getMap().getConfigurationInfo().getImagePath();
            System.out.println(imagePath);
            image = ImageIO.read(new File("maps/" + imagePath));

        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e);
            this.viewRisk.showError("An error occured while attempting to load the game.");
            return;
        }

        //if all went well, update model
        this.modelRisk = newModel;
        this.modelRisk.getMap().setImage(image);

        //setup listeners
        this.territoryListener = new MapListener(this);
        this.menuListener = new MenuListener(getModelRisk(), getViewRisk(), this);
        this.gameController = new GameController(getModelRisk());

        //setup observers + update the view
        this.modelRisk.setLogWriter(new LogWriter());
        this.viewRisk.observeModel(getModelRisk());
        viewRisk.setController(this);
        viewRisk.setLogs(getModelRisk().getLogs());
    }
}
