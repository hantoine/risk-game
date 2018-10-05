/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.mapeditor;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author timot
 */
public class ModifyContinentPanel extends JPanel {
    protected JTextField continentNameField;
    protected JSpinner bonusScore;

    public ModifyContinentPanel(String continentName, int bonusScore) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setSize(500, 500);

        this.continentNameField = new JTextField(continentName);
        SpinnerNumberModel bonusScoreModel = new SpinnerNumberModel(bonusScore, //initial value
                               0, //min
                               20, //max
                               1);                //step
        this.bonusScore = new JSpinner(bonusScoreModel);
        
        this.add(continentNameField);
        this.add(this.bonusScore);
    }

    public String getContinentName() {
        return this.continentNameField.getText();
    }

    public String getBonusScore() {
        return Integer.toString((int)bonusScore.getValue());
    }
}
