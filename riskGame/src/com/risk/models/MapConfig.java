/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

/**
 *
 * @author timot
 */
public class MapConfig {

    /**
     * wrap
     * warn
     * scroll
     * author
     * imagePath
     */
    boolean wrap;
    boolean warn;
    String scroll;
    String author;
    String imagePath;

    /**
     * 
     */
    public MapConfig() {
        this.author = "new author";
        this.scroll = "";
        this.warn = true;
        this.wrap = true;
    }

    /**
     * 
     * @return 
     */
    public boolean isWrap() {
        return wrap;
    }

    /**
     * 
     * @return 
     */
    public boolean isWarn() {
        return warn;
    }

    /**
     * 
     * @return 
     */
    public String getScroll() {
        return scroll;
    }

    /**
     * 
     * @return 
     */
    public String getAuthor() {
        return author;
    }

    /**
     * 
     * @param wrap 
     */
    public void setWrap(boolean wrap) {
        this.wrap = wrap;
    }

    /**
     * 
     * @param warn 
     */
    public void setWarn(boolean warn) {
        this.warn = warn;
    }

    /**
     * 
     * @param scroll 
     */
    public void setScroll(String scroll) {
        this.scroll = scroll;
    }

    /**
     * 
     * @param author 
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * 
     * @return 
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * 
     * @param imagePath 
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
