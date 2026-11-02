package dto;

import java.time.LocalDate;

public class AlumnoConRegistroDTO {
    private int id;
    private String nombre;
    private String apellidos;
    private String curso;
    private String ejercicio;
    private LocalDate fecha;
    private double puntuacion;
    private double media;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(String ejercicio) {
        this.ejercicio = ejercicio;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(double puntuacion) {
        this.puntuacion = puntuacion;
    }

    public double getMedia() {
        return media;
    }

    public void setMedia(double media) {
        this.media = media;
    }

    public AlumnoConRegistroDTO(int id, String nombre, String apellidos, String curso, String ejercicio,
            LocalDate fecha, double puntuacion, double media) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.curso = curso;
        this.ejercicio = ejercicio;
        this.fecha = fecha;
        this.puntuacion = puntuacion;
        this.media = media;
    }

    @Override
    public String toString() {
        return "AlumnoConRegistroDTO [id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos + ", curso=" + curso
                + ", ejercicio=" + ejercicio + ", fecha=" + fecha + ", puntuacion=" + puntuacion + ", media=" + media
                + "]";
    }

}
