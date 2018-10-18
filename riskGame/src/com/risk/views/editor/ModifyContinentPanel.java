/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.editor;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

/**
 * Panel that is showed when editing a continent.
 *
 * @author timot
 */
public class ModifyContinentPanel extends JPanel {

    /**
     * Textfield that contains the name of the continent
     */
    protected JTextField continentNameField;

    /**
     * Spinner that contains the bonus score of the continent being edited.
     */
    protected JSpinner bonusScore;

    /**
     * Constructor.
     *
     * @param continentName the name of the continent 
     * @param bonusScore bonus score of the continent.
     */
    public ModifyContinentPanel(String continentName, int bonusScore) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setSize(500, 500);

        this.continentNameField = new JTextField(continentName);
        SpinnerNumberModel bonusScoreModel = new SpinnerNumberModel(bonusScore, //initial value
                0, //min
                20, //max
                1);     //step
        this.bonusScore = new JSpinner(bonusScoreModel);

        this.add(continentNameField);
        this.add(this.bonusScore);
    }

    /**
     * Getter on the continent's name
     *
     * @return the continent's name
     */
    public String getContinentName() {
        return this.continentNameField.getText();
    }

    /**
     * Getter on the bonus score
     *
     * @return the bonus score converted into a String
     */
    public String getBonusScore() {
        return Integer.toString((int) bonusScore.getValue());
    }
}
