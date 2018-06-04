package retamar.com.gym_app.adaptadores;

import android.content.Context;
import android.view.View;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import retamar.com.gym_app.activities.EjerciciosGuardados;
import retamar.com.gym_app.utils.Ejercicios;

public class EjercicioEntrenamientoFirebaseAdapter extends FirebaseRecyclerAdapter<Ejercicios, EjercicioEntrenamientoHolder> {

    Context contexto;
    OnEjercicioEntrenamientoSelectedListener listener;

    public EjercicioEntrenamientoFirebaseAdapter(Class<Ejercicios> modelClass, int modelLayout, Class<EjercicioEntrenamientoHolder> viewHolderClass, Query query, Context contexto) {
        super(modelClass, modelLayout, viewHolderClass, query);
        this.contexto = contexto;
        listener = (OnEjercicioEntrenamientoSelectedListener) contexto;
    }

    @Override
    protected void populateViewHolder(EjercicioEntrenamientoHolder viewHolder, final Ejercicios model, int position) {
        viewHolder.nombre.setText(model.getNombre());
        Glide.with(contexto).load(model.getImagen()).into(viewHolder.imagen);
        viewHolder.imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onEjercicioEntrenamientoSelected(model);
            }
        });
    }

    public interface OnEjercicioEntrenamientoSelectedListener {
        public void onEjercicioEntrenamientoSelected(Ejercicios ej);
    }
}
