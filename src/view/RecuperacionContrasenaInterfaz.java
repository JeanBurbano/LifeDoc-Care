package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import model.MetodosPublicos;

import static view.PacienteInterfaz.COLOR_AZUL_CORPORATIVO;
import static view.PacienteInterfaz.COLOR_VERDE_ACENTO;

public class RecuperacionContrasenaInterfaz extends JFrame {

    private JPanel panelSuperior, panelContenido;
    private JLabel fondo;
    public JTextField field,contrasena;
    public JPasswordField contrasenaVeri;
    private JButton flecha, btnCorreo, btnSms, btnContinuar;

    public RecuperacionContrasenaInterfaz() {
        super("Recuperacion Contrasena");//Nombre de la ventana
        this.fondo = new JLabel(new ImageIcon("fondo2.png"));
        this.fondo.setOpaque(true);
        this.fondo.setLayout(new GridBagLayout());
        this.setContentPane(fondo);

        this.panelSuperior = new JPanel();
        this.panelSuperior.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.panelSuperior.setOpaque(false);
        this.panelSuperior.setPreferredSize(new Dimension(50, 90));

        this.flecha = new JButton("←");
        JLabel titulo1 = new JLabel("Cambiar Contrasena");
        titulo1.setFont(new Font("arial", Font.BOLD, 25));

        Titulo titulo2 = new Titulo("LifeDoc", "Care");

        this.panelSuperior.add(flecha);
        this.panelSuperior.add(titulo1);
        this.panelSuperior.add(titulo2.getPanelTitulo());
        titulo2 = null;
        titulo1 = null;

        this.panelContenido = new JPanel();
        this.panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        this.panelContenido.setBackground(Color.WHITE);
        this.panelContenido.setPreferredSize(new Dimension(600, 400));
        this.panelContenido.setBorder(BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO));

        GridBagConstraints gbcSuperior = new GridBagConstraints();
        gbcSuperior.gridx = 0;
        gbcSuperior.gridy = 0;
        gbcSuperior.anchor = GridBagConstraints.NORTHWEST; // arriba izquierda
        gbcSuperior.weightx = 1.0; // que ocupe todo el ancho disponible
        gbcSuperior.weighty = 0;   // altura minima
        gbcSuperior.fill = GridBagConstraints.HORIZONTAL; // se estira horizontalmente
        gbcSuperior.insets = new Insets(10, 10, 0, 0); // margen arriba e izquierda
        this.fondo.add(panelSuperior, gbcSuperior);
        GridBagConstraints gbcContenido = new GridBagConstraints();
        gbcContenido.gridx = 0;
        gbcContenido.gridy = 1;
        gbcContenido.anchor = GridBagConstraints.CENTER; // centrado
        gbcContenido.weightx = 1.0;
        gbcContenido.weighty = 1.0; // que ocupe el resto del espacio vertical
        this.fondo.add(panelContenido, gbcContenido);
        this.btnCorreo = new JButton(" 📧 Correo electronico");
        this.btnSms = new JButton(" 💬 SMS");
        this.btnContinuar = new JButton("Continuar");
        MetodosPublicos.estilizarBoton(btnCorreo, (byte) 4);
        MetodosPublicos.estilizarBoton(btnSms, (byte) 4);
        MetodosPublicos.estilizarBoton(btnContinuar, (byte) 5);
        this.field = new JTextField();
        this.field.setBorder(BorderFactory.createLineBorder(COLOR_VERDE_ACENTO));
        this.contrasena = new JTextField();
        this.contrasena.setBorder(BorderFactory.createLineBorder(COLOR_VERDE_ACENTO));
        this.contrasenaVeri = new JPasswordField();
        this.contrasenaVeri.setBorder(BorderFactory.createLineBorder(COLOR_VERDE_ACENTO));
    }

    public void vistaMetodoDerecuperacion() {
        MetodosPublicos.vaciarPanel(panelContenido);

        JLabel titulo = new JLabel("Recuperar Contraseña");
        titulo.setFont(new Font("arial", Font.BOLD, 30));
        titulo.setForeground(COLOR_AZUL_CORPORATIVO);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);//Centrado horizontal en BoxLayout

        JLabel descripcion1 = new JLabel("Selecciona el metodo que deseas para");
        descripcion1.setFont(new Font("arial", Font.BOLD, 15));
        descripcion1.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel descripcion2 = new JLabel("realizar la recuperacion de contrasena");
        descripcion2.setFont(new Font("arial", Font.BOLD, 15));
        descripcion2.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnCorreo.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSms.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.panelContenido.add(Box.createVerticalGlue());//Empuja todo el contenido hacia el centro vertical
        this.panelContenido.add(titulo);
        this.panelContenido.add(Box.createRigidArea(new Dimension(0, 10)));//Espacio entre titulo y descripcion
        this.panelContenido.add(descripcion1);
        this.panelContenido.add(descripcion2);
        this.panelContenido.add(Box.createRigidArea(new Dimension(0, 30)));//Espacio entre descripcion y botones
        this.panelContenido.add(btnCorreo);
        this.panelContenido.add(Box.createRigidArea(new Dimension(0, 15)));//Espacio entre los dos botones
        this.panelContenido.add(btnSms);
        this.panelContenido.add(Box.createVerticalGlue());//Empuja desde abajo para que quede centrado

        titulo = null;
        descripcion1 = null;
        descripcion2 = null;

        MetodosPublicos.refrescarVentana(panelContenido);
    }

    public void vistaRecuperacion(String cadena) {
        MetodosPublicos.vaciarPanel(panelContenido);

        JLabel titulo = new JLabel("Recuperacion por " + cadena);
        titulo.setFont(new Font("arial", Font.BOLD, 30));
        titulo.setForeground(COLOR_AZUL_CORPORATIVO);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);//Centrado horizontal en BoxLayout

        JLabel subtitulo = new JLabel("Por favor ingrese un " + cadena + " activo");
        subtitulo.setFont(new Font("arial", Font.BOLD, 15));
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);//Centrado horizontal en BoxLayout

        JLabel indicador = new JLabel(cadena);
        indicador.setFont(new Font("arial", Font.BOLD, 10));

        this.panelContenido.add(Box.createVerticalGlue());
        this.panelContenido.add(titulo);
        this.panelContenido.add(Box.createRigidArea(new Dimension(0, 10)));
        this.panelContenido.add(subtitulo);
        this.panelContenido.add(Box.createRigidArea(new Dimension(0, 10)));
        this.panelContenido.add(indicador);
        this.panelContenido.add(field);
        this.panelContenido.add(btnContinuar);
        MetodosPublicos.refrescarVentana(panelContenido);
    }

    public void codigoAutenticacion(String cadena1, String cadena2) {
        MetodosPublicos.vaciarPanel(panelContenido);
        JLabel titulo = new JLabel("Codigo de autenticacion");
        titulo.setFont(new Font("arial", Font.BOLD, 30));
        titulo.setForeground(COLOR_AZUL_CORPORATIVO);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);//Centrado horizontal en BoxLayout

        JTextArea areaTexto = new JTextArea("Hemos enviado un codigo al " + cadena1
                + " " + cadena2 + "\nPara verificar su identidad. Por favor digite el codigo\n"
                + "Para seguir con el procedimiento");
        areaTexto.setFont(new Font("arial", Font.BOLD, 20));
        areaTexto.setEditable(false);       // No editable por el usuario
        areaTexto.setCursor(null);         // Oculta el cursor de texto
        areaTexto.setOpaque(false);        // Fondo transparente
        areaTexto.setFocusable(false);     // No toma el foco
        areaTexto.setLineWrap(true);       // Ajusta el texto al borde
        areaTexto.setWrapStyleWord(true);

        this.panelContenido.add(Box.createVerticalGlue());
        this.panelContenido.add(titulo);
        this.panelContenido.add(Box.createRigidArea(new Dimension(0, 10)));
        this.panelContenido.add(areaTexto);
        this.panelContenido.add(field);
        this.panelContenido.add(Box.createRigidArea(new Dimension(0, 10)));
        this.panelContenido.add(btnContinuar);
        MetodosPublicos.refrescarVentana(panelContenido);
    }

    public void vistaCambiarContrasena() {
        MetodosPublicos.vaciarPanel(panelContenido);
        JLabel titulo = new JLabel("Nueva Contrasena"),
                subtitulo = new JLabel("Por favor ingresa la nueva contrasena "),
                indicador1 = new JLabel("Contrasena"),
                indicador2 = new JLabel("Confirmar Contrasena");
        titulo.setFont(new Font("arial", Font.BOLD, 30));
        titulo.setForeground(COLOR_AZUL_CORPORATIVO);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);//Centrado horizontal en BoxLayout
        subtitulo.setFont(new Font("arial", Font.BOLD, 15));
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);//Centrado horizontal en BoxLayout
        indicador1.setFont(new Font("arial", Font.BOLD, 10));
        indicador2.setFont(new Font("arial", Font.BOLD, 10));
        this.panelContenido.add(Box.createVerticalGlue());
        this.panelContenido.add(titulo);
        this.panelContenido.add(Box.createRigidArea(new Dimension(0, 10)));
        this.panelContenido.add(subtitulo);
        this.panelContenido.add(Box.createRigidArea(new Dimension(0, 10)));
        this.panelContenido.add(indicador1);
        this.panelContenido.add(contrasena);
        this.panelContenido.add(indicador2);
        this.panelContenido.add(contrasenaVeri);
        this.panelContenido.add(btnContinuar);
        MetodosPublicos.refrescarVentana(panelContenido);
    }
}
