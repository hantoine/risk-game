/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views;

import com.risk.controllers.MenuListener;
import com.risk.models.Player;
import com.risk.models.RiskModel;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 *
 * @author Nellybett
 */
public class PlayerListPanel extends JPanel{

      
    private JPanel addPlayerPanel;
    private JPanel playersListPanel;
    int nbBots;
    int uniqueID;
    int maxNbPlayers;
    Color basicColors[] = {Color.red, 
                            Color.green, 
                            Color.blue,
                            Color.white, 
                            Color.black, 
                            Color.orange};
    private LinkedList<Color> colorUsed;
    MenuListener menuListener;
    
    public PlayerListPanel(RiskModel riskModel, MenuListener menuAction) {
        //setup PlayersPanel panel
        this.menuListener=menuAction;
        this.setSize(400,300);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.addPlayerPanel = new JPanel();
        this.playersListPanel = new JPanel();
        this.playersListPanel.setLayout(new BoxLayout(getPlayersListPanel(), BoxLayout.Y_AXIS)); 
        this.nbBots=1;
        this.uniqueID=0;
        this.maxNbPlayers = riskModel.getMaxNumberOfPlayers();
        this.colorUsed = new LinkedList<Color>();
        menuAction.setPanel(this);
        JButton addPlayerButton = new JButton("+");
        
        
        BoxLayout layout = new BoxLayout(addPlayerPanel, BoxLayout.X_AXIS);
        addPlayerPanel.setLayout(layout);
        this.addPlayerPanel.add(addPlayerButton);
        
        
        addPlayerButton.addMouseListener(menuAction);
        //content
        //initialize players list
        LinkedList<Player> playerList = riskModel.getPlayerList();
        
        //add content to panel
        this.playersListPanel.add(addPlayerPanel);
        this.initializePlayers(playerList);
        this.add(playersListPanel);
        this.playersListPanel.setBackground(Color.white);
    }
    
    /*
     *
     */
    protected void initializePlayers(LinkedList<Player> playerList){
        for (Player player : playerList) {
            addElement(player.getName(), getNewColor());
        }
    }
    
    /**
     * @return the colorUsed
     */
    public LinkedList<Color> getColorUsed() {
        return colorUsed;
    }

    /**
     * @param colorUsed the colorUsed to set
     */
    public void setColorUsed(LinkedList<Color> colorUsed) {
        this.colorUsed = colorUsed;
    }
    
     /**
     * @return the playersListPanel
     */
    public JPanel getPlayersListPanel() {
        return playersListPanel;
    }

    /**
     * @param playersListPanel the playersListPanel to set
     */
    public void setPlayersListPanel(JPanel playersListPanel) {
        this.playersListPanel = playersListPanel;
    }
    
    /*
     *
     */
    public String getNewPlayerName(){
        Integer nbPlayers = this.getPlayersListPanel().getComponentCount()-1-this.nbBots; //remove 1 because there is the "+" button
        nbPlayers+=1;
        return "Player " + nbPlayers.toString();
    }
    
    /*
     *
     */
    public Color getNewColor(){
        for(int i =0; i< basicColors.length; i++){
            Color tested = basicColors[i];
            if(!colorUsed.contains(tested))
            {
                getColorUsed().add(tested);
                return tested;
            }
        }
        return Color.pink;
    }
    
    /*
     *
     */
    public void addElement(String playerName, Color color) {   
        if(getPlayersListPanel().getComponentCount()-1==maxNbPlayers){
            JOptionPane.showMessageDialog(null, "The maximum number of players has been reached.");
            return;
        }
        
        //create new player element
        JPanel newPlayer = new JPanel();
        newPlayer.setBackground(Color.white);
        
        //verify name
        if(playerName.equals("")){
            playerName = "Bot " + this.nbBots;
            this.nbBots+=1;
        }
        
        //add player to the players' list
        newPlayer.setLayout(new FlowLayout());
        
        //add player's name
        JTextField playerNameTextField = new JTextField(10);
        playerNameTextField.setText(playerName);
        newPlayer.add(playerNameTextField);
        
        //add color button
        JButton colorButton = new JButton("    ");
        colorButton.addMouseListener(this.menuListener);
        
        colorButton.setBackground(color);
        newPlayer.add(colorButton);
        
        //add del button
        DeletableButton delButton = new DeletableButton("-", this.uniqueID);
        this.uniqueID+=1;
        delButton.addMouseListener(this.menuListener);
        newPlayer.add(delButton);
        revalidate();
        repaint();
        
        //finally, add the new player's pane to the player's list
        this.getPlayersListPanel().add(newPlayer,getPlayersListPanel().getComponentCount()-1);
    }
    
    public void addAddPlayerListener(ActionListener addPlayerListener){
	((JButton)addPlayerPanel.getComponent(0)).addActionListener(addPlayerListener);
    }
}
