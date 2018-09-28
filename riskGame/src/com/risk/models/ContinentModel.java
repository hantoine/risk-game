/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import java.util.LinkedList;

/**
 * It represents a continent
 * @author n_irahol
 */
public class ContinentModel {

    private LinkedList<TerritoryModel> members;
    private final int bonusScore;
    private String name;

    /**
     * Constructor
     * @param name name of the continent
     * @param bonusScore bonus armies receive when a player conquer the continent
     */
    public ContinentModel(String name, int bonusScore) {
        this.name = name;
        this.bonusScore = bonusScore;
        this.members = new LinkedList<>();
    }

    /**
     * Getter of the members attribute
     * @return the members
     */
    public LinkedList<TerritoryModel> getMembers() {
        return members;
    }

    /**
     * Setter of the members attribute
     * @param members the members to set
     */
    public void setMembers(LinkedList<TerritoryModel> members) {
        this.members = members;
    }

    /**
     * Adds a member
     * @param member the member to add to members list
     */
    public void setMember(TerritoryModel member) {
        this.members.add(member);
    }

    /**
     * Getter of the bonusScore attribute
     * @return the bonusScore
     */
    public int getBonusScore() {
        return bonusScore;
    }
    
    /**
     * Getter of the name attribute
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter of the name attribute
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

}
