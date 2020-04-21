/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import javafx.collections.ObservableList;
import modelo.Referencia;

/**
 * @author Victor
 */
public interface ReferenciasDAO extends DAO<Referencia, String> {
    ObservableList<Referencia> obtenerPlantillas() throws DAOException;
    public ObservableList<Referencia> obtenerPresentaciones(String alimentoOejercicio) throws DAOException;
}