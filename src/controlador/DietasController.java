/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import DAO.DAOException;
import static controlador.Controller.getAlimentos;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import modelo.cliente.Medida;
import modelo.plan.Alimento;
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
        comboObjetivos.setItems(objetivos);
        comboObjetivos.getSelectionModel().select(0);
        obtener();
        comboSexo.setItems(sexos);
        comboSexo.getSelectionModel().select(0);
        updated();
    }

    @Override
    public void updated() {
        setKCalCliente();
        porcentajesVacios();
        if (isAlimentosUpdated()) {
            obtenerAlimentos();
        }
    }

    @Override
    public Plan captar() throws DAOException {
        Plan d = new Plan();
        d.setTipo("DIETA");
        d.setNombre(textNombre.getText());
        d.setObjetivo(comboObjetivos.getSelectionModel().getSelectedItem());
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
                        if (textBuscar.getText().isEmpty()) {
                            obtener();
                        } else {
                            buscar();
                        }
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
        tKcal.setText("" + k);
        tProtein.setText("" + p);
        tCarbs.setText("" + c);
        tFat.setText("" + f);
        tGrams.setText("" + g);

    }

    public void setKCalCliente() {
        if (!getMedida().isEmpty()) {
            tKcalCliente.setText("" + getMedida().getSuperavitODeficit());
        }
    }

    public void porcentajesVacios() {
        if (textProteinas.getText().isEmpty()) {
            if (!textProteinas.isFocused()) {
                textProteinas.setText("40");
            }
        }
        porcentajeProteinas();
        if (textGrasas.getText().isEmpty()) {
            if (!textGrasas.isFocused()) {
                textGrasas.setText("30");
            }
        }
        porcentajeGrasas();
        if (textCarbohidratos.getText().isEmpty()) {
            if (!textCarbohidratos.isFocused()) {
                textCarbohidratos.setText("30");
            }
        }
        porcentajeCarbos();
    }

    public void porcentajeProteinas() {
        if (!tKcalCliente.getText().isEmpty()) {
            double cal = Double.parseDouble(tKcalCliente.getText());
            double porc;
            if (!textProteinas.getText().isEmpty()) {
                porc = Double.parseDouble(textProteinas.getText()) / 100;
                tProteinCliente.setText("" + Medida.redondear(cal * porc / 4));
            }
        }
        porcentajeGramos();
    }

    public void porcentajeGrasas() {
        if (!tKcalCliente.getText().isEmpty()) {
            double cal = Double.parseDouble(tKcalCliente.getText());
            double porc;
            if (textGrasas.getText().isEmpty()) {
                if (!textGrasas.isFocused()) {
                    textGrasas.setText("30");
                    tFatCliente.setText("" + Medida.redondear(cal * 0.3 / 9));
                }
            } else {
                porc = Double.parseDouble(textGrasas.getText()) / 100;
                tFatCliente.setText("" + Medida.redondear(cal * porc / 9));
            }
        }
        porcentajeGramos();
    }

    public void porcentajeCarbos() {
        if (!tKcalCliente.getText().isEmpty()) {
            double cal = Double.parseDouble(tKcalCliente.getText());
            double porc;
            if (textCarbohidratos.getText().isEmpty()) {
                if (!textCarbohidratos.isFocused()) {
                    textCarbohidratos.setText("30");
                    tCarbsCliente.setText("" + Medida.redondear(cal * 0.3 / 4));
                }
            } else {
                porc = Double.parseDouble(textCarbohidratos.getText()) / 100;
                tCarbsCliente.setText("" + Medida.redondear(cal * porc / 4));
            }
        }
        porcentajeGramos();
    }

    public void porcentajeGramos() {
        if (!tKcalCliente.getText().isEmpty()) {
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
                a.setCantidad(0);
            } else {
                a.setCantidad(Double.parseDouble(textCantidad.getText()));
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
                a.setDia("");
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
            if (buttonDomingo.isSelected()) {
                listDesayuno.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.DOMINGO, AlxDiet.DESAYUNO));
                listAlmuerzo.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.DOMINGO, AlxDiet.ALMUERZO));
                listCena.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.DOMINGO, AlxDiet.CENA));
                listPre.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.DOMINGO, AlxDiet.PREENTRENO));
                listPost.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.DOMINGO, AlxDiet.POSTENTRENO));
                listMerienda.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.DOMINGO, AlxDiet.MERIENDA));
                listExtra.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.DOMINGO, AlxDiet.EXTRA));
                datosDieta();
            } else {
                listDesayuno.getItems().clear();
                listAlmuerzo.getItems().clear();
                listCena.getItems().clear();
                listPre.getItems().clear();
                listPost.getItems().clear();
                listMerienda.getItems().clear();
                listExtra.getItems().clear();
                datosDieta();
            }
        } catch (DAOException ex) {
            mensaje("Condición", "error", ex);
        }
    }

    public void getLunes() {
        try {
            if (buttonDomingo.isSelected()) {
                listDesayuno.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.LUNES, AlxDiet.DESAYUNO));
                listAlmuerzo.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.LUNES, AlxDiet.ALMUERZO));
                listCena.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.LUNES, AlxDiet.CENA));
                listPre.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.LUNES, AlxDiet.PREENTRENO));
                listPost.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.LUNES, AlxDiet.POSTENTRENO));
                listMerienda.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.LUNES, AlxDiet.MERIENDA));
                listExtra.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.LUNES, AlxDiet.EXTRA));
                datosDieta();
            } else {
                listDesayuno.getItems().clear();
                listAlmuerzo.getItems().clear();
                listCena.getItems().clear();
                listPre.getItems().clear();
                listPost.getItems().clear();
                listMerienda.getItems().clear();
                listExtra.getItems().clear();
                datosDieta();
            }
        } catch (DAOException ex) {
            mensaje("Condición", "error", ex);
        }
    }

    public void getMartes() {
        try {
            if (buttonDomingo.isSelected()) {
                listDesayuno.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.MARTES, AlxDiet.DESAYUNO));
                listAlmuerzo.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.MARTES, AlxDiet.ALMUERZO));
                listCena.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.MARTES, AlxDiet.CENA));
                listPre.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.MARTES, AlxDiet.PREENTRENO));
                listPost.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.MARTES, AlxDiet.POSTENTRENO));
                listMerienda.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.MARTES, AlxDiet.MERIENDA));
                listExtra.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.MARTES, AlxDiet.EXTRA));
                datosDieta();
            } else {
                listDesayuno.getItems().clear();
                listAlmuerzo.getItems().clear();
                listCena.getItems().clear();
                listPre.getItems().clear();
                listPost.getItems().clear();
                listMerienda.getItems().clear();
                listExtra.getItems().clear();
                datosDieta();
            }
        } catch (DAOException ex) {
            mensaje("Condición", "error", ex);
        }
    }

    public void getMiercoles() {
        try {
            if (buttonDomingo.isSelected()) {
                listDesayuno.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.MIERCOLES, AlxDiet.DESAYUNO));
                listAlmuerzo.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.MIERCOLES, AlxDiet.ALMUERZO));
                listCena.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.MIERCOLES, AlxDiet.CENA));
                listPre.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.MIERCOLES, AlxDiet.PREENTRENO));
                listPost.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.MIERCOLES, AlxDiet.POSTENTRENO));
                listMerienda.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.MIERCOLES, AlxDiet.MERIENDA));
                listExtra.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.MIERCOLES, AlxDiet.EXTRA));
                datosDieta();
            } else {
                listDesayuno.getItems().clear();
                listAlmuerzo.getItems().clear();
                listCena.getItems().clear();
                listPre.getItems().clear();
                listPost.getItems().clear();
                listMerienda.getItems().clear();
                listExtra.getItems().clear();
                datosDieta();
            }
        } catch (DAOException ex) {
            mensaje("Condición", "error", ex);
        }
    }

    public void getJueves() {
        try {
            if (buttonDomingo.isSelected()) {
                listDesayuno.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.JUEVES, AlxDiet.DESAYUNO));
                listAlmuerzo.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.JUEVES, AlxDiet.ALMUERZO));
                listCena.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.JUEVES, AlxDiet.CENA));
                listPre.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.JUEVES, AlxDiet.PREENTRENO));
                listPost.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.JUEVES, AlxDiet.POSTENTRENO));
                listMerienda.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.JUEVES, AlxDiet.MERIENDA));
                listExtra.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.JUEVES, AlxDiet.EXTRA));
                datosDieta();
            } else {
                listDesayuno.getItems().clear();
                listAlmuerzo.getItems().clear();
                listCena.getItems().clear();
                listPre.getItems().clear();
                listPost.getItems().clear();
                listMerienda.getItems().clear();
                listExtra.getItems().clear();
                datosDieta();
            }
        } catch (DAOException ex) {
            mensaje("Condición", "error", ex);
        }
    }

    public void getViernes() {
        try {
            if (buttonDomingo.isSelected()) {
                listDesayuno.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.VIERNES, AlxDiet.DESAYUNO));
                listAlmuerzo.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.VIERNES, AlxDiet.ALMUERZO));
                listCena.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.VIERNES, AlxDiet.CENA));
                listPre.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.VIERNES, AlxDiet.PREENTRENO));
                listPost.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.VIERNES, AlxDiet.POSTENTRENO));
                listMerienda.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.VIERNES, AlxDiet.MERIENDA));
                listExtra.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.VIERNES, AlxDiet.EXTRA));
                datosDieta();
            } else {
                listDesayuno.getItems().clear();
                listAlmuerzo.getItems().clear();
                listCena.getItems().clear();
                listPre.getItems().clear();
                listPost.getItems().clear();
                listMerienda.getItems().clear();
                listExtra.getItems().clear();
                datosDieta();
            }
        } catch (DAOException ex) {
            mensaje("Condición", "error", ex);
        }
    }

    public void getSabado() {
        try {
            if (buttonDomingo.isSelected()) {
                listDesayuno.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.SABADO, AlxDiet.DESAYUNO));
                listAlmuerzo.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.SABADO, AlxDiet.ALMUERZO));
                listCena.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.SABADO, AlxDiet.CENA));
                listPre.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.SABADO, AlxDiet.PREENTRENO));
                listPost.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.SABADO, AlxDiet.POSTENTRENO));
                listMerienda.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.SABADO, AlxDiet.MERIENDA));
                listExtra.setItems(getAlxdiets().obtenerTodos(getDieta().getPlankey(), AlxDiet.SABADO, AlxDiet.EXTRA));
                datosDieta();
            } else {
                listDesayuno.getItems().clear();
                listAlmuerzo.getItems().clear();
                listCena.getItems().clear();
                listPre.getItems().clear();
                listPost.getItems().clear();
                listMerienda.getItems().clear();
                listExtra.getItems().clear();
                datosDieta();
            }
        } catch (DAOException ex) {
            mensaje("Condición", "error", ex);
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
            datosDieta();
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
                actualizarUso(a.getAlimento());
                datosDieta();
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
            datosDieta();
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
                actualizarUso(a.getAlimento());
                datosDieta();
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
            datosDieta();
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
                actualizarUso(a.getAlimento());
                datosDieta();
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
            datosDieta();
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
                actualizarUso(a.getAlimento());
                datosDieta();
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
            datosDieta();
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
                actualizarUso(a.getAlimento());
                datosDieta();
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
            datosDieta();
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
                actualizarUso(a.getAlimento());
                datosDieta();
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
            datosDieta();
        } else {
            mensaje("Seleccione un alimento", "aviso", null);

        }
    }

    public void actualizarUso(Alimento a) {
        a.usar();
        try {
            getAlimentos().modificar(a);
        } catch (DAOException ex) {
            mensaje("Condición", "error", ex);
        }
    }
}
