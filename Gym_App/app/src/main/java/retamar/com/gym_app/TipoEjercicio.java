package retamar.com.gym_app;

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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import retamar.com.gym_app.adaptadores.AdaptadorRecycler_Lista;
import retamar.com.gym_app.adaptadores.AdaptadorTipoEjercicio;
import retamar.com.gym_app.utils.Ejercicios;

public class TipoEjercicio extends AppCompatActivity implements AdaptadorTipoEjercicio.OnListaCheckListener {

    RecyclerView recycler;
    List<Ejercicios> ejercicios;

    FirebaseDatabase database;
    DatabaseReference referencia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_ejercicio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        String ho = getIntent().getExtras().getString("TipoEjercicio");
        Toast.makeText(this, ho, Toast.LENGTH_SHORT).show();
        instancias();
        rellenarLista();
    }

    private void instancias() {
        recycler = findViewById(R.id.recycler_tipo_ejercicio);
    }

    private void rellenarLista() {
        database = FirebaseDatabase.getInstance();
        referencia = database.getReference("Tipo Ejercicios");

        ejercicios = new ArrayList<>();

        final AdaptadorTipoEjercicio adaptador = new AdaptadorTipoEjercicio(this, ejercicios);

        referencia.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ejercicios.add(dataSnapshot.getValue(Ejercicios.class));
                adaptador.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        recycler.setAdapter(adaptador);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        /*recycler.setLayoutManager(new GridLayoutManager(contexto,2,
                LinearLayoutManager.VERTICAL,false));*/
    }

    @Override
    public void onListaCheck(Ejercicios ej) {

    }
}
