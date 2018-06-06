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
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import retamar.com.gym_app.R;
import retamar.com.gym_app.adaptadores.AdaptadorEjerciciosEmtrenamientos;
import retamar.com.gym_app.adaptadores.AdaptadorEntrenamientosGuardados;
import retamar.com.gym_app.adaptadores.EntrenamientoFirebaseAdapter;
import retamar.com.gym_app.adaptadores.EntrenamientoHolder;
import retamar.com.gym_app.utils.Ejercicios;
import retamar.com.gym_app.utils.Entrenamiento;

public class EjerciciosGuardados extends AppCompatActivity implements  AdaptadorEntrenamientosGuardados.OnTipoSelectedListener {

    RecyclerView recycler;
    String fecha;
    TextView textoFecha;
    static String TAG_REFERENCIA;
    static String TAG_ENTRENAMIENTO;
    static String TAG_FECHA;
    FirebaseDatabase database;
    DatabaseReference referencia;
    FirebaseUser user;
    ArrayList<Entrenamiento> array = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicios_guardados);
        instancias();
        configurarToolbar();
        rellenarLista();
    }

    private void rellenarLista() {

        referencia = database.getReference().child(TAG_REFERENCIA).child(user.getUid()).child(fecha);

        final AdaptadorEntrenamientosGuardados adaptador = new AdaptadorEntrenamientosGuardados(this, array );
        recycler.setAdapter(adaptador);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        referencia.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren())
                    array.add(childSnapshot.getValue(Entrenamiento.class));
                if(array.size() != 0) {
                    adaptador.notifyDataSetChanged();
                }
                else {
                    Entrenamiento u = new Entrenamiento("No hay entrenamientos disponibles");
                    array.add(u);
                    adaptador.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void instancias() {
        textoFecha = findViewById(R.id.fecha_ejercicio);
        recycler = findViewById(R.id.lista_entrenamientos);
        database = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        TAG_REFERENCIA = "Entrenamientos Usuarios";
        if(Principal.TAG_FECHA != null) {
            fecha = getIntent().getExtras().getString(Principal.TAG_FECHA);
            textoFecha.setText(fecha);
            TAG_FECHA = fecha;
        }
    }

    private void configurarToolbar() {
        Toolbar toolbar =  findViewById(R.id.toolbar_guardados);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_ejercicios_guardados));
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
    public void onTipoSelected(Entrenamiento ej) {
        TAG_ENTRENAMIENTO = ej.getNombre();
        if(!ej.getNombre().equals("No hay entrenamientos disponibles")) {
            Intent i = new Intent(EjerciciosGuardados.this, EjerciciosEntrenamientos.class);
            i.putExtra(TAG_ENTRENAMIENTO, ej.getNombre());
            startActivity(i);
        }
    }
}
