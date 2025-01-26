package com.foxpro.fileManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class FileComponentMain {

    /*
         # crete specific folder everytime a new_establishment created
    # file , dir -> create , save , delete
     */
    static void touch(String path) throws IOException {
        File file = new File(path);
        if ( !file.exists()){
            file.createNewFile();
        }
    }

    static void mkdir(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    static boolean isExists(String path) {
        return new File(path).exists();
    }

    static void copy(File source, File destination) {
        try {
            InputStream is = null;
            OutputStream os = null;

            try{
               is = new FileInputStream(source);
               os = new FileOutputStream(destination);

               int c ;
               byte[] buffer = new byte[1024];

               while( ( c = is.read(buffer)) > 0){
                    os.write(buffer , 0 , c);
               }
            }
            finally{
               if ( is != null){
                    is.close();
               }
               if ( os != null){
                    os.close();
               }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

}
