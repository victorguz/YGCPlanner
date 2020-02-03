/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQLite;

import DAO.DAOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelo.Referencia;

public class SQLiteReferenciasDAO implements DAO.ReferenciasDAO {

    private Connection conex;

    private final String INSERT = "INSERT INTO referencias (nombre, descripcion, link) values (?,?,?)";
    private final String SELECT = "SELECT Referenciakey, nombre, descripcion, link FROM referencias where nombre = ?";
    private final String SELECTWHERE = "SELECT Referenciakey, nombre, descripcion, link FROM referencias where nombre like ? ";
    private final String SELECTALL = "SELECT Referenciakey, nombre, descripcion, link FROM Referencias";
    private final String UPDATE = "UPDATE Referencias SET descripcion = ?, link = ? WHERE nombre = ?";
    private final String DELETE = "DELETE FROM Referencias WHERE Referenciakey = ?";

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
                throw new DAOException("Error al insertar Referencia");
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
     * @param ignorar
     * @param ignorarx2
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
                throw new DAOException("Error al modificar Referencia");
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
                throw new DAOException("Error al eliminar Referencia");
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
            rs=s.executeQuery();
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
                throw new DAOException("Referencia no encontrada: "+equal);
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
    public ObservableList<Referencia> where(String Referencia) throws DAOException {
        PreparedStatement s = null;
        ResultSet rs = null;
        ObservableList<Referencia> l = FXCollections.observableArrayList();
        try {
            s = conex.prepareStatement(SELECTWHERE);
            s.setString(1, "%"+Referencia+"%");
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
