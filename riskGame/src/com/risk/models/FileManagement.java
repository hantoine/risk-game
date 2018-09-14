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
            String[] aux;
            linesRead=dataInput.readLine();
            
            while(linesRead!=null){
                
                switch (linesRead) {
                    case "[Map]":
                        System.out.println("File"+linesRead);
                        linesRead=dataInput.readLine();
                        aux=linesRead.split("=",2);
                        if("author".equals(aux[0])){
                            configurationInfo.put(aux[0], aux[1]);
                        }else{
                            throw new IOException();
                        }   linesRead=dataInput.readLine();
                        aux=linesRead.split("=",2);
                        if("image".equals(aux[0])){
                            configurationInfo.put(aux[0], aux[1]);
                            BufferedImage image= ImageIO.read(new File(aux[1]));
                            board.setImage(image);
                            
                        }else{
                            throw new IOException();
                        }   linesRead=dataInput.readLine();
                        aux=linesRead.split("=",2);
                        if(("wrap".equals(aux[0])) && (aux[1].equals("no") || aux[1].equals("yes"))){
                            configurationInfo.put(aux[0], aux[1]);
                        }else{
                            throw new IOException();
                        }   linesRead=dataInput.readLine();
                        aux=linesRead.split("=",2);
                        if(("scroll".equals(aux[0])) && (aux[1].equals("horizontal") || aux[1].equals("vertical"))){
                            configurationInfo.put(aux[0], aux[1]);
                        }else{
                            throw new IOException();
                        }   linesRead=dataInput.readLine();
                        aux=linesRead.split("=",2);
                        if((aux[0].equals("warn")) && (aux[1].equals("no") || aux[1].equals("yes"))){
                            configurationInfo.put(aux[0], aux[1]);
                        }else{
                            throw new IOException();
                        }   break;
                    case "[Continents]":
                        linesRead=dataInput.readLine();
                        while(!linesRead.equals("")){
                            aux=linesRead.split("=",2);
                            Continent auxContinent=new Continent(aux[0],Integer.parseInt(aux[1]));
                            graphContinents.put(aux[0], auxContinent);
                            linesRead=dataInput.readLine();
                        }   break;
                    case "[Territories]":
                        linesRead=dataInput.readLine();
                        while(!linesRead.equals("")){
                            aux=linesRead.split(",");
                            int i;
                            if(aux.length>2){
                                Country auxCountry = new Country(aux[0],Integer.getInteger(aux[1]),Integer.getInteger(aux[2]));
                                LinkedList<String> auxAdj=new LinkedList<>();
                                for(i=0;i<aux.length-3;i++){
                                    auxAdj.add(aux[i+3]);
                                }
                                auxCountry.setAdj(auxAdj);
                                graphTerritories.put(aux[0], auxCountry);
                            }else{
                                throw new IOException();                            
                            }
                            linesRead=dataInput.readLine();
                        }   break;
                    default:
                        linesRead=dataInput.readLine();
                        break;
                }
                board.setConfigurationInfo(configurationInfo);
                board.setGraphTerritories(graphTerritories);
                board.setGraphContinents(graphContinents);
            
            }
        
            dataInput.close(); 
       
        
        }catch(FileNotFoundException e){
            System.err.println("File not Found: "+e.getMessage());
        }catch(IOException e){
            System.err.println("Reading Failure: "+e.getMessage());
        }
        
        return null;
    }
    
    // New requirement - Discussion
    public static Board generateBoardFile(String path){
        return null;
    }
}
