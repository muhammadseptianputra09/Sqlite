package com.example.sqlite.Database;

public class Teman {
    String id;
    String Nama;
    String Telepon;

    public Teman() {
    }

    public Teman(String id, String nama, String telepon) {
        this.id = id;
        this.Nama = nama;
        this.Telepon = telepon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getTelepon() {
        return Telepon;
    }

    public void setTelepon(String telepon) {
        Telepon = telepon;
    }
}