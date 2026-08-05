package view;

//import
import java.awt.BorderLayout;
import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

/*clase abstracta LayoutView 
  se escogio ese nombre ya que hace referencia directa a la estructura de layout:
  encabezado
  cuerpo
  pie
 */
public abstract class LayoutView extends JFrame {

    //Variables constantes static - variables de Clase
    private static ImageIcon iconoOriginal = new ImageIcon("Fondo1_watermark.jpeg");
    public static final Color COLOR_AZUL_CORPORATIVO = new Color(0, 79, 124);
    public static final Color COLOR_VERDE_ACENTO = new Color(0, 194, 177);
    public static final Color COLOR_GRIS_SUBTITULO = new Color(100, 120, 130);

    //Variables de instacia
    public JLabel fondoVentana;
    public JPanel panelContenido;
    public JPanel encabezado;
    public JPanel cuerpo1;
    public JPanel cuerpo2;

    public LayoutView(String nombreVentana) {
        //Nombre de la ventana
        super(nombreVentana);
        init();
    }
    
    public LayoutView() {
        this.setUndecorated(true);
        init();
    }

    private void init() {
        boolean opaque = true;

        //Inicializo fondoVentana
        fondoVentana = new JLabel();
        //Obtener las dimensiones de la pantalla
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int ancho = screenSize.width;
        int alto = screenSize.height;

        //Escalar la imagen al tamaño de la pantalla Image.SCALE_SMOOTH ofrece mejor calidad visual
        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        //Crear un nuevo ImageIcon con la imagen ya escalada
        ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);
        //Configurar el JLabel con la imagen escalada
        fondoVentana.setIcon(iconoEscalado);
        //Establecer que el tamaño del JLabel sea el de la pantalla
        fondoVentana.setBounds(0, 0, ancho, alto);
        //Estableces el JLabel como contenedor
        fondoVentana.setOpaque(opaque);
        fondoVentana.setLayout(new BorderLayout());
        setContentPane(fondoVentana);
        //Encabezado
        encabezado = new JPanel();
        encabezado.setOpaque(!opaque);
        //Cuerpo1 es el panel de botones
        cuerpo1 = new JPanel();
        cuerpo1.setOpaque(!opaque);
        //cuerpo2 panel donde cambia la vista
        cuerpo2 = new JPanel();
        cuerpo2.setOpaque(!opaque);
        //Se arma la estructura de la plantilla
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setOpaque(!opaque);
        panelSuperior.add(encabezado, BorderLayout.NORTH);
        panelSuperior.add(cuerpo1, BorderLayout.SOUTH);

        panelContenido = new JPanel(new BorderLayout());
        panelContenido.setBorder(new EmptyBorder(5, 10, 0, 10));
        panelContenido.setOpaque(!opaque);
        panelContenido.add(panelSuperior, BorderLayout.NORTH);
        panelContenido.add(cuerpo2, BorderLayout.CENTER);
        JScrollPane scrollFormulario = new JScrollPane(panelContenido);
        scrollFormulario.setOpaque(!opaque);
        scrollFormulario.getViewport().setOpaque(!opaque);
        scrollFormulario.setBorder(null);
        scrollFormulario.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollFormulario.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        fondoVentana.add(scrollFormulario, BorderLayout.CENTER);
    }
}
