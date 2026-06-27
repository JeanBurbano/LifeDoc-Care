package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MedicoInterfaz extends PacienteInterfaz{
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
//        this.btnMisCitas.addActionListener(this);
//        this.btnHistorial.addActionListener(this);

        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new FlowLayout());
    }
}
