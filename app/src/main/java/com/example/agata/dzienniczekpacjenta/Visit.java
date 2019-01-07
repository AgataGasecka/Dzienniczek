package com.example.agata.dzienniczekpacjenta;

public class Visit {
    private String date;
    private String hour;
    private String doctor;
    private String place;
    private String info;
    private int id;

    public Visit(String date, String hour, String doctor, String place, String info, int id) {
        this.date = date;
        this.hour = hour;
        this.doctor = doctor;
        this.place = place;
        this.info = info;
        this.id = id;
    }

    public Visit(String date, String hour, String doctor, String place, String info) {
        this.date = date;
        this.hour = hour;
    }

    public String getDate() {
        return date;
    }

    public String getHour() {
        return hour;
    }

    public String getDoctor() {
        return doctor;
    }

    public String getPlace() {
        return place;
    }

    public String getInfo() {
        return info;
    }

    public int getID() { return id; }
}
