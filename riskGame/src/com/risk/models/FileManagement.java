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
import java.util.HashMap;
import java.util.LinkedList;
import javax.imageio.ImageIO;

/**
 *
 * @author Nellybett
 */
public class FileManagement {
    
    public static Board createBoard(String path){
        
        Board board=new Board();
        HashMap<String,String> configurationInfo=new HashMap();
        HashMap<String,Continent> graphContinents=new HashMap();
        HashMap<String,Country> graphTerritories=new HashMap();
        
        try{  
            File fileRead=new File(path);
  
            //Use BufferedRead and FileReader
            BufferedReader dataInput=new BufferedReader(new FileReader(fileRead));
        
            //Reading the information
            String linesRead;
            linesRead=dataInput.readLine();
            
            while(linesRead!=null){
                String[] aux;
                
                switch (linesRead) {
                    
                    case "[Map]":
                        linesRead=dataInput.readLine();
                        while(!linesRead.equals("")){                        
                        System.out.println("lineas leidas" + linesRead);
                            aux=null;
                            aux=linesRead.split("=",2);
                            System.out.println("Campo-valor: "+aux[0]+","+aux[1]);
                            if("author".equals(aux[0])){
                                System.out.println("Entro");
                                configurationInfo.put(aux[0], aux[1]);
                                
                            }else if("image".equals(aux[0])){
                                int pathPos=0;
                                configurationInfo.put(aux[0], aux[1]);
                                pathPos=path.lastIndexOf("\\");
                                BufferedImage image= ImageIO.read(new File(path.substring(0, pathPos)+"\\"+aux[1]));
                                board.setImage(image);
                            
                            }else if(("wrap".equals(aux[0])) && (aux[1].equals("no") || aux[1].equals("yes"))){
                                configurationInfo.put(aux[0], aux[1]);
                            
                            }else if(("scroll".equals(aux[0])) && (aux[1].equals("horizontal") || aux[1].equals("vertical") || aux[1].equals("none"))){
                                configurationInfo.put(aux[0], aux[1]);
                            }else if((aux[0].equals("warn")) && (aux[1].equals("no") || aux[1].equals("yes"))){
                                configurationInfo.put(aux[0], aux[1]);
                            }else{
                                throw new IOException();
                            }
                        
                            linesRead=dataInput.readLine();
                        }
                        
                        
                        break;
                    case "[Continents]":
                        System.out.println("File"+linesRead);
                        linesRead=dataInput.readLine();
                        while(!linesRead.equals("")){
                            aux=linesRead.split("=",2);
                            Continent auxContinent=new Continent(aux[0],Integer.parseInt(aux[1]));
                            System.out.println("Continent"+aux[0]);
                            graphContinents.put(aux[0], auxContinent);
                            linesRead=dataInput.readLine();
                        }   break;
                    case "[Territories]":
                        System.out.println("File"+linesRead);
                        linesRead=dataInput.readLine();
                        while(linesRead!=null){
                            if(!linesRead.equals("")){
                                aux=linesRead.split(",");
                                
                                int i=0;
                                if(aux.length>4){
                                    
                                    Country auxCountry = new Country(aux[0],Integer.parseInt(aux[1]),Integer.parseInt(aux[2]));
                                    System.out.println("Country-----------"+aux[0]);
                                    LinkedList<String> auxAdj=new LinkedList<>();
                                    System.out.println("CREO EL PAIS");
                                    for(i=0;i<aux.length-4;i++){
                                        System.out.println("Adj"+aux[i+4]);
                                        auxAdj.add(aux[i+4]);
                                    }
                                    System.out.println("Continente al que pertenece "+aux[3]);
                                    auxCountry.setAdj(auxAdj);
                                    System.out.println("CREO las adj");
                                   
                                        graphContinents.get(aux[3]).setMember(auxCountry);
                                        System.out.println("Lo metio en un continente");
                                   
                                    graphTerritories.put(aux[0], auxCountry);
                                    System.out.println("Lo metio en los paises");
                                }else{
                                    throw new IOException();                            
                                }
                               
                            }
                               
                            linesRead=dataInput.readLine();
                           
                        }       break;
                    default:
                        linesRead=dataInput.readLine();
                        break;
                }
                   
            }
            
            System.out.println("Board Created");
            board.setConfigurationInfo(configurationInfo);
            board.setGraphTerritories(graphTerritories);
            board.setGraphContinents(graphContinents);
            dataInput.close(); 
       
        
        }catch(FileNotFoundException e){
            System.err.println("File not Found: "+e.getMessage());
        }catch(IOException e){
            System.err.println("Reading Failure: "+e.getMessage());
        }
        
        return board;
    }
    
    // New requirement - Discussion
    public static Board generateBoardFile(String path){
        return null;
    }
}
