package com.ady.romedical;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ady.romedical.adapter.SpitalAdapter;
import com.ady.romedical.api.ISearchAPI;
import com.ady.romedical.api.RetrofitClient;
import com.ady.romedical.model.Spital;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CautareFragment extends Fragment {

    ISearchAPI myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    RecyclerView recycler_search;
    LinearLayoutManager layoutManager;
    SpitalAdapter adapter;

    MaterialSearchBar materialSearchBar;

    TextView tv_toate;

    @Override
    public void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cautare, container, false);

        myAPI = getAPI();

        recycler_search = (RecyclerView)view.findViewById(R.id.recycler_search);
        recycler_search.setHasFixedSize(true);
        if(getActivity()!=null){
            layoutManager = new LinearLayoutManager(getActivity());
        }
        recycler_search.setLayoutManager(layoutManager);
        if(getActivity()!=null){
            recycler_search.addItemDecoration(new DividerItemDecoration(getActivity(), layoutManager.getOrientation()));
        }

        materialSearchBar = (MaterialSearchBar) view.findViewById(R.id.search_bar);
        materialSearchBar.setCardViewElevation(10);

        tv_toate = (TextView) view.findViewById(R.id.tv_toate);

        List<Spital> lista = new ArrayList<>();
        Spital nul= new Spital("Introduceți criteriul de căutare în bara de mai sus!","CRITERIUL DE CĂUTARE","INTRODUCEȚI");
        lista.add(nul);
        if(getActivity()!=null){

            adapter = new SpitalAdapter(getActivity(),lista);
            recycler_search.setAdapter(adapter);
        }

        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if(!enabled){
                    List<Spital> lista = new ArrayList<>();
                    Spital nul= new Spital("Introduceți criteriul de căutare în bara de mai sus!","CRITERIUL DE CĂUTARE","INTRODUCEȚI");
                    lista.add(nul);
                    if(getActivity()!=null){

                        adapter = new SpitalAdapter(getActivity(),lista);
                        recycler_search.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text.toString());
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

        tv_toate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllSpital();
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private ISearchAPI getAPI() {
        return RetrofitClient.getInstance().create(ISearchAPI.class);
    }

    private void startSearch(String query) {
        compositeDisposable.add(myAPI.searchSpital(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Spital>>() {
                    @Override
                    public void accept(List<Spital> spitals) throws Exception {
                        adapter = new SpitalAdapter(getActivity(),spitals);
                        recycler_search.setAdapter(adapter);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        List<Spital> lista = new ArrayList<>();
                        Spital nul= new Spital("Nu am găsit spital după criteriile introduse!","CĂUTAREA","REÎNCERCAȚI");
                        lista.add(nul);
                        adapter = new SpitalAdapter(getActivity(),lista);
                        recycler_search.setAdapter(adapter);
                    }
                }));
    }

    private void getAllSpital() {
        compositeDisposable.add(myAPI.getSpitalList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Spital>>() {
                    @Override
                    public void accept(List<Spital> spitals) throws Exception {
                        adapter = new SpitalAdapter(getActivity(),spitals);
                        recycler_search.setAdapter(adapter);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getActivity(),"Eroare de conectare la baza de date!", Toast.LENGTH_LONG).show();
                    }
                }));
    }
}
