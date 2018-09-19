/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.views;

import com.risk.controllers.RiskController_copy;
import com.risk.models.Player;
import com.risk.models.RiskModel;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    LinkedList<Color> colorUsed;
    
    public PlayerListPanel(RiskModel riskModel) {
        //setup PlayersPanel panel
        this.setSize(400,300);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.addPlayerPanel = new JPanel();
        this.playersListPanel = new JPanel();
        this.playersListPanel.setLayout(new BoxLayout(playersListPanel, BoxLayout.Y_AXIS)); 
        this.nbBots=1;
        this.uniqueID=0;
        this.maxNbPlayers = riskModel.getMaxNumberOfPlayers();
        this.colorUsed = new LinkedList<Color>();
        
        JButton addPlayerButton = new JButton("+");
        addPlayerButton.addActionListener(new ActionListener()
        {
          @Override
          public void actionPerformed(ActionEvent e)
          {
            addElement(getNewPlayerName(), getNewColor());
          }
        });
        
        BoxLayout layout = new BoxLayout(addPlayerPanel, BoxLayout.X_AXIS);
        addPlayerPanel.setLayout(layout);
        this.addPlayerPanel.add(addPlayerButton);
        
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
    
    /*
     *
     */
    protected String getNewPlayerName(){
        Integer nbPlayers = this.playersListPanel.getComponentCount()-1-this.nbBots; //remove 1 because there is the "+" button
        nbPlayers+=1;
        return "Player " + nbPlayers.toString();
    }
    
    /*
     *
     */
    private Color getNewColor(){
        for(int i =0; i< basicColors.length; i++){
            Color tested = basicColors[i];
            if(!colorUsed.contains(tested))
            {
                colorUsed.add(tested);
                return tested;
            }
        }
        return Color.pink;
    }
    
    /*
     *
     */
    public void addElement(String playerName, Color color) {   
        if(playersListPanel.getComponentCount()-1==maxNbPlayers){
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
        colorButton.addActionListener(new ActionListener()
        {
          @Override
          public void actionPerformed(ActionEvent e)
          {
            Color selectedColor = JColorChooser.showDialog(null, "Choose a color", Color.RED);
            
            if(colorUsed.contains(selectedColor))
                JOptionPane.showMessageDialog(null, "This color is already used");
            else
                colorButton.setBackground(selectedColor);
          }
        });
        colorButton.setBackground(color);
        newPlayer.add(colorButton);
        
        //add del button
        DeletableButton delButton = new DeletableButton("-", this.uniqueID);
        this.uniqueID+=1;
        delButton.addActionListener(new ActionListener()
        {
          @Override
          public void actionPerformed(ActionEvent e)
          {
            if(playersListPanel.getComponentCount()-1<=3)
                JOptionPane.showMessageDialog(null, "You need at least three players to play the game.");
            else{
                int IDtoDelete = delButton.getID();
                int nbPlayers = playersListPanel.getComponentCount()-1;
                for(int i =0; i< nbPlayers; i++){
                    JPanel panelToTest = (JPanel)playersListPanel.getComponent(i);
                    DeletableButton delButtonToTest = (DeletableButton)panelToTest.getComponent(2);
                    if(delButtonToTest.getID() == IDtoDelete){
                        Color colorToRemove = ((JButton)panelToTest.getComponent(1)).getBackground();
                        colorUsed.remove(colorToRemove);
                        playersListPanel.remove(i);
                        revalidate();
                        repaint();
                        return;
                    }
                }
                JOptionPane.showMessageDialog(null, "Oops... Something went wrong.");
            }
          }
        });
        newPlayer.add(delButton);
        revalidate();
        repaint();
        
        //finally, add the new player's pane to the player's list
        this.playersListPanel.add(newPlayer,playersListPanel.getComponentCount()-1);
    }
    /*
    void addAddPlayerListener(ActionListener listenForCalcButton){
	playersListPanel.addActionListener(listenForCalcButton);
    }
    
    void addRemovePlayerListener(ActionListener listenForCalcButton){
	playersListPanel.addActionListener(listenForCalcButton);
    }*/
}
