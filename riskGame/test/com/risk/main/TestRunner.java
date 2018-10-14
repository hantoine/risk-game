/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.main;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Runner of the Suite of test cases
 *
 * @author Nellybett
 */
public class TestRunner {

    public static void main(String[] args) {
        Result result;
        result = JUnitCore.runClasses(JUnitTestSuite.class);
        for (Failure f : result.getFailures()) {
            System.out.println(f.toString());
        }
        System.out.println("Success: " + result.wasSuccessful());
    }
}
