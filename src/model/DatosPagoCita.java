package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class DatosPagoCita {
    
    private int idCita;
    private String nombrePaciente;
    private String identificacionPaciente;
    private String sisben;
    private String especialidad;
    private String nombreMedico;
    private LocalDate fechaCita;
    private LocalTime horaCita;
    
    public DatosPagoCita(int idCita, String nombrePaciente, String identificacionPaciente,
            String sisben, String especialidad, String nombreMedico,
            LocalDate fechaCita, LocalTime horaCita) {
        this.idCita = idCita;
        this.nombrePaciente = nombrePaciente;
        this.identificacionPaciente = identificacionPaciente;
        this.sisben = sisben;
        this.especialidad = especialidad;
        this.nombreMedico = nombreMedico;
        this.fechaCita = fechaCita;
        this.horaCita = horaCita;
    }

    public int getIdCita() {
        return idCita;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public String getIdentificacionPaciente() {
        return identificacionPaciente;
    }

    public String getSisben() {
        return sisben;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public String getNombreMedico() {
        return nombreMedico;
    }

    public LocalDate getFechaCita() {
        return fechaCita;
    }

    public LocalTime getHoraCita() {
        return horaCita;
    }
    
    public String getRegimen() {
        return (sisben == null || sisben.isBlank()) ? "Contributivo / Particular" : "Subsidiado";
    }
    
    public String getClasificacionSisbenTexto() {
        
        if (sisben == null || sisben.isBlank()) {
            return "Sin clasificar";
        }
        return "Grupo " + sisben.charAt(0);
        
    }
}
