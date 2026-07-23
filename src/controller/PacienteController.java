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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
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
import model.MetodosPublicos;
import model.Paciente;
import model.UsuarioDao;
import view.EditarPerfilInterfaz;
import view.PacienteInterfaz;
import view.Titulo;

public class PacienteController implements ActionListener {

    //variables de clase
    private static List<Foro> foro = new ArrayList<Foro>();
    private final static byte MEDICINA_GENERAL = 1;
    private final static byte DERMATOLOGIA = 3;
    private final static byte ODONTOLOGIA = 2;

    private Paciente usurio;
    private CitaDao citadao;
    private MedicoDao medicodao;
    private ForoDao forodao;
    protected PacienteInterfaz pacienteI;

    private Medico[] medicos;
    private Medico medicoSeleccionado;
    private int especialidadSeleccionada; // 1 = Medico General, 2 = Odontologia, 3 = Dermatologia

    protected Cita[] citas;
    private boolean verificador;
    private String historial;
    private Cita citaAReagendar;

    private boolean estadoNotificacion;

    public PacienteController(PacienteInterfaz pacienteI) {
        this.pacienteI = pacienteI;
        this.usurio = this.pacienteI.getUsuario();
        this.citadao = new CitaDao();
        this.medicodao = new MedicoDao();
        this.forodao = new ForoDao();
        agregaMauseClick();
        agregarActionListener();
        inicializarForo();
        this.estadoNotificacion = true;
    }

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

    private void inicializarForo() {
        if (foro == null || foro.isEmpty()) {
            foro = forodao.listar();
        }
    }

    private void botonesFuncionalesMedicos(int n) {
        this.especialidadSeleccionada = n;
        pacienteI.listaBotonesMedicos.clear();
        medicos = medicodao.listarPorEspecialidad(n);
        String[] nombreMedicos = new String[medicos.length];
        for (int i = 0; i < medicos.length; i++) {
            nombreMedicos[i] = medicos[i].getPrimerNombre() + " " + medicos[i].getPrimerApellido();
        }
        pacienteI.mostrarVistaSeleccionMedico(nombreMedicos);
        actionListenerParaBotonesDeVectores(pacienteI.listaBotonesMedicos, "Agenda una ", "Cita");
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
                //no se necesita hacer nada aqui
            }
        });
    }

    private void mostrarHorasDisponibles(LocalDate fecha) {
        pacienteI.limpiarPanelHorarios();

        Horario horarioMedico = new HorarioDao().obtenerPorMedico(medicoSeleccionado.getId_medico());
        HorarioDia diaHorario = CalculadorHorarioDisponible.buscarDiaParaFecha(horarioMedico, fecha);
        if (diaHorario == null) {
            pacienteI.mostrarMensajeSinDisponibilidad("El medico no atiende este dia.");
            return;
        }

        List<LocalTime> ocupadas = citadao.listarHorasOcupadas(medicoSeleccionado.getId_medico(), fecha);
        if (citaAReagendar != null && fecha.equals(citaAReagendar.getFechaCita())) {
            ocupadas.remove(citaAReagendar.getHoraCita());
        }
        List<LocalTime> disponibles = CalculadorHorarioDisponible.calcularDisponibles(diaHorario, ocupadas);
        if (disponibles.isEmpty()) {
            pacienteI.mostrarMensajeSinDisponibilidad("No hay horas disponibles para este dia.");
            return;
        }

        for (LocalTime hora : disponibles) {
            JButton btnHora = pacienteI.agregarBotonHoraDisponible(hora.toString());
            btnHora.addActionListener((ActionEvent ev) -> {
                btnHora.setEnabled(false);
                confirmarCita(fecha, hora);
            });
        }
    }

    protected void procesoNotificacion(String encabezado, String descripcion) {
        if (estadoNotificacion) {
            pacienteI.btnNotificaciones.setIcon(new ImageIcon("iconsP/notification.png"));
            estadoNotificacion = false;
        }
        pacienteI.agregarNotificaciones(encabezado, descripcion);
        MetodosPublicos.reproducirSonido("notificacion.wav");
    }

    protected void procesoConfirmarCitaVerdadero(LocalDate fecha, LocalTime hora) {
        JOptionPane.showMessageDialog(pacienteI, "Cita agendada correctamente.");
        procesoNotificacion("Cita confirmada",
                "Tu cita con el Dr(a). " + medicoSeleccionado.getPrimerNombre()
                + " " + medicoSeleccionado.getPrimerApellido()
                + " ha sido programada para el " + fecha + " a las " + hora + ".");
//        switch (especialidadSeleccionada) {
//            case MEDICINA_GENERAL:
//                this.pacienteI.btnMedicoGeneral.setEnabled(false);
//                break;
//            case ODONTOLOGIA:
//                this.pacienteI.btnOdontologia.setEnabled(false);
//                break;
//            case DERMATOLOGIA:
//                this.pacienteI.btnDermatologia.setEnabled(false);
//                break;
//        }
        procesoBtnMisCitas();
    }

    private void confirmarCita(LocalDate fecha, LocalTime hora) {
        if (citaAReagendar != null) {
            int resultado = citadao.setReagendar(citaAReagendar.getIdCita(), medicoSeleccionado.getId_medico(), fecha, hora);
            if (resultado == CitaDao.CONFLICTO_HORARIO) {
                JOptionPane.showMessageDialog(pacienteI, "Esa hora acaba de ser tomada, elige otra.");
                mostrarHorasDisponibles(fecha);
            } else if (resultado > 0) {
                JOptionPane.showMessageDialog(pacienteI, "Cita reagendada correctamente.");
                procesoNotificacion("Cita reagendada",
                        "Tu cita con el Dr(a). " + medicoSeleccionado.getPrimerNombre()
                        + " " + medicoSeleccionado.getPrimerApellido()
                        + " ahora es el " + fecha + " a las " + hora + ".");
                citaAReagendar = null;
                procesoBtnMisCitas();
            } else {
                JOptionPane.showMessageDialog(pacienteI, "No se pudo reagendar, intenta nuevamente.");
            }
            return;
        }

        Cita nuevaCita = new Cita((byte) 0, true, hora, fecha, (byte) usurio.getIdUsuario(), null,
                (byte) medicoSeleccionado.getId_medico(), null, null, (byte) usurio.getIdUsuario());

        int resultado = citadao.setAgregar(nuevaCita);

        if (resultado == CitaDao.CONFLICTO_HORARIO) {
            JOptionPane.showMessageDialog(pacienteI, "Esa hora acaba de ser tomada, elige otra.");
            mostrarHorasDisponibles(fecha);
        } else if (resultado > 0) {
            procesoConfirmarCitaVerdadero(fecha, hora);
        } else {
            JOptionPane.showMessageDialog(pacienteI, "No se pudo agendar, intenta nuevamente.");
        }
    }

    private void estaditodeBotonesComentarios(JButton boton1, JButton boton2, JButton boton3) {
        boton1.setEnabled(false);
        boton2.setEnabled(true);
        boton3.setEnabled(true);
    }

    private void proceso(String mensaje, boolean valor) {
        pacienteI.mostrarVistaHistorialConHistorial(mensaje, pacienteI.getUsuario().getPrimerNombre(),
                String.valueOf(pacienteI.getUsuario().getEdad()));
        pacienteI.btnDescargar.setEnabled(valor);
    }

    private int mapearEspecialidadAnumero(String especialidad) {
        if (especialidad == null) {
            return MEDICINA_GENERAL;
        }
        switch (especialidad) {
            case "Odontologia":
                return ODONTOLOGIA;
            case "Dermatologia":
                return DERMATOLOGIA;
            default:
                return MEDICINA_GENERAL;
        }
    }

    private void iniciarReagendamiento(Cita citaOriginal) {
        this.citaAReagendar = citaOriginal;
        this.especialidadSeleccionada = mapearEspecialidadAnumero(citaOriginal.getEspecialidad());
        Medico[] medicosEspecialidad = medicodao.listarPorEspecialidad(especialidadSeleccionada);
        for (Medico m : medicosEspecialidad) {
            if (m.getId_medico() == citaOriginal.getIdMedico()) {
                this.medicoSeleccionado = m;
                break;
            }
        }
        pacienteI.mostrarVistaAgendamientoCita(new Titulo("Reagendar ", "Cita", 50));
        agregarListenerCalendario();
    }

    protected void procesoBtnMisCitas() {
        this.citaAReagendar = null; //Esto lo pongo por si quedo a medias un reagendamiento anterior
        pacienteI.habilitarBotonesMenu(pacienteI.btnMisCitas);
        pacienteI.mostrarVistaMisCitas();
        this.pacienteI.listaBotonesCancelar.clear();
        this.pacienteI.listaBotonesReagendar.clear();
        this.citas = citadao.listarPorUsuario(usurio.getIdUsuario());
        if (citas == null || citas.length == 0) {
            pacienteI.agregarAlPanelMiscitas();
        } else {
            int i = 0;
            for (Cita clave : citas) {
                pacienteI.agregarAlPanelMiscitas(new Titulo("Cita ", clave.getEspecialidad(), 30).getPanelTitulo(),
                        clave.getFechaCita().toString(), clave.getHoraCita().toString(),
                        "Nombre Medico(a): " + clave.getNombreMedico());

                pacienteI.listaBotonesCancelar.get(i).addActionListener((ActionEvent e) -> {
                    int n = citadao.setEliminar(clave.getIdCita());
                    if (n > 0) {
                        procesoNotificacion("Cita cancelada", "Tu cita con el Dr(a). " + clave.getNombreMedico()
                                + " ha sido cancelada correctamente.");
                        procesoBtnMisCitas();
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo cancelar su cita por favor intentalo mas tarde");
                    }
                });

                pacienteI.listaBotonesReagendar.get(i).addActionListener((ActionEvent e) -> {
                    iniciarReagendamiento(clave);
                });
                i++;
            }
        }
    }

    protected void procesoBtnHistorial() {
        pacienteI.habilitarBotonesMenu(pacienteI.btnHistorial);
        pacienteI.btnHistorialCitas.setEnabled(true);
        pacienteI.btnHistorialCitas.doClick();
    }

    protected void procesoBtnComentarios() {
        pacienteI.habilitarBotonesMenu(pacienteI.btnComentarios);
        pacienteI.mostrarVistaComentarios();
        pacienteI.btnSugerencias.doClick();
    }

    protected void procesoBtnNotificaciones() {
        if (!estadoNotificacion) {
            pacienteI.btnNotificaciones.setIcon(new ImageIcon("iconsP/bell.png"));
            estadoNotificacion = true;
        }
        pacienteI.habilitarBotonesMenu(pacienteI.btnNotificaciones);
        pacienteI.mostrarVistaNotificaciones();
    }

    protected void procesoBtnSugerencia() {
        estaditodeBotonesComentarios(pacienteI.btnSugerencias, pacienteI.btnQuejas, pacienteI.btnForo);
        this.verificador = true;
        pacienteI.construirFormularioComentario();
    }

    protected void procesoBtnQuejas() {
        estaditodeBotonesComentarios(pacienteI.btnQuejas, pacienteI.btnSugerencias, pacienteI.btnForo);
        this.verificador = false;
        pacienteI.construirFormularioComentario();
    }

    protected void procesoBtnForo() {
        estaditodeBotonesComentarios(pacienteI.btnForo, pacienteI.btnQuejas, pacienteI.btnSugerencias);
        pacienteI.mostarPanelComentarioVacio();
        if (foro != null && !foro.isEmpty()) {
            for (Foro clave : foro) {
                pacienteI.agregarAlPanelComentarios(clave.getTipoMensaje(), clave.getAsunto(),
                        clave.getNombreUsuario(), clave.getDescripcion());
            }
        }
    }

    protected void procesoBtnEnviar() {
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

    private void estadoBotonesHistial(boolean estado) {
        pacienteI.btnHistorialCitas.setEnabled(estado);
        pacienteI.btnHistorialMedico.setEnabled(!estado);
    }

    protected void procesoBtnHistorialCitas() {
        estadoBotonesHistial(false);
        pacienteI.mostrarVistaHistorial();
        Cita[] citasHistorial = citadao.listarTodasPorUsuario(pacienteI.getUsuario().getIdUsuario());
        if (citasHistorial == null || citasHistorial.length == 0) {
            pacienteI.mostrarMensajeHistorialVacio();
        } else {
            for (Cita clave : citasHistorial) {
                String estadoCita = clave.getEstado() ? "Activa" : "Cancelada";
                pacienteI.agregarAlPanelHistorialCitas(
                        new Titulo("Cita ", clave.getEspecialidad(), 30).getPanelTitulo(),
                        clave.getFechaCita().toString(),
                        clave.getHoraCita().toString(),
                        "Dr(a). " + clave.getNombreMedico(),
                        estadoCita);
            }
        }
    }

    protected void procesoBtnHistorialMedico() {
        estadoBotonesHistial(true);
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
            procesoBtnMisCitas();
            return;
        }
        if (e.getSource() == pacienteI.btnHistorial) {
            procesoBtnHistorial();
            return;
        }
        if (e.getSource() == pacienteI.btnComentarios) {
            procesoBtnComentarios();
            return;
        }
        if (e.getSource() == pacienteI.btnNotificaciones) {
            procesoBtnNotificaciones();
            return;
        }
        if (e.getSource() == pacienteI.btnAgendar) {
            pacienteI.mostrarVistaTipoConsulta(new Titulo("Agendamiento de ", "Cita"));
            return;
        }
        if (e.getSource() == pacienteI.btnOdontologia) {
            botonesFuncionalesMedicos(2);
            return;
        }
        if (e.getSource() == pacienteI.btnDermatologia) {
            botonesFuncionalesMedicos(3);
            return;
        }
        if (e.getSource() == pacienteI.btnMedicoGeneral) {
            botonesFuncionalesMedicos(1);
            return;
        }
        if (e.getSource() == pacienteI.btnSugerencias) {
            procesoBtnSugerencia();
            return;
        }
        if (e.getSource() == pacienteI.btnQuejas) {
            procesoBtnQuejas();
            return;
        }
        if (e.getSource() == pacienteI.btnForo) {
            procesoBtnForo();
            return;
        }
        if (e.getSource() == pacienteI.btnEnviar) {
            procesoBtnEnviar();
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
