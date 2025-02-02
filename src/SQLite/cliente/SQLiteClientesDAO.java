/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SQLite.cliente;

import DAO.DAOException;
import DAO.cliente.ClientesDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelo.cliente.Cliente;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class SQLiteClientesDAO implements ClientesDAO {

    static String ONE = "SELECT clientekey, nombre, apellido, sexo, "
            + "tipoidentificacion, identificacion, edad "
            + "from clientes where clientekey = ?";
    static String WHERE = "SELECT clientekey, nombre, apellido, sexo, "
            + " tipoidentificacion, identificacion, edad "
            + " from clientes "
            + " where nombre like ? or "
            + " apellido like ? "
            + " order by nombre DESC, "
            + " apellido DESC";
    final String INSERT = "INSERT INTO clientes (nombre, apellido, "
            + "sexo, tipoidentificacion, identificacion, edad, usedate, usetime) "
            + "values (?,?,?,?,?,?,?,?)";
    final String UPDATE = "UPDATE clientes SET nombre = ?, apellido = ?, "
            + "sexo = ?, tipoidentificacion = ?, identificacion = ?, edad = ?, "
            + " usedate = ?, usetime = ? where clientekey = ?";
    final String DELETE = "DELETE from clientes where clientekey = ?";
    final String ALL = "SELECT clientekey, nombre, apellido, sexo, "
            + "tipoidentificacion, identificacion, edad "
            + " from clientes order by  usedate desc, usetime desc";
    Connection conex;

    public SQLiteClientesDAO(Connection conex) {
        this.conex = conex;
    }

    @Override
    public void insert(Cliente a) throws DAOException {
        PreparedStatement s = null;
        try {
            s = conex.prepareStatement(INSERT);
            s.setString(1, a.getNombre().toLowerCase());
            s.setString(2, a.getApellido().toLowerCase());
            s.setString(3, a.getSexo().toLowerCase());
            s.setString(4, a.getTipoIdentificacion());
            s.setString(5, a.getIdentificacion());
            s.setInt(6, a.getEdad());
            s.setDate(7, Date.valueOf(LocalDate.now()));
            s.setTime(8, Time.valueOf(LocalTime.now()));
            if (s.executeUpdate() == 0) {
                throw new DAOException("Error al insertar cliente");
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
    public void update(Cliente a) throws DAOException {
        PreparedStatement s = null;
        try {
            s = conex.prepareStatement(UPDATE);
            s.setString(1, a.getNombre().toLowerCase());
            s.setString(2, a.getApellido().toLowerCase());
            s.setString(3, a.getSexo().toLowerCase());
            s.setString(4, a.getTipoIdentificacion());
            s.setString(5, a.getIdentificacion());
            s.setInt(6, a.getEdad());
            s.setDate(7, Date.valueOf(LocalDate.now()));
            s.setTime(8, Time.valueOf(LocalTime.now()));
            s.setInt(9, a.getClienteKey());
            if (s.executeUpdate() == 0) {
                throw new DAOException("Error al modificar cliente 1");
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
    public void delete(Cliente a) throws DAOException {
        PreparedStatement s = null;
        try {
            s = conex.prepareStatement(DELETE);
            s.setInt(1, a.getClienteKey());
            if (s.executeUpdate() == 0) {
                throw new DAOException("Error al eliminar cliente 1");
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
    public ObservableList<Cliente> all() throws DAOException {
        PreparedStatement s = null;
        ResultSet rs = null;
        ObservableList<Cliente> clientes = FXCollections.observableArrayList();
        try {
            s = conex.prepareStatement(ALL);
            rs = s.executeQuery();
            int x = 0;
            while (rs.next()) {
                clientes.add(convertir(rs));
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
        return clientes;
    }

    @Override
    public Cliente select(Integer clientekey) throws DAOException {
        PreparedStatement s = null;
        ResultSet rs = null;
        Cliente c = null;
        try {
            s = conex.prepareStatement(ONE);
            s.setInt(1, clientekey);
            rs = s.executeQuery();
            if (rs.next()) {
                c = convertir(rs);
            } else {
                throw new DAOException("Cliente no encontrado");
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
    public ObservableList<Cliente> where(String equal) throws DAOException {

        PreparedStatement s = null;
        ResultSet rs = null;
        ObservableList<Cliente> l = FXCollections.observableArrayList();
        try {
            s = conex.prepareStatement(WHERE);
            s.setString(1, "%" + equal.toLowerCase() + "%");
            s.setString(2, "%" + equal.toLowerCase() + "%");
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
    public Cliente convertir(ResultSet rs) throws DAOException {
        try {
            Cliente c = new Cliente();
            c.setClienteKey(rs.getInt("clientekey"));
            c.setNombre(rs.getString("nombre"));
            c.setApellido(rs.getString("apellido"));
            c.setTipoIdentificacion(rs.getString("tipoidentificacion"));
            c.setIdentificacion(rs.getString("identificacion"));
            c.setSexo(rs.getString("sexo"));
            c.setEdad(rs.getInt("edad"));
            return c;
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
    }

}
