/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.plan;

/**
 * @author Victor
 */
public abstract class BasePlan {

    //Dias
    public static final String DOMINGO = "Domingo";
    public static final String LUNES = "Lunes";
    public static final String MARTES = "Martes";
    public static final String MIERCOLES = "Miercoles";
    public static final String JUEVES = "Jueves";
    public static final String VIERNES = "Viernes";
    public static final String SABADO = "Sabado";
    private String dia = "";
    private String momento = "";
    private Plan plan;


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

    public String getMomento() {
        return momento;
    }

    public void setMomento(String momento) {
        this.momento = momento;
    }

}
