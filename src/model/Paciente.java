package model;

import java.time.LocalDate;

public class Paciente {

    private int id_usuario;
    private String tipoId, numeroId, primerNombre, segundoNombre, primerApellido, segundoApellido,
            correoElectronico, contrasena, sexoBiologico, numeroTelefonico, sisben, fotoPerfil;
    private LocalDate fechaNacimiento;
    private byte id_rol, edad;
    private boolean estado;

    public Paciente() {

    }

    public Paciente(String primerNombre, String primerApellido) {
        this.primerNombre = primerNombre;
        this.primerApellido = primerApellido;
    }

    public Paciente(int id_usuario, byte id_rol, String primerNombre, String segundoNombre,
            String primerApellido, String segundoApellido, byte edad,
            String correoElectronico, String numeroTelefonico, boolean estado) {
        this.id_usuario = id_usuario;
        this.id_rol = id_rol;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.edad = edad;
        this.correoElectronico = correoElectronico;
        this.numeroTelefonico = numeroTelefonico;
        this.estado = estado;
    }

    //contructor datos seguros del paciente
    public Paciente(int id_usuario, byte id_rol, String tipoId, String numeroId, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido,
            String correo, LocalDate fechaNacimiento, String sexoBiologico, String numeroTelefonico, byte edad, boolean estado, String sisben, String fotoPerfil) {
        this.id_usuario = id_usuario;
        this.id_rol = id_rol;
        this.tipoId = tipoId;
        this.numeroId = numeroId;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.correoElectronico = correo;
        this.fechaNacimiento = fechaNacimiento;
        this.sexoBiologico = sexoBiologico;
        this.numeroTelefonico = numeroTelefonico;
        this.edad = edad;
        this.estado = estado;
        this.sisben = sisben;
        this.fotoPerfil = fotoPerfil;
    }

    //constructor paciente
    public Paciente(int id_usuario, byte id_rol, String tipoId, String numeroId, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido,
            String correoElectronico, String contrasena, String sexoBiologico, String numeroTelefonico, String sisben, String fotoPerfil,
            LocalDate fechaNacimiento, byte edad, boolean estado) {

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
        this.estado = estado;
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

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

}
