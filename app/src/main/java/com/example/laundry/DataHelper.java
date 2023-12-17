package com.example.laundry;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DataHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "db_laundry.db";
    private static final int DATABASE_VERSION = 2; // Increment the version when you change the schema

    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table user(id integer primary key, " +
                "username text null, " +
                "password text null, " +
                "nama text null, " +
                "nomer_tlp text null, " +
                "email text null, " +
                "alamat text null, " +
                "CONSTRAINT unique_username UNIQUE(username)" +
                ");";
        Log.d("data", "onCreate" + sql);
        db.execSQL(sql);

        String jasa = "create table jasa (" +
                "id INTEGER primary key," +
                "nama text null," +
                "harga integer null " +
                ")";
        Log.d("data", "onCreate" + jasa);
        db.execSQL(jasa);

        String store = "create table store(id integer primary key, " +
                "username text null, " +
                "password text null, " +
                "nama_toko text null, " +
                "notelp_toko integer null, " +
                "email_toko text null, " +
                "alamat_toko text null, " +
                "info_toko text null, " +
                "hariop_toko text null, " +
                "waktuop_toko text null," +
                "CONSTRAINT unique_usernamestore UNIQUE(username)" +
                ");";
        Log.d("data", "onCreate" + store);
        db.execSQL(store);

        db.execSQL("INSERT INTO store (username, password, nama_toko, notelp_toko, email_toko, alamat_toko, info_toko, hariop_toko, waktuop_toko) " +
                "VALUES ('admin', 'admin123', 'Toko Saya', 123456789, 'tokosaya@email.com', 'Jl. Contoh No. 123', 'Informasi tambahan', 'Senin - Jumat', '08:00 - 17:00');");

        String order = "create table \"order\"(id integer primary key, " +
                "id_user integer null, " +
                "tanggal_order text null, " +
                "total_harga integer null, " +
                "status text null " +
                ");";
        Log.d("data", "onCreate" + order);
        db.execSQL(order);

        String detail_order = "create table detail_order(id integer primary key, " +
                "id_order integer null, " +
                "nama_jasa text null, " +
                "harga_jasa integer null, "+
                "jumlah integer null, " +
                "totaljmlh_jasa integer null " +
                ");";
        Log.d("data", "onCreate" + detail_order);
        db.execSQL(detail_order);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public int hitungTotalJmlhJasa(List<Jasa> jasaList, List<Integer> jumlahList) {
        int totalJmlhJasa = 0;

        for (int i = 0; i < jasaList.size(); i++) {
            Jasa jasa = jasaList.get(i);
            int jumlah = jumlahList.get(i);

            // Jika jumlah tidak sama dengan 0, tambahkan ke total
            if (jumlah != 0) {
                totalJmlhJasa += jasa.getHarga() * jumlah;
            }
        }

        return totalJmlhJasa;
    }

    public void tambahPesanan(int userId, String tanggalOrder, int total_harga, String status, List<Jasa> jasaList, List<Integer> jumlahList) {
        // Tambahkan pesanan ke tabel 'order'
        long orderId = tambahOrder(userId, tanggalOrder, total_harga, status);

        // Tambahkan detail pesanan ke tabel 'detail_order' untuk setiap jasa dalam daftar
        for (int i = 0; i < jasaList.size(); i++) {
            int jumlah = jumlahList.get(i);
            if (jumlah != 0) {
                Jasa jasa = jasaList.get(i);
                int totalJmlhJasa = jasa.getHarga() * jumlah;
                tambahDetailOrder(orderId, jasa.getNama(), jasa.getHarga(), jumlah, totalJmlhJasa);
            }
        }
    }

    public long tambahOrder(int userId, String tanggalOrder, int total_harga, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_user", userId);
        values.put("tanggal_order", tanggalOrder);
        values.put("total_harga", total_harga);
        values.put("status", status);

        long orderId = db.insert("\"order\"", null, values);

        db.close();

        return orderId;
    }

    public void tambahDetailOrder(long orderId, String namaJasa, int hargaJasa, int jumlah, int totalJmlhJasa) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_order", orderId);
        values.put("nama_jasa", namaJasa);
        values.put("harga_jasa", hargaJasa);
        values.put("jumlah", jumlah);
        values.put("totaljmlh_jasa", totalJmlhJasa);

        // Masukkan data ke dalam tabel 'detail_order'
        db.insert("detail_order", null, values);

        // Tutup koneksi database
        db.close();
    }
    @SuppressLint("Range")
    public String getDetailOrderByIdOrder(int orderId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String result = "";

        // Query untuk mendapatkan detail_order berdasarkan id_order
        String query = "SELECT * FROM detail_order WHERE id_order = " + orderId;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                // Ambil nilai dari setiap kolom
                String namaJasa = cursor.getString(cursor.getColumnIndex("nama_jasa"));
                int hargaJasa = cursor.getInt(cursor.getColumnIndex("harga_jasa"));
                int jumlah = cursor.getInt(cursor.getColumnIndex("jumlah"));
                int totalJumlahJasa = cursor.getInt(cursor.getColumnIndex("totaljmlh_jasa"));

                // Format string dengan informasi yang diambil
                String detailInfo = namaJasa + " x" + jumlah + " \t=" + totalJumlahJasa + "\n";

                // Tambahkan ke hasil akhir
                result += detailInfo;

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return result;
    }

    public void ubahStatusPesanan(int orderId, String newStatus) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("status", newStatus);

        db.update("\"order\"", values, "id=?", new String[]{String.valueOf(orderId)});

        db.close();
    }

    @SuppressLint("Range")
    public List<Order> getPesananByUserId(int userId) {
        List<Order> orderList = new ArrayList<>();

        // Query SQL untuk mendapatkan pesanan berdasarkan userId
        String query = "SELECT * FROM \"order\" WHERE id_user = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        // Iterasi melalui hasil query
        if (cursor.moveToFirst()) {
            do {
                int orderId = cursor.getInt(cursor.getColumnIndex("id"));
                String tanggalOrder = cursor.getString(cursor.getColumnIndex("tanggal_order"));
                int totalHarga = cursor.getInt(cursor.getColumnIndex("total_harga"));
                String status = cursor.getString(cursor.getColumnIndex("status"));

                // Buat objek Order dan tambahkan ke daftar
                Order order = new Order(orderId, userId, tanggalOrder, totalHarga, status);
                orderList.add(order);
            } while (cursor.moveToNext());
        }

        // Tutup cursor dan koneksi database
        cursor.close();
        db.close();
        return orderList;
    }

    @SuppressLint("Range")
    public List<Order> getAllPesanan() {
        List<Order> pesananList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Define the columns you want to retrieve
        String[] columns = {"id", "id_user", "tanggal_order", "total_harga","status"};

        Cursor cursor = db.query("\"order\"", columns, null, null, null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            int userId = cursor.getInt(cursor.getColumnIndex("id_user"));
            String tanggalOrder = cursor.getString(cursor.getColumnIndex("tanggal_order"));
            int totalHarga = cursor.getInt(cursor.getColumnIndex("total_harga"));
            String status = cursor.getString(cursor.getColumnIndex("status"));

            Order order = new Order(id, userId, tanggalOrder, totalHarga, status);
            pesananList.add(order);
        }

        cursor.close();
        db.close();

        return pesananList;
    }


    @SuppressLint("Range")
    public Order getPesananById(int orderId) {
        Order order = null;

        // Query SQL untuk mendapatkan pesanan berdasarkan orderId
        String query = "SELECT * FROM \"order\" WHERE id = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(orderId)});

        // Jika cursor bergerak ke baris pertama
        if (cursor.moveToFirst()) {
            int userId = cursor.getInt(cursor.getColumnIndex("id_user"));
            String tanggalOrder = cursor.getString(cursor.getColumnIndex("tanggal_order"));
            int totalHarga = cursor.getInt(cursor.getColumnIndex("total_harga"));
            String status = cursor.getString(cursor.getColumnIndex("status"));

            // Buat objek Order
            order = new Order(orderId, userId, tanggalOrder, totalHarga, status);
        }

        // Tutup cursor dan koneksi database
        cursor.close();
        db.close();

        return order;
    }


    public int getUserIdByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        int userId = -1; // Default value if not found

        String query = "SELECT id FROM user WHERE username = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username});

        if (cursor.moveToFirst()) {
            userId = cursor.getInt(0);
        }

        cursor.close();
        db.close();

        return userId;
    }

    public String getNamaById(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String nama = ""; // Default value if not found

        String query = "SELECT nama FROM user WHERE id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            nama = cursor.getString(0);
        }

        cursor.close();
        db.close();

        return nama;
    }

    @SuppressLint("Range")
    public User getUserByID(int userID) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;

        String query = "SELECT * FROM user WHERE id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userID)});

        if (cursor.moveToFirst()) {
            user = new User();
            user.setId(cursor.getInt(cursor.getColumnIndex("id")));
            user.setUsername(cursor.getString(cursor.getColumnIndex("username")));
            user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            user.setNama(cursor.getString(cursor.getColumnIndex("nama")));
            user.setNoTlp(cursor.getString(cursor.getColumnIndex("nomer_tlp")));
            user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            user.setAlamat(cursor.getString(cursor.getColumnIndex("alamat")));
        }

        cursor.close();
        db.close();

        return user;
    }

    @SuppressLint("Range")
    public Jasa getJasaById(int jasaId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Jasa jasa = null;

        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(jasaId)};

        Cursor cursor = db.query("jasa", null, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String nama = cursor.getString(cursor.getColumnIndex("nama"));
            int harga = cursor.getInt(cursor.getColumnIndex("harga"));

            jasa = new Jasa(id, nama, harga);
            // Add other properties if needed
        }

        cursor.close();
        db.close();

        return jasa;
    }


    @SuppressLint("Range")
    public List<Jasa> getAllJasa() {
        List<Jasa> jasaList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Define the columns you want to retrieve
        String[] columns = {"id", "nama", "harga"};

        Cursor cursor = db.query("jasa", columns, null, null, null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String nama = cursor.getString(cursor.getColumnIndex("nama"));
            int harga = cursor.getInt(cursor.getColumnIndex("harga"));

            Jasa jasa = new Jasa(id, nama, harga);
            jasaList.add(jasa);
        }

        cursor.close();
        db.close();

        return jasaList;
    }

    public boolean insertJasa(String nama, double harga, int storeId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nama", nama);
        contentValues.put("harga", harga);

        long result = db.insert("jasa", null, contentValues);
        return result != -1;
    }

    public boolean isJasaExists(String serviceName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM jasa WHERE nama=?", new String[]{serviceName});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    public void deleteJasa(int jasaId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("jasa", "id=?", new String[]{String.valueOf(jasaId)});
        db.close();
    }

    public boolean updateJasa(int jasaId, String newNama, double newHarga) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nama", newNama);
        contentValues.put("harga", newHarga);

        int rowsAffected = db.update("jasa", contentValues, "id=?", new String[]{String.valueOf(jasaId)});
        db.close();

        return rowsAffected > 0;
    }

    public Boolean cekusernameStore(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM store WHERE username=?", new String[]{username});
        return cursor.getCount() > 0;
    }

    public Boolean insertDataStore(String username, String password, String namaToko,
                                   String noTelpToko, String emailToko, String alamatToko,
                                   String infoToko, String hariOperasional, String waktuOperasional) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("nama_toko", namaToko);
        contentValues.put("notelp_toko", noTelpToko);
        contentValues.put("email_toko", emailToko);
        contentValues.put("alamat_toko", alamatToko);
        contentValues.put("info_toko", infoToko);
        contentValues.put("hariop_toko", hariOperasional);
        contentValues.put("waktuop_toko", waktuOperasional);
        try {
            long result = db.insertOrThrow("store", null, contentValues);
            return result != -1;
        } catch (SQLiteException e) {
            // Log the exception or store it for later retrieval
            lastException = e;
            return false;
        }
    }

    public String getNamaByStoreId(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String nama = ""; // Default value if not found

        String query = "SELECT nama_toko FROM store WHERE id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            nama = cursor.getString(0);
        }

        cursor.close();
        db.close();

        return nama;
    }

    public int getStoreIdByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        int storeId = -1; // Default value if not found

        String query = "SELECT id FROM store WHERE username = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username});

        if (cursor.moveToFirst()) {
            storeId = cursor.getInt(0);
        }

        cursor.close();
        db.close();

        return storeId;
    }

    public Boolean cekusernamestore(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM store WHERE username=?", new String[]{username});
        return cursor.getCount() > 0;
    }

    public Boolean cekpasswordstore(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM store WHERE username=? AND password=?", new String[]{username, password});
        return cursor.getCount() > 0;
    }

    public boolean updateData(int userID, String newUsername, String newPassword, String newNama, String newNomerTlp, String newEmail, String newAlamat) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", newUsername);
        contentValues.put("password", newPassword);
        contentValues.put("nama", newNama);
        contentValues.put("nomer_tlp", newNomerTlp);
        contentValues.put("email", newEmail);
        contentValues.put("alamat", newAlamat);

        int rowsAffected = db.update("user", contentValues, "id=?", new String[]{String.valueOf(userID)});
        db.close();

        return rowsAffected > 0;
    }


    public Boolean insertData(String username, String password, String nama, String nomer_tlp, String email, String alamat) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("nama", nama);
        contentValues.put("nomer_tlp", nomer_tlp);
        contentValues.put("email", email);
        contentValues.put("alamat", alamat);

        try {
            long result = db.insertOrThrow("user", null, contentValues);
            return result != -1;
        } catch (SQLiteException e) {
            // Log the exception or store it for later retrieval
            lastException = e;
            return false;
        }
    }

    private SQLiteException lastException;

    public SQLiteException getLastException() {
        return lastException;
    }

    public Boolean cekusername(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE username=?", new String[]{username});
        return cursor.getCount() > 0;
    }

    public Boolean cekpassword(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE username=? AND password=?", new String[]{username, password});
        return cursor.getCount() > 0;
    }
}