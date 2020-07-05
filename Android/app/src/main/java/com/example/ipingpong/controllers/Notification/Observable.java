package com.example.ipingpong.controllers.Notification;

import java.util.ArrayList;

public abstract class Observable {

    public ArrayList<String> emails = new ArrayList<>();
    public ArrayList<String> numbers = new ArrayList<>();
    public String massageContent;
    public String massageSubject;

    abstract void attached(Observer object);
    abstract void deatched(Observer object);
    abstract void notified();

}
