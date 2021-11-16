package com.ady.romedical.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ady.romedical.R;
import com.ady.romedical.interfata.ISpitalClickListener;
import com.ady.romedical.model.Spital;

import java.util.ArrayList;
import java.util.List;

public class SpitalAdapter extends RecyclerView.Adapter<SpitalAdapter.MyViewHolder> {

    Dialog dialog;

    TextView tv_detalii_denumire;

    Context context;
    List<Spital> spitalList = new ArrayList<>();

    public SpitalAdapter(Context context, List<Spital> spitalList) {

        this.context = context;
        this.spitalList = spitalList;

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog);
        dialog.setCancelable(false);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.spital_layout, viewGroup, false);

        Button btnMaps = dialog.findViewById(R.id.buton_maps);
        Button btnAnulare = dialog.findViewById(R.id.buton_anulare);

        btnMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String termen = tv_detalii_denumire.getText().toString();
                termen = termen.replaceAll(" ","+");
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:0,0?q="+termen));
                Intent chooser = Intent.createChooser(intent, "Maps");
                v.getContext().startActivity(chooser);
                dialog.dismiss();
            }
        });

        btnAnulare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.denumire.setText(spitalList.get(i).getDenumire());
        myViewHolder.categorie.setText(spitalList.get(i).getCategorie());
        myViewHolder.judet.setText(spitalList.get(i).getJudet());

        myViewHolder.setSpitalClickListener(new ISpitalClickListener() {
            @Override
            public void onSpitalClick(View view, int position) {
                String poz = spitalList.get(position).getDenumire();
                if (!poz.equals("Nu am găsit spital după criteriile introduse!")){
                    if (!poz.equals("Introduceți criteriul de căutare în bara de mai sus!")){
                        dialog.create();
                        dialog.show();

                        tv_detalii_denumire = dialog.findViewById(R.id.tv_detalii_denumire);
                        tv_detalii_denumire.setText(poz);
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return spitalList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView root_view;
        TextView denumire;
        TextView categorie;
        TextView judet;

        ISpitalClickListener spitalClickListener;

        public void setSpitalClickListener(ISpitalClickListener spitalClickListener) {
            this.spitalClickListener = spitalClickListener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            root_view = (CardView)itemView.findViewById(R.id.root_view);

            denumire = (TextView)itemView.findViewById(R.id.tv_denumire);
            categorie = (TextView)itemView.findViewById(R.id.tv_categorie);
            judet = (TextView)itemView.findViewById(R.id.tv_judet);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            spitalClickListener.onSpitalClick(view, getAdapterPosition());
        }
    }
}
