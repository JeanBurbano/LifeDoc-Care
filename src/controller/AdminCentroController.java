/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.Color;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.CategoriaMedicamento;
import model.CategoriaMedicamentoDao;
import model.Consultorio;
import model.ConsultorioDao;
import model.Horario;
import model.HorarioDao;
import model.HorarioDia;
import model.Medicamentos;
import model.MedicamentosDao;
import model.MedicoDao;
import model.Medicos;
import model.MedicosDao;
import model.OperarioDao;
import model.PersonalCentroDao;
import model.RolDao;
import model.SeleccionImagenes;

import model.TipoMedicamento;
import model.TipoMedicamentoDao;
import view.ConstructorFilaHorario;
import view.AdministradorCentroInterfaz;
import view.RegistroPersonalInterfaz;

public class AdminCentroController extends PacienteController {

    AdministradorCentroInterfaz adminI;

    // Columnas de la tabla de horarios
    private static final int COLUMNA_ASIGNAR = 3;
    private static final int COLUMNA_EDITAR = 4;
    private static final int COLUMNA_ELIMINAR = 5;

    private List<Horario> horariosActuales = new ArrayList<>();
    private List<Medicos> medicosActuales = new ArrayList<>();
    private List<Consultorio> consultoriosActuales = new ArrayList<>();
    private List<TipoMedicamento> tiposActuales = new ArrayList<>();
    private List<CategoriaMedicamento> categoriasActuales = new ArrayList<>();
    private List<Medicamentos> medicamentosActuales = new ArrayList<>();

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
            cargarMedicamentos();
            agregarListenerBotonEstadoMedicamento();
        }
        if (e.getSource() == adminI.btnHorarioMedico) {
            adminI.mostrarVistaHorarioMedicoApartado();
            adminI.btnHorarioMedico.setEnabled(false);
            adminI.habilitarBotonesMenu(adminI.btnHorarioMedico);
            cargarHorarios();
            agregarListenerBotonesTabla();
        }
    }

    private void agregarListenerBotonEstadoMedicamento() {
        adminI.tablaMedicamentoR.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = adminI.tablaMedicamentoR.rowAtPoint(e.getPoint());
                int columna = adminI.tablaMedicamentoR.columnAtPoint(e.getPoint());
                if (fila < 0 || columna != 8) {
                    return;
                }
                alternarEstadoMedicamento(fila);
            }
        });
    }

    private void alternarEstadoMedicamento(int fila) {
        Medicamentos m = medicamentosActuales.get(fila);
        MedicamentosDao dao = new MedicamentosDao();

        if (m.isEstado()) {
            dao.deshabilitar(m.getnRegistroSanitario());
        } else {
            dao.habilitar(m.getnRegistroSanitario());
        }

        adminI.mostrarVistaInventarioMedicamentoApartado();
        cargarMedicamentos();
        agregarListenerBotonEstadoMedicamento();
    }

    //apartado de personal del centro
    private void manejarApartadoPersonal(ActionEvent e) {
        if (e.getSource() == adminI.btnregistrarPersonal) {
            RegistroPersonalInterfaz vistaPersonal = new RegistroPersonalInterfaz("Registro de Personal");
            RegistroPersonalController controller = new RegistroPersonalController(vistaPersonal);
            vistaPersonal.setVisible(true);
        }
    }

    //inventario de medicamentos 
    private void manejarApartadoMedicamento(ActionEvent e) {
        if (e.getSource() == adminI.btnAñadirMedicamento) {
            adminI.mostrarAñadirMedicamentoApartado();
            poblarTipoMedicamento();
            poblarCategoriaMedicamento();
        }

        if (e.getSource() == adminI.btnSeleccionar) {
            seleccionarImagenMedicamento();
        }

        if (e.getSource() == adminI.btnGuardarMedicamento) {
            guardarMedicamento();
        }
    }

    private void poblarTipoMedicamento() {
        tiposActuales = new TipoMedicamentoDao().listar();
        adminI.campoTipoM.removeAllItems();
        for (TipoMedicamento t : tiposActuales) {
            adminI.campoTipoM.addItem(t);
        }
    }

    private void poblarCategoriaMedicamento() {
        categoriasActuales = new CategoriaMedicamentoDao().listar();
        adminI.campoCategoriaM.removeAllItems();
        for (CategoriaMedicamento c : categoriasActuales) {
            adminI.campoCategoriaM.addItem(c);
        }
    }
    private ImageIcon icono;
    private File archivo;

    private void seleccionarImagenMedicamento() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Imágenes (JPG, PNG)",
                "jpg", "jpeg", "png");
        fileChooser.setFileFilter(filtro);
        int resultado = fileChooser.showOpenDialog(null);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            archivo = fileChooser.getSelectedFile();

            if (archivo != null) {
                icono = new ImageIcon(archivo.getAbsolutePath());

                if (icono.getIconWidth() <= 0) {
                    adminI.previsualizacionImagen.setIcon(null);
                    adminI.previsualizacionImagen.setText("Error al cargar la imagen");
                    return;
                }

                adminI.imagenMedicamentoSeleccionada = archivo;
                Image imagenEscalada = icono.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
                adminI.previsualizacionImagen.setIcon(new ImageIcon(imagenEscalada));
                adminI.previsualizacionImagen.setText("");
            }
        }
    }

    private void guardarMedicamento() {
        if (adminI.campoNRS.getText().trim().isEmpty()
                || adminI.campoNombreM.getText().trim().isEmpty()
                || adminI.campoCantidad.getText().trim().isEmpty()
                || adminI.campoStock.getText().trim().isEmpty()
                || adminI.campoTipoM.getSelectedItem() == null
                || adminI.campoCategoriaM.getSelectedItem() == null
                || adminI.campoFechaVencimiento.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Por favor completa todos los campos obligatorios.",
                    "Campos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String textoStock = adminI.campoStock.getText().trim();
        if (!textoStock.matches("\\d+")) {
            JOptionPane.showMessageDialog(null, "El stock debe ser un número entero.",
                    "Dato inválido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int stock = Integer.parseInt(textoStock);

        TipoMedicamento tipo = (TipoMedicamento) adminI.campoTipoM.getSelectedItem();
        CategoriaMedicamento categoria = (CategoriaMedicamento) adminI.campoCategoriaM.getSelectedItem();

        Medicamentos m = new Medicamentos();
        m.setnRegistroSanitario(adminI.campoNRS.getText().trim());
        m.setNombre(adminI.campoNombreM.getText().trim());
        m.setDescripcion(adminI.campoDescripcionMedicamento.getText().trim());
        m.setFechaVencimiento(adminI.campoFechaVencimiento.getDate());
        m.setCantidad(adminI.campoCantidad.getText().trim());
        m.setIdTipoMedicamento(tipo.getIdTipoMedicamento());
        m.setIdCategoria(categoria.getIdCategoria());
        m.setStock(stock);

        if (adminI.imagenMedicamentoSeleccionada != null) {
            SeleccionImagenes url = new SeleccionImagenes();
            String urlRelativa = url.copiarImagenMedicamento(adminI.imagenMedicamentoSeleccionada, m.getnRegistroSanitario());
            m.setUrlFoto(urlRelativa);
        } else {
            m.setUrlFoto("imagenesMedicamento/fotoDefecto.png");
        }

        int resultado = new MedicamentosDao().setAgregar(m);

        if (resultado > 0) {
            JOptionPane.showMessageDialog(null,
                    "Medicamento \"" + m.getNombre() + "\" guardado correctamente.",
                    "Medicamento Guardado", JOptionPane.INFORMATION_MESSAGE);
        }

        adminI.mostrarVistaInventarioMedicamentoApartado();
        adminI.btnInventarioMedicamentos.setEnabled(false);
        adminI.habilitarBotonesMenu(adminI.btnInventarioMedicamentos);
        cargarMedicamentos();
    }

    private void cargarMedicamentos() {
        medicamentosActuales = new MedicamentosDao().listar();
        for (Medicamentos m : medicamentosActuales) {
            adminI.listaMedicamentoR.addRow(new Object[]{
                m.getUrlFoto(), m.getnRegistroSanitario(), m.getNombre(), m.getDescripcion(),
                m.getFechaVencimiento(), m.getCantidad(), m.getStock(),
                m.isEstado() ? "Habilitado" : "Inhabilitado", ""
            });
        }
    }

    // horarios
    private void manejarApartadoHorario(ActionEvent e) {
        if (e.getSource() == adminI.btnCrearHorario) {
            adminI.mostrarFormularioCreacionHorarioApartado();
            agregarListenersFormularioHorario();
        }
        for (int i = 0; i < adminI.horaInicio.length; i++) {
            if (e.getSource() == adminI.horaInicio[i]) {
                actualizarComboFin(i); // la hora fin se recalcula según la nueva hora de inicio
            }
            if (e.getSource() == adminI.horaInicio[i] || e.getSource() == adminI.horaFin[i]) {
                actualizarComboAlmuerzoIni(i); // el rango de almuerzo depende de inicio y fin de jornada
            }
            if (e.getSource() == adminI.almuerzoIni[i]) {
                actualizarComboAlmuerzoFin(i); // el fin del almuerzo se fija a +1 hora automáticamente
            }
            if (e.getSource() == adminI.horaInicio[i] || e.getSource() == adminI.horaFin[i]
                    || e.getSource() == adminI.almuerzoIni[i] || e.getSource() == adminI.almuerzoFin[i]) {
                calcularHorasLaborales(i);
            }
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

            actualizarComboFin(i);
            actualizarComboAlmuerzoIni(i);
            actualizarComboAlmuerzoFin(i);
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
                horasPorDia[i] = "Horario:\n" + adminI.horaInicio[i].getSelectedItem() + " - " + adminI.horaFin[i].getSelectedItem()
                        + "\nDescanso:\n " + adminI.almuerzoIni[i].getSelectedItem() + " - " + adminI.almuerzoFin[i].getSelectedItem();
            }
        }

        adminI.mostrarVistaPreviaHorarioApartado(diasActivos, horasPorDia);
        this.adminI.btnConfirmarGuardarHorario.addActionListener(this);
        this.adminI.btnCancelarVistaPrevia.addActionListener(this);

        adminI.dialogoVistaPreviaHorario.setVisible(true);
    }

    /**
     * Filtra el combo hora Fin para que solo muestre horas después de la hora
     * de inicio elegida.
     */
    private void actualizarComboFin(int i) {
        String horaIniStr = (String) adminI.horaInicio[i].getSelectedItem();
        if (horaIniStr == null) {
            return;
        }
        LocalTime horaIni = LocalTime.parse(horaIniStr);

        List<String> disponibles = new ArrayList<>();
        for (String hora : AdministradorCentroInterfaz.HORAS) {
            if (LocalTime.parse(hora).isAfter(horaIni)) { // solo horas después de la de inicio
                disponibles.add(hora);
            }
        }
        adminI.horaFin[i].setModel(new DefaultComboBoxModel(disponibles.toArray()));
    }

    /**
     * Filtra el combo almuerzo Inicio para que solo muestre horas dentro del
     * rango de la jornada (entre hora inicio y hora fin).
     */
    private void actualizarComboAlmuerzoIni(int i) {
        String horaIniStr = (String) adminI.horaInicio[i].getSelectedItem();
        String horaFinStr = (String) adminI.horaFin[i].getSelectedItem();
        if (horaIniStr == null || horaFinStr == null) {
            return;
        }

        LocalTime horaIni = LocalTime.parse(horaIniStr);
        LocalTime horaFin = LocalTime.parse(horaFinStr);

        List<String> disponibles = new ArrayList<>();
        for (String hora : AdministradorCentroInterfaz.HORAS) {
            LocalTime h = LocalTime.parse(hora);
            if (!h.isBefore(horaIni) && !h.isAfter(horaFin)) { // dentro del rango [horaIni, horaFin]
                disponibles.add(hora);
            }
        }
        adminI.almuerzoIni[i].setModel(new DefaultComboBoxModel(disponibles.toArray()));
    }

    /**
     * El almuerzo dura exactamente 1 hora entonces al elegir la hora de inicio
     * del almuerzo, el combo almuerzo fin solo muestra esa única opción, sin
     * dejar elegir nada más.
     */
    private void actualizarComboAlmuerzoFin(int i) {
        String almIniStr = (String) adminI.almuerzoIni[i].getSelectedItem();
        if (almIniStr == null) {
            return;
        }

        LocalTime almIni = LocalTime.parse(almIniStr);
        String almFinStr = almIni.plusHours(1).format(DateTimeFormatter.ofPattern("HH:mm"));

        adminI.almuerzoFin[i].setModel(new DefaultComboBoxModel(new String[]{almFinStr}));
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
        String horaIniStr = (String) adminI.horaInicio[i].getSelectedItem();
        String horaFinStr = (String) adminI.horaFin[i].getSelectedItem();
        String almIniStr = (String) adminI.almuerzoIni[i].getSelectedItem();
        String almFinStr = (String) adminI.almuerzoFin[i].getSelectedItem();

        if (horaIniStr == null || horaFinStr == null || almIniStr == null || almFinStr == null) {
            adminI.lblHoras[i].setText("—");
            adminI.lblHoras[i].setForeground(Color.GRAY);
            return;
        }

        LocalTime horaIni = LocalTime.parse(horaIniStr);
        LocalTime horaFin = LocalTime.parse(horaFinStr);
        LocalTime almIni = LocalTime.parse(almIniStr);
        LocalTime almFin = LocalTime.parse(almFinStr);

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

                    int respuesta = JOptionPane.showConfirmDialog(null,
                            "¿Seguro que deseas eliminar este horario?",
                            "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

                    if (respuesta == JOptionPane.YES_OPTION) {
                        eliminarHorario(fila);
                    }
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
        adminI.dialogoAsignarMedico.setVisible(true);
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
        int mes = LocalDate.now().getMonthValue();
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
