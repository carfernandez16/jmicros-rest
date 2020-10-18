package com.jmicros;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    private Config config;

    public Main(Config config){
        this.config = config;
    }

    private void run() {
        showArt();
    }

    private void showArt() {
        logger.info("   ___ ___  ___ _                        ");
        logger.info("  |_  ||  \\/  |(_)                       ");
        logger.info("    | || .  . | _   ___  _ __  ___   ___ ");
        logger.info("    | || |\\/| || | / __|| '__|/ _ \\ / __|");
        logger.info("/\\__/ /| |  | || || (__ | |  | (_) |\\__ \\");
        logger.info("\\____/ \\_|  |_/|_| \\___||_|   \\___/ |___/");
    }

    public static void main(String[] args){
        Config config = new Config();
        Main main = new Main(config);
        main.run();
    }

}
