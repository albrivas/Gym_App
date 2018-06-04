package retamar.com.gym_app.asyntask;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import retamar.com.gym_app.activities.Principal;
import retamar.com.gym_app.R;

public class LoginFirebaseTask extends AsyncTask<Void, Void, Void> {

    private String email, password;
    private Activity contexto;

    public LoginFirebaseTask(String email, String password, Activity contexto) {
        this.email = email;
        this.password = password;
        this.contexto = contexto;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(email, password).
                addOnCompleteListener(contexto, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    contexto.startActivity(new Intent(contexto, Principal.class));
                    contexto.finish();
                }
                else {
                    if(task.getException() instanceof FirebaseAuthInvalidUserException) {
                        Toast.makeText(contexto, ""+task.getException(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(contexto, contexto.getString(R.string.error_autenticacion), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        return null;
    }
}
