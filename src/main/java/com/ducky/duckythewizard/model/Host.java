package com.ducky.duckythewizard.model;

// pattern: SINGLETON
public class Host extends Player{

    private static Host INSTANCE;

    private Host(String name) {
        super();
        this.setPlayerName(name);
    }

    public static Host getInstance(){
        return INSTANCE;
    }

    public static void createHostInstance(String name){
        if (INSTANCE == null){
            INSTANCE = new Host(name);
        }
    }
}
