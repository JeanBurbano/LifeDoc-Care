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

    // constructor de copia, se usa cuando hay que cambiar el estado de una cita
    // sin perder el resto de los datos (los campos son final, no hay setters)
    public Cita(Cita origen, boolean nuevoEstado) {
        this.idCita = origen.idCita;
        this.estado = nuevoEstado;
        this.horaCita = origen.horaCita;
        this.fechaCita = origen.fechaCita;
        this.idUsuario = origen.idUsuario;
        this.nombrePaciente = origen.nombrePaciente;
        this.idMedico = origen.idMedico;
        this.nombreMedico = origen.nombreMedico;
        this.especialidad = origen.especialidad;
        this.idUsuarioAgenda = origen.idUsuarioAgenda;
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

    // dos citas se consideran iguales si son del mismo medico en la misma
    // fecha y hora, esto es lo que permite detectar choques de horario
    // cuando se usa el hashSet global setCitas
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Cita)) {
            return false;
        }
        Cita otra = (Cita) obj;
        return idMedico == otra.idMedico
                && fechaCita.equals(otra.fechaCita)
                && horaCita.equals(otra.horaCita);
    }

    @Override
    public int hashCode() {
        int resultado = Integer.hashCode(idMedico);
        resultado = 31 * resultado + fechaCita.hashCode();
        resultado = 31 * resultado + horaCita.hashCode();
        return resultado;
    }
}