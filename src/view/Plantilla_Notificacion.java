package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class Plantilla_Notificacion extends JFrame {

    private Container contenedor;
    private JPanel panel1, panel2, panelTitulo, primerPanelCompleto, segundoPanelCompleto;
    private JLabel titulo1, titulo2, titulo3;
    private JButton flecha;

    public Plantilla_Notificacion() {
        contenedor = getContentPane();//A contenedor se asigno el contenido de la ventana
        contenedor.setLayout(new FlowLayout());//Le asigno el flowlayout
        contenedor.setBackground(Color.WHITE);//Color de fonde del contenedor

        this.panel1 = new JPanel();
        this.panel1.setLayout(new FlowLayout());
        this.panel1.setBackground(Color.WHITE);

        this.flecha = new JButton("←");
        this.flecha.setFont(new Font("arial", Font.BOLD, 25));
        this.flecha.setBackground(Color.WHITE);
        this.flecha.setForeground(new Color(0, 194, 177));
        this.flecha.setBorder(null);

        this.titulo1 = new JLabel("Cambiar Contrasena");
        this.titulo1.setFont(new Font("arial", Font.BOLD, 25));

        this.panel1.add(flecha);
        this.panel1.add(titulo1);

        this.panel2 = new JPanel();
        this.panel2.setLayout(new FlowLayout());
        this.panel2.setBackground(Color.WHITE);

        this.titulo2 = new JLabel("LifeDoc");//Creo etiqueta con texto LifeDoc titulo2
        this.titulo2.setFont(new Font(("arial"), Font.BOLD, 20));//A la etiqueta titulo2 le pongo tipo arial con negrita tamano 30
        this.titulo2.setForeground(new Color(0, 79, 124));//Aqui pongo la letra de la etiqueta titulo2 en blanco

        this.titulo3 = new JLabel("Care");
        this.titulo3.setForeground(new Color(0, 194, 177));
        this.titulo3.setFont(new Font(("arial"), Font.BOLD, 20));

        this.panel2.add(titulo2);
        this.panel2.add(titulo3);

        this.panelTitulo = new JPanel();
        this.panelTitulo.setLayout(new BoxLayout(panelTitulo, BoxLayout.Y_AXIS));
        this.panelTitulo.setBackground(Color.WHITE);
        this.panelTitulo.add(panel1);
        this.panelTitulo.add(panel2);

        this.primerPanelCompleto = new JPanel();
        this.primerPanelCompleto.setBorder(new EmptyBorder(0, 0, 0, 260));//creo padding Arriba,Izquierda,Abajo,Derecha son los valores del padding
        this.primerPanelCompleto.setLayout(new FlowLayout());
        this.primerPanelCompleto.setBackground(Color.WHITE);
        this.primerPanelCompleto.add(panelTitulo);

        this.segundoPanelCompleto = new JPanel();
        this.segundoPanelCompleto.add(new JTextArea(10, 45));
        this.segundoPanelCompleto.setBorder(BorderFactory.createLineBorder(new Color(0, 79, 124)));

        this.contenedor.add(primerPanelCompleto);
        this.contenedor.add(segundoPanelCompleto);

        setSize(600, 400);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
