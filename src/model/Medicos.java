/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;

/**
 *
 * @author lunaa
 */
public class Medicos extends Paciente{
     private int id_usuarioM;
    private int id_medico;
    private String especialidad;
    
    public Medicos(){}

    public Medicos(int id_usuario, byte id_rol, String tipoId, String numeroId, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido,
            String correo, LocalDate fechaNacimiento, String sexoBiologico, String numeroTelefonico, byte edad, boolean estado, String sisben, String fotoPerfil, String especialidad) {
        super(id_usuario,
                id_rol,
                tipoId,
                numeroId,
                primerNombre,
                segundoNombre,
                primerApellido,
                segundoApellido,
                correo,
                fechaNacimiento,
                sexoBiologico,
                numeroTelefonico,
                edad,
                estado,
                sisben,
                fotoPerfil);
        this.id_usuarioM=id_usuario;
        this.especialidad = especialidad;
    }

    public Medicos(int id_medico, String primerNombre, String primerApellido) {
        super(primerNombre, primerApellido);
        this.id_medico = id_medico;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getEspecialidad() {
        return this.especialidad;
    }

    public void setId_medico(int id_medico) {
        this.id_medico = id_medico;
    }

    public int getId_medico() {
        return this.id_medico;
    }
}
