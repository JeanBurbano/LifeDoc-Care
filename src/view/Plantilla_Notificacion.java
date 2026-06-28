package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import static view.PacienteInterfaz.COLOR_AZUL_CORPORATIVO;

public class Plantilla_Notificacion extends JFrame {

    private JPanel panelSuperior, panelContenido;
    private JLabel fondo;
    private JButton flecha;

    public Plantilla_Notificacion() {
        super("Recuperacion Contrasena");//Nombre de la ventana
        this.fondo = new JLabel(new ImageIcon("fondo2.png"));
        this.fondo.setOpaque(true);
        this.fondo.setLayout(new GridBagLayout());
        this.setContentPane(fondo);

        this.panelSuperior = new JPanel();
        this.panelSuperior.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.panelSuperior.setOpaque(false);
        this.panelSuperior.setPreferredSize(new Dimension(50, 90));

        this.flecha = new JButton("←");
        JLabel titulo1 = new JLabel("Cambiar Contrasena");
        titulo1.setFont(new Font("arial", Font.BOLD, 25));

        Titulo titulo2 = new Titulo("LifeDoc", "Care");

        this.panelSuperior.add(flecha);
        this.panelSuperior.add(titulo1);
        this.panelSuperior.add(titulo2.getPanelTitulo());
        titulo2 = null;
        titulo1 = null;

        this.panelContenido = new JPanel();
        this.panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        this.panelContenido.setBackground(Color.WHITE);
        this.panelContenido.setPreferredSize(new Dimension(600, 400));
        this.panelContenido.setBorder(BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO));

        GridBagConstraints gbcSuperior = new GridBagConstraints();
        gbcSuperior.gridx = 0;
        gbcSuperior.gridy = 0;
        gbcSuperior.anchor = GridBagConstraints.NORTHWEST; // arriba izquierda
        gbcSuperior.weightx = 1.0; // que ocupe todo el ancho disponible
        gbcSuperior.weighty = 0;   // altura minima
        gbcSuperior.fill = GridBagConstraints.HORIZONTAL; // se estira horizontalmente
        gbcSuperior.insets = new Insets(10, 10, 0, 0); // margen arriba e izquierda
        this.fondo.add(panelSuperior, gbcSuperior);
        GridBagConstraints gbcContenido = new GridBagConstraints();
        gbcContenido.gridx = 0;
        gbcContenido.gridy = 1;
        gbcContenido.anchor = GridBagConstraints.CENTER; // centrado
        gbcContenido.weightx = 1.0;
        gbcContenido.weighty = 1.0; // que ocupe el resto del espacio vertical
        this.fondo.add(panelContenido, gbcContenido);
    }
}
