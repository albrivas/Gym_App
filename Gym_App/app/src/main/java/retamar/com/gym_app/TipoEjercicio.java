package retamar.com.gym_app;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Objects;

import retamar.com.gym_app.adaptadores.AdaptadorTipoEjercicio;
import retamar.com.gym_app.adaptadores.FirebaseAdapterTipo;
import retamar.com.gym_app.adaptadores.FilterFirebaseAdapter;
import retamar.com.gym_app.adaptadores.TipoEjercicioHolder;
import retamar.com.gym_app.asyntask.LeerEjerciciosBDTask;
import retamar.com.gym_app.utils.Ejercicios;

public class TipoEjercicio extends AppCompatActivity implements
        FirebaseAdapterTipo.OnEjercicioSelectedListener,
        FilterFirebaseAdapter.OnFilterEjercicioSelectedListener,
        AdaptadorTipoEjercicio.OnListaCheckListener{

    RecyclerView recycler;
    String ejercicio;

    FirebaseDatabase database;
    DatabaseReference referencia;
    android.support.v7.widget.SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_ejercicio);
        configurarToolbar();
        instancias();
    }

    @Override
    protected void onStart() {
        rellenarLista();
        super.onStart();
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
    }

    private void rellenarLista() {

        database = FirebaseDatabase.getInstance();

        if(ejercicio != null) {

            referencia = database.getReference("Ejercicios").child(ejercicio);

            if (ejercicio.equals("Todos")) {
                referencia = database.getReference("Ejercicios");
                LeerEjerciciosBDTask tarea = new LeerEjerciciosBDTask(this, database, referencia, recycler);
                tarea.execute();

            } else {
                FirebaseAdapterTipo adaptadorFirebase = new FirebaseAdapterTipo(Ejercicios.class,R.layout.item_tipo_ejercicio
                        ,TipoEjercicioHolder.class,referencia,this);

                recycler.setAdapter(adaptadorFirebase);
                recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                recycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (android.support.v7.widget.SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        //searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        //searchView.setMaxWidth(Integer.MAX_VALUE);

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

        return true;
    }

    private void FirebaseSearch(String searhText) {
        Query firebaseSearchQuery = referencia.orderByChild("nombre").startAt(searhText).endAt(searhText + "\uf8ff");

        FilterFirebaseAdapter firebaseRecyclerAdapter = new FilterFirebaseAdapter(

                Ejercicios.class,
                R.layout.item_tipo_ejercicio,
                TipoEjercicioHolder.class,
                firebaseSearchQuery, this

        ) {
            @Override
            protected void populateViewHolder(TipoEjercicioHolder viewHolder, Ejercicios model, int position) {

                viewHolder.setDetails(getApplicationContext(), model.getNombre(), model.getImagen());
            }

        };
        
        recycler.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                break;

                // Boton de cerrar searchview. Al pulsarlo volvemos a rellenar la lista
            case R.id.search_close_btn:
                rellenarLista();
                break;
        }

        return super.onOptionsItemSelected(item);
    }



    // Accion volver atras.
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onEjercicioSelected(Ejercicios ej) {
        Toast.makeText(this, ej.getNombre(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFilterEjercicioSelected(Ejercicios ej) {
        Toast.makeText(this, ej.getNombre(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onListaCheck(Ejercicios ej) {
        Toast.makeText(this, ej.getNombre(), Toast.LENGTH_SHORT).show();
    }
}
