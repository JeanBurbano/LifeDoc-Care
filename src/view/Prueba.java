package view;

import static java.awt.Frame.MAXIMIZED_BOTH;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Prueba {

    public static void main(String[] args) {
        PacienteInterfaz i = new PacienteInterfaz("Alejandro Vanegas", "aDMINISTRADOR","anonymous.png");
        i.setVisible(true);
        i.setDefaultCloseOperation(EXIT_ON_CLOSE);
        i.setExtendedState(MAXIMIZED_BOTH);
    }

}
