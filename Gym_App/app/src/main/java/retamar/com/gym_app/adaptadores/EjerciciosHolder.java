package retamar.com.gym_app.adaptadores;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import retamar.com.gym_app.R;

public class EjerciciosHolder extends RecyclerView.ViewHolder {

    ImageView imagen;
    TextView nombre;
    TextView numero;

    public EjerciciosHolder(View itemView) {
        super(itemView);

        imagen = itemView.findViewById(R.id.imagen_lista);
        nombre = itemView.findViewById(R.id.nombre_ejercicio);
        numero = itemView.findViewById(R.id.numero_ejercicios);
    }
}

