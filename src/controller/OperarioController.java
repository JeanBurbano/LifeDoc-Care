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
        // Botones principales del Operario
        vista.btnAgendarCitas.addActionListener(this);
        vista.btnPagos.addActionListener(this);
        vista.btnConsultas.addActionListener(this);
        
        // Botones heredados de la plantilla
        vista.btnMisCitas.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnMisCitas) {
            vista.mostrarVistaPrincipalOperario();
        } 
        else if (e.getSource() == vista.btnAgendarCitas) {
            // Abrir vista de Agendar Cita
            System.out.println("Abriendo vista Agendar Cita...");
            // Aquí puedes abrir AgendarCitaOperarioVista
        } 
        else if (e.getSource() == vista.btnPagos) {
            System.out.println("Abriendo módulo de Pagos...");
        } 
        else if (e.getSource() == vista.btnConsultas) {
            System.out.println("Abriendo módulo de Consultas...");
        }
    }
}