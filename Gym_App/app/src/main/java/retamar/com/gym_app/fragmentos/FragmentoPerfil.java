package retamar.com.gym_app.fragmentos;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import retamar.com.gym_app.R;
import retamar.com.gym_app.activities.Principal;
import retamar.com.gym_app.asyntask.LeerUsuarioTask;
import retamar.com.gym_app.dialogos.DialogoEntrenamiento;
import retamar.com.gym_app.utils.Usuario;

public class FragmentoPerfil extends Fragment {

    View v;
    Context contexto;

    ImageView imagen;
    TextView nombre, email, imc;
    Usuario u;

    OnIMCListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        contexto = context;
        listener = (OnIMCListener) contexto;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragmento_perfil, container, false);
        
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        instancias();
        rellenarDatos();
        acciones();
    }

    private void acciones() {
        imc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onIMCSelected();
            }
        });
    }

    private void rellenarDatos() {


        final FirebaseDatabase database;
        final DatabaseReference referencia;
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();

        referencia = database.getReference("Usuarios");
        referencia.child(user.getUid()).child("fullname").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Usuario u = dataSnapshot.getValue(Usuario.class);
                String valor = (String) dataSnapshot.getValue();

                if(user != null) {

                    nombre.setText(user.getDisplayName());
                    email.setText(user.getEmail());

                    email.setText(user.getEmail());
                    if (user.getPhotoUrl() != null) {
                        Glide.with(contexto).load(user.getPhotoUrl()).into(imagen);
                    } else if (user.getDisplayName() == null || user.getDisplayName().equals("")) {
                        nombre.setText(valor);
                    } else {
                        Glide.with(contexto).load(R.drawable.ic_perfil).into(imagen);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void instancias() {
        imagen = v.findViewById(R.id.perfil_imagen);
        nombre = v.findViewById(R.id.perfil_nombre);
        email = v.findViewById(R.id.perfil_email);
        imc = v.findViewById(R.id.imc);
    }

    public interface OnIMCListener {
        public void onIMCSelected();
    }
}
