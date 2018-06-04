package retamar.com.gym_app.asyntask;

import android.os.AsyncTask;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.FirebaseDatabase;

import retamar.com.gym_app.utils.Usuario;

public class EscribirBDTask extends AsyncTask<Void, Void, Void> {

    Task<AuthResult> task;
    Usuario user;
    String child;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    public EscribirBDTask(Task<AuthResult> task, Usuario user, String child) {
        this.task = task;
        this.user = user;
        this.child = child;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        database.getReference().child(child).child(task.getResult().getUser().getUid()).setValue(user);
        return null;
    }
}
