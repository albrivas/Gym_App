package retamar.com.gym_app.dialogos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import retamar.com.gym_app.R;

public class DialogoEntrenamiento extends android.support.v4.app.DialogFragment {

    View view;
    EditText nombre;
    OnDialogoIntroListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        view = LayoutInflater.from(context).inflate(R.layout.dialogo_entrenamiento, null);
        listener = (OnDialogoIntroListener) context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);

        nombre = view.findViewById(R.id.texto_nombre);

        AlertDialog.Builder dialogo = new AlertDialog.Builder(getActivity());
        dialogo.setTitle(getResources().getString(R.string.titulo_dialogo_entrenamiento));
        dialogo.setView(view);
        dialogo.setPositiveButton(getResources().getString(R.string.guardar_dialogo_entrenamiento), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onDialogoSelected(nombre.getText().toString());
            }
        });

        dialogo.setNegativeButton(getResources().getString(R.string.cancelar_dialogo_entrenamiento), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();
            }
        });

        return dialogo.create();
    }

    public interface OnDialogoIntroListener {
        public void onDialogoSelected(String nombre);
    }
}
