package model;

import java.time.LocalDate;

public class Medico extends Paciente {

    private String especialidad;
    private byte nConsultorio;

    //constructor medico
    public Medico(int id_usuario, byte id_rol, String tipoId, String numeroId, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido,
            String correoElectronico, String contrasena, String sexoBiologico, String numeroTelefonico, String sisben, String fotoPerfil, String especialidad,
            LocalDate fechaNacimiento, byte edad, boolean estado, byte nConsultorio) {

        super(id_usuario, id_rol, tipoId, numeroId, primerNombre, segundoNombre, primerApellido, segundoApellido,
                correoElectronico, contrasena, sexoBiologico, numeroTelefonico, sisben, fotoPerfil,
                fechaNacimiento, edad, estado);
        this.especialidad = especialidad;
        this.nConsultorio = nConsultorio;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public byte getnConsultorio() {
        return nConsultorio;
    }

    public void setnConsultorio(byte nConsultorio) {
        this.nConsultorio = nConsultorio;
    }
}
