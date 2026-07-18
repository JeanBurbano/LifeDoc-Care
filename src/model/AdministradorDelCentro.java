package model;

import java.time.LocalDate;

public class AdministradorDelCentro extends Paciente {

    private int id_usuarioA;
    private int id_administrador_centro;

    public AdministradorDelCentro(int id_usuario, int id_administrador_centro) {
        this.id_usuarioA = id_usuario;
        this.id_administrador_centro = id_administrador_centro;
    }

    public AdministradorDelCentro(int id_usuario, byte id_rol, String tipoId, String numeroId, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido,
            String correo, LocalDate fechaNacimiento, String sexoBiologico, String numeroTelefonico, byte edad, boolean estado, String sisben, String fotoPerfil) {
        super(id_usuario, id_rol, tipoId, numeroId, primerNombre, segundoNombre, primerApellido, segundoApellido,
                correo, fechaNacimiento, sexoBiologico, numeroTelefonico, edad, estado, sisben, fotoPerfil);
        this.id_usuarioA = id_usuario;
    }

    public AdministradorDelCentro(int id_usuario) {
        this.id_usuarioA = id_usuario;
    }

    public int getId_usuario() {
        return id_usuarioA;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuarioA = id_usuario;
    }

    public int getId_administrador_centro() {
        return id_administrador_centro;
    }

    public void setId_administrador_centro(int id_administrador_centro) {
        this.id_administrador_centro = id_administrador_centro;
    }
}
