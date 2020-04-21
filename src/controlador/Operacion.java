/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.text.DecimalFormat;

/**
 * @author 57300
 */
public class Operacion {

    public static String toCamelCase(String cadena) {
        if (cadena.contains(" ")) {
            String[] b = cadena.split(" ");
            String k = "";
            for (String c : b) {
                if (!c.isEmpty()) {
                    cadena = (c.charAt(0) + "").toUpperCase() + c.substring(1).toLowerCase();
                    if (k.isEmpty()) {
                        k = cadena;
                    } else {
                        k += " " + cadena;
                    }
                }
            }
            return k;
        } else {
            cadena = (cadena.charAt(0) + "").toUpperCase() + cadena.substring(1).toLowerCase();
            return cadena;
        }
    }

    public static String inicialMayuscula(String cadena) {
        if (cadena == null || cadena.isEmpty()) {
            return "";
        }
        String ini = cadena.substring(0, 1);
        String cad = cadena.substring(1);
        return ini.toUpperCase() + cad.toLowerCase();
    }

    public static String formatear(double valor) {
        DecimalFormat df = new DecimalFormat("#.#");
        return df.format(valor).replaceAll(",", "\\.").replaceAll("\\.0", "");
    }

    public static String youtube() throws Exception {
        throw new Exception("Aquí iba a hacer algo pero se me olvidó");
    }

    public static String[] toCamelCase(String[] cadenas) {
        String[] nuevas = new String[cadenas.length];
        for (int i = 0; i < cadenas.length; i++) {
            nuevas[i] = inicialMayuscula(cadenas[i]);
        }
        return nuevas;
    }
}
