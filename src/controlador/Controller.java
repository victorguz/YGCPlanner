/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import DAO.DAOException;
import DAO.ReferenciasDAO;
import DAO.cliente.ClientesDAO;
import DAO.cliente.MedidasDAO;
import DAO.plan.AlimentosDAO;
import DAO.plan.AlxDietDAO;
import DAO.plan.EjerciciosDAO;
import DAO.plan.EjxRutDAO;
import SQLite.SQLiteDAOManager;
import ds.desktop.notify.DesktopNotify;
import ds.desktop.notify.NotifyTheme;
import java.awt.Color;
import java.awt.Toolkit;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import modelo.cliente.Cliente;
import modelo.cliente.Medida;
import modelo.plan.Alimento;
import modelo.plan.Ejercicio;
import modelo.plan.Plan;
import DAO.plan.PlanesDAO;

/**
 *
 * @author 201621279487
 * @param <T>
 */
public abstract class Controller<T> implements Initializable {

    /**
     * TextField que está contenido en la mayoría de frames
     */
    @FXML
    protected TextField textBuscar;

    @FXML
    protected TextField textEdad;

    protected static ObservableList<Cliente> clientes = FXCollections.observableArrayList();

    protected static ObservableList<Medida> medidas = FXCollections.observableArrayList();

    protected static ObservableList<Plan> rutinas = FXCollections.observableArrayList();

    protected static ObservableList<Plan> dietas = FXCollections.observableArrayList();

    public static ObservableList<Alimento> alimentos = FXCollections.observableArrayList();

    protected static ObservableList<Ejercicio> ejercicios = FXCollections.observableArrayList();

    protected static final ObservableList<String> sexos = FXCollections.observableArrayList("Hombre", "Mujer");
    protected static final ObservableList<String> objetivos = FXCollections.observableArrayList("Perder", "Aumentar", "Mantener");

    private static SQLiteDAOManager manager;
    private static Cliente cliente;
    private static boolean clientesUpdated;
    private static boolean clienteUpdated;
    private static Medida medida;
    private static boolean medidasUpdated;
    private static boolean dietasUpdated;
    private static boolean rutinasUpdated;
    private static boolean medidaUpdated;
    private static boolean alimentosUpdated;
    private static boolean ejerciciosUpdated;
    private static boolean onConfig;

    public static boolean isEjerciciosUpdated() {
        return ejerciciosUpdated;
    }

    public static void setEjerciciosUpdated(boolean updated) {
        Controller.ejerciciosUpdated = updated;
    }

    public static boolean isAlimentosUpdated() {
        return alimentosUpdated;
    }

    public static void setAlimentosUpdated(boolean updated) {
        Controller.alimentosUpdated = updated;
    }

    private static SQLiteDAOManager getManager() throws DAOException {
        if (manager == null) {
            try {
                manager = new SQLiteDAOManager();
            } catch (SQLException ex) {
                excepcion(ex);
            }
        }
        return manager;
    }

    public static ClientesDAO getClientes() throws DAOException {
        return getManager().getClientesDAO();
    }

    public static MedidasDAO getMedidas() throws DAOException {
        return getManager().getMedidasDAO();
    }

    public static PlanesDAO getPlanes() throws DAOException {
        return getManager().getPlanesDAO();
    }

    public static AlimentosDAO getAlimentos() throws DAOException {
        return getManager().getAlimentosDAO();
    }

    public static EjerciciosDAO getEjercicios() throws DAOException {
        return getManager().getEjerciciosDAO();
    }

    public static AlxDietDAO getAlxdiets() throws DAOException {
        return getManager().getAlimentosDietasDAO();
    }

    public static EjxRutDAO getEjxruts() throws DAOException {
        return getManager().getEjerciciosRutinasDAO();
    }

    public static ReferenciasDAO getReferencias() throws DAOException {
        return getManager().getReferenciasDAO();
    }

    public static void prueba() {
        NotifyTheme nt = NotifyTheme.Light;
        nt.setBgGrad(Color.white, Color.LIGHT_GRAY);
        DesktopNotify.setDefaultTheme(nt);
        DesktopNotify.showDesktopMessage("PRUEBA", "Esto es una prueba");
    }

    public static void mensaje(String mensaje, String tipo) {
        NotifyTheme nt = NotifyTheme.Light;
        nt.setBgGrad(Color.white, Color.LIGHT_GRAY);
        DesktopNotify.setDefaultTheme(nt);
        int notify = 0;
        if (tipo.equalsIgnoreCase("aviso")) {
            notify = DesktopNotify.TIP;
        } else if (tipo.equalsIgnoreCase("exito")) {
            notify = DesktopNotify.SUCCESS;
        } else if (tipo.equalsIgnoreCase("info")) {
            notify = DesktopNotify.INFORMATION;
        } else if (tipo.equalsIgnoreCase("vacio")) {
            notify = DesktopNotify.INPUT_REQUEST;
        }
        DesktopNotify.showDesktopMessage(tipo.toUpperCase(), mensaje, notify, 5000, null);
    }

    public static void excepcion(Exception ex) {
        NotifyTheme nt = NotifyTheme.Light;
        nt.setBgGrad(Color.white, Color.LIGHT_GRAY);
        DesktopNotify.setDefaultTheme(nt);
        if (ex.getMessage().contains("sql")) {
            DesktopNotify.showDesktopMessage("AVISO", getResultOrException(ex.getMessage()), DesktopNotify.ERROR, 10000, (e) -> {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            });
        } else {
            DesktopNotify.showDesktopMessage("Error", getResultOrException(ex.getMessage()), DesktopNotify.ERROR, 10000, (e) -> {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            });
        }
    }

    public static void fadeTransition(Node node, int time) {
        FadeTransition ft = new FadeTransition(Duration.millis(time));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

    public static String getResultOrException(String ex) {
        if (ex.contains("medidas.clienteKey, medidas.fecha")) {
            return "Este cliente ya tiene medidas en esta fecha";
        } else if (ex.contains("clientes.identificacion")) {
            return "Ya existe un cliente con esta identificación";
        } else if (ex.contains("planes.nombre")) {
            return "Ya existe un plan con este nombre";
        } else if (ex.contains("alxdiet.plankey, alxdiet.alimentokey, alxdiet.momento, alxdiet.dia, alxdiet.combinacion")) {
            return "Esta opcion ya contiene este alimento";
        } else if (ex.contains("ejercicios.nombre")) {
            return "Ya existe un ejercicio con este nombre";
        } else if (ex.contains("planes.nombre, planes.tipo")) {
            return "Ya existe un plan con este nombre";
        } else if (ex.contains("alimentos.nombre")) {
            return "Ya existe un alimento con este nombre";
        } else if (ex.contains("NOT NULL")) {
            String i = ex.substring(ex.indexOf("NOT NULL"));
            return "Digite un " + i.substring(i.indexOf(".") + 1, i.indexOf(")"));
        } else if (ex.contains("no such table: ")) {
            String i = ex.substring(ex.indexOf("no such table: "), ex.indexOf(")")).toLowerCase();
            return "No se encontró la tabla " + i + ", contacte al programador";
        } else if (ex.contains("no such column: ")) {
            String i = ex.substring(ex.indexOf("no such column: "));
            return "No se encontró la columna " + i + ", contacte al programador";
        } else if (ex.contains("ejxrut.plankey, ejxrut.ejerciciokey, ejxrut.dia, ejxrut.combinacion")) {
            return "Este bloque ya contiene este ejercicio";
        } else {
            return ex;
        }
    }

    public static void consume(KeyEvent e) {
        boolean backspace = e.getCharacter().equals("\b");
        boolean tab = e.getCharacter().equals("\t");
        boolean enter = e.getCharacter().getBytes()[0] == 13;
        if (!(backspace || tab || enter)) {
            e.consume();
            Toolkit.getDefaultToolkit().beep();
        }
    }

    public static boolean getDouble(KeyEvent e) {
        Pattern patron = Pattern.compile("[.0-9]*");
        Matcher mevento = patron.matcher(e.getCharacter());
        if (mevento.matches()) {
            return true;
        } else {
            consume(e);
            return false;
        }
    }

    public static boolean getIntegers(KeyEvent e) {
        Pattern patron = Pattern.compile("[0-9]*");
        Matcher mevento = patron.matcher(e.getCharacter());
        if (mevento.matches()) {
            return true;
        } else {
            consume(e);
            return false;
        }
    }
  public void consumeIntegers(KeyEvent e) {
        getIntegers(e);
    }
    public static boolean getLetters(KeyEvent e) {
        Pattern patron = Pattern.compile("[A-Za-z]*\\s*[áéíóúñÁÉÍÓÚÑ]*");
        Matcher mevento = patron.matcher(e.getCharacter());
        if (mevento.matches()) {
            return true;
        } else {
            consume(e);
            return false;
        }
    }

    public static boolean getDigits(KeyEvent e) {
        Pattern patron = Pattern.compile("\\w*\\s*[.,-_]*[áéíóúñ]*");
        Matcher mevento = patron.matcher(e.getCharacter());
        if (mevento.matches()) {
            return true;
        } else {
            consume(e);
            return false;
        }
    }

    public static boolean getCorreo(KeyEvent e) {
        Pattern patron = Pattern.compile("\\w*[@._-]*");
        Matcher mevento = patron.matcher(e.getCharacter());
        if (mevento.matches()) {
            return true;
        } else {
            consume(e);
            return false;
        }
    }

    public void consumeCorreo(KeyEvent e) {
        getCorreo(e);
    }

    public static boolean getWeb(KeyEvent e) {
        Pattern patron = Pattern.compile("\\w*[/.:]*");
        Matcher mevento = patron.matcher(e.getCharacter());
        if (mevento.matches()) {
            return true;
        } else {
            consume(e);
            return false;
        }
    }

    public void consumeWeb(KeyEvent e) {
        getWeb(e);
    }

    public static boolean getTel(KeyEvent e) {
        Pattern patron = Pattern.compile("[0-9]*[ +]*");
        Matcher mevento = patron.matcher(e.getCharacter());
        if (mevento.matches()) {
            return true;
        } else {
            consume(e);
            return false;
        }
    }

    public void consumeTel(KeyEvent e) {
        getTel(e);
    }

    public void consumeDouble(KeyEvent e) {
        getDouble(e);
    }

  

    public void consumeLetters(KeyEvent e) {
        getLetters(e);
    }

    public void consumeDigits(KeyEvent e) {
        getDigits(e);
    }

    public void selectCombo(ComboBox<String> combo, String a) {
        if (!combo.getItems().isEmpty()) {
            for (int i = 0; i < combo.getItems().size(); i++) {
                if (combo.getItems().get(i).equalsIgnoreCase(a)) {
                    combo.getSelectionModel().select(i);
                    return;
                }
            }
        }
    }

    /**
     * Método para implementación de hilo
     */
    public abstract void updated();

    public abstract void mostrar();

    public abstract T captar() throws DAOException;

    public abstract void obtener();

    public abstract void registrar();

    public abstract void modificar();

    public abstract void eliminar();

    public abstract void limpiar();

    public static boolean isOnConfig() {
        return onConfig;
    }

    public static void setOnConfig(boolean aOnConfig) {
        onConfig = aOnConfig;
    }

    public static Cliente getCliente() {
        if (cliente == null) {
            cliente = new Cliente();
        }
        return cliente;
    }

    public static void setCliente(Cliente c) {
        cliente = c;
    }

    /**
     * Cuando se actualiza la lista de clientes cambia estado a actualizado
     * (true)
     *
     * @param c
     */
    public static void setClientesUpdated(boolean c) {
        clientesUpdated = c;
    }

    public static boolean isClientesUpdated() {
        return clientesUpdated;
    }

    /**
     * Cuando se actualiza el cliente, cambia estado a actualizado (true)
     *
     * @param c
     */
    public static void setClienteUpdated(boolean c) {
        clienteUpdated = c;
    }

    public static boolean isClienteUpdated() {
        return clienteUpdated;
    }

    public static Medida getMedida() {
        if (medida == null) {
            medida = new Medida();
        }
        return medida;
    }

    public static void setMedida(Medida c) {
        medida = c;
    }

    /**
     * Cuando se actualiza la lista de medidas cambia estado a actualizado
     * (true)
     *
     * @param c
     */
    public static void setMedidasUpdated(boolean c) {
        medidasUpdated = c;
    }

    public static boolean isMedidasUpdated() {
        return medidasUpdated;
    }

    /**
     * Cuando se actualiza la variable de medida cambia estado a actualizado
     * (true)
     *
     * @param c
     */
    public static void setMedidaUpdated(boolean c) {
        medidaUpdated = c;
    }

    public static boolean isMedidaUpdated() {
        return medidaUpdated;
    }

    /**
     * Cuando se actualiza la lista de dietas cambia estado a actualizado (true)
     *
     * @param c
     */
    public static void setDietasUpdated(boolean c) {
        dietasUpdated = c;
    }

    public static boolean isDietasUpdated() {
        return dietasUpdated;
    }

    /**
     * Cuando se actualiza la lista de rutina cambia estado a actualizado (true)
     *
     * @param c
     */
    public static void setRutinasUpdated(boolean c) {
        rutinasUpdated = c;
    }

    public static boolean isRutinasUpdated() {
        return rutinasUpdated;
    }

}
