package controller;

import com.github.lgooddatepicker.optionalusertools.CalendarListener;
import com.github.lgooddatepicker.zinternaltools.CalendarSelectionEvent;
import com.github.lgooddatepicker.zinternaltools.YearMonthChangeEvent;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import model.Cita;
import model.CalculadorHorarioDisponible;
import model.Horario;
import model.HorarioDao;
import model.HorarioDia;
import view.PacienteInterfaz;
import model.UsuarioPublico;
import model.CitaDao;
import model.MedicoDao;
import model.Medico;
import model.MetodosPublicos;
import view.Titulo;

public class GestorCitas {

    //set global compartido por todos los pacientes esto es para evitar que
    //dos personas agenden con el mismo medico en la misma fecha y hora
    protected static Set<Cita> setCitas = new HashSet<>();
    private static boolean setCitasCargado = false;

    protected static int contadorCitasCancelas = 0;
    protected static int contadorCitasAgendadas = 0;
    protected static int contadorCitasReagendadas = 0;

    protected static final byte MEDICO_GENERAL = 1;
    protected static final byte ODONTOLOGIA = 2;
    protected static final byte DERMATOLOGIA = 3;

    private static final int HORAS_MINIMAS_ANTELACION = 4;

    private final PacienteInterfaz pacienteI;
    private final PacienteController controller;
    private final UsuarioPublico usu;
    private final CitaDao citaDao;
    private final MedicoDao medicoDao;
    private final HorarioDao horarioDao;

    private ArrayList<Medico> medicosListados;
    private final Set<Cita> hashSetCitasActivas;
    private final Set<Cita> hashSetCitasNoActivas;
    private Medico medicoSeleccionado;
    private Horario horarioMedicoSeleccionado;

    public GestorCitas(PacienteInterfaz pacienteI, PacienteController controller) {
        hashSetCitasActivas = new HashSet<>();
        hashSetCitasNoActivas = new HashSet<>();
        citaDao = new CitaDao();
        horarioDao = new HorarioDao();
        this.pacienteI = pacienteI;
        usu = this.pacienteI.getUsuario();
        this.controller = controller;
        inicializarCitas();
        inicializarSetCitasGlobal();
        medicoDao = new MedicoDao();
        medicosListados = new ArrayList<>();
    }

    //separa las citas del paciente en vigentes y no vigentes si encuentra
    //una cita que quedo activa en bd pero ya paso de fecha la cancela
    private void inicializarCitas() {
        if (!hashSetCitasActivas.isEmpty() || !hashSetCitasNoActivas.isEmpty()) {
            return;
        }
        Cita[] todasLasCitas = citaDao.listarTodasPorUsuario(usu.getIdUsuario());
        LocalDateTime ahora = LocalDateTime.now();
        for (Cita clave : todasLasCitas) {
            LocalDateTime fechaHoraCita = clave.getFechaCita().atTime(clave.getHoraCita());
            boolean vigente = fechaHoraCita.isAfter(ahora);
            if (clave.getEstado() && vigente) {
                hashSetCitasActivas.add(clave);
            } else if (clave.getEstado() && !vigente) {
                //la cita quedo en estado activo pero ya paso se cancela por que logicamente no es posible una cita de ayer como vigente
                citaDao.setEliminar(clave.getIdCita());
                hashSetCitasNoActivas.add(new Cita(clave, false));
            } else {
                hashSetCitasNoActivas.add(clave);
            }
        }
    }

    //carga en el set global todas las citas activas que existen en la bd
    //solo se hace una vez sin importar cuantos pacientes abran la app
    private static synchronized void inicializarSetCitasGlobal() {
        if (setCitasCargado) {
            return;
        }
        CitaDao daoTemporal = new CitaDao();
        List<Cita> todasLasCitas = daoTemporal.listar();
        for (Cita clave : todasLasCitas) {
            if (clave.getEstado()) {
                setCitas.add(clave);
            }
        }
        setCitasCargado = true;
    }

    protected void procesoBtnMiscitas() {
        pacienteI.habilitarBotonesMenu(pacienteI.btnMisCitas);
        MetodosPublicos.vaciarPanel(pacienteI.panelInfoCitas);
        pacienteI.mostrarVistaMisCitas();
        if (hashSetCitasActivas.isEmpty()) {
            pacienteI.agregarAlPanelMiscitas();
        } else {
            controller.vaciarListasBotones();
            int i = 0;
            for (Cita clave : hashSetCitasActivas) {
                JButton botonCancelar = new JButton("Cancelar");
                JButton botonReagendar = new JButton("Reagendar");
                pacienteI.agregarAlPanelMiscitas(new Titulo("Cita ", clave.getEspecialidad(), 30).getPanelTitulo(),
                        clave.getFechaCita().toString(), clave.getHoraCita().toString(),
                        "Nombre Medico(a): " + clave.getNombreMedico(), botonCancelar, botonReagendar);
                controller.agregarBotonesListas(botonCancelar, botonReagendar);
                controller.agregarListenerBotonesCancelar(i, clave);
                controller.agregarListenerBotonesReagendar(i, clave);
                i++;
            }
        }
    }

    //recorre unicamente las citas no vigentes (historial)
    protected void procesoBtnHistorialCitas() {
        pacienteI.construirPanelVistaHistorial();
        pacienteI.vaciarPanel();
        if (hashSetCitasNoActivas.isEmpty()) {
            pacienteI.mostrarMensajeHistorialVacio();
        } else {
            for (Cita clave : hashSetCitasNoActivas) {
                pacienteI.agregarAlPanelHistorialCitas(
                        new Titulo("Cita ", clave.getEspecialidad(), 30).getPanelTitulo(),
                        clave.getFechaCita().toString(),
                        clave.getHoraCita().toString(),
                        "Dr(a). " + clave.getNombreMedico(),
                        "No Activa");
            }
        }
    }

    protected void procesoBtnAgendar() {
        pacienteI.mostrarVistaTipoConsulta(new Titulo("Agendamiento de ", "Cita"));
    }

    protected void seleccionarEspecialidad(byte n) {
        medicosListados.clear();
        medicoSeleccionado = null;
        horarioMedicoSeleccionado = null;
        controller.listaBotonesMedicos.clear();
        medicosListados = new ArrayList<>(medicoDao.listarPorEspecialidad(n));
        String[] nombreMedicos = new String[medicosListados.size()];
        for (int i = 0; i < medicosListados.size(); i++) {
            nombreMedicos[i] = medicosListados.get(i).getPrimerNombre() + " " + medicosListados.get(i).getPrimerApellido();
        }
        pacienteI.mostrarVistaSeleccionMedico(nombreMedicos, controller.listaBotonesMedicos);
        asignarListenerBotonesMedicos();
    }

    private void asignarListenerBotonesMedicos() {
        for (int i = 0; i < controller.listaBotonesMedicos.size(); i++) {
            Medico medicoDelBoton = medicosListados.get(i);
            controller.listaBotonesMedicos.get(i).addActionListener((ActionEvent e) -> {
                medicoSeleccionado = medicoDelBoton;
                pacienteI.mostrarVistaAgendamientoCita(new Titulo("Agenda una ", "Cita", 50));
                cargarHorarioMedicoSeleccionado();
                agregarListenerCalendario();
            });
        }
    }

    //trae el horario del medico seleccionado desde la bd
    private void cargarHorarioMedicoSeleccionado() {
        horarioMedicoSeleccionado = horarioDao.obtenerPorMedico(medicoSeleccionado.getId_medico());
    }

    //engancha el calendario para que cada vez que se elija una fecha se
    //calculen las horas disponibles de ese dia
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
                //no hacer nada
            }
        });
    }

    private void mostrarHorasDisponibles(LocalDate fecha) {
        pacienteI.limpiarPanelHorarios();
        HorarioDia diaHorario = CalculadorHorarioDisponible.buscarDiaParaFecha(horarioMedicoSeleccionado, fecha);
        if (diaHorario == null) {
            pacienteI.mostrarMensajeSinDisponibilidad("El medico no atiende este dia");
            return;
        }
        List<LocalTime> horasOcupadas = citaDao.listarHorasOcupadas(medicoSeleccionado.getId_medico(), fecha);
        List<LocalTime> horasDisponibles = CalculadorHorarioDisponible.calcularDisponibles(diaHorario, horasOcupadas);
        horasDisponibles = filtrarPorAntelacionMinima(horasDisponibles, fecha);
        if (horasDisponibles.isEmpty()) {
            pacienteI.mostrarMensajeSinDisponibilidad("No hay horarios disponibles para este dia");
            return;
        }
        for (LocalTime hora : horasDisponibles) {
            JButton botonHora = pacienteI.agregarBotonHoraDisponible(hora.toString());
            botonHora.addActionListener((ActionEvent e) -> confirmarAgendamiento(fecha, hora));
        }
    }

    //quita del listado las horas que no dejan un margen de 4 horas desde ahora
    private List<LocalTime> filtrarPorAntelacionMinima(List<LocalTime> horas, LocalDate fecha) {
        List<LocalTime> filtradas = new ArrayList<>();
        LocalDateTime limiteMinimo = LocalDateTime.now().plusHours(HORAS_MINIMAS_ANTELACION);
        for (LocalTime hora : horas) {
            LocalDateTime fechaHora = fecha.atTime(hora);
            if (!fechaHora.isBefore(limiteMinimo)) {
                filtradas.add(hora);
            }
        }
        return filtradas;
    }

    //intenta reservar el cupo en el set global antes de tocar la bd, si el
    //cupo ya estaba tomado por otro paciente el add devuelve false
    private void confirmarAgendamiento(LocalDate fecha, LocalTime hora) {
        Cita nuevaCita = new Cita((byte) 0, true, hora, fecha, (byte) usu.getIdUsuario(),
                usu.getPrimerNombre() + " " + usu.getPrimerApellido(), (byte) medicoSeleccionado.getId_medico(),
                medicoSeleccionado.getPrimerNombre() + " " + medicoSeleccionado.getPrimerApellido(),
                medicoSeleccionado.getEspecialidad(), (byte) usu.getIdUsuario());

        boolean cupoDisponible = setCitas.add(nuevaCita);
        if (!cupoDisponible) {
            JOptionPane.showMessageDialog(pacienteI, "Ese horario ya fue agendado por otro paciente, elige otro.",
                    "Horario no disponible", JOptionPane.ERROR_MESSAGE);
            mostrarHorasDisponibles(fecha);
            return;
        }

        int filasInsertadas = citaDao.setAgregar(nuevaCita);
        if (filasInsertadas > 0) {
            hashSetCitasActivas.add(nuevaCita);
            contadorCitasAgendadas++;
            controller.procesoNotificacion("Cita agendada", "Tu cita con el Dr(a). "
                    + nuevaCita.getNombreMedico() + " ha sido agendada correctamente.");
            procesoBtnMiscitas();
        } else {
            //no se pudo guardar en bd se libera el cupo que se habia reservado en memoria
            setCitas.remove(nuevaCita);
            JOptionPane.showMessageDialog(pacienteI, "No se pudo agendar la cita, intente mas tarde.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //calcula las horas que faltan para la cita, se usa para saber si
    //todavia se puede cancelar o reagendar
    protected long calcularHorasRestantes(Cita clave) {
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime fechaHoraCita = clave.getFechaCita().atTime(clave.getHoraCita());
        return ChronoUnit.HOURS.between(ahora, fechaHoraCita);
    }

    //usado tanto para cancelar como para reagendar, en ambos casos se exigen 4 horas de antelacion
    protected boolean tieneAntelacionSuficiente(Cita clave) {
        return calcularHorasRestantes(clave) >= HORAS_MINIMAS_ANTELACION;
    }

    //cancela la cita primero actualiza bd y solo si eso funciono mueve
    //la cita entre los hashSet en memoria
    protected boolean cancelarCita(Cita clave) {
        int filasActualizadas = citaDao.setEliminar(clave.getIdCita());
        if (filasActualizadas <= 0) {
            return false;
        }
        hashSetCitasActivas.remove(clave);
        hashSetCitasNoActivas.add(new Cita(clave, false));
        setCitas.remove(clave);
        contadorCitasCancelas++;
        return true;
    }
}