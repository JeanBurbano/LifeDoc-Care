package view;

import controller.AgendarCitaController;

public class MainPrueba {
    
    public static void main(String[] args) {
        
        // Crear la vista de Agendar Cita
        AgendarCitaOperarioVista vista = new AgendarCitaOperarioVista(
            "Juanita Velasquez", 
            "Agendar Cita - Operario", 
            "anonymous.png"
        );
        
        // Crear el controlador y conectarlo
        AgendarCitaController controller = new AgendarCitaController(vista);
        
        // Configuración de la ventana
        vista.setSize(1250, 850);
        vista.setLocationRelativeTo(null);
        vista.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        vista.setVisible(true);
    }
}