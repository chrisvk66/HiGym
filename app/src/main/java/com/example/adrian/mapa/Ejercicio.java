package com.example.adrian.mapa;

/**
 * Created by alumno on 16/12/2016.
 */

public class Ejercicio {
    protected enum Tipo {BRAZOS, HOMBRO, PECHO, ABDOMINALES, PIERNAS, ESPALDA, CARDIO};
    protected Tipo tipoEjercicio;

    private String nombre;
    private String descripcion;
    private String repeticiones;

    public Ejercicio(String nombre){
        this.nombre = nombre;
    }

    public Ejercicio(String nombre, String descripcion){
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String toString(){
        return nombre;
    }

        //get and set
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(String tiempo) {
        this.repeticiones = tiempo;
    }

    public Tipo getGenero() {
        return tipoEjercicio;
    }

    public void setGenero(Tipo genero) {
        this.tipoEjercicio = genero;
    }
}
