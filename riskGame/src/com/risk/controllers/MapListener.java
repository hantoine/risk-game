/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.controllers;

import com.risk.views.game.MapPanel;
import com.risk.views.game.TerritoryLabel;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

/**
 * It listens to Mouse events in the map It includes:
 * <ul>
 * <li>Attack movements</li>
 * <li>Reinforcement movements</li>
 * <li>Fortification movements</li>
 * </ul>
 *
 * @author Nellybett
 */
public class MapListener extends MouseAdapter {

    /**
     * territorySource It represents the territory where the drag and drop
     * action starts
     */
    private TerritoryLabel territorySource;
    /**
     * riskController It is a reference to the principal controller of the game
     */
    final private RiskController riskController;

    /**
     * Constructor
     *
     * @param riskController The RiskController controlling the main view of the
     * game
     */
    public MapListener(RiskController riskController) {
        this.riskController = riskController;
    }

    /**
     * It manages a pressed event in the map
     *
     * @param e the event to manage
     */
    @Override
    public void mousePressed(MouseEvent e) {
        JComponent c = (JComponent) e.getSource();
        if (c instanceof MapPanel) {
            Component cAux = SwingUtilities.getDeepestComponentAt(c, e.getX(), e.getY());
            if (cAux instanceof TerritoryLabel) {
                TerritoryLabel source = (TerritoryLabel) cAux;
                source.setBackground(Color.gray);
                this.setTerritorySource(source);
            }

        }

    }

    /**
     * It manages a release event from the map
     *
     * @param e the event to manage
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        JComponent c = (JComponent) e.getSource();
        if (c instanceof MapPanel) {
            Component cAux = SwingUtilities.getDeepestComponentAt(c, e.getX(), e.getY());
            if (cAux instanceof TerritoryLabel) {
                TerritoryLabel destiny = (TerritoryLabel) cAux;
                TerritoryLabel source = this.getTerritorySource();
                if (source == null) {
                    return;
                }
                this.riskController.getGameController().dragNDropTerritory(source.getName(), destiny.getName());
            }
        }
        if (this.getTerritorySource() != null) {
            this.getTerritorySource().setBackground(Color.white);
            this.setTerritorySource(null);
        }

    }

    /**
     * It manages a click event in the map
     *
     * @param e the event to manage
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        JComponent c = (JComponent) e.getSource();
        if (c instanceof MapPanel) {
            Component cAux = SwingUtilities.getDeepestComponentAt(c, e.getX(), e.getY());
            if (cAux instanceof TerritoryLabel) {
                TerritoryLabel terrClicked = (TerritoryLabel) cAux;
                terrClicked.setBackground(Color.white);

                this.riskController.getGameController()
                        .clickOnTerritory(terrClicked.getName());
            }
        }
    }

    /**
     * Getter of the territorySource attribute
     *
     * @return territorySource it is a label that represents a territory
     */
    private TerritoryLabel getTerritorySource() {
        return territorySource;
    }

    /**
     * Setter of the territorySource attribute
     *
     * @param terrSource a label that represents a territory in the map
     */
    private void setTerritorySource(TerritoryLabel terrSource) {
        this.territorySource = terrSource;
    }
}
