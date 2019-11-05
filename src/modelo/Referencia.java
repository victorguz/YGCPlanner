/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import DAO.DAOException;


/**
 *
 * @author Victor
 */
public class Referencia {

    private int referenciakey;
    private String nombre="";
    private String descripcion="";
    private String link="";

    public Referencia() {
    }

    public Referencia(String Descripcion) throws DAOException {
        setDescripcion(Descripcion);
    }

    public int getReferenciakey() {
        return referenciakey;
    }

    public void setReferenciakey(int referenciakey) {
        this.referenciakey = referenciakey;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) throws DAOException {
        if (descripcion.isEmpty()) {
            throw new DAOException("Digite un Descripcion");
        }
        this.descripcion = descripcion;
    }
public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) throws DAOException {
        if (nombre.isEmpty() ) {
            throw new DAOException("Digite un nombre");
        }
        this.nombre = nombre;
    }
    
    public String getLink() {
        return link;
    }

    public void setLink(String link) throws DAOException {
        if (link.isEmpty()) {
            throw new DAOException("Digite un link");
        }
        this.link = link;
    }
    
    @Override
    public String toString() {
        return getReferenciakey() + "\t" + getDescripcion();
    }

}
