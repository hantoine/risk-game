/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.observers;

/**
 * Custom interface for the map model being observable.
 * @author timot
 */
public interface MapModelObservable {

    /**
     * Allows to add a new observer.
     * @param newObserver 
     */
    public void addObserver(MapModelObserver newObserver);

    /**
     * Allows to notify the observers that a change occured in the model.
     * @param updateType
     * @param object 
     */
    public void notifyObservers(UpdateTypes updateType, Object object);
}
