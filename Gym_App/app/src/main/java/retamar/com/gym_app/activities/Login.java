package retamar.com.gym_app.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import retamar.com.gym_app.R;
import retamar.com.gym_app.asyntask.EscribirBDTask;
import retamar.com.gym_app.asyntask.LoginFirebaseTask;
import retamar.com.gym_app.asyntask.LoginGoogleTask;
import retamar.com.gym_app.utils.Modelo;
import retamar.com.gym_app.utils.Usuario;

public class Login extends AppCompatActivity implements View.OnClickListener{

    private static final int RC_SIGN_IN = 123;
    Modelo modelo;

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInOptions gso;
    private FirebaseDatabase database;
    private DatabaseReference ref;

    private android.app.AlertDialog alertDialog;
    private TextView linkSignUp, linkOlvidar;
    private SignInButton btnGoogle;
    private AppCompatButton btnLogin;
    private EditText emailLogin, passwordLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        instancias();
        instanciasFirebase();
        instanciasGoogle();
        acciones();
        modelo.activityTransparent(Login.this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() != null) {
            startActivity(new Intent(Login.this, Principal.class));
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        modelo.hideProgressDialog();
    }

    private void acciones() {
        btnGoogle.setOnClickListener(this);
        linkSignUp.setOnClickListener(this);
        linkOlvidar.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    private void instanciasFirebase() {
        //Autenticacion de firebase
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();
    }

    private void instanciasGoogle() {
        //Configuracion inicio sesion con google
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void instancias() {
        btnGoogle = findViewById(R.id.boton_login_google);
        linkSignUp = findViewById(R.id.link_signup);
        linkOlvidar = findViewById(R.id.link_olvidar);
        btnLogin = findViewById(R.id.btn_login);
        emailLogin = findViewById(R.id.input_email);
        passwordLogin = findViewById(R.id.input_password);
        modelo = new Modelo();
    }

    private void signInGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void escribirBD(Task<AuthResult> task) {
        Usuario user = new Usuario(
                mAuth.getCurrentUser().getDisplayName(),
                mAuth.getCurrentUser().getEmail(),
                mAuth.getCurrentUser().getUid());

        EscribirBDTask escribirBDTask = new EscribirBDTask(task, user, "Usuarios");
        escribirBDTask.execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                //Inicio de sesion correcto, autenticamos con firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);

                if(modelo.compruebaConexion(getApplicationContext())) {
                    firebaseAuthWithGoogle(account);
                }
                else {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_conexion), Toast.LENGTH_SHORT).show();
                }
            } catch (ApiException e) {
                Toast.makeText(Login.this, getString(R.string.error_login), Toast.LENGTH_LONG).show();
            }
        }
    }

    // ATENCICACION GOOGLE CON FIREBASE.
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        modelo.showProgressDialog(
                "Iniciando Sesion",
                "Espre por favor...",
                Login.this);

        LoginGoogleTask loginGoogleTask = new LoginGoogleTask(this, acct);
        loginGoogleTask.execute();
    }

    // AUTENTICACION CON EMAIL y PASSWORD.
    private void signInFirebase(String email, final String password) {

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), getString(R.string.email_introducir), Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), getString(R.string.pass_introducir), Toast.LENGTH_SHORT).show();
            return;
        }

        if(password.length() < 6) {
            passwordLogin.setError(getString(R.string.pass_longitud));
            return;
        }

        //Autenticacion del usuario
        LoginFirebaseTask loginFirebaseTask = new LoginFirebaseTask(email, password, Login.this);
        loginFirebaseTask.execute();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.boton_login_google:
                    signInGoogle();
                break;

            case R.id.link_signup:
                    startActivity(new Intent(Login.this, CreateAccount.class));
                    Login.this.finish();
                break;

            case R.id.link_olvidar:
                    startActivity(new Intent(Login.this, ForgotPassword.class));
                    Login.this.finish();
                break;

            case R.id.btn_login:
                    signInFirebase(emailLogin.getText().toString(), passwordLogin.getText().toString());
                break;
        }
    }
}
