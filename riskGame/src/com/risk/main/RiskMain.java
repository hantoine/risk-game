/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.main;

import com.risk.controllers.MainMenuController;
import com.risk.views.MainMenuView;

/**
 * Main of the game
 *
 * @author n_irahol
 */
public class RiskMain {

    /**
     * Creates the view and model and passes them to the controller
     *
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        MainMenuView mmv = new MainMenuView();
        MainMenuController mmc = new MainMenuController(mmv);
        mmv.setController(mmc);
    }
}
