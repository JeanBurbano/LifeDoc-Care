/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import view.AdminCentroInterfaz;

/**
 *
 * @author lunaa
 */
public class AdminCentroController implements ActionListener {

    AdminCentroInterfaz personalCentro;
    

    public AdminCentroController(AdminCentroInterfaz personalCentro) {
        this.personalCentro = personalCentro;
        this.personalCentro.btnPersonalCentro.addActionListener(this);
        this.personalCentro.btnInventarioMedicamentos.addActionListener(this);
        this.personalCentro.btnHorarioMedico.addActionListener(this);
        this.personalCentro.btnCrearHorario.addActionListener(this);
        this.personalCentro.btnAñadirMedicamento.addActionListener(this);
        this.personalCentro.btnSeleccionar.addActionListener(this);
        
   
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.personalCentro.btnPersonalCentro) {
            this.personalCentro.mostrarVistaPersonalCentro();
            this.personalCentro.btnPersonalCentro.setEnabled(false);
            this.personalCentro.habilitarBotones(this.personalCentro.btnPersonalCentro);
            System.out.println("hai");
        }
        
        if (e.getSource() == this.personalCentro.btnInventarioMedicamentos) {
            this.personalCentro.mostrarVistaInventarioMedicamento();
            this.personalCentro.btnInventarioMedicamentos.setEnabled(false);
            this.personalCentro.habilitarBotones(this.personalCentro.btnInventarioMedicamentos);
            System.out.println("hao");
        }
        
        if (e.getSource() == this.personalCentro.btnAñadirMedicamento) {
            this.personalCentro.mostrarAñadirMedicamento();
            this.personalCentro.btnAñadirMedicamento.setEnabled(false);
            this.personalCentro.habilitarBotones(this.personalCentro.btnAñadirMedicamento);
            System.out.println("hyy");
        }
        
        if (e.getSource() == this.personalCentro.btnHorarioMedico) {
            this.personalCentro.mostrarVistaHorarioMedico();
            this.personalCentro.btnHorarioMedico.setEnabled(false);
            this.personalCentro.habilitarBotones(this.personalCentro.btnHorarioMedico);
            System.out.println("hau");
        }
        
        if (e.getSource() == this.personalCentro.btnCrearHorario) {
            this.personalCentro.mostrarFormularioCreacionHorario();
            this.personalCentro.btnCrearHorario.setEnabled(false);
            System.out.println("hayy");
        }
        
        if(e.getSource() == this.personalCentro.btnSeleccionar){
            
        }

    }
    
}
