package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
        
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new FlowLayout());
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
        panelPrincipal.setOpaque(false);
        
        JLabel tituloMiAgenda = new JLabel("Citas programadas vigentes");
        tituloMiAgenda.setFont(new Font("arial", Font.BOLD, 28));
        tituloMiAgenda.setForeground(COLOR_AZUL_CORPORATIVO);
        this.panelPrincipal.add(tituloMiAgenda);
        
        JScrollPane scrollCitas = new JScrollPane(panelPrincipal);
        scrollCitas.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollCitas.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        MetodosPublicos.refrescarVentana(panelPrincipal);
        MetodosPublicos.refrescarVentana(super.cuerpo2);
    }
    public void mostrarVistaConsultorio(){
        
    }
}
