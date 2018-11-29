/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import java.io.Serializable;
import java.util.Objects;

/**
 * Class that contains all the configuration parameters for the map.
 *
 * @author timot
 */
public class MapConfig  implements Serializable  {

   

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
     *
     * @return the wrap parameter.
     */
    public boolean isWrap() {
        return wrap;
    }

    /**
     * Getter on the warn parameter.
     *
     * @return the warn parameter.
     */
    public boolean isWarn() {
        return warn;
    }

    /**
     * Getter on the scroll parameter.
     *
     * @return the scroll parameter.
     */
    public String getScroll() {
        return scroll;
    }

    /**
     * Getter on the author parameter.
     *
     * @return the name of the author.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Getter for the background image path.
     *
     * @return a path to the image.
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * Setter on the wrap parameter.
     *
     * @param wrap new wrap value
     */
    void setWrap(boolean wrap) {
        this.wrap = wrap;
    }

    /**
     * Setter on the warn parameter.
     *
     * @param warn new warn value
     */
    void setWarn(boolean warn) {
        this.warn = warn;
    }

    /**
     * Setter on the scroll parameter.
     *
     * @param scroll scroll of the map
     */
    void setScroll(String scroll) {
        this.scroll = scroll;
    }

    /**
     * *
     * Setter of the author.
     *
     * @param author new author
     */
    void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Setter of the image path.
     *
     * @param imagePath new path of the image
     */
    void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    
     /**
     * Alias for the equal function in order to test equality between two objects of the same class.
     * @param obj object of the same class we want to compare to this instance.
     * @return boolean to know if the objects are equal or not.
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
        final MapConfig other = (MapConfig) obj;
        if (this.wrap != other.wrap) {
            return false;
        }
        if (this.warn != other.warn) {
            return false;
        }
        if (!Objects.equals(this.scroll, other.scroll)) {
            return false;
        }
        if (!Objects.equals(this.author, other.author)) {
            return false;
        }
        if (!Objects.equals(this.imagePath, other.imagePath)) {
            return false;
        }
        return true;
    }
}
