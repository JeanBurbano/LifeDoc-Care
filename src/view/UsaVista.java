package view;

import static java.awt.Frame.MAXIMIZED_BOTH;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import controller.LoginController;
import controller.PacienteController;
import model.MetodosPublicos;

public class UsaVista {

    public static void main(String[] args) {
//        Login lg = new Login();
//        lg.setVisible(true);
//        lg.setDefaultCloseOperation(EXIT_ON_CLOSE);
//        lg.setExtendedState(MAXIMIZED_BOTH);
//        LoginController clg = new LoginController(lg);
//        RecuperacionContrasenaInterfaz p = new RecuperacionContrasenaInterfaz();
//        PacienteInterfaz p = new PacienteInterfaz("Alejandro Vanegas", "Paciente","anonymous.png");
//        p.vistaMetodoDerecuperacion();
//        p.vistaRecuperacion("Correo");
//        p.vistaCambiarContrasena();
//        p.vistaCambiarContrasena();
//        p.vistaCambiarContrasena();
//        p.setVisible(true);
//        p.setDefaultCloseOperation(EXIT_ON_CLOSE);
//        p.setExtendedState(MAXIMIZED_BOTH);
//        PacienteController clg = new PacienteController(p);
System.out.println(MetodosPublicos.calcularEdad("2026", "07", "2008", "12"));
    }

}
