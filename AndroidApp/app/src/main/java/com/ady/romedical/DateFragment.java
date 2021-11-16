package com.ady.romedical;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

public class DateFragment extends Fragment {

    int NOTIFICATION_ID = 1;
    String CHANNEL_ID ="1";

    String date;

    EditText et1;
    Switch switch1;
    Button btnSalvare;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_date, container, false);

        et1 = (EditText) view.findViewById(R.id.edittext_datepers);
        switch1 = (Switch) view.findViewById(R.id.switch_afisare);
        btnSalvare = (Button) view.findViewById(R.id.buton_salvare);

        return view;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            if(getActivity()!=null){
                NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);

            }
        }
    }

    public void setDate(String str){
        date = str;
    }

    public String getDate(){
        return date;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getActivity()!=null){

            SharedPreferences prefs = getActivity().getSharedPreferences("Prefs",0);
            et1.setText(prefs.getString("date", null));
            switch1.setChecked(prefs.getBoolean("afisare", false));
            date = prefs.getString("date", null);
        }

        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.equals("")){
                    setDate(et1.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnSalvare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getActivity().getSharedPreferences("Prefs", 0);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("date", et1.getText().toString());
                editor.putBoolean("afisare", switch1.isChecked());
                editor.apply();


                NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), CHANNEL_ID)
                        .setSmallIcon(R.mipmap.ic_caduceus_foreground)
                        .setContentTitle("Date medicale")
                        .setContentText(getDate())
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(getDate()))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setOngoing(true);

                createNotificationChannel();

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getActivity());

                if(switch1.isChecked()){
                    notificationManager.notify(NOTIFICATION_ID, builder.build());
                }else{
                    notificationManager.cancel(NOTIFICATION_ID);
                }
            }
        });

    }

}
