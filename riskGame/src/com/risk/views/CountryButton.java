/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views;

import com.risk.models.Country;
import javax.swing.JButton;

/**
 *
 * @author hantoine
 */
public class CountryButton extends JButton {
    //Jlabel with JtextField inside or JTextField directly
    private Country country;
    private static final int buttonSize = 25;

    public CountryButton(Country country) {
        super("");
        this.country = country;
        this.setLocation(country.getPositionX() - buttonSize / 2, country.getPositionY() - buttonSize / 2);
        this.setSize(buttonSize, buttonSize);
    }

}
