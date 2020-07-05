package com.example.ipingpong.shared.entities;

public enum Action {

    ViewUpdate(1), Add(2), View(3);

    private int value;

    private Action(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
