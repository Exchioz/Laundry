package com.example.laundry;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JasaOrderAdapter extends ArrayAdapter<Jasa> {

    private Context context;
    private List<Jasa> jasas;

    private List<Integer> jumlahList;

    public JasaOrderAdapter(Context context, List<Jasa> jasas) {
        super(context, 0, jasas);
        this.context = context;
        this.jasas = jasas;
        this.jumlahList = new ArrayList<>(Collections.nCopies(jasas.size(), 0));
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_jasa_order, parent, false);
        }

        Jasa jasa = getItem(position);

        TextView textViewJasaName = convertView.findViewById(R.id.textViewJasaNameOrder);
        TextView textViewJasaPrice = convertView.findViewById(R.id.textViewJasaPriceOrder);
        TextView textViewJasaCount = convertView.findViewById(R.id.textViewJasaCount);
        Button btnDecrease = convertView.findViewById(R.id.btnDecrease);
        Button btnIncrease = convertView.findViewById(R.id.btnIncrease);

        if (jasa != null) {
            textViewJasaName.setText(jasa.getNama());
            textViewJasaPrice.setText(String.valueOf(jasa.getHarga()));

            // Set initial count value
            int initialCount = 0;
            textViewJasaCount.setText(String.valueOf(initialCount));

            btnDecrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int count = jumlahList.get(position);
                    if (count > 0) {
                        count--;
                        jumlahList.set(position, count);
                        textViewJasaCount.setText(String.valueOf(count));
                    }
                }
            });

            btnIncrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int count = jumlahList.get(position);
                    count++;
                    jumlahList.set(position, count);
                    textViewJasaCount.setText(String.valueOf(count));
                }
            });
        }

        return convertView;
    }

    public List<Integer> getJumlahList() {
        return jumlahList;
    }
}
