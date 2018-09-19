/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import java.util.LinkedList;

/**
 *
 * @author n_irahol
 */
public class Continent {

    private LinkedList<Country> members;
    private final int bonusScore;
    private String name;

    /**
     * @return the bonusScore
     */
    public int getBonusScore() {
        return bonusScore;
    }

    public Continent(String name, int bonusScore) {
        this.name = name;
        this.bonusScore = bonusScore;
        this.members = new LinkedList<>();
    }

    /**
     * @return the members
     */
    public LinkedList<Country> getMembers() {
        return members;
    }

    /**
     * @param members the members to set
     */
    public void setMembers(LinkedList<Country> members) {
        this.members = members;
    }

    /**
     * @param members the members to set
     */
    public void setMember(Country member) {
        this.members.add(member);
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

}
