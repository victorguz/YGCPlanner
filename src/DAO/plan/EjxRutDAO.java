/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.plan;

import DAO.DAO;
import DAO.DAOException;
import javafx.collections.ObservableList;
import modelo.plan.EjxRut;

public interface EjxRutDAO extends DAO<EjxRut, String> {

    ObservableList<EjxRut> where(int plankey, String dia) throws DAOException;

    ObservableList<EjxRut> where(int plankey, String dia, String momento) throws DAOException;

    void whereCopy(String planOrig, String planCopy) throws DAOException;

}
