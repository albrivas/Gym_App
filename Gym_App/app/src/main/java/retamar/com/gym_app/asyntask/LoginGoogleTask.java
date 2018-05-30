package retamar.com.gym_app.asyntask;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import retamar.com.gym_app.activities.Principal;
import retamar.com.gym_app.R;
import retamar.com.gym_app.utils.Usuario;

public class LoginGoogleTask extends AsyncTask<Void, Void, Void> {

    Context contexto;
    GoogleSignInAccount account;

    public LoginGoogleTask(Context contexto, GoogleSignInAccount account) {
        this.contexto = contexto;
        this.account = account;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Usuario user = new Usuario(mAuth.getCurrentUser().getDisplayName(),
                                    mAuth.getCurrentUser().getEmail(),
                                    mAuth.getCurrentUser().getUid());

                            EscribirBDTask escribirBDTask = new EscribirBDTask(task, user, "Usuarios");
                            escribirBDTask.execute();
                            contexto.startActivity(new Intent(contexto, Principal.class));
                        } else {
                            Toast.makeText(contexto, contexto.getString(R.string.error_autenticacion), Toast.LENGTH_LONG).show();
                        }
                    }
                });

        return null;
    }
}
