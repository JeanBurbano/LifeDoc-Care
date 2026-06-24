package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.PacienteInterfaz;

public class PacienteController implements ActionListener{

    PacienteInterfaz pacienteI = new PacienteInterfaz("Alejandro", "", "");

    public PacienteController(PacienteInterfaz pacienteI) {
        this.pacienteI = pacienteI;
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

    }
}
