package retamar.com.gym_app.utils;

import java.io.Serializable;

public class Ejercicios {

    String nombre, imagen, video, descripcion;

    public Ejercicios() {
    }

    public Ejercicios(String nombre, String imagen) {
        this.nombre = nombre;
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public String getVideo() {
        return video;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
