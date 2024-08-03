package com.foxpro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public static class Config {

    #
    read config.txt from
    resource folder

    static {
        public String pathToPrntFolder;
        InputStream inputStream = Config.class.getClassLoader().getResourceAsStream("config.txt");
    }

    public static String getPrntPathFromConfig(){
        InputStreamReader inputStreamReader = null;

        String newLine , path;

        Pattern pathRegex = Pattern.compile("path:(\\S+)")
        Matcher matcher = pathRegex.matcher(newLine);

        try{
            inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            while((newLine = bufferedReader.readLine()) != null){
                matcher.reset(newLine);
                if ( matcher.matches()){
                    while(matcher.find()){
                        path = matcher.group(1);
                    }
                }
            }
            bufferedReader.close();
        }
        catch(IOException exception){
            exception.printStackTrace();
        }
        finally{
            if ( inputStreamReader != null){
                inputStreamReader = null;
            }
        }
        return path;
    }





}