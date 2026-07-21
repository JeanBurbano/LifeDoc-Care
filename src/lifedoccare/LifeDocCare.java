package lifedoccare;

import controller.LoginController;
import static java.awt.Frame.MAXIMIZED_BOTH;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import view.Login;
import view.RecuperacionContrasenaInterfaz;

public class LifeDocCare {

    public static void main(String[] args) {
        Login lg = new Login();
        RecuperacionContrasenaInterfaz rc = new RecuperacionContrasenaInterfaz();
        LoginController clg = new LoginController(lg, rc);
        lg.setDefaultCloseOperation(EXIT_ON_CLOSE);
        lg.setExtendedState(MAXIMIZED_BOTH);
        lg.setVisible(true);
    }

}
