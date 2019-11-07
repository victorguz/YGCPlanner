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
public abstract class BasePlan {

    protected String momento = "";
    private String dia = "";
    private Plan plan;
    public static final String DOMINGO = "DOMINGO";
    public static final String LUNES = "LUNES";
    public static final String MARTES = "MARTES";
    public static final String MIERCOLES = "MIERCOLES";
    public static final String JUEVES = "JUEVES";
    public static final String VIERNES = "VIERNES";
    public static final String SABADO = "SABADO";
    public static final String AYUNO = "AYUNO";
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

    public BasePlan(Plan plan, String momento, String dia) throws DAOException {
        setPlan(plan);
        setMomento(momento);
        setDia(dia);
    }

    public String getMomento() {
        return momento;
    }

    public void setMomento(String momento) throws DAOException {
        if (momento.isEmpty()) {
            throw new DAOException("Seleccione el momento del día");
        }
        this.momento = momento;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) throws DAOException {
        if (dia.isEmpty()) {
            throw new DAOException("Seleccione un dia de la semana");
        }
        this.dia = dia;
    }

    public boolean isEmpty() {
        return getPlan().isEmpty() || getMomento().isEmpty()
                || getDia().isEmpty();
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) throws DAOException {
        if (plan.isEmpty()) {
            throw new DAOException("MTMOM: Seleccione un(a) " + plan.getClass().getCanonicalName());
        }
        this.plan = plan;
    }
}
