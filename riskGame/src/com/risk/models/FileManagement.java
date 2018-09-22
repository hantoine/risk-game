/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import com.risk.models.exceptions.FormatException;
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
public class FileManagement {
    
    /**
     * It creates a board from a file
     * @param path
     * @return
     * @throws FormatException
     * @throws IOException 
     */
    public Board createBoard(String path) throws FormatException, IOException {
        Board board = new Board();
        HashMap<String, Continent> graphContinents = new HashMap();
        HashMap<String, Country> graphTerritories = new HashMap();
        HashMap<String, String> configurationInfo=new HashMap<>();
        String fileRead;
        
        try {
            fileRead = readFile(path);
            
            String[] stringSplit=fileRead.split(Pattern.quote("[Continents]"), 2);
      
            if(stringSplit.length==2){
                try {
                    configurationInfo=configurationInf(stringSplit[0],path, board);
                    String[] stringSplit1=stringSplit[1].split(Pattern.quote("[Territories]"), 2);
                    if(stringSplit1.length==2){
                        graphContinents=continentCreator(stringSplit1[0]);
                        graphTerritories=countryCreator(stringSplit1[1], graphContinents);
                        board.setConfigurationInfo(configurationInfo);
                        board.setGraphTerritories(graphTerritories);
                        board.setGraphContinents(graphContinents);
                        System.out.println("Board Created");
                    
                    }else{
                        throw new FormatException("File Format not valid");
                    }
                } catch (Exception ex) {
                    throw new FormatException(ex.getMessage());
                }
                
            }else{
                throw new FormatException("File Format not valid");
            }
            
        } catch (IOException ex) {
            throw new IOException("Reading Error");
        }
        return board; 
        
    }    
    
    /**
     * It reads a file and creates a String with it content
     * @param path
     * @return
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public String readFile(String path) throws FileNotFoundException, IOException{  
        String linesRead="";
        try {
            linesRead = new String(Files.readAllBytes(Paths.get(path)));
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File not found");
        } catch (IOException e) {
            throw new IOException("File reading failed");
        }
        
        return linesRead;  
    }
    
    /**
     * It returns information to configurate the board (author, image, and others) using a String
     * @param info
     * @param path
     * @param board
     * @return
     * @throws FormatException
     * @throws IOException 
     */
    public HashMap<String, String> configurationInf(String info, String path, Board board) throws FormatException, IOException{
        
        String[] linesInfo;
        HashMap<String, String> configurationInfo = new HashMap();
        String[] aux;
        File fileRead = new File(path);
        
        if(!info.equals("")){
            linesInfo=info.split(System.getProperty("line.separator"));
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
                         
                }
            }else{
                throw new FormatException("Invalid file format");
            }
                
        }
        
        if(board.getImage()==null){
            throw new FormatException("Image loading failure");
        }
        
        return configurationInfo;
    }
    
    /**
     * It returns the continents for the board from a String
     * @param info
     * @return
     * @throws FormatException 
     */
    public HashMap<String, Continent> continentCreator(String info) throws FormatException{
        HashMap<String, Continent> graphContinents = new HashMap();
        
        if(!info.equals("")){
            String[] linesInfo=info.split(System.getProperty("line.separator"));
            int i=0;
            String aux[];
            while (i<linesInfo.length) {
                if(!linesInfo[i].equals("")){
                    aux = linesInfo[i].split("=", 2);
                    if(aux.length>1){
                        Continent auxContinent = new Continent(aux[0], Integer.parseInt(aux[1]));
                        graphContinents.put(aux[0], auxContinent);
                            
                    }else{
                        throw new FormatException("Continent without correct delimiter");
                    }
                }
                i++;
            }
        }else{
            throw new FormatException("Invalid file format");
        }
        return graphContinents;
    }
 
    /**
     * It returns the countries for the board from a String. It also puts them in a Continent.
     * @param info
     * @param graphContinents
     * @return
     * @throws FormatException 
     */
    public HashMap<String, Country> countryCreator(String info, HashMap<String, Continent> graphContinents ) throws FormatException{
        HashMap<String, Country> graphTerritories = new HashMap();
        
        if(graphContinents==null){
            throw new FormatException("No continents provided");
        }
        
        if(!info.equals("")){
            String[] linesInfo=info.split(System.getProperty("line.separator"));
            int i=0;
            String aux[];
            while (i<linesInfo.length) {
               
                if(!linesInfo[i].equals("")){
                  
                    //Splits the country information
                    aux = linesInfo[i].split(",");
                    int j = 0;
                            
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

                        for (j = 0; j < aux.length - 4; j++) {
                            if (graphTerritories.keySet().contains(aux[j + 4])) {
                                auxCountryAdj = graphTerritories.get(aux[j + 4]);

                            } else {
                                auxCountryAdj = new Country(aux[j + 4]);
                            }
                            //Adds the adj
                            auxCountry.getAdj().add(auxCountryAdj);
                            graphTerritories.put(aux[j+4], auxCountryAdj);
                        }

                        graphContinents.get(aux[3]).setMember(auxCountry);
                        graphTerritories.put(aux[0], auxCountry);

                    } else {
                        throw new FormatException("Country without adjacencies");
                    }             
                }
                i++;
            }
            
        }else{
            throw new FormatException("Invalid file format");
        }
        
        return graphTerritories;
    }
    
    
    // New requirement - Discussion
    public static Board generateBoardFile(String path) {
        return null;
    }
}
