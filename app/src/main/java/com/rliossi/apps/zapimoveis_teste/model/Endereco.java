package com.rliossi.apps.zapimoveis_teste.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Rene on 20/08/2016.
 */
public class Endereco {
    @SerializedName("Logradouro")
    private String mLogradouro;

    @SerializedName("Numero")
    private String mNumero;

    @SerializedName("CEP")
    private String mCep;

    @SerializedName("Bairro")
    private String mBairro;

    @SerializedName("Cidade")
    private String mCidade;

    @SerializedName("Estado")
    private String mEstado;

    public String getLogradouro() {
        return mLogradouro;
    }

    public void setLogradouro(String logradouro) {
        mLogradouro = logradouro;
    }

    public String getNumero() {
        return mNumero;
    }

    public void setNumero(String numero) {
        mNumero = numero;
    }

    public String getCep() {
        return mCep;
    }

    public void setCep(String cep) {
        mCep = cep;
    }

    public String getBairro() {
        return mBairro;
    }

    public void setBairro(String bairro) {
        mBairro = bairro;
    }

    public String getCidade() {
        return mCidade;
    }

    public void setCidade(String cidade) {
        mCidade = cidade;
    }

    public String getEstado() {
        return mEstado;
    }

    public void setEstado(String estado) {
        mEstado = estado;
    }
}
