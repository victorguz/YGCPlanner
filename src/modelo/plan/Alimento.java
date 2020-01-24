/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.plan;

import DAO.DAOException;
import controlador.Operacion;

/**
 *
 * @author Victor
 */
public class Alimento {

    private int alimentokey;
    private String nombre = "";
    private double proteinas;
    private double grasas;
    private double carbohidratos;
    private String unidad;

    public Alimento() {

    }

    public Alimento(String nombre, double proteinas, double grasas, double carbohidratos) {
        setNombre(nombre);
        setProteinas(proteinas);
        setGrasas(grasas);
        setCarbohidratos(carbohidratos);
    }

    public Alimento(int alimentoKey, String nombre, double proteinas, double grasas, double carbohidratos) {
        setAlimentokey(alimentoKey);
        setNombre(nombre);
        setProteinas(proteinas);
        setGrasas(grasas);
        setCarbohidratos(carbohidratos);
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
        this.nombre = Operacion.nombreCamelCase(nombre);
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

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    @Override
    public String toString() {
        return getNombre();
    }

    public boolean isEmpty() {
        return getNombre().isEmpty();
    }

}
