/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import DAO.DAOException;
import static controlador.Controller.getAlimentos;
import static controlador.Controller.isAlimentosUpdated;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import modelo.plan.Alimento;
import modelo.plan.AlxDiet;
import modelo.plan.Plan;

/**
 *
 * @author 201621279487
 */
public class DietasController extends Controller<Plan> {

    @FXML
    private ToggleGroup filtro;

    @FXML
    private ComboBox<Plan> comboDieta;

    @FXML
    private ComboBox<String> comboObjetivos;

    @FXML
    private TextField textNombre;

    @FXML
    private TextArea textDescripcion;

    @FXML
    private TextField textCantidad;

    @FXML
    private TextField textProteinas;

    @FXML
    private TextField textCarbohidratos;

    @FXML
    private TextField textGrasas;

    @FXML
    private Label labelGramsCliente;

    @FXML
    private Label labelKcalCliente;

    @FXML
    private Label labelCarbsCliente;

    @FXML
    private Label labelProteinCliente;

    @FXML
    private Label labelFatCliente;

    @FXML
    private Label labelGrams;

    @FXML
    private Label labelKcal;

    @FXML
    private Label labelCarbs;

    @FXML
    private Label labelProtein;

    @FXML
    private Label labelFat;

    @FXML
    private Label gramosDesayuno;

    @FXML
    private Label kcalDesayuno;

    @FXML
    private Label carbDesayuno;

    @FXML
    private Label proteDesayuno;

    @FXML
    private Label fatDesayuno;

    @FXML
    private TextArea textDesayuno;

    @FXML
    private Label gramosAlmuerzo;

    @FXML
    private Label kcalAlmuerzo;

    @FXML
    private Label carbAlmuerzo;

    @FXML
    private Label proteAlmuerzo;

    @FXML
    private Label fatAlmuerzo;

    @FXML
    private TextArea textAlmuerzo;

    @FXML
    private Label gramosCena;

    @FXML
    private Label kcalCena;

    @FXML
    private Label carbCena;

    @FXML
    private Label proteCena;

    @FXML
    private Label fatCena;

    @FXML
    private TextArea textCena;

    @FXML
    private Label gramosPre;

    @FXML
    private Label kcalPre;

    @FXML
    private Label carbPre;

    @FXML
    private Label protePre;

    @FXML
    private Label fatPre;

    @FXML
    private TextArea textPre;

    @FXML
    private Label gramosPost;

    @FXML
    private Label kcalPost;

    @FXML
    private Label carbPost;

    @FXML
    private Label protePost;

    @FXML
    private Label fatPost;

    @FXML
    private TextArea textPost;

    @FXML
    private Label gramosMerienda;

    @FXML
    private Label kcalMerienda;

    @FXML
    private Label carbMerienda;

    @FXML
    private Label proteMerienda;

    @FXML
    private Label fatMerienda;

    @FXML
    private TextArea textMerienda;

    @FXML
    private Label gramosExtra;

    @FXML
    private Label kcalExtra;

    @FXML
    private Label carbExtra;

    @FXML
    private Label proteExtra;

    @FXML
    private Label fatExtra;

    @FXML
    private TextArea textExtra;

    @FXML
    private ListView<AlxDiet> listExtra;

    @FXML
    private ListView<AlxDiet> listMerienda;

    @FXML
    private ListView<AlxDiet> listPost;

    @FXML
    private ListView<AlxDiet> listPre;

    @FXML
    private ListView<AlxDiet> listCena;

    @FXML
    private ListView<AlxDiet> listAlmuerzo;

    @FXML
    private ListView<AlxDiet> listDesayuno;

    private String activatedDay;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboObjetivos.setItems(objetivos);
        comboObjetivos.getSelectionModel().select(0);
        obtener();
        comboSexo.setItems(sexos);
        comboSexo.getSelectionModel().select(0);
        comboAlimentos.setItems(alimentos);
        comboAlimentos.getSelectionModel().select(0);
        updated();
    }

    public void setFiltro() {
        int i = filtro.getSelectedToggle().toString().indexOf("'");
        setFiltro(filtro.getSelectedToggle().toString().substring(i));
    }

    @Override
    public void updated() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Runnable updater = new Runnable() {
                    @Override
                    public void run() {
                        if (isMedidaUpdated()) {
                            setKCalCliente();
                            porcentajeCarbos();
                            porcentajeGrasas();
                            porcentajeProteinas();
                        }
                        if (isAlimentosUpdated()) {
                            comboAlimentos.setItems(alimentos);
                            comboAlimentos.getSelectionModel().select(0);
                        }
                    }
                };
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                    }
                    // UI update is run on the Application thread
                    Platform.runLater(updater);
                }
            }
        });
        t.setDaemon(true);
        t.start();
    }

    @Override
    public Plan captar() throws DAOException {
        Plan d = new Plan();
        d.setTipo("DIETA");
        d.setNombre(textNombre.getText());
        d.setObjetivo(comboObjetivos.getSelectionModel().getSelectedItem());
        d.setDescripcion(textDescripcion.getText());
        if (textEdad.getText().isEmpty()) {
            d.setEdad(0);
        } else {
            d.setEdad(Integer.parseInt(textEdad.getText()));
        }
        d.setSexo(comboSexo.getSelectionModel().getSelectedItem());
        return d;
    }

    @Override
    public void obtener() {
        try {
            comboDieta.getItems().clear();
            if (textBuscar.getText().isEmpty()) {
                dietas = getDietas().obtenerTodos();
            } else {
                dietas = getDietas().obtenerTodos(textBuscar.getText());
            }
            if (!dietas.isEmpty()) {
                comboDieta.setItems(dietas);
                select(0);
            }
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    @Override
    public void registrar() {
        try {
            Plan m = captar();
            if (m != null) {
                if (m.isEmpty()) {
                    mensaje("Aún faltan algunos datos en el plan de alimentación", "aviso");
                } else {
                    getDietas().insertar(m);
                    obtener();
                    mensaje("Plan de alimentación registrado", "exito");
                }
            }
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    @Override
    public void modificar() {
        try {
            if (!comboDieta.getItems().isEmpty()) {
                Plan m = captar();
                if (m != null) {
                    m.setPlankey(comboDieta.getSelectionModel().getSelectedItem().getPlankey());
                    if (m.isEmpty()) {
                        mensaje("Aún faltan algunos datos en el plan de alimentación", "aviso");
                    } else {
                        getDietas().modificar(m);
                        textBuscar.setText(textNombre.getText());
                        obtener();
                        mensaje("Plan de alimentación modificado", "exito");
                    }
                }
            } else {
                mensaje("No hay planes de alimentación que modificar", "aviso");
            }
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    @Override
    public void eliminar() {
        try {
            if (!comboDieta.getItems().isEmpty()) {
                getDietas().eliminar(comboDieta.getSelectionModel().getSelectedItem());
                obtener();
                mensaje("Plan de alimentación eliminado", "exito");
            } else {
                mensaje("No hay planes de alimentación que eliminar", "aviso");
            }
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    @Override
    public void limpiar() {
        textBuscar.setText("");
        textNombre.setText("");
        textDescripcion.setText("");
        textBuscar.setText("");
        textCantidad.setText("");
        labelGrams.setText("");
        labelKcal.setText("");
        labelCarbs.setText("");
        labelProtein.setText("");
        labelFat.setText("");
        listDesayuno.getItems().clear();
        gramosDesayuno.setText("");
        kcalDesayuno.setText("");
        carbDesayuno.setText("");
        proteDesayuno.setText("");
        fatDesayuno.setText("");
        textDesayuno.setText("");
        listAlmuerzo.getItems().clear();
        gramosAlmuerzo.setText("");
        kcalAlmuerzo.setText("");
        carbAlmuerzo.setText("");
        proteAlmuerzo.setText("");
        fatAlmuerzo.setText("");
        textAlmuerzo.setText("");
        listCena.getItems().clear();
        gramosCena.setText("");
        kcalCena.setText("");
        carbCena.setText("");
        proteCena.setText("");
        fatCena.setText("");
        textCena.setText("");
        listPre.getItems().clear();
        gramosPre.setText("");
        kcalPre.setText("");
        carbPre.setText("");
        protePre.setText("");
        fatPre.setText("");
        textPre.setText("");
        listPost.getItems().clear();
        gramosPost.setText("");
        kcalPost.setText("");
        carbPost.setText("");
        protePost.setText("");
        fatPost.setText("");
        textPost.setText("");
        listMerienda.getItems().clear();
        gramosMerienda.setText("");
        kcalMerienda.setText("");
        carbMerienda.setText("");
        proteMerienda.setText("");
        fatMerienda.setText("");
        textMerienda.setText("");
        listExtra.getItems().clear();
        gramosExtra.setText("");
        kcalExtra.setText("");
        carbExtra.setText("");
        proteExtra.setText("");
        fatExtra.setText("");
        textExtra.setText("");
    }

    @Override
    public void mostrar() {
        if (!comboDieta.getItems().isEmpty()) {
            Plan d = comboDieta.getSelectionModel().getSelectedItem();
            textNombre.setText(d.getNombre());
            selectCombo(comboObjetivos, d.getObjetivo());
            textDescripcion.setText(d.getDescripcion());
            textEdad.setText(d.getEdad() + "");
            selectCombo(comboSexo, comboSexo.getSelectionModel().getSelectedItem());
        }
    }

    public void select(int i) {
        if (!comboDieta.getItems().isEmpty()) {
            comboDieta.getSelectionModel().select(i);
            mostrar();
        }
    }

    public void datosDieta() {
        double g = 0;
        double p = 0;
        double c = 0;
        double f = 0;
        double k = 0;

        for (AlxDiet item : listDesayuno.getItems()) {
            g += item.getCantidad();
            c += item.getCarbohidratosxpeso();
            p += item.getProteinasxpeso();
            f += item.getGrasasxpeso();
            k += item.getKilocaloriasxpeso();
        }
        for (AlxDiet item : listAlmuerzo.getItems()) {
            g += item.getCantidad();
            c += item.getCarbohidratosxpeso();
            p += item.getProteinasxpeso();
            f += item.getGrasasxpeso();
            k += item.getKilocaloriasxpeso();
        }
        for (AlxDiet item : listCena.getItems()) {
            g += item.getCantidad();
            c += item.getCarbohidratosxpeso();
            p += item.getProteinasxpeso();
            f += item.getGrasasxpeso();
            k += item.getKilocaloriasxpeso();
        }
        for (AlxDiet item : listPre.getItems()) {
            g += item.getCantidad();
            c += item.getCarbohidratosxpeso();
            p += item.getProteinasxpeso();
            f += item.getGrasasxpeso();
            k += item.getKilocaloriasxpeso();
        }
        for (AlxDiet item : listPost.getItems()) {
            g += item.getCantidad();
            c += item.getCarbohidratosxpeso();
            p += item.getProteinasxpeso();
            f += item.getGrasasxpeso();
            k += item.getKilocaloriasxpeso();
        }
        for (AlxDiet item : listMerienda.getItems()) {
            g += item.getCantidad();
            c += item.getCarbohidratosxpeso();
            p += item.getProteinasxpeso();
            f += item.getGrasasxpeso();
            k += item.getKilocaloriasxpeso();
        }
        for (AlxDiet item : listExtra.getItems()) {
            g += item.getCantidad();
            c += item.getCarbohidratosxpeso();
            p += item.getProteinasxpeso();
            f += item.getGrasasxpeso();
            k += item.getKilocaloriasxpeso();
        }
        labelKcal.setText("" + k);
        labelProtein.setText("" + p);
        labelCarbs.setText("" + c);
        labelFat.setText("" + f);
        labelGrams.setText("" + g);

    }

    public void setKCalCliente() {
        if (!getMedida().isEmpty()) {
            labelKcalCliente.setText("" + getMedida().getSuperavitODeficit());
        }
    }

    public void porcentajeProteinas() {
        if (!labelKcalCliente.getText().isEmpty()) {
            double cal = Double.parseDouble(labelKcalCliente.getText());
            double porc;
            if (!textProteinas.getText().isEmpty()) {
                porc = Double.parseDouble(textProteinas.getText()) / 100;
                labelProteinCliente.setText("" + Operacion.redondear(cal * porc / 4));
                textProteinas.setText("" + Operacion.redondear(cal * porc / 4));
            }
        }
        porcentajeGramos();
    }

    public void porcentajeGrasas() {
        if (!labelKcalCliente.getText().isEmpty()) {
            double cal = Double.parseDouble(labelKcalCliente.getText());
            double porc;
            if (!textGrasas.getText().isEmpty()) {
                porc = Double.parseDouble(textGrasas.getText()) / 100;
                labelFatCliente.setText("" + Operacion.redondear(cal * porc / 9));
                textGrasas.setText("" + Operacion.redondear(cal * porc / 9));
            }
        }
        porcentajeGramos();
    }

    public void porcentajeCarbos() {
        if (!labelKcalCliente.getText().isEmpty()) {
            double cal = Double.parseDouble(labelKcalCliente.getText());
            double porc;
            if (!textCarbohidratos.getText().isEmpty()) {
                porc = Double.parseDouble(textCarbohidratos.getText()) / 100;
                labelCarbsCliente.setText("" + Operacion.redondear(cal * porc / 4));
                textCarbohidratos.setText("" + Operacion.redondear(cal * porc / 4));
            }
        }
        porcentajeGramos();
    }

    public void porcentajeGramos() {
        if (!(labelProteinCliente.getText().isEmpty() && labelCarbsCliente.getText().isEmpty() && labelFat.getText().isEmpty())) {
            double grams = Double.parseDouble(labelFatCliente.getText()) + Double.parseDouble(labelCarbsCliente.getText()) + Double.parseDouble(labelProteinCliente.getText());
            labelGramsCliente.setText(Operacion.redondear(grams) + "");
        }
    }

    public AlxDiet getAlxDiet() throws DAOException {
        if (!comboDieta.getItems().isEmpty()) {
            AlxDiet a = new AlxDiet();
            a.setPlan(getDieta());
            a.setAlimento(comboAlimentos.getSelectionModel().getSelectedItem());
            if (textCantidad.getText().isEmpty()) {
                a.setCantidad(0);
            } else {
                a.setCantidad(Double.parseDouble(textCantidad.getText()));
            }
            a.setDia(activatedDay);
            return a;
        }
        return null;
    }

    public Plan getDieta() {
        return comboDieta.getSelectionModel().getSelectedItem();
    }

    public void getDomingo() {
        try {
            activatedDay = AlxDiet.DOMINGO;
            listDesayuno.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.DOMINGO, AlxDiet.DESAYUNO));
            listAlmuerzo.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.DOMINGO, AlxDiet.ALMUERZO));
            listCena.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.DOMINGO, AlxDiet.CENA));
            listPre.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.DOMINGO, AlxDiet.PREENTRENO));
            listPost.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.DOMINGO, AlxDiet.POSTENTRENO));
            listMerienda.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.DOMINGO, AlxDiet.MERIENDA));
            listExtra.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.DOMINGO, AlxDiet.EXTRA));
            datosDieta();
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    public void getLunes() {
        try {
            activatedDay = AlxDiet.LUNES;
            listDesayuno.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.LUNES, AlxDiet.DESAYUNO));
            listAlmuerzo.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.LUNES, AlxDiet.ALMUERZO));
            listCena.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.LUNES, AlxDiet.CENA));
            listPre.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.LUNES, AlxDiet.PREENTRENO));
            listPost.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.LUNES, AlxDiet.POSTENTRENO));
            listMerienda.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.LUNES, AlxDiet.MERIENDA));
            listExtra.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.LUNES, AlxDiet.EXTRA));
            datosDieta();
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    public void getMartes() {
        try {
            activatedDay = AlxDiet.MARTES;
            listDesayuno.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.MARTES, AlxDiet.DESAYUNO));
            listAlmuerzo.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.MARTES, AlxDiet.ALMUERZO));
            listCena.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.MARTES, AlxDiet.CENA));
            listPre.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.MARTES, AlxDiet.PREENTRENO));
            listPost.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.MARTES, AlxDiet.POSTENTRENO));
            listMerienda.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.MARTES, AlxDiet.MERIENDA));
            listExtra.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.MARTES, AlxDiet.EXTRA));
            datosDieta();
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    public void getMiercoles() {
        try {
            activatedDay = AlxDiet.MIERCOLES;
            listDesayuno.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.MIERCOLES, AlxDiet.DESAYUNO));
            listAlmuerzo.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.MIERCOLES, AlxDiet.ALMUERZO));
            listCena.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.MIERCOLES, AlxDiet.CENA));
            listPre.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.MIERCOLES, AlxDiet.PREENTRENO));
            listPost.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.MIERCOLES, AlxDiet.POSTENTRENO));
            listMerienda.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.MIERCOLES, AlxDiet.MERIENDA));
            listExtra.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.MIERCOLES, AlxDiet.EXTRA));
            datosDieta();
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    public void getJueves() {
        try {
            activatedDay = AlxDiet.JUEVES;
            listDesayuno.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.JUEVES, AlxDiet.DESAYUNO));
            listAlmuerzo.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.JUEVES, AlxDiet.ALMUERZO));
            listCena.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.JUEVES, AlxDiet.CENA));
            listPre.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.JUEVES, AlxDiet.PREENTRENO));
            listPost.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.JUEVES, AlxDiet.POSTENTRENO));
            listMerienda.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.JUEVES, AlxDiet.MERIENDA));
            listExtra.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.JUEVES, AlxDiet.EXTRA));
            datosDieta();
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    public void getViernes() {
        try {
            activatedDay = AlxDiet.VIERNES;
            listDesayuno.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.VIERNES, AlxDiet.DESAYUNO));
            listAlmuerzo.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.VIERNES, AlxDiet.ALMUERZO));
            listCena.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.VIERNES, AlxDiet.CENA));
            listPre.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.VIERNES, AlxDiet.PREENTRENO));
            listPost.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.VIERNES, AlxDiet.POSTENTRENO));
            listMerienda.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.VIERNES, AlxDiet.MERIENDA));
            listExtra.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.VIERNES, AlxDiet.EXTRA));
            datosDieta();
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    public void getSabado() {
        try {
            activatedDay = AlxDiet.SABADO;
            listDesayuno.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.SABADO, AlxDiet.DESAYUNO));
            listAlmuerzo.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.SABADO, AlxDiet.ALMUERZO));
            listCena.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.SABADO, AlxDiet.CENA));
            listPre.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.SABADO, AlxDiet.PREENTRENO));
            listPost.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.SABADO, AlxDiet.POSTENTRENO));
            listMerienda.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.SABADO, AlxDiet.MERIENDA));
            listExtra.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.SABADO, AlxDiet.EXTRA));
            datosDieta();
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    public void addDesayuno() {
        try {
            AlxDiet a = getAlxDiet();
            if (a != null) {
                a.setMomento(AlxDiet.DESAYUNO);
                getAlxdiets().insertar(a);
                listDesayuno.getItems().add(a);
                actualizarUso(a.getAlimento());
                datosDieta();
            }
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    @FXML
    void deleteDesayuno(ActionEvent event) {
        if (listDesayuno.getSelectionModel().getSelectedIndex() != -1) {
            try {
                getAlxdiets().eliminar(listDesayuno.getSelectionModel().getSelectedItem());
            } catch (DAOException ex) {
                excepcion(ex);
            }
            listDesayuno.getItems().remove(listDesayuno.getSelectionModel().getSelectedItem());
            datosDieta();
        } else {
            mensaje("Seleccione un alimento", "aviso");

        }
    }

    public void addAlmuerzo() {
        try {
            AlxDiet a = getAlxDiet();
            if (a != null) {
                a.setMomento(AlxDiet.ALMUERZO);
                getAlxdiets().insertar(a);
                listAlmuerzo.getItems().add(a);
                actualizarUso(a.getAlimento());
                datosDieta();
            }
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    @FXML
    void deleteAlmuerzo(ActionEvent event) {
        if (listAlmuerzo.getSelectionModel().getSelectedIndex() != -1) {
            try {
                getAlxdiets().eliminar(listAlmuerzo.getSelectionModel().getSelectedItem());
            } catch (DAOException ex) {
                excepcion(ex);
            }
            listAlmuerzo.getItems().remove(listAlmuerzo.getSelectionModel().getSelectedItem());
            datosDieta();
        } else {
            mensaje("Seleccione un alimento", "aviso");

        }
    }

    public void addCena() {
        try {
            AlxDiet a = getAlxDiet();
            if (a != null) {
                a.setMomento(AlxDiet.CENA);
                getAlxdiets().insertar(a);
                listCena.getItems().add(a);
                actualizarUso(a.getAlimento());
                datosDieta();
            }
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    @FXML
    void deleteCena(ActionEvent event) {
        if (listCena.getSelectionModel().getSelectedIndex() != -1) {
            try {
                getAlxdiets().eliminar(listCena.getSelectionModel().getSelectedItem());
            } catch (DAOException ex) {
                excepcion(ex);
            }
            listCena.getItems().remove(listCena.getSelectionModel().getSelectedItem());
            datosDieta();
        } else {
            mensaje("Seleccione un alimento", "aviso");

        }
    }

    public void addExtra() {
        try {
            AlxDiet a = getAlxDiet();
            if (a != null) {
                a.setMomento(AlxDiet.EXTRA);
                getAlxdiets().insertar(a);
                listExtra.getItems().add(a);
                actualizarUso(a.getAlimento());
                datosDieta();
            }
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    @FXML
    void deleteExtra(ActionEvent event) {
        if (listExtra.getSelectionModel().getSelectedIndex() != -1) {
            try {
                getAlxdiets().eliminar(listExtra.getSelectionModel().getSelectedItem());
            } catch (DAOException ex) {
                excepcion(ex);
            }
            listExtra.getItems().remove(listExtra.getSelectionModel().getSelectedItem());
            datosDieta();
        } else {
            mensaje("Seleccione un alimento", "aviso");

        }
    }

    public void addMerienda() {
        try {
            AlxDiet a = getAlxDiet();
            if (a != null) {
                a.setMomento(AlxDiet.MERIENDA);
                getAlxdiets().insertar(a);
                listMerienda.getItems().add(a);
                actualizarUso(a.getAlimento());
                datosDieta();
            }
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    @FXML
    void deleteMerienda(ActionEvent event) {
        if (listMerienda.getSelectionModel().getSelectedIndex() != -1) {
            try {
                getAlxdiets().eliminar(listMerienda.getSelectionModel().getSelectedItem());
            } catch (DAOException ex) {
                excepcion(ex);
            }
            listMerienda.getItems().remove(listMerienda.getSelectionModel().getSelectedItem());
            datosDieta();
        } else {
            mensaje("Seleccione un alimento", "aviso");

        }
    }

    public void addPost() {
        try {
            AlxDiet a = getAlxDiet();
            if (a != null) {
                a.setMomento(AlxDiet.POSTENTRENO);
                getAlxdiets().insertar(a);
                listPost.getItems().add(a);
                actualizarUso(a.getAlimento());
                datosDieta();
            }
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    @FXML
    void deletePost(ActionEvent event) {
        if (listPost.getSelectionModel().getSelectedIndex() != -1) {
            try {
                getAlxdiets().eliminar(listPost.getSelectionModel().getSelectedItem());
            } catch (DAOException ex) {
                excepcion(ex);
            }
            listPost.getItems().remove(listPost.getSelectionModel().getSelectedItem());
            datosDieta();
        } else {
            mensaje("Seleccione un alimento", "aviso");

        }
    }

    public void addPre() {
        try {
            AlxDiet a = getAlxDiet();
            if (a != null) {
                a.setMomento(AlxDiet.PREENTRENO);
                getAlxdiets().insertar(a);
                listPre.getItems().add(a);
                actualizarUso(a.getAlimento());
                datosDieta();
            }
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    @FXML
    void deletePre(ActionEvent event) {
        if (listPre.getSelectionModel().getSelectedIndex() != -1) {
            try {
                getAlxdiets().eliminar(listPre.getSelectionModel().getSelectedItem());
            } catch (DAOException ex) {
                excepcion(ex);
            }
            listPre.getItems().remove(listPre.getSelectionModel().getSelectedItem());
            datosDieta();
        } else {
            mensaje("Seleccione un alimento", "aviso");

        }
    }

    public void actualizarUso(Alimento a) {
        try {
            getAlimentos().modificar(a);
            setAlimentosUpdated(true);
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }
}
