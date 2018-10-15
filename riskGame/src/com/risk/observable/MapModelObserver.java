/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.observable;

/**
 *
 * @author timot
 */
public interface MapModelObserver {

    public void update(UpdateTypes updateType, Object object);
}
