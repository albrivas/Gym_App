package retamar.com.gym_app.adaptadores;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import retamar.com.gym_app.R;

public class EntrenamientoHolder extends RecyclerView.ViewHolder {

    TextView nombre;
    ImageView imagen;

    public EntrenamientoHolder(View itemView) {
        super(itemView);
        nombre = itemView.findViewById(R.id.nombre_entrenamiento);
        imagen = itemView.findViewById(R.id.imagen_lista_guardado);
    }
}