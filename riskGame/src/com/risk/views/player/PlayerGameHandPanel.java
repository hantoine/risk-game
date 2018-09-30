/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.player;

import com.risk.models.interfaces.PlayerModel;
import java.awt.Image;
import java.io.File;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * View of the cards owned by the player
 * @author liyixuan
 */
public final class PlayerGameHandPanel extends JPanel {
    
    JButton handCard;

    /**
     * Constructor
     *
     * @param currentPlayer the current player whose hand will be displayed
     */
    public PlayerGameHandPanel(PlayerModel currentPlayer) {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setSize(150, 450);
        updatePlayer(currentPlayer);
    }

    public void updatePlayer(PlayerModel currentPlayer) {

        this.removeAll();
        
        currentPlayer.getCardsOwned().getCards().stream().forEach((card) -> {
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
        });
    }
}
