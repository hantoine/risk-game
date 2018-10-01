/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.observers;

import com.risk.mapeditor.UpdateTypes;

/**
 *
 * @author timot
 */
public interface MapModelObservable {
    public void addObserver(MapModelObserver newObserver);
    public void notifyObserver(UpdateTypes updateType, Object object);
}
