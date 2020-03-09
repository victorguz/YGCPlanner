/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import DAO.DAOException;
import archivo.PDF;
import com.itextpdf.text.DocumentException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import modelo.cliente.Cliente;
import modelo.cliente.Medida;
import modelo.plan.Plan;

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
    private TextField textNombre;

    @FXML
    private TextField textApellido;

    @FXML
    private TextField textDocumento;

    @FXML
    private ComboBox<String> comboTipoDoc;

    @FXML
    private ToggleButton buttonBienvenida;

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
    TextField textEdad;

    /*Objetos del modulo de medidas*/
    @FXML
    private ImageView img;
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
    @FXML
    private ComboBox<Medida> comboMedidas;

    /*Logica de negocio o funciones*/

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setCombos();
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
        comboObjetivos.getItems().setAll("Perder", "Aumentar", "Mantener");
        comboObjetivos.getSelectionModel().select(0);
        obtenerClientes();
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
        setCombos();
    }

    /**
     * Llena los campos de la vista con los datos del cliente
     */
    public void mostrarCliente() {
        if (!getCliente().isEmpty()) {
            textNombre.setText(Operacion.camelCase(getCliente().getNombre()));
            textApellido.setText(Operacion.camelCase(getCliente().getApellido()));
            selectCombo(comboTipoDoc, getCliente().getTipoIdentificacion());
            textDocumento.setText(getCliente().getIdentificacion());
            textEdad.setText(getCliente().getEdad() + "");
            selectCombo(comboSexo, getCliente().getSexo());
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

    public void imprimir() {
        try {
            PDF f = new PDF();
            f.createPDF();
            f.setCliente(getCliente());
            if (buttonBienvenida.isSelected()) {
                f.addBienvenida();
            }
            if (medidas.isEmpty()) {
                mensaje("Este cliente no tiene medidas, ni planes asignados", "error");
            } else {
                if (buttonMedidas.isSelected()) {
                    f.addMedidas();
                }
                if (buttonRutina.isSelected()) {
                    f.addRutina();
                }
                if (buttonDieta.isSelected()) {
                    f.addDieta();
                }
            }
            f.close();
        } catch (DocumentException | IOException | DAOException ex) {
            excepcion(ex);
        }
    }


    /*Logica de negocio para medidas*/


    public Medida captarMedida() {
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


    public void registrarMedida() {
        if (getCliente().isEmpty()) {
            mensaje("Seleccione un cliente", "aviso");
        } else {
            Medida m = captarMedida();
            if (m.isEmpty()) {
                mensaje("Los campos señalados con asterisco son obligatorios.", "aviso");
            } else {
                try {
                    getMedidas().insert(m);
                    mensaje("Medida registrada", "exito");
                    obtenerMedidas();
                } catch (DAOException ex) {
                    excepcion(ex);
                }
            }
        }
    }

    public void limpiarMedida() {
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


    public void mostrarMedida() {
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
                Medida k = captarMedida();
                if (k.isEmpty()) {
                    mensaje("Los campos señalados con asterisco son obligatorios.", "aviso");
                } else {
                    try {
                        k.setMedidakey(getMedida().getMedidakey());
                        getMedidas().update(k);
                        obtenerMedidas();
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
        Medida k = captarMedida();
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

    public void obtenerDietas() {
        if (!dietas.isEmpty()) {
            comboDietas.setItems(dietas);
            comboDietas.getSelectionModel().select(0);
        }
    }
    public void obtenerRutinas() {
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
        } else {
            setCliente(new Cliente());
        }
        mostrarCliente();
        cambiarImagen();
        obtenerMedidas();
    }

    public void selectCliente() {
        if (!comboClientes.getItems().isEmpty()) {
            setCliente(comboClientes.getSelectionModel().getSelectedItem());
        } else {
            setCliente(new Cliente());
        }
        mostrarCliente();
        cambiarImagen();
        obtenerMedidas();
    }

    public void selectMedida(int i) {
        if (!comboMedidas.getItems().isEmpty()) {
            comboMedidas.getSelectionModel().select(i);
            setMedida(comboMedidas.getSelectionModel().getSelectedItem());
            mostrarMedida();
        } else {
            mostrarMedida();
            setMedida(new Medida());
        }
    }

    public void selectMedida() {
        if (!comboMedidas.getItems().isEmpty()) {
            setMedida(comboMedidas.getSelectionModel().getSelectedItem());
            mostrarMedida();
        } else {
            setMedida(new Medida());
            mostrarMedida();
        }
    }


    public void obtenerClientes() {
        try {
            comboClientes.getItems().clear();
            if (textBuscar.getText().isEmpty()) {
                clientes = getClientes().all();
            } else {
                clientes = getClientes().where(textBuscar.getText());
            }
            if (!clientes.isEmpty()) {
                comboClientes.setItems(clientes);
                selectCliente(0);
                obtenerMedidas();
            }
        } catch (DAOException ex) {
            excepcion(ex);
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



}
