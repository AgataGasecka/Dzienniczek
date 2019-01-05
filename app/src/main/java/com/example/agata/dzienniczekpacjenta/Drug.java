package com.example.agata.dzienniczekpacjenta;

public class Drug {
    private String data;
    private String godzina;
    private String wynik;

    public Drug(String data, String godzina, String wynik) {
        this.data = data;
        this.godzina = godzina;
        this.wynik = wynik;
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
}
