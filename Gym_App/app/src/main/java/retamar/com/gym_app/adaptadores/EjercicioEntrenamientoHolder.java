package retamar.com.gym_app.adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import retamar.com.gym_app.R;

public class EjercicioEntrenamientoHolder extends RecyclerView.ViewHolder {

    ImageView imagen;
    TextView nombre;

    public EjercicioEntrenamientoHolder(View itemView) {
        super(itemView);

        imagen = itemView.findViewById(R.id.imagen_ejercicio_entrenamiento);
        nombre = itemView.findViewById(R.id.nombre_ejercicio_entrenamiento);

    }

    public void setDetails (Context contexto, String imagen2, String nombre2) {
        Glide.with(contexto).load(imagen2).into(imagen);
        nombre.setText(nombre2);
    }

}
