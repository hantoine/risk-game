/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import java.awt.Color;
import java.util.LinkedList;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Class to test player model class
 *
 * @author Nellybett
 */
public class PlayerModelTest {

    /**
     * player reference to an instance of the class to be tested
     */
    PlayerModel player;

    /**
     * Constructor
     */
    public PlayerModelTest() {
    }

    /**
     * Setting the player attributes
     */
    @Before
    public void setUp() {
        player = new PlayerModel("Player 1", Color.yellow);
        LinkedList<TerritoryModel> territories = new LinkedList<>();
        LinkedList<ContinentModel> continents = new LinkedList<>();
        TerritoryModel source = new TerritoryModel("Venezuela");
        TerritoryModel dest = new TerritoryModel("France");

        territories.add(source);
        territories.add(dest);
        territories.add(new TerritoryModel("Peru"));
        territories.add(new TerritoryModel("Bolivia"));
        territories.add(new TerritoryModel("Canada"));
        territories.add(new TerritoryModel("USA"));
        territories.add(new TerritoryModel("Ecuador"));

        player.setContinentsOwned(continents);
        player.setContriesOwned(territories);

        source.setNumArmies(7);
        dest.setNumArmies(1);
        AttackMove attack = new AttackMove(player, source, dest);
        player.setCurrentAttack(attack);

    }

    /**
     * Test of armiesAssignation method, of class PlayerModel.
     */
    @Test
    public void testArmiesAssignationMinimum() {

        int expResult = 3;
        int result = player.armiesAssignation();
        assertEquals(expResult, result);
    }

    /**
     * Test of armiesAssignation method, of class PlayerModel.
     */
    @Test
    public void testArmiesAssignationWithMoreTerritories() {

        player.addTerritoryOwned(new TerritoryModel("France"));
        player.addTerritoryOwned(new TerritoryModel("Germany"));
        player.addTerritoryOwned(new TerritoryModel("Italy"));
        player.addTerritoryOwned(new TerritoryModel("UK"));
        player.addTerritoryOwned(new TerritoryModel("Spain"));

        int expResult = 4;
        int result = player.armiesAssignation();
        assertEquals(expResult, result);
    }

    /**
     * Test of armiesAssignation method, of class PlayerModel.
     */
    @Test
    public void testArmiesAssignationWithContinents() {

        LinkedList<ContinentModel> continents = new LinkedList<>();
        continents.add(new ContinentModel("South America", 4));
        continents.add(new ContinentModel("North America", 3));
        player.setContinentsOwned(continents);

        int expResult = 9;
        int result = player.armiesAssignation();
        assertEquals(expResult, result);
    }

    /**
     * Test of armiesCardAssignation method, of class PlayerModel.
     */
    @Test
    public void testArmiesAssignationCards() {

        //returned cards > 18
        player.setReturnedCards(27);
        int expResult = 35;
        int result = player.armiesAssignationCards();
        assertEquals(expResult, result);

    }

    /**
     * Test of armiesCardAssignation method, of class PlayerModel.
     */
    @Test
    public void testArmiesAssignationCardsWithoutReturnedCards() {

        //First time returnig cards =0
        player.setReturnedCards(0);
        int expResult = 4;
        int result = player.armiesAssignationCards();
        assertEquals(expResult, result);

    }

    /**
     * Test the movement of armies after the conquer Value that is less than the
     * dice selected
     */
    @Test
    public void testConquerTerritory() {
        player.getCurrentAttack().setNbDiceAttack(2);
        int result = player.conquerTerritory(1);
        assertEquals(-1, result);
    }

    /**
     * Test the movement of armies after the conquer Good value of armies to
     * move
     */
    @Test
    public void testConquerTerritory1() {
        player.getCurrentAttack().setNbDiceAttack(2);
        int result = player.conquerTerritory(3);
        assertEquals(0, result);
    }

    /**
     * Test the movement of armies after the conquer Number of armies to move
     * more or equal than source number of armies
     */
    @Test
    public void testConquerTerritory2() {
        player.getCurrentAttack().setNbDiceAttack(2);
        int result = player.conquerTerritory(7);
        assertEquals(-1, result);
    }

    /**
     * Test the validity of an attack movement before performing it aux is not
     * adj
     */
    @Test
    public void testValidateAttack() {

        TerritoryModel aux = new TerritoryModel("Germany");
        aux.setNumArmies(1);

        int result = player.validateAttack(player.getCurrentAttack().getSource(), aux);
        assertEquals(-1, result);

    }

    /**
     * Test the validity of an attack movement before performing it Source is
     * not owned by the attack player
     */
    @Test
    public void testValidateAttack1() {

        TerritoryModel source = new TerritoryModel("Germany");
        TerritoryModel dest = new TerritoryModel("Venezuela");
        source.setNumArmies(3);

        player.setCurrentAttack(null);
        source.addAdjacentTerritory(dest);

        int result = player.validateAttack(source, dest);
        assertEquals(-3, result);
    }

    /**
     * Test the validity of an attack movement before performing it Destiny is
     * owned by the attack player
     */
    @Test
    public void testValidateAttack2() {
        TerritoryModel source = new TerritoryModel("Ecuador");
        TerritoryModel dest = new TerritoryModel("Holanda");

        player.setCurrentAttack(null);
        source.addAdjacentTerritory(dest);

        player.addTerritoryOwned(source);
        player.addTerritoryOwned(dest);

        int result = player.validateAttack(source, dest);
        assertEquals(-3, result);
    }

    /**
     * Test the validity of an attack movement before performing it Source
     * armies are less than 2
     */
    @Test
    public void testValidateAttack3() {

        TerritoryModel aux = new TerritoryModel("Germany");
        aux.setNumArmies(1);
        TerritoryModel source = new TerritoryModel("Venezuel");
        player.setCurrentAttack(null);
        player.addTerritoryOwned(source);
        source.addAdjacentTerritory(aux);
        source.setNumArmies(1);
        int result = player.validateAttack(source, aux);
        assertEquals(-4, result);
    }

    /**
     * Test getNbInitialArmies with 2 players
     */
    @Test
    public void testGetNbInitialArmies2() {
        assertEquals(
                40,
                PlayerModel.getNbInitialArmies(2)
        );
    }

    /**
     * Test getNbInitialArmies with 3 players
     */
    @Test
    public void testGetNbInitialArmies3() {
        assertEquals(
                35,
                PlayerModel.getNbInitialArmies(3)
        );
    }

    /**
     * Test getNbInitialArmies with 4 players
     */
    @Test
    public void testGetNbInitialArmies4() {
        assertEquals(
                30,
                PlayerModel.getNbInitialArmies(4)
        );
    }

    /**
     * Test getNbInitialArmies with 5 players
     */
    @Test
    public void testGetNbInitialArmies5() {
        assertEquals(
                25,
                PlayerModel.getNbInitialArmies(5)
        );
    }

    /**
     * Test getNbInitialArmies with 6 players
     */
    @Test
    public void testGetNbInitialArmies6() {
        assertEquals(
                20,
                PlayerModel.getNbInitialArmies(6)
        );
    }

    /**
     * Test getNbInitialArmies with 7 players
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetNbInitialArmies7() {
        PlayerModel.getNbInitialArmies(7);
    }
}
