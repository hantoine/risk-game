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

    LogEvent(String message) {
        this.message = message;
    }

    public String toString() {
        return message;
    }
}
