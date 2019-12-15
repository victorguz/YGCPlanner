/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQLite.Plan;

import DAO.DAOException;
import java.sql.Connection;
import java.sql.ResultSet;
import DAO.plan.EjxRutDAO;
import controlador.Controller;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelo.plan.EjxRut;

public class SQLiteEjxRutDAO implements EjxRutDAO {

    //int ejxrutkey, int plankey, int ejerciciokey, int repeticiones, String momento, String dia, double peso
    private Connection conex;

    private final String INSERT = "INSERT INTO EjxRut(plankey, Ejerciciokey, "
            + "momento, dia, peso, repeticiones, series) values (?, ?, ?, ?, ?, ?)";
    private final String WHERE = "SELECT ejxrutkey, plankey, Ejerciciokey, "
            + "momento, dia, peso, repeticiones, series FROM EjxRut WHERE plankey = ?";
    private final String DELETE = "DELETE FROM EjxRut WHERE ejxrutkey = ?";

    public SQLiteEjxRutDAO(Connection conex) {
        this.conex = conex;
    }

    @Override
    public void insertar(EjxRut a) throws DAOException {
        PreparedStatement s = null;
        try {
            s = conex.prepareStatement(INSERT);
            s.setInt(1, a.getPlan().getPlankey());
            s.setInt(2, a.getEjercicio().getEjerciciokey());
            s.setString(3, a.getMomento().toLowerCase());
            s.setString(4, a.getDia().toLowerCase());
            s.setInt(5, a.getSeries());
            s.setInt(6, a.getRepeticiones());
            if (s.executeUpdate() == 0) {
                throw new DAOException("Error al insertar EjxRut");
            }
        } catch (SQLException ex) {
            throw new DAOException(ex);
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (SQLException ex) {
                    throw new DAOException(ex);
                }
            }
        }
    }

    @Override
    public void modificar(EjxRut a) throws DAOException {
                throw new DAOException("Modificar EjxRut: este metodo no funciona");
    }

    @Override
    public void eliminar(EjxRut a) throws DAOException {
        PreparedStatement s = null;
        try {
            s = conex.prepareStatement(DELETE);
            s.setInt(1, a.getEjxrutkey());
            if (s.executeUpdate() == 0) {
                throw new DAOException("Error al eliminar EjxRut");
            }
        } catch (SQLException ex) {
            throw new DAOException(ex);
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (SQLException ex) {
                    throw new DAOException(ex);
                }
            }
        }
    }

    @Override
    public ObservableList<EjxRut> obtenerTodos() throws DAOException {
        throw new DAOException("Este metodo no funciona");
    }

    @Override
    public EjxRut obtener(String equal) throws DAOException {
        throw new DAOException("Este metodo no funciona");
    }

    /**
     *
     * @param ejxrutkey
     * @return
     * @throws DAOException
     */
    @Override
    public ObservableList<EjxRut> obtenerTodos(String ejxrutkey) throws DAOException {
        PreparedStatement s = null;
        ResultSet rs = null;
        ObservableList<EjxRut> l = FXCollections.observableArrayList();
        try {
            s = conex.prepareStatement(WHERE);
            s.setInt(1, Integer.parseInt(ejxrutkey));
            rs = s.executeQuery();
            while (rs.next()) {
                l.add(convertir(rs));
            }
        } catch (SQLException ex) {
            throw new DAOException(ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    throw new DAOException(ex);
                }
            }
            if (s != null) {
                try {
                    s.close();
                } catch (SQLException ex) {
                    throw new DAOException(ex);
                }
            }
        }
        return l;
    }

    @Override
    public EjxRut convertir(ResultSet rs) throws DAOException {
        if (rs == null) {
            throw new DAOException("Error al convertir EjxRut");
        }
        try {
            EjxRut c = new EjxRut();
            c.setEjxrutkey(rs.getInt("EjxRutkey"));
            c.setPlan(Controller.getRutinas().obtener(""+rs.getInt("plankey")));
            c.setEjercicio(Controller.getEjercicios().obtener(""+rs.getInt("Ejerciciokey")));
            c.setMomento(rs.getString("momento"));
            c.setDia(rs.getString("dia"));
            c.setSeries(rs.getInt("series"));
            c.setRepeticiones(rs.getInt("repeticiones"));
            return c;
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
    }
}
