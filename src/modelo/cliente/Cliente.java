/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.cliente;

import DAO.DAOException;
import controlador.Operacion;

/**
 *
 * @author 201621279487
 */
public class Cliente {

    private int clienteKey;
    private String nombre = "";
    private String apellido = "";
    private String sexo = "";
    private String identificacion = "";
    private String tipoIdentificacion = "";
    private int edad = 0;

    public Cliente() {
    }

    public Cliente(int clienteKey, String nombre, String apellido,
            String sexo, String identificacion,
            String tipoIdentificacion, int edad) throws DAOException {
        setNombre(nombre);
        setApellido(apellido);
        setEdad(edad);
        setSexo(sexo);
        setIdentificacion(identificacion);
        setTipoIdentificacion(tipoIdentificacion);
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) throws DAOException {
        if (edad < 0) {
            throw new DAOException("Ingrese una edad válida.");
        }
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) throws DAOException {
        if (nombre.isEmpty()) {
            throw new DAOException("Digite un nombre");
        }
        this.nombre = Operacion.nombreCamelCase(nombre);
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) throws DAOException {
        if (apellido.isEmpty()) {
            throw new DAOException("Digite un apellido");
        }
        this.apellido = Operacion.nombreCamelCase(apellido);
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) throws DAOException {
        if (sexo.isEmpty()) {
            throw new DAOException("Seleccione un sexo");
        }
        this.sexo = Operacion.nombreCamelCase(sexo);
    }

    public int getClienteKey() {
        return clienteKey;
    }

    public void setClienteKey(int clientekey) throws DAOException {
        if (clientekey < 0) {
            throw new DAOException("Cliente: La llave primaria no puede ser " + clientekey);
        }
        this.clienteKey = clientekey;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) throws DAOException {
        if (identificacion.isEmpty()) {
            throw new DAOException("Digite un numero de documento");
        }
        this.identificacion = identificacion;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) throws DAOException {
        if (tipoIdentificacion.isEmpty()) {
            throw new DAOException("Seleccione un tipo de documento");
        }
        this.tipoIdentificacion = tipoIdentificacion;
    }

    @Override
    public String toString() {
        return getNombre() + " " + getApellido();
    }

    public boolean isEmpty() {
        return getNombre().isEmpty()
                || getApellido().isEmpty()
                || getSexo().isEmpty()
                || getEdad() <= 0
                || getIdentificacion().isEmpty()
                || getTipoIdentificacion().isEmpty();
    }
}
