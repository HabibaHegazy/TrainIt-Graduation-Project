package com.example.ipingpong.controllers.Notification;

public class Facade {

    public SMS sms;
    public Email email;

    public Facade() {
        this.sms = new SMS();
        this.email = new Email();
    }
}
