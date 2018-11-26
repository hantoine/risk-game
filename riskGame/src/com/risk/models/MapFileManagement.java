/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.risk.models;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Optional;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;

/**
 * Class created to load or download information (using a file)
 *
 * @author Nellybett
 */
public class MapFileManagement {

    /**
     * It creates a board from a file
     *
     * @param path the path provided from the user
     * @param board the map in the model
     * @throws com.risk.models.MapFileManagement.MapFileManagementException exception for the class
     */
    public static void createBoard(String path, MapModel board)
            throws MapFileManagementException {
        String fileRead;

        if (board == null) {
            throw new MapInvalidException(new MapPath(path));
        }

        fileRead = readFile(path);
        if (fileRead == null || fileRead.equals("-1") || fileRead.equals("")) {
            throw new MapFileReadingException(new MapPath(path));
        }

        String[] stringSplit = fileRead.split(Pattern.quote("[Continents]"), 2);
        if (stringSplit.length != 2) {
            throw new MapFileNoContinentsException(new MapPath(path));
        }

        configurationInf(stringSplit[0], path, board);

        String[] stringSplit1
                = stringSplit[1].split(Pattern.quote("[Territories]"), 2);
        if (stringSplit1.length != 2) {
            throw new MapFileNoTerritoriesException(new MapPath(path));
        }

        continentCreator(stringSplit1[0], board, path);
        territoryCreator(stringSplit1[1], board, path);

        if (!board.isValid()) {
            throw new MapInvalidException(new MapPath(path));
        }
    }

    /**
     * It reads a file and creates a String with its content
     *
     * @param path Path of the file
     * @return Return the string with the file content or the String "-1" if an
     * error occurred
     */
    public static String readFile(String path) {
        String linesRead;
        String[] extenValid;
        Path pathReadFile;
        try {
            extenValid = path.split("[.]");
            if (extenValid == null || !extenValid[extenValid.length - 1].equals("map")) {
                return "-1";
            }
            try {
                pathReadFile = Paths.get(path);
            } catch (Exception ex) {
                return "-1";
            }
            linesRead = new String(Files.readAllBytes(pathReadFile));
        } catch (FileNotFoundException e) {
            return "-1";
        } catch (IOException e) {
            return "-1";
        }

        return linesRead;
    }

    /**
     * It sets information to configurate the board (author, image, and others)
     * using a String
     *
     * @param info the string with the information
     * @param path the path were the file was so it can look for the image
     * @param map the map from the model
     * @throws com.risk.models.MapFileManagement.MapFileConfigException exception for the class
     */
    public static void configurationInf(String info, String path, MapModel map)
            throws MapFileConfigException {

        String[] linesInfo;
        String[] linesRead;
        File fileRead = new File(path);

        if (!info.equals("")) {
            linesInfo = info.split("\\r?\\n");
            if (!linesInfo[0].equals("[Map]")) {
                throw new MapFileConfigException(new MapPath(path));
            }

            for (int i = 1; i < linesInfo.length && !linesInfo[i].equals(""); i++) {
                linesRead = linesInfo[i].split("=", 2);

                switch (linesRead[0]) {
                    case "author":
                        map.setAuthorConfig(linesRead[1]);
                        break;
                    case "image":
                        if (linesRead[1].isEmpty() || linesRead[1].equals("null")) {
                            break;
                        }

                        map.setImagePath(linesRead[1]);
                        Path imagePath = fileRead.toPath().resolveSibling(linesRead[1]);
                        try {
                            BufferedImage image = ImageIO.read(new File(imagePath.toString()));
                            map.setImage(image);
                        } catch (FileNotFoundException e) {
                            throw new MapFileConfigException(new MapPath(path));
                        } catch (IOException e) {
                            throw new MapFileConfigException(new MapPath(path));
                        }
                        break;
                    case "wrap":
                        if ((linesRead[1].equals("no") || linesRead[1].equals("yes"))) {
                            map.setWrapConfig(linesRead[1].equals("yes"));
                        } else {
                            throw new MapFileConfigException(new MapPath(path));
                        }
                        break;
                    case "scroll":
                        if ((linesRead[1].equals("horizontal") || linesRead[1].equals("vertical") || linesRead[1].equals("none"))) {
                            map.setScrollConfig(linesRead[1]);
                        } else {
                            throw new MapFileConfigException(new MapPath(path));
                        }
                        break;
                    case "warn":
                        if ((linesRead[1].equals("no") || linesRead[1].equals("yes"))) {
                            map.setWarnConfig(linesRead[1].equals("yes"));
                        } else {
                            throw new MapFileConfigException(new MapPath(path));
                        }
                        break;
                    default:
                        throw new MapFileConfigException(new MapPath(path));
                }
            }
        }
    }

    /**
     * It sets the continents in the board from a String
     *
     * @param info the string with the continents information
     * @param board the map to be modified from the model
     * @param path Path of the file
     * @throws com.risk.models.MapFileManagement.MapFileContinentException exception for the class
     */
    public static void continentCreator(String info, MapModel board, String path)
            throws MapFileContinentException {
        HashMap<String, ContinentModel> graphContinents = new HashMap();

        if (!info.equals("")) {
            String[] linesInfo = info.split("\\r?\\n");
            int i = 0;
            String[] linesRead;
            while (i < linesInfo.length) {
                if (!linesInfo[i].equals("")) {
                    linesRead = linesInfo[i].split("=", 2);
                    if (linesRead.length > 1) {
                        ContinentModel auxContinent;
                        try {
                            auxContinent = new ContinentModel(linesRead[0], Integer.parseInt(linesRead[1]));
                        } catch (NumberFormatException ex) {
                            throw new MapFileContinentException(new MapPath(path));
                        }

                        if (graphContinents.containsKey(linesRead[0])) {
                            throw new MapFileContinentException(new MapPath(path));
                        } else {
                            graphContinents.put(linesRead[0], auxContinent);
                        }
                    } else {
                        throw new MapFileContinentException(new MapPath(path));
                    }
                }
                i++;
            }
        } else {
            throw new MapFileContinentException(new MapPath(path));
        }
        board.setGraphContinents(graphContinents);
    }

    /**
     * It sets the territories in the board from a String. It also puts them in
     * a Continent.
     *
     * @param info the string with the territories
     * @param board the map from the model
     * @param path Path of the file
     * @throws com.risk.models.MapFileManagement.MapFileTerritoryException exception for the class
     */
    public static void territoryCreator(String info, MapModel board, String path)
            throws MapFileTerritoryException {
        HashMap<String, TerritoryModel> graphTerritories = new HashMap();

        if (board.getTerritories() == null) {
            throw new MapFileTerritoryException(new MapPath(path));
        }

        if (info.equals("")) {
            throw new MapFileTerritoryException(new MapPath(path));
        }

        String[] linesInfo = info.split("\\r?\\n");
        int i = 0;
        String currentTerritoryLine[];
        while (i < linesInfo.length) {

            if (linesInfo[i].equals(" ") || linesInfo[i].isEmpty()) {
                i++;
                continue;
            }

            //Splits the territory information
            currentTerritoryLine = linesInfo[i].split(",");
            int j;

            //The information has to be bigger first territory second and third position 4th continent
            if (currentTerritoryLine.length > 4) {
                // Creates de territory in the file
                TerritoryModel auxterritory;
                int cordX;
                int cordY;
                try {
                    cordX = Integer.parseInt(currentTerritoryLine[1]);
                    cordY = Integer.parseInt(currentTerritoryLine[2]);

                    if (cordX + 50 > board.getMapWidth()) {
                        board.setMapWidth(cordX + 50);
                    }

                    if (cordY + 50 > board.getMapHeight()) {
                        board.setMapHeight(cordY + 50);
                    }
                } catch (NumberFormatException e) {
                    throw new MapFileTerritoryException(new MapPath(path));

                }

                final String currentTerritoryName = currentTerritoryLine[0].trim();
                Optional<String> alreadyCreatedTerritoryName = graphTerritories.keySet().stream()
                        .filter(s -> s.equalsIgnoreCase(currentTerritoryName)).findFirst();
                if (alreadyCreatedTerritoryName.isPresent()) {

                    auxterritory = graphTerritories.get(alreadyCreatedTerritoryName.get());
                    if (auxterritory.getPositionX() == -1 && auxterritory.getPositionY() == -1) {
                        auxterritory.territorySetter(cordX, cordY);
                    } else {
                        throw new MapFileTerritoryException(new MapPath(path));

                    }

                } else {
                    auxterritory = new TerritoryModel(currentTerritoryName, cordX, cordY);
                }

                //Creates adj territory
                TerritoryModel auxTerritoryAdj;

                for (j = 0; j < currentTerritoryLine.length - 4; j++) {
                    final String currentAdjacentTerritoryName = currentTerritoryLine[j + 4].trim();
                    Optional<String> alreadyCreatedAdjacentTerritoryName = graphTerritories.keySet().stream()
                            .filter(s -> s.equalsIgnoreCase(currentAdjacentTerritoryName)).findFirst();

                    if (alreadyCreatedAdjacentTerritoryName.isPresent()) {
                        auxTerritoryAdj = graphTerritories.get(alreadyCreatedAdjacentTerritoryName.get());
                    } else {
                        auxTerritoryAdj = new TerritoryModel(currentAdjacentTerritoryName);
                    }
                    //Adds the adj
                    auxterritory.addAdjacentTerritory(auxTerritoryAdj);
                    graphTerritories.put(auxTerritoryAdj.getName(), auxTerritoryAdj);
                }

                if (board.getContinentList().contains(currentTerritoryLine[3])) {
                    board.getContinentByName(currentTerritoryLine[3]).addMember(auxterritory);
                    auxterritory.setContinentName(currentTerritoryLine[3]);
                } else {
                    throw new MapFileTerritoryException(new MapPath(path));

                }
                graphTerritories.put(auxterritory.getName(), auxterritory);

            } else {
                throw new MapFileTerritoryException(new MapPath(path));
            }

            i++;
        }

        //If the line of one of the adjacent territory has not been met, there is an error in the file
        if (graphTerritories.values().stream().anyMatch((t) -> (t.getContinentName() == null))) {
            throw new MapFileTerritoryException(new MapPath(path));
        }

        board.setGraphTerritories(graphTerritories);
    }

    /**
     * It generates a file with the board information in the given path
     *
     * @param path the path provided by the user it includes the name of the
     * file
     * @param board the map to read
     * @throws com.risk.models.MapFileManagement.MapFileManagementException exception for the class
     */
    public static void generateBoardFile(String path, MapModel board)
            throws MapFileManagementException {
        String fileContent;
        String configuration = "[Map]\n";
        String continents = "[Continents]\n";
        String territories = "[Territories]\n";

        if (!board.isValid()) {
            throw new MapInvalidException(new MapPath(path));
        }
        if (board == null) {
            throw new MapFileReadingException(new MapPath(path));
        }

        MapConfig mapConfig = board.getConfigurationInfo();
        configuration += "author=" + mapConfig.getAuthor() + "\r\n";
        configuration += "image=" + mapConfig.getImagePath() + "\r\n";
        configuration += "wrap=" + (mapConfig.isWrap() ? "yes" : "no") + "\r\n";
        configuration += "scroll=" + mapConfig.getScroll() + "\r\n";
        configuration += "warn=" + (mapConfig.isWarn() ? "yes" : "no") + "\r\n";

        for (String continent : board.getContinentList()) {
            continents += continent + "=" + board.getContinentByName(continent).getBonusScore() + "\n";
        }

        for (String terrName : board.getTerritoryList()) {
            TerritoryModel aux = board.getTerritoryByName(terrName);
            String adj = getAdj(aux);
            territories += aux.getName() + "," + aux.getPositionX() + "," + aux.getPositionY() + "," + aux.getContinentName();
            if (!adj.isEmpty()) {
                territories += "," + adj;
            }
            territories += "\n";
        }

        fileContent = configuration + "\n" + continents + "\n" + territories;
        savingFile(path, fileContent);
    }

    /**
     * It creates a string with the adjacencies of a territory
     *
     * @param territory receives a territory
     * @return return a string with the adj separated by a ,
     */
    public static String getAdj(TerritoryModel territory) {
        String adj = "";
        int i = 0;
        for (TerritoryModel aux : territory.getAdj()) {
            if (i == 0) {
                adj += aux.getName();
                i++;
            } else {
                adj += "," + aux.getName();
            }
        }
        return adj;
    }

    /**
     * It saves string in a file
     *
     * @param path is the place to save the string
     * @param fileContent is the contents for a file
     * @return success 0, or error -1
     * @throws MapFileReadingException exception for the method
     */
    private static int savingFile(String path, String fileContent) throws MapFileReadingException {
        BufferedWriter bufferedWriter = null;
        try {

            File myFile = new File(path);
            // check if file exist, otherwise create the file before writing
            if (!myFile.exists()) {
                myFile.createNewFile();
            }
            Writer writer = new FileWriter(myFile);
            bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(fileContent);
            return 0;
        } catch (IOException e) {
            throw new MapFileReadingException(new MapPath(path));
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException ex) {
                throw new MapFileReadingException(new MapPath(path));
            }
        }

    }

    public static class MapFileManagementException extends Exception {

        public MapFileManagementException(String message) {
            super(message);
        }

        public MapFileManagementException(MapPath map) {
            super("Error in file format for map " + map);
        }
    }

    public static class MapFileReadingException
            extends MapFileManagementException {

        public MapFileReadingException(MapPath map) {
            super("Error reading the file for map " + map);
        }
    }

    public static class MapFileConfigException
            extends MapFileManagementException {

        public MapFileConfigException(MapPath map) {
            super("Error in parameters to configurate the map " + map);
        }
    }

    public static class MapFileContinentException
            extends MapFileManagementException {

        public MapFileContinentException(MapPath map) {
            super("Error in continent information for map " + map);
        }
    }

    public static class MapFileTerritoryException
            extends MapFileManagementException {

        public MapFileTerritoryException(MapPath map) {
            super("Error in territory information for map " + map);
        }
    }

    public static class MapFileNoTerritoriesException
            extends MapFileManagementException {

        public MapFileNoTerritoriesException(MapPath map) {
            super("No territories separator in file for map " + map);
        }
    }

    public static class MapFileNoContinentsException
            extends MapFileManagementException {

        public MapFileNoContinentsException(MapPath map) {
            super("No continents separator in file for map " + map);
        }
    }

    public static class MapInvalidException
            extends MapFileManagementException {

        public MapInvalidException(MapPath map) {
            super("The map " + map + " is not valid.");
        }
    }

}
