package controller;

import java.awt.event.ActionEvent;
import view.MedicoInterfaz;

public class MedicoController extends PacienteController {

    MedicoInterfaz medico;

    public MedicoController(MedicoInterfaz medico) {
        super(medico);
        this.medico.btnMiAgenda.addActionListener(this);
        this.medico.btnConsultorio.addActionListener(this);
        this.medico.simboloRegresarConfirmacionP.addActionListener(this);
        this.medico.btnAsistio.addActionListener(this);
        this.medico.btnNoAsistio.addActionListener(this);
        this.medico.pruebaFicha.addActionListener(this);
        this.medico.btnGuardarFicha.addActionListener(this);
        this.medico.btnAceptarFicha.addActionListener(this);
        this.medico.btnVerDetalles.addActionListener(this);
        this.medico.btnVolverVerDetalles.addActionListener(this);
        this.medico.btnReagendarCita.addActionListener(this);
        this.medico.btnActReagendar.addActionListener(this);
        this.medico.btnNoReagendar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        this.medico = (MedicoInterfaz) pacienteI;
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