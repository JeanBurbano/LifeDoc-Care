package model;

import java.time.LocalDate;

public class EstadisticaCitas {

    private int idEstadistica;
    private LocalDate fecha;
    private int citasAgendadas;
    private int citasConfirmadas;
    private int citasCanceladas;
    private int citasReagendadas;
    private int citasAtendidas;

    public EstadisticaCitas(int idEstadistica, LocalDate fecha, int citasAgendadas, int citasConfirmadas, int citasCanceladas, int citasReagendadas, int citasAtendidas) {
        this.idEstadistica = idEstadistica;
        this.fecha = fecha;
        this.citasAgendadas = citasAgendadas;
        this.citasConfirmadas = citasConfirmadas;
        this.citasCanceladas = citasCanceladas;
        this.citasReagendadas = citasReagendadas;
        this.citasAtendidas = citasAtendidas;
    }

    public void setIdEstadistica(int idEstadistica) {
        this.idEstadistica = idEstadistica;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setCitasAgendadas(int citasAgendadas) {
        this.citasAgendadas = citasAgendadas;
    }

    public void setCitasConfirmadas(int citasConfirmadas) {
        this.citasConfirmadas = citasConfirmadas;
    }

    public void setCitasCanceladas(int citasCanceladas) {
        this.citasCanceladas = citasCanceladas;
    }

    public void setCitasReagendadeas(int citasReagendadas) {
        this.citasReagendadas = citasReagendadas;
    }

    public void setCitasAtendidas(int citasAtendidas) {
        this.citasAtendidas = citasAtendidas;
    }

    public int getIdEstadistica() {
        return idEstadistica;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public int getCitasAgendadas() {
        return citasAgendadas;
    }

    public int getCitasConfirmada() {
        return citasConfirmadas;
    }

    public int getCitaCancelada() {
        return citasCanceladas;
    }

    public int getCitaReagendada() {
        return citasReagendadas;
    }
    
    public int getCitaAgendida(){
        return citasAtendidas;
    }
}