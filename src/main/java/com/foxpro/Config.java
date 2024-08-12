package com.foxpro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

static class Config {

    /*
     * inputStream to config file already defined to the config.txt in the resource
     * folder gloabally
     *
     * functions : static
     * getPathToPrntFolder() -> if( path not present ) -> terminate the program ,
     * but does the check the validity of the path ( valid dir or wrong path )
     *
     */

    static {
        InputStream inputStreamToConfigFile = Config.class.getClassLoader().getResourceAsStream("config.txt");
        String pathToPrntFolder;
        Pattern pattern;
        Matcher matcher;
    }

    Config(){
        pathToPrntFolder = getPathToPrntFolder();
    }

    public static String getPathToPrntFolder() {
        pattern = Pattern.compile("path:(\\S+)");
        String path = "";
        matcher = pattern.matcher(path);

        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(inputStreamToConfigFile);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            outer: while ((path = bufferedReader.readLine().trim()) != null) {
                matcher.reset(path);
                if (matcher.matches() == true) {
                    while (matcher.find()) {
                        path = matcher.group(1);
                        break outer;
                    }
                }
            }

            if (path == "") {
                System.out.println("path could not be found");
                System.exit(0);

            }
            bufferedReader.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            if (inputStreamToConfigFile != null) {
                inputStreamToConfigFile.close();
            }
        }
        return path;

    }
}