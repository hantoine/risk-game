/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.attack;

import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Nellybett
 */
public class AttackView extends JPanel{
    JPanel attackOptions;
    JButton[] options;
    JLabel countriesInvolve;
    
    public AttackView(String source, String dest){
        
        attackOptions=new JPanel();
        
        options=new JButton[4];
        options[0]=new JButton("1");
        options[1]=new JButton("2");
        options[2]=new JButton("3");
        options[3]=new JButton("All");
        
        countriesInvolve=new JLabel(source+" vs "+dest);
        
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
    }
    public void update(String countrySource, String countryDest){
        this.setVisible(true);
        countriesInvolve.setText(countrySource+" vs "+countryDest);
    }
    
}
