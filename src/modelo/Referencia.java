/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;


/**
 *
 * @author Victor
 */
public class Referencia {

    private int referenciakey;
    private String nombre = "";
    private String descripcion = "";
    private String link = "";

    public Referencia() {
    }

    public Referencia(String nombre, String desc, String link) {
        this.nombre = nombre;
        this.descripcion = desc;
        this.link = link;
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

    public void setDescripcion(String descripcion)  {
        this.descripcion = descripcion;
    }

    public void setDescripcion(String descripcion, String unQue)  {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre)  {
        this.nombre = nombre;
    }

    /**
     *
     * @param nombre Dato a guardar como nombre
     * @param unQue Titulo de la referencia
     * @
     */
    public void setNombre(String nombre, String unQue)  {
        this.nombre = nombre;
    }

    public String getLink() {
        return link;
    }

    /**
     *
     * @param link Dato a guardar como link
     * @
     */
    public void setLink(String link)  {
        this.link = link;
    }

    public void setLink(String link, String unQue)  {
        this.link = link;
    }

    @Override
    public String toString() {
        return getNombre()+ "\t" + getDescripcion()+ "\t" +getLink();
    }

    public boolean isEmpty() {
        return getNombre().isEmpty() || getDescripcion().isEmpty() || getLink().isEmpty();
    }
}
