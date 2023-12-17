package com.example.laundry.Store;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.laundry.DataHelper;
import com.example.laundry.Users.LoginActivity;
import com.example.laundry.R;

public class LoginStoreActivity extends AppCompatActivity {

    protected Cursor cursor;
    DataHelper dbHelper;
    Button btnlogin, btnstore;
    EditText username, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_store);

        dbHelper = new DataHelper(this);
        username = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPassword);
        btnlogin = findViewById(R.id.btnLogin);
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

        btnstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginuser = new Intent(getApplicationContext(), LoginActivity.class);
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