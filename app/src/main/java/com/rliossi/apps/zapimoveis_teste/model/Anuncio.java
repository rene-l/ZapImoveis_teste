package com.rliossi.apps.zapimoveis_teste.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Rene on 20/08/2016.
 */
public class Anuncio implements Comparable<Anuncio>{

    @SerializedName("ID")
    private String mID;

    @SerializedName("Endereco")
    private Endereco mEndereco;

    @SerializedName("Preco")
    private Double mPreco;

    @SerializedName("Vagas")
    private int mVagas;

    @SerializedName("Suites")
    private int mSuites;

    @SerializedName("AreaUtil")
    private int mAreaUtil;

    @SerializedName("AreaTotal")
    private int mAreaTotal;

    @SerializedName("Cliente")
    private Cliente mCliente;

    @SerializedName("UrlImagem")
    private String mUrlImagem;

    @SerializedName("EstagioObra")
    private String mEstagioObra;

    @SerializedName("TipoImovel")
    private String mTipoImovel;

    @SerializedName("TipoOferta")
    private String mTipoOferta;

    @SerializedName("Descricao")
    private String mDescricao;

    public String getID() {
        return mID;
    }

    public void setID(String ID) {
        mID = ID;
    }

    public Endereco getEndereco() {
        return mEndereco;
    }

    public void setEndereco(Endereco endereco) {
        mEndereco = endereco;
    }

    public Double getPreco() {
        return mPreco;
    }

    public void setPreco(Double preco) {
        mPreco = preco;
    }

    public int getVagas() {
        return mVagas;
    }

    public void setVagas(int vagas) {
        mVagas = vagas;
    }

    public int getSuites() {
        return mSuites;
    }

    public void setSuites(int suites) {
        mSuites = suites;
    }

    public int getAreaUtil() {
        return mAreaUtil;
    }

    public void setAreaUtil(int areaUtil) {
        mAreaUtil = areaUtil;
    }

    public int getAreaTotal() {
        return mAreaTotal;
    }

    public void setAreaTotal(int areaTotal) {
        mAreaTotal = areaTotal;
    }

    public Cliente getCliente() {
        return mCliente;
    }

    public void setCliente(Cliente cliente) {
        mCliente = cliente;
    }

    public String getUrlImagem() {
        return mUrlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        mUrlImagem = urlImagem;
    }

    public String getEstagioObra() {
        return mEstagioObra;
    }

    public void setEstagioObra(String estagioObra) {
        mEstagioObra = estagioObra;
    }

    public String getTipoImovel() {
        return mTipoImovel;
    }

    public void setTipoImovel(String tipoImovel) {
        mTipoImovel = tipoImovel;
    }

    public String getTipoOferta() {
        return mTipoOferta;
    }

    public void setTipoOferta(String tipoOferta) {
        mTipoOferta = tipoOferta;
    }

    public String getDescricao() {
        return mDescricao;
    }

    public void setDescricao(String descricao) {
        mDescricao = descricao;
    }

    @Override
    public int compareTo(Anuncio a) {
        if (this.getPreco() < a.getPreco()) {
            return -1;
        } else if (this.getPreco() > a.getPreco()) {
            return 1;
        }
        return 0;
    }
}
