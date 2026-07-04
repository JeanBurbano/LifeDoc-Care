/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import com.github.lgooddatepicker.components.DatePicker;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author lunaa
 */
public class AdministradorCentroInterfaz extends PacienteInterfaz {
    //Botones del menu principal
    public JButton btnPersonalCentro; //Abre la vista de personal del centro
    public JButton btnInventarioMedicamentos; //Abre la vista de inventario de medicamentos
    public JButton btnHorarioMedico; //Abre la vista de horarios médicos
    
    //Botones dentro de cada apartado
    public JButton btnregistrarPersonal; //Abre el formulario para registrar personal nuevo
    public JButton btnAñadirMedicamento; //Abre el formulario para añadir un medicamento
    public JButton btnGuardarMedicamento; //Guarda el medicamento del formulario
    public JButton btnSeleccionar; //Abre el JFileChooser para elegir la imagen del medicamento
    public JButton btnCrearHorario; //Abre el formulario para crear un nuevo horario médico
    public JButton btnVolver; //Regresa desde el formulario de horario a la lista de horarios
    public JButton btnGuardarHorario;// Guarda el horario creado
    
    //Paneles principales de apartados
    public JPanel personalC; //Panel contenedor de la vista "Personal del Centro"
    public JPanel inventarioM; // Panel contenedor de la vista "Inventario de Medicamentos"
    public JPanel horarioM; //Panel contenedor de la vista "Horarios Médicos"
    public JPanel añadirM; //Panel contenedor del formulario "Añadir Medicamento"
    public JPanel asignarHorario; //Panel reservado para la vista de asignación de horario
    public JPanel panelFotoM; //Panel para adjuntar la foto del medicamento
    
    // Tablas con sus modelos
    public JTable tablaPersonalR; // Tabla que muestra el personal registrado
    public JTable tablaMedicamentoR; // Tabla que muestra los medicamentos registrados
    public JTable tablaHorarioM; // Tabla que muestra los horarios creados
    public DefaultTableModel listaPersonalR; // Modelo de datos (filas/columnas) de tablaPersonalR
    public DefaultTableModel listaMedicamentoR; // Modelo de datos de tablaMedicamentoR
    public DefaultTableModel listaHorarioM; // Modelo de datos de tablaHorarioM
    
    // Scroll de cada tabla privados ya que solo esta clase los necesitas en la panel
    private JScrollPane miscrollListaPersonal;
    private JScrollPane miscrollListaMedicamento;
    private JScrollPane miscrollListaHorarioM;
    
    //formulario de añadir medicamento
    public JTextField campoNRS; // Número de Registro Sanitario del medicamento
    public JTextField campoNombreM; // Nombre del medicamento
    public JTextField campoCantidad; // Cantidad en stock
    public JComboBox campoTipoM; // Tipo de medicamento (se llena desde el controlador con datos de la BD)
    public DatePicker campoFechaVencimiento; // Fecha de vencimiento (con calendario LGoodDatePicker)
    public JTextArea campoDescripcionMedicamento; // Descripción del medicamento
    public JLabel previsualizacionImagen; // Muestra la imagen seleccionada del medicamento
    public java.io.File imagenMedicamentoSeleccionada; // Archivo de imagen elegido en el JFileChooser
    
    //formlario para crear horario
    public JTextField campoNombreHorario; // Nombre que el usuario le da al horario
    public JComboBox<String> comboColorEtiqueta; // Color de la etiqueta visual del horario

    // Un arreglo por cada día de la semana Lunes a Sábado
    public JCheckBox[] diaSemana = new JCheckBox[6]; // Activa/desactiva el día en el horario
    public JComboBox[] horaInicio = new JComboBox[6]; // Hora de inicio de jornada por día
    public JComboBox[] horaFin = new JComboBox[6]; // Hora de fin de jornada por día
    public JComboBox[] almuerzoIni = new JComboBox[6]; //Hora de inicio de almuerzo por día
    public JComboBox[] almuerzoFin = new JComboBox[6]; // Hora de fin de almuerzo por día
    public JLabel[] lblHoras = new JLabel[6]; //Muestra las horas laborales calculadas por día

    //colores para etiqueta de horario formato rgb
    public static final Color ETIQ_AZUL = new Color(0x1B, 0x6B, 0x7B);
    public static final Color ETIQ_ROJA = new Color(0xE5, 0x4A, 0x4A);
    public static final Color ETIQ_VERDE = new Color(0x1D, 0x9E, 0x75);
    public static final Color ETIQ_NARANJA = new Color(0xEF, 0x9F, 0x27);
    public static final Color ETIQ_MORADA = new Color(0x7C, 0x4D, 0xFF);
    public static final Color ETIQ_GRIS = new Color(0x78, 0x90, 0x9C);
    
    // Nombres visibles en el combo de color, en el mismo orden que COLOR_VALORES
    public static final String[] COLOR_NOMBRES = {"Azul", "Roja", "Verde", "Naranja", "Morado", "Gris"};
    // Valores de Color correspondientes a cada nombre (mismo índice que COLOR_NOMBRES)
    public static final Color[] COLOR_VALORES = {
        ETIQ_AZUL, ETIQ_ROJA, ETIQ_VERDE, ETIQ_NARANJA, ETIQ_MORADA, ETIQ_GRIS
    };

    //Días de la semana que se muestran en el formulario de horario
    public static final String[] DIAS_SEMANA = {
        "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"
    };

    // Horas disponibles en los combos de hora (07:00 a 17:00 cada 30 min).
    // Se llenará en el constructor llamando a la clase  GeneradorHoras()
    public static String[] HORAS;
    
    
}
