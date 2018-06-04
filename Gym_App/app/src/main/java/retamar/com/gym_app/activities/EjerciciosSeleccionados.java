package retamar.com.gym_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import retamar.com.gym_app.R;
import retamar.com.gym_app.adaptadores.AdaptadorRecycler_Lista;
import retamar.com.gym_app.dialogos.DialogoEntrenamiento;
import retamar.com.gym_app.utils.Ejercicios;
import retamar.com.gym_app.utils.Modelo;

public class EjerciciosSeleccionados extends AppCompatActivity implements AdaptadorRecycler_Lista.OnTipoSelectedListener,
        DialogoEntrenamiento.OnDialogoIntroListener{

    private RecyclerView recycler;
    ArrayList<Ejercicios> array;
    public static String TAG_DIALOGO = "NombreEntrenamiento";

    Modelo modelo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicios_seleccionados);
        instancias();
        configurarToolbar();
        rellenarLista();
    }

    private void instancias() {
        recycler = findViewById(R.id.recycler_ejercicios_seleccionados);
        array = (ArrayList<Ejercicios>) getIntent().getExtras().get(TipoEjercicio.TAG_SELECCIONADOS);
        modelo = new Modelo();
    }

    private void configurarToolbar() {
        Toolbar toolbar =  findViewById(R.id.toolbar_seleccionados);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_ejercicios_seleccionados));
        }

        // Boton volver atras.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void rellenarLista() {
        final AdaptadorRecycler_Lista adaptador = new AdaptadorRecycler_Lista(this, array);
        recycler.setAdapter(adaptador);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                array.remove(viewHolder.getAdapterPosition());
                adaptador.notifyDataSetChanged();
            }
        });

        itemTouchHelper.attachToRecyclerView(recycler);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_anadir, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_upload:
                    lanzarDialogo();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void lanzarDialogo() {
        DialogoEntrenamiento dialog = new DialogoEntrenamiento();
        dialog.show(getSupportFragmentManager(), TAG_DIALOGO);
    }

    // Accion volver atras.
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onTipoSelected(Ejercicios ej) {
        TipoEjercicio.TAG_EJERCICIO = ej.getNombre(); //Para que al recuperarse de DescripcionEjercicio lo haga bien.

        Intent i = new Intent(EjerciciosSeleccionados.this, DescripcionEjercicio.class);
        i.putExtra(TipoEjercicio.TAG_EJERCICIO, ej);
        startActivity(i);
    }

    private void guardarEntrenamiento(String nombre) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference refencia = database.getReference();
        HashMap<String, Ejercicios> names = new HashMap<>();
        //refencia.setValue(modelo.obtenerFechaHoy());

        for (int i= 0; i< array.size(); i++) {
            array.get(i).setFecha(modelo.obtenerFechaHoy());
            array.get(i).setEntrenamiento(nombre);
            names.put(array.get(i).getNombre(), array.get(i));
        }


        refencia.child("Ejercicios Usuarios").child(FirebaseAuth.getInstance().getUid()).child(modelo.obtenerFechaHoy()).child(nombre).setValue(names); //Agregamos el array
        refencia.child("Entrenamientos Usuarios").child(FirebaseAuth.getInstance().getUid()).child(modelo.obtenerFechaHoy()).child(nombre).child("nombre").setValue(nombre); //Establecemos un nombre al entrenamiento

    }

    @Override
    public void onDialogoSelected(String nombre) {
        guardarEntrenamiento(nombre);

        Toast.makeText(this, "Entrenamiento guardado", Toast.LENGTH_SHORT).show();
    }
}
