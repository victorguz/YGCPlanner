/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.plan;

import DAO.DAO;
import DAO.DAOException;
import javafx.collections.ObservableList;
import modelo.plan.AlxDiet;
import modelo.plan.EjxRut;

public interface EjxRutDAO extends DAO<EjxRut, String> {

    public ObservableList<EjxRut> obtenerTodos(int plankey, String dia) throws DAOException;

    public EjxRut obtener(int ejerciciokey, String dia) throws DAOException;
}
