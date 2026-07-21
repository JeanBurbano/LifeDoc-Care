package controller;

import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import model.Cita;
import model.CitaDao;
import model.MetodosPublicos;
import view.MedicoInterfaz;
import view.Titulo;

public class MedicoController extends PacienteController {
    
    private CitaDao citadao;
    protected Cita[] citas;
    MedicoInterfaz medico;

    public MedicoController(MedicoInterfaz medico) {
        super(medico);
        this.citadao = new CitaDao();
        this.medico.btnHistorialMedicoPaciente.addActionListener(this);
        this.medico.btnBuscarIdHistorialPaciente.addActionListener(this);
        this.medico.btnMiAgenda.addActionListener(this);
        this.medico.btnConsultorio.addActionListener(this);
        this.medico.simboloRegresarConfirmacionP.addActionListener(this);
        this.medico.btnAsistio.addActionListener(this);
        this.medico.btnNoAsistio.addActionListener(this);
        this.medico.pruebaFicha.addActionListener(this);
        this.medico.btnGuardarFicha.addActionListener(this);
        this.medico.btnAceptarFicha.addActionListener(this);
//        this.medico.btnVerDetalles.addActionListener(this);
        this.medico.btnVolverVerDetalles.addActionListener(this);
//        this.medico.btnReagendarCita.addActionListener(this);
        this.medico.btnActReagendar.addActionListener(this);
        this.medico.btnNoReagendar.addActionListener(this);
    }

    private void habilitarBotonesHistorial(JButton boton1, JButton boton2, JButton boton3) {
        boton1.setEnabled(false);
        boton2.setEnabled(true);
        boton3.setEnabled(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        this.medico = (MedicoInterfaz) pacienteI;
        if(e.getSource() == this.medico.btnHistorialCitas){
            habilitarBotonesHistorial(this.medico.btnHistorialCitas,this.medico.btnHistorialMedicoPaciente,this.medico.btnHistorialMedico);
        }
        if(e.getSource() == this.medico.btnHistorialMedico){
            habilitarBotonesHistorial(this.medico.btnHistorialMedico,this.medico.btnHistorialCitas,this.medico.btnHistorialMedicoPaciente);
        }
        if(e.getSource() == this.medico.btnHistorialMedicoPaciente){
            this.medico.mostrarFormularioHistorialMedicoPaciente();
            habilitarBotonesHistorial(this.medico.btnHistorialMedicoPaciente,this.medico.btnHistorialCitas,this.medico.btnHistorialMedico);
        }
        if(e.getSource() == this.medico.btnBuscarIdHistorialPaciente){
            this.medico.mostrarHistorialMedicoPaciente();
            habilitarBotonesHistorial(this.medico.btnHistorialMedicoPaciente,this.medico.btnHistorialCitas,this.medico.btnHistorialMedico);
        }
        if (e.getSource() == this.medico.btnMiAgenda || e.getSource() == this.medico.btnVolverVerDetalles || e.getSource() == this.medico.btnNoReagendar) {
            this.medico.mostrarVistaMiAgenda();
            this.medico.btnMiAgenda.setEnabled(false);
            this.medico.habilitarBotonesMenu(this.medico.btnMiAgenda);
            this.citas = citadao.listarPorMedico(medico.usuario.getIdUsuario());
            if (citas == null || citas.length == 0) {
                medico.panelPrincipal.add(new JLabel("No tienes citas asignadas"));
                MetodosPublicos.refrescarVentana(medico.panelPrincipal);
            } else {
                for (Cita clave : citas) {
                    medico.citaVistaMiAgenda(clave.getEspecialidad(),
                            clave.getFechaCita().toString(), clave.getHoraCita().toString(),
                            clave.getNombrePaciente());
                }
            }
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
//        if (e.getSource() == this.medico.btnVerDetalles) {
//            this.medico.mostrarDetallesCita();
//            this.medico.btnMiAgenda.setEnabled(false);
//            this.medico.habilitarBotonesMenu(this.medico.btnMiAgenda);
//        }
//        if (e.getSource() == this.medico.btnReagendarCita) {
//            this.medico.mostrarVistaConfirmacionReagendar();
//            this.medico.btnMisCitas.setEnabled(false);
//            this.medico.btnHistorial.setEnabled(false);
//            this.medico.btnComentarios.setEnabled(false);
//            this.medico.btnNotificaciones.setEnabled(false);
//            this.medico.btnMiAgenda.setEnabled(false);
//            this.medico.btnConsultorio.setEnabled(false);
//        }
//        if (e.getSource() == this.medico.btnActReagendar) {
//
//        }
    }
}