package com.example.agata.dzienniczekpacjenta;

public class Pomiar {
    private String data;
    private String godzina;
    private String wynik;

    public Pomiar(String data, String godzina, String wynik) {
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

    public String getWynik() {
        return wynik;
    }
}
