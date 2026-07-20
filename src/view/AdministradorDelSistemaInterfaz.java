package view;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import model.MetodosPublicos;
import model.Paciente;

public class AdministradorDelSistemaInterfaz extends PacienteInterfaz {

    public JPanel panelUsuarios;
    public JButton btnRol, btnHabilitar, btnDesabilitar;

    public AdministradorDelSistemaInterfaz(String nombreInterfaz, Paciente usuario) {
        super(nombreInterfaz, usuario);
        this.panelUsuarios = new JPanel();
        this.panelUsuarios.setLayout(new BoxLayout(panelUsuarios, BoxLayout.Y_AXIS));
        this.panelUsuarios.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO),
                BorderFactory.createEmptyBorder(0, 30, 5, 30)));
        this.panelUsuarios.setOpaque(false);
        this.btnRol = new JButton("Usuarios", new ImageIcon("iconsP/friends.png"));
        this.btnHabilitar = new JButton("Habilitar ");
        MetodosPublicos.estilizarBoton(btnHabilitar, (byte) 7);
        this.btnDesabilitar = new JButton("Desabilitar");
        MetodosPublicos.estilizarBoton(btnDesabilitar, (byte) 6);
        super.agregarBotonCuerpo1(btnRol);
        MetodosPublicos.refrescarVentana(cuerpo2);
    }

    public void vistaUsuarios() {
        MetodosPublicos.vaciarPanel(panelUsuarios);
        MetodosPublicos.vaciarPanel(cuerpo2);
        super.cuerpo2.setBorder(new EmptyBorder(40, 40, 40, 40));
        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false);
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));
        panelBotones.add(Box.createVerticalStrut(170));
        panelBotones.add(btnHabilitar);
        panelBotones.add(Box.createVerticalStrut(20));
        panelBotones.add(btnDesabilitar);
        super.cuerpo2.add(panelBotones, BorderLayout.WEST);
        super.cuerpo2.add(panelUsuarios, BorderLayout.CENTER);
        MetodosPublicos.refrescarVentana(panelUsuarios);
        MetodosPublicos.refrescarVentana(cuerpo2);
    }

    @Override
    public void habilitarBotonesMenu(JButton botonActivo) {
        super.habilitarBotonesMenu(botonActivo);
        if (botonActivo != btnRol && !btnRol.isEnabled()) {
            this.btnRol.setEnabled(true);
        }
    }
}
