/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import DAO.DAOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import modelo.plan.Alimento;
import modelo.plan.Plan;

/**
 *
 * @author 201621279487
 */
public class RutinasController extends Controller<Plan> {

    @FXML
    protected ComboBox<Plan> comboRutina;

    @FXML
    private TextField textBuscarRutina;

    @FXML
    private TextField textNombre;

    @FXML
    private TextArea textDescripcion;

    @FXML
    private HBox botones;

    @FXML
    private TextField textBuscarAlimento;

    @FXML
    private ComboBox<Alimento> comboAlimento;

    @FXML
    private TextField textCantidad;

    @FXML
    private Label tGrams;

    @FXML
    private Label tKca;

    @FXML
    private Label tCarbs;

    @FXML
    private Label tProtein;

    @FXML
    private Label tFat;

    @FXML
    private ListView<Alimento> listDesayuno;

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
    private ListView<Alimento> listAlmuerzo;

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
    private TextArea texAlmuerzo;

    @FXML
    private ListView<Alimento> listCena;

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
    private ListView<Alimento> listPre;

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
    private ListView<Alimento> listPost;

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
    private ListView<Alimento> listMerienda;

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
    private ListView<Alimento> listExtra;

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
    void deleteAlmuerzo(ActionEvent event) {

    }

    @FXML
    void deleteCena(ActionEvent event) {

    }

    @FXML
    void deleteDesayuno(ActionEvent event) {

    }

    @FXML
    void deleteExtra(ActionEvent event) {

    }

    @FXML
    void deleteMerienda(ActionEvent event) {

    }

    @FXML
    void deletePost(ActionEvent event) {

    }

    @FXML
    void deletePre(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        obtenerObjetivos();
        obtener();
        comboSexo.setItems(sexos);
        comboSexo.getSelectionModel().select(0);
        updated();
    }

    @Override
    public void updated() {
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
            rutinas = getRutinas().obtenerTodos();
            comboRutina.getItems().clear();
            if (!rutinas.isEmpty()) {
                comboRutina.setItems(rutinas);
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
                    mensaje("Aún faltan algunos datos en el plan de entrenamiento", "aviso", null);
                } else {
                    getRutinas().insertar(m);
                    obtener();
                    mensaje("Plan de entrenamiento registrado", "exito", null);
                }
            }
        } catch (DAOException ex) {
            mensaje("Condición", "error", ex);
        }
    }

    @Override
    public void modificar() {
        try {
            if (!comboRutina.getItems().isEmpty()) {
                Plan m = captar();
                if (m != null) {
                    m.setPlankey(comboRutina.getSelectionModel().getSelectedItem().getPlankey());
                    if (m.isEmpty()) {
                        mensaje("Aún faltan algunos datos en el plan de entrenamiento", "aviso", null);
                    } else {
                        getRutinas().modificar(m);
                        mensaje("Plan de entrenamiento actualizado", "exito", null);
                        obtener();
                    }
                }
            } else {
                mensaje("No hay planes de entrenamiento que modificar, registre uno", "aviso", null);
            }
        } catch (DAOException ex) {
            mensaje("Condición", "error", ex);
        }
    }

    @Override
    public void eliminar() {
        try {
            if (!comboRutina.getItems().isEmpty()) {
                getRutinas().eliminar(comboRutina.getSelectionModel().getSelectedItem());
                obtener();
                mensaje("Plan de entrenamiento eliminado", "exito", null);
            } else {
                mensaje("No hay planes de entrenamiento que eliminar, registre uno", "aviso", null);
            }
        } catch (DAOException ex) {
            mensaje("Condición", "error", ex);
        }
    }

    @Override
    public void buscar() {
        if (textBuscarRutina.getText().isEmpty()) {
            try {
                rutinas = getRutinas().obtenerTodos(textBuscarRutina.getText());
                //comboRutina.getItems().clear();
                if (rutinas.isEmpty()) {
                    mensaje("No se encontraron planes de entrenamiento", "aviso", null);
                } else {
                    comboRutina.setItems(rutinas);
                    select(0);
                    mensaje("Se encontraron " + rutinas.size() + " planes de entrenamiento", "exito", null);
                }
            } catch (DAOException ex) {
                mensaje("Condición", "error", ex);
            }
        }
    }

    @Override
    public void limpiar() {
        textBuscarRutina.setText("");
        textNombre.setText("");
        textDescripcion.setText("");
        textBuscarAlimento.setText("");
        comboAlimento.getItems().clear();
        textCantidad.setText("");
        tGrams.setText("");
        tKca.setText("");
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
        texAlmuerzo.setText("");
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
        if (!comboRutina.getItems().isEmpty()) {
            Plan d = comboRutina.getSelectionModel().getSelectedItem();
            textNombre.setText(d.getNombre());
            selectObjetivo(d.getObjetivo());
            textDescripcion.setText(d.getDescripcion());
            textEdad.setText(d.getEdad() + "");
            selectSexo(comboSexo.getSelectionModel().getSelectedItem());
        }
    }

    public void select(int i) {
        if (!comboRutina.getItems().isEmpty()) {
            comboRutina.getSelectionModel().select(i);
            mostrar();
        }
    }

}
