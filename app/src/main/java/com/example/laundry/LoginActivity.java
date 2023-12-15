package com.example.laundry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laundry.DataHelper;

public class LoginActivity extends AppCompatActivity {

    protected Cursor cursor;
    DataHelper dbHelper;
    Button btnlogin, btnsignup, btnstore;
    EditText username, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DataHelper(this);
        username = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPassword);
        btnlogin = findViewById(R.id.btnLogin);
        btnsignup = findViewById(R.id.btnSignup);
        btnstore = findViewById(R.id.btnLoginStore);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usern = username.getText().toString();
                String passw = password.getText().toString();

                if (usern.equals("") || passw.equals("")) {
                    Toast.makeText(LoginActivity.this, "Tolong masukkan username dan password", Toast.LENGTH_SHORT).show();
                } else {
                    int userId = dbHelper.getUserIdByUsername(usern); // Assuming you have a method to get user ID
                    if (isCredentialsValid(usern, passw)) {
                        User user = dbHelper.getUserByID(userId);
                        Toast.makeText(LoginActivity.this, "Login berhasil, Selamat datang " + userId, Toast.LENGTH_LONG).show();
                        Intent login = new Intent(LoginActivity.this, MainActivity.class);
                        login.putExtra("USER_ID", userId);
                        startActivity(login);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Username atau password salah", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sign_up = new Intent(getApplicationContext(), com.example.laundry.SignupActivity.class);
                startActivity(sign_up);
            }
        });

        btnstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginstore = new Intent(getApplicationContext(), com.example.laundry.LoginStoreActivity.class);
                startActivity(loginstore);
            }
        });
    }
    private boolean isCredentialsValid(String username, String password) {
        boolean isUsernameValid = dbHelper.cekusername(username);
        boolean isPasswordValid = dbHelper.cekpassword(username, password);

        return isUsernameValid && isPasswordValid;
    }
}