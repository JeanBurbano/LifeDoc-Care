package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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

public class AgendarCitaOperarioVista extends PacienteInterfaz {

    // Búsqueda
    public JTextField txtBuscarID;
    public JButton btnBuscarPaciente;

    // Panel izquierdo (Donde se ve los pacientes buscados)
    public JPanel panelResumenPaciente;
    public JLabel lblIDPaciente;
    public JLabel lblNombrePaciente;

    // Panel derecho (Formulario)
    public JTextField txtPrimerNombre, txtSegundoNombre;
    public JTextField txtPrimerApellido, txtSegundoApellido;
    public JTextField txtCorreo, txtTelefono;
    public JTextField txtFechaNacimiento;
    public JTextField txtSexo;

    public JButton btnAgendarCita;

    public AgendarCitaOperarioVista(String nombrePersona, String nombreInterfaz, String url) {
        super(nombrePersona, nombreInterfaz, url);

        // Agrega los botones del menú superior
        super.agregarBotonesMenuPaciente();

        // Desactivar boton temporalmente
        habilitarBotonesMenu(null);

        crearVistaAgendarCita();
    }

    private void crearVistaAgendarCita() {
        vaciarPanel(cuerpo2);
        cuerpo2.setLayout(new BorderLayout(15, 15));
        cuerpo2.setBorder(new EmptyBorder(20, 40, 20, 40));

        // Panel superior de búsqueda
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

        // === PANEL IZQUIERDO - Resumen del Paciente ===
        panelResumenPaciente = new JPanel();
        panelResumenPaciente.setLayout(new BoxLayout(panelResumenPaciente, BoxLayout.Y_AXIS));
        panelResumenPaciente.setBorder(BorderFactory.createTitledBorder("Paciente Encontrado"));
        panelResumenPaciente.setPreferredSize(new Dimension(380, 400));

        lblIDPaciente = new JLabel("ID: ");
        lblNombrePaciente = new JLabel("Nombre: ");

        panelResumenPaciente.add(lblIDPaciente);
        panelResumenPaciente.add(Box.createRigidArea(new Dimension(0, 15)));
        panelResumenPaciente.add(lblNombrePaciente);

        // === PANEL DERECHO - Formulario ===
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Información del paciente"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new java.awt.Insets(6, 6, 6, 6);

        txtPrimerNombre = new JTextField(20);
        txtSegundoNombre = new JTextField(20);
        txtPrimerApellido = new JTextField(20);
        txtSegundoApellido = new JTextField(20);
        txtCorreo = new JTextField(20);
        txtTelefono = new JTextField(20);
        txtFechaNacimiento = new JTextField(20);
        txtSexo = new JTextField(20);

        addFormField(panelFormulario, gbc, "Primer Nombre:", txtPrimerNombre, 0);
        addFormField(panelFormulario, gbc, "Segundo Nombre:", txtSegundoNombre, 1);
        addFormField(panelFormulario, gbc, "Primer Apellido:", txtPrimerApellido, 2);
        addFormField(panelFormulario, gbc, "Segundo Apellido:", txtSegundoApellido, 3);
        addFormField(panelFormulario, gbc, "Correo Electrónico:", txtCorreo, 4);
        addFormField(panelFormulario, gbc, "Número de Teléfono:", txtTelefono, 5);
        addFormField(panelFormulario, gbc, "Fecha de Nacimiento:", txtFechaNacimiento, 6);
        addFormField(panelFormulario, gbc, "Sexo Biológico:", txtSexo, 7);

        panelCentral.add(panelResumenPaciente, BorderLayout.WEST);
        panelCentral.add(panelFormulario, BorderLayout.CENTER);

        // Botón principal
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

    // Método para cargar datos desde el controlador
    public void cargarDatosPaciente(String id, String nombreCompleto, String correo, 
                                    String telefono, String fechaNac, String sexo) {
        lblIDPaciente.setText("ID: " + id);
        lblNombrePaciente.setText("Nombre: " + nombreCompleto);
        
    }
}