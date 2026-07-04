package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class CalculadorPago {

    // Aqui defino el valor base de una consulta medica general
    private static final double VALOR_BASE_CONSULTA = 68000.0;

    // Aqui creo el metodo que calcula el subsidio segun el grupo SISBEN del paciente
    // Grupo A = 100% subsidiado, Grupo B = 87.5%, Grupo C = 75%, fuera del regimen = 0%
    public static double calcularSubsidio(String grupoSisben) {
        switch (grupoSisben.toUpperCase()) {
            case "A1": case "A2": case "A3": case "A4":
                return 1.0; // 100% subsidiado
            case "B1": case "B2": case "B3": case "B4": case "B5": case "B6": case "B7":
                return 0.875; // 87.5% subsidiado
            case "C1": case "C2": case "C3": case "C4": case "C5": case "C6": case "C7": case "C8":
                return 0.75; // 75% subsidiado
            default:
                return 0.0; // Sin subsidio (regimen contributivo o particular)
        }
    }

    // Aqui creo el metodo que calcula el valor neto que debe pagar el paciente
    public static double calcularValorNeto(double valorConsulta, String grupoSisben) {
        double porcentajeSubsidio = calcularSubsidio(grupoSisben);
        double montoSubsidio = valorConsulta * porcentajeSubsidio;
        return valorConsulta - montoSubsidio;
    }

    // Aqui creo el metodo que calcula el monto del subsidio en pesos
    public static double calcularMontoSubsidio(double valorConsulta, String grupoSisben) {
        return valorConsulta * calcularSubsidio(grupoSisben);
    }

    // Aqui creo el metodo que genera el codigo unico de factura
    // Formato: F-YYYYMMDD-XXXX (año+mes+dia + 4 caracteres unicos)
    public static String generarCodigoFactura() {
        LocalDateTime ahora = LocalDateTime.now();
        String fecha = ahora.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String unico = UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        return "F-" + fecha + "-" + unico;
    }

    // Aqui creo el metodo que retorna la fecha y hora actual formateada para mostrar en la factura
    public static String obtenerFechaHoraActual() {
        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy, hh:mm a");
        return ahora.format(formato);
    }

    // Aqui creo el metodo que retorna solo la hora actual formateada
    public static String obtenerHoraActual() {
        LocalDateTime ahora = LocalDateTime.now();
        return ahora.format(DateTimeFormatter.ofPattern("hh:mm a"));
    }

    // Aqui creo el metodo que formatea un valor double a texto en pesos colombianos
    public static String formatearPesos(double valor) {
        return String.format("$%,.0f COP", valor);
    }

    // Aqui creo el metodo que retorna el valor base de consulta segun la especialidad
    public static double obtenerValorConsulta(String especialidad) {
        switch (especialidad.toLowerCase()) {
            case "medico general":
                return 68000.0;
            case "odontologia":
                return 85000.0;
            case "dermatologia":
                return 120000.0;
            default:
                return VALOR_BASE_CONSULTA;
        }
    }
}
