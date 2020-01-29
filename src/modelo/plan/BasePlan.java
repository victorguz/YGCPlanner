/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.plan;

/**
 *
 * @author Victor
 */
public abstract class BasePlan {

    private String dia = "";
    private String combinacion = "";
    private Plan plan;
    public static final String DOMINGO = "DOMINGO";
    public static final String LUNES = "LUNES";
    public static final String MARTES = "MARTES";
    public static final String MIERCOLES = "MIERCOLES";
    public static final String JUEVES = "JUEVES";
    public static final String VIERNES = "VIERNES";
    public static final String SABADO = "SABADO";
    public static final String DESAYUNO = "DESAYUNO";
    public static final String ALMUERZO = "ALMUERZO";
    public static final String CENA = "CENA";
    public static final String PREENTRENO = "PREENTRENO";
    public static final String POSTENTRENO = "POSTENTRENO";
    public static final String ENTRENO = "ENTRENO";
    public static final String MERIENDA = "MERIENDA";
    public static final String EXTRA = "EXTRA";

    public BasePlan() {
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public abstract boolean isEmpty();

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public String getCombinacion() {
        return combinacion;
    }

    public void setCombinacion(String combinacion) {
        this.combinacion = combinacion;
    }

}
