package retamar.com.gym_app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import retamar.com.gym_app.asyntask.ForgotPasswordTask;
import retamar.com.gym_app.utils.Modelo;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener{

    Modelo modelo;
    FirebaseAuth mAuth;

    EditText emailRecuperacion;
    TextView linkLogin;
    AppCompatButton btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotgot_password);
        instancias();
        acciones();
        modelo.activityTransparent(ForgotPassword.this);
    }

    private void acciones() {
        linkLogin.setOnClickListener(this);
        btnReset.setOnClickListener(this);
    }

    private void instancias() {
        modelo = new Modelo();
        linkLogin = findViewById(R.id.link_login);
        btnReset = findViewById(R.id.btn_reset);
        emailRecuperacion = findViewById(R.id.input_email_forgot);
        mAuth = FirebaseAuth.getInstance();
    }

    private void firebaseResetPassword(String email) {
        if(TextUtils.isEmpty(email)) {
            emailRecuperacion.setError("Debe introducir un email");
        }

        modelo.showProgressDialog("Enviando Email", "Espere por favor...", ForgotPassword.this);

        if(modelo.compruebaConexion(ForgotPassword.this)) {
            ForgotPasswordTask task = new ForgotPasswordTask(this, emailRecuperacion.getText().toString());
            task.execute();
        }
        else {
            modelo.hideProgressDialog();
            Toast.makeText(getApplicationContext(), getString(R.string.error_conexion), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_reset:
                    firebaseResetPassword(emailRecuperacion.getText().toString());
                break;

            case R.id.link_login:
                    startActivity(new Intent(ForgotPassword.this, Login.class));
                    finish();
                break;
        }
    }
}
