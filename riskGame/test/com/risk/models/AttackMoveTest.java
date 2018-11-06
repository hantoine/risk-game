/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import java.awt.Color;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 *
 * @author Nellybett
 */
public class AttackMoveTest {
    /**
     * Attack move to test
     */
    AttackMove attack;
    
    /**
     * Preparing the attack move attributes
     */
    @Before
    public void setUp() {
        TerritoryModel source=new TerritoryModel("Venezuela");
        TerritoryModel dest=new TerritoryModel("France");
        source.setNumArmies(7);
        dest.setNumArmies(1);
        attack=new AttackMove(new HumanPlayerModel("Player 1", Color.yellow, null),source, dest);
    }
     
    /**
     * Test of compareDice method, of class AttackMove.
     * Attacker won
     */
    @Test
    public void testCompareDice() {
        System.out.println("compareDice");
        int[] attacker = {3,6};
        int[] defense = {1,2};
        attack.setDiceAttack(2);
        int j = 1;
        int i = 1;
        attack.compareDice(attacker, defense, j, i);
        Assert.assertEquals(0, attack.getDest().getNumArmies());
    }

    /**
     * Test of compareDice method, of class AttackMove.
     * Two dices the attacker won both, we guarantee that armies of dest are always >=0
     */
    @Test
    public void testCompareDice1() {
        System.out.println("compareDice1");
        int[] attacker = {3,6};
        int[] defense = {1,2};
        attack.setDiceAttack(2);
        attack.getDest().setNumArmies(0);
        int j = 1;
        int i = 1;
        attack.compareDice(attacker, defense, j, i);
        Assert.assertEquals(0, attack.getDest().getNumArmies());
    }

    /**
     * Test of compareDice method, of class AttackMove.
     * Defense won
     */
    @Test
    public void testCompareDice2() {
        System.out.println("compareDice2");
        int[] attacker = {3,2};
        int[] defense = {1,4};
        attack.setDiceAttack(2);
        int j = 1;
        int i = 1;
        attack.compareDice(attacker, defense, j, i);
        assertEquals(6, attack.getSource().getNumArmies());
    }
    
    
    /**
     * Test of createDice method, of class AttackMove.
     * Generating an array with 1 number between 1 and 6
     */
    @Test
    public void testCreateDice() {
        System.out.println("createDice");
        int dice = 1;
        int[] result = attack.createDice(dice);
        assertTrue((result[0]<=6 && result[0]>0) && result.length==1);

    }

    /**
     * Test of roolDice method, of class AttackMove.
     */
    @Test
    public void testRoolDice() {
        System.out.println("roolDice");
        int result = attack.roolDice();
        assertTrue((result<=6 && result>0));

    }
    
}
