package retamar.com.gym_app.utils;

import retamar.com.gym_app.R;

public class Entrenamiento {
    String nombre;

    public Entrenamiento() {
    }

    public Entrenamiento(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return getNombre();
    }
}
