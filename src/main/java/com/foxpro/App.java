package com.foxpro;

import java.io.IOException;


public class App {



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
