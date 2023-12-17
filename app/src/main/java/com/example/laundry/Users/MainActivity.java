package com.example.laundry.Users;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.laundry.DataHelper;
import com.example.laundry.Users.Pesanan.OrderJasaActivity;
import com.example.laundry.R;
import com.example.laundry.Users.Pesanan.ViewOrderActivity;

public class MainActivity extends AppCompatActivity {
    private DataHelper dbHelper;
    private int userId; // Assuming userId is a class variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DataHelper(this);

        // Retrieve the user ID from the Intent
        userId = getIntent().getIntExtra("USER_ID", -1);

        if (userId != -1) {
            // Fetch and display the user's name based on the user ID
            String nama = dbHelper.getNamaById(userId);

            // Display the user's name in a TextView
            TextView welcomeTextView = findViewById(R.id.welcomeTextView);
            welcomeTextView.setText("Welcome, " + nama + "!");

            // Add a button for editing
            Button editButton = findViewById(R.id.btnEdit);
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Open EditProfileActivity and pass the user ID
                    Toast.makeText(MainActivity.this, "Mengedit " + userId, Toast.LENGTH_LONG).show();
                    Intent editIntent = new Intent(MainActivity.this, EditProfileActivity.class);
                    editIntent.putExtra("USER_ID", userId);
                    startActivity(editIntent);
                }
            });

            Button btnPesan = findViewById(R.id.btnPesan);
            btnPesan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Open EditProfileActivity and pass the user ID
                    Intent editIntent = new Intent(MainActivity.this, OrderJasaActivity.class);
                    editIntent.putExtra("USER_ID", userId);
                    startActivity(editIntent);
                }
            });

            Button btnPesanan = findViewById(R.id.btnPesanan);
            btnPesanan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Open EditProfileActivity and pass the user ID
                    Intent viewIntent = new Intent(MainActivity.this, ViewOrderActivity.class);
                    viewIntent.putExtra("USER_ID", userId);
                    startActivity(viewIntent);
                }
            });

            Button btnLogout = findViewById(R.id.btnLogout);
            btnLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            });
        } else {
            finish();
        }
    }
}
