/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.main;

import com.risk.models.MapFileManagementTest;
import com.risk.models.interfaces.PlayerModelTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Suite for the group of test Classes
 * @author Nellybett
 */
@RunWith(Suite.class)
@SuiteClasses({MapFileManagementTest.class, PlayerModelTest.class})
public class JUnitTestSuite {   
}  