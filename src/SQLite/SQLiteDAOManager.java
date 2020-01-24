/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQLite;

import DAO.DAOException;
import DAO.cliente.ClientesDAO;
import DAO.cliente.MedidasDAO;
import DAO.DAOManager;
import DAO.ReferenciasDAO;
import DAO.plan.AlimentosDAO;
import java.sql.Connection;
import java.sql.DriverManager;
import DAO.plan.EjerciciosDAO;
import SQLite.cliente.SQLiteClientesDAO;
import SQLite.cliente.SQLiteMedidasDAO;
import SQLite.Plan.SQLiteAlxDietDAO;
import SQLite.Plan.SQLiteAlimentosDAO;
import SQLite.Plan.SQLiteEjerciciosDAO;
import SQLite.Plan.SQLiteEjxRutDAO;
import org.sqlite.JDBC;
import DAO.plan.AlxDietDAO;
import DAO.plan.EjxRutDAO;
import SQLite.Plan.SQLitePlanesDAO;
import java.sql.SQLException;
import DAO.plan.PlanesDAO;

/**
 *
 * @author Victor
 */
public class SQLiteDAOManager implements DAOManager {

    private Connection conex;
    private ClientesDAO clientes = null;
    private MedidasDAO medidas = null;
    private PlanesDAO rutinas = null;
    private PlanesDAO dietas = null;
    private PlanesDAO planes = null;
    private EjerciciosDAO ejercicios = null;
    private EjxRutDAO ejerciciosRutinas = null;
    private AlimentosDAO alimentos = null;
    private AlxDietDAO alimentosDietas = null;
    private ReferenciasDAO referencias = null;

    public SQLiteDAOManager() throws DAOException {
        try {
            DriverManager.registerDriver(new JDBC());
            conex = DriverManager.getConnection("jdbc:sqlite:src/database/ygcplanner.db");
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
    }

    @Override
    public ClientesDAO getClientesDAO() {
        if (clientes == null) {
            clientes = new SQLiteClientesDAO(conex);
        }
        return clientes;
    }

    @Override
    public MedidasDAO getMedidasDAO() {
        if (medidas == null) {
            medidas = new SQLiteMedidasDAO(conex);
        }
        return medidas;
    }

    @Override
    public AlimentosDAO getAlimentosDAO() {
        if (alimentos == null) {
            alimentos = new SQLiteAlimentosDAO(conex);

        }
        return alimentos;
    }

    @Override
    public PlanesDAO getPlanesDAO() {
        if (rutinas == null) {
            rutinas = new SQLitePlanesDAO(conex);
        }
        return rutinas;
    }

    @Override
    public AlxDietDAO getAlimentosDietasDAO() {
        if (alimentosDietas == null) {
            alimentosDietas = new SQLiteAlxDietDAO(conex);
        }
        return alimentosDietas;
    }

    @Override
    public EjerciciosDAO getEjerciciosDAO() {
        if (ejercicios == null) {
            ejercicios = new SQLiteEjerciciosDAO(conex);
        }
        return ejercicios;
    }

    @Override
    public EjxRutDAO getEjerciciosRutinasDAO() {
        if (ejerciciosRutinas == null) {
            ejerciciosRutinas = new SQLiteEjxRutDAO(conex);
        }
        return ejerciciosRutinas;
    }

    @Override
    public ReferenciasDAO getReferenciasDAO() {
        if (referencias == null) {
            referencias = new SQLiteReferenciasDAO(conex);
        }
        return referencias;
    }

}
