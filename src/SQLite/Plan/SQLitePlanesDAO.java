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
import javafx.collections.ObservableList;
import modelo.plan.Plan;

public class SQLitePlanesDAO implements DAO.plan.PlanDAO {

    private Connection conex;

    private final String SELECT = "SELECT Plankey, nombre, objetivo,"
            + " descripcion, sexo, edad, tipo FROM Planes "
            + "where Plankey = ?";

    public SQLitePlanesDAO(Connection conex) {
        this.conex = conex;
    }

    @Override
    public void insertar(Plan a) throws DAOException {
        System.out.println("Plan: Método no funciona");
    }

    /**
     * @param a
     * @throws DAOException
     */
    @Override
    public void modificar(Plan a) throws DAOException {
        System.out.println("Plan: Método no funciona");
    }

    @Override
    public void eliminar(Plan a) throws DAOException {
        System.out.println("Plan: Método no funciona");
    }

    @Override
    public ObservableList<Plan> obtenerTodos() throws DAOException {
            System.out.println("Plan: Método no funciona");
return null;}

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
     * @param equal
     * @return
     * @throws DAOException
     */
    @Override
    public ObservableList<Plan> obtenerTodos(String equal) throws DAOException {
        System.out.println("Plan: Método no funciona");
return null;    }

    @Override
    public Plan convertir(ResultSet rs) throws DAOException {
        if (rs == null) {
            throw new DAOException("Error al convertir Plan");
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
