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
    int numberMembers;
    private String name;

    public Continent(String name, int numberMembers) {
        this.name=name;
        this.numberMembers=numberMembers;
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
