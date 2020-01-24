/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import DAO.DAOException;
import static controlador.Controller.mensaje;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import modelo.plan.Alimento;

public class AlimentosController extends Controller<Alimento> {

    @FXML
    private ComboBox<Alimento> comboAlimentos;
    @FXML
    private TextField textBuscarAlimento;
    @FXML
    private TextField textNombre;

    @FXML
    private TextField textKilocalorias;

    @FXML
    private TextField textProteina;

    @FXML
    private TextField textGrasas;

    @FXML
    private TextField textCarbos;

    @FXML
    private TextField textKilocalorias1;

    @FXML
    private TextField textProteina1;

    @FXML
    private TextField textGrasas1;

    @FXML
    private TextField textCarbos1;

    @Override
    public void updated() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        obtener();
    }

    @Override
    public void registrar() {
        try {
            Alimento c = captar();
            if (c.isEmpty()) {
                    mensaje("Los campos señalados con asterisco son obligatorios.", "aviso");
            } else {
                getAlimentos().insertar(c);
                mensaje("Alimento registrado", "exito");
                obtener();
                setAlimentosUpdated(true);
            }
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    @Override
    public void modificar() {
        if (!comboAlimentos.getItems().isEmpty()) {
            try {
                Alimento a = captar();
                if (!a.isEmpty()) {
                    a.setAlimentokey(comboAlimentos.getSelectionModel().getSelectedItem().getAlimentokey());
                    getAlimentos().modificar(a);
                    textBuscarAlimento.setText(textNombre.getText());
                    obtener();
                    setAlimentosUpdated(true);
                    mensaje("Alimento modificado", "exito");
                } else {
                    mensaje("Los campos señalados con asterisco son obligatorios.", "aviso");
                }
            } catch (DAOException ex) {
                excepcion(ex);
            }
        } else {
            mensaje("Seleccione un alimento", "aviso");

        }
    }

    @Override
    public void eliminar() {
        if (!comboAlimentos.getItems().isEmpty()) {
            try {
                Alimento a = captar();
                if (!a.isEmpty()) {
                    a.setAlimentokey(comboAlimentos.getSelectionModel().getSelectedItem().getAlimentokey());
                    getAlimentos().eliminar(a);
                    mensaje("Alimento eliminado", "exito");
                    obtener();
                    setAlimentosUpdated(true);
                } else {
                    mensaje("Los campos señalados con asterisco son obligatorios.", "aviso");
                }
            } catch (DAOException ex) {
                excepcion(ex);
            }
        } else {
            mensaje("Seleccione un alimento", "aviso");

        }
    }

    @Override
    public void limpiar() {
        textNombre.setText("");
        textProteina.setText("");
        textGrasas.setText("");
        textCarbos.setText("");
        textKilocalorias.setText("");
        textProteina1.setText("");
        textGrasas1.setText("");
        textCarbos1.setText("");
        textKilocalorias1.setText("");
    }

    @Override
    public void mostrar() {
        if (!comboAlimentos.getItems().isEmpty()) {
            Alimento c = comboAlimentos.getSelectionModel().getSelectedItem();
            textNombre.setText(c.getNombre());
            textProteina.setText("" + c.getProteinas());
            textGrasas.setText("" + c.getGrasas());
            textCarbos.setText("" + c.getCarbohidratos());
            textKilocalorias.setText("" + Operacion.redondear(c.getKilocalorias()));
            //1 gramo
            textProteina1.setText("" + Operacion.redondear(c.getProteinas() / 100));
            textGrasas1.setText("" + Operacion.redondear(c.getGrasas() / 100));
            textCarbos1.setText("" + Operacion.redondear(c.getCarbohidratos() / 100));
            textKilocalorias1.setText("" + Operacion.redondear(c.getKilocalorias() / 100));
        }
    }

    @Override
    public Alimento captar() throws DAOException {
        Alimento c = new Alimento();
        c.setNombre(textNombre.getText());
        c.setProteinas((textProteina.getText().isEmpty())?0:Double.parseDouble(textProteina.getText()));
        c.setGrasas((textGrasas.getText().isEmpty())?0:Double.parseDouble(textGrasas.getText()));
        c.setCarbohidratos((textCarbos.getText().isEmpty())?0:Double.parseDouble(textCarbos.getText()));
        return c;
    }

    @Override
    public void obtener() {
        try {
            comboAlimentos.getItems().clear();
            if (textBuscarAlimento.getText().isEmpty()) {
                alimentos = getAlimentos().obtenerTodos();
            } else {
                alimentos = getAlimentos().obtenerTodos(textBuscarAlimento.getText());
            }
            if (!alimentos.isEmpty()) {
                comboAlimentos.setItems(alimentos);
                selectAlimento(0);
            }
        } catch (DAOException ex) {
            excepcion(ex);
        }
        setAlimentosUpdated(true);
    }

    public void selectAlimento(int i) {
        if (!comboAlimentos.getItems().isEmpty()) {
            comboAlimentos.getSelectionModel().select(i);
            mostrar();
        }
    }

}
    