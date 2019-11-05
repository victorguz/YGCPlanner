/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.plan;

import DAO.DAOException;


/**
 * En esta clase se registrarán todos los posibles ejercicios que el instructor
 * pondrá a sus clientes.
 *
 * @author Victor
 */
public class Ejercicio {

    private int ejerciciokey;
    private String nombre="";
    private String descripcion="";
    private String comentarios="";

    public Ejercicio() {
    }

    public Ejercicio(int ejerciciokey, String nombre, String descripcion, String comentarios) throws DAOException {
        setEjerciciokey(ejerciciokey);
        setNombre(nombre);
        setDescripcion(descripcion);
        setComentarios(comentarios);
    }

    public Ejercicio(String nombre, String descripcion, String comentarios) throws DAOException {
        setNombre(nombre);
        setDescripcion(descripcion);
        setComentarios(comentarios);
    }

    public int getEjerciciokey() {
        return ejerciciokey;
    }

    public void setEjerciciokey(int ejerciciokey) throws DAOException {
        if (ejerciciokey < 0) {
            throw new DAOException("La llave primaria no puede ser " + ejerciciokey);
        }
        this.ejerciciokey = ejerciciokey;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) throws DAOException {
        if (nombre.isEmpty()) {
            throw new DAOException("Digite un nombre");
        }
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) throws DAOException {
        if (descripcion.isEmpty()) {
            throw new DAOException("Digite una descripcion");
        }
        this.descripcion = descripcion;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) throws DAOException {
        if (comentarios.isEmpty()) {
            throw new DAOException("Digite un comentario");
        }
        this.comentarios = comentarios;
    }

    public boolean isEmpty() {
        if (getEjerciciokey() < 0) {
            return false;
        }
        if (getNombre().isEmpty()) {
            return false;
        }
        if (getDescripcion().isEmpty()) {
            return false;
        }
        return !getComentarios().isEmpty();
    }
    @Override
    public String toString() {
        return getNombre();
    }
}
