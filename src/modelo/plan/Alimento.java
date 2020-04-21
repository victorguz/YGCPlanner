/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.plan;

import controlador.Operacion;

/**
 * @author Victor
 */
public class Alimento {


    private int alimentokey;
    private String nombre = "";
    private String plural = "";
    private double proteinas;
    private double grasas;
    private double carbohidratos;


    public Alimento() {

    }

    public int getAlimentokey() {
        return alimentokey;
    }

    public void setAlimentokey(int alimentokey) {
        this.alimentokey = alimentokey;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = (nombre);
    }


    public double getKilocalorias() {
        return getProteinas() * 4 + getGrasas() * 9 + getCarbohidratos() * 4;
    }

    public double getProteinas() {
        return proteinas;
    }

    public void setProteinas(double proteinas) {
        this.proteinas = proteinas;
    }

    public double getGrasas() {
        return grasas;
    }

    public void setGrasas(double grasas) {
        this.grasas = grasas;
    }

    public double getCarbohidratos() {
        return carbohidratos;
    }

    public void setCarbohidratos(double carbohidratos) {
        this.carbohidratos = carbohidratos;
    }

    public String getPlural() {
        return plural;
    }

    public void setPlural(String plural) {
        this.plural = plural;
    }

    @Override
    public String toString() {
        return Operacion.inicialMayuscula(getNombre());
    }

    public boolean isEmpty() {
        return getNombre().isEmpty();
    }

}
