package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.MetodosPublicos;
import view.PacienteInterfaz;
import view.Titulo;

public class PacienteController implements ActionListener{

    PacienteInterfaz pacienteI = new PacienteInterfaz("Alejandro", "", "");

    public PacienteController(PacienteInterfaz pacienteI) {
        this.pacienteI = pacienteI;
        this.pacienteI.agregarBotonesMenuPaciente();
        this.pacienteI.btnMisCitas.addActionListener(this);
        this.pacienteI.btnMisCitas.doClick();
        this.pacienteI.btnMisCitas.addActionListener(this);
        this.pacienteI.btnHistorial.addActionListener(this);
        this.pacienteI.btnComentarios.addActionListener(this);
        this.pacienteI.btnNotificaciones.addActionListener(this);
        this.pacienteI.btnAgendar.addActionListener(this);
        this.pacienteI.btnHistorialMedico.addActionListener(this);
        this.pacienteI.btnHistorialCitas.addActionListener(this);
        this.pacienteI.btnDescargar.addActionListener(this);
        this.pacienteI.btnSugerencias.addActionListener(this);
        this.pacienteI.btnQuejas.addActionListener(this);
        this.pacienteI.btnForo.addActionListener(this);
        this.pacienteI.btnEnviar.addActionListener(this);
        this.pacienteI.btnOdontologia.addActionListener(this);
        this.pacienteI.btnDermatologia.addActionListener(this);
        this.pacienteI.btnMedicoGeneral.addActionListener(this);
    }
     @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.pacienteI.btnMisCitas) {
            this.pacienteI.mostrarVistaMisCitas();
            this.pacienteI.btnMisCitas.setEnabled(false);
            this.pacienteI.habilitarBotonesMenu(this.pacienteI.btnMisCitas);
        }
        if (e.getSource() == this.pacienteI.btnHistorial) {
            this.pacienteI.mostrarVistaHistorial();
            this.pacienteI.btnHistorial.setEnabled(false);
            this.pacienteI.habilitarBotonesMenu(this.pacienteI.btnHistorial);
        }
        if (e.getSource() == this.pacienteI.btnCerrarSesion) {
       
        }
        if (e.getSource() == this.pacienteI.btnComentarios) {
            this.pacienteI.mostrarVistaComentarios();
            this.pacienteI.btnComentarios.setEnabled(false);
            this.pacienteI.habilitarBotonesMenu(this.pacienteI.btnComentarios);
        }
        if (e.getSource() == this.pacienteI.btnForo) {
            MetodosPublicos.vaciarPanel(this.pacienteI.panelComentarios);
        }
        if (e.getSource() == this.pacienteI.btnNotificaciones) {
            this.pacienteI.mostrarVistaNotificaciones();
            this.pacienteI.btnNotificaciones.setEnabled(false);
            this.pacienteI.habilitarBotonesMenu(this.pacienteI.btnNotificaciones);
        }
        if (e.getSource() == this.pacienteI.btnQuejas) {
            this.pacienteI.construirFormularioComentario();
        }
        if (e.getSource() == this.pacienteI.btnAgendar) {
            this.pacienteI.mostrarVistaTipoConsulta(new Titulo("Agendamiento de ", "Cita"));
        }
        if (e.getSource() == this.pacienteI.btnOdontologia) {
            String[] medicos = new String[]{"Jean", "cepeda", "petro", "luna", "tovar", "otero"};
            this.pacienteI.mostrarVistaSeleccionMedico(medicos);
//            mostrarVistaAgendamientoCita(new Titulo("Agendamiento de", " Cita"));
        }
    }
}
