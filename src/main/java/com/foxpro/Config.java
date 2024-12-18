package com.foxpro;

import java.io.IOException;
import java.util.Properties;


class Config{

    private static final Properties properties = new Properties();


    static {
        try{
            properties.load(Config.class.getClassLoader().getResourceAsStream("config.properties"));
        }
        catch(IOException exception){
            exception.printStackTrace();
        }
    }


    public static String getPathMain(){
        return properties.getProperty("path");
    }

    public static int getThisYear(){
        return Integer.parseInt(properties.getProperty("year"));
    }

    public static double getPfDeductionPercentage(){
        return Double.parseDouble(properties.getProperty("pf_deduction_percentage"));
    }
    public static double getEsicDeductionPercentage(){
        return Double.parseDouble(properties.getProperty("esic_deduction_percentage"));
    }


    public static String getPathLogFile(){
        return properties.getProperty("pathLogFile");
    }



}