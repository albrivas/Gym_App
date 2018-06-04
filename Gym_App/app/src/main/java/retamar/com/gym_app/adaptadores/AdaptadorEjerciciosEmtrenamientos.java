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
import java.util.List;

import retamar.com.gym_app.R;
import retamar.com.gym_app.utils.Ejercicios;

public class AdaptadorEjerciciosEmtrenamientos extends RecyclerView.Adapter<AdaptadorEjerciciosEmtrenamientos.MyHolder> {

    Context contexto;
    ArrayList<Ejercicios> ejercicios;
    View v;
    OnTipoSelectedListener listener;

    public AdaptadorEjerciciosEmtrenamientos(Context contexto, ArrayList<Ejercicios> ejercicios) {
        this.contexto = contexto;
        this.ejercicios = ejercicios;
        listener = (OnTipoSelectedListener) contexto;
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        v = LayoutInflater.from(contexto).inflate(R.layout.item_ejercicios_entrenamientos, parent, false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position, @NonNull List<Object> payloads) {
        final Ejercicios temporal = ejercicios.get(position);

        holder.nombre.setText(temporal.getNombre());
        Glide.with(contexto).load(temporal.getImagen()).into(holder.imagen);
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

            imagen = itemView.findViewById(R.id.imagen_ejercicio_entrenamiento);
            nombre = itemView.findViewById(R.id.nombre_ejercicio_entrenamiento);
        }
    }

    public interface OnTipoSelectedListener {
        public void onTipoSelected(Ejercicios ej);
    }
}
