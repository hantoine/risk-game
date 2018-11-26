/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

/**
 * Log event displayed in the phase view current action box
 *
 * @author hantoine
 */
public class LogEvent  {

    /**
     * The message
     */
    String message;
    boolean clear;

    /**
     * Constructor
     *
     * @param message the message which is shown
     */
    LogEvent(String message) {
        this.message = message;
    }

    /**
     * Constructor
     *
     * @param message the message
     * @param clear the message which is shown
     */
    LogEvent(String message, boolean clear) {
        this(message);
        this.clear = clear;
    }

    /**
     * Getter of message
     *
     * @return message
     */
    @Override
    public String toString() {
        return message;
    }

    /**
     * Getter of clear
     *
     * @return clear
     */
    public boolean isClear() {
        return clear;
    }
}
