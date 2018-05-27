package retamar.com.gym_app.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import dmax.dialog.SpotsDialog;
import retamar.com.gym_app.Principal;
import retamar.com.gym_app.R;

public class Modelo {

    private android.app.AlertDialog alertDialog;
    private ProgressDialog progressDialog;

    //COMPROBAR CONEXION INTERNET
    public boolean compruebaConexion(Context context) {

        boolean connected = false;

        ConnectivityManager connec = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        // Recupera todas las redes (tanto móviles como wifi)
        NetworkInfo[] redes = connec.getAllNetworkInfo();

        for (int i = 0; i < redes.length; i++) {
            // Si alguna red tiene conexión, se devuelve true
            if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
                connected = true;
            }
        }
        return connected;
    }

    public void activityTransparent(AppCompatActivity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    public void showDialog(Context contexto) {
        alertDialog = new SpotsDialog(contexto, "Iniciacion sesion....");
        alertDialog.show();
    }

    public void hideDialog() {
        if(alertDialog != null) {
            alertDialog.hide();
        }
    }

    // DIALOGO NORMAL.
    public void showProgressDialog(String title, String message, Context contexto) {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.setMessage(message);
        else
            progressDialog = ProgressDialog.show(contexto, title, message, true, false);
    }

    // CERRAR DIALOGO.
    public void hideProgressDialog() {
        if(progressDialog != null)
            progressDialog.hide();
    }

    public void signOut(FirebaseAuth auth, GoogleSignInClient client) {
        auth.signOut();
        client.signOut();
    }

    public void signOutFirebase(FirebaseAuth auth) {
        auth.signOut();
    }
}
