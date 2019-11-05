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
    private Plan Plan;
    private Ejercicio ejercicio;
    private int repeticiones;

    public EjxRut() {

    }

    public EjxRut(Plan Plan, Ejercicio ejercicio, int repeticiones, String momento, String dia, double peso) throws DAOException {
        super(Plan, momento, dia, peso); 
        setEjercicio(ejercicio);
        setRepeticiones(repeticiones);
    }

    public EjxRut(int ejxrutkey, Plan Plan, Ejercicio ejercicio, int repeticiones, String momento, String dia, double peso) throws DAOException {
        super(Plan, momento, dia, peso);
        setEjxrutkey(ejxrutkey);
        setPlan(Plan);
        setEjercicio(ejercicio);
        setRepeticiones(repeticiones);
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

    public void setPlan(Plan Plan) throws DAOException {
        if (Plan.isEmpty()) {
            throw new DAOException("EJXRUT: Seleccione una Plan");
        }
        this.Plan = Plan;
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

    public void setMomento(String momento) throws DAOException {
        if (momento.isEmpty()) {
            throw new DAOException("Seleccione un momento de la Plan");
        }
        String m = momento;
        switch (m) {
            case "POST-ENTRENO":
            case "PRE-ENTRENO":
            case "DURANTE EL ENTRENO":
            case "INTERCALADO CON EL EJERCICIO ANTERIOR":
            case "EN AYUNAS":
                super.momento = m;
                break;
            default:
                throw new DAOException("Seleccione un momento de la Plan");
        }
    }

    public void setRepeticiones(int repeticiones) throws DAOException {
        if (repeticiones < 0) {
            throw new DAOException("Digite un numero de repeticiones valido");
        }
        this.repeticiones = repeticiones;
    }

    public int getRepeticiones() {
        return repeticiones;
    }

    @Override
    public boolean isEmpty() {
        return getPlan().isEmpty()
                || getEjercicio().isEmpty()
                || getRepeticiones() < 0
                || super.isEmpty();
    }

}
