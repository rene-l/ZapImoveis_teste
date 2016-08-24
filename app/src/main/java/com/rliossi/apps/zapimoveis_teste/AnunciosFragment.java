package com.rliossi.apps.zapimoveis_teste;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rliossi.apps.zapimoveis_teste.model.Anuncio;
import com.rliossi.apps.zapimoveis_teste.network.RestManager;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rene on 20/08/2016.
 */
public class AnunciosFragment extends Fragment {
    private static final String SORT_PRECO_MAIOR = "preco_maior";
    private static final String SORT_PRECO_MENOR = "preco_menor";
    private static final String SORT_AREA_TOTAL = "area_total";
    private static final String SORT_KEY = "sort";

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private RestManager mRestManager;
    private AnunciosAdapter mAnunciosAdapter;
    private NumberFormat mNumberFormat;
    private String mOrdenadoPor;
    private ImageView mRefresh;
    private LinearLayout mOnLayout;
    private LinearLayout mOffLayout;

    public static AnunciosFragment newInstance() {
        Bundle args = new Bundle();

        AnunciosFragment fragment = new AnunciosFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        showPopup();
        return super.onOptionsItemSelected(item);
    }

    private void showPopup() {
        View v = getView().findViewById(R.id.menu_icon_sort);

        PopupMenu popupMenu = new PopupMenu(getContext(), v);

        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.popup, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.ordena_preco_menor:
                        if (mAnunciosAdapter != null) {
                            sortPrecoMenor(mAnunciosAdapter.getAnuncios());
                            mAnunciosAdapter.notifyDataSetChanged();
                            return true;
                        }
                        break;
                    case R.id.ordena_area_total:
                        if (mAnunciosAdapter != null) {
                            sortAreaTotal(mAnunciosAdapter.getAnuncios());
                            mAnunciosAdapter.notifyDataSetChanged();
                            return true;
                        }
                        break;
                    case R.id.ordena_preco_maior:
                        if (mAnunciosAdapter != null) {
                            sortPrecoMaior(mAnunciosAdapter.getAnuncios());
                            mAnunciosAdapter.notifyDataSetChanged();
                            return true;
                        }
                        break;


                }
                return false;
            }
        });
        popupMenu.show();
    }

    private void sortPrecoMenor(List<Anuncio> anuncios){
        Collections.sort(anuncios);
        mOrdenadoPor = SORT_PRECO_MENOR;
    }

    private void sortPrecoMaior(List<Anuncio> anuncios){
        Collections.sort(anuncios, new Comparator<Anuncio>() {
            @Override
            public int compare(Anuncio anuncio, Anuncio t1) {
                if (anuncio.getPreco() > t1.getPreco()) {
                    return -1;
                } else if (anuncio.getPreco() < t1.getPreco()) {
                    return 1;
                }
                return 0;
            }
        });
        mOrdenadoPor = SORT_PRECO_MAIOR;
    }

    private void sortAreaTotal(List<Anuncio> anuncios) {

        Collections.sort(anuncios, new Comparator<Anuncio>() {
            @Override
            public int compare(Anuncio anuncio, Anuncio t1) {
                if (anuncio.getAreaTotal() < t1.getAreaTotal()) {
                    return 1;
                } else if (anuncio.getAreaTotal() > t1.getAreaTotal()) {
                    return -1;
                }
                return 0;
            }
        });
        mOrdenadoPor = SORT_AREA_TOTAL;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null && savedInstanceState.containsKey(SORT_KEY)) {
            mOrdenadoPor = savedInstanceState.getString(SORT_KEY);
        }

        mRestManager = RestManager.getInstance();
        mNumberFormat = NumberFormat.getCurrencyInstance();
        setHasOptionsMenu(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mOrdenadoPor != null) {
            outState.putString(SORT_KEY, mOrdenadoPor);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anuncios, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        mOnLayout = (LinearLayout) view.findViewById(R.id.layout_normal);
        mOffLayout = (LinearLayout) view.findViewById(R.id.offline_layout);

        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        mRefresh = (ImageView) view.findViewById(R.id.refresh_button);
        mRefresh.setOnClickListener(new RefreshListener());

        mRecyclerView = (RecyclerView) view.findViewById(R.id.imoveis_recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new RecyclerItemDecoration(getResources().getDrawable(R.drawable.spacer)));

        setAnunciosAdapter();

        return view;
    }
    private void setOffLayout(){
        mOffLayout.setVisibility(View.VISIBLE);
        mOnLayout.setVisibility(View.GONE);
    }

    private void setOnLayout(){
        mOnLayout.setVisibility(View.VISIBLE);
        mOffLayout.setVisibility(View.GONE);
    }

    private class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Anuncio mAnuncio;

        public ItemHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        public void bindView(Anuncio anuncio) {
            mAnuncio = anuncio;

            ImageView foto = (ImageView) itemView.findViewById(R.id.foto_imageView);
            Picasso.with(getContext()).load(anuncio.getUrlImagem()).into(foto);

            TextView preco = (TextView)itemView.findViewById(R.id.preco_textView);
            preco.setText(mNumberFormat.format(anuncio.getPreco()));

            SpannableString spanCidade = new SpannableString("Cidade: " + anuncio.getEndereco().getCidade() + " - " + anuncio.getEndereco().getEstado());
            spanCidade.setSpan(new StyleSpan(Typeface.BOLD),0, 6, 0);

            TextView cidade = (TextView)itemView.findViewById(R.id.cidade_textView);
            cidade.setText(spanCidade);

            SpannableString spanAreaTotal = new SpannableString("Área Total(m2): " + anuncio.getAreaTotal());
            spanAreaTotal.setSpan(new StyleSpan(Typeface.BOLD),0, 14, 0);

            TextView areaTotal = (TextView)itemView.findViewById(R.id.area_total_textView);
            areaTotal.setText(spanAreaTotal);

            TextView tipoImovel = (TextView) itemView.findViewById(R.id.tipo_imovel_textView);
            tipoImovel.setText(anuncio.getTipoImovel());

        }

        @Override
        public void onClick(View view) {
            Intent i = AnuncioDetalheActivity.newIntent(getContext(), Integer.parseInt(mAnuncio.getID()));
            startActivity(i);
        }
    }

    private class AnunciosAdapter extends RecyclerView.Adapter<ItemHolder> {
        private List<Anuncio> mAnuncios;

        public AnunciosAdapter(List<Anuncio> anuncios) {
            mAnuncios = anuncios;
        }

        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View v = inflater.inflate(R.layout.item_list_anuncios, parent, false);
            return new ItemHolder(v);
        }

        @Override
        public void onBindViewHolder(ItemHolder holder, int position) {
            Anuncio anuncio = mAnuncios.get(position);
            holder.bindView(anuncio);
        }

        @Override
        public int getItemCount() {
            return mAnuncios.size();
        }

        public List<Anuncio> getAnuncios() {
            return mAnuncios;
        }

        public void setAnuncios(List<Anuncio> anuncios) {
            mAnuncios = anuncios;
        }
    }

    private class RecyclerItemDecoration extends RecyclerView.ItemDecoration {
        private Drawable mSpacer;

        public RecyclerItemDecoration(Drawable spacer) {
            mSpacer = spacer;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            if (parent.getChildAdapterPosition(view) == 0) {
                return;
            }

            outRect.top = mSpacer.getIntrinsicHeight();
        }
    }

    private void setAnunciosAdapter(){
        mProgressBar.setVisibility(View.VISIBLE);
        if (mAnunciosAdapter == null) {
            Call<List<Anuncio>> call = mRestManager.getServiceAPI().getAnuncios();

            call.enqueue(new Callback<List<Anuncio>>() {
                @Override
                public void onResponse(Call<List<Anuncio>> call, Response<List<Anuncio>> response) {
                    if (response.isSuccessful()) {
                        mProgressBar.setVisibility(View.GONE);
                        List<Anuncio> anuncios = response.body();
                        if (mOrdenadoPor != null) {
                            switch (mOrdenadoPor) {
                                case SORT_PRECO_MAIOR:
                                    sortPrecoMaior(anuncios);
                                    break;
                                case SORT_AREA_TOTAL:
                                    sortAreaTotal(anuncios);
                                    break;
                                case SORT_PRECO_MENOR:
                                    sortPrecoMenor(anuncios);
                                    break;
                            }
                        }
                        mAnunciosAdapter = new AnunciosAdapter(anuncios);
                        mRecyclerView.setAdapter(mAnunciosAdapter);
                    }
                }

                @Override
                public void onFailure(Call<List<Anuncio>> call, Throwable t) {
                    mProgressBar.setVisibility(View.GONE);
                    setOffLayout();
                }
            });
        } else {
            mRecyclerView.setAdapter(mAnunciosAdapter);
        }

    }

    private class RefreshListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            setOnLayout();
            setAnunciosAdapter();
        }
    }


}
