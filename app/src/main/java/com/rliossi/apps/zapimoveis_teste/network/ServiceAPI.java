package com.rliossi.apps.zapimoveis_teste.network;

import com.rliossi.apps.zapimoveis_teste.model.Anuncio;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Rene on 20/08/2016.
 */
public interface ServiceAPI {
    @GET("imoveis")
    Call<List<Anuncio>> getAnuncios();

    @GET("imoveis/{id}")
    Call<Anuncio> getAnuncio(@Path("id") int id);

    @POST("imoveis/contato")
    Call<Void> sendMensagem(
            @Query("id_anuncio") int id,
            @Query("nome") String nome,
            @Query("email") String email,
            @Query("fone") String fone,
            @Query("msg") String msg
    );
}

