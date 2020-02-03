/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import DAO.DAOException;
import static controlador.Controller.getReferencias;
import static controlador.Controller.mensaje;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import modelo.Referencia;

public class ConfigController extends Controller<Referencia> {

    @FXML
    private TextField textTel1;

    @FXML
    private CheckBox checkWhatsapp1;

    @FXML
    private TextField textWeb1;

    @FXML
    private TextField textCorreo1;

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
            Referencia dash = getDash();
            getReferencias().insert(dash);
            Referencia tel1 = getTel1();
            getReferencias().insert(tel1);
            Referencia web1 = getWeb1();
            getReferencias().insert(web1);
            Referencia correo1 = getCorreo1();
            getReferencias().insert(correo1);
            Referencia face1 = getFace1();
            getReferencias().insert(face1);
            Referencia insta1 = getInsta1();
            getReferencias().insert(insta1);
            Referencia bienvenida = getBienvenida();
            getReferencias().insert(bienvenida);
            Referencia titulobienvenida = getTituloBienvenida();
            getReferencias().insert(titulobienvenida);
            Referencia wa1 = getWhatsapp1();
            getReferencias().insert(wa1);
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    @Override
    public void modificar() {
        try {
             Referencia dash = getDash();
            getReferencias().update(dash);
            Referencia tel1 = getTel1();
            getReferencias().update(tel1);
            Referencia web1 = getWeb1();
            getReferencias().update(web1);
            Referencia correo1 = getCorreo1();
            getReferencias().update(correo1);
            Referencia face1 = getFace1();
            getReferencias().update(face1);
            Referencia insta1 = getInsta1();
            getReferencias().update(insta1);
            Referencia bienvenida = getBienvenida();
            getReferencias().update(bienvenida);
            Referencia titulobienvenida = getTituloBienvenida();
            getReferencias().update(titulobienvenida);
            Referencia wa1 = getWhatsapp1();
            getReferencias().update(wa1);
            mensaje("Configuración modificada", "exito");
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

    @Override
    public void eliminar() {
    }

    @Override
    public void limpiar() {
    }

    @Override
    public void mostrar() {
    }

    @Override
    public Referencia captar() {
        return null;
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


    public Referencia getWeb1() {
        Referencia web = new Referencia();
        web.setNombre("web1");
        web.setDescripcion("pagina web 1");
        web.setDato(textWeb1.getText());
        return web;
    }

    public void setWeb1(Referencia web) {
        textWeb1.setText(web.getDato());
    }

    public Referencia getCorreo1() {
        Referencia ref = new Referencia();
        ref.setNombre("correo1");
        ref.setDescripcion("correo electronico 1");
        ref.setDato(textCorreo1.getText());
        return ref;
    }

    public void setCorreo1(Referencia ref) {
        textCorreo1.setText(ref.getDato());
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

    public void setDash(Referencia ref) {
        checkdash.setSelected(Boolean.valueOf(ref.getDato()));
    }

    public Referencia getDash() {
        Referencia ref = new Referencia();
        ref.setNombre("dash");
        ref.setDescripcion("activa o desactiva el dashboard como pantalla inicial");
        ref.setDato(checkdash.isSelected() + "");
        return ref;
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

    public Referencia getWhatsapp1() {
        Referencia web = new Referencia();
        web.setNombre("wa1");
        web.setDescripcion("si el telefono 1 tiene whatsapp");
        web.setDato(checkWhatsapp1.isSelected() + "");
        return web;
    }

    public void setWhatsapp1(Referencia web) {
        checkWhatsapp1.setSelected(Boolean.valueOf(web.getDato()));
    }


    @Override
    public void obtener() {
        try {
            setTel1(getReferencias().select("tel1"));
            setWeb1(getReferencias().select("web1"));
            setCorreo1(getReferencias().select("correo1"));
            setInsta1(getReferencias().select("insta1"));
            setFace1(getReferencias().select("face1"));
            setWhatsapp1(getReferencias().select("wa1"));
            setTituloBienvenida(getReferencias().select("titulobienvenida"));
            setBienvenida(getReferencias().select("bienvenida"));
            setDash(getReferencias().select("dash"));
        } catch (DAOException ex) {
            excepcion(ex);
        }
    }

}
