package com.example.laundry.Store.Pesanan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.laundry.DataHelper;
import com.example.laundry.Order;
import com.example.laundry.R;
import com.example.laundry.Store.StoreActivity;

import java.util.ArrayList;
import java.util.List;

public class ViewPesananActivity extends AppCompatActivity {

    private ListView listViewPesanan;
    private List<String> pesananList;
    Button btnBack;
    private ArrayAdapter<String> adapter;

    private DataHelper dbHelper;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesanan);

        // Mendapatkan userId dari intent
        //userId = getIntent().getIntExtra("USER_ID", -1);

        // Inisialisasi komponen UI
        listViewPesanan = findViewById(R.id.listViewPesanan);
        btnBack = findViewById(R.id.buttonKembali);
        pesananList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, pesananList);
        listViewPesanan.setAdapter(adapter);

        dbHelper = new DataHelper(this);

        tampilkanPesanan();

        listViewPesanan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Dapatkan ID pesanan dari posisi item yang dipilih
                Order selectedOrder = dbHelper.getAllPesanan().get(position);
                int orderId = (int) selectedOrder.getId();

                // Panggil aktivitas DetailViewOrder dan kirimkan ID pesanan
                Intent intent = new Intent(ViewPesananActivity.this, DetailViewPesanan.class);
                intent.putExtra("ORDER_ID", orderId);
                startActivity(intent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tambahkan pesanan ke database
                Intent orderview = new Intent(ViewPesananActivity.this, StoreActivity.class);
                startActivity(orderview);
                finish();
            }
        });

    }

    private void tampilkanPesanan() {
        List<Order> orderList = dbHelper.getAllPesanan();

        pesananList.clear(); // Membersihkan daftar sebelum menambahkan pesanan baru

        for (Order order : orderList) {
            String pesananInfo = "ID Pesanan: " + order.getId() +
                    "\nNama: " + dbHelper.getNamaById(order.getUserId()) +
                    "\nTanggal Order: " + order.getTanggalOrder() +
                    "\nStatus: " + order.getStatus();

            pesananList.add(pesananInfo);
        }

        // Memberitahu adapter bahwa data telah berubah
        adapter.notifyDataSetChanged();
    }
}