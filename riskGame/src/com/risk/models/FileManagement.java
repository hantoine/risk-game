/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedList;
import javax.imageio.ImageIO;

/**
 *
 * @author Nellybett
 */
public class FileManagement {

    public static Board createBoard(String path) {

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
                            System.out.println("lineas leidas" + linesRead);
                            aux = linesRead.split("=", 2);
                            System.out.println("Campo-valor: " + aux[0] + "," + aux[1]);
                            //maybe we could use switch case here ? https://www.geeksforgeeks.org/string-in-switch-case-in-java/

                            if ("author".equals(aux[0])) {
                                System.out.println("Entro");
                                configurationInfo.put(aux[0], aux[1]);
                            } else if ("image".equals(aux[0])) {
                                configurationInfo.put(aux[0], aux[1]);
                                Path imagePath = fileRead.toPath().resolveSibling(aux[1]);
                                try {
                                    BufferedImage image = ImageIO.read(new File(imagePath.toString()));
                                    board.setImage(image);
                                } catch (IOException e) {
                                    System.err.println("Cannot read image file: " + e.getMessage());
                                }
                            } else if (("wrap".equals(aux[0])) && (aux[1].equals("no") || aux[1].equals("yes"))) {
                                configurationInfo.put(aux[0], aux[1]);
                            } else if (("scroll".equals(aux[0])) && (aux[1].equals("horizontal") || aux[1].equals("vertical") || aux[1].equals("none"))) {
                                configurationInfo.put(aux[0], aux[1]);
                            } else if ((aux[0].equals("warn")) && (aux[1].equals("no") || aux[1].equals("yes"))) {
                                configurationInfo.put(aux[0], aux[1]);
                            } else {
                                throw new IOException();
                            }

                            linesRead = dataInput.readLine();
                        }

                        break;
                    case "[Continents]":
                        System.out.println("File" + linesRead);
                        linesRead = dataInput.readLine();
                        while (!linesRead.equals("")) {
                            aux = linesRead.split("=", 2);
                            Continent auxContinent = new Continent(aux[0], Integer.parseInt(aux[1]));
                            System.out.println("Continent" + aux[0]);
                            graphContinents.put(aux[0], auxContinent);
                            linesRead = dataInput.readLine();
                        }
                        break;
                    case "[Territories]":
                        System.out.println("File" + linesRead);
                        linesRead = dataInput.readLine();
                        while (linesRead != null) {
                            if (linesRead.equals("")) {
                                linesRead = dataInput.readLine();
                                continue;
                            }

                            aux = linesRead.split(",");

                            int i = 0;
                            if (aux.length > 4) {
                                
                                Country auxCountry;
                                if(graphTerritories.keySet().contains(aux[0])){
                                    auxCountry=graphTerritories.get(aux[0]);
                                    auxCountry.countrySetter(Integer.parseInt(aux[1]), Integer.parseInt(aux[2]));
                                }else{
                                    auxCountry = new Country(aux[0], Integer.parseInt(aux[1]), Integer.parseInt(aux[2]));
                                }
                                
                                
                                System.out.println("Country-----------" + aux[0]);
                                Country auxCountryAdj;
                                System.out.println("CREO EL PAIS");
                                for (i = 0; i < aux.length - 4; i++) {
                                    if(graphTerritories.keySet().contains(aux[i + 4])){
                                        auxCountryAdj=graphTerritories.get(aux[i + 4]);
                                        
                                    }else{
                                        auxCountryAdj=new Country(aux[i + 4]);
                                        
                                    }
                                    
                                    auxCountry.getAdj().add(auxCountryAdj);
                                    auxCountryAdj.getAdj().add(auxCountry);
                                    System.out.println("Adj" + aux[i + 4]);
                                    
                                }
                                System.out.println("Continente al que pertenece " + aux[3]);
                               
                                System.out.println("CREO las adj");

                                graphContinents.get(aux[3]).setMember(auxCountry);
                                System.out.println("Lo metio en un continente");

                                graphTerritories.put(aux[0], auxCountry);
                                System.out.println("Lo metio en los paises");
                            } else {
                                throw new IOException();
                            }

                            linesRead = dataInput.readLine();

                        }
                        break;
                    default:
                        linesRead = dataInput.readLine();
                        break;
                }

            }

            System.out.println("Board Created");
            board.setConfigurationInfo(configurationInfo);
            board.setGraphTerritories(graphTerritories);
            board.setGraphContinents(graphContinents);
        } catch (FileNotFoundException e) {
            System.err.println("File not Found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Reading Failure: " + e.getMessage());
        }

        return board;
    }

    // New requirement - Discussion
    public static Board generateBoardFile(String path) {
        return null;
    }
}
