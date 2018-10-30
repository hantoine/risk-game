/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.attack;


import com.risk.controllers.GameController;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * View to move the armies to the conquered country
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
     * Message with the countries involve in the attack
     */
    JLabel message;
    /**
     * Field with the number of armies to move
     */
    JTextField armiesMoved;
    /**
     * Number of armies in the source country (of the attack)
     */
    int max;
    
    /**
     * Constructor
     */
    public ArmiesLeft() {
        more=new JButton("+");
        armiesMoved=new JTextField("0");
        move=new JButton("Move");
        message=new JLabel("");
        
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
     * @param sourceCountry name of the country that have the armies that are attacking
     * @param countryDest name of the country that receives the attack
     * @param gc game controller
     * @param max max number of dices
     */
    public void update(String sourceCountry, String countryDest,GameController gc, int max){
        this.max=max;
        more.setEnabled(true);
        armiesMoved.setText("0");
        setListener(gc);
        message.setText(sourceCountry+" -> "+countryDest);
        this.setVisible(true);
    }
    
    /**
     * Sets the listener for the buttons
     * @param gc game controller
     */
    private void setListener(GameController gc){
        more.addActionListener(e -> {
            int i = 0;
            if(Integer.parseInt(armiesMoved.getText())<max-1){
                i=Integer.parseInt(armiesMoved.getText())+1;
                armiesMoved.setText(Integer.toString(i));
            }else   
                more.setEnabled(false);
        });
        
        move.addActionListener(e -> {
            gc.moveArmiesAttack(Integer.parseInt(armiesMoved.getText()));
        });
        
     }
}
