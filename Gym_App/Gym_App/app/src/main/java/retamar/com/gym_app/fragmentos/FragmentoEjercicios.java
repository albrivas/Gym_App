package retamar.com.gym_app.fragmentos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retamar.com.gym_app.R;
import retamar.com.gym_app.adaptadores.EjerciciosHolder;
import retamar.com.gym_app.adaptadores.FirebaseAdapter;
import retamar.com.gym_app.utils.Ejercicios;


public class FragmentoEjercicios extends Fragment {

    RecyclerView recycler;
    Context contexto;
    View v;
    static String TAG_REFERENCIA = "Tipo Ejercicios";

    FirebaseDatabase database;
    DatabaseReference referencia;

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

        FirebaseAdapter adaptadorFirebase = new FirebaseAdapter(
                Ejercicios.class,
                R.layout.item_lista_ejercicios
                ,EjerciciosHolder.class,
                referencia,
                contexto);

        recycler.setAdapter(adaptadorFirebase);
        recycler.setLayoutManager(new GridLayoutManager(
                contexto,
                2,
                LinearLayoutManager.VERTICAL,
                false));
        //recycler.addItemDecoration(new DividerItemDecoration(contexto, DividerItemDecoration.VERTICAL));
    }

    private void instancias() {
        recycler = v.findViewById(R.id.lista_recycler);
        database = FirebaseDatabase.getInstance();
        referencia = database.getReference(TAG_REFERENCIA);
    }
}
