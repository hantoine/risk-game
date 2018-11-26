/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.game;

import com.risk.controllers.GameController;
import com.risk.models.AttackMove;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;
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
     * Message with the name of the territories involve in an attack
     */
    JLabel territoriesInvolve;
    /**
     * subject of the action
     */
    JLabel actionSubject;

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
        actionSubject = new JLabel("Attacker");
        territoriesInvolve = new JLabel("source vs destination");

        options[0].setSize(50, 70);
        options[1].setSize(50, 70);
        options[2].setSize(50, 70);
        options[3].setSize(50, 70);

        attackOptions.add(actionSubject);
        attackOptions.add(territoriesInvolve);
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
     * @param src name of the territory that have the armies that are attacking
     * @param dest name of the territory that receives the attack
     * @param armies number of dice involve in the attack
     */
    public void update(String src, String dest, int armies) {
        this.setVisible(true);
        this.actionSubject.setText("Attacker");
        territoriesInvolve.setText(src + " vs " + dest);

        for (int i = 0; i < 4; i++) {
            options[i].setEnabled(i < armies - 1);
            options[i].setVisible(true);
        }
    }

    /**
     * Adds the listener for each button
     *
     * @param gc game controller
     */
    public void setListeners(GameController gc) {

        
        //remove old listeners      
        Arrays.stream(options).forEach((o) -> {
            Arrays.stream(o.getActionListeners()).forEach((a)->{
                o.removeActionListener(a);
            });
        });

        
        options[0].addActionListener(e -> {
            gc.clickAttack(1);
        });

        options[1].addActionListener(e -> {
            gc.clickAttack(2);

        });

        options[2].addActionListener(e -> {
            gc.clickAttack(3);

        });

        options[3].addActionListener(e -> {
            gc.clickAttack(-1);
        });
    }

}
