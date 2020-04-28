/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.plan;

import controlador.Operacion;

import java.util.ArrayList;


/**
 * @author Victor
 */
public class Plan {

    private int plankey;
    private String nombre = "";
    private String tipo = "";

    public Plan(String tipo) {
        setTipo(tipo);
    }

    public int getPlankey() {
        return plankey;
    }

    public void setPlankey(int plankey) {
        this.plankey = plankey;
    }

    public String getNombre() {
        return nombre.toLowerCase();
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    private void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return Operacion.toCamelCase(getNombre());
    }

    public boolean isEmpty() {
        return getNombre().isEmpty()
                || getTipo().isEmpty();
    }

    public ArrayList<String> toArray() {
        ArrayList<String> n = new ArrayList<>();
        return n;
    }
}
