package com.example.laundry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class StoreActivity extends AppCompatActivity {
    private DataHelper dbHelper;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        dbHelper = new DataHelper(this);

        // Retrieve the user ID from the Intent
        userId = getIntent().getIntExtra("USER_ID", -1);

        if (userId != -1) {
            // Fetch and display the user's name based on the user ID
            String nama = dbHelper.getNamaByStoreId(userId);

            // Display the user's name in a TextView
            TextView welcomeTextView = findViewById(R.id.welcomeTextView);
            welcomeTextView.setText("Welcome, " + nama + "!");

            // Add a button for editing
            Button editButton = findViewById(R.id.btnEdit);
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Open EditProfileActivity and pass the user ID
                    Toast.makeText(StoreActivity.this, "Mengedit " + userId, Toast.LENGTH_LONG).show();
                    Intent editIntent = new Intent(StoreActivity.this, EditStoreActivity.class);
                    editIntent.putExtra("USER_ID", userId);
                    startActivity(editIntent);
                }
            });

            Button jasaButton = findViewById(R.id.btnJasa);
            jasaButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Open EditProfileActivity and pass the user ID
                    //Toast.makeText(StoreActivity.this, "Mengedit " + userId, Toast.LENGTH_LONG).show();
                    Intent jasaIntent = new Intent(StoreActivity.this, JasaActivity.class);
                    jasaIntent.putExtra("USER_ID", userId);
                    startActivity(jasaIntent);
                }
            });
        } else {
            finish();
        }
    }
}