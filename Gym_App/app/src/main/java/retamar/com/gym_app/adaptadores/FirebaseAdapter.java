package retamar.com.gym_app.adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import retamar.com.gym_app.R;
import retamar.com.gym_app.utils.Ejercicios;

public class FirebaseAdapter extends FirebaseRecyclerAdapter<Ejercicios, EjerciciosHolder> {

    Context contexto;
    OnTipoSelectedListener listener;

    public FirebaseAdapter(Class<Ejercicios> modelClass, int modelLayout, Class<EjerciciosHolder> viewHolderClass, DatabaseReference ref, Context contexto) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.contexto = contexto;
        listener = (OnTipoSelectedListener) contexto;
    }


    @Override
    protected void populateViewHolder(EjerciciosHolder viewHolder, final Ejercicios model, int position) {
        viewHolder.nombre.setText(model.getNombre());
        Glide.with(contexto).load(model.getImagen()).into(viewHolder.imagen);
        viewHolder.imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onTipoSelected(model);
            }
        });
    }

    public interface OnTipoSelectedListener {
        public void onTipoSelected(Ejercicios ej);
    }
}

