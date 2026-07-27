package model;

import java.time.LocalDate;

public class Paciente extends Usuario {

    private int idPaciente;

    public Paciente() {

    }

    public Paciente(int idUsuario, byte idRol, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, byte edad, String correo, String numeroCelular, boolean esttado) {

    }

    public Paciente(int idUsuario, byte idRol, byte idTipoIdentificacion,
            String numeroIdentificacion, String primerNombre, String segundoNombre,
            String primerApellido, String segundoApellido, String correo, String contraseña,
            LocalDate fechaNacimiento, String sexoBiologico, String numeroCelular, byte edad, String sisben, boolean estado, String fotoPerfil, int idPaciente) {

        super(idUsuario, idRol, idTipoIdentificacion,
                numeroIdentificacion, primerNombre, segundoNombre,
                primerApellido, segundoApellido, correo, contraseña,
                fechaNacimiento, sexoBiologico, numeroCelular, edad, sisben, estado, fotoPerfil);

        this.idPaciente = idPaciente;
    }
}
