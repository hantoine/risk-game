/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Object that writes the logs to a file.
 *
 * @author user
 */
public class LogWriter implements Observer {

    /**
     * Path to the file where the log will be written.
     */
    String pathToFile;

    /**
     * Object which writes on the file currently open.
     */
    BufferedWriter out;

    /**
     * Constructor creates a log file.
     */
    public LogWriter() {
        this.pathToFile = Paths.get("logs", ZonedDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME)+ ".txt").toString();
        out = null;
    }

    /**
     * Open the log file.
     */
    public void openFile() {
        try {
            this.out = new BufferedWriter(
                    new FileWriter(this.pathToFile, true)); //true implies the "append" mode
        } catch (IOException ex) {
            Logger.getLogger(LogWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Add the new log to the log file.
     *
     * @param o Class of the object sending the log. Here it is the risk model.
     * @param o1 Log to be written
     */
    @Override
    public void update(Observable o, Object o1) {
        if (this.out != null) { //if file open
            if (o1 instanceof LogEvent) { //and the object is a log
                LogEvent le = (LogEvent) o1; 
                if (!le.isClear()) //if the log is not empty, 
                    try {
                        out.write(le + "\n"); //write the log
                        out.flush();
                    } catch (IOException ex) {
                        //else print exception;
                        Logger.getLogger(LogWriter.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
        }
    }

    /**
     * Close the file currently opened.
     */
    @Override
    public void finalize() {
        if (this.out != null) {
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(LogWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
