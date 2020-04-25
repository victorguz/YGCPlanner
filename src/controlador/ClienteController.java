/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import DAO.DAOException;
import archivo.PDF;
import com.itextpdf.text.DocumentException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import modelo.Referencia;
import modelo.cliente.Cliente;
import modelo.cliente.Medida;
import modelo.plan.Plan;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import vista.Main;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * @author 201621279487
 */
public class ClienteController extends Controller<Cliente> {

    /*Objetos del modulo de clientes*/

    @FXML
    protected TextField textBuscar;

    @FXML
    protected ListView<Medida> listView;
    @FXML
    TextField textEdad;
    @FXML
    private TextField textNombre;
    @FXML
    private TextField textApellido;
    @FXML
    private TextField textDocumento;
    @FXML
    private ComboBox<String> comboTipoDoc;
    @FXML
    private ToggleButton buttonMedidas;
    @FXML
    private ToggleButton buttonRutina;
    @FXML
    private ToggleButton buttonDieta;
    @FXML
    private ComboBox<Cliente> comboClientes;
    @FXML
    private ComboBox<String> comboSexo;
    @FXML
    private TextField textGuardar;
    /*Objetos del modulo de medidas*/
    @FXML
    private ImageView img;
    @FXML
    private ComboBox<Plan> comboRutinas;
    @FXML
    private ComboBox<Plan> comboDietas;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<String> comboActividad;
    @FXML
    private ComboBox<Referencia> comboPlantillas;
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
    private TextField textMantenimiento;
    @FXML
    private ComboBox<Medida> comboMedidas;
    @FXML
    private Spinner<Integer> spinnerCantidad;
    @FXML
    private ToggleButton buttonAumentar;
    @FXML
    private ToggleButton buttonMantener;
    @FXML
    private ToggleButton buttonPerder;

    /*Logica de negocio o funciones*/
    private AutoCompletionBinding autoCompletionClientes;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setCombos();
        obtenerClientes();
        obtenerPlanes();
        getRuta();
        obtenerPlantillas();
    }

    private void setCombos() {
        comboSexo.getItems().addAll("Hombre", "Mujer");
        comboSexo.getSelectionModel().select(0);
        comboTipoDoc.getItems().addAll("Cédula de ciudadanía",
                "Tarjeta de identidad", "Cédula extranjera");
        comboTipoDoc.getSelectionModel().select(0);
        comboActividad.getItems().setAll("Ninguno: 0 dias x semana",
                "Ligero: 1 a 3 días x semana",
                "Moderado: 3 a 5 días x semana",
                "Deportista: 6 a 7 días x semana",
                "Atleta: dos veces al dia");
        comboActividad.getSelectionModel().select(0);
    }

    public String getObjetivo() throws SimpleException {
        if (buttonAumentar.isSelected()) {
            return "Aumentar";
        }
        if (buttonMantener.isSelected()) {
            return "Mantener";
        }
        if (buttonPerder.isSelected()) {
            return "Perder";
        }
        throw new SimpleException("Seleccione un objetivo");
    }

    public void setObjetivo(String objetivo) throws SimpleException {
        if (objetivo.equalsIgnoreCase("Aumentar")) {
            buttonAumentar.setSelected(true);
        } else if (objetivo.equalsIgnoreCase("Mantener")) {
            buttonMantener.setSelected(true);
        } else if (objetivo.equalsIgnoreCase("Perder")) {
            buttonPerder.setSelected(true);
        } else {
            throw new SimpleException("Objetivo incorrecto");
        }
    }

    public Cliente captarCliente() throws DAOException {
        Cliente a = new Cliente();
        a.setNombre(textNombre.getText());
        a.setApellido(textApellido.getText());
        a.setEdad(Integer.parseInt(textEdad.getText()));
        a.setTipoIdentificacion(comboTipoDoc.getSelectionModel().getSelectedItem());
        a.setIdentificacion(textDocumento.getText());
        a.setSexo(comboSexo.getSelectionModel().getSelectedItem());
        return a;
    }

    public void limpiarCliente() {
        textNombre.setText("");
        textApellido.setText("");
        textDocumento.setText("");
        textEdad.setText("");
    }

    /**
     * Llena los campos de la vista con los datos del cliente
     */
    public void mostrarCliente() {
        if (!getCliente().isEmpty()) {
            textNombre.setText(Operacion.toCamelCase(getCliente().getNombre()));
            textApellido.setText(Operacion.toCamelCase(getCliente().getApellido()));
            selectCombo(comboTipoDoc, getCliente().getTipoIdentificacion());
            textDocumento.setText(getCliente().getIdentificacion());
            textEdad.setText(getCliente().getEdad() + "");
            selectCombo(comboSexo, getCliente().getSexo());
            cambiarImagen();
        }
    }

    public void mostrarCliente(Cliente item) {
        if (!item.isEmpty()) {
            textNombre.setText(Operacion.toCamelCase(item.getNombre()));
            textApellido.setText(Operacion.toCamelCase(item.getApellido()));
            selectCombo(comboTipoDoc, item.getTipoIdentificacion());
            textDocumento.setText(item.getIdentificacion());
            textEdad.setText(item.getEdad() + "");
            selectCombo(comboSexo, item.getSexo());
            cambiarImagen();
        }
    }

    public void registrarCliente() {
        try {
            Cliente c = captarCliente();
            if (c.isEmpty()) {
                mensaje("Los campos señalados con asterisco son obligatorios.", "aviso");
            } else {
                getClientes().insert(c);
                obtenerClientes();
                mensaje("Cliente registrado", "exito");
            }
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    private void eliminarMedidas() {
        if (!medidas.isEmpty()) {
            for (Medida medida :
                    medidas) {
                try {
                    Controller.getMedidas().delete(medida);
                } catch (DAOException e) {
                    excepcion(e);
                }
            }
        }
    }

    public void eliminarCliente() {
        if (!getCliente().isEmpty()) {
            try {
                getClientes().delete(getCliente());
                eliminarMedidas();
                obtenerClientes();
                mensaje("Cliente eliminado", "exito");
            } catch (DAOException ex) {
                excepcion(ex);
            }
        } else {
            mensaje("Seleccione primero un cliente", "aviso");
        }

    }

    public void modificarCliente() {
        if (!getCliente().isEmpty()) {
            try {
                Cliente c = captarCliente();
                c.setClienteKey(getCliente().getClienteKey());
                getClientes().update(c);
                obtenerClientes();
                mensaje("Cliente modificado", "exito");
            } catch (DAOException ex) {
                excepcion(ex);
            }
        } else {
            mensaje("Seleccione primero un cliente", "aviso");
        }
    }

    public Referencia getPlantilla() {
        if (comboPlantillas.getItems().isEmpty()) {
            return null;
        } else {
            return comboPlantillas.getSelectionModel().getSelectedItem();
        }
    }

    public void obtenerPlantillas() {
        try {
            comboPlantillas.getItems().clear();
            comboPlantillas.setItems(Controller.getReferencias().obtenerPlantillas());
            if (!comboPlantillas.getItems().isEmpty()) {
                comboPlantillas.getSelectionModel().select(0);
            }
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    public Plan getRutina() throws SimpleException {
        if (comboRutinas.getItems().isEmpty()) {
            throw new SimpleException("No hay planes de entrenamiento");
        }
        return comboRutinas.getSelectionModel().getSelectedItem();
    }

    public Plan getDieta() throws SimpleException {
        if (comboDietas.getItems().isEmpty()) {
            throw new SimpleException("No hay planes de alimentación");
        }
        return comboDietas.getSelectionModel().getSelectedItem();
    }


    /*Logica de negocio para medidas*/

    public void imprimir() {
        String ruta = textGuardar.getText();
        if (ruta.isEmpty()) {
            mensaje("Seleccione primero una ruta de guardado", "aviso");
        } else {
            if (new File(ruta).exists()) {
                try {
                    PDF f = new PDF(getCliente(), textGuardar.getText(), getPlantilla());
                    if (medidas.isEmpty()) {
                        mensaje("Este cliente no tiene medidas, ni planes asignados", "Aviso");
                    } else {
                        if (buttonMedidas.isSelected()) {
                            f.addMedidas();
                        }
                        if (buttonRutina.isSelected()) {

                            f.addRutina(getRutina());
                        }
                        if (buttonDieta.isSelected()) {
                            f.addDieta(getDieta());
                        }
                        f.guardar();
                    }
                } catch (DocumentException | IOException | DAOException | SimpleException ex) {
                    excepcion(ex);
                }
            } else {
                mensaje("La ruta seleccionada no existe", "aviso");
            }
        }
    }

    public Medida captarMedida() throws SimpleException {
        if (getCliente().isEmpty()) {
            throw new SimpleException("Seleccione un cliente primero");
        } else {
            Medida k = new Medida();
            k.setCliente(getCliente());
            k.setFecha(datePicker.getValue());
            k.setActividad(comboActividad.getSelectionModel().getSelectedItem());
            k.setObjetivo(getObjetivo());
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

    public void registrarMedida() {
        if (getCliente().isEmpty()) {
            mensaje("Seleccione un cliente", "aviso");
        } else {
            try {
                Medida m = captarMedida();
                if (m.isEmpty()) {
                    mensaje("Los campos señalados con asterisco son obligatorios.", "aviso");
                } else {
                    getMedidas().insert(m);
                    mensaje("Medida registrada", "exito");
                    obtenerMedidas();

                }
            } catch (DAOException | SimpleException ex) {
                excepcion(ex);
            }
        }
    }

    public void limpiarMedida() {
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

    public void mostrarMedida() {
        if (!getMedida().isEmpty()) {
            selectCombo(comboActividad, getMedida().getActividad());
            selectCombo(comboActividad, getMedida().getActividad());
            try {
                setObjetivo(getMedida().getObjetivo());
            } catch (SimpleException e) {
                excepcion(e);
            }
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
            limpiarMedida();
        }
    }

    public void eliminarMedida() {
        if (getCliente().isEmpty()) {
            mensaje("Seleccione un cliente", "aviso");
        } else {
            if (getMedida().isEmpty()) {
                mensaje("Los campos señalados con asterisco son obligatorios.", "aviso");
            } else {
                try {
                    getMedidas().delete(getMedida());
                    obtenerMedidas();
                    mensaje("Medida eliminada", "exito");
                } catch (DAOException ex) {
                    excepcion(ex);
                }
            }
        }
    }

    public void modificarMedida() {
        if (getCliente().isEmpty()) {
            mensaje("Seleccione un cliente", "aviso");
        } else {
            if (!getMedida().isEmpty()) {
                try {
                    Medida k = captarMedida();
                    if (k.isEmpty()) {
                        mensaje("Los campos señalados con asterisco son obligatorios.", "aviso");
                    } else {
                        k.setMedidakey(getMedida().getMedidakey());
                        getMedidas().update(k);
                        obtenerMedidas();
                        mensaje("Medida modificada", "exito");

                    }
                } catch (DAOException | SimpleException ex) {
                    excepcion(ex);
                }
            } else {
                mensaje("Aún no ha registrado ninguna medida", "aviso");
            }
        }
    }

    public void calcular() {
        try {
            Medida k;
            k = captarMedida();
            if (!k.isEmpty()) {
                if (textMuneca.getText().isEmpty()) {
                    textComplexion.setText("Ingrese muñeca");
                    textPesoIdealCreff.setText("Ingrese muñeca");
                } else {
                    textComplexion.setText("" + k.getComplexionText());
                    textPesoIdealCreff.setText(Operacion.formatear(k.getPesoIdealCreff()) + " Kg");
                }
                textPesoIdealAprox.setText(Operacion.formatear(k.getPesoIdealAprox()) + " Kg");
                textPesoIdealLorentz.setText(Operacion.formatear(k.getPesoIdealLorentz()) + " Kg");
                textPesoIdealMonnerotDumaine.setText(Operacion.formatear(k.getPesoIdealMonnerotDumaine()) + " Kg");
                textGradoObesidad.setText(k.getGradoObesidad());
                textHarrys.setText(Operacion.formatear(k.getTasaMetabolicaHarrys()));
                textMifflin.setText(Operacion.formatear(k.getTasaMetabolicaMifflin()));
                textIMC.setText(Operacion.formatear(k.getIndiceMasaCorporal()) + " Kg/m2");
                if (k.getBicipital() == 0 || k.getTricipital() == 0 || k.getSubescapular() == 0 || k.getSuprailiaco() == 0) {
                    textDensidad.setText("Ingrese pliegues");

                } else {
                    textDensidad.setText(Operacion.formatear(k.getDensidadCorporalPorPliegues()));
                }
                textPorcentajeGrasa.setText(Operacion.formatear(k.getPorcentajeGrasa()) + " %");
                textPesoGrasa.setText(Operacion.formatear(k.getPesoGrasaCorporal()) + " Kg");
                textPorcentajeMasa.setText(Operacion.formatear(k.getPorcentajeMasaMagra()) + " %");
                textMasaLibre.setText(Operacion.formatear(k.getMasaLibreDeGrasa()) + " Kg");
                labelObjetivo.setText("Calorías para " + k.getObjetivo().toLowerCase());
                textMantenimiento.setText(Operacion.formatear(k.getCaloriasMantenimiento()));
                textSuperavit.setText(Operacion.formatear(k.getSuperavitODeficit()));
            }
        } catch (SimpleException e) {
            excepcion(e);
        }

    }

    public void obtenerPlanes() {
        if (!dietas.isEmpty()) {
            comboDietas.setItems(dietas);
            comboDietas.getSelectionModel().select(0);
        }
        if (!rutinas.isEmpty()) {
            comboRutinas.setItems(rutinas);
            comboRutinas.getSelectionModel().select(0);
        }
    }

    public void cambiarImagen() {
        if (comboSexo.getSelectionModel().getSelectedItem().equalsIgnoreCase("hombre")) {
            img.setImage(new Image("/imagen/icono/man.png"));
        } else {
            img.setImage(new Image("/imagen/icono/woman.png"));
        }
    }

    public void selectCliente(int i) {
        if (!comboClientes.getItems().isEmpty()) {
            comboClientes.getSelectionModel().select(i);
            setCliente(comboClientes.getSelectionModel().getSelectedItem());
            obtenerMedidas();
            mostrarCliente();
        } else {
            limpiarCliente();
        }
        mostrarCliente();
    }

    public void selectCliente() {
        if (!comboClientes.getItems().isEmpty()) {
            setCliente(comboClientes.getSelectionModel().getSelectedItem());
            mostrarCliente();
            obtenerMedidas();
            mostrarCliente();
        } else {
            limpiarCliente();
            limpiarMedida();
        }
        mostrarCliente();
    }

    public void selectMedida(int i) {
        if (!comboMedidas.getItems().isEmpty()) {
            comboMedidas.getSelectionModel().select(i);
            setMedida(comboMedidas.getSelectionModel().getSelectedItem());
            mostrarMedida();
        } else {
            limpiarMedida();
        }
    }

    public void selectMedida() {
        if (!comboMedidas.getItems().isEmpty()) {
            setMedida(comboMedidas.getSelectionModel().getSelectedItem());
            mostrarMedida();
        } else {
            limpiarMedida();
        }
    }

    public void obtenerClientes() {
        try {
            clientes = getClientes().all();

            if (autoCompletionClientes != null) {
                autoCompletionClientes.dispose();
            }

            autoCompletionClientes = TextFields.bindAutoCompletion(textBuscar, clientes);

            comboClientes.getItems().clear();
            comboClientes.setItems(clientes);
            selectCliente(0);

        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    public void buscarCliente() throws SimpleException {
        if (!textBuscar.getText().isEmpty()) {
            for (int i = 0; i < comboClientes.getItems().size(); i++) {
                if ((comboClientes.getItems().get(i)
                        .getNombre() + " " + comboClientes.getItems().get(i)
                        .getApellido()).toLowerCase().equalsIgnoreCase(textBuscar.getText().toLowerCase())) {
                    selectCliente(i);
                    break;
                }
            }
        }
    }

    public void obtenerMedidas() {
        comboMedidas.getItems().clear();
        if (!getCliente().isEmpty()) {
            try {
                medidas = getMedidas().where("" + getCliente().getClienteKey());
                if (!medidas.isEmpty()) {
                    comboMedidas.setItems(medidas);
                    selectMedida(0);
                }
            } catch (DAOException ex) {
                excepcion(ex);
            }
        }
    }

    public void getRuta() {
        try {
            Referencia r = getReferencias().select("rutaguardado");
            textGuardar.setText(r.getDato());
        } catch (DAOException e) {
            excepcion(e);
        }
    }

    public void setRuta() {
        DirectoryChooser dc = new DirectoryChooser();
        File file = dc.showDialog(Main.stagestatic);
        if (file != null) {
            textGuardar.setText(file.getAbsolutePath());
            try {
                getReferencias().update(new Referencia("rutaguardado", "ruta donde se guardaran los archivos pdf", file.getAbsolutePath()));
            } catch (DAOException e) {
                excepcion(e);
            }
        }
    }
}