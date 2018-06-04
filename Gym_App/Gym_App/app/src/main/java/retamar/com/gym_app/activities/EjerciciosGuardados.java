package retamar.com.gym_app.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import retamar.com.gym_app.R;
import retamar.com.gym_app.utils.Ejercicios;
import retamar.com.gym_app.utils.Entrenamiento;

public class EjerciciosGuardados extends AppCompatActivity {

    ListView lista;
    String fecha;
    TextView textoFecha;
    static String TAG_REFERENCIA;
    FirebaseDatabase database;
    DatabaseReference referencia;
    FirebaseUser user;
    ArrayList<Entrenamiento> array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicios_guardados);
        instancias();
        configurarToolbar();
        rellenarLista();
    }

    private void rellenarLista() {

        referencia = database.getReference().child(TAG_REFERENCIA).child(user.getUid()).child(fecha);
        referencia.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren())
                    array.add(childSnapshot.getValue(Entrenamiento.class));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,array);
        lista.setAdapter(adapter);
        ((BaseAdapter) lista.getAdapter()).notifyDataSetChanged();

    }

    private void instancias() {
        textoFecha = findViewById(R.id.fecha_ejercicio);
        lista = findViewById(R.id.lista_entrenamientos);
        if(Principal.TAG_FECHA != null) {
            fecha = getIntent().getExtras().getString(Principal.TAG_FECHA);
            textoFecha.setText(fecha);
        }
        database = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        TAG_REFERENCIA = "Ejercicios Usuarios";
        array = new ArrayList<>();
    }

    private void configurarToolbar() {
        Toolbar toolbar =  findViewById(R.id.toolbar_guardados);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_ejercicios_guardados));
        }

        // Boton volver atras.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    // Accion volver atras.
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
