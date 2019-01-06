package com.example.agata.dzienniczekpacjenta;

public class Drug {
    private String data;
    private String godzina;
    private String wynik;
    private String nazwa;

    public Drug(String data, String godzina,String nazwa, String wynik) {
        this.data = data;
        this.godzina = godzina;
        this.wynik = wynik;
        this.nazwa=nazwa;
    }

    public String getData() {
        return data;
    }

    public String getGodzina() {
        return godzina;
    }
    //wynik dawki
    public String getWynik() {
        return wynik;
    }

    public String getNazwa() {
        return nazwa;
    }
}
