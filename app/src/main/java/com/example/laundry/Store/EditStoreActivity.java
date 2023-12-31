package com.example.laundry.Store;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.laundry.DataHelper;
import com.example.laundry.R;
import com.example.laundry.Toko;

public class EditStoreActivity extends AppCompatActivity {

    private DataHelper dbHelper;
    private EditText editTextUsername, editTextPassword, editTextNama,
            editTextNotlp, editTextEmail, editTextAlamat;
    private Button buttonUpdate, buttonBack;
    private int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Inisialisasi EditText
        editTextUsername = findViewById(R.id.etUsername);
        editTextPassword = findViewById(R.id.etPassword);
        editTextNama = findViewById(R.id.etNama);
        editTextNotlp = findViewById(R.id.etNoTlp);
        editTextEmail = findViewById(R.id.etEmail);
        editTextAlamat = findViewById(R.id.etAlamat);

        // Inisialisasi buttonUpdate
        buttonUpdate = findViewById(R.id.btnUpdate);

        // Mendapatkan ID dari Intent
        userID = getIntent().getIntExtra("USER_ID", -1);

        // Mengambil data dari database berdasarkan ID
        dbHelper = new DataHelper(this);
        Toko store = dbHelper.getStoreByID(userID);

        // Menampilkan data di EditText
        editTextUsername.setText(store.getUsername());
        editTextPassword.setText(store.getPassword());
        editTextNama.setText(store.getNama());
        editTextNotlp.setText(store.getNotlp());
        editTextEmail.setText(store.getEmail());
        editTextAlamat.setText(store.getAlamat());

        // Set listener untuk buttonUpdate
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUserData();
            }
        });
    }

    private void updateUserData() {
        // Mendapatkan nilai dari EditText
        String newUsername = editTextUsername.getText().toString();
        String newPassword = editTextPassword.getText().toString();
        String newNama = editTextNama.getText().toString();
        String newNomerTlp = editTextNotlp.getText().toString();
        String newEmail = editTextEmail.getText().toString();
        String newAlamat = editTextAlamat.getText().toString();

        // Memperbarui data pengguna
        DataHelper dataHelper = new DataHelper(this);
        boolean isUpdated = dataHelper.updateDataStore(userID, newUsername, newPassword, newNama, newNomerTlp, newEmail, newAlamat);

        if (isUpdated) {
            Toast.makeText(EditStoreActivity.this, "Data berhasil diperbarui", Toast.LENGTH_SHORT).show();
            Intent back = new Intent(EditStoreActivity.this, StoreActivity.class);
            back.putExtra("USER_ID", userID);
            startActivity(back);
            finish();
        } else {
            Toast.makeText(EditStoreActivity.this, "Gagal memperbarui data", Toast.LENGTH_SHORT).show();
        }
    }
}
