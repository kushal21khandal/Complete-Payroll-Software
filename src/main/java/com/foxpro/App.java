package com.foxpro;

import java.io.IOException;

public class App {

    public static void run() {
        try {
            Logs logs = new Logs(   );
            Cmd cmd = new Cmd();
            cmd.run(Config.getPathMain());
            logs.closeLogFileHandler();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        run();
        System.gc();
        System.exit(0);
    }
}
