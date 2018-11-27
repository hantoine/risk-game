/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import java.io.Serializable;

/**
 *
 * @author hantoine
 */
public class LogModel implements Serializable {

    String content;

    public LogModel() {
        this.content = "";
    }

    public String getContent() {
        return content;
    }

    void addLogEntry(String msg) {
        if (!content.equals("")) {
            content += "\n";
        }
        content += msg;
    }

    void clear() {
        content = "";
    }
}
