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

    /**
     * Setter of the message
     * @param message 
     */
    LogEvent(String message) {
        this.message = message;
    }

    /**
     * Getter of message
     * @return message
     */
    public String toString() {
        return message;
    }
}
