/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.attack;

import com.risk.controllers.GameController;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * It contains the number of dices in an attack
 *
 * @author Nellybett
 */
public class AttackView extends JPanel {

    /**
     * Panel with the different buttons
     */
    JPanel attackOptions;
    /**
     * An array of buttons with the number of dices
     */
    JButton[] options;
    /**
     * Message with the name of the countries involve in an attack
     */
    JLabel countriesInvolve;
    String source;
    String dest;

    /**
     * Constructor
     *
     */
    public AttackView() {

        attackOptions = new JPanel();
        options = new JButton[4];
        options[0] = new JButton("1");
        options[1] = new JButton("2");
        options[2] = new JButton("3");
        options[3] = new JButton("All");

        countriesInvolve = new JLabel("source vs destination");

        options[0].setSize(50, 70);
        options[1].setSize(50, 70);
        options[2].setSize(50, 70);
        options[3].setSize(50, 70);

        attackOptions.add(countriesInvolve);
        attackOptions.add(options[0]);
        attackOptions.add(options[1]);
        attackOptions.add(options[2]);
        attackOptions.add(options[3]);

        this.add(attackOptions);

        this.setVisible(false);
    }

    /**
     * Updates the view for an attack
     *
     * @param countrySource name of the country that have the armies that are
     * attacking
     * @param countryDest name of the country that receives the attack
     * @param armies number of dice involve in the attack
     */
    public void update(String countrySource, String countryDest, int armies) {
        this.setVisible(true);
        this.source = countrySource;
        this.dest = countryDest;
        countriesInvolve.setText(countrySource + " vs " + countryDest);

        switch (armies) {
            case (2):
                options[0].setEnabled(true);
                options[1].setEnabled(false);
                options[2].setEnabled(false);
                options[3].setEnabled(false);
                break;
            case (3):
                options[0].setEnabled(true);
                options[1].setEnabled(true);
                options[2].setEnabled(false);
                options[3].setEnabled(false);
                break;
            case (4):
                options[0].setEnabled(true);
                options[1].setEnabled(true);
                options[2].setEnabled(true);
                options[3].setEnabled(false);
                break;
            default:
                options[0].setEnabled(true);
                options[1].setEnabled(true);
                options[2].setEnabled(true);
                options[3].setEnabled(true);
                break;

        }
    }

    /**
     * Adds the listener for each button
     *
     */
    public void setListeners(GameController gc) {
        options[0].addActionListener(e -> {
            gc.clickAttack(source, dest, 1);
        });

        options[1].addActionListener(e -> {
            gc.clickAttack(source, dest, 2);
        });

        options[2].addActionListener(e -> {
            gc.clickAttack(source, dest, 3);
        });

        options[3].addActionListener(e -> {
            gc.clickAttack(source, dest, 4);
        });
    }
}
