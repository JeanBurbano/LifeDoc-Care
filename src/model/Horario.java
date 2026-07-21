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
    private String fechaCreacion;
    private List<HorarioDia> dias;
    private Integer idMedico;
    private Integer idConsultorio;
    private Integer mes;
    private Integer anio;

    public Integer getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(Integer idMedico) {
        this.idMedico = idMedico;
    }

    public Integer getIdConsultorio() {
        return idConsultorio;
    }

    public void setIdConsultorio(Integer idConsultorio) {
        this.idConsultorio = idConsultorio;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

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

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    
}
