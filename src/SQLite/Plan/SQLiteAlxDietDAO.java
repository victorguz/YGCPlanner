/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQLite.Plan;

import DAO.DAOException;
import java.sql.Connection;
import java.sql.ResultSet;
import DAO.plan.AlxDietDAO;
import controlador.Controller;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelo.plan.AlxDiet;

public class SQLiteAlxDietDAO implements AlxDietDAO {

    private Connection conex;

    private final String INSERT = "INSERT INTO AlxDiet(plankey, alimentokey, "
            + "momento, dia, cantidad) values (?, ?, ?, ?, ?)";
    private final String ALL = "SELECT alxdietkey, alimentokey, "
            + "momento, dia, cantidad FROM AlxDiet WHERE plankey = ?";
    private final String WHERE = "SELECT alxdietkey, alimentokey, "
            + "momento, dia, cantidad FROM AlxDiet WHERE plankey = ? and dia = ? and momento = ?";
    private final String DELETE = "DELETE FROM AlxDiet WHERE alxdietkey = ?";

    public SQLiteAlxDietDAO(Connection conex) {
        this.conex = conex;
    }

    @Override
    public void insertar(AlxDiet a) throws DAOException {
        PreparedStatement s = null;
        try {
            s = conex.prepareStatement(INSERT);
            s.setInt(1, a.getPlan().getPlankey());
            s.setInt(2, a.getAlimento().getAlimentoKey());
            s.setString(3, a.getMomento());
            s.setString(4, a.getDia());
            s.setDouble(5, a.getCantidad());
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
    public void modificar(AlxDiet a) throws DAOException {
        throw new DAOException("Modificar AlxDietDAO:Este método no funciona");
    }

    @Override
    public void eliminar(AlxDiet a) throws DAOException {
        PreparedStatement s = null;
        try {
            s = conex.prepareStatement(DELETE);
            s.setInt(1, a.getAlxdietkey());
            if (s.executeUpdate() == 0) {
                throw new DAOException("Error al eliminar AlxDiet");
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
    public ObservableList<AlxDiet> obtenerTodos() throws DAOException {
        throw new DAOException("Este metodo no funciona");
    }

    @Override
    public AlxDiet obtener(String equal) throws DAOException {
        throw new DAOException("Este metodo no funciona");
    }
    public ObservableList<AlxDiet> obtenerTodos(int plankey, String dia, String momento) throws DAOException {
        PreparedStatement s = null;
        ResultSet rs = null;
        ObservableList<AlxDiet> l = FXCollections.observableArrayList();
        try {
            s = conex.prepareStatement(WHERE);
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
    public ObservableList<AlxDiet> obtenerTodos(String plankey) throws DAOException {
        PreparedStatement s = null;
        ResultSet rs = null;
        ObservableList<AlxDiet> l = FXCollections.observableArrayList();
        try {
            s = conex.prepareStatement(ALL);
            s.setInt(1, Integer.parseInt(plankey));
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
    public AlxDiet convertir(ResultSet rs) throws DAOException {
        if (rs == null) {
            throw new DAOException("Error al convertir AlxDiet");
        }
        try {
            AlxDiet c = new AlxDiet();
            c.setAlxdietkey(rs.getInt("alxdietkey"));
            c.setAlimento(Controller.getAlimentos().obtener(""+rs.getInt("alimentokey")));
            c.setMomento(rs.getString("momento"));
            c.setDia(rs.getString("dia"));
            c.setCantidad(rs.getDouble("cantidad"));
            return c;
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
    }
}
