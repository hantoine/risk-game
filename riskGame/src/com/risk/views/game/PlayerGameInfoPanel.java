/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.game;

import com.risk.models.PlayerModel;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.TitledBorder;

/**
 * View of the player information
 *
 * @author Will
 */
public final class PlayerGameInfoPanel extends JPanel {

    /**
     * playerName name of the player
     */
    private final JLabel percentMapControlledLabel;
    private final JProgressBar percentMapControlled;
    private final JList<String> continentsOwned;
    private final JLabel continentsOwnedLabel;

    /**
     * numArmiesOwned the number of armies owned by the player
     */
    private final JLabel numArmiesOwned;

    /**
     * Constructor
     *
     */
    public PlayerGameInfoPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setVisible(false);

        this.percentMapControlledLabel = new JLabel();
        this.numArmiesOwned = new JLabel();
        this.percentMapControlled = new JProgressBar();
        this.continentsOwned = new JList<>();
        this.continentsOwnedLabel = new JLabel();

        this.percentMapControlled.setStringPainted(true);
        this.percentMapControlledLabel.setText("Percentage of map controlled:");

        GroupLayout gl = new GroupLayout(this);
        this.setLayout(gl);
        gl.setAutoCreateGaps(true);
        gl.setAutoCreateContainerGaps(true);

        gl.setVerticalGroup(gl.createSequentialGroup()
                .addComponent(this.percentMapControlledLabel)
                .addComponent(this.percentMapControlled)
                .addComponent(this.numArmiesOwned)
                .addComponent(this.continentsOwnedLabel)
                .addComponent(this.continentsOwned)
        );
        gl.setHorizontalGroup(gl.createParallelGroup()
                .addComponent(this.percentMapControlledLabel)
                .addComponent(this.percentMapControlled)
                .addComponent(this.numArmiesOwned)
                .addComponent(this.continentsOwnedLabel)
                .addComponent(this.continentsOwned)
        );

        TitledBorder border;
        border = BorderFactory.createTitledBorder("PlayerName");
        border.setTitleJustification(TitledBorder.CENTER);
        this.setBorder(border);
    }

    /**
     * Update the information displayed by the PlayerGameInfoPanel according to
     * the information of the current player
     *
     * @param player current player
     */
    public void updateView(PlayerModel player) {
        this.setVisible(true);

        TitledBorder border = (TitledBorder) this.getBorder();
        border.setTitle(player.getName());
        border.setTitleColor(player.getColor());

        this.percentMapControlled.setValue(player.getPercentMapControlled());

        this.numArmiesOwned.setText("Army Owned: " + player.getNbArmiesOwned());

        String[] listContinentsOwned = (String[]) player.getContinentsOwned()
                .stream().map((c) -> c.getName()).toArray(String[]::new);
        if (listContinentsOwned.length != 0) {
            continentsOwnedLabel.setText("Continents owned:");
        } else {
            continentsOwnedLabel.setText("No continents owned");
        }
        this.continentsOwned.setListData(listContinentsOwned);
        this.repaint();
    }
}
