/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQLite.Plan;

import DAO.DAOException;
import DAO.plan.AlimentosDAO;
import modelo.plan.Alimento;
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

public class SQLiteAlimentosDAO implements AlimentosDAO {

    private Connection conex;

    private final String INSERT = "INSERT INTO ALIMENTOS(nombre, "
            + "  proteinas, grasas, carbohidratos, usedate, usetime, unidad)"
            + " values (?, ?, ?, ?, ?, ?, ?, ?,?)";
    private final String SELECT = "SELECT alimentokey, nombre, "
            + "  proteinas, grasas, carbohidratos, unidad FROM alimentos "
            + "where alimentokey = ? ";
    private final String ALL = "SELECT alimentokey, nombre, "
            + "  proteinas, grasas, carbohidratos, unidad FROM alimentos order by usedate desc, usetime desc";
    private final String WHERE = "SELECT alimentokey, nombre, "
            + "  proteinas, grasas, carbohidratos, unidad FROM ALIMENTOS "
            + " order by nombre like ? desc";
    private final String UPDATE = "UPDATE ALIMENTOS SET  nombre = ?, "
            + " proteinas = ?, grasas = ?, carbohidratos  = ? , "
            + "usedate = ?, usetime = ? , unidad = ? WHERE alimentokey = ? ";
    private final String DELETE = "DELETE FROM ALIMENTOS WHERE alimentokey = ?";

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
            s.setString(1, a.getNombre());
            s.setDouble(2, a.getProteinas());
            s.setDouble(3, a.getGrasas());
            s.setDouble(4, a.getCarbohidratos());
            s.setDate(5, Date.valueOf(LocalDate.now()));
            s.setTime(6, Time.valueOf(LocalTime.now()));
            s.setString(7, a.getUnidad());
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
            s.setString(1, a.getNombre());
            s.setDouble(2, a.getProteinas());
            s.setDouble(3, a.getGrasas());
            s.setDouble(4, a.getCarbohidratos());
            s.setDate(5, Date.valueOf(LocalDate.now()));
            s.setTime(6, Time.valueOf(LocalTime.now()));
            s.setString(7, a.getUnidad());
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
            s.setInt(1, a.getAlimentokey());
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
            c.setCarbohidratos(rs.getDouble("carbohidratos"));
            c.setProteinas(rs.getDouble("proteinas"));
            c.setGrasas(rs.getDouble("grasas"));
            c.setUnidad(rs.getString("unidad"));
            return c;
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
    }

}
