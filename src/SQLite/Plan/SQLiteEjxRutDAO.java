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
            + " dia, repeticiones, series, momento) values (?, ?, ?, ?, ?, ?)";
    private final String DELETE = "DELETE FROM EjxRut WHERE plankey = ? and ejerciciokey = ? and dia = ? and momento = ?";
    private final String WHERE = "SELECT ejxrutkey, plankey, Ejerciciokey, "
            + " dia, repeticiones, series, momento FROM ejxrut "
            + "WHERE plankey = ? and dia = ? order by ejxrutkey asc";
    private final String WHERE2 = "SELECT ejxrutkey, plankey, Ejerciciokey, "
            + " dia, repeticiones, series, momento FROM ejxrut "
            + "WHERE plankey = ? and dia = ? and momento = ? order by ejxrutkey asc";

    public SQLiteEjxRutDAO(Connection conex) {
        this.conex = conex;
    }

    @Override
    public void insert(EjxRut a) throws DAOException {
        PreparedStatement s = null;
        try {
            s = conex.prepareStatement(INSERT);
            s.setInt(1, a.getPlan().getPlankey());
            s.setInt(2, a.getEjercicio().getEjerciciokey());
            s.setString(3, a.getDia());
            s.setInt(4, a.getSeries());
            s.setInt(5, a.getRepeticiones());
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
    public void update(EjxRut a) throws DAOException {
        throw new DAOException("Modificar EjxRut: este metodo no funciona");
    }

    @Override
    public void delete(EjxRut a) throws DAOException {
        PreparedStatement s = null;
        try {
            s = conex.prepareStatement(DELETE);
            s.setInt(1, a.getPlan().getPlankey());
            s.setInt(2, a.getEjercicio().getEjerciciokey());
            s.setString(3, a.getDia());
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
    public ObservableList<EjxRut> all() throws DAOException {
        throw new DAOException("Este metodo no funciona");
    }

    @Override
    public EjxRut select(String equal) throws DAOException {
        throw new DAOException("Este metodo no funciona");
    }

    /**
     *
     * @param ejxrutkey
     * @return
     * @throws DAOException
     */
    @Override
    public ObservableList<EjxRut> where(String ejxrutkey) throws DAOException {
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
            c.setEjxrutkey(rs.getInt("ejxrutkey"));
            c.setEjercicio(Controller.getEjercicios().select(rs.getInt("Ejerciciokey")));
            c.setDia(rs.getString("dia"));
            c.setSeries(rs.getInt("series"));
            c.setRepeticiones(rs.getInt("repeticiones"));
            c.setMomento(rs.getString("momento"));
            return c;
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
    }

    @Override
    public ObservableList<EjxRut> where(int plankey, String dia) throws DAOException {
        PreparedStatement s = null;
        ResultSet rs = null;
        ObservableList<EjxRut> l = FXCollections.observableArrayList();
        try {
            s = conex.prepareStatement(WHERE);
            s.setInt(1, plankey);
            s.setString(2, dia);
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
    public ObservableList<EjxRut> where(int plankey, String dia, String momento) throws DAOException {
        PreparedStatement s = null;
        ResultSet rs = null;
        ObservableList<EjxRut> l = FXCollections.observableArrayList();
        try {
            s = conex.prepareStatement(WHERE2);
            s.setInt(1, plankey);
            s.setString(2, dia);
            s.setString(3, momento);
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

}
