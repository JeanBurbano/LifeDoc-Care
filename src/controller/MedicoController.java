package controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import java.time.LocalDate;
import java.time.Period;
import model.Paciente;
import model.PacienteDao;
import model.Cita;
import model.CitaDao;
import model.HistorialMedico;
import model.HistorialMedicoDao;
import model.Medicamentos;
import model.MedicamentosDao;
import model.Medico;
import model.MedicoDao;
import model.MetodosPublicos;
import model.UsuarioDao;
import view.MedicoInterfaz;

public class MedicoController extends PacienteController {

    private CitaDao citadao;
    private MedicoDao medicodao;
    private Medico doc;
    private String historialPaciente, historial;
    protected Cita[] citas, citasConsultorio;
    private static List<HistorialMedico> historialM = new ArrayList<HistorialMedico>();
    private List<Medicamentos> nombresM = new ArrayList<>();
    private HistorialMedicoDao historialdao;
    MedicoInterfaz medico;

    public MedicoController(MedicoInterfaz medico) {
        super(medico);
        this.citadao = new CitaDao();
        this.historialdao = new HistorialMedicoDao();
        this.medicodao = new MedicoDao();
        this.doc = medicodao.buscarIdMedico(medico.getUsuario().getIdUsuario());

        this.medico.btnHistorialMedicoPaciente.addActionListener(this);
        this.medico.btnBuscarIdHistorialPaciente.addActionListener(this);
        this.medico.btnMiAgenda.addActionListener(this);
        this.medico.btnConsultorio.addActionListener(this);
        this.medico.simboloRegresarConfirmacionP.addActionListener(this);
        this.medico.btnAsistio.addActionListener(this);
        this.medico.btnNoAsistio.addActionListener(this);
        this.medico.btnGuardarFicha.addActionListener(this);
        this.medico.btnAceptarFicha.addActionListener(this);
        this.medico.btnVolverVerDetalles.addActionListener(this);
        this.medico.btnActReagendar.addActionListener(this);
        this.medico.btnNoReagendar.addActionListener(this);
        this.medico.btnAcpReagendamiento.addActionListener(this);
    }

    private void proceso(String mensaje, boolean valor, String nombrePaciente, String edadPaciente) {
        medico.mostrarVistaHistorialConHistorial(mensaje, nombrePaciente, edadPaciente);
        medico.btnDescargar.setEnabled(valor);
    }

    private void nombreMedicamentos() {
        nombresM = new MedicamentosDao().listarNombres();
        for (Medicamentos m : nombresM) {
            medico.campoMedicamento.addItem(m.getNombre());
        }
    }

    private void procesoCitasMiAgenda() {
        this.medico.mostrarVistaMiAgenda();
        this.medico.btnMiAgenda.setEnabled(false);
        this.medico.habilitarBotonesMenu(this.medico.btnMiAgenda);
        this.citas = citadao.listarPorMedico(doc.getId_medico());
        if (citas == null || citas.length == 0) {
            medico.sinCitas();
            MetodosPublicos.refrescarVentana(medico.panelPrincipal);
        } else {
            for (Cita clave : citas) {
                medico.citaVistaMiAgenda(clave.getEspecialidad(),
                        clave.getFechaCita().toString(), clave.getHoraCita().toString(),
                        clave.getNombrePaciente(), clave);
            }
        }
    }

    private void procesoCitasConsultorio() {
        this.medico.mostrarVistaConsultorio();
        this.medico.btnConsultorio.setEnabled(false);
        this.medico.habilitarBotonesMenu(this.medico.btnConsultorio);
        this.citasConsultorio = citadao.listarPorMedico(doc.getId_medico());
        if (citasConsultorio == null || citasConsultorio.length == 0) {
            medico.sinCitas();
            MetodosPublicos.refrescarVentana(medico.panelPrincipal);
        } else {
            for (Cita clave : citasConsultorio) {
                medico.citaVistaConsultorio(clave.getEspecialidad(),
                        clave.getFechaCita().toString(), clave.getHoraCita().toString(),
                        clave.getNombrePaciente(), clave);

            }
        }
    }

    private void procesoGuardarFicha() {
        String diagnostico = medico.campoDiagnostico.getText().trim();
        String medicamento = (String) medico.campoMedicamento.getSelectedItem();
        String observaciones = medico.campoObservaciones.getText().trim();
        String descripcion = "Diagnóstico: " + diagnostico + "\n\n"
                + (medicamento != null && !medicamento.isBlank() ? "Medicamento: " + medicamento + "\n\n" : "")
                + "Observaciones: " + observaciones;

        if (diagnostico.isBlank()) {
            JOptionPane.showMessageDialog(medico, "El campo diagnostico debe contener algo");
        } else if (observaciones.isBlank()) {
            JOptionPane.showMessageDialog(medico, "El campo descripción debe contener algo");
        } else {
            HistorialMedico historial = new HistorialMedico(
                    medico.citaSeleccionada.getIdUsuario(),
                    doc.getId_medico(),
                    medico.citaSeleccionada.getIdCita(),
                    medico.citaSeleccionada.getFechaCita(),
                    LocalTime.now(),
                    descripcion
            );

            boolean guardado = historialdao.agregar(historial);
            if (guardado) {
                historialdao.actualizarEstadoCita(medico.citaSeleccionada.getIdCita());
                this.medico.mostrarVistaConfirmacionFichaGuardada();
                medico.campoDiagnostico.setText("");
                medico.campoMedicamento.setSelectedIndex(0);
                medico.campoObservaciones.setText("");
            } else {
                JOptionPane.showMessageDialog(medico, "No se pudo guardar la ficha clínica, intenta nuevamente");
            }
        }
    }

    @Override
    protected void estadoBotonesHistial(boolean estado) {
        activarSeccionHistorial(estado ? pacienteI.btnHistorialMedico : pacienteI.btnHistorialCitas);
    }

    private void activarSeccionHistorial(javax.swing.JButton activo) {
        medico.btnHistorialMedicoPaciente.setEnabled(activo != medico.btnHistorialMedicoPaciente);
        pacienteI.btnHistorialCitas.setEnabled(activo != pacienteI.btnHistorialCitas);
        pacienteI.btnHistorialMedico.setEnabled(activo != pacienteI.btnHistorialMedico);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        this.medico = (MedicoInterfaz) pacienteI;

        if (e.getSource() == this.medico.btnBuscarIdHistorialPaciente) {
            activarSeccionHistorial(this.medico.btnHistorialMedicoPaciente);
            String idHistorial = medico.idHistorial.getText().trim();
            if (idHistorial.isBlank()) {
                JOptionPane.showMessageDialog(medico, "Por favor ingresa un numero de identificación");
            } else {
                Paciente pacienteBuscado = new PacienteDao().buscarPorId(idHistorial);
                if (pacienteBuscado == null) {
                    JOptionPane.showMessageDialog(medico, "No se encontró ningún paciente con esa identificación");
                } else {
                    UsuarioDao usuDao = new UsuarioDao();
                    this.historialPaciente = usuDao.historialMedicoPorId(idHistorial);

                    String nombrePaciente = pacienteBuscado.getPrimerNombre() + " " + pacienteBuscado.getPrimerApellido();
                    String edadPaciente = String.valueOf(
                            Period.between(pacienteBuscado.getFechaNacimiento(), LocalDate.now()).getYears());

                    if (historialPaciente == null) {
                        proceso("Este paciente no tiene un historial medico", false, nombrePaciente, edadPaciente);
                    } else {
                        proceso(historialPaciente, true, nombrePaciente, edadPaciente);
                    }
                }
            }
            return;
        }

        if (e.getSource() == this.medico.btnMiAgenda) {
            procesoCitasMiAgenda();
            return;
        }

        if (e.getSource() == this.medico.btnVolverVerDetalles) {
            procesoCitasMiAgenda();
            return;
        }

        if (e.getSource() == this.medico.btnNoReagendar) {
            procesoCitasMiAgenda();
            return;
        }

        if (e.getSource() == this.medico.btnActReagendar) {
            this.medico.mostrarVistaReagendarCitaMiAgenda(medico.citaSeleccionada);
            return;
        }

        if (e.getSource() == this.medico.btnAcpReagendamiento) {
            procesoCitasMiAgenda();
            return;
        }

        if (e.getSource() == this.medico.btnConsultorio) {
            procesoCitasConsultorio();
            return;
        }

        if (e.getSource() == this.medico.simboloRegresarConfirmacionP) {
            procesoCitasConsultorio();
            return;
        }

        if (e.getSource() == this.medico.btnAceptarFicha) {
            procesoCitasConsultorio();
            return;
        }

        if (e.getSource() == this.medico.btnAsistio) {
            this.medico.mostrarVistaFichaClinica();
            nombreMedicamentos();
            return;
        }

        if (e.getSource() == this.medico.btnNoAsistio) {
            historialdao.actualizarEstadoCita(medico.citaSeleccionada.getIdCita());
            procesoCitasConsultorio();
            return;
        }

        if (e.getSource() == this.medico.btnGuardarFicha) {
            procesoGuardarFicha();
            return;
        }

        if (e.getSource() == this.medico.btnAceptarFicha) {
            this.medico.mostrarVistaConsultorio();
            this.medico.btnConsultorio.setEnabled(false);
            this.medico.habilitarBotonesMenu(this.medico.btnConsultorio);
            return;
        }

        if (e.getSource() == this.medico.btnHistorialMedicoPaciente) {
            this.medico.mostrarVistaHistorial();
            activarSeccionHistorial(this.medico.btnHistorialMedicoPaciente);
            this.medico.mostrarFormularioHistorialMedicoPaciente();
            return;
        }
    }
}
