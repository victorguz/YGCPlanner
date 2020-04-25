/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.plan;

import DAO.DAOException;
import controlador.Operacion;

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
    public static final String[] UNIDADES
            = new String[]{
            "Gramos", "Onzas", "Libras",
            "Unidades", "Mililitros", "Litros", "Tazas",
            "Vasos", "Rodajas", "Lonjas", "Torrejas",
            "Scoops", "Cucharadas", "Porciones", "Al gusto"
    };


    private int alxdietkey;
    private Alimento alimento;
    private double cantidad;
    private String unidad = "gramos";
    private String presentacion = "cantidad unidad de alimento";
    private int gramos;//gramos que equivale la porción

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

    public String getUnidad() {
        return this.unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getUnidadSingular() {
        switch (getUnidad().toLowerCase()) {
            case "unidades":
                return "";
            case "gramos":
                return "g";
            case "onzas":
                return "Oz";
            case "libras":
                return "lb";
            case "mililitros":
                return "mL";
            case "al gusto":
                return "al gusto";
            case "porciones":
                return "porcion";
            default:
                return getUnidad().toLowerCase().substring(0, this.unidad.length() - 1);
        }
    }

    public String getUnidadPlural() {
        switch (getUnidad().toLowerCase()) {
            case "unidades":
                return "";
            case "gramos":
                return "g";
            case "onzas":
                return "Oz";
            case "libras":
                return "lb";
            case "mililitros":
                return "mL";
            case "al gusto":
                return "al gusto";
            default:
                return getUnidad().toLowerCase();
        }
    }

    public String getPresentacion() {
        return presentacion.toLowerCase();
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public int getGramos() {
        return gramos;
    }

    public void setGramos(int gramos) {
        this.gramos = gramos;
    }

    public double getKilocaloriasxpeso() {
        return getAlimento().getKilocalorias() * getGramos() / 100;
    }

    public double getProteinasxpeso() {
        return getAlimento().getProteinas() * getGramos() / 100;
    }

    public double getGrasasxpeso() {
        return getAlimento().getGrasas() * getGramos() / 100;
    }

    public double getCarbohidratosxpeso() {
        return getAlimento().getCarbohidratos() * getGramos() / 100;
    }

    public String calcularPresentacion() {
        String nombre = ((getCantidad() == 1) ? getAlimento().getNombre()
                : (getAlimento().getPlural().isEmpty()) ?
                getAlimento().getNombre() : getAlimento().getPlural());
        String unidad = ((getCantidad() == 1) ? getUnidadSingular() : getUnidadPlural());
        if (getPresentacion().isEmpty()) {
            return Operacion.formatear(getCantidad()) + " " + unidad + (unidad.isEmpty() ? "" : " de ") + nombre;
        }
        return getPresentacion().replaceAll("#alimento", nombre)
                //.replaceAll("plural", getAlimento().getPlural())
                .replaceAll("#cantidad", Operacion.formatear(getCantidad()))
                .replaceAll("#unidad", unidad);
    }


    @Override
    public String toString() {
        return calcularPresentacion();
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
