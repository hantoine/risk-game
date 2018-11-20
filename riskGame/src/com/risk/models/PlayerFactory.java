/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import java.awt.Color;

/**
 *
 * @author Nellybett
 */
public class PlayerFactory {

    private PlayerFactory() {
    }
    
    static public PlayerModel getPlayer(String typeOfPlayer, String name, Color color, RiskModel rm){
        PlayerModel currentPlayer;
        if(typeOfPlayer.equals("HUMAN")){
             currentPlayer=new HumanPlayerModel(name,color,rm);
        }else{
            currentPlayer=new AIPlayerModel(name,color,rm);
            Strategy strategy=null;
            switch(typeOfPlayer){
                case "BENEVOLENT":
                    strategy=new Benevolent();
                    break;
                case "AGGRESSIVE":
                    strategy=new Aggressive();
                    break;
                case "CHEATER":
                    strategy=new Cheater();
                    break;
                case "RANDOM":
                    strategy=new Random();
                    break;
            }
            ((AIPlayerModel) currentPlayer).setStrategy(strategy);
        }
     
        return currentPlayer;
    }
    
}
