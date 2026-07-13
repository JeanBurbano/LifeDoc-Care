package view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Titulo {

    private JPanel panelTitulo;

    public Titulo(String titulo1, String titulo2) {
        panelTitulo = new JPanel();

        JLabel jtitulo1 = new JLabel(titulo1);
        jtitulo1.setFont(new Font("Arial", Font.BOLD, 24));
        jtitulo1.setForeground(new Color(0, 79, 124));

        JLabel jtitulo2 = new JLabel(titulo2);
        jtitulo2.setForeground(new Color(0, 194, 177));
        jtitulo2.setFont(new Font("Arial", Font.BOLD, 24));

        panelTitulo.setOpaque(false);
        panelTitulo.setLayout(new FlowLayout());
        panelTitulo.add(jtitulo1);
        panelTitulo.add(jtitulo2);
    }
    public Titulo(String titulo1, String titulo2,int tamano) {
        panelTitulo = new JPanel();

        JLabel jtitulo1 = new JLabel(titulo1);
        jtitulo1.setFont(new Font("Arial", Font.BOLD, tamano));
        jtitulo1.setForeground(new Color(0, 79, 124));

        JLabel jtitulo2 = new JLabel(titulo2);
        jtitulo2.setForeground(new Color(0, 194, 177));
        jtitulo2.setFont(new Font("Arial", Font.BOLD, tamano));

        panelTitulo.setOpaque(false);
        panelTitulo.setLayout(new FlowLayout());
        panelTitulo.add(jtitulo1);
        panelTitulo.add(jtitulo2);
    }

    public JPanel getPanelTitulo() {
        return panelTitulo;
    }
}