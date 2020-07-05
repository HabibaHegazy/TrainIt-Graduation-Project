package com.example.ipingpong.shared.entities;

public enum DialogEntities {
    ChangePassword(1), CountDown(2), Notification(3), DatasetData(4);

    private int value;

    private DialogEntities(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
