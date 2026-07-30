package lifedoccare;

import controller.LoginController;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import view.Login;
import view.RecuperacionContrasenaInterfaz;
import view.RegistroUsuariosInterfaz;
import javax.swing.UIManager;

public class LifeDocCare {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

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

        Login lg = new Login();
        RecuperacionContrasenaInterfaz rc = new RecuperacionContrasenaInterfaz();
        RegistroUsuariosInterfaz ur = new RegistroUsuariosInterfaz("Registro");
        LoginController clg = new LoginController(lg, rc, ur);
        lg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        lg.setSize(dimension.width, dimension.height);
        lg.setVisible(true);
    }
}
