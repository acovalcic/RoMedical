package com.ady.romedical.api;

import com.ady.romedical.model.Categorie;
import com.ady.romedical.model.Clasament;
import com.ady.romedical.model.Feedback;
import com.ady.romedical.model.Judet;
import com.ady.romedical.model.Spital;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ISearchAPI {
    @GET("spital")
    Observable<List<Spital>> getSpitalList();

    @POST("search")
    @FormUrlEncoded
    Observable<List<Spital>> searchSpital(@Field("search") String searchQuery);

    @GET("judet")
    Call<List<Judet>> getJudeteList();

    @GET("categorie")
    Call<List<Categorie>> getCategoriiList();

    @POST("spitaljudcat")
    @FormUrlEncoded
    Call<List<Spital>> searchSpitalByJudAndCat(@Field("judet") String denumireJudet, @Field("categorie") String denumireCategorie);

    @POST("feedback")
    @FormUrlEncoded
    Call<Feedback> inserareFeedback(@Field("p_spital") String denumireSpital, @Field("p_doctor") String numeDoctor,
                                    @Field("p_nota_spital") int notaSpital, @Field("p_nota_doctor") int notaDoctor,
                                    @Field("p_mentiuni") String mentiuni);

    @GET("cladoc")
    Call<List<Clasament>> getClasamentDoctori();

    @GET("claspi")
    Call<List<Clasament>> getClasamentSpitale();

}
