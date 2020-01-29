/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.plan;

import controlador.Operacion;
import java.util.ArrayList;


/**
 *
 * @author Victor
 */
public class Plan {

    private int plankey;
    private String nombre = "";
    private String objetivo = "";
    private String descripcion = "";
    private String sexo = "";
    private String tipo = "";
    private int edad;

    public Plan() {
    }

    public int getPlankey() {
        return plankey;
    }

    public void setPlankey(int plankey)  {
        this.plankey = plankey;
    }

    public Plan(String nombre, String objetivo, String descripcion, String sexo, int edad)  {
        setNombre(nombre);
        setObjetivo(objetivo);
        setDescripcion(descripcion);
        setSexo(sexo);
        setEdad(edad);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre)  {
        this.nombre = nombre;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo){
        this.objetivo = objetivo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion)  {
        this.descripcion = descripcion;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo)  {
        this.sexo = sexo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo)  {
                this.tipo = tipo;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad)  {
    }

    @Override
    public String toString() {
        return Operacion.nombreCamelCase(getNombre());
    }

    public boolean isEmpty() {
        return getNombre().isEmpty()
                || getTipo().isEmpty();
    }
    
        public ArrayList<String> toArray(){
        ArrayList<String> n=new ArrayList<>();
        return n;
        }
}
