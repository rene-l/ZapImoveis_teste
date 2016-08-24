package com.rliossi.apps.zapimoveis_teste;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rliossi.apps.zapimoveis_teste.model.Anuncio;
import com.rliossi.apps.zapimoveis_teste.network.RestManager;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rene on 21/08/2016.
 */
public class AnuncioDetalheFragment extends Fragment{
    private static final String ARG_IMOVEL_ID = "imovel_id";
    private static final String DIALOG_TAG = "mensagem_dialog";

    private RestManager mRestManager;
    private ProgressBar mProgressBar;
    private ImageView mFotoImageView;
    private TextView mPrecoTextView;
    private TextView mEnderecoTextView;
    private TextView mDescricaoTextView;
    private TextView mSuitesTextView;
    private TextView mVagasTextView;
    private TextView mAreaTotalTextView;

    private FloatingActionButton mMensagemButton;
    private Toolbar mToolbar;

    private NumberFormat mCurrencyFormat;

    public static AnuncioDetalheFragment newInstance(int id) {

        Bundle args = new Bundle();
        args.putInt(ARG_IMOVEL_ID, id);

        AnuncioDetalheFragment fragment = new AnuncioDetalheFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRestManager = RestManager.getInstance();
        mCurrencyFormat = NumberFormat.getCurrencyInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detalhes_anuncio, container, false);

        mToolbar = (Toolbar) v.findViewById(R.id.toolbar);

        mProgressBar = (ProgressBar) v.findViewById(R.id.progress_bar);
        mFotoImageView = (ImageView) v.findViewById(R.id.foto_imageView);
        mPrecoTextView = (TextView) v.findViewById(R.id.preco_textView);
        mEnderecoTextView = (TextView) v.findViewById(R.id.endereco_textView);
        mDescricaoTextView = (TextView) v.findViewById(R.id.descricao_textView);
        mSuitesTextView = (TextView) v.findViewById(R.id.suites_textView);
        mVagasTextView = (TextView) v.findViewById(R.id.vagas_textView);
        mAreaTotalTextView = (TextView) v.findViewById(R.id.area_total_textView);

        mMensagemButton = (FloatingActionButton) v.findViewById(R.id.mensagem_fab);

        int id = getArguments().getInt(ARG_IMOVEL_ID);

        fetchImovel(id);

        return v;
    }

    private void fetchImovel(int id){
        mProgressBar.setVisibility(View.VISIBLE);
        Call<Anuncio> call = mRestManager.getServiceAPI().getAnuncio(id);
        call.enqueue(new Callback<Anuncio>() {
            @Override
            public void onResponse(Call<Anuncio> call, Response<Anuncio> response) {
                if (response.isSuccessful()) {
                    mProgressBar.setVisibility(View.GONE);
                    Anuncio i = response.body();
                    setupUI(i);
                }
            }

            @Override
            public void onFailure(Call<Anuncio> call, Throwable t) {
                mProgressBar.setVisibility(View.GONE);
            }
        });

    }

    private void setupUI(Anuncio anuncio) {

        mPrecoTextView.setText(mCurrencyFormat.format(anuncio.getPreco()));
        mEnderecoTextView.setText(buildEndereco(anuncio));
        mDescricaoTextView.setText(anuncio.getDescricao());
        mSuitesTextView.setText(String.valueOf(anuncio.getSuites()));
        mVagasTextView.setText(String.valueOf(anuncio.getVagas()));
        mAreaTotalTextView.setText(String.valueOf(anuncio.getAreaTotal()));

        mMensagemButton.setOnClickListener(new MensagemButtonListener(Integer.parseInt(anuncio.getID())));

        Picasso.with(getContext()).load(anuncio.getUrlImagem()).into(mFotoImageView);
        mToolbar.setTitle(anuncio.getTipoImovel());
    }

    private String buildEndereco(Anuncio anuncio) {
        StringBuilder endBuilder = new StringBuilder();
        endBuilder.append(anuncio.getEndereco().getLogradouro() + ", ");
        endBuilder.append(anuncio.getEndereco().getNumero() + " - ");
        endBuilder.append(anuncio.getEndereco().getBairro() + "\n");
        endBuilder.append(anuncio.getEndereco().getCidade() + " - ");
        endBuilder.append(anuncio.getEndereco().getEstado() + "\n");
        endBuilder.append(anuncio.getEndereco().getCep());

        return endBuilder.toString();
    }

    private class MensagemButtonListener implements View.OnClickListener {
        private int mAnuncioId;

        public MensagemButtonListener(int anuncioId) {
            mAnuncioId = anuncioId;
        }

        @Override
        public void onClick(View view) {
            FragmentManager fm = getChildFragmentManager();

            MensagemDialog dialog = MensagemDialog.newInstance(mAnuncioId);
            dialog.show(fm, DIALOG_TAG);

        }
    }
}
