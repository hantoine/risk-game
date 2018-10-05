/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.mapeditor;

/**
 *
 * @author timot
 */
public class MapConfig {
    boolean wrap;
    boolean warn;
    String scroll;
    String author;
    
    public MapConfig(){
        this.author="new author";
        this.scroll="";
        this.warn=true;
        this.wrap=true;
    }

    public boolean isWrap() {
        return wrap;
    }

    public boolean isWarn() {
        return warn;
    }

    public String getScroll() {
        return scroll;
    }

    public String getAuthor() {
        return author;
    }

    public void setWrap(boolean wrap) {
        this.wrap = wrap;
    }

    public void setWarn(boolean warn) {
        this.warn = warn;
    }

    public void setScroll(String scroll) {
        this.scroll = scroll;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    
    
}
