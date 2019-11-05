/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.ObservableList;
import org.sqlite.JDBC;

/**
 *
 * @author Victor
 * @param <T>
 * @param <K>
 */
public interface DAO<T, K> {


    void insertar(T a) throws DAOException;

    void modificar(T a) throws DAOException;

    void eliminar(T a) throws DAOException;

    T convertir(ResultSet rs) throws DAOException;

    ObservableList<T> obtenerTodos() throws DAOException;

    ObservableList<T> obtenerTodos(String dato) throws DAOException;

    T obtener(String equal) throws DAOException;
}
