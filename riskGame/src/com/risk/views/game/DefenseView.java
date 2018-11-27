/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.game;

import com.risk.controllers.GameController;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Defense view
 *
 * @author Nellybett
 */
public class DefenseView extends JPanel {

    /**
     * Panel with the different buttons
     */
    JPanel defenseOptions;
    /**
     * An array of buttons with the number of dices
     */
    JButton[] options;
    /**
     * Message with the name of the territories involve in an attack
     */
    JLabel territoriesInvolve;
    /**
     * subject of the action
     */
    JLabel actionSubject;

    /**
     * Constructor
     */
    public DefenseView() {

        defenseOptions = new JPanel();
        options = new JButton[2];
        options[0] = new JButton("1");
        options[1] = new JButton("2");

        actionSubject = new JLabel("Attacked");
        territoriesInvolve = new JLabel("");

        options[0].setSize(50, 70);
        options[1].setSize(50, 70);

        defenseOptions.add(actionSubject);
        defenseOptions.add(territoriesInvolve);
        defenseOptions.add(options[0]);
        defenseOptions.add(options[1]);

        this.add(defenseOptions);
        this.setVisible(false);
    }

    /**
     * Updates the view for an attack
     *
     * @param src name of the territory that have the armies that are attacking
     * @param dest name of the territory that receives the attack
     * @param armies number of dice involve in the attack
     */
    public void update(String src, String dest, int armies) {
        this.setVisible(true);
        territoriesInvolve.setText(src + " vs " + dest);

        for (int i = 0; i < 2; i++) {
            options[i].setEnabled(armies > i + 1);
            options[i].setVisible(true);
        }
    }

    /**
     * Adds the listener for each button
     *
     * @param gc game controller
     */
    public void setListeners(GameController gc) {

        options[0].addActionListener(e -> {
            gc.clickDefense(1);
        });

        options[1].addActionListener(e -> {
            gc.clickDefense(2);

        });

    }
}
