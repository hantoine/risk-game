/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

/**
 * Class that contains all the configuration parameters for the map.
 * @author timot
 */
public class MapConfig {

    /**
     * Wrap parameter of the map.
     */
    boolean wrap;
    
    /**
     * Warn parameter of the map.
     */
    boolean warn;
    
    /**
     * Scroll parameter of the map.
     */
    String scroll;
    
    /**
     * Author of the map.
     */
    String author;
    
    /**
     * Path to the background image of the map
     */
    String imagePath;

    /**
     * Constructor.
     */
    public MapConfig() {
        this.author = "new author";
        this.scroll = "none";
        this.warn = false;
        this.wrap = false;
    }

    /**
     * Getter on the wrap parameter.
     * @return the wrap parameter.
     */
    public boolean isWrap() {
        return wrap;
    }

    /**
     * Getter on the warn parameter.
     * @return the warn parameter.
     */
    public boolean isWarn() {
        return warn;
    }

    /**
     * Getter on the scroll parameter.
     * @return the scroll parameter.
     */
    public String getScroll() {
        return scroll;
    }

    /**
     * Getter on the author parameter.
     * @return the name of the author.
     */
    public String getAuthor() {
        return author;
    }
    
    /**
     * Getter for the background image path.
     * @return a path to the image.
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * Setter on the wrap parameter.
     * @param wrap new wrap value
     */
    public void setWrap(boolean wrap) {
        this.wrap = wrap;
    }

    /**
     * Setter on the warn parameter.
     * @param warn new warn value
     */
    public void setWarn(boolean warn) {
        this.warn = warn;
    }

    /**
     * Setter on the scroll parameter.
     * @param scroll scroll of the map
     */
    public void setScroll(String scroll) {
        this.scroll = scroll;
    }

    /***
     * Setter of the author.
     * @param author new author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Setter of the image path.
     * @param imagePath new path of the image
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
