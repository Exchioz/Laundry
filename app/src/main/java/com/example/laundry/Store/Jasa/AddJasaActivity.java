package com.example.laundry.Store.Jasa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.laundry.DataHelper;
import com.example.laundry.R;

public class AddJasaActivity extends AppCompatActivity {
    private EditText etServiceName, etServicePrice;
    private DataHelper dbHelper;
    private Button btnAddService;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_jasa);

        etServiceName = findViewById(R.id.etServiceName);
        etServicePrice = findViewById(R.id.etServicePrice);
        btnAddService = findViewById(R.id.btnAddService);

        dbHelper = new DataHelper(this);

        btnAddService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tangani penambahan jasa ke database di sini
                String serviceName = etServiceName.getText().toString().trim();
                String servicePriceStr = etServicePrice.getText().toString().trim();

                if (!serviceName.isEmpty() && !servicePriceStr.isEmpty()) {
                    if (!isJasaExists(serviceName)) {
                        // Jika tidak ada, simpan jasa ke database
                        double servicePrice = Double.parseDouble(servicePriceStr);

                        saveJasaToDatabase(serviceName, servicePrice);

                        // Tampilkan pesan sukses
                        Toast.makeText(AddJasaActivity.this, "Jasa berhasil ditambahkan", Toast.LENGTH_SHORT).show();

                        // Clear input fields
                        etServiceName.getText().clear();
                        etServicePrice.getText().clear();
                    } else {
                        // Jika nama jasa sudah ada, tampilkan pesan kesalahan
                        Toast.makeText(AddJasaActivity.this, "Jasa dengan nama tersebut sudah ada", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddJasaActivity.this, "Harap isi semua field", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void saveJasaToDatabase(String nama, double harga) {
        DataHelper dbHelper = new DataHelper(this);
        userId = getIntent().getIntExtra("USER_ID", -1);
        boolean isSuccess = dbHelper.insertJasa(nama, harga, userId);

        if (isSuccess) {
            Toast.makeText(this, "Jasa berhasil ditambahkan", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AddJasaActivity.this, JasaActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Gagal menambahkan jasa", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isJasaExists(String serviceName) {
        // Menggunakan dbHelper untuk memeriksa apakah nama jasa sudah ada
        return dbHelper.isJasaExists(serviceName);
    }
}
