package com.example.laundry;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    protected Cursor cursor;
    DataHelper dbHelper;
    Button btnsingup, btnsingin;
    EditText username, password, nama, notlp, email, alamat;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        dbHelper = new DataHelper(this);
        username = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPassword);
        nama = findViewById(R.id.etNama);
        notlp = findViewById(R.id.etNoTlp);
        email = findViewById(R.id.etEmail);
        alamat = findViewById(R.id.etAlamat);
        btnsingup = findViewById(R.id.btnSignup);
        btnsingin = findViewById(R.id.btnSignin);

        btnsingup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_usern = username.getText().toString();
                String str_passw = password.getText().toString();
                String str_nama = nama.getText().toString();
                String str_notlp = notlp.getText().toString();
                String str_email = email.getText().toString();
                String str_alamat = alamat.getText().toString();

                if(str_usern.equals("")||str_passw.equals("")){
                    Toast.makeText(SignupActivity.this, "tolong masukan username dan password", Toast.LENGTH_SHORT).show();
                } else {
                    if(str_passw.equals(str_passw)) {
                        Boolean cekuser = dbHelper.cekusername(str_usern);
                        if (cekuser == false) {
                            Boolean insert = dbHelper.insertData(str_usern, str_passw, str_nama, str_notlp, str_email, str_alamat);
                            if (insert == true) {
                                Toast.makeText(SignupActivity.this, "register berhasil", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                            } else {
                                SQLiteException e = dbHelper.getLastException();
                                Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(SignupActivity.this, "nama pengguna sudah ada, silahkan log in", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SignupActivity.this, "harap isi password", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

        btnsingin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Intent sign_in = new Intent(getApplicationContext(), com.example.laundry.LoginActivity.class);
                    startActivity(sign_in);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(SignupActivity.this, "Terjadi kesalahan: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
