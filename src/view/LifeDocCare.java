package view;


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
            // El tema (Look & Feel) se configura ANTES de crear cualquier ventana
            configurarTema();

            PantallaDeCarga carga = new PantallaDeCarga();
            carga.setVisible(true);

            new InicializadorApp(carga).execute();
        });
    }

    /**
     * Configura el Look & Feel (FlatLaf) y los estilos globales de UIManager.
     * Debe ejecutarse en el EDT, antes de instanciar cualquier componente Swing.
     */
    private static void configurarTema() {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Le dice a Swing que dibuje la barra de título con el Look & Feel
        // en vez del estilo nativo del sistema operativo (así todo se ve consistente)
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);

        // Estilos globales de componentes
        UIManager.put("Button.arc", 15);
        UIManager.put("Component.arc", 15);
        UIManager.put("ProgressBar.arc", 15);
        UIManager.put("TextComponent.arc", 10);
        UIManager.put("ScrollBar.width", 12);
        UIManager.put("ScrollBar.thumbArc", 999);
        UIManager.put("ScrollBar.trackArc", 999);
        UIManager.put("Component.focusColor", new Color(0, 79, 124));
        UIManager.put("Component.borderColor", new Color(0, 79, 124));
        UIManager.put("ScrollBar.thumbColor", new Color(0, 79, 124, 120));
    }

    /**
     * Worker que crea las vistas de la aplicación en segundo plano
     * mientras se muestra el progreso en la pantalla de carga.
     */
    static class InicializadorApp extends SwingWorker<Login, Integer> {

        private final PantallaDeCarga carga;

        public InicializadorApp(PantallaDeCarga carga) {
            this.carga = carga;
        }

        @Override
        protected Login doInBackground() throws Exception {
            avanzarProgresoSuave(0, 20);

            // Creo la vista principal, que sería el login
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

        /**
         * Avanza la barra de progreso número por número entre "desde" y "hasta",
         * dando un efecto de deslizamiento suave en vez de saltos bruscos.
         */
        private void avanzarProgresoSuave(int desde, int hasta) throws InterruptedException {
            for (int i = desde; i <= hasta; i++) {
                publish(i);
                Thread.sleep(15); // controla la velocidad del deslizamiento
            }
        }

        @Override
        protected void process(java.util.List<Integer> chunks) {
            // Se ejecuta en el EDT: actualiza la barra con el último valor publicado
            int ultimo = chunks.get(chunks.size() - 1);
            carga.setProgreso(ultimo);
        }

        @Override
        protected void done() {
            try {
                Login lg = get(); // Obtiene lo que devolvió doInBackground()

                lg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                Toolkit toolkit = Toolkit.getDefaultToolkit();
                Dimension dimension = toolkit.getScreenSize();
                lg.setSize(dimension.width, dimension.height);
                lg.setVisible(true);

                carga.cerrar(); // Cierra la pantalla de carga cuando el login ya está listo

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}