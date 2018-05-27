package retamar.com.gym_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;

import java.io.Serializable;
import java.util.Objects;

import retamar.com.gym_app.API.YoutubeConfig;
import retamar.com.gym_app.utils.Ejercicios;

public class DescripcionEjercicio extends AppCompatActivity {

    private Ejercicios ejercicio;
    private TextView descripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripcion_ejercicio);
        instancias();
        configurarToolbar();
        rellenar();
        videoYoutube();
    }

    private void videoYoutube() {
        YouTubePlayerFragment youtubeFragment = (YouTubePlayerFragment)
                getFragmentManager().findFragmentById(R.id.youtubeFragment);

        youtubeFragment.initialize(YoutubeConfig.KEY_DEVELOPER,
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {
                        youTubePlayer.cueVideo(ejercicio.getVideo());
                    }
                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });
    }

    private void rellenar() {
        if(ejercicio != null) {
            descripcion.setText(ejercicio.getNombre());
        }
    }

    private void instancias() {
        ejercicio = (Ejercicios) getIntent().getExtras().getSerializable(TipoEjercicio.TAG_EJERCICIO);
        descripcion = findViewById(R.id.descripcion);

    }

    private void configurarToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(TipoEjercicio.TAG_EJERCICIO);
        }

        // Boton volver atras.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
