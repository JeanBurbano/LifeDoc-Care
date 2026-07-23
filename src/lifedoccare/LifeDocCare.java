package lifedoccare;

import controller.LoginController;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import view.Login;
import view.RecuperacionContrasenaInterfaz;
import view.RegistroUsuariosInterfaz;

public class LifeDocCare {

    public static void main(String[] args) {
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
