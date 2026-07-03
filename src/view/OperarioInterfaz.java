package view;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import controller.OperarioController;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.time.LocalDate;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import static model.MetodosPublicos.estilizarBoton;
import static model.MetodosPublicos.refrescarVentana;
import static model.MetodosPublicos.vaciarPanel;

public class OperarioInterfaz extends PacienteInterfaz {

    // Botones principales
    public JButton btnAgendarCitas;
    public JButton btnPagos;
    public JButton btnConsultas;

    // Componentes del módulo Agendar Cita
    private JTextField txtBuscarID;
    private JButton btnBuscarPaciente;
    private JPanel panelResumen;
    private JLabel lblIDPaciente;
    private JLabel lblNombrePaciente;
    private JPanel panelFormulario;
    private JTextField txtPrimerNombre, txtSegundoNombre;
    private JTextField txtPrimerApellido, txtSegundoApellido;
    private JTextField txtCorreo, txtTelefono;
    private JTextField txtFechaNacimiento;
    private JTextField txtSexo;
    private DatePicker datePickerCita;
    private JTextField txtHoraCita;
    private JButton btnAgendarCita;

    public OperarioInterfaz(String nombrePersona, String nombreInterfaz, String url) {
        super(nombrePersona, nombreInterfaz, url);

        cuerpo1.removeAll();

        this.btnAgendarCitas = new JButton("📅 Agendar Citas");
        this.btnPagos = new JButton("💰 Pagos");
        this.btnConsultas = new JButton("📋 Consultas");

        super.agregarBotonCuerpo1(btnAgendarCitas);
        super.agregarBotonCuerpo1(btnPagos);
        super.agregarBotonCuerpo1(btnConsultas);

        new OperarioController(this);

        refrescarVentana(cuerpo1);
    }

    public void AgendarCita() {
        vaciarPanel(cuerpo2);
        cuerpo2.setOpaque(false);
        cuerpo2.setLayout(new BorderLayout(15, 15));
        cuerpo2.setBorder(new EmptyBorder(20, 40, 20, 40));

        // Búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.setOpaque(false);

        JLabel lblBuscar = new JLabel("ID del Paciente: ");
        lblBuscar.setFont(new Font("Arial", Font.BOLD, 16));

        txtBuscarID = new JTextField(15);
        txtBuscarID.setFont(new Font("Arial", Font.PLAIN, 16));

        btnBuscarPaciente = new JButton("Buscar");
        estilizarBoton(btnBuscarPaciente, (byte) 5);

        panelBusqueda.add(lblBuscar);
        panelBusqueda.add(txtBuscarID);
        panelBusqueda.add(btnBuscarPaciente);

        // Panel central
        JPanel panelCentral = new JPanel(new BorderLayout(15, 0));
        panelCentral.setOpaque(false);

        // Resumen
        panelResumen = new JPanel();
        panelResumen.setLayout(new BoxLayout(panelResumen, BoxLayout.Y_AXIS));
        panelResumen.setOpaque(false);
        panelResumen.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO, 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        lblIDPaciente = new JLabel("ID: ");
        lblNombrePaciente = new JLabel("Nombre: ");

        panelResumen.add(lblIDPaciente);
        panelResumen.add(Box.createRigidArea(new Dimension(0, 15)));
        panelResumen.add(lblNombrePaciente);

        // Formulario
        panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setOpaque(false);
        panelFormulario.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO, 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new java.awt.Insets(8, 8, 8, 8);

        txtPrimerNombre = new JTextField(20);
        txtSegundoNombre = new JTextField(20);
        txtPrimerApellido = new JTextField(20);
        txtSegundoApellido = new JTextField(20);
        txtCorreo = new JTextField(20);
        txtTelefono = new JTextField(20);
        txtFechaNacimiento = new JTextField(20);
        txtSexo = new JTextField(20);

        DatePickerSettings settings = new DatePickerSettings();
        settings.setAllowEmptyDates(false);

        datePickerCita = new DatePicker(settings);   // ← Primero crear

        // Bloquear fechas pasadas (permitir hoy y futuro)
        settings.setVetoPolicy(date -> date.isAfter(LocalDate.now().minusDays(1)));

        txtHoraCita = new JTextField(10);

        addFormField(panelFormulario, gbc, "Primer Nombre:", txtPrimerNombre, 0);
        addFormField(panelFormulario, gbc, "Segundo Nombre:", txtSegundoNombre, 1);
        addFormField(panelFormulario, gbc, "Primer Apellido:", txtPrimerApellido, 2);
        addFormField(panelFormulario, gbc, "Segundo Apellido:", txtSegundoApellido, 3);
        addFormField(panelFormulario, gbc, "Correo:", txtCorreo, 4);
        addFormField(panelFormulario, gbc, "Teléfono:", txtTelefono, 5);
        addFormField(panelFormulario, gbc, "Fecha Nacimiento:", txtFechaNacimiento, 6);
        addFormField(panelFormulario, gbc, "Sexo:", txtSexo, 7);

        gbc.gridx = 0; gbc.gridy = 8;
        panelFormulario.add(new JLabel("Fecha de la Cita:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(datePickerCita, gbc);

        gbc.gridx = 0; gbc.gridy = 9;
        panelFormulario.add(new JLabel("Hora (HH:mm):"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtHoraCita, gbc);

        panelCentral.add(panelResumen, BorderLayout.WEST);
        panelCentral.add(panelFormulario, BorderLayout.CENTER);

        btnAgendarCita = new JButton("❤️ Agendar una cita");
        estilizarBoton(btnAgendarCita, (byte) 5);

        cuerpo2.add(panelBusqueda, BorderLayout.NORTH);
        cuerpo2.add(panelCentral, BorderLayout.CENTER);
        cuerpo2.add(btnAgendarCita, BorderLayout.SOUTH);

        refrescarVentana(cuerpo2);
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, String label, JTextField field, int row) {
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    public void cargarDatosPaciente(String id, String nombreCompleto, String correo, 
                                    String telefono, String fechaNac, String sexo) {
        lblIDPaciente.setText("ID: " + id);
        lblNombrePaciente.setText("Nombre: " + nombreCompleto);
    }
    
    
}