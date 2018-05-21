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
import java.util.Objects;

import retamar.com.gym_app.adaptadores.AdaptadorRecycler_Lista;
import retamar.com.gym_app.adaptadores.AdaptadorTipoEjercicio;
import retamar.com.gym_app.asyntask.LeerEjerciciosBDTask;
import retamar.com.gym_app.utils.Ejercicios;

public class TipoEjercicio extends AppCompatActivity implements AdaptadorTipoEjercicio.OnListaCheckListener {

    RecyclerView recycler;
    String ejercicio;

    FirebaseDatabase database;
    DatabaseReference referencia;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_ejercicio);
        configurarToolbar();
        instancias();
        rellenarLista();

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    private void configurarToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(Principal.TAG_EJERCICIO);
        }

        // Boton volver atras.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void instancias() {
        recycler = findViewById(R.id.recycler_tipo_ejercicio);
        ejercicio = Objects.requireNonNull(getIntent().getExtras()).getString(Principal.TAG_EJERCICIO);
    }

    private void rellenarLista() {

        database = FirebaseDatabase.getInstance();
        referencia = database.getReference("Ejercicios").child(ejercicio);

        LeerEjerciciosBDTask tarea = new LeerEjerciciosBDTask(this, database,referencia, recycler);
        tarea.execute();
    }

    // Accion volver atras.
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onListaCheck(Ejercicios ej) {

    }
}
