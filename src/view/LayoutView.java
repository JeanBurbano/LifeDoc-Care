package view;

//import
import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Toolkit;

/*clase abstracta LayoutView 
  se escogio ese nombre ya que hace referencia directa a la estructura de layout:
  encabezado
  cuerpo
  pie
 */
public abstract class LayoutView extends JFrame {

    //Variables constantes static - variables de Clase
    public static final JLabel fondoVentana = new JLabel();
    public static final Color COLOR_AZUL_CORPORATIVO = new Color(0, 79, 124);
    public static final Color COLOR_VERDE_ACENTO = new Color(0, 194, 177);
    public static final Color COLOR_GRIS_SUBTITULO = new Color(100, 120, 130);

    //Variables de instacia
    public JPanel encabezado;
    public JPanel cuerpo1;
    public JPanel cuerpo2;

    public LayoutView() {

        boolean opaque = false;

        //Asigno como contenedor a fondoVentana
//        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
//        int alto=screenSize.he;
//        int ancho = screenSize.width;
//        
        setContentPane(fondoVentana);
        fondoVentana.setOpaque(opaque);

        //Encabezado
        encabezado = new JPanel();
        encabezado.setOpaque(opaque);

        //Cuerpo1 es el panel de botones
        cuerpo1 = new JPanel();
        cuerpo1.setOpaque(opaque);

        //cuerpo2 panel donde cambia la vista
        cuerpo2 = new JPanel();
        cuerpo2.setOpaque(opaque);

    }
}
