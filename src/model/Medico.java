package model;

import java.time.LocalDate;

public class Medico extends Paciente {

    private String especialidad;

    public Medico(int id_usuario, byte id_rol, String tipoId, String numeroId, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido,
            String correo, LocalDate fechaNacimiento, String sexoBiologico, String numeroTelefonico, byte edad, boolean estado, String sisben, String fotoPerfil, String especialidad) {
        super(id_usuario,
                id_rol,
                tipoId,
                numeroId,
                primerNombre,
                segundoNombre,
                primerApellido,
                segundoApellido,
                correo,
                fechaNacimiento,
                sexoBiologico,
                numeroTelefonico,
                edad,
                estado,
                sisben,
                fotoPerfil);
        this.especialidad = especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getEspecialidad() {
        return this.especialidad;
    }
}
