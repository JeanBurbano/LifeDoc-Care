package view;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import controller.OperarioController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import model.CalculadorPago;
import model.Cita;
import model.CitaDao;
import model.Consultorio;
import model.CreadorPdf;
import model.DatosPagoCita;
import model.MetodosPublicos;
import static model.MetodosPublicos.estilizarBoton;
import static model.MetodosPublicos.estilizarComboBox;
import static model.MetodosPublicos.refrescarVentana;
import static model.MetodosPublicos.vaciarPanel;
import model.Paciente;

public class OperarioInterfaz extends PacienteInterfaz {

    private OperarioController controlador;

    public void setControlador(OperarioController controlador) {
        this.controlador = controlador;
    }

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
    public JButton btnAgendarCita;

    public ArrayList<JButton> listaBotonesCancelarOpeario;
    public ArrayList<JButton> listaBotonesReagendarOpeario;
    
    private Paciente pacienteConsultasActual;

    public OperarioInterfaz(String nombreInterfaz, Paciente usuario) {
        super(nombreInterfaz, usuario);
        
        this.listaBotonesCancelarOpeario = new ArrayList<JButton>();
        this.listaBotonesReagendarOpeario = new ArrayList<JButton>();
        
        this.btnAgendarCitas = new JButton("Paciente ", new ImageIcon("iconsP/schedule.png"));
        this.btnPagos = new JButton("Pagos ", new ImageIcon("iconsP/money-bag.png"));
        this.btnConsultas = new JButton("Consultas ", new ImageIcon("iconsP/done.png"));
        this.btnAgendarCita = new JButton("Agendar una cita", new ImageIcon("iconsP/heart.png"));
        estilizarBoton(btnAgendarCita, (byte) 5);
        super.agregarBotonCuerpo1(btnAgendarCitas);
        super.agregarBotonCuerpo1(btnPagos);
        super.agregarBotonCuerpo1(btnConsultas);

        refrescarVentana(cuerpo1);
    }

    public void AgendarCita() {
        vaciarPanel(cuerpo2);
        cuerpo2.setOpaque(false);
        cuerpo2.setLayout(new BorderLayout(15, 15));
        cuerpo2.setBorder(new EmptyBorder(20, 40, 20, 40));

        // Búsqueda pacientes
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.setOpaque(false);

        JLabel lblBuscar = new JLabel("ID del Paciente: ");
        lblBuscar.setFont(new Font("Arial", Font.BOLD, 16));

        txtBuscarID = new JTextField(15);
        txtBuscarID.setFont(new Font("Arial", Font.PLAIN, 16));

        btnBuscarPaciente = new JButton("Buscar", new ImageIcon("iconsP/magnifying-glass.png"));
        estilizarBoton(btnBuscarPaciente, (byte) 5);

        panelBusqueda.add(lblBuscar);
        panelBusqueda.add(txtBuscarID);
        panelBusqueda.add(btnBuscarPaciente);
        
        // Panel central
        JPanel panelCentral = new JPanel(new BorderLayout(15, 0));
        panelCentral.setOpaque(false);

        // Resumen paciente
        panelResumen = new JPanel();
        panelResumen.setLayout(new BoxLayout(panelResumen, BoxLayout.Y_AXIS));
        panelResumen.setOpaque(false);
        panelResumen.setPreferredSize(new Dimension(380, 450));
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

        addFormField(panelFormulario, gbc, "Primer Nombre:", txtPrimerNombre, 0);
        addFormField(panelFormulario, gbc, "Segundo Nombre:", txtSegundoNombre, 1);
        addFormField(panelFormulario, gbc, "Primer Apellido:", txtPrimerApellido, 2);
        addFormField(panelFormulario, gbc, "Segundo Apellido:", txtSegundoApellido, 3);
        addFormField(panelFormulario, gbc, "Correo:", txtCorreo, 4);
        addFormField(panelFormulario, gbc, "Teléfono:", txtTelefono, 5);
        addFormField(panelFormulario, gbc, "Fecha Nacimiento:", txtFechaNacimiento, 6);
        addFormField(panelFormulario, gbc, "Sexo:", txtSexo, 7);

        panelCentral.add(panelResumen, BorderLayout.WEST);
        panelCentral.add(panelFormulario, BorderLayout.CENTER);

        cuerpo2.add(panelBusqueda, BorderLayout.NORTH);
        cuerpo2.add(panelCentral, BorderLayout.CENTER);
        cuerpo2.add(btnAgendarCita, BorderLayout.SOUTH);

        refrescarVentana(cuerpo2);
        
        btnBuscarPaciente.addActionListener(e -> buscarPaciente());
        
    }
    
    private void buscarPaciente() {
        String id = txtBuscarID.getText().trim();
        if (id.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Por favor ingrese un ID de paciente.",
                "Campo vacío",
                javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        controlador.buscarPaciente(id);
    }
    private void addFormField(JPanel panel, GridBagConstraints gbc, String label, JTextField field, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    public void cargarDatosPaciente(String id, String primerNombre, String segundoNombre,
            String primerApellido, String segundoApellido, String correo, String telefono,
            String fechaNac, String sexo) {
        
    // Aqui se arma el nombre completo del paciente que se busca, sin importar si no cuenta con segundo nombre o apellido
            String nombreCompleto = primerNombre
                + (segundoNombre != null && !segundoNombre.isEmpty() ? " " + segundoNombre : "")
                + " " + primerApellido
                + (segundoApellido != null && !segundoApellido.isEmpty() ? " " + segundoApellido : "");

        lblIDPaciente.setText("ID: " + id);
        lblNombrePaciente.setText("Nombre: " + nombreCompleto);
        
    // Se llenan tambien los campos del formulario para que el Operario
    // pueda revisarlos antes de agendar la cita
    
        txtPrimerNombre.setText(primerNombre);
        txtSegundoNombre.setText(segundoNombre);
        txtPrimerApellido.setText(primerApellido);
        txtSegundoApellido.setText(segundoApellido);
        txtCorreo.setText(correo);
        txtTelefono.setText(telefono);
        txtFechaNacimiento.setText(fechaNac);
        txtSexo.setText(sexo);
        
    }
    
    private String mapearMetodoPagoABD(String metodoCombo) {
        switch (metodoCombo) {
            case "Tarjeta débito":
                return "Tarjeta debito";
            case "Tarjeta crédito":
                return "Tarjeta de credito";
            case "Transferencia":
                return "Transferencia bancaria";
            case "Efectivo":
            default:
                return "Efectivo";
        }
    }
    
    // ---------------------------- MODULO DE PAGOS ----------------------------

    // Punto de entrada del botón "Pagos": muestra el buscador de paciente,
    // igual que en Consultas, para listar sus citas pendientes de pago.
    public void mostrarVistaPagos() {
        vaciarPanel(cuerpo2);
        cuerpo2.setOpaque(false);
        cuerpo2.setLayout(new BorderLayout(0, 15));
        cuerpo2.setBorder(new EmptyBorder(20, 40, 20, 40));

        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));
        panelSuperior.setOpaque(false);

        JLabel lblTitulo = new JLabel("Pagos");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 26));
        lblTitulo.setForeground(COLOR_AZUL_CORPORATIVO);
        lblTitulo.setBorder(new EmptyBorder(0, 0, 10, 0));

        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.setOpaque(false);
        JLabel lblBuscar = new JLabel("ID del Paciente: ");
        lblBuscar.setFont(new Font("Arial", Font.BOLD, 16));
        JTextField txtBuscarIdPagos = new JTextField(15);
        txtBuscarIdPagos.setFont(new Font("Arial", Font.PLAIN, 16));
        JButton btnBuscarPagos = new JButton("Buscar", new ImageIcon("iconsP/magnifying-glass.png"));
        estilizarBoton(btnBuscarPagos, (byte) 5);
        panelBusqueda.add(lblBuscar);
        panelBusqueda.add(txtBuscarIdPagos);
        panelBusqueda.add(btnBuscarPagos);

        panelSuperior.add(lblTitulo);
        panelSuperior.add(panelBusqueda);

        JPanel panelListaPagos = new JPanel();
        panelListaPagos.setLayout(new BoxLayout(panelListaPagos, BoxLayout.Y_AXIS));
        panelListaPagos.setOpaque(false);
        JScrollPane scrollListaPagos = new JScrollPane(panelListaPagos);
        scrollListaPagos.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollListaPagos.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollListaPagos.setOpaque(false);
        scrollListaPagos.getViewport().setOpaque(false);
        scrollListaPagos.setBorder(BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO));

        btnBuscarPagos.addActionListener(e -> {
            String id = txtBuscarIdPagos.getText().trim();
            if (id.isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "Por favor ingrese un ID de paciente.",
                        "Campo vacío", javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }
            Paciente paciente = controlador.buscarPacientePorId(id);
            if (paciente == null) {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "No se encontró paciente con ID: " + id,
                        "Paciente no encontrado", javax.swing.JOptionPane.ERROR_MESSAGE);
                return;
            }

            List<DatosPagoCita> citasPendientes = controlador.buscarCitasPendientesPago(paciente.getIdUsuario());
            vaciarPanel(panelListaPagos);
            if (citasPendientes.isEmpty()) {
                JLabel lblSinPagos = new JLabel("Este paciente no tiene citas pendientes de pago.");
                lblSinPagos.setFont(new Font("Arial", Font.PLAIN, 16));
                lblSinPagos.setBorder(new EmptyBorder(20, 10, 20, 10));
                panelListaPagos.add(lblSinPagos);
            } else {
                for (DatosPagoCita datosPago : citasPendientes) {
                    panelListaPagos.add(construirTarjetaPago(datosPago));
                }
            }
            refrescarVentana(panelListaPagos);
        });

        cuerpo2.add(panelSuperior, BorderLayout.NORTH);
        cuerpo2.add(scrollListaPagos, BorderLayout.CENTER);
        refrescarVentana(cuerpo2);
    }

    // Tarjeta con los datos de una cita pendiente de pago y el botón para cobrarla
    private JPanel construirTarjetaPago(DatosPagoCita datosPago) {
        JPanel tarjeta = new JPanel(new BorderLayout());
        tarjeta.setOpaque(false);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY),
                new EmptyBorder(12, 10, 12, 10)));

        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.setOpaque(false);

        JLabel lblTituloCita = new JLabel("Cita " + datosPago.getEspecialidad());
        lblTituloCita.setFont(new Font("Arial", Font.BOLD, 17));
        lblTituloCita.setForeground(COLOR_AZUL_CORPORATIVO);

        JLabel lblFecha = new JLabel("Fecha: " + datosPago.getFechaCita());
        JLabel lblHora = new JLabel("Hora: " + datosPago.getHoraCita());
        JLabel lblMedico = new JLabel("Médico: " + datosPago.getNombreMedico());
        lblFecha.setFont(new Font("Arial", Font.PLAIN, 14));
        lblHora.setFont(new Font("Arial", Font.PLAIN, 14));
        lblMedico.setFont(new Font("Arial", Font.PLAIN, 14));
        lblFecha.setForeground(COLOR_GRIS_SUBTITULO);
        lblHora.setForeground(COLOR_GRIS_SUBTITULO);
        lblMedico.setForeground(COLOR_GRIS_SUBTITULO);

        panelInfo.add(lblTituloCita);
        panelInfo.add(lblFecha);
        panelInfo.add(lblHora);
        panelInfo.add(lblMedico);

        JButton btnCobrar = new JButton("Cobrar", new ImageIcon("iconsP/money-bag.png"));
        estilizarBoton(btnCobrar, (byte) 5);
        btnCobrar.addActionListener(e -> mostrarPanelPagoParaCita(datosPago));

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        panelBoton.setOpaque(false);
        panelBoton.add(btnCobrar);

        tarjeta.add(panelInfo, BorderLayout.WEST);
        tarjeta.add(panelBoton, BorderLayout.EAST);
        return tarjeta;
    }

    // Abre el panel de cobro (PanelPagos) para la cita elegida.
    private void mostrarPanelPagoParaCita(DatosPagoCita datosPago) {
        vaciarPanel(cuerpo2);
        cuerpo2.setOpaque(false);
        cuerpo2.setLayout(new BorderLayout());
        PanelPagos panelPagos = new PanelPagos(datosPago);
        cuerpo2.add(panelPagos.panelPagos, BorderLayout.CENTER);
        refrescarVentana(cuerpo2);
    }

    public class PanelPagos {

        public JPanel panelPagos;
        public JButton btnAceptar;
        public JComboBox cmbMetodoPago;
        
        private int idCita;
        private DatosPagoCita datos;
        private String codigoFactura;
        private String fechaHoraFactura;
        private double valorConsulta;
        private double montoSubsidio;
        private double valorNeto;   

        public PanelPagos(DatosPagoCita datos) {

            this.idCita = datos.getIdCita();
            this.datos = datos;
            this.codigoFactura = CalculadorPago.generarCodigoFactura();
            this.fechaHoraFactura = CalculadorPago.obtenerFechaHoraActual();
            this.valorConsulta = CalculadorPago.obtenerValorConsulta(datos.getEspecialidad());
            this.montoSubsidio = CalculadorPago.calcularMontoSubsidio(valorConsulta, datos.getSisben());
            this.valorNeto = CalculadorPago.calcularValorNeto(valorConsulta, datos.getSisben());

            this.panelPagos = new JPanel();
            this.panelPagos.setLayout(new BorderLayout(30, 0));
            this.panelPagos.setOpaque(false);
            this.panelPagos.setBorder(new EmptyBorder(20, 0, 0, 0));

            String fechaCitaTexto = datos.getFechaCita().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    + ", " + datos.getHoraCita().format(java.time.format.DateTimeFormatter.ofPattern("hh:mm a"));

            JPanel panelFactura = construirPanelFactura(datos.getNombrePaciente(), datos.getIdentificacionPaciente(),
                    datos.getSisben(), datos.getClasificacionSisbenTexto(), datos.getRegimen(), "Consulta Externa",
                    datos.getEspecialidad(), datos.getNombreMedico(), fechaCitaTexto);
            JPanel panelMetodosPago = construirPanelMetodosPago();

            this.panelPagos.add(panelFactura, BorderLayout.CENTER);
            this.panelPagos.add(panelMetodosPago, BorderLayout.EAST);
        }

        private JPanel construirPanelFactura(String nombrePaciente, String identificacion,
                String grupoSisben, String clasificacionSisben, String regimen,
                String condicionAtencion, String especialidad, String nombreMedico, String fechaCita) {
            JPanel panelFactura = new JPanel();
            panelFactura.setLayout(new BoxLayout(panelFactura, BoxLayout.Y_AXIS));
            panelFactura.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO),
                    BorderFactory.createEmptyBorder(20, 25, 20, 25)));
            panelFactura.setOpaque(false);

            JPanel panelEncabezadoFactura = new JPanel();
            panelEncabezadoFactura.setLayout(new BoxLayout(panelEncabezadoFactura, BoxLayout.Y_AXIS));
            panelEncabezadoFactura.setOpaque(false);
            panelEncabezadoFactura.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel lblTituloFactura = new JLabel("Factura");
            lblTituloFactura.setFont(new Font("Arial", Font.BOLD, 26));
            lblTituloFactura.setForeground(COLOR_AZUL_CORPORATIVO);

            JPanel panelNombreConsultorio = new JPanel();
            panelNombreConsultorio.setLayout(new BoxLayout(panelNombreConsultorio, BoxLayout.X_AXIS));
            panelNombreConsultorio.setOpaque(false);
            panelNombreConsultorio.setAlignmentX(Component.LEFT_ALIGNMENT);
            JLabel lblLifeDoc = new JLabel("LifeDoc");
            lblLifeDoc.setFont(new Font("Arial", Font.BOLD, 16));
            lblLifeDoc.setForeground(COLOR_AZUL_CORPORATIVO);
            JLabel lblCare = new JLabel(" Care");
            lblCare.setFont(new Font("Arial", Font.BOLD, 16));
            lblCare.setForeground(COLOR_VERDE_ACENTO);
            panelNombreConsultorio.add(lblLifeDoc);
            panelNombreConsultorio.add(lblCare);

            JLabel lblCodigoFactura = new JLabel("Factura Digital de Servicio No: " + this.codigoFactura);
            lblCodigoFactura.setFont(new Font("Arial", Font.PLAIN, 13));
            lblCodigoFactura.setForeground(COLOR_GRIS_SUBTITULO);
            lblCodigoFactura.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel lblFechaHora = new JLabel("Fecha/Hora Emisión: " + this.fechaHoraFactura);
            lblFechaHora.setFont(new Font("Arial", Font.PLAIN, 13));
            lblFechaHora.setForeground(COLOR_GRIS_SUBTITULO);
            lblFechaHora.setAlignmentX(Component.LEFT_ALIGNMENT);

            panelEncabezadoFactura.add(lblTituloFactura);
            panelEncabezadoFactura.add(panelNombreConsultorio);
            panelEncabezadoFactura.add(Box.createRigidArea(new Dimension(0, 5)));
            panelEncabezadoFactura.add(lblCodigoFactura);
            panelEncabezadoFactura.add(lblFechaHora);

            JPanel separador1 = crearSeparador();

            JPanel panelDatos = new JPanel();
            panelDatos.setLayout(new BorderLayout(40, 0));
            panelDatos.setOpaque(false);
            panelDatos.setAlignmentX(Component.LEFT_ALIGNMENT);

            JPanel columnasPaciente = new JPanel();
            columnasPaciente.setLayout(new BoxLayout(columnasPaciente, BoxLayout.Y_AXIS));
            columnasPaciente.setOpaque(false);
            JLabel lblTituloPaciente = new JLabel("DATOS DEL PACIENTE");
            lblTituloPaciente.setFont(new Font("Arial", Font.BOLD, 13));
            lblTituloPaciente.setForeground(COLOR_GRIS_SUBTITULO);
            agregarFilaDato(columnasPaciente, "Nombre:", nombrePaciente);
            agregarFilaDato(columnasPaciente, "Identificación:", identificacion);
            agregarFilaDato(columnasPaciente, "Régimen:", regimen);
            columnasPaciente.add(Box.createRigidArea(new Dimension(0, 10)));
            JLabel lblClasifSisben = new JLabel("Clasificación SISBÉN: " + grupoSisben);
            lblClasifSisben.setFont(new Font("Arial", Font.BOLD, 13));
            lblClasifSisben.setForeground(COLOR_AZUL_CORPORATIVO);
            JLabel lblCondicion = new JLabel("Condición de Atención: " + condicionAtencion + "");
            lblCondicion.setFont(new Font("Arial", Font.PLAIN, 13));
            lblCondicion.setForeground(new Color(60, 60, 60));
            columnasPaciente.add(lblTituloPaciente);
            columnasPaciente.add(Box.createRigidArea(new Dimension(0, 5)));
            columnasPaciente.add(lblClasifSisben);
            columnasPaciente.add(Box.createRigidArea(new Dimension(0, 5)));
            columnasPaciente.add(lblCondicion);

            JPanel columnasCita = new JPanel();
            columnasCita.setLayout(new BoxLayout(columnasCita, BoxLayout.Y_AXIS));
            columnasCita.setOpaque(false);
            JLabel lblTituloCita = new JLabel("DETALLE DE LA CITA");
            lblTituloCita.setFont(new Font("Arial", Font.BOLD, 13));
            lblTituloCita.setForeground(COLOR_GRIS_SUBTITULO);
            columnasCita.add(lblTituloCita);
            columnasCita.add(Box.createRigidArea(new Dimension(0, 5)));
            agregarFilaDato(columnasCita, "Especialidad:", especialidad);
            agregarFilaDato(columnasCita, "Médico Asignado:", nombreMedico);
            agregarFilaDato(columnasCita, "Fecha y Hora:", fechaCita + ", " + CalculadorPago.obtenerHoraActual());

            panelDatos.add(columnasPaciente, BorderLayout.WEST);
            panelDatos.add(columnasCita, BorderLayout.CENTER);

            JPanel separador2 = crearSeparador();

            JPanel panelLiquidacion = new JPanel();
            panelLiquidacion.setLayout(new BoxLayout(panelLiquidacion, BoxLayout.Y_AXIS));
            panelLiquidacion.setOpaque(false);
            panelLiquidacion.setAlignmentX(Component.LEFT_ALIGNMENT);
            JLabel lblTituloLiquidacion = new JLabel("LIQUIDACIÓN DEL COBRO");
            lblTituloLiquidacion.setFont(new Font("Arial", Font.BOLD, 13));
            lblTituloLiquidacion.setForeground(COLOR_GRIS_SUBTITULO);

            agregarFilaDato(panelLiquidacion, "Valor Base de la Consulta:",
                    CalculadorPago.formatearPesos(valorConsulta));
            agregarFilaDato(panelLiquidacion, "Subsidio Entidad (Régimen " + clasificacionSisben + "):",
                    CalculadorPago.formatearPesos(montoSubsidio));
            agregarFilaDato(panelLiquidacion, "Cuota Moderadora / Copago Paciente:",
                    CalculadorPago.formatearPesos(valorNeto));
            panelLiquidacion.add(lblTituloLiquidacion);

            JPanel separador3 = crearSeparador();

            JPanel panelValorNeto = new JPanel();
            panelValorNeto.setLayout(new BoxLayout(panelValorNeto, BoxLayout.X_AXIS));
            panelValorNeto.setOpaque(false);
            panelValorNeto.setAlignmentX(Component.LEFT_ALIGNMENT);
            JLabel lblEtiquetaNeto = new JLabel("VALOR NETO A PAGAR PACIENTE: ");
            lblEtiquetaNeto.setFont(new Font("Arial", Font.BOLD, 16));
            lblEtiquetaNeto.setForeground(COLOR_AZUL_CORPORATIVO);
            JLabel lblValorNeto = new JLabel(CalculadorPago.formatearPesos(valorNeto));
            lblValorNeto.setFont(new Font("Arial", Font.BOLD, 18));
            lblValorNeto.setForeground(COLOR_AZUL_CORPORATIVO);
            panelValorNeto.add(lblEtiquetaNeto);
            panelValorNeto.add(lblValorNeto);

            panelFactura.add(panelEncabezadoFactura);
            panelFactura.add(Box.createRigidArea(new Dimension(0, 10)));
            panelFactura.add(separador1);
            panelFactura.add(Box.createRigidArea(new Dimension(0, 10)));
            panelFactura.add(panelDatos);
            panelFactura.add(Box.createRigidArea(new Dimension(0, 10)));
            panelFactura.add(separador2);
            panelFactura.add(Box.createRigidArea(new Dimension(0, 10)));
            panelFactura.add(panelLiquidacion);
            panelFactura.add(Box.createRigidArea(new Dimension(0, 10)));
            panelFactura.add(separador3);
            panelFactura.add(Box.createRigidArea(new Dimension(0, 10)));
            panelFactura.add(panelValorNeto);

            return panelFactura;
        }

        private JPanel construirPanelMetodosPago() {
            JPanel panelMetodosPago = new JPanel();
            panelMetodosPago.setLayout(new BoxLayout(panelMetodosPago, BoxLayout.Y_AXIS));
            panelMetodosPago.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO),
                    BorderFactory.createEmptyBorder(20, 20, 20, 20)));
            panelMetodosPago.setOpaque(false);
            panelMetodosPago.setPreferredSize(new Dimension(280, 0));

            JLabel lblTituloMetodos = new JLabel("Métodos de Pago");
            lblTituloMetodos.setFont(new Font("Arial", Font.BOLD, 20));
            lblTituloMetodos.setForeground(COLOR_AZUL_CORPORATIVO);
            lblTituloMetodos.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel labelQR = generarImagenQR(this.codigoFactura, 200, 200);
            labelQR.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel lblEscanea = new JLabel("Escanéame");
            lblEscanea.setFont(new Font("Arial", Font.BOLD, 15));
            lblEscanea.setForeground(COLOR_GRIS_SUBTITULO);
            lblEscanea.setAlignmentX(Component.CENTER_ALIGNMENT);

            JPanel separadorQR = crearSeparador();
            separadorQR.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel lblMetodo = new JLabel("Método de pago empleado");
            lblMetodo.setFont(new Font("Arial", Font.PLAIN, 13));
            lblMetodo.setForeground(COLOR_GRIS_SUBTITULO);
            lblMetodo.setAlignmentX(Component.CENTER_ALIGNMENT);

            this.cmbMetodoPago = new JComboBox(new String[]{"Efectivo", "Tarjeta débito", "Tarjeta crédito", "Transferencia"});
            MetodosPublicos.estilizarComboBox(cmbMetodoPago);
            this.cmbMetodoPago.setAlignmentX(Component.CENTER_ALIGNMENT);
            this.cmbMetodoPago.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

            this.btnAceptar = new JButton("Aceptar", new ImageIcon("iconsP/accept.png"));
            MetodosPublicos.estilizarBoton(btnAceptar, (byte) 5);
            this.btnAceptar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
            this.btnAceptar.setAlignmentX(Component.CENTER_ALIGNMENT);

            this.btnAceptar.addActionListener(e -> {
                
                String metodoSeleccionado = (String) this.cmbMetodoPago.getSelectedItem();

                if ("Efectivo".equals(metodoSeleccionado)) {
                    boolean pagoConfirmado = mostrarModalEfectivo(this.valorNeto);
                    if (!pagoConfirmado) {
                        return;
                    }
                }

                String metodoParaBD = mapearMetodoPagoABD(metodoSeleccionado);
                String descripcion = "Consulta " + datos.getEspecialidad();

                // El guardado en base de datos ahora lo hace el controlador.
                boolean pagoRegistrado = controlador.registrarPago(this.idCita, descripcion, this.valorNeto, metodoParaBD);

                if (!pagoRegistrado) {
                    javax.swing.JOptionPane.showMessageDialog(OperarioInterfaz.this,
                            "No se pudo guardar la factura. Verifica que XAMPP (MySQL) este activo.",
                            "Error al guardar el pago",
                            javax.swing.JOptionPane.ERROR_MESSAGE);
                    return;
                }

                OperarioInterfaz.this.mostrarFacturaDetallada(codigoFactura, fechaHoraFactura,
                        datos.getNombrePaciente(), datos.getIdentificacionPaciente(),
                        datos.getRegimen(), datos.getClasificacionSisbenTexto(),
                        datos.getEspecialidad(), datos.getNombreMedico(),
                        datos.getFechaCita(), datos.getHoraCita(),
                        valorConsulta, montoSubsidio, valorNeto, metodoSeleccionado);
            });

            panelMetodosPago.add(lblTituloMetodos);
            panelMetodosPago.add(Box.createRigidArea(new Dimension(0, 15)));
            panelMetodosPago.add(labelQR);
            panelMetodosPago.add(Box.createRigidArea(new Dimension(0, 5)));
            panelMetodosPago.add(lblEscanea);
            panelMetodosPago.add(Box.createRigidArea(new Dimension(0, 15)));
            panelMetodosPago.add(separadorQR);
            panelMetodosPago.add(Box.createRigidArea(new Dimension(0, 15)));
            panelMetodosPago.add(lblMetodo);
            panelMetodosPago.add(Box.createRigidArea(new Dimension(0, 8)));
            panelMetodosPago.add(cmbMetodoPago);
            panelMetodosPago.add(Box.createRigidArea(new Dimension(0, 20)));
            panelMetodosPago.add(btnAceptar);

            return panelMetodosPago;
        }

        private void agregarFilaDato(JPanel panel, String etiqueta, String valor) {
            JPanel fila = new JPanel();
            fila.setLayout(new BoxLayout(fila, BoxLayout.X_AXIS));
            fila.setOpaque(false);
            fila.setAlignmentX(Component.LEFT_ALIGNMENT);
            JLabel lblEtiqueta = new JLabel(etiqueta + " ");
            lblEtiqueta.setFont(new Font("Arial", Font.BOLD, 13));
            lblEtiqueta.setForeground(new Color(60, 60, 60));
            JLabel lblValor = new JLabel(valor);
            lblValor.setFont(new Font("Arial", Font.PLAIN, 13));
            lblValor.setForeground(new Color(60, 60, 60));
            fila.add(lblEtiqueta);
            fila.add(lblValor);
            panel.add(fila);
            panel.add(Box.createRigidArea(new Dimension(0, 4)));
        }

        private JPanel crearSeparador() {
            JPanel separador = new JPanel();
            separador.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
            separador.setPreferredSize(new Dimension(0, 1));
            separador.setBackground(new Color(200, 210, 220));
            separador.setAlignmentX(Component.LEFT_ALIGNMENT);
            return separador;
        }

        private JLabel generarImagenQR(String contenido, int ancho, int alto) {
            JLabel labelQR = new JLabel();
            try {
                BitMatrix matrix = new MultiFormatWriter().encode(
                        contenido,
                        BarcodeFormat.QR_CODE,
                        ancho, alto
                );
                BufferedImage imagenQR = MatrixToImageWriter.toBufferedImage(matrix);
                labelQR.setIcon(new ImageIcon(imagenQR));
            } catch (Exception e) {
                labelQR.setText("[Error al generar QR]");
                labelQR.setForeground(Color.RED);
            }
            return labelQR;
        }

        public String getCodigoFactura() {
            return codigoFactura;
        }

        public double getValorNeto() {
            return valorNeto;
        }
    }

    public void mostrarFacturaDetallada(String codigoFactura, String fechaHoraFactura,
            String nombrePaciente, String identificacion, String regimen, String clasificacionSisben,
            String especialidad, String nombreMedico, LocalDate fechaCita, java.time.LocalTime horaCita,
            double valorConsulta, double montoSubsidio, double valorNeto, String metodoPago) {

        vaciarPanel(cuerpo2);
        cuerpo2.setOpaque(false);
        cuerpo2.setLayout(new BorderLayout(25, 0));
        cuerpo2.setBorder(new EmptyBorder(20, 40, 20, 40));

        JPanel panelPrincipal = new JPanel(new BorderLayout(25, 0));
        panelPrincipal.setOpaque(false);
        
        String fechaCitaTexto = fechaCita.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                + " - " + horaCita.format(java.time.format.DateTimeFormatter.ofPattern("hh:mm a"));

        //PANEL IZQUIERDO: FACTURA DETALLADA
        JPanel panelFactura = new JPanel();
        panelFactura.setLayout(new BoxLayout(panelFactura, BoxLayout.Y_AXIS));
        panelFactura.setOpaque(false);
        panelFactura.setPreferredSize(new Dimension(700, 0));
        panelFactura.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO, 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel lblTitulo = new JLabel("Factura");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(COLOR_AZUL_CORPORATIVO);

        panelFactura.add(lblTitulo);
        panelFactura.add(new JLabel("LifeDoc Care"));
        panelFactura.add(new JLabel("Factura Digital No: " + codigoFactura));
        panelFactura.add(new JLabel("Fecha/Hora Emisión: " + fechaHoraFactura));

        panelFactura.add(Box.createRigidArea(new Dimension(0, 15)));

        // SEPARADOR
        JPanel separador1 = new JPanel();
        separador1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        separador1.setPreferredSize(new Dimension(0, 1));
        separador1.setBackground(new Color(200, 210, 220));
        panelFactura.add(separador1);

        panelFactura.add(new JLabel("DATOS DEL PACIENTE"));
        panelFactura.add(new JLabel("Nombre: " + nombrePaciente));
        panelFactura.add(new JLabel("Identificación: " + identificacion));
        panelFactura.add(new JLabel("Régimen: " + regimen));
        panelFactura.add(new JLabel("Clasificación SISBEN: " + clasificacionSisben));

        panelFactura.add(Box.createRigidArea(new Dimension(0, 10)));

        // SEPARADOR
        JPanel separador2 = new JPanel();
        separador1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        separador1.setPreferredSize(new Dimension(0, 1));
        separador1.setBackground(new Color(200, 210, 220));
        panelFactura.add(separador2);

        panelFactura.add(new JLabel("DETALLE DE LA CITA"));
        panelFactura.add(new JLabel("Especialidad: " + especialidad));
        panelFactura.add(new JLabel("Médico Asignado: " + nombreMedico));
        panelFactura.add(new JLabel("Fecha y Hora: " + fechaCitaTexto));

        panelFactura.add(Box.createRigidArea(new Dimension(0, 10)));

        // SEPARADOR
        JPanel separador3 = new JPanel();
        separador1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        separador1.setPreferredSize(new Dimension(0, 1));
        separador1.setBackground(new Color(200, 210, 220));
        panelFactura.add(separador3);

        panelFactura.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel lblValorNeto = new JLabel("VALOR NETO A PAGAR PACIENTE: " + CalculadorPago.formatearPesos(valorNeto));
        lblValorNeto.setFont(new Font("Arial", Font.BOLD, 18));
        lblValorNeto.setForeground(COLOR_AZUL_CORPORATIVO);
        panelFactura.add(lblValorNeto);

        // PANEL DERECHO: BOTONES DE PAGO
        JPanel panelConfirmacion  = new JPanel();
        panelConfirmacion.setLayout(new BoxLayout(panelConfirmacion, BoxLayout.Y_AXIS));
        panelConfirmacion.setOpaque(false);
        panelConfirmacion.setPreferredSize(new Dimension(240, 0));
        panelConfirmacion.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO, 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel lblExito = new JLabel("Pago registrado correctamente");
        lblExito.setFont(new Font("Arial", Font.BOLD, 16));
        lblExito.setForeground(new Color(35, 105, 60));
        lblExito.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblMetodoUsado = new JLabel("Metodo de pago" + metodoPago);
        lblMetodoUsado.setFont(new Font("Arial", Font.PLAIN, 14));
        lblMetodoUsado.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton btnDebito = new JButton("Tarjeta débito", new ImageIcon("iconsP/atm-card.png"));
        JButton btnCredito = new JButton("Tarjeta crédito", new ImageIcon("iconsP/atm-card.png"));
        JButton btnTransferencia = new JButton("Transferencia", new ImageIcon("iconsP/bank.png"));

        estilizarBoton(btnDebito, (byte) 4);
        estilizarBoton(btnCredito, (byte) 4);
        estilizarBoton(btnTransferencia, (byte) 4);

        // Si ya se pagó en efectivo, no tiene sentido dejar que se abran
        // los modales de tarjeta o transferencia, así que se bloquean.
        boolean pagoEnEfectivo = "Efectivo".equals(metodoPago);
        btnDebito.setEnabled(!pagoEnEfectivo);
        btnCredito.setEnabled(!pagoEnEfectivo);
        btnTransferencia.setEnabled(!pagoEnEfectivo);

        btnDebito.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCredito.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnTransferencia.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        btnDebito.setPreferredSize(new Dimension(Integer.MAX_VALUE, 45));
        btnCredito.setPreferredSize(new Dimension(Integer.MAX_VALUE, 45));
        btnTransferencia.setPreferredSize(new Dimension(Integer.MAX_VALUE, 45));

        btnDebito.addActionListener(e -> mostrarModalTarjeta("Tarjeta Debito"));
        btnCredito.addActionListener(e -> mostrarModalTarjeta( "Tarjeta Credito"));
        btnTransferencia.addActionListener(e -> mostrarModalTransferencia());
        
        JButton btnFinalizar = new JButton("Finalizar");
        estilizarBoton(btnFinalizar, (byte) 5);
        btnFinalizar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnFinalizar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        
        JButton btnDescargarFactura = new JButton("Descargar factura (PDF)", new ImageIcon("iconsP/descargar.png"));
        estilizarBoton(btnDescargarFactura, (byte) 4);
        btnDescargarFactura.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnDescargarFactura.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

        btnDescargarFactura.addActionListener(e -> {
            String contenidoFactura = "\n\n"
                    + "Factura Digital No: " + codigoFactura + "\n"
                    + "Fecha/Hora Emisión: " + fechaHoraFactura + "\n\n"
                    + "DATOS DEL PACIENTE\n"
                    + "Nombre: " + nombrePaciente + "\n"
                    + "Identificación: " + identificacion + "\n"
                    + "Régimen: " + regimen + "\n"
                    + "Clasificación SISBEN: " + clasificacionSisben + "\n\n"
                    + "DETALLE DE LA CITA\n"
                    + "Especialidad: " + especialidad + "\n"
                    + "Médico Asignado: " + nombreMedico + "\n"
                    + "Fecha y Hora: " + fechaCitaTexto + "\n\n"
                    + "DETALLE DEL PAGO\n"
                    + "Valor consulta: " + CalculadorPago.formatearPesos(valorConsulta) + "\n"
                    + "Subsidio: " + CalculadorPago.formatearPesos(montoSubsidio) + "\n"
                    + "Valor neto pagado: " + CalculadorPago.formatearPesos(valorNeto) + "\n"
                    + "Método de pago: " + metodoPago;

            String nombreArchivo = "factura_" + codigoFactura;
            CreadorPdf.constructorCreadorPdf(nombreArchivo, "Factura");
            
        });
        
        btnFinalizar.addActionListener(e -> {
            btnPagos.setEnabled(true);
            btnPagos.doClick();
            
        });
        
        panelConfirmacion.add(lblExito);
        panelConfirmacion.add(Box.createRigidArea(new Dimension(0, 20)));
        panelConfirmacion.add(lblMetodoUsado);
        panelConfirmacion.add(lblMetodoUsado);
        panelConfirmacion.add(Box.createRigidArea(new Dimension(0, 20)));
        panelConfirmacion.add(btnDebito);
        panelConfirmacion.add(Box.createRigidArea(new Dimension(0, 12)));
        panelConfirmacion.add(btnCredito);
        panelConfirmacion.add(Box.createRigidArea(new Dimension(0, 12)));
        panelConfirmacion.add(btnTransferencia);
        panelConfirmacion.add(Box.createRigidArea(new Dimension(0, 20)));
        panelConfirmacion.add(btnDescargarFactura);
        panelConfirmacion.add(Box.createRigidArea(new Dimension(0, 30)));
        panelConfirmacion.add(btnFinalizar);
 
        panelPrincipal.add(panelFactura, BorderLayout.WEST);
        panelPrincipal.add(panelConfirmacion, BorderLayout.CENTER);

        cuerpo2.add(panelPrincipal, BorderLayout.CENTER);

        refrescarVentana(cuerpo2);
    }

    // Monto máximo que se puede recibir en efectivo
    private static final double MAXIMO_MONTO_EFECTIVO = 2_500_000;

    // Ventanas emergentes, donde se mostrata como una espcie de modal para el metodo de pago de las tarjetas
    // Ventana emergente para pago en efectivo: pide el monto recibido y
    // calcula el cambio a devolver en vivo mientras el operario escribe.
    // Devuelve true si se confirmó con un monto válido, false si se canceló.
    private boolean mostrarModalEfectivo(double valorAPagar) {
        JPanel modal = new JPanel();
        modal.setLayout(new BoxLayout(modal, BoxLayout.Y_AXIS));
        modal.setPreferredSize(new Dimension(360, 260));

        JLabel lblValorAPagar = new JLabel("Valor a pagar: " + CalculadorPago.formatearPesos(valorAPagar));
        lblValorAPagar.setFont(new Font("Arial", Font.BOLD, 15));
        lblValorAPagar.setForeground(COLOR_AZUL_CORPORATIVO);
        lblValorAPagar.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField txtMontoRecibido = MetodosPublicos.crearCampoTexto();

        // Filtro que solo deja escribir dígitos (0-9)
        javax.swing.text.AbstractDocument documentoMonto = (javax.swing.text.AbstractDocument) txtMontoRecibido.getDocument();
        documentoMonto.setDocumentFilter(new javax.swing.text.DocumentFilter() {
            private boolean esSoloNumeros(String texto) {
                return texto.matches("[0-9]*");
            }

            @Override
            public void insertString(FilterBypass fb, int offset, String string, javax.swing.text.AttributeSet attr)
                    throws javax.swing.text.BadLocationException {
                if (esSoloNumeros(string)) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, javax.swing.text.AttributeSet attrs)
                    throws javax.swing.text.BadLocationException {
                if (esSoloNumeros(text)) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });

        JPanel panelCampoMonto = MetodosPublicos.crearCampoConEtiqueta("Monto recibido", txtMontoRecibido);
        panelCampoMonto.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblCambio = new JLabel("Cambio a devolver: " + CalculadorPago.formatearPesos(0));
        lblCambio.setFont(new Font("Arial", Font.BOLD, 16));
        lblCambio.setForeground(new Color(35, 105, 60));
        lblCambio.setAlignmentX(Component.CENTER_ALIGNMENT);

        txtMontoRecibido.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            private void recalcular() {
                try {
                    double montoRecibido = Double.parseDouble(txtMontoRecibido.getText().trim());
                    double cambio = montoRecibido - valorAPagar;
                    if (cambio >= 0) {
                        lblCambio.setText("Cambio a devolver: " + CalculadorPago.formatearPesos(cambio));
                        lblCambio.setForeground(new Color(35, 105, 60));
                    } else {
                        lblCambio.setText("Faltan: " + CalculadorPago.formatearPesos(Math.abs(cambio)));
                        lblCambio.setForeground(Color.RED);
                    }
                } catch (NumberFormatException ex) {
                    lblCambio.setText("Cambio a devolver: " + CalculadorPago.formatearPesos(0));
                    lblCambio.setForeground(new Color(35, 105, 60));
                }
            }

            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                recalcular();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                recalcular();
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                recalcular();
            }
        });

        modal.add(MetodosPublicos.crearSeparador("Pago en Efectivo"));
        modal.add(Box.createRigidArea(new Dimension(0, 15)));
        modal.add(lblValorAPagar);
        modal.add(Box.createRigidArea(new Dimension(0, 12)));
        modal.add(panelCampoMonto);
        modal.add(Box.createRigidArea(new Dimension(0, 12)));
        modal.add(lblCambio);

        while (true) {
            int opcion = javax.swing.JOptionPane.showConfirmDialog(this, modal, "Pago en Efectivo",
                    javax.swing.JOptionPane.OK_CANCEL_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE);

            if (opcion != javax.swing.JOptionPane.OK_OPTION) {
                return false;
            }

            try {
                double montoRecibido = Double.parseDouble(txtMontoRecibido.getText().trim());
                if (montoRecibido > MAXIMO_MONTO_EFECTIVO) {
                    javax.swing.JOptionPane.showMessageDialog(this,
                            "El monto ingresado es demasiado alto. Verifica el valor.",
                            "Monto no válido", javax.swing.JOptionPane.WARNING_MESSAGE);
                    continue;
                }
                if (montoRecibido < valorAPagar) {
                    javax.swing.JOptionPane.showMessageDialog(this,
                            "El monto recibido es menor al valor a pagar.",
                            "Monto insuficiente", javax.swing.JOptionPane.WARNING_MESSAGE);
                    continue;
                }
                return true;
            } catch (NumberFormatException ex) {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "Ingresa un monto válido.",
                        "Dato inválido", javax.swing.JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void mostrarModalTarjeta(String tituloModal) {
        JPanel modal = new JPanel();
        modal.setLayout(new BoxLayout(modal, BoxLayout.Y_AXIS));
        modal.setPreferredSize(new Dimension(360, 260));

        JTextField txtNumero = MetodosPublicos.crearCampoTexto();
        JTextField txtFecha = MetodosPublicos.crearCampoTexto();
        JTextField txtCodigo = MetodosPublicos.crearCampoTexto();

        modal.add(MetodosPublicos.crearSeparador(tituloModal));
        modal.add(Box.createRigidArea(new Dimension(0, 15)));
        modal.add(MetodosPublicos.crearCampoConEtiqueta("Número de tarjeta", txtNumero));
        modal.add(Box.createRigidArea(new Dimension(0, 12)));
        modal.add(MetodosPublicos.crearCampoConEtiqueta("Fecha de vencimiento", txtFecha));
        modal.add(Box.createRigidArea(new Dimension(0, 12)));
        modal.add(MetodosPublicos.crearCampoConEtiqueta("Código de seguridad", txtCodigo));

        int opcion = javax.swing.JOptionPane.showConfirmDialog(this, modal, tituloModal,
                javax.swing.JOptionPane.OK_CANCEL_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE);

        if (opcion == javax.swing.JOptionPane.OK_OPTION) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Pago con " + tituloModal.toLowerCase() + " confirmado",
                    "Éxito", javax.swing.JOptionPane.INFORMATION_MESSAGE);
        }
        
    }
    
    // Ventana emergente para digitar los datos de la transferencia bancaria.
    private void mostrarModalTransferencia() {
        JPanel modal = new JPanel();
        modal.setLayout(new BoxLayout(modal, BoxLayout.Y_AXIS));
        modal.setPreferredSize(new Dimension(360, 260));

        JComboBox cmbEntidad = new JComboBox(new String[]{"Bancolombia", "Davivienda", "BBVA"});
        JComboBox cmbTipoCuenta = new JComboBox(new String[]{"Corriente", "Ahorros"});
        MetodosPublicos.crearComboEstilizado(cmbEntidad);
        MetodosPublicos.crearComboEstilizado(cmbTipoCuenta);
        JTextField txtNumeroCuenta = MetodosPublicos.crearCampoTexto();

        modal.add(MetodosPublicos.crearSeparador("Transferencia"));
        modal.add(Box.createRigidArea(new Dimension(0, 15)));
        modal.add(MetodosPublicos.crearCampoConEtiqueta("Entidad bancaria", cmbEntidad));
        modal.add(Box.createRigidArea(new Dimension(0, 12)));
        modal.add(MetodosPublicos.crearCampoConEtiqueta("Tipo de cuenta", cmbTipoCuenta));
        modal.add(Box.createRigidArea(new Dimension(0, 12)));
        modal.add(MetodosPublicos.crearCampoConEtiqueta("Número de cuenta", txtNumeroCuenta));

        int opcion = javax.swing.JOptionPane.showConfirmDialog(this, modal, "Transferencia",
                javax.swing.JOptionPane.OK_CANCEL_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE);

        if (opcion == javax.swing.JOptionPane.OK_OPTION) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Transferencia confirmada",
                    "Éxito", javax.swing.JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    // -------------------------- MODULO CONSULTAS -----------------------------------
    
    public void mostrarVistaConsultas() {
        this.pacienteConsultasActual = null;
        mostrarVistaAsistencia();
    }
    
    private void mostrarVistaAsistencia() {
        construirVistaListaCitas(false);
    }

    private void mostrarVistaGestionCitas() {
        construirVistaListaCitas(true);
    }
    
    private void construirVistaListaCitas(boolean esGestionCitas) {
        vaciarPanel(cuerpo2);
        cuerpo2.setOpaque(false);
        cuerpo2.setLayout(new BorderLayout(0, 15));
        cuerpo2.setBorder(new EmptyBorder(20, 40, 20, 40));

        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));
        panelSuperior.setOpaque(false);
        
        JPanel panelSubMenu = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        panelSubMenu.setOpaque(false);
        JButton btnAsistenciaTab = new JButton("Citas actuales");
        JButton btnGestionTab = new JButton("Gestión de citas");
        estilizarBoton(btnAsistenciaTab, (byte) 2);
        estilizarBoton(btnGestionTab, (byte) 2);
        
        btnAsistenciaTab.setEnabled(esGestionCitas);
        btnGestionTab.setEnabled(!esGestionCitas);

        btnAsistenciaTab.addActionListener(e -> mostrarVistaAsistencia());
        btnGestionTab.addActionListener(e -> mostrarVistaGestionCitas());
        panelSubMenu.add(btnAsistenciaTab);
        panelSubMenu.add(btnGestionTab);

        JLabel lblTitulo = new JLabel("Citas");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 26));
        lblTitulo.setForeground(COLOR_AZUL_CORPORATIVO);
        lblTitulo.setBorder(new EmptyBorder(15, 0, 10, 0));
        
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.setOpaque(false);
        JLabel lblBuscar = new JLabel("ID del Paciente: ");
        lblBuscar.setFont(new Font("Arial", Font.BOLD, 16));
        JTextField txtBuscarIdConsultas = new JTextField(15);
        txtBuscarIdConsultas.setFont(new Font("Arial", Font.PLAIN, 16));
        JButton btnBuscarConsultas = new JButton("Buscar", new ImageIcon("iconsP/magnifying-glass.png"));
        estilizarBoton(btnBuscarConsultas, (byte) 5);
        panelBusqueda.add(lblBuscar);
        panelBusqueda.add(txtBuscarIdConsultas);
        panelBusqueda.add(btnBuscarConsultas);

        panelSuperior.add(panelSubMenu);
        panelSuperior.add(lblTitulo);
        panelSuperior.add(panelBusqueda);
        
        JPanel panelListaCitas = new JPanel();
        panelListaCitas.setLayout(new BoxLayout(panelListaCitas, BoxLayout.Y_AXIS));
        panelListaCitas.setOpaque(false);
        JScrollPane scrollListaCitas = new JScrollPane(panelListaCitas);
        scrollListaCitas.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollListaCitas.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollListaCitas.setOpaque(false);
        scrollListaCitas.getViewport().setOpaque(false);
        scrollListaCitas.setBorder(BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO));
        
        btnBuscarConsultas.addActionListener(e -> {
            String id = txtBuscarIdConsultas.getText().trim();
            if (id.isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "Por favor ingrese un ID de paciente.",
                        "Campo vacío", javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }
            Paciente paciente = controlador.buscarPacientePorId(id);
            if (!MetodosPublicos.validarNumero(id) || !MetodosPublicos.validarTamano(id, 8, 10)) {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "No se encontró paciente con ID: " + id,
                        "Paciente no encontrado", javax.swing.JOptionPane.ERROR_MESSAGE);
                return;
            }
            this.pacienteConsultasActual = paciente;
            cargarListaCitas(panelListaCitas, esGestionCitas);
        });

        cuerpo2.add(panelSuperior, BorderLayout.NORTH);
        cuerpo2.add(scrollListaCitas, BorderLayout.CENTER);

        refrescarVentana(cuerpo2);
        
        if (pacienteConsultasActual != null) {
            txtBuscarIdConsultas.setText(pacienteConsultasActual.getNumeroId());
            cargarListaCitas(panelListaCitas, esGestionCitas);
        }
        
    }
    
    private void cargarListaCitas(JPanel panelListaCitas, boolean esGestionCitas) {
        vaciarPanel(panelListaCitas);
        this.listaBotonesCancelarOpeario.clear();
        this.listaBotonesReagendarOpeario.clear();
        
        Cita[] citas = controlador.listarCitasPorUsuario(pacienteConsultasActual.getIdUsuario());
        
        if (citas.length == 0) {
            JLabel lblSinCitas = new JLabel("Este paciente no tiene citas activas.");
            lblSinCitas.setFont(new Font("Arial", Font.PLAIN, 16));
            lblSinCitas.setBorder(new EmptyBorder(20, 10, 20, 10));
            panelListaCitas.add(lblSinCitas);
        } else {
            for (Cita cita : citas) {
                panelListaCitas.add(construirTarjetaCita(cita, esGestionCitas));
            }
        }
        refrescarVentana(panelListaCitas);
    }
    
    private JPanel construirTarjetaCita(Cita cita, boolean esGestionCitas) {
        JPanel tarjeta = new JPanel(new BorderLayout());
        tarjeta.setOpaque(false);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY),
                new EmptyBorder(12, 10, 12, 10)));

        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.setOpaque(false);

        JLabel lblTituloCita = new JLabel("Cita " + cita.getEspecialidad());
        lblTituloCita.setFont(new Font("Arial", Font.BOLD, 17));
        lblTituloCita.setForeground(COLOR_AZUL_CORPORATIVO);

        JLabel lblFecha = new JLabel("Fecha: " + cita.getFechaCita());
        JLabel lblHora = new JLabel("Hora: " + cita.getHoraCita());
        JLabel lblMedico = new JLabel("Médico: " + cita.getNombreMedico());
        lblFecha.setFont(new Font("Arial", Font.PLAIN, 14));
        lblHora.setFont(new Font("Arial", Font.PLAIN, 14));
        lblMedico.setFont(new Font("Arial", Font.PLAIN, 14));
        lblFecha.setForeground(COLOR_GRIS_SUBTITULO);
        lblHora.setForeground(COLOR_GRIS_SUBTITULO);
        lblMedico.setForeground(COLOR_GRIS_SUBTITULO);

        panelInfo.add(lblTituloCita);
        panelInfo.add(lblFecha);
        panelInfo.add(lblHora);
        panelInfo.add(lblMedico);
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        panelBotones.setOpaque(false);

        if (esGestionCitas) {
            JButton btnCancelar = new JButton("Cancelar", new ImageIcon("iconsP/quejas.png"));
            JButton btnReagendar = new JButton("Reagendar", new ImageIcon("iconsP/reagendar.png"));
            estilizarBoton(btnCancelar, (byte) 6);
            estilizarBoton(btnReagendar, (byte) 7);
            btnCancelar.setPreferredSize(new Dimension(130, 40));
            btnReagendar.setPreferredSize(new Dimension(130, 40));

            btnCancelar.addActionListener(e -> cancelarCita(cita));
            btnReagendar.addActionListener(e -> mostrarVistaReagendarCita(cita));
            panelBotones.add(btnCancelar);
            panelBotones.add(btnReagendar);
            
            this.listaBotonesCancelarOpeario.add(btnCancelar);
            this.listaBotonesReagendarOpeario.add(btnReagendar);
            
        } else {
            JButton btnPacienteEnEspera = new JButton("Paciente en Espera", new ImageIcon("iconsP/bell.png"));
            estilizarBoton(btnPacienteEnEspera, (byte) 5);
            btnPacienteEnEspera.addActionListener(e -> {
                // Pendiente: aquí se agregará la lógica específica de este botón.
            });
            panelBotones.add(btnPacienteEnEspera);
        }

        tarjeta.add(panelInfo, BorderLayout.WEST);
        tarjeta.add(panelBotones, BorderLayout.EAST);
        return tarjeta;
    }
    
    
    private void cancelarCita(Cita cita) {
        int confirmacion = javax.swing.JOptionPane.showConfirmDialog(this,
                "¿Desea cancelar esta cita?",
                "Confirmar cancelación",
                javax.swing.JOptionPane.YES_NO_OPTION,
                javax.swing.JOptionPane.WARNING_MESSAGE);

        if (confirmacion != javax.swing.JOptionPane.YES_OPTION) {
            return;
        }

        boolean cancelada = controlador.cancelarCita(cita.getIdCita());

        if (cancelada) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "La cita fue cancelada con éxito.",
                    "Cita cancelada",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
            mostrarVistaGestionCitas();
        } else {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "No se pudo cancelar la cita. Verifica que XAMPP (MySQL) esté activo.",
                    "Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void mostrarVistaReagendarCita(Cita citaAReagendar) {
        vaciarPanel(cuerpo2);
        cuerpo2.setOpaque(false);
        cuerpo2.setLayout(new GridBagLayout());
        cuerpo2.setBorder(new EmptyBorder(40, 40, 40, 40));

        JPanel panelReagendar = new JPanel();
        panelReagendar.setLayout(new BoxLayout(panelReagendar, BoxLayout.Y_AXIS));
        panelReagendar.setOpaque(false);
        panelReagendar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO),
                BorderFactory.createEmptyBorder(40, 60, 40, 60)));

        JLabel lblTitulo = new JLabel("Reagendar Cita");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(COLOR_AZUL_CORPORATIVO);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblActual = new JLabel("Cita actual: " + citaAReagendar.getEspecialidad()
                + " - " + citaAReagendar.getFechaCita() + " " + citaAReagendar.getHoraCita());
        lblActual.setFont(new Font("Arial", Font.PLAIN, 14));
        lblActual.setForeground(COLOR_GRIS_SUBTITULO);
        lblActual.setAlignmentX(Component.CENTER_ALIGNMENT);

        DatePickerSettings ajustesFecha = new DatePickerSettings();
        DatePicker selectorNuevaFecha = new DatePicker(ajustesFecha);
        ajustesFecha.setVetoPolicy((LocalDate fecha) -> !fecha.isBefore(LocalDate.now()));
        selectorNuevaFecha.setAlignmentX(Component.CENTER_ALIGNMENT);

        JComboBox cmbNuevaHora = new JComboBox();
        estilizarComboBox(cmbNuevaHora);

        
        selectorNuevaFecha.addDateChangeListener(evento -> {
            LocalDate fechaElegida = evento.getNewDate();
            cmbNuevaHora.removeAllItems();
            if (fechaElegida == null) {
                return;
            }
            List<LocalTime> disponibles = controlador.calcularHorasDisponiblesReagendar(
                    citaAReagendar.getIdMedico(), fechaElegida);
            if (disponibles == null) {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "El médico no atiende este día.",
                        "Sin disponibilidad", javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (disponibles.isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "No hay horas disponibles para este día.",
                        "Sin disponibilidad", javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }
            for (LocalTime hora : disponibles) {
                cmbNuevaHora.addItem(hora.toString());
            }
        });

        JButton btnConfirmar = new JButton("Confirmar Reagendamiento");
        estilizarBoton(btnConfirmar, (byte) 5);
        btnConfirmar.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnConfirmar.addActionListener(e -> {
            LocalDate nuevaFecha = selectorNuevaFecha.getDate();
            if (nuevaFecha == null) {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "Selecciona la nueva fecha de la cita.",
                        "Campo vacío", javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }
            String horaSeleccionada = (String) cmbNuevaHora.getSelectedItem();
            if (horaSeleccionada == null) {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "Selecciona una hora disponible para la nueva fecha.",
                        "Campo vacío", javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }
            LocalTime nuevaHora = LocalTime.parse(horaSeleccionada);
            
            int idMedico = citaAReagendar.getIdMedico();
            
            int resultado = controlador.reagendarCita(citaAReagendar.getIdCita(), idMedico, nuevaFecha, nuevaHora);

            if (resultado == CitaDao.CONFLICTO_HORARIO) {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "El médico ya tiene otra cita en esa fecha y hora. Elige otro horario.",
                        "Horario no disponible", javax.swing.JOptionPane.ERROR_MESSAGE);
            } else if (resultado > 0) {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "La cita fue reagendada con éxito.",
                        "Éxito", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                mostrarVistaGestionCitas();
            } else {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "No se pudo reagendar la cita. Verifica que XAMPP (MySQL) esté activo.",
                        "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        });

        panelReagendar.add(lblTitulo);
        panelReagendar.add(Box.createRigidArea(new Dimension(0, 8)));
        panelReagendar.add(lblActual);
        panelReagendar.add(Box.createRigidArea(new Dimension(0, 20)));
        panelReagendar.add(selectorNuevaFecha);
        panelReagendar.add(Box.createRigidArea(new Dimension(0, 15)));
        panelReagendar.add(MetodosPublicos.crearCampoConEtiqueta("Hora:", cmbNuevaHora));
        panelReagendar.add(Box.createRigidArea(new Dimension(0, 15)));
        panelReagendar.add(btnConfirmar);

        cuerpo2.add(panelReagendar, new GridBagConstraints());
        refrescarVentana(cuerpo2);
    }
    
    @Override
    public void habilitarBotonesMenu(JButton botonActivo) {
        super.habilitarBotonesMenu(botonActivo);
        if (botonActivo != btnAgendarCitas && !btnAgendarCitas.isEnabled()) {
            this.btnAgendarCitas.setEnabled(true);
        }
        if (botonActivo != btnPagos && !btnPagos.isEnabled()) {
            this.btnPagos.setEnabled(true);
        }
        if (botonActivo != btnConsultas && !btnConsultas.isEnabled()) {
            this.btnConsultas.setEnabled(true);
        }
    }
}