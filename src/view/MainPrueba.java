package view;

import controller.OperarioController;

public class MainPrueba {
    public static void main(String[] args) {
        OperarioInterfaz vista = new OperarioInterfaz("Juanita Velasquez", 
                                                      "LifeDoc Care - Operario", 
                                                      "anonymous.png");
        
        OperarioController controller = new OperarioController(vista);
        
        // Cargar citas de ejemplo
        controller.cargarCitasEjemplo();

        vista.setSize(1250, 850);
        vista.setLocationRelativeTo(null);
        vista.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        vista.setVisible(true);
        
    }
    
}
