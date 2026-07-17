package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import model.Cita;
import model.CitaDao;
import model.Foro;
import model.ForoDao;
import model.Medico;
import model.MedicoDao;
import model.MetodosPublicos;
import view.PacienteInterfaz;
import view.Titulo;

public class PacienteController implements ActionListener {

    private static ArrayList<Foro> foro = new ArrayList<Foro>();
    private CitaDao citadao;
    private MedicoDao medicodao;
    private ForoDao forodao;
    protected PacienteInterfaz pacienteI;//protected para que el hijo lo acceda directo
    public Medico[] medicos;
    protected Cita[] citas;
    private boolean verificador;

    public PacienteController(PacienteInterfaz pacienteI) {
        if (foro == null) {
            
        }
        pacienteI.labelFotoPerfil.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(pacienteI, "Vas a editar tu perfil");
            }
        });
        this.citadao = new CitaDao();
        this.medicodao = new MedicoDao();
        this.forodao = new ForoDao();
        this.pacienteI = pacienteI;
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

    private void actionListenerParaBotonesDeVectores(ArrayList<JButton> vectorBotones, String primero, String segundo) {
        for (JButton boton : vectorBotones) {
            boton.addActionListener((ActionEvent e) -> {
                pacienteI.mostrarVistaAgendamientoCita(new Titulo(primero, segundo, 50));
            });
        }
    }

    private void estaditodeBotonesComentarios(JButton boton1, JButton boton2, JButton boton3) {
        boton1.setEnabled(false);
        boton2.setEnabled(true);
        boton3.setEnabled(true);
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
            actionListenerParaBotonesDeVectores(pacienteI.listaBotonesMedicos, "Agenda una ", "Cita");
        }
        if (e.getSource() == pacienteI.btnSugerencias) {
            estaditodeBotonesComentarios(pacienteI.btnSugerencias, pacienteI.btnQuejas, pacienteI.btnForo);
            this.verificador = true;
            pacienteI.construirFormularioComentario();
        }
        if (e.getSource() == pacienteI.btnQuejas) {
            estaditodeBotonesComentarios(pacienteI.btnQuejas, pacienteI.btnSugerencias, pacienteI.btnForo);
            this.verificador = false;
            pacienteI.construirFormularioComentario();
        }
        if (e.getSource() == pacienteI.btnForo) {
            estaditodeBotonesComentarios(pacienteI.btnForo, pacienteI.btnQuejas, pacienteI.btnSugerencias);
            MetodosPublicos.vaciarPanel(pacienteI.panelComentarios);
        }
        if (e.getSource() == pacienteI.btnEnviar) {
            String asunto = pacienteI.campoAsunto.getText().trim();
            String descripcion = pacienteI.areaDescripcion.getText().trim();
            if (asunto.isEmpty() || descripcion.isEmpty()) {
                JOptionPane.showMessageDialog(pacienteI, "Los campos deben contener algo");
            } else {
                String tipoMensaje = verificador ? "Sugerencia" : "Queja";
                Foro nuevoComentario = new Foro(tipoMensaje, asunto, descripcion, pacienteI.usuario.getIdUsuario());
                int filasInsertadas = forodao.setAgregar(nuevoComentario);

                if (filasInsertadas > 0) {
                    JOptionPane.showMessageDialog(pacienteI, "Tu " + tipoMensaje.toLowerCase() + " fue enviada correctamente");
                    pacienteI.campoAsunto.setText("");
                    pacienteI.areaDescripcion.setText("");
                } else {
                    JOptionPane.showMessageDialog(pacienteI, "No se pudo enviar tu " + tipoMensaje.toLowerCase() + ", intenta nuevamente");
                }
            }
        }
    }
}
