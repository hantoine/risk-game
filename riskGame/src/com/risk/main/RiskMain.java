/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.main;

import com.risk.controllers.RiskController;

import com.risk.models.RiskModel;
import com.risk.views.RiskView;

/**
 *
 * @author n_irahol
 */
public class RiskMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        RiskView riskView = new RiskView();
        RiskModel riskModel = new RiskModel();
        RiskController riskController = new RiskController(riskView, riskModel);
    }
}
