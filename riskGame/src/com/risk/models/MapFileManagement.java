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
     * @return 0 for an execution without errors
     */
    public static int createBoard(String path, MapModel board) {

        int errorConfigurationInfo;
        String fileRead;

        if (board == null) {
            return -7;
        }

        fileRead = readFile(path);
        if (fileRead == null || fileRead.equals("-1") || fileRead.equals("")) {
            return -1;
        }

        String[] stringSplit = fileRead.split(Pattern.quote("[Continents]"), 2);

        if (stringSplit.length == 2) {
            errorConfigurationInfo = configurationInf(stringSplit[0], path, board);
            if (errorConfigurationInfo != 0) {
                return -2;
            }

            String[] stringSplit1 = stringSplit[1].split(Pattern.quote("[Territories]"), 2);
            if (stringSplit1.length == 2) {

                errorConfigurationInfo = continentCreator(stringSplit1[0], board);
                if (errorConfigurationInfo != 0) {
                    return -3;
                }
                errorConfigurationInfo = countryCreator(stringSplit1[1], board);
                if (errorConfigurationInfo != 0) {
                    return -4;
                }

                System.out.println("Board Created");

            } else {
                return -5;
            }
        } else {
            return -6;
        }

        return 0;

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
            if (!extenValid[extenValid.length - 1].equals("map") || extenValid == null) {
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
     * @return 0 success or -1 error
     */
    public static int configurationInf(String info, String path, MapModel map) {

        String[] linesInfo;
        String[] linesRead;
        File fileRead = new File(path);

        if (!info.equals("")) {
            linesInfo = info.split("\\r?\\n");
            if (!linesInfo[0].equals("[Map]")) {
                return -1;
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
                            return -1;
                        } catch (IOException e) {
                            return -1;
                        }
                        break;
                    case "wrap":
                        if ((linesRead[1].equals("no") || linesRead[1].equals("yes"))) {
                            map.setWrapConfig(linesRead[1].equals("yes"));
                        } else {
                            return -1;
                        }
                        break;
                    case "scroll":
                        if ((linesRead[1].equals("horizontal") || linesRead[1].equals("vertical") || linesRead[1].equals("none"))) {
                            map.setScrollConfig(linesRead[1]);
                        } else {
                            return -1;
                        }
                        break;
                    case "warn":
                        if ((linesRead[1].equals("no") || linesRead[1].equals("yes"))) {
                            map.setWarnConfig(linesRead[1].equals("yes"));
                        } else {
                            return -1;
                        }
                        break;
                    default:
                        return -1;
                }
            }
        }

        return 0;
    }

    /**
     * It sets the continents in the board from a String
     *
     * @param info the string with the continents information
     * @param board the map to be modified from the model
     * @return 0 success or -1 error
     */
    public static int continentCreator(String info, MapModel board) {
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
                            return -1;
                        }

                        if (graphContinents.containsKey(linesRead[0])) {
                            return -1;
                        } else {
                            graphContinents.put(linesRead[0], auxContinent);
                        }
                    } else {
                        return -1;
                    }
                }
                i++;
            }
        } else {
            return -1;
        }
        board.setGraphContinents(graphContinents);
        return 0;
    }

    /**
     * It sets the countries in the board from a String. It also puts them in a
     * Continent.
     *
     * @param info the string with the countries
     * @param board the map from the model
     * @return 0 success or -1 error
     */
    public static int countryCreator(String info, MapModel board) {
        HashMap<String, TerritoryModel> graphTerritories = new HashMap();

        if (board.getTerritories() == null) {
            return -1;
        }

        if (info.equals("")) {
            return -1;
        }

        String[] linesInfo = info.split("\\r?\\n");
        int i = 0;
        String currentTerritoryLine[];
        while (i < linesInfo.length) {

            if (linesInfo[i].equals(" ") || linesInfo[i].isEmpty()) {
                i++;
                continue;
            }

            //Splits the country information
            currentTerritoryLine = linesInfo[i].split(",");
            int j;

            //The information has to be bigger first country second and third position 4th continent
            if (currentTerritoryLine.length > 4) {
                // Creates de Country in the file
                TerritoryModel auxCountry;
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
                    return -1;
                }

                final String currentTerritoryName = currentTerritoryLine[0].trim();
                Optional<String> alreadyCreatedTerritoryName = graphTerritories.keySet().stream()
                        .filter(s -> s.equalsIgnoreCase(currentTerritoryName)).findFirst();
                if (alreadyCreatedTerritoryName.isPresent()) {

                    auxCountry = graphTerritories.get(alreadyCreatedTerritoryName.get());
                    if (auxCountry.getPositionX() == -1 && auxCountry.getPositionY() == -1) {
                        auxCountry.countrySetter(cordX, cordY);
                    } else {
                        return -1;
                    }

                } else {
                    auxCountry = new TerritoryModel(currentTerritoryName, cordX, cordY);
                }

                //Creates adj country
                TerritoryModel auxCountryAdj;

                for (j = 0; j < currentTerritoryLine.length - 4; j++) {
                    final String currentAdjacentTerritoryName = currentTerritoryLine[j + 4].trim();
                    Optional<String> alreadyCreatedAdjacentTerritoryName = graphTerritories.keySet().stream()
                            .filter(s -> s.equalsIgnoreCase(currentAdjacentTerritoryName)).findFirst();

                    if (alreadyCreatedAdjacentTerritoryName.isPresent()) {
                        auxCountryAdj = graphTerritories.get(alreadyCreatedAdjacentTerritoryName.get());
                    } else {
                        auxCountryAdj = new TerritoryModel(currentAdjacentTerritoryName);
                    }
                    //Adds the adj
                    auxCountry.addAdjacentTerritory(auxCountryAdj);
                    graphTerritories.put(auxCountryAdj.getName(), auxCountryAdj);
                }

                if (board.getContinentList().contains(currentTerritoryLine[3])) {
                    board.getContinentByName(currentTerritoryLine[3]).addMember(auxCountry);
                    auxCountry.setContinentName(currentTerritoryLine[3]);
                } else {
                    return -1;
                }
                graphTerritories.put(auxCountry.getName(), auxCountry);

            } else {
                return -1;
            }

            i++;
        }

        //If the line of one of the adjacent territory has not been met, there is an error in the file
        if (graphTerritories.values().stream().anyMatch((t) -> (t.getContinentName() == null))) {
            return -4;
        }

        board.setGraphTerritories(graphTerritories);

        return 0;
    }

    /**
     * It generates a file with the board information in the given path
     *
     * @param path the path provided by the user it includes the name of the
     * file
     * @param board the map to read
     * @return 0 success or -1 error
     */
    public static int generateBoardFile(String path, MapModel board) {
        String fileContent = "";
        String configuration = "[Map]\n";
        String continents = "[Continents]\n";
        String territories = "[Territories]\n";
        int result = 0;

        if (!board.isValid()) {
            return -7;
        }
        if (board == null) {
            return -1;
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

        for (String country : board.getTerritoryList()) {
            TerritoryModel aux = board.getTerritoryByName(country);
            String adj = getAdj(aux);

            if (result == 0) {
                territories += aux.getName() + "," + aux.getPositionX() + "," + aux.getPositionY() + "," + aux.getContinentName();
                if (!adj.isEmpty()) {
                    territories += "," + adj;
                }
                territories += "\n";
            } else {
                return -1;
            }

        }

        fileContent = configuration + "\n" + continents + "\n" + territories;
        result = savingFile(path, fileContent);
        return result;

    }

    /**
     * It creats a string with the adjacencies of a country
     *
     * @param country receives a country
     * @return return a string with the adj sepaarated by a ,
     */
    public static String getAdj(TerritoryModel country) {
        String adj = "";
        int i = 0;
        for (TerritoryModel aux : country.getAdj()) {
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
     */
    private static int savingFile(String path, String fileContent) {
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
            return -1;
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (Exception ex) {
                return -1;
            }
        }

    }

    /**
     * It provides the message in case of error in reading the map from a file
     *
     * @param resultReadingValidation it has a value between -1 and -6, each
     * value represent a different error
     * @return A string with the error message
     */
    public static String readingError(int resultReadingValidation) {
        switch (resultReadingValidation) {
            case -1:
                return "Error reading the file";
            case -2:
                return "Error in parameters to configurate the map.";
            case -3:
                return "Error in continent information.";
            case -4:
                return "Error in country information.";
            case -5:
                return "No territories separator in file.";
            case -6:
                return "No continents separator in file.";
            case -7:
                return "The map is not valid.";
        }
        return "Error in file format";
    }
}
