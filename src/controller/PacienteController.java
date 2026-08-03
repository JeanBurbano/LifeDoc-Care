package controller;

import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import model.Cita;
import model.CreadorPdf;
import model.MetodosPublicos;
import model.UsuarioPublico;
import model.UsuarioDao;
import view.EditarPerfilInterfaz;
import view.PacienteInterfaz;

public class PacienteController implements ActionListener {

    //variables
    private UsuarioPublico usurio;

    protected PacienteInterfaz pacienteI;
    private GestorCitas gestorCita;
    private GestorForo gestorForo;
    protected ArrayList<JButton> listaBotonesReagendar;
    protected ArrayList<JButton> listaBotonesCancelar;
    protected ArrayList<JButton> listaBotonesMedicos;

    private String historial;
    private boolean estadoNotificacion;

    public PacienteController() {

    }

    public PacienteController(PacienteInterfaz pacienteI) {
        init(pacienteI);
    }

    protected void init(PacienteInterfaz pacienteI) {
        listaBotonesCancelar = new ArrayList<>();
        listaBotonesReagendar = new ArrayList<>();
        listaBotonesMedicos = new ArrayList<>();
        this.pacienteI = pacienteI;
        gestorCita = new GestorCitas(this.pacienteI, this);
        gestorForo = new GestorForo(this.pacienteI);
        usurio = this.pacienteI.getUsuario();
        agregaMauseClick();
        agregarActionListener();
        estadoNotificacion = true;
    }

    protected void vaciarListasBotones() {
        listaBotonesCancelar.clear();
        listaBotonesReagendar.clear();
    }

    protected void agregarBotonesListas(JButton btnCancelar, JButton btnReagendar) {
        listaBotonesCancelar.add(btnCancelar);
        listaBotonesReagendar.add(btnReagendar);
    }

    protected void agregarListenerBotonesCancelar(int i, Cita clave) {
        listaBotonesCancelar.get(i).addActionListener((ActionEvent e) -> {
            if (gestorCita.tieneAntelacionSuficiente(clave)) {
                int r = JOptionPane.showConfirmDialog(pacienteI, "Estás seguro de cancelar esta cita", "Advertencia", JOptionPane.WARNING_MESSAGE);
                if (r == 0) {
                    boolean cancelada = gestorCita.cancelarCita(clave);
                    if (cancelada) {
                        procesoNotificacion("Cita cancelada", "Tu cita con el Dr(a). " + clave.getNombreMedico()
                                + " ha sido cancelada correctamente.");
                        gestorCita.procesoBtnMiscitas();
                    } else {
                        JOptionPane.showMessageDialog(pacienteI, "No se pudo cancelar su cita, intente más tarde.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(pacienteI, "No decidiste cancelar la cita.");
                }
            } else {
                JOptionPane.showMessageDialog(pacienteI, "No se puede cancelar con menos de 4 horas de antelación.", "Error de cancelación", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    protected void agregarListenerBotonesReagendar(int i, Cita clave) {
        listaBotonesReagendar.get(i).addActionListener((ActionEvent e) -> {
            if (gestorCita.tieneAntelacionSuficiente(clave)) {
                int r = JOptionPane.showConfirmDialog(pacienteI, "Estás seguro de reagendar esta cita", "Advertencia", JOptionPane.WARNING_MESSAGE);
                if (r == 0) {
                    //esta en elaboracion
                } else {
                    JOptionPane.showMessageDialog(pacienteI, "No decidiste reagendar la cita.");
                }
            } else {
                JOptionPane.showMessageDialog(pacienteI, "No se puede reagendar con menos de 4 horas de antelación.", "Error de reagendamiento", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void agregaMauseClick() {
        pacienteI.labelFotoPerfil.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                EditarPerfilInterfaz vista = new EditarPerfilInterfaz("Editar Perfil", usurio.getPrimerNombre(),
                        String.valueOf(usurio.getEdad()), usurio.getCorreo(), usurio.getNumeroCelular(),
                        usurio.getSexoBiologico(), String.valueOf(usurio.getFechaNacimiento()), String.valueOf(usurio.getSisben()), usurio.getFotoPerfil());
                EditarPerfilController cedI = new EditarPerfilController(vista, usurio.getIdUsuario());
                vista.setDefaultCloseOperation(EXIT_ON_CLOSE);
                vista.setExtendedState(MAXIMIZED_BOTH);
                vista.setVisible(true);
            }
        });
    }

    private void agregarActionListener() {
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

    protected void procesoNotificacion(String encabezado, String descripcion) {
        if (estadoNotificacion) {
            pacienteI.btnNotificaciones.setIcon(new ImageIcon("iconsP/notification.png"));
            estadoNotificacion = false;
        }
        pacienteI.agregarNotificaciones(encabezado, descripcion);
        MetodosPublicos.reproducirSonido("notificacion.wav");
    }

    private void proceso(String mensaje, boolean valor) {
        pacienteI.mostrarVistaHistorialConHistorial(mensaje, pacienteI.getUsuario().getPrimerNombre(),
                String.valueOf(pacienteI.getUsuario().getEdad()));
        pacienteI.btnDescargar.setEnabled(valor);
    }

    protected void procesoBtnHistorial() {
        pacienteI.habilitarBotonesMenu(pacienteI.btnHistorial);
        pacienteI.btnHistorialCitas.setEnabled(true);
        pacienteI.mostrarVistaHistorial();
        pacienteI.btnHistorialCitas.doClick();
    }

    protected void procesoBtnNotificaciones() {
        if (!estadoNotificacion) {
            pacienteI.btnNotificaciones.setIcon(new ImageIcon("iconsP/bell.png"));
            estadoNotificacion = true;
        }
        pacienteI.habilitarBotonesMenu(pacienteI.btnNotificaciones);
        pacienteI.mostrarVistaNotificaciones();
    }

    protected void estadoBotonesHistial(boolean estado) {
        pacienteI.btnHistorialCitas.setEnabled(estado);
        pacienteI.btnHistorialMedico.setEnabled(!estado);
    }

    protected void procesoBtnHistorialCitas() {
        estadoBotonesHistial(false);
        gestorCita.procesoBtnHistorialCitas();
    }

    protected void procesoBtnHistorialMedico() {
        estadoBotonesHistial(true);
        pacienteI.construirPanelVistaHistorialConHistorial();
        UsuarioDao usuDao = new UsuarioDao();
        this.historial = usuDao.historialMedico(pacienteI.getUsuario().getIdUsuario());
        if (historial == null) {
            proceso("No tienes historial medico", false);
        } else {
            proceso(historial, true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == pacienteI.btnCerrarSesion) {
            this.pacienteI.dispose();
            return;
        }
        if (e.getSource() == pacienteI.btnMisCitas) {
            gestorCita.procesoBtnMiscitas();
            return;
        }
        if (e.getSource() == pacienteI.btnHistorial) {
            procesoBtnHistorial();
            return;
        }
        if (e.getSource() == pacienteI.btnComentarios) {
            gestorForo.procesoBtnComentarios();
            return;
        }
        if (e.getSource() == pacienteI.btnNotificaciones) {
            procesoBtnNotificaciones();
            return;
        }
        if (e.getSource() == pacienteI.btnAgendar) {
            gestorCita.procesoBtnAgendar();
            return;
        }
        if (e.getSource() == pacienteI.btnOdontologia) {
            gestorCita.seleccionarEspecialidad(GestorCitas.ODONTOLOGIA);
            return;
        }
        if (e.getSource() == pacienteI.btnDermatologia) {
            gestorCita.seleccionarEspecialidad(GestorCitas.DERMATOLOGIA);
            return;
        }
        if (e.getSource() == pacienteI.btnMedicoGeneral) {
            gestorCita.seleccionarEspecialidad(GestorCitas.MEDICO_GENERAL);
            return;
        }
        if (e.getSource() == pacienteI.btnSugerencias) {
            gestorForo.procesoBtnSugerencia();
            return;
        }
        if (e.getSource() == pacienteI.btnQuejas) {
            gestorForo.procesoBtnQuejas();
            return;
        }
        if (e.getSource() == pacienteI.btnForo) {
            gestorForo.procesoBtnForo();
            return;
        }
        if (e.getSource() == pacienteI.btnEnviar) {
            gestorForo.procesoBtnEnviar();
            return;
        }
        if (e.getSource() == pacienteI.btnHistorialCitas) {
            procesoBtnHistorialCitas();
            return;
        }
        if (e.getSource() == pacienteI.btnHistorialMedico) {
            procesoBtnHistorialMedico();
            return;
        }
        if (e.getSource() == pacienteI.btnDescargar) {
            CreadorPdf.constructorCreadorPdf("historial_clinico_" + pacienteI.getUsuario().getPrimerNombre(), historial);
        }
    }
}