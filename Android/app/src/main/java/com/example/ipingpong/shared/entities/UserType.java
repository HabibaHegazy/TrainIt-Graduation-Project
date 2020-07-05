package com.example.ipingpong.shared.entities;

public enum UserType {
    COACH(2), PLAYER(1), ClubManager(3), Admin(4);

    private int value;

    UserType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
