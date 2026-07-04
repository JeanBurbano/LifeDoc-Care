package model;

import java.time.LocalDate;

public class Medico extends Usuarios {

    private byte nConsultorio;
    private String especialidad;

    public Medico(String id_rol, String tipoId, String numeroId, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido,
            String correoElectronico, String contrasena, String sexoBiologico, String numeroTelefonico, String sisben,
            LocalDate fechaNacimiento, byte edad, byte nConsultorio, String especialidad) {

        super(id_rol,tipoId, numeroId, primerNombre, segundoNombre, primerApellido, segundoApellido,
                correoElectronico, contrasena, sexoBiologico, numeroTelefonico, sisben,
                fechaNacimiento, edad);

        this.nConsultorio = nConsultorio;
        this.especialidad = especialidad;
    }

    public byte getnConsultorio() {
        return nConsultorio;
    }

    public void setnConsultorio(byte nConsultorio) {
        this.nConsultorio = nConsultorio;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
}
