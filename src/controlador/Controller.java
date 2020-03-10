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
import DAO.plan.*;
import SQLite.SQLiteDAOManager;
import ds.desktop.notify.DesktopNotify;
import ds.desktop.notify.NotifyTheme;
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

import java.awt.*;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @param <T>
 * @author 201621279487
 */
public abstract class Controller<T> implements Initializable {

    public static ObservableList<Alimento> alimentos = FXCollections.observableArrayList();
    protected static ObservableList<Cliente> clientes = FXCollections.observableArrayList();
    protected static ObservableList<Medida> medidas = FXCollections.observableArrayList();
    protected static ObservableList<Plan> rutinas = FXCollections.observableArrayList();
    protected static ObservableList<Plan> dietas = FXCollections.observableArrayList();
    protected static ObservableList<Ejercicio> ejercicios = FXCollections.observableArrayList();
    private static SQLiteDAOManager manager;
    private static Cliente cliente;
    private static Medida medida;
    private static boolean onConfig;
    /**
     * TextField que está contenido en la mayoría de frames
     */
    @FXML
    protected TextField textBuscar;

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

    public static Cliente getCliente() {
        return cliente;
    }

    public static void setCliente(Cliente cliente) {
        Controller.cliente = cliente;
    }

    public static Medida getMedida() {
        return medida;
    }

    public static void setMedida(Medida medida) {
        Controller.medida = medida;
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
        if (ex.contains("medidas.clientekey, medidas.fecha")) {
            return "Este cliente ya tiene medidas en esta fecha";
        } else if (ex.contains("clientes.identificacion")) {
            return "Ya existe un cliente con esta identificación";
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

    public static boolean isOnConfig() {
        return onConfig;
    }

    public static void setOnConfig(boolean aOnConfig) {
        onConfig = aOnConfig;
    }

    public void consumeIntegers(KeyEvent e) {
        getIntegers(e);
    }

    public void consumeCorreo(KeyEvent e) {
        getCorreo(e);
    }

    public void consumeWeb(KeyEvent e) {
        getWeb(e);
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

}
