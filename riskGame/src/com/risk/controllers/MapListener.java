/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.controllers;

import com.risk.models.RiskModel;
import com.risk.views.map.CountryButton;
import com.risk.views.map.MapPanel;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

/**
 * It listens to Mouse events in the map
 * It includes:
 * <ul>
 * <li>Attack movements</li>
 * <li>Reinforcement movements</li>
 * <li>Fortification movements</li>
 * </ul>
 * @author Nellybett
 */
public class MapListener extends MouseAdapter {

    private String countrySource;
    private String countryReceive;
    private String countryReinforce;
    private RiskModel riskModel;

    /**
     * Constructor
     * @param riskModel receives the model to change it when an event occurs 
     */
    public MapListener(RiskModel riskModel) {
        this.riskModel = riskModel;
    }

    /**
     * It manages a pressed event in the map
     * @param e the event to manage
     */
    @Override
    public void mousePressed(MouseEvent e) {
        MapPanel mapPanel;
        JComponent c = (JComponent) e.getSource();
        if (c != null && c instanceof MapPanel) {
            mapPanel = (MapPanel) c;
            Component cAux = SwingUtilities.getDeepestComponentAt(c, e.getX(), e.getY());
            if (cAux != null && cAux instanceof CountryButton) {
                CountryButton source = (CountryButton) cAux;
                source.setBackground(Color.gray);
                this.setCountrySource(source.getName());
                System.out.println(source.getName());
            }

        }

    }

    /**
     * It manages a release event from the map
     * @param e the event to manage
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        MapPanel mapPanel;
        JComponent c = (JComponent) e.getSource();
        if (c != null && c instanceof MapPanel) {
            mapPanel = (MapPanel) c;
            Component cAux = SwingUtilities.getDeepestComponentAt(c, e.getX(), e.getY());
            if (cAux != null && cAux instanceof CountryButton) {
                CountryButton destiny = (CountryButton) cAux;
                CountryButton source = mapPanel.getCountriesButtons().get(this.getCountrySource());
                source.setBackground(Color.white);
                System.out.println(source.getName() + "--->" + destiny.getName());
                this.setCountryReceive(source.getName());
            }

        }
    }

    /**
     * It manages a click event in the map
     * @param e the event to manage
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        MapPanel mapPanel;
        JComponent c = (JComponent) e.getSource();
        if (c != null && c instanceof MapPanel) {
            mapPanel = (MapPanel) c;
            Component cAux = SwingUtilities.getDeepestComponentAt(c, e.getX(), e.getY());
            if (cAux != null && cAux instanceof CountryButton) {

                CountryButton reinforce = (CountryButton) cAux;
                reinforce.setBackground(Color.white);
                this.countryReinforce = reinforce.getName();

            }

        }
    }

    
    /**
     * Getter of countrySource attribute
     * @return the countrySource
     */
    public String getCountrySource() {
        return countrySource;
    }

    /**
     * Setter of countrySource attribute
     * @param countrySource the countrySource to set
     */
    public void setCountrySource(String countrySource) {
        this.countrySource = countrySource;
    }

    /**
     * Getter of countryReceive attribute
     * @return the countryReceive
     */
    public String getCountryReceive() {
        return countryReceive;
    }

    /**
     * Setter of countryReceive attribute
     * @param countryReceive the countryReceive to set
     */
    public void setCountryReceive(String countryReceive) {
        this.countryReceive = countryReceive;
    }

    /**
     * Getter of riskModel attribute attribute
     * @return the riskModel
     */
    public RiskModel getRiskModel() {
        return riskModel;
    }

    /**
     * Setter of riskModel attribute
     * @param riskModel the riskModel to set
     */
    public void setRiskModel(RiskModel riskModel) {
        this.riskModel = riskModel;
    }
}
