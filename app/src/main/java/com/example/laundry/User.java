package com.example.laundry;

public class User {
    private int id;
    private String username;
    private String password;
    private String nama;
    private String nomerTlp;
    private String email;
    private String alamat;
    private int tipe;

    public User() {
        this.id = this.id;
        this.username = this.username;
        this.password = this.password;
        this.nama = this.nama;
        this.nomerTlp = this.nomerTlp;
        this.email = this.email;
        this.alamat = this.alamat;
        this.tipe = this.tipe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter and setter for password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Getter and setter for nama
    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getNotlp() {
        return nomerTlp;
    }

    public void setNoTlp(String nomerTlp) {
        this.nomerTlp = nomerTlp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    // Getter and setter for email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTipe() {
        return tipe;
    }

    public void setTipe(int tipe) {
        this.tipe = tipe;
    }
}
