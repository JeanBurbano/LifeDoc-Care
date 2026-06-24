package view;

import java.awt.Container;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Notificacion extends JFrame {

    private Container contenedor;
    private JPanel panel1;
    private JTextArea miarea;

    public Notificacion() {
        contenedor = getContentPane();
        contenedor.setLayout(new FlowLayout());

        this.panel1 = new JPanel();
        this.panel1.setLayout(new FlowLayout());
        this.miarea = new JTextArea(20,30);
        this.miarea.setEnabled(false);
        this.panel1.add(miarea);
        this.contenedor.add(panel1);
        
        setSize(500, 400);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
