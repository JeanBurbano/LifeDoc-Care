/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.Duration;
import java.time.LocalTime;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import view.ConstructorFilaHorario;
import view.AdministradorCentroInterfaz;



public class AdminCentroController implements ActionListener {

    AdministradorCentroInterfaz adminI; 
    
    public AdminCentroController(AdministradorCentroInterfaz adminI) {
        this.adminI = adminI;

        //Botones del menú principal
        this.adminI.btnPersonalCentro.addActionListener(this);
        this.adminI.btnInventarioMedicamentos.addActionListener(this);
        this.adminI.btnHorarioMedico.addActionListener(this);
        //Botones de acción en los apartados
        this.adminI.btnregistrarPersonal.addActionListener(this);
        this.adminI.btnAñadirMedicamento.addActionListener(this);
        this.adminI.btnGuardarMedicamento.addActionListener(this);
        this.adminI.btnSeleccionar.addActionListener(this);
        this.adminI.btnCrearHorario.addActionListener(this);
        this.adminI.btnVolver.addActionListener(this);
        this.adminI.btnHorarioMedico.addActionListener(this);
        

        //Vista inicial al abrir la interfaz: Personal del Centro
        this.adminI.btnPersonalCentro.doClick(); // simula el primer clic para mostrar la vista de entrada
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //Menu princcipal
        if (e.getSource() == adminI.btnPersonalCentro) {
            adminI.mostrarVistaPersonalCentroApartado();
            adminI.btnPersonalCentro.setEnabled(false);
            adminI.habilitarBotonesMenu(this.adminI.btnPersonalCentro);
        }

        if (e.getSource() == adminI.btnInventarioMedicamentos) {
            adminI.mostrarVistaInventarioMedicamentoApartado();
            adminI.btnInventarioMedicamentos.setEnabled(false);
            adminI.habilitarBotonesMenu(this.adminI.btnInventarioMedicamentos);
        }

        if (e.getSource() == adminI.btnHorarioMedico) {
            adminI.mostrarVistaHorarioMedicoApartado();
            adminI.btnHorarioMedico.setEnabled(false);
            adminI.habilitarBotonesMenu(this.adminI.btnHorarioMedico);
        }

        //apartado personal del centro
        if (e.getSource() == adminI.btnregistrarPersonal) {
            // Aún no existe un formulario de registro de personal en la interfaz;
            // se simula la acción mientras se conecta la base de datos.
            JOptionPane.showMessageDialog(null,
                    "Formulario de registro de personal (pendiente de conectar a la base de datos).",
                    "Registrar Personal", JOptionPane.INFORMATION_MESSAGE);
        }

        //Medicamentos
        if (e.getSource() == adminI.btnAñadirMedicamento) {
            adminI.mostrarAñadirMedicamentoApartado(); // crea los campos del formulario (nuevos objetos cada vez)
            poblarTipoMedicamento(); // llena el combo de tipo con datos simulados (en producción vendría de la BD)
        }

        if (e.getSource() == adminI.btnSeleccionar) {
            seleccionarImagenMedicamento();
        }

        if (e.getSource() == adminI.btnGuardarMedicamento) {
            guardarMedicamento();
        }

        //horarios medicos
        if (e.getSource() == adminI.btnCrearHorario) {
            adminI.mostrarFormularioCreacionHorarioApartado(); // crea los checkbox/combos de días (nuevos cada vez)
            agregarListenersFormularioHorario(); // se les agrega el listener recién ahora que existen
        }

        if (e.getSource() == adminI.btnVolver) {
            adminI.mostrarVistaHorarioMedicoApartado(); // regresa a la lista de horarios sin guardar
        }

        if (e.getSource() == adminI.btnGuardarHorario) {
            guardarHorario();
        }

        // checkbox activo e inactivo
        for (int i = 0; i < adminI.diaSemana.length; i++) {
            if (e.getSource() == adminI.diaSemana[i]) {
                boolean activo = adminI.diaSemana[i].isSelected();
                // Habilita/deshabilita los combos de ese día y resetea la etiqueta de horas si se desactiva
                ConstructorFilaHorario.atenuarFila(i, activo,
                        adminI.horaInicio, adminI.horaFin,
                        adminI.almuerzoIni, adminI.almuerzoFin, adminI.lblHoras);
            }
        }

        //calcular horas laborales
        for (int i = 0; i < adminI.horaInicio.length; i++) {
            if (e.getSource() == adminI.horaInicio[i] || e.getSource() == adminI.horaFin[i]
                    || e.getSource() == adminI.almuerzoIni[i] || e.getSource() == adminI.almuerzoFin[i]) {
                calcularHorasLaborales(i);
            }
        }
        
        if (e.getSource() == adminI.btnHorarioMedico) {
            adminI.mostrarVistaHorarioMedicoApartado();
            adminI.btnHorarioMedico.setEnabled(false);
            adminI.habilitarBotonesMenu(adminI.btnHorarioMedico);
            poblarHorariosPrueba();
            agregarListenerBotonAsignar(); // conecta el clic en la columna "Asignar" (tabla nueva cada vez)
        }
    }

    

    /**
     * Agrega el ActionListener a los checkbox y combos de cada día del
     * formulario de horario. Se llama después de construir el
     * formulario, porque esos componentes se crean de nuevo cada vez
     * que se abre (no existen desde el constructor de la interfaz).
     */
    private void agregarListenersFormularioHorario() {
        for (int i = 0; i < adminI.diaSemana.length; i++) {
            adminI.diaSemana[i].addActionListener(this);
            adminI.horaInicio[i].addActionListener(this);
            adminI.horaFin[i].addActionListener(this);
            adminI.almuerzoIni[i].addActionListener(this);
            adminI.almuerzoFin[i].addActionListener(this);
        }
    }

    /**
     * Llena el combo de tipo de medicamento con datos simulados.
     * Cuando exista conexión a la base de datos, esta lista debería
     * obtenerse con una consulta (por ejemplo, ModeloMedicamento.obtenerTipos()).
     */
    private void poblarTipoMedicamento() {
        String[] tiposSimulados = {"Tableta", "Jarabe", "Inyectable", "Cápsula", "Crema"};
        adminI.campoTipoM.removeAllItems(); // se limpia por si tenía datos de una apertura anterior
        for (String tipo : tiposSimulados) {
            adminI.campoTipoM.addItem(tipo);
        }
    }

    /**
     * Abre el selector de archivos para elegir la imagen del
     * medicamento, la muestra en la previsualización, y guarda el
     * archivo en el atributo correspondiente para cuando se guarde
     * el medicamento en la base de datos.
     */
    private void seleccionarImagenMedicamento() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Imágenes", "jpg", "jpeg", "png"));
        int resultado = fileChooser.showOpenDialog(null);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            try {
                File archivo = fileChooser.getSelectedFile();
                adminI.imagenMedicamentoSeleccionada = archivo; // se guarda para cuando el modelo se conecte a la BD

                ImageIcon icono = new ImageIcon(archivo.getAbsolutePath());
                Image imagenEscalada = icono.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
                adminI.previsualizacionImagen.setIcon(new ImageIcon(imagenEscalada));
                adminI.previsualizacionImagen.setText(""); // se quita el texto "Sin imagen"
            } catch (Exception ex) {
                adminI.previsualizacionImagen.setIcon(null);
                adminI.previsualizacionImagen.setText("Error al cargar");
            }
        }
    }

    /**
     * Valida los campos obligatorios del formulario de medicamento y
     * simula el guardado (mientras no haya conexión a la base de
     * datos), mostrando un mensaje de confirmación y regresando a la
     * lista de inventario.
     */
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

        JOptionPane.showMessageDialog(null,
                "Medicamento \"" + adminI.campoNombreM.getText() + "\" guardado correctamente (simulado).",
                "Medicamento Guardado", JOptionPane.INFORMATION_MESSAGE);

        adminI.mostrarVistaInventarioMedicamentoApartado(); // vuelve a la lista tras guardar
        adminI.btnInventarioMedicamentos.setEnabled(false);
        adminI.habilitarBotonesMenu(adminI.btnInventarioMedicamentos);
    }

    /**
     * Calcula las horas laborales de un día del horario, a partir de
     * los combos de hora de inicio/fin de jornada y de almuerzo, y
     * actualiza la etiqueta correspondiente.
     *
     * @param i índice del día (0=Lunes ... 5=Sábado)
     */
    private void calcularHorasLaborales(int i) {
        try {
            LocalTime horaIni = LocalTime.parse((String) adminI.horaInicio[i].getSelectedItem());
            LocalTime horaFin = LocalTime.parse((String) adminI.horaFin[i].getSelectedItem());
            LocalTime almIni = LocalTime.parse((String) adminI.almuerzoIni[i].getSelectedItem());
            LocalTime almFin = LocalTime.parse((String) adminI.almuerzoFin[i].getSelectedItem());

            long minutosJornada = Duration.between(horaIni, horaFin).toMinutes(); // minutos entre inicio y fin
            long minutosAlmuerzo = Duration.between(almIni, almFin).toMinutes(); // minutos de almuerzo
            long minutosLaborales = minutosJornada - minutosAlmuerzo; // minutos realmente trabajados

            if (minutosLaborales < 0) {
                // Las horas ingresadas no tienen sentido (ej. almuerzo más largo que la jornada)
                adminI.lblHoras[i].setText("Error");
                adminI.lblHoras[i].setForeground(Color.RED);
                return;
            }

            double horasLaborales = minutosLaborales / 60.0; // conversión a horas decimales
            adminI.lblHoras[i].setText(String.format("%.1fh", horasLaborales));
            adminI.lblHoras[i].setForeground(Color.DARK_GRAY);
        } catch (Exception ex) {
            // Algún combo aún no tiene selección válida; se deja el guion por defecto
            adminI.lblHoras[i].setText("—");
            adminI.lblHoras[i].setForeground(Color.GRAY);
        }
    }

    /**
     * Valida el nombre del horario y simula el guardado (mientras no
     * haya conexión a la base de datos), mostrando un mensaje de
     * confirmación y regresando a la lista de horarios.
     */
    private void guardarHorario() {
        if (adminI.campoNombreHorario.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Por favor ingresa un nombre para el horario.",
                    "Campo incompleto", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(null,
                "Horario \"" + adminI.campoNombreHorario.getText() + "\" guardado correctamente (simulado).",
                "Horario Guardado", JOptionPane.INFORMATION_MESSAGE);

        adminI.mostrarVistaHorarioMedicoApartado(); // vuelve a la lista tras guardar
        adminI.btnHorarioMedico.setEnabled(false);
        adminI.habilitarBotonesMenu(adminI.btnHorarioMedico);
    }
    
    /**
     * Llena la tabla de horarios con filas de prueba, solo para poder
     * ver y probar el botón "Asignar" mientras no hay conexión a la
     * base de datos. Cuando exista esa conexión, este método se
     * reemplaza por una consulta real (ej. ModeloHorario.obtenerTodos()).
     */
    private void poblarHorariosPrueba() {
        // Se agrega una fila por cada horario de prueba: {ETIQUETA, NOMBRE, FECHA CREACIÓN, columna del botón}
        adminI.listaHorarioM.addRow(new Object[]{"Azul", "Horario Mañana", "2026-07-01", ""});
        adminI.listaHorarioM.addRow(new Object[]{"Verde", "Horario Tarde", "2026-07-02", ""});
        adminI.listaHorarioM.addRow(new Object[]{"Naranja", "Horario Fin de Semana", "2026-07-03", ""});
    }
    
    /**
     * Detecta el clic sobre la columna del botón "Asignar" en la
     * tabla de horarios (la tabla se recrea cada vez que se muestra
     * la vista, así que este listener se agrega de nuevo cada vez).
     */
    private void agregarListenerBotonAsignar() {
        adminI.tablaHorarioM.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                JTable tabla = adminI.tablaHorarioM;
                int fila = tabla.rowAtPoint(e.getPoint());
                int columna = tabla.columnAtPoint(e.getPoint());
                int columnaBoton = tabla.getColumnCount() - 1; // la última columna es la del botón

                if (fila >= 0 && columna == columnaBoton) {
                    String nombreHorario = String.valueOf(adminI.listaHorarioM.getValueAt(fila, 1)); // columna NOMBRE
                    adminI.mostrarAsignacionHorarioMedicoApartado(nombreHorario, fila);
                    adminI.btnConfirmarAsignacion.addActionListener(AdminCentroController.this);
                    adminI.btnCancelarAsignacion.addActionListener(AdminCentroController.this);
                }
            }
        });
    }

    
}
    

