package view;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
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
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import model.CalculadorPago;
import model.DatosPagoCita;
import model.DatosPagoCitaDao;
import model.MetodosPublicos;
import static model.MetodosPublicos.estilizarBoton;
import static model.MetodosPublicos.refrescarVentana;
import static model.MetodosPublicos.vaciarPanel;
import model.Paciente;
import model.PacienteDao;

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
    public JButton btnAgendarCita;

    public ArrayList<JButton> listaBotonesCancelarOpeario;
    public ArrayList<JButton> listaBotonesReagendarOpeario;
    
    public ArrayList<JButton> listaBotonesNoasistio;
    public ArrayList<JButton> listaBotonesAsistio;

    public OperarioInterfaz(String nombreInterfaz, Paciente usuario) {
        super(nombreInterfaz, usuario);
        
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
        
        PacienteDao dao = new PacienteDao();
        Paciente paciente = dao.buscarPorId(id);
        
//        System.out.println(paciente.getPrimerNombre());
        
        if (paciente != null) {
            cargarDatosPaciente(
                paciente.getNumeroId(),
                paciente.getPrimerNombre(),
                paciente.getSegundoNombre(),
                paciente.getPrimerApellido(),
                paciente.getSegundoApellido(),
                paciente.getCorreoElectronico(),
                paciente.getNumeroTelefonico(),
                paciente.getFechaNacimiento() != null ? paciente.getFechaNacimiento().toString() : "",
                paciente.getSexoBiologico()
            );
            javax.swing.JOptionPane.showMessageDialog(this, 
                "Paciente encontrado y cargado correctamente.", 
                "Éxito", 
                javax.swing.JOptionPane.INFORMATION_MESSAGE);

        } else {
            javax.swing.JOptionPane.showMessageDialog(this, 
                "No se encontró paciente con ID: " + id, 
                "Paciente no encontrado", 
                javax.swing.JOptionPane.ERROR_MESSAGE);
            
        }
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
    // pueda revisarlos/editarlos antes de agendar la cita
    
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
                String metodoParaBD = mapearMetodoPagoABD(metodoSeleccionado);

                DatosPagoCitaDao datosPagoCitaDao = new DatosPagoCitaDao();
                String descripcion = "Consulta " + datos.getEspecialidad();
                int filasInsertadas = datosPagoCitaDao.crear(this.idCita, descripcion, this.valorNeto, metodoParaBD);

                if (filasInsertadas <= 0) {
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

        // === PANEL DERECHO: BOTONES DE PAGO ===
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
        
        panelConfirmacion.add(lblExito);
        panelConfirmacion.add(Box.createRigidArea(new Dimension(0, 20)));
        panelConfirmacion.add(lblMetodoUsado);
        panelConfirmacion.add(Box.createRigidArea(new Dimension(0, 30)));
//        panelConfirmacion.add(btnFinalizar);
 
        panelPrincipal.add(panelFactura, BorderLayout.WEST);
        panelPrincipal.add(panelConfirmacion, BorderLayout.CENTER);
 
        cuerpo2.add(panelPrincipal, BorderLayout.CENTER);
 
        refrescarVentana(cuerpo2);
        JButton btnDebito = new JButton("Tarjeta débito", new ImageIcon("iconsP/atm-card.png"));
        JButton btnCredito = new JButton("Tarjeta crédito", new ImageIcon("iconsP/atm-card.png"));
        JButton btnTransferencia = new JButton("Transferencia", new ImageIcon("iconsP/bank.png"));

        estilizarBoton(btnDebito, (byte) 4);
        estilizarBoton(btnCredito, (byte) 4);
        estilizarBoton(btnTransferencia, (byte) 4);

        btnDebito.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCredito.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnTransferencia.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        btnDebito.setPreferredSize(new Dimension(Integer.MAX_VALUE, 45));
        btnCredito.setPreferredSize(new Dimension(Integer.MAX_VALUE, 45));
        btnTransferencia.setPreferredSize(new Dimension(Integer.MAX_VALUE, 45));

        btnDebito.addActionListener(e -> mostrarModalTarjetaDebito());
        btnCredito.addActionListener(e -> mostrarModalTarjetaCredito());
        btnTransferencia.addActionListener(e -> mostrarModalTransferencia());
        
        JButton btnFinalizar = new JButton("Finalizar");
        estilizarBoton(btnFinalizar, (byte) 5);
        btnFinalizar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnFinalizar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        
        btnFinalizar.addActionListener(e -> {
            btnPagos.setEnabled(true);
            btnPagos.doClick();
            
        });

        

        panelPrincipal.add(panelFactura, BorderLayout.WEST);
//        panelPrincipal.add(panelOpciones, BorderLayout.CENTER);

        cuerpo2.add(panelPrincipal, BorderLayout.CENTER);

        refrescarVentana(cuerpo2);
    }

    // Modales (Ventanas emergentes)
    private void mostrarModalTarjetaDebito() {
        JPanel modal = new JPanel(new GridBagLayout());
        modal.setBorder(BorderFactory.createTitledBorder("Tarjeta débito"));
        modal.setPreferredSize(new Dimension(400, 300));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new java.awt.Insets(10, 10, 10, 10);

        JTextField txtNumero = new JTextField(20);
        JTextField txtFecha = new JTextField(10);
        JTextField txtCodigo = new JTextField(6);

        JButton btnAceptar = new JButton("Aceptar");
        estilizarBoton(btnAceptar, (byte) 5);

        btnAceptar.addActionListener(e -> {
            javax.swing.JOptionPane.showMessageDialog(this, "Pago con tarjeta débito confirmado", "Éxito", javax.swing.JOptionPane.INFORMATION_MESSAGE);
        });

        modal.add(new JLabel("Número de tarjeta"), gbc);
        gbc.gridx = 1;
        modal.add(txtNumero, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        modal.add(new JLabel("Fecha de vencimiento"), gbc);
        gbc.gridx = 1;
        modal.add(txtFecha, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        modal.add(new JLabel("Código de seguridad"), gbc);
        gbc.gridx = 1;
        modal.add(txtCodigo, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        modal.add(btnAceptar, gbc);

        javax.swing.JOptionPane.showOptionDialog(this, modal, "Tarjeta Débito",
                javax.swing.JOptionPane.DEFAULT_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);
    }

    private void mostrarModalTarjetaCredito() {
        mostrarModalTarjetaDebito();

    }

    private void mostrarModalTransferencia() {
        JPanel modal = new JPanel(new GridBagLayout());
        modal.setBorder(BorderFactory.createTitledBorder("Transferencia"));
        modal.setPreferredSize(new Dimension(400, 300));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new java.awt.Insets(10, 10, 10, 10);

        JComboBox cmbEntidad = new JComboBox(new String[]{"Bancolombia", "Davivienda", "BBVA"});
        JComboBox cmbTipoCuenta = new JComboBox(new String[]{"Corriente", "Ahorros"});
        JTextField txtNumeroCuenta = new JTextField(20);

        JButton btnAceptar = new JButton("Aceptar");
        estilizarBoton(btnAceptar, (byte) 5);

        btnAceptar.addActionListener(e -> {
            javax.swing.JOptionPane.showMessageDialog(this, "Transferencia confirmada", "Éxito", javax.swing.JOptionPane.INFORMATION_MESSAGE);
        });

        modal.add(new JLabel("Entidad Bancaria"), gbc);
        gbc.gridx = 1;
        modal.add(cmbEntidad, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        modal.add(new JLabel("Tipo de cuenta"), gbc);
        gbc.gridx = 1;
        modal.add(cmbTipoCuenta, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        modal.add(new JLabel("Número de cuenta"), gbc);
        gbc.gridx = 1;
        modal.add(txtNumeroCuenta, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        modal.add(btnAceptar, gbc);

        javax.swing.JOptionPane.showOptionDialog(this, modal, "Transferencia",
                javax.swing.JOptionPane.DEFAULT_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);
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
