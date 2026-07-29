package controller;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import javax.swing.JButton;
import model.Cita;
import view.PacienteInterfaz;
import model.Usuario;
import model.CitaDao;
import model.MedicoDao;
import model.Medico;
import model.MetodosPublicos;
import view.Titulo;

public class GestorCitas {

    protected static Set<Cita> setCitas = new HashSet<>();
    protected static int contadorCitasCancelas = 0;
    protected static int contadorCitasAgendadas = 0;
    protected static int contadorCitasReagendadas = 0;

    protected static final byte MEDICO_GENERAL = 1;
    protected static final byte ODONTOLOGIA = 2;
    protected static final byte DERMATOLOGIA = 3;

    private final PacienteInterfaz pacienteI;
    private final PacienteController controller;
    private final Usuario usu;
    private final CitaDao citaDao;
    private final MedicoDao medicoDao;

    private ArrayList<Medico> medicosListados;
    private Set<Cita> hashSetMisCitas;
    private Medico medicoSeleccionado;

    public GestorCitas(PacienteInterfaz pacienteI, PacienteController controller) {
        hashSetMisCitas = new HashSet<>();
        citaDao = new CitaDao();
        this.pacienteI = pacienteI;
        usu = this.pacienteI.getUsuario();
        this.controller = controller;
        inicializarCitas();
        medicoDao = new MedicoDao();
        medicosListados = new ArrayList<>();
    }

    private void inicializarCitas() {
        if (hashSetMisCitas.isEmpty()) {
            ArrayList<Cita> arreglo = new ArrayList<>(citaDao.listarPorUsuario(usu.getIdUsuario()));
            for (Cita clave : arreglo) {
                hashSetMisCitas.add(clave);
            }
            arreglo = null;
        }
    }

    protected void procesoBtnMiscitas() {
        pacienteI.habilitarBotonesMenu(pacienteI.btnMisCitas);
        MetodosPublicos.vaciarPanel(pacienteI.panelInfoCitas);
        pacienteI.mostrarVistaMisCitas();
        if (hashSetMisCitas == null || hashSetMisCitas.isEmpty()) {
            pacienteI.agregarAlPanelMiscitas();
        } else {
            controller.vaciarListasBotones();
            int i = 0;
            for (Cita clave : hashSetMisCitas) {
                JButton botoncancelar = new JButton("Cancelar");
                JButton botonReagendar = new JButton("Reagendar");
                pacienteI.agregarAlPanelMiscitas(new Titulo("Cita ", clave.getEspecialidad(), 30).getPanelTitulo(),
                        clave.getFechaCita().toString(), clave.getHoraCita().toString(),
                        "Nombre Medico(a): " + clave.getNombreMedico(), botoncancelar, botonReagendar);
                controller.agregarBotonesListas(botoncancelar, botonReagendar);
                controller.agregarListenerBotonesCancelar(i, clave);
                controller.agregarListenerBotonesReagendar(i, clave);
                i++;
            }
        }
    }

    protected void procesoBtnAgendar() {
        pacienteI.mostrarVistaTipoConsulta(new Titulo("Agendamiento de ", "Cita"));
    }

    protected void seleccionarEspecialidad(byte n) {
        medicosListados.clear();
        medicoSeleccionado = null;
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
            });
        }
    }

    protected boolean isEliminarCita(Cita clave) {
        boolean validador = hashSetMisCitas.remove(clave);
        String mensaje;
        System.out.println(mensaje = validador ? "Cancelo Cita correctamente" : "No se cancelo la cita correctamente");
        return validador;
    }

}
