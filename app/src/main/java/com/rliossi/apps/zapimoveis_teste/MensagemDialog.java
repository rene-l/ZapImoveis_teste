package com.rliossi.apps.zapimoveis_teste;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rliossi.apps.zapimoveis_teste.network.RestManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rene on 21/08/2016.
 */
public class MensagemDialog extends DialogFragment {
    private static final String ARG_ID = "anuncio_id";
    private RestManager mRestManager;

    public static MensagemDialog newInstance(int id) {
        
        Bundle args = new Bundle();
        args.putInt(ARG_ID, id);
        
        MensagemDialog fragment = new MensagemDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRestManager = RestManager.getInstance();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final int id = getArguments().getInt(ARG_ID);

        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_enviar_mensagem, null);
        
        final AlertDialog dialog =  new AlertDialog.Builder(getContext())
                .setView(dialogView)
                .setTitle(R.string.dialog_title)
                .setPositiveButton(R.string.dialog_positive_button, null)
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button b = dialog.getButton(AlertDialog.BUTTON_POSITIVE);

                final EditText nome = (EditText) dialog.findViewById(R.id.nome_contato_textView);
                final EditText email = (EditText) dialog.findViewById(R.id.email_contato_textView);
                final EditText fone = (EditText) dialog.findViewById(R.id.fone_contato_textView);
                final EditText msg = (EditText) dialog.findViewById(R.id.mensagem_editText);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Call<Void> call = mRestManager.getServiceAPI()
                                .sendMensagem(
                                        id,
                                        nome.getText().toString(),
                                        email.getText().toString(),
                                        fone.getText().toString(),
                                        msg.getText().toString()
                                );
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()){
                                    Toast.makeText(getContext(), "Mensagem enviada com sucesso!", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(getContext(), "Não foi possível enviar a mensagem.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });

        return dialog;
    }





}
