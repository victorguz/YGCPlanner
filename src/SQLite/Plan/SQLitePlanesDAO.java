/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQLite.Plan;

import DAO.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelo.plan.Plan;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class SQLitePlanesDAO implements DAO.plan.PlanesDAO {

    private final String INSERT = "INSERT INTO Planes(nombre, "
            + "tipo, usedate, usetime) "
            + "values (?, ?, ?, ?)";
    private final String UPDATE = "UPDATE Planes SET nombre = ?, "
            + " usedate = ?, usetime = ? WHERE plankey = ? ";
    private final String DELETE = "delete from planes where nombre = ? and tipo = ?";
    private final String SELECT = "SELECT Plankey, nombre, "
            + "tipo FROM Planes "
            + " where Plankey = ? ";
    private final String WHERE = "SELECT Plankey, nombre,"
            + " tipo FROM Planes "
            + "where tipo = ? and deleted = false order by nombre like ? desc";
    private final String ALL = "SELECT Plankey, nombre, "
            + " tipo FROM Planes where tipo = ? and deleted=false "
            + "order by usedate desc, usetime desc ";
    private Connection conex;

    public SQLitePlanesDAO(Connection conex) {
        this.conex = conex;
    }

    @Override
    public void insert(Plan a) throws DAOException {
        PreparedStatement s = null;
        try {
            s = conex.prepareStatement(INSERT);
            s.setString(1, a.getNombre().toLowerCase());
            s.setString(2, a.getTipo());
            s.setDate(3, Date.valueOf(LocalDate.now()));
            s.setTime(4, Time.valueOf(LocalTime.now()));
            if (s.executeUpdate() == 0) {
                throw new DAOException("Error al insertar plan");
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
    public void update(Plan a) throws DAOException {
        PreparedStatement s = null;
        try {
            s = conex.prepareStatement(UPDATE);
            s.setString(1, a.getNombre());
            s.setDate(2, Date.valueOf(LocalDate.now()));
            s.setTime(3, Time.valueOf(LocalTime.now()));
            s.setInt(4, a.getPlankey());
            if (s.executeUpdate() == 0) {
                throw new DAOException("Error al modificar plan");
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

    private void deleteAlxOrEjx(Plan a) throws DAOException {
        PreparedStatement s = null;
        try {
            if (a.getTipo().equalsIgnoreCase("dieta")) {
                s = conex.prepareStatement("DELETE FROM ALXDIET WHERE PLANKEY=(select plankey from planes where nombre=?)");
            } else {
                s = conex.prepareStatement("DELETE FROM ejxrut WHERE PLANKEY=(select plankey from planes where nombre=?) ");
            }
            s.setString(1, a.getNombre());
            if (s.executeUpdate() == 0) {
                throw new DAOException("Error los elementos del plan");
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
    public void delete(Plan a) throws DAOException {
        PreparedStatement s = null;
        try {
            deleteAlxOrEjx(a);
            s = conex.prepareStatement(DELETE);
            s.setString(1, a.getNombre());
            s.setString(2, a.getTipo());
            if (s.executeUpdate() == 0) {
                throw new DAOException("Error al eliminar plan");
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
    public ObservableList<Plan> all() throws DAOException {
        return null;
    }

    @Override
    public ObservableList<Plan> allRutinas() throws DAOException {
        PreparedStatement s = null;
        ResultSet rs = null;
        ObservableList<Plan> l = FXCollections.observableArrayList();
        try {
            s = conex.prepareStatement(ALL);
            s.setString(1, "rutina");
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
    public ObservableList<Plan> allDietas() throws DAOException {
        PreparedStatement s = null;
        ResultSet rs = null;
        ObservableList<Plan> l = FXCollections.observableArrayList();
        try {
            s = conex.prepareStatement(ALL);
            s.setString(1, "dieta");
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
    public Plan select(Integer equal) throws DAOException {
        PreparedStatement s = null;
        ResultSet rs = null;
        Plan l = null;
        try {
            s = conex.prepareStatement(SELECT);
            s.setInt(1, equal);
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

    @Override
    public ObservableList<Plan> where(String equal) throws DAOException {
        return null;
    }

    @Override
    public ObservableList<Plan> whereRutinas(String equal) throws DAOException {
        PreparedStatement s = null;
        ResultSet rs = null;
        ObservableList<Plan> l = FXCollections.observableArrayList();
        try {
            s = conex.prepareStatement(WHERE);
            s.setString(1, "rutina");//tipo
            s.setString(2, "%" + equal + "%");
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
    public ObservableList<Plan> whereDietas(String equal) throws DAOException {
        PreparedStatement s = null;
        ResultSet rs = null;
        ObservableList<Plan> l = FXCollections.observableArrayList();
        try {
            s = conex.prepareStatement(WHERE);
            s.setString(1, "dieta");//tipo
            s.setString(2, "%" + equal + "%");
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
            throw new DAOException("Error al convertir plan");
        }
        try {
            Plan c = new Plan(rs.getString("tipo"));
            c.setPlankey(rs.getInt("Plankey"));
            c.setNombre(rs.getString("nombre"));
            return c;
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
    }
}
