/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.plan;

import DAO.DAOException;

/**
 * Esta clase enlaza los alimentos con las dietas, teniendo en cuenta que su
 * relación es de muchos a muchos.
 *
 * @author Victor
 */
public class AlxDiet extends BasePlan {

    private int alxdietkey;
    private Alimento alimento;
    private double cantidad;

    public AlxDiet() {
    }

    public AlxDiet(Alimento alimento, Plan plan, String momento, String dia, double cantidad) throws DAOException {
        super(plan, momento, dia);
        setAlimento(alimento);
        setCantidad(cantidad);
    }

    public AlxDiet(int alxdietkey, Alimento alimento, Plan plan, String momento, String dia, double cantidad) throws DAOException {
        super(plan, momento, dia);
        setAlxdietkey(alxdietkey);
        setAlimento(alimento);
        setCantidad(cantidad);
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

    public void setAlimento(Alimento alimento) throws DAOException {
        if (alimento.isEmpty()) {
            throw new DAOException("Seleccione un alimento");
        }
        this.alimento = alimento;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) throws DAOException {
        if (cantidad <= 0) {
            throw new DAOException("Digite una cantidad válida");
        }
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
        return getCantidad() + "g DE " + getAlimento()
                + ", CARBS: " + getCarbohidratosxpeso()
                + "g, PROTEIN: " + getProteinasxpeso()
                + "g FAT: " + getGrasasxpeso() + "g";
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty() || getAlimento().isEmpty() || getAlxdietkey() < 0;
    }

}
