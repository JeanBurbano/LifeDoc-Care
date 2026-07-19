package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import model.MetodosPublicos;

import static view.PacienteInterfaz.COLOR_AZUL_CORPORATIVO;

public class EditarPerfilInterfaz extends JFrame {
    
    private static Color COLOR_VENTANA = new Color(255, 255, 255);
    
    private Container contenedor;
    public JButton volver;
    private PanelRound fotoPerfil;
    public JPanel contenedor2, encabezado, cuerpo1, cuerpo2;
    
    public EditarPerfilInterfaz(String nombreInterfaz) {
        super(nombreInterfaz);
        this.contenedor = getContentPane();
        this.contenedor2 = new JPanel();
        this.contenedor2.setLayout(new BoxLayout(contenedor2, BoxLayout.Y_AXIS));
        this.contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));
        this.contenedor2.setBackground(COLOR_VENTANA);
        this.contenedor.setBackground(COLOR_VENTANA);
        this.encabezado = new JPanel();
        this.encabezado.setBorder(new EmptyBorder(40, 40, 0, 40));
        this.encabezado.setLayout(new BorderLayout());
        JPanel panelBienvenida = new JPanel();
        panelBienvenida.setLayout(new BorderLayout());
        panelBienvenida.setOpaque(false);
        panelBienvenida.add(new Titulo("LifeDoc", "Care", 50).getPanelTitulo(), BorderLayout.NORTH);
        panelBienvenida.add(new JLabel("Centro de salud - Nombre del usuario"), BorderLayout.WEST);
        JPanel panelBoton = new JPanel();
        panelBoton.setLayout(new FlowLayout());
        panelBoton.setOpaque(false);
        this.volver = new JButton("Volver", new ImageIcon("iconsP/arrow-left.png"));
        MetodosPublicos.estilizarBoton(volver, (byte) 1);
        this.volver.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panelBoton.add(volver);
        this.encabezado.add(panelBienvenida, BorderLayout.WEST);
        this.encabezado.add(panelBoton, BorderLayout.EAST);
        int valor  = 400;
        this.fotoPerfil = new PanelRound();
        this.fotoPerfil.setRoundBottomLeft(valor);
        this.fotoPerfil.setRoundBottomRight(valor);
        this.fotoPerfil.setRoundTopLeft(valor);
        this.fotoPerfil.setRoundTopRight(valor);
        this.fotoPerfil.setPreferredSize(new Dimension(420, 310));
        this.fotoPerfil.setMaximumSize(new Dimension(420, 310));
        JLabel fotito = new JLabel(new ImageIcon("C:\\Users\\Usuario\\Documents\\NetBeansProjects\\LifeDoc-Care\\imagen.png"));
        fotito.setPreferredSize(new Dimension(420, 310));
        fotito.setMaximumSize(new Dimension(420, 310));
        this.fotoPerfil.add(fotito);
        this.cuerpo1 = new JPanel();
        this.cuerpo1.setBorder(BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO));
        this.cuerpo2 = new JPanel();
        this.cuerpo2.setBorder(BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO));
        this.encabezado.setBackground(COLOR_VENTANA);
        this.encabezado.setPreferredSize(new Dimension(40, 125));
        this.cuerpo1.setBackground(COLOR_AZUL_CORPORATIVO);
        this.cuerpo1.setPreferredSize(new Dimension(1520, 326));
        this.cuerpo1.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.cuerpo1.add(fotoPerfil);
        this.cuerpo2.setBackground(COLOR_VENTANA);
        this.cuerpo2.setPreferredSize(new Dimension(1520, 326));
        this.cuerpo2.setLayout(new BoxLayout(cuerpo2, BoxLayout.Y_AXIS));
        JPanel panelConField = new JPanel();
        this.cuerpo2.add(panelConField);
        this.contenedor2.setBorder(new EmptyBorder(10, 40, 40, 40));
        this.contenedor2.add(encabezado);
        this.contenedor2.add(Box.createVerticalStrut(10));
        this.contenedor2.add(cuerpo1);
        this.contenedor2.add(cuerpo2);
        this.contenedor.add(contenedor2);
    }
}
