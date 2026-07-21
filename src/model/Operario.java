package model;

import java.time.LocalDate;

public class Operario extends Paciente{
    private int id_operario;
    
    public Operario(int id_usuario, byte id_rol, String tipoId, String numeroId, 
                    String primerNombre, String segundoNombre, String primerApellido, 
                    String segundoApellido, String correo, LocalDate fechaNacimiento, 
                    String sexoBiologico, String numeroTelefonico, byte edad, 
                    boolean estado, String sisben, String fotoPerfil) {
        
        super(id_usuario, id_rol, tipoId, numeroId, primerNombre, segundoNombre, 
              primerApellido, segundoApellido, correo, fechaNacimiento, 
              sexoBiologico, numeroTelefonico, edad, estado, sisben, fotoPerfil);
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
    
    public int getId_usuario(){
        return super.getIdUsuario();
    }
    
    public void setId_usuario(int id_usuario){
        super.setIdUsuario(id_usuario);
    }
    
}
