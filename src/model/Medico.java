package model;

public class Medico {

    private final int idMedico;
    private final int idUsuario;
    private final String nombreCompleto;
    private final int idEspecialidad;
    private final String especialidad;

    public Medico(int idMedico, int idUsuario, String nombreCompleto, int idEspecialidad, String especialidad) {
        this.idMedico = idMedico;
        this.idUsuario = idUsuario;
        this.nombreCompleto = nombreCompleto;
        this.idEspecialidad = idEspecialidad;
        this.especialidad = especialidad;
    }

    public int getIdMedico() {
        return idMedico;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public int getIdEspecialidad() {
        return idEspecialidad;
    }

    public String getEspecialidad() {
        return especialidad;
    }
}