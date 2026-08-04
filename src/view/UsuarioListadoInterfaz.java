package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.Container;
import java.awt.Insets;
import model.MetodosPublicos;
import static view.PacienteInterfaz.COLOR_AZUL_CORPORATIVO;

public class UsuarioListadoInterfaz extends LayoutView {

    JPanel contedorTarjeta;
    private static final int TAMANO_FOTO = 90;
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public UsuarioListadoInterfaz(String nombreInterfaz) {
        super(nombreInterfaz);

        JPanel panelTitulo = new Titulo("LifeDoc", "Care", 20).getPanelTitulo();
        JLabel usuarios = new JLabel("Usuarios de LifeDoc Care");
        usuarios.setOpaque(false);
        usuarios.setFont(new Font("Arial",Font.BOLD,20));
        encabezado.add(panelTitulo);
        cuerpo1.add(usuarios);

        contedorTarjeta = new JPanel();
        contedorTarjeta.setOpaque(false);
        contedorTarjeta.setLayout(new WrapLayout(FlowLayout.LEFT, 16, 16));

        JScrollPane scrollTarjetas = new JScrollPane(contedorTarjeta);
        scrollTarjetas.setBorder(BorderFactory.createEmptyBorder());
        scrollTarjetas.getViewport().setOpaque(false);
        scrollTarjetas.setOpaque(false);
        scrollTarjetas.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollTarjetas.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollTarjetas.getVerticalScrollBar().setUnitIncrement(16);

        cuerpo2.setLayout(new BorderLayout());
        cuerpo2.add(scrollTarjetas, BorderLayout.CENTER);
    }

    public void agregarTarjetaUsuario(String rutaFotoPerfil, String rol, String tipoIdentificacion,
            String numeroIdentificacion, String primerNombre, String primerApellido, String correo,
            LocalDate fechaNacimiento, String sexoBiologico, String numeroCelular, byte edad,
            String sisben, boolean estado) {

        JPanel tarjeta = construirTarjeta(rutaFotoPerfil, rol, tipoIdentificacion, numeroIdentificacion,
                primerNombre, primerApellido, correo, fechaNacimiento, sexoBiologico, numeroCelular,
                edad, sisben, estado);

        contedorTarjeta.add(tarjeta);
        MetodosPublicos.refrescarVentana(contedorTarjeta);
    }

    private JPanel construirTarjeta(String fotoPerfil, String rol, String tipoIdentificacion,
            String numeroIdentificacion, String primerNombre, String primerApellido, String correo,
            LocalDate fechaNacimiento, String sexoBiologico, String numeroCelular, byte edad,
            String sisben, boolean estado) {

        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO,1),
                BorderFactory.createEmptyBorder(16, 16, 16, 16)));
        tarjeta.setAlignmentX(Component.LEFT_ALIGNMENT);
        tarjeta.setMaximumSize(new Dimension(260, 320));
        tarjeta.setMinimumSize(new Dimension(240, 300));

        JPanel fotoContenedor = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        fotoContenedor.setOpaque(false);
        fotoContenedor.setAlignmentX(Component.CENTER_ALIGNMENT);
        fotoContenedor.setMaximumSize(new Dimension(Integer.MAX_VALUE, TAMANO_FOTO));
        fotoContenedor.add(construirFoto(fotoPerfil));
        tarjeta.add(fotoContenedor);
        tarjeta.add(javax.swing.Box.createVerticalStrut(10));

        JPanel datos = construirDatos(rol, tipoIdentificacion, numeroIdentificacion, primerNombre,
                primerApellido, correo, fechaNacimiento, sexoBiologico, numeroCelular, edad, sisben);
        datos.setAlignmentX(Component.LEFT_ALIGNMENT);
        datos.setMaximumSize(new Dimension(Integer.MAX_VALUE, datos.getPreferredSize().height));
        tarjeta.add(datos);

        tarjeta.add(javax.swing.Box.createVerticalStrut(10));

        JPanel estadoPanel = construirEstado(estado);
        estadoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        tarjeta.add(estadoPanel);

        return tarjeta;
    }

    private JLabel construirFoto(String fotoPerfil) {
        if(fotoPerfil == null || fotoPerfil.isEmpty()) fotoPerfil = "fotosPerfil/fotoDefecto.png";
        ImageIcon iconoOriginal = new ImageIcon(fotoPerfil);
        Image imagenEscalada = iconoOriginal.getImage()
                .getScaledInstance(TAMANO_FOTO, TAMANO_FOTO, Image.SCALE_SMOOTH);
        JLabel foto = new JLabel(new ImageIcon(imagenEscalada));
        foto.setHorizontalAlignment(SwingConstants.CENTER);
        return foto;
    }

    private JPanel construirDatos(String rol, String tipoIdentificacion, String numeroIdentificacion,
            String primerNombre, String primerApellido, String correo, LocalDate fechaNacimiento,
            String sexoBiologico, String numeroCelular, byte edad, String sisben) {

        JPanel datos = new JPanel();
        datos.setLayout(new BoxLayout(datos, BoxLayout.Y_AXIS));
        datos.setOpaque(false);
        datos.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel nombreCompleto = new JLabel(primerNombre + " " + primerApellido);
        nombreCompleto.setFont(new Font("SansSerif", Font.BOLD, 16));
        nombreCompleto.setForeground(COLOR_AZUL_CORPORATIVO);
        nombreCompleto.setAlignmentX(Component.LEFT_ALIGNMENT);
        estirarAncho(nombreCompleto);

        JLabel rolLabel = new JLabel(rol);
        rolLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        rolLabel.setForeground(COLOR_VERDE_ACENTO);
        rolLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        estirarAncho(rolLabel);

        String fechaTexto = fechaNacimiento != null ? fechaNacimiento.format(FORMATO_FECHA) : "-";

        JLabel identificacion = crearFilaDato(tipoIdentificacion + " · " + numeroIdentificacion);
        JLabel contacto = crearFilaDato(correo);
        JLabel celular = crearFilaDato(numeroCelular);
        JLabel detalle = crearFilaDato(sexoBiologico + " · " + edad + " años · Nac.: " + fechaTexto);
        JLabel sisbenLabel = crearFilaDato("Sisben: " + sisben);

        datos.add(nombreCompleto);
        datos.add(javax.swing.Box.createVerticalStrut(2));
        datos.add(rolLabel);
        datos.add(javax.swing.Box.createVerticalStrut(8));
        datos.add(identificacion);
        datos.add(contacto);
        datos.add(celular);
        datos.add(detalle);
        datos.add(sisbenLabel);

        return datos;
    }

    private JLabel crearFilaDato(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("SansSerif", Font.PLAIN, 12));
        label.setForeground(COLOR_GRIS_SUBTITULO);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        estirarAncho(label);
        return label;
    }

    private void estirarAncho(JLabel label) {
        label.setMaximumSize(new Dimension(Integer.MAX_VALUE, label.getPreferredSize().height));
    }

    private JPanel construirEstado(boolean estado) {
        JPanel contenedor = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
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
    
    private static class WrapLayout extends FlowLayout {

        WrapLayout(int align, int hgap, int vgap) {
            super(align, hgap, vgap);
        }

        @Override
        public Dimension preferredLayoutSize(Container target) {
            return layoutSize(target, true);
        }

        @Override
        public Dimension minimumLayoutSize(Container target) {
            Dimension minimo = layoutSize(target, false);
            minimo.width -= (getHgap() + 1);
            return minimo;
        }

        private Dimension layoutSize(Container target, boolean preferred) {
            synchronized (target.getTreeLock()) {
                int anchoDisponible = obtenerAnchoDisponible(target);

                int filaAncho = 0;
                int filaAlto = 0;
                int alturaTotal = 0;
                int anchoMaximo = 0;

                int cantidad = target.getComponentCount();
                for (int i = 0; i < cantidad; i++) {
                    Component componente = target.getComponent(i);
                    if (!componente.isVisible()) {
                        continue;
                    }

                    Dimension tamano = preferred ? componente.getPreferredSize() : componente.getMinimumSize();

                    if (filaAncho + tamano.width + getHgap() > anchoDisponible && filaAncho > 0) {
                        anchoMaximo = Math.max(anchoMaximo, filaAncho);
                        alturaTotal += filaAlto + getVgap();
                        filaAncho = 0;
                        filaAlto = 0;
                    }

                    if (filaAncho > 0) {
                        filaAncho += getHgap();
                    }
                    filaAncho += tamano.width;
                    filaAlto = Math.max(filaAlto, tamano.height);
                }

                anchoMaximo = Math.max(anchoMaximo, filaAncho);
                alturaTotal += filaAlto;

                Insets insets = target.getInsets();
                Dimension resultado = new Dimension(
                        anchoMaximo + insets.left + insets.right + getHgap() * 2,
                        alturaTotal + insets.top + insets.bottom + getVgap() * 2);
                return resultado;
            }
        }

        private int obtenerAnchoDisponible(Container target) {
            Container contenedorScroll = target;
            while (contenedorScroll != null && !(contenedorScroll instanceof javax.swing.JViewport)) {
                contenedorScroll = contenedorScroll.getParent();
            }

            if (contenedorScroll != null) {
                return contenedorScroll.getWidth();
            }

            int ancho = target.getWidth();
            if (ancho <= 0) {
                ancho = Integer.MAX_VALUE;
            }
            Insets insets = target.getInsets();
            return ancho - insets.left - insets.right - getHgap() * 2;
        }
    }
}