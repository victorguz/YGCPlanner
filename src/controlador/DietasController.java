/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import DAO.DAOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyEvent;
import modelo.cliente.Medida;
import modelo.plan.AlxDiet;
import modelo.plan.Plan;

/**
 *
 * @author 201621279487
 */
public class DietasController extends Controller<Plan> {

    @FXML
    private TextField textBuscarDieta;

    @FXML
    private ComboBox<Plan> comboDieta;

    @FXML
    private TextField textNombre;

    @FXML
    private TextArea textDescripcion;

    @FXML
    private TextField textCantidad;

    @FXML
    private Label tGramsCliente;

    @FXML
    private TextField textProteinas;

    @FXML
    private TextField textCarbohidratos;

    @FXML
    private TextField textGrasas;
    @FXML
    private Label tKcalCliente;

    @FXML
    private Label tCarbsCliente;

    @FXML
    private Label tProteinCliente;

    @FXML
    private Label tFatCliente;

    @FXML
    private Label tGrams;

    @FXML
    private Label tKcal;

    @FXML
    private Label tCarbs;

    @FXML
    private Label tProtein;

    @FXML
    private Label tFat;

    @FXML
    private ListView<AlxDiet> listDesayuno;

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
    private ListView<AlxDiet> listAlmuerzo;

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
    private ListView<AlxDiet> listCena;

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
    private ListView<AlxDiet> listPre;

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
    private ListView<AlxDiet> listPost;

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
    private ListView<AlxDiet> listMerienda;

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
    private ListView<AlxDiet> listExtra;

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
    private ToggleButton buttonDomingo;

    @FXML
    private ToggleButton buttonLunes;

    @FXML
    private ToggleButton buttonMartes;

    @FXML
    private ToggleButton buttonMiercoles;

    @FXML
    private ToggleButton buttonJueves;

    @FXML
    private ToggleButton buttonViernes;

    @FXML
    private ToggleButton buttonSabado;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        obtenerObjetivos();
        obtenerAlimentos();
        comboAlimentos.getSelectionModel().select(0);
        obtener();
        comboSexo.setItems(sexos);
        comboSexo.getSelectionModel().select(0);
        updated();
    }

    @Override
    public void updated() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Runnable updater = new Runnable() {
                    @Override
                    public void run() {
                        datosDieta();
                    }
                };
                while (true) {
                    try {
                        Thread.sleep(1);
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
        d.setObjetivo(comboObjetivo.getSelectionModel().getSelectedItem().getObjetivo());
        d.setDescripcion(textDescripcion.getText());
        d.setEdad(Integer.parseInt(textEdad.getText()));
        d.setSexo(comboSexo.getSelectionModel().getSelectedItem());
        return d;
    }

    @Override
    public void obtener() {
        try {
            dietas = getDietas().obtenerTodos();
            comboDieta.getItems().clear();
            if (!dietas.isEmpty()) {
                comboDieta.setItems(dietas);
                select(0);
            }
        } catch (DAOException ex) {
            mensaje("Condición", "error", ex);
        }
    }

    @Override
    public void registrar() {
        try {
            Plan m = captar();
            if (m != null) {
                if (m.isEmpty()) {
                    mensaje("Aún faltan algunos datos en el plan de alimentación", "aviso", null);
                } else {
                    getDietas().insertar(m);
                    obtener();
                    mensaje("Plan de alimentación registrado", "exito", null);
                }
            }
        } catch (DAOException ex) {
            mensaje("Condición", "error", ex);
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
                        mensaje("Aún faltan algunos datos en el plan de alimentación", "aviso", null);
                    } else {
                        getDietas().modificar(m);
                        mensaje("Plan de alimentación actualizado", "exito", null);
                        obtener();
                    }
                }
            } else {
                mensaje("No hay planes de alimentación que modificar", "aviso", null);
            }
        } catch (DAOException ex) {
            mensaje("Condición", "error", ex);
        }
    }

    @Override
    public void eliminar() {
        try {
            if (!comboDieta.getItems().isEmpty()) {
                getDietas().eliminar(comboDieta.getSelectionModel().getSelectedItem());
                obtener();
                mensaje("Plan de alimentación eliminado", "exito", null);
            } else {
                mensaje("No hay planes de alimentación que eliminar", "aviso", null);
            }
        } catch (DAOException ex) {
            mensaje("Condición", "error", ex);
        }
    }

    @Override
    public void buscar() {
        if (textBuscarDieta.getText().isEmpty()) {
            obtener();
        } else {
            try {
                dietas = getDietas().obtenerTodos(textBuscarDieta.getText());
                comboDieta.getItems().clear();
                if (dietas.isEmpty()) {
                    mensaje("No se encontraron planes de alimentación", "aviso", null);
                } else {
                    comboDieta.setItems(dietas);
                    select(0);
                    mensaje("Se encontraron " + dietas.size() + " planes de alimentación", "exito", null);
                }
            } catch (DAOException ex) {
                mensaje("Condición", "error", ex);
            }
        }
    }

    @Override
    public void limpiar() {
        textBuscarDieta.setText("");
        textNombre.setText("");
        textDescripcion.setText("");
        textBuscar.setText("");
        textCantidad.setText("");
        tGrams.setText("");
        tKcal.setText("");
        tCarbs.setText("");
        tProtein.setText("");
        tFat.setText("");
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
            selectObjetivo(d.getObjetivo());
            textDescripcion.setText(d.getDescripcion());
            textEdad.setText(d.getEdad() + "");
            selectSexo(comboSexo.getSelectionModel().getSelectedItem());
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
        if (listDesayuno.getSelectionModel().getSelectedIndex() != -1) {
            carbDesayuno.setText("Carbs:\n" + listDesayuno.getSelectionModel().getSelectedItem().getCarbohidratosxpeso());
            proteDesayuno.setText("Protein:\n" + listDesayuno.getSelectionModel().getSelectedItem().getProteinasxpeso());
            fatDesayuno.setText("Fat:\n" + listDesayuno.getSelectionModel().getSelectedItem().getGrasasxpeso());
            kcalDesayuno.setText("KCal:\n" + listDesayuno.getSelectionModel().getSelectedItem().getKilocaloriasxpeso());
            gramosDesayuno.setText("Grams:\n" + listDesayuno.getSelectionModel().getSelectedItem().getPeso());
        } else {
            carbDesayuno.setText("Carbs:\n----");
            proteDesayuno.setText("Protein:\n----");
            fatDesayuno.setText("Fat:\n----");
            kcalDesayuno.setText("KCal:\n----");
            gramosDesayuno.setText("Grams:\n----");
        }
        if (listAlmuerzo.getSelectionModel().getSelectedIndex() != -1) {
            carbAlmuerzo.setText("Carbs:\n" + listAlmuerzo.getSelectionModel().getSelectedItem().getCarbohidratosxpeso());
            proteAlmuerzo.setText("Protein:\n" + listAlmuerzo.getSelectionModel().getSelectedItem().getProteinasxpeso());
            fatAlmuerzo.setText("Fat:\n" + listAlmuerzo.getSelectionModel().getSelectedItem().getGrasasxpeso());
            kcalAlmuerzo.setText("KCal:\n" + listAlmuerzo.getSelectionModel().getSelectedItem().getKilocaloriasxpeso());
            gramosAlmuerzo.setText("Grams:\n" + listAlmuerzo.getSelectionModel().getSelectedItem().getPeso());
        } else {
            carbAlmuerzo.setText("Carbs:\n----");
            proteAlmuerzo.setText("Protein:\n----");
            fatAlmuerzo.setText("Fat:\n----");
            kcalAlmuerzo.setText("KCal:\n----");
            gramosAlmuerzo.setText("Grams:\n----");
        }
        if (listCena.getSelectionModel().getSelectedIndex() != -1) {
            carbCena.setText("Carbs:\n" + listCena.getSelectionModel().getSelectedItem().getCarbohidratosxpeso());
            proteCena.setText("Protein:\n" + listCena.getSelectionModel().getSelectedItem().getProteinasxpeso());
            fatCena.setText("Fat:\n" + listCena.getSelectionModel().getSelectedItem().getGrasasxpeso());
            kcalCena.setText("KCal:\n" + listCena.getSelectionModel().getSelectedItem().getKilocaloriasxpeso());
            gramosCena.setText("Grams:\n" + listCena.getSelectionModel().getSelectedItem().getPeso());
        } else {
            carbCena.setText("Carbs:\n----");
            proteCena.setText("Protein:\n----");
            fatCena.setText("Fat:\n----");
            kcalCena.setText("KCal:\n----");
            gramosCena.setText("Grams:\n----");
        }
        if (listPre.getSelectionModel().getSelectedIndex() != -1) {
            carbPre.setText("Carbs:\n" + listPre.getSelectionModel().getSelectedItem().getCarbohidratosxpeso());
            protePre.setText("Protein:\n" + listPre.getSelectionModel().getSelectedItem().getProteinasxpeso());
            fatPre.setText("Fat:\n" + listPre.getSelectionModel().getSelectedItem().getGrasasxpeso());
            kcalPre.setText("KCal:\n" + listPre.getSelectionModel().getSelectedItem().getKilocaloriasxpeso());
            gramosPre.setText("Grams:\n" + listPre.getSelectionModel().getSelectedItem().getPeso());
        } else {
            carbPre.setText("Carbs:\n----");
            protePre.setText("Protein:\n----");
            fatPre.setText("Fat:\n----");
            kcalPre.setText("KCal:\n----");
            gramosPre.setText("Grams:\n----");
        }
        if (listPost.getSelectionModel().getSelectedIndex() != -1) {
            carbPost.setText("Carbs:\n" + listPost.getSelectionModel().getSelectedItem().getCarbohidratosxpeso());
            protePost.setText("Protein:\n" + listPost.getSelectionModel().getSelectedItem().getProteinasxpeso());
            fatPost.setText("Fat:\n" + listPost.getSelectionModel().getSelectedItem().getGrasasxpeso());
            kcalPost.setText("KCal:\n" + listPost.getSelectionModel().getSelectedItem().getKilocaloriasxpeso());
            gramosPost.setText("Grams:\n" + listPost.getSelectionModel().getSelectedItem().getPeso());
        } else {
            carbPost.setText("Carbs:\n----");
            protePost.setText("Protein:\n----");
            fatPost.setText("Fat:\n----");
            kcalPost.setText("KCal:\n----");
            gramosPost.setText("Grams:\n----");
        }
        if (listMerienda.getSelectionModel().getSelectedIndex() != -1) {
            carbMerienda.setText("Carbs:\n" + listMerienda.getSelectionModel().getSelectedItem().getCarbohidratosxpeso());
            proteMerienda.setText("Protein:\n" + listMerienda.getSelectionModel().getSelectedItem().getProteinasxpeso());
            fatMerienda.setText("Fat:\n" + listMerienda.getSelectionModel().getSelectedItem().getGrasasxpeso());
            kcalMerienda.setText("KCal:\n" + listMerienda.getSelectionModel().getSelectedItem().getKilocaloriasxpeso());
            gramosMerienda.setText("Grams:\n" + listMerienda.getSelectionModel().getSelectedItem().getPeso());
        } else {
            carbMerienda.setText("Carbs:\n----");
            proteMerienda.setText("Protein:\n----");
            fatMerienda.setText("Fat:\n----");
            kcalMerienda.setText("KCal:\n----");
            gramosMerienda.setText("Grams:\n----");
        }
        if (listExtra.getSelectionModel().getSelectedIndex() != -1) {
            carbExtra.setText("Carbs:\n" + listExtra.getSelectionModel().getSelectedItem().getCarbohidratosxpeso());
            proteExtra.setText("Protein:\n" + listExtra.getSelectionModel().getSelectedItem().getProteinasxpeso());
            fatExtra.setText("Fat:\n" + listExtra.getSelectionModel().getSelectedItem().getGrasasxpeso());
            kcalExtra.setText("KCal:\n" + listExtra.getSelectionModel().getSelectedItem().getKilocaloriasxpeso());
            gramosExtra.setText("Grams:\n" + listExtra.getSelectionModel().getSelectedItem().getPeso());
        } else {
            carbExtra.setText("Carbs:\n----");
            proteExtra.setText("Protein:\n----");
            fatExtra.setText("Fat:\n----");
            kcalExtra.setText("KCal:\n----");
            gramosExtra.setText("Grams:\n----");
        }
        for (AlxDiet item : listDesayuno.getItems()) {
            g += item.getPeso();
            c += item.getCarbohidratosxpeso();
            p += item.getProteinasxpeso();
            f += item.getGrasasxpeso();
            k += item.getKilocaloriasxpeso();
        }
        for (AlxDiet item : listAlmuerzo.getItems()) {
            g += item.getPeso();
            c += item.getCarbohidratosxpeso();
            p += item.getProteinasxpeso();
            f += item.getGrasasxpeso();
            k += item.getKilocaloriasxpeso();
        }
        for (AlxDiet item : listCena.getItems()) {
            g += item.getPeso();
            c += item.getCarbohidratosxpeso();
            p += item.getProteinasxpeso();
            f += item.getGrasasxpeso();
            k += item.getKilocaloriasxpeso();
        }
        for (AlxDiet item : listPre.getItems()) {
            g += item.getPeso();
            c += item.getCarbohidratosxpeso();
            p += item.getProteinasxpeso();
            f += item.getGrasasxpeso();
            k += item.getKilocaloriasxpeso();
        }
        for (AlxDiet item : listPost.getItems()) {
            g += item.getPeso();
            c += item.getCarbohidratosxpeso();
            p += item.getProteinasxpeso();
            f += item.getGrasasxpeso();
            k += item.getKilocaloriasxpeso();
        }
        for (AlxDiet item : listMerienda.getItems()) {
            g += item.getPeso();
            c += item.getCarbohidratosxpeso();
            p += item.getProteinasxpeso();
            f += item.getGrasasxpeso();
            k += item.getKilocaloriasxpeso();
        }
        for (AlxDiet item : listExtra.getItems()) {
            g += item.getPeso();
            c += item.getCarbohidratosxpeso();
            p += item.getProteinasxpeso();
            f += item.getGrasasxpeso();
            k += item.getKilocaloriasxpeso();
        }
        tKcal.setText("" + k);
        tProtein.setText("" + p);
        tCarbs.setText("" + c);
        tFat.setText("" + f);
        tGrams.setText("" + g);

    }

    public void macroPorcentajes() {
        if (!getMedida().isEmpty()) {
            double cal = getMedida().getSuperavitODeficit();
            tKcalCliente.setText("" + cal);
            double porc;
            if (textProteinas.getText().isEmpty()) {
                if (!textProteinas.isFocused()) {
                    textProteinas.setText("40");
                    //   tProteinCliente.setText("" + Medida.redondear(cal * 0.4 / 4));
                }
            } else {
                porc = Double.parseDouble(textProteinas.getText()) / 100;
                tProteinCliente.setText("" + Medida.redondear(cal * porc / 4));
            }
            if (textCarbohidratos.getText().isEmpty()) {
                if (!textCarbohidratos.isFocused()) {
                    textCarbohidratos.setText("30");
                    //   tCarbsCliente.setText("" + Medida.redondear(cal * 0.3 / 4));
                }
            } else {
                porc = Double.parseDouble(textCarbohidratos.getText()) / 100;
                tCarbsCliente.setText("" + Medida.redondear(cal * porc / 4));
            }
            if (textGrasas.getText().isEmpty()) {
                if (!textGrasas.isFocused()) {
                    textGrasas.setText("30");
                    //    tFatCliente.setText("" + Medida.redondear(cal * 0.3 / 4));
                }
            } else {
                porc = Double.parseDouble(textGrasas.getText()) / 100;
                tFatCliente.setText("" + Medida.redondear(cal * porc / 4));
            }
            double grams = Double.parseDouble(tFatCliente.getText()) + Double.parseDouble(tCarbsCliente.getText()) + Double.parseDouble(tProteinCliente.getText());
            tGramsCliente.setText(Medida.redondear(grams) + "");
        }
    }

    public AlxDiet getAlxDiet() throws DAOException {
        if (!comboDieta.getItems().isEmpty()) {
            AlxDiet a = new AlxDiet();
            a.setPlan(getDieta());
            a.setAlimento(comboAlimentos.getSelectionModel().getSelectedItem());
            if (textCantidad.getText().isEmpty()) {
                throw new DAOException("Digite una cantidad");
            } else {
                a.setPeso(Double.parseDouble(textCantidad.getText()));
            }
            if (buttonDomingo.isSelected()) {
                a.setDia(AlxDiet.DOMINGO);
            } else if (buttonLunes.isSelected()) {
                a.setDia(AlxDiet.LUNES);
            } else if (buttonMartes.isSelected()) {
                a.setDia(AlxDiet.MARTES);
            } else if (buttonMiercoles.isSelected()) {
                a.setDia(AlxDiet.MIERCOLES);
            } else if (buttonJueves.isSelected()) {
                a.setDia(AlxDiet.JUEVES);
            } else if (buttonViernes.isSelected()) {
                a.setDia(AlxDiet.VIERNES);
            } else if (buttonSabado.isSelected()) {
                a.setDia(AlxDiet.SABADO);
            } else {
                throw new DAOException("Seleccione un día de la semana");
            }
            return a;
        }
        return null;
    }

    public Plan getDieta() {
        return comboDieta.getSelectionModel().getSelectedItem();
    }

    public void getDomingo() {
        try {
            listDesayuno.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.DOMINGO, AlxDiet.DESAYUNO));
            listAlmuerzo.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.DOMINGO, AlxDiet.ALMUERZO));
            listCena.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.DOMINGO, AlxDiet.CENA));
            listPre.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.DOMINGO, AlxDiet.PREENTRENO));
            listPost.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.DOMINGO, AlxDiet.POSTENTRENO));
            listMerienda.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.DOMINGO, AlxDiet.MERIENDA));
            listExtra.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.DOMINGO, AlxDiet.EXTRA));
        } catch (DAOException ex) {
            Logger.getLogger(DietasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getLunes() {
        try {
            listDesayuno.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.LUNES, AlxDiet.DESAYUNO));
            listAlmuerzo.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.LUNES, AlxDiet.ALMUERZO));
            listCena.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.LUNES, AlxDiet.CENA));
            listPre.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.LUNES, AlxDiet.PREENTRENO));
            listPost.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.LUNES, AlxDiet.POSTENTRENO));
            listMerienda.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.LUNES, AlxDiet.MERIENDA));
            listExtra.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.LUNES, AlxDiet.EXTRA));
        } catch (DAOException ex) {
            Logger.getLogger(DietasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getMartes() {
        try {
            listDesayuno.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.MARTES, AlxDiet.DESAYUNO));
            listAlmuerzo.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.MARTES, AlxDiet.ALMUERZO));
            listCena.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.MARTES, AlxDiet.CENA));
            listPre.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.MARTES, AlxDiet.PREENTRENO));
            listPost.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.MARTES, AlxDiet.POSTENTRENO));
            listMerienda.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.MARTES, AlxDiet.MERIENDA));
            listExtra.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.MARTES, AlxDiet.EXTRA));
        } catch (DAOException ex) {
            Logger.getLogger(DietasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getMiercoles() {
        try {
            listDesayuno.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.MIERCOLES, AlxDiet.DESAYUNO));
            listAlmuerzo.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.MIERCOLES, AlxDiet.ALMUERZO));
            listCena.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.MIERCOLES, AlxDiet.CENA));
            listPre.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.MIERCOLES, AlxDiet.PREENTRENO));
            listPost.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.MIERCOLES, AlxDiet.POSTENTRENO));
            listMerienda.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.MIERCOLES, AlxDiet.MERIENDA));
            listExtra.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.MIERCOLES, AlxDiet.EXTRA));
        } catch (DAOException ex) {
            Logger.getLogger(DietasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getJueves() {
        try {
            listDesayuno.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.JUEVES, AlxDiet.DESAYUNO));
            listAlmuerzo.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.JUEVES, AlxDiet.ALMUERZO));
            listCena.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.JUEVES, AlxDiet.CENA));
            listPre.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.JUEVES, AlxDiet.PREENTRENO));
            listPost.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.JUEVES, AlxDiet.POSTENTRENO));
            listMerienda.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.JUEVES, AlxDiet.MERIENDA));
            listExtra.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.JUEVES, AlxDiet.EXTRA));
        } catch (DAOException ex) {
            Logger.getLogger(DietasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getViernes() {
        try {
            listDesayuno.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.VIERNES, AlxDiet.DESAYUNO));
            listAlmuerzo.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.VIERNES, AlxDiet.ALMUERZO));
            listCena.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.VIERNES, AlxDiet.CENA));
            listPre.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.VIERNES, AlxDiet.PREENTRENO));
            listPost.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.VIERNES, AlxDiet.POSTENTRENO));
            listMerienda.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.VIERNES, AlxDiet.MERIENDA));
            listExtra.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.VIERNES, AlxDiet.EXTRA));
        } catch (DAOException ex) {
            Logger.getLogger(DietasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getSabado() {
        try {
            listDesayuno.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.SABADO, AlxDiet.DESAYUNO));
            listAlmuerzo.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.SABADO, AlxDiet.ALMUERZO));
            listCena.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.SABADO, AlxDiet.CENA));
            listPre.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.SABADO, AlxDiet.PREENTRENO));
            listPost.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.SABADO, AlxDiet.POSTENTRENO));
            listMerienda.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.SABADO, AlxDiet.MERIENDA));
            listExtra.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.SABADO, AlxDiet.EXTRA));
        } catch (DAOException ex) {
            Logger.getLogger(DietasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addDesayuno() {
        try {
            AlxDiet a = getAlxDiet();
            if (a != null) {
                getAlxdiets().insertar(a);
                a.setMomento(AlxDiet.DESAYUNO);
                getAlxdiets().insertar(a);
                listDesayuno.getItems().add(a);
            }
        } catch (DAOException ex) {
            mensaje("Condición", "error", ex);
        }
    }

    @FXML
    void deleteDesayuno(ActionEvent event) {
        if (listDesayuno.getSelectionModel().getSelectedIndex() != -1) {
            try {
                getAlxdiets().eliminar(listDesayuno.getSelectionModel().getSelectedItem());
            } catch (DAOException ex) {
                mensaje("Condición", "error", ex);
            }
            listDesayuno.getItems().remove(listDesayuno.getSelectionModel().getSelectedItem());
        } else {
            mensaje("Seleccione un alimento", "aviso", null);

        }
    }

    public void addAlmuerzo() {
        try {
            AlxDiet a = getAlxDiet();
            if (a != null) {
                a.setMomento(AlxDiet.ALMUERZO);
                getAlxdiets().insertar(a);
                listAlmuerzo.getItems().add(a);
            }
        } catch (DAOException ex) {
            mensaje("Condición", "error", ex);
        }
    }

    @FXML
    void deleteAlmuerzo(ActionEvent event) {
        if (listAlmuerzo.getSelectionModel().getSelectedIndex() != -1) {
            try {
                getAlxdiets().eliminar(listAlmuerzo.getSelectionModel().getSelectedItem());
            } catch (DAOException ex) {
                mensaje("Condición", "error", ex);
            }
            listAlmuerzo.getItems().remove(listAlmuerzo.getSelectionModel().getSelectedItem());
        } else {
            mensaje("Seleccione un alimento", "aviso", null);

        }
    }

    public void addCena() {
        try {
            AlxDiet a = getAlxDiet();
            if (a != null) {
                a.setMomento(AlxDiet.CENA);
                getAlxdiets().insertar(a);
                listCena.getItems().add(a);

            }
        } catch (DAOException ex) {
            mensaje("Condición", "error", ex);
        }
    }

    @FXML
    void deleteCena(ActionEvent event) {
        if (listCena.getSelectionModel().getSelectedIndex() != -1) {
            try {
                getAlxdiets().eliminar(listCena.getSelectionModel().getSelectedItem());
            } catch (DAOException ex) {
                mensaje("Condición", "error", ex);
            }
            listCena.getItems().remove(listCena.getSelectionModel().getSelectedItem());
        } else {
            mensaje("Seleccione un alimento", "aviso", null);

        }
    }

    public void addExtra() {
        try {
            AlxDiet a = getAlxDiet();
            if (a != null) {
                a.setMomento(AlxDiet.EXTRA);
                getAlxdiets().insertar(a);
                listExtra.getItems().add(a);

            }
        } catch (DAOException ex) {
            mensaje("Condición", "error", ex);
        }
    }

    @FXML
    void deleteExtra(ActionEvent event) {
        if (listExtra.getSelectionModel().getSelectedIndex() != -1) {
            try {
                getAlxdiets().eliminar(listExtra.getSelectionModel().getSelectedItem());
            } catch (DAOException ex) {
                mensaje("Condición", "error", ex);
            }
            listExtra.getItems().remove(listExtra.getSelectionModel().getSelectedItem());
        } else {
            mensaje("Seleccione un alimento", "aviso", null);

        }
    }

    public void addMerienda() {
        try {
            AlxDiet a = getAlxDiet();
            if (a != null) {
                a.setMomento(AlxDiet.MERIENDA);
                getAlxdiets().insertar(a);
                listMerienda.getItems().add(a);

            }
        } catch (DAOException ex) {
            mensaje("Condición", "error", ex);
        }
    }

    @FXML
    void deleteMerienda(ActionEvent event) {
        if (listMerienda.getSelectionModel().getSelectedIndex() != -1) {
            try {
                getAlxdiets().eliminar(listMerienda.getSelectionModel().getSelectedItem());
            } catch (DAOException ex) {
                mensaje("Condición", "error", ex);
            }
            listMerienda.getItems().remove(listMerienda.getSelectionModel().getSelectedItem());
        } else {
            mensaje("Seleccione un alimento", "aviso", null);

        }
    }

    public void addPost() {
        try {
            AlxDiet a = getAlxDiet();
            if (a != null) {
                a.setMomento(AlxDiet.POSTENTRENO);
                getAlxdiets().insertar(a);
                listPost.getItems().add(a);

            }
        } catch (DAOException ex) {
            mensaje("Condición", "error", ex);
        }
    }

    @FXML
    void deletePost(ActionEvent event) {
        if (listPost.getSelectionModel().getSelectedIndex() != -1) {
            try {
                getAlxdiets().eliminar(listPost.getSelectionModel().getSelectedItem());
            } catch (DAOException ex) {
                mensaje("Condición", "error", ex);
            }
            listPost.getItems().remove(listPost.getSelectionModel().getSelectedItem());
        } else {
            mensaje("Seleccione un alimento", "aviso", null);

        }
    }

    public void addPre() {
        try {
            AlxDiet a = getAlxDiet();
            if (a != null) {
                a.setMomento(AlxDiet.PREENTRENO);
                getAlxdiets().insertar(a);
                listPre.getItems().add(a);
            }
        } catch (DAOException ex) {
            mensaje("Condición", "error", ex);
        }
    }

    @FXML
    void deletePre(ActionEvent event) {
        if (listPre.getSelectionModel().getSelectedIndex() != -1) {
            try {
                getAlxdiets().eliminar(listPre.getSelectionModel().getSelectedItem());
            } catch (DAOException ex) {
                mensaje("Condición", "error", ex);
            }
            listPre.getItems().remove(listPre.getSelectionModel().getSelectedItem());
        } else {
            mensaje("Seleccione un alimento", "aviso", null);

        }
    }

}
