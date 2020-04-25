/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import DAO.DAOException;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import modelo.Referencia;
import vista.Main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ConfigController extends Controller<Referencia> {
    @FXML
    private CheckBox checkdash;

    @FXML
    private TextField textTel1;

    @FXML
    private TextField textInsta1;

    @FXML
    private TextField textFace1;

    @FXML
    private TextField textRutaWhite;

    @FXML
    private TextField textNombrePlantilla;

    @FXML
    private TextField textRutaEntrenamiento;

    @FXML
    private TextField textRutaAlimentacion;

    @FXML
    private TextField textRutaMedida;

    @FXML
    private ComboBox<Referencia> comboPlantillas;

    @FXML
    private TextArea textRutina;

    @FXML
    private TextArea textDieta;

    private FileChooser fileChooser;

    public void initialize(URL location, ResourceBundle resources) {
        obtener();
        obtenerPlantillas();
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg")
        );
        seleccionarPlantilla();
    }


    public void registrar() {
        try {
            Referencia dash = getDash();
            getReferencias().insert(dash);
            Referencia tel1 = getTel1();
            getReferencias().insert(tel1);
            Referencia face1 = getFace1();
            getReferencias().insert(face1);
            Referencia insta1 = getInsta1();
            getReferencias().insert(insta1);
            Referencia rutina = getRutina();
            getReferencias().insert(rutina);
            Referencia dieta = getDieta();
            getReferencias().insert(dieta);
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }


    public void modificar() {
        try {
            Referencia dash = getDash();
            getReferencias().update(dash);
            Referencia tel1 = getTel1();
            getReferencias().update(tel1);
            Referencia face1 = getFace1();
            getReferencias().update(face1);
            Referencia insta1 = getInsta1();
            getReferencias().update(insta1);
            Referencia rutina = getRutina();
            getReferencias().update(rutina);
            Referencia dieta = getDieta();
            getReferencias().update(dieta);
            mensaje("Configuración modificada", "exito");
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    public Referencia getTel1() {
        Referencia ref = new Referencia();
        ref.setNombre("tel1");
        ref.setDescripcion("telefono numero 1");
        ref.setDato(textTel1.getText());
        return ref;
    }

    public void setTel1(Referencia ref) {
        textTel1.setText(ref.getDato());
    }


    public Referencia getRutina() {
        Referencia ref = new Referencia();
        ref.setNombre("textorutina");
        ref.setDescripcion("texto de presentación para la rutina");
        ref.setDato(textRutina.getText());
        return ref;
    }

    public void setRutina(Referencia ref) {
        textRutina.setText(ref.getDato());
    }

    public Referencia getDieta() {
        Referencia ref = new Referencia();
        ref.setNombre("textodieta");
        ref.setDescripcion("texto de presentación para la dieta");
        ref.setDato(textDieta.getText());
        return ref;
    }

    public void setDieta(Referencia ref) {
        textDieta.setText(ref.getDato());
    }

    public Referencia getDash() {
        Referencia ref = new Referencia();
        ref.setNombre("dash");
        ref.setDescripcion("activa o desactiva el dashboard como pantalla inicial");
        ref.setDato(checkdash.isSelected() + "");
        return ref;
    }

    public void setDash(Referencia ref) {
        checkdash.setSelected(Boolean.valueOf(ref.getDato()));
    }

    public Referencia getInsta1() {
        Referencia web = new Referencia();
        web.setNombre("insta1");
        web.setDescripcion("instagram 1");
        web.setDato(textInsta1.getText());
        return web;
    }

    public void setInsta1(Referencia web) {
        textInsta1.setText(web.getDato());
    }

    public Referencia getFace1() {
        Referencia web = new Referencia();
        web.setNombre("face1");
        web.setDescripcion("facebook 1");
        web.setDato(textFace1.getText());
        return web;
    }

    public void setFace1(Referencia web) {
        textFace1.setText(web.getDato());
    }


    public void obtener() {
        try {
            setTel1(getReferencias().select("tel1"));
            setInsta1(getReferencias().select("insta1"));
            setFace1(getReferencias().select("face1"));
            setRutina(getReferencias().select("textorutina"));
            setDieta(getReferencias().select("textodieta"));
            setDash(getReferencias().select("dash"));
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    public void registrarPlantilla() {
        //nombre=plantilla+nombrePlantilla
        //descripcion="whitepage/rutina/dieta"
        //link="ruta de imagen"
        if (textNombrePlantilla.getText().isEmpty()) {
            mensaje("Digite un nombre de plantilla", "aviso");
            return;
        }
        if (textRutaWhite.getText().isEmpty()) {
            mensaje("Seleccione una ruta de imagen para la whitepage", "aviso");
            return;
        }
        if (textRutaMedida.getText().isEmpty()) {
            mensaje("Seleccione una ruta de imagen para las medidas", "aviso");
            return;
        }
        if (textRutaEntrenamiento.getText().isEmpty()) {
            mensaje("Seleccione una ruta de imagen para entrenamiento", "aviso");
            return;
        }
        if (textRutaAlimentacion.getText().isEmpty()) {
            mensaje("Seleccione una ruta de imagen para alimentacion", "aviso");
            return;
        }

        try {

            copiarImagen(textRutaWhite.getText(), "src/imagen/pdf/" + textNombrePlantilla.getText(), "whitepage");
            copiarImagen(textRutaAlimentacion.getText(), "src/imagen/pdf/" + textNombrePlantilla.getText(), "alimentacion");
            copiarImagen(textRutaEntrenamiento.getText(), "src/imagen/pdf/" + textNombrePlantilla.getText(), "entrenamiento");
            copiarImagen(textRutaMedida.getText(), "src/imagen/pdf/" + textNombrePlantilla.getText() + "/", "medidas");

            getReferencias().insert(new Referencia(textNombrePlantilla.getText(), "plantilla", "src/imagen/pdf/" + textNombrePlantilla.getText() + "/"));

        } catch (SimpleException | DAOException | IOException e) {
            excepcion(e);
        }

    }

    public void copiarImagen(String origen, String destino, String nombre) throws SimpleException, IOException {
        File file = new File(origen);
        if (file != null) {
            if (file.exists()) {
                BufferedImage imagen = ImageIO.read(new File(origen));
                File folder = new File(destino);
                if (!folder.exists()) {
                    folder.mkdirs();
                }
                File outputfile = new File(folder, nombre + ".jpg");
                outputfile.createNewFile();
                ImageIO.write(imagen, "jpg", outputfile);
            } else {
                throw new SimpleException("El archivo " + file.getName() + " no existe.");
            }
        } else {
            throw new SimpleException("El archivo " + file.getName() + " no existe.");
        }
    }

    public void modificarPlantilla() {
        //nombre=plantilla+nombrePlantilla
        //descripcion="whitepage/rutina/dieta"
        //link="ruta de imagen"

    }

    public void eliminarPlantilla() {
        if (getPlantilla().getNombre().equalsIgnoreCase("principal") || getPlantilla().getNombre().equalsIgnoreCase("onlinefit")) {
            mensaje("Esta plantilla no se puede eliminar.", "tip");
        } else {
            try {
                getReferencias().delete(getPlantilla());
            } catch (DAOException e) {
                excepcion(e);
            }
        }
    }

    public void eliminarPlantilla(Referencia ref) {
        try {
            getReferencias().delete(ref);
        } catch (DAOException e) {
            excepcion(e);
        }
    }

    public Referencia getPlantilla() {
        return comboPlantillas.getSelectionModel().getSelectedItem();
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

    public void seleccionarPlantilla() {
        if (!comboPlantillas.getItems().isEmpty()) {
            Referencia ref = comboPlantillas.getSelectionModel().getSelectedItem();
            textNombrePlantilla.setText(Operacion.toCamelCase(ref.getNombre()));
            textRutaWhite.setText(new File(ref.getDato() + "whitepage.jpg").getAbsolutePath());
            textRutaMedida.setText(new File(ref.getDato() + "medidas.jpg").getAbsolutePath());
            textRutaEntrenamiento.setText(new File(ref.getDato() + "entrenamiento.jpg").getAbsolutePath());
            textRutaAlimentacion.setText(new File(ref.getDato() + "alimentacion.jpg").getAbsolutePath());
        }
    }

    public File setFile(String titulo) {
        fileChooser.setTitle("Buscar imagen para página de " + titulo);
        File file = fileChooser.showOpenDialog(Main.stagestatic);
        if (file != null) {
            fileChooser.setInitialDirectory(file.getParentFile());
            return file;
        }
        return null;
    }

    public void setDieta() {
        File file = setFile("alimentacion");
        textRutaAlimentacion.setText((file == null) ?
                "" : file.getAbsolutePath());
    }

    public void setWhitepage() {
        File file = setFile("whitepage");
        textRutaWhite.setText((file == null) ?
                "" : file.getAbsolutePath());
    }

    public void setRutina() {
        File file = setFile("entrenamiento");
        textRutaEntrenamiento.setText((file == null) ?
                "" : file.getAbsolutePath());
    }

    public void setMedida() {
        File file = setFile("medidas");
        textRutaMedida.setText((file == null) ?
                "" : file.getAbsolutePath());
    }

}
