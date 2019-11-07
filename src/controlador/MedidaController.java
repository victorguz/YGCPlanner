/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import DAO.DAOException;
import static controlador.Controller.isOnEliminar;
import static controlador.Controller.isOnLimpiar;
import static controlador.Controller.isOnModificar;
import static controlador.Controller.isOnRegistrar;
import static controlador.Controller.setOnEliminar;
import static controlador.Controller.setOnLimpiar;
import static controlador.Controller.setOnModificar;
import static controlador.Controller.setOnRegistrar;
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
import modelo.plan.Plan;

/**
 *
 * @author 201621279487
 */
public class MedidaController extends Controller<Medida> {

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
    private ComboBox<Plan> comboDieta;

    @FXML
    private ComboBox<Plan> comboRutina;

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
    private TextField textObjetivo;

    @FXML
    private TextField textICA;

    ObservableList<String> actividades = FXCollections.observableArrayList();

    private void setCombos() {
        //Llenar listas
        actividades.addAll(
                "NINGUNO: 0 DIAS X SEMANA",
                "LIGERO: 1 A 3 DÍAS X SEMANA",
                "MODERADO: 3 A 5 DÍAS X SEMANA",
                "DEPORTISTA: 6 A 7 DÍAS X SEMANA",
                "ATLETA: DOS VECES AL DIA"
        );
        //Asignar combobox
        comboActividad.setItems(actividades);
        comboActividad.getSelectionModel().select(0);
        //Características de listas
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setCombos();
        obtener();
        obtenerObjetivos();
        selectObjetivo();
        obtenerRutinas();
        obtenerDietas();
        updated();
            calcular();
}

    private void obtenerRutinas() {
        try {
            rutinas = getRutinas().obtenerTodos();
            comboRutina.getItems().clear();
            if (rutinas != null) {
                if (!rutinas.isEmpty()) {
                    comboRutina.setItems(rutinas);
                    comboRutina.getSelectionModel().select(0);
                }
            }
        } catch (DAOException ex) {
            mensaje("Condición", "error", ex);
        }
    }

    private void obtenerDietas() {
        try {
            dietas = getDietas().obtenerTodos();
            comboDieta.getItems().clear();
            if (dietas != null) {
                if (!dietas.isEmpty()) {
                    comboDieta.setItems(dietas);
                    comboDieta.getSelectionModel().select(0);
                }
            }
        } catch (DAOException ex) {
            mensaje("Condición", "error", ex);
        }
    }

    private Plan getRutina() {
        return comboRutina.getSelectionModel().getSelectedItem();
    }

    private Plan getDieta() {
        return comboDieta.getSelectionModel().getSelectedItem();
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
            if (comboRutina.getItems().isEmpty()) {
                k.setRutina(null);
            } else {
                k.setRutina(getRutina());
            }
            if (comboDieta.getItems().isEmpty()) {
                k.setDieta(null);
            } else {
                k.setDieta(getDieta());
            }
            k.setFecha(datePicker.getValue());
            k.setActividad(comboActividad.getSelectionModel().getSelectedItem());
            k.setObjetivo(comboObjetivo.getSelectionModel().getSelectedItem().getObjetivo());
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
                k.setMuneca(0);
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
                k.setPantorrillaIzq(Double.parseDouble(textBicipital.getText()));
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
            mensaje("Condición", "error", ex);
        }
        return k;
    }

    @Override
    public void registrar() {
        if (getCliente().isEmpty()) {
            mensaje("Seleccione un cliente", "aviso", null);
        } else {
            Medida m = captar();
            if (m != null) {
                if (m.isEmpty()) {
                    mensaje("Aún faltan algunas medidas", "aviso", null);
                } else {
                    try {
                        getMedidas().insertar(m);
                        mensaje("Medida registrada", "exito", null);
                        setMedidasUpdated(true);
                    } catch (DAOException ex) {
                        mensaje("Condición", "error", ex);
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
        comboObjetivo.getSelectionModel().select(0);
    }

    @Override
    public void mostrar() {
        if (!getMedida().isEmpty()) {
            selectActividad(getMedida().getActividad());
            selectObjetivo(getMedida().getObjetivo());
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
            selectDieta(getMedida().getDieta());
            selectRutina(getMedida().getRutina());
        }
    }

    public void selectDieta(Plan a) {
        if (!comboDieta.getItems().isEmpty()) {
            for (int i = 0; i < comboDieta.getItems().size(); i++) {
                if (comboDieta.getItems().get(i).getNombre().equalsIgnoreCase(a.getNombre())) {
                    comboDieta.getSelectionModel().select(i);
                    return;
                }
            }
        }
    }

    public void selectRutina(Plan a) {
        if (!comboRutina.getItems().isEmpty()) {
            for (int i = 0; i < comboRutina.getItems().size(); i++) {
                if (comboRutina.getItems().get(i).getNombre().equalsIgnoreCase(a.getNombre())) {
                    comboRutina.getSelectionModel().select(i);
                    return;
                }
            }
        }
    }

    @Override
    public void eliminar() {
        if (getCliente().isEmpty()) {
            mensaje("Seleccione un cliente", "aviso", null);
        } else {
            if (getMedida().isEmpty()) {
                mensaje("Seleccione una medida", "aviso", null);
            } else {
                try {
                    getMedidas().eliminar(getMedida());
                    mensaje("Medida eliminada", "exito", null);
                    setMedidasUpdated(true);
                } catch (DAOException ex) {
                    mensaje("Condición", "error", ex);
                }
            }
        }
    }

    @Override
    public void modificar() {
        if (getCliente().isEmpty()) {
            mensaje("Seleccione un cliente", "aviso", null);
        } else {
            Medida k = captar();
            if (k.isEmpty()) {
                mensaje("Aún faltan algunas medidas", "aviso", null);
            } else {
                try {
                    k.setMedidakey(getMedida().getMedidakey());
                    getMedidas().modificar(k);
                    mensaje("Medida actualizada", "exito", null);
                    setMedidasUpdated(true);
                } catch (DAOException ex) {
                    mensaje("Condición", "error", ex);
                }
            }
        }
    }

    public void calcular() {
        Medida k = new Medida();
        if (!getCliente().isEmpty()) {
            try {
                k.setCliente(getCliente());
                k.setActividad(comboActividad.getSelectionModel().getSelectedItem());
                k.setObjetivo(comboObjetivo.getSelectionModel().getSelectedItem().getObjetivo());
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
                    textObjetivo.setText("" + k.getSuperavitODeficit());
                    textMifflin.setText("" + k.getTasaMetabolicaMifflin());
                    textIMC.setText("" + k.getIndiceMasaCorporal() + " Kg/m2");

                    if (!textCinturaMedia.getText().isEmpty()) {
                        k.setCinturaMedia(Double.parseDouble(textCinturaMedia.getText()));
                        textICA.setText("" + k.getIndiceCinturaAltura());
                    }
                }
                //Pliegues
                if (!(textSuprailiaco.getText().isEmpty() && textSubescapular.getText().isEmpty() && textBicipital.getText().isEmpty() && textTricipital.getText().isEmpty())) {
                    k.setBicipital(Double.parseDouble(textBicipital.getText()));
                    k.setTricipital(Double.parseDouble(textTricipital.getText()));
                    k.setSubescapular(Double.parseDouble(textSubescapular.getText()));
                    k.setSuprailiaco(Double.parseDouble(textSuprailiaco.getText()));
                    textDensidad.setText("" + k.getDensidadCorporalPorPliegues());
                    textPorcentajeGrasa.setText(k.getPorcentajeGrasaSiri() + " %");
                    textPesoGrasa.setText("" + k.getPesoGrasaCorporal() + " Kg");
                    textPorcentajeMasa.setText(k.getPorcentajeMasaMagra() + " %");
                    textMasaLibre.setText("" + k.getMasaLibreDeGrasa() + " Kg");
                }
            } catch (DAOException ex) {
                mensaje("Condición", "error", ex);
            }
        }
    }

    private void selectActividad(String a) {
        if (!comboActividad.getItems().isEmpty()) {
            for (int i = 0; i < comboActividad.getItems().size(); i++) {
                if (comboActividad.getItems().get(i).equalsIgnoreCase(a)) {
                    comboActividad.getSelectionModel().select(i);
                    return;
                }
            }
        }
    }

    /**
     * Si no hay clientes, este método inhabilita algunas cosas.
     */
    @Override
    public void updated() {
        if (isMedidaUpdated()) {
            mostrar();
            setMedidaUpdated(false);
        }
        if (datePicker.getValue() == null) {
            datePicker.setValue(LocalDate.now());
        }
    }

    public void selectObjetivo() {
        labelObjetivo.setText("Calorías para " + comboObjetivo.getSelectionModel().getSelectedItem().getObjetivo().toLowerCase());
    }

    @Override
    public void buscar() {
    }

}
