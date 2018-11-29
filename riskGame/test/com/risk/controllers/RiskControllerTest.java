/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.controllers;

import com.risk.models.RiskModel;
import com.risk.views.RiskView;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author user
 */
public class RiskControllerTest {

    /**
     * model of a game.
     */
    RiskModel riskModel;
   
    /**
     * View that displays information of the game from riskModel data.
     */
    RiskView riskView;
    
    /**
     * Controller of the game containing riskView and riskModel.
     */
    RiskController riskController;

    public void RiskControllerTest() {

    }

    /**
     * This method just setup a risk model and a risk view and then launch a new game.
     */
    @Before
    public void setUp() {
        riskModel = new RiskModel();
        riskView = new RiskView();
        riskView.setVisible(false);
        riskController = new RiskController(riskModel, riskView);
        riskView.setController(riskController);
        riskController.pressPlayButtonRandomMap();
    }

    /**
     * Test to save and load a map model, then compare the content of both map model (before and after saving).
     * The comparison is made using "identical" method which is like an equal method to check the content of 
     * objects rather than checking pointers.
     */
    @Test
    public void testSaveGame() {
        String filePath="test.ser";
        
        //saving the risk model using the method saveGame of the controller
        this.riskController.saveGame(filePath);
        
        //creating a new risk model
        RiskModel loadedGameModel = new RiskModel();
        RiskView tmpView = new RiskView();
        tmpView.setVisible(false);
        RiskController testingController = new RiskController(loadedGameModel, tmpView);
        testingController.pressPlayButtonRandomMap();
        
        //loading the saved model into the new model from the backup file 
        testingController.loadGame(filePath);
        
        //testing equality
        boolean test = this.riskModel.identical(testingController.getModelRisk());
        assertTrue(test);
    }
    
}
