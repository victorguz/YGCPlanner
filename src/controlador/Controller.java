/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import DAO.DAOException;
import DAO.ObjetivosDAO;
import DAO.ReferenciasDAO;
import DAO.cliente.ClientesDAO;
import DAO.cliente.MedidasDAO;
import DAO.plan.AlimentosDAO;
import DAO.plan.AlxDietDAO;
import DAO.plan.EjerciciosDAO;
import DAO.plan.EjxRutDAO;
import DAO.plan.PlanDAO;
import SQLite.SQLiteDAOManager;
import ds.desktop.notify.DesktopNotify;
import ds.desktop.notify.NotifyTheme;
import java.awt.Color;
import java.awt.Toolkit;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import modelo.Objetivo;
import modelo.cliente.Cliente;
import modelo.cliente.Medida;
import modelo.plan.Alimento;
import modelo.plan.Ejercicio;
import modelo.plan.Plan;

/**
 *
 * @author 201621279487
 * @param <T>
 */
public abstract class Controller<T> implements Initializable {

    //Manager
    private static SQLiteDAOManager manager;

    private static Cliente cliente;
    private static boolean clientesUpdated;
    private static boolean clienteUpdated;
    private static Medida medida;
    private static boolean medidasUpdated;
    private static boolean medidaUpdated;
    private static boolean onClientes;
    private static boolean onMedidas;
    private static boolean onDietas;
    private static boolean onRutinas;
    private static boolean onAlimentos;
    private static boolean onEjercicios;
    private static boolean onConfig;
    private static boolean buttonsClientes;
    private static boolean buttonsMedidas;
    private static boolean buttonsDietas;
    private static boolean buttonsRutinas;
    private static boolean buttonsAlimentos;
    private static boolean buttonsEjercicios;
    private static boolean buttonsConfig;
    private static boolean onRegistrar;
    private static boolean onModificar;
    private static boolean onLimpiar;
    private static boolean onEliminar;

    public static boolean isButtonsClientes() {
        return buttonsClientes;
    }

    public static void setButtonsClientes(boolean buttonsClientes) {
        Controller.buttonsClientes = buttonsClientes;
    }

    public static boolean isButtonsMedidas() {
        return buttonsMedidas;
    }

    public static void setButtonsMedidas(boolean buttonsMedidas) {
        Controller.buttonsMedidas = buttonsMedidas;
    }

    public static boolean isButtonsDietas() {
        return buttonsDietas;
    }

    public static void setButtonsDietas(boolean buttonsDietas) {
        Controller.buttonsDietas = buttonsDietas;
    }

    public static boolean isButtonsRutinas() {
        return buttonsRutinas;
    }

    public static void setButtonsRutinas(boolean buttonsRutinas) {
        Controller.buttonsRutinas = buttonsRutinas;
    }

    public static boolean isButtonsAlimentos() {
        return buttonsAlimentos;
    }

    public static void setButtonsAlimentos(boolean buttonsAlimentos) {
        Controller.buttonsAlimentos = buttonsAlimentos;
    }

    public static boolean isButtonsEjercicios() {
        return buttonsEjercicios;
    }

    public static void setButtonsEjercicios(boolean buttonsEjercicios) {
        Controller.buttonsEjercicios = buttonsEjercicios;
    }

    public static boolean isButtonsConfig() {
        return buttonsConfig;
    }

    public static void setButtonsConfig(boolean buttonsConfig) {
        Controller.buttonsConfig = buttonsConfig;
    }

    @FXML
    protected TextField textBuscar;

    @FXML
    protected ComboBox<Objetivo> comboObjetivo;

    @FXML
    protected StackPane holderPane;

    @FXML
    protected ListView<Cliente> listView;

    @FXML
    protected TextField textEdad;

    @FXML
    protected ComboBox<String> comboSexo;

    @FXML
    protected ComboBox<Alimento> comboAlimentos;
    
    @FXML
    protected ComboBox<Ejercicio> comboEjercicios;

    protected static ObservableList<Cliente> clientes;

    protected static ObservableList<Medida> medidas;

    protected static ObservableList<Plan> rutinas;

    protected static ObservableList<Plan> dietas;

    public static ObservableList<Alimento> alimentos;

    protected static ObservableList<Ejercicio> ejercicios;

    protected static final ObservableList<String> sexos = FXCollections.observableArrayList("HOMBRE", "MUJER");

    private static SQLiteDAOManager getManager() throws DAOException {
        if (manager == null) {
            try {
                manager = new SQLiteDAOManager();
            } catch (SQLException ex) {
                mensaje("Error en getManager", "error", new DAOException(ex));
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

    public static ObjetivosDAO getObjetivos() throws DAOException {
        return getManager().getObjetivosDAO();
    }

    public static PlanDAO getDietas() throws DAOException {
        return getManager().getDietasDAO();
    }

    public static PlanDAO getPlanes() throws DAOException {
        return getManager().getPlanesDAO();
    }

    public static AlimentosDAO getAlimentos() throws DAOException {
        return getManager().getAlimentosDAO();
    }

    public static PlanDAO getRutinas() throws DAOException {
        return getManager().getRutinasDAO();
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

    public void setSexo(String sexo) {
        for (int i = 0; i < comboSexo.getItems().size(); i++) {
            if (comboSexo.getItems().get(i).equalsIgnoreCase(sexo)) {
                comboSexo.getSelectionModel().select(i);
            }
        }
    }

    public static void prueba() {
        NotifyTheme nt = NotifyTheme.Light;
        nt.setBgGrad(Color.white, Color.LIGHT_GRAY);
        DesktopNotify.setDefaultTheme(nt);
        DesktopNotify.showDesktopMessage("PRUEBA", "Esto es una prueba");
    }

    public static void mensaje(String mensaje, String tipo, DAOException ex) {
        NotifyTheme nt = NotifyTheme.Light;
        nt.setBgGrad(Color.white, Color.LIGHT_GRAY);
        DesktopNotify.setDefaultTheme(nt);
        if (tipo.equalsIgnoreCase("error")) {
            DesktopNotify.showDesktopMessage("AVISO", getResultOrException(ex.getMessage()), DesktopNotify.INFORMATION, 10000, (e) -> {
                ex.printStackTrace();
            });
        } else if (tipo.equalsIgnoreCase("aviso")) {
            DesktopNotify.showDesktopMessage("TIP", mensaje, DesktopNotify.TIP, 5000);
        } else if (tipo.equalsIgnoreCase("exito")) {
            DesktopNotify.showDesktopMessage(tipo.toUpperCase(), mensaje, DesktopNotify.SUCCESS, 3000);
        } else if (tipo.equalsIgnoreCase("referencia")) {
            DesktopNotify.showDesktopMessage(tipo.toUpperCase(), mensaje, DesktopNotify.INFORMATION, 5000);
        } else {
            DesktopNotify.showDesktopMessage(tipo.toUpperCase(), mensaje, DesktopNotify.INPUT_REQUEST, 3000);
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

    public static String getResultOrException(String ex_getMessage) {
        int uniqueCliente = ex_getMessage.indexOf("clientes.identificacion");
        int uniqueMedida = ex_getMessage.indexOf("medidas.clienteKey, medidas.fecha");
        int uniquePlan = ex_getMessage.indexOf("planes.nombre");
        int notNull = ex_getMessage.indexOf("NOT NULL");
        int noSuchTable = ex_getMessage.indexOf("no such table: ");
        int noSuchColumn = ex_getMessage.indexOf("no such column: ");
        int uniqueAlxdiet = ex_getMessage.indexOf("alxdiet.plankey, alxdiet.alimentokey, alxdiet.momento");
        if (uniqueMedida != -1) {
            return "Este cliente ya tiene medidas en esta fecha";
        } else if (uniqueCliente != -1) {
            return "Ya existe un cliente con esta identificación";
        } else if (uniquePlan != -1) {
            return "Ya existe un plan con este nombre";
        } else if (uniqueAlxdiet != -1) {
            return "Ya añadiste este alimento para este momento";
        } else if (notNull != -1) {
            String i = ex_getMessage.substring(notNull);
            return "Digite un " + i.substring(i.indexOf(".") + 1, i.indexOf(")"));
        } else if (noSuchTable != -1) {
            String i = ex_getMessage.substring(noSuchTable, ex_getMessage.indexOf(")")).toLowerCase();
            return "No se encontró la tabla " + i + ", contacte al programador";
        } else if (noSuchColumn != -1) {
            String i = ex_getMessage.substring(noSuchColumn);
            return "No se encontró la columna " + i + ", contacte al programador";
        } else {
            return ex_getMessage;
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
        Pattern patron = Pattern.compile("[A-Za-z]*\\s*");
        Matcher mevento = patron.matcher(e.getCharacter());
        if (mevento.matches()) {
            return true;
        } else {
            consume(e);
            return false;
        }
    }

    public static boolean getDigits(KeyEvent e) {
        Pattern patron = Pattern.compile("\\w*\\s*");
        Matcher mevento = patron.matcher(e.getCharacter());
        if (mevento.matches()) {
            return true;
        } else {
            consume(e);
            return false;
        }
    }

    public void consumeDouble(KeyEvent e) {
        getDouble(e);
    }

    public void consumeIntegers(KeyEvent e) {
        getIntegers(e);
    }

    public void consumeLetters(KeyEvent e) {
        getLetters(e);
    }

    public void consumeDigits(KeyEvent e) {
        getDigits(e);
    }

    public void obtenerObjetivos() {

        try {
            ObservableList objetivos = getObjetivos().obtenerTodos();
            if (!objetivos.isEmpty()) {
                comboObjetivo.getItems().clear();
                comboObjetivo.setItems(objetivos);
                comboObjetivo.getSelectionModel().select(0);
            }
        } catch (DAOException ex) {
            mensaje("Condición", "error", ex);
        }
    }

    public void selectObjetivo(String a) {
        if (!comboObjetivo.getItems().isEmpty()) {
            for (int i = 0; i < comboObjetivo.getItems().size(); i++) {
                if (comboObjetivo.getItems().get(i).getObjetivo().equalsIgnoreCase(a)) {
                    comboObjetivo.getSelectionModel().select(i);
                    return;
                }
            }
        }
    }

    public void selectSexo(String a) {
        if (!comboSexo.getItems().isEmpty()) {
            for (int i = 0; i < comboSexo.getItems().size(); i++) {
                if (comboSexo.getItems().get(i).equalsIgnoreCase(a)) {
                    comboSexo.getSelectionModel().select(i);
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

    public abstract void buscar();

    public abstract void limpiar();

    public static boolean isOnClientes() {
        return onClientes;
    }

    public static void setOnClientes(boolean aOnClientes) {
        onClientes = aOnClientes;
    }

    public static boolean isOnMedidas() {
        return onMedidas;
    }

    public static void setOnMedidas(boolean aOnMedidas) {
        onMedidas = aOnMedidas;
    }

    public static boolean isOnDietas() {
        return onDietas;
    }

    public static void setOnDietas(boolean aOnDietas) {
        onDietas = aOnDietas;
    }

    public static boolean isOnRutinas() {
        return onRutinas;
    }

    public static void setOnRutinas(boolean aOnRutinas) {
        onRutinas = aOnRutinas;
    }

    public static boolean isOnAlimentos() {
        return onAlimentos;
    }

    public static void setOnAlimentos(boolean aOnAlimentos) {
        onAlimentos = aOnAlimentos;
    }

    public static boolean isOnEjercicios() {
        return onEjercicios;
    }

    public static void setOnEjercicios(boolean aOnEjercicios) {
        onEjercicios = aOnEjercicios;
    }

    public static boolean isOnConfig() {
        return onConfig;
    }

    public static void setOnConfig(boolean aOnConfig) {
        onConfig = aOnConfig;
    }

    public static boolean isOnRegistrar() {
        return onRegistrar;
    }

    public static void setOnRegistrar(boolean aOnRegistrar) {
        onRegistrar = aOnRegistrar;
    }

    public static boolean isOnModificar() {
        return onModificar;
    }

    public static void setOnModificar(boolean aOnModificar) {
        onModificar = aOnModificar;
    }

    public static boolean isOnLimpiar() {
        return onLimpiar;
    }

    public static void setOnLimpiar(boolean aOnLimpiar) {
        onLimpiar = aOnLimpiar;
    }

    public static boolean isOnEliminar() {
        return onEliminar;
    }

    public static void setOnEliminar(boolean aOnEliminar) {
        onEliminar = aOnEliminar;
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
     * si se actualiza o elimina algún cliente,
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
     * si se selecciona algún cliente,
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
     * si se actualiza o elimina algún medida,
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
     * si se selecciona algún medida,
     *
     * @param c
     */
    public static void setMedidaUpdated(boolean c) {
        medidaUpdated = c;
    }

    public static boolean isMedidaUpdated() {
        return medidaUpdated;
    }

    public void buscarAlimento() {
        if (textBuscar.getText().isEmpty()) {
            obtenerAlimentos();
        } else {
            try {
                alimentos = getAlimentos().obtenerTodos(textBuscar.getText());
                comboAlimentos.getItems().clear();
                if (alimentos.isEmpty()) {
                    mensaje("No se encontraron alimentos con este nombre", "aviso", null);
                    limpiar();
                } else {
                    comboAlimentos.setItems(alimentos);
                    mensaje("Se encontraron " + alimentos.size() + " alimentos", "exito", null);
                    selectAlimento(0);
                }
            } catch (DAOException ex) {
                mensaje("Condición", "error", ex);
            }
        }
    }

    public void obtenerAlimentos() {
        try {
            alimentos = getAlimentos().obtenerTodos();
            comboAlimentos.getItems().clear();
            if (!alimentos.isEmpty()) {
                comboAlimentos.setItems(alimentos);
                selectAlimento(0);
            }
        } catch (DAOException ex) {
            mensaje("Condición", "error", ex);
        }
    }

    public void selectAlimento(int i) {
        if (!comboAlimentos.getItems().isEmpty()) {
            comboAlimentos.getSelectionModel().select(i);
        }
    }
    public void buscarEjercicio() {
        if (textBuscar.getText().isEmpty()) {
            obtenerEjercicios();
        } else {
            try {
                ejercicios = getEjercicios().obtenerTodos(textBuscar.getText());
                comboEjercicios.getItems().clear();
                if (ejercicios.isEmpty()) {
                    mensaje("No se encontraron ejercicios con este nombre", "aviso", null);
                    limpiar();
                } else {
                    comboEjercicios.setItems(ejercicios);
                    mensaje("Se encontraron " + ejercicios.size() + " ejercicios", "exito", null);
                    selectEjercicio(0);
                }
            } catch (DAOException ex) {
                mensaje("Condición", "error", ex);
            }
        }
    }
    public void obtenerEjercicios() {
        try {
            ejercicios = getEjercicios().obtenerTodos();
            comboEjercicios.getItems().clear();
            if (!ejercicios.isEmpty()) {
                comboEjercicios.setItems(ejercicios);
                selectAlimento(0);
            }
        } catch (DAOException ex) {
            mensaje("Condición", "error", ex);
        }
    }

    public void selectEjercicio(int i) {
        if (!comboEjercicios.getItems().isEmpty()) {
            comboEjercicios.getSelectionModel().select(i);
        }
    }
}
