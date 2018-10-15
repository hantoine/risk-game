/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.observable;

/**
 * Custom interface for a component to be an observer.
 * @author timot
 */
public interface MapModelObserver {

    /**
     * Method to update the observer.
     * @param updateType Type of update to do.
     * @param object Object containing the data needed to update the view.
     */
    public void update(UpdateTypes updateType, Object object);
}
