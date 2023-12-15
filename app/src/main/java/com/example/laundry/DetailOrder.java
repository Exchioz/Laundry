package com.example.laundry;

public class DetailOrder {
    private int id;
    private static int idOrder;
    private static int idJasa;
    private static int jumlah;


    public int getId() {
        return id;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdJasa(int idJasa) {
        this.idJasa = idJasa;
    }

    public static int getJumlah() {
        return jumlah;
    }
    public static int getIdJasa() {
        return idJasa;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }
}
