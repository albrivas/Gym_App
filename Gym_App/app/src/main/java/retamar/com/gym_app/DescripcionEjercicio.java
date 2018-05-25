package retamar.com.gym_app;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.io.Serializable;
import java.util.Objects;

import retamar.com.gym_app.utils.Ejercicios;

public class DescripcionEjercicio extends YouTubeBaseActivity {

    Ejercicios ejercicio;
    YouTubePlayerView youTubePlayerView;
    YouTubePlayer.OnInitializedListener listener;
    TextView descripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripcion_ejercicio);
        instancias();
        configurarToolbar();
        rellenar();
    }

    private void rellenar() {
        if(ejercicio != null) {
            descripcion.setText(ejercicio.getDescripcion());
            Toast.makeText(this, ejercicio.getDescripcion(), Toast.LENGTH_LONG).show();
        }
    }

    private void instancias() {
        ejercicio = (Ejercicios) getIntent().getExtras().getSerializable(TipoEjercicio.TAG_EJERCICIO);
        descripcion = findViewById(R.id.descripcion);
    }

    private void configurarToolbar() {
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(Principal.TAG_EJERCICIO);
        }

        // Boton volver atras.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);*/
    }

}
