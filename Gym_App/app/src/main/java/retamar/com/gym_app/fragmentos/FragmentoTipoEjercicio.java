package retamar.com.gym_app.fragmentos;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import retamar.com.gym_app.R;
import retamar.com.gym_app.adaptadores.AdaptadorRecycler_Lista;
import retamar.com.gym_app.utils.Ejercicios;

public class FragmentoTipoEjercicio extends Fragment {

    RecyclerView recycler;
    Context contexto;
    View v;
    List<Ejercicios> ejercicios;
    AdaptadorRecycler_Lista adaptadorRecycler_lista;

    FirebaseDatabase database;
    DatabaseReference referencia;
    SwipeRefreshLayout refreshLayout;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        contexto = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragmento_ejercicios, container, false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        instancias();
        rellenarLista();
    }

    private void rellenarLista() {
        database = FirebaseDatabase.getInstance();
        referencia = database.getReference("Tipo Ejercicios");

        ejercicios = new ArrayList<>();

        final AdaptadorRecycler_Lista adaptador = new AdaptadorRecycler_Lista(contexto, ejercicios);

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
        /*recycler.setLayoutManager(new GridLayoutManager(contexto,2,
                LinearLayoutManager.VERTICAL,false));*/
    }

    private void instancias() {
        recycler = v.findViewById(R.id.lista_recycler);
    }
}

