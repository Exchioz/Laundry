package com.example.laundry;

public class DetailOrder {
    private int id;
    private static int idOrder;
    private static String nama;
    private static int harga;
    private static int jumlah;
    private static int totalHarga;

    public DetailOrder (int id, int orderId, String nama, int harga, int jumlah, int totalHarga){
        this.id = id;
        this.idOrder = orderId;
        this.nama = nama;
        this.harga = harga;
        this.jumlah = jumlah;
        this.totalHarga = totalHarga;
    }


    public int getId() {
        return id;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }
}
