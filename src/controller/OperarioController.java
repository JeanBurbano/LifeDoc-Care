package controller;

import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javax.swing.JOptionPane;
import model.CalculadorHorarioDisponible;
import model.Cita;
import model.CitaDao;
import model.Consultorio;
import model.ConsultorioDao;
import model.DatosPagoCita;
import model.DatosPagoCitaDao;
import model.Horario;
import model.HorarioDao;
import model.HorarioDia;
import model.Paciente;
import model.PacienteDao;
import static model.MetodosPublicos.refrescarVentana;
import static model.MetodosPublicos.vaciarPanel;
import view.OperarioInterfaz;
import view.Titulo;

public class OperarioController extends PacienteController {

    private OperarioInterfaz vista;
    private DatosPagoCitaDao datosPagoCitaDao;
    private PacienteDao pacienteDao;
    private CitaDao citaDao;
    private ConsultorioDao consultorioDao;
    private HorarioDao horarioDao;
    
    public OperarioController(OperarioInterfaz vista) {
        super(vista);
        this.vista = vista;
        this.datosPagoCitaDao = new DatosPagoCitaDao();
        this.pacienteDao = new PacienteDao();
        this.citaDao = new CitaDao();
        this.consultorioDao = new ConsultorioDao();
        this.horarioDao = new HorarioDao();
        vista.setControlador(this);
        agregarActionListener(vista);
    }
    
    public void buscarPaciente(String id) {
        Paciente paciente = pacienteDao.buscarPorId(id);

        if (paciente != null) {
            vista.cargarDatosPaciente(
                paciente.getNumeroId(),
                paciente.getPrimerNombre(),
                paciente.getSegundoNombre(),
                paciente.getPrimerApellido(),
                paciente.getSegundoApellido(),
                paciente.getCorreoElectronico(),
                paciente.getNumeroTelefonico(),
                paciente.getFechaNacimiento() != null ? paciente.getFechaNacimiento().toString() : "",
                paciente.getSexoBiologico()
            );
            JOptionPane.showMessageDialog(vista,
                "Paciente encontrado y cargado correctamente.",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(vista,
                "No se encontró paciente con ID: " + id,
                "Paciente no encontrado",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public boolean registrarPago(int idCita, String descripcion, double valorNeto, String metodoParaBD) {
        int filasInsertadas = datosPagoCitaDao.crear(idCita, descripcion, valorNeto, metodoParaBD);
        return filasInsertadas > 0;
    }
    
    public List<DatosPagoCita> buscarCitasPendientesPago(int idUsuarioPaciente) {
        return datosPagoCitaDao.listarCitasPendientesPago(idUsuarioPaciente);
    }
    
    public Paciente buscarPacientePorId(String id) {
        return pacienteDao.buscarPorId(id);
    }
    
    public Cita[] listarCitasPorUsuario(int idUsuario) {
        return citaDao.listarPorUsuario(idUsuario);
    }
    
    public boolean cancelarCita(int idCita) {
        int resultado = citaDao.setEliminar(idCita);
        return resultado > 0;
    }
    
    public List<LocalTime> calcularHorasDisponiblesReagendar(int idMedico, LocalDate fecha) {
        Horario horarioMedico = horarioDao.obtenerPorMedico(idMedico);
        HorarioDia diaHorario = CalculadorHorarioDisponible.buscarDiaParaFecha(horarioMedico, fecha);
        if (diaHorario == null) {
            return null;
        }   
        List<LocalTime> ocupadas = citaDao.listarHorasOcupadas(idMedico, fecha);
        return CalculadorHorarioDisponible.calcularDisponibles(diaHorario, ocupadas);
    }
    
    public List<Consultorio> listarConsultorios() {
        return consultorioDao.listar();
    }
    
    public int reagendarCita(int idCita, LocalDate nuevaFecha, LocalTime nuevaHora, int idConsultorio) {
        return citaDao.reagendar(idCita, nuevaFecha, nuevaHora, idConsultorio);
    }
    
    public void agregarActionListener(OperarioInterfaz vista) {
        vista.btnAgendarCitas.addActionListener(this);
        vista.btnPagos.addActionListener(this);
        vista.btnAgendarCita.addActionListener(this);
        vista.btnConsultas.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        this.vista = (OperarioInterfaz) pacienteI;
        
        if (e.getSource() == vista.btnAgendarCitas) {
            this.vista.btnAgendarCitas.setEnabled(false);
            vista.habilitarBotonesMenu(vista.btnAgendarCitas);
            vista.AgendarCita();
        }
        if (e.getSource() == vista.btnPagos) {
            this.vista.btnPagos.setEnabled(false);
            vista.habilitarBotonesMenu(vista.btnPagos);
            vista.mostrarVistaPagos();
        }
        if (e.getSource() == vista.btnAgendarCita) {
            vista.mostrarVistaTipoConsulta(new Titulo("Agendamiento de ", "Cita"));
        }
        if (e.getSource() == vista.btnConsultas) {
            this.vista.btnConsultas.setEnabled(false);
            vista.habilitarBotonesMenu(vista.btnConsultas);
            vista.mostrarVistaConsultas();
        }
    }               
}
