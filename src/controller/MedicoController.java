package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import model.Cita;
import model.CitaDao;
import model.HistorialMedico;
import model.HistorialMedicoDao;
import model.Medicamentos;
import model.MedicamentosDao;
import model.Medico;
import model.MetodosPublicos;
import model.UsuarioDao;
import view.MedicoInterfaz;
import view.Titulo;

public class MedicoController extends PacienteController {

    private CitaDao citadao;
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
    }

    private void habilitarBotonesHistorial(JButton botonA, JButton boton2, JButton boton3) {
        botonA.setEnabled(false);
        boton2.setEnabled(true);
        boton3.setEnabled(true);
    }

    private void proceso(String mensaje, boolean valor) {
        medico.mostrarVistaHistorialConHistorial(mensaje, medico.getUsuario().getPrimerNombre(),
                String.valueOf(pacienteI.getUsuario().getEdad()));
        medico.btnDescargar.setEnabled(valor);
    }
    
//    private void nombreMedicamentos() {
//        nombresM = new MedicamentosDao().listarNombres();
//        medico.campoMedicamento.removeAllItems();
//        for (Medicamentos m : nombresM) {
//            medico.campoMedicamento.addItem(m);
//        }
//    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        this.medico = (MedicoInterfaz) pacienteI;
//        if (e.getSource() == this.medico.btnHistorial) {
//            habilitarBotonesHistorial(this.medico.btnHistorialCitas, this.medico.btnHistorialMedicoPaciente, this.medico.btnHistorialMedico);
//            medico.btnHistorialCitas.doClick();
//        }
//        
//        if (e.getSource() == this.medico.btnHistorialCitas) {
//            habilitarBotonesHistorial(this.medico.btnHistorialCitas, this.medico.btnHistorialMedicoPaciente, this.medico.btnHistorialMedico);
//            pacienteI.mostrarVistaHistorial();
//        }
//        
//        if (e.getSource() == this.medico.btnHistorialMedico) {
//            habilitarBotonesHistorial(this.medico.btnHistorialMedico, this.medico.btnHistorialCitas, this.medico.btnHistorialMedicoPaciente);
//            UsuarioDao usuDao = new UsuarioDao();
//            this.historial = usuDao.historialMedico(medico.getUsuario().getIdUsuario());
//            if (historial == null) {
//                proceso("No tienes historial medico", false);
//            } else {
//                proceso(historial, true);
//            }
//        }
//        
//        if (e.getSource() == this.medico.btnHistorialMedicoPaciente) {
//            this.medico.mostrarFormularioHistorialMedicoPaciente();
//            habilitarBotonesHistorial(this.medico.btnHistorialMedicoPaciente, this.medico.btnHistorialCitas, this.medico.btnHistorialMedico);
//        }
//        
//        if (e.getSource() == this.medico.btnBuscarIdHistorialPaciente) {
//            habilitarBotonesHistorial(this.medico.btnHistorialMedicoPaciente, this.medico.btnHistorialCitas, this.medico.btnHistorialMedico);
//            String idHistorial = medico.idHistorial.getText().trim();
//            if (idHistorial.isBlank()) {
//                JOptionPane.showMessageDialog(medico, "Por favor ingresa un numero de identificación");
//            } else {
//                UsuarioDao usuDao = new UsuarioDao();
//                this.historialPaciente = usuDao.historialMedicoPorId(idHistorial);
//                if (historialPaciente == null) {
//                    proceso("Este paciente no tiene un historial medico", false);
//                } else {
//                    proceso(historialPaciente, true);
//                }
//            }
//        }
        
        if (e.getSource() == this.medico.btnMiAgenda || e.getSource() == this.medico.btnVolverVerDetalles || e.getSource() == this.medico.btnNoReagendar) {
            this.medico.mostrarVistaMiAgenda();
            this.medico.btnMiAgenda.setEnabled(false);
            this.medico.habilitarBotonesMenu(this.medico.btnMiAgenda);
            this.citas = citadao.listarPorMedico(medico.getUsuario().getIdUsuario());
            if (citas == null || citas.length == 0) {
                medico.panelPrincipal.add(new JLabel("No tienes citas asignadas"));
                MetodosPublicos.refrescarVentana(medico.panelPrincipal);
            } else {
                for (Cita clave : citas) {
                    medico.citaVistaMiAgenda(clave.getEspecialidad(),
                            clave.getFechaCita().toString(), clave.getHoraCita().toString(),
                            clave.getNombrePaciente(),clave);
                }
            }
        }
        
        if (e.getSource() == this.medico.btnConsultorio || e.getSource() == this.medico.simboloRegresarConfirmacionP || e.getSource() == this.medico.btnNoAsistio || e.getSource() == this.medico.btnAceptarFicha) {
            this.medico.mostrarVistaConsultorio();
            this.medico.btnConsultorio.setEnabled(false);
            this.medico.habilitarBotonesMenu(this.medico.btnConsultorio);
            this.citasConsultorio = citadao.listarPorMedico(medico.getUsuario().getIdUsuario());
            if (citasConsultorio == null || citasConsultorio.length == 0) {
                medico.panelPrincipal.add(new JLabel("No tienes citas asignadas"));
                MetodosPublicos.refrescarVentana(medico.panelPrincipal);
            } else {
                for (Cita clave : citasConsultorio) {
                    medico.citaVistaConsultorio(clave.getEspecialidad(),
                            clave.getFechaCita().toString(), clave.getHoraCita().toString(),
                            clave.getNombrePaciente(), clave);

                }
            }
        }
        if (e.getSource() == this.medico.btnAsistio) {
            this.medico.mostrarVistaFichaClinica();
            nombreMedicamentos();
        }
        
        if (e.getSource() == this.medico.btnNoAsistio) {
            historialdao.actualizarEstadoCita(medico.citaSeleccionada.getIdCita());
        }
        
        if (e.getSource() == this.medico.btnGuardarFicha) {
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
                        medico.citaSeleccionada.getIdUsuario(), // id_usuario -> el paciente de la cita
                        medico.getUsuario().getIdUsuario(), // id_medico -> el médico logueado
                        medico.citaSeleccionada.getIdCita(), // id_cita -> la cita que se está atendiendo
                        medico.citaSeleccionada.getFechaCita(), // dia -> el día de la cita
                        LocalTime.now(), // hora -> el momento real en que se guarda
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
        
        if (e.getSource() == this.medico.btnAceptarFicha) {
            this.medico.mostrarVistaConsultorio();
            this.medico.btnConsultorio.setEnabled(false);
            this.medico.habilitarBotonesMenu(this.medico.btnConsultorio);
        }
//        if (e.getSource() == this.medico.btnVerDetalles) {
//            this.medico.mostrarDetallesCita();
//            this.medico.btnMiAgenda.setEnabled(false);
//            this.medico.habilitarBotonesMenu(this.medico.btnMiAgenda);
//        }
//        if (e.getSource() == this.medico.btnReagendarCita) {
//            this.medico.mostrarVistaConfirmacionReagendar();
//            this.medico.btnMisCitas.setEnabled(false);
//            this.medico.btnHistorial.setEnabled(false);
//            this.medico.btnComentarios.setEnabled(false);
//            this.medico.btnNotificaciones.setEnabled(false);
//            this.medico.btnMiAgenda.setEnabled(false);
//            this.medico.btnConsultorio.setEnabled(false);
//        }
//        if (e.getSource() == this.medico.btnActReagendar) {
//
//        }
    }
}
