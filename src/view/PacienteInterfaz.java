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
import java.awt.GridLayout;
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
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import model.MetodosPublicos;
import model.UsuarioPublico;

public class PacienteInterfaz extends LayoutView {

    //Aqui creo los colores que mas vamos autilizar en la plantilla.
    public static final Color COLOR_AZUL_CORPORATIVO = new Color(0, 79, 124);
    public static final Color COLOR_VERDE_ACENTO = new Color(0, 194, 177);
    public static final Color COLOR_GRIS_SUBTITULO = new Color(100, 120, 130);

    //Aqui creo los componentes que basicamente llevara todo
    private UsuarioPublico usuario;
    private JPanel panelBienvenida;//Aqui creo el JPanel de bienbenida ejemplo:Bienbenido alejo! lifedoccare
    private JPanel panelSesionUsuario;//Aqui creo el JPanel que lelva el boton cerrar sesion y foto de perfil
    private PanelRound panelFotoPerfil;
    public JLabel labelFotoPerfil;//Aqui creo JLabel que llevara la foto de perfil
    public JButton btnCerrarSesion;//Aqui creo el boton cerrar sesion

    //Aqui creo los botones del Paciente
    public JButton btnMisCitas;
    public JButton btnHistorial;
    public JButton btnComentarios;
    public JButton btnNotificaciones;

    //Aqui creo los panes que contendran cada vista
    public JPanel panelVistaMiscitas;
    public JPanel panelVistaHistorial;
    public JPanel panelVistaComentarios;
    public JPanel panelVistaNotificacion;
    public JPanel panelLateralBotonesHistorial;
    public JPanel panelLateralBotonesComentarios;
    //Scroll
    JScrollPane scrollCitas;
    JScrollPane scrollHistorialCitas;
    JScrollPane scrollComentarios;

    //Aqui creo todo para el apartado MisCitas
    public JPanel panelInfoCitas;
    public JButton btnAgendar;

    //Aqui creo todo para el apartado Historial
    public JButton btnHistorialMedico;
    public JButton btnHistorialCitas;
    public JButton btnDescargar;
    public JTextField barraBusqueda;
    public JPanel panelHistorial;
    public JPanel panelBarraBusqueda;
    public JPanel panelListaHistorial;
    public JScrollPane scrollHistorial;
    public JTextArea areaHistorialMedico;
    public JScrollPane scrollHistorialMedico;
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

    public PacienteInterfaz(String nombreInterfaz, UsuarioPublico usuario) {
        super(nombreInterfaz);
        init(usuario);
    }

    private void init(UsuarioPublico usuario) {
        boolean opaque = false;
        this.usuario = usuario;

        super.encabezado.setBorder(new EmptyBorder(40, 40, 0, 40));
        super.encabezado.setLayout(new BorderLayout());

        panelBienvenida = new JPanel();
        panelBienvenida.setLayout(new BorderLayout());
        panelBienvenida.setOpaque(opaque);

        JLabel tituloBienvenida = new JLabel((usuario.getSexoBiologico().equals("Masculino") ? "Bienvenido, " : "Bienvenida, ")
                + this.usuario.getPrimerNombre() + "!");
        tituloBienvenida.setFont(new Font("arial", Font.BOLD, 30));

        panelBienvenida.add(tituloBienvenida, BorderLayout.NORTH);
        panelBienvenida.add(new Titulo("LifeDoc", "Care").getPanelTitulo(), BorderLayout.WEST);

        panelSesionUsuario = new JPanel();
        panelSesionUsuario.setLayout(new FlowLayout());
        panelSesionUsuario.setOpaque(opaque);

        btnCerrarSesion = new JButton("Cerrar sesion", new ImageIcon("iconsP/arrow-left.png"));
        btnCerrarSesion.setBackground(Color.WHITE);
        btnCerrarSesion.setForeground(COLOR_AZUL_CORPORATIVO);
        btnCerrarSesion.setFont(new Font("arial", Font.BOLD, 15));

        Dimension tamanoFijo = new Dimension(64, 64);
        int radio = 100;
        panelFotoPerfil = new PanelRound();
        panelFotoPerfil.setLayout(new BorderLayout());
        panelFotoPerfil.setPreferredSize(tamanoFijo);
        panelFotoPerfil.setMaximumSize(tamanoFijo);
        panelFotoPerfil.setRoundTopLeft(radio);
        panelFotoPerfil.setRoundTopRight(radio);
        panelFotoPerfil.setRoundBottomLeft(radio);
        panelFotoPerfil.setRoundBottomRight(radio);
        ImageIcon imagen = new ImageIcon(this.usuario.getFotoPerfil());
        labelFotoPerfil = new JLabel();
        labelFotoPerfil.setPreferredSize(tamanoFijo);
        labelFotoPerfil.setMinimumSize(tamanoFijo);
        labelFotoPerfil.setMaximumSize(tamanoFijo);
        Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(64, 64, Image.SCALE_DEFAULT));
        labelFotoPerfil.setIcon(icono);
        labelFotoPerfil.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        panelFotoPerfil.add(labelFotoPerfil, BorderLayout.CENTER);
        panelSesionUsuario.add(btnCerrarSesion);
        panelSesionUsuario.add(panelFotoPerfil);

        encabezado.add(panelBienvenida, BorderLayout.WEST);
        encabezado.add(panelSesionUsuario, BorderLayout.EAST);

        cuerpo1.setLayout(new FlowLayout(FlowLayout.LEFT));
        cuerpo1.setOpaque(opaque);
        cuerpo1.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(20, 40, 0, 40),
                BorderFactory.createMatteBorder(0, 0, 2, 0, COLOR_AZUL_CORPORATIVO)));
        agregarBotonesMenuPaciente();

        cuerpo2.setLayout(new BorderLayout());
        cuerpo2.setOpaque(opaque);

        //Vista Mis citas
        panelInfoCitas = new JPanel();
        panelInfoCitas.setLayout(new BoxLayout(panelInfoCitas, BoxLayout.Y_AXIS));
        panelInfoCitas.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO, 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        panelInfoCitas.setOpaque(opaque);

        btnAgendar = new JButton("Agendar una cita", new ImageIcon("iconsP/heart.png"));
        MetodosPublicos.estilizarBoton(btnAgendar, (byte) 3);

        JPanel panelAgenda = new JPanel();
        panelAgenda.setLayout(new BoxLayout(panelAgenda, BoxLayout.Y_AXIS));
        panelAgenda.setOpaque(opaque);
        panelAgenda.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO, 4),
                BorderFactory.createEmptyBorder(100, 100, 95, 100)));

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

        JPanel panelContacto = new JPanel();
        panelContacto.setLayout(new BoxLayout(panelContacto, BoxLayout.Y_AXIS));
        panelContacto.setOpaque(false);
        panelContacto.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO, 4),
                BorderFactory.createEmptyBorder(70, 40, 70, 0)));

        JLabel lblContactoTitulo = new JLabel("Si tienes dificultades para agendar tu cita, ¡contáctanos! ",
                new ImageIcon("iconsP/friends.png"), JLabel.CENTER);
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

        JLabel labelCitas = new JLabel("Citas programadas Vigentes");
        labelCitas.setFont(new Font("arial", Font.BOLD, 28));
        labelCitas.setForeground(COLOR_AZUL_CORPORATIVO);

        scrollCitas = new JScrollPane(panelInfoCitas);
        scrollCitas.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollCitas.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollCitas.setBorder(BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO, 2));
        scrollCitas.getViewport().setOpaque(opaque);
        scrollCitas.setOpaque(opaque);

        panelVistaMiscitas = new JPanel();
        panelVistaMiscitas.setLayout(new BorderLayout(20, 20));
        panelVistaMiscitas.setOpaque(opaque);
        panelVistaMiscitas.setBorder(new EmptyBorder(5, 40, 20, 40));
        panelVistaMiscitas.add(labelCitas, BorderLayout.NORTH);
        panelVistaMiscitas.add(panelAgenda, BorderLayout.EAST);
        panelVistaMiscitas.add(scrollCitas, BorderLayout.CENTER);
        panelVistaMiscitas.add(panelContacto, BorderLayout.SOUTH);

        //Vista historial
        panelBarraBusqueda = new JPanel();
        panelBarraBusqueda.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 20));
        panelBarraBusqueda.setOpaque(opaque);

        barraBusqueda = new JTextField(20);
        JLabel lblFiltrar = new JLabel("Buscar");
        lblFiltrar.setFont(new Font("Arial", Font.PLAIN, 18));
        lblFiltrar.setForeground(new Color(80, 80, 80));
        panelBarraBusqueda.add(lblFiltrar);
        panelBarraBusqueda.add(barraBusqueda);

        panelListaHistorial = new JPanel();
        panelListaHistorial.setLayout(new BoxLayout(panelListaHistorial, BoxLayout.Y_AXIS));
        panelListaHistorial.setBorder(new EmptyBorder(5, 5, 5, 5));
        panelListaHistorial.setOpaque(opaque);

        scrollHistorial = new JScrollPane(panelListaHistorial);
        scrollHistorial.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollHistorial.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollHistorial.setOpaque(opaque);
        scrollHistorial.getViewport().setOpaque(opaque);
        scrollHistorial.setBorder(null);

        panelHistorial = new JPanel();
        panelHistorial.setLayout(new BorderLayout());
        panelHistorial.setBorder(BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO, 4));
        panelHistorial.setOpaque(opaque);
        Dimension dimension = new Dimension(950, 550);
        panelHistorial.setPreferredSize(dimension);
        dimension = new Dimension(400, 200);
        panelHistorial.setMaximumSize(dimension);

        areaHistorialMedico = new JTextArea();
        areaHistorialMedico.setOpaque(opaque);
        areaHistorialMedico.setLineWrap(!opaque);
        areaHistorialMedico.setFont(new Font("Arial", Font.PLAIN, 14));
        areaHistorialMedico.setEditable(opaque);
        areaHistorialMedico.getCaret().setVisible(opaque);
        areaHistorialMedico.setFocusable(opaque);

        scrollHistorialMedico = new JScrollPane(areaHistorialMedico);
        scrollHistorialMedico.setOpaque(opaque);
        scrollHistorialMedico.getViewport().setOpaque(opaque);
        scrollHistorialMedico.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollHistorialMedico.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollHistorialMedico.setBorder(BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO, 2));

        btnHistorialMedico = new JButton("Historial Medico ", new ImageIcon("iconsP/avatar.png"));
        btnHistorialCitas = new JButton("Historial de Citas", new ImageIcon("iconsP/friends.png"));
        btnDescargar = new JButton("Descargar Historial Medico", new ImageIcon("iconsP/descargar.png"));
        MetodosPublicos.estilizarBoton(btnDescargar, (byte) 5);
        MetodosPublicos.estilizarBoton(btnHistorialMedico, (byte) 2);
        MetodosPublicos.estilizarBoton(btnHistorialCitas, (byte) 2);

        panelLateralBotonesHistorial = new JPanel();
        panelLateralBotonesHistorial.setLayout(new BoxLayout(panelLateralBotonesHistorial, BoxLayout.Y_AXIS));
        panelLateralBotonesHistorial.setBorder(new EmptyBorder(105, 0, 0, 0));
        panelLateralBotonesHistorial.setPreferredSize(new Dimension(400, 0));
        panelLateralBotonesHistorial.setOpaque(opaque);
        panelLateralBotonesHistorial.add(btnHistorialMedico);
        panelLateralBotonesHistorial.add(Box.createRigidArea(new Dimension(0, 30)));
        panelLateralBotonesHistorial.add(btnHistorialCitas);

        panelVistaHistorial = new JPanel();
        panelVistaHistorial.setLayout(new BorderLayout(20, 0));
        panelVistaHistorial.setOpaque(opaque);
        panelVistaHistorial.setBorder(new EmptyBorder(40, 40, 40, 40));
        panelVistaHistorial.add(panelLateralBotonesHistorial, BorderLayout.WEST);

        JPanel wrapperHistorial = new JPanel(new GridBagLayout());
        wrapperHistorial.setOpaque(opaque);
        wrapperHistorial.add(panelHistorial);

        panelVistaHistorial.add(wrapperHistorial, BorderLayout.CENTER);

        //Vista comentarios.
        campoAsunto = new JTextField();
        campoAsunto.setFont(new Font("Arial", Font.PLAIN, 18));
        campoAsunto.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        campoAsunto.setBorder(BorderFactory.createLineBorder(COLOR_VERDE_ACENTO));

        areaDescripcion = new JTextArea();
        areaDescripcion.setFont(new Font("Arial", Font.PLAIN, 18));
        areaDescripcion.setLineWrap(!opaque);
        areaDescripcion.setWrapStyleWord(!opaque);
        areaDescripcion.setBorder(BorderFactory.createLineBorder(COLOR_VERDE_ACENTO));
        miCroll = new JScrollPane(areaDescripcion);
        miCroll.setBorder(BorderFactory.createLineBorder(COLOR_VERDE_ACENTO));

        panelComentarios = new JPanel();
        panelComentarios.setLayout(new BoxLayout(panelComentarios, BoxLayout.Y_AXIS));
        panelComentarios.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO, 4),
                BorderFactory.createEmptyBorder(5, 30, 5, 30)));
        panelComentarios.setOpaque(opaque);

        panelComentarios1 = new JPanel();
        panelComentarios1.setLayout(new BoxLayout(panelComentarios1, BoxLayout.Y_AXIS));
        panelComentarios1.setBorder(null);
        panelComentarios1.setOpaque(opaque);

        btnSugerencias = new JButton("Sugerencias ", new ImageIcon("iconsP/happy-face.png"));
        btnQuejas = new JButton("Quejas ", new ImageIcon("iconsP/quejas.png"));
        btnForo = new JButton("Foro ", new ImageIcon("iconsP/communication.png"));
        btnEnviar = new JButton("Enviar ", new ImageIcon("iconsP/descargar.png"));
        MetodosPublicos.estilizarBoton(btnQuejas, (byte) 4);
        MetodosPublicos.estilizarBoton(btnSugerencias, (byte) 4);
        MetodosPublicos.estilizarBoton(btnForo, (byte) 4);
        MetodosPublicos.estilizarBoton(btnEnviar, (byte) 5);

        construirFormularioComentario();

        panelLateralBotonesComentarios = new JPanel();
        panelLateralBotonesComentarios.setLayout(new BoxLayout(panelLateralBotonesComentarios, BoxLayout.Y_AXIS));
        panelLateralBotonesComentarios.setBorder(new EmptyBorder(105, 0, 0, 0));
        panelLateralBotonesComentarios.setPreferredSize(new Dimension(400, 0));
        panelLateralBotonesComentarios.setOpaque(opaque);
        panelLateralBotonesComentarios.add(btnSugerencias);
        panelLateralBotonesComentarios.add(Box.createRigidArea(new Dimension(0, 30)));
        panelLateralBotonesComentarios.add(btnQuejas);
        panelLateralBotonesComentarios.add(Box.createRigidArea(new Dimension(0, 30)));
        panelLateralBotonesComentarios.add(btnForo);

        panelVistaComentarios = new JPanel();
        panelVistaComentarios.setLayout(new BorderLayout(20, 0));
        panelVistaComentarios.setOpaque(opaque);
        panelVistaComentarios.setBorder(new EmptyBorder(40, 40, 40, 40));
        panelVistaComentarios.add(panelLateralBotonesComentarios, BorderLayout.WEST);
        panelVistaComentarios.add(panelComentarios, BorderLayout.CENTER);

        panelContenidoNotificaciones = new JPanel();
        panelContenidoNotificaciones.setLayout(new BoxLayout(panelContenidoNotificaciones, BoxLayout.Y_AXIS));
        panelContenidoNotificaciones.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO, 4),
                BorderFactory.createEmptyBorder(0, 30, 5, 30)));
        panelContenidoNotificaciones.setOpaque(opaque);

        JScrollPane scrollNotificaciones = new JScrollPane(panelContenidoNotificaciones);
        scrollNotificaciones.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollNotificaciones.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollNotificaciones.setOpaque(opaque);
        scrollNotificaciones.getViewport().setOpaque(opaque);
        scrollNotificaciones.setBorder(null);

        panelVistaNotificacion = new JPanel();
        panelVistaNotificacion.setLayout(new BorderLayout());
        panelVistaNotificacion.setOpaque(opaque);
        panelVistaNotificacion.setBorder(new EmptyBorder(40, 40, 40, 40));
        panelVistaNotificacion.add(scrollNotificaciones, BorderLayout.CENTER);

        btnOdontologia = new JButton("Odontologia", new ImageIcon("iconsP/heart.png"));
        btnDermatologia = new JButton("Dermatologia", new ImageIcon("iconsP/heart.png"));
        btnMedicoGeneral = new JButton("Medico General", new ImageIcon("iconsP/heart.png"));
        MetodosPublicos.estilizarBoton(btnOdontologia, (byte) 4);
        MetodosPublicos.estilizarBoton(btnDermatologia, (byte) 4);
        MetodosPublicos.estilizarBoton(btnMedicoGeneral, (byte) 4);
        listaBotonesMedicos = new ArrayList<>();

        panelSeleccionConsulta = new JPanel();
        panelSeleccionConsulta.setLayout(new BoxLayout(panelSeleccionConsulta, BoxLayout.Y_AXIS));
        panelSeleccionConsulta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO, 4),
                BorderFactory.createEmptyBorder(50, 60, 50, 60)));
        panelSeleccionConsulta.setOpaque(opaque);

        panelCalendario = new JPanel();
        panelCalendario.setLayout(new BoxLayout(panelCalendario, BoxLayout.Y_AXIS));
        panelCalendario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO, 4),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        panelCalendario.setPreferredSize(new Dimension(750, 0));
        panelCalendario.setOpaque(opaque);

        panelHorarios = new JPanel();
        panelHorarios.setLayout(new BoxLayout(panelHorarios, BoxLayout.Y_AXIS));
        panelHorarios.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO, 4),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        panelHorarios.setOpaque(opaque);

        scrollHorarios = new JScrollPane(panelHorarios);
        scrollHorarios.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollHorarios.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollHorarios.setOpaque(opaque);
        scrollHorarios.getViewport().setOpaque(opaque);
        scrollHorarios.setBorder(BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO));
    }

    public void construirPanelVistaHistorial() {
        MetodosPublicos.vaciarPanel(panelHistorial);
        panelHistorial.add(panelBarraBusqueda, BorderLayout.NORTH);
        panelHistorial.add(scrollHistorial, BorderLayout.CENTER);
        MetodosPublicos.refrescarVentana(panelHistorial);
    }

    public void construirPanelVistaHistorialConHistorial() {
        MetodosPublicos.vaciarPanel(panelHistorial);
        MetodosPublicos.vaciarPanel(areaHistorialMedico);
        areaHistorialMedico.add(new Titulo("Lifedoc", "Care", 30).getPanelTitulo());
        panelHistorial.add(scrollHistorialMedico, BorderLayout.CENTER);
        panelHistorial.add(btnDescargar, BorderLayout.SOUTH);
        MetodosPublicos.refrescarVentana(panelHistorial);
        MetodosPublicos.refrescarVentana(areaHistorialMedico);
    }

    //Aqui creo el metodo para habilitar o desabilitar botones del paciente.
    @Override
    public void habilitarBotonesMenu(JButton botonActivo) {
        botonActivo.setEnabled(false);
        if (botonActivo != btnMisCitas && !btnMisCitas.isEnabled()) {
            btnMisCitas.setEnabled(true);
        }
        if (botonActivo != btnHistorial && !btnHistorial.isEnabled()) {
            btnHistorial.setEnabled(true);
        }
        if (botonActivo != btnComentarios && !btnComentarios.isEnabled()) {
            btnComentarios.setEnabled(true);
        }
        if (botonActivo != btnNotificaciones && !btnNotificaciones.isEnabled()) {
            btnNotificaciones.setEnabled(true);
        }
    }

    //Aqui creo el metodo que nos va a servir para agregar un JButton al cuerpo1 que seria el panel para los botones
    @Override
    public void agregarBotonCuerpo1(JButton boton) {
        MetodosPublicos.estilizarBoton(boton, (byte) 1);
        cuerpo1.add(boton);
        cuerpo1.add(Box.createHorizontalStrut(5));
        MetodosPublicos.refrescarVentana(cuerpo1);
    }

    //Aqui creo el metodo que nos va a permitir agregar cualquier tipo de 
    //objeto que sea creado con una clase que hereda de JComponent para agregar al panel cuerpo1
    public void agregarComponenteCuerpo1(JComponent componente) {
        cuerpo1.add(componente);
        MetodosPublicos.refrescarVentana(cuerpo1);
    }

    //Aqui creo metodo que me permitira cargar al cuerpo1 los botones del paciente
    public void agregarBotonesMenuPaciente() {
        btnMisCitas = new JButton("Mis citas", new ImageIcon("iconsP/heart.png"));
        btnHistorial = new JButton("Historial", new ImageIcon("iconsP/file.png"));
        btnComentarios = new JButton("️Comentarios", new ImageIcon("iconsP/chat-bubble.png"));
        btnNotificaciones = new JButton("️Notificaciones", new ImageIcon("iconsP/bell.png"));
        agregarBotonCuerpo1(btnMisCitas);
        agregarBotonCuerpo1(btnHistorial);
        agregarBotonCuerpo1(btnComentarios);
        agregarBotonCuerpo1(btnNotificaciones);
        MetodosPublicos.refrescarVentana(cuerpo1);
    }

    //Aqui creo el metodo que me permitiria vizualizar en el JPanel cuerpo2 el apartado de mis citas
    public void mostrarVistaMisCitas() {
        MetodosPublicos.vaciarPanel(cuerpo2);
        cuerpo2.setLayout(new BorderLayout());
        cuerpo2.setBorder(new EmptyBorder(0, 0, 0, 0));
        cuerpo2.add(panelVistaMiscitas, BorderLayout.CENTER);
        MetodosPublicos.refrescarVentana(cuerpo2);
    }

    public void mostrarVistaHistorial() {
        MetodosPublicos.vaciarPanel(cuerpo2);
        cuerpo2.setLayout(new BorderLayout());
        cuerpo2.setBorder(new EmptyBorder(0, 0, 0, 0));
        cuerpo2.add(panelVistaHistorial, BorderLayout.CENTER);
        MetodosPublicos.refrescarVentana(cuerpo2);
    }

    public void mostrarVistaComentarios() {
        MetodosPublicos.vaciarPanel(cuerpo2);
        cuerpo2.setLayout(new BorderLayout());
        cuerpo2.setBorder(new EmptyBorder(0, 0, 0, 0));
        cuerpo2.add(panelVistaComentarios, BorderLayout.CENTER);
        MetodosPublicos.refrescarVentana(cuerpo2);
    }

    public void mostrarVistaNotificaciones() {
        MetodosPublicos.vaciarPanel(cuerpo2);
        cuerpo2.setBorder(new EmptyBorder(0, 0, 0, 0));
        cuerpo2.add(panelVistaNotificacion, BorderLayout.CENTER);
        MetodosPublicos.refrescarVentana(cuerpo2);
    }

    public void mostrarVistaHistorialConHistorial(String historial, String nombre, String edad) {
        MetodosPublicos.vaciarPanel(panelHistorial);

        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));
        panelSuperior.setOpaque(false);
        JPanel panelLife = new Titulo("Lifedoc ", "Care", 30).getPanelTitulo();
        panelLife.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel lNombre = new JLabel("Nombre :" + nombre);
        lNombre.setOpaque(false);
        lNombre.setFont(new Font("Arial", Font.CENTER_BASELINE, 20));
        lNombre.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel lEdad = new JLabel("Edad: " + edad);
        lEdad.setOpaque(false);
        lEdad.setFont(new Font("Arial", Font.CENTER_BASELINE, 20));
        lEdad.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelSuperior.add(panelLife);
        panelSuperior.add(lNombre);
        panelSuperior.add(lEdad);

        JTextArea miarea = new JTextArea(historial);
        miarea.setOpaque(false);
        miarea.setEditable(false);
        miarea.setFont(new Font("Segoe UI", Font.BOLD, 20));
        JScrollPane scrollDetalleHistorial = new JScrollPane(miarea);
        scrollDetalleHistorial.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollDetalleHistorial.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollDetalleHistorial.setOpaque(false);
        scrollDetalleHistorial.getViewport().setOpaque(false);
        scrollDetalleHistorial.setBorder(null);

        this.panelHistorial.add(panelSuperior, BorderLayout.NORTH);
        this.panelHistorial.add(scrollDetalleHistorial, BorderLayout.CENTER);
        this.panelHistorial.add(btnDescargar, BorderLayout.SOUTH);

        MetodosPublicos.refrescarVentana(panelHistorial);
    }

    public void vaciarPanel() {
        MetodosPublicos.vaciarPanel(panelListaHistorial);
        MetodosPublicos.refrescarVentana(panelListaHistorial);
    }

    public void agregarAlPanelHistorialCitas(JPanel titulo, String fecha, String hora, String nombreMedico, String estado) {
        JPanel targeta = new JPanel();
        targeta.setLayout(new GridLayout(1, 2));
        targeta.setBackground(Color.WHITE);
        targeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO, 2),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        Dimension dimension = new Dimension(800, 205);
        targeta.setMaximumSize(dimension);
        dimension = new Dimension(300, 200);
        targeta.setMinimumSize(dimension);

        JPanel parte1 = new JPanel();
        parte1.setLayout(new BoxLayout(parte1, BoxLayout.Y_AXIS));
        parte1.setOpaque(false);
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

        parte1.add(titulo);
        parte1.add(lblFecha);
        parte1.add(lblHora);
        parte1.add(lblMedico);

        PanelRound insignia = new PanelRound();
        insignia.setRoundTopLeft(20);
        insignia.setRoundTopRight(20);
        insignia.setRoundBottomLeft(20);
        insignia.setRoundBottomRight(20);
        insignia.setBackground(new Color(200, 60, 60));
        insignia.setLayout(new GridBagLayout());
        insignia.setPreferredSize(new Dimension(100, 30));
        JLabel lblEstado = new JLabel(estado);
        lblEstado.setForeground(Color.WHITE);
        lblEstado.setFont(new Font("Arial", Font.BOLD, 13));
        lblEstado.setAlignmentY(Component.CENTER_ALIGNMENT);
        lblEstado.setAlignmentX(Component.CENTER_ALIGNMENT);
        insignia.add(lblEstado);

        JPanel parte2 = new JPanel();
        parte2.setLayout(new FlowLayout(FlowLayout.RIGHT)); // RIGHT para que quede pegada al borde derecho
        parte2.setOpaque(false);
        parte2.add(insignia);

        JPanel wrapperParte2 = new JPanel(new BorderLayout());
        wrapperParte2.setOpaque(false);
        wrapperParte2.add(parte2, BorderLayout.NORTH);

        targeta.add(parte1);
        targeta.add(wrapperParte2);

        this.panelListaHistorial.add(targeta);
        this.panelListaHistorial.add(Box.createVerticalStrut(8));
        MetodosPublicos.refrescarVentana(scrollHistorial);
        MetodosPublicos.refrescarVentana(panelListaHistorial);
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
        Dimension dimension = new Dimension(800, 543);
        scrollComentarios.setPreferredSize(dimension);
        scrollComentarios.setMaximumSize(dimension);
        dimension = new Dimension(700, 400);
        scrollComentarios.setMinimumSize(dimension);
        this.panelComentarios.add(scrollComentarios);
        MetodosPublicos.refrescarVentana(panelComentarios1);
        MetodosPublicos.refrescarVentana(panelComentarios);
    }

    //Aqui creo el metodo que me permitiria agregar componentes al panel comentarios
    public void agregarAlPanelComentarios(String tipoMensaje, String asunto, String nombreUsu, String descripcion) {
        JPanel c = new JPanel();
        c.setBackground(Color.WHITE);
        c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
        c.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO, 2),
                new EmptyBorder(15, 20, 15, 20)));
        c.setMaximumSize(new Dimension(1000, 170));
        c.setMinimumSize(new Dimension(500, 100));

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

    public void agregarNotificaciones(String encabezado, String descripcion) {
        //Tarjetita
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BorderLayout(0, 0));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO, 2, true),
                new EmptyBorder(16, 18, 16, 20)));
        tarjeta.setMaximumSize(new Dimension(1500, 105));
        tarjeta.setMinimumSize(new Dimension(500, 100));
        tarjeta.setAlignmentX(Component.LEFT_ALIGNMENT);

        //Icono
        PanelRound circuloIcono = new PanelRound();
        circuloIcono.setRoundTopLeft(100);
        circuloIcono.setRoundTopRight(100);
        circuloIcono.setRoundBottomLeft(100);
        circuloIcono.setRoundBottomRight(100);
        circuloIcono.setBackground(new Color(0, 79, 124, 25));
        circuloIcono.setLayout(new GridBagLayout());
        Dimension tamanoCirculo = new Dimension(50, 50);
        circuloIcono.setPreferredSize(tamanoCirculo);
        circuloIcono.setMinimumSize(tamanoCirculo);
        circuloIcono.setMaximumSize(tamanoCirculo);

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

        JPanel filaSuperior = new JPanel(new BorderLayout());
        filaSuperior.setOpaque(false);
        filaSuperior.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblTitulo = new JLabel(encabezado);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setForeground(new Color(28, 28, 28));

        JLabel lblHora = new JLabel(java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm")));
        lblHora.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblHora.setForeground(new Color(160, 160, 160));

        filaSuperior.add(lblTitulo, BorderLayout.WEST);
        filaSuperior.add(lblHora, BorderLayout.EAST);

        JTextArea txtDescripcion = new JTextArea(descripcion);
        txtDescripcion.setEditable(false);
        txtDescripcion.setOpaque(false);
        txtDescripcion.setFocusable(false);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtDescripcion.setForeground(new Color(105, 105, 105));
        txtDescripcion.setAlignmentX(Component.LEFT_ALIGNMENT);

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
        tamanoPunto = new Dimension(5, 5);
        puntoEstado.setMinimumSize(tamanoPunto);

        JLabel lblEstado = new JLabel("  Nueva notificacion");
        lblEstado.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblEstado.setForeground(COLOR_VERDE_ACENTO);

        franjaEstado.add(puntoEstado);
        franjaEstado.add(lblEstado);

        JPanel contenido = new JPanel();
        contenido.setOpaque(false);
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.add(filaSuperior);
        contenido.add(Box.createVerticalStrut(6));
        contenido.add(txtDescripcion);
        contenido.add(franjaEstado);

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

    public void mostrarVistaSeleccionMedico() {
        MetodosPublicos.vaciarPanel(cuerpo2);
        MetodosPublicos.vaciarPanel(panelSeleccionConsulta);

        Titulo titulo = new Titulo("Agendamiento de ", "Cita");
        JLabel descripcion = new JLabel("No se Encontraron Medicos Asociados");
        descripcion.setFont(new Font("Arial", Font.BOLD, 22));
        descripcion.setForeground(Color.BLACK);
        descripcion.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel panelTitulo = titulo.getPanelTitulo();
        panelTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.panelSeleccionConsulta.add(panelTitulo);
        this.panelSeleccionConsulta.add(descripcion);
        this.panelSeleccionConsulta.add(Box.createRigidArea(new Dimension(0, 25)));

        this.cuerpo2.setLayout(new GridBagLayout());
        this.cuerpo2.setBorder(new EmptyBorder(40, 40, 40, 40));
        this.cuerpo2.add(panelSeleccionConsulta, new GridBagConstraints());
        MetodosPublicos.refrescarVentana(panelSeleccionConsulta);
        MetodosPublicos.refrescarVentana(cuerpo2);
    }

    public void mostrarVistaSeleccionMedico(String[] medicos, ArrayList listaBotonesMedicos) {
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

        for (String nombreMedico : medicos) {
            JButton botonMedico = new JButton(nombreMedico, new ImageIcon("iconsP/avatar.png"));//Creo boton con el nombre del medico
            MetodosPublicos.estilizarBoton(botonMedico, (byte) 4);//Agrego estilo al boton
            MetodosPublicos.prepararBotonTarjeta(botonMedico, 380, 55);//Agrego tamano estable 
            listaBotonesMedicos.add(botonMedico);
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

    public void agregarAlPanelMiscitas(JPanel titulo, String fecha, String hora, String nombreMedico, JButton btnCancelarCita, JButton btnReagendarCita) {
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

        MetodosPublicos.estilizarBoton(btnReagendarCita, (byte) 7);
        MetodosPublicos.estilizarBoton(btnCancelarCita, (byte) 6);
        panelBotones.add(btnCancelarCita);
        panelBotones.add(btnReagendarCita);

        c.add(panelContenido, BorderLayout.WEST);
        c.add(panelBotones, BorderLayout.EAST);
        Dimension dimension = new Dimension(830, 150);
        c.setMaximumSize(dimension);
        dimension = new Dimension(300, 100);
        c.setMinimumSize(dimension);
        this.panelInfoCitas.add(c);
        MetodosPublicos.refrescarVentana(panelInfoCitas);
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
    
    public void mostrarMensajeHistorialVacio(String mensaje) {
        MetodosPublicos.vaciarPanel(panelListaHistorial);
        JLabel lbl = new JLabel(mensaje, new ImageIcon("iconsP/info.png"), JLabel.CENTER);
        lbl.setHorizontalTextPosition(JLabel.CENTER);
        lbl.setVerticalTextPosition(JLabel.BOTTOM);
        lbl.setFont(new Font("Arial", Font.BOLD, 20));
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelListaHistorial.add(Box.createVerticalGlue());
        panelListaHistorial.add(lbl);
        panelListaHistorial.add(Box.createVerticalGlue());
        MetodosPublicos.refrescarVentana(panelListaHistorial);
    }

    public UsuarioPublico getUsuario() {
        return this.usuario;
    }
}
