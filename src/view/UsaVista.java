package view;

import controller.RecuperarContrasenaController;
import static java.awt.Frame.MAXIMIZED_BOTH;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import controller.LoginController;
//import controller.PacienteController;
//import model.MetodosPublicos;

public class UsaVista {

    public static void main(String[] args) {
        Login lg = new Login();
        RecuperacionContrasenaInterfaz rc = new RecuperacionContrasenaInterfaz();
        lg.setVisible(true);
        lg.setDefaultCloseOperation(EXIT_ON_CLOSE);
        lg.setExtendedState(MAXIMIZED_BOTH);
        LoginController clg = new LoginController(lg,rc);
//        RecuperacionContrasenaInterfaz p = new RecuperacionContrasenaInterfaz();
//        p.setVisible(true);
//        p.setDefaultCloseOperation(EXIT_ON_CLOSE);
//        p.setExtendedState(MAXIMIZED_BOTH);
//        RecuperarContrasenaController control = new RecuperarContrasenaController(p);
//        PacienteInterfaz p = new PacienteInterfaz("Alejandro Vanegas", "Paciente","anonymous.png");
//        p.setVisible(true);
//        p.setDefaultCloseOperation(EXIT_ON_CLOSE);
//        p.setExtendedState(MAXIMIZED_BOTH);
//        p.vistaMetodoDerecuperacion();
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        p.vistaRecuperacion("Correo");
//        try {
//            Thread.sleep(10000); // Espera 10 segundos
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        p.codigoAutenticacion("dasd","holaiasdas");
//        try {
//            Thread.sleep(10000); // Espera 10 segundos
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        p.vistaCambiarContrasena();
//        PacienteController clg = new PacienteController(p);

    }

}
