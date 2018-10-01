/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.observers;

/**
 *
 * @author timot
 */
public interface MapModelObservable {
    public void addObserver(MapModelObserver newObserver);
    public void removeObserver();
    public void notifyObserver(int posX, int posY, String newName);
}
