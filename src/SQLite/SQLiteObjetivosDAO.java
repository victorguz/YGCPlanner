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
import modelo.Objetivo;

public class SQLiteObjetivosDAO implements DAO.ObjetivosDAO {

    private Connection conex;

    private final String INSERT = "INSERT INTO Objetivos (objetivo) values (?)";
    private final String SELECT = "SELECT Objetivokey, objetivo FROM Objetivos where objetivo = ?";
    private final String SELECTWHERE = "SELECT Objetivokey, objetivo FROM Objetivos where objetivo like ?";
    private final String SELECTALL = "SELECT Objetivokey, objetivo FROM Objetivos";
    private final String UPDATE = "UPDATE Objetivos objetivo = ? WHERE Objetivokey = ?";
    private final String DELETE = "DELETE FROM Objetivo WHERE Objetivo = ?";

    public SQLiteObjetivosDAO(Connection conex) {
        this.conex = conex;
    }

    @Override
    public void insertar(Objetivo a) throws DAOException {
        PreparedStatement s = null;
        try {
            s = conex.prepareStatement(INSERT);
            s.setString(1, a.getObjetivo().toUpperCase());
            if (s.executeUpdate() == 0) {
                throw new DAOException("Error al insertar Objetivo");
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
    public void modificar(Objetivo a) throws DAOException {
        PreparedStatement s = null;
        try {
            s = conex.prepareStatement(UPDATE);
            s.setString(1, a.getObjetivo());
            s.setInt(2, a.getObjetivokey());
            if (s.executeUpdate() == 0) {
                throw new DAOException("Error al modificar Objetivo");
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
    public void eliminar(Objetivo a) throws DAOException {
        PreparedStatement s = null;
        try {
            s = conex.prepareStatement(DELETE);
            s.setInt(1, a.getObjetivokey());
            if (s.executeUpdate() == 0) {
                throw new DAOException("Error al eliminar Objetivo");
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
    public ObservableList<Objetivo> obtenerTodos() throws DAOException {
        PreparedStatement s = null;
        ResultSet rs = null;
        ObservableList<Objetivo> l = FXCollections.observableArrayList();
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
    public Objetivo obtener(String objetivo) throws DAOException {
        PreparedStatement s = null;
        ResultSet rs = null;
        Objetivo l = null;
        try {
            s = conex.prepareStatement(SELECT);
            s.setString(1, objetivo.toUpperCase());
            rs = s.executeQuery();
            if (rs.next()) {
                l = convertir(rs);
            } else {
                throw new DAOException("Objetivo no encontrado");
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
    public ObservableList<Objetivo> obtenerTodos(String objetivo) throws DAOException {
        PreparedStatement s = null;
        ResultSet rs = null;
        ObservableList<Objetivo> l = FXCollections.observableArrayList();
        try {
            s = conex.prepareStatement(SELECTWHERE);
            s.setString(1, "'%" + objetivo.toUpperCase() + "%'");
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
    public Objetivo convertir(ResultSet rs) throws DAOException {
        if (rs == null) {
            throw new DAOException("Error al convertir Objetivo");
        }
        try {
            Objetivo c = new Objetivo();
            c.setObjetivokey(rs.getInt("Objetivokey"));
            c.setObjetivo(rs.getString("objetivo"));
            return c;
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
    }
}
