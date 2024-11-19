package com.foxpro.fileManager;

import java.io.IOException;

public class FileComponentHandler {

    private static final String OS_PATH_DELIMITER = System.getProperty("os.name").contains("win") ? "\\" : "/";

    public static String generatePath(String root, String[] leaves) {
        for (String s : leaves) {
            root += (OS_PATH_DELIMITER + s);
        }
        return root;
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

}
