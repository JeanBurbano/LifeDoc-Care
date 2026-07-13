package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Cita {

    private final int idCita;
    private final boolean estado;
    private final LocalTime horaCita;
    private final LocalDate fechaCita;
    private final int idUsuario;
    private final String nombrePaciente;
    private final int idMedico;
    private final String nombreMedico;
    private final String especialidad;
    private final int idUsuarioAgenda;

    public Cita(byte idCita, boolean estado, LocalTime horaCita, LocalDate fechaCita,
            byte idUsuario, String nombrePaciente, byte idMedico, String nombreMedico,
            String especialidad, byte idUsuarioAgenda) {
        this.idCita = idCita;
        this.estado = estado;
        this.horaCita = horaCita;
        this.fechaCita = fechaCita;
        this.idUsuario = idUsuario;
        this.nombrePaciente = nombrePaciente;
        this.idMedico = idMedico;
        this.nombreMedico = nombreMedico;
        this.especialidad = especialidad;
        this.idUsuarioAgenda = idUsuarioAgenda;
    }

    public int getIdCita() {
        return idCita;
    }

    public boolean getEstado() {
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

    public String getEspecialidad() {
        return especialidad;
    }
}
