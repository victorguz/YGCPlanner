/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import DAO.DAOException;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import modelo.Referencia;

import java.net.URL;
import java.util.ResourceBundle;

public class ConfigController extends Controller<Referencia> {

    @FXML
    private TextField textTel1;

    @FXML
    private TextField textInsta1;

    @FXML
    private TextField textFace1;

    @FXML
    private CheckBox checkdash;

    @FXML
    private TextField textTitulo;

    @FXML
    private TextArea textBienvenida;

    @FXML
    private TextArea textRutina;

    @FXML
    private TextArea textDieta;


    public void initialize(URL location, ResourceBundle resources) {
        obtener();
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
            Referencia bienvenida = getBienvenida();
            getReferencias().insert(bienvenida);
            Referencia titulobienvenida = getTituloBienvenida();
            getReferencias().insert(titulobienvenida);
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
            Referencia bienvenida = getBienvenida();
            getReferencias().update(bienvenida);
            Referencia rutina = getRutina();
            getReferencias().update(rutina);
            Referencia dieta = getDieta();
            getReferencias().update(dieta);
            Referencia titulobienvenida = getTituloBienvenida();
            getReferencias().update(titulobienvenida);
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

    public Referencia getTituloBienvenida() {
        Referencia ref = new Referencia();
        ref.setNombre("titulobienvenida");
        ref.setDescripcion("es el título de la hoja de bienvenida");
        ref.setDato(textTitulo.getText());
        return ref;
    }

    public void setTituloBienvenida(Referencia ref) {
        textTitulo.setText(ref.getDato());
    }

    public Referencia getBienvenida() {
        Referencia ref = new Referencia();
        ref.setNombre("bienvenida");
        ref.setDescripcion("es el texto de bienvenida que aparece en la página de bienvenida");
        ref.setDato(textBienvenida.getText());
        return ref;
    }

    public void setBienvenida(Referencia ref) {
        textBienvenida.setText(ref.getDato());
    }

    public Referencia getRutina() {
        Referencia ref = new Referencia();
        ref.setNombre("textrutina");
        ref.setDescripcion("texto de presentación para la rutina");
        ref.setDato(textRutina.getText());
        return ref;
    }

    public void setRutina(Referencia ref) {
        textRutina.setText(ref.getDato());
    }

    public Referencia getDieta() {
        Referencia ref = new Referencia();
        ref.setNombre("textdieta");
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
            setTituloBienvenida(getReferencias().select("titulobienvenida"));
            setBienvenida(getReferencias().select("bienvenida"));
            setRutina(getReferencias().select("textrutina"));
            setDieta(getReferencias().select("textdieta"));
            setDash(getReferencias().select("dash"));
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

}
