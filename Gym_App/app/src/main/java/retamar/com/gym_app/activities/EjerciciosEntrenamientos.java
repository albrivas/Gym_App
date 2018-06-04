package retamar.com.gym_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import retamar.com.gym_app.R;
import retamar.com.gym_app.adaptadores.AdaptadorEjerciciosEmtrenamientos;
import retamar.com.gym_app.adaptadores.AdaptadorEntrenamientosGuardados;
import retamar.com.gym_app.adaptadores.EjercicioEntrenamientoFirebaseAdapter;
import retamar.com.gym_app.adaptadores.EjercicioEntrenamientoHolder;
import retamar.com.gym_app.adaptadores.EntrenamientoHolder;
import retamar.com.gym_app.utils.Ejercicios;
import retamar.com.gym_app.utils.Entrenamiento;

public class EjerciciosEntrenamientos extends AppCompatActivity implements AdaptadorEjerciciosEmtrenamientos.OnTipoSelectedListener {

    RecyclerView recycler;
    String nombreEntrenamiento;
    FirebaseDatabase database;
    DatabaseReference referencia;
    static String TAG_REFERENCIA = "Ejercicios Usuarios";
    FirebaseUser user;
    ArrayList<Ejercicios> array = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicios_entrenamientos);
        instancias();
        configurarToolbar();
        rellenarLista();
    }

    private void rellenarLista() {
        Toast.makeText(this, nombreEntrenamiento, Toast.LENGTH_LONG).show();
        referencia = database.getReference(TAG_REFERENCIA).child(user.getUid()).child(EjerciciosGuardados.TAG_FECHA).child(nombreEntrenamiento);

        final AdaptadorEjerciciosEmtrenamientos adaptador = new AdaptadorEjerciciosEmtrenamientos(this, array);

        referencia.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data: dataSnapshot.getChildren()) {

                        Ejercicios ej = data.getValue(Ejercicios.class);
                        array.add(ej);
                        adaptador.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


        recycler.setAdapter(adaptador);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void instancias() {
        recycler = findViewById(R.id.recycler_ejercicios_entrenamiento);
        nombreEntrenamiento = getIntent().getExtras().getString(EjerciciosGuardados.TAG_ENTRENAMIENTO);
        database = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    private void configurarToolbar() {
        Toolbar toolbar =  findViewById(R.id.toolbar_ejercicios_entrenamiento);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(nombreEntrenamiento);
        }

        // Boton volver atras.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    // Accion volver atras.
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onTipoSelected(Ejercicios ej) {
        //Toast.makeText(this, ej.getNombre(), Toast.LENGTH_SHORT).show();

        TipoEjercicio.TAG_EJERCICIO = ej.getNombre();
        Intent i = new Intent(EjerciciosEntrenamientos.this, DescripcionEjercicio.class);
        i.putExtra(TipoEjercicio.TAG_EJERCICIO, ej);
        startActivity(i);
    }
}
