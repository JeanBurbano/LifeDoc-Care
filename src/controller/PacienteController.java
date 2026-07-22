package controller;

import com.github.lgooddatepicker.optionalusertools.CalendarListener;
import com.github.lgooddatepicker.zinternaltools.CalendarSelectionEvent;
import com.github.lgooddatepicker.zinternaltools.YearMonthChangeEvent;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import model.CalculadorHorarioDisponible;
import model.Cita;
import model.CitaDao;
import model.CreadorPdf;
import model.Foro;
import model.ForoDao;
import model.Horario;
import model.HorarioDao;
import model.HorarioDia;
import model.Medico;
import model.MedicoDao;
import model.Paciente;
import model.UsuarioDao;
import view.EditarPerfilInterfaz;
import view.PacienteInterfaz;
import view.Titulo;

public class PacienteController implements ActionListener {

    private static List<Foro> foro = new ArrayList<Foro>();
    private Paciente usurio;
    private CitaDao citadao;
    private MedicoDao medicodao;
    private ForoDao forodao;
    protected PacienteInterfaz pacienteI;//protected para que el hijo lo acceda directo
    private Medico[] medicos;
    private Medico medicoSeleccionado;
    protected Cita[] citas;
    private boolean verificador;
    private String historial;

    private void agregaMauseClick() {
        pacienteI.labelFotoPerfil.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                EditarPerfilInterfaz vista = new EditarPerfilInterfaz("Editar Perfil", usurio.getPrimerNombre(),
                        String.valueOf(usurio.getEdad()), usurio.getCorreoElectronico(), usurio.getNumeroTelefonico(),
                        usurio.getSexoBiologico(), String.valueOf(usurio.getFechaNacimiento()), usurio.getSisben(), usurio.getFotoPerfil());
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

    //funcion para rellenar foro si es null
    private void inicializarForo() {
        if (foro == null || foro.isEmpty()) {
            foro = forodao.listar();
        }
    }

    //constructor
    public PacienteController(PacienteInterfaz pacienteI) {
        this.pacienteI = pacienteI;
        this.usurio = this.pacienteI.getUsuario();
        this.citadao = new CitaDao();
        this.medicodao = new MedicoDao();
        this.forodao = new ForoDao();
        agregaMauseClick();
        agregarActionListener();
        inicializarForo();
    }

    //funsion 
    private void mostrarHorasDisponibles(LocalDate fecha) {
        pacienteI.limpiarPanelHorarios();
        Horario horarioMedico = new HorarioDao().obtenerPorMedico(medicoSeleccionado.getId_medico());
        HorarioDia diaHorario = CalculadorHorarioDisponible.buscarDiaParaFecha(horarioMedico, fecha);
        if (diaHorario == null) {
            pacienteI.mostrarMensajeSinDisponibilidad("El medico no atiende este dia.");
            return;
        }
        List<java.time.LocalTime> ocupadas = citadao.listarHorasOcupadas(medicoSeleccionado.getId_medico(), fecha);
        List<java.time.LocalTime> disponibles = CalculadorHorarioDisponible.calcularDisponibles(diaHorario, ocupadas);
        if (disponibles.isEmpty()) {
            pacienteI.mostrarMensajeSinDisponibilidad("No hay horas disponibles para este dia.");
            return;
        }
        for (java.time.LocalTime hora : disponibles) {
            JButton btnHora = pacienteI.agregarBotonHoraDisponible(hora.toString());
            btnHora.addActionListener((ActionEvent ev) -> confirmarCita(fecha, hora));
        }
    }

    //funcion para agregar el listener al calendario
    private void agregarListenerCalendario() {
        pacienteI.calendario.addCalendarListener(new CalendarListener() {
            @Override
            public void selectedDateChanged(CalendarSelectionEvent event) {
                LocalDate fechaSeleccionada = event.getNewDate();
                if (fechaSeleccionada == null || medicoSeleccionado == null) {
                    return;
                }
                mostrarHorasDisponibles(fechaSeleccionada);
            }

            @Override
            public void yearMonthChanged(YearMonthChangeEvent event) {
                // como tal no se necesita hacer nada aqui pero el metodo es obligatorio
            }
        });
    }

    private void confirmarCita(LocalDate fecha, java.time.LocalTime hora) {
        Cita nuevaCita = new Cita((byte) 0, true, hora, fecha, (byte) usurio.getIdUsuario(), null,
                (byte) medicoSeleccionado.getId_medico(), null, null, (byte) usurio.getIdUsuario());
        
        int resultado = citadao.setAgregar(nuevaCita);

        if (resultado == CitaDao.CONFLICTO_HORARIO) {
            JOptionPane.showMessageDialog(pacienteI, "Esa hora acaba de ser tomada, elige otra.");
            mostrarHorasDisponibles(fecha);
        } else if (resultado > 0) {
            JOptionPane.showMessageDialog(pacienteI, "Cita agendada correctamente.");
            pacienteI.btnMisCitas.doClick();
        } else {
            JOptionPane.showMessageDialog(pacienteI, "No se pudo agendar, intenta nuevamente.");
        }
    }

    private void actionListenerParaBotonesDeVectores(ArrayList<JButton> vectorBotones, String primero, String segundo) {
        for (int i = 0; i < vectorBotones.size(); i++) {
            JButton boton = vectorBotones.get(i);
            Medico medicoDelBoton = medicos[i];
            boton.addActionListener((ActionEvent e) -> {
                this.medicoSeleccionado = medicoDelBoton;
                pacienteI.mostrarVistaAgendamientoCita(new Titulo(primero, segundo, 50));
                agregarListenerCalendario();
            });
        }
    }

    private void estaditodeBotonesComentarios(JButton boton1, JButton boton2, JButton boton3) {
        boton1.setEnabled(false);
        boton2.setEnabled(true);
        boton3.setEnabled(true);
    }

    private void botonesFuncionalesMedicos(int n) {
        pacienteI.listaBotonesMedicos.clear();
        medicos = medicodao.listarPorEspecialidad(n);
        String[] nombreMedicos = new String[medicos.length];
        for (int i = 0; i < medicos.length; i++) {
            nombreMedicos[i] = medicos[i].getPrimerNombre() + " " + medicos[i].getPrimerApellido();
        }
        pacienteI.mostrarVistaSeleccionMedico(nombreMedicos);
        actionListenerParaBotonesDeVectores(pacienteI.listaBotonesMedicos, "Agenda una ", "Cita");
    }

    private void proceso(String mensaje, boolean valor) {
        pacienteI.mostrarVistaHistorialConHistorial(mensaje, pacienteI.getUsuario().getPrimerNombre(),
                String.valueOf(pacienteI.getUsuario().getEdad()));
        pacienteI.btnDescargar.setEnabled(valor);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == pacienteI.btnCerrarSesion) {
            this.pacienteI.dispose();
        }
        if (e.getSource() == pacienteI.btnMisCitas) {
            pacienteI.habilitarBotonesMenu(pacienteI.btnMisCitas);
            pacienteI.mostrarVistaMisCitas();
            this.pacienteI.listaBotonesCancelar.clear();
            this.pacienteI.listaBotonesReagendar.clear();
            this.citas = citadao.listarPorUsuario(usurio.getIdUsuario());
            if (citas == null || citas.length == 0) {
                pacienteI.agregarAlPanelMiscitas();
            } else {
                for (Cita clave : citas) {
                    pacienteI.agregarAlPanelMiscitas(new Titulo("Cita ", clave.getEspecialidad(), 30).getPanelTitulo(),
                            clave.getFechaCita().toString(), clave.getHoraCita().toString(),
                            "Nombre Medico(a): " + clave.getNombreMedico());
                }
            }
        }
        if (e.getSource() == pacienteI.btnHistorial) {
            pacienteI.habilitarBotonesMenu(pacienteI.btnHistorial);
            pacienteI.btnHistorialCitas.setEnabled(true);
            pacienteI.btnHistorialCitas.doClick();
        }
        if (e.getSource() == pacienteI.btnComentarios) {
            pacienteI.habilitarBotonesMenu(pacienteI.btnComentarios);
            pacienteI.mostrarVistaComentarios();
            pacienteI.btnSugerencias.doClick();
        }
        if (e.getSource() == pacienteI.btnNotificaciones) {
            pacienteI.habilitarBotonesMenu(pacienteI.btnNotificaciones);
            pacienteI.mostrarVistaNotificaciones();
        }
        if (e.getSource() == pacienteI.btnAgendar) {
            pacienteI.mostrarVistaTipoConsulta(new Titulo("Agendamiento de ", "Cita"));
        }
        if (e.getSource() == pacienteI.btnOdontologia) {
            botonesFuncionalesMedicos(2);
        }
        if (e.getSource() == pacienteI.btnDermatologia) {
            botonesFuncionalesMedicos(3);
        }
        if (e.getSource() == pacienteI.btnMedicoGeneral) {
            botonesFuncionalesMedicos(1);
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
            pacienteI.mostarPanelComentarioVacio();
            if (foro != null && !foro.isEmpty()) {
                for (Foro clave : foro) {
                    pacienteI.agregarAlPanelComentarios(clave.getTipoMensaje(), clave.getAsunto(),
                            clave.getNombreUsuario(), clave.getDescripcion());
                }
            }
        }
        if (e.getSource() == pacienteI.btnEnviar) {
            String asunto = pacienteI.campoAsunto.getText().trim();
            String descripcion = pacienteI.areaDescripcion.getText().trim();
            if (asunto.isEmpty() || descripcion.isEmpty()) {
                JOptionPane.showMessageDialog(pacienteI, "Los campos deben contener algo");
            } else {
                String tipoMensaje = verificador ? "Sugerencia" : "Queja";
                Foro nuevoComentario = new Foro(tipoMensaje, asunto, descripcion, usurio.getIdUsuario());
                int filasInsertadas = forodao.setAgregar(nuevoComentario);
                if (filasInsertadas > 0) {
                    nuevoComentario = new Foro(nuevoComentario.getTipoMensaje(), nuevoComentario.getAsunto(),
                            nuevoComentario.getDescripcion(), usurio.getPrimerNombre());
                    foro.add(0, nuevoComentario);
                    JOptionPane.showMessageDialog(pacienteI, "Tu " + tipoMensaje.toLowerCase() + " fue enviada correctamente");
                    pacienteI.campoAsunto.setText("");
                    pacienteI.areaDescripcion.setText("");
                } else {
                    JOptionPane.showMessageDialog(pacienteI, "No se pudo enviar tu " + tipoMensaje.toLowerCase() + ", intenta nuevamente");
                }
            }
        }
        if (e.getSource() == pacienteI.btnHistorialCitas) {
            pacienteI.btnHistorialCitas.setEnabled(false);
            pacienteI.btnHistorialMedico.setEnabled(true);
            pacienteI.mostrarVistaHistorial();
        }
        if (e.getSource() == pacienteI.btnHistorialMedico) {
            pacienteI.btnHistorialMedico.setEnabled(false);
            pacienteI.btnHistorialCitas.setEnabled(true);
            UsuarioDao usuDao = new UsuarioDao();
            this.historial = usuDao.historialMedico(pacienteI.getUsuario().getIdUsuario());
            if (historial == null) {
                proceso("No tienes historial medico", false);
            } else {
                proceso(historial, true);
            }
        }
        if (e.getSource() == pacienteI.btnDescargar) {
            CreadorPdf.constructorCreadorPdf("historial_clinico_" + pacienteI.getUsuario().getPrimerNombre(), historial);
        }
    }
}
