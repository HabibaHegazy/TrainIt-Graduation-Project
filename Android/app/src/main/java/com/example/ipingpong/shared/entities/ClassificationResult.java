package com.example.ipingpong.shared.entities;

public class ClassificationResult {

    private int id;
    private String date;
    private String errorType;
    private boolean isMistake;
    private String strokeType;
    private int readed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public boolean isMistake() {
        return isMistake;
    }

    public void setMistake(boolean mistake) {
        isMistake = mistake;
    }

    public String getStrokeType() {
        return strokeType;
    }

    public void setStrokeType(String strokeType) {
        this.strokeType = strokeType;
    }

    public int getReaded() {
        return readed;
    }

    public void setReaded(int readed) {
        this.readed = readed;
    }
}
