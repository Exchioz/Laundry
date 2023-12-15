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

public class LoginStoreActivity extends AppCompatActivity {

    protected Cursor cursor;
    DataHelper dbHelper;
    Button btnlogin, btnsignup, btnstore;
    EditText username, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_store);

        dbHelper = new DataHelper(this);
        username = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPassword);
        btnlogin = findViewById(R.id.btnLogin);
        btnsignup = findViewById(R.id.btnSignup);
        btnstore = findViewById(R.id.btnLoginUser);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usern = username.getText().toString();
                String passw = password.getText().toString();

                if (usern.equals("") || passw.equals("")) {
                    Toast.makeText(LoginStoreActivity.this, "Tolong masukkan username dan password", Toast.LENGTH_SHORT).show();
                } else {
                    int storeId = dbHelper.getStoreIdByUsername(usern); // Assuming you have a method to get user ID
                    if (isCredentialsValid(usern, passw)) {
                        Toast.makeText(LoginStoreActivity.this, "Login berhasil, Selamat datang di Toko" + storeId, Toast.LENGTH_LONG).show();
                        Intent storeIntent = new Intent(LoginStoreActivity.this, StoreActivity.class);
                        storeIntent.putExtra("USER_ID", storeId);
                        startActivity(storeIntent);
                        finish();
                    } else {
                        Toast.makeText(LoginStoreActivity.this, "Username atau password salah", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sign_up = new Intent(getApplicationContext(), com.example.laundry.SingupStoreActivity.class);
                startActivity(sign_up);
            }
        });

        btnstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginuser = new Intent(getApplicationContext(), com.example.laundry.LoginActivity.class);
                startActivity(loginuser);
            }
        });
    }
    private boolean isCredentialsValid(String username, String password) {
        boolean isUsernameValid = dbHelper.cekusernamestore(username);
        boolean isPasswordValid = dbHelper.cekpasswordstore(username, password);

        return isUsernameValid && isPasswordValid;
    }
}