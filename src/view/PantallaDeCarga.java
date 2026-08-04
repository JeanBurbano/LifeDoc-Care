package view;

import java.awt.BorderLayout;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class PantallaDeCarga extends JDialog {

    public static final ImageIcon IMAGEN_ORIGINAL = new ImageIcon("imagencarga.JPG");
    private JPanel contenedorImagen;
    private JLabel labelImagen;
    private JProgressBar barraProgreso;
    public int ancho;
    public int alto;

    public PantallaDeCarga() {
        init();
    }

    private void init() {
        contenedorImagen = new JPanel(new BorderLayout());
        ancho = 900;
        alto = 510;

        Image imagenEscalada = IMAGEN_ORIGINAL.getImage()
                .getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);
        labelImagen = new JLabel(iconoEscalado);

        barraProgreso = new JProgressBar(0, 100);
        barraProgreso.setStringPainted(true);

        setContentPane(contenedorImagen);
        contenedorImagen.add(labelImagen, BorderLayout.CENTER);
        contenedorImagen.add(barraProgreso, BorderLayout.SOUTH);

        //Aqui hago lo de para que no se pueda cerrar con la x mientras carga
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setModal(false);
        setUndecorated(true); //Quita la barra de título

        pack();
        setLocationRelativeTo(null);
    }

    public void setProgreso(int valor) {
        barraProgreso.setValue(valor);
    }

    public void cerrar() {
        dispose();
    }
}