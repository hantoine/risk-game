/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * It represents a continent
 *
 * @author n_irahol
 */
public class ContinentModel {

    /**
     * members countries that are part of the continent
     */
    private LinkedList<TerritoryModel> members;
    /**
     * bonusScore the number of extra armies per continent
     */
    private int bonusScore;
    /**
     * name the name of the continent
     */
    private String name;

    /**
     * Constructor
     *
     * @param name name of the continent
     * @param bonusScore bonus armies receive when a player conquer the
     * continent
     */
    public ContinentModel(String name, int bonusScore) {
        this.name = name;
        this.bonusScore = bonusScore;
        this.members = new LinkedList<>();
    }

    /**
     * It removes a member from the continent
     *
     * @param member the member which is removed
     */
    public void removeMember(TerritoryModel member) {
        this.members.remove(member);
    }

    /**
     * Getter of the members attribute
     *
     * @return the members
     */
    public LinkedList<TerritoryModel> getMembers() {
        return members;
    }

    /**
     * Setter of the members attribute
     *
     * @param members the members to set
     */
    public void setMembers(LinkedList<TerritoryModel> members) {
        this.members = members;
    }

    /**
     * Adds a member
     *
     * @param member the member to add to members list
     */
    public void setMember(TerritoryModel member) {
        this.members.add(member);
    }

    /**
     * Getter of the bonusScore attribute
     *
     * @return the bonusScore
     */
    public int getBonusScore() {
        return bonusScore;
    }

    /**
     * Setter of the bonusScore attribute
     * @param newBonusScore new value of the bonusScore attribute
     */
    public void setBonusScore(int newBonusScore) {
        this.bonusScore = newBonusScore;
    }

    /**
     * Getter of the name attribute
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter of the name attribute
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Check that this continent is valid, that is to say that it contains
     * territories and that it is a connected graph
     *
     * @return True if this continent is valid
     */
    public boolean check() {
        if (members.isEmpty()) {
            System.out.println("name of fail continent:"+ this.name);
            System.out.println(this.members);
            return false;
        }

        List<String> visitedTerritories = new ArrayList<>(members.size());
        dfsConnected(members.getFirst(), visitedTerritories);
        return visitedTerritories.size() == members.size();
    }

    /**
     * Use Deep First Search algorithm to verify that each territory in the
     * continent is reachable through territories of this continent
     *
     * @param v Territory currently visited
     * @param visitedTerritories List of already visited countries
     */
    private void dfsConnected(TerritoryModel v, List<String> visitedTerritories) {
        visitedTerritories.add(v.getName());

        v.getAdj().stream()
                .filter((c) -> (!(visitedTerritories.contains(c.getName()))))
                .filter((c) -> (c.getContinentName().equals(this.getName())))
                .forEach((c) -> {
                    dfsConnected(c, visitedTerritories);
                });
    }

}
