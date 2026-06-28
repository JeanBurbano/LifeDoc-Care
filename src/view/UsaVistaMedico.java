/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package view;

import controller.MedicoController;
import static java.awt.Frame.MAXIMIZED_BOTH;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author Windows 10 PRO
 */
public class UsaVistaMedico {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MedicoInterfaz m = new MedicoInterfaz("Maria Guzman", "Medico","anonymous.png");
        m.setVisible(true);
        m.setDefaultCloseOperation(EXIT_ON_CLOSE);
        m.setExtendedState(MAXIMIZED_BOTH);
        MedicoController clg = new MedicoController(m);
    } 
    
}
