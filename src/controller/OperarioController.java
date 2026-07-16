package controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import static model.MetodosPublicos.refrescarVentana;
import static model.MetodosPublicos.vaciarPanel;
import view.OperarioInterfaz;
import view.Titulo;


public class OperarioController extends PacienteController {

    private OperarioInterfaz vista;

    public OperarioController(OperarioInterfaz vista) {
        super(vista);
        agregarActionListener(vista);
    }

    public void agregarActionListener(OperarioInterfaz vista) {
        vista.btnAgendarCitas.addActionListener(this);
        vista.btnPagos.addActionListener(this);
        vista.btnAgendarCita.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        this.vista = (OperarioInterfaz) pacienteI;
        if (e.getSource() == vista.btnAgendarCitas) {
            this.vista.btnAgendarCitas.setEnabled(false);
            vista.habilitarBotonesMenu(vista.btnAgendarCitas);
            vista.AgendarCita();
        }
        if (e.getSource() == vista.btnPagos) {
            this.vista.btnPagos.setEnabled(false);
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
        if (e.getSource() == vista.btnAgendarCita) {
            vista.mostrarVistaTipoConsulta(new Titulo("Agendamiento de ", "Cita"));
        }
    }
}
