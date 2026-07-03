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
        if (vista.btnAgendarCitas != null) {
            vista.btnAgendarCitas.addActionListener(this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnAgendarCitas) {
            vista.AgendarCita();   // Carga el formulario en la misma ventana
        }
    }
}