package retamar.com.gym_app.asyntask;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import retamar.com.gym_app.utils.Modelo;
import retamar.com.gym_app.utils.Usuario;

public class CreateAccountTask extends AsyncTask<Void, Void, Void> {

    private String nombre, email, password;
    private Activity contexto;
    private Modelo modelo;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    public CreateAccountTask(String nombre, String email, String password, Activity contexto, Modelo modelo) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.contexto = contexto;
        this.modelo = modelo;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //modelo.showDialog(contexto);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(contexto, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Usuario user = new Usuario(nombre,
                                    email, task.getResult().getUser().getUid());

                            // Escribir usuario. Con la tarea asincrona no funciona
                            HashMap<String, Object> has = new HashMap<>();
                            has.put("email", user.getEmail());
                            has.put("fullname", user.getFullname());
                            has.put("uid", user.getUid());

                            database.getReference().child("Usuarios").child(user.getUid()).updateChildren(has);

                            modelo.signOutFirebase(mAuth); // Para que al ir a Login no inicie sesion directamente

                            Toast.makeText(contexto,
                                    "Usuario registrado", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                modelo.hideProgressDialog();
                                Toast.makeText(contexto,
                                        "Email ya registrado. Utilice otro", Toast.LENGTH_SHORT).show();
                            }

                            else if(task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(contexto,
                                        "El email introducido no es correcto", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(contexto,
                                        String.valueOf(task.getException()), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //modelo.hideDialog();
    }
}
