/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import java.nio.file.Paths;

/**
 *
 * @author hantoine
 */
public class MapPath {

    final private String path;

    public MapPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return Paths.get(path).getFileName().toString();
    }

    public String getPath() {
        return path;
    }
}
