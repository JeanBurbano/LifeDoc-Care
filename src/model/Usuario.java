package model;

import java.time.LocalDate;

public class Usuario extends UsuarioPublico {

    private String contrasena;

    public Usuario() {

    }

    public Usuario(String numeroIdentificacion) {
        super(numeroIdentificacion);
    }
    
    public Usuario(int idUsuario, byte idRol, byte idTipoIdentificacion,
            String numeroIdentificacion, String primerNombre, String segundoNombre,
            String primerApellido, String segundoApellido, String correo, String contrasena,
            LocalDate fechaNacimiento, String sexoBiologico, String numeroCelular, byte edad,
            String sisben, boolean estado, String fotoPerfil) {

        super(idUsuario, idRol, idTipoIdentificacion,
                numeroIdentificacion, primerNombre, segundoNombre,
                primerApellido, segundoApellido, correo,
                fechaNacimiento, sexoBiologico, numeroCelular, edad, sisben, estado, fotoPerfil);

        this.contrasena = contrasena;
    }
    
     public Usuario(int idUsuario, byte idRol, byte idTipoIdentificacion,
            String numeroIdentificacion, String primerNombre, String segundoNombre,
            String primerApellido, String segundoApellido, String correo, String contrasena,
            LocalDate fechaNacimiento, String sexoBiologico, String numeroCelular,String sisben, 
            boolean estado, String fotoPerfil) {
         
          super(idUsuario, idRol, idTipoIdentificacion,
                numeroIdentificacion, primerNombre, segundoNombre,
                primerApellido, segundoApellido, correo,
                fechaNacimiento, sexoBiologico, numeroCelular, MetodosPublicos.calcularEdad(fechaNacimiento), sisben, estado, fotoPerfil);
          this.contrasena = contrasena;
    }
    
    public String getContrasena(){
        return contrasena;
    }
}
