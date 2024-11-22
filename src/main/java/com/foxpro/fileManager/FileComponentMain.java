package com.foxpro.fileManager;

import java.io.File;
import java.io.IOException;

class FileComponentMain {

    /*
         # crete specific folder everytime a new_establishment created
    # file , dir -> create , save , delete
     */
     static void touch(String path) throws IOException{
          File file = new File(path);
          file.createNewFile();
     }

     static void mkdir(String path){
          File file = new File(path);
          file.mkdirs();
     }

     static boolean isExists(String path){
          return new File(path).exists();
     }

}
