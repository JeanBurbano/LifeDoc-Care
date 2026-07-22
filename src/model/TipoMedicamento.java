/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author lunaa
 */
public class TipoMedicamento {
    private int idTipoMedicamento;
    private String nombre;
    
    public TipoMedicamento(){}
    
    public TipoMedicamento(int idTipoMedicamento, String nombre){
        this.idTipoMedicamento = idTipoMedicamento;
        this.nombre = nombre;
    }

    public int getIdTipoMedicamento() {
        return idTipoMedicamento;
    }

    public void setIdTipoMedicamento(int idTipoMedicamento) {
        this.idTipoMedicamento = idTipoMedicamento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    @Override
    public String toString(){
        return nombre;
    }
}
