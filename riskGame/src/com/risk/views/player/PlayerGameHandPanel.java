/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.player;

import com.risk.models.RiskModel;
import com.risk.models.interfaces.PlayerModel;
import java.awt.Image;
import java.io.File;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * View of the cards owned by the player
 *
 * @author liyixuan
 */
public final class PlayerGameHandPanel extends JPanel{

    JButton handCard;

    /**
     * Constructor
     *
     */
    public PlayerGameHandPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    }

    /**
     * Update the information displayed by the PlayerGameHandPanl according to
     * the information of the current player
     *
     * @param rm The model of the game containing all information about the
     * current player
     */
    public void updateView(RiskModel rm) {
        PlayerModel currentPlayer = rm.getCurrentPlayer();
        this.removeAll();
        currentPlayer.getCardsOwned().getCards().stream()
                .forEach((card) -> {
                    System.out.println("printing cards");
                    // get the corresponding card ImageIcon and resize the card image
                    ImageIcon cardIcon = new ImageIcon("." + File.separator + "images"
                            + File.separator + card.getTypeOfArmie() + ".png");
                    Image image = cardIcon.getImage();
                    Image newImage = image.getScaledInstance(50, 70, java.awt.Image.SCALE_SMOOTH);
                    cardIcon = new ImageIcon(newImage);
                    JButton aux = new JButton();
                    aux.setIcon(cardIcon);
                    aux.setText("");
                    aux.setBackground(currentPlayer.getColor());
                    this.add(aux);
                }
                );
        this.repaint();
    }
}
