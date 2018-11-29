/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.controllers;

import com.risk.models.GamePhase;
import com.risk.models.MapFileManagement;
import com.risk.models.MapModel;
import com.risk.models.PlayerFactory;
import com.risk.models.PlayerModel;
import com.risk.models.RiskModel;
import com.risk.views.RiskViewInterface;
import com.risk.views.menu.PlayerPanel;
import java.awt.Color;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Observable;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.AssumptionViolatedException;
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
     * Dummy view used by the RiskController
     */
    DummyRiskView riskView;

    /**
     * Controller of the game containing riskView and riskModel.
     */
    RiskController riskController;

    public void RiskControllerTest() {

    }

    /**
     * This method just setup a risk model and a risk view and then launch a new
     * game.
     */
    @Before
    public void setUp() {
        riskModel = new RiskModel();
        riskView = new DummyRiskView();
        riskController = new RiskController(riskModel, riskView);
        simulateRiskGame();
    }

    /**
     * Test to save and load a map model, then compare the content of both map
     * model (before and after saving). The comparison is made using "identical"
     * method which is like an equal method to check the content of objects
     * rather than checking pointers.
     */
    @Test
    public void testSaveGame() {
        String filePath = "test.ser";
        RiskModel testModel = new RiskModel();
        DummyRiskView drv = new DummyRiskView();
        RiskController testController = new RiskController(testModel, drv);

        //saving the risk model using the method saveGame of the controller
        this.riskController.saveGame(filePath);

        //loading the saved model into the new model from the backup file
        testController.loadGame(filePath);

        // Testing equality
        boolean test = this.riskModel.identical(testController.getModelRisk());
        assertTrue(test);

        // testing the game can continue
        this.currentPlayerPlays(testModel, testController);
        assertEquals(
                testModel.getCurrentPlayer(),
                testModel.getPlayerList().get(0)
        );
    }

    /**
     * Simulate the beginning of a game
     *
     */
    private void simulateRiskGame() {
        // Load map
        MapModel map = new MapModel();
        try {
            MapFileManagement.createBoard(Paths.get("maps", "Atlantis.map").toString(), map);
        } catch (MapFileManagement.MapFileManagementException ex) {
            throw new AssumptionViolatedException("Failed to load map");
        }
        riskModel.setMap(map);

        // Set players
        LinkedList<PlayerModel> players = new LinkedList<>();
        players.add(
                PlayerFactory.getPlayer(
                        "HUMAN",
                        "Player1",
                        Color.black
                )
        );
        players.add(
                PlayerFactory.getPlayer(
                        "HUMAN",
                        "Player2",
                        Color.black
                )
        );

        riskModel.setPlayerList(players);

        // start game
        riskModel.startGame();
        // Automatically perform startup phase
        riskModel.finishPhase();

        currentPlayerPlays(riskModel, riskController);

    }

    /**
     * Make the current Player play its turn
     *
     * @param rm RiskModel of the game on which to play
     * @param rc Corresponding controller
     */
    private void currentPlayerPlays(RiskModel rm, RiskController rc) {
        //place available
        while (rm.getPhase() == GamePhase.REINFORCEMENT) {
            String terrName = rm.getCurrentPlayer()
                    .getTerritoryOwned().get(0).getName();
            rc.getGameController().clickOnTerritory(terrName);
        }
        // does not attack
        if (rm.getPhase() == GamePhase.ATTACK) {
            rc.getGameController().endPhaseButtonPressed();
        }
        // does not fortify
        if (rm.getPhase() == GamePhase.FORTIFICATION) {
            rc.getGameController().endPhaseButtonPressed();
        }
    }

    /**
     * Dummy view used to test that the view is correctly notified
     */
    private static class DummyRiskView implements RiskViewInterface {

        LinkedList<String> logs;

        public DummyRiskView() {
        }

        @Override
        public void observeModel(RiskModel rm) {
            rm.addObserver(this);
        }

        @Override
        public void setController(RiskController rc) {
        }

        @Override
        public void initialMenu(RiskModel riskModel, MenuListener menuListener) {
        }

        @Override
        public void closeMenu() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void update(Observable o, Object o1) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String getMapPathForNewGame() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public LinkedList<PlayerPanel> getPlayersForNewGame() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void showMessage(String message) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void setVisible(boolean b) {
        }

        @Override
        public void showError(String errorMsg) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public LinkedList<String> getLogs() {
            return logs;
        }

        @Override
        public void setLogs(LinkedList<String> logs) {
            this.logs = logs;
        }
    }

}
