/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

/**
 *
 * @author hantoine
 */
public class LogEvent {
    /**
     * The message
     */
    String message;
    boolean clear;

    /**
     * Constructor
     * @param message 
     */
    LogEvent(String message) {
        this.message = message;
    }
    
    /**
     * Constructor
     * @param message
     * @param clear
     */
    LogEvent(String message, boolean clear) {
        this(message);
        this.clear = true;
    }

    /**
    * Getter of message
    * @return message
    */
    @Override
    public String toString() {
        return message;
    }

    /**
    * Getter of clear
    * @return clear
    */
    public boolean isClear() {
        return clear;
    }
}
