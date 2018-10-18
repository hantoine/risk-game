/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.observable;

/**
 * Custom interface for the map model being observable.
 *
 * @author timot
 */
public interface MapModelObservable {

    /**
     * Allows to add a new observer.
     *
     * @param newObserver the new observer which is needed
     */
    public void addObserver(MapModelObserver newObserver);

    /**
     * Allows to notify the observers that a change occured in the model.
     *
     * @param updateType the update type of the object
     * @param object object of observer
     */
    public void notifyObservers(UpdateTypes updateType, Object object);
}
