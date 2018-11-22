/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.controllers;

import com.risk.models.RiskModel;
import com.risk.views.RiskViewInterface;
import com.risk.views.menu.PlayerPanel;
import java.util.LinkedList;
import java.util.Observable;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author hantoine
 */
public class MenuListenerTest {

    RiskModel rm;
    DummyRiskView rv;
    RiskController rc;
    MenuListener instance;

    public MenuListenerTest() {
    }

    @Before
    public void setUp() {
        rm = new RiskModel();
        rv = new DummyRiskView();

        instance = new MenuListener(rm, rv, null);
    }

    @Test
    public void testPlayButton() {

    }

    /**
     * Dummy view used to test that the view is correctly notified
     */
    private static class DummyRiskView implements RiskViewInterface {

        String message;

        public DummyRiskView() {
        }

        @Override
        public void observeModel(RiskModel rm) {
            rm.addObserver(this);
        }

        @Override
        public void setController(RiskController rc) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void initialMenu(RiskModel riskModel, MenuListener menuListener) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void closeMenu() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public String getMessage() {
            return this.message;
        }

        @Override
        public void update(Observable o, Object o1) {
            if (o instanceof RiskModel && o1 instanceof String) {
                this.message = (String) o1;
            }
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
    }

}
