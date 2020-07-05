package com.example.ipingpong.shared.entities;

public class Player {

    private int id, playerNumber, level;
    private User user;
    private String handUsed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getHandUsed() {
        return handUsed;
    }

    public void setHandUsed(String handUsed) {
        this.handUsed = handUsed;
    }



}
