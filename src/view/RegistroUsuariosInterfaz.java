package view;

import com.github.lgooddatepicker.components.DatePicker;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import model.MetodosPublicos;

public class RegistroUsuariosInterfaz extends PacienteInterfaz {

    final static String[] ARREGLO_ID = {"", "Registro civil", "Targeta Identidad", "Cedula Ciudadania",};
    final static String[] ARREGLO_SEXO = {"", "Femenino", "Masculino"};
    final static String[] ARREGLO_SISBEN = {"", "No aplica", "A", "B", "C", "D"};

    public JPanel creacionPerfil; //panel del titulo
    public JPanel volver; //panel del boton de volver
    public JPanel identificacion, datos, fotoF; //panel de las tres secciones de registro
    public JLabel tituloCreacion; //titulo de crear cuenta
    public JLabel tloSeccion1, tloSeccion2, tloSeccion3; //titulos de las tres secciones
    public JButton btnVolverA, btnRegistrarse; //botones de volver al login y de registrarse
    public JPanel panelLifeDoc; //panel para el titulo lifedoccare
    public JPanel camposIde; //panel para los campos de identificacion
    //campos de identificacion
    public JTextField campoNumeroID;
    public JComboBox campoTipoId;

    public JPanel camposDatos, camposFoto; //contenedores internos de las secciones "Datos personales" y "Foto frontal"

    // Campos de "Datos personales"
    public JTextField campoPrimerNombre, campoSegundoNombre, campoPrimerApellido, campoSegundoApellido;
    public JComboBox comboSexo;
    public DatePicker datePickerNacimiento;
    public JTextField campoCorreo, campoTelefono;
    public JPasswordField campoContraseña;
    public JComboBox campoSisben;
    // Campos de Foto frontal
    public JLabel previsualizacionFoto;
    public JButton btnSeleccionarFoto;

    public RegistroUsuariosInterfaz(String nombreInterfaz) {
        super(nombreInterfaz);

        this.encabezado.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.cuerpo1.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.cuerpo2.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.cuerpo2.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        this.cuerpo1.setBorder(BorderFactory.createEmptyBorder());
        this.cuerpo1.setLayout(new BoxLayout(cuerpo1, BoxLayout.Y_AXIS));
        this.cuerpo2.setLayout(new BoxLayout(cuerpo2, BoxLayout.Y_AXIS));

        this.cuerpo1.setBorder(BorderFactory.createEmptyBorder());
        this.cuerpo1.setLayout(new BoxLayout(cuerpo1, BoxLayout.Y_AXIS));
        this.cuerpo2.setLayout(new BoxLayout(cuerpo2, BoxLayout.Y_AXIS));

        this.creacionPerfil = new JPanel();
        this.volver = new JPanel();
        this.identificacion = new JPanel();
        this.datos = new JPanel();
        this.fotoF = new JPanel();
        creacionPerfil.setOpaque(false);
        volver.setOpaque(false);
        identificacion.setOpaque(false);
        datos.setOpaque(false);
        fotoF.setOpaque(false);

        //Botones
        this.btnVolverA = new JButton("← Volver");
        btnVolverA.setForeground(PacienteInterfaz.COLOR_VERDE_ACENTO);
        btnVolverA.setFont(new Font("Arial", Font.BOLD, 18));
        btnVolverA.setContentAreaFilled(false);
        btnVolverA.setBorderPainted(false);
        btnVolverA.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        this.btnRegistrarse = new JButton("Registrarme");
        MetodosPublicos.estilizarBoton(btnRegistrarse, (byte) 5);

        //Encabezado
        creacionPerfil.setLayout(new BoxLayout(creacionPerfil, BoxLayout.Y_AXIS));

        this.tituloCreacion = new JLabel("Crear una Cuenta");
        this.tituloCreacion.setFont(new Font("Arial", Font.PLAIN, 33));
        this.tituloCreacion.setForeground(Color.BLACK);
        this.tituloCreacion.setAlignmentX(Component.LEFT_ALIGNMENT);

        this.panelLifeDoc = new Titulo("LifeDoc", "Care").getPanelTitulo();
        this.panelLifeDoc.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.panelLifeDoc.setMaximumSize(panelLifeDoc.getPreferredSize());

        this.creacionPerfil.add(tituloCreacion);
        this.creacionPerfil.add(panelLifeDoc);

        this.volver.setLayout(new FlowLayout(FlowLayout.RIGHT));
        this.volver.add(btnVolverA);

        this.encabezado.add(volver, BorderLayout.EAST);
        this.encabezado.add(creacionPerfil, BorderLayout.WEST);

        //Sección 1: Identificación
        this.identificacion.setLayout(new BorderLayout(8, 0));
        this.identificacion.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.identificacion.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));

        this.tloSeccion1 = new JLabel("Identificación");
        tloSeccion1.setFont(new Font("Arial", Font.BOLD, 22));
        tloSeccion1.setForeground(PacienteInterfaz.COLOR_AZUL_CORPORATIVO);

        JSeparator sep1 = new JSeparator();
        sep1.setForeground(new Color(225, 225, 225));

        this.identificacion.add(tloSeccion1, BorderLayout.WEST);
        this.identificacion.add(sep1, BorderLayout.CENTER);

        this.cuerpo2.add(identificacion);
        this.cuerpo2.add(Box.createVerticalStrut(20));

        camposIde = new JPanel(new GridBagLayout());
        camposIde.setOpaque(false);
        camposIde.setAlignmentX(Component.LEFT_ALIGNMENT);

        GridBagConstraints gb = new GridBagConstraints();
        gb.insets = new Insets(0, 0, 0, 30); // separación horizontal entre campos, sin separación vertical
        gb.anchor = GridBagConstraints.WEST;
        gb.fill = GridBagConstraints.NONE;

        campoNumeroID = MetodosPublicos.crearCampoTexto();
        campoTipoId = new JComboBox(ARREGLO_ID);
        MetodosPublicos.crearComboEstilizado(campoTipoId);

        gb.gridx = 0;
        gb.gridy = 0;
        camposIde.add(MetodosPublicos.crearCampoConEtiqueta("Tipo de documento *", campoTipoId), gb);
        gb.gridx = 1;
        camposIde.add(MetodosPublicos.crearCampoConEtiqueta("Número de identificación *", campoNumeroID), gb);

        camposIde.setAlignmentX(Component.LEFT_ALIGNMENT);
        camposIde.setMaximumSize(camposIde.getPreferredSize());

        this.cuerpo2.add(camposIde);
        this.cuerpo2.add(Box.createVerticalStrut(30));

        //Sección 2: Datos personales
        this.datos.setLayout(new BorderLayout(8, 0));
        this.datos.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.datos.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));

        this.tloSeccion2 = new JLabel("Datos personales");
        tloSeccion2.setFont(new Font("Arial", Font.BOLD, 22));
        tloSeccion2.setForeground(PacienteInterfaz.COLOR_AZUL_CORPORATIVO);

        JSeparator sep2 = new JSeparator();
        sep2.setForeground(new Color(225, 225, 225));

        this.datos.add(tloSeccion2, BorderLayout.WEST);
        this.datos.add(sep2, BorderLayout.CENTER);

        this.cuerpo2.add(datos);
        this.cuerpo2.add(Box.createVerticalStrut(20));

        camposDatos = new JPanel(new GridBagLayout());
        camposDatos.setOpaque(false);
        camposDatos.setAlignmentX(Component.LEFT_ALIGNMENT);

        GridBagConstraints gbDatos = new GridBagConstraints();
        gbDatos.insets = new Insets(0, 0, 22, 30); //separación vertical entre filas + horizontal entre columnas
        gbDatos.anchor = GridBagConstraints.WEST;
        gbDatos.fill = GridBagConstraints.NONE;

        campoPrimerNombre = MetodosPublicos.crearCampoTexto();
        campoSegundoNombre = MetodosPublicos.crearCampoTexto();
        campoPrimerApellido = MetodosPublicos.crearCampoTexto();
        campoSegundoApellido = MetodosPublicos.crearCampoTexto();

        comboSexo = new JComboBox(ARREGLO_SEXO);
        MetodosPublicos.crearComboEstilizado(comboSexo);

        datePickerNacimiento = FechaCalendarioEstilizar.crearDatePickerNacimiento();
        datePickerNacimiento.getComponentDateTextField().setPreferredSize(new Dimension(187, 36));

        campoCorreo = MetodosPublicos.crearCampoTexto();
        campoTelefono = MetodosPublicos.crearCampoTexto();

        campoContraseña = new JPasswordField(15);
        campoContraseña.setFont(new Font("Arial", Font.PLAIN, 15));
        campoContraseña.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PacienteInterfaz.COLOR_VERDE_ACENTO, 1, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        
        campoSisben = new JComboBox(ARREGLO_SISBEN);
        MetodosPublicos.crearComboEstilizado(campoSisben);

        // Fila 0
        gbDatos.gridx = 0;
        gbDatos.gridy = 0;
        camposDatos.add(MetodosPublicos.crearCampoConEtiqueta("Primer nombre *", campoPrimerNombre), gbDatos);
        gbDatos.gridx = 1;
        camposDatos.add(MetodosPublicos.crearCampoConEtiqueta("Segundo nombre", campoSegundoNombre), gbDatos);
        gbDatos.gridx = 2;
        camposDatos.add(MetodosPublicos.crearCampoConEtiqueta("Primer apellido *", campoPrimerApellido), gbDatos);
        gbDatos.gridx = 3;
        gbDatos.insets = new Insets(0, 0, 22, 0); // última columna de la fila sin margen derecho
        camposDatos.add(MetodosPublicos.crearCampoConEtiqueta("Segundo apellido", campoSegundoApellido), gbDatos);

        // Fila 1
        gbDatos.insets = new Insets(0, 0, 22, 30);
        gbDatos.gridx = 0;
        gbDatos.gridy = 1;
        camposDatos.add(MetodosPublicos.crearCampoConEtiqueta("Sexo Biológico *", comboSexo), gbDatos);
        gbDatos.gridx = 1;
        camposDatos.add(MetodosPublicos.crearCampoConEtiqueta("Fecha de nacimiento *", datePickerNacimiento), gbDatos);
        gbDatos.gridx = 2;
        camposDatos.add(MetodosPublicos.crearCampoConEtiqueta("Correo electrónico *", campoCorreo), gbDatos);
        gbDatos.gridx = 3;
        gbDatos.insets = new Insets(0, 0, 22, 0);
        camposDatos.add(MetodosPublicos.crearCampoConEtiqueta("Número de teléfono ", campoTelefono), gbDatos);

        // Fila 2: Contraseña y Grupo Sisbén
        gbDatos.insets = new Insets(0, 0, 0, 30);
        gbDatos.gridx = 0;
        gbDatos.gridy = 2;
        camposDatos.add(MetodosPublicos.crearCampoConEtiqueta("Contraseña *", campoContraseña), gbDatos);
        gbDatos.gridx = 1;
        gbDatos.insets = new Insets(0, 0, 0, 0);
        camposDatos.add(MetodosPublicos.crearCampoConEtiqueta("Grupo Sisbén *", campoSisben), gbDatos);

        camposDatos.setAlignmentX(Component.LEFT_ALIGNMENT);
        camposDatos.setMaximumSize(camposDatos.getPreferredSize());

        this.cuerpo2.add(camposDatos);
        this.cuerpo2.add(Box.createVerticalStrut(30));

        //Sección 3: Foto frontal 
        this.fotoF.setLayout(new BorderLayout(8, 0));
        this.fotoF.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.fotoF.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));

        this.tloSeccion3 = new JLabel("Foto frontal *");
        tloSeccion3.setFont(new Font("Arial", Font.BOLD, 22));
        tloSeccion3.setForeground(PacienteInterfaz.COLOR_AZUL_CORPORATIVO);

        JSeparator sep3 = new JSeparator();
        sep3.setForeground(new Color(225, 225, 225));

        this.fotoF.add(tloSeccion3, BorderLayout.WEST);
        this.fotoF.add(sep3, BorderLayout.CENTER);

        this.cuerpo2.add(fotoF);
        this.cuerpo2.add(Box.createVerticalStrut(20));

        JPanel filaFoto = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 0));
        filaFoto.setOpaque(false);
        filaFoto.setAlignmentX(Component.LEFT_ALIGNMENT);

        previsualizacionFoto = new JLabel();
        btnSeleccionarFoto = new JButton();
        JPanel panelFoto = MetodosPublicos.crearPanelImagen(btnSeleccionarFoto, previsualizacionFoto, "Foto Frontal");

        JPanel columnaInfo = new JPanel();
        columnaInfo.setOpaque(false);
        columnaInfo.setLayout(new BoxLayout(columnaInfo, BoxLayout.Y_AXIS));

        JPanel panelInfo = MetodosPublicos.crearPanelInfo("Tener en cuenta",
                "La imagen que adjuntes debe ser PNG y pesar máximo 5 MB");
        panelInfo.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnRegistrarse.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        String reglasContraseña = "La contraseña debe cumplir con:\n" +
                                "• Al menos 1 Mayúscula\n" +
                                "• Al menos 1 Minúscula\n" +
                                "• Al menos 1 Número\n" +
                                "• Símbolos permitidos: @, #, $, %, &, *, -, _, !, ?.";
        JPanel panelInfoContraseña = MetodosPublicos.crearPanelInfo("Requisitos Contraseña", reglasContraseña);
        panelInfoContraseña.setAlignmentX(Component.LEFT_ALIGNMENT);
        

        columnaInfo.add(panelInfo);
        columnaInfo.add(Box.createVerticalStrut(15));
        columnaInfo.add(panelInfoContraseña); 
        columnaInfo.add(Box.createVerticalStrut(15));
        columnaInfo.add(btnRegistrarse);

        filaFoto.add(panelFoto);
        filaFoto.add(columnaInfo);

        this.cuerpo2.add(filaFoto);

        MetodosPublicos.refrescarVentana(cuerpo2);
        MetodosPublicos.refrescarVentana(cuerpo1);
    }
}
