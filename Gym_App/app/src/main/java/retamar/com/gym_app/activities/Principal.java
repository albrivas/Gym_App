package retamar.com.gym_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;
import retamar.com.gym_app.R;
import retamar.com.gym_app.adaptadores.AdaptadorViewPager;
import retamar.com.gym_app.adaptadores.FirebaseAdapter;
import retamar.com.gym_app.asyntask.LeerUsuarioTask;
import retamar.com.gym_app.dialogos.DialogoInicio;
import retamar.com.gym_app.utils.Ejercicios;
import retamar.com.gym_app.utils.Modelo;

public class Principal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener,
        DialogoInicio.OnDialogoInicio, FirebaseAdapter.OnTipoSelectedListener {

    Modelo modelo;

    //Firebase y Google
    FirebaseAuth mAuth;
    FirebaseUser user;
    private GoogleSignInClient mGoogleSignInClient;


    FloatingActionButton fab;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    ViewPager viewPager;
    TabLayout tableLayout;
    static String TAG_EJERCICIO = "Todos";

    View headerView;
    CircleImageView imagenUsuario;
    TextView nombreUsuario, emailUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        instancias();
        instanciasFirebase();
        acciones();
        configurarToolbar();
        configurarPager();
        datosPerfil();
    }

    @Override
    protected void onStart() {
        super.onStart();
        datosPerfil();
    }

    private void instanciasFirebase() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Firebase -> Inicializar.
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
    }

    private void datosPerfil() {
        if(user != null) {

            nombreUsuario.setText(user.getDisplayName());
            emailUsuario.setText(user.getEmail());

            if(user.getPhotoUrl() != null) {
                Glide.with(Principal.this).load(user.getPhotoUrl()).into(imagenUsuario);
            }
            if(user.getDisplayName() == null || user.getDisplayName().equals("")) {
                LeerUsuarioTask leerUsuarioTask = new LeerUsuarioTask(mAuth, nombreUsuario);
                leerUsuarioTask.execute();
            }
            else {
                Glide.with(Principal.this).load(R.drawable.profile).into(imagenUsuario);
            }
        }
    }

    private void acciones() {
        navigationView.setNavigationItemSelectedListener(this);
        fab.setOnClickListener(this);
    }

    private void instancias() {
        modelo = new Modelo();
        fab = findViewById(R.id.fab);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        headerView = navigationView.getHeaderView(0);
        imagenUsuario = headerView.findViewById(R.id.header_imagen);
        emailUsuario = headerView.findViewById(R.id.header_email);
        nombreUsuario = headerView.findViewById(R.id.header_nombre);
        viewPager = findViewById(R.id.view_pager);
        tableLayout = findViewById(R.id.pestanias);
    }

    private void configurarPager() {
        AdaptadorViewPager adapter = new AdaptadorViewPager(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);
        tableLayout.setupWithViewPager(viewPager);

         /*int[] imageResId = {
                 R.drawable.lista,
                 R.drawable.ejercicios,
                 R.drawable.ejercicios,
                 R.drawable.perfil_pager};

        for (int i = 0; i < imageResId.length; i++) {
            tableLayout.getTabAt(i).setIcon(imageResId[i]);
        }*/
    }

    private void configurarToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_session:
                modelo.signOut(mAuth, mGoogleSignInClient);
                startActivity(new Intent(Principal.this, Login.class));
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch(item.getItemId()) {

            case R.id.nav_sesion:
                modelo.signOut(mAuth, mGoogleSignInClient);
                startActivity(new Intent(Principal.this, Login.class));
                finish();
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                break;
        }
    }

    @Override
    public void onTipoSelected(Ejercicios ej) {
        TAG_EJERCICIO = ej.getNombre();

        Intent i = new Intent(Principal.this, TipoEjercicio.class);
        i.putExtra(TAG_EJERCICIO, ej.getNombre());
        startActivity(i);
    }

    @Override
    public void onDataDialogoInicio(String s) {

    }
}
