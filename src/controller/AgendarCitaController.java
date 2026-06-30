package controller;

import view.AgendarCitaOperarioVista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AgendarCitaController implements ActionListener {

    private AgendarCitaOperarioVista vista;

    public AgendarCitaController(AgendarCitaOperarioVista vista) {
        this.vista = vista;
        agregarListeners();
    }

    private void agregarListeners() {
        vista.btnBuscarPaciente.addActionListener(this);
        vista.btnAgendarCita.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnBuscarPaciente) {
            buscarPaciente();
        } 
        else if (e.getSource() == vista.btnAgendarCita) {
            agendarCita();
        }
    }

    private void buscarPaciente() {
        String id = vista.txtBuscarID.getText().trim();

        if (id.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(vista, 
                "Por favor ingrese un ID de paciente.", 
                "Campo vacío", 
                javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Simulación de búsqueda (luego conectar con BD real)
        if (id.equals("167894320")) {
            vista.cargarDatosPaciente(
                "167894320",
                "Jhon Alejandro Vanegas Morcillo",
                "jhonalejandro@gmail.com",
                "+57 316 123 4567",
                "1960-03-10",
                "Masculino"
            );
            
            javax.swing.JOptionPane.showMessageDialog(vista, 
                "Paciente encontrado y cargado correctamente.", 
                "Éxito", 
                javax.swing.JOptionPane.INFORMATION_MESSAGE);
        } else {
            javax.swing.JOptionPane.showMessageDialog(vista, 
                "No se encontró paciente con ID: " + id, 
                "Paciente no encontrado", 
                javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agendarCita() {
        if (vista.lblIDPaciente.getText().equals("ID: ")) {
            javax.swing.JOptionPane.showMessageDialog(vista, 
                "Primero debe buscar un paciente.", 
                "Paciente no cargado", 
                javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        javax.swing.JOptionPane.showMessageDialog(vista, 
            "Cita agendada exitosamente para el paciente.", 
            "Cita Agendada", 
            javax.swing.JOptionPane.INFORMATION_MESSAGE);

    }
}