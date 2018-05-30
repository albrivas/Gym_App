package retamar.com.gym_app.asyntask;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import retamar.com.gym_app.adaptadores.AdaptadorTipoEjercicio;
import retamar.com.gym_app.utils.Ejercicios;

public class LeerEjerciciosBDTask extends AsyncTask<Void, Void, Void> {

    Context contexto;
    FirebaseDatabase database;
    DatabaseReference referencia;
    RecyclerView recycler;


    public LeerEjerciciosBDTask(Context contexto, FirebaseDatabase database, DatabaseReference referencia, RecyclerView recycler) {
        this.contexto = contexto;
        this.database = database;
        this.referencia = referencia;
        this.recycler = recycler;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        final List<Ejercicios> ejercicios = new ArrayList<>();
        final AdaptadorTipoEjercicio adaptador = new AdaptadorTipoEjercicio(contexto, ejercicios);

        if(referencia.getKey().equals("Ejercicios")) {
            referencia.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    // Doble bucle foreach para recorrer un hijo que tiene otro hijo y obtener los datos
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        for (DataSnapshot snapshot2 : snapshot.getChildren()) {
                            Ejercicios ej = snapshot2.getValue(Ejercicios.class);
                            ejercicios.add(ej);
                            adaptador.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        recycler.setAdapter(adaptador);
        recycler.setLayoutManager(new LinearLayoutManager(contexto, LinearLayoutManager.VERTICAL, false));
        recycler.addItemDecoration(new DividerItemDecoration(contexto, DividerItemDecoration.VERTICAL));

        return null;


    }
}
