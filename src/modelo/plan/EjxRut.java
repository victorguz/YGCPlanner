/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.plan;

import controlador.Operacion;

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
    public static final String[] UNIDADES = new String[]{"Repeticiones", "Segundos", "Minutos", "Horas"};
    private int ejxrutkey;
    private Ejercicio ejercicio = new Ejercicio();
    private int repeticiones;
    private int series;
    private String unidad = "Repeticiones";
    private String presentacion = "cant unidad x # series de ejercicio";

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

    public int getCantidad() {
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

    public String getUnidad() {
        return this.unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getUnidadSingular() {
        switch (getUnidad().toLowerCase()) {
            case "repeticiones":
                return "repeticion";
            case "segundos":
                return "segundo";
            case "minutos":
                return "minuto";
            case "horas":
                return "hora";
            default:
                return getUnidad().toLowerCase();
        }
    }

    public String getUnidadPlural() {
        return getUnidad().toLowerCase();
    }

    public String getPresentacion() {
        return presentacion.toLowerCase();
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public String calcularPresentacion() {
        String nombre = ((getCantidad() == 1 || getSeries() == 1) ? getEjercicio().getNombre()
                :(getEjercicio().getPlural().isEmpty())?
                getEjercicio().getNombre():getEjercicio().getPlural() );
        String unidad = ((getCantidad() == 1 || getSeries() == 1) ? getUnidadSingular() : getUnidadPlural());
        nombre = ((unidad.contains("segundo") || unidad.contains("minuto") || unidad.contains("hora")) ? getEjercicio().getPlural() : nombre);

        return getPresentacion().replaceAll("ejercicio",nombre)
                //.replaceAll("plural", getEjercicio().getPlural())
                .replaceAll("#cantidad", Operacion.formatear(getCantidad()))
                .replaceAll("#series", Operacion.formatear(getSeries()))
                .replaceAll("unidad",unidad);

    }

    @Override
    public String toString() {
        return calcularPresentacion();
    }

    @Override
    public boolean isEmpty() {
        return getPlan().isEmpty()
                || getEjercicio().isEmpty()
                || getPlan().isEmpty()
                || getDia().isEmpty();
    }

}
