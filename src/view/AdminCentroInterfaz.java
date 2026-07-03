/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import model.MetodosPublicos;

/**
 *
 * @author lunaa
 */
public class AdminCentroInterfaz extends PacienteInterfaz {

    //interfaces principales (basico)
    public JButton btnPersonalCentro, btnInventarioMedicamentos, btnregistrarPersonal,
            btnAñadirMedicamento, btnHorarioMedico, btnCrearHorario, btnVolver;
    public JPanel personalC, inventarioM, horarioM, AsignarHorario;
    public JTable tablaPersonalR, tablaMedicamentoR, tablaHorarioM;
    private JScrollPane miscrollListaPersonal, miscrollListaMedicamento, miscrollListaHorarioM;
    public DefaultTableModel listaPersonalR, listaMedicamentoR, listaHorarioM;

    //colores para horarios (etiqueta)
    private static final Color ETIQ_AZUL = new Color(0x1B, 0x6B, 0x7B);
    private static final Color ETIQ_ROJA = new Color(0xE5, 0x4A, 0x4A);
    private static final Color ETIQ_VERDE = new Color(0x1D, 0x9E, 0x75);
    private static final Color ETIQ_NARANJA = new Color(0xEF, 0x9F, 0x27);
    private static final Color ETIQ_MORADA = new Color(0x7C, 0x4D, 0xFF);
    private static final Color ETIQ_GRIS = new Color(0x78, 0x90, 0x9C);

    //lista de colores para la etiqueta del horario
    private static final String[] COLOR_NOMBRES = {"Azul", "Roja",
        "Verde", "Naranja", "Morado", "Gris"};
    private static final Color[] COLOR_VALORES = {ETIQ_AZUL, ETIQ_ROJA, ETIQ_VERDE, ETIQ_NARANJA,
        ETIQ_MORADA, ETIQ_GRIS};
    //Lista de dias de la semana para horario
    private static final String[] DIAS_SEMANA = {"Lunes", "Martes", "Miércoles", "Jueves",
        "Viernes", "Sábado"};
    // Horas disponibles en los JCombos (de 07:00 a 17:00 cada 30 min)
    private static final String[] HORAS = generarHoras();
    
    public JCheckBox[] diaSemana = new JCheckBox[6];
    public JComboBox[] horaInicio = new JComboBox[6];
    public JComboBox[] horaFin    = new JComboBox[6];
    public JComboBox[] almuerzoIni = new JComboBox[6];
    public JComboBox[] almuerzoFin = new JComboBox[6];
    public JLabel[] lblHoras  = new JLabel[6];

    public AdminCentroInterfaz(String nombrePersona, String nombreInterfaz, String url) {
        super(nombrePersona, nombreInterfaz, url);
        this.btnPersonalCentro = new JButton("👥 Personal del Centro");
        this.btnregistrarPersonal = new JButton("👤 Registrar Personal");
        MetodosPublicos.estilizarBoton(btnregistrarPersonal, (byte) 5);
        this.btnInventarioMedicamentos = new JButton("💊 Inventario de Medicamentos");
        this.btnAñadirMedicamento = new JButton("➕ Añadir Medicamento");
        MetodosPublicos.estilizarBoton(btnAñadirMedicamento, (byte) 5);
        this.btnHorarioMedico = new JButton("🗓️ Horarios Médicos");
        this.btnCrearHorario = new JButton("📍 Crear Nuevo Horario");
        btnCrearHorario.setFont(new Font("Arial", Font.BOLD, 20));
        btnCrearHorario.setBackground(PacienteInterfaz.COLOR_VERDE_ACENTO);
        btnCrearHorario.setForeground(Color.WHITE);
        btnCrearHorario.setAlignmentX(Component.CENTER_ALIGNMENT);
        super.agregarBotonCuerpo1(btnPersonalCentro);
        super.agregarBotonCuerpo1(btnInventarioMedicamentos);
        super.agregarBotonCuerpo1(btnHorarioMedico);
        this.personalC = new JPanel();
        this.inventarioM = new JPanel();
        this.horarioM = new JPanel();
        this.personalC.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.inventarioM.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.horarioM.setLayout(new FlowLayout(FlowLayout.LEFT));
        personalC.setOpaque(false);
        inventarioM.setOpaque(false);
        horarioM.setOpaque(false);
        btnPersonalCentro.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnInventarioMedicamentos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnHorarioMedico.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnregistrarPersonal.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnAñadirMedicamento.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCrearHorario.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

    }

    public void habilitarBotones(JButton botonActivo) {
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

    public void mostrarVistaPersonalCentro() {
        MetodosPublicos.vaciarPanel(cuerpo2);
        MetodosPublicos.vaciarPanel(personalC);

        JLabel tituloPersonalRegistrado = new JLabel("Personal del Centro Registrado");
        //todo lo del titulo de personal del centro registrado 
        tituloPersonalRegistrado.setFont(new Font("arial", Font.BOLD, 20)); //se crea el titulo
        tituloPersonalRegistrado.setForeground(PacienteInterfaz.COLOR_AZUL_CORPORATIVO); //se le añade un color
        this.personalC.add(tituloPersonalRegistrado); //se agrega el titulo al panel del apartado

        //boton de registro al lado del titulo
        this.personalC.add(Box.createHorizontalStrut(10)); //se da un espacio entre el titulo y el boton para separar
        this.personalC.add(btnregistrarPersonal); //se agrega el boton al panel del apartado

        //lista de personal regisrado
        listaPersonalR = new DefaultTableModel();
        listaPersonalR.addColumn("FOTO DE PERFIL");
        listaPersonalR.addColumn("TIPO DOCUMENTO");
        listaPersonalR.addColumn("NÚMERO DOCUMENTO");
        listaPersonalR.addColumn("NOMBRE COMPLETO");
        listaPersonalR.addColumn("EDAD");
        listaPersonalR.addColumn("SEXO");
        listaPersonalR.addColumn("CORREO");
        listaPersonalR.addColumn("NÚMERO CELULAR");
        listaPersonalR.addColumn("ROL");
        listaPersonalR.addColumn("  ");
        //detallesListaPersonalR = new JPanel(); //panel que va a contener la lista
        //grid = new GridLayout(1,1);
        tablaPersonalR = new JTable(listaPersonalR);
        miscrollListaPersonal = new JScrollPane(tablaPersonalR);
        //miscrollLista.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        //se visualiza todo el tiempo
        miscrollListaPersonal.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        miscrollListaPersonal.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        //se agrega todo lo anterior al cuerpo dos
        cuerpo2.setLayout(new BorderLayout());
        cuerpo2.add(personalC, BorderLayout.NORTH);
        cuerpo2.add(miscrollListaPersonal, BorderLayout.CENTER);

        //diseño para las columnas de la tabla
        JTableHeader diseñoColumnaT = tablaPersonalR.getTableHeader();
        diseñoColumnaT.setFont(new Font("arial", Font.BOLD, 14));
        diseñoColumnaT.setForeground(Color.WHITE); //color del texto
        diseñoColumnaT.setBackground(PacienteInterfaz.COLOR_VERDE_ACENTO); //color de fondo de la columna

        //diseño para las filas en general
        tablaPersonalR.setFont(new Font("Arial", Font.PLAIN, 13));
        tablaPersonalR.setForeground(Color.DARK_GRAY);//color del texto filas
        tablaPersonalR.setBackground(Color.WHITE);// fondo blanco

        MetodosPublicos.refrescarVentana(personalC);
        MetodosPublicos.refrescarVentana(cuerpo2);

    }

    public void mostrarVistaInventarioMedicamento() {
        MetodosPublicos.vaciarPanel(cuerpo2);
        MetodosPublicos.vaciarPanel(inventarioM);

        JLabel tituloMedicamentoRegistrado = new JLabel("Medicamentos Registrados");
        //todo lo del titulo de medicamentos registrados 
        tituloMedicamentoRegistrado.setFont(new Font("arial", Font.BOLD, 20)); //se crea el titulo
        tituloMedicamentoRegistrado.setForeground(PacienteInterfaz.COLOR_AZUL_CORPORATIVO); //se le añade un color
        this.inventarioM.add(tituloMedicamentoRegistrado); //se agrega el titulo al panel del apartado

        //boton de añadir al lado del titulo
        this.inventarioM.add(Box.createHorizontalStrut(10)); //se da un espacio entre el titulo y el boton para separar
        this.inventarioM.add(btnAñadirMedicamento); //se agrega el boton al panel del apartado

        //lista de personal regisrado
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

        tablaMedicamentoR = new JTable(listaMedicamentoR);
        miscrollListaMedicamento = new JScrollPane(tablaMedicamentoR);

        //se visualiza todo el tiempo
        miscrollListaMedicamento.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        miscrollListaMedicamento.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        //se agrega todo lo anterior al cuerpo dos
        cuerpo2.setLayout(new BorderLayout());
        cuerpo2.add(inventarioM, BorderLayout.NORTH);
        cuerpo2.add(miscrollListaMedicamento, BorderLayout.CENTER);

        //diseño para las columnas de la tabla
        JTableHeader diseñoColumnaTa = tablaMedicamentoR.getTableHeader();
        diseñoColumnaTa.setFont(new Font("arial", Font.BOLD, 14));
        diseñoColumnaTa.setForeground(Color.WHITE); //color del texto
        diseñoColumnaTa.setBackground(PacienteInterfaz.COLOR_VERDE_ACENTO); //color de fondo de la columna

        //diseño para las filas en general
        tablaMedicamentoR.setFont(new Font("Arial", Font.PLAIN, 13));
        tablaMedicamentoR.setForeground(Color.DARK_GRAY);//color del texto filas
        tablaMedicamentoR.setBackground(Color.WHITE);// fondo blanco

        MetodosPublicos.refrescarVentana(inventarioM);
        MetodosPublicos.refrescarVentana(cuerpo2);
    }

    public void mostrarVistaHorarioMedico() {
        MetodosPublicos.vaciarPanel(cuerpo2);
        MetodosPublicos.vaciarPanel(horarioM);

        JLabel tituloHorarioCreado = new JLabel("Lista de Horarios Creados");
        //todo lo del titulo de horarios creados 
        tituloHorarioCreado.setFont(new Font("arial", Font.BOLD, 20)); //se crea el titulo
        tituloHorarioCreado.setForeground(PacienteInterfaz.COLOR_AZUL_CORPORATIVO); //se le añade un color
        this.horarioM.add(tituloHorarioCreado); //se agrega el titulo al panel del apartado

        //boton de añadir al lado del titulo
        this.horarioM.add(Box.createHorizontalStrut(10)); //se da un espacio entre el titulo y el boton para separar
        this.horarioM.add(btnCrearHorario); //se agrega el boton al panel del apartado

        //lista de horario creado
        listaHorarioM = new DefaultTableModel();
        listaHorarioM.addColumn("ETIQUETA");
        listaHorarioM.addColumn("NOMBRE");
        listaHorarioM.addColumn("FECHA CREACIÓN");

        tablaHorarioM = new JTable(listaHorarioM);
        miscrollListaHorarioM = new JScrollPane(tablaHorarioM);

        //se visualiza todo el tiempo
        miscrollListaHorarioM.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        miscrollListaHorarioM.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        //se agrega todo lo anterior al cuerpo dos
        cuerpo2.setLayout(new BorderLayout());
        cuerpo2.add(horarioM, BorderLayout.NORTH);
        cuerpo2.add(miscrollListaHorarioM, BorderLayout.CENTER);

        //diseño para las columnas de la tabla
        JTableHeader diseñoColumnaTab = tablaHorarioM.getTableHeader();
        diseñoColumnaTab.setFont(new Font("arial", Font.BOLD, 14));
        diseñoColumnaTab.setForeground(Color.WHITE); //color del texto
        diseñoColumnaTab.setBackground(PacienteInterfaz.COLOR_AZUL_CORPORATIVO); //color de fondo de la columna

        //diseño para las filas en general
        tablaHorarioM.setFont(new Font("Arial", Font.PLAIN, 13));
        tablaHorarioM.setForeground(Color.DARK_GRAY);//color del texto filas
        tablaHorarioM.setBackground(Color.WHITE);// fondo blanco

        MetodosPublicos.refrescarVentana(horarioM);
        MetodosPublicos.refrescarVentana(cuerpo2);
    }

    public JPanel mostrarFormularioCreacionHorario() {
        JPanel formulario = new JPanel(new BorderLayout(0, 12));
        MetodosPublicos.vaciarPanel(cuerpo2);
        MetodosPublicos.vaciarPanel(formulario);
        formulario.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));
        formulario.setOpaque(false);

        //titulo del formulario
        JLabel tituloFormulario = new JLabel("Crear Nuevo Horario");
        tituloFormulario.setFont(new Font("Arial", Font.BOLD, 20));
        tituloFormulario.setForeground(PacienteInterfaz.COLOR_AZUL_CORPORATIVO);

        //boton para volver 
        JPanel volver = new JPanel(new BorderLayout());
        volver.setLayout(new FlowLayout(FlowLayout.LEFT));
        volver.setOpaque(false);
        btnVolver = new JButton("← Volver");
        btnVolver.setForeground(PacienteInterfaz.COLOR_VERDE_ACENTO);
        btnVolver.setFont(new Font("Arial", Font.BOLD, 18));
        btnVolver.setContentAreaFilled(false); //quitarle el fondo al boton
        btnVolver.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        //agregar el volver y el titulo al formulario
        volver.add(btnVolver, BorderLayout.WEST);
        volver.add(tituloFormulario, BorderLayout.CENTER);
        formulario.add(volver, BorderLayout.NORTH);
        volver.add(Box.createHorizontalStrut(40));
        
        

        //Cuerpo del formulario
        JPanel cuerpoFormulario = new JPanel();
        cuerpoFormulario.setLayout(new FlowLayout(FlowLayout.LEFT));
        cuerpoFormulario.setLayout(new BoxLayout(cuerpoFormulario, BoxLayout.Y_AXIS));
        cuerpoFormulario.setOpaque(false);
        cuerpoFormulario.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 4));
        
        //Sección 1: Datos básicos
        JPanel seccionInfo = new JPanel(new BorderLayout(8, 0));
        seccionInfo.setLayout(new FlowLayout(FlowLayout.LEFT));
        seccionInfo.setOpaque(false);
        seccionInfo.setAlignmentX(Component.LEFT_ALIGNMENT);
        seccionInfo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        JLabel tituloSeccion1 = new JLabel("1. Información del Horario");
        tituloSeccion1.setFont(new Font("Arial", Font.BOLD, 13));
        tituloSeccion1.setForeground(PacienteInterfaz.COLOR_AZUL_CORPORATIVO);
        JSeparator sepDia = new JSeparator();
        sepDia.setForeground(PacienteInterfaz.COLOR_VERDE_ACENTO);
        seccionInfo.add(tituloSeccion1, BorderLayout.WEST);
        seccionInfo.add(sepDia,     BorderLayout.WEST);
        cuerpoFormulario.add(seccionInfo);
        cuerpoFormulario.add(Box.createVerticalStrut(10));

        //campo para nombre del horario
        JTextField campoNombre = new JTextField(16);
        campoNombre.setFont(new Font("Arial", Font.PLAIN, 13));
        cuerpoFormulario.add(crearFila("Nombre del horario:", campoNombre));
        cuerpoFormulario.add(Box.createVerticalStrut(20));
        campoNombre.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PacienteInterfaz.COLOR_VERDE_ACENTO, 1, true),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));

        // Campo Color / etiqueta
        JComboBox<String> comboColor = new JComboBox<>(COLOR_NOMBRES);
        comboColor.setFont(new Font("Arial", Font.PLAIN, 13));
        comboColor.setPreferredSize(new Dimension(150, 32));
        
        //Poner el el tono del color al lado del nombre en la lista de etiqueta
        comboColor.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object val,
                    int idx, boolean sel, boolean foc) {
                JPanel filaCb = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 2));
                filaCb.setBackground(sel ? PacienteInterfaz.COLOR_VERDE_ACENTO : Color.WHITE);
                int i = idx < 0 ? comboColor.getSelectedIndex() : idx;
                if (i >= 0 && i < COLOR_VALORES.length) {
                    filaCb.add(new JPanel() {
                        { setPreferredSize(new Dimension(14, 14)); setOpaque(false); }
                        @Override
                        protected void paintComponent(Graphics g) {
                            Graphics2D g2 = (Graphics2D) g;
                            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                            g2.setColor(COLOR_VALORES[i]);
                            g2.fillOval(0, 0, 14, 14);
                        }
                    });
                }
                JLabel lbl = new JLabel(val != null ? val.toString() : "");
                lbl.setFont(new Font("Arial", Font.BOLD, 13));
                filaCb.add(lbl);
                return filaCb;
            }
        });
        cuerpoFormulario.add(crearFila("Color de etiqueta:", comboColor));
        cuerpoFormulario.add(Box.createVerticalStrut(20));

        //Sección 2: Días de la semana
        JPanel seccionDias = new JPanel(new BorderLayout(8, 0));
        seccionDias.setLayout(new FlowLayout(FlowLayout.LEFT));
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
        seccionDias.add(sepDias,     BorderLayout.CENTER);
        seccionDias.add(nota,        BorderLayout.EAST);
        cuerpoFormulario.add(seccionDias);
        cuerpoFormulario.add(Box.createVerticalStrut(10));
        
        JPanel tarjetaDias = new JPanel();
        tarjetaDias.setLayout(new BorderLayout(0, 12));
        tarjetaDias.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        tarjetaDias.setOpaque(false);
        
        // Panel de tarjetas de días (apiladas a la izquierda)
        JPanel panelDias = new JPanel();
        panelDias.setLayout(new BoxLayout(panelDias, BoxLayout.Y_AXIS));
        panelDias.setOpaque(false);
 
        for (int i = 0; i < 6; i++) {
            panelDias.add(buildFilaDia(i));
            if (i < 5) panelDias.add(Box.createVerticalStrut(6));
        }
        
        JScrollPane scrollFormulario = new JScrollPane(cuerpoFormulario);
        scrollFormulario.setBorder(BorderFactory.createEmptyBorder());
        scrollFormulario.getVerticalScrollBar().setUnitIncrement(14);
        formulario.add(scrollFormulario, BorderLayout.CENTER);
        
        //boton de guardar
        JButton btnGuardar = new JButton("Guardar horario");
        MetodosPublicos.estilizarBoton(btnGuardar, (byte) 1);
        cuerpoFormulario.add(btnGuardar);
        cuerpoFormulario.add(Box.createVerticalStrut(20));
        tarjetaDias.add(panelDias, BorderLayout.CENTER);
        cuerpoFormulario.add(tarjetaDias);
        cuerpoFormulario.add(Box.createVerticalStrut(20));
        
        
        cuerpo2.setLayout(new BorderLayout());
        cuerpo2.add(formulario, BorderLayout.NORTH);
        cuerpo2.add(scrollFormulario, BorderLayout.CENTER);
        
        MetodosPublicos.refrescarVentana(formulario);
        MetodosPublicos.refrescarVentana(cuerpo2);

        return formulario;

    }
    
     private JPanel buildFilaDia(int i) {
        // Panel contenedor de la fila con borde suave
        JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 6));
        fila.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PacienteInterfaz.COLOR_VERDE_ACENTO, 1, true),
            BorderFactory.createEmptyBorder(2, 8, 2, 8)
        ));
        fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        fila.setAlignmentX(Component.LEFT_ALIGNMENT);
 
        // Checkbox para activar/desactivar el día
        diaSemana[i] = new JCheckBox();
        diaSemana[i].setSelected(i < 5); // Lun-Vie activos, Sáb no
        diaSemana[i].setOpaque(false);
 
        // Nombre del día con ancho fijo para que queden alineados
        JLabel lblDia = new JLabel(DIAS_SEMANA[i]);
        lblDia.setFont(new Font("Arial", Font.BOLD, 13));
        lblDia.setForeground(Color.DARK_GRAY);
        lblDia.setPreferredSize(new Dimension(82, 20));
 
        // Separador visual entre el nombre y los combos
        JSeparator sep = new JSeparator(JSeparator.VERTICAL);
        sep.setPreferredSize(new Dimension(1, 24));
        sep.setForeground(PacienteInterfaz.COLOR_VERDE_ACENTO);
 
        // Combos de hora — sin ActionListener (el Controlador los pone)
        horaInicio[i] = buildComboHora();
        horaFin[i]    = buildComboHora();
        almuerzoIni[i] = buildComboHora();
        almuerzoFin[i] = buildComboHora();
 
        // Label que muestra las horas laborales calculadas
        lblHoras[i] = new JLabel("—");
        lblHoras[i].setFont(new Font("Arial", Font.BOLD, 12));
        lblHoras[i].setForeground(Color.DARK_GRAY);
        lblHoras[i].setPreferredSize(new Dimension(54, 20));
 
        // Pequeñas etiquetas entre combos para mayor claridad
        fila.add(diaSemana[i]);
        fila.add(lblDia);
        fila.add(sep);
        fila.add(buildMiniEtiqueta("Inicio"));
        fila.add(horaInicio[i]);
        fila.add(buildMiniEtiqueta("Fin"));
        fila.add(horaFin[i]);
        fila.add(buildMiniEtiqueta("Almuerzo"));
        fila.add(almuerzoIni[i]);
        fila.add(buildMiniEtiqueta("→"));
        fila.add(almuerzoFin[i]);
        fila.add(buildMiniEtiqueta("Lab."));
        fila.add(lblHoras[i]);
 
        // Si el día está inactivo al inicio, atenuar la fila
        if (!diaSemana[i].isSelected()) atenuarFila(i, false);
 
        return fila;
    }
     
     public void atenuarFila(int i, boolean activo) {
        horaInicio[i].setEnabled(activo);
        horaFin[i].setEnabled(activo);
        almuerzoIni[i].setEnabled(activo);
        almuerzoFin[i].setEnabled(activo);
        if (!activo) {
            lblHoras[i].setText("—");
            lblHoras[i].setForeground(Color.GRAY);
        }
    }
     
    private JLabel buildMiniEtiqueta(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(new Font("SansSerif", Font.PLAIN, 11));
        l.setForeground(Color.GRAY);
        return l;
    }
    
    private JComboBox buildComboHora() {
        JComboBox cb = new JComboBox<>(HORAS);
        cb.setFont(new Font("SansSerif", Font.PLAIN, 12));
        cb.setPreferredSize(new Dimension(76, 28));
        return cb;
    }

    //Genera el arreglo de horas de 07:00 a 17:00 en pasos de 30 min.
    private static String[] generarHoras() {
        List<String> lista = new ArrayList<>();
        for (int h = 7; h <= 17; h++) {
            for (int m : new int[]{0, 30}) {
                lista.add(String.format("%02d:%02d", h, m));
            }
        }
        return lista.toArray(new String[0]);
    }

    private JPanel crearFila(String etiqueta, JComponent componente) {
        JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        fila.setOpaque(false);
        fila.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(new Font("Arial", Font.BOLD, 17));
        lbl.setForeground(Color.darkGray);
        fila.add(lbl);
        fila.add(componente);
        return fila;
    }

    private JPanel crearSeparador(String texto) {
        JPanel p = new JPanel(new BorderLayout(8, 0));
        p.setOpaque(false);
        p.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Arial", Font.BOLD, 17));
        lbl.setForeground(PacienteInterfaz.COLOR_AZUL_CORPORATIVO);
        JSeparator sep = new JSeparator();
        sep.setForeground(PacienteInterfaz.COLOR_VERDE_ACENTO);
        p.add(lbl, BorderLayout.CENTER);
        p.add(sep, BorderLayout.CENTER);
        return p;
    }
    
    

}
