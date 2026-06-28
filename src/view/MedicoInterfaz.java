package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import model.MetodosPublicos;
import static view.PacienteInterfaz.COLOR_AZUL_CORPORATIVO;

public class MedicoInterfaz extends PacienteInterfaz {

    public JButton btnMisCitas, btnHistorial, btnComentarios, btnNotificaciones, btnMiAgenda, btnConsultorio;
    public JPanel panelPrincipal;

    public MedicoInterfaz(String nombre, String nombreInterfaz, String rutaFotoP) {
        super(nombre, nombreInterfaz, rutaFotoP);
        
        this.btnMisCitas = new JButton("👥 Mis citas");
        this.btnHistorial = new JButton("☰ Historial");
        this.btnComentarios = new JButton("💬 Comentarios");
        this.btnNotificaciones = new JButton("✉️ Notificaciones");
        this.btnMiAgenda = new JButton("📅 Mi Agenda");
        this.btnConsultorio = new JButton("❤️ Consultorio");
        
        super.agregarBotonCuerpo1(btnMisCitas);
        super.agregarBotonCuerpo1(btnHistorial);
        super.agregarBotonCuerpo1(btnComentarios);
        super.agregarBotonCuerpo1(btnNotificaciones);
        super.agregarBotonCuerpo1(btnMiAgenda);
        super.agregarBotonCuerpo1(btnConsultorio);
        
        this.panelPrincipal = new JPanel();
        this.panelPrincipal.setOpaque(false);
    }

    @Override
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
        if (botonActivo != btnMiAgenda && !btnMiAgenda.isEnabled()) {
            this.btnMiAgenda.setEnabled(true);
        }
        if (botonActivo != btnConsultorio && !btnConsultorio.isEnabled()) {
            this.btnConsultorio.setEnabled(true);
        }
    }
    
    @Override
    public void mostrarVistaMisCitas(){        
        super.mostrarVistaMisCitas();
    }
    @Override
    public void mostrarVistaHistorial(){
        super.mostrarVistaHistorial();   
    }
    @Override
    public void mostrarVistaComentarios(){
        super.mostrarVistaComentarios();
    }
    @Override
    public void mostrarVistaNotificaciones(){
        super.mostrarVistaNotificaciones();
    }
    public void mostrarVistaMiAgenda(){
        MetodosPublicos.vaciarPanel(cuerpo2);
        MetodosPublicos.vaciarPanel(panelPrincipal);     
        
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
        tituloMiAgenda.setFont(new Font("arial",Font.BOLD,28));
        tituloMiAgenda.setForeground(COLOR_AZUL_CORPORATIVO);
        
        this.cuerpo2.add(tituloMiAgenda, BorderLayout.NORTH);
        this.cuerpo2.add(scrollCitas,BorderLayout.SOUTH);
        
        MetodosPublicos.refrescarVentana(panelPrincipal);
        MetodosPublicos.refrescarVentana(cuerpo2);
        MetodosPublicos.refrescarVentana(scrollCitas);
    }
    public void mostrarVistaConsultorio(){
        MetodosPublicos.vaciarPanel(cuerpo2);
        MetodosPublicos.vaciarPanel(panelPrincipal);
        
        this.cuerpo2.setBorder(new EmptyBorder(10, 40, 40, 40));
        
        JLabel tituloConsultorio = new JLabel("Atender Paciente");
        tituloConsultorio.setFont(new Font("arial", Font.BOLD, 28));
        tituloConsultorio.setForeground(COLOR_AZUL_CORPORATIVO);
        this.cuerpo2.add(tituloConsultorio, BorderLayout.NORTH);
        
        JTextField id = new JTextField(20);
        this.panelPrincipal.add(id);
        
        
        MetodosPublicos.refrescarVentana(panelPrincipal);
        MetodosPublicos.refrescarVentana(cuerpo2);
    }
}
