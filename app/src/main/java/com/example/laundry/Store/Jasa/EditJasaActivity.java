package com.example.laundry.Store.Jasa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.laundry.DataHelper;
import com.example.laundry.Jasa;
import com.example.laundry.R;

public class EditJasaActivity extends AppCompatActivity {

    private EditText editTextNama, editTextHarga;
    private Button btnUpdate;
    private int jasaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_jasa);

        editTextNama = findViewById(R.id.editTextNama);
        editTextHarga = findViewById(R.id.editTextHarga);
        btnUpdate = findViewById(R.id.btnUpdate);

        // Get the Jasa ID from the Intent
        jasaId = getIntent().getIntExtra("JASA_ID", -1);

        // Fetch the existing Jasa details based on ID and populate the fields
        DataHelper dbHelper = new DataHelper(this);
        Jasa jasa = dbHelper.getJasaById(jasaId);

        if (jasa != null) {
            editTextNama.setText(jasa.getNama());
            editTextHarga.setText(String.valueOf(jasa.getHarga()));
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateJasa(jasaId);
            }
        });
    }

    private void updateJasa(int jasaId) {
        String nama = editTextNama.getText().toString();
        double harga = Double.parseDouble(editTextHarga.getText().toString());

        DataHelper dbHelper = new DataHelper(this);
        boolean isSuccess = dbHelper.updateJasa(jasaId, nama, harga);

        if (isSuccess) {
            Toast.makeText(this, "Jasa berhasil diperbarui", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(EditJasaActivity.this, JasaActivity.class);
            startActivity(intent);
            finish(); // Close the EditJasaActivity after successful update
        } else {
            Toast.makeText(this, "Gagal memperbarui jasa", Toast.LENGTH_SHORT).show();
        }
    }
}
