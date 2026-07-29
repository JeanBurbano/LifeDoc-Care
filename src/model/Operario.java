package model;

import java.time.LocalDate;

public class Operario extends Usuario {

    private int id_operario;

    public Operario(int id_usuario, byte id_rol, byte tipoId, String numeroId,
            String primerNombre, String segundoNombre, String primerApellido,
            String segundoApellido, String correo, String contrasena, LocalDate fechaNacimiento,
            String sexoBiologico, String numeroTelefonico, byte edad,
            String sisben, boolean estado, String fotoPerfil) {

        super(id_usuario, id_rol, tipoId, numeroId, primerNombre, segundoNombre, primerApellido, segundoApellido,
                correo, contrasena, fechaNacimiento, sexoBiologico, numeroTelefonico, edad, sisben, estado,
                fotoPerfil);
    }

    public Operario(int id_operario, String primerNombre, String primerApellido) {
        super(primerNombre, primerApellido);
        this.id_operario = id_operario;
    }

    public Operario() {
        super();
    }

    public int getId_operario() {
        return id_operario;
    }

    public void setId_operario(int id_operario) {
        this.id_operario = id_operario;
    }

    public int getId_usuario() {
        return super.getIdUsuario();
    }

    public void setId_usuario(int id_usuario) {
        super.setIdUsuario(id_usuario);
    }

}
