/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import DAO.DAOException;
import static controlador.Controller.isMedidaUpdated;
import static controlador.Controller.setMedidaUpdated;
import modelo.cliente.Medida;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author 201621279487
 */
public class MedidaController extends Controller<Medida> {

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

    @FXML
    private TextField textICA;

    ObservableList<String> actividades = FXCollections.observableArrayList();

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
        Medida k = new Medida();
        try {
            if (getCliente().isEmpty()) {
                k.setCliente(null);
            } else {
                k.setCliente(getCliente());
            }
            k.setFecha(datePicker.getValue());
            k.setActividad(comboActividad.getSelectionModel().getSelectedItem());
            k.setObjetivo(comboObjetivos.getSelectionModel().getSelectedItem());
            if (textPeso.getText().isEmpty()) {
                k.setPeso(0);
            } else {
                k.setPeso(Double.parseDouble((textPeso.getText())));
            }
            if (textAltura.getText().isEmpty()) {
                k.setAltura(0);
            } else {
                k.setAltura(Double.parseDouble(textAltura.getText()));
            }
            if (textMuneca.getText().isEmpty()) {
                k.setMuneca(17);
            } else {
                k.setMuneca(Double.parseDouble(textMuneca.getText()));
            }
            if (textCuello.getText().isEmpty()) {
                k.setCuello(0);
            } else {
                k.setCuello(Double.parseDouble(textCuello.getText()));
            }
            if (textCinturaAlta.getText().isEmpty()) {
                k.setCinturaAlta(0);
            } else {
                k.setCinturaAlta(Double.parseDouble(textCinturaAlta.getText()));
            }
            if (textCinturaMedia.getText().isEmpty()) {
                k.setCinturaMedia(0);
            } else {
                k.setCinturaMedia(Double.parseDouble(textCinturaMedia.getText()));
            }
            if (textCinturaBaja.getText().isEmpty()) {
                k.setCinturaBaja(0);
            } else {
                k.setCinturaBaja(Double.parseDouble(textCinturaBaja.getText()));
            }
            if (textCadera.getText().isEmpty()) {
                k.setCadera(0);
            } else {
                k.setCadera(Double.parseDouble(textCadera.getText()));
            }
            if (textPectorales.getText().isEmpty()) {
                k.setPectoral(0);
            } else {
                k.setPectoral(Double.parseDouble(textPectorales.getText()));
            }
            if (textBicepDer.getText().isEmpty()) {
                k.setBicepDer(0);
            } else {
                k.setBicepDer(Double.parseDouble(textBicepDer.getText()));
            }
            if (textBicepIzq.getText().isEmpty()) {
                k.setBicepIzq(0);
            } else {
                k.setBicepIzq(Double.parseDouble(textBicepIzq.getText()));
            }
            if (textCuadricepDer.getText().isEmpty()) {
                k.setCuadricepDer(0);
            } else {
                k.setCuadricepDer(Double.parseDouble(textCuadricepDer.getText()));
            }
            if (textCuadricepIzq.getText().isEmpty()) {
                k.setCuadricepIzq(0);
            } else {
                k.setCuadricepIzq(Double.parseDouble(textCuadricepIzq.getText()));
            }
            if (textPantorrillaDer.getText().isEmpty()) {
                k.setPantorrillaDer(0);
            } else {
                k.setPantorrillaDer(Double.parseDouble(textPantorrillaDer.getText()));
            }
            if (textPantorrillaIzq.getText().isEmpty()) {
                k.setPantorrillaIzq(0);
            } else {
                k.setPantorrillaIzq(Double.parseDouble(textPantorrillaIzq.getText()));
            }
            //Pliegues
            if (textBicipital.getText().isEmpty()) {
                k.setBicipital(0);
            } else {
                k.setBicipital(Double.parseDouble(textBicipital.getText()));
            }
            if (textTricipital.getText().isEmpty()) {
                k.setTricipital(0);
            } else {
                k.setTricipital(Double.parseDouble(textTricipital.getText()));
            }
            if (textSubescapular.getText().isEmpty()) {
                k.setSubescapular(0);
            } else {
                k.setSubescapular(Double.parseDouble(textSubescapular.getText()));
            }
            if (textSuprailiaco.getText().isEmpty()) {
                k.setSuprailiaco(0);
            } else {
                k.setSuprailiaco(Double.parseDouble(textSuprailiaco.getText()));
            }
            return k;
        } catch (DAOException ex) {
            excepcion(ex);
        }
        return k;
    }

    @Override
    public void registrar() {
        if (getCliente().isEmpty()) {
            mensaje("Seleccione un cliente", "aviso");
        } else {
            Medida m = captar();
            if (m != null) {
                if (m.isEmpty()) {
                    mensaje("Aún faltan algunas medidas", "aviso");
                } else {
                    try {
                        getMedidas().insertar(m);
                        mensaje("Medida registrada", "exito");
                        setMedidasUpdated(true);
                    } catch (DAOException ex) {
                        excepcion(ex);
                    }
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
        textICA.setText("");
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
            selectObjetivo();
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
                mensaje("Aún no ha registrado ninguna medida", "aviso");
            } else {
                try {
                    getMedidas().eliminar(getMedida());
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
                    mensaje("Aún faltan algunas medidas", "aviso");
                } else {
                    try {
                        k.setMedidakey(getMedida().getMedidakey());
                        getMedidas().modificar(k);
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
        Medida k = new Medida();
        if (!getCliente().isEmpty()) {
            try {
                k.setCliente(getCliente());
                k.setActividad(comboActividad.getSelectionModel().getSelectedItem());
                k.setObjetivo(comboObjetivos.getSelectionModel().getSelectedItem());
                if (!textPeso.getText().isEmpty()) {
                    k.setPeso(Double.parseDouble((textPeso.getText())));
                }
                if (!textAltura.getText().isEmpty()) {
                    k.setAltura(Double.parseDouble(textAltura.getText()));
                    if (!textMuneca.getText().isEmpty()) {
                        k.setMuneca(Double.parseDouble(textMuneca.getText()));
                        textComplexion.setText("" + k.getComplexionText());
                    }
                    textPesoIdealAprox.setText("" + k.getPesoIdealAprox() + " Kg");
                    textPesoIdealCreff.setText("" + k.getPesoIdealCreff() + " Kg");
                    textPesoIdealLorentz.setText("" + k.getPesoIdealLorentz() + " Kg");
                    textPesoIdealMonnerotDumaine.setText("" + k.getPesoIdealMonnerotDumaine() + " Kg");
                    textGradoObesidad.setText(k.getGradoObesidad());
                    textHarrys.setText("" + k.getTasaMetabolicaHarrys());
                    textSuperavit.setText("" + k.getSuperavitODeficit());
                    textMifflin.setText("" + k.getTasaMetabolicaMifflin());
                    textIMC.setText("" + k.getIndiceMasaCorporal() + " Kg/m2");

                    if (!textCinturaMedia.getText().isEmpty()) {
                        k.setCinturaMedia(Double.parseDouble(textCinturaMedia.getText()));
                        textICA.setText("" + k.getIndiceCinturaAltura());
                    }
                }
                //Pliegues
                if (textSuprailiaco.getText().isEmpty()) {
                    k.setSuprailiaco(0);
                } else {
                    k.setSuprailiaco(Double.parseDouble(textSuprailiaco.getText()));
                }
                if (textSubescapular.getText().isEmpty()) {

                    k.setSubescapular(0);
                } else {
                    k.setSubescapular(Double.parseDouble(textSubescapular.getText()));
                }
                if (textBicipital.getText().isEmpty()) {

                    k.setBicipital(0);
                } else {
                    k.setBicipital(Double.parseDouble(textBicipital.getText()));
                }
                if (textTricipital.getText().isEmpty()) {
                    k.setTricipital(0);
                } else {
                    k.setTricipital(Double.parseDouble(textTricipital.getText()));
                }
                textDensidad.setText("" + k.getDensidadCorporalPorPliegues());
                textPorcentajeGrasa.setText(k.getPorcentajeGrasaSiri() + " %");
                textPesoGrasa.setText("" + k.getPesoGrasaCorporal() + " Kg");
                textPorcentajeMasa.setText(k.getPorcentajeMasaMagra() + " %");
                textMasaLibre.setText("" + k.getMasaLibreDeGrasa() + " Kg");
            } catch (DAOException ex) {
                excepcion(ex);
            }
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

    public void selectObjetivo() {
        labelObjetivo.setText("Calorías para " + comboObjetivos.getSelectionModel().getSelectedItem().toLowerCase());
        calcular();
    }
}
