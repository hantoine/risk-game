/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;

/**
 * Class created to load or download information (using a file)
 * @author Nellybett
 */
public class MapFileManagement {
    
    /**
     * It creates a board from a file
     * @param path the path provided from the user
     * @param board the map in the model
     * @return 0 for an execution without errors
     */
    public int createBoard(String path, MapModel board){
        
        int errorConfigurationInfo;
        String fileRead;
        
        
        fileRead = readFile(path);
        if (fileRead.equals("-1")){
            return -1;
        }
            
            String[] stringSplit=fileRead.split(Pattern.quote("[Continents]"), 2);
      
            if(stringSplit.length==2){
                    errorConfigurationInfo=configurationInf(stringSplit[0],path, board);
                    if(errorConfigurationInfo!=0){
                        return -2;
                    }
                    
                    String[] stringSplit1=stringSplit[1].split(Pattern.quote("[Territories]"), 2);
                    if(stringSplit1.length==2){
                        
                        errorConfigurationInfo=continentCreator(stringSplit1[0], board);
                        if(errorConfigurationInfo!=0){
                        return -3;
                        }
                        errorConfigurationInfo=countryCreator(stringSplit1[1], board);
                        if(errorConfigurationInfo!=0){
                        return -4;
                        }
                        
                        System.out.println("Board Created");
                    
                    }else{
                        return -5;
                    }
            }else{
                return -6;
            }
            
        return 0; 
        
    }    
    
    /**
     * It reads a file and creates a String with it content
     * @param path
     * @return -1 if a mistake occurs or the string with the file content
     */
    public String readFile(String path){  
        String linesRead;
        Path aux;
        try {
            
            try{
                aux=Paths.get(path);        
            }catch(Exception ex){
                return "-1";
            }
            linesRead = new String(Files.readAllBytes(aux));
        } catch (FileNotFoundException e) {
            return "-1";
        } catch (IOException e) {
            return "-1";
        }
        
        return linesRead;  
    }
    
    /**
     * It sets information to configurate the board (author, image, and others) using a String
     * @param info the string with the information
     * @param path the path were the file was so it can look for the image
     * @param board the map from the model
     * @return 0 success or -1 error
     */
    public int configurationInf(String info, String path, MapModel board){
        
        String[] linesInfo;
        HashMap<String, String> configurationInfo = new HashMap();
        String[] aux;
        File fileRead = new File(path);
        
        if(!info.equals("")){
            linesInfo=info.split("\\r?\\n");
            if(linesInfo[0].equals("[Map]")){
                for(int i=1;i<linesInfo.length && !linesInfo[i].equals("");i++){
                    aux = linesInfo[i].split("=", 2);
                    
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
                                        return -1;
                                    } catch (IOException e) {
                                        return-1;
                                    }
                                    break;
                                case "wrap":
                                    if((aux[1].equals("no") || aux[1].equals("yes"))) {
                                        configurationInfo.put(aux[0], aux[1]);
                                    }else{
                                        return -1;
                                    }
                                    break;
                                case "scroll":
                                    if((aux[1].equals("horizontal") || aux[1].equals("vertical") || aux[1].equals("none"))) {
                                        configurationInfo.put(aux[0], aux[1]);
                                    }else{
                                        return -1;
                                    }
                                    break;
                                case "warn":
                                    if((aux[1].equals("no") || aux[1].equals("yes"))) {
                                        configurationInfo.put(aux[0], aux[1]);
                                    }else{
                                        return -1;
                                    }
                                    break;
                                default:
                                    return -1;
                                      
                            }
                         
                }
            }else{
                return -1;
            }
            
            board.setConfigurationInfo(configurationInfo);
            return 0;
                
        }
        
        if(board.getImage()==null){
            return -1;
        }
        
        return 0;
    }
    
    /**
     * It sets the continents in the board from a String
     * @param info the string with the continents information
     * @param board the map to be modified from the model
     * @return 0 success or -1 error
     */
    public int continentCreator(String info, MapModel board){
        HashMap<String, ContinentModel> graphContinents = new HashMap();
        
        if(!info.equals("")){
            String[] linesInfo=info.split("\\r?\\n");
            int i=0;
            String aux[];
            while (i<linesInfo.length) {
                if(!linesInfo[i].equals("")){
                    aux = linesInfo[i].split("=", 2);
                    if(aux.length>1){
                        ContinentModel auxContinent;
                        try{
                            auxContinent = new ContinentModel(aux[0], Integer.parseInt(aux[1]));    
                        }catch (NumberFormatException ex){
                            return -1;
                        }
                        
                        if(graphContinents.containsKey(aux[0])){
                            return -1;
                        }else{
                            graphContinents.put(aux[0], auxContinent);
                        }
                    }else{
                        return -1;
                    }
                }
                i++;
            }
        }else{
            return -1;
        }
        board.setGraphContinents(graphContinents);
        return 0;
    }
 
    /**
     * It sets the countries in the board from a String. It also puts them in a Continent.
     * @param info the string with the countries
     * @param board the map from the model
     * @return 0 success or -1 error
     */
    public int countryCreator(String info, MapModel board ){
        HashMap<String, TerritoryModel> graphTerritories = new HashMap();
        
        if(board.getGraphContinents()==null){
            return -1;
        }
        
        if(!info.equals("")){
            String[] linesInfo=info.split("\\r?\\n");
            int i=0;
            String aux[];
            while (i<linesInfo.length) {
               
                if(!linesInfo[i].equals("")){
                  
                    //Splits the country information
                    aux = linesInfo[i].split(",");
                    int j;
                            
                    //The information has to be bigger first country second and third position 4th continent
                    if (aux.length > 4) {
                        // Creates de Country in the file
                        TerritoryModel auxCountry;
                        int cordX;
                        int cordY;
                        try{
                            cordX=Integer.parseInt(aux[1]);
                            cordY=Integer.parseInt(aux[2]);
                        
                        }catch(NumberFormatException e){
                            return -1;
                        }               
                            
                        if (graphTerritories.keySet().contains(aux[0])) {
                            
                            auxCountry = graphTerritories.get(aux[0]);
                            if(auxCountry.getPositionX()==-1 && auxCountry.getPositionY()==-1){
                                auxCountry.countrySetter(cordX,cordY);
                            }else{
                                return -1;
                            }
                            
                        } else {
                            auxCountry = new TerritoryModel(aux[0], cordX,cordY);
                        }

                        //Creates adj country
                        TerritoryModel auxCountryAdj;

                        for (j = 0; j < aux.length - 4; j++) {
                            if (graphTerritories.keySet().contains(aux[j + 4])) {
                                auxCountryAdj = graphTerritories.get(aux[j + 4]);

                            } else {
                                auxCountryAdj = new TerritoryModel(aux[j + 4]);
                            }
                            //Adds the adj
                            auxCountry.getAdj().add(auxCountryAdj);
                            graphTerritories.put(aux[j+4], auxCountryAdj);
                        }
                        
                        if(board.getGraphContinents().containsKey(aux[3])){
                            board.getGraphContinents().get(aux[3]).setMember(auxCountry);
                        }else{
                            return -1;
                        }
                        graphTerritories.put(aux[0], auxCountry);

                    } else {
                        return -1;
                    }             
                }
                i++;
            }
            
        }else{
            return -1;
        }
        board.setGraphTerritories(graphTerritories);
        return 0;
    }
    
    
    /**
     * It generates a file with the board information in the given path
     * @param path the path provided by the user
     * @param board the map to read
     * @return 0 success or -1 error
     */
    public int generateBoardFile(String path, MapModel board) {
        return 0;
    }
}
