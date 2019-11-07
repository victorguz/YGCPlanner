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
public class Alimento {

    private int alimentoKey;
    private String nombre = "";
    private String unidadDeMedida = "";
    private double proteinas;
    private double grasas;
    private double carbohidratos;
    private int uso;

    public Alimento() {

    }

    public Alimento(String nombre, double proteinas, double grasas, double carbohidratos) throws DAOException {
        setNombre(nombre);
        setProteinas(proteinas);
        setGrasas(grasas);
        setCarbohidratos(carbohidratos);
    }

    public Alimento(int alimentoKey, String nombre, double proteinas, double grasas, double carbohidratos) throws DAOException {
        setAlimentoKey(alimentoKey);
        setNombre(nombre);
        setProteinas(proteinas);
        setGrasas(grasas);
        setCarbohidratos(carbohidratos);
    }

    public int getAlimentoKey() {
        return alimentoKey;
    }

    public void setAlimentoKey(int alimentoKey) throws DAOException {
        if (alimentoKey <= 0) {
            throw new DAOException("La llave primaria no puede ser " + alimentoKey);
        }
        this.alimentoKey = alimentoKey;
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

    public String getUnidadDeMedida() {
        return unidadDeMedida;
    }

    public void setUnidadDeMedida(String unidadDeMedida) throws DAOException {
        if (unidadDeMedida.isEmpty()) {
            throw new DAOException("Digite una unidad de medida");
        }
        this.unidadDeMedida = unidadDeMedida;
    }

    public double getKilocalorias() {
        return getProteinas() * 4 + getGrasas() * 9 + getCarbohidratos() * 4;
    }

    public double getProteinas() {
        return proteinas;
    }

    public void setProteinas(double proteinas) throws DAOException {
        if (proteinas < 0) {
            throw new DAOException("Medida de proteinas incorrectas");
        }
        this.proteinas = proteinas;
    }

    public double getGrasas() {
        return grasas;
    }

    public void setGrasas(double grasas) throws DAOException {
        if (grasas < 0) {
            throw new DAOException("Medida de grasas incorrectas");
        }
        this.grasas = grasas;
    }

    public double getCarbohidratos() {
        return carbohidratos;
    }

    public void setCarbohidratos(double carbohidratos) {
        this.carbohidratos = carbohidratos;
    }

    @Override
    public String toString() {
        return getNombre();
    }

    public boolean isEmpty() {
        return getNombre().isEmpty()
                || getProteinas() < 0
                || getGrasas() < 0
                || getCarbohidratos() < 0;
    }

    public int getUso() {
        return uso;
    }

    public void setUso(int uso) throws DAOException {
        if(uso<0){
            throw new DAOException("Este valor de uso es inválido");
        }
        this.uso = uso;
    }
    
    public void usar(){
        this.uso++;
    }
    public void reiniciarUso(){
        this.uso=0;
    }
}
