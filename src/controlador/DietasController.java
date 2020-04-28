/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import DAO.DAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import modelo.plan.Alimento;
import modelo.plan.AlxDiet;
import modelo.plan.BasePlan;
import modelo.plan.Plan;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author 201621279487
 */
public class DietasController extends Controller<Plan> {
    public static ObservableList<Alimento> alimentos = FXCollections.observableArrayList();
    @FXML
    protected TextField textDieta;
    @FXML
    Button buttonCal;
    @FXML
    private TextField textBuscarAlx;
    @FXML
    private TextField textKcal;
    @FXML
    private TextField textCarbohidratos;
    @FXML
    private TextField textGrasas;
    @FXML
    private TextField textProteinas;
    @FXML
    private TextField carbosDistribucion;
    @FXML
    private TextField proteinasDistribucion;
    @FXML
    private TextField grasasDistribucion;
    @FXML
    private ListView<AlxDiet> listDesayuno;
    @FXML
    private ListView<AlxDiet> listAM;
    @FXML
    private ListView<AlxDiet> listAlmuerzo;
    @FXML
    private ListView<AlxDiet> listPM;
    @FXML
    private ListView<AlxDiet> listCena;
    @FXML
    private ListView<AlxDiet> listPre;
    @FXML
    private ListView<AlxDiet> listPost;
    @FXML
    private TextField textBuscarAlimento;
    @FXML
    private TextField textNombreAlimento;
    @FXML
    private TextField textPluralAlimento;
    @FXML
    private TextField textKilocaloriasAlimentos;
    @FXML
    private TextField textProteinaAlimentos;
    @FXML
    private TextField textGrasasAlimentos;
    @FXML
    private TextField textCarbosAlimentos;
    @FXML
    private TextField textKcalMenu;
    @FXML
    private TextField textGramosMenu;
    @FXML
    private TextField textProteinasMenu;
    @FXML
    private TextField textCarbosMenu;
    @FXML
    private TextField textGrasasMenu;
    @FXML
    private TextField textCantidad;
    @FXML
    private TextField textGramos;
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
    @FXML
    private ToggleButton buttonDomingo;
    @FXML
    private Label labelPresentacion;
    @FXML
    private Label labelUnidad;
    @FXML
    private ComboBox<String> comboUnidades;
    private AutoCompletionBinding autoCompletionAlx;

    private AutoCompletionBinding autoCompletionAlimentos;
    private AutoCompletionBinding autoCompletionDietas;
    @FXML
    private TextField textPresentacion;
    @FXML
    private HBox boxCantidad;

    public void initialize(URL location, ResourceBundle resources) {
        obtener();
        obtenerAlimentos();
        getMenu();
        calcular();
        textCantidad.setText("1");
        textGramos.setText("1");
        comboUnidades.getItems().setAll(AlxDiet.UNIDADES);
        comboUnidades.getSelectionModel().select(0);
        mostrarPresentaciones();
        setTooltips();
    }

    public void setKcal() {
        if (getMedida().isEmpty()) {
            mensaje("Este cliente no tiene medidas", "aviso");
        } else {
            textKcal.setText(Operacion.formatear(getMedida().getSuperavitODeficit()));
            calcular();
        }
    }

    /**
     * Este método calcula los macronutrientes calculando la distribución
     * porcentual digitada por el usuario.
     */
    public void calcular() {
        if (textKcal.getText().isEmpty()) {
            textKcal.setText("2000");
        }
        //Obtener calorías
        double cal = (textProteinas.getText().isEmpty()) ? 0
                : Double.parseDouble(textKcal.getText());
        //Obtener porcentajes
        double pro = ((textProteinas.getText().isEmpty()) ? 0
                : Double.parseDouble(textProteinas.getText())) / 100;
        double gra = ((textGrasas.getText().isEmpty()) ? 0
                : Double.parseDouble(textGrasas.getText())) / 100;
        double car = ((textCarbohidratos.getText().isEmpty()) ? 0
                : Double.parseDouble(textCarbohidratos.getText())) / 100;
        //Utilizar porcentajes para calcular gramos de macronutrientes
        double proteinas = (cal * pro) / 4;
        double grasas = (cal * gra) / 9;
        double carbohidratos = (cal * car) / 4;
        //Los ponemos en las etiquetas asignadas
        proteinasDistribucion.setText(proteinas + "");
        grasasDistribucion.setText(grasas + "");
        carbosDistribucion.setText(carbohidratos + "");

    }

    public Plan captar() throws SimpleException {
        Plan d = new Plan("dieta");
        if (textDieta.getText().isEmpty()) {
            throw new SimpleException("Seleccione un nombre de plan");
        }
        d.setNombre(textDieta.getText());
        return d;
    }

    public void obtener() {
        try {
            dietas = getPlanes().allDietas();

            if (autoCompletionDietas != null) {
                autoCompletionDietas.dispose();
            }

            autoCompletionDietas = TextFields.bindAutoCompletion(textDieta, dietas);
        } catch (DAOException ex) {
            excepcion(ex);
        }
        getMenu();
    }

    public Plan buscarPlan(String e) throws SimpleException {
        if (e.isEmpty()) {
            throw new SimpleException("Digite el nombre del plan");
        } else {
            for (Plan item : dietas) {
                if (item.getNombre().equalsIgnoreCase(e.toLowerCase())) {
                    return item;
                }
            }
        }
        throw new SimpleException("No se encontró un plan con este nombre: " + e);
    }

    public Plan buscarPlanSilencioso(String e) {
        if (!e.isEmpty()) {
            for (Plan item : dietas) {
                if (item.getNombre().equalsIgnoreCase(e.toLowerCase())) {
                    return item;
                }
            }
        }
        return null;
    }

    public void registrar() {
        try {
            Plan m = captar();
            if (m != null) {
                if (m.isEmpty()) {
                    mensaje("Los campos señalados con asterisco son obligatorios.", "aviso");
                } else {
                    getPlanes().insert(m);
                    obtener();
                    mensaje("Plan de alimentación registrado", "exito");
                }
            }
        } catch (DAOException | SimpleException ex) {
            excepcion(ex);
        }
    }

    public void eliminar() {
        if (opcion(
                "¿Está seguro que desea eliminar el plan "
                        + textDieta.getText().toUpperCase() + " ? \n\nNo podrás recuperarlo más tarde", "error"
        )) {
            try {
                getPlanes().delete(captar());
            } catch (DAOException | SimpleException e) {
                excepcion(e);
            }
            obtener();
            limpiar();
            mensaje("Plan de alimentación eliminado", "exito");
        }
    }

    public void limpiar() {
        textDieta.setText("");
        textDieta.setText("");
        textKcal.setText("");
        textProteinas.setText("");
        textCarbohidratos.setText("");
        textGrasas.setText("");
        listDesayuno.getItems().clear();
        listAM.getItems().clear();
        listAlmuerzo.getItems().clear();
        listPM.getItems().clear();
        listCena.getItems().clear();
        listPre.getItems().clear();
        listPost.getItems().clear();
        textNombreAlimento.setText("");
        textProteinaAlimentos.setText("");
        textGrasasAlimentos.setText("");
        textCarbosAlimentos.setText("");
        textKilocaloriasAlimentos.setText("");
        textBuscarAlx.setText("");
        textPluralAlimento.setText("");
        textCantidad.setText("1");
        textGramos.setText("1");

    }

    public AlxDiet getAlxDiet(String momento) throws SimpleException {
        AlxDiet a = new AlxDiet();
        a.setPlan(buscarPlan(textDieta.getText()));
        a.setAlimento(buscarAlimento(textBuscarAlx.getText()));
        a.setCantidad(Double.parseDouble(textCantidad.getText()));
        a.setGramos(Integer.parseInt(textGramos.getText()));
        a.setDia(getDia());
        a.setMomento(momento);
        a.setUnidad(comboUnidades.getSelectionModel().getSelectedItem());
        a.setPresentacion(textPresentacion.getText());
        return a;
    }

    public void deleteDesayuno() {
        if (listDesayuno.getSelectionModel().getSelectedIndex() == -1) {
            mensaje("Seleccione un alimento en el menú", "aviso");
        } else {
            try {
                AlxDiet a = listDesayuno.getSelectionModel().getSelectedItem();
                getAlxdiets().delete(a);
                listDesayuno.getItems().remove(a);
                deleteMacrosMenu(a);
            } catch (DAOException ex) {
                excepcion(ex);
            }
        }
    }

    public void deleteAm() {
        if (listAM.getSelectionModel().getSelectedIndex() == -1) {
            mensaje("Seleccione un alimento en el menú", "aviso");
        } else {
            try {
                AlxDiet a = listAM.getSelectionModel().getSelectedItem();
                getAlxdiets().delete(a);
                listAM.getItems().remove(a);
                deleteMacrosMenu(a);
            } catch (DAOException ex) {
                excepcion(ex);
            }
        }
    }

    public void deleteAlmuerzo() {
        if (listAlmuerzo.getSelectionModel().getSelectedIndex() == -1) {
            mensaje("Seleccione un alimento en el menú", "aviso");
        } else {
            try {
                AlxDiet a = listAlmuerzo.getSelectionModel().getSelectedItem();
                getAlxdiets().delete(a);
                listAlmuerzo.getItems().remove(a);
                deleteMacrosMenu(a);
            } catch (DAOException ex) {
                excepcion(ex);
            }
        }
    }

    public void deletePm() {
        if (listPM.getSelectionModel().getSelectedIndex() == -1) {
            mensaje("Seleccione un alimento en el menú", "aviso");
        } else {
            try {
                AlxDiet a = listPM.getSelectionModel().getSelectedItem();
                getAlxdiets().delete(a);
                listPM.getItems().remove(a);
                deleteMacrosMenu(a);
            } catch (DAOException ex) {
                excepcion(ex);
            }
        }
    }

    public void deleteCena() {
        if (listCena.getSelectionModel().getSelectedIndex() == -1) {
            mensaje("Seleccione un alimento en el menú", "aviso");
        } else {
            try {
                AlxDiet a = listCena.getSelectionModel().getSelectedItem();
                getAlxdiets().delete(a);
                listCena.getItems().remove(a);
                deleteMacrosMenu(a);
            } catch (DAOException ex) {
                excepcion(ex);
            }
        }
    }

    public void deletePre() {
        if (listPre.getSelectionModel().getSelectedIndex() == -1) {
            mensaje("Seleccione un alimento en el menú", "aviso");
        } else {
            try {
                AlxDiet a = listPre.getSelectionModel().getSelectedItem();
                getAlxdiets().delete(a);
                listPre.getItems().remove(a);
                deleteMacrosMenu(a);
            } catch (DAOException ex) {
                excepcion(ex);
            }
        }
    }

    public void deletePost() {
        if (listPost.getSelectionModel().getSelectedIndex() == -1) {
            mensaje("Seleccione un alimento en el menú", "aviso");
        } else {
            try {
                AlxDiet a = listPost.getSelectionModel().getSelectedItem();
                getAlxdiets().delete(a);
                listPost.getItems().remove(a);
                deleteMacrosMenu(a);
            } catch (DAOException ex) {
                excepcion(ex);
            }
        }
    }

    public void addDesayuno() {
        try {
            AlxDiet a = getAlxDiet(AlxDiet.DESAYUNO);
            if (a != null) {
                if (!a.isEmpty()) {
                    getAlxdiets().insert(a);
                    actualizarUso(a.getAlimento());
                    addMacrosMenu(a);
                    listDesayuno.getItems().add(a);
                    listDesayuno.getSelectionModel().select(listDesayuno.getItems().size() - 1);
                }
            }
        } catch (DAOException | SimpleException ex) {
            excepcion(ex);
        }
    }

    public void addAm() {
        try {
            AlxDiet a = getAlxDiet(AlxDiet.SNACKAM);
            if (a != null) {
                if (!a.isEmpty()) {
                    getAlxdiets().insert(a);
                    actualizarUso(a.getAlimento());
                    addMacrosMenu(a);
                    listAM.getItems().add(a);
                    listAM.getSelectionModel().select(listAM.getItems().size() - 1);
                }
            }
        } catch (DAOException | SimpleException ex) {
            excepcion(ex);
        }
    }

    public void addAlmuerzo() {

        try {
            AlxDiet a = getAlxDiet(AlxDiet.ALMUERZO);
            if (a != null) {
                if (!a.isEmpty()) {
                    getAlxdiets().insert(a);
                    actualizarUso(a.getAlimento());
                    addMacrosMenu(a);
                    listAlmuerzo.getItems().add(a);
                    listAlmuerzo.getSelectionModel().select(listAlmuerzo.getItems().size() - 1);
                }
            }
        } catch (DAOException | SimpleException ex) {
            excepcion(ex);
        }
    }

    public void addPm() {

        try {
            AlxDiet a = getAlxDiet(AlxDiet.SNACKPM);
            if (a != null) {
                if (!a.isEmpty()) {
                    getAlxdiets().insert(a);
                    actualizarUso(a.getAlimento());
                    addMacrosMenu(a);
                    listPM.getItems().add(a);
                    listPM.getSelectionModel().select(listPM.getItems().size() - 1);
                }
            }
        } catch (DAOException | SimpleException ex) {
            excepcion(ex);
        }
    }

    public void addCena() {

        try {
            AlxDiet a = getAlxDiet(AlxDiet.CENA);
            if (a != null) {
                if (!a.isEmpty()) {
                    getAlxdiets().insert(a);
                    actualizarUso(a.getAlimento());
                    addMacrosMenu(a);
                    listCena.getItems().add(a);
                    listCena.getSelectionModel().select(listCena.getItems().size() - 1);
                }
            }
        } catch (DAOException | SimpleException ex) {
            excepcion(ex);
        }
    }

    public void addPre() {

        try {
            AlxDiet a = getAlxDiet(AlxDiet.PREENTRENO);
            if (a != null) {
                if (!a.isEmpty()) {
                    getAlxdiets().insert(a);
                    actualizarUso(a.getAlimento());
                    addMacrosMenu(a);
                    listPre.getItems().add(a);
                    listPre.getSelectionModel().select(listPre.getItems().size() - 1);
                }
            }
        } catch (DAOException | SimpleException ex) {
            excepcion(ex);
        }
    }

    public void addPost() {

        try {
            AlxDiet a = getAlxDiet(AlxDiet.POSTENTRENO);
            if (a != null) {
                if (!a.isEmpty()) {
                    getAlxdiets().insert(a);
                    actualizarUso(a.getAlimento());
                    addMacrosMenu(a);
                    listPost.getItems().add(a);
                    listPost.getSelectionModel().select(listPost.getItems().size() - 1);
                }
            }
        } catch (DAOException | SimpleException ex) {
            excepcion(ex);
        }
    }

    public void actualizarUso(Alimento a) {
        try {
            getAlimentos().update(a);
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    public String getDia() throws SimpleException {
        if (buttonLunes.isSelected()) {
            return BasePlan.LUNES;
        } else if (buttonMartes.isSelected()) {
            return BasePlan.MARTES;
        } else if (buttonMiercoles.isSelected()) {
            return BasePlan.MIERCOLES;
        } else if (buttonJueves.isSelected()) {
            return BasePlan.JUEVES;
        } else if (buttonViernes.isSelected()) {
            return BasePlan.VIERNES;
        } else if (buttonSabado.isSelected()) {
            return BasePlan.SABADO;
        } else if (buttonDomingo.isSelected()) {
            return BasePlan.DOMINGO;
        } else {
            throw new SimpleException("Debe seleccionar un día");
        }
    }

    public void getMenu() {
        if (!textDieta.getText().isEmpty()) {
            try {
                Plan dieta = buscarPlanSilencioso(textDieta.getText());
                if (dieta == null) {
                    listDesayuno.getItems().clear();
                    listAM.getItems().clear();
                    listAlmuerzo.getItems().clear();
                    listPM.getItems().clear();
                    listCena.getItems().clear();
                    listPre.getItems().clear();
                    listPost.getItems().clear();
                } else {
                    listDesayuno.setItems(getAlxdiets().where(dieta.getPlankey(), getDia(), AlxDiet.DESAYUNO));
                    listDesayuno.getSelectionModel().select(listDesayuno.getItems().size() - 1);
                    listAM.setItems(getAlxdiets().where(dieta.getPlankey(), getDia(), AlxDiet.SNACKPM));
                    listAM.getSelectionModel().select(listAM.getItems().size() - 1);
                    listAlmuerzo.setItems(getAlxdiets().where(dieta.getPlankey(), getDia(), AlxDiet.ALMUERZO));
                    listAlmuerzo.getSelectionModel().select(listAlmuerzo.getItems().size() - 1);
                    listPM.setItems(getAlxdiets().where(dieta.getPlankey(), getDia(), AlxDiet.SNACKPM));
                    listPM.getSelectionModel().select(listPM.getItems().size() - 1);
                    listCena.setItems(getAlxdiets().where(dieta.getPlankey(), getDia(), AlxDiet.CENA));
                    listCena.getSelectionModel().select(listCena.getItems().size() - 1);
                    listPre.setItems(getAlxdiets().where(dieta.getPlankey(), getDia(), AlxDiet.PREENTRENO));
                    listPre.getSelectionModel().select(listPre.getItems().size() - 1);
                    listPost.setItems(getAlxdiets().where(dieta.getPlankey(), getDia(), AlxDiet.POSTENTRENO));
                    listPost.getSelectionModel().select(listPost.getItems().size() - 1);
                }
                getMacrosMenu();
            } catch (DAOException | SimpleException e) {
                excepcion(e);
            }
        }
    }

    public void addMacrosMenu(AlxDiet alx) {
        double gramos = (textGramosMenu.getText().isEmpty() ? 0 : Double.parseDouble(textGramosMenu.getText())) + alx.getCantidad();
        double prote = (textProteinasMenu.getText().isEmpty() ? 0 : Double.parseDouble(textProteinasMenu.getText())) + alx.getProteinasxpeso();
        double carbos = (textCarbosMenu.getText().isEmpty() ? 0 : Double.parseDouble(textCarbosMenu.getText())) + alx.getCarbohidratosxpeso();
        double grasas = (textGrasasMenu.getText().isEmpty() ? 0 : Double.parseDouble(textGrasasMenu.getText())) + alx.getGrasasxpeso();
        double kcal = (textKcalMenu.getText().isEmpty() ? 0 : Double.parseDouble(textKcalMenu.getText())) + alx.getKilocaloriasxpeso();
        textGramosMenu.setText(gramos + "");
        textProteinasMenu.setText(prote + "");
        textCarbosMenu.setText(carbos + "");
        textGrasasMenu.setText(grasas + "");
        textKcalMenu.setText(kcal + "");
    }

    public void deleteMacrosMenu(AlxDiet alx) {
        double gramos = (textGramosMenu.getText().isEmpty() ? 0 : Double.parseDouble(textGramosMenu.getText())) - alx.getCantidad();
        double prote = (textProteinasMenu.getText().isEmpty() ? 0 : Double.parseDouble(textGramosMenu.getText())) - alx.getCantidad();
        double carbos = (textCarbosMenu.getText().isEmpty() ? 0 : Double.parseDouble(textGramosMenu.getText())) - alx.getCantidad();
        double grasas = (textGrasasMenu.getText().isEmpty() ? 0 : Double.parseDouble(textGramosMenu.getText())) - alx.getCantidad();
        double kcal = (textKcalMenu.getText().isEmpty() ? 0 : Double.parseDouble(textGramosMenu.getText())) - alx.getCantidad();
        textGramosMenu.setText(gramos + "");
        textProteinasMenu.setText(prote + "");
        textCarbosMenu.setText(carbos + "");
        textGrasasMenu.setText(grasas + "");
        textKcalMenu.setText(kcal + "");

    }

    public void getMacrosMenu() {
        double gramos = 0;
        double prote = 0;
        double carbos = 0;
        double grasas = 0;
        double kcal = 0;
        for (AlxDiet alx :
                listDesayuno.getItems()) {
            gramos += alx.getCantidad();
            prote += alx.getProteinasxpeso();
            carbos += alx.getCarbohidratosxpeso();
            grasas += alx.getGrasasxpeso();
            kcal += alx.getKilocaloriasxpeso();
        }
        for (AlxDiet alx :
                listAM.getItems()) {
            gramos += alx.getCantidad();
            prote += alx.getProteinasxpeso();
            carbos += alx.getCarbohidratosxpeso();
            grasas += alx.getGrasasxpeso();
            kcal += alx.getKilocaloriasxpeso();
        }
        for (AlxDiet alx :
                listAlmuerzo.getItems()) {
            gramos += alx.getCantidad();
            prote += alx.getProteinasxpeso();
            carbos += alx.getCarbohidratosxpeso();
            grasas += alx.getGrasasxpeso();
            kcal += alx.getKilocaloriasxpeso();
        }
        for (AlxDiet alx :
                listPM.getItems()) {
            gramos += alx.getCantidad();
            prote += alx.getProteinasxpeso();
            carbos += alx.getCarbohidratosxpeso();
            grasas += alx.getGrasasxpeso();
            kcal += alx.getKilocaloriasxpeso();
        }
        for (AlxDiet alx :
                listCena.getItems()) {
            gramos += alx.getCantidad();
            prote += alx.getProteinasxpeso();
            carbos += alx.getCarbohidratosxpeso();
            grasas += alx.getGrasasxpeso();
            kcal += alx.getKilocaloriasxpeso();
        }
        for (AlxDiet alx :
                listPre.getItems()) {
            gramos += alx.getCantidad();
            prote += alx.getProteinasxpeso();
            carbos += alx.getCarbohidratosxpeso();
            grasas += alx.getGrasasxpeso();
            kcal += alx.getKilocaloriasxpeso();
        }
        for (AlxDiet alx :
                listPost.getItems()) {
            gramos += alx.getCantidad();
            prote += alx.getProteinasxpeso();
            carbos += alx.getCarbohidratosxpeso();
            grasas += alx.getGrasasxpeso();
            kcal += alx.getKilocaloriasxpeso();
        }
        textGramosMenu.setText(gramos + "");
        textProteinasMenu.setText(prote + "");
        textCarbosMenu.setText(carbos + "");
        textGrasasMenu.setText(grasas + "");
        textKcalMenu.setText(kcal + "");
    }


    public void duplicarPlan() {
        JOptionPane.showMessageDialog(null,
                "Esto puede tardarse un momento.  \n\nSe le avisará cuando haya terminado.", "Aviso", 0);
        try {
            Plan org = buscarPlan(textDieta.getText());
            Plan copy = new Plan("dieta");
            copy.setNombre(org.getNombre() + " - copia");
            getPlanes().insert(copy);
            getAlxdiets().whereCopy(org.getNombre(), copy.getNombre());
            obtener();
            textDieta.setText(copy.toString());
            getMenu();
            mensaje("El plan ha sido duplicado", "exito");
        } catch (DAOException | SimpleException e) {
            excepcion(e);
        }
        getMenu();
    }

    public void registrarAlimento() {
        try {
            Alimento c = captarAlimento();
            getAlimentos().insert(c);
            mensaje("Alimento registrado", "exito");
            obtenerAlimentos();
        } catch (DAOException | SimpleException ex) {
            excepcion(ex);
        }
    }

    public void modificarAlimento() {
        if (opcion("Está a punto de modificar el alimento \"" + textBuscarAlimento.getText().toUpperCase() + "\"."
                + " \n\n¿Está seguro de esto?", "error")) {
            try {
                Alimento a = buscarAlimento(textBuscarAlimento.getText());
                Alimento c = captarAlimento();
                c.setAlimentokey(a.getAlimentokey());
                getAlimentos().update(c);
                obtenerAlimentos();
                mensaje("Alimento modificado", "exito");
                mostrarPresentaciones();
            } catch (DAOException | SimpleException ex) {
                excepcion(ex);
            }
        }
    }

    public void eliminarAlimento() {
        if (opcion("Está a punto de eliminar el alimento \"" + textBuscarAlimento.getText().toUpperCase() + "\"."
                + " \n\n¿Está seguro de esto?", "error")) {
            try {
                Alimento a = captarAlimento();
                getAlimentos().delete(a);
                mensaje("Alimento eliminado", "exito");
                obtenerAlimentos();
            } catch (DAOException | SimpleException ex) {
                excepcion(ex);
            }
        }
    }

    public void mostrarAlimento(Alimento getAlimento) {
        if (!getAlimento.isEmpty()) {
            textNombreAlimento.setText(getAlimento.toString());
            textPluralAlimento.setText(Operacion.inicialMayuscula(getAlimento.getPlural()));
            textProteinaAlimentos.setText(Operacion.formatear(getAlimento.getProteinas()));
            textGrasasAlimentos.setText(Operacion.formatear(getAlimento.getGrasas()));
            textCarbosAlimentos.setText(Operacion.formatear(getAlimento.getCarbohidratos()));
            textKilocaloriasAlimentos.setText(Operacion.formatear(getAlimento.getKilocalorias()));

            textBuscarAlimento.setText(getAlimento.toString());
            textBuscarAlx.setText(getAlimento.toString());
            //mostrarPresentaciones();
        } else {
            limpiar();
        }
    }

    public Alimento captarAlimento() throws SimpleException {
        Alimento c = new Alimento();
        if (textNombreAlimento.getText().isEmpty()) {
            throw new SimpleException("Digite el nombre del alimento");
        }
        if (textPluralAlimento.getText().isEmpty()) {
            throw new SimpleException("Digite el nombre plural para el alimento");
        }
        c.setNombre(textNombreAlimento.getText());
        c.setPlural(textPluralAlimento.getText());
        c.setProteinas((textProteinaAlimentos.getText().isEmpty()) ? 0 : Double.parseDouble(textProteinaAlimentos.getText()));
        c.setGrasas((textGrasasAlimentos.getText().isEmpty()) ? 0 : Double.parseDouble(textGrasasAlimentos.getText()));
        c.setCarbohidratos((textCarbosAlimentos.getText().isEmpty()) ? 0 : Double.parseDouble(textCarbosAlimentos.getText()));
        return c;
    }

    public void obtenerAlimentos() {
        try {
            alimentos = getAlimentos().all();

            if (autoCompletionAlimentos != null) {
                autoCompletionAlimentos.dispose();
            }

            if (autoCompletionAlx != null) {
                autoCompletionAlx.dispose();
            }

            autoCompletionAlimentos = TextFields.bindAutoCompletion(textBuscarAlimento, alimentos);
            autoCompletionAlx = TextFields.bindAutoCompletion(textBuscarAlx, alimentos);
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    public Alimento buscarAlimento(String e) throws SimpleException {
        if (e.isEmpty()) {
            throw new SimpleException("Digite el nombre del alimento a buscar");
        } else {
            for (Alimento item : alimentos) {
                if (item.getNombre().equalsIgnoreCase(e)) {
                    mostrarAlimento(item);
                    return item;
                }
            }
        }
        throw new SimpleException("No se encontró un alimento con este nombre: " + e);
    }

    public void buscarAlimento() {
        if (!textBuscarAlimento.getText().isEmpty()) {
            for (Alimento item : alimentos) {
                if (item.getNombre().equalsIgnoreCase(textBuscarAlimento.getText())) {
                    mostrarAlimento(item);
                    break;
                }
            }
        }
    }

    public void mostrarPresentaciones() {
        cambiarLabelUnidad();
        AlxDiet a = new AlxDiet();
        Alimento k = new Alimento();
        k.setNombre(textBuscarAlx.getText());
        a.setAlimento(k);
        a.setCantidad(Double.parseDouble(textCantidad.getText()));
        a.setGramos(Integer.parseInt(textGramos.getText()));
        a.setUnidad(comboUnidades.getSelectionModel().getSelectedItem());
        a.setPresentacion(textPresentacion.getText());
        labelPresentacion.setText(a.calcularPresentacion());
    }

    public void cambiarLabelUnidad() {
        if (comboUnidades.getSelectionModel().getSelectedItem().equalsIgnoreCase("gramos")) {
            boxCantidad.setDisable(true);
            textCantidad.setText(textGramos.getText());
        } else if (comboUnidades.getSelectionModel().getSelectedItem().equalsIgnoreCase("libras")) {
            boxCantidad.setDisable(true);
            textCantidad.setText((Double.parseDouble(textGramos.getText()) / 453.592) + "");
        } else if (comboUnidades.getSelectionModel().getSelectedItem().equalsIgnoreCase("onzas")) {
            boxCantidad.setDisable(true);
            textCantidad.setText((Double.parseDouble(textGramos.getText()) / 28.3495) + "");
        } else if (comboUnidades.getSelectionModel().getSelectedItem().equalsIgnoreCase("al gusto")) {
            boxCantidad.setDisable(true);
            textCantidad.setText("0");
        } else {
            boxCantidad.setDisable(false);
        }
        labelUnidad.setText(comboUnidades.getSelectionModel().getSelectedItem());
    }

    public void increaseGrams() {
        if (textGramos.getText().isEmpty()) {
            textGramos.setText("1");
        } else {
            if (textGramos.getText().contains(".")) {
                textGramos.setText((Double.parseDouble(textGramos.getText()) + 0.1) + "");
            } else {
                textGramos.setText((Integer.parseInt(textGramos.getText()) + 1) + "");
            }
        }
        mostrarPresentaciones();
    }

    public void increaseCant() {
        if (textCantidad.getText().isEmpty()) {
            textCantidad.setText("1");
        } else {
            if (textCantidad.getText().contains(".")) {
                textCantidad.setText((Double.parseDouble(textCantidad.getText()) + 0.1) + "");
            } else {
                textCantidad.setText((Integer.parseInt(textCantidad.getText()) + 1) + "");
            }
        }
        mostrarPresentaciones();
    }

    public void decreaseGrams() {
        if (textGramos.getText().isEmpty()) {
            textGramos.setText("1");
        } else {
            if (Double.parseDouble(textGramos.getText()) > 1.999999999999) {
                if (textGramos.getText().contains(".")) {
                    textGramos.setText((Double.parseDouble(textGramos.getText()) - 0.1) + "");
                } else {
                    textGramos.setText((Integer.parseInt(textGramos.getText()) - 1) + "");
                }
            }
        }
        mostrarPresentaciones();
    }

    public void decreaseCant() {
        if (textCantidad.getText().isEmpty()) {
            textCantidad.setText("1");
        } else {
            if (Double.parseDouble(textCantidad.getText()) > 1.999999999999) {
                if (textCantidad.getText().contains(".")) {
                    textCantidad.setText((Double.parseDouble(textCantidad.getText()) - 0.1) + "");
                } else {
                    textCantidad.setText((Integer.parseInt(textCantidad.getText()) - 1) + "");
                }
            }
        }
        mostrarPresentaciones();
    }

    public void forTextCantAndGrams(KeyEvent e) {
        increaseOrDecrease(e);
        mostrarPresentaciones();
    }

    public void setTooltips() {
        Tooltip tool = new Tooltip();
        tool.setGraphicTextGap(0);
        tool.setText("En la presentación de los datos puedes usar las palabras clave" +
                "\n\"#cantidad\", \"#unidad\" y \"#alimento\" para hacer uso de los" +
                "\ndatos del alimento de referencia que deseas añadir");
        tool.setWrapText(true);
        textPresentacion.setTooltip(tool);
    }

}
