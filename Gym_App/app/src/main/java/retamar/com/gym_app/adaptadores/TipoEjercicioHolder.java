package retamar.com.gym_app.adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import retamar.com.gym_app.R;

public class TipoEjercicioHolder extends RecyclerView.ViewHolder {

    ImageView imagen;
    TextView nombre;
    CheckBox check;

    public TipoEjercicioHolder(View itemView) {
        super(itemView);

        imagen = itemView.findViewById(R.id.imagen_tipo_ejercicio);
        nombre = itemView.findViewById(R.id.nombre_tipo_ejercicio);
        check = itemView.findViewById(R.id.checkbox);
    }

    public void setDetails(Context ctx, String userName, String imagenN){

        nombre.setText(userName);
        Glide.with(ctx).load(imagenN).into(imagen);
    }
}
