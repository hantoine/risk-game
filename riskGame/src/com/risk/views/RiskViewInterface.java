/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views;

import com.risk.controllers.MenuListener;
import com.risk.controllers.RiskController;
import com.risk.models.RiskModel;
import com.risk.views.menu.NewGamePanel;

/**
 *
 * @author hantoine
 */
public interface RiskViewInterface {

    /**
     * Update the information displayed in the view to match the ones in the
     * model
     *
     * @param rm Model of the game
     */
    public void updateView(RiskModel rm);

    /**
     * Update the information displayed in the view to match the ones in the
     * model Update also the map which can have changed (if not changed just
     * overhead)
     *
     * @param rm model of the game
     */
    public void updateViewWithNewMap(RiskModel rm);

    /**
     * Open a Message Dialog to show message to user
     *
     * @param message Text to be displayed in the message dialog
     */
    public void showMessage(String message);

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
     * Getter of the new game panel inside the menu panel
     *
     * @return the new panel
     */
    public NewGamePanel getNewGamePanel();
}
