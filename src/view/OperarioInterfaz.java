package view;

import javax.swing.JButton;
import static model.MetodosPublicos.refrescarVentana;
import static model.MetodosPublicos.vaciarPanel;

public class OperarioInterfaz extends PacienteInterfaz {

    // Botones exclusivos del Operario
    public JButton btnAgendarCitas;
    public JButton btnPagos;
    public JButton btnConsultas;

    public OperarioInterfaz(String nombrePersona, String nombreInterfaz, String url) {
        super(nombrePersona, nombreInterfaz, url);

        // Agregar botones adicionales del operario
        this.btnAgendarCitas = new JButton("📅 Agendar Citas");
        this.btnPagos = new JButton("💰 Pagos");
        this.btnConsultas = new JButton("📋 Consultas");

        super.agregarBotonCuerpo1(btnAgendarCitas);
        super.agregarBotonCuerpo1(btnPagos);
        super.agregarBotonCuerpo1(btnConsultas);
    }

    // Vista principal del Operario (Dashboard)
    public void mostrarVistaPrincipalOperario() {
        vaciarPanel(cuerpo2);   // Limpia el contenido actual

        javax.swing.JLabel label = new javax.swing.JLabel("Bienvenido al Dashboard del Operario");
        label.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 24));
        label.setForeground(COLOR_AZUL_CORPORATIVO);
        label.setHorizontalAlignment(javax.swing.JLabel.CENTER);

        cuerpo2.add(label, java.awt.BorderLayout.CENTER);
        refrescarVentana(cuerpo2);
    }
}