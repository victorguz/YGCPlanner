/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQLite.Plan;

import DAO.DAOException;
import DAO.plan.EjxRutDAO;
import controlador.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelo.plan.AlxDiet;
import modelo.plan.EjxRut;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLiteEjxRutDAO implements EjxRutDAO {

    private final String INSERT = "INSERT INTO EjxRut(plankey, Ejerciciokey, "
            + " dia, repeticiones, series, momento, unidad, presentacion) values (?, ?, ?, ?, ?, ?, ?, ?)";
    private final String DELETE = "DELETE FROM EjxRut WHERE ejxrutkey=?";
    private final String WHERE = "SELECT ejxrutkey, plankey, Ejerciciokey, "
            + " dia, repeticiones, series, momento, unidad, presentacion FROM ejxrut "
            + "WHERE plankey = ? and dia = ? order by ejxrutkey asc";
    private final String WHERE2 = "SELECT ejxrutkey, plankey, Ejerciciokey, "
            + " dia, repeticiones, series, momento, unidad, presentacion FROM ejxrut "
            + "WHERE plankey = ? and dia = ? and momento = ? order by ejxrutkey asc";

    private Connection conex;

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
            s.setInt(4, a.getCantidad());
            s.setInt(5, a.getSeries());
            s.setString(6, a.getMomento());
            s.setString(7, a.getUnidad());
            s.setString(8, a.getPresentacion());
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

    private void copy(ResultSet rs, String nombreCopy) throws DAOException {
        PreparedStatement s = null;
        try {
            String INSERT = "INSERT INTO AlxDiet(plankey, ejerciciokey, "
                    + "momento, dia, repeticiones, unidad, presentacion, series) " +
                    "values ((select plankey from planes where lower(nombre)=?), ?, ?, ?, ?, ?, ?, ?)";
            s = conex.prepareStatement(INSERT);
            s.setString(1, nombreCopy.toLowerCase());
            s.setInt(2, rs.getInt("ejerciciokey"));
            s.setString(3, rs.getString("momento"));
            s.setString(4, rs.getString("dia"));
            s.setDouble(5, rs.getDouble("repeticiones"));
            s.setString(6, rs.getString("unidad"));
            s.setString(7, rs.getString("presentacion"));
            s.setInt(8, rs.getInt("series"));
            if (s.executeUpdate() == 0) {
                throw new DAOException("Error al insertar AlxDiet");
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
    public ObservableList<EjxRut> all() throws DAOException {
        throw new DAOException("Este metodo no funciona");
    }

    @Override
    public EjxRut select(String equal) throws DAOException {
        throw new DAOException("Este metodo no funciona");
    }

    /**
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
            c.setUnidad(rs.getString("unidad"));
            c.setPresentacion(rs.getString("presentacion"));
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

    @Override
    public void whereCopy(String planOrig, String planCopy) throws DAOException {
        PreparedStatement s = null;
        ResultSet rs = null;
        ObservableList<AlxDiet> l = FXCollections.observableArrayList();
        try {
            String WHERE = "SELECT ejxrutkey, plankey ," +
                    "ejerciciokey,momento,series,dia,repeticiones,unidad,presentacion " +
                    " FROM ejxrut inner join planes on planes.plankey=ejxrut.plankey where planes.nombre=?";
            s = conex.prepareStatement(WHERE);
            s.setString(1, planOrig);
            rs = s.executeQuery();
            while (rs.next()) {
                copy(rs, planCopy);
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
    }
}
