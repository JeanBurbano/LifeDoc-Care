/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import com.github.lgooddatepicker.components.DatePicker;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.Box;
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
import javax.swing.table.JTableHeader;
import model.MetodosPublicos;

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
    // Se llenará en el constructor llamando a la clase  GeneradorHorarios()
    public static String[] HORAS;

    public AdministradorCentroInterfaz(String nombrePersona, String nombreInterfaz, String url) {
        super(nombrePersona, nombreInterfaz, url);

        //Generar las horas disponibles para los combos de horario
        HORAS = GeneradorHorarios.generarHoras(); //

        //Botones del menú principal
        this.btnPersonalCentro = new JButton("👥 Personal del Centro");
        this.btnInventarioMedicamentos = new JButton("💊 Inventario de Medicamentos");
        this.btnHorarioMedico = new JButton("🗓️ Horarios Médicos");
        // Se agregan al cuerpo1 heredado
        super.agregarBotonCuerpo1(btnPersonalCentro);
        super.agregarBotonCuerpo1(btnInventarioMedicamentos);
        super.agregarBotonCuerpo1(btnHorarioMedico);

        //Botón para registrar personal
        this.btnregistrarPersonal = new JButton("👤 Registrar Personal");
        MetodosPublicos.estilizarBoton(btnregistrarPersonal, (byte) 5); // aplica el estilo 5 predefinido

        //Botones del formulario de medicamento
        this.btnAñadirMedicamento = new JButton("➕ Añadir Medicamento");
        this.btnGuardarMedicamento = new JButton("➕ Guardar Medicamento");
        this.btnSeleccionar = new JButton("Seleccionar Medicamento");
        MetodosPublicos.estilizarBoton(btnGuardarMedicamento, (byte) 5);
        MetodosPublicos.estilizarBoton(btnAñadirMedicamento, (byte) 5);
        MetodosPublicos.estilizarBoton(btnSeleccionar, (byte) 5);

        //Botón para crear un nuevo horario con estilo propio
        this.btnCrearHorario = new JButton("📍 Crear Nuevo Horario");
        btnCrearHorario.setFont(new Font("Arial", Font.BOLD, 20));
        btnCrearHorario.setBackground(PacienteInterfaz.COLOR_VERDE_ACENTO);
        btnCrearHorario.setForeground(Color.WHITE);
        btnCrearHorario.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);

        //Botón para volver (usado dentro del formulario de horario)
        this.btnVolver = new JButton("← Volver");
        btnVolver.setForeground(PacienteInterfaz.COLOR_VERDE_ACENTO);
        btnVolver.setFont(new Font("Arial", Font.BOLD, 18));
        btnVolver.setContentAreaFilled(false); // sin fondo, se ve asi como un link
        btnVolver.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        //Botón para guardar el horario creado
        this.btnGuardarHorario = new JButton("Guardar horario");
        MetodosPublicos.estilizarBoton(btnGuardarHorario, (byte) 1);

        //Paneles principales de cada vista
        this.personalC = new JPanel();
        this.inventarioM = new JPanel();
        this.horarioM = new JPanel();
        this.añadirM = new JPanel();
        this.personalC.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.inventarioM.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.horarioM.setLayout(new FlowLayout(FlowLayout.LEFT));
        //this.añadirM.setLayout(new FlowLayout(FlowLayout.LEFT)); 
        personalC.setOpaque(false); //sin fondo
        inventarioM.setOpaque(false);
        horarioM.setOpaque(false);
        añadirM.setOpaque(false);

        //Cursor de manito al pasar sobre botones
        btnPersonalCentro.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnInventarioMedicamentos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnHorarioMedico.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnregistrarPersonal.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnAñadirMedicamento.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCrearHorario.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

    }
    
    //Habilitar e inhabilitar los botones
    @Override
    public void habilitarBotonesMenu(JButton botonActivo) {
        if (botonActivo != btnPersonalCentro && !btnPersonalCentro.isEnabled()) {
            this.btnPersonalCentro.setEnabled(true);
        }
        
        if (botonActivo != btnInventarioMedicamentos && !btnInventarioMedicamentos.isEnabled()) {
            this.btnInventarioMedicamentos.setEnabled(true);
        }
        
        if (botonActivo != btnHorarioMedico && !btnHorarioMedico.isEnabled()) {
            this.btnHorarioMedico.setEnabled(true);
        }
    }
    
    public void mostrarVistaPersonalCentroApartado() {
        MetodosPublicos.vaciarPanel(cuerpo2); // limpia el panel principal antes de dibujar la nueva vista
        MetodosPublicos.vaciarPanel(personalC); // limpia el panel de esta vista por si ya tenía contenido

        // ---- Título de la vista ----
        JLabel tituloPersonalRegistrado = new JLabel("Personal del Centro Registrado"); // texto del título
        tituloPersonalRegistrado.setFont(new Font("arial", Font.BOLD, 20)); // fuente en negrita, tamaño 20
        tituloPersonalRegistrado.setForeground(PacienteInterfaz.COLOR_AZUL_CORPORATIVO); // color corporativo
        this.personalC.add(tituloPersonalRegistrado); // se agrega el título al panel de la vista

        // ---- Botón de registrar personal, al lado del título ----
        this.personalC.add(Box.createHorizontalStrut(10)); // espacio horizontal entre el título y el botón
        this.personalC.add(btnregistrarPersonal); // se agrega el botón

        // ---- Modelo y columnas de la tabla de personal ----
        listaPersonalR = new DefaultTableModel(); // modelo de datos vacío para la tabla
        listaPersonalR.addColumn("FOTO DE PERFIL");
        listaPersonalR.addColumn("TIPO DOCUMENTO");
        listaPersonalR.addColumn("NÚMERO DOCUMENTO");
        listaPersonalR.addColumn("NOMBRE COMPLETO");
        listaPersonalR.addColumn("EDAD");
        listaPersonalR.addColumn("SEXO");
        listaPersonalR.addColumn("CORREO");
        listaPersonalR.addColumn("NÚMERO CELULAR");
        listaPersonalR.addColumn("ROL");
        listaPersonalR.addColumn("  "); // columna extra para un botón de acción por fila

        // Se crea la tabla con ese modelo y se envuelve en un scroll
        tablaPersonalR = new JTable(listaPersonalR);
        miscrollListaPersonal = new JScrollPane(tablaPersonalR);
        miscrollListaPersonal.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); // scroll horizontal siempre visible
        miscrollListaPersonal.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // scroll vertical siempre visible

        //Se ubica todo dentro de cuerpo2: título/botón arriba, tabla ocupando el resto
        cuerpo2.setLayout(new BorderLayout());
        cuerpo2.add(personalC, BorderLayout.NORTH);
        cuerpo2.add(miscrollListaPersonal, BorderLayout.CENTER);

        //Estilo del encabezado de la tabla
        JTableHeader diseñoColumnaT = tablaPersonalR.getTableHeader();
        diseñoColumnaT.setFont(new Font("arial", Font.BOLD, 14));
        diseñoColumnaT.setForeground(Color.WHITE); // texto blanco
        diseñoColumnaT.setBackground(PacienteInterfaz.COLOR_VERDE_ACENTO); // fondo verde

        //Estilo de las filas de la tabla
        tablaPersonalR.setFont(new Font("Arial", Font.PLAIN, 13));
        tablaPersonalR.setForeground(Color.DARK_GRAY);
        tablaPersonalR.setBackground(Color.WHITE);

        //Se refrescan los paneles para que los cambios se vean en pantalla
        MetodosPublicos.refrescarVentana(personalC);
        MetodosPublicos.refrescarVentana(cuerpo2);
    }
    
    public void mostrarVistaInventarioMedicamentoApartado() {
        MetodosPublicos.vaciarPanel(cuerpo2); // limpia el panel principal
        MetodosPublicos.vaciarPanel(inventarioM); // limpia el panel de esta vista

        // ---- Título de la vista ----
        JLabel tituloMedicamentoRegistrado = new JLabel("Medicamentos Registrados");
        tituloMedicamentoRegistrado.setFont(new Font("arial", Font.BOLD, 20));
        tituloMedicamentoRegistrado.setForeground(PacienteInterfaz.COLOR_AZUL_CORPORATIVO);
        this.inventarioM.add(tituloMedicamentoRegistrado); // se agrega el título

        // ---- Botón de añadir medicamento, al lado del título ----
        this.inventarioM.add(Box.createHorizontalStrut(10)); // espacio entre título y botón
        this.inventarioM.add(btnAñadirMedicamento);

        // ---- Modelo y columnas de la tabla de medicamentos ----
        listaMedicamentoR = new DefaultTableModel();
        listaMedicamentoR.addColumn("FOTO MEDICAMENTO");
        listaMedicamentoR.addColumn("NÚMERO REGISTRO SANITARIO");
        listaMedicamentoR.addColumn("NOMBRE");
        listaMedicamentoR.addColumn("DESCRIPCIÓN");
        listaMedicamentoR.addColumn("FECHA VENCIMIETO");
        listaMedicamentoR.addColumn("CANTIDAD");
        listaMedicamentoR.addColumn("STOCK");
        listaMedicamentoR.addColumn("ULTIMA ACTUALIZACIÓN");
        listaMedicamentoR.addColumn("  ");

        // ---- Tabla y scroll ----
        tablaMedicamentoR = new JTable(listaMedicamentoR);
        miscrollListaMedicamento = new JScrollPane(tablaMedicamentoR);
        miscrollListaMedicamento.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        miscrollListaMedicamento.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // ---- Ubicación dentro de cuerpo2 ----
        cuerpo2.setLayout(new BorderLayout());
        cuerpo2.add(inventarioM, BorderLayout.NORTH);
        cuerpo2.add(miscrollListaMedicamento, BorderLayout.CENTER);

        // ---- Estilo del encabezado de la tabla ----
        JTableHeader diseñoColumnaTa = tablaMedicamentoR.getTableHeader();
        diseñoColumnaTa.setFont(new Font("arial", Font.BOLD, 14));
        diseñoColumnaTa.setForeground(Color.WHITE);
        diseñoColumnaTa.setBackground(PacienteInterfaz.COLOR_VERDE_ACENTO);

        // ---- Estilo de las filas ----
        tablaMedicamentoR.setFont(new Font("Arial", Font.PLAIN, 13));
        tablaMedicamentoR.setForeground(Color.DARK_GRAY);
        tablaMedicamentoR.setBackground(Color.WHITE);

        MetodosPublicos.refrescarVentana(inventarioM);
        MetodosPublicos.refrescarVentana(cuerpo2);
    }
    
    public void mostrarAñadirMedicamentoApartado() {
        MetodosPublicos.vaciarPanel(cuerpo2); // limpia el panel principal
        MetodosPublicos.vaciarPanel(añadirM); // limpia el panel de este formulario
        añadirM.setLayout(new BorderLayout()); // título arriba, contenido en el centro
        añadirM.setOpaque(false); // sin fondo propio

        // ---- Título del formulario ----
        JLabel tituloAñadir = new JLabel("Añadir Nuevo Medicamento");
        tituloAñadir.setFont(new Font("Arial", Font.BOLD, 20));
        tituloAñadir.setForeground(PacienteInterfaz.COLOR_AZUL_CORPORATIVO);
        tituloAñadir.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // aire alrededor del título
        añadirM.add(tituloAñadir, BorderLayout.NORTH);

        // ---- Panel contenedor: formulario a la izquierda, imagen a la derecha ----
        JPanel contenido = new JPanel(new BorderLayout(20, 0)); // 20px de espacio horizontal entre ambos
        contenido.setOpaque(false);
        contenido.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //Formulario con distribución en cuadrícula (2 columnas)
        JPanel formuM = new JPanel(new GridBagLayout());
        formuM.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8); // espacio entre celdas
        gbc.fill = GridBagConstraints.HORIZONTAL; // cada celda ocupa todo el ancho disponible
        gbc.weightx = 0.5; // ambas columnas se reparten el espacio por igual

        //Se crean los campos y se guardan en los atributos de la clase
        campoNRS = EstiloFormulario.crearCampoTexto(); // campo para el número de registro sanitario
        campoNombreM = EstiloFormulario.crearCampoTexto(); // campo para el nombre del medicamento
        campoCantidad = EstiloFormulario.crearCampoTexto(); // campo para la cantidad

        campoTipoM = new JComboBox(); // combo vacío: el Controlador lo llenará con los tipos desde la BD
        EstiloFormulario.crearComboEstilizado(campoTipoM); // se le aplica el estilo, sin tocar sus datos

        // Selector de fecha de vencimiento (calendario, solo fechas desde hoy en adelante)
        campoFechaVencimiento = FechaCalendarioEstilizar.crearDatePickerEstilizado();
        campoFechaVencimiento.setPreferredSize(new Dimension(200, 35)); // tamaño para que combine con los demás campos

        //Campo de descripción (más grande)
        campoDescripcionMedicamento = new JTextArea(5, 20); // 5 filas de alto, 20 columnas de ancho
        campoDescripcionMedicamento.setFont(new Font("Arial", Font.PLAIN, 15));
        campoDescripcionMedicamento.setLineWrap(true); // el texto salta de línea automáticamente
        campoDescripcionMedicamento.setWrapStyleWord(true); // el salto respeta palabras completas
        campoDescripcionMedicamento.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PacienteInterfaz.COLOR_VERDE_ACENTO, 1, true),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        JScrollPane scrollDescrip = new JScrollPane(campoDescripcionMedicamento); // scroll por si el texto crece
        scrollDescrip.setBorder(BorderFactory.createEmptyBorder()); // sin borde propio (ya lo tiene el JTextArea)

        //Fila 0: Número de Registro Sanitario | Nombre 
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1; // ocupa 1 sola columna
        formuM.add(EstiloFormulario.crearCampoConEtiqueta("Numero de Registro Sanitario:", campoNRS), gbc);
        gbc.gridx = 1;
        formuM.add(EstiloFormulario.crearCampoConEtiqueta("Nombre:", campoNombreM), gbc);

        // Fila 1: Fecha de Vencimiento | Cantidad
        gbc.gridx = 0;
        gbc.gridy = 1;
        formuM.add(EstiloFormulario.crearCampoConEtiqueta("Fecha de Vencimiento:", campoFechaVencimiento), gbc);
        gbc.gridx = 1;
        formuM.add(EstiloFormulario.crearCampoConEtiqueta("Cantidad:", campoCantidad), gbc);

        // Fila 2: Tipo de Medicamento (una sola columna)
        gbc.gridx = 0;
        gbc.gridy = 2;
        formuM.add(EstiloFormulario.crearCampoConEtiqueta("Tipo de Medicamento:", campoTipoM), gbc);

        // Fila 3: Descripción (ocupa las 2 columnas y se estira verticalmente)
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2; // ocupa ambas columnas
        gbc.fill = GridBagConstraints.BOTH; // se estira en ancho y alto
        gbc.weighty = 1.0; // toma todo el espacio vertical restante
        formuM.add(EstiloFormulario.crearCampoConEtiqueta("Descripción:", scrollDescrip), gbc);

        contenido.add(formuM, BorderLayout.CENTER); // el formulario va en el centro

        //Panel lateral para adjuntar la imagen del medicamento
        previsualizacionImagen = new JLabel(); // se guarda como atributo para que el Controlador la actualice
        JPanel panelImagen = EstiloFormulario.crearPanelImagen(btnSeleccionar, previsualizacionImagen);
        contenido.add(panelImagen, BorderLayout.EAST); // el panel de imagen va a la derecha

        añadirM.add(contenido, BorderLayout.CENTER); // se agrega todo el contenido al panel del formulario

        //Se ubica el formulario dentro de cuerpo2, ocupando toda la pantalla
        cuerpo2.setLayout(new BorderLayout());
        cuerpo2.add(añadirM, BorderLayout.CENTER);

        MetodosPublicos.refrescarVentana(añadirM);
        MetodosPublicos.refrescarVentana(cuerpo2);
    }
    
    public void mostrarVistaHorarioMedicoApartado() {
        MetodosPublicos.vaciarPanel(cuerpo2); // limpia el panel principal
        MetodosPublicos.vaciarPanel(horarioM); // limpia el panel de esta vista

        //Título de la vista
        JLabel tituloHorarioCreado = new JLabel("Lista de Horarios Creados");
        tituloHorarioCreado.setFont(new Font("arial", Font.BOLD, 20));
        tituloHorarioCreado.setForeground(PacienteInterfaz.COLOR_AZUL_CORPORATIVO);
        this.horarioM.add(tituloHorarioCreado);

        //Botón de crear horario, al lado del título 
        this.horarioM.add(Box.createHorizontalStrut(10));
        this.horarioM.add(btnCrearHorario);

        //Modelo y columnas de la tabla de horarios
        listaHorarioM = new DefaultTableModel();
        listaHorarioM.addColumn("ETIQUETA");
        listaHorarioM.addColumn("NOMBRE");
        listaHorarioM.addColumn("FECHA CREACIÓN");

        //Tabla y scroll
        tablaHorarioM = new JTable(listaHorarioM);
        miscrollListaHorarioM = new JScrollPane(tablaHorarioM);
        miscrollListaHorarioM.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        miscrollListaHorarioM.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        //Ubicación dentro de cuerpo2
        cuerpo2.setLayout(new BorderLayout());
        cuerpo2.add(horarioM, BorderLayout.NORTH);
        cuerpo2.add(miscrollListaHorarioM, BorderLayout.CENTER);

        //Estilo del encabezado de la tabla
        JTableHeader diseñoColumnaTab = tablaHorarioM.getTableHeader();
        diseñoColumnaTab.setFont(new Font("arial", Font.BOLD, 14));
        diseñoColumnaTab.setForeground(Color.WHITE);
        diseñoColumnaTab.setBackground(PacienteInterfaz.COLOR_AZUL_CORPORATIVO); // nota: aquí usa azul, no verde como las otras tablas

        //Estilo de las filas
        tablaHorarioM.setFont(new Font("Arial", Font.PLAIN, 13));
        tablaHorarioM.setForeground(Color.DARK_GRAY);
        tablaHorarioM.setBackground(Color.WHITE);

        MetodosPublicos.refrescarVentana(horarioM);
        MetodosPublicos.refrescarVentana(cuerpo2);
    }
    
    public JPanel mostrarFormularioCreacionHorarioApartado() {
        JPanel formulario = new JPanel(new BorderLayout(0, 12)); // separación vertical entre secciones
        MetodosPublicos.vaciarPanel(cuerpo2); // limpia el panel principal
        MetodosPublicos.vaciarPanel(formulario); // limpia el formulario por si ya tenía contenido
        formulario.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20)); // márgenes generales
        formulario.setOpaque(false);

        // ---- Título del formulario ----
        JLabel tituloFormulario = new JLabel("Crear Nuevo Horario");
        tituloFormulario.setFont(new Font("Arial", Font.BOLD, 20));
        tituloFormulario.setForeground(PacienteInterfaz.COLOR_AZUL_CORPORATIVO);

        // ---- Fila superior: botón "Volver" + título ----
        JPanel volver = new JPanel(new BorderLayout());
        volver.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT)); // se sobreescribe a FlowLayout
        volver.setOpaque(false);
        btnVolver.setForeground(PacienteInterfaz.COLOR_VERDE_ACENTO); // estilo del botón (ya creado en el constructor)
        btnVolver.setFont(new Font("Arial", Font.BOLD, 18));
        btnVolver.setContentAreaFilled(false); // sin fondo, parece un link
        btnVolver.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        volver.add(btnVolver, BorderLayout.WEST); // botón a la izquierda
        volver.add(tituloFormulario, BorderLayout.CENTER); // título al centro
        formulario.add(volver, BorderLayout.NORTH); // toda la fila va arriba del formulario
        volver.add(Box.createHorizontalStrut(40)); // espacio extra a la derecha

        // ---- Cuerpo del formulario (apilado verticalmente) ----
        JPanel cuerpoFormulario = new JPanel();
        cuerpoFormulario.setLayout(new BoxLayout(cuerpoFormulario, BoxLayout.Y_AXIS));
        cuerpoFormulario.setOpaque(false);
        cuerpoFormulario.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 4));

        // ---- Sección 1: Información básica del horario ----
        JPanel seccionInfo = new JPanel(new BorderLayout(8, 0));
        seccionInfo.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        seccionInfo.setOpaque(false);
        seccionInfo.setAlignmentX(Component.LEFT_ALIGNMENT);
        seccionInfo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        JLabel tituloSeccion1 = new JLabel("1. Información del Horario");
        tituloSeccion1.setFont(new Font("Arial", Font.BOLD, 13));
        tituloSeccion1.setForeground(PacienteInterfaz.COLOR_AZUL_CORPORATIVO);
        JSeparator sepDia = new JSeparator();
        sepDia.setForeground(PacienteInterfaz.COLOR_VERDE_ACENTO);
        seccionInfo.add(tituloSeccion1, BorderLayout.WEST);
        seccionInfo.add(sepDia, BorderLayout.WEST); // (se mantiene igual que el original, aunque ambos usan WEST)
        cuerpoFormulario.add(seccionInfo);
        cuerpoFormulario.add(Box.createVerticalStrut(10)); // espacio debajo de la sección

        // ---- Campo: nombre del horario (ahora es un atributo de la clase) ----
        campoNombreHorario = new JTextField(16);
        campoNombreHorario.setFont(new Font("Arial", Font.PLAIN, 13));
        campoNombreHorario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PacienteInterfaz.COLOR_VERDE_ACENTO, 1, true),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        cuerpoFormulario.add(EstiloFormulario.crearFila("Nombre del horario:", campoNombreHorario));
        cuerpoFormulario.add(Box.createVerticalStrut(20));

        // ---- Campo: color de la etiqueta del horario (ahora es un atributo de la clase) ----
        comboColorEtiqueta = new JComboBox<>(COLOR_NOMBRES); // usa el arreglo de nombres definido en la Sección 1
        comboColorEtiqueta.setFont(new Font("Arial", Font.PLAIN, 13));
        comboColorEtiqueta.setPreferredSize(new Dimension(150, 32));

        // Renderer personalizado: muestra un círculo del color al lado del nombre en la lista
        comboColorEtiqueta.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object val,
                    int idx, boolean sel, boolean foc) {
                JPanel filaCb = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 6, 2));
                filaCb.setBackground(sel ? PacienteInterfaz.COLOR_VERDE_ACENTO : Color.WHITE); // resalta la opción seleccionada
                int i = idx < 0 ? comboColorEtiqueta.getSelectedIndex() : idx; // índice del color a pintar
                if (i >= 0 && i < COLOR_VALORES.length) {
                    filaCb.add(new JPanel() { // panel pequeño que dibuja el círculo de color
                        {
                            setPreferredSize(new Dimension(14, 14));
                            setOpaque(false);
                        }

                        @Override
                        protected void paintComponent(Graphics g) {
                            Graphics2D g2 = (Graphics2D) g;
                            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // bordes suaves
                            g2.setColor(COLOR_VALORES[i]); // color correspondiente a esta opción
                            g2.fillOval(0, 0, 14, 14); // círculo relleno
                        }
                    });
                }
                JLabel lbl = new JLabel(val != null ? val.toString() : ""); // nombre del color
                lbl.setFont(new Font("Arial", Font.BOLD, 13));
                filaCb.add(lbl);
                return filaCb;
            }
        });
        cuerpoFormulario.add(EstiloFormulario.crearFila("Color de etiqueta:", comboColorEtiqueta));
        cuerpoFormulario.add(Box.createVerticalStrut(20));

        // ---- Sección 2: Días de la semana ----
        JPanel seccionDias = new JPanel(new BorderLayout(8, 0));
        seccionDias.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        seccionDias.setOpaque(false);
        seccionDias.setAlignmentX(Component.LEFT_ALIGNMENT);
        seccionDias.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        JLabel tituloSeccion2 = new JLabel("2. Configurar días de la semana");
        tituloSeccion2.setFont(new Font("Arial", Font.BOLD, 13));
        tituloSeccion2.setForeground(PacienteInterfaz.COLOR_AZUL_CORPORATIVO);
        JLabel nota = new JLabel("Máx. 8h laborales + 1h almuerzo por día  ");
        nota.setFont(new Font("Arial", Font.PLAIN, 11));
        nota.setForeground(Color.GRAY);
        JSeparator sepDias = new JSeparator();
        sepDias.setForeground(PacienteInterfaz.COLOR_VERDE_ACENTO);
        seccionDias.add(tituloSeccion2, BorderLayout.WEST);
        seccionDias.add(sepDias, BorderLayout.CENTER);
        seccionDias.add(nota, BorderLayout.EAST);
        cuerpoFormulario.add(seccionDias);
        cuerpoFormulario.add(Box.createVerticalStrut(10));

        // ---- Tarjeta que contendrá las 6 filas de días ----
        JPanel tarjetaDias = new JPanel();
        tarjetaDias.setLayout(new BorderLayout(0, 12));
        tarjetaDias.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        tarjetaDias.setOpaque(false);

        // ---- Panel que apila las 6 filas de días (una por cada día de la semana) ----
        JPanel panelDias = new JPanel();
        panelDias.setLayout(new BoxLayout(panelDias, BoxLayout.Y_AXIS));
        panelDias.setOpaque(false);

        for (int i = 0; i < 6; i++) { // recorre Lunes(0) a Sábado(5)
            // Se construye cada fila con la clase utilitaria, pasándole los arreglos de la Sección 1
            panelDias.add(ConstructorFilaHorario.construirFilaDia(
                    i, DIAS_SEMANA, HORAS,
                    diaSemana, horaInicio, horaFin, almuerzoIni, almuerzoFin, lblHoras
            ));
            if (i < 5) { // espacio entre filas, menos después de la última
                panelDias.add(Box.createVerticalStrut(6));
            }
        }

        // ---- Scroll para todo el cuerpo del formulario ----
        JScrollPane scrollFormulario = new JScrollPane(cuerpoFormulario);
        scrollFormulario.setBorder(BorderFactory.createEmptyBorder());
        scrollFormulario.getVerticalScrollBar().setUnitIncrement(14); // desplazamiento más suave con la rueda del mouse
        formulario.add(scrollFormulario, BorderLayout.CENTER);

        // ---- Botón de guardar horario (ahora es el atributo btnGuardarHorario de la Sección 1) ----
        cuerpoFormulario.add(btnGuardarHorario);
        cuerpoFormulario.add(Box.createVerticalStrut(20));
        tarjetaDias.add(panelDias, BorderLayout.CENTER); // se agregan las 6 filas a la tarjeta
        cuerpoFormulario.add(tarjetaDias); // la tarjeta se agrega al cuerpo del formulario
        cuerpoFormulario.add(Box.createVerticalStrut(20));

        // ---- Se ubica el formulario dentro de cuerpo2 ----
        cuerpo2.setLayout(new BorderLayout());
        cuerpo2.add(formulario, BorderLayout.NORTH);
        cuerpo2.add(scrollFormulario, BorderLayout.CENTER);

        MetodosPublicos.refrescarVentana(formulario);
        MetodosPublicos.refrescarVentana(cuerpo2);

        return formulario; // se retorna por si el Controlador necesita la referencia directa
    }
}
