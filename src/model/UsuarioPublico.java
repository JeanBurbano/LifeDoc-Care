package model;

import java.time.LocalDate;

public class UsuarioPublico implements SegunRol {

    //variable de publicas de un usaurio
    private int idUsuario;
    private byte idRol;
    private String rol;
    private byte idTipoIdentificacion;
    private String tipoIdentificacion;
    private final String numeroIdentificacion;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String correo;
    private final LocalDate fechaNacimiento;
    private final String sexoBiologico;
    private String numeroCelular;
    private byte edad;
    private String sisben;
    private boolean estado;
    private String fotoPerfil;

    //metodo de la implementacion obligatorio
    @Override
    public void cargarSegunRol(byte n) {
        switch (n) {
            case 1:
                rol = "Administrador del sistema";
                break;
            case 2:
                rol = "Administrador del centro";
                break;
            case 3:
                rol = "Medico";
                break;
            case 4:
                rol = "Operario";
                break;
            case 5:
                rol = "Paciente";
                break;
            default:
                rol = "Paciente";
                break;
        }
    }

    //funcion que carga en el atributo tipo de identificaion el tipo de identificacion
    public void cargarTipoIdentificacion(byte n) {
        switch (n) {
            case 1:
                tipoIdentificacion = "Registro civil";
                break;
            case 2:
                tipoIdentificacion = "Tarjeta de identidad";
                break;
            case 3:
                tipoIdentificacion = "Cedula de ciudadania";
                break;
            default:
                tipoIdentificacion = "Tarjeta de identidad";
                break;
        }
    }

    //constructor por si solo necesita uno que otro atributo para listar 
    public UsuarioPublico() {
        this.numeroIdentificacion = "0";
        this.fechaNacimiento = null;
        this.sexoBiologico = "No aplica";
    }

    //constructor por si solo necesita uno que otro atributo y el numero identificacion
    public UsuarioPublico(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
        this.fechaNacimiento = null;
        this.sexoBiologico = "No aplica";
    }

    //constructor completo
    public UsuarioPublico(int idUsuario, byte idRol, byte idTipoIdentificacion,
            String numeroIdentificacion, String primerNombre,
            String segundoNombre, String primerApellido, String segundoApellido, String correo,
            LocalDate fechaNacimiento, String sexoBiologico, String numeroCelular, byte edad, String sisben,
            boolean estado, String fotoPerfil) {

        this.idUsuario = idUsuario;
        this.idRol = idRol;
        cargarSegunRol(idRol);
        this.idTipoIdentificacion = idTipoIdentificacion;
        cargarTipoIdentificacion(idTipoIdentificacion);
        this.numeroIdentificacion = numeroIdentificacion;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre.isEmpty() ? "No aplica" : segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido.isEmpty() ? "No aplica" : segundoApellido;
        this.correo = correo;
        this.fechaNacimiento = fechaNacimiento;
        this.sexoBiologico = sexoBiologico;
        this.numeroCelular = numeroCelular;
        this.edad = edad;
        this.sisben = sisben;
        this.estado = estado;
        this.fotoPerfil = fotoPerfil;
    }

    public UsuarioPublico(int idUsuario, byte idRol, byte idTipoIdentificacion,
            String numeroIdentificacion, String primerNombre,
            String segundoNombre, String primerApellido, String segundoApellido, String correo,
            LocalDate fechaNacimiento, String sexoBiologico, String numeroCelular, String sisben,
            boolean estado, String fotoPerfil) {

        this.idUsuario = idUsuario;
        this.idRol = idRol;
        cargarSegunRol(idRol);
        this.idTipoIdentificacion = idTipoIdentificacion;
        cargarTipoIdentificacion(idTipoIdentificacion);
        this.numeroIdentificacion = numeroIdentificacion;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre.isEmpty() ? "No aplica" : segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido.isEmpty() ? "No aplica" : segundoApellido;
        this.correo = correo;
        this.fechaNacimiento = fechaNacimiento;
        this.sexoBiologico = sexoBiologico;
        this.numeroCelular = numeroCelular;
        this.edad = MetodosPublicos.calcularEdad(fechaNacimiento);
        this.sisben = sisben;
        this.estado = estado;
        this.fotoPerfil = fotoPerfil;
    }

    //Getters y Setters
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdRol(byte idRol) {
        this.idRol = idRol;
    }

    public byte getIdRol() {
        return idRol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getRol() {
        return rol;
    }

    public void setIdTipoIdentificacion(byte idTipoIdentificacion) {
        this.idTipoIdentificacion = idTipoIdentificacion;
    }

    public byte getIdTipoIdentificacion() {
        return idTipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCorreo() {
        return correo;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getSexoBiologico() {
        return sexoBiologico;
    }

    public void setNumeroCelular(String numeroCelular) {
        this.numeroCelular = numeroCelular;
    }

    public String getNumeroCelular() {
        return numeroCelular;
    }

    public void setEdad(byte edad) {
        this.edad = edad;
    }

    public byte getEdad() {
        return edad;
    }

    public void setSisben(String sisben) {
        this.sisben = sisben;
    }

    public String getSisben() {
        return sisben;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }
}
