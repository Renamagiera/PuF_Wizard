package com.ducky.duckythewizard.model;

public class GameObject {

    protected boolean canPassThrough;

    public GameObject(boolean canPassThrough) {
        this.canPassThrough = canPassThrough;
    }

    public boolean getCanPassThrough(){
        return this.canPassThrough;
    }

}
