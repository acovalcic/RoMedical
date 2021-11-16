package com.ady.romedical.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ady.romedical.R;
import com.ady.romedical.model.Clasament;

import java.util.List;

public class ClasamentAdapter extends ArrayAdapter<Clasament> {

    Context context;
    int resource;
    List<Clasament> clasamentList;
    LayoutInflater layoutInflater;

    public ClasamentAdapter(@NonNull Context context, int resource, List<Clasament> clasamentList, LayoutInflater layoutInflater) {
        super(context, resource, clasamentList);
        this.context=context;
        this.resource=resource;
        this.clasamentList=clasamentList;
        this.layoutInflater=layoutInflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = layoutInflater.inflate(resource, parent, false);
        Clasament cd = clasamentList.get(position);

        if(cd.getDoctor() != null){
            TextView tv_doctor = view.findViewById(R.id.tv_doctor);
            tv_doctor.setText(cd.getDoctor());
            tv_doctor.setTextColor(Color.BLACK);

            TextView tv_spital = view.findViewById(R.id.tv_spital);
            tv_spital.setText(cd.getSpital());
            tv_spital.setTextColor(Color.BLACK);


            TextView tv_medie = view.findViewById(R.id.tv_medie);
            tv_medie.setText(String.valueOf(cd.getMedie()));
            tv_medie.setTextColor(Color.BLACK);

        } else {

            TextView tv_doctor = view.findViewById(R.id.tv_doctor);
            tv_doctor.setVisibility(View.GONE);

            TextView tv_spital = view.findViewById(R.id.tv_spital);
            tv_spital.setText(cd.getSpital());
            tv_spital.setTextColor(Color.BLACK);

            TextView tv_medie = view.findViewById(R.id.tv_medie);
            tv_medie.setText(String.valueOf(cd.getMedie()));
            tv_medie.setTextColor(Color.BLACK);
        }

        return view;
    }
}
