/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import java.io.Serializable;

/**
 * This class represents the log of the game
 *
 * @author hantoine
 */
public class LogModel implements Serializable {

    /**
     * Log content
     */
    String content;

    /**
     * Constructor
     */
    public LogModel() {
        this.content = "";
    }

    /**
     * Getter of content
     *
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * Add log
     *
     * @param msg log entry
     */
    void addLogEntry(String msg) {
        if (!content.equals("")) {
            content += "\n";
        }
        content += msg;
    }

    /**
     * Remove the log content
     */
    void clear() {
        content = "";
    }
}
