package com.example.laundry.Users.Pesanan;

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
import com.example.laundry.Users.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class ViewOrderActivity extends AppCompatActivity {

    private ListView listViewPesanan;
    private List<String> pesananList;
    private ArrayAdapter<String> adapter;

    Button btnBack;

    private DataHelper dbHelper;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);

        // Mendapatkan userId dari intent
        userId = getIntent().getIntExtra("USER_ID", -1);

        // Inisialisasi komponen UI
        listViewPesanan = findViewById(R.id.listViewPesanan);
        pesananList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, pesananList);
        listViewPesanan.setAdapter(adapter);

        // Inisialisasi dbHelper
        dbHelper = new DataHelper(this);

        // Menampilkan pesanan berdasarkan userId
        tampilkanPesananByUserId(userId);

        listViewPesanan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Dapatkan ID pesanan dari posisi item yang dipilih
                Order selectedOrder = dbHelper.getPesananByUserId(userId).get(position);
                int orderId = (int) selectedOrder.getId();

                // Panggil aktivitas DetailViewOrder dan kirimkan ID pesanan
                Intent intent = new Intent(ViewOrderActivity.this, DetailViewOrder.class);
                intent.putExtra("USER_ID", userId);
                intent.putExtra("ORDER_ID", orderId);
                startActivity(intent);
            }
        });

    }

    private void tampilkanPesananByUserId(int userId) {
        List<Order> orderList = dbHelper.getPesananByUserId(userId);

        pesananList.clear();

        for (Order order : orderList) {
            String pesananInfo = "ID Pesanan: " + order.getId() +
                    "\nTanggal Order: " + order.getTanggalOrder() +
                    "\nTotal Harga: Rp." + order.getTotalHarga() +
                    "\nStatus: " + order.getStatus();

            pesananList.add(pesananInfo);
        }

        // Memberitahu adapter bahwa data telah berubah
        adapter.notifyDataSetChanged();
    }
}