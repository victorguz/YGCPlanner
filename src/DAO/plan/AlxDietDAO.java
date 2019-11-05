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


public interface AlxDietDAO extends DAO<AlxDiet, String> {

        public ObservableList<AlxDiet> obtenerTodos(int plankey, String dia, String momento) throws DAOException;
}
