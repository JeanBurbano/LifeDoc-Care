/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.List;

/**
 *
 * @author lunaa
 */
public class Horario {
    private int id;
    private String nombre;
    private String colorEtiqueta;
    private List<HorarioDia> dias;

    public Horario() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getColorEtiqueta() {
        return colorEtiqueta;
    }

    public void setColorEtiqueta(String colorEtiqueta) {
        this.colorEtiqueta = colorEtiqueta;
    }

    public List<HorarioDia> getDias() {
        return dias;
    }

    public void setDias(List<HorarioDia> dias) {
        this.dias = dias;
    }
    
}
