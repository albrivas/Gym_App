package retamar.com.gym_app.utils;

import java.io.Serializable;

public class Usuario implements Serializable {

    private String fullname, email, uid;
    private int edad;
    private double peso, altura, IMC;

    public Usuario() {
    }

    public Usuario(String fullname, String email, String uid, int edad, double peso, double altura) {
        this.fullname = fullname;
        this.email = email;
        this.uid = uid;
        this.edad = edad;
        this.peso = peso;
        this.altura = altura;
        this.IMC = IMC;
    }

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

    public int getEdad() {
        return edad;
    }

    public double getPeso() {
        return peso;
    }

    public double getAltura() {
        return altura;
    }

    public double getIMC() {
        return IMC;
    }
}
