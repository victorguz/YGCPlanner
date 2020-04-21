/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQLite.Plan;

import DAO.DAOException;
import DAO.plan.AlxDietDAO;
import controlador.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelo.plan.AlxDiet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLiteAlxDietDAO implements AlxDietDAO {

    private final String INSERT = "INSERT INTO AlxDiet(plankey, alimentokey, "
            + "momento, dia, cantidad, unidad, presentacion, gramos) values (?, ?, ?, ?, ?, ?, ?, ?)";
    private final String DELETE = "DELETE FROM AlxDiet WHERE "
            + "plankey = ? and alimentokey = ? and dia = ? and momento = ?";
    private final String WHERE = "SELECT plankey, alxdietkey, alimentokey, "
            + "momento, dia, cantidad, unidad, presentacion, gramos FROM AlxDiet "
            + "WHERE plankey = ? and dia = ? and momento = ? order by alxdietkey asc";
    private final String ALL = "SELECT plankey, alxdietkey, alimentokey, "
            + "momento, dia, cantidad, unidad, presentacion, gramos FROM AlxDiet WHERE plankey = ? and dia = ? order by dia, momento";
    private Connection conex;

    public SQLiteAlxDietDAO(Connection conex) {
        this.conex = conex;
    }

    @Override
    public void insert(AlxDiet a) throws DAOException {
        PreparedStatement s = null;
        try {
            s = conex.prepareStatement(INSERT);
            s.setInt(1, a.getPlan().getPlankey());
            s.setInt(2, a.getAlimento().getAlimentokey());
            s.setString(3, a.getMomento());
            s.setString(4, a.getDia());
            s.setInt(5, a.getCantidad());//, unidad, presentacion, gramos
            s.setString(6, a.getUnidad());
            s.setString(7, a.getPresentacion());
            s.setInt(8, a.getGramos());
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
    public void update(AlxDiet a) throws DAOException {
        throw new DAOException("Modificar AlxDietDAO: Este método no funciona");
    }

    @Override
    public void delete(AlxDiet a) throws DAOException {
        PreparedStatement s = null;
        try {
            //plankey = ? and alimentokey = ? and dia = ? and momento = ? and combinacion = ?
            s = conex.prepareStatement(DELETE);
            s.setInt(1, a.getPlan().getPlankey());
            s.setInt(2, a.getAlimento().getAlimentokey());
            s.setString(3, a.getDia());
            s.setString(4, a.getMomento());
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
    public ObservableList<AlxDiet> all() throws DAOException {
        throw new DAOException("Este metodo no funciona");
    }

    @Override
    public ObservableList<AlxDiet> where(int plankey, String dia) throws DAOException {
        PreparedStatement s = null;
        ResultSet rs = null;
        ObservableList<AlxDiet> l = FXCollections.observableArrayList();
        try {
            s = conex.prepareStatement(ALL);
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
    public AlxDiet select(Integer equal) throws DAOException {
        throw new DAOException("Este metodo no funciona");
    }

    @Override
    public ObservableList<AlxDiet> where(int plankey, String dia, String momento) throws DAOException {
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
    public AlxDiet convertir(ResultSet rs) throws DAOException {
        if (rs == null) {
            throw new DAOException("Error al convertir AlxDiet");
        }
        try {
            AlxDiet c = new AlxDiet();
            c.setAlxdietkey(rs.getInt("alxdietkey"));
            c.setPlan(Controller.getPlanes().select(rs.getInt("plankey")));
            c.setAlimento(Controller.getAlimentos().select(rs.getInt("alimentokey")));
            c.setMomento(rs.getString("momento"));
            c.setDia(rs.getString("dia"));
            c.setCantidad(rs.getInt("cantidad"));
            c.setGramos(rs.getInt("gramos"));
            c.setUnidad(rs.getString("unidad"));
            c.setPresentacion(rs.getString("presentacion"));
            return c;
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
    }

    @Override
    public ObservableList<AlxDiet> where(String dato) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
