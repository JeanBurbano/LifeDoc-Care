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
//        this.medico.btnMisCitas.doClick();
        this.medico.btnHistorial.addActionListener(this);
        this.medico.btnComentarios.addActionListener(this);
        this.medico.btnNotificaciones.addActionListener(this);
        this.medico.btnMiAgenda.addActionListener(this);
        this.medico.btnMiAgenda.doClick();
        this.medico.btnConsultorio.addActionListener(this);
        this.medico.simboloRegresarConfirmacionP.addActionListener(this);
        this.medico.btnAsistio.addActionListener(this);
        this.medico.btnNoAsistio.addActionListener(this);
        this.medico.btnGuardarFicha.addActionListener(this);
        this.medico.btnAceptarFicha.addActionListener(this);
        this.medico.btnVerDetalles.addActionListener(this);
        this.medico.btnVolverVerDetalles.addActionListener(this);
        this.medico.btnReagendarCita.addActionListener(this);
        this.medico.btnActReagendar.addActionListener(this);
        this.medico.btnNoReagendar.addActionListener(this);

        this.medico.pruebaFicha.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.medico.btnMiAgenda || e.getSource() == this.medico.btnVolverVerDetalles || e.getSource() == this.medico.btnNoReagendar) {
            this.medico.mostrarVistaMiAgenda();
            this.medico.citaVistaMiAgenda();
            this.medico.btnMiAgenda.setEnabled(false);
            this.medico.habilitarBotonesMenu(this.medico.btnMiAgenda);
        }
        if (e.getSource() == this.medico.btnConsultorio || e.getSource() == this.medico.simboloRegresarConfirmacionP || e.getSource() == this.medico.btnNoAsistio) {
            this.medico.mostrarVistaConsultorio();
            this.medico.citaVistaConsultorio();
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
            this.medico.btnMisCitas.setEnabled(false);
            this.medico.btnHistorial.setEnabled(false);
            this.medico.btnComentarios.setEnabled(false);
            this.medico.btnNotificaciones.setEnabled(false);
            this.medico.btnMiAgenda.setEnabled(false);
            this.medico.btnConsultorio.setEnabled(false);
        }
        if (e.getSource() == this.medico.btnGuardarFicha) {
            this.medico.mostrarVistaConfirmacionFichaGuardada();
            this.medico.btnMisCitas.setEnabled(false);
            this.medico.btnHistorial.setEnabled(false);
            this.medico.btnComentarios.setEnabled(false);
            this.medico.btnNotificaciones.setEnabled(false);
            this.medico.btnMiAgenda.setEnabled(false);
            this.medico.btnConsultorio.setEnabled(false);
        }
        if (e.getSource() == this.medico.btnAceptarFicha) {
            this.medico.mostrarVistaConsultorio();
            this.medico.btnConsultorio.setEnabled(false);
            this.medico.habilitarBotonesMenu(this.medico.btnConsultorio);
        }
        if (e.getSource() == this.medico.btnVerDetalles) {
            this.medico.mostrarDetallesCita();
            this.medico.btnMiAgenda.setEnabled(false);
            this.medico.habilitarBotonesMenu(this.medico.btnMiAgenda);
        }
        if (e.getSource() == this.medico.btnReagendarCita) {
            this.medico.mostrarVistaConfirmacionReagendar();
            this.medico.btnMisCitas.setEnabled(false);
            this.medico.btnHistorial.setEnabled(false);
            this.medico.btnComentarios.setEnabled(false);
            this.medico.btnNotificaciones.setEnabled(false);
            this.medico.btnMiAgenda.setEnabled(false);
            this.medico.btnConsultorio.setEnabled(false);
        }
        if (e.getSource() == this.medico.btnActReagendar) {
            
        }
    }
}
