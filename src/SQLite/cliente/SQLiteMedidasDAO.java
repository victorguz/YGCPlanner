/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQLite.cliente;

import DAO.DAOException;
import modelo.cliente.Medida;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import DAO.cliente.MedidasDAO;
import controlador.Controller;
import controlador.Operacion;
import java.sql.Date;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelo.cliente.Cliente;

/**
 *
 * @author Victor
 */
public class SQLiteMedidasDAO implements MedidasDAO {

    Connection conex;
    final String INSERT = "INSERT INTO "
            + "medidas("
            + "CLIENTEKEY,"
            + "PESO,"
            + "ALTURA,"
            + "CINTURAALTA,"
            + "CINTURAMEDIA,"
            + "CINTURABAJA,"
            + "CADERA,"
            + "BICEPIZQ,"
            + "BICEPDER,"
            + "PECTORAL,"
            + "PANTORRILLAIZQ,"
            + "PANTORRILLADER,"
            + "CUADRICEPIZQ,"
            + "CUADRICEPDER,"
            + "bicipital,"
            + "tricipital,"
            + "subescapular,"
            + "suprailiaco,"
            + "FECHA,"
            + "CUELLO,"
            + "objetivo, "
            + "ACTIVIDAD, "
            + "muneca)"
            + " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";//23
    final String UPDATE = "UPDATE medidas SET "
            + "PESO=?,"
            + "ALTURA=?,"
            + "CINTURAALTA=?,"
            + "CINTURAMEDIA=?,"
            + "CINTURABAJA=?,"
            + "CADERA=?,"
            + "BICEPIZQ=?,"
            + "BICEPDER=?,"
            + "PECTORAL=?,"
            + "PANTORRILLAIZQ=?,"
            + "PANTORRILLADER=?,"
            + "CUADRICEPIZQ=?,"
            + "CUADRICEPDER=?,"
            + "bicipital=?,"
            + "tricipital=?,"
            + "subescapular=?,"
            + "suprailiaco=?,"
            + "FECHA=?,"
            + "CUELLO=?,"
            + "objetivo=?,"
            + "ACTIVIDAD=?, "
            + "muneca=? "
            + " where medidakey = ?";//25
    final String DELETE = "DELETE from medidas where medidakey = ?";
    final String GETONE = "SELECT "
            + "CLIENTEKEY,"
            + "PESO,"
            + "ALTURA,"
            + "CINTURAALTA,"
            + "CINTURAMEDIA,"
            + "CINTURABAJA,"
            + "CADERA,"
            + "BICEPIZQ,"
            + "BICEPDER,"
            + "PECTORAL,"
            + "PANTORRILLAIZQ,"
            + "PANTORRILLADER,"
            + "CUADRICEPIZQ,"
            + "CUADRICEPDER,"
            + "bicipital,"
            + "tricipital,"
            + "subescapular,"
            + "suprailiaco,"
            + "FECHA,"
            + "CUELLO,"
            + "objetivo, "
            + "ACTIVIDAD,"
            + "medidakey,"
            + "muneca"
            + " from medidas where clientekey = ? and fecha ?";//2
    final String GETALL = "SELECT "
            + "CLIENTEKEY,"
            + "PESO,"
            + "ALTURA,"
            + "CINTURAALTA,"
            + "CINTURAMEDIA,"
            + "CINTURABAJA,"
            + "CADERA,"
            + "BICEPIZQ,"
            + "BICEPDER,"
            + "PECTORAL,"
            + "PANTORRILLAIZQ,"
            + "PANTORRILLADER,"
            + "CUADRICEPIZQ,"
            + "CUADRICEPDER,"
            + "bicipital,"
            + "tricipital,"
            + "subescapular,"
            + "suprailiaco,"
            + "FECHA,"
            + "CUELLO,"
            + "objetivo, "
            + "ACTIVIDAD, "
            + "medidakey, "
            + "muneca"
            + " from medidas where clientekey = ? ORDER BY fecha DESC";//1

    public SQLiteMedidasDAO(Connection conex) {
        this.conex = conex;
    }

    @Override
    public ObservableList<Medida> obtenerTodos() throws DAOException {
        System.out.println("Este método no funciona.");
        return null;
    }

    /**
     *
     * @param clientekey
     * @return
     * @throws DAOException
     */
    @Override
    public ObservableList<Medida> obtenerTodos(String clientekey) throws DAOException {
        PreparedStatement s = null;
        ResultSet rs = null;
        ObservableList<Medida> m = FXCollections.observableArrayList();
        try {
            s = conex.prepareStatement(GETALL);
            s.setInt(1, Integer.parseInt(clientekey));
            rs = s.executeQuery();
            while (rs.next()) {
                m.add(convertir(rs));
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
        return m;
    }

    @Override
    public Medida obtener(String equal) throws DAOException {
        throw new DAOException("Este metodo no funciona");
    }

    @Override
    public Medida obtener(int clientekey, LocalDate fecha) throws DAOException {
        PreparedStatement s = null;
        ResultSet rs = null;
        Medida c = null;
        try {
            s = conex.prepareStatement(GETONE);
            s.setInt(1, clientekey);
            s.setDate(2, Date.valueOf(fecha));
            rs = s.executeQuery();
            if (rs.next()) {
                c = convertir(rs);
            } else {
                throw new DAOException("Medida no encontrada");
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
    public Medida convertir(ResultSet rs) throws DAOException {
        if (rs == null) {
            throw new DAOException("Error al convertir las medidas del cliente");
        }
        try {
            Cliente c = new Cliente();
            Medida m = new Medida();
            m.setMedidakey(rs.getInt("medidakey"));
            m.setCliente(Controller.getClientes().obtener("" + rs.getInt("clienteKey")));
            m.setFecha(rs.getDate("fecha").toLocalDate());
            m.setPeso(rs.getDouble("peso"));
            m.setAltura(rs.getDouble("ALTURA"));
            m.setCinturaAlta(rs.getDouble("cinturaAlta"));
            m.setCinturaMedia(rs.getDouble("cinturaMedia"));
            m.setCinturaBaja(rs.getDouble("cinturaBaja"));
            m.setCadera(rs.getDouble("cadera"));
            m.setCuello(rs.getDouble("cuello"));
            m.setPectoral(rs.getDouble("pectoral"));
            m.setBicepDer(rs.getDouble("bicepDer"));
            m.setBicepIzq(rs.getDouble("bicepIzq"));
            m.setCuadricepDer(rs.getDouble("cuadricepDer"));
            m.setCuadricepIzq(rs.getDouble("cuadricepIzq"));
            m.setPantorrillaDer(rs.getDouble("pantorrillaDer"));
            m.setPantorrillaIzq(rs.getDouble("pantorrillaIzq"));
            m.setBicipital(rs.getDouble("bicipital"));
            m.setTricipital(rs.getDouble("tricipital"));
            m.setSubescapular(rs.getDouble("subescapular"));
            m.setSuprailiaco(rs.getDouble("suprailiaco"));
            m.setObjetivo(Operacion.nombreCamelCase(rs.getString("objetivo")));
            m.setActividad(Operacion.nombreCamelCase(rs.getString("actividad")));
            m.setMuneca(rs.getDouble("muneca"));
            return m;
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
    }

    /**
     * @param a
     * @throws DAOException
     */
    @Override
    public void insertar(Medida a) throws DAOException {
        PreparedStatement s = null;
        try {
            s = conex.prepareStatement(INSERT);
            s.setInt(1, a.getCliente().getClienteKey());
            s.setDouble(2, a.getPeso());
            s.setDouble(3, a.getAltura());
            s.setDouble(4, a.getCinturaAlta());
            s.setDouble(5, a.getCinturaMedia());
            s.setDouble(6, a.getCinturaBaja());
            s.setDouble(7, a.getCadera());
            s.setDouble(8, a.getBicepIzq());
            s.setDouble(9, a.getBicepDer());
            s.setDouble(10, a.getPectoral());
            s.setDouble(11, a.getPantorrillaIzq());
            s.setDouble(12, a.getPantorrillaDer());
            s.setDouble(13, a.getCuadricepIzq());
            s.setDouble(14, a.getCuadricepDer());
            s.setDouble(15, a.getBicipital());
            s.setDouble(16, a.getTricipital());
            s.setDouble(17, a.getSubescapular());
            s.setDouble(18, a.getSuprailiaco());
            s.setDate(19, Date.valueOf(a.getFecha()));
            s.setDouble(20, a.getCuello());
            s.setString(21, a.getObjetivo().toLowerCase());
            s.setString(22, a.getActividad().toLowerCase());
            s.setDouble(23, a.getMuneca());
            if (s.executeUpdate() == 0) {
                throw new DAOException("Error al insertar las medidas del cliente");
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
    public void modificar(Medida a) throws DAOException {
        PreparedStatement s = null;
        try {
            s = conex.prepareStatement(UPDATE);
            s.setDouble(1, a.getPeso());
            s.setDouble(2, a.getAltura());
            s.setDouble(3, a.getCinturaAlta());
            s.setDouble(4, a.getCinturaMedia());
            s.setDouble(5, a.getCinturaBaja());
            s.setDouble(6, a.getCadera());
            s.setDouble(7, a.getBicepIzq());
            s.setDouble(8, a.getBicepDer());
            s.setDouble(9, a.getPectoral());
            s.setDouble(10, a.getPantorrillaIzq());
            s.setDouble(11, a.getPantorrillaDer());
            s.setDouble(12, a.getCuadricepIzq());
            s.setDouble(13, a.getCuadricepDer());
            s.setDouble(14, a.getBicipital());
            s.setDouble(15, a.getTricipital());
            s.setDouble(16, a.getSubescapular());
            s.setDouble(17, a.getSuprailiaco());
            s.setDate(18,Date.valueOf(a.getFecha()));
            s.setDouble(19, a.getCuello());
            s.setString(20, a.getObjetivo().toLowerCase());
            s.setString(21, a.getActividad().toLowerCase());
            s.setDouble(22, a.getMuneca());
            s.setInt(23, a.getMedidakey());
            if (s.executeUpdate() == 0) {
                throw new DAOException("Error al modificar las medidas del cliente");
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
    public void eliminar(Medida a) throws DAOException {
        PreparedStatement s = null;
        try {
            s = conex.prepareStatement(DELETE);
            s.setInt(1, a.getMedidakey());
            if (s.executeUpdate() == 0) {
                throw new DAOException("Error al eliminar medida");
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
}
