/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views;

import com.risk.controllers.MenuListener;
import com.risk.controllers.RiskController;
import com.risk.models.RiskModel;
import com.risk.views.menu.PlayerPanel;
import java.util.LinkedList;
import java.util.Observer;

/**
 * Interface for RiskView, used to mock the RiskView interface in the tests
 *
 * @author hantoine
 */
public interface RiskViewInterface extends Observer {

    /**
     * Add view elements as observer on corresponding model elements
     *
     * @param rm model of the game
     */
    public void observeModel(RiskModel rm);

    /**
     * Link the view to the controller by setting all required listeners
     *
     * @param rc Controller
     */
    public void setController(RiskController rc);

    /**
     * Initialize the new game menu
     *
     * @param riskModel model of the game
     * @param menuListener listen the events in the menu
     */
    public void initialMenu(RiskModel riskModel, MenuListener menuListener);

    /**
     * Close menu action
     */
    public void closeMenu();

    /**
     * Getter of the map path for the new game panel
     *
     * @return the map path for the new game panel
     */
    public String getMapPathForNewGame();

    /**
     * Getter of the player list for the new game
     *
     * @return the player list for the new game
     */
    public LinkedList<PlayerPanel> getPlayersForNewGame();

    /**
     * Open a Message Dialog to show message to user
     *
     * @param message Text to be displayed in the message dialog
     */
    public void showMessage(String message);

    /**
     * Make the view visible or not to the user
     *
     * @param b True if the view should be displayed
     */
    public void setVisible(boolean b);

    /**
     * Display an error to the user
     *
     * @param errorMsg Error message to display
     */
    public void showError(String errorMsg);

    /**
     * Return the log lines currently displayed
     *
     * @return the log lines currently displayed
     */
    public LinkedList<String> getLogs();

    /**
     * Set the log lines that should be displayed
     *
     * @param logs the log lines that should be displayed
     */
    public void setLogs(LinkedList<String> logs);
}
