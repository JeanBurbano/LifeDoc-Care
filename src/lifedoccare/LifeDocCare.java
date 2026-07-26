package lifedoccare;

import controller.LoginController;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import view.Login;
import view.RecuperacionContrasenaInterfaz;
import view.RegistroUsuariosInterfaz;
import javax.swing.JDialog;
import javax.swing.UIManager;

public class LifeDocCare {

    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
        try{
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        }catch(Exception e){
            e.printStackTrace();
        }
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
