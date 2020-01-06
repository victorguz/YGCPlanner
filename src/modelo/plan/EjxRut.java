/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.plan;

import DAO.DAOException;

/**
 * Esta clase enlaza la Plan con los ejercicios, teniendo en cuenta que la
 * relación es de muchos a muchos.
 *
 * @author Victor
 */
public class EjxRut extends BasePlan {

    private int ejxrutkey;
    private Plan Plan = new Plan();
    private Ejercicio ejercicio = new Ejercicio();
    private int repeticiones;
    private int series;

    public EjxRut() {

    }

    public EjxRut(Plan Plan, Ejercicio ejercicio, int repeticiones, int series, String momento, String dia) throws DAOException {
        super(Plan, momento, dia);
        setEjercicio(ejercicio);
        setRepeticiones(repeticiones);
        setSeries(series);
    }

    public EjxRut(int ejxrutkey, Plan Plan, Ejercicio ejercicio, int repeticiones, int series, String momento, String dia, double peso) throws DAOException {
        super(Plan, momento, dia);
        setEjxrutkey(ejxrutkey);
        setPlan(Plan);
        setEjercicio(ejercicio);
        setRepeticiones(repeticiones);
        setSeries(series);
    }

    public int getEjxrutkey() {
        return ejxrutkey;
    }

    public void setEjxrutkey(int ejxrutkey) throws DAOException {
        if (ejxrutkey < 0) {
            throw new DAOException("La llave primaria no puede ser " + ejxrutkey);
        }
        this.ejxrutkey = ejxrutkey;
    }

    public Plan getPlan() {
        return Plan;
    }

    public Ejercicio getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(Ejercicio ejercicio) throws DAOException {
        if (ejercicio.isEmpty()) {
            throw new DAOException("EJXRUT: Seleccione un ejercicio");
        }
        this.ejercicio = ejercicio;
    }
    public void setRepeticiones(int repeticiones) throws DAOException {
        if (repeticiones <= 0) {
            throw new DAOException("Digite un numero de repeticiones valido");
        }
        this.repeticiones = repeticiones;
    }

    public int getRepeticiones() {
        return repeticiones;
    }

    public void setSeries(int series) throws DAOException {
        if (series < 0) {
            throw new DAOException("Digite un numero de series valido");
        }
        this.series = series;
    }

    public int getSeries() {
        return series;
    }

    @Override
    public boolean isEmpty() {
        return getPlan().isEmpty()
                || getEjercicio().isEmpty()
                || getRepeticiones() <= 0
                || getSeries() <= 0
                || super.isEmpty();
    }

}
