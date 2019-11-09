/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.cliente.ClientesDAO;
import DAO.plan.AlimentosDAO;
import DAO.cliente.MedidasDAO;
import DAO.plan.EjerciciosDAO;
import DAO.plan.AlxDietDAO;
import DAO.plan.EjxRutDAO;
import DAO.plan.PlanDAO;

/**
 *
 * @author Victor
 */
public interface DAOManager {

    ClientesDAO getClientesDAO();

    MedidasDAO getMedidasDAO();

    AlimentosDAO getAlimentosDAO();

    AlxDietDAO getAlimentosDietasDAO();

    PlanDAO getRutinasDAO();

    PlanDAO getDietasDAO();

    PlanDAO getPlanesDAO();

    EjerciciosDAO getEjerciciosDAO();

    EjxRutDAO getEjerciciosRutinasDAO();


    ReferenciasDAO getReferenciasDAO();

}
