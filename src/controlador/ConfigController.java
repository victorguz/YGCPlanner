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
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import modelo.Referencia;

public class ConfigController extends Controller<Referencia> {

    @FXML
    private TextField textCelular;

    @FXML
    private TextField textIndicativo;

    @FXML
    private TextField textNombrePagina;

    @FXML
    private TextField textLink;

    @FXML
    private TextField textCorreo;

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
            Referencia celular = getCelular();
            Referencia web = getWeb();
            Referencia correo = getCorreo();
            Referencia bienvenida = getBienvenida();
            getReferencias().insertar(celular);
            getReferencias().insertar(web);
            getReferencias().insertar(correo);
            getReferencias().insertar(bienvenida);
        } catch (DAOException ex) {
            mensaje("Condición", "error", ex);
        }
    }

    @Override
    public void modificar() {
        try {
            Referencia celular = getCelular();
            Referencia web = getWeb();
            Referencia correo = getCorreo();
            Referencia bienvenida = getBienvenida();
            getReferencias().modificar(celular);
            getReferencias().modificar(web);
            getReferencias().modificar(correo);
            getReferencias().modificar(bienvenida);
            mensaje("Referencia actualizado", "exito", null);
        } catch (DAOException ex) {
            mensaje("Condición", "error", ex);
        }
    }

    @Override
    public void eliminar() {
    }

    @Override
    public void buscar() {
    }

    @Override
    public void limpiar() {
    }

    @Override
    public void mostrar() {
    }

    @Override
    public Referencia captar() throws DAOException {
        return null;
    }

    public Referencia getCelular() throws DAOException {
        Referencia ref = new Referencia();
        ref.setReferenciakey(1);
        ref.setNombre("CELULAR", "celular");
        ref.setDescripcion(textIndicativo.getText(), "indicativo");
        ref.setLink(textCelular.getText(), "celular");
        return ref;
    }

    public Referencia getWeb() throws DAOException {
        Referencia web = new Referencia();
        web.setReferenciakey(2);
        web.setNombre("WEB", "nombre de página web");
        web.setDescripcion(textNombrePagina.getText(), "nombre de página web");
        web.setLink(textLink.getText(), "link de página web");
        return web;
    }

    public Referencia getCorreo() throws DAOException {
        Referencia ref = new Referencia();
        ref.setReferenciakey(3);
        ref.setNombre("CORREO", "correo");
        ref.setDescripcion(textCorreo.getText(), "correo");
        ref.setLink("correo", "electronico");
        return ref;
    }

    public Referencia getBienvenida() throws DAOException {
        Referencia ref = new Referencia();
        ref.setReferenciakey(4);
        ref.setNombre("BIENVENIDA", "bienvenida");
        ref.setDescripcion(textTitulo.getText(), "titulo de bienvenida");
        ref.setLink(textBienvenida.getText(), "mensaje de bienvenida");
        return ref;
    }

    public void setCelular(Referencia ref) throws DAOException {
        textIndicativo.setText(ref.getDescripcion());
        textCelular.setText(ref.getLink());
    }

    public void setWeb(Referencia web) throws DAOException {
        textNombrePagina.setText(web.getDescripcion());
        textLink.setText(web.getLink());
    }

    public void setCorreo(Referencia ref) throws DAOException {
        textCorreo.setText(ref.getDescripcion());
    }

    public void setBienvenida(Referencia ref) throws DAOException {
        textTitulo.setText(ref.getDescripcion());
        textBienvenida.setText(ref.getLink());
    }

    @Override
    public void obtener() {
        try {
            setCelular(getReferencias().obtener("celular"));
            setWeb(getReferencias().obtener("web"));
            setCorreo(getReferencias().obtener("correo"));
            setBienvenida(getReferencias().obtener("bienvenida"));
        } catch (DAOException ex) {
            mensaje("Condición", "error", ex);
        }
    }

}
