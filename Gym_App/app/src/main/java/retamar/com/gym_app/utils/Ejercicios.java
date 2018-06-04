package retamar.com.gym_app.utils;

import java.io.Serializable;

public class Ejercicios implements Serializable {

    String nombre, imagen, video, descripcion, categoria, fecha, entrenamiento;

    public Ejercicios() {
    }

    public Ejercicios(String nombre, String imagen, String categoria) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.categoria = categoria;
    }

    public Ejercicios(String nombre, String imagen) {
        this.nombre = nombre;
        this.imagen = imagen;
    }

    public Ejercicios(String nombre, String imagen, String video, String descripcion, String categoria, String fecha, String entrenamiento) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.video = video;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.fecha = fecha;
        this.entrenamiento = entrenamiento;
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

    public String getCategoria() {
        return categoria;
    }

    public String getFecha() {
        return fecha;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEntrenamiento() {
        return entrenamiento;
    }

    public void setEntrenamiento(String entrenamiento) {
        this.entrenamiento = entrenamiento;
    }
}
