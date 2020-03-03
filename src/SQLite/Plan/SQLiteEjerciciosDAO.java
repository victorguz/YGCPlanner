/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQLite.Plan;

import DAO.DAOException;
import DAO.plan.EjerciciosDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelo.plan.Ejercicio;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class SQLiteEjerciciosDAO implements EjerciciosDAO {
//int ejerciciokey, String nombre, String descripcion, String comentarios

    private final String INSERT = "INSERT INTO EjercicioS(nombre, "
            + "descripcion, comentario, usedate, usetime) values (?, ?, ?, ?, ?)";
    private final String SELECTALL = "SELECT Ejerciciokey, nombre, "
            + "descripcion, comentario FROM EjercicioS order by usedate desc, usetime desc";
    private final String SELECTWHERE = "SELECT Ejerciciokey, nombre, "
            + "descripcion, comentario FROM EjercicioS order by NOMBRE LIKE ? desc";
    private final String SELECTONE = "SELECT Ejerciciokey, nombre, "
            + "descripcion, comentario FROM EjercicioS WHERE ejerciciokey = ?";
    private final String UPDATE = "UPDATE EjercicioS SET  nombre = ? , "
            + "descripcion = ? , comentario  = ?, usedate = ?, usetime = ?  "
            + "WHERE ejerciciokey = ? ";
    private final String DELETE = "DELETE FROM EjercicioS WHERE NOMBRE = ?";
    private Connection conex;

    public SQLiteEjerciciosDAO(Connection conex) {
        this.conex = conex;
    }

    /**
     * nombre, " + "categoria, kilocalorias, proteinas, " + "grasas,
     * carbohidratos
     *
     * @param a
     * @throws DAOException
     */
    @Override
    public void insert(Ejercicio a) throws DAOException {
        PreparedStatement s = null;
        try {
            s = conex.prepareStatement(INSERT);
            s.setString(1, a.getNombre());
            s.setString(2, a.getDescripcion());
            s.setString(3, a.getComentarios());
            s.setDate(4, Date.valueOf(LocalDate.now()));
            s.setTime(5, Time.valueOf(LocalTime.now()));
            if (s.executeUpdate() == 0) {
                throw new DAOException("Error al insertar Ejercicio");
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
    public void update(Ejercicio a) throws DAOException {
        PreparedStatement s = null;
        try {
            s = conex.prepareStatement(UPDATE);
            s.setString(1, a.getNombre());
            s.setString(2, a.getDescripcion());
            s.setString(3, a.getComentarios());
            s.setDate(4, Date.valueOf(LocalDate.now()));
            s.setTime(5, Time.valueOf(LocalTime.now()));
            s.setInt(6, a.getEjerciciokey());
            if (s.executeUpdate() == 0) {
                throw new DAOException("Error al modificar Ejercicio");
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
    public void delete(Ejercicio a) throws DAOException {
        PreparedStatement s = null;
        try {
            s = conex.prepareStatement(DELETE);
            s.setString(1, a.getNombre());
            if (s.executeUpdate() == 0) {
                throw new DAOException("Error al eliminar Ejercicio");
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
    public ObservableList<Ejercicio> all() throws DAOException {
        PreparedStatement s = null;
        ResultSet rs = null;
        ObservableList<Ejercicio> ejercicios = FXCollections.observableArrayList();
        try {
            s = conex.prepareStatement(SELECTALL);
            rs = s.executeQuery();
            while (rs.next()) {
                ejercicios.add(convertir(rs));
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
        return ejercicios;
    }

    @Override
    public Ejercicio select(Integer ejerciciokey) throws DAOException {
        PreparedStatement s = null;
        ResultSet rs = null;
        Ejercicio c = null;
        try {
            s = conex.prepareStatement(SELECTONE);
            s.setInt(1, ejerciciokey);
            rs = s.executeQuery();
            if (rs.next()) {
                c = convertir(rs);
            } else {
                throw new DAOException("Ejercicio no encontrado: " + ejerciciokey);
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
        return c;
    }

    @Override
    public ObservableList<Ejercicio> where(String equal) throws DAOException {

        PreparedStatement s = null;
        ResultSet rs = null;
        ObservableList<Ejercicio> l = FXCollections.observableArrayList();
        try {
            s = conex.prepareStatement(SELECTWHERE);
            s.setString(1, "%" + equal + "%");
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
    public Ejercicio convertir(ResultSet rs) throws DAOException {
        if (rs == null) {
            throw new DAOException("Error al convertir Ejercicio");
        }
        try {
            Ejercicio c = new Ejercicio();
            c.setEjerciciokey(rs.getInt("Ejerciciokey"));
            c.setNombre(rs.getString("nombre"));
            c.setDescripcion(rs.getString("descripcion"));
            c.setComentarios(rs.getString("comentario"));
            return c;
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
    }
}
