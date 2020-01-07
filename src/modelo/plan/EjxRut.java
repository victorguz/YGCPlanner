/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.plan;

/**
 * Esta clase enlaza la Plan con los ejercicios, teniendo en cuenta que la
 * relación es de muchos a muchos.
 *
 * @author Victor
 */
public class EjxRut extends BasePlan {

    private int ejxrutkey;
    private Ejercicio ejercicio = new Ejercicio();
    private int repeticiones;
    private int series;

    public EjxRut() {

    }

    public EjxRut(Plan Plan, Ejercicio ejercicio, int repeticiones, int series, String momento, String dia) {
        super(Plan, momento, dia);
        setEjercicio(ejercicio);
        setRepeticiones(repeticiones);
        setSeries(series);
    }

    public EjxRut(int ejxrutkey, Plan Plan, Ejercicio ejercicio, int repeticiones, int series, String momento, String dia, double peso) {
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

    public void setEjxrutkey(int ejxrutkey) {
        this.ejxrutkey = ejxrutkey;
    }

    public Ejercicio getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(Ejercicio ejercicio) {
        this.ejercicio = ejercicio;
    }

    public void setRepeticiones(int repeticiones) {
        this.repeticiones = repeticiones;
    }

    public int getRepeticiones() {
        return repeticiones;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getSeries() {
        return series;
    }

    @Override
    public String toString() {
        return getSeries() + " x " + getRepeticiones() + " de " + getEjercicio().getNombre();
    }

    @Override
    public boolean isEmpty() {
        return getPlan().isEmpty()
                || getEjercicio().isEmpty()
                || getRepeticiones() <= 0
                || getSeries() <= 0
                || getPlan().isEmpty()
                || getMomento().isEmpty()
                || getDia().isEmpty();
    }

}
