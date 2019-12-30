/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQLite.Plan;

import DAO.DAOException;
import DAO.plan.EjerciciosDAO;
import modelo.plan.Ejercicio;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SQLiteEjerciciosDAO implements EjerciciosDAO {
//int ejerciciokey, String nombre, String descripcion, String comentarios

    private Connection conex;

    private final String INSERT = "INSERT INTO EjercicioS(nombre, "
            + "descripcion, comentario, usedate, usetime) values (?, ?, ?, ?, ?)";
    private final String SELECTALL = "SELECT Ejerciciokey, nombre, "
            + "descripcion, comentario FROM EjercicioS order by usetime desc, usedate desc";
    private final String SELECTWHERE = "SELECT Ejerciciokey, nombre, "
            + "descripcion, comentario FROM EjercicioS order by NOMBRE LIKE ? desc";
    private final String SELECTONE = "SELECT Ejerciciokey, nombre, "
            + "descripcion, comentario FROM EjercicioS WHERE NOMBRE = ?";
    private final String UPDATE = "UPDATE EjercicioS SET  nombre = ? , "
            + "descripcion = ? , comentario  = ?, usedate = ?, usetime = ?  "
            + "WHERE ejerciciokey = ? ";
    private final String DELETE = "DELETE FROM EjercicioS WHERE NOMBRE = ?";

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
    public void insertar(Ejercicio a) throws DAOException {
        PreparedStatement s = null;
        try {
            s = conex.prepareStatement(INSERT);
            s.setString(1, a.getNombre().toLowerCase());
            s.setString(2, a.getDescripcion().toLowerCase());
            s.setString(3, a.getComentarios().toLowerCase());
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
    public void modificar(Ejercicio a) throws DAOException {
        PreparedStatement s = null;
        try {
            s = conex.prepareStatement(UPDATE);
            s.setString(1, a.getNombre().toLowerCase());
            s.setString(2, a.getDescripcion().toLowerCase());
            s.setString(3, a.getComentarios().toLowerCase());
            s.setInt(4, a.getUso());
            s.setDate(5, Date.valueOf(LocalDate.now()));
            s.setTime(6, Time.valueOf(LocalTime.now()));
            s.setInt(7, a.getEjerciciokey());
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
    public void eliminar(Ejercicio a) throws DAOException {
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
    public ObservableList<Ejercicio> obtenerTodos() throws DAOException {
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
    public Ejercicio obtener(String equal) throws DAOException {
        PreparedStatement s = null;
        ResultSet rs = null;
        Ejercicio c = null;
        try {
            s = conex.prepareStatement(SELECTONE);
            s.setString(1, equal);
            rs = s.executeQuery();
            if (rs.next()) {
                c = convertir(rs);
            } else {
                throw new DAOException("Ejercicio no encontrado.");
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
    public ObservableList<Ejercicio> obtenerTodos(String equal) throws DAOException {

        PreparedStatement s = null;
        ResultSet rs = null;
        ObservableList<Ejercicio> l = FXCollections.observableArrayList();
        try {
            s = conex.prepareStatement(SELECTWHERE);
            s.setString(1, "%"+equal+"%");
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
