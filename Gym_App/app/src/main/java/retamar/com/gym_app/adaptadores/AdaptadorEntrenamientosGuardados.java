package retamar.com.gym_app.adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import retamar.com.gym_app.R;
import retamar.com.gym_app.utils.Ejercicios;
import retamar.com.gym_app.utils.Entrenamiento;

public class AdaptadorEntrenamientosGuardados extends RecyclerView.Adapter<AdaptadorEntrenamientosGuardados.MyHolder> {

    Context contexto;
    ArrayList<Entrenamiento> ejercicios;
    View v;
    AdaptadorEntrenamientosGuardados.OnTipoSelectedListener listener;

    public AdaptadorEntrenamientosGuardados(Context contexto, ArrayList<Entrenamiento> ejercicios) {
        this.contexto = contexto;
        this.ejercicios = ejercicios;
        listener = (AdaptadorEntrenamientosGuardados.OnTipoSelectedListener) contexto;
    }

    @NonNull
    @Override
    public AdaptadorEntrenamientosGuardados.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        v = LayoutInflater.from(contexto).inflate(R.layout.item_entrenamiento_guardado, parent, false);
        return new AdaptadorEntrenamientosGuardados.MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorEntrenamientosGuardados.MyHolder holder, int position) {
        final Entrenamiento temporal = ejercicios.get(position);

        holder.nombre.setText(temporal.getNombre());

        holder.imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onTipoSelected(temporal);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ejercicios.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        ImageView imagen;
        TextView nombre;

        public MyHolder(View itemView) {
            super(itemView);

            imagen = itemView.findViewById(R.id.imagen_lista_guardado);
            nombre = itemView.findViewById(R.id.nombre_entrenamiento);
        }
    }

    public interface OnTipoSelectedListener {
        public void onTipoSelected(Entrenamiento ej);
    }
}
