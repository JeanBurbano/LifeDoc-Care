package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class UsuarioListadoInterfaz extends LayoutView {

    JPanel contedorTarjeta;
    private static final int DIAMETRO_FOTO = 64;
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public UsuarioListadoInterfaz(String nombreInterfaz) {
        super(nombreInterfaz);

        JPanel panelTitulo = new Titulo("LifeDoc", "Care", 20).getPanelTitulo();
        JLabel usuarios = new JLabel("Usuarios");
        JTextField buscador = new JTextField();
        buscador.setPreferredSize(new Dimension(320, 34));

        encabezado.add(panelTitulo);
        encabezado.add(usuarios);
        cuerpo1.add(buscador);

        contedorTarjeta = new JPanel();
        contedorTarjeta.setOpaque(false);
        contedorTarjeta.setLayout(new BoxLayout(contedorTarjeta, BoxLayout.Y_AXIS));

        cuerpo2.setLayout(new BorderLayout());
        cuerpo2.add(contedorTarjeta, BorderLayout.CENTER);
    }


    public void agregarTarjetaUsuario(ImageIcon fotoPerfil, String rol, String tipoIdentificacion,
            String numeroIdentificacion, String primerNombre, String primerApellido, String correo,
            LocalDate fechaNacimiento, String sexoBiologico, String numeroCelular, byte edad,
            String sisben, boolean estado) {

        JPanel tarjeta = construirTarjeta(fotoPerfil, rol, tipoIdentificacion, numeroIdentificacion,
                primerNombre, primerApellido, correo, fechaNacimiento, sexoBiologico, numeroCelular,
                edad, sisben, estado);

        contedorTarjeta.add(tarjeta);
        contedorTarjeta.add(Box.createVerticalStrut(10));
        contedorTarjeta.revalidate();
        contedorTarjeta.repaint();
    }

    private JPanel construirTarjeta(ImageIcon fotoPerfil, String rol, String tipoIdentificacion,
            String numeroIdentificacion, String primerNombre, String primerApellido, String correo,
            LocalDate fechaNacimiento, String sexoBiologico, String numeroCelular, byte edad,
            String sisben, boolean estado) {

        TarjetaRedondeada tarjeta = new TarjetaRedondeada();
        tarjeta.setLayout(new BorderLayout(16, 0));
        tarjeta.setBorder(new EmptyBorder(14, 16, 14, 16));
        tarjeta.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        tarjeta.setMaximumSize(new Dimension(Integer.MAX_VALUE, 112));

        tarjeta.add(construirFoto(fotoPerfil), BorderLayout.WEST);
        tarjeta.add(construirDatos(rol, tipoIdentificacion, numeroIdentificacion, primerNombre,
                primerApellido, correo, fechaNacimiento, sexoBiologico, numeroCelular, edad, sisben),
                BorderLayout.CENTER);
        tarjeta.add(construirEstado(estado), BorderLayout.EAST);

        return tarjeta;
    }

    private JLabel construirFoto(ImageIcon fotoPerfil) {
        JLabel foto = new JLabel(crearIconoCircular(fotoPerfil, DIAMETRO_FOTO));
        foto.setPreferredSize(new Dimension(DIAMETRO_FOTO, DIAMETRO_FOTO));
        foto.setBorder(new EmptyBorder(0, 0, 0, 8));
        return foto;
    }

    private JPanel construirDatos(String rol, String tipoIdentificacion, String numeroIdentificacion,
            String primerNombre, String primerApellido, String correo, LocalDate fechaNacimiento,
            String sexoBiologico, String numeroCelular, byte edad, String sisben) {

        JPanel datos = new JPanel(new GridBagLayout());
        datos.setOpaque(false);

        JLabel nombreCompleto = new JLabel(primerNombre + " " + primerApellido);
        nombreCompleto.setFont(new Font("SansSerif", Font.BOLD, 16));
        nombreCompleto.setForeground(COLOR_AZUL_CORPORATIVO);

        JLabel rolLabel = new JLabel(rol);
        rolLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        rolLabel.setForeground(COLOR_VERDE_ACENTO);

        String fechaTexto = fechaNacimiento != null ? fechaNacimiento.format(FORMATO_FECHA) : "-";

        JLabel identificacion = crearFilaDato(tipoIdentificacion + " · " + numeroIdentificacion);
        JLabel contacto = crearFilaDato(correo + "  ·  " + numeroCelular);
        JLabel detalle = crearFilaDato(sexoBiologico + "  ·  " + edad + " años  ·  Nac.: " + fechaTexto);
        JLabel sisbenLabel = crearFilaDato("Sisben: " + sisben);

        GridBagConstraints gb = new GridBagConstraints();
        gb.gridx = 0;
        gb.anchor = GridBagConstraints.WEST;
        gb.insets = new Insets(0, 0, 3, 0);

        gb.gridy = 0;
        datos.add(nombreCompleto, gb);
        gb.gridy = 1;
        datos.add(rolLabel, gb);
        gb.gridy = 2;
        datos.add(identificacion, gb);
        gb.gridy = 3;
        datos.add(contacto, gb);
        gb.gridy = 4;
        datos.add(detalle, gb);
        gb.gridy = 5;
        datos.add(sisbenLabel, gb);

        return datos;
    }

    private JLabel crearFilaDato(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("SansSerif", Font.PLAIN, 12));
        label.setForeground(COLOR_GRIS_SUBTITULO);
        return label;
    }

    private JPanel construirEstado(boolean estado) {
        JPanel contenedor = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        contenedor.setOpaque(false);

        JLabel estadoLabel = new JLabel(estado ? "Activo" : "Inactivo");
        estadoLabel.setOpaque(true);
        estadoLabel.setFont(new Font("SansSerif", Font.BOLD, 11));
        estadoLabel.setForeground(Color.WHITE);
        estadoLabel.setBackground(estado ? new Color(46, 175, 111) : new Color(200, 60, 60));
        estadoLabel.setBorder(new EmptyBorder(4, 10, 4, 10));
        estadoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        contenedor.add(estadoLabel);
        return contenedor;
    }

    private ImageIcon crearIconoCircular(ImageIcon original, int diametro) {
        Image imagenEscalada = original.getImage().getScaledInstance(diametro, diametro, Image.SCALE_SMOOTH);
        BufferedImage buffer = new BufferedImage(diametro, diametro, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = buffer.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setClip(new Ellipse2D.Double(0, 0, diametro, diametro));
        g2.drawImage(imagenEscalada, 0, 0, null);
        g2.dispose();
        return new ImageIcon(buffer);
    }

    private static class TarjetaRedondeada extends JPanel {
        private static final int RADIO = 18;

        TarjetaRedondeada() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE);
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, RADIO, RADIO));
            g2.setColor(new Color(220, 224, 228));
            g2.draw(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, RADIO, RADIO));
            g2.dispose();
            super.paintComponent(g);
        }
    }
}