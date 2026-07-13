package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import model.Cita;
import model.CitaDao;
import model.MetodosPublicos;
import view.PacienteInterfaz;
import view.Titulo;

public class PacienteController implements ActionListener {

    private CitaDao citadao;
    protected PacienteInterfaz pacienteI;//protected para que el hijo lo acceda directo
    public String[] medicos;
    protected Cita[] citas;

    public PacienteController(PacienteInterfaz pacienteI) {
        this.citadao = new CitaDao();
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
                    pacienteI.agregarAlPanelMiscitas(new Titulo("Cita ", clave.getEspecialidad(), 40).getPanelTitulo(),
                            clave.getFechaCita().toString(), clave.getHoraCita().toString(),
                            clave.getNombreMedico());
                }
            }
        }
        if (e.getSource() == pacienteI.btnHistorial) {
            pacienteI.btnHistorial.setEnabled(false);
            pacienteI.habilitarBotonesMenu(pacienteI.btnHistorial);
            pacienteI.mostrarVistaHistorial();
        }
        if (e.getSource() == pacienteI.btnComentarios) {
            pacienteI.mostrarVistaComentarios();
            pacienteI.btnComentarios.setEnabled(false);
            pacienteI.habilitarBotonesMenu(pacienteI.btnComentarios);
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
//            String[] medicos = new String[]{"Jean", "cepeda", "petro", "luna", "tovar", "otero"};
            pacienteI.mostrarVistaSeleccionMedico(medicos);
        }
        if (e.getSource() == pacienteI.btnSugerencias) {
            pacienteI.construirFormularioComentario();
        }
        if (e.getSource() == pacienteI.btnQuejas) {
            pacienteI.construirFormularioComentario();
        }
        if (e.getSource() == pacienteI.btnForo) {
            MetodosPublicos.vaciarPanel(pacienteI.panelComentarios);
        }
    }
}
