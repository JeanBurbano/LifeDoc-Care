package lifedoccare;

import com.formdev.flatlaf.FlatLightLaf;
import controller.LoginController;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import view.Login;
import view.PantallaDeCarga;
import view.RecuperacionContrasenaInterfaz;
import view.RegistroUsuariosInterfaz;

public class LifeDocCare {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            //El tema look y field se configura antes de crear cualquier ventana.
            configurarTema();

            PantallaDeCarga carga = new PantallaDeCarga();
            carga.setVisible(true);

            new InicializadorApp(carga).execute();
        });
    }

    private static void configurarTema() {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);

        UIManager.put("Button.arc", 15);//Esto se supone que redondea las esquinas de todos los JButton de la app 15px de radio
        UIManager.put("Component.arc", 15);
        //Redondea las esquinas de componentes genericos que no tienen su propia
        //propiedad de arc ejp JComboBox JSpinner es un valor por defecto general
        UIManager.put("ProgressBar.arc", 15);//Redondea las esquinas de los JProgressBar
        UIManager.put("TextComponent.arc", 10);//Redondea las esquinas de los campos de texto
        UIManager.put("ScrollBar.width", 12);//Ancho en pixeles de la barra de scroll
        UIManager.put("ScrollBar.thumbArc", 999);//Redondea las esquinas del thumb que es la parte que ase arratra con el mouse dentro del scrollbar
        UIManager.put("ScrollBar.trackArc", 999);
        //Lo mismo de arriba pero para en est acaso es el track osea el fondo/riel por donde se desliza el
        // thumb tambien queda completamente redondeado.
        UIManager.put("Component.focusColor", new Color(0, 79, 124));
        //Color del anillo o resaltado que aparece alrededor de un componente
        //cuando tiene el foco ejp cuando haces clic en un JTextField o le das
        //Tab este es un azul oscuro (0, 79, 124).
        UIManager.put("Component.borderColor", new Color(0, 79, 124));
        // Color del borde normal sin foco de los componentes campos de texto
        // combos etc etc usa el mismo azul para mantener una supuesta consistencia visual en
        // toda la ap
        UIManager.put("ScrollBar.thumbColor", new Color(0, 79, 124, 120));
        //Color del thumb del scrollbar el cuarto parametro 120 es el canal
        //alfa transparencia de 0 a 255 Con 120 queda semi transparente
        //para que no se vea tan solido pesado sobre el fondo
    }

    static class InicializadorApp extends SwingWorker<Login, Integer> {

        private final PantallaDeCarga carga;

        public InicializadorApp(PantallaDeCarga carga) {
            this.carga = carga;
        }

        @Override
        protected Login doInBackground() throws Exception {
            avanzarProgresoSuave(0, 20);

            //Creo la vista principal que seria el login
            Login lg = new Login();
            avanzarProgresoSuave(20, 50);

            RecuperacionContrasenaInterfaz rc = new RecuperacionContrasenaInterfaz();
            avanzarProgresoSuave(50, 70);

            RegistroUsuariosInterfaz ur = new RegistroUsuariosInterfaz();
            avanzarProgresoSuave(70, 85);

            LoginController clg = new LoginController(lg, rc, ur);
            avanzarProgresoSuave(85, 100);

            return lg;
        }

        private void avanzarProgresoSuave(int desde, int hasta) throws InterruptedException {
            for (int i = desde; i <= hasta; i++) {
                publish(i);
                Thread.sleep(20);//Controla la velocidad del deslizamiento
            }
        }

        @Override
        protected void process(java.util.List<Integer> chunks) {
            //Se ejecuta en el edt actualiza la barra con el ultimo valor publicado
            int ultimo = chunks.get(chunks.size() - 1);
            carga.setProgreso(ultimo);
        }

        @Override
        protected void done() {
            try {
                Login lg = get();//Obtiene lo que devolvió doInBackground()

                lg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                Toolkit toolkit = Toolkit.getDefaultToolkit();
                Dimension dimension = toolkit.getScreenSize();
                lg.setSize(dimension.width, dimension.height);
                lg.setVisible(true);

                carga.cerrar();//Cierra la pantalla de carga cuando el login ya esta listo

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
