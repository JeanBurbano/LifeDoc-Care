package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class HistorialMedico {

    private int id_historial_medico;
    private int id_usuario;
    private int id_medico;
    private int id_cita;
    private LocalDate dia;
    private LocalTime hora;
    private String descripcion;
    private String nombrePaciente;
    private String nombreMedico;

    public HistorialMedico() {

    }

    public HistorialMedico(int id_usuario, int id_medico, int id_cita, LocalDate dia, LocalTime hora, String descripcion) {
        this.id_usuario = id_usuario;
        this.id_medico = id_medico;
        this.id_cita = id_cita;
        this.dia = dia;
        this.hora = hora;
        this.descripcion = descripcion;
    }

    public int getId_historial_medico() {
        return id_historial_medico;
    }

    public void setId_historial_medico(int id_historial_medico) {
        this.id_historial_medico = id_historial_medico;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId_medico() {
        return id_medico;
    }

    public void setId_medico(int id_medico) {
        this.id_medico = id_medico;
    }

    public int getId_cita() {
        return id_cita;
    }

    public void setId_cita(int id_cita) {
        this.id_cita = id_cita;
    }

    public LocalDate getDia() {
        return dia;
    }

    public void setDia(LocalDate dia) {
        this.dia = dia;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getNombreMedico() {
        return nombreMedico;
    }

    public void setNombreMedico(String nombreMedico) {
        this.nombreMedico = nombreMedico;
    }
}
