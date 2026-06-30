package controller;

import view.OperarioInterfaz;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OperarioController implements ActionListener {

    private OperarioInterfaz vista;

    public OperarioController(OperarioInterfaz vista) {
        this.vista = vista;
        agregarListeners();
    }

    private void agregarListeners() {
        vista.btnMisCitas.addActionListener(this);
        vista.btnPagos.addActionListener(this);
        vista.btnAgendarCitas.addActionListener(this);
        vista.btnComentarios.addActionListener(this);
        vista.btnNotificaciones.addActionListener(this);
        vista.btnConsultas.addActionListener(this);
        vista.btnAgendar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnMisCitas) {
            vista.mostrarVistaPrincipalOperario();
        } 
        else if (e.getSource() == vista.btnPagos) {
//            vista.mostrarVistaPagos();   // Se crea ahora
        } 
        else if (e.getSource() == vista.btnAgendarCitas || e.getSource() == vista.btnAgendar) {
            System.out.println("");
            // Aqui va la logica de agendar cita
        }
    }

    // Método para cargar citas de ejemplo (Borrar cuando conecte base de datos NO OLVIDAR)
    public void cargarCitasEjemplo() {
        vista.agregarCita("Cita Odontológica", "Viernes 12 de junio 2026", "9:30 am", "Jhon Palencia Morcillo");
        vista.agregarCita("Cita Dermatología", "Jueves 10 de septiembre 2026", "4:20 pm", "Jhon Palencia Morcillo");
    }
}
