/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import DAO.ReferenciasDAO;
import DAO.cliente.ClientesDAO;
import DAO.cliente.MedidasDAO;
import DAO.plan.*;
import SQLite.SQLiteDAOManager;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import modelo.cliente.Cliente;
import modelo.cliente.Medida;
import modelo.plan.Plan;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @param <T>
 * @author 201621279487
 */
public abstract class Controller<T> implements Initializable {

    protected static ObservableList<Cliente> clientes = FXCollections.observableArrayList();
    protected static ObservableList<Medida> medidas = FXCollections.observableArrayList();
    protected static ObservableList<Plan> rutinas = FXCollections.observableArrayList();
    protected static ObservableList<Plan> dietas = FXCollections.observableArrayList();
    private static SQLiteDAOManager manager;
    private static Cliente cliente;
    private static Medida medida;
    private static boolean onConfig;

    private static SQLiteDAOManager getManager() {
        if (manager == null) {
            try {
                manager = new SQLiteDAOManager();
            } catch (SQLException ex) {
                excepcion(ex);
            }
        }
        return manager;
    }

    public static ClientesDAO getClientes() {
        return getManager().getClientesDAO();
    }

    public static MedidasDAO getMedidas() {
        return getManager().getMedidasDAO();
    }

    public static PlanesDAO getPlanes() {
        return getManager().getPlanesDAO();
    }

    public static AlimentosDAO getAlimentos() {
        return getManager().getAlimentosDAO();
    }

    public static EjerciciosDAO getEjercicios() {
        return getManager().getEjerciciosDAO();
    }

    public static AlxDietDAO getAlxdiets() {
        return getManager().getAlimentosDietasDAO();
    }

    public static EjxRutDAO getEjxruts() {
        return getManager().getEjerciciosRutinasDAO();
    }

    public static ReferenciasDAO getReferencias() {
        return getManager().getReferenciasDAO();
    }

    public static Cliente getCliente() {
        if (Controller.cliente == null) {
            setCliente(new Cliente());
        }
        return Controller.cliente;
    }

    public static void setCliente(Cliente cliente) {
        Controller.cliente = cliente;
    }

    public static Medida getMedida() {
        if (Controller.medida == null) {
            setMedida(new Medida());
        }
        return Controller.medida;
    }

    public static void setMedida(Medida medida) {
        Controller.medida = medida;
    }


    public static boolean opcion(String mensaje, String tipo) {
        int x = JOptionPane.showConfirmDialog(null, mensaje
                        + ".\n\nEsta acción no se puede deshacer",
                tipo.toUpperCase(),
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null);
        return x == 0;
    }

    public static void mensaje(String mensaje, String tipo) {
        try {
            //Obtain only one instance of the SystemTray object
            SystemTray tray = SystemTray.getSystemTray();

            // If you want to create an icon in the system tray to preview
            java.awt.Image image = Toolkit.getDefaultToolkit().createImage("src/imagen/icono/copy.png");
            //Alternative (if the icon is on the classpath):
            //Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("icon.png"));

            TrayIcon trayIcon = new TrayIcon(image, "Tooltip");
            //Let the system resize the image if needed
            trayIcon.setImageAutoSize(true);
            //Set tooltip text for the tray icon
            trayIcon.setToolTip("Tooltip For Icon");
            tray.add(trayIcon);

            TrayIcon.MessageType notify = TrayIcon.MessageType.NONE;

            if (tipo.equalsIgnoreCase("aviso")) {
                notify = TrayIcon.MessageType.WARNING;
            } else if (tipo.equalsIgnoreCase("exito")) {
                notify = TrayIcon.MessageType.INFO;
            }

            // Display info notification:
            trayIcon.displayMessage(tipo.toUpperCase(), mensaje, notify);

        } catch (Exception ex) {
            java.awt.Image image = Toolkit.getDefaultToolkit().createImage("src/imagen/icono/copy.png");
            TrayIcon trayIcon = new TrayIcon(image, "Tooltip");
            trayIcon.displayMessage("Error de notificación", "Ocurrió el siguiente error con las notificaciones: " + ex.getMessage(), TrayIcon.MessageType.ERROR);
        }
    }

    public static void excepcion(Exception ex) {
        try {
            //Obtain only one instance of the SystemTray object
            SystemTray tray = SystemTray.getSystemTray();

            // If you want to create an icon in the system tray to preview
            java.awt.Image image = Toolkit.getDefaultToolkit().createImage("src/imagen/icono/copy.png");
            //Alternative (if the icon is on the classpath):
            //Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("icon.png"));

            TrayIcon trayIcon = new TrayIcon(image, "Tooltip", new PopupMenu());
            //Let the system resize the image if needed
            trayIcon.setImageAutoSize(true);
            //Set tooltip text for the tray icon
            trayIcon.setToolTip("Tooltip For Icon");
            tray.add(trayIcon);

            TrayIcon.MessageType notify = TrayIcon.MessageType.WARNING;
            String tipo = "ERROR";
            String mensaje = getResultOrException(ex.getMessage());

            if (mensaje == null) {
                if (ex.fillInStackTrace().toString().contains("SimpleException")) {
                    tipo = "AVISO";
                    mensaje = ex.getMessage();
                } else {
                    mensaje = "Ha ocurrido un error inesperado, si persiste contacte a soporte técnico";
                    notify = TrayIcon.MessageType.ERROR;
                    ex.printStackTrace();
                }
            }

            trayIcon.displayMessage(tipo.toUpperCase(), mensaje, notify);

        } catch (Exception e) {
            java.awt.Image image = Toolkit.getDefaultToolkit().createImage("src/imagen/icono/copy.png");
            TrayIcon trayIcon = new TrayIcon(image, "Tooltip");
            trayIcon.displayMessage("Error de notificación", "Ocurrió el siguiente error con las notificaciones: " + e.getMessage(), TrayIcon.MessageType.ERROR);
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
        } else if (ex.contains("YezidGuzmanCoach.pdf")) {
            return "Primero cierre el archivo PDF";
        } else if (ex.contains("clientes.identificacion")) {
            return "Ya existe un cliente con esta identificación";
        } else if (ex.contains("alxdiet.plankey, alxdiet.alimentokey, alxdiet.momento, alxdiet.dia")) {
            return "Ya añadiste este alimento para este momento del día";
        } else if (ex.contains("ejercicios.nombre")) {
            return "Ya existe un ejercicio con este nombre";
        } else if (ex.contains("planes.nombre, planes.tipo")) {
            return "Ya existe un plan con este nombre";
        } else if (ex.contains("alimentos.nombre")) {
            return "Ya existe un alimento con este nombre";
        } else if (ex.contains("NOT NULL")) {
            String i = ex.substring(ex.indexOf("NOT NULL"));
            return "Digite un " + i.substring(i.indexOf(".") + 1, i.indexOf(")"));
        } else if (ex.contains("ejxrut.plankey, ejxrut.ejerciciokey, ejxrut.dia, ejxrut.momento")) {
            return "Este bloque ya contiene este ejercicio";
        } else if (ex.contains("referencias.nombre")) {
            return "Ya existe una plantilla con este nombre";
        } else {
            return null;
        }
    }

    public static void consume(KeyEvent e) {
        boolean backspace = e.getCharacter().equals("\b");
        if (!(backspace
                || e.getCode().equals(KeyCode.ENTER)
                || e.getCode().equals(KeyCode.BACK_SPACE)
                || e.getCode().equals(KeyCode.BACK_QUOTE)
                || e.getCode().equals(KeyCode.BACK_SLASH)
                || e.getCode().equals(KeyCode.TAB)
                || e.getCode().equals(KeyCode.UP)
                || e.getCode().equals(KeyCode.DOWN)
                || e.getCode().equals(KeyCode.RIGHT)
                || e.getCode().equals(KeyCode.LEFT)
                || e.getCode().equals(KeyCode.SHIFT)
                || e.getCode().equals(KeyCode.SPACE)
                || e.getCode().equals(KeyCode.DELETE))) {
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
        Pattern patron = Pattern.compile("[A-Za-z]*\\s*[áéíóúñÁÉÍÓÚÑ#]*");
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

    public void increaseOrDecrease(KeyEvent e) {
        TextField spin = (TextField) e.getSource();
        if (spin.getText().isEmpty()) {
            spin.setText("1");
        }
        if (e.getCode().equals(KeyCode.DOWN)) {
            if (Double.parseDouble(spin.getText()) > 1) {
                if (spin.getText().contains(".")) {
                    spin.setText((Double.parseDouble(spin.getText()) - 0.1) + "");
                } else {
                    spin.setText((Integer.parseInt(spin.getText()) - 1) + "");
                }
            }
        }
        if (e.getCode().equals(KeyCode.UP)) {
            if (!(spin.getText().equalsIgnoreCase("0")
                    || spin.getText().equalsIgnoreCase("0.0"))) {
                if (spin.getText().contains(".")) {
                    spin.setText((Double.parseDouble(spin.getText()) + 0.1) + "");
                } else {
                    spin.setText((Integer.parseInt(spin.getText()) + 1) + "");
                }
            }
        }
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
