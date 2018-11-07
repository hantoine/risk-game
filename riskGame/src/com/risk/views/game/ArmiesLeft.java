/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.game;

import com.risk.controllers.GameController;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * View to move the armies to the conquered territory
 *
 * @author Nellybett
 */
public class ArmiesLeft extends JPanel {

    /**
     * Button to increase the number of armies to move
     */
    JButton more;
    /**
     * Button to move the armies
     */
    JButton move;
    /**
     * Message with the territories involve in the attack
     */
    JLabel message;
    /**
     * Field with the number of armies to move
     */
    JTextField armiesMoved;
    /**
     * Number of armies in the source territory (of the attack)
     */
    int max;

    /**
     * Constructor
     */
    public ArmiesLeft() {
        more = new JButton("+");
        armiesMoved = new JTextField("1");
        move = new JButton("Move");
        message = new JLabel("");

        more.setSize(50, 50);
        Dimension d = new Dimension(50, 20);
        this.armiesMoved.setPreferredSize(d);
        armiesMoved.setEditable(false);

        this.add(message);
        this.add(armiesMoved);
        this.add(more);
        this.add(move);
    }

    /**
     * It updates the view
     *
     * @param src name of the territory that have the armies that are attacking
     * @param dest name of the territory that receives the attack
     * @param max max number of dices
     * @param min min number of armies
     */
    public void update(String src, String dest, int max, int min) {
        this.max = max;
        if (min == max - 1) {
            more.setEnabled(false);
        } else {
            more.setEnabled(true);
        }
        armiesMoved.setText(Integer.toString(min));
        message.setText(src + " -> " + dest);
        this.setVisible(true);
    }

    /**
     * Sets the listener for the buttons
     *
     * @param gc game controller
     */
    public void setListener(GameController gc) {
        more.addActionListener(e -> {
            int i = Integer.parseInt(armiesMoved.getText()) + 1;
            if (i < max) {
                armiesMoved.setText(Integer.toString(i));
            }
            if (i == max - 1) {
                more.setEnabled(false);
            }
        });

        move.addActionListener(e -> {
            int armiesToTransfer = Integer.parseInt(armiesMoved.getText());

            gc.moveArmiesToConqueredTerritory(armiesToTransfer);
        });

    }
}
