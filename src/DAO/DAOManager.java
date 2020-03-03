/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.cliente.ClientesDAO;
import DAO.cliente.MedidasDAO;
import DAO.plan.*;

/**
 * @author Victor
 */
public interface DAOManager {

    ClientesDAO getClientesDAO();

    MedidasDAO getMedidasDAO();

    AlimentosDAO getAlimentosDAO();

    AlxDietDAO getAlimentosDietasDAO();

    PlanesDAO getPlanesDAO();

    EjerciciosDAO getEjerciciosDAO();

    EjxRutDAO getEjerciciosRutinasDAO();


    ReferenciasDAO getReferenciasDAO();

}
