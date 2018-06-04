package retamar.com.gym_app.asyntask;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import retamar.com.gym_app.R;
import retamar.com.gym_app.utils.Modelo;

public class ForgotPasswordTask extends AsyncTask<Void, Void, Void> {

    private Context contexto;
    private String email;

    public ForgotPasswordTask(Context contexto, String email) {
        this.contexto = contexto;
        this.email = email;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isComplete()) {
                    Toast.makeText(contexto, contexto.getString(R.string.email_enviado), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(contexto, contexto.getString(R.string.error_email), Toast.LENGTH_SHORT).show();
                }
            }
        });

        return null;
    }
}
