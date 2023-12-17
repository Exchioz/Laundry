package com.example.laundry.Store.Pesanan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.laundry.DataHelper;
import com.example.laundry.Order;
import com.example.laundry.R;

import java.util.List;

public class DetailViewPesanan extends AppCompatActivity {

    private TextView orderIdTextView,orderDateTextView, orderStatusTextView, itemInfoTextView, orderTotalTextView, orderNamaTextView;
    Button btnBack, btnUbahStatus, btnBatal, btnEdit;
    private List<String> pesananList;
    private DataHelper dbHelper;
    private int userId;
    private Order order;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view_pesanan);

        // Inisialisasi komponen UI
        orderIdTextView = findViewById(R.id.textViewOrderId);
        orderNamaTextView = findViewById(R.id.textViewNama);
        orderDateTextView = findViewById(R.id.textViewOrderDate);
        orderTotalTextView = findViewById(R.id.textViewOrderTotalHarga);
        orderStatusTextView = findViewById(R.id.textViewOrderStatus);
        itemInfoTextView = findViewById(R.id.textViewItemInfo);
        btnUbahStatus = findViewById(R.id.btnUbah);
        btnBack = findViewById(R.id.btnBack);
        btnBatal = findViewById(R.id.btnBatal);
        btnEdit = findViewById(R.id.btnEdit);

        // Inisialisasi dbHelper
        dbHelper = new DataHelper(this);
        userId = getIntent().getIntExtra("USER_ID", -1);

        // Dapatkan orderId dari intent
        int orderId = getIntent().getIntExtra("ORDER_ID", -1);

        // Tampilkan detail pesanan
        tampilkanDetailPesanan(orderId);

        btnUbahStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (order != null) {
                    String currentStatus = order.getStatus();

                    // Ubah status sesuai dengan urutan yang diinginkan
                    String newStatus = "";
                    if ("PENDING".equals(currentStatus)) {
                        newStatus = "PESANAN DITERIMA";
                    } else if ("PESANAN DITERIMA".equals(currentStatus)) {
                        newStatus = "KURIR SEDANG KE TUJUAN";
                    } else if ("KURIR SEDANG KE TUJUAN".equals(currentStatus)) {
                        newStatus = "DIPROSES";
                    } else if ("DIPROSES".equals(currentStatus)) {
                        newStatus = "PESANAN SELESAI";
                    }

                    // Ubah status dan tampilkan kembali detail pesanan
                    dbHelper.ubahStatusPesanan((int) order.getId(), newStatus);
                    tampilkanDetailPesanan((int) order.getId());
                }
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (order != null && ("KURIR SEDANG KE TUJUAN".equals(order.getStatus()) ||
                        "DIPROSES".equals(order.getStatus()))) {
                    // Logika untuk menangani edit pesanan
                    // Misalnya, buka aktivitas untuk mengedit pesanan
                    Toast.makeText(DetailViewPesanan.this, "Edit pesanan!", Toast.LENGTH_SHORT).show();

                    // Implementasikan logika edit pesanan sesuai kebutuhan
                }
            }
        });

        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (order != null && "PENDING".equals(order.getStatus())) {
                    // Logika untuk menangani pembatalan pesanan
                    // Misalnya, tampilkan konfirmasi atau lakukan tindakan sesuai dengan pembatalan
                    Toast.makeText(DetailViewPesanan.this, "Pesanan dibatalkan!", Toast.LENGTH_SHORT).show();

                    // Ubah status pesanan dan tampilkan kembali detail pesanan
                    dbHelper.ubahStatusPesanan((int) order.getId(), "DIBATALKAN");
                    tampilkanDetailPesanan((int) order.getId());
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tambahkan pesanan ke database
                Intent orderview = new Intent(DetailViewPesanan.this, ViewPesananActivity.class);
                startActivity(orderview);
                finish();
            }
        });
    }

    private void tampilkanDetailPesanan(int orderId) {
        order = dbHelper.getPesananById(orderId);

        orderIdTextView.setText("ID Pesanan: " + order.getId());
        orderNamaTextView.setText("Nama: " + dbHelper.getNamaById(order.getUserId()));
        orderDateTextView.setText("Tanggal Order: " + order.getTanggalOrder());
        orderTotalTextView.setText("Total Harga: " + order.getTotalHarga());
        orderStatusTextView.setText("Status: " + order.getStatus());

        String detailOrderInfo = dbHelper.getDetailOrderByIdOrder(orderId);
        itemInfoTextView.setText(detailOrderInfo);

        if (order != null) {
            String currentStatus = order.getStatus();
            btnUbahStatus.setText(getNextStatus(currentStatus));
        }
    }

    private String getNextStatus(String currentStatus) {
        btnUbahStatus.setEnabled(false);
        btnEdit.setEnabled(false);
        btnBatal.setEnabled(false);
        if ("PENDING".equals(currentStatus)) {
            btnUbahStatus.setEnabled(true);
            btnBatal.setEnabled(true);
            return "Terima Pesanan";
        } else if ("PESANAN DITERIMA".equals(currentStatus)) {
            btnUbahStatus.setEnabled(true);
            btnBatal.setEnabled(true);
            return "Ambil Barang";
        } else if ("KURIR SEDANG KE TUJUAN".equals(currentStatus)) {
            btnUbahStatus.setEnabled(true);
            btnEdit.setEnabled(true);
            btnBatal.setEnabled(false);
            return "Proses Barang";
        } else if ("DIPROSES".equals(currentStatus)) {
            btnUbahStatus.setEnabled(true);
            btnEdit.setEnabled(false);
            btnBatal.setEnabled(false);
            return "Pesanan Selesai";
        } else if ("PESANAN SELESAI".equals(currentStatus)) {
            btnUbahStatus.setEnabled(false);
            btnEdit.setEnabled(false);
            btnBatal.setEnabled(false);
            return "Pesanan Berhasil";
        }
        btnUbahStatus.setEnabled(false);
        btnEdit.setEnabled(false);
        btnBatal.setEnabled(false);
        return "Pesanan Dibatalkan";
    }
}
