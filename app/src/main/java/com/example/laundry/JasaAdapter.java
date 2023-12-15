package com.example.laundry;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class JasaAdapter extends ArrayAdapter<Jasa> {

    private Context context;
    private List<Jasa> jasas;

    public JasaAdapter(Context context, List<Jasa> jasas) {
        super(context, 0, jasas);
        this.context = context;
        this.jasas = jasas;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_jasa, parent, false);
        }

        Jasa jasa = getItem(position);

        TextView textViewJasaName = convertView.findViewById(R.id.textViewJasaName);
        TextView textViewJasaPrice = convertView.findViewById(R.id.textViewJasaPrice);

        if (jasa != null) {
            textViewJasaName.setText(jasa.getNama());
            textViewJasaPrice.setText(String.valueOf(jasa.getHarga()));
        }

        Button btnEditJasa = convertView.findViewById(R.id.btnEditJasa);
        btnEditJasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle edit button click
                Jasa jasa = getItem(position);
                if (jasa != null) {
                    // Create an intent to start the EditJasaActivity
                    Intent editIntent = new Intent(context, EditJasaActivity.class);

                    // Pass the Jasa ID to the EditJasaActivity
                    editIntent.putExtra("JASA_ID", jasa.getId());

                    // Start the EditJasaActivity
                    context.startActivity(editIntent);
                }
            }
        });

        Button btnDeleteJasa = convertView.findViewById(R.id.btnDeleteJasa);
        btnDeleteJasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Jasa jasa = getItem(position);
                if (jasa != null) {
                    // Call the method to delete the jasa from the database
                    deleteJasa(jasa.getId());

                    // Remove the deleted jasa from the list and notify the adapter
                    jasas.remove(jasa);
                    notifyDataSetChanged();

                    // Optionally, you can show a toast or perform other actions after deletion
                    Toast.makeText(context, "Jasa berhasil dihapus", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return convertView;
    }

    private void deleteJasa(int jasaId) {
        DataHelper dbHelper = new DataHelper(context);
        dbHelper.deleteJasa(jasaId);
    }
}
