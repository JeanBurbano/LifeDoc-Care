/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
import javax.swing.border.EmptyBorder;
import model.MetodosPublicos;

/**
 *
 * @author lunaa
 */
public class RegistroUsuariosInterfaz extends PacienteInterfaz {

    JPanel creacionPerfil; //panel del titulo
    JPanel volver; //panel del boton de volver
    JPanel identificacion, datos, fotoF; //panel de las tres secciones de registro
    JLabel tituloCreacion; //titulo de crear cuenta
    JLabel tloSeccion1, tloSeccion2, tloSeccion3; //titulos de las tres secciones
    JButton btnVolverA, btnRegistrarse; //botones de volver al login y de registrarse
    JPanel panelLifeDoc; //panel para el titulo lifedoccare
    JPanel camposIde; //panel para los campos de identificacion
    //campos de identificacion
    JTextField campoNumeroID;
    JComboBox campoTipoId;

    JPanel camposDatos, camposFoto; //contenedores internos de las secciones "Datos personales" y "Foto frontal"

    // Campos de "Datos personales"
    JTextField campoPrimerNombre, campoSegundoNombre, campoPrimerApellido, campoSegundoApellido;
    JComboBox comboSexo;
    DatePicker datePickerNacimiento;
    JTextField campoCorreo, campoTelefono;
    JPasswordField campoContraseña;
    // Campos de Foto frontal
    JLabel previsualizacionFoto;
    JButton btnSeleccionarFoto;
    

    public RegistroUsuariosInterfaz(String nombrePersona, String nombreInterfaz, String url) {
        super(nombrePersona, nombreInterfaz, url);
        MetodosPublicos.vaciarPanel(encabezado);
        MetodosPublicos.vaciarPanel(cuerpo1);
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

        //botones
        this.btnVolverA = new JButton("← Volver");
        btnVolverA.setForeground(PacienteInterfaz.COLOR_VERDE_ACENTO);
        btnVolverA.setFont(new Font("Arial", Font.BOLD, 18));
        btnVolverA.setContentAreaFilled(false);
        btnVolverA.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.btnRegistrarse = new JButton("Registrarme");
        MetodosPublicos.estilizarBoton(btnRegistrarse, (byte) 5);

        // encabezado
        creacionPerfil.setLayout(new BoxLayout(creacionPerfil, BoxLayout.Y_AXIS));

        this.tituloCreacion = new JLabel("Crear una Cuenta");
        this.tituloCreacion.setFont(new Font("arial", Font.PLAIN, 33));
        this.tituloCreacion.setForeground(Color.BLACK);
        this.tituloCreacion.setAlignmentX(Component.LEFT_ALIGNMENT);

        this.panelLifeDoc = new Titulo("LifeDoc", "Care").getPanelTitulo();
        this.panelLifeDoc.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.panelLifeDoc.setMaximumSize(panelLifeDoc.getPreferredSize());

        this.creacionPerfil.add(tituloCreacion);
        this.creacionPerfil.add(panelLifeDoc);

        this.volver.setLayout(new FlowLayout());
        this.volver.add(btnVolverA);
        this.encabezado.add(volver, BorderLayout.EAST);
        this.encabezado.add(creacionPerfil, BorderLayout.WEST);

        this.cuerpo1.setBorder(new EmptyBorder(0, 0, 30, 0));

        // seccion 1 de identificacion
        // titulo
        this.identificacion.setLayout(new BorderLayout(8, 0));
        this.identificacion.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.identificacion.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));

        this.tloSeccion1 = new JLabel("Identificación");
        this.tloSeccion1.setAlignmentX(Component.LEFT_ALIGNMENT);
        tloSeccion1.setFont(new Font("Arial", Font.BOLD, 24));
        tloSeccion1.setForeground(PacienteInterfaz.COLOR_GRIS_SUBTITULO);

        JSeparator sep = new JSeparator();
        sep.setForeground(PacienteInterfaz.COLOR_VERDE_ACENTO);

        this.identificacion.add(tloSeccion1, BorderLayout.WEST);
        this.identificacion.add(sep, BorderLayout.CENTER);

        this.cuerpo2.add(identificacion); 

        //campos
        camposIde = new JPanel(new GridBagLayout());
        camposIde.setOpaque(false);
        camposIde.setAlignmentX(Component.LEFT_ALIGNMENT);

        GridBagConstraints gb = new GridBagConstraints();
        gb.insets = new Insets(8, 8, 8, 8);
        gb.anchor = GridBagConstraints.WEST;
        gb.fill = GridBagConstraints.NONE;

        campoNumeroID = MetodosPublicos.crearCampoTexto();
        campoTipoId = new JComboBox();
        MetodosPublicos.crearComboEstilizado(campoTipoId);

        gb.gridx = 0;
        gb.gridy = 0;
        gb.gridwidth = 1;
        camposIde.add(MetodosPublicos.crearCampoConEtiqueta("Tipo de identificacion *", campoTipoId), gb);
        gb.gridx = 1;
        camposIde.add(MetodosPublicos.crearCampoConEtiqueta("Numero de Identificación *", campoNumeroID), gb);

        camposIde.setMaximumSize(camposIde.getPreferredSize());

        this.cuerpo2.add(camposIde); 

        // seccion 2 de datos personales 
        // Título
        this.datos.setLayout(new BorderLayout(8, 0));
        this.datos.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.datos.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));

        this.tloSeccion2 = new JLabel("Datos Personales");
        tloSeccion2.setFont(new Font("Arial", Font.BOLD, 24));
        tloSeccion2.setForeground(PacienteInterfaz.COLOR_GRIS_SUBTITULO);

        JSeparator sepDatos = new JSeparator();
        sepDatos.setForeground(PacienteInterfaz.COLOR_VERDE_ACENTO);

        this.datos.add(tloSeccion2, BorderLayout.WEST);
        this.datos.add(sepDatos, BorderLayout.CENTER);

        this.cuerpo2.add(datos); 
        // capos
        camposDatos = new JPanel(new GridBagLayout());
        camposDatos.setOpaque(false);
        camposDatos.setAlignmentX(Component.LEFT_ALIGNMENT);

        GridBagConstraints gbDatos = new GridBagConstraints();
        gbDatos.insets = new Insets(8, 8, 8, 8);
        gbDatos.anchor = GridBagConstraints.WEST;
        gbDatos.fill = GridBagConstraints.NONE;

        campoPrimerNombre = MetodosPublicos.crearCampoTexto();
        campoSegundoNombre = MetodosPublicos.crearCampoTexto();
        campoPrimerApellido = MetodosPublicos.crearCampoTexto();
        campoSegundoApellido = MetodosPublicos.crearCampoTexto();

        comboSexo = new JComboBox();
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

        // Fila 0 de los primeros 4 campos
        gbDatos.gridx = 0;
        gbDatos.gridy = 0;
        camposDatos.add(MetodosPublicos.crearCampoConEtiqueta("Primer nombre *", campoPrimerNombre), gbDatos);
        gbDatos.gridx = 1;
        camposDatos.add(MetodosPublicos.crearCampoConEtiqueta("Segundo nombre", campoSegundoNombre), gbDatos);
        gbDatos.gridx = 2;
        camposDatos.add(MetodosPublicos.crearCampoConEtiqueta("Primer apellido *", campoPrimerApellido), gbDatos);
        gbDatos.gridx = 3;
        camposDatos.add(MetodosPublicos.crearCampoConEtiqueta("Segundo apellido", campoSegundoApellido), gbDatos);

        // Fila 1 de los segundos 4 campos
        gbDatos.gridx = 0;
        gbDatos.gridy = 1;
        camposDatos.add(MetodosPublicos.crearCampoConEtiqueta("Sexo Biológico *", comboSexo), gbDatos);
        gbDatos.gridx = 1;
        camposDatos.add(MetodosPublicos.crearCampoConEtiqueta("Fecha de nacimiento *", datePickerNacimiento), gbDatos);
        gbDatos.gridx = 2;
        camposDatos.add(MetodosPublicos.crearCampoConEtiqueta("Correo electrónico *", campoCorreo), gbDatos);
        gbDatos.gridx = 3;
        camposDatos.add(MetodosPublicos.crearCampoConEtiqueta("Número de teléfono *", campoTelefono), gbDatos);

        // Fila 2: Contraseña
        gbDatos.gridx = 0;
        gbDatos.gridy = 2;
        camposDatos.add(MetodosPublicos.crearCampoConEtiqueta("Contraseña *", campoContraseña), gbDatos);

        camposDatos.setMaximumSize(camposDatos.getPreferredSize());

        this.cuerpo2.add(camposDatos);

        // seccion de foto frontal
        this.fotoF.setLayout(new BorderLayout(8, 0));
        this.fotoF.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.fotoF.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));

        this.tloSeccion3 = new JLabel("Foto Frontal");
        tloSeccion3.setFont(new Font("Arial", Font.BOLD, 24));
        tloSeccion3.setForeground(PacienteInterfaz.COLOR_GRIS_SUBTITULO);

        JSeparator sepFoto = new JSeparator();
        sepFoto.setForeground(PacienteInterfaz.COLOR_VERDE_ACENTO);

        this.fotoF.add(tloSeccion3, BorderLayout.WEST);
        this.fotoF.add(sepFoto, BorderLayout.CENTER);

        this.cuerpo2.add(fotoF);

        // Panel con la imagen + botón Registrarme
        JPanel filaFotoYBoton = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        filaFotoYBoton.setOpaque(false);
        filaFotoYBoton.setAlignmentX(Component.LEFT_ALIGNMENT);

        previsualizacionFoto = new JLabel();
        btnSeleccionarFoto = new JButton("Seleccionar Foto");
        MetodosPublicos.estilizarBoton(btnSeleccionarFoto, (byte) 5);

        JPanel panelFoto = MetodosPublicos.crearPanelImagen(btnSeleccionarFoto, previsualizacionFoto, "Foto Frontal");
        filaFotoYBoton.add(panelFoto);

        JPanel contenedorBotonRegistro = new JPanel();
        contenedorBotonRegistro.setOpaque(false);
        contenedorBotonRegistro.setLayout(new BoxLayout(contenedorBotonRegistro, BoxLayout.Y_AXIS));
        contenedorBotonRegistro.add(Box.createVerticalGlue());
        contenedorBotonRegistro.add(btnRegistrarse);
        contenedorBotonRegistro.add(Box.createVerticalGlue());
        filaFotoYBoton.add(contenedorBotonRegistro);

        this.cuerpo2.add(filaFotoYBoton);

        MetodosPublicos.refrescarVentana(cuerpo2);
        MetodosPublicos.refrescarVentana(cuerpo1);
    }

}
