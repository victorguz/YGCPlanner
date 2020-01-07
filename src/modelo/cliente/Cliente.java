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
            String tipoIdentificacion, int edad)  {
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

    public void setEdad(int edad)  {
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre)  {
        this.nombre = nombre.toLowerCase();
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido)  {
        this.apellido = apellido;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo)  {
        this.sexo = sexo.toLowerCase();
    }

    public int getClienteKey() {
        return clienteKey;
    }

    public void setClienteKey(int clientekey)  {
        this.clienteKey = clientekey;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion)  {
        this.identificacion = identificacion;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion)  {
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
