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
            + "DIETAKEY,"
            + "RUTINAKEY,"
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
            + " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";//25
    final String UPDATE = "UPDATE medidas SET "
            + "DIETAKEY =?,"
            + "RUTINAKEY=?,"
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
            + "DIETAKEY,"
            + "RUTINAKEY,"
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
            + "DIETAKEY,"
            + "RUTINAKEY,"
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
            m.setDieta(Controller.getPlanes().obtener("" + rs.getInt("dietaKey")));
            m.setRutina(Controller.getPlanes().obtener("" + rs.getInt("rutinaKey")));
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
            m.setObjetivo(rs.getString("objetivo"));
            m.setActividad(rs.getString("actividad"));
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
            s.setInt(2, a.getDieta().getPlankey());
            s.setInt(3, a.getRutina().getPlankey());
            s.setDouble(4, a.getPeso());
            s.setDouble(5, a.getAltura());
            s.setDouble(6, a.getCinturaAlta());
            s.setDouble(7, a.getCinturaMedia());
            s.setDouble(8, a.getCinturaBaja());
            s.setDouble(9, a.getCadera());
            s.setDouble(10, a.getBicepIzq());
            s.setDouble(11, a.getBicepDer());
            s.setDouble(12, a.getPectoral());
            s.setDouble(13, a.getPantorrillaIzq());
            s.setDouble(14, a.getPantorrillaDer());
            s.setDouble(15, a.getCuadricepIzq());
            s.setDouble(16, a.getCuadricepDer());
            s.setDouble(17, a.getBicipital());
            s.setDouble(18, a.getTricipital());
            s.setDouble(19, a.getSubescapular());
            s.setDouble(20, a.getSuprailiaco());
            s.setDate(21, Date.valueOf(a.getFecha()));
            s.setDouble(22, a.getCuello());
            s.setString(23, a.getObjetivo());
            s.setString(24, a.getActividad());
            s.setDouble(25, a.getMuneca());
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
            s.setInt(1, a.getDieta().getPlankey());
            s.setInt(2, a.getRutina().getPlankey());
            s.setDouble(3, a.getPeso());
            s.setDouble(4, a.getAltura());
            s.setDouble(5, a.getCinturaAlta());
            s.setDouble(6, a.getCinturaMedia());
            s.setDouble(7, a.getCinturaBaja());
            s.setDouble(8, a.getCadera());
            s.setDouble(9, a.getBicepIzq());
            s.setDouble(10, a.getBicepDer());
            s.setDouble(11, a.getPectoral());
            s.setDouble(12, a.getPantorrillaIzq());
            s.setDouble(13, a.getPantorrillaDer());
            s.setDouble(14, a.getCuadricepIzq());
            s.setDouble(15, a.getCuadricepDer());
            s.setDouble(16, a.getBicipital());
            s.setDouble(17, a.getTricipital());
            s.setDouble(18, a.getSubescapular());
            s.setDouble(19, a.getSuprailiaco());
            s.setDate(20, Date.valueOf(a.getFecha()));
            s.setDouble(21, a.getCuello());
            s.setString(22, a.getObjetivo());
            s.setString(23, a.getActividad());
            s.setDouble(24, a.getMuneca());
            s.setInt(25, a.getMedidakey());
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
