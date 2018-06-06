package retamar.com.gym_app.fragmentos;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.collection.LLRBNode;

import java.util.HashMap;
import java.util.Locale;

import retamar.com.gym_app.R;
import retamar.com.gym_app.utils.Usuario;

public class FragmentoPerfil extends Fragment {

    View v;
    Context contexto;

    ImageView imagen;
    TextView nombre, email, imc, imc_edit, edad, peso, altura, edad_edit, peso_edit, altura_edit;
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
        acciones();
        rellenarNombre();
        rellenarDatos();
    }

    private void acciones() {
        imc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onIMCSelected();
            }
        });
    }

    private void rellenarNombre() {

        final FirebaseDatabase database;
        final DatabaseReference referencia;
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();

        referencia = database.getReference("Usuarios");
        referencia.child(user.getUid()).child("fullname").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String valor = (String) dataSnapshot.getValue();

                if(user != null) {

                    nombre.setText(user.getDisplayName());
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

    public void rellenarDatos() {
        final FirebaseDatabase database;
        final DatabaseReference referencia;
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();

        referencia = database.getReference("Usuarios");

        if(user != null) {

            referencia.child(user.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild("edad")) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            String edad = (String) data.getValue();

                            if (data.getKey().equals("edad")) {
                                edad_edit.setText(String.valueOf(edad));
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            referencia.child(user.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild("peso")) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            String peso = (String) data.getValue();

                            if (data.getKey().equals("peso")) {
                                peso_edit.setText(String.valueOf(peso));
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            referencia.child(user.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild("altura")) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            String altura = (String) data.getValue();

                            if (data.getKey().equals("altura")) {
                                altura_edit.setText(String.valueOf(altura));
                            }
                        }
                    }
                    //Se pone aqui porque ya sabemos que se han rellenado los campos. De la otra forma no estaban rellenos
                    if(!peso_edit.getText().toString().isEmpty() || !altura_edit.getText().toString().isEmpty() )
                        rellenarIMC(Double.parseDouble(peso_edit.getText().toString()), Double.parseDouble(altura_edit.getText().toString()));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private void rellenarIMC(double peso, double altura) {
        try {
            double calculo = peso/((altura/100)*(altura/100));

            if(calculo<18 || calculo>25) {
                imc_edit.setTextColor(Color.RED);
                imc_edit.setText(String.format(Locale.getDefault(), "%.2f", calculo));
            }
            else{
                imc_edit.setTextColor(Color.GREEN);
                imc_edit.setText(String.format(Locale.getDefault(), "%.2f", calculo));
            }
        }
        catch (Exception e) {
            Toast.makeText(contexto, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void instancias() {
        imagen = v.findViewById(R.id.perfil_imagen);
        nombre = v.findViewById(R.id.perfil_nombre);
        email = v.findViewById(R.id.perfil_email);
        imc = v.findViewById(R.id.imc);
        imc_edit = v.findViewById(R.id.imc_edit);
        edad = v.findViewById(R.id.perfil_edad);
        edad_edit = v.findViewById(R.id.perfil_edad_edit);
        peso = v.findViewById(R.id.perfil_peso);
        peso_edit = v.findViewById(R.id.perfil_peso_edit);
        altura = v.findViewById(R.id.perfil_altura);
        altura_edit = v.findViewById(R.id.perfil_altura_edit);

    }

    public interface OnIMCListener {
        public void onIMCSelected();
    }
}
