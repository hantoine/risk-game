/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import java.io.Serializable;
import java.nio.file.Paths;

/**
 * This class represents map path
 *
 * @author hantoine
 */
public class MapPath implements Serializable {

    /**
     * Map path
     */
    final private String path;

    /**
     * Constructor
     *
     * @param path map path
     */
    public MapPath(String path) {
        this.path = path;
    }

    /**
     * Getter of path
     *
     * @return path
     */
    public String getPath() {
        return path;
    }

    /**
     * Get the map path plus the map name
     *
     * @return map path and map name
     */
    @Override
    public String toString() {
        return Paths.get(path).getFileName()
                .toString().replaceFirst("[.][^.]+$", "");
    }

}
