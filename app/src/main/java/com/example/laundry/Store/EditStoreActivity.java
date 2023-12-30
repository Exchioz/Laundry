package com.example.laundry.Store;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.laundry.R;

public class EditStoreActivity extends AppCompatActivity {

    int storeId; // Deklarasikan variabel untuk menyimpan ID toko

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_store);

        // Terima data dari Intent yang dikirim dari LoginStoreActivity
        Intent intent = getIntent();
        if (intent != null) {
            storeId = intent.getIntExtra("STORE_ID", 0); // 0 adalah nilai default jika tidak ada data yang dikirim
        }

        // Gunakan storeId sesuai kebutuhan di sini untuk mengedit data toko
        // Misalnya, gunakan storeId untuk mengambil data toko dari database
        // dan tampilkan pada tampilan EditStoreActivity
    }
}
