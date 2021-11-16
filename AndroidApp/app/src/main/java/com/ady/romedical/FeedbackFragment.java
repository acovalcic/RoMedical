package com.ady.romedical;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ady.romedical.adapter.ClasamentAdapter;
import com.ady.romedical.api.ISearchAPI;
import com.ady.romedical.api.RetrofitClient;
import com.ady.romedical.model.Categorie;
import com.ady.romedical.model.Clasament;
import com.ady.romedical.model.Feedback;
import com.ady.romedical.model.Judet;
import com.ady.romedical.model.Spital;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedbackFragment extends Fragment {

    ISearchAPI myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    Spinner spinnerJudet;
    Spinner spinnerCategorie;
    Spinner spinnerDenumireSpital;
    Spinner spinnerNotaSpital;
    Spinner spinnerNotaDoctor;
    EditText etDoctor;
    EditText etMentiuni;
    Button trimiteFeedback;
    Button clasamentDoctor;
    Button clasamentSpital;

    @Override
    public void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        myAPI = getAPI();

        spinnerJudet = (Spinner) view.findViewById(R.id.spinner_judet);
        spinnerCategorie = (Spinner) view.findViewById(R.id.spinner_categorie);
        spinnerDenumireSpital = (Spinner) view.findViewById(R.id.spinner_denumire_spital);
        spinnerNotaDoctor = (Spinner) view.findViewById(R.id.spinner_nota_doctor);
        spinnerNotaSpital = (Spinner) view.findViewById(R.id.spinner_nota_spital);
        etDoctor = (EditText) view.findViewById(R.id.edittext_doctor);
        etMentiuni = (EditText) view.findViewById(R.id.edittext_mentiuni);
        trimiteFeedback = (Button) view.findViewById(R.id.trimite_feedback);
        clasamentDoctor = (Button) view.findViewById(R.id.clasament_doctor);
        clasamentSpital = (Button) view.findViewById(R.id.clasament_spital);

        if(getActivity()!=null){

            ArrayAdapter<CharSequence> adapterNote = ArrayAdapter.createFromResource(getActivity(), R.array.note, R.layout.support_simple_spinner_dropdown_item);

            spinnerNotaSpital.setAdapter(adapterNote);
            spinnerNotaDoctor.setAdapter(adapterNote);
        }

        Call<List<Judet>> callJudet = myAPI.getJudeteList();
        callJudet.enqueue(new Callback<List<Judet>>() {
            @Override
            public void onResponse(Call<List<Judet>> call, Response<List<Judet>> response) {
                List<String> judeteList = new ArrayList<>();

                if(response.body()!=null){
                    for(int i=0; i<response.body().size(); i++){
                        judeteList.add(response.body().get(i).getDenumire().toString());
                    }
                }

                if(getActivity()!=null){
                    ArrayAdapter<String> adapterJudete = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, judeteList);

                    spinnerJudet.setAdapter(adapterJudete);
                }
            }

            @Override
            public void onFailure(Call<List<Judet>> call, Throwable t) {

            }
        });

        Call<List<Categorie>> callCategorie =  myAPI.getCategoriiList();
        callCategorie.enqueue(new Callback<List<Categorie>>() {
            @Override
            public void onResponse(Call<List<Categorie>> call, Response<List<Categorie>> response) {
                List<String> categoriiList = new ArrayList<>();
                if(response.body()!=null){
                    for(int i=0; i<response.body().size(); i++){
                        categoriiList.add(response.body().get(i).getDenumire().toString());
                    }
                }

                if(getActivity()!=null){
                    ArrayAdapter<String> adapterCategorii = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, categoriiList);

                    spinnerCategorie.setAdapter(adapterCategorii);
                }
            }

            @Override
            public void onFailure(Call<List<Categorie>> call, Throwable t) {

            }
        });

        trimiteFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spinnerDenumireSpital.getSelectedItem()!=null){
                    if(spinnerDenumireSpital.getSelectedItem().toString().equals("Nu există spitale de acest tip în acest județ")){
                        TextView errorText = (TextView)spinnerDenumireSpital.getSelectedView();
                        errorText.setError("");
                        errorText.setTextColor(Color.RED);
                        errorText.setText("Alegeți categoria spitalului și selectați spitalul!");
                    }else{
                        if(etDoctor.getText().toString().isEmpty()){
                            etDoctor.setError("Introduceți numele doctorului!");
                        }else{
                            if(spinnerNotaSpital.getSelectedItem().toString().equals("--Alegeți Nota--")){
                                TextView errorText = (TextView)spinnerNotaSpital.getSelectedView();
                                errorText.setError("");
                                errorText.setTextColor(Color.RED);
                                errorText.setText("Alegeți nota!");
                            }else{
                                if(spinnerNotaDoctor.getSelectedItem().toString().equals("--Alegeți Nota--")){
                                    TextView errorText = (TextView)spinnerNotaDoctor.getSelectedView();
                                    errorText.setError("");
                                    errorText.setTextColor(Color.RED);
                                    errorText.setText("Alegeți nota!");
                                }else{
                                    String judet = spinnerJudet.getSelectedItem().toString();
                                    String categorie = spinnerCategorie.getSelectedItem().toString();
                                    String spital = spinnerDenumireSpital.getSelectedItem().toString();
                                    String doctor = etDoctor.getText().toString();
                                    int notaSpital = Integer.parseInt(spinnerNotaSpital.getSelectedItem().toString());
                                    int notaDoctor = Integer.parseInt(spinnerNotaDoctor.getSelectedItem().toString());
                                    String mentiuni = etMentiuni.getText().toString();

                                    AlertDialog dialog = new AlertDialog.Builder(getActivity())
                                            .setTitle("Confirmare Trimitere Feedback")
                                            .setMessage("Doriți trimiterea feedback-ului în forma actuală?")
                                            .setCancelable(false)
                                            .setNegativeButton("NU", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Toast.makeText(getActivity(), "Anulat", Toast.LENGTH_SHORT).show();
                                                    dialog.cancel();
                                                }
                                            }).setPositiveButton("DA", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Feedback feedback = new Feedback(spital, doctor, notaSpital, notaDoctor, mentiuni);
                                                    Call<Feedback> callFeedback = myAPI.inserareFeedback(spital, doctor, notaSpital, notaDoctor, mentiuni);
                                                    callFeedback.enqueue(new Callback<Feedback>() {
                                                        @Override
                                                        public void onResponse(Call<Feedback> call, Response<Feedback> response) {
                                                            int statusCode = response.code();
                                                            Log.i("TAG","Status code:"+statusCode);
                                                        }

                                                        @Override
                                                        public void onFailure(Call<Feedback> call, Throwable t) {
                                                            Log.i("TAG", "Eroare: "+t.toString());

                                                        }
                                                    });
                                                    Toast.makeText(getActivity(), "Feedback Trimis!", Toast.LENGTH_SHORT).show();
                                                    dialog.cancel();
                                                    spinnerJudet.setSelection(0);
                                                    spinnerCategorie.setSelection(0);
                                                    etDoctor.setText("");
                                                    spinnerNotaSpital.setSelection(0);
                                                    spinnerNotaDoctor.setSelection(0);
                                                    etMentiuni.setText("");
                                                }
                                            }).create();
                                    dialog.show();

                                }
                            }
                        }
                    }

                }
            }
        });

        clasamentDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<List<Clasament>> callClasament = myAPI.getClasamentDoctori();
                callClasament.enqueue(new Callback<List<Clasament>>() {
                    @Override
                    public void onResponse(Call<List<Clasament>> call, Response<List<Clasament>> response) {
                        List<Clasament> lista=new ArrayList<>();
                        if(response.body()!=null){
                            lista = response.body();
                        }
                        Dialog clasament = new Dialog(getContext());
                        clasament.setContentView(R.layout.clasament);
                        clasament.setCancelable(false);
                        TextView tv_clasament = clasament.findViewById(R.id.tv_clasament);
                        tv_clasament.setText("Clasament Doctori");
                        Button btnInchidere = clasament.findViewById(R.id.clasament_close_btn);
                        ClasamentAdapter adapter = new ClasamentAdapter(getContext(),R.layout.elem_clasament, lista, getLayoutInflater());
                        ListView lv = clasament.findViewById(R.id.list_view);
                        lv.setAdapter(adapter);
                        clasament.show();
                        btnInchidere.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                clasament.dismiss();
                            }
                        });

                    }

                    @Override
                    public void onFailure(Call<List<Clasament>> call, Throwable t) {

                    }
                });
            }
        });

        clasamentSpital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Call<List<Clasament>> callClasament = myAPI.getClasamentSpitale();
                callClasament.enqueue(new Callback<List<Clasament>>() {
                    @Override
                    public void onResponse(Call<List<Clasament>> call, Response<List<Clasament>> response) {
                        List<Clasament> lista=new ArrayList<>();
                        if(response.body()!=null){
                            lista = response.body();
                        }
                        Dialog clasament = new Dialog(getContext());
                        clasament.setContentView(R.layout.clasament);
                        clasament.setCancelable(false);
                        TextView tv_clasament = clasament.findViewById(R.id.tv_clasament);
                        tv_clasament.setText("Clasament Spitale");
                        TextView tv_doc_dummy = clasament.findViewById(R.id.tv_doc_dummy);
                        tv_doc_dummy.setVisibility(View.GONE);
                        Button btnInchidere = clasament.findViewById(R.id.clasament_close_btn);
                        ClasamentAdapter adapter = new ClasamentAdapter(getContext(),R.layout.elem_clasament, lista, getLayoutInflater());
                        ListView lv = clasament.findViewById(R.id.list_view);
                        lv.setAdapter(adapter);
                        clasament.show();
                        btnInchidere.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                clasament.dismiss();
                            }
                        });

                    }

                    @Override
                    public void onFailure(Call<List<Clasament>> call, Throwable t) {

                    }
                });
            }
        });

        return view;
    }

    private ISearchAPI getAPI() {
        return RetrofitClient.getInstance().create(ISearchAPI.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();

        spinnerJudet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(spinnerJudet.getSelectedItem()!=null && spinnerCategorie.getSelectedItem()!=null){

                    String judetSelectat = spinnerJudet.getSelectedItem().toString();
                    String categorieSelectata = spinnerCategorie.getSelectedItem().toString();
                    Call<List<Spital>> callSpital = myAPI.searchSpitalByJudAndCat(judetSelectat, categorieSelectata);
                    callSpital.enqueue(new Callback<List<Spital>>() {
                        @Override
                        public void onResponse(Call<List<Spital>> call, Response<List<Spital>> response) {
                            List<String> spitaleList = new ArrayList<>();

                            for(int i=0; i<response.body().size(); i++){
                                spitaleList.add(response.body().get(i).getDenumire().toString());
                            }

                            if (getActivity()!= null) {

                                ArrayAdapter<String> adapterSpitale = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, spitaleList);

                                spinnerDenumireSpital.setAdapter(adapterSpitale);
                                spinnerDenumireSpital.setEnabled(true);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Spital>> call, Throwable t) {

                            List<String> spitaleList = new ArrayList<>();

                            spitaleList.add("Nu există spitale de acest tip în acest județ");


                            if(getActivity()!=null){
                                ArrayAdapter<String> adapterSpitale = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, spitaleList);

                                spinnerDenumireSpital.setAdapter(adapterSpitale);
                                spinnerDenumireSpital.setEnabled(false);
                            }
                        }
                    });
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerCategorie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(spinnerJudet.getSelectedItem()!=null && spinnerCategorie.getSelectedItem()!=null){

                    String judetSelectat = spinnerJudet.getSelectedItem().toString();
                    String categorieSelectata = spinnerCategorie.getSelectedItem().toString();
                    Call<List<Spital>> callSpital = myAPI.searchSpitalByJudAndCat(judetSelectat, categorieSelectata);

                    callSpital.enqueue(new Callback<List<Spital>>() {
                        @Override
                        public void onResponse(Call<List<Spital>> call, Response<List<Spital>> response) {
                            List<String> spitaleList = new ArrayList<>();

                            for(int i=0; i<response.body().size(); i++){
                                spitaleList.add(response.body().get(i).getDenumire().toString());
                            }

                            if(getActivity()!=null){

                                ArrayAdapter<String> adapterSpitale = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, spitaleList);

                                spinnerDenumireSpital.setAdapter(adapterSpitale);
                                spinnerDenumireSpital.setEnabled(true);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Spital>> call, Throwable t) {

                            List<String> spitaleList = new ArrayList<>();

                            spitaleList.add("Nu există spitale de acest tip în acest județ");

                            if(getActivity()!=null){

                                ArrayAdapter<String> adapterSpitale = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, spitaleList);

                                spinnerDenumireSpital.setAdapter(adapterSpitale);
                                spinnerDenumireSpital.setEnabled(false);

                            }
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
