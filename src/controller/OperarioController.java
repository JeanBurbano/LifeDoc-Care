package controller;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import model.DatosPagoCita;
import static model.MetodosPublicos.refrescarVentana;
import static model.MetodosPublicos.vaciarPanel;
import view.OperarioInterfaz;

public class OperarioController extends PacienteController {

    private OperarioInterfaz vista;
    private DatosPagoCita datosPago;

    public void agregarActionListener(OperarioInterfaz vista) {
        vista.btnAgendarCitas.addActionListener(this);
        vista.btnPagos.addActionListener(this);
        vista.btnAgendarCita.addActionListener(this);
    }

    public OperarioController(OperarioInterfaz vista) {
        super(vista);
        agregarActionListener(vista);
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
            
            // Trae la ultima cita agendada por este operario que aun no
            // tiene factura, con todos los datos
//            DatosPagoCita datosPago = new DatosPagoCita();
//            if (datosPago == null) {
//                JLabel lblSinPagos = new JLabel("No hay citas pendientes de pago.", SwingConstants.CENTER);
//                lblSinPagos.setFont(new Font("Arial", Font.BOLD, 18));
//                vista.cuerpo2.setLayout(new BorderLayout());
//                vista.cuerpo2.add(lblSinPagos, BorderLayout.CENTER);
//            } else {
//                OperarioInterfaz.PanelPagos panelPagos = vista.new PanelPagos(datosPago);
//                vista.cuerpo2.setLayout(new BorderLayout());
//                vista.cuerpo2.add(panelPagos.panelPagos, BorderLayout.CENTER);
//            }
//            refrescarVentana(vista.cuerpo2);
        }
    }

//        if (e.getSource() == v) {
//            vista.mostrarVistaTipoConsulta(new Titulo("Agendamiento de ", "Cita"));
//
//        }
//        
}
