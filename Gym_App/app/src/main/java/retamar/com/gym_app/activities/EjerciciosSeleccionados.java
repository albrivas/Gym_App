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
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import retamar.com.gym_app.R;
import retamar.com.gym_app.adaptadores.AdaptadorRecycler_Lista;
import retamar.com.gym_app.utils.Ejercicios;

public class EjerciciosSeleccionados extends AppCompatActivity implements AdaptadorRecycler_Lista.OnTipoSelectedListener {

    private RecyclerView recycler;
    ArrayList<Ejercicios> array;

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
}
