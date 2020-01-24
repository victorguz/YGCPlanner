/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.plan;


/**
 * En esta clase se registrarán todos los posibles ejercicios que el instructor
 * pondrá a sus clientes.
 *
 * @author Victor
 */
public class Ejercicio {

    private int ejerciciokey;
    private String nombre = "";
    private String descripcion = "";
    private String comentarios = "";

    public Ejercicio() {
    }

    public Ejercicio(int ejerciciokey, String nombre, String descripcion, String comentarios) {
        setEjerciciokey(ejerciciokey);
        setNombre(nombre);
        setDescripcion(descripcion);
        setComentarios(comentarios);
    }

    public Ejercicio(String nombre, String descripcion, String comentarios) {
        setNombre(nombre);
        setDescripcion(descripcion);
        setComentarios(comentarios);
    }

    public int getEjerciciokey() {
        return ejerciciokey;
    }

    public void setEjerciciokey(int ejerciciokey) {
        this.ejerciciokey = ejerciciokey;
    }

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

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public boolean isEmpty() {
        return getNombre().isEmpty();
    }

    @Override
    public String toString() {
        return getNombre();
    }

}
