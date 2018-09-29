    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.player;

import com.risk.models.CardModel;
import com.risk.models.interfaces.PlayerModel;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * View of the player information
 * @author Will
 */
public class PlayerGameInfoPanel extends JPanel {

    private PlayerModel currentPlayer;
    private JButton playerName;
    private BufferedImage image;
    private ImageIcon background;
    private JButton numArmies;
    private Box armyBox;
    private JButton numCountries;
    private JButton numContinents;
    private Box cardBox;
    private JButton numCards;
    private JLabel title;
    
    private int infantryNum = 0;
    private int cavalryNum = 0;
    private int artillery = 0;
    
    /**
     * Constructor
     * @param currentPlayer player that has the turn
     * @throws IOException 
     */
    public PlayerGameInfoPanel(PlayerModel currentPlayer) throws IOException {
        this.currentPlayer = currentPlayer;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setSize(150,450);
        this.playerName = new JButton();
        
        this.numArmies = new JButton("Army Owned: " + currentPlayer.getNumArmiesOwned()); //I think it is going to be hard keep track of this
        this.armyBox = new Box(BoxLayout.X_AXIS);
        this.numContinents = new JButton("Continent Owned: " + currentPlayer.getContinentsOwned().size());
        this.numCountries = new JButton("Country Owned: " + currentPlayer.getContriesOwned().size());
        this.numCards = new JButton("Card Owned: " + currentPlayer.getCardsOwned().getHand().size());
        this.cardBox = new Box(BoxLayout.X_AXIS);
        
        //I think you are repeating code, this should be eliminated
        /*
        // initialize the armyBox
        ImageIcon infantryIcon = new ImageIcon("." + File.separator + "images" + File.separator + "icon-1.png");
        ImageIcon cavalryIcon = new ImageIcon("." + File.separator + "images" + File.separator + "icon-2.png");
        ImageIcon artilleryIcon = new ImageIcon("." + File.separator + "images" + File.separator + "icon-3.png");
        
        JLabel numInfantry = new JLabel(infantryIcon);
        JLabel numCavalry = new JLabel(cavalryIcon);
        JLabel numArtillery = new JLabel(artilleryIcon);
        
        Box artilleryArmyBox = new Box(BoxLayout.X_AXIS);
        int totalNumArmies = currentPlayer.getNumArmies();  // get total amount of armies owned
        this.artillery = totalNumArmies / 10;
        artilleryArmyBox.add(numArtillery);
        artilleryArmyBox.add(new JLabel("* " + artillery + "     "));
        
        this.cavalryNum = (totalNumArmies - (totalNumArmies / 10) * 10) / 5;
        Box cavalryArmyBox = new Box(BoxLayout.X_AXIS);
        cavalryArmyBox.add(numCavalry);
        cavalryArmyBox.add(new JLabel("* " + cavalryNum + "     "));
        
        this.infantryNum = totalNumArmies - (totalNumArmies / 10) * 10 - (((totalNumArmies - (totalNumArmies / 10) * 10) / 5) * 5);
        Box infantryArmyBox = new Box(BoxLayout.X_AXIS);
        infantryArmyBox.add(numInfantry);
        infantryArmyBox.add(new JLabel("* " + infantryNum + "     "));
        this.armyBox.add(infantryArmyBox);
        this.armyBox.add(cavalryArmyBox);
        this.armyBox.add(artilleryArmyBox);
        
        // initialize the cardBox
        Box infantryCardBox = new Box(BoxLayout.Y_AXIS);
        Box cavalryCardBox = new Box(BoxLayout.Y_AXIS);
        Box artilleryCardBox = new Box(BoxLayout.Y_AXIS);
        for (CardModel hand : this.currentPlayer.getCardsOwned().getHand()) {
            // get the corresponding card ImageIcon and resize the card image
            ImageIcon cardIcon = new ImageIcon("." + File.separator + "images" 
                    + File.separator + hand.getTypeOfArmie() + ".png");
            Image image = cardIcon.getImage();
            Image newImage = image.getScaledInstance(50, 70, java.awt.Image.SCALE_SMOOTH);
            cardIcon = new ImageIcon(newImage);


            JButton aux = new JButton();
            aux.setIcon(cardIcon);
            aux.setText("");
            switch(hand.getTypeOfArmie()) {
                case "infantry":
                    infantryCardBox.add(aux);
                    break;
                case "cavalry":
                    cavalryCardBox.add(aux);
                    break;
                case "artillery":
                    artilleryCardBox.add(aux);
                    break;
            }
        }
        cardBox.add(infantryCardBox);
        cardBox.add(cavalryCardBox);
        cardBox.add(artilleryCardBox); */
        
        // add player name
        this.add(this.playerName);
        this.add(this.numArmies);
        this.add(this.armyBox);
        this.add(this.numCountries);
        this.add(this.numContinents);
        this.add(this.numCards);
        this.add(this.cardBox);
        
        updatePlayer(currentPlayer); //It is better if you call it from outside but it was just to reuse the code
        // work:
        /*
        1. make the player panel more beautiful (title, border, card image, fix the width)
        2. 
        */
        // set a background
//        try {
//            image = ImageIO.read(new File("." + File.separator + "images" 
//                    + File.separator + "PlayerPanelBackground.jpg"));
//        } catch (IOException ex) {
//            System.out.println("playerPanelBackground.jpg not found.");
//        }
//        background = new JLabel(new ImageIcon(image));
//        this.add(background);
        
        // 
    }
    
    /**
     * 
     * @param currentPlayer 
     */
    public void updatePlayer(PlayerModel currentPlayer){
        this.currentPlayer = currentPlayer;
        
        // update player name and color
        playerName.setText(this.currentPlayer.getName());
        playerName.setBackground(this.currentPlayer.getColor());
        
        // update armies
        ImageIcon infantryIcon = new ImageIcon("." + File.separator + "images" + File.separator + "icon-1.png");
        ImageIcon cavalryIcon = new ImageIcon("." + File.separator + "images" + File.separator + "icon-2.png");
        ImageIcon artilleryIcon = new ImageIcon("." + File.separator + "images" + File.separator + "icon-3.png");
        JLabel numInfantry = new JLabel(infantryIcon);
        JLabel numCavalry = new JLabel(cavalryIcon);
        JLabel numArtillery = new JLabel(artilleryIcon);
        Box artilleryArmyBox = new Box(BoxLayout.X_AXIS);
        int totalNumArmies = currentPlayer.getNumArmiesOwned();  // get total amount of armies owned
        this.artillery = totalNumArmies / 10;
        artilleryArmyBox.add(numArtillery);
        artilleryArmyBox.add(new JLabel("* " + artillery + "     "));
        this.cavalryNum = (totalNumArmies - (totalNumArmies / 10) * 10) / 5;
        Box cavalryArmyBox = new Box(BoxLayout.X_AXIS);
        cavalryArmyBox.add(numCavalry);
        cavalryArmyBox.add(new JLabel("* " + cavalryNum + "     "));
        this.infantryNum = totalNumArmies - (totalNumArmies / 10) * 10 - (((totalNumArmies - (totalNumArmies / 10) * 10) / 5) * 5);
        Box infantryArmyBox = new Box(BoxLayout.X_AXIS);
        infantryArmyBox.add(numInfantry);
        infantryArmyBox.add(new JLabel("* " + infantryNum + "     "));
        this.armyBox.removeAll();
        this.armyBox.add(infantryArmyBox);
        this.armyBox.add(cavalryArmyBox);
        this.armyBox.add(artilleryArmyBox);
        
        // update countries and continents
        this.numCountries.setText("Countries Owned: " + Integer.toString(currentPlayer
                .getContriesOwned().size()));
        this.numContinents.setText("Continents Owned: " + Integer.toString(currentPlayer
                .getContinentsOwned().size()));
        
        // update cards
        this.numCards.setText("Card Owned: " + currentPlayer.getCardsOwned().getHand().size());
        Box infantryCardBox = new Box(BoxLayout.Y_AXIS);
        Box cavalryCardBox = new Box(BoxLayout.Y_AXIS);
        Box artilleryCardBox = new Box(BoxLayout.Y_AXIS);
        for (CardModel hand : this.currentPlayer.getCardsOwned().getHand()) {
            // get the corresponding card ImageIcon and resize the card image
            ImageIcon cardIcon = new ImageIcon("." + File.separator + "images" 
                    + File.separator + hand.getTypeOfArmie() + ".png");
            Image image = cardIcon.getImage();
            Image newImage = image.getScaledInstance(50, 70, java.awt.Image.SCALE_SMOOTH);
            cardIcon = new ImageIcon(newImage);
            JButton aux = new JButton();
            aux.setIcon(cardIcon);
            aux.setText("");
            aux.setBackground(this.currentPlayer.getColor());
            switch(hand.getTypeOfArmie()) {
                case "infantry":
                    infantryCardBox.add(aux);
                    break;
                case "cavalry":
                    cavalryCardBox.add(aux);
                    break;
                case "artillery":
                    artilleryCardBox.add(aux);
                    break;
            }
        }
        cardBox.removeAll();
        cardBox.add(infantryCardBox);
        cardBox.add(cavalryCardBox);
        cardBox.add(artilleryCardBox);
        
        
    }

    /**
     * 
     * @param currentPlayer 
     */
    public void setCurrentPlayer(PlayerModel currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * @return the playerName
     */
    public JButton getPlayerName() {
        return playerName;
    }

    /**
     * @param playerName the playerName to set
     */
    public void setPlayerName(JButton playerName) {
        this.playerName = playerName;
    }

}