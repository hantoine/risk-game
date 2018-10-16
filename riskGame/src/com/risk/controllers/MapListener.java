/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.controllers;

import com.risk.views.game.CountryLabel;
import com.risk.views.game.MapPanel;
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
     * countrySource It represents the country where the drag and drop action
     * starts
     */
    private CountryLabel countrySource;
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
            if (cAux instanceof CountryLabel) {
                CountryLabel source = (CountryLabel) cAux;
                source.setBackground(Color.gray);
                this.setCountrySource(source);

                System.out.println(source.getName());
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
            if (cAux instanceof CountryLabel) {
                CountryLabel destiny = (CountryLabel) cAux;
                CountryLabel source = this.getCountrySource();
                if (source == null) {
                    return;
                }
                System.out.println(source.getName() + "--->" + destiny.getName());
                this.riskController.getPlayGame().dragNDropTerritory(source.getName(), destiny.getName());
            }
        }
        if (this.getCountrySource() != null) {
            this.getCountrySource().setBackground(Color.white);
            this.setCountrySource(null);
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
            if (cAux instanceof CountryLabel) {
                CountryLabel countryClicked = (CountryLabel) cAux;
                countryClicked.setBackground(Color.white);

                this.riskController.getPlayGame().clickOnTerritory(countryClicked.getName());
            }
        }
    }

    /**
     * Getter of the countrySource attribute
     *
     * @return countrySource it is a label that represents a country
     */
    private CountryLabel getCountrySource() {
        return countrySource;
    }

    /**
     * Setter of the countrySource attribute
     *
     * @param countrySource a label that represents a country in the map
     */
    private void setCountrySource(CountryLabel countrySource) {
        this.countrySource = countrySource;
    }
}
