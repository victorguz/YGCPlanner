/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

/**
 * @author 57300
 */
public class Operacion {

    public static String nombreCamelCase(String cadena) {
        if (cadena.contains(" ")) {
            String[] b = cadena.split(" ");
            String k = "";
            for (String c : b) {
                cadena = (c.charAt(0) + "").toUpperCase() + c.substring(1).toLowerCase();
                if (k.isEmpty()) {
                    k = cadena;
                } else {
                    k += " " + cadena;
                }
            }
            return k;
        } else {
            cadena = (cadena.charAt(0) + "").toUpperCase() + cadena.substring(1).toLowerCase();
            return cadena;
        }
    }

    public static String inicialMayuscula(String cadena) {
        String ini = cadena.substring(0, 1);
        String cad = cadena.substring(1);
        return ini.toUpperCase() + cad.toLowerCase();
    }

    public static double redondear(double a) {
        return Math.round(a * 100) / 100d;
    }
}
