package view;

import com.github.lgooddatepicker.components.CalendarPanel;
import com.github.lgooddatepicker.components.DatePickerSettings;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import model.MetodosPublicos;
import model.Paciente;

public class PacienteInterfaz extends JFrame {

    public Paciente usuario;
    //Aqui creo los colores que mas vamos autilizar en la plantilla.
    public static final Color COLOR_AZUL_CORPORATIVO = new Color(0, 79, 124);
    public static final Color COLOR_VERDE_ACENTO = new Color(0, 194, 177);
    public static final Color COLOR_GRIS_SUBTITULO = new Color(100, 120, 130);
    //Aqui creo los componentes que basicamente llevara todo
    private JLabel fondoVentana;//Aqui creo el JLabel que se comportara como contendor y fondo.
    public JPanel encabezado;//Aqui creo el JPanel que sera el encabezado
    private JPanel panelBienvenida;//Aqui creo el JPanel de bienbenida ejemplo:Bienbenido alejo! lifedoccare
    private JPanel panelSesionUsuario;//Aqui creo el JPanel que lelva el boton cerrar sesion y foto de perfil
    private PanelRound panelFotoPerfil;
    public JLabel labelFotoPerfil;//Aqui creo JLabel que llevara la foto de perfil
    public JButton btnCerrarSesion;//Aqui creo el boton cerrar sesion
    public JPanel cuerpo1;//Aqui creo el JPanel que va hacer el cuerpo1
    public JPanel cuerpo2;//Aqui creo el JPanel que va hacer el cuerpo2
    //Aqui creo los botones del Paciente
    public JButton btnMisCitas;
    public JButton btnHistorial;
    public JButton btnComentarios;
    public JButton btnNotificaciones;
    //Aqui creo todo para el apartado MisCitas
    public JPanel panelInfoCitas;
    public JButton btnAgendar;
    public ArrayList<JButton> listaBotonesReagendar;
    public ArrayList<JButton> listaBotonesCancelar;
    //Aqui creo todo para el apartado Historial
    public JButton btnHistorialMedico;
    public JButton btnHistorialCitas;
    public JButton btnDescargar;
    public JComboBox cmbFecha;
    public JComboBox cmbMedico;
    public JComboBox cmbEspecialidad;
    public JPanel panelHistorial;
    public JPanel panelFiltros;
    public JPanel panelListaHistorial;
    public JScrollPane scrollHistorial;
    public JPanel panelBotonesLaterales;
    //Aqui creo todo para el apartado Comentarios
    public JButton btnSugerencias;
    public JButton btnQuejas;
    public JButton btnForo;
    public JButton btnEnviar;
    public JTextField campoAsunto;
    public JTextArea areaDescripcion;
    public JScrollPane miCroll;
    public JPanel panelComentarios, panelComentarios1;

    //Aqui creo todo para el apartado notificaciones
    public JPanel panelContenidoNotificaciones;
    //Aqui ceo todo para el apartado AgendarCita
    public JButton btnOdontologia;
    public JButton btnDermatologia;
    public JButton btnMedicoGeneral;
    public ArrayList<JButton> listaBotonesMedicos;
    public JPanel panelSeleccionConsulta;
    //Aqui creo todo para la siguiente vista despues del apartado de agendar
    public JPanel panelCalendario;
    public CalendarPanel calendario;
    public JPanel panelHorarios;
    public JScrollPane scrollHorarios;

    public PacienteInterfaz(String nombreInterfaz) {
        super(nombreInterfaz);

        this.fondoVentana = new JLabel(new ImageIcon("Fondo1_watermark.jpeg"));
        this.fondoVentana.setOpaque(true);
        this.fondoVentana.setLayout(new BoxLayout(fondoVentana, BoxLayout.Y_AXIS));
        this.setContentPane(fondoVentana);

        this.encabezado = new JPanel();
        this.encabezado.setBorder(new EmptyBorder(40, 40, 0, 40));
        this.encabezado.setLayout(new BorderLayout());
        this.encabezado.setOpaque(false);

        this.cuerpo1 = new JPanel();
        this.cuerpo1.setLayout(new FlowLayout(FlowLayout.LEFT, 25, 10));
        this.cuerpo1.setOpaque(false);
        this.cuerpo1.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(40, 40, 0, 40),
                BorderFactory.createMatteBorder(0, 0, 2, 0, COLOR_AZUL_CORPORATIVO)));

        this.cuerpo2 = new JPanel();
        this.cuerpo2.setLayout(new BorderLayout());
        this.cuerpo2.setBorder(new EmptyBorder(0, 40, 20, 40));
        this.cuerpo2.setOpaque(false);
        this.cuerpo2.setPreferredSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));

        this.fondoVentana.add(encabezado);
        this.fondoVentana.add(cuerpo1);
        this.fondoVentana.add(cuerpo2);
    }

    public PacienteInterfaz(String nombreInterfaz, Paciente usuario) {
        super(nombreInterfaz);//Asigno nombre de la ventana
        this.usuario = usuario;
        //Fondo de toda la ventana
        this.fondoVentana = new JLabel(new ImageIcon("Fondo1_watermark.jpeg"));//Imagen de marca de agua
        this.fondoVentana.setOpaque(true);//Necesario para que se vea la imagen de fondo
        this.fondoVentana.setLayout(new BoxLayout(fondoVentana, BoxLayout.Y_AXIS));//Aqui asigno distribuccion vertical al fondo
        this.setContentPane(fondoVentana);//El fondo pasa a ser el contenedor principal del JFrame

        //Encabezado: bienvenida + sesion/perfil 
        this.encabezado = new JPanel();
        this.encabezado.setBorder(new EmptyBorder(40, 40, 0, 40));//Padding Arriba,Izquierda,Abajo,Derecha
        this.encabezado.setLayout(new BorderLayout());
        this.encabezado.setOpaque(false);

        this.panelBienvenida = new JPanel();
        this.panelBienvenida.setLayout(new BorderLayout());
        this.panelBienvenida.setOpaque(false);

        JLabel tituloBienvenida = new JLabel((usuario.getSexoBiologico().equals("Masculino") ? "Bienvenido, " : "Bienvenida, ")
                + this.usuario.getPrimerNombre() + "!");
        tituloBienvenida.setFont(new Font("arial", Font.BOLD, 30));

        this.panelBienvenida.add(tituloBienvenida, BorderLayout.NORTH);
        this.panelBienvenida.add(new Titulo("LifeDoc", "Care").getPanelTitulo(), BorderLayout.WEST);
        tituloBienvenida = null;

        this.panelSesionUsuario = new JPanel();
        this.panelSesionUsuario.setLayout(new FlowLayout());
        this.panelSesionUsuario.setOpaque(false);

        this.btnCerrarSesion = new JButton("Cerrar sesion", new ImageIcon("iconsP/arrow-left.png"));
        this.btnCerrarSesion.setBackground(Color.WHITE);
        this.btnCerrarSesion.setForeground(COLOR_AZUL_CORPORATIVO);
        this.btnCerrarSesion.setFont(new Font("arial", Font.BOLD, 15));

        Dimension tamanoFijo = new Dimension(64, 64);
        int radio = 100;
        this.panelFotoPerfil = new PanelRound();
        this.panelFotoPerfil.setLayout(new BorderLayout());
        this.panelFotoPerfil.setPreferredSize(tamanoFijo);
        this.panelFotoPerfil.setMaximumSize(tamanoFijo);
        this.panelFotoPerfil.setRoundTopLeft(radio);
        this.panelFotoPerfil.setRoundTopRight(radio);
        this.panelFotoPerfil.setRoundBottomLeft(radio);
        this.panelFotoPerfil.setRoundBottomRight(radio);
        ImageIcon imagen = new ImageIcon(this.usuario.getFotoPerfil());
        this.labelFotoPerfil = new JLabel();
        this.labelFotoPerfil.setPreferredSize(tamanoFijo);
        this.labelFotoPerfil.setMinimumSize(tamanoFijo);
        this.labelFotoPerfil.setMaximumSize(tamanoFijo);
        Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(
                64, 64,
                Image.SCALE_DEFAULT));
        this.labelFotoPerfil.setIcon(icono);
        this.labelFotoPerfil.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        this.panelFotoPerfil.add(labelFotoPerfil, BorderLayout.CENTER);
        this.panelSesionUsuario.add(btnCerrarSesion);
        this.panelSesionUsuario.add(panelFotoPerfil);

        this.encabezado.add(panelBienvenida, BorderLayout.WEST);
        this.encabezado.add(panelSesionUsuario, BorderLayout.EAST);

        this.cuerpo1 = new JPanel();
        this.cuerpo1.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.cuerpo1.setOpaque(false);
        this.cuerpo1.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(40, 40, 0, 40),
                BorderFactory.createMatteBorder(0, 0, 2, 0, COLOR_AZUL_CORPORATIVO)));
        agregarBotonesMenuPaciente();

        this.cuerpo2 = new JPanel();
        this.cuerpo2.setLayout(new BorderLayout());
        this.cuerpo2.setBorder(new EmptyBorder(0, 40, 20, 40));
        this.cuerpo2.setOpaque(false);
        this.cuerpo2.setPreferredSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));

        this.fondoVentana.add(encabezado);
        this.fondoVentana.add(cuerpo1);
        this.fondoVentana.add(cuerpo2);

        this.panelInfoCitas = new JPanel();
        this.panelInfoCitas.setLayout(new BoxLayout(panelInfoCitas, BoxLayout.Y_AXIS));
        this.panelInfoCitas.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO, 2),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)
                ));
        this.panelInfoCitas.setOpaque(false);

        this.btnAgendar = new JButton("️Agendar una cita", new ImageIcon("iconsP/heart.png"));
        MetodosPublicos.estilizarBoton(btnAgendar, (byte) 3);
        this.listaBotonesCancelar = new ArrayList<JButton>();
        this.listaBotonesReagendar = new ArrayList<JButton>();

        this.panelHistorial = new JPanel();
        this.panelHistorial.setLayout(new BorderLayout());
        this.panelHistorial.setBorder(BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO, 2));
        this.panelHistorial.setOpaque(false);

        this.panelFiltros = new JPanel();
        this.panelFiltros.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 20));
        this.panelFiltros.setOpaque(false);
        this.panelFiltros.setPreferredSize(new Dimension(0, 100));
        this.cmbFecha = new JComboBox();
        this.cmbMedico = new JComboBox();
        this.cmbEspecialidad = new JComboBox();
        MetodosPublicos.estilizarComboBox(cmbFecha);
        MetodosPublicos.estilizarComboBox(cmbMedico);
        MetodosPublicos.estilizarComboBox(cmbEspecialidad);
        JLabel lblFiltrar = new JLabel("Filtrar por");
        lblFiltrar.setFont(new Font("Arial", Font.PLAIN, 18));
        lblFiltrar.setForeground(new Color(80, 80, 80));
        this.panelFiltros.add(lblFiltrar);
        this.panelFiltros.add(this.cmbFecha);
        this.panelFiltros.add(this.cmbMedico);
        this.panelFiltros.add(this.cmbEspecialidad);

        this.panelListaHistorial = new JPanel();
        this.panelListaHistorial.setLayout(new BoxLayout(panelListaHistorial, BoxLayout.Y_AXIS));
        this.panelListaHistorial.setPreferredSize(new Dimension(0, 470));
        this.panelListaHistorial.setOpaque(false);

        this.panelBotonesLaterales = new JPanel();
        this.panelBotonesLaterales.setBorder(new EmptyBorder(105, 0, 0, 0));
        this.panelBotonesLaterales.setLayout(new BoxLayout(panelBotonesLaterales, BoxLayout.Y_AXIS));
        this.panelBotonesLaterales.setPreferredSize(new Dimension(400, 0));
        this.panelBotonesLaterales.setOpaque(false);

        this.btnHistorialMedico = new JButton("Historial Medico ", new ImageIcon("iconsP/avatar.png"));
        this.btnHistorialCitas = new JButton("Historial de Citas", new ImageIcon("iconsP/friends.png"));
        this.btnDescargar = new JButton("Descargar Historial Medico", new ImageIcon("iconsP/descargar.png"));
        MetodosPublicos.estilizarBoton(btnDescargar, (byte) 5);
        MetodosPublicos.estilizarBoton(btnHistorialMedico, (byte) 2);
        MetodosPublicos.estilizarBoton(btnHistorialCitas, (byte) 2);

        this.campoAsunto = new JTextField();
        this.campoAsunto.setFont(new Font("Arial", Font.PLAIN, 18));
        this.campoAsunto.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        this.campoAsunto.setBorder(BorderFactory.createLineBorder(COLOR_VERDE_ACENTO));

        this.areaDescripcion = new JTextArea();
        this.areaDescripcion.setFont(new Font("Arial", Font.PLAIN, 18));
        this.areaDescripcion.setLineWrap(true);
        this.areaDescripcion.setWrapStyleWord(true);
        this.areaDescripcion.setBorder(BorderFactory.createLineBorder(COLOR_VERDE_ACENTO));
        this.miCroll = new JScrollPane(areaDescripcion);
        this.miCroll.setBorder(BorderFactory.createLineBorder(COLOR_VERDE_ACENTO));

        this.panelComentarios = new JPanel();
        this.panelComentarios1 = new JPanel();
        this.panelComentarios.setLayout(new BoxLayout(panelComentarios, BoxLayout.Y_AXIS));
        this.panelComentarios.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO, 2),
                BorderFactory.createEmptyBorder(5, 30, 5, 30)
        ));
        this.panelComentarios.setOpaque(false);
        this.panelComentarios1.setLayout(new BoxLayout(panelComentarios1, BoxLayout.Y_AXIS));
        this.panelComentarios1.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        this.panelComentarios1.setOpaque(false);
        this.panelComentarios1.setBorder(null);
        this.btnSugerencias = new JButton("Sugerencias ", new ImageIcon("iconsP/happy-face.png"));
        this.btnQuejas = new JButton("Quejas ", new ImageIcon("iconsP/quejas.png"));
        this.btnForo = new JButton("Foro ", new ImageIcon("iconsP/communication.png"));
        this.btnEnviar = new JButton("Enviar ", new ImageIcon("iconsP/descargar.png"));
        MetodosPublicos.estilizarBoton(btnQuejas, (byte) 4);
        MetodosPublicos.estilizarBoton(btnSugerencias, (byte) 4);
        MetodosPublicos.estilizarBoton(btnForo, (byte) 4);
        MetodosPublicos.estilizarBoton(btnEnviar, (byte) 5);

        this.panelContenidoNotificaciones = new JPanel();
        this.panelContenidoNotificaciones.setLayout(new BoxLayout(panelContenidoNotificaciones, BoxLayout.Y_AXIS));
        this.panelContenidoNotificaciones.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO, 2),
                        BorderFactory.createEmptyBorder(0, 30, 5, 30)
                ));
        this.panelContenidoNotificaciones.setOpaque(false);

        this.btnOdontologia = new JButton("Odontologia", new ImageIcon("iconsP/heart.png"));
        this.btnDermatologia = new JButton("Dermatologia", new ImageIcon("iconsP/heart.png"));
        this.btnMedicoGeneral = new JButton("Medico General", new ImageIcon("iconsP/heart.png"));
        MetodosPublicos.estilizarBoton(btnOdontologia, (byte) 4);
        MetodosPublicos.estilizarBoton(btnDermatologia, (byte) 4);
        MetodosPublicos.estilizarBoton(btnMedicoGeneral, (byte) 4);
        this.listaBotonesMedicos = new ArrayList<JButton>();

        this.panelSeleccionConsulta = new JPanel();
        this.panelSeleccionConsulta.setLayout(new BoxLayout(panelSeleccionConsulta, BoxLayout.Y_AXIS));
        this.panelSeleccionConsulta.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO, 2),
                        BorderFactory.createEmptyBorder(50, 60, 50, 60)
                ));
        this.panelSeleccionConsulta.setOpaque(false);

        this.panelCalendario = new JPanel();
        this.panelCalendario.setLayout(new BoxLayout(panelCalendario, BoxLayout.Y_AXIS));
        this.panelCalendario.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO, 3),
                        BorderFactory.createEmptyBorder(20, 20, 20, 20)
                ));

        this.panelCalendario.setPreferredSize(new Dimension(750, 0));
        this.panelCalendario.setOpaque(false);

        this.panelHorarios = new JPanel();
        this.panelHorarios.setLayout(new BoxLayout(panelHorarios, BoxLayout.Y_AXIS));
        this.panelHorarios.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO, 2),
                        BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        this.panelHorarios.setOpaque(false);
        this.scrollHorarios = new JScrollPane(panelHorarios);
        this.scrollHorarios.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.scrollHorarios.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.scrollHorarios.setOpaque(false);
        this.scrollHorarios.getViewport().setOpaque(false);
        this.scrollHorarios.setBorder(null);
        this.scrollHorarios.setBorder(BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO));
    }

    //Aqui creo el metodo para habilitar o desabilitar botones del paciente.
    public void habilitarBotonesMenu(JButton botonActivo) {
        botonActivo.setEnabled(false);
        if (botonActivo != btnMisCitas && !btnMisCitas.isEnabled()) {
            this.btnMisCitas.setEnabled(true);
        }
        if (botonActivo != btnHistorial && !btnHistorial.isEnabled()) {
            this.btnHistorial.setEnabled(true);
        }
        if (botonActivo != btnComentarios && !btnComentarios.isEnabled()) {
            this.btnComentarios.setEnabled(true);
        }
        if (botonActivo != btnNotificaciones && !btnNotificaciones.isEnabled()) {
            this.btnNotificaciones.setEnabled(true);
        }
    }

    //Aqui creo el metodo que nos va a servir para agregar un JButton al cuerpo1 que seria el panel para los botones
    public void agregarBotonCuerpo1(JButton boton) {
        MetodosPublicos.estilizarBoton(boton, (byte) 1);
        this.cuerpo1.add(boton);
        this.cuerpo1.add(Box.createHorizontalStrut(5));
        MetodosPublicos.refrescarVentana(cuerpo1);
    }

    //Aqui creo el metodo que nos va a permitir agregar cualquier tipo de 
    //objeto que sea creado con una clase que hereda de JComponent para agregar al panel cuerpo1
    public void agregarComponenteCuerpo1(JComponent componente) {
        this.cuerpo1.add(componente);
        MetodosPublicos.refrescarVentana(cuerpo1);
    }

    //Aqui creo metodo que me permitira cargar al cuerpo1 los botones del paciente
    public void agregarBotonesMenuPaciente() {
        this.btnMisCitas = new JButton("Mis citas", new ImageIcon("iconsP/heart.png"));
        this.btnHistorial = new JButton("Historial", new ImageIcon("iconsP/file.png"));
        this.btnComentarios = new JButton("️Comentarios", new ImageIcon("iconsP/chat-bubble.png"));
        this.btnNotificaciones = new JButton("️Notificaciones", new ImageIcon("iconsP/bell.png"));
        agregarBotonCuerpo1(btnMisCitas);
        agregarBotonCuerpo1(btnHistorial);
        agregarBotonCuerpo1(btnComentarios);
        agregarBotonCuerpo1(btnNotificaciones);
        MetodosPublicos.refrescarVentana(cuerpo1);
    }

    //Aqui creo el metodo que me permitiria vizualizar en el JPanel cuerpo2 el apartado de mis citas
    public void mostrarVistaMisCitas() {
        MetodosPublicos.vaciarPanel(cuerpo2);
        MetodosPublicos.vaciarPanel(panelInfoCitas);
        this.cuerpo2.setLayout(new BorderLayout(20, 20));//Esta vista necesita gap entre sus 4 zonas
        this.cuerpo2.setBorder(new EmptyBorder(5, 40, 20, 40));//Padding propio de esta vista

        JPanel panelAgenda = new JPanel();
        panelAgenda.setLayout(new BoxLayout(panelAgenda, BoxLayout.Y_AXIS));
        panelAgenda.setPreferredSize(new Dimension(600, 0));
        panelAgenda.setOpaque(false);
        panelAgenda.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO, 2),
                BorderFactory.createEmptyBorder(110, 0, 95, 0)));

        JLabel lblTituloAgenda = new JLabel("¡Agenda una cita con nosotros!");
        lblTituloAgenda.setFont(new Font("arial", Font.BOLD, 20));
        lblTituloAgenda.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTituloAgenda.setForeground(COLOR_AZUL_CORPORATIVO);

        JLabel lblSubAgenda = new JLabel("No dejes tu salud a último momento.");
        lblSubAgenda.setFont(new Font("arial", Font.BOLD, 20));
        lblSubAgenda.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblSubAgenda.setForeground(COLOR_GRIS_SUBTITULO);

        panelAgenda.add(lblTituloAgenda);
        panelAgenda.add(lblSubAgenda);
        panelAgenda.add(btnAgendar);
        lblTituloAgenda = null;//Ya quedaron dentro de panelAgenda libero las referencias locales
        lblSubAgenda = null;

        JPanel panelContacto = new JPanel();
        panelContacto.setLayout(new BoxLayout(panelContacto, BoxLayout.Y_AXIS));
        panelContacto.setOpaque(false);
        panelContacto.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO, 2),
                BorderFactory.createEmptyBorder(40, 40, 0, 0)));
        panelContacto.setPreferredSize(new Dimension(Short.MAX_VALUE, 200));

        JLabel lblContactoTitulo = new JLabel("Si tienes dificultades para agendar tu cita, ¡contáctanos! ", new ImageIcon("iconsP/friends.png"), JLabel.CENTER);
        lblContactoTitulo.setFont(new Font("arial", Font.BOLD, 28));
        lblContactoTitulo.setForeground(COLOR_AZUL_CORPORATIVO);
        lblContactoTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblContactoSub = new JLabel("Llama al número de este operario para que podamos ayudarte:");
        lblContactoSub.setFont(new Font("arial", Font.BOLD, 20));
        lblContactoSub.setForeground(COLOR_AZUL_CORPORATIVO);
        lblContactoSub.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblTelefono = new JLabel("+57 316 127 3588", new ImageIcon("iconsP/phone-call.png"), JLabel.CENTER);
        lblTelefono.setFont(new Font("arial", Font.BOLD, 30));
        lblTelefono.setForeground(COLOR_AZUL_CORPORATIVO);
        lblTelefono.setAlignmentX(Component.LEFT_ALIGNMENT);

        panelContacto.add(lblContactoTitulo);
        panelContacto.add(lblContactoSub);
        panelContacto.add(lblTelefono);
        lblContactoTitulo = null;//ya estan dentro de panelContacto entonces libero memoria
        lblContactoSub = null;//Mismo criterio ya estan dentro de panelContacto entonces libero memoria
        lblTelefono = null;//Mismo criterio ya estan dentro de panelContacto entonces libero memoria

        JLabel labelCitas = new JLabel("Citas programadas Vigentes");
        labelCitas.setFont(new Font("arial", Font.BOLD, 28));
        labelCitas.setForeground(COLOR_AZUL_CORPORATIVO);

        JScrollPane scrollCitas = new JScrollPane(panelInfoCitas);
        scrollCitas.setPreferredSize(new Dimension(600, 345));
        scrollCitas.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollCitas.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollCitas.setBorder(BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO, 2));
        scrollCitas.setOpaque(false);
        scrollCitas.getViewport().setOpaque(false);

        this.cuerpo2.add(labelCitas, BorderLayout.NORTH);
        this.cuerpo2.add(panelAgenda, BorderLayout.EAST);
        this.cuerpo2.add(scrollCitas, BorderLayout.CENTER);
        this.cuerpo2.add(panelContacto, BorderLayout.SOUTH);
        labelCitas = null;//Ya quedo agregado al cuerpo2 entonces libero memoria 
        MetodosPublicos.refrescarVentana(cuerpo2);
        MetodosPublicos.refrescarVentana(panelInfoCitas);
    }

    //Aqui armo la vista del histial vacia
    private void prepararVistaBaseHistorial() {
        MetodosPublicos.vaciarPanel(cuerpo2);
        MetodosPublicos.vaciarPanel(panelBotonesLaterales);
        MetodosPublicos.vaciarPanel(panelHistorial);

        this.cuerpo2.setLayout(new BorderLayout());
        this.cuerpo2.setBorder(new EmptyBorder(40, 40, 40, 40)); // Padding propio de esta vista

        this.panelBotonesLaterales.add(btnHistorialMedico);
        this.panelBotonesLaterales.add(Box.createRigidArea(new Dimension(0, 30)));
        this.panelBotonesLaterales.add(btnHistorialCitas);

        this.cuerpo2.add(panelBotonesLaterales, BorderLayout.WEST);
        this.cuerpo2.add(panelHistorial, BorderLayout.CENTER);

        MetodosPublicos.refrescarVentana(cuerpo2);
        MetodosPublicos.refrescarVentana(panelBotonesLaterales);
        MetodosPublicos.refrescarVentana(panelHistorial);
    }

    //Aqui muestro la vista del historial
    public void mostrarVistaHistorial() {
        prepararVistaBaseHistorial();
        MetodosPublicos.vaciarPanel(panelHistorial);
        MetodosPublicos.vaciarPanel(panelListaHistorial);

        this.scrollHistorial = new JScrollPane(panelListaHistorial);
        this.scrollHistorial.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.scrollHistorial.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.scrollHistorial.setOpaque(false);
        this.scrollHistorial.getViewport().setOpaque(false);
        this.scrollHistorial.setBorder(null);

        this.panelHistorial.add(panelFiltros, BorderLayout.NORTH);
        this.panelHistorial.add(scrollHistorial, BorderLayout.CENTER);

        MetodosPublicos.refrescarVentana(panelHistorial);
        MetodosPublicos.refrescarVentana(panelListaHistorial);
    }

    //cargar el panel historial con el historial
    public void mostrarVistaHistorialConHistorial(String historial, String nombre, String edad) {
        MetodosPublicos.vaciarPanel(panelHistorial);

        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));
        panelSuperior.setOpaque(false);
        JPanel panelLife = new Titulo("Lifedoc ", "Care", 30).getPanelTitulo();
        panelLife.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel lNombre = new JLabel("Nombre :" + nombre);
        lNombre.setOpaque(false);
        lNombre.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel lEdad = new JLabel("Edad: " + edad);
        lEdad.setOpaque(false);
        lEdad.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelSuperior.add(panelLife);
        panelSuperior.add(lNombre);
        panelSuperior.add(lEdad);

        JTextArea miarea = new JTextArea(historial);
        miarea.setOpaque(false);
        miarea.setEditable(false);
        miarea.setFont(new Font("Segoe UI", Font.BOLD, 20));
        this.scrollHistorial = new JScrollPane(miarea);
        this.scrollHistorial.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.scrollHistorial.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.scrollHistorial.setOpaque(false);
        this.scrollHistorial.getViewport().setOpaque(false);
        this.scrollHistorial.setBorder(null);

        this.panelHistorial.add(panelSuperior, BorderLayout.NORTH);
        this.panelHistorial.add(scrollHistorial, BorderLayout.CENTER);
        this.panelHistorial.add(btnDescargar, BorderLayout.SOUTH);

        MetodosPublicos.refrescarVentana(panelHistorial);
    }

    //Aqui creo el metodo que me permitiria contruer el aprtado de comentarios para quejas y sujerencias
    public void construirFormularioComentario() {
        MetodosPublicos.vaciarPanel(panelComentarios);
        JLabel lblAsunto = new JLabel("Asunto *");
        lblAsunto.setFont(new Font("Arial", Font.BOLD, 22));
        lblAsunto.setForeground(COLOR_AZUL_CORPORATIVO);
        lblAsunto.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblAsunto.setBorder(new EmptyBorder(20, 0, 0, 0));

        JLabel lblDescripcion = new JLabel("Descripción *");
        lblDescripcion.setFont(new Font("Arial", Font.BOLD, 22));
        lblDescripcion.setForeground(COLOR_AZUL_CORPORATIVO);
        lblDescripcion.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblDescripcion.setBorder(new EmptyBorder(20, 0, 0, 0));

        this.panelComentarios.add(lblAsunto);
        this.panelComentarios.add(campoAsunto);
        this.panelComentarios.add(lblDescripcion);
        this.panelComentarios.add(miCroll);
        this.panelComentarios.add(Box.createRigidArea(new Dimension(0, 20)));
        this.panelComentarios.add(btnEnviar);
        lblAsunto = null;//Ya quedaron dentro de panelComentarios
        lblDescripcion = null;

        MetodosPublicos.refrescarVentana(panelComentarios);
    }

    public void mostarPanelComentarioVacio() {
        MetodosPublicos.vaciarPanel(panelComentarios1);
        MetodosPublicos.vaciarPanel(panelComentarios);
        JScrollPane scrollComentarios = new JScrollPane(panelComentarios1);
        scrollComentarios.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollComentarios.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollComentarios.setOpaque(false);
        scrollComentarios.getViewport().setOpaque(false);
        scrollComentarios.setBorder(null);
        this.panelComentarios.add(scrollComentarios);
        MetodosPublicos.refrescarVentana(panelComentarios1);
        MetodosPublicos.refrescarVentana(panelComentarios);
    }

    //Aqui creo el metodo que me permitiria vizualizar en el JPanel cuerpo2 el apartado de comentarios
    public void mostrarVistaComentarios() {
        MetodosPublicos.vaciarPanel(panelBotonesLaterales);
        MetodosPublicos.vaciarPanel(panelComentarios);
        MetodosPublicos.vaciarPanel(cuerpo2);

        construirFormularioComentario();
        this.panelBotonesLaterales.add(btnSugerencias);
        this.panelBotonesLaterales.add(Box.createRigidArea(new Dimension(0, 30)));
        this.panelBotonesLaterales.add(btnQuejas);
        this.panelBotonesLaterales.add(Box.createRigidArea(new Dimension(0, 30)));
        this.panelBotonesLaterales.add(btnForo);

        this.cuerpo2.setLayout(new BorderLayout());//Misma distribucion que historial: botones | panel
        this.cuerpo2.setBorder(new EmptyBorder(40, 40, 40, 40));//Padding propio de esta vista
        this.cuerpo2.add(panelBotonesLaterales, BorderLayout.WEST);
        this.cuerpo2.add(panelComentarios, BorderLayout.CENTER);

        MetodosPublicos.refrescarVentana(panelBotonesLaterales);
        MetodosPublicos.refrescarVentana(panelComentarios);
        MetodosPublicos.refrescarVentana(cuerpo2);
    }

    //Aqui creo el metodo que me permitiria agregar componentes al panel comentarios
    public void agregarAlPanelComentarios(String tipoMensaje, String asunto, String nombreUsu, String descripcion) {
        JPanel c = new JPanel();
        c.setBackground(Color.WHITE);
        c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
        c.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO, 2),
                new EmptyBorder(15, 20, 15, 20)
        ));
        c.setMaximumSize(new Dimension(Integer.MAX_VALUE, 170));

        // Encabezado
        JLabel titulo = new JLabel(asunto);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setForeground(new Color(33, 33, 33));
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel informacion = new JLabel(tipoMensaje + " • " + nombreUsu);
        informacion.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        informacion.setForeground(new Color(120, 120, 120));
        informacion.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Descripción
        JTextArea descripcionText = new JTextArea(descripcion);
        descripcionText.setEditable(false);
        descripcionText.setLineWrap(true);
        descripcionText.setWrapStyleWord(true);
        descripcionText.setOpaque(false);
        descripcionText.setFocusable(false);
        descripcionText.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        descripcionText.setForeground(new Color(60, 60, 60));
        descripcionText.setAlignmentX(Component.LEFT_ALIGNMENT);

        c.add(titulo);
        c.add(Box.createVerticalStrut(5));
        c.add(informacion);
        c.add(Box.createVerticalStrut(12));
        c.add(descripcionText);

        panelComentarios1.add(Box.createVerticalStrut(15));
        panelComentarios1.add(c);

        MetodosPublicos.refrescarVentana(panelComentarios1);
        MetodosPublicos.refrescarVentana(panelComentarios);
    }

    //Aqui creo el metodo que permitiria vizualsar en el panel cuerpo2 el apartado de notificaciones
    public void mostrarVistaNotificaciones() {
        MetodosPublicos.vaciarPanel(cuerpo2);
        this.cuerpo2.setLayout(new BorderLayout());
        this.cuerpo2.setBorder(new EmptyBorder(40, 40, 40, 40));//Padding propio de esta vista
        JScrollPane scrollNotificaciones = new JScrollPane(panelContenidoNotificaciones);
        scrollNotificaciones.setOpaque(false);
        scrollNotificaciones.getViewport().setOpaque(false);
        this.cuerpo2.add(scrollNotificaciones);

        MetodosPublicos.refrescarVentana(panelContenidoNotificaciones);
        MetodosPublicos.refrescarVentana(cuerpo2);
    }

    public void agregarNotificaciones(String encabezado, String descripcion) {
        //Tarjetita que es la contenedora con bordes redondeados y borde azul corporativo
        PanelRound tarjeta = new PanelRound();
        tarjeta.setRoundTopLeft(18);
        tarjeta.setRoundTopRight(18);
        tarjeta.setRoundBottomLeft(18);
        tarjeta.setRoundBottomRight(18);
        tarjeta.setLayout(new BorderLayout(0, 0));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO, 2, true),
                new EmptyBorder(16, 18, 16, 20)
        ));
        tarjeta.setMaximumSize(new Dimension(Integer.MAX_VALUE, 105));
        tarjeta.setAlignmentX(Component.LEFT_ALIGNMENT);

        //Icono dentro de un circulo de acento
        PanelRound circuloIcono = new PanelRound();
        circuloIcono.setRoundTopLeft(100);
        circuloIcono.setRoundTopRight(100);
        circuloIcono.setRoundBottomLeft(100);
        circuloIcono.setRoundBottomRight(100);
        circuloIcono.setBackground(new Color(0, 79, 124, 25)); //azul corporativo translucido
        circuloIcono.setLayout(new GridBagLayout());
        Dimension tamanoCirculo = new Dimension(50, 50);
        circuloIcono.setPreferredSize(tamanoCirculo);
        circuloIcono.setMinimumSize(tamanoCirculo);
        circuloIcono.setMaximumSize(tamanoCirculo);

        // Elegimos un icono al azar entre los 4 disponibles.
        // ThreadLocalRandom.nextInt(1, 5) genera un numero entre 1 y 4 (el limite superior es exclusivo).
        int azar = java.util.concurrent.ThreadLocalRandom.current().nextInt(1, 5);
        String rutaIcono;
        switch (azar) {
            case 1:
                rutaIcono = "iconsP/notificacionesAzar/quality.png";
                break;
            case 2:
                rutaIcono = "iconsP/notificacionesAzar/verified.png";
                break;
            case 3:
                rutaIcono = "iconsP/notificacionesAzar/verify.png";
                break;
            default:
                rutaIcono = "iconsP/notificacionesAzar/excited.png";
                break;
        }

        ImageIcon iconoOriginal = new ImageIcon(rutaIcono);
        Image iconoEscalado = iconoOriginal.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        JLabel icono = new JLabel(new ImageIcon(iconoEscalado));
        circuloIcono.add(icono);

        JPanel panelIcono = new JPanel(new GridBagLayout());
        panelIcono.setOpaque(false);
        panelIcono.setBorder(new EmptyBorder(0, 0, 0, 16));
        panelIcono.add(circuloIcono);

        //Fila superior: titulo + hora
        JPanel filaSuperior = new JPanel(new BorderLayout());
        filaSuperior.setOpaque(false);
        filaSuperior.setAlignmentX(Component.LEFT_ALIGNMENT);
        filaSuperior.setMaximumSize(new Dimension(Integer.MAX_VALUE, 22));

        JLabel lblTitulo = new JLabel(encabezado);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setForeground(new Color(28, 28, 28));

        JLabel lblHora = new JLabel(java.time.LocalTime.now().format(
                java.time.format.DateTimeFormatter.ofPattern("HH:mm")));
        lblHora.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblHora.setForeground(new Color(160, 160, 160));

        filaSuperior.add(lblTitulo, BorderLayout.WEST);
        filaSuperior.add(lblHora, BorderLayout.EAST);

        //Descripcion
        JTextArea txtDescripcion = new JTextArea(descripcion);
        txtDescripcion.setEditable(false);
        txtDescripcion.setOpaque(false);
        txtDescripcion.setFocusable(false);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtDescripcion.setForeground(new Color(105, 105, 105));
        txtDescripcion.setAlignmentX(Component.LEFT_ALIGNMENT);

        //Franja de acento inferior detalle con un toque de de modernidad tipo "estado"
        JPanel franjaEstado = new JPanel();
        franjaEstado.setOpaque(false);
        franjaEstado.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        franjaEstado.setAlignmentX(Component.LEFT_ALIGNMENT);
        franjaEstado.setBorder(new EmptyBorder(8, 0, 0, 0));

        PanelRound puntoEstado = new PanelRound();
        puntoEstado.setRoundTopLeft(100);
        puntoEstado.setRoundTopRight(100);
        puntoEstado.setRoundBottomLeft(100);
        puntoEstado.setRoundBottomRight(100);
        puntoEstado.setBackground(COLOR_VERDE_ACENTO);
        Dimension tamanoPunto = new Dimension(7, 7);
        puntoEstado.setPreferredSize(tamanoPunto);
        puntoEstado.setMinimumSize(tamanoPunto);
        puntoEstado.setMaximumSize(tamanoPunto);

        JLabel lblEstado = new JLabel("  Nueva notificacion");
        lblEstado.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblEstado.setForeground(COLOR_VERDE_ACENTO);

        franjaEstado.add(puntoEstado);
        franjaEstado.add(lblEstado);

        //Contenido central
        JPanel contenido = new JPanel();
        contenido.setOpaque(false);
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.add(filaSuperior);
        contenido.add(Box.createVerticalStrut(6));
        contenido.add(txtDescripcion);
        contenido.add(franjaEstado);

        //Ensamblado final
        tarjeta.add(panelIcono, BorderLayout.WEST);
        tarjeta.add(contenido, BorderLayout.CENTER);

        panelContenidoNotificaciones.add(Box.createVerticalStrut(14));
        panelContenidoNotificaciones.add(tarjeta);
        MetodosPublicos.refrescarVentana(panelContenidoNotificaciones);
    }

    //Aqui creo el metodo que me permitiria vizualisar el apartado para escoger el tipo de consulta
    public void mostrarVistaTipoConsulta(Titulo titulo) {
        MetodosPublicos.vaciarPanel(cuerpo2);
        MetodosPublicos.vaciarPanel(panelSeleccionConsulta);

        JLabel descripcion = new JLabel("Selecciona Tipo De Consulta Que Deseas Agendar");
        descripcion.setFont(new Font("Arial", Font.BOLD, 22));
        descripcion.setForeground(Color.BLACK);
        descripcion.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel panelTitulo = titulo.getPanelTitulo();
        panelTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        MetodosPublicos.prepararBotonTarjeta(btnOdontologia, 380, 55);
        MetodosPublicos.prepararBotonTarjeta(btnDermatologia, 380, 55);
        MetodosPublicos.prepararBotonTarjeta(btnMedicoGeneral, 380, 55);

        this.panelSeleccionConsulta.add(panelTitulo);
        this.panelSeleccionConsulta.add(descripcion);
        this.panelSeleccionConsulta.add(Box.createRigidArea(new Dimension(0, 25)));
        this.panelSeleccionConsulta.add(btnOdontologia);
        this.panelSeleccionConsulta.add(Box.createRigidArea(new Dimension(0, 15)));
        this.panelSeleccionConsulta.add(btnDermatologia);
        this.panelSeleccionConsulta.add(Box.createRigidArea(new Dimension(0, 15)));
        this.panelSeleccionConsulta.add(btnMedicoGeneral);
        descripcion = null;//Ya quedo agregado a panelSeleccionConsulta liberio memoria
        panelTitulo = null;//mismo criterio ya quedo agregado a panelSeleccionConsulta liberio memoria

        this.cuerpo2.setLayout(new GridBagLayout());
        this.cuerpo2.setBorder(new EmptyBorder(40, 40, 40, 40));
        this.cuerpo2.add(panelSeleccionConsulta, new GridBagConstraints());

        MetodosPublicos.refrescarVentana(panelSeleccionConsulta);
        MetodosPublicos.refrescarVentana(cuerpo2);
    }

    public void mostrarVistaSeleccionMedico(String[] medicos) {
        MetodosPublicos.vaciarPanel(cuerpo2);
        MetodosPublicos.vaciarPanel(panelSeleccionConsulta);

        Titulo titulo = new Titulo("Agendamiento de ", "Cita");
        JLabel descripcion = new JLabel("Selecciona El Medico Con El Que Deseas Agendar");
        descripcion.setFont(new Font("Arial", Font.BOLD, 22));
        descripcion.setForeground(Color.BLACK);
        descripcion.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel panelTitulo = titulo.getPanelTitulo();
        panelTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.panelSeleccionConsulta.add(panelTitulo);
        this.panelSeleccionConsulta.add(descripcion);
        this.panelSeleccionConsulta.add(Box.createRigidArea(new Dimension(0, 25)));
        titulo = null;//Ya quedo agregado a panelSeleccionConsulta por ende lo puedo elimianr para ahorrar memoria o algo asi por el estilo creo
        descripcion = null;//Ya quedo agregado a panelSeleccionConsulta por ende lo puedo eliminar para ahorrar memoria o algo asi por el estilo creo
        panelTitulo = null;//Ya quedo agregado a panelSeleccionConsulta por ende lo puedo eliminar para ahorrar memoria o algo asi por el estilo creo

        for (String nombreMedico : medicos) {
            JButton botonMedico = new JButton(nombreMedico, new ImageIcon("iconsP/avatar.png"));//Creo boton con el nombre del medico
            MetodosPublicos.estilizarBoton(botonMedico, (byte) 4);//Agrego estilo al boton
            MetodosPublicos.prepararBotonTarjeta(botonMedico, 380, 55);//Agrego tamano estable 
            this.listaBotonesMedicos.add(botonMedico);
            this.panelSeleccionConsulta.add(botonMedico);
            this.panelSeleccionConsulta.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        this.cuerpo2.setLayout(new GridBagLayout());
        this.cuerpo2.setBorder(new EmptyBorder(40, 40, 40, 40));
        this.cuerpo2.add(panelSeleccionConsulta, new GridBagConstraints());
        MetodosPublicos.refrescarVentana(panelSeleccionConsulta);
        MetodosPublicos.refrescarVentana(cuerpo2);
    }

    //Aqui creo el metodo que me permitiria visualizar el apartado para terminar de agendar una cita 
    public void mostrarVistaAgendamientoCita(Titulo tituloAgendaMientoCita) {
        MetodosPublicos.vaciarPanel(cuerpo2);
        MetodosPublicos.vaciarPanel(panelCalendario);
        MetodosPublicos.vaciarPanel(panelHorarios);

        this.cuerpo2.setLayout(new BorderLayout(5, 0));//Gap horizontal entre las dos tarjetas
        this.cuerpo2.setBorder(new EmptyBorder(20, 40, 40, 40));//Padding propio de esta vista
        JLabel descrip1 = new JLabel("Selecciona Fecha y Horario en el");
        descrip1.setFont(new Font("Arial", Font.BOLD, 20));
        JLabel descrip2 = new JLabel("que deseas agendar");
        descrip2.setFont(new Font("Arial", Font.BOLD, 20));
        JLabel tituloHorarioDisponibles = new JLabel("Horarios Disponibles");
        tituloHorarioDisponibles.setFont(new Font("Arial", Font.BOLD, 25));
        tituloHorarioDisponibles.setForeground(COLOR_AZUL_CORPORATIVO);

        int añoActual = Year.now().getValue();
        DatePickerSettings settings = new DatePickerSettings();
        settings.setDefaultYearMonth(YearMonth.now());
        settings.setVisibleNextYearButton(false);
        settings.setVisiblePreviousYearButton(false);

        //Agrandar el calendario
        settings.setSizeDatePanelMinimumHeight((int) (settings.getSizeDatePanelMinimumHeight() * 2.5));
        settings.setSizeDatePanelMinimumWidth((int) (settings.getSizeDatePanelMinimumWidth() * 2.5));

        //Aqui esta la logica que me permitiria bloquear los dias que no estan disponibles
        this.calendario = new CalendarPanel(settings);
        settings.setVetoPolicy((LocalDate fecha) -> {
            return !fecha.isBefore(LocalDate.now())
                    && fecha.getYear() == añoActual;
        });

        // Fondo transparente del calendario
        this.calendario.setOpaque(false);
        this.calendario.setBackground(new Color(0, 0, 0, 0));

        // Layout vertical para que los JLabel no se corran horizontalmente
        this.panelCalendario.setLayout(new BoxLayout(panelCalendario, BoxLayout.Y_AXIS));

        // Alinear cada componente a la izquierda
        tituloAgendaMientoCita.getPanelTitulo().setAlignmentX(Component.LEFT_ALIGNMENT);
        descrip1.setAlignmentX(Component.LEFT_ALIGNMENT);
        descrip2.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.calendario.setAlignmentX(Component.LEFT_ALIGNMENT);

        this.panelCalendario.add(tituloAgendaMientoCita.getPanelTitulo());
        this.panelCalendario.add(descrip1);
        this.panelCalendario.add(descrip2);
        this.panelCalendario.add(calendario);
        this.panelHorarios.add(tituloHorarioDisponibles);
        this.scrollHorarios.setViewportView(panelHorarios);
        this.cuerpo2.add(panelCalendario, BorderLayout.WEST);
        this.cuerpo2.add(scrollHorarios, BorderLayout.CENTER);
        descrip1 = null;
        descrip2 = null;
        tituloHorarioDisponibles = null;
        tituloAgendaMientoCita = null;
        settings = null;
        MetodosPublicos.refrescarVentana(panelCalendario);
        MetodosPublicos.refrescarVentana(panelHorarios);
        MetodosPublicos.refrescarVentana(scrollHorarios);
        MetodosPublicos.refrescarVentana(cuerpo2);
    }

    public void agregarAlPanelMiscitas() {
        MetodosPublicos.vaciarPanel(panelInfoCitas);
        JLabel label = new JLabel("No tienes citas pendientes", new ImageIcon("iconsP/emoji.png"), JLabel.CENTER);
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.BOTTOM);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelInfoCitas.add(Box.createVerticalGlue());
        panelInfoCitas.add(label);
        panelInfoCitas.add(Box.createVerticalGlue());
        MetodosPublicos.refrescarVentana(panelInfoCitas);
    }

    private void procesoPanelConInformacionCita(JPanel contenedor, JPanel titulo,
            String fecha, String hora, String nombreMedico, String estado) {
        boolean activa = "Activa".equalsIgnoreCase(estado);
        Color colorEstado = activa ? COLOR_VERDE_ACENTO : new Color(200, 60, 60);

        PanelRound tarjeta = new PanelRound();
        tarjeta.setRoundTopLeft(14);
        tarjeta.setRoundTopRight(14);
        tarjeta.setRoundBottomLeft(14);
        tarjeta.setRoundBottomRight(14);
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setLayout(new BorderLayout(15, 0));
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO, 2, true),
                new EmptyBorder(15, 20, 15, 20)));
        tarjeta.setAlignmentX(Component.LEFT_ALIGNMENT);
        tarjeta.setMaximumSize(new Dimension(Integer.MAX_VALUE, 140));

        JPanel panelContenido = new JPanel();
        panelContenido.setOpaque(false);
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblFecha = new JLabel("Fecha: " + fecha);
        lblFecha.setFont(new Font("Arial", Font.PLAIN, 16));
        lblFecha.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblHora = new JLabel("Hora: " + hora);
        lblHora.setFont(new Font("Arial", Font.PLAIN, 16));
        lblHora.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblMedico = new JLabel(nombreMedico);
        lblMedico.setFont(new Font("Arial", Font.PLAIN, 16));
        lblMedico.setAlignmentX(Component.LEFT_ALIGNMENT);

        panelContenido.add(titulo);
        panelContenido.add(Box.createVerticalStrut(4));
        panelContenido.add(lblFecha);
        panelContenido.add(lblHora);
        panelContenido.add(lblMedico);

        PanelRound insignia = new PanelRound();
        insignia.setRoundTopLeft(20);
        insignia.setRoundTopRight(20);
        insignia.setRoundBottomLeft(20);
        insignia.setRoundBottomRight(20);
        insignia.setBackground(colorEstado);
        insignia.setLayout(new GridBagLayout());
        insignia.setPreferredSize(new Dimension(100, 30));
        JLabel lblEstado = new JLabel(estado);
        lblEstado.setForeground(Color.WHITE);
        lblEstado.setFont(new Font("Arial", Font.BOLD, 13));
        insignia.add(lblEstado);

        JPanel panelDerecha = new JPanel(new GridBagLayout());
        panelDerecha.setOpaque(false);
        panelDerecha.add(insignia);

        tarjeta.add(panelContenido, BorderLayout.CENTER);
        tarjeta.add(panelDerecha, BorderLayout.EAST);

        contenedor.setOpaque(false);
        contenedor.setLayout(new BorderLayout());
        contenedor.add(tarjeta, BorderLayout.CENTER);
    }

    public void agregarAlPanelMiscitas(JPanel titulo, String fecha, String hora, String nombreMedico) {
        JPanel panelBotones = new JPanel(), panelContenido = new JPanel(), c = new JPanel();
        panelBotones.setOpaque(false);
        panelBotones.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        panelBotones.setBorder(new EmptyBorder(45, 0, 0, 0));
        panelContenido.setOpaque(false);
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setAlignmentX(Component.LEFT_ALIGNMENT);
        c.setLayout(new BorderLayout());
        c.setOpaque(false);
        c.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GRAY),
                new EmptyBorder(10, 0, 10, 0)));

        JLabel lblFecha = new JLabel("Fecha: " + fecha);
        lblFecha.setFont(new Font("Arial", Font.BOLD, 19));
        JLabel lblHora = new JLabel("Hora: " + hora);
        lblHora.setFont(new Font("Arial", Font.BOLD, 19));
        JLabel lblMedico = new JLabel(nombreMedico);
        lblMedico.setFont(new Font("Arial", Font.BOLD, 19));

        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblFecha.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblHora.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblMedico.setAlignmentX(Component.LEFT_ALIGNMENT);
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        panelContenido.add(titulo);
        panelContenido.add(lblFecha);
        panelContenido.add(lblHora);
        panelContenido.add(lblMedico);

        JButton btnReagendarCita = new JButton("Reagendar");
        JButton btnCancelarCita = new JButton("Cancelar");
        MetodosPublicos.estilizarBoton(btnReagendarCita, (byte) 7);
        MetodosPublicos.estilizarBoton(btnCancelarCita, (byte) 6);
        panelBotones.add(btnCancelarCita);
        panelBotones.add(btnReagendarCita);

        c.add(panelContenido, BorderLayout.WEST);
        c.add(panelBotones, BorderLayout.EAST);

        this.listaBotonesReagendar.add(btnReagendarCita);
        this.listaBotonesCancelar.add(btnCancelarCita);
        this.panelInfoCitas.add(c);
        MetodosPublicos.refrescarVentana(panelInfoCitas);
    }

    public void agregarAlPanelHistorialCitas(JPanel titulo, String fecha, String hora, String nombreMedico, String estado) {
        JPanel c = new JPanel();
        procesoPanelConInformacionCita(c, titulo, fecha, hora, nombreMedico, estado);
        this.panelListaHistorial.add(c);
        this.panelListaHistorial.add(Box.createVerticalStrut(12));
        MetodosPublicos.refrescarVentana(panelListaHistorial);
    }

    public void mostrarMensajeHistorialVacio() {
        MetodosPublicos.vaciarPanel(panelListaHistorial);
        JLabel lbl = new JLabel("No tienes historial de citas", new ImageIcon("iconsP/info.png"), JLabel.CENTER);
        lbl.setHorizontalTextPosition(JLabel.CENTER);
        lbl.setVerticalTextPosition(JLabel.BOTTOM);
        lbl.setFont(new Font("Arial", Font.BOLD, 20));
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelListaHistorial.add(Box.createVerticalGlue());
        panelListaHistorial.add(lbl);
        panelListaHistorial.add(Box.createVerticalGlue());
        MetodosPublicos.refrescarVentana(panelListaHistorial);
    }

    public void limpiarPanelHorarios() {
        MetodosPublicos.vaciarPanel(panelHorarios);
        panelHorarios.add(new JLabel("Horarios Disponibles"));
        MetodosPublicos.refrescarVentana(panelHorarios);
    }

    public JButton agregarBotonHoraDisponible(String hora) {
        JButton btn = new JButton(hora);
        MetodosPublicos.estilizarBoton(btn, (byte) 4);
        panelHorarios.add(btn);
        panelHorarios.add(Box.createRigidArea(new Dimension(0, 8)));
        MetodosPublicos.refrescarVentana(panelHorarios);
        return btn;
    }

    public void mostrarMensajeSinDisponibilidad(String mensaje) {
        JLabel lbl = new JLabel(mensaje);
        lbl.setFont(new Font("Arial", Font.BOLD, 16));
        panelHorarios.add(lbl);
        MetodosPublicos.refrescarVentana(panelHorarios);
    }

    public Paciente getUsuario() {
        return this.usuario;
    }
}
