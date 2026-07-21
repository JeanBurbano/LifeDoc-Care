/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author lunaa
 */
public class Consultorio {
    private int idConsultorio;
    private int numeroConsultorio;

    public Consultorio() {
    }

    public Consultorio(int idConsultorio, int numeroConsultorio) {
        this.idConsultorio = idConsultorio;
        this.numeroConsultorio = numeroConsultorio;
    }

    public int getIdConsultorio() {
        return idConsultorio;
    }

    public void setIdConsultorio(int idConsultorio) {
        this.idConsultorio = idConsultorio;
    }

    public int getNumeroConsultorio() {
        return numeroConsultorio;
    }

    public void setNumeroConsultorio(int numeroConsultorio) {
        this.numeroConsultorio = numeroConsultorio;
    }
}
