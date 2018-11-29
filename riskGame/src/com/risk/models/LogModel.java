/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import java.io.Serializable;
import java.util.Objects;

/**
 * This class represents the log of the game
 *
 * @author hantoine
 */
public class LogModel implements Serializable {

    /**
     * Alias for the equal function in order to test equality between two objects of the same class.
     * @param obj object of the same class we want to compare to this instance.
     * @return boolean to know if the objects are equal or not
     */
    public boolean identical(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LogModel other = (LogModel) obj;
        if (!Objects.equals(this.content, other.content)) {
            return false;
        }
        return true;
    }

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
