/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.MedicoInterfaz;

/**
 *
 * @author Windows 10 PRO
 */
public class MedicoController implements ActionListener {

    MedicoInterfaz medico;

    public MedicoController(MedicoInterfaz medico) {
        this.medico = medico;
        this.medico.btnMisCitas.addActionListener(this);
        this.medico.btnMisCitas.doClick();
        this.medico.btnHistorial.addActionListener(this);
        this.medico.btnComentarios.addActionListener(this);
        this.medico.btnNotificaciones.addActionListener(this);
        this.medico.btnMiAgenda.addActionListener(this);
        this.medico.btnConsultorio.addActionListener(this);
        this.medico.simboloRegresarConfirmacionP.addActionListener(this);
        this.medico.btnAsistio.addActionListener(this);
        this.medico.btnNoAsistio.addActionListener(this);

        this.medico.pruebaFicha.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.medico.btnMisCitas) {
            this.medico.mostrarVistaMisCitas();
            this.medico.btnMisCitas.setEnabled(false);
            this.medico.habilitarBotonesMenu(this.medico.btnMisCitas);
        }
        if (e.getSource() == this.medico.btnHistorial) {
            this.medico.mostrarVistaHistorial();
            this.medico.btnHistorial.setEnabled(false);
            this.medico.habilitarBotonesMenu(this.medico.btnHistorial);
        }
        if (e.getSource() == this.medico.btnComentarios) {
            this.medico.mostrarVistaComentarios();
            this.medico.btnComentarios.setEnabled(false);
            this.medico.habilitarBotonesMenu(this.medico.btnComentarios);
        }
        if (e.getSource() == this.medico.btnNotificaciones) {
            this.medico.mostrarVistaNotificaciones();
            this.medico.btnNotificaciones.setEnabled(false);
            this.medico.habilitarBotonesMenu(this.medico.btnNotificaciones);
        }
        if (e.getSource() == this.medico.btnMiAgenda) {
            this.medico.mostrarVistaMiAgenda();
            this.medico.btnMiAgenda.setEnabled(false);
            this.medico.habilitarBotonesMenu(this.medico.btnMiAgenda);
        }
        if (e.getSource() == this.medico.btnConsultorio || e.getSource() == this.medico.simboloRegresarConfirmacionP || e.getSource() == this.medico.btnNoAsistio) {
            this.medico.mostrarVistaConsultorio();
            this.medico.btnConsultorio.setEnabled(false);
            this.medico.habilitarBotonesMenu(this.medico.btnConsultorio);
        }
        if (e.getSource() == this.medico.pruebaFicha) {
            this.medico.mostrarVistaConfirmacionAsistencia();
            this.medico.btnMisCitas.setEnabled(false);
            this.medico.btnHistorial.setEnabled(false);
            this.medico.btnComentarios.setEnabled(false);
            this.medico.btnNotificaciones.setEnabled(false);
            this.medico.btnMiAgenda.setEnabled(false);
            this.medico.btnConsultorio.setEnabled(false);
        }
        if (e.getSource() == this.medico.btnAsistio) {
            this.medico.mostrarVistaFichaClinica();
            this.medico.btnConsultorio.setEnabled(false);
            this.medico.habilitarBotonesMenu(this.medico.btnConsultorio);
        }
    }
}
