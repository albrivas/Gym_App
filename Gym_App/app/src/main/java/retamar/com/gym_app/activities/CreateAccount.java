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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import retamar.com.gym_app.R;
import retamar.com.gym_app.asyntask.CreateAccountTask;
import retamar.com.gym_app.utils.Modelo;

public class CreateAccount extends AppCompatActivity implements View.OnClickListener {

    Modelo modelo;

    EditText emailRegistro, passwordRegistro, nombreRegistro;
    AppCompatButton botonRegistro;
    TextView linkSignIn;

    FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        instanciasFirebase();
        instancias();
        acciones();
        modelo.activityTransparent(CreateAccount.this);
    }

    private void acciones() {
        botonRegistro.setOnClickListener(this);
        linkSignIn.setOnClickListener(this);
    }

    private void instanciasFirebase() {
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();
    }

    private void instancias() {
        modelo = new Modelo();
        emailRegistro = findViewById(R.id.input_email_reg);
        passwordRegistro = findViewById(R.id.input_password_reg);
        botonRegistro = findViewById(R.id.btn_registrar);
        linkSignIn = findViewById(R.id.link_signin);
        nombreRegistro = findViewById(R.id.input_name_reg);
    }

    private void createUserFirebase(String email, String password) {

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), getString(R.string.email_introducir), Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), getString(R.string.pass_introducir), Toast.LENGTH_SHORT).show();
            return;
        }

        if(password.length() < 6) {
            passwordRegistro.setError(getString(R.string.pass_longitud));
            return;
        }

        if(modelo.compruebaConexion(CreateAccount.this)) {

            CreateAccountTask createAccountTask = new CreateAccountTask(
                    nombreRegistro.getText().toString(),
                    emailRegistro.getText().toString(),
                    passwordRegistro.getText().toString(),
                    this,
                    modelo);

            createAccountTask.execute();
        }
        else {
            modelo.hideProgressDialog();
            Toast.makeText(getApplicationContext(), getString(R.string.error_conexion), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_registrar:
                    createUserFirebase(
                            emailRegistro.getText().toString(),
                            passwordRegistro.getText().toString());
                break;

            case R.id.link_signin:
                    startActivity(new Intent(CreateAccount.this, Login.class));
                break;
        }
    }
}
