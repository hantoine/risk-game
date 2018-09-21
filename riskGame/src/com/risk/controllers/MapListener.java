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
 *
 * @author Nellybett
 */
public class MapListener extends MouseAdapter {

    String countrySource;
    String countryReceive;
    private String countryReinforce;
    RiskModel riskModel;

    public MapListener(RiskModel riskModel) {
        this.riskModel = riskModel;
    }

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
                this.countrySource = source.getName();
                System.out.println(source.getName());
            }

        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        MapPanel mapPanel;
        JComponent c = (JComponent) e.getSource();
        if (c != null && c instanceof MapPanel) {
            mapPanel = (MapPanel) c;
            Component cAux = SwingUtilities.getDeepestComponentAt(c, e.getX(), e.getY());
            if (cAux != null && cAux instanceof CountryButton) {
                CountryButton destiny = (CountryButton) cAux;
                CountryButton source = mapPanel.getCountriesButtons().get(this.countrySource);
                source.setBackground(Color.white);
                System.out.println(source.getName() + "--->" + destiny.getName());
                this.countryReceive = source.getName();
            }

        }
    }

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

}
