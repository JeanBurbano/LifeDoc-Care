package view;

import static java.awt.Frame.MAXIMIZED_BOTH;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class UsaVista {

    public static void main(String[] args) {
        EditarPerfilInterfaz vista = new EditarPerfilInterfaz("Editar Perfil");
        vista.setVisible(true);
        vista.setDefaultCloseOperation(EXIT_ON_CLOSE);
        vista.setExtendedState(MAXIMIZED_BOTH);
    }
}
