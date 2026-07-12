package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Cita {
    
    private final int idCita;
    private final String estado;
    private final LocalTime horaCita;
    private final LocalDate fechaCita;
    private final int idUsuario;
    private final String nombrePaciente;
    private final int idMedico;
    private final String nombreMedico;
    private final int idUsuarioAgenda;

    public Cita(int idCita, String estado, LocalTime horaCita, LocalDate fechaCita,
            int idUsuario, String nombrePaciente, int idMedico, String nombreMedico, int idUsuarioAgenda) {
        this.idCita = idCita;
        this.estado = estado;
        this.horaCita = horaCita;
        this.fechaCita = fechaCita;
        this.idUsuario = idUsuario;
        this.nombrePaciente = nombrePaciente;
        this.idMedico = idMedico;
        this.nombreMedico = nombreMedico;
        this.idUsuarioAgenda = idUsuarioAgenda;
    }

    public int getIdCita() {
        return idCita;
    }

    public String getEstado() {
        return estado;
    }

    public LocalTime getHoraCita() {
        return horaCita;
    }

    public LocalDate getFechaCita() {
        return fechaCita;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public int getIdMedico() {
        return idMedico;
    }

    public String getNombreMedico() {
        return nombreMedico;
    }

    public int getIdUsuarioAgenda() {
        return idUsuarioAgenda;
    }
}
