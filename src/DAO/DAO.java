/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import javafx.collections.ObservableList;

import java.sql.ResultSet;

/**
 * @param <T>
 * @param <K>
 * @author Victor
 */
public interface DAO<T, K> {


    void insert(T a) throws DAOException;

    void update(T a) throws DAOException;

    void delete(T a) throws DAOException;

    T convertir(ResultSet rs) throws DAOException;

    ObservableList<T> all() throws DAOException;

    ObservableList<T> where(String dato) throws DAOException;

    T select(K equal) throws DAOException;
}
