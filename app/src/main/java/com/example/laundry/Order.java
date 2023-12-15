package com.example.laundry;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
public class Order {
    private long id;
    private int userId;
    private String tanggalOrder;
    private String status;

    public Order(long id, int userId, String tanggalOrder, String status) {
        this.id = id;
        this.userId = userId;
        this.tanggalOrder = tanggalOrder;
        this.status = status;
    }

    public long getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public String getTanggalOrder() {
        return tanggalOrder;
    }
    public void setTanggalOrder(String tanggalOrder) {
        this.tanggalOrder = tanggalOrder;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
