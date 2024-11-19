package com.foxpro;

import java.io.IOException;


public class App {
    /*
     * to - do
     * Config.getPathToPrntFolder();
     * EstablishmentDatabaseHandler.initiateConnection() -> to database Main
      */



    public static void run(){
      try {
        Cmd cmd = new Cmd();
        cmd.run(Config.getPathMain());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }


    public static void main(String[] args) {
      run();
    }
}
