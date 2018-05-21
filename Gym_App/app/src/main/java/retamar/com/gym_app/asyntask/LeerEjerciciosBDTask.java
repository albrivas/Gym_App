package retamar.com.gym_app.asyntask;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        recycler.setLayoutManager(new LinearLayoutManager(contexto, LinearLayoutManager.VERTICAL, false));
        recycler.addItemDecoration(new DividerItemDecoration(contexto, DividerItemDecoration.VERTICAL));
        return null;
    }
}
