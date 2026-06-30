package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import static model.MetodosPublicos.refrescarVentana;
import static model.MetodosPublicos.vaciarPanel;


public class OperarioInterfaz extends PacienteInterfaz{

//    Botones del menu principal
    
    public JButton btnAgendarCitas;
    public JButton btnPagos;
    public JButton btnComentarios;
    public JButton btnMisCitas;
    public JButton btnNotificaciones;
    public JButton btnConsultas;
    
    public JButton btnAgendar;      //Boton para agendar la cita
    public JPanel panelInfoCitas;   //Panel donde de muestran las citas
    
//    Botones de pagos
    public JButton btnVerFactura;
    public JButton btnProcesarPago;
    
    public OperarioInterfaz(String nombre, String nombreInterfaz, String rutaFotoP){
        super(nombre, nombreInterfaz, rutaFotoP);
        
        inicializarComponentesOperario();
        agregarBotonesMenuOperario();
        
//        Vista inicial
        mostrarVistaPrincipalOperario();
        
    }
    
    private void inicializarComponentesOperario() {
        this.panelInfoCitas = new JPanel();
        this.panelInfoCitas.setLayout(new BoxLayout(panelInfoCitas, BoxLayout.Y_AXIS));
        this.panelInfoCitas.setOpaque(false);

        // Se crean los botones
        this.btnAgendar = new JButton("❤️ Agendar una cita");
        this.btnAgendarCitas = new JButton("📅 Agendar Citas");
        this.btnPagos = new JButton("💰 Pagos");
        this.btnComentarios = new JButton("🗨️ Comentarios");
        this.btnMisCitas = new JButton("🫂 Mis Citas");
        this.btnNotificaciones = new JButton("✉️ Notificaciones");
        this.btnConsultas = new JButton("📋 Consultas");

        this.btnVerFactura = new JButton("📄 Ver Factura");
        this.btnProcesarPago = new JButton("💳 Procesar Pago");

        // Botones Estilizados
        estilizarBoton(btnAgendar, (byte) 3);
        estilizarBoton(btnAgendarCitas, (byte) 1);
        estilizarBoton(btnPagos, (byte) 1);
        estilizarBoton(btnComentarios, (byte) 1);
        estilizarBoton(btnMisCitas, (byte) 1);
        estilizarBoton(btnNotificaciones, (byte) 1);
        estilizarBoton(btnConsultas, (byte) 1);
    }
    
    private void agregarBotonesMenuOperario(){
        
        cuerpo1.removeAll();

        agregarBotonCuerpo1(btnAgendarCitas);
        agregarBotonCuerpo1(btnPagos);
        agregarBotonCuerpo1(btnComentarios);
        agregarBotonCuerpo1(btnMisCitas);
        agregarBotonCuerpo1(btnNotificaciones);
        agregarBotonCuerpo1(btnConsultas);

        refrescarVentana(cuerpo1);
    }
    
    public void mostrarVistaPrincipalOperario(){
        vaciarPanel(cuerpo2);
        cuerpo2.setLayout(new BorderLayout(20, 20));
        cuerpo2.setBorder(new EmptyBorder(0, 40, 20, 40));
        
        JLabel labelCitas = new JLabel("Citas programadas vigentes");
        labelCitas.setFont(new Font("arial", Font.BOLD, 28));
        labelCitas.setForeground(COLOR_AZUL_CORPORATIVO);
        
//        Panel donde se agregaran las citas
        vaciarPanel(panelInfoCitas);
        
        JScrollPane scrollCitas = new JScrollPane(panelInfoCitas);
        scrollCitas.setOpaque(false);
        scrollCitas.getViewport().setOpaque(false);
        scrollCitas.setBorder(BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO));
        
        JPanel panelAgenda = crearPanelAgenda();
        JPanel panelContacto = crearPanelContacto();
        
        cuerpo2.add(labelCitas, BorderLayout.NORTH);
        cuerpo2.add(scrollCitas, BorderLayout.CENTER);
        cuerpo2.add(panelAgenda, BorderLayout.EAST);
        cuerpo2.add(panelContacto, BorderLayout.SOUTH);
             
        refrescarVentana(cuerpo2);
    }
    
    public void agregarCita(String tipo, String fecha, String hora, String medico){
        JPanel panelCita = new JPanel();
        panelCita.setLayout(new BoxLayout(panelCita, BoxLayout.X_AXIS));
        panelCita.setOpaque(false);
        panelCita.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        
        JLabel lblTipo = new JLabel(tipo);
        lblTipo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTipo.setForeground(COLOR_AZUL_CORPORATIVO);
        
        panelCita.add(lblTipo);
        panelCita.add(new JLabel("Fecha: " + fecha));
        panelCita.add(new JLabel("Hora: " + hora));
        panelCita.add(new JLabel("Médico: " + medico));
        
        panelInfoCitas.add(panelCita);
        panelInfoCitas.add(Box.createRigidArea(new Dimension(0, 12)));
        
        refrescarVentana(panelInfoCitas);
    }
    
    public JPanel crearPanelAgenda(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(520, 0));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO),
                BorderFactory.createEmptyBorder(70, 40, 40, 40)));
        
        JLabel lbl1 = new JLabel("¡Agenda una cita con nosotros!");
        lbl1.setFont(new Font("arial", Font.BOLD, 20));
        lbl1.setForeground(COLOR_AZUL_CORPORATIVO);
        lbl1.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lbl2 = new JLabel("No dejes tu salud a último momento.");
        lbl2.setFont(new Font("arial", Font.BOLD, 18));
        lbl2.setForeground(COLOR_GRIS_SUBTITULO);
        lbl2.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panel.add(lbl1);
        panel.add(lbl2);
        panel.add(Box.createRigidArea(new Dimension(0, 40)));
        panel.add(btnAgendar);
        
        return panel;
    }
    
    public JPanel crearPanelContacto(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO),
                BorderFactory.createEmptyBorder(25, 40, 25, 40)));
        
        JLabel titulo = new JLabel("👤 Si tienes dificultades para agendar tu cita, ¡contáctanos!");
        titulo.setFont(new Font("arial", Font.BOLD, 24));
        titulo.setForeground(COLOR_AZUL_CORPORATIVO);
        
        JLabel sub = new JLabel("Llama al número de este operario para que podamos ayudarte!");
        sub.setFont(new Font("arial", Font.BOLD, 18));
        sub.setForeground(COLOR_AZUL_CORPORATIVO);
        
        JLabel telefono = new JLabel("📞 +57 316 127 3588");
        telefono.setFont(new Font("arial", Font.BOLD, 28));
        telefono.setForeground(COLOR_AZUL_CORPORATIVO);
        
        panel.add(titulo);
        panel.add(sub);
        panel.add(Box.createRigidArea(new Dimension(0, 12)));
        panel.add(telefono);
        
        return panel;
        
    }
}
