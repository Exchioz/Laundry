package com.example.laundry.Store.Jasa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.laundry.DataHelper;
import com.example.laundry.Jasa;
import com.example.laundry.R;
import com.example.laundry.Store.StoreActivity;
import com.example.laundry.Users.Pesanan.DetailViewOrder;
import com.example.laundry.Users.Pesanan.ViewOrderActivity;

public class JasaActivity extends AppCompatActivity {

    private ListView listViewJasa;
    private JasaAdapter jasaAdapter;
    private DataHelper dbHelper;

    private Button btnTambahJasa, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jasa);

        dbHelper = new DataHelper(this);
        listViewJasa = findViewById(R.id.listViewJasa);
        btnTambahJasa = findViewById(R.id.btnTambah);
        btnBack = findViewById(R.id.btnBack);

        // Setup adapter for the ListView
        jasaAdapter = new JasaAdapter(this, dbHelper.getAllJasa());
        listViewJasa.setAdapter(jasaAdapter);

        // Set click listeners for edit and delete buttons
        listViewJasa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Jasa jasa = (Jasa) parent.getItemAtPosition(position);
                // Handle item click (e.g., open edit activity)
                // You can use jasa.getId() to get the ID of the selected jasa
            }
        });

        listViewJasa.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Jasa jasa = (Jasa) parent.getItemAtPosition(position);
                // Handle long click (e.g., show delete confirmation dialog)
                // You can use jasa.getId() to get the ID of the selected jasa
                return true;
            }
        });

        btnTambahJasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click event, e.g., open activity to add a new jasa
                Intent intent = new Intent(JasaActivity.this, AddJasaActivity.class);
                startActivity(intent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tambahkan pesanan ke database
                Intent orderview = new Intent(JasaActivity.this, StoreActivity.class);
                startActivity(orderview);
                finish();
            }
        });
    }
}
