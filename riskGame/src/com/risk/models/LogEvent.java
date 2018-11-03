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

    String message;
    boolean clear;

    LogEvent(String message) {
        this.message = message;
    }

    LogEvent(String message, boolean clear) {
        this(message);
        this.clear = true;
    }

    @Override
    public String toString() {
        return message;
    }

    public boolean isClear() {
        return clear;
    }
}
