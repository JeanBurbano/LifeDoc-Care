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
    public JTextField field, contrasena;
    public JPasswordField contrasenaVeri;
    public JButton flecha, btnCorreo, btnSms, btnContinuar;

    public RecuperacionContrasenaInterfaz() {
        super("Recuperacion Contrasena");
        this.fondo = new JLabel(new ImageIcon("fondo2.png"));
        this.fondo.setOpaque(true);
        this.fondo.setLayout(new GridBagLayout());
        this.setContentPane(fondo);

        this.panelSuperior = new JPanel();
        this.panelSuperior.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.panelSuperior.setOpaque(false);
        this.panelSuperior.setPreferredSize(new Dimension(50, 90));

        this.flecha = new JButton("Volver",new ImageIcon("iconsP/arrow-left.png"));
        MetodosPublicos.estilizarBoton(flecha, (byte) 1);
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
        this.btnCorreo = new JButton("Correo electronico ", new ImageIcon("iconsP/gmail.png"));
        this.btnSms = new JButton("SMS ", new ImageIcon("iconsP/chatting.png"));
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

        JLabel titulo = new JLabel("Recuperacion por " + cadena),
                subtitulo = new JLabel("Por favor ingrese un " + cadena + " activo"),
                indicador = new JLabel(cadena);
        titulo.setFont(new Font("arial", Font.BOLD, 28));
        titulo.setForeground(COLOR_AZUL_CORPORATIVO);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        subtitulo.setFont(new Font("arial", Font.BOLD, 14));
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        indicador.setFont(new Font("arial", Font.BOLD, 12));
        indicador.setAlignmentX(Component.RIGHT_ALIGNMENT);//Alineado a la izquierda como un label de campo
        JPanel filaIndicador = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        filaIndicador.setOpaque(false);//Transparente para no tapar el fondo blanco
        filaIndicador.setMaximumSize(new Dimension(400, 20));//Mismo ancho que el campo
        filaIndicador.add(indicador);
        field.setAlignmentX(Component.CENTER_ALIGNMENT);
        field.setMaximumSize(new Dimension(400, 35));//Ancho fijo, sin estirarse
        field.setPreferredSize(new Dimension(400, 35));

        btnContinuar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnContinuar.setMaximumSize(new Dimension(200, 40));

        this.panelContenido.add(Box.createVerticalGlue());
        this.panelContenido.add(titulo);
        this.panelContenido.add(Box.createRigidArea(new Dimension(0, 8)));
        this.panelContenido.add(subtitulo);
        this.panelContenido.add(Box.createRigidArea(new Dimension(0, 20)));
        this.panelContenido.add(filaIndicador);
        this.panelContenido.add(Box.createRigidArea(new Dimension(0, 5)));
        this.panelContenido.add(field);
        this.panelContenido.add(Box.createRigidArea(new Dimension(0, 20)));
        this.panelContenido.add(btnContinuar);
        this.panelContenido.add(Box.createVerticalGlue());

        titulo = null;
        subtitulo = null;
        indicador = null;
        filaIndicador = null;
        MetodosPublicos.refrescarVentana(panelContenido);
    }

    public void codigoAutenticacion(String cadena1, String cadena2) {
        MetodosPublicos.vaciarPanel(panelContenido);

        JLabel titulo = new JLabel("Codigo de autenticacion");
        titulo.setFont(new Font("arial", Font.BOLD, 28));
        titulo.setForeground(COLOR_AZUL_CORPORATIVO);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextArea areaTexto = new JTextArea("Hemos enviado un codigo al " + cadena1
                + " " + cadena2 + "\nPara verificar su identidad. Por favor digite el codigo\n"
                + "para seguir con el procedimiento");
        areaTexto.setFont(new Font("arial", Font.PLAIN, 14));
        areaTexto.setEditable(false);
        areaTexto.setCursor(null);
        areaTexto.setOpaque(false);
        areaTexto.setFocusable(false);
        areaTexto.setLineWrap(true);
        areaTexto.setWrapStyleWord(true);
        areaTexto.setAlignmentX(Component.CENTER_ALIGNMENT);
        areaTexto.setMaximumSize(new Dimension(450, 80));//Ancho fijo para que no se estire
        areaTexto.setPreferredSize(new Dimension(450, 80));

        field.setAlignmentX(Component.CENTER_ALIGNMENT);
        field.setMaximumSize(new Dimension(400, 35));
        field.setPreferredSize(new Dimension(400, 35));

        btnContinuar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnContinuar.setMaximumSize(new Dimension(200, 40));

        this.panelContenido.add(Box.createVerticalGlue());
        this.panelContenido.add(titulo);
        this.panelContenido.add(Box.createRigidArea(new Dimension(0, 15)));
        this.panelContenido.add(areaTexto);
        this.panelContenido.add(Box.createRigidArea(new Dimension(0, 20)));
        this.panelContenido.add(field);
        this.panelContenido.add(Box.createRigidArea(new Dimension(0, 20)));
        this.panelContenido.add(btnContinuar);
        this.panelContenido.add(Box.createVerticalGlue());

        titulo = null;
        areaTexto = null;
        MetodosPublicos.refrescarVentana(panelContenido);
    }

    public void vistaCambiarContrasena() {
        MetodosPublicos.vaciarPanel(panelContenido);

        JLabel titulo = new JLabel("Nueva Contrasena"),
                subtitulo = new JLabel("Por favor ingresa la nueva contrasena"),
                indicador1 = new JLabel("Contrasena"),
                indicador2 = new JLabel("Confirmar Contrasena");

        titulo.setFont(new Font("arial", Font.BOLD, 28));
        titulo.setForeground(COLOR_AZUL_CORPORATIVO);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        subtitulo.setFont(new Font("arial", Font.BOLD, 14));
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        indicador1.setFont(new Font("arial", Font.BOLD, 12));
        indicador1.setAlignmentX(Component.RIGHT_ALIGNMENT);
        JPanel filaIndicador1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        filaIndicador1.setOpaque(false);//Transparente para no tapar el fondo blanco
        filaIndicador1.setMaximumSize(new Dimension(400, 20));//Mismo ancho que el campo
        filaIndicador1.add(indicador1);

        indicador2.setFont(new Font("arial", Font.BOLD, 12));
        indicador2.setAlignmentX(Component.RIGHT_ALIGNMENT);
        JPanel filaIndicador2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        filaIndicador2.setOpaque(false);//Transparente para no tapar el fondo blanco
        filaIndicador2.setMaximumSize(new Dimension(400, 20));//Mismo ancho que el campo
        filaIndicador2.add(indicador2);
        contrasena.setAlignmentX(Component.CENTER_ALIGNMENT);
        contrasena.setMaximumSize(new Dimension(400, 35));//Ancho fijo para que no se estire
        contrasena.setPreferredSize(new Dimension(400, 35));

        contrasenaVeri.setAlignmentX(Component.CENTER_ALIGNMENT);
        contrasenaVeri.setMaximumSize(new Dimension(400, 35));
        contrasenaVeri.setPreferredSize(new Dimension(400, 35));

        btnContinuar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnContinuar.setMaximumSize(new Dimension(200, 40));

        this.panelContenido.add(Box.createVerticalGlue());
        this.panelContenido.add(titulo);
        this.panelContenido.add(Box.createRigidArea(new Dimension(0, 8)));
        this.panelContenido.add(subtitulo);
        this.panelContenido.add(Box.createRigidArea(new Dimension(0, 25)));
        this.panelContenido.add(filaIndicador1);
        this.panelContenido.add(Box.createRigidArea(new Dimension(0, 5)));
        this.panelContenido.add(contrasena);
        this.panelContenido.add(Box.createRigidArea(new Dimension(0, 15)));
        this.panelContenido.add(filaIndicador2);
        this.panelContenido.add(Box.createRigidArea(new Dimension(0, 5)));
        this.panelContenido.add(contrasenaVeri);
        this.panelContenido.add(Box.createRigidArea(new Dimension(0, 25)));
        this.panelContenido.add(btnContinuar);
        this.panelContenido.add(Box.createVerticalGlue());

        titulo = null;
        subtitulo = null;
        indicador1 = null;
        indicador2 = null;
        filaIndicador1 = null;
        filaIndicador2 = null;
        MetodosPublicos.refrescarVentana(panelContenido);
    }
}
