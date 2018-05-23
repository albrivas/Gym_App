package retamar.com.gym_app.asyntask;

import android.os.AsyncTask;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LeerUsuarioBDTask extends AsyncTask<Void,Void,Void> {

    FirebaseAuth mAuth;
    TextView nombre;

    public LeerUsuarioBDTask(FirebaseAuth mAuth, TextView nombre) {
        this.mAuth = mAuth;
        this.nombre = nombre;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        final FirebaseDatabase database;
        final DatabaseReference referencia;
        database = FirebaseDatabase.getInstance();
        referencia = database.getReference("Usuarios");

        referencia.child(mAuth.getCurrentUser().getUid()).child("fullname").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String valor = (String) dataSnapshot.getValue();
                nombre.setText(valor);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return null;
    }
}
