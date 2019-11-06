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

    public AlxDiet() {
    }

    public AlxDiet(Alimento alimento, Plan plan, String momento, String dia, double peso) throws DAOException {
        super(plan, momento, dia, peso);
        setAlimento(alimento);
    }

    public AlxDiet(int alxdietkey, Alimento alimento, Plan plan, String momento, String dia, double peso) throws DAOException {
        super(plan, momento, dia, peso);
        setAlxdietkey(alxdietkey);
        setAlimento(alimento);
    }

    public void setMomento(String momento) throws DAOException {
        if (momento.isEmpty()) {
            throw new DAOException("Seleccione el momento del día");
        }
        super.momento = momento;
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

    public double getKilocaloriasxpeso() {
        return getAlimento().getKilocalorias() * getPeso() / 100;
    }

    public double getProteinasxpeso() {
        return getAlimento().getProteinas() * getPeso() / 100;
    }

    public double getGrasasxpeso() {
        return getAlimento().getGrasas() * getPeso() / 100;
    }

    public double getCarbohidratosxpeso() {
        return getAlimento().getCarbohidratos() * getPeso() / 100;
    }

    @Override
    public String toString() {
        return getPeso() + "g DE " + getAlimento() 
                + ", CARBS: " + getCarbohidratosxpeso() 
                + "g, PROTEIN: " + getProteinasxpeso()
                + "g FAT: " + getGrasasxpeso() + "g";
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty() || getAlimento().isEmpty() || getAlxdietkey() < 0;
    }

}
