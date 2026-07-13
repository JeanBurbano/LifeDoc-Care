package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import model.Cita;
import model.CitaDao;
import model.Medico;
import model.MedicoDao;
import model.MetodosPublicos;
import view.PacienteInterfaz;
import view.Titulo;

public class PacienteController implements ActionListener {

    private CitaDao citadao;
    private MedicoDao medicodao;
    protected PacienteInterfaz pacienteI;//protected para que el hijo lo acceda directo
    public Medico[] medicos;
    protected Cita[] citas;

    public PacienteController(PacienteInterfaz pacienteI) {
        this.citadao = new CitaDao();
        this.medicodao = new MedicoDao();
        this.pacienteI = pacienteI;
        this.pacienteI.agregarBotonesMenuPaciente();
        this.pacienteI.btnMisCitas.addActionListener(this);
        this.pacienteI.btnHistorial.addActionListener(this);
        this.pacienteI.btnComentarios.addActionListener(this);
        this.pacienteI.btnNotificaciones.addActionListener(this);
        this.pacienteI.btnCerrarSesion.addActionListener(this);
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
        this.pacienteI.btnMisCitas.doClick();
    }

  public void actionListenerParaBotonesDeVectores(ArrayList<JButton> vectorBotones) {
    for (JButton boton : vectorBotones) {
        boton.addActionListener((ActionEvent e) -> {
            pacienteI.mostrarVistaAgendamientoCita(new Titulo("Agendar una ", "cita",50));
        });
    }
}
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == pacienteI.btnCerrarSesion) {
            this.pacienteI.dispose();
        }
        if (e.getSource() == pacienteI.btnMisCitas) {
            pacienteI.btnMisCitas.setEnabled(false);
            pacienteI.habilitarBotonesMenu(pacienteI.btnMisCitas);
            pacienteI.mostrarVistaMisCitas();
            this.pacienteI.listaBotonesCancelar.clear();
            this.pacienteI.listaBotonesReagendar.clear();
            this.citas = citadao.listarPorUsuario(pacienteI.usuario.getIdUsuario());
            if (citas == null || citas.length == 0) {
                pacienteI.panelInfoCitas.add(new JLabel("No tienes citas"));
                MetodosPublicos.refrescarVentana(pacienteI.panelInfoCitas);
            } else {
                for (Cita clave : citas) {
                    pacienteI.agregarAlPanelMiscitas(new Titulo("Cita ", clave.getEspecialidad(), 30).getPanelTitulo(),
                            clave.getFechaCita().toString(), clave.getHoraCita().toString(),
                            "Nombre Medico(a): " + clave.getNombreMedico());
                }
            }
        }
        if (e.getSource() == pacienteI.btnHistorial) {
            pacienteI.btnHistorial.setEnabled(false);
            pacienteI.habilitarBotonesMenu(pacienteI.btnHistorial);
            pacienteI.mostrarVistaHistorial();
        }
        if (e.getSource() == pacienteI.btnComentarios) {
            pacienteI.btnComentarios.setEnabled(false);
            pacienteI.habilitarBotonesMenu(pacienteI.btnComentarios);
            pacienteI.mostrarVistaComentarios();
            pacienteI.btnSugerencias.doClick();
        }
        if (e.getSource() == pacienteI.btnNotificaciones) {
            pacienteI.mostrarVistaNotificaciones();
            pacienteI.btnNotificaciones.setEnabled(false);
            pacienteI.habilitarBotonesMenu(pacienteI.btnNotificaciones);
        }
        if (e.getSource() == pacienteI.btnAgendar) {
            pacienteI.mostrarVistaTipoConsulta(new Titulo("Agendamiento de ", "Cita"));
        }
        if (e.getSource() == pacienteI.btnOdontologia) {
            pacienteI.listaBotonesMedicos.clear();
            medicos = medicodao.listarPorEspecialidad(2);
            String[] nombreMedicos = new String[medicos.length];
            for (int i = 0; i < medicos.length; i++) {
                nombreMedicos[i] = medicos[i].getPrimerNombre() + " " + medicos[i].getPrimerApellido();
            }
            pacienteI.mostrarVistaSeleccionMedico(nombreMedicos);
            actionListenerParaBotonesDeVectores(pacienteI.listaBotonesMedicos);
        }
        if (e.getSource() == pacienteI.btnSugerencias) {
            pacienteI.btnSugerencias.setEnabled(false);
            pacienteI.btnQuejas.setEnabled(true);
            pacienteI.construirFormularioComentario();
        }
        if (e.getSource() == pacienteI.btnQuejas) {
            pacienteI.btnQuejas.setEnabled(false);
            pacienteI.btnSugerencias.setEnabled(true);
            pacienteI.construirFormularioComentario();
        }
        if (e.getSource() == pacienteI.btnForo) {
            pacienteI.btnQuejas.setEnabled(true);
            pacienteI.btnSugerencias.setEnabled(true);
            MetodosPublicos.vaciarPanel(pacienteI.panelComentarios);
        }
    }
}
