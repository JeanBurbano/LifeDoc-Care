package model;

import java.time.LocalDate;

public abstract class Usuario {

    private int idUsuario;
    private byte idRol;
    private String rol;
    private byte idIipoIdentificacion;
    private String tipoIdentificacion;
    private String numeroIdentificacion;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String correo;
    private String contraseña;
    private LocalDate fechaNacimiento;
    private String sexoBiologico;
    private String numeroCelular;
    private byte edad;
    private char sisben;
    private boolean estado;
    private String fotoPerfil;

    public Usuario(int idUsuario, byte idRol, byte idTipoIdentificacion,
            String numeroIdentificacion, String primerNombre, String segundoNombre,
            String primerApellido, String segundoApellido, String correo, String contraseña,
            LocalDate fechaNacimiento, String sexoBiologico, String numeroCelular, byte edad, char sisben, boolean estado, String fotoPerfil) {

        this.idUsuario = idUsuario;
        this.idRol = idRol;
        this.idIipoIdentificacion = idTipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre.isEmpty() ? "null" : segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido.isEmpty() ? "null" : segundoApellido;
        this.correo = correo;
        this.contraseña = contraseña;
        this.fechaNacimiento = fechaNacimiento;
        this.sexoBiologico = sexoBiologico;
        this.numeroCelular = numeroCelular;
        this.edad = edad;
        this.sisben = sisben;
        this.estado = estado;
        this.fotoPerfil = fotoPerfil;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public byte getIdRol() {
        return idRol;
    }

    public void setIdRol(byte idRol) {
        this.idRol = idRol;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public byte getIdIipoIdentificacion() {
        return idIipoIdentificacion;
    }

    public void setIdIipoIdentificacion(byte idIipoIdentificacion) {
        this.idIipoIdentificacion = idIipoIdentificacion;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getSexoBiologico() {
        return sexoBiologico;
    }

    public void setSexoBiologico(String sexoBiologico) {
        this.sexoBiologico = sexoBiologico;
    }

    public String getNumeroCelular() {
        return numeroCelular;
    }

    public void setNumeroCelular(String numeroCelular) {
        this.numeroCelular = numeroCelular;
    }

    public byte getEdad() {
        return edad;
    }

    public void setEdad(byte edad) {
        this.edad = edad;
    }

    public char getSisben() {
        return sisben;
    }

    public void setSisben(char sisben) {
        this.sisben = sisben;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getFotoPeril() {
        return fotoPerfil;
    }

    public void setFotoPerfil() {
        this.fotoPerfil = fotoPerfil;
    }
}
