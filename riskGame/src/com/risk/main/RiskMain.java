/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.main;
import com.risk.controllers.RiskController;
import com.risk.controllers.RiskController_copy;
import com.risk.models.RiskModel;
import com.risk.views.RiskView;
import com.risk.views.StartMenuView;

/**
 *
 * @author n_irahol
 */
public class RiskMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        RiskView riskView=new RiskView();
        RiskModel riskModel=new RiskModel();
        StartMenuView menuView = new StartMenuView(riskModel);
                
        //tim
        //RiskController riskController=new RiskController(riskView, riskModel);  
        if(riskView!=null && menuView!=null && riskModel!=null){
            RiskController_copy riskController  = new RiskController_copy(riskView, menuView, riskModel);
        }
    }
}
