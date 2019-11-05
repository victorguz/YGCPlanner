/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import DAO.DAOException;


/**
 *
 * @author Victor
 */
public class Objetivo {

    private int objetivokey;
    private String objetivo="";

    public Objetivo() {
    }

    public Objetivo(String objetivo) throws DAOException {
        setObjetivo(objetivo);
    }

    public int getObjetivokey() {
        return objetivokey;
    }

    public void setObjetivokey(int objetivokey) throws DAOException {
        if (objetivokey<0) {
            throw new DAOException("Digite un objetivokey valido");
        }
        this.objetivokey = objetivokey;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) throws DAOException {
        if (objetivo.isEmpty()) {
            throw new DAOException("Digite un objetivo");
        }
        
        this.objetivo = objetivo;
    }

    @Override
    public String toString() {
        return getObjetivo();
    }

}
