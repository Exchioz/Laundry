package com.example.laundry.Users.Pesanan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.laundry.DataHelper;
import com.example.laundry.Jasa;
import com.example.laundry.R;
import com.example.laundry.Store.Jasa.JasaOrderAdapter;
import com.example.laundry.Users.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderJasaActivity extends AppCompatActivity {

    private ListView listViewJasa;
    private Button btnPesanSekarang;
    private List<Jasa> jasaList;
    private JasaOrderAdapter jasaOrderAdapter;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_jasa);

        userId = getIntent().getIntExtra("USER_ID", -1);

        // Inisialisasi komponen UI
        listViewJasa = findViewById(R.id.listViewJasa);
        btnPesanSekarang = findViewById(R.id.btnPesanSekarang);

        // Ambil data jasa dari database atau sumber lainnya
        // Gantilah dengan cara yang sesuai dengan implementasi Anda
        jasaList = fetchDataFromDatabase();

        // Buat dan set adapter untuk ListView
        jasaOrderAdapter = new JasaOrderAdapter(this, jasaList);
        listViewJasa.setAdapter(jasaOrderAdapter);

        // Set listener untuk item yang diklik pada ListView
        listViewJasa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Jasa selectedJasa = jasaList.get(position);
                Toast.makeText(OrderJasaActivity.this, "Anda memilih jasa: " + selectedJasa.getNama(), Toast.LENGTH_SHORT).show();
            }
        });

        // Set listener untuk tombol "Pesan Sekarang"
        btnPesanSekarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tambahkan pesanan ke database
                DataHelper dbHelper = new DataHelper(OrderJasaActivity.this);
                dbHelper.tambahPesanan(userId, getCurrentDate(), jasaOrderAdapter.hitungTotalHarga(), "PENDING", jasaList, jasaOrderAdapter.getJumlahList());
                // Buka aktivitas konfirmasi pemesanan
                Intent orderview = new Intent(OrderJasaActivity.this, MainActivity.class);
                orderview.putExtra("USER_ID", userId);
                startActivity(orderview);
                finish();
            }
        });
    }

    // Metode sederhana untuk mengambil data jasa dari database
    private List<Jasa> fetchDataFromDatabase() {
        // Implementasikan sesuai dengan kebutuhan Anda
        // Secara sederhana, kita bisa mengembalikan data dari metode getAllJasa di DataHelper
        DataHelper dbHelper = new DataHelper(this);
        return dbHelper.getAllJasa();
    }
    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        return currentDateandTime;
    }

}
