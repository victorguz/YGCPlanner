/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.cliente;

import controlador.Operacion;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * El proposito de esta clase es almacenar las medidas que cada cliente va
 * teniendo en su proceso de asesoría.
 *
 * @author Victor
 */
public class Medida {

    private int medidakey;
    private Cliente cliente;
    private LocalDate fecha;
    private double peso = 0;
    private double altura = 0;
    private double cinturaAlta = 0;
    private double cinturaMedia = 0;
    private double cinturaBaja = 0;
    private double cadera = 0;
    private double cuello = 0;
    private double pectoral = 0;
    private double bicepIzq = 0;
    private double bicepDer = 0;
    private double cuadricepIzq = 0;
    private double cuadricepDer = 0;
    private double pantorrillaDer = 0;
    private double pantorrillaIzq = 0;
    private String actividad = "";
    private String objetivo = "";
    private double tricipital = 0;
    private double subescapular = 0;
    private double bicipital = 0;
    private double suprailiaco = 0;
    private double muneca = 0;

    //Registro de Funciones
    public Medida() {
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public double getCinturaAlta() {

        return cinturaAlta;
    }

    public void setCinturaAlta(double cinturaAlta) {
        this.cinturaAlta = cinturaAlta;
    }

    public double getCinturaMedia() {
        return cinturaMedia;
    }

    public void setCinturaMedia(double cinturaMedia) {
        this.cinturaMedia = cinturaMedia;
    }

    public double getCinturaBaja() {
        return cinturaBaja;
    }

    public void setCinturaBaja(double cinturaBaja) {
        this.cinturaBaja = cinturaBaja;
    }

    public double getCuello() {
        return cuello;
    }

    public void setCuello(double cuello) {
        this.cuello = cuello;
    }

    public double getCadera() {
        return cadera;
    }

    public void setCadera(double cadera) {
        this.cadera = cadera;
    }

    public double getBicepIzq() {
        return bicepIzq;
    }

    public void setBicepIzq(double bicepIzq) {
        this.bicepIzq = bicepIzq;
    }

    public double getBicepDer() {
        return bicepDer;
    }

    public void setBicepDer(double bicepDer) {
        this.bicepDer = bicepDer;
    }

    public double getCuadricepIzq() {
        return cuadricepIzq;
    }

    public void setCuadricepIzq(double cuadricepIzq) {
        this.cuadricepIzq = cuadricepIzq;
    }

    public double getCuadricepDer() {
        return cuadricepDer;
    }

    public void setCuadricepDer(double cuadricepDer) {
        this.cuadricepDer = cuadricepDer;
    }

    public double getPantorrillaDer() {
        return pantorrillaDer;
    }

    public void setPantorrillaDer(double pantorrillaDer) {
        this.pantorrillaDer = pantorrillaDer;
    }

    public double getPantorrillaIzq() {
        return pantorrillaIzq;
    }

    public void setPantorrillaIzq(double pantorrillaIzq) {
        this.pantorrillaIzq = pantorrillaIzq;
    }

    public double getPectoral() {
        return pectoral;
    }

    public void setPectoral(double pectoral) {
        this.pectoral = pectoral;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getMedidakey() {
        return medidakey;
    }

    public void setMedidakey(int medidakey) {
        this.medidakey = medidakey;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public double getTricipital() {
        return tricipital;
    }

    public void setTricipital(double tricipital) {
        this.tricipital = tricipital;
    }

    public double getSubescapular() {
        return subescapular;
    }

    public void setSubescapular(double subescapular) {
        this.subescapular = subescapular;
    }

    public double getBicipital() {
        return bicipital;
    }

    public void setBicipital(double bicipital) {
        this.bicipital = bicipital;
    }

    public double getSuprailiaco() {
        return suprailiaco;
    }

    public void setSuprailiaco(double suprailiaco) {
        this.suprailiaco = suprailiaco;
    }

    public double getMuneca() {
        return muneca;
    }

    public void setMuneca(double muneca) {
        this.muneca = muneca;
    }

    @Override
    public String toString() {
        return "Medida del " + getFecha().format(DateTimeFormatter.ofPattern("d MMM"));
    }

    public String getComplexionText() {
        double c = getAltura() / getMuneca();
        switch (getCliente().getSexo().toLowerCase()) {
            case "hombre":
                if (c < 9.6) {//Grande
                    return "Grande";
                } else if (c >= 9.6 && c <= 10.4) {//mediano
                    return "Mediana";
                } else {//pequeño
                    return "Pequeña";
                }
            case "mujer":
                if (c < 10.1) {//Grande
                    return "Grande";
                } else if (c >= 10.1 && c <= 11) {//mediano
                    return "Mediana";
                } else {//pequeño
                    return "Pequeña";
                }
            default:
                return "null";
        }
    }

    /**
     * Devuelve una descripción del grado de obesidad segun el IMC
     *
     * @return
     */
    public String getGradoObesidad() {
        double c = getIndiceMasaCorporal();
        if (c < 16) {
            return "Desnutrición grave";
        } else if (c > 16 & c < 17) {
            return "Desnutrición moderada";
        } else if (c > 17 & c < 18.5) {
            return "Desnutrición leve";
        } else if (c > 18.5 & c < 25) {
            return "Normopeso";
        } else if (c > 25 & c < 27) {
            return "Sobrepeso grado I";
        } else if (c > 27 & c < 30) {
            return "Sobrepeso grado II (preobesidad)";
        } else if (c > 30 & c < 35) {
            return "Obesidad grado I ";
        } else if (c > 35 & c < 39) {
            return "Obesidad grado II";
        } else if (c > 39 & c < 44) {
            return "Obesidad grado III (mórbida)";
        } else /*if (c >= 44)*/ {
            return "Obesidad grado IV (extrema)";
        }
    }

    /**
     * Formula de peso ideal de Creff
     *
     * @return
     */
    public double getPesoIdealCreff() {
        switch (getComplexionText()) {
            case "Grande":
                return ((getAltura() - 100) + (getCliente().getEdad()) / 10) * 0.99;
            case "Mediana":
                return ((getAltura() - 100) + (getCliente().getEdad()) / 10) * 0.9;
            case "Pequeña":
                return ((getAltura() - 100) + (getCliente().getEdad()) / 10) * 0.81;
            default:
                return -1;
        }
    }

    /**
     * Formula de peso ideal aproximado
     */
    public double getPesoIdealAprox() {
        return getAltura() - 100;
    }

    /**
     * Formula de peso ideal de Lorentz
     */
    public double getPesoIdealLorentz() {
        switch (getCliente().getSexo().toLowerCase()) {
            case "hombre":
                return getAltura() - 100 - (getAltura() - 150) / 4;
            case "mujer":
                return getAltura() - 100 - (getAltura() - 150) / 2.5;
            default:
                return -1;
        }
    }

    /**
     * Formula de peso ideal de Lorentz
     */
    public double getPesoIdealMonnerotDumaine() {
        return (getAltura() - 100 + 4 * getMuneca()) / 2;
    }

    /**
     * Índice de masa corporal: Peso sobre altura al cuadrado, el resultado es
     * en kg/m^2
     */
    public double getIndiceMasaCorporal() {
        double a = (getAltura() / 100) * (getAltura() / 100);
        return (getPeso() / a);
    }

    public double getTasaMetabolicaMifflin() {
        switch (getCliente().getSexo().toLowerCase()) {
            case "mujer":
                return -161 + 10 * getPeso() + 6.25 * getAltura() - 5 * getCliente().getEdad();
            case "hombre":
                return 5 + 10 * getPeso() + 6.25 * getAltura() - 5 * getCliente().getEdad();
            default:
                return -1;
        }
    }

    public double getTasaMetabolicaHarrys() {
        switch (getCliente().getSexo().toLowerCase()) {
            case "mujer":
                return (665 + 9.6 * getPeso()
                        + 1.85 * getAltura() - 4.7 * getCliente().getEdad());
            case "hombre":
                return (66.5 + 13.7 * getPeso()
                        + 5 * getAltura() - 6.8 * getCliente().getEdad());
            default:
                return -1;
        }
    }

    /**
     * Obtiene las calorías que debe consumir la persona dependiendo de su TMB
     *
     * @return
     */
    public double getCaloriasMantenimiento() {
        double tasa = getTasaMetabolicaHarrys();
        switch (getActividad().toLowerCase()) {
            case "ninguno: 0 dias x semana":
                return tasa * 1.2;
            case "ligero: 1 a 3 días x semana":
                return tasa * 1.375;
            case "moderado: 3 a 5 días x semana":
                return tasa * 1.55;
            case "deportista: 6 a 7 días x semana":
                return tasa * 1.72;
            case "atleta: dos veces al dia":
                return tasa * 1.9;
            default:
                return -1;
        }
    }

    //c
    public double getCoeficienteC() {
        switch (getCliente().getSexo()) {
            case "hombre":
                if (getCliente().getEdad() >= 16 && getCliente().getEdad() <= 19) {
                    return (1.1620);
                } else if (getCliente().getEdad() >= 20 && getCliente().getEdad() <= 29) {
                    return (1.1631);
                } else if (getCliente().getEdad() >= 30 && getCliente().getEdad() <= 39) {
                    return (1.1422);
                } else if (getCliente().getEdad() >= 40 && getCliente().getEdad() <= 49) {
                    return (1.1620);
                } else if (getCliente().getEdad() >= 50) {
                    return (1.1714);
                }
                break;
            case "mujer":
                if (getCliente().getEdad() >= 16 && getCliente().getEdad() <= 19) {
                    return (1.1549);
                } else if (getCliente().getEdad() >= 20 && getCliente().getEdad() <= 29) {
                    return (1.1599);
                } else if (getCliente().getEdad() >= 30 && getCliente().getEdad() <= 39) {
                    return (1.1423);
                } else if (getCliente().getEdad() >= 40 && getCliente().getEdad() <= 49) {
                    return (1.1333);
                } else if (getCliente().getEdad() >= 50) {
                    return (1.1339);
                }
                break;
        }
        return -1;
    }

    //m
    public double getCoeficienteM() {
        switch (getCliente().getSexo().toLowerCase()) {
            case "hombre":
                if (getCliente().getEdad() >= 16 && getCliente().getEdad() <= 19) {
                    return (0.0630);
                } else if (getCliente().getEdad() >= 20 && getCliente().getEdad() <= 29) {
                    return (0.0632);
                } else if (getCliente().getEdad() >= 30 && getCliente().getEdad() <= 39) {
                    return (0.0544);
                } else if (getCliente().getEdad() >= 40 && getCliente().getEdad() <= 49) {
                    return (0.0700);
                } else if (getCliente().getEdad() >= 50) {
                    return (0.0779);
                }
                break;
            case "mujer":
                if (getCliente().getEdad() >= 16 && getCliente().getEdad() <= 19) {
                    return (0.0678);
                } else if (getCliente().getEdad() >= 20 && getCliente().getEdad() <= 29) {
                    return (0.0717);
                } else if (getCliente().getEdad() >= 30 && getCliente().getEdad() <= 39) {
                    return (0.0632);
                } else if (getCliente().getEdad() >= 40 && getCliente().getEdad() <= 49) {
                    return (0.0612);
                } else if (getCliente().getEdad() >= 50) {
                    return (0.0645);
                }
                break;
        }
        return -1;
    }

    /**
     * La densidad corporal se obtiene mediante la ecuación de regresión lineal
     * propuesta por Durning & Womersley en 1974
     */
    public double getDensidadCorporalPorPliegues() {
        if (getBicipital() == 0 && getTricipital() == 0 && getSubescapular() == 0 && getSuprailiaco() == 0) {
            return -1;
        }
        return getCoeficienteC() - (getCoeficienteM()
                * Math.log10(getTricipital() + getBicipital()
                + getSubescapular() + getSuprailiaco()));
    }

    /**
     * La ecuación de Siri (1956) utiliza la densidad corporal para calcular el
     * porcentaje de masa grasa
     */
    public double getPorcentajeGrasa() {
        if (getBicipital() == 0 || getTricipital() == 0 || getSubescapular() == 0 || getSuprailiaco() == 0) {
            switch (getCliente().getSexo().toLowerCase()) {
                case "hombre":
                    return 1.39 * getIndiceMasaCorporal() + 0.16 * getCliente().getEdad() - 10.34 - 9;
                case "mujer":
                    return 1.39 * getIndiceMasaCorporal() + 0.16 * getCliente().getEdad() - 9;
            }
        }
        return ((4.95 / getDensidadCorporalPorPliegues()) - 4.5) * 100;
    }

    /**
     * Para calcular el peso de la grasa corporal se utiliza el porcentaje de
     * masa grasa (Siri) y el peso en kg de la persona dividido entre 100.
     */
    public double getPesoGrasaCorporal() {
        return getPorcentajeGrasa() * getPeso() / 100;
    }

    /**
     * Para calcular el porcentaje de masa magra se divide el porcentaje de
     * grasa (siri) entre el peso total en kg
     */
    public double getPorcentajeMasaMagra() {
        return 100 - getPorcentajeGrasa();
    }

    /**
     * Para calcular la masa magra (masa libre de grasa) se utiliza el peso en
     * kg y el peso de grasa corporal (Siri)
     */
    public double getMasaLibreDeGrasa() {
        return getPeso() - getPesoGrasaCorporal();
    }

    public double getSuperavitODeficit() {
        double mant = getCaloriasMantenimiento();
        switch (getObjetivo().toLowerCase()) {
            case "aumentar":
                return mant + mant * 0.25;
            case "perder":
                return mant - mant * 0.25;
            case "mantener":
                return mant;
        }
        return -1;
    }

    /**
     * Este método valida si hace falta llenar algún campo en el objeto de
     * medida
     *
     * @return true si falta alguno y false si no falta ninguno
     */
    public boolean isEmpty() {
        if (getCliente() == null || getFecha() == null) {
            return true;
        }
        return getCliente().isEmpty();

    }

    public String[][] toArray() {

        String[][] n = new String[26][2];

        n[0][0] = "FECHA";
        n[1][0] = "PESO";
        n[2][0] = "GRADO ACTIVIDAD";
        n[3][0] = "GRADO OBESIDAD";
        n[4][0] = "% GRASA CORP.";
        n[5][0] = "GRASA CORP.";
        n[6][0] = "% MASA MAGRA";
        n[7][0] = "MASA MAGRA";
        n[8][0] = "KCAL MANT.";
        n[9][0] = "KCAL OBJETIVO";
        n[10][0] = "PLI. TRICIPITAL";
        n[11][0] = "PLI. BICIPITAL";
        n[12][0] = "PLI. SUPRAILIACO";
        n[13][0] = "PLI. SUBESC.";
        n[14][0] = "CUELLO";
        n[15][0] = "BICEP IZQ.";
        n[16][0] = "BICEP DER.";
        n[17][0] = "PECTORALES";
        n[18][0] = "CINTURA ALTA";
        n[19][0] = "CINTURA MEDIA";
        n[20][0] = "CINTURA BAJA";
        n[21][0] = "CADERA";
        n[22][0] = "CUAD. IZQ.";
        n[23][0] = "CUAD. DER.";
        n[24][0] = "GEMELO IZQ.";
        n[25][0] = "GEMELO DER.";

        n[0][1] = getFecha().format(DateTimeFormatter.ofPattern("d MMM Y")).toUpperCase();
        n[1][1] = Operacion.formatear(getPeso()).replaceAll("\\.0", "") + " Kg";
        int s = getActividad().indexOf(": ");
        n[2][1] = getActividad().substring(s + 1).toUpperCase();
        n[3][1] = getGradoObesidad().toUpperCase();
        n[4][1] = Operacion.formatear(getPorcentajeGrasa()).replaceAll("\\.0", "") + " %";
        n[5][1] = Operacion.formatear(getPesoGrasaCorporal()).replaceAll("\\.0", "") + " Kg";
        n[6][1] = Operacion.formatear(getPorcentajeMasaMagra()).replaceAll("\\.0", "") + " %";
        n[7][1] = Operacion.formatear(getMasaLibreDeGrasa()).replaceAll("\\.0", "") + " Kg";
        n[8][1] = getCaloriasMantenimiento() + " kcal";
        n[9][1] = Operacion.formatear(getSuperavitODeficit()).replaceAll("\\.0", "") + " kcal";
        n[10][1] = Operacion.formatear(getTricipital()).replaceAll("\\.0", "") + " mm";
        n[11][1] = Operacion.formatear(getBicipital()).replaceAll("\\.0", "") + " mm";
        n[12][1] = Operacion.formatear(getSuprailiaco()).replaceAll("\\.0", "") + " mm";
        n[13][1] = Operacion.formatear(getSubescapular()).replaceAll("\\.0", "") + " mm";
        n[14][1] = getCuello() + " cm";
        n[15][1] = Operacion.formatear(getBicepIzq()).replaceAll("\\.0", "") + " cm";
        n[16][1] = Operacion.formatear(getBicepDer()).replaceAll("\\.0", "") + " cm";
        n[17][1] = Operacion.formatear(getPectoral()).replaceAll("\\.0", "") + " cm";
        n[18][1] = Operacion.formatear(getCinturaAlta()).replaceAll("\\.0", "") + " cm";
        n[19][1] = Operacion.formatear(getCinturaMedia()).replaceAll("\\.0", "") + " cm";
        n[20][1] = Operacion.formatear(getCinturaBaja()).replaceAll("\\.0", "") + " cm";
        n[21][1] = Operacion.formatear(getCadera()).replaceAll("\\.0", "") + " cm";
        n[22][1] = Operacion.formatear(getCuadricepIzq()).replaceAll("\\.0", "") + " cm";
        n[23][1] = Operacion.formatear(getCuadricepDer()).replaceAll("\\.0", "") + " cm";
        n[24][1] = Operacion.formatear(getPantorrillaIzq()).replaceAll("\\.0", "") + " cm";
        n[25][1] = Operacion.formatear(getPantorrillaDer()).replaceAll("\\.0", "") + " cm";

        return n;
    }

}
