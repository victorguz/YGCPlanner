/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.plan;

import DAO.DAOException;

/**
 *
 * @author Victor
 */
public class Plan {

    private int plankey;
    private String nombre = "";
    private String objetivo = "";
    private String descripcion = "";
    private String sexo = "";
    private String tipo = "";
    private int edad;

    public Plan() {
    }

    public int getPlankey() {
        return plankey;
    }

    public void setPlankey(int plankey) throws DAOException {
        if (plankey < 0) {
            throw new DAOException("La clave del plan no puede ser " + plankey);
        }
        this.plankey = plankey;
    }

    public Plan(String nombre, String objetivo, String descripcion, String sexo, int edad) throws DAOException {
        setNombre(nombre);
        setObjetivo(objetivo);
        setDescripcion(descripcion);
        setSexo(sexo);
        setEdad(edad);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) throws DAOException {
        if (nombre.isEmpty() || nombre == null) {
            throw new DAOException("Digite el nombre del plan");
        }
        this.nombre = nombre;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo){
        this.objetivo = objetivo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) throws DAOException {
        if (descripcion.isEmpty()) {
            throw new DAOException("Digite una descripcion de " + getTipo().toLowerCase());
        }
        this.descripcion = descripcion;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) throws DAOException {
        this.sexo = sexo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) throws DAOException {
                this.tipo = tipo;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) throws DAOException {
        if (edad < 0) {
            throw new DAOException("Digite una edad valida");
        }
        this.edad = edad;
    }

    @Override
    public String toString() {
        return getNombre() + ", PARA " + getSexo() + " DE " + getEdad() + " AÑOS, OBJETIVO " + getObjetivo();
    }

    public boolean isEmpty() {
        return getNombre().isEmpty()
                || getObjetivo().isEmpty()
                || getDescripcion().isEmpty()
                || getSexo().isEmpty()
                || getEdad() < 0
                || getTipo().isEmpty();
    }
}
