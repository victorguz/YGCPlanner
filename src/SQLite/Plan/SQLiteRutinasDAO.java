/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQLite.Plan;

import DAO.DAOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelo.plan.Plan;

public class SQLiteRutinasDAO implements DAO.plan.PlanDAO {

    private Connection conex;

    private final String INSERT = "INSERT INTO Planes(nombre, objetivo,"
            + " descripcion, sexo, edad, tipo) values (?, ?, ?, ?, ?, ?)";
    private final String SELECT = "SELECT Plankey, nombre, objetivo,"
            + " descripcion, sexo, edad FROM Planes "
            + "where Plankey = ?";
    private final String WHERE = "SELECT Plankey, nombre, objetivo,"
            + " descripcion, sexo, edad, tipo FROM Planes "
            + "where (nombre like ? "
            + "or objetivo like ? "
            + "or sexo like ? "
            + "or edad = ? ) and tipo = ? order by plankey DESC";
    private final String ALL = "SELECT Plankey, nombre, objetivo,"
            + " descripcion, sexo, edad, tipo FROM planes where tipo = ? order by plankey DESC";
    private final String UPDATE = "UPDATE Planes SET nombre = ?, objetivo = ?,"
            + " descripcion = ?, sexo = ?, edad  = ? WHERE plankey = ? ";
    private final String DELETE = "DELETE FROM Planes WHERE Plankey = ?";

    public SQLiteRutinasDAO(Connection conex) {
        this.conex = conex;
    }

    @Override
    public void insertar(Plan a) throws DAOException {
        PreparedStatement s = null;
        try {
            s = conex.prepareStatement(INSERT);
            s.setString(1, a.getNombre().toUpperCase());
            s.setString(2, a.getObjetivo().toUpperCase());
            s.setString(3, a.getDescripcion().toUpperCase());
            s.setString(4, a.getSexo().toUpperCase());
            s.setInt(5, a.getEdad());
            s.setString(6,"RUTINA");
            if (s.executeUpdate() == 0) {
                throw new DAOException("Error al insertar plan de entrenamiento");
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

    /**
     * @param a
     * @throws DAOException
     */
    @Override
    public void modificar(Plan a) throws DAOException {
        PreparedStatement s = null;
        try {
            s = conex.prepareStatement(UPDATE);
            s.setString(1, a.getNombre());
            s.setString(2, a.getObjetivo());
            s.setString(3, a.getDescripcion());
            s.setString(4, a.getSexo());
            s.setInt(5, a.getEdad());
            s.setInt(6, a.getPlankey());
            if (s.executeUpdate() == 0) {
                throw new DAOException("Error al modificar plan de entrenamiento");
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
    public void eliminar(Plan a) throws DAOException {
        PreparedStatement s = null;
        try {
            s = conex.prepareStatement(DELETE);
            s.setInt(1, a.getPlankey());
            if (s.executeUpdate() == 0) {
                throw new DAOException("Error al eliminar plan de entrenamiento");
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
    public ObservableList<Plan> obtenerTodos() throws DAOException {
        PreparedStatement s = null;
        ResultSet rs = null;
        ObservableList<Plan> l = FXCollections.observableArrayList();
        try {
            s = conex.prepareStatement(ALL);
            s.setString(1, "RUTINA");
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
    public Plan obtener(String equal) throws DAOException {
        PreparedStatement s = null;
        ResultSet rs = null;
        Plan l = null;
        try {
            s = conex.prepareStatement(SELECT);
            s.setInt(1, Integer.parseInt(equal));
            rs = s.executeQuery();
            if (rs.next()) {
                l = convertir(rs);
            } else {
                throw new DAOException("Plan no encontrado");
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

    /**
     *
     * @return
     * @throws DAOException
     */
    @Override
    public ObservableList<Plan> obtenerTodos(String equal) throws DAOException {
        PreparedStatement s = null;
        ResultSet rs = null;
        ObservableList<Plan> l = FXCollections.observableArrayList();
        try {
            s = conex.prepareStatement(WHERE);
            s.setString(1, "%" + equal.toUpperCase() + "%");
            s.setString(2, "%" + equal.toUpperCase() + "%");
            s.setString(3, "%" + equal.toUpperCase() + "%");
            s.setInt(4, Integer.parseInt(equal));
            s.setString(5,"RUTINA");
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
    public Plan convertir(ResultSet rs) throws DAOException {
        if (rs == null) {
            throw new DAOException("Error al convertir plan de entrenamiento");
        }
        try {
            Plan c = new Plan();
            c.setPlankey(rs.getInt("Plankey"));
            c.setNombre(rs.getString("nombre"));
            c.setObjetivo(rs.getString("objetivo"));
            c.setDescripcion(rs.getString("descripcion"));
            c.setSexo(rs.getString("sexo"));
            c.setEdad(rs.getInt("edad"));
            c.setTipo(rs.getString("tipo"));
            return c;
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
    }
}
