/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.plan;

import DAO.DAOException;

import java.util.ArrayList;

/**
 * Esta clase enlaza los alimentos con las dietas, teniendo en cuenta que su
 * relación es de muchos a muchos.
 *
 * @author Victor
 */
public class AlxDiet extends BasePlan {

    //Momentos alimenticios
    public static final String DESAYUNO = "Desayuno";
    public static final String ALMUERZO = "Almuerzo";
    public static final String CENA = "Cena";
    public static final String PREENTRENO = "Preentreno";
    public static final String POSTENTRENO = "Postentreno";
    public static final String SNACKAM = "Snack AM";
    public static final String SNACKPM = "Snack PM";
    private int alxdietkey;
    private Alimento alimento;
    private double cantidad = 0;

    public AlxDiet() {
    }

    public int getAlxdietkey() {
        return alxdietkey;
    }

    public void setAlxdietkey(int alxdietkey) throws DAOException {
        if (alxdietkey < 0) {
            throw new DAOException("Llave primaria incorrecta");
        }
        this.alxdietkey = alxdietkey;
    }

    public Alimento getAlimento() {
        return alimento;
    }

    public void setAlimento(Alimento alimento) {
        this.alimento = alimento;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getKilocaloriasxpeso() {
        return getAlimento().getKilocalorias() * getCantidad() / 100;
    }

    public double getProteinasxpeso() {
        return getAlimento().getProteinas() * getCantidad() / 100;
    }

    public double getGrasasxpeso() {
        return getAlimento().getGrasas() * getCantidad() / 100;
    }

    public double getCarbohidratosxpeso() {
        return getAlimento().getCarbohidratos() * getCantidad() / 100;
    }

    @Override
    public String toString() {
        String unidad=getAlimento().getUnidad();
        String a="";
        if(unidad.contains("unidad")){
            a=" unidades de";
        }else if(unidad.contains("gramo")){
            a=" gramos de ";
        }else if(unidad.contains("mililitro")){
            a=" mililitros de ";
        }
        return getCantidad() + a + getAlimento().toString();
    }

    @Override
    public boolean isEmpty() {
        return getPlan().isEmpty()
                || getMomento().isEmpty()
                || getDia().isEmpty()
                || getAlimento().isEmpty()
                || getCantidad() <= 0;
    }

}
