package model;

import java.time.LocalDate;

public class Usuarios {

    private String tipoId, numeroId, primerNombre, segundoNombre, primerApellido, segundoApellido,
            correoElectronico, contrasena, sexoBiologico, numeroTelefonico, sisben, fotoPerfil, especialidad;
    private LocalDate fechaNacimiento;
    private byte id_rol,edad, nConsultorio;
    private int id_usuario;

    public Usuarios() {

    }

    public Usuarios(int id_usuario, byte id_rol, String primer_nombre, String url) {
        this.id_usuario = id_usuario;
        this.id_rol = id_rol;
        this.primerNombre = primer_nombre;
        this.fotoPerfil = url;
    }

    public Usuarios(int id_usuario, byte id_rol, String tipoId, String numeroId, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido,
            String correoElectronico, String contrasena, String sexoBiologico, String numeroTelefonico, String sisben, String fotoPerfil,
            LocalDate fechaNacimiento, byte edad) {

        this.id_usuario = id_usuario;
        this.id_rol = id_rol;
        this.tipoId = tipoId;
        this.numeroId = numeroId;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
        this.sexoBiologico = sexoBiologico;
        this.numeroTelefonico = numeroTelefonico;
        this.sisben = sisben;
        this.fotoPerfil = fotoPerfil;
        this.fechaNacimiento = fechaNacimiento;
        this.edad = edad;
    }

    public Usuarios(int id_usuario, byte id_rol, String tipoId, String numeroId, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido,
            String correoElectronico, String contrasena, String sexoBiologico, String numeroTelefonico, String sisben, String fotoPerfil, String especialidad,
            LocalDate fechaNacimiento, byte edad, byte nConsultorio) {

        this.id_usuario = id_usuario;
        this.id_rol = id_rol;
        this.tipoId = tipoId;
        this.numeroId = numeroId;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
        this.sexoBiologico = sexoBiologico;
        this.numeroTelefonico = numeroTelefonico;
        this.sisben = sisben;
        this.fotoPerfil = fotoPerfil;
        this.especialidad = especialidad;
        this.fechaNacimiento = fechaNacimiento;
        this.edad = edad;
        this.nConsultorio = nConsultorio;
    }

    public byte getId_rol() {
        return id_rol;
    }

    public void setId_rol(byte id_rol) {
        this.id_rol = id_rol;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public int getIdUsuario() {
        return id_usuario;
    }

    public void setIdUsuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getTipoId() {
        return tipoId;
    }

    public void setTipoId(String tipoId) {
        this.tipoId = tipoId;
    }

    public String getNumeroId() {
        return numeroId;
    }

    public void setNumeroId(String numeroId) {
        this.numeroId = numeroId;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getSexoBiologico() {
        return sexoBiologico;
    }

    public void setSexoBiologico(String sexoBiologico) {
        this.sexoBiologico = sexoBiologico;
    }

    public String getNumeroTelefonico() {
        return numeroTelefonico;
    }

    public void setNumeroTelefonico(String numeroTelefonico) {
        this.numeroTelefonico = numeroTelefonico;
    }

    public String getSisben() {
        return sisben;
    }

    public void setSisben(String sisben) {
        this.sisben = sisben;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public byte getEdad() {
        return edad;
    }

    public void setEdad(byte edad) {
        this.edad = edad;
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
