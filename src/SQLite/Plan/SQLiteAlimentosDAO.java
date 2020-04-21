/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQLite.Plan;

import DAO.DAOException;
import DAO.plan.AlimentosDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelo.plan.Alimento;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class SQLiteAlimentosDAO implements AlimentosDAO {

    private final String INSERT = "INSERT INTO ALIMENTOS(nombre, plural, "
            + "  proteinas, grasas, carbohidratos, usedate, usetime)"
            + " values (?, ?, ?, ?, ?, ?,?)";
    private final String SELECT = "SELECT alimentokey, nombre, plural, "
            + "  proteinas, grasas, carbohidratos FROM alimentos "
            + "where alimentokey = ? ";
    private final String ALL = "SELECT alimentokey, nombre,  plural,"
            + "  proteinas, grasas, carbohidratos FROM alimentos order by usedate desc, usetime desc";
    private final String WHERE = "SELECT alimentokey, nombre, plural, "
            + "  proteinas, grasas, carbohidratosFROM ALIMENTOS "
            + " where NOMBRE = ?";
    private final String UPDATE = "UPDATE ALIMENTOS SET  nombre = ?, plural=?, "
            + " proteinas = ?, grasas = ?, carbohidratos  = ? , "
            + "usedate = ?, usetime = ?  WHERE alimentokey = ? ";
    private final String DELETE = "DELETE FROM ALIMENTOS WHERE nombre = ?";
    private Connection conex;

    public SQLiteAlimentosDAO(Connection conex) {
        this.conex = conex;
    }

    /**
     * nombre, " + " proteinas, " + "grasas, carbohidratos
     *
     * @param a
     * @throws DAOException
     */
    @Override
    public void insert(Alimento a) throws DAOException {
        PreparedStatement s = null;
        try {
            s = conex.prepareStatement(INSERT);
            s.setString(1, a.getNombre().toLowerCase());
            s.setString(2, a.getPlural().toLowerCase());
            s.setDouble(3, a.getProteinas());
            s.setDouble(4, a.getGrasas());
            s.setDouble(5, a.getCarbohidratos());
            s.setDate(6, Date.valueOf(LocalDate.now()));
            s.setTime(7, Time.valueOf(LocalTime.now()));
            if (s.executeUpdate() == 0) {
                throw new DAOException("Error al insertar Alimento");
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
    public void update(Alimento a) throws DAOException {
        PreparedStatement s = null;
        try {
            s = conex.prepareStatement(UPDATE);
            s.setString(1, a.getNombre().toLowerCase());
            s.setString(2, a.getPlural().toLowerCase());
            s.setDouble(3, a.getProteinas());
            s.setDouble(4, a.getGrasas());
            s.setDouble(5, a.getCarbohidratos());
            s.setDate(6, Date.valueOf(LocalDate.now()));
            s.setTime(7, Time.valueOf(LocalTime.now()));
            s.setInt(8, a.getAlimentokey());
            if (s.executeUpdate() == 0) {
                throw new DAOException("Error al modificar Alimento");
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
    public void delete(Alimento a) throws DAOException {
        PreparedStatement s = null;
        try {
            s = conex.prepareStatement(DELETE);
            s.setString(1, a.getNombre().toLowerCase());
            if (s.executeUpdate() == 0) {
                throw new DAOException("Error al eliminar Alimento");
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
    public ObservableList<Alimento> all() throws DAOException {
        PreparedStatement s = null;
        ResultSet rs = null;
        ObservableList<Alimento> alimentos = FXCollections.observableArrayList();
        try {
            s = conex.prepareStatement(ALL);
            rs = s.executeQuery();
            while (rs.next()) {
                alimentos.add(convertir(rs));
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
        return alimentos;
    }

    @Override
    public Alimento select(Integer alimentokey) throws DAOException {
        PreparedStatement s = null;
        ResultSet rs = null;
        Alimento a = null;
        try {
            s = conex.prepareStatement(SELECT);
            s.setInt(1, alimentokey);
            rs = s.executeQuery();
            if (rs.next()) {
                a = convertir(rs);
            } else {
                throw new DAOException("Alimento no encontrado");
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
        return a;
    }

    @Override
    public ObservableList<Alimento> where(String equal) throws DAOException {
        PreparedStatement s = null;
        ResultSet rs = null;
        ObservableList<Alimento> list = FXCollections.observableArrayList();
        try {
            s = conex.prepareStatement(WHERE);
            s.setString(1, "%" + equal + "%");
            rs = s.executeQuery();
            while (rs.next()) {
                list.add(convertir(rs));
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
        return list;
    }

    @Override
    public Alimento convertir(ResultSet rs) throws DAOException {
        if (rs == null) {
            throw new DAOException("Error al convertir Alimento");
        }
        try {
            Alimento c = new Alimento();
            c.setAlimentokey(rs.getInt("alimentokey"));
            c.setNombre(rs.getString("nombre"));
            c.setPlural(rs.getString("plural"));
            c.setCarbohidratos(rs.getDouble("carbohidratos"));
            c.setProteinas(rs.getDouble("proteinas"));
            c.setGrasas(rs.getDouble("grasas"));
            return c;
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
    }

}
