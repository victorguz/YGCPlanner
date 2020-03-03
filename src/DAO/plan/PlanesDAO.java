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
 * @author Victor
 */
public interface PlanesDAO extends DAO<Plan, Integer> {

    ObservableList<Plan> whereRutinas(String equal) throws DAOException;

    ObservableList<Plan> allRutinas() throws DAOException;

    ObservableList<Plan> whereDietas(String equal) throws DAOException;

    ObservableList<Plan> allDietas() throws DAOException;
}
