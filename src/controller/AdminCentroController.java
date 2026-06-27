/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.AdminCentroInterfaz;
import view.PacienteInterfaz;

/**
 *
 * @author lunaa
 */
public class AdminCentroController implements ActionListener {

    AdminCentroInterfaz adminC;

    public AdminCentroController(AdminCentroInterfaz adminC) {
        this.adminC = adminC;
        this.adminC.btnPersonalCentro.addActionListener(this);
        this.adminC.btnInventarioMedicamentos.addActionListener(this);
        
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.adminC.btnPersonalCentro) {
            this.adminC.mostrarVistaPersonalCentro();
            this.adminC.btnPersonalCentro.setEnabled(false);
            this.adminC.habilitarBotones(this.adminC.btnPersonalCentro);
            System.out.println("hai");
        }

    }
}
