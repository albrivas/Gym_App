package retamar.com.gym_app.adaptadores;

import android.content.Context;
import android.view.View;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

import retamar.com.gym_app.utils.Ejercicios;

public class FirebaseAdapterTipo extends FirebaseRecyclerAdapter<Ejercicios, TipoEjercicioHolder> {

    Context contexto;
    OnEjercicioSelectedListener listener;

    public FirebaseAdapterTipo(Class<Ejercicios> modelClass, int modelLayout, Class<TipoEjercicioHolder> viewHolderClass, DatabaseReference ref, Context contexto) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.contexto = contexto;
        listener = (OnEjercicioSelectedListener) contexto;
    }

    @Override
    protected void populateViewHolder(TipoEjercicioHolder viewHolder, final Ejercicios model, int position) {
        viewHolder.nombre.setText(model.getNombre());
        Glide.with(contexto).load(model.getImagen()).into(viewHolder.imagen);
        viewHolder.imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onEjercicioSelected(model);
            }
        });
    }

    public interface OnEjercicioSelectedListener {
        public void onEjercicioSelected(Ejercicios ej);
    }
}
