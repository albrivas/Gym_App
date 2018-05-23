package retamar.com.gym_app.adaptadores;

import android.content.Context;
import android.view.View;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import retamar.com.gym_app.utils.Ejercicios;

public class FilterFirebaseAdapter extends FirebaseRecyclerAdapter<Ejercicios, TipoEjercicioHolder> {

    Context contexto;
    OnFilterEjercicioSelectedListener listener;


    public FilterFirebaseAdapter(Class<Ejercicios> modelClass, int modelLayout, Class<TipoEjercicioHolder> viewHolderClass, Query query, Context contexto) {
        super(modelClass, modelLayout, viewHolderClass,  query);
        this.contexto = contexto;
        listener = (OnFilterEjercicioSelectedListener) contexto;
    }

    @Override
    protected void populateViewHolder(TipoEjercicioHolder viewHolder, final Ejercicios model, int position) {
        viewHolder.nombre.setText(model.getNombre());
        Glide.with(contexto).load(model.getImagen()).into(viewHolder.imagen);
        viewHolder.imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onFilterEjercicioSelected(model);
            }
        });
    }

    public interface OnFilterEjercicioSelectedListener {
        public void onFilterEjercicioSelected(Ejercicios ej);
    }


}