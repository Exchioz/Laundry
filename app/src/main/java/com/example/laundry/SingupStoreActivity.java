package com.example.laundry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SingupStoreActivity extends AppCompatActivity {

    EditText etUsername, etPassword, etNamaToko, etNoTelpToko, etEmailToko,
            etAlamatToko, etInfoToko, etHariOperasional, etWaktuOperasional;
    Button btnSignUp, btnSignin;

    private DataHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup_store);

        dbHelper = new DataHelper(this);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etNamaToko = findViewById(R.id.etNama);
        etNoTelpToko = findViewById(R.id.etNoTlp);
        etEmailToko = findViewById(R.id.etEmail);
        etAlamatToko = findViewById(R.id.etAlamat);
        etInfoToko = findViewById(R.id.etInfo);
        etHariOperasional = findViewById(R.id.etHari);
        etWaktuOperasional = findViewById(R.id.etJam);
        btnSignUp = findViewById(R.id.btnSignup);
        btnSignin = findViewById(R.id.btnSignin);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpStore();
            }
        });
    }

    private void signUpStore() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String namaToko = etNamaToko.getText().toString().trim();
        String noTelpToko = etNoTelpToko.getText().toString().trim();
        String emailToko = etEmailToko.getText().toString().trim();
        String alamatToko = etAlamatToko.getText().toString().trim();
        String infoToko = etInfoToko.getText().toString().trim();
        String hariOperasional = etHariOperasional.getText().toString().trim();
        String waktuOperasional = etWaktuOperasional.getText().toString().trim();

        if(username.equals("")||password.equals("")){
            Toast.makeText(SingupStoreActivity.this, "tolong masukan username dan password", Toast.LENGTH_SHORT).show();
        } else {
            if(username.equals(password)) {
                Boolean cekuser = dbHelper.cekusernameStore(username);
                if (cekuser == false) {
                    Boolean insert = dbHelper.insertDataStore(username, password, namaToko, noTelpToko, emailToko, alamatToko, infoToko, hariOperasional, waktuOperasional);
                    if (insert == true) {
                        Toast.makeText(SingupStoreActivity.this, "register berhasil", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                    } else {
                        SQLiteException e = dbHelper.getLastException();
                        Toast.makeText(SingupStoreActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SingupStoreActivity.this, "nama pengguna sudah ada, silahkan log in", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(SingupStoreActivity.this, "harap isi password", Toast.LENGTH_SHORT).show();
            }

        }

        btnSignin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Intent sign_in = new Intent(getApplicationContext(), com.example.laundry.LoginActivity.class);
                    startActivity(sign_in);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(SingupStoreActivity.this, "Terjadi kesalahan: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}