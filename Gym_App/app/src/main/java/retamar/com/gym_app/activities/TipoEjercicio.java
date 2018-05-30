package retamar.com.gym_app.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.Toolbar;

import android.view.Menu;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


import java.util.Objects;

import retamar.com.gym_app.R;
import retamar.com.gym_app.adaptadores.FirebaseAdapterTipo;
import retamar.com.gym_app.adaptadores.FilterFirebaseAdapter;
import retamar.com.gym_app.adaptadores.TipoEjercicioHolder;

import retamar.com.gym_app.utils.Ejercicios;

public class TipoEjercicio extends AppCompatActivity implements
        FirebaseAdapterTipo.OnEjercicioSelectedListener,
        FilterFirebaseAdapter.OnFilterEjercicioSelectedListener {

    RecyclerView recycler;
    String ejercicio;
    static String TAG_EJERCICIO = "Todos";
    static String TAG_REFERENCIA = "Ejercicios2";
    FirebaseDatabase database;
    DatabaseReference referencia;
    android.support.v7.widget.SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_ejercicio);
        configurarToolbar();
        instancias();
        rellenarLista();
    }

    private void configurarToolbar() {
        Toolbar toolbar =  findViewById(R.id.toolbar);
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
        database = FirebaseDatabase.getInstance();
        referencia = database.getReference(TAG_REFERENCIA);
    }

    private void rellenarLista() {

        final FilterFirebaseAdapter firebaseRecyclerAdapter;
        Query query;

        if(ejercicio != null) {

             switch (ejercicio) {
                 case "Todos":

                     query = referencia.orderByChild("nombre");
                     firebaseRecyclerAdapter = new FilterFirebaseAdapter(

                             Ejercicios.class,
                             R.layout.item_tipo_ejercicio,
                             TipoEjercicioHolder.class,
                             query, this
                     );

                     break;

                     default:

                         query = referencia.orderByChild("categoria").equalTo(ejercicio);

                         firebaseRecyclerAdapter = new FilterFirebaseAdapter(

                                 Ejercicios.class,
                                 R.layout.item_tipo_ejercicio,
                                 TipoEjercicioHolder.class,
                                 query, this

                         );

                         break;
             }

            recycler.setAdapter(firebaseRecyclerAdapter);
            recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            recycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (android.support.v7.widget.SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                FirebaseSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                FirebaseSearch(newText);
                return false;
            }
        });

        /*searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                //rellenarLista();
                return false;
            }
        });*/


        return true;
    }

    private void FirebaseSearch(String searhText) {
        final Query firebaseSearchQuery = referencia.orderByChild("nombre").startAt(searhText).endAt(searhText + "\uf8ff");
        //Query query = referencia.orderByChild("categoria").equalTo("Abdominales");

        final FilterFirebaseAdapter firebaseRecyclerAdapter = new FilterFirebaseAdapter(

                Ejercicios.class,
                R.layout.item_tipo_ejercicio,
                TipoEjercicioHolder.class,
                firebaseSearchQuery, this

        );

        recycler.setAdapter(firebaseRecyclerAdapter);
    }

    // Accion volver atras.
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onEjercicioSelected(Ejercicios ej) {
        TAG_EJERCICIO = ej.getNombre();

        Intent i = new Intent(TipoEjercicio.this, DescripcionEjercicio.class);
        i.putExtra(TAG_EJERCICIO, ej);
        startActivity(i);
    }

    @Override
    public void onFilterEjercicioSelected(Ejercicios ej) {
        TAG_EJERCICIO = ej.getNombre();

        Intent i = new Intent(TipoEjercicio.this, DescripcionEjercicio.class);
        i.putExtra(TAG_EJERCICIO, ej);
        startActivity(i);
    }
}
