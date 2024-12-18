package com.foxpro.fileManager;

import java.io.IOException;
import java.lang.StringBuilder;

public class FileComponentHandler {

    private static final String OS_PATH_DELIMITER = System.getProperty("os.name").contains("win") ? "\\" : "/";


    public static String generatePath(String root, String[] leaves) {
        StringBuilder stringBuilder = new StringBuilder(root);
        for (String s : leaves) {
            stringBuilder.append(OS_PATH_DELIMITER);
            stringBuilder.append(s);
        }
        return stringBuilder.toString();
    }

    public static void createDir(String parent, String[] childArgs) {
        String path = generatePath(parent, childArgs);
        FileComponentMain.mkdir(path);
    }

    public static void createFile(String parent , String[] childArgs){
        String path = generatePath(parent, childArgs);
        try {
            FileComponentMain.touch(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isExists(String path){
        return FileComponentMain.isExists(path);
    }

}
