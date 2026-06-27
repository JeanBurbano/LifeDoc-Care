/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package view;

import static java.awt.Frame.MAXIMIZED_BOTH;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author lunaa
 */
public class PruebaAdminCentro {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        AdminCentroInterfaz i = new AdminCentroInterfaz("alejandra cossio", "Administrador del Centro", "anonymous.png");
        i.setVisible(true);
        i.setDefaultCloseOperation(EXIT_ON_CLOSE);
        i.setExtendedState(MAXIMIZED_BOTH);
    }
    
}
