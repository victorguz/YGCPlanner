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

    //Momentos entrenamiento
    public static final String BLOQUE1 = "Bloque 1";
    public static final String BLOQUE2 = "Bloque 2";
    public static final String BLOQUE3 = "Bloque 3";
    public static final String BLOQUE4 = "Bloque 4";
    public static final String BLOQUE5 = "Bloque 5";
    private int ejxrutkey;
    private Ejercicio ejercicio = new Ejercicio();
    private int repeticiones;
    private int series;

    public EjxRut() {

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

    public int getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(int repeticiones) {
        this.repeticiones = repeticiones;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    @Override
    public String toString() {
        String s = "";
        String r = "";
        if (getSeries() == 1) {
            s = " serie de ";
        } else {
            s = " series de ";
        }
        if (getRepeticiones() == 1) {
            r = " repetición de ";
        } else {
            r = " repeticiones de ";
        }
        return getSeries() + s + getRepeticiones() + r + getEjercicio().getNombre();
    }

    @Override
    public boolean isEmpty() {
        return getPlan().isEmpty()
                || getEjercicio().isEmpty()
                || getRepeticiones() <= 0
                || getSeries() <= 0
                || getPlan().isEmpty()
                || getDia().isEmpty();
    }

}
