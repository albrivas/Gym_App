package retamar.com.gym_app.utils;

import java.io.Serializable;

public class Usuario implements Serializable {

    String fullname, email, uid, password;
    int edad;
    double peso, altura;

    public Usuario(String fullname, String email, String uid) {
        this.fullname = fullname;
        this.email = email;
        this.uid = uid;
    }

    public Usuario(int edad, double peso, double altura) {
        this.edad = edad;
        this.peso = peso;
        this.altura = altura;
    }

    public Usuario(String fullname, String email) {
        this.fullname = fullname;
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }

    public String getUid() {
        return uid;
    }
}
