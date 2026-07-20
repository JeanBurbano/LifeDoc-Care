/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author lunaa
 */
public class HorarioDia {
    private String diaSemana;
    private String horaInicio;
    private String horaFin;
    private String descansoInicio;
    private String descansoFin;
    
    public HorarioDia(){}
    
    public HorarioDia(String diaSemana, String horaInicio, String horaFin, String descansoInicio, String descansoFin){
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.descansoInicio = descansoInicio;
        this.descansoFin = descansoFin;
    }
    
    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public String getDescansoInicio() {
        return descansoInicio;
    }

    public void setDescansoInicio(String descansoInicio) {
        this.descansoInicio = descansoInicio;
    }

    public String getDescansoFin() {
        return descansoFin;
    }

    public void setDescansoFin(String descansoFin) {
        this.descansoFin = descansoFin;
    }
    
    
}


