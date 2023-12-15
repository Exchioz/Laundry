package com.example.laundry;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class DetailViewOrder extends AppCompatActivity {

    private TextView orderIdTextView;
    private TextView orderDateTextView;
    private TextView orderStatusTextView;
    private TextView itemInfoTextView;
    Button btnBack;
    private DataHelper dbHelper;
    private int userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view_order);

        // Inisialisasi komponen UI
        orderIdTextView = findViewById(R.id.textViewOrderId);
        orderDateTextView = findViewById(R.id.textViewOrderDate);
        orderStatusTextView = findViewById(R.id.textViewOrderStatus);
        itemInfoTextView = findViewById(R.id.textViewItemInfo);
        btnBack = findViewById(R.id.btnBack);

        // Inisialisasi dbHelper
        dbHelper = new DataHelper(this);
        userId = getIntent().getIntExtra("USER_ID", -1);

        // Dapatkan orderId dari intent
        int orderId = getIntent().getIntExtra("ORDER_ID", -1);

        // Tampilkan detail pesanan
        tampilkanDetailPesanan(orderId);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tambahkan pesanan ke database
                Intent orderview = new Intent(DetailViewOrder.this, ViewOrderActivity.class);
                orderview.putExtra("USER_ID", userId);
                startActivity(orderview);
                finish();
            }
        });
    }

    private void tampilkanDetailPesanan(int orderId) {
        Order order = dbHelper.getPesananById(orderId);

        orderIdTextView.setText("ID Pesanan: " + order.getId());
        orderDateTextView.setText("Tanggal Order: " + order.getTanggalOrder());
        orderStatusTextView.setText("Status: " + order.getStatus());

        List<DetailOrder> detailOrderList = dbHelper.getDetailPesananByOrderId(orderId);

        StringBuilder itemInfoBuilder = new StringBuilder("Detail Item:\n");
        Jasa jasa;
        DetailOrder detailOrder;

        for (int i = 0; i < detailOrderList.size(); i++) {
            detailOrder = detailOrderList.get(i);
            jasa = dbHelper.getJasaById(detailOrder.getIdJasa());

            itemInfoBuilder.append("- ").append(jasa.getNama())
                    .append(", Jumlah: ").append(detailOrder.getJumlah())
                    .append("\n");
            System.out.println("idJasa: " + jasa);
        }


        // Tampilkan informasi item pada TextView
        itemInfoTextView.setText(itemInfoBuilder.toString());
    }
}
