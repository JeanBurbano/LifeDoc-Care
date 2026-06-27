package view;

import com.github.lgooddatepicker.components.CalendarPanel;
import com.github.lgooddatepicker.components.DatePickerSettings;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
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

public class PacienteInterfaz extends JFrame{

    //Aqui creo los colores que mas vamos autilizar en la plantilla.
    public static final Color COLOR_AZUL_CORPORATIVO = new Color(0, 79, 124);
    public static final Color COLOR_VERDE_ACENTO = new Color(0, 194, 177);
    public static final Color COLOR_GRIS_SUBTITULO = new Color(100, 120, 130);
    //Aqui creo los componentes que basicamente llevara todo
    private JLabel fondoVentana;//Aqui creo el JLabel que se comportara como contendor y fondo.
    public JPanel encabezado;//Aqui creo el JPanel que sera el encabezado
    private JPanel panelBienvenida;//Aqui creo el JPanel de bienbenida ejemplo:Bienbenido alejo! lifedoccare
    private JPanel panelSesionUsuario;//Aqui creo el JPanel que lelva el boton cerrar sesion y foto de perfil
    private JLabel labelFotoPerfil;//Aqui creo JLabel que llevara la foto de perfil
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
    public JPanel panelBotonesLaterales;
    //Aqui creo todo para el apartado Comentarios
    public JButton btnSugerencias;
    public JButton btnQuejas;
    public JButton btnForo;
    public JButton btnEnviar;
    public JTextField campoAsunto;
    public JTextArea areaDescripcion;
    public JScrollPane miCroll;
    public JPanel panelComentarios;
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
    
    public PacienteInterfaz(){
        
    }
    public PacienteInterfaz(String nombre, String nombreInterfaz, String rutaFotoP) {
        super(nombreInterfaz);//Asigno nombre de la ventana

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

        JLabel tituloBienvenida = new JLabel("Bienvenido, " + nombre + "!");
        tituloBienvenida.setFont(new Font("arial", Font.BOLD, 30));

        this.panelBienvenida.add(tituloBienvenida, BorderLayout.NORTH);
        this.panelBienvenida.add(new Titulo("LifeDoc", "Care").getPanelTitulo(), BorderLayout.WEST);
        tituloBienvenida = null;

        this.panelSesionUsuario = new JPanel();
        this.panelSesionUsuario.setLayout(new FlowLayout());
        this.panelSesionUsuario.setOpaque(false);

        this.btnCerrarSesion = new JButton("← Cerrar sesion");
        this.btnCerrarSesion.setBackground(Color.WHITE);
        this.btnCerrarSesion.setForeground(COLOR_AZUL_CORPORATIVO);
        this.btnCerrarSesion.setFont(new Font("arial", Font.BOLD, 15));

        this.labelFotoPerfil = new JLabel(new ImageIcon(rutaFotoP));
        this.labelFotoPerfil.setSize(150, 150);

        this.panelSesionUsuario.add(btnCerrarSesion);
        this.panelSesionUsuario.add(labelFotoPerfil);

        this.encabezado.add(panelBienvenida, BorderLayout.WEST);
        this.encabezado.add(panelSesionUsuario, BorderLayout.EAST);

        this.cuerpo1 = new JPanel();
        this.cuerpo1.setLayout(new FlowLayout(FlowLayout.LEFT, 25, 10));
        this.cuerpo1.setOpaque(false);
        this.cuerpo1.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(40, 40, 0, 40),
                BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GRAY)));

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
        this.panelInfoCitas.setOpaque(false);
        this.panelInfoCitas.setPreferredSize(new Dimension(0, 345));

        this.btnAgendar = new JButton("❤️Agendar una cita");
        estilizarBoton(btnAgendar, (byte) 3);

        this.panelHistorial = new JPanel();
        this.panelHistorial.setLayout(new BorderLayout());
        this.panelHistorial.setBorder(BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO));
        this.panelHistorial.setOpaque(false);

        this.panelFiltros = new JPanel();
        this.panelFiltros.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 20));
        this.panelFiltros.setOpaque(false);
        this.panelFiltros.setPreferredSize(new Dimension(0, 100));

        this.panelListaHistorial = new JPanel();
        this.panelListaHistorial.setLayout(new BoxLayout(panelListaHistorial, BoxLayout.Y_AXIS));
        this.panelListaHistorial.setPreferredSize(new Dimension(0, 470));
        this.panelListaHistorial.setOpaque(false);

        this.panelBotonesLaterales = new JPanel();
        this.panelBotonesLaterales.setBorder(new EmptyBorder(105, 0, 0, 0));
        this.panelBotonesLaterales.setLayout(new BoxLayout(panelBotonesLaterales, BoxLayout.Y_AXIS));
        this.panelBotonesLaterales.setPreferredSize(new Dimension(400, 0));
        this.panelBotonesLaterales.setOpaque(false);

        this.btnHistorialMedico = new JButton(" 👤 Historial Medico ");
        this.btnHistorialCitas = new JButton(" 👥 Historial de Citas");
        this.btnDescargar = new JButton(" ⬇️ Descargar Historial Medico");
        estilizarBoton(btnHistorialMedico, (byte) 2);
        estilizarBoton(btnHistorialCitas, (byte) 2);

        this.cmbFecha = new JComboBox();
        this.cmbMedico = new JComboBox();
        this.cmbEspecialidad = new JComboBox();
        estilizarComboBox(cmbFecha);
        estilizarComboBox(cmbMedico);
        estilizarComboBox(cmbEspecialidad);

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

        this.panelComentarios = new JPanel();
        this.panelComentarios.setLayout(new BoxLayout(panelComentarios, BoxLayout.Y_AXIS));
        this.panelComentarios.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO),
                BorderFactory.createEmptyBorder(0, 30, 5, 30)));
        this.panelComentarios.setOpaque(false);

        this.btnSugerencias = new JButton(" 😄 Sugerencias ");
        this.btnQuejas = new JButton(" ❌ Quejas ");
        this.btnForo = new JButton(" ✉️ Foro ");
        this.btnEnviar = new JButton(" 📤 Enviar ");
        estilizarBoton(btnQuejas, (byte) 4);
        estilizarBoton(btnSugerencias, (byte) 4);
        estilizarBoton(btnForo, (byte) 4);
        estilizarBoton(btnEnviar, (byte) 5);

        this.panelContenidoNotificaciones = new JPanel();
        this.panelContenidoNotificaciones.setLayout(new BoxLayout(panelContenidoNotificaciones, BoxLayout.Y_AXIS));
        this.panelContenidoNotificaciones.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO),
                BorderFactory.createEmptyBorder(0, 30, 5, 30)));
        this.panelContenidoNotificaciones.setOpaque(false);

        this.btnOdontologia = new JButton("❤️ Odontologia");
        this.btnDermatologia = new JButton("❤️ Dermatologia");
        this.btnMedicoGeneral = new JButton("❤️ Medico General");
        estilizarBoton(btnOdontologia, (byte) 4);
        estilizarBoton(btnDermatologia, (byte) 4);
        estilizarBoton(btnMedicoGeneral, (byte) 4);
        this.listaBotonesMedicos = new ArrayList<JButton>();

        this.panelSeleccionConsulta = new JPanel();
        this.panelSeleccionConsulta.setLayout(new BoxLayout(panelSeleccionConsulta, BoxLayout.Y_AXIS));
        this.panelSeleccionConsulta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO),
                BorderFactory.createEmptyBorder(50, 60, 50, 60)));//Padding interno tipo "tarjeta" como en la imagen
        this.panelSeleccionConsulta.setOpaque(false);

        this.panelCalendario = new JPanel();
        this.panelCalendario.setLayout(new BoxLayout(panelCalendario, BoxLayout.Y_AXIS));
        this.panelCalendario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        this.panelCalendario.setPreferredSize(new Dimension(750, 0));
        this.panelCalendario.setOpaque(false);

        this.panelHorarios = new JPanel();
        this.panelHorarios.setLayout(new BoxLayout(panelHorarios, BoxLayout.Y_AXIS));
        this.panelHorarios.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        this.panelHorarios.setPreferredSize(new Dimension(750, 0));
        this.panelHorarios.setOpaque(false);
    }

    //Aqui creo un funcion para estilizar el boton
    public void estilizarBoton(JButton boton, byte estilo) {
        switch (estilo) {
            case 1://Botones del menu cuerpo1
                boton.setFont(new Font("Arial", Font.BOLD, 15));
                boton.setBackground(new Color(232, 249, 248));
                boton.setForeground(new Color(0, 75, 121));
                break;
            case 2://Botones laterales tipo Historial Medico/Historial de Citas
                boton.setFont(new Font("Arial", Font.BOLD, 30));
                boton.setBackground(Color.WHITE);
                boton.setForeground(new Color(0, 75, 121));
                break;
            case 3://Boton Agendar una cita
                boton.setFont(new Font("arial", Font.BOLD, 20));
                boton.setAlignmentX(Component.CENTER_ALIGNMENT);
                boton.setBackground(Color.WHITE);
                boton.setForeground(COLOR_AZUL_CORPORATIVO);
                break;
            case 4://Botones de tipo de consulta / sugerencias / quejas / foro
                boton.setFont(new Font("Arial", Font.BOLD, 30));
                boton.setBackground(Color.WHITE);
                boton.setForeground(new Color(0, 75, 121));
                break;
            case 5://Boton Enviar resaltado en azul solido
                boton.setFont(new Font("Arial", Font.BOLD, 20));
                boton.setBackground(new Color(0, 75, 121));
                boton.setForeground(Color.WHITE);
                boton.setAlignmentX(Component.CENTER_ALIGNMENT);
                break;
        }
    }

    //Aqui creo la funcion para etilizar un Combobox
    public void estilizarComboBox(JComboBox combo) {
        combo.setFont(new Font("Arial", Font.BOLD, 16));
        combo.setForeground(new Color(0, 75, 121));
    }

    //Aqui creo la funcion para establecerele algunos parametros al boton
    public void prepararBotonTarjeta(JButton boton, int ancho, int alto) {
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);//Centrado dentro del BoxLayout vertical
        boton.setMaximumSize(new Dimension(ancho, alto));//Evito que el BoxLayout lo estire de mas
        boton.setPreferredSize(new Dimension(ancho, alto));//Mismo ancho para todos los botones de la tarjeta
    }

    //Aqui creo el metodo para habilitar o desabilitar botones del paciente.
    public void habilitarBotonesMenu(JButton botonActivo) {
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
        estilizarBoton(boton, (byte) 1);
        this.cuerpo1.add(boton);
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
        this.btnMisCitas = new JButton("🫂Mis citas");
        this.btnHistorial = new JButton("📚Historial");
        this.btnComentarios = new JButton("🗨️Comentarios");
        this.btnNotificaciones = new JButton("✉️Notificaciones");
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
        this.cuerpo2.setBorder(new EmptyBorder(0, 40, 20, 40));//Padding propio de esta vista

        JPanel panelAgenda = new JPanel();
        panelAgenda.setLayout(new BoxLayout(panelAgenda, BoxLayout.Y_AXIS));
        panelAgenda.setPreferredSize(new Dimension(600, 0));
        panelAgenda.setOpaque(false);
        panelAgenda.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO),
                BorderFactory.createEmptyBorder(90, 0, 0, 0)));

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
                BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO),
                BorderFactory.createEmptyBorder(40, 40, 0, 0)));
        panelContacto.setPreferredSize(new Dimension(Short.MAX_VALUE, 200));

        JLabel lblContactoTitulo = new JLabel("👤 Si tienes dificultades para agendar tu cita, ¡contáctanos!");
        lblContactoTitulo.setFont(new Font("arial", Font.BOLD, 28));
        lblContactoTitulo.setForeground(COLOR_AZUL_CORPORATIVO);
        lblContactoTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblContactoSub = new JLabel("Llama al número de este operario para que podamos ayudarte:");
        lblContactoSub.setFont(new Font("arial", Font.BOLD, 20));
        lblContactoSub.setForeground(COLOR_AZUL_CORPORATIVO);
        lblContactoSub.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblTelefono = new JLabel("📞+57 316 127 3588");
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
        scrollCitas.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollCitas.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollCitas.setOpaque(false);
        scrollCitas.getViewport().setOpaque(false);
        scrollCitas.setBorder(BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO));

        this.cuerpo2.add(labelCitas, BorderLayout.NORTH);
        this.cuerpo2.add(panelAgenda, BorderLayout.EAST);
        this.cuerpo2.add(scrollCitas, BorderLayout.CENTER);
        this.cuerpo2.add(panelContacto, BorderLayout.SOUTH);
        labelCitas = null;//Ya quedo agregado al cuerpo2 entonces libero memoria 

        MetodosPublicos.refrescarVentana(cuerpo2);
        MetodosPublicos.refrescarVentana(panelInfoCitas);
    }

    //qui creo el metodo que me permitiria vizualizar en el JPanel cuerpo2 el apartado de historial
    public void mostrarVistaHistorial() {
        MetodosPublicos.vaciarPanel(cuerpo2);
        MetodosPublicos.vaciarPanel(panelListaHistorial);
        MetodosPublicos.vaciarPanel(panelBotonesLaterales);
        MetodosPublicos.vaciarPanel(panelFiltros);

        this.cuerpo2.setLayout(new BorderLayout());
        this.cuerpo2.setBorder(new EmptyBorder(40, 40, 40, 40));//Padding propio de esta vista

        this.panelBotonesLaterales.add(btnHistorialMedico);
        this.panelBotonesLaterales.add(Box.createRigidArea(new Dimension(0, 30)));
        this.panelBotonesLaterales.add(btnHistorialCitas);

        JLabel lblFiltrar = new JLabel("Filtrar por");
        lblFiltrar.setFont(new Font("Arial", Font.PLAIN, 18));
        lblFiltrar.setForeground(new Color(80, 80, 80));

        this.panelFiltros.add(lblFiltrar);
        this.panelFiltros.add(this.cmbFecha);
        this.panelFiltros.add(this.cmbMedico);
        this.panelFiltros.add(this.cmbEspecialidad);
        lblFiltrar = null;//Ya quedo agregado a panelFiltros

        JScrollPane scrollHistorial = new JScrollPane(panelListaHistorial);
        scrollHistorial.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollHistorial.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollHistorial.setOpaque(false);
        scrollHistorial.getViewport().setOpaque(false);
        scrollHistorial.setBorder(null);

        this.panelHistorial.add(panelFiltros, BorderLayout.NORTH);
        this.panelHistorial.add(scrollHistorial, BorderLayout.CENTER);

        this.cuerpo2.add(panelBotonesLaterales, BorderLayout.WEST);
        this.cuerpo2.add(panelHistorial, BorderLayout.CENTER);

        MetodosPublicos.refrescarVentana(cuerpo2);
        MetodosPublicos.refrescarVentana(panelListaHistorial);
        MetodosPublicos.refrescarVentana(panelBotonesLaterales);
        MetodosPublicos.refrescarVentana(panelFiltros);
    }

    //Aqui creo el metodo que me permitiria contruer el aprtado de comentarios para quejas y sujerencias
    public void construirFormularioComentario() {
        MetodosPublicos.vaciarPanel(panelComentarios);
        JLabel lblAsunto = new JLabel("Asunto");
        lblAsunto.setFont(new Font("Arial", Font.BOLD, 22));
        lblAsunto.setForeground(COLOR_AZUL_CORPORATIVO);
        lblAsunto.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblAsunto.setBorder(new EmptyBorder(20, 0, 0, 0));

        JLabel lblDescripcion = new JLabel("Descripción");
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

    //Aqui creo el metodo que me permitiria vizualizar en el JPanel cuerpo2 el apartado de comentarios
    public void mostrarVistaComentarios() {
        MetodosPublicos.vaciarPanel(cuerpo2);
        MetodosPublicos.vaciarPanel(panelBotonesLaterales);
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

        MetodosPublicos.refrescarVentana(panelComentarios);
        MetodosPublicos.refrescarVentana(panelBotonesLaterales);
        MetodosPublicos.refrescarVentana(cuerpo2);
    }
    //Aqui creo el metodo que me permitiria agregar componentes al panel comentarios
    public void agregarAlPanelComentarios(JComponent c) {
        this.panelComentarios.add(c);
        this.panelComentarios.add(Box.createRigidArea(new Dimension(0, 30)));
        MetodosPublicos.refrescarVentana(panelComentarios);
    }

    //Aqui creo el metodo que permitiria vizualsar en el panel cuerpo2 el apartado de notificaciones
    public void mostrarVistaNotificaciones() {
        MetodosPublicos.vaciarPanel(cuerpo2);
        MetodosPublicos.vaciarPanel(panelContenidoNotificaciones);
        this.cuerpo2.setLayout(new BorderLayout());
        this.cuerpo2.setBorder(new EmptyBorder(40, 40, 40, 40));//Padding propio de esta vista
        this.cuerpo2.add(panelContenidoNotificaciones);

        MetodosPublicos.refrescarVentana(cuerpo2);
        MetodosPublicos.refrescarVentana(panelContenidoNotificaciones);
    }

    public void agregarNotificaciones(JComponent c) {
        this.panelContenidoNotificaciones.add(c);
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

        prepararBotonTarjeta(btnOdontologia, 380, 55);
        prepararBotonTarjeta(btnDermatologia, 380, 55);
        prepararBotonTarjeta(btnMedicoGeneral, 380, 55);

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
            JButton botonMedico = new JButton("👤 " + nombreMedico);//Creo boton con el nombre del medico
            estilizarBoton(botonMedico, (byte) 4);//Agrego estilo al boton
            prepararBotonTarjeta(botonMedico, 380, 55);//Agrego tamano estable 
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

        // Agrandar el calendario
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
        this.cuerpo2.add(panelCalendario, BorderLayout.WEST);
        this.cuerpo2.add(panelHorarios, BorderLayout.EAST);
        descrip1 = null;
        descrip2 = null;
        tituloHorarioDisponibles = null;
        tituloAgendaMientoCita = null;
        settings = null;
        MetodosPublicos.refrescarVentana(panelCalendario);
        MetodosPublicos.refrescarVentana(panelHorarios);
        MetodosPublicos.refrescarVentana(cuerpo2);
    }

    //Aqui creo el metodo que me permitiria agregar compoenentes al panelHorarios
    public void agregarAlPanelHorarios(JComponent c) {
        this.panelHorarios.add(c);
        MetodosPublicos.refrescarVentana(panelHorarios);
    }

}
