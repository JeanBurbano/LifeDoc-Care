/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.File;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.Consultorio;
import model.ConsultorioDao;
import model.Horario;
import model.HorarioDao;
import model.HorarioDia;
import model.Medico;
import model.MedicoDao;
import model.Medicos;
import model.MedicosDao;
import view.ConstructorFilaHorario;
import view.AdministradorCentroInterfaz;

public class AdminCentroController extends PacienteController {

    AdministradorCentroInterfaz adminI;

    // Columnas de la tabla de horarios
    private static final int COLUMNA_ASIGNAR = 3;
    private static final int COLUMNA_EDITAR = 4;
    private static final int COLUMNA_ELIMINAR = 5;

    private List<Horario> horariosActuales = new ArrayList<>();
    private List<Medicos> medicosActuales = new ArrayList<>();
    private List<Consultorio> consultoriosActuales = new ArrayList<>();

    public AdminCentroController(AdministradorCentroInterfaz adminI) {
        super(adminI);

        // menu principal
        this.adminI.btnPersonalCentro.addActionListener(this);
        this.adminI.btnInventarioMedicamentos.addActionListener(this);
        this.adminI.btnHorarioMedico.addActionListener(this);

        //botones de apartados
        this.adminI.btnregistrarPersonal.addActionListener(this);
        this.adminI.btnAñadirMedicamento.addActionListener(this);
        this.adminI.btnGuardarMedicamento.addActionListener(this);
        this.adminI.btnSeleccionar.addActionListener(this);
        this.adminI.btnCrearHorario.addActionListener(this);
        this.adminI.btnVolver.addActionListener(this);
        this.adminI.btnGuardarHorario.addActionListener(this);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        this.adminI = (AdministradorCentroInterfaz) pacienteI;

        manejarMenuPrincipal(e);
        manejarApartadoPersonal(e);
        manejarApartadoMedicamento(e);
        manejarApartadoHorario(e);
        manejarFormularioHorario(e);
        manejarTablaHorarios(e);
    }

    // menu principal
    private void manejarMenuPrincipal(ActionEvent e) {
        if (e.getSource() == adminI.btnPersonalCentro) {
            adminI.mostrarVistaPersonalCentroApartado();
            adminI.btnPersonalCentro.setEnabled(false);
            adminI.habilitarBotonesMenu(adminI.btnPersonalCentro);
        }

        if (e.getSource() == adminI.btnInventarioMedicamentos) {
            adminI.mostrarVistaInventarioMedicamentoApartado();
            adminI.btnInventarioMedicamentos.setEnabled(false);
            adminI.habilitarBotonesMenu(adminI.btnInventarioMedicamentos);
        }

        if (e.getSource() == adminI.btnHorarioMedico) {
            adminI.mostrarVistaHorarioMedicoApartado();
            adminI.btnHorarioMedico.setEnabled(false);
            adminI.habilitarBotonesMenu(adminI.btnHorarioMedico);
            cargarHorarios();
            agregarListenerBotonesTabla();
        }
    }

    //apartado de personal del centro
    private void manejarApartadoPersonal(ActionEvent e) {
        if (e.getSource() == adminI.btnregistrarPersonal) {
            // Aún no existe un formulario de registro de personal en la interfaz.
            JOptionPane.showMessageDialog(null,
                    "Formulario de registro de personal (pendiente de conectar a la base de datos).",
                    "Registrar Personal", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    //inventario de medicamentos 
    private void manejarApartadoMedicamento(ActionEvent e) {
        if (e.getSource() == adminI.btnAñadirMedicamento) {
            adminI.mostrarAñadirMedicamentoApartado();
            poblarTipoMedicamento();
        }

        if (e.getSource() == adminI.btnSeleccionar) {
            seleccionarImagenMedicamento();
        }

        if (e.getSource() == adminI.btnGuardarMedicamento) {
            guardarMedicamento();
        }
    }

    //simulacion
    private void poblarTipoMedicamento() {
        String[] tiposSimulados = {"Tableta", "Jarabe", "Inyectable", "Cápsula", "Crema"};
        adminI.campoTipoM.removeAllItems();
        for (String tipo : tiposSimulados) {
            adminI.campoTipoM.addItem(tipo);
        }
    }

    private void seleccionarImagenMedicamento() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Imágenes", "jpg", "jpeg", "png"));
        int resultado = fileChooser.showOpenDialog(null);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            try {
                File archivo = fileChooser.getSelectedFile();
                adminI.imagenMedicamentoSeleccionada = archivo;

                ImageIcon icono = new ImageIcon(archivo.getAbsolutePath());
                Image imagenEscalada = icono.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
                adminI.previsualizacionImagen.setIcon(new ImageIcon(imagenEscalada));
                adminI.previsualizacionImagen.setText("");
            } catch (Exception ex) {
                adminI.previsualizacionImagen.setIcon(null);
                adminI.previsualizacionImagen.setText("Error al cargar");
            }
        }
    }

    private void guardarMedicamento() {
        if (adminI.campoNRS.getText().trim().isEmpty()
                || adminI.campoNombreM.getText().trim().isEmpty()
                || adminI.campoCantidad.getText().trim().isEmpty()
                || adminI.campoTipoM.getSelectedItem() == null
                || adminI.campoFechaVencimiento.getDate() == null) {
            JOptionPane.showMessageDialog(null,
                    "Por favor completa todos los campos obligatorios.",
                    "Campos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Pendiente: MedicamentoDao().setAgregar(...) cuando exista
        JOptionPane.showMessageDialog(null,
                "Medicamento \"" + adminI.campoNombreM.getText() + "\" guardado correctamente (simulado).",
                "Medicamento Guardado", JOptionPane.INFORMATION_MESSAGE);

        adminI.mostrarVistaInventarioMedicamentoApartado();
        adminI.btnInventarioMedicamentos.setEnabled(false);
        adminI.habilitarBotonesMenu(adminI.btnInventarioMedicamentos);
    }

    // horarios
    private void manejarApartadoHorario(ActionEvent e) {
        if (e.getSource() == adminI.btnCrearHorario) {
            adminI.mostrarFormularioCreacionHorarioApartado();
            agregarListenersFormularioHorario();
        }

        if (e.getSource() == adminI.btnVolver) {
            adminI.mostrarVistaHorarioMedicoApartado();
            cargarHorarios();
            agregarListenerBotonesTabla();
        }

        if (e.getSource() == adminI.btnGuardarHorario) {
            mostrarVistaPreviaAntesDeGuardar();
        }

        if (e.getSource() == adminI.btnConfirmarGuardarHorario) {
            adminI.dialogoVistaPreviaHorario.dispose();
            guardarHorario();
        }

        if (e.getSource() == adminI.btnCancelarVistaPrevia) {
            adminI.dialogoVistaPreviaHorario.dispose();
        }
    }

    private void agregarListenersFormularioHorario() {
        for (int i = 0; i < adminI.diaSemana.length; i++) {
            adminI.diaSemana[i].addActionListener(this);
            adminI.horaInicio[i].addActionListener(this);
            adminI.horaFin[i].addActionListener(this);
            adminI.almuerzoIni[i].addActionListener(this);
            adminI.almuerzoFin[i].addActionListener(this);
        }
    }

    private void mostrarVistaPreviaAntesDeGuardar() {
        if (adminI.campoNombreHorario.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor ingresa un nombre para el horario.",
                    "Campo incompleto", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean[] diasActivos = new boolean[6];
        String[] horasPorDia = new String[6];
        for (int i = 0; i < 6; i++) {
            diasActivos[i] = adminI.diaSemana[i].isSelected();
            if (diasActivos[i]) {
                horasPorDia[i] = adminI.horaInicio[i].getSelectedItem() + " - " + adminI.horaFin[i].getSelectedItem()
                        + "\nAlm: " + adminI.almuerzoIni[i].getSelectedItem() + "-" + adminI.almuerzoFin[i].getSelectedItem();
            }
        }

        adminI.mostrarVistaPreviaHorarioApartado(diasActivos, horasPorDia);
        this.adminI.btnConfirmarGuardarHorario.addActionListener(this);
        this.adminI.btnCancelarVistaPrevia.addActionListener(this);
    }

    /**
     * Arma el objeto Horario a partir del formulario y lo guarda en la BD.
     */
    private void guardarHorario() {
        Horario h = new Horario();
        h.setNombre(adminI.campoNombreHorario.getText());
        h.setColorEtiqueta((String) adminI.comboColorEtiqueta.getSelectedItem());

        List<HorarioDia> dias = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            if (adminI.diaSemana[i].isSelected()) {
                dias.add(new HorarioDia(
                        HorarioDao.indiceANombreDia(i),
                        (String) adminI.horaInicio[i].getSelectedItem(),
                        (String) adminI.horaFin[i].getSelectedItem(),
                        (String) adminI.almuerzoIni[i].getSelectedItem(),
                        (String) adminI.almuerzoFin[i].getSelectedItem()
                ));
            }
        }
        h.setDias(dias);

        int idGenerado = new HorarioDao().setAgregar(h);

        if (idGenerado > 0) {
            JOptionPane.showMessageDialog(null, "Horario guardado correctamente.",
                    "Horario Guardado", JOptionPane.INFORMATION_MESSAGE);
        }

        adminI.mostrarVistaHorarioMedicoApartado();
        adminI.btnHorarioMedico.setEnabled(false);
        adminI.habilitarBotonesMenu(adminI.btnHorarioMedico);
        cargarHorarios();
        agregarListenerBotonesTabla();
    }

    private void manejarFormularioHorario(ActionEvent e) {
        // Checkbox de día activo/inactivo
        for (int i = 0; i < adminI.diaSemana.length; i++) {
            if (e.getSource() == adminI.diaSemana[i]) {
                boolean activo = adminI.diaSemana[i].isSelected();
                ConstructorFilaHorario.atenuarFila(i, activo,
                        adminI.horaInicio, adminI.horaFin,
                        adminI.almuerzoIni, adminI.almuerzoFin, adminI.lblHoras);
            }
        }

        // Recalcular horas laborales
        for (int i = 0; i < adminI.horaInicio.length; i++) {
            if (e.getSource() == adminI.horaInicio[i] || e.getSource() == adminI.horaFin[i]
                    || e.getSource() == adminI.almuerzoIni[i] || e.getSource() == adminI.almuerzoFin[i]) {
                calcularHorasLaborales(i);
            }
        }
    }

    private void calcularHorasLaborales(int i) {
        try {
            LocalTime horaIni = LocalTime.parse((String) adminI.horaInicio[i].getSelectedItem());
            LocalTime horaFin = LocalTime.parse((String) adminI.horaFin[i].getSelectedItem());
            LocalTime almIni = LocalTime.parse((String) adminI.almuerzoIni[i].getSelectedItem());
            LocalTime almFin = LocalTime.parse((String) adminI.almuerzoFin[i].getSelectedItem());

            long minutosJornada = Duration.between(horaIni, horaFin).toMinutes();
            long minutosAlmuerzo = Duration.between(almIni, almFin).toMinutes();
            long minutosLaborales = minutosJornada - minutosAlmuerzo;

            if (minutosLaborales < 0) {
                adminI.lblHoras[i].setText("Error");
                adminI.lblHoras[i].setForeground(Color.RED);
                return;
            }

            double horasLaborales = minutosLaborales / 60.0;
            adminI.lblHoras[i].setText(String.format("%.1fh", horasLaborales));
            adminI.lblHoras[i].setForeground(Color.DARK_GRAY);
        } catch (Exception ex) {
            adminI.lblHoras[i].setText("—");
            adminI.lblHoras[i].setForeground(Color.GRAY);
        }
    }

    // tabla de horarios
    private void cargarHorarios() {
        horariosActuales = new HorarioDao().listar();
        for (Horario h : horariosActuales) {
            adminI.listaHorarioM.addRow(new Object[]{h.getColorEtiqueta(), h.getNombre(), h.getFechaCreacion(), "", "", ""});
        }
    }

    private void agregarListenerBotonesTabla() {
        adminI.tablaHorarioM.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int fila = adminI.tablaHorarioM.rowAtPoint(e.getPoint());
                int columna = adminI.tablaHorarioM.columnAtPoint(e.getPoint());
                if (fila < 0) {
                    return;
                }

                if (columna == COLUMNA_ASIGNAR) {
                    abrirAsignacion(fila);
                } else if (columna == COLUMNA_EDITAR) {
                    editarHorario(fila);
                } else if (columna == COLUMNA_ELIMINAR) {
                    eliminarHorario(fila);
                }
            }
        });
    }

    private void abrirAsignacion(int fila) {
        Horario horarioSeleccionado = horariosActuales.get(fila);
        adminI.mostrarAsignacionHorarioMedicoApartado(horarioSeleccionado.getNombre(), fila);
        poblarMedicosAsignacion();
        poblarConsultoriosAsignacion();
        adminI.btnConfirmarAsignacion.addActionListener(this);
        adminI.btnCancelarAsignacion.addActionListener(this);
    }

    private void poblarMedicosAsignacion() {
        medicosActuales = new MedicosDao().listar();
        adminI.comboMedicoAsignar.removeAllItems();
        for (Medicos m : medicosActuales) {
            adminI.comboMedicoAsignar.addItem(m.getPrimerNombre() + " " + m.getPrimerApellido());
        }
    }

     

    private void poblarConsultoriosAsignacion() {
        consultoriosActuales = new ConsultorioDao().listar();
        adminI.comboConsultorioHorario.removeAllItems();
        for (Consultorio c : consultoriosActuales) {
            adminI.comboConsultorioHorario.addItem("Consultorio: " + c.getNumeroConsultorio());
        }
    }

    private void confirmarAsignacionMedico(int filaHorario) {
        int indiceMedico = adminI.comboMedicoAsignar.getSelectedIndex();
        int indiceConsultorio = adminI.comboConsultorioHorario.getSelectedIndex();
        if (indiceMedico < 0 || indiceConsultorio < 0) {
            JOptionPane.showMessageDialog(null, "Por favor selecciona un médico y un consultorio.",
                    "Selección requerida", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Horario horario = horariosActuales.get(filaHorario);
        Medicos medico = medicosActuales.get(indiceMedico);
        int idConsultorioSeleccionado = consultoriosActuales.get(indiceConsultorio).getIdConsultorio(); // <-- aquí sale
        int mes = adminI.comboMedicoAsignar.getSelectedIndex() + 1;
        int anio = java.time.LocalDate.now().getYear();

        new HorarioDao().asignarMedico(horario.getId(), medico.getId_medico(), idConsultorioSeleccionado, mes, anio);
        JOptionPane.showMessageDialog(null,
                "Horario \"" + horario.getNombre() + "\" asignado a " + medico.getPrimerNombre() + " " + medico.getPrimerApellido() + ".",
                "Asignación Exitosa", JOptionPane.INFORMATION_MESSAGE);

        adminI.dialogoAsignarMedico.dispose();
    }

    private void editarHorario(int fila) {
        // Pendiente,me falta setActualizar en HorarioDao
    }

    private void eliminarHorario(int fila) {
        int idHorario = horariosActuales.get(fila).getId();
        int respuesta = JOptionPane.showConfirmDialog(null,
                "¿Seguro que deseas eliminar este horario?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (respuesta == JOptionPane.YES_OPTION) {
            new HorarioDao().setEliminar(idHorario);
            adminI.mostrarVistaHorarioMedicoApartado();
            cargarHorarios();
            agregarListenerBotonesTabla();
        }
    }

    private void manejarTablaHorarios(ActionEvent e) {
        if (e.getSource() == adminI.btnConfirmarAsignacion) {
            confirmarAsignacionMedico(adminI.filaHorarioSeleccionada); // ajusta al nombre real de tu atributo de fila
        }
        if (e.getSource() == adminI.btnCancelarAsignacion) {
            adminI.dialogoAsignarMedico.dispose();
        }
    }

}
