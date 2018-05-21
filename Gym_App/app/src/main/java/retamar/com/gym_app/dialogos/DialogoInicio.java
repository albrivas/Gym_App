package retamar.com.gym_app.dialogos;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.KeyboardShortcutGroup;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

import retamar.com.gym_app.R;

public class DialogoInicio extends android.support.v4.app.DialogFragment{
    EditText textoPeso, textoAltura;
    Spinner spinnerEdad;
    View v;
    OnDialogoInicio onDialogoInicio;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        v = LayoutInflater.from(getActivity().getBaseContext()).inflate(R.layout.dialogo_inicio, null);
        onDialogoInicio= (OnDialogoInicio) context;

    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        instancias();
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        builder.setView(v);

        return builder.create();
    }

    private void instancias() {
        //spinnerEdad=


    }
    public interface OnDialogoInicio{
        public void onDataDialogoInicio(String s);
    }



}
