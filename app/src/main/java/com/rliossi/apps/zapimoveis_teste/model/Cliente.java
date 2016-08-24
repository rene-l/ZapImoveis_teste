package com.rliossi.apps.zapimoveis_teste.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Rene on 20/08/2016.
 */
public class Cliente {

    @SerializedName("NomeFantasia")
    private String mNomeFantasia;

    public String getNomeFantasia() {
        return mNomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        mNomeFantasia = nomeFantasia;
    }
}
