package com.example.laundry;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {

    private DataHelper dbHelper;
    private EditText editTextID, editTextUsername, editTextPassword, editTextNama,
            editTextNotlp, editTextEmail, editTextAlamat;
    private Button buttonUpdate, buttonBack;
    private int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Inisialisasi EditText
        editTextID = findViewById(R.id.etId);
        editTextUsername = findViewById(R.id.etUsername);
        editTextPassword = findViewById(R.id.etPassword);
        editTextNama = findViewById(R.id.etNama);
        editTextNotlp = findViewById(R.id.etNoTlp);
        editTextEmail = findViewById(R.id.etEmail);
        editTextAlamat = findViewById(R.id.etAlamat);

        // Inisialisasi buttonUpdate
        buttonUpdate = findViewById(R.id.btnUpdate);
        buttonBack = findViewById(R.id.btnBack);

        // Mendapatkan ID dari Intent
        userID = getIntent().getIntExtra("USER_ID", -1);

        // Mengambil data dari database berdasarkan ID
        dbHelper = new DataHelper(this);
        User user = dbHelper.getUserByID(userID);

        // Menampilkan data di EditText
        editTextID.setText(String.valueOf(user.getId()));
        editTextUsername.setText(user.getUsername());
        editTextPassword.setText(user.getPassword());
        editTextNama.setText(user.getNama());
        editTextNotlp.setText(user.getNotlp());
        editTextEmail.setText(user.getEmail());
        editTextAlamat.setText(user.getAlamat());

        // Set listener untuk buttonUpdate
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUserData();
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backButton();
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
        boolean isUpdated = dataHelper.updateData(userID, newUsername, newPassword, newNama, newNomerTlp, newEmail, newAlamat);

        if (isUpdated) {
            Toast.makeText(EditProfileActivity.this, "Data berhasil diperbarui", Toast.LENGTH_SHORT).show();
            Intent back = new Intent(EditProfileActivity.this, MainActivity.class);
            back.putExtra("USER_ID", userID);
            startActivity(back);
            finish();
        } else {
            Toast.makeText(EditProfileActivity.this, "Gagal memperbarui data", Toast.LENGTH_SHORT).show();
        }
    }

    private void backButton(){
        dbHelper = new DataHelper(this);
        Intent back = new Intent(EditProfileActivity.this, MainActivity.class);
        back.putExtra("USER_ID", userID);
        startActivity(back);
        finish();
    }
}
