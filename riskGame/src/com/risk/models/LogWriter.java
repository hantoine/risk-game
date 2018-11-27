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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Object that writes the logs to a file.
 *
 * @author user
 */
public class LogWriter {

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
        this.pathToFile = Paths.get("logs",
                ZonedDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME)
                + ".log").toString();
        out = null;
    }

    /**
     * Constructor creates a log file with the given file name
     *
     * @param filename Name of the log file to use
     */
    public LogWriter(String filename) {
        this.pathToFile = Paths.get("logs", filename).toString();
        out = null;
    }

    /**
     * Open the log file.
     */
    public void openFile() {
        try {
            // true implies the "append" mode
            this.out = new BufferedWriter(
                    new FileWriter(this.pathToFile, true));
        } catch (IOException ex) {
            Logger.getLogger(LogWriter.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Add the new log to the log file.
     *
     * @param msg Log message to write to the file
     */
    public void log(String msg) {
        if (msg.isEmpty() || out == null) {
            return;
        }

        try {
            out.write(msg + "\n"); //write the log
            out.flush();
        } catch (IOException ex) {
            //else print exception;
            Logger.getLogger(LogWriter.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Close the file currently opened.
     *
     * @throws Throwable Throwable exception
     */
    @Override
    public void finalize() throws Throwable {
        try {
            this.close();
        } finally {
            super.finalize();
        }
    }

    /**
     * Close the file currently opened.
     */
    public void close() {
        if (this.out != null) {
            try {
                out.close();
                out = null;
            } catch (IOException ex) {
                Logger.getLogger(LogWriter.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
    }
}
