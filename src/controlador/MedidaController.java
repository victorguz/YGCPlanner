/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import DAO.DAOException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import modelo.cliente.Medida;
import modelo.plan.Plan;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * @author 201621279487
 */
public class MedidaController extends Controller<Medida> {

    ObservableList<String> actividades = FXCollections.observableArrayList();
    @FXML
    private ComboBox<Plan> comboRutinas;
    @FXML
    private ComboBox<Plan> comboDietas;
    @FXML
    private ComboBox<String> comboObjetivos;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<String> comboActividad;
    @FXML
    private TextField textPeso;
    @FXML
    private TextField textAltura;
    @FXML
    private TextField textMuneca;
    @FXML
    private TextField textCuello;
    @FXML
    private TextField textCinturaAlta;
    @FXML
    private TextField textCinturaMedia;
    @FXML
    private TextField textCinturaBaja;
    @FXML
    private TextField textCadera;
    @FXML
    private TextField textPectorales;
    @FXML
    private TextField textBicepIzq;
    @FXML
    private TextField textBicepDer;
    @FXML
    private TextField textCuadricepIzq;
    @FXML
    private TextField textCuadricepDer;
    @FXML
    private TextField textPantorrillaIzq;
    @FXML
    private TextField textPantorrillaDer;
    @FXML
    private TextField textTricipital;
    @FXML
    private TextField textBicipital;
    @FXML
    private TextField textSubescapular;
    @FXML
    private TextField textSuprailiaco;
    @FXML
    private TextField textComplexion;
    @FXML
    private TextField textGradoObesidad;
    @FXML
    private TextField textDensidad;
    @FXML
    private TextField textPesoIdealCreff;
    @FXML
    private TextField textPesoIdealAprox;
    @FXML
    private TextField textPesoIdealLorentz;
    @FXML
    private TextField textPesoIdealMonnerotDumaine;
    @FXML
    private TextField textIMC;
    @FXML
    private TextField textPorcentajeGrasa;
    @FXML
    private TextField textPorcentajeMasa;
    @FXML
    private TextField textPesoGrasa;
    @FXML
    private TextField textMasaLibre;
    @FXML
    private TextField textHarrys;
    @FXML
    private TextField textMifflin;
    @FXML
    private Label labelObjetivo;
    @FXML
    private TextField textSuperavit;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboActividad.getItems().setAll("Ninguno: 0 dias x semana",
                "Ligero: 1 a 3 días x semana",
                "Moderado: 3 a 5 días x semana",
                "Deportista: 6 a 7 días x semana",
                "Atleta: dos veces al dia");
        comboActividad.getSelectionModel().select(0);
        comboObjetivos.setItems(objetivos);
        comboObjetivos.getSelectionModel().select(0);
        updated();
    }

    @Override
    public Medida captar() {
        if (clientes.isEmpty()) {
            return new Medida();
        } else {
            Medida k = new Medida();
            k.setCliente(getCliente());
            k.setFecha(datePicker.getValue());
            k.setDieta(comboDietas.getSelectionModel().getSelectedItem());
            k.setRutina(comboRutinas.getSelectionModel().getSelectedItem());
            k.setActividad(comboActividad.getSelectionModel().getSelectedItem());
            k.setObjetivo(comboObjetivos.getSelectionModel()
                    .getSelectedItem());
            k.setPeso((textPeso.getText().isEmpty()) ? 0
                    : Double.parseDouble((textPeso.getText())));
            k.setAltura((textAltura.getText().isEmpty()) ? 0
                    : Double.parseDouble(textAltura.getText()));
            k.setMuneca((textMuneca.getText().isEmpty()) ? 0
                    : Double.parseDouble(textMuneca.getText()));
            k.setCuello((textCuello.getText().isEmpty()) ? 0
                    : Double.parseDouble(textCuello.getText()));
            k.setCinturaAlta((textCinturaAlta.getText().isEmpty()) ? 0
                    : Double.parseDouble(textCinturaAlta.getText()));
            k.setCinturaMedia((textCinturaMedia.getText().isEmpty()) ? 0
                    : Double.parseDouble(textCinturaMedia.getText()));
            k.setCinturaBaja((textCinturaBaja.getText().isEmpty()) ? 0
                    : Double.parseDouble(textCinturaBaja.getText()));
            k.setCadera((textCadera.getText().isEmpty()) ? 0
                    : Double.parseDouble(textCadera.getText()));
            k.setPectoral((textPectorales.getText().isEmpty()) ? 0
                    : Double.parseDouble(textPectorales.getText()));
            k.setBicepDer((textBicepDer.getText().isEmpty()) ? 0
                    : Double.parseDouble(textBicepDer.getText()));
            k.setBicepIzq((textBicepIzq.getText().isEmpty()) ? 0
                    : Double.parseDouble(textBicepIzq.getText()));
            k.setCuadricepDer((textCuadricepDer.getText().isEmpty()) ? 0
                    : Double.parseDouble(textCuadricepDer.getText()));
            k.setCuadricepIzq((textCuadricepIzq.getText().isEmpty()) ? 0
                    : Double.parseDouble(textCuadricepIzq.getText()));
            k.setPantorrillaDer((textPantorrillaDer.getText().isEmpty()) ? 0
                    : Double.parseDouble(textPantorrillaDer.getText()));
            k.setPantorrillaIzq((textPantorrillaIzq.getText().isEmpty()) ? 0
                    : Double.parseDouble(textPantorrillaIzq.getText()));
            k.setBicipital((textBicipital.getText().isEmpty()) ? 0
                    : Double.parseDouble(textBicipital.getText()));
            k.setTricipital((textTricipital.getText().isEmpty()) ? 0
                    : Double.parseDouble(textTricipital.getText()));
            k.setSubescapular((textSubescapular.getText().isEmpty()) ? 0
                    : Double.parseDouble(textSubescapular.getText()));
            k.setSuprailiaco((textSuprailiaco.getText().isEmpty()) ? 0
                    : Double.parseDouble(textSuprailiaco.getText()));
            return k;
        }
    }

    @Override
    public void registrar() {
        if (getCliente().isEmpty()) {
            mensaje("Seleccione un cliente", "aviso");
        } else {
            Medida m = captar();
            if (m.isEmpty()) {
                mensaje("Los campos señalados con asterisco son obligatorios.", "aviso");
            } else {
                try {
                    getMedidas().insert(m);
                    mensaje("Medida registrada", "exito");
                    setMedidasUpdated(true);
                } catch (DAOException ex) {
                    excepcion(ex);
                }
            }
        }
    }

    @Override
    public void obtener() {
    }

    @Override
    public void limpiar() {
        datePicker.show();
        datePicker.setValue(LocalDate.now());
        textPeso.setText("");
        textAltura.setText("");
        textCuello.setText("");
        textCinturaAlta.setText("");
        textCinturaMedia.setText("");
        textCinturaBaja.setText("");
        textCadera.setText("");
        textMuneca.setText("");
        textPectorales.setText("");
        textBicepIzq.setText("");
        textBicepDer.setText("");
        textCuadricepIzq.setText("");
        textCuadricepDer.setText("");
        textPantorrillaIzq.setText("");
        textPantorrillaDer.setText("");
        textSubescapular.setText("");
        textBicipital.setText("");
        textSuprailiaco.setText("");
        textTricipital.setText("");
        comboActividad.getSelectionModel().select(0);
        comboObjetivos.getSelectionModel().select(0);
        textComplexion.setText("");
        textGradoObesidad.setText("");
        textPesoIdealAprox.setText("");
        textPesoIdealCreff.setText("");
        textPesoIdealLorentz.setText("");
        textPesoIdealMonnerotDumaine.setText("");
        textDensidad.setText("");
        textIMC.setText("");
        textPorcentajeGrasa.setText("");
        textPorcentajeMasa.setText("");
        textPesoGrasa.setText("");
        textMasaLibre.setText("");
        textHarrys.setText("");
        textMifflin.setText("");
        textSuperavit.setText("");
    }

    @Override
    public void mostrar() {
        if (!getMedida().isEmpty()) {
            selectCombo(comboActividad, getMedida().getActividad());
            selectCombo(comboActividad, getMedida().getActividad());
            selectCombo(comboObjetivos, getMedida().getObjetivo());
            datePicker.setValue(getMedida().getFecha());
            textPeso.setText("" + getMedida().getPeso());
            textAltura.setText("" + getMedida().getAltura());
            textMuneca.setText("" + getMedida().getMuneca());
            textCuello.setText("" + getMedida().getCuello());
            textCinturaAlta.setText("" + getMedida().getCinturaAlta());
            textCinturaMedia.setText("" + getMedida().getCinturaMedia());
            textCinturaBaja.setText("" + getMedida().getCinturaBaja());
            textCadera.setText("" + getMedida().getCadera());
            textPectorales.setText("" + getMedida().getPectoral());
            textBicepIzq.setText("" + getMedida().getBicepIzq());
            textBicepDer.setText("" + getMedida().getBicepDer());
            textCuadricepIzq.setText("" + getMedida().getCuadricepIzq());
            textCuadricepDer.setText("" + getMedida().getCuadricepDer());
            textPantorrillaIzq.setText("" + getMedida().getPantorrillaIzq());
            textPantorrillaDer.setText("" + getMedida().getPantorrillaDer());
            textSubescapular.setText("" + getMedida().getSubescapular());
            textBicipital.setText("" + getMedida().getBicipital());
            textSuprailiaco.setText("" + getMedida().getSuprailiaco());
            textTricipital.setText("" + getMedida().getTricipital());
            calcular();
        } else {
            limpiar();
        }
    }

    @Override
    public void eliminar() {
        if (getCliente().isEmpty()) {
            mensaje("Seleccione un cliente", "aviso");
        } else {
            if (getMedida().isEmpty()) {
                mensaje("Los campos señalados con asterisco son obligatorios.", "aviso");
            } else {
                try {
                    getMedidas().delete(getMedida());
                    setMedidasUpdated(true);
                    mensaje("Medida eliminada", "exito");
                } catch (DAOException ex) {
                    excepcion(ex);
                }
            }
        }
    }

    @Override
    public void modificar() {
        if (getCliente().isEmpty()) {
            mensaje("Seleccione un cliente", "aviso");
        } else {
            if (!getMedida().isEmpty()) {
                Medida k = captar();
                if (k.isEmpty()) {
                    mensaje("Los campos señalados con asterisco son obligatorios.", "aviso");
                } else {
                    try {
                        k.setMedidakey(getMedida().getMedidakey());
                        getMedidas().update(k);
                        setMedidasUpdated(true);
                        mensaje("Medida modificada", "exito");
                    } catch (DAOException ex) {
                        excepcion(ex);
                    }
                }
            } else {
                mensaje("Aún no ha registrado ninguna medida", "aviso");
            }
        }
    }

    public void calcular() {
        Medida k = captar();
        textComplexion.setText("" + k.getComplexionText());
        textPesoIdealAprox.setText("" + k.getPesoIdealAprox() + " Kg");
        textPesoIdealCreff.setText("" + k.getPesoIdealCreff() + " Kg");
        textPesoIdealLorentz.setText("" + k.getPesoIdealLorentz() + " Kg");
        textPesoIdealMonnerotDumaine.setText("" + k.getPesoIdealMonnerotDumaine() + " Kg");
        textGradoObesidad.setText(k.getGradoObesidad());
        textHarrys.setText("" + k.getTasaMetabolicaHarrys());
        textSuperavit.setText("" + k.getSuperavitODeficit());
        textMifflin.setText("" + k.getTasaMetabolicaMifflin());
        textIMC.setText("" + k.getIndiceMasaCorporal() + " Kg/m2");
        textDensidad.setText("" + k.getDensidadCorporalPorPliegues());
        textPorcentajeGrasa.setText(k.getPorcentajeGrasaSiri() + " %");
        textPesoGrasa.setText("" + k.getPesoGrasaCorporal() + " Kg");
        textPorcentajeMasa.setText(k.getPorcentajeMasaMagra() + " %");
        textMasaLibre.setText("" + k.getMasaLibreDeGrasa() + " Kg");
        labelObjetivo.setText("Calorías para " + k.getObjetivo().toLowerCase());
    }

    public void obtenerDietasYRutinas() {
        if (!dietas.isEmpty()) {
            comboDietas.setItems(dietas);
            comboDietas.getSelectionModel().select(0);
        }
        if (!rutinas.isEmpty()) {
            comboRutinas.setItems(rutinas);
            comboRutinas.getSelectionModel().select(0);
        }
    }

    /**
     * Si no hay clientes, este método inhabilita algunas cosas.
     */
    @Override
    public void updated() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Runnable updater = new Runnable() {
                    @Override
                    public void run() {
                        if (isMedidaUpdated()) {
                            mostrar();
                            setMedidaUpdated(false);
                        }
                        if (datePicker.getValue() == null) {
                            datePicker.setValue(LocalDate.now());
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
}
