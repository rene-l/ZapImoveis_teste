package com.rliossi.apps.zapimoveis_teste;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Rene on 21/08/2016.
 */
public class AnuncioDetalheActivity extends AppCompatActivity {
    private static final String EXTRA_IMOVEL_ID = "com.rliossi.apps.zapimoveis_teste.imovel_id";

    public static Intent newIntent(Context context, int imovelId) {
        Intent i = new Intent(context, AnuncioDetalheActivity.class);
        i.putExtra(EXTRA_IMOVEL_ID, imovelId);

        return i;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        int imovelId = getIntent().getIntExtra(EXTRA_IMOVEL_ID, 0);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = AnuncioDetalheFragment.newInstance(imovelId);

            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}
