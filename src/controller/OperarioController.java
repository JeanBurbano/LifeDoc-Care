package controller;

import java.awt.BorderLayout;
import view.OperarioInterfaz;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static model.MetodosPublicos.vaciarPanel;
import static model.MetodosPublicos.refrescarVentana;

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
        if (vista.btnPagos != null) {
            vista.btnPagos.addActionListener(this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnAgendarCitas) {
            vista.habilitarBotonesMenu(vista.btnAgendarCitas);
            vista.AgendarCita();
        }else if (e.getSource() == vista.btnPagos) {
            vista.habilitarBotonesMenu(vista.btnPagos);
            
            // Crear el PanelPagos correctamente (clase interna)
            OperarioInterfaz.PanelPagos panelPagos = vista.new PanelPagos(
                "Jhon Alejandro Vanegas Morcillo",
                "167894320",
                "Grupo B",
                "Subsidizado",
                "Contributivo",
                "Consulta General",
                "Odontología",
                "Dr. Jhon Palencia",
                "12/07/2026"
            );

            vaciarPanel(vista.cuerpo2);
            vista.cuerpo2.add(panelPagos.panelPagos, BorderLayout.CENTER);
            refrescarVentana(vista.cuerpo2);
        }
    }
}