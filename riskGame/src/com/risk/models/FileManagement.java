/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import com.risk.models.exceptions.FormatException;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import javax.imageio.ImageIO;

/**
 *
 * @author Nellybett
 */
public class FileManagement {

    public static Board createBoard(String path) throws FormatException, IOException {

        Board board = new Board();
        HashMap<String, String> configurationInfo = new HashMap();
        HashMap<String, Continent> graphContinents = new HashMap();
        HashMap<String, Country> graphTerritories = new HashMap();

        File fileRead = new File(path);

        //Reading the information
        //Use BufferedRead and FileReader
        try (BufferedReader dataInput = new BufferedReader(new FileReader(fileRead))) {
            String linesRead;
            linesRead = dataInput.readLine();

            while (linesRead != null) {
                String[] aux;

                switch (linesRead) {

                    case "[Map]":
                        linesRead = dataInput.readLine();
                        while (!linesRead.equals("")) {
                           
                            aux = linesRead.split("=", 2);
                            
                            switch(aux[0]){
                                case "author":
                                    configurationInfo.put(aux[0], aux[1]);
                                    break;
                                case "image":
                                    configurationInfo.put(aux[0], aux[1]);
                                    Path imagePath = fileRead.toPath().resolveSibling(aux[1]);
                                    try {
                                        BufferedImage image = ImageIO.read(new File(imagePath.toString()));
                                        board.setImage(image);
                                    } catch (FileNotFoundException e) {
                                        throw new FileNotFoundException("Image not found");
                                    } catch (IOException e) {
                                        throw new IOException("Image error");
                                    }
                                    break;
                                case "wrap":
                                    if((aux[1].equals("no") || aux[1].equals("yes"))) {
                                        configurationInfo.put(aux[0], aux[1]);
                                    }else{
                                        throw new FormatException("Parameter wrap not valid");
                                    }
                                    break;
                                case "scroll":
                                    if((aux[1].equals("horizontal") || aux[1].equals("vertical") || aux[1].equals("none"))) {
                                        configurationInfo.put(aux[0], aux[1]);
                                    }else{
                                        throw new FormatException("Parameter scroll not valid");
                                    }
                                    break;
                                case "warn":
                                    if((aux[1].equals("no") || aux[1].equals("yes"))) {
                                        configurationInfo.put(aux[0], aux[1]);
                                    }else{
                                        throw new FormatException("Parameter warn not valid");
                                    }
                                    break;
                                default:
                                    throw new FormatException("File format error, parameter not known");
                                    
                            }                                


                            linesRead = dataInput.readLine();
                        }

                        break;
                    case "[Continents]":

                        linesRead = dataInput.readLine();
                        while (!linesRead.equals("")) {
                            aux = linesRead.split("=", 2);
                            if(aux.length>1){
                                Continent auxContinent = new Continent(aux[0], Integer.parseInt(aux[1]));
                                graphContinents.put(aux[0], auxContinent);
                            
                            }else{
                                throw new FormatException("Continent without correct delimiter");
                            }
                            linesRead = dataInput.readLine();
                        }
                        break;
                    case "[Territories]":

                        linesRead = dataInput.readLine();

                        //Reads until the end of the file
                        while (linesRead != null) {

                            //In case of space between countries
                            if (linesRead.equals("")) {
                                linesRead = dataInput.readLine();
                                continue;
                            }

                            //Splits the country information
                            aux = linesRead.split(",");

                            int i = 0;
                            //The information has to be bigger first country second and third position 4th continent
                            if (aux.length > 4) {
                                // Creates de Country in the file
                                Country auxCountry;
                                if (graphTerritories.keySet().contains(aux[0])) {
                                    auxCountry = graphTerritories.get(aux[0]);
                                    auxCountry.countrySetter(Integer.parseInt(aux[1]), Integer.parseInt(aux[2]));
                                } else {
                                    auxCountry = new Country(aux[0], Integer.parseInt(aux[1]), Integer.parseInt(aux[2]));
                                }

                                //Creates adj country
                                Country auxCountryAdj;

                                for (i = 0; i < aux.length - 4; i++) {
                                    if (graphTerritories.keySet().contains(aux[i + 4])) {
                                        auxCountryAdj = graphTerritories.get(aux[i + 4]);

                                    } else {
                                        auxCountryAdj = new Country(aux[i + 4]);

                                    }
                                    //Adds the adj
                                    auxCountry.getAdj().add(auxCountryAdj);
                                    graphTerritories.put(aux[i+4], auxCountryAdj);

                                }

                                graphContinents.get(aux[3]).setMember(auxCountry);
                                graphTerritories.put(aux[0], auxCountry);

                            } else {
                                throw new FormatException("Country without adjacencies");
                            }

                            linesRead = dataInput.readLine();

                        }
                        break;
                    default:
                        throw new FormatException("File Format not valid");
                }
                linesRead = dataInput.readLine();
            }

            board.setConfigurationInfo(configurationInfo);
            board.setGraphTerritories(graphTerritories);
            board.setGraphContinents(graphContinents);
            System.out.println("Board Created");
        
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File not found");
        } catch (IOException e) {
            throw new IOException("File reading failed");
        }
        //board.printBoard();
        System.out.println("IS CONNECTED? " + board.connectedGraph());
        return board;
    }

    // New requirement - Discussion
    public static Board generateBoardFile(String path) {
        return null;
    }
}
