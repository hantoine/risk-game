/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

/**
 *
 * @author Nellybett
 */
public class RiskModel {

 
    Board board;

    public RiskModel() {
        board=new Board();
    }
    
       public Board getBoard() {
        return board;
    }

}
