package com.ducky.duckythewizard.model;

import java.util.Observable;

public class CountDownTimer extends Observable { // ??? deprecated, need alternative ???

    private int countdown;

    public CountDownTimer(int startValue){
        this.countdown = startValue;
    }


    // !!! example code, needs to be changed !!!
    public void tick() {
        if (this.countdown > 0) {
            this.countdown--;
            setChanged(); // geerbt von Observable
        }
        notifyObservers(); // geerbt von Observable
    }
}
