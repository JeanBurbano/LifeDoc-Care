package view;

import static java.awt.Frame.MAXIMIZED_BOTH;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class UsaVista {

    public static void main(String[] args) {
        RegistoPersonalInterfaz p = new RegistoPersonalInterfaz("dsadasdas");
         p.setDefaultCloseOperation(EXIT_ON_CLOSE);
        p.setExtendedState(MAXIMIZED_BOTH);
        p.setVisible(true);
    }
}
