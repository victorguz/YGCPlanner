/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.plan;

import controlador.Operacion;


/**
 * En esta clase se registrarán todos los posibles ejercicios que el instructor
 * pondrá a sus clientes.
 *
 * @author Victor
 */
public class Ejercicio {

    private int ejerciciokey;
    private String nombre = "";
    private String plural = "";
    private String comentarios = "";

    public Ejercicio() {
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

    public String getPlural() {
        return plural;
    }

    public void setPlural(String plural) {
        this.plural = plural;
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
        return Operacion.inicialMayuscula(getNombre());
    }

}
