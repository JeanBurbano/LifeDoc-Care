/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


import java.util.ArrayList;
import java.util.List;

/**
 * Clase para calcular los datos usados en los combos de
 * hora del formulario de creación de horarios.
 */
public class GeneradorHorarios {

    private GeneradorHorarios() {
    } // no se instancia, solo se usan sus métodos estáticos

    /**
     * Genera el arreglo de horas disponibles para los combos de hora,
     * desde las 07:00 hasta las 17:00, en pasos de 30 minutos.
     *
     * @return arreglo de horas en formato "HH:mm"
     */
    public static String[] generarHoras() {
        List<String> lista = new ArrayList<>(); // lista temporal para ir agregando las horas
        for (int h = 7; h <= 17; h++) { // recorre cada hora desde las 7 hasta las 17
            for (int m : new int[]{0, 30}) { // dentro de cada hora, agrega el minuto 0 y el 30
                lista.add(String.format("%02d:%02d", h, m)); // formatea como "07:00", "07:30", etc.
            }
        }
        return lista.toArray(new String[0]); // convierte la lista a arreglo antes de retornar
    }
}
