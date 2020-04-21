/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQLite;

import DAO.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelo.Referencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLiteReferenciasDAO implements DAO.ReferenciasDAO {

    private final String INSERT = "INSERT INTO referencias (nombre, descripcion, link) values (?,?,?)";
    private final String SELECT = "SELECT Referenciakey, nombre, descripcion, link FROM referencias where nombre = ?";
    private final String SELECTPLANTILLAS = "SELECT Referenciakey, nombre, descripcion, link FROM referencias where descripcion = 'plantilla' ";
    private final String SELECTALL = "SELECT Referenciakey, nombre, descripcion, link FROM Referencias";
    private final String UPDATE = "UPDATE Referencias SET descripcion = ?, link = ? WHERE nombre = ?";
    private final String DELETE = "DELETE FROM Referencias WHERE Referenciakey = ?";
    private final String SELECTWHERE = "SELECT Referenciakey, nombre, descripcion, link FROM referencias where descripcion = ? and link = ? ";
    private Connection conex;

    public SQLiteReferenciasDAO(Connection conex) {
        this.conex = conex;
    }

    @Override
    public void insert(Referencia a) throws DAOException {
        PreparedStatement s = null;
        try {
            s = conex.prepareStatement(INSERT);
            s.setString(1, a.getNombre());
            s.setString(2, a.getDescripcion());
            s.setString(3, a.getDato());
            if (s.executeUpdate() == 0) {
                throw new DAOException("Error al insertar la referencia: "+a.getNombre());
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
    public void update(Referencia a) throws DAOException {
        PreparedStatement s = null;
        try {
            s = conex.prepareStatement(UPDATE);
            s.setString(3, a.getNombre());
            s.setString(1, a.getDescripcion());
            s.setString(2, a.getDato());
            if (s.executeUpdate() == 0) {
                throw new DAOException("Error al modificar la referencia: "+a.getNombre());
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
    public void delete(Referencia a) throws DAOException {
        PreparedStatement s = null;
        try {
            s = conex.prepareStatement(DELETE);
            s.setInt(1, a.getReferenciakey());
            if (s.executeUpdate() == 0) {
                throw new DAOException("Error al eliminar referencia: "+a.getNombre());
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
    public ObservableList<Referencia> all() throws DAOException {
        PreparedStatement s = null;
        ResultSet rs = null;
        ObservableList<Referencia> l = FXCollections.observableArrayList();
        try {
            s = conex.prepareStatement(SELECTALL);
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
    public ObservableList<Referencia> where(String dato) throws DAOException {
        return null;
    }

    @Override
    public Referencia select(String equal) throws DAOException {
        PreparedStatement s = null;
        ResultSet rs = null;
        Referencia l = null;
        try {
            s = conex.prepareStatement(SELECT);
            s.setString(1, equal);
            rs = s.executeQuery();
            if (rs.next()) {
                l = convertir(rs);
            } else {
                throw new DAOException("Referencia no encontrada: " + equal);
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
    public ObservableList<Referencia> obtenerPlantillas() throws DAOException {
        PreparedStatement s = null;
        ResultSet rs = null;
        ObservableList<Referencia> l = FXCollections.observableArrayList();
        try {
            s = conex.prepareStatement(SELECTPLANTILLAS);
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
    public ObservableList<Referencia> obtenerPresentaciones(String alimentoOejercicio) throws DAOException {
        PreparedStatement s = null;
        ResultSet rs = null;
        ObservableList<Referencia> l = FXCollections.observableArrayList();
        try {
            s = conex.prepareStatement(SELECTWHERE);
            s.setString(1,"presentacion");
            s.setString(2,alimentoOejercicio);
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
    public Referencia convertir(ResultSet rs) throws DAOException {
        if (rs == null) {
            throw new DAOException("Error al convertir Referencia");
        }
        try {
            Referencia c = new Referencia();
            c.setReferenciakey(rs.getInt("Referenciakey"));
            c.setNombre(rs.getString("nombre"));
            c.setDescripcion(rs.getString("descripcion"));
            c.setDato(rs.getString("link"));
            return c;
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
    }
}
