/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.plan;

import DAO.DAO;
import DAO.DAOException;
import javafx.collections.ObservableList;
import modelo.plan.Plan;

/**
 *
 * @author Victor
 */
public interface PlanesDAO extends DAO<Plan, String> {

    public ObservableList<Plan> obtenerRutinas(String equal) throws DAOException;

    public ObservableList<Plan> obtenerRutinas() throws DAOException;

    public ObservableList<Plan> obtenerDietas(String equal) throws DAOException;

    public ObservableList<Plan> obtenerDietas() throws DAOException;
}
