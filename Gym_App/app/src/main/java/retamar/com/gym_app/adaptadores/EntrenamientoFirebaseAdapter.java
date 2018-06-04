package retamar.com.gym_app.adaptadores;

import android.content.Context;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

import retamar.com.gym_app.utils.Ejercicios;
import retamar.com.gym_app.utils.Entrenamiento;

public class EntrenamientoFirebaseAdapter extends FirebaseRecyclerAdapter<Entrenamiento, EntrenamientoHolder> {

    Context contexto;
    OnEntrenamientoSelectedListener listener;

    public EntrenamientoFirebaseAdapter(Class<Entrenamiento> modelClass, int modelLayout, Class<EntrenamientoHolder> viewHolderClass, DatabaseReference ref, Context contexto) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.contexto = contexto;
        listener = (OnEntrenamientoSelectedListener) contexto;
    }

    @Override
    protected void populateViewHolder(EntrenamientoHolder viewHolder, final Entrenamiento model, int position) {
        viewHolder.nombre.setText(model.getNombre());
        viewHolder.imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onEntrenamientoSelected(model);
            }
        });
    }

    public interface OnEntrenamientoSelectedListener {
        public void onEntrenamientoSelected(Entrenamiento en);
    }
}
