/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author lunaa
 */
public class Especialidad {
    private int idEsp;
    private String nombreEsp;
    
    public Especialidad(){
        
    }
    
    public Especialidad(int idEsp, String nombreEsp){
        this.idEsp = idEsp;
        this.nombreEsp = nombreEsp;
    }

    public int getIdEsp() {
        return idEsp;
    }

    public void setIdEsp(int idEsp) {
        this.idEsp = idEsp;
    }

    public String getNombreEsp() {
        return nombreEsp;
    }

    public void setNombreEsp(String nombreEsp) {
        this.nombreEsp = nombreEsp;
    }
    
    @Override
    public String toString(){
        return nombreEsp;
    }
    
}
