package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import model.MetodosPublicos;
import model.Paciente;
import static view.PacienteInterfaz.COLOR_AZUL_CORPORATIVO;

public class MedicoInterfaz extends PacienteInterfaz {

    public JButton btnMisCitas, btnHistorial, btnComentarios, btnNotificaciones, btnMiAgenda, btnConsultorio, btnBuscar,
            pruebaFicha, simboloRegresarConfirmacionP, btnAsistio, btnNoAsistio, btnGuardarFicha, btnAceptarFicha, btnReagendarCita,
            btnVerDetalles, btnVolverVerDetalles, btnActReagendar, btnNoReagendar;
    public JPanel panelPrincipal;
    public JTextField id;

    ;

    public MedicoInterfaz(String nombreInterfaz,Paciente usuario) {
        super(nombreInterfaz, usuario);

        this.btnMisCitas = new JButton("👥 Mis citas");
        this.btnHistorial = new JButton("☰ Historial");
        this.btnComentarios = new JButton("💬 Comentarios");
        this.btnNotificaciones = new JButton("✉️ Notificaciones");
        this.btnMiAgenda = new JButton("📅 Mi Agenda");
        this.btnConsultorio = new JButton("❤️ Consultorio");
        btnBuscar = new JButton("📤 Buscar");
        pruebaFicha = new JButton("✓ Atender");
        simboloRegresarConfirmacionP = new JButton("←");
        btnAsistio = new JButton("✓ Asistió");
        btnNoAsistio = new JButton("X No asistió");
        btnGuardarFicha = new JButton("✓ Guardar");
        btnAceptarFicha = new JButton("✓ Aceptar");
        btnReagendarCita = new JButton("→ Reagendar");
        btnVerDetalles = new JButton("🔎 Ver detalles");
        btnVolverVerDetalles = new JButton("← Volver");
        btnActReagendar = new JButton("✓ Aceptar");
        btnNoReagendar = new JButton("X Cancelar");

        MetodosPublicos.estilizarBoton(btnBuscar, (byte) 5);
        btnBuscar.setPreferredSize(new Dimension(130, 30));

        MetodosPublicos.estilizarBoton(pruebaFicha, (byte) 1);
        pruebaFicha.setPreferredSize(new Dimension(150, 40));

        MetodosPublicos.estilizarBoton(btnAsistio, (byte) 5);
        btnAsistio.setBackground(COLOR_VERDE_ACENTO);
        btnAsistio.setPreferredSize(new Dimension(150, 40));

        MetodosPublicos.estilizarBoton(btnNoAsistio, (byte) 5);
        btnNoAsistio.setBackground(Color.RED);
        btnNoAsistio.setPreferredSize(new Dimension(150, 40));
        pruebaFicha.setPreferredSize(new Dimension(150, 40));

        MetodosPublicos.estilizarBoton(btnGuardarFicha, (byte) 5);

        MetodosPublicos.estilizarBoton(btnAceptarFicha, (byte) 5);
        btnAceptarFicha.setPreferredSize(new Dimension(150, 40));

        MetodosPublicos.estilizarBoton(btnReagendarCita, (byte) 1);
        btnReagendarCita.setPreferredSize(new Dimension(150, 40));

        MetodosPublicos.estilizarBoton(btnVerDetalles, (byte) 1);
        btnVerDetalles.setPreferredSize(new Dimension(150, 40));
        
        MetodosPublicos.estilizarBoton(btnVolverVerDetalles, (byte) 1);
        btnVolverVerDetalles.setPreferredSize(new Dimension(150, 40));

        MetodosPublicos.estilizarBoton(btnActReagendar, (byte) 5);
        btnActReagendar.setBackground(COLOR_VERDE_ACENTO);
        btnActReagendar.setPreferredSize(new Dimension(150, 40));

        MetodosPublicos.estilizarBoton(btnNoReagendar, (byte) 5);
        btnNoReagendar.setBackground(Color.RED);
        btnNoReagendar.setPreferredSize(new Dimension(150, 40));
        
//        super.agregarBotonCuerpo1(btnMisCitas);
//        super.agregarBotonCuerpo1(btnHistorial);
//        super.agregarBotonCuerpo1(btnComentarios);
//        super.agregarBotonCuerpo1(btnNotificaciones);
        super.agregarBotonCuerpo1(btnMiAgenda);
        super.agregarBotonCuerpo1(btnConsultorio);

        botonesRegresar(simboloRegresarConfirmacionP);

        id = new JTextField();
        id.setFont(new Font("Arial", Font.PLAIN, 18));
        id.setPreferredSize(new Dimension(240, 30));
        id.setBorder(BorderFactory.createLineBorder(COLOR_VERDE_ACENTO));

        this.panelPrincipal = new JPanel();
        this.panelPrincipal.setOpaque(false);
    }

    @Override
    public void habilitarBotonesMenu(JButton botonActivo) {
       super.habilitarBotonesMenu(botonActivo);
        if (botonActivo != btnMiAgenda && !btnMiAgenda.isEnabled()) {
            this.btnMiAgenda.setEnabled(true);
        }
        if (botonActivo != btnConsultorio && !btnConsultorio.isEnabled()) {
            this.btnConsultorio.setEnabled(true);
        }
    }

    public void botonesRegresar(JButton botonRegresar) {
        botonRegresar.setFont(new Font("arial", Font.BOLD, 40));
        botonRegresar.setForeground(COLOR_VERDE_ACENTO);
        botonRegresar.setContentAreaFilled(false);
        botonRegresar.setBorderPainted(false);
        botonRegresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public void campo(int tamañoFuente, int ancho, int largo, JTextField campo) {
        campo.setFont(new Font("Arial", Font.PLAIN, tamañoFuente));
        campo.setMinimumSize(new Dimension(ancho, largo));
        campo.setMaximumSize(new Dimension(ancho, largo));
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);
        campo.setBorder(BorderFactory.createLineBorder(COLOR_VERDE_ACENTO));
    }

    public void campoComboBox(int tamañoFuente, int ancho, int largo, JComboBox campo) {
        campo.setFont(new Font("Arial", Font.PLAIN, tamañoFuente));
        campo.setMinimumSize(new Dimension(ancho, largo));
        campo.setMaximumSize(new Dimension(ancho, largo));
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);
        campo.setBorder(BorderFactory.createLineBorder(COLOR_VERDE_ACENTO));
        campo.setBackground(Color.WHITE);
    }

    @Override
    public void mostrarVistaHistorial() {
        super.mostrarVistaHistorial();
    }

    public void mostrarVistaMiAgenda() {
        MetodosPublicos.vaciarPanel(cuerpo2);
        MetodosPublicos.vaciarPanel(panelPrincipal);

        //se crea el panel donde va a contener las citas asignadas vigentes al medico
        this.panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        this.panelPrincipal.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO),
                BorderFactory.createEmptyBorder(0, 30, 5, 30)));
        this.panelPrincipal.setPreferredSize(new Dimension(1455, 563));
        this.cuerpo2.setLayout(new BorderLayout());
        this.cuerpo2.setBorder(new EmptyBorder(10, 40, 40, 40));

        JScrollPane scrollCitas = new JScrollPane(panelPrincipal);
        scrollCitas.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollCitas.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollCitas.setOpaque(false);
        scrollCitas.getViewport().setOpaque(false);
        scrollCitas.setBorder(null);

        JLabel tituloMiAgenda = new JLabel("Citas programadas vigentes");
        tituloMiAgenda.setFont(new Font("arial", Font.BOLD, 28));
        tituloMiAgenda.setForeground(COLOR_AZUL_CORPORATIVO);

        this.cuerpo2.add(tituloMiAgenda, BorderLayout.NORTH);
        this.cuerpo2.add(scrollCitas, BorderLayout.SOUTH);

        MetodosPublicos.refrescarVentana(panelPrincipal);
        MetodosPublicos.refrescarVentana(cuerpo2);
        MetodosPublicos.refrescarVentana(scrollCitas);
    }

    public void mostrarVistaConsultorio() {
        MetodosPublicos.vaciarPanel(cuerpo2);
        MetodosPublicos.vaciarPanel(panelPrincipal);

        //se crea el panel donde va a contener las citas vigentes del paciente al buscarlo
        this.panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        this.panelPrincipal.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO),
                BorderFactory.createEmptyBorder(10, 30, 5, 30)));
        this.panelPrincipal.setPreferredSize(new Dimension(1455, 500));
        this.cuerpo2.setLayout(new BorderLayout());
        this.cuerpo2.setBorder(new EmptyBorder(10, 40, 40, 40));

        //se crea otro panel para añadir el boton de buscar junto con el campo id ya que si se añade al panel cuerpo2
        //estos campos de deforman y toman todo el panel porque el panel tiene un borderlayout
        JPanel panelBuscar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBuscar.setBorder(new EmptyBorder(20, 0, 10, 0));
        panelBuscar.setOpaque(false);

        JLabel numeroId = new JLabel("Número de identificación");
        numeroId.setFont(new Font("arial", Font.PLAIN, 15));
        numeroId.setBorder(new EmptyBorder(0, 0, 0, Short.MAX_VALUE));

        JScrollPane scrollCitas = new JScrollPane(panelPrincipal);
        scrollCitas.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollCitas.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollCitas.setOpaque(false);
        scrollCitas.getViewport().setOpaque(false);
        scrollCitas.setBorder(null);

        JLabel tituloConsultorio = new JLabel("Atender Paciente");
        tituloConsultorio.setFont(new Font("arial", Font.BOLD, 28));
        tituloConsultorio.setForeground(COLOR_AZUL_CORPORATIVO);

        this.cuerpo2.add(tituloConsultorio, BorderLayout.NORTH);
        this.cuerpo2.add(scrollCitas, BorderLayout.SOUTH);
        panelBuscar.add(numeroId);
        panelBuscar.add(id);
        panelBuscar.add(btnBuscar);
        this.cuerpo2.add(panelBuscar);

        MetodosPublicos.refrescarVentana(panelPrincipal);
        MetodosPublicos.refrescarVentana(cuerpo2);
        MetodosPublicos.refrescarVentana(scrollCitas);
    }

    public void mostrarVistaConfirmacionAsistencia() {
        MetodosPublicos.vaciarPanel(cuerpo2);
        MetodosPublicos.vaciarPanel(panelPrincipal);

        //se crea el panel donde se va a mostrar la confirmacion del paciente
        this.panelPrincipal.setLayout(new FlowLayout());
        this.panelPrincipal.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO),
                BorderFactory.createEmptyBorder(20, 60, 20, 80)));
        this.panelPrincipal.setPreferredSize(new Dimension(800, 500));
        this.cuerpo2.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 75));

        simboloRegresarConfirmacionP.setBorder(new EmptyBorder(50, 0, 0, 0));

        JLabel tituloConfirmacion = new JLabel("Confirmar asistencia");
        tituloConfirmacion.setFont(new Font("arial", Font.BOLD, 35));
        tituloConfirmacion.setForeground(COLOR_AZUL_CORPORATIVO);
        tituloConfirmacion.setBorder(new EmptyBorder(50, 0, 0, 0));

        //se utiliza html para usar el br que es un salto de linea en el jlabel y el center es para centrar el texto
        JLabel textoConfirmacion = new JLabel("<html><center>Por favor, seleccione si el paciente<br>asistió o no a la cita</center></html>");
        textoConfirmacion.setFont(new Font("arial", Font.BOLD, 32));
        textoConfirmacion.setForeground(COLOR_GRIS_SUBTITULO);
        textoConfirmacion.setBorder(new EmptyBorder(70, 90, 50, 90));

        //se crea un boton invisible para separar el boton de asistio y no asistio
        JButton separadorBotones = new JButton();
        separadorBotones.setContentAreaFilled(false);
        separadorBotones.setBorderPainted(false);

        this.panelPrincipal.add(simboloRegresarConfirmacionP);
        this.panelPrincipal.add(tituloConfirmacion);
        this.panelPrincipal.add(textoConfirmacion);
        this.panelPrincipal.add(btnAsistio);
        this.panelPrincipal.add(separadorBotones);
        this.panelPrincipal.add(btnNoAsistio);
        this.cuerpo2.add(panelPrincipal);

        MetodosPublicos.refrescarVentana(panelPrincipal);
        MetodosPublicos.refrescarVentana(cuerpo2);
    }

    public void mostrarVistaFichaClinica() {
        MetodosPublicos.vaciarPanel(cuerpo2);
        MetodosPublicos.vaciarPanel(panelPrincipal);

        JPanel titulo = new JPanel();
        titulo.setLayout(new FlowLayout());
        titulo.setOpaque(false);

        //se crea el panel donde va a contener la informacion previa del paciente a realizar la ficha tecnica
        this.panelPrincipal.setLayout(new BorderLayout());
        this.panelPrincipal.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO),
                BorderFactory.createEmptyBorder(0, 30, 5, 30)));
        this.panelPrincipal.setPreferredSize(new Dimension(725, 563));
        this.cuerpo2.setLayout(new BorderLayout(30, 20));

        //se crea el otro panel donde va a contener los campos a ingresar por el medico para la ficha clinica
        JPanel panelDiagnostico = new JPanel();
        panelDiagnostico.setLayout(new BoxLayout(panelDiagnostico, BoxLayout.Y_AXIS));
        panelDiagnostico.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO),
                BorderFactory.createEmptyBorder(0, 30, 5, 30)));
        panelDiagnostico.setPreferredSize(new Dimension(725, 563));
        panelDiagnostico.setOpaque(false);

        //se crea dos paneles para dividir la informacion del paciente en dos columnas, derecha e izquierda
        JLabel tituloFichaClinica = new JLabel("Ficha Clínica");
        tituloFichaClinica.setFont(new Font("arial", Font.BOLD, 28));
        tituloFichaClinica.setForeground(COLOR_AZUL_CORPORATIVO);

        JPanel infoIzquierda = new JPanel();
        infoIzquierda.setLayout(new BoxLayout(infoIzquierda, BoxLayout.Y_AXIS));
        infoIzquierda.setPreferredSize(new Dimension(320, 100));
        infoIzquierda.setOpaque(false);

        JPanel infoDerecha = new JPanel();
        infoDerecha.setLayout(new BoxLayout(infoDerecha, BoxLayout.Y_AXIS));
        infoDerecha.setPreferredSize(new Dimension(320, 100));
        infoDerecha.setOpaque(false);

        //datos del paciente en la columna izquierda
        JLabel primerN = new JLabel("Primer nombre");
        primerN.setFont(new Font("arial", Font.PLAIN, 15));
        primerN.setBorder(new EmptyBorder(15, 0, 0, 0));

        JTextField campoPrimerN = new JTextField();
        campo(18, 270, 35, campoPrimerN);

        JLabel segundoN = new JLabel("Segundo nombre");
        segundoN.setFont(new Font("arial", Font.PLAIN, 15));
        segundoN.setBorder(new EmptyBorder(50, 0, 0, 0));

        JTextField campoSegundoN = new JTextField();
        campo(18, 270, 35, campoSegundoN);

        JLabel correo = new JLabel("Correo electrónico");
        correo.setFont(new Font("arial", Font.PLAIN, 15));
        correo.setBorder(new EmptyBorder(50, 0, 0, 0));

        JTextField campoCorreo = new JTextField();
        campo(18, 270, 35, campoCorreo);

        JLabel fechaN = new JLabel("Fecha de nacimiento");
        fechaN.setFont(new Font("arial", Font.PLAIN, 15));
        fechaN.setBorder(new EmptyBorder(50, 0, 0, 0));

        JTextField campoFechaN = new JTextField();
        campo(18, 270, 35, campoFechaN);

        JLabel tipoId = new JLabel("Tipo de identificación");
        tipoId.setFont(new Font("arial", Font.PLAIN, 15));
        tipoId.setBorder(new EmptyBorder(50, 0, 0, 0));

        String listaIds[] = {"", "Registro civil", "Tarjeta de identidad", "Cédula"};
        JComboBox campoTipoId = new JComboBox(listaIds);
        MetodosPublicos.crearComboEstilizado(campoTipoId);
        campoComboBox(18, 270, 35, campoTipoId);

        //datos del paciente en la columna derecha
        JLabel primerA = new JLabel("Primer apellido");
        primerA.setFont(new Font("arial", Font.PLAIN, 15));
        primerA.setBorder(new EmptyBorder(8, 0, 0, 0));
        primerA.setBorder(new EmptyBorder(15, 0, 0, 0));

        JTextField campoPrimerA = new JTextField();
        campo(18, 270, 35, campoPrimerA);

        JLabel segundoA = new JLabel("Segundo apellido");
        segundoA.setFont(new Font("arial", Font.PLAIN, 15));
        segundoA.setBorder(new EmptyBorder(50, 0, 0, 0));

        JTextField campoSegundoA = new JTextField();
        campo(18, 270, 35, campoSegundoA);

        JLabel numeroT = new JLabel("Número de telefono");
        numeroT.setFont(new Font("arial", Font.PLAIN, 15));
        numeroT.setBorder(new EmptyBorder(50, 0, 0, 0));

        JTextField campoNumeroT = new JTextField();
        campo(18, 270, 35, campoNumeroT);

        JLabel sexoBio = new JLabel("Sexo biológico");
        sexoBio.setFont(new Font("arial", Font.PLAIN, 15));
        sexoBio.setBorder(new EmptyBorder(50, 0, 0, 0));

        String listaSB[] = {"", "Femenino", "Masculino"};
        JComboBox campoSexoBio = new JComboBox(listaSB);
        MetodosPublicos.crearComboEstilizado(campoSexoBio);
        campoComboBox(18, 270, 35, campoSexoBio);

        JLabel numeroId = new JLabel("Número de identificación");
        numeroId.setFont(new Font("arial", Font.PLAIN, 15));
        numeroId.setBorder(new EmptyBorder(50, 0, 0, 0));

        JTextField campoNumeroId = new JTextField();
        campo(18, 270, 35, campoNumeroId);

        //datos a ingresar de la consulta
        JLabel diagnostico = new JLabel("Diagnóstico");
        diagnostico.setFont(new Font("arial", Font.BOLD, 24));
        diagnostico.setBorder(new EmptyBorder(35, 0, 10, 0));
        diagnostico.setAlignmentX(Component.LEFT_ALIGNMENT);
        diagnostico.setForeground(COLOR_AZUL_CORPORATIVO);

        JTextArea campoDiagnostico = new JTextArea();
        campoDiagnostico.setFont(new Font("Arial", Font.PLAIN, 18));
        campoDiagnostico.setLineWrap(true);
        campoDiagnostico.setWrapStyleWord(true);
        campoDiagnostico.setBorder(BorderFactory.createLineBorder(COLOR_VERDE_ACENTO));
        JScrollPane miScroll = new JScrollPane(campoDiagnostico);
        miScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        miScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        miScroll.setMaximumSize(new Dimension(658, 140));
        miScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        miScroll.setOpaque(false);
        miScroll.getViewport().setOpaque(false);
        miScroll.setBorder(null);

        JLabel medicamento = new JLabel("Medicamento");
        medicamento.setFont(new Font("arial", Font.BOLD, 24));
        medicamento.setBorder(new EmptyBorder(10, 0, 10, 0));
        medicamento.setAlignmentX(Component.LEFT_ALIGNMENT);
        medicamento.setForeground(COLOR_AZUL_CORPORATIVO);

        String listaMed[] = {""};
        JComboBox campoMedicamento = new JComboBox(listaMed);
        MetodosPublicos.crearComboEstilizado(campoMedicamento);
        campoComboBox(18, 270, 35, campoMedicamento);
        campoMedicamento.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel observaciones = new JLabel("Observaciones");
        observaciones.setFont(new Font("arial", Font.BOLD, 24));
        observaciones.setBorder(new EmptyBorder(10, 0, 10, 0));
        observaciones.setAlignmentX(Component.LEFT_ALIGNMENT);
        observaciones.setForeground(COLOR_AZUL_CORPORATIVO);

        JTextArea campoObservaciones = new JTextArea();
        campoObservaciones.setFont(new Font("Arial", Font.PLAIN, 18));
        campoObservaciones.setLineWrap(true);
        campoObservaciones.setWrapStyleWord(true);
        campoObservaciones.setBorder(BorderFactory.createLineBorder(COLOR_VERDE_ACENTO));
        JScrollPane miScroll2 = new JScrollPane(campoObservaciones);
        miScroll2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        miScroll2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        miScroll2.setMaximumSize(new Dimension(658, 140));
        miScroll2.setAlignmentX(Component.LEFT_ALIGNMENT);
        miScroll2.setOpaque(false);
        miScroll2.getViewport().setOpaque(false);
        miScroll2.setBorder(null);

        //se crea otro panel que contenga el boton guardar de la ficha, ya que este va en contra del alineamiento
        //izquierdo que se ha estado manteniendo, es necesario crearlo para que el boton quede a la derecha
        //se aplica el left aligment como los demas para que respete el orden que se ha estado haciendo
        //con los demas elementos aunque el boton ira a la derecha
        JPanel panelDiagnosticoBoton = new JPanel();
        panelDiagnosticoBoton.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panelDiagnosticoBoton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        panelDiagnosticoBoton.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelDiagnosticoBoton.setOpaque(false);

        titulo.add(tituloFichaClinica);
        this.cuerpo2.add(titulo, BorderLayout.NORTH);
        this.panelPrincipal.add(new Titulo("Información del ", "paciente").getPanelTitulo(), BorderLayout.NORTH);
        infoIzquierda.add(primerN);
        infoIzquierda.add(campoPrimerN);
        infoIzquierda.add(segundoN);
        infoIzquierda.add(campoSegundoN);
        infoIzquierda.add(correo);
        infoIzquierda.add(campoCorreo);
        infoIzquierda.add(fechaN);
        infoIzquierda.add(campoFechaN);
        infoIzquierda.add(tipoId);
        infoIzquierda.add(campoTipoId);
        infoDerecha.add(primerA);
        infoDerecha.add(campoPrimerA);
        infoDerecha.add(segundoA);
        infoDerecha.add(campoSegundoA);
        infoDerecha.add(numeroT);
        infoDerecha.add(campoNumeroT);
        infoDerecha.add(sexoBio);
        infoDerecha.add(campoSexoBio);
        infoDerecha.add(numeroId);
        infoDerecha.add(campoNumeroId);
        panelDiagnostico.add(diagnostico);
        panelDiagnostico.add(miScroll);
        panelDiagnostico.add(medicamento);
        panelDiagnostico.add(campoMedicamento);
        panelDiagnostico.add(observaciones);
        panelDiagnostico.add(miScroll2);
        panelDiagnosticoBoton.add(btnGuardarFicha);
        panelDiagnostico.add(panelDiagnosticoBoton);
        this.panelPrincipal.add(infoIzquierda, BorderLayout.WEST);
        this.panelPrincipal.add(infoDerecha, BorderLayout.EAST);
        this.cuerpo2.add(panelPrincipal, BorderLayout.WEST);
        this.cuerpo2.add(panelDiagnostico, BorderLayout.EAST);

        MetodosPublicos.refrescarVentana(panelPrincipal);
        MetodosPublicos.refrescarVentana(cuerpo2);
    }

    public void mostrarVistaConfirmacionFichaGuardada() {
        MetodosPublicos.vaciarPanel(cuerpo2);
        MetodosPublicos.vaciarPanel(panelPrincipal);

        //se crea el panel donde se va a mostrar la confirmacion de la ficha clinica guardada
        this.panelPrincipal.setLayout(new FlowLayout());
        this.panelPrincipal.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO),
                BorderFactory.createEmptyBorder(20, 60, 20, 80)));
        this.panelPrincipal.setPreferredSize(new Dimension(800, 500));
        this.cuerpo2.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 75));

        JLabel titulo1 = new JLabel("Ficha");
        titulo1.setFont(new Font("Arial", Font.BOLD, 35));
        titulo1.setForeground(new Color(0, 79, 124));
        titulo1.setBorder(new EmptyBorder(50, 0, 0, 0));

        JLabel titulo2 = new JLabel("clínica");
        titulo2.setForeground(new Color(0, 194, 177));
        titulo2.setFont(new Font("Arial", Font.BOLD, 35));
        titulo2.setBorder(new EmptyBorder(50, 0, 0, 0));

        //se utiliza html para usar el br que es un salto de linea en el jlabel y el center es para centrar el texto
        JLabel textoConfirmacion = new JLabel("<html><center>La ficha clínica del paciente ha sido guardada con exito!<br>Verifique el registro consultando el historial médico<br>en el apartado Historial -> Historial Médico Paciente</center></html>");
        textoConfirmacion.setFont(new Font("arial", Font.BOLD, 25));
        textoConfirmacion.setForeground(COLOR_GRIS_SUBTITULO);
        textoConfirmacion.setBorder(new EmptyBorder(70, 90, 50, 90));

        this.panelPrincipal.add(titulo1);
        this.panelPrincipal.add(titulo2);
        this.panelPrincipal.add(textoConfirmacion);
        this.panelPrincipal.add(btnAceptarFicha);
        this.cuerpo2.add(panelPrincipal);

        MetodosPublicos.refrescarVentana(panelPrincipal);
        MetodosPublicos.refrescarVentana(cuerpo2);
    }

    public void citaVistaMiAgenda() {
        //se crea el panel donde va a contener la cita de un paciente
        JPanel panelCita = new JPanel();
        panelCita.setLayout(new BorderLayout());
        panelCita.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5,0, 0,0),
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY)));
        panelCita.setMaximumSize(new Dimension(Short.MAX_VALUE, 120));
        panelCita.setOpaque(false);

        //se crea dos paneles para dividir la informacion del paciente en dos columnas, derecha e izquierda
        JPanel infoIzquierda = new JPanel();
        infoIzquierda.setLayout(new FlowLayout(FlowLayout.LEFT));
        infoIzquierda.setPreferredSize(new Dimension(320, 100));
        infoIzquierda.setOpaque(false);
        
        JPanel infoDerecha = new JPanel();
        infoDerecha.setLayout(new FlowLayout(FlowLayout.CENTER));
        infoDerecha.setPreferredSize(new Dimension(320, 100));
        infoDerecha.setBorder(new EmptyBorder(30, 0,0,0));
        infoDerecha.setOpaque(false);

        JLabel titulo1 = new JLabel("Odontologica");
        titulo1.setForeground(new Color(0, 194, 177));
        titulo1.setFont(new Font("Arial", Font.BOLD, 20));
        titulo1.setBorder(new EmptyBorder(0,0,0,100));

        JLabel titulo2 = new JLabel("Cita");
        titulo2.setFont(new Font("Arial", Font.BOLD, 20));
        titulo2.setForeground(new Color(0, 79, 124));

        JLabel fecha = new JLabel("Fecha: ");
        fecha.setFont(new Font("arial", Font.PLAIN, 15));

        JTextField campoFecha = new JTextField();
        campoFecha.setFont(new Font("Arial", Font.PLAIN, 15));
        campoFecha.setPreferredSize(new Dimension(240, 20));
        campoFecha.setBorder(null);

        JLabel hora = new JLabel("Hora: ");
        hora.setFont(new Font("arial", Font.PLAIN, 15));

        JTextField campoHora = new JTextField();
        campoHora.setFont(new Font("Arial", Font.PLAIN, 15));
        campoHora.setPreferredSize(new Dimension(240, 20));
        campoHora.setBorder(null);

        JLabel paciente = new JLabel("Paciente: ");
        paciente.setFont(new Font("arial", Font.PLAIN, 15));

        JTextField campoPaciente = new JTextField();
        campoPaciente.setFont(new Font("Arial", Font.PLAIN, 15));
        campoPaciente.setPreferredSize(new Dimension(240, 20));
        campoPaciente.setBorder(null);

        infoIzquierda.add(titulo2);
        infoIzquierda.add(titulo1);
        infoIzquierda.add(fecha);
        infoIzquierda.add(campoFecha);
        infoIzquierda.add(hora);
        infoIzquierda.add(campoHora);
        infoIzquierda.add(paciente);
        infoIzquierda.add(campoPaciente);
        infoDerecha.add(this.btnVerDetalles);
        infoDerecha.add(this.btnReagendarCita);
        panelCita.add(infoDerecha,BorderLayout.EAST);
        panelCita.add(infoIzquierda,BorderLayout.WEST);
        this.panelPrincipal.add(panelCita);
    }
    
    public void mostrarVistaConfirmacionReagendar(){
        MetodosPublicos.vaciarPanel(cuerpo2);
        MetodosPublicos.vaciarPanel(panelPrincipal);

        //se crea el panel donde se va a mostrar la confirmacion del reagendamiento de la cita de un paciente
        this.panelPrincipal.setLayout(new FlowLayout());
        this.panelPrincipal.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO),
                BorderFactory.createEmptyBorder(20, 60, 20, 80)));
        this.panelPrincipal.setPreferredSize(new Dimension(800, 500));
        this.cuerpo2.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 75));

        simboloRegresarConfirmacionP.setBorder(new EmptyBorder(50, 0, 0, 0));

        JLabel tituloConfirmacion = new JLabel("Confirmar reagendamiento");
        tituloConfirmacion.setFont(new Font("arial", Font.BOLD, 35));
        tituloConfirmacion.setForeground(COLOR_AZUL_CORPORATIVO);
        tituloConfirmacion.setBorder(new EmptyBorder(50, 0, 0, 0));

        //se utiliza html para usar el br que es un salto de linea en el jlabel y el center es para centrar el texto
        JLabel textoConfirmacion = new JLabel("<html><center>Esta seguro que desea reagendar<br>esta cita médica?</center></html>");
        textoConfirmacion.setFont(new Font("arial", Font.BOLD, 32));
        textoConfirmacion.setForeground(COLOR_GRIS_SUBTITULO);
        textoConfirmacion.setBorder(new EmptyBorder(50, 90, 50, 90));

        //se crea un boton invisible para separar el boton de asistio y no asistio
        JButton separadorBotones = new JButton();
        separadorBotones.setContentAreaFilled(false);
        separadorBotones.setBorderPainted(false);

        this.panelPrincipal.add(tituloConfirmacion);
        this.panelPrincipal.add(textoConfirmacion);
        this.panelPrincipal.add(btnActReagendar);
        this.panelPrincipal.add(separadorBotones);
        this.panelPrincipal.add(btnNoReagendar);
        this.cuerpo2.add(panelPrincipal);

        MetodosPublicos.refrescarVentana(panelPrincipal);
        MetodosPublicos.refrescarVentana(cuerpo2);
    }
    
    public void citaVistaConsultorio() {
        //se crea el panel donde va a contener la cita de un paciente
        JPanel panelCita = new JPanel();
        panelCita.setLayout(new BorderLayout());
        panelCita.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5,0, 0,0),
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY)));
        panelCita.setMaximumSize(new Dimension(Short.MAX_VALUE, 120));
        panelCita.setOpaque(false);

        //se crea dos paneles para dividir la informacion del paciente en dos columnas, derecha e izquierda
        JPanel infoIzquierda = new JPanel();
        infoIzquierda.setLayout(new FlowLayout(FlowLayout.LEFT));
        infoIzquierda.setPreferredSize(new Dimension(320, 100));
        infoIzquierda.setOpaque(false);
        
        JPanel infoDerecha = new JPanel();
        infoDerecha.setLayout(new FlowLayout(FlowLayout.CENTER));
        infoDerecha.setPreferredSize(new Dimension(380, 100));
        infoDerecha.setBorder(new EmptyBorder(15,0,0,0));
        infoDerecha.setOpaque(false);

        JLabel titulo1 = new JLabel("Odontologica");
        titulo1.setForeground(new Color(0, 194, 177));
        titulo1.setFont(new Font("Arial", Font.BOLD, 20));
        titulo1.setBorder(new EmptyBorder(0,0,0,100));

        JLabel titulo2 = new JLabel("Cita");
        titulo2.setFont(new Font("Arial", Font.BOLD, 20));
        titulo2.setForeground(new Color(0, 79, 124));

        JLabel fecha = new JLabel("Fecha: ");
        fecha.setFont(new Font("arial", Font.PLAIN, 15));

        JTextField campoFecha = new JTextField();
        campoFecha.setFont(new Font("Arial", Font.PLAIN, 15));
        campoFecha.setPreferredSize(new Dimension(240, 20));
        campoFecha.setBorder(null);

        JLabel hora = new JLabel("Hora: ");
        hora.setFont(new Font("arial", Font.PLAIN, 15));

        JTextField campoHora = new JTextField();
        campoHora.setFont(new Font("Arial", Font.PLAIN, 15));
        campoHora.setPreferredSize(new Dimension(240, 20));
        campoHora.setBorder(null);

        JLabel paciente = new JLabel("Paciente: ");
        paciente.setFont(new Font("arial", Font.PLAIN, 15));

        JTextField campoPaciente = new JTextField();
        campoPaciente.setFont(new Font("Arial", Font.PLAIN, 15));
        campoPaciente.setPreferredSize(new Dimension(240, 20));
        campoPaciente.setBorder(null);
        
        JLabel titulo = new JLabel("Estado de");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(new Color(0, 79, 124));
        titulo.setBorder(new EmptyBorder(0,150,0,0));

        JLabel tituloS = new JLabel("cita");
        tituloS.setForeground(new Color(0, 194, 177));
        tituloS.setFont(new Font("Arial", Font.BOLD, 20));
        
        JButton btnEstado = new JButton("☑ En sala de espera");
        MetodosPublicos.estilizarBoton(btnEstado, (byte) 1);
        btnEstado.setPreferredSize(new Dimension(190, 40));
        
        infoIzquierda.add(titulo2);
        infoIzquierda.add(titulo1);
        infoIzquierda.add(fecha);
        infoIzquierda.add(campoFecha);
        infoIzquierda.add(hora);
        infoIzquierda.add(campoHora);
        infoIzquierda.add(paciente);
        infoIzquierda.add(campoPaciente);
        infoDerecha.add(titulo);
        infoDerecha.add(tituloS);
        infoDerecha.add(this.pruebaFicha);
        infoDerecha.add(btnEstado);
        panelCita.add(infoDerecha,BorderLayout.EAST);
        panelCita.add(infoIzquierda,BorderLayout.WEST);
        this.panelPrincipal.add(panelCita);
    }
    
    public void mostrarDetallesCita(){
        MetodosPublicos.vaciarPanel(cuerpo2);
        MetodosPublicos.vaciarPanel(panelPrincipal);
        
        //se crea el panel donde va a contener la cita de un paciente detalladamente
        this.panelPrincipal.setLayout(new BorderLayout());
        this.panelPrincipal.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO),
                BorderFactory.createEmptyBorder(20, 60, 20, 80)));
        this.panelPrincipal.setPreferredSize(new Dimension(800, 500));
        this.cuerpo2.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 75));

        //se crea un panel para contener el titulo y la linea debajo de este
        JPanel panelTitulo = new JPanel();
        panelTitulo.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelTitulo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5,0, 20,0),
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY)));
        panelTitulo.setMaximumSize(new Dimension(Short.MAX_VALUE, 70));
        panelTitulo.setOpaque(false);
        
        JPanel infoIzquierda = new JPanel();
        infoIzquierda.setLayout(new FlowLayout(FlowLayout.LEFT));
        infoIzquierda.setPreferredSize(new Dimension(300, 100));
        infoIzquierda.setOpaque(false);
        
        //se crea los campos en los cuales se va a mostrar la informacion de la cita de un paciente 
        
        JLabel titulo1 = new JLabel("Cita");
        titulo1.setForeground(new Color(0, 194, 177));
        titulo1.setFont(new Font("Arial", Font.BOLD, 35));

        JLabel titulo2 = new JLabel("Odontológica");
        titulo2.setFont(new Font("Arial", Font.BOLD, 35));
        titulo2.setForeground(new Color(0, 79, 124));
        
        JLabel fecha = new JLabel("Fecha: ");
        fecha.setFont(new Font("arial", Font.PLAIN, 20));
        fecha.setForeground(new Color(0, 79, 124));
        
        JTextField campoFecha = new JTextField();
        campoFecha.setFont(new Font("Arial", Font.PLAIN, 20));
        campoFecha.setPreferredSize(new Dimension(240, 20));
        campoFecha.setBorder(null);

        JLabel hora = new JLabel("Hora: ");
        hora.setFont(new Font("arial", Font.PLAIN, 20));
        hora.setBorder(new EmptyBorder(30, 0,0, 0));
        hora.setForeground(new Color(0, 79, 124));
       
        JTextField campoHora = new JTextField();
        campoHora.setFont(new Font("Arial", Font.PLAIN, 20));
        campoHora.setPreferredSize(new Dimension(240, 20));
        campoHora.setBorder(null);

        JLabel paciente = new JLabel("Paciente: ");
        paciente.setFont(new Font("arial", Font.PLAIN, 20));
        paciente.setBorder(new EmptyBorder(30, 0,0, 0));
        paciente.setForeground(new Color(0, 79, 124));

        JTextField campoPaciente = new JTextField();
        campoPaciente.setFont(new Font("Arial", Font.PLAIN, 20));
        campoPaciente.setPreferredSize(new Dimension(240, 20));
        campoPaciente.setBorder(null);
        
        JLabel numeroId = new JLabel("Número de identificación");
        numeroId.setFont(new Font("arial", Font.PLAIN, 20));
        numeroId.setBorder(new EmptyBorder(30, 0,0, 0));
        numeroId.setForeground(new Color(0, 79, 124));

        JTextField campoNumeroId = new JTextField();
        campoNumeroId.setFont(new Font("Arial", Font.PLAIN, 20));
        campoNumeroId.setPreferredSize(new Dimension(240, 20));
        campoNumeroId.setBorder(null);
        
        //se crea un panel en el que contiene el boton de volver para que el boton tenga su respectivo tamaño y no el
        //de todo el contenedor padre
        JPanel panelBtnVolver = new JPanel();
        panelBtnVolver.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelBtnVolver.setOpaque(false);
        
        panelTitulo.add(titulo1);
        panelTitulo.add(titulo2);
        infoIzquierda.add(fecha,BorderLayout.WEST);
        infoIzquierda.add(campoFecha);
        infoIzquierda.add(hora);
        infoIzquierda.add(campoHora);
        infoIzquierda.add(paciente);
        infoIzquierda.add(campoPaciente);
        infoIzquierda.add(numeroId);
        infoIzquierda.add(campoNumeroId);
        panelBtnVolver.add(this.btnVolverVerDetalles);
        this.panelPrincipal.add(panelTitulo,BorderLayout.NORTH);
        this.panelPrincipal.add(infoIzquierda,BorderLayout.WEST);
        this.panelPrincipal.add(panelBtnVolver,BorderLayout.SOUTH);
        this.cuerpo2.add(panelPrincipal);
        
        MetodosPublicos.refrescarVentana(panelPrincipal);
        MetodosPublicos.refrescarVentana(cuerpo2);
    }
}