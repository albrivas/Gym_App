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
import retamar.com.gym_app.utils.Usuario;

public class DialogoIMC extends android.support.v4.app.DialogFragment {

    View view;
    EditText peso, altura, edad;
    OnPerfilListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        view = LayoutInflater.from(context).inflate(R.layout.dialogo_imc, null);
        listener = (OnPerfilListener) context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);

        peso = view.findViewById(R.id.texto_peso);
        altura = view.findViewById(R.id.texto_altura);
        edad = view.findViewById(R.id.texto_edad);

        AlertDialog.Builder dialogo = new AlertDialog.Builder(getActivity());
        dialogo.setTitle("Introduce los datos");
        dialogo.setView(view);
        dialogo.setPositiveButton(getResources().getString(R.string.guardar_dialogo_entrenamiento), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onPerfilSelected(peso.getText().toString(), altura.getText().toString(), edad.getText().toString());
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

    public interface OnPerfilListener {
        public void onPerfilSelected(String peso, String altura, String edad);
    }
}
