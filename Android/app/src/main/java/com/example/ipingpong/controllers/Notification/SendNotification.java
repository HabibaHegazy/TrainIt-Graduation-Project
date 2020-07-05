package com.example.ipingpong.controllers.Notification;

import java.util.ArrayList;

public class SendNotification extends Observable {

    protected ArrayList<Observer> observers  = new ArrayList<>();

    public SendNotification() {
        Facade facade = new Facade();
        this.attached(facade.email);
        this.attached(facade.sms);
    }

    @Override
    void attached(Observer object) {
        boolean found = observers.contains(object);
        if (!found) {
            this.observers.add(object);
        }
    }

    @Override
    void deatched(Observer object) {
        if(!observers.isEmpty()){
            boolean found = observers.contains(object);
            if(found){
                observers.remove(object);
//                for(int i =0; i < observers.size(); i++){
//                    if(object == observers.get(i)){
//                        observers.remove(i);
//                    }
//                }
            }
        }
    }

    @Override
    void notified() {
        if(!observers.isEmpty()){
            for (Observer ob : observers) {
                // do something with object
                ob.sendMsg(this);
            }
        }
    }
}
