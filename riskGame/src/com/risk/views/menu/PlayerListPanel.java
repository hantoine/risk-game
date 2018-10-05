/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views.menu;

import com.risk.controllers.MenuListener;
import com.risk.models.interfaces.PlayerModel;
import com.risk.models.RiskModel;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.LinkedList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Panel that contains multiple panels with players information
 *
 * @author Nellybett
 */
public class PlayerListPanel extends JPanel {

    final private JPanel addPlayerPanel;
    private LinkedList<PlayerPanel> playersArray;
    int nbBots;
    int uniqueID;
    int maxNbPlayers;
    Color basicColors[] = {
        Color.red,
        Color.green,
        Color.blue,
        Color.black,
        Color.orange,
        Color.CYAN
    };
    private LinkedList<Color> colorUsed;
    MenuListener menuListener;

    /**
     * Constructor
     *
     * @param riskModel model
     * @param menuAction mouse action listener for the menu
     */
    public PlayerListPanel(RiskModel riskModel, MenuListener menuAction) {
        //setup PlayersPanel panel
        this.playersArray = new LinkedList<>();
        this.menuListener = menuAction;
        this.setSize(400, 300);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.addPlayerPanel = new JPanel();

        this.nbBots = 0;
        this.uniqueID = 1;
        this.maxNbPlayers = riskModel.getMaxNumberOfPlayers();
        this.colorUsed = new LinkedList<Color>();

        menuAction.setPlayerList(this);
        JButton addPlayerButton = new JButton("+");

        BoxLayout layout = new BoxLayout(addPlayerPanel, BoxLayout.X_AXIS);
        addPlayerPanel.setLayout(layout);
        this.addPlayerPanel.add(addPlayerButton);
        addPlayerButton.addMouseListener(menuAction);

        //initialize players list
        LinkedList<PlayerModel> playerList = riskModel.getPlayerList();

        //add content to panel
        this.add(addPlayerPanel);
        this.initializePlayers(playerList);
        this.setBackground(Color.white);
    }

    /**
     * Initialize the player from the model
     *
     * @param playerList list of players
     */
    protected void initializePlayers(LinkedList<PlayerModel> playerList) {

        for (PlayerModel player : playerList) {
            Color aux = getNewColor();
            this.addElement(player.getName(), aux);

        }
    }

    /**
     * Getter of the colorUsed attribute (list of colors already used)
     *
     * @return the colorUsed
     */
    public LinkedList<Color> getColorUsed() {
        return colorUsed;
    }

    /**
     * Setter of the colorUsed attribute
     *
     * @param colorUsed the colorUsed to set
     */
    public void setColorUsed(LinkedList<Color> colorUsed) {
        this.colorUsed = colorUsed;
    }

    /**
     * Creates a name of a player
     *
     * @return string with a name for a player
     */
    public String getNewPlayerName() {
        Integer nbPlayers = this.getPlayersArray().size() - this.nbBots; //remove 1 because there is the "+" button
        nbPlayers += 1;
        return "Player " + nbPlayers.toString();
    }

    /**
     * Select a new color from the list of options
     *
     * @return color for a player
     */
    public Color getNewColor() {
        for (int i = 0; i < basicColors.length; i++) {
            Color tested = basicColors[i];
            if (!colorUsed.contains(tested)) {
                getColorUsed().add(tested);
                return tested;
            }
        }
        return Color.pink;
    }

    /**
     * Adds and creates a player panel to the main panel and the list of player
     * panels
     *
     * @param playerName name of the player
     * @param color color of the player
     */
    public void addElement(String playerName, Color color) {
        if (this.getPlayersArray().size() == maxNbPlayers) {
            JOptionPane.showMessageDialog(null, "The maximum number of players has been reached.");
            return;
        }

        //create new player element
        PlayerPanel newPlayer = new PlayerPanel();
        newPlayer.setBackground(Color.white);

        //verify name
        if (playerName.equals("")) {
            playerName = "Player " + this.nbBots;
            this.nbBots += 1;

        }

        //add player to the players' list
        newPlayer.setLayout(new FlowLayout());

        //add player's name
        newPlayer.setPlayerNameTextField(new JTextField(10));
        newPlayer.getPlayerNameTextField().setText(playerName);
        newPlayer.add(newPlayer.getPlayerNameTextField());

        //add color button
        newPlayer.setColorButton(new JButton("    "));
        newPlayer.getColorButton().addMouseListener(this.menuListener);

        newPlayer.getColorButton().setBackground(color);
        newPlayer.add(newPlayer.getColorButton());

        //add del button
        newPlayer.setDelButton(new DeletableButton("-", this.uniqueID));
        this.uniqueID += 1;
        newPlayer.getDelButton().addMouseListener(this.menuListener);
        newPlayer.add(newPlayer.getDelButton());
        this.getPlayersArray().add(newPlayer);

        this.add(newPlayer);

        revalidate();
        repaint();

    }

    /**
     * Getter of the playersArray attribute
     *
     * @return the playersArray
     */
    public LinkedList<PlayerPanel> getPlayersArray() {
        return playersArray;
    }

    /**
     * Setter of the playersArray attribute
     *
     * @param playersArray the playersArray to set
     */
    public void setPlayersArray(LinkedList<PlayerPanel> playersArray) {
        this.playersArray = playersArray;
    }

}
