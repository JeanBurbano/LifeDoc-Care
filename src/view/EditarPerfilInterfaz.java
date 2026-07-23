package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import model.MetodosPublicos;

import static view.PacienteInterfaz.COLOR_AZUL_CORPORATIVO;

public class EditarPerfilInterfaz extends JFrame {

    private static Color COLOR_VENTANA = new Color(255, 255, 255);

    private Container contenedor;
    public JButton volver, editar, guardar;
    private PanelRound fotoPerfil, cuerpo1;
    public JTextField fielNombre, fieldEdad, fieldCorreo, fieldTelefono, fieldSexo, fieldFechaN, fieldGrupoS;
    public JPanel contenedor2, cuerpo2, encabezado, panelBotones;

    public EditarPerfilInterfaz(String nombreInterfaz, String nombre, String edad, String correo,
            String telefono, String sexo, String fechan, String grupos, String ruta) {
        //nombre interfaz
        super(nombreInterfaz);
        //el contenedor del JFrame
        this.contenedor = getContentPane();
        //el contenedor que va dentro del otro contedor
        this.contenedor2 = new JPanel();
        //distribucion del contenedor
        this.contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));
        //distribucion del contenedor2
        this.contenedor2.setLayout(new BoxLayout(contenedor2, BoxLayout.Y_AXIS));
        //color de los contendores
        this.contenedor.setBackground(COLOR_VENTANA);
        this.contenedor2.setBackground(COLOR_VENTANA);

        this.encabezado = new JPanel();
        this.encabezado.setBorder(new EmptyBorder(40, 40, 0, 40));
        this.encabezado.setLayout(new BorderLayout());

        JPanel panelBienvenida = new JPanel();
        panelBienvenida.setLayout(new BorderLayout());
        panelBienvenida.setOpaque(false);
        panelBienvenida.add(new Titulo("LifeDoc", "Care", 50).getPanelTitulo(), BorderLayout.NORTH);
        panelBienvenida.add(new JLabel("Centro de salud - Nombre del usuario"), BorderLayout.WEST);

        JPanel panelBoton = new JPanel();
        panelBoton.setLayout(new FlowLayout());
        panelBoton.setOpaque(false);
        this.volver = new JButton("Volver", new ImageIcon("iconsP/arrow-left.png"));
        MetodosPublicos.estilizarBoton(volver, (byte) 1);
        this.volver.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panelBoton.add(volver);

        this.encabezado.add(panelBienvenida, BorderLayout.WEST);
        this.encabezado.add(panelBoton, BorderLayout.EAST);

        //foto perfil
        int valor = 310;
        int radio = 400;
        this.fotoPerfil = new PanelRound();
        this.fotoPerfil.setRoundBottomLeft(radio);
        this.fotoPerfil.setRoundBottomRight(radio);
        this.fotoPerfil.setRoundTopLeft(radio);
        this.fotoPerfil.setRoundTopRight(radio);
        this.fotoPerfil.setPreferredSize(new Dimension(valor, valor));
        this.fotoPerfil.setMaximumSize(new Dimension(valor, valor));
        this.fotoPerfil.setLayout(new BorderLayout());
        ImageIcon imagen = new ImageIcon(ruta);
        Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(valor,
                valor,
                Image.SCALE_DEFAULT));
        JLabel fotito = new JLabel(icono);
        fotito.setOpaque(false);
        fotito.setPreferredSize(new Dimension(valor, valor));
        fotito.setMaximumSize(new Dimension(valor, valor));
        this.fotoPerfil.add(fotito, BorderLayout.CENTER);

        this.encabezado.setBackground(COLOR_VENTANA);
        this.encabezado.setPreferredSize(new Dimension(40, 125));

        int borde = 50;
        this.cuerpo1 = new PanelRound();
        this.cuerpo1.setRoundTopLeft(borde);
        this.cuerpo1.setRoundTopRight(borde);

        this.cuerpo1.setBorder(BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO));
        this.cuerpo1.setBorder(new EmptyBorder(5, 30, 0, 0));

        this.cuerpo2 = new JPanel();
        this.cuerpo2.setBorder(BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO));
        this.cuerpo1.setBackground(COLOR_AZUL_CORPORATIVO);
        this.cuerpo1.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.cuerpo1.add(fotoPerfil);

        this.cuerpo2.setBackground(COLOR_VENTANA);
        this.cuerpo2.setLayout(new BorderLayout());

        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int anchoDisponible = pantalla.width - 80; // menos algo de margen

        this.cuerpo1.setPreferredSize(new Dimension(anchoDisponible, 326));
        this.cuerpo2.setPreferredSize(new Dimension(anchoDisponible, 326));

        this.editar = new JButton("Editar", new ImageIcon("iconsP/pen.png"));
        MetodosPublicos.estilizarBoton(editar, (byte) 1);
        JPanel panelConField = new JPanel();
        panelConField.setBackground(COLOR_VENTANA);
        panelConField.setLayout(new GridLayout(3, 3));
        panelConField.setBorder(new EmptyBorder(30, 0, 20, 0));
        this.panelBotones = new JPanel();
        this.panelBotones.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.panelBotones.setBorder(new EmptyBorder(10, 130, 0, 0));
        this.panelBotones.setBackground(COLOR_VENTANA);
        this.panelBotones.add(editar);
        this.fielNombre = MetodosPublicos.crearCampoTexto(COLOR_AZUL_CORPORATIVO);
        this.fieldEdad = MetodosPublicos.crearCampoTexto(COLOR_AZUL_CORPORATIVO);
        this.fieldCorreo = MetodosPublicos.crearCampoTexto(COLOR_AZUL_CORPORATIVO);
        this.fieldTelefono = MetodosPublicos.crearCampoTexto(COLOR_AZUL_CORPORATIVO);
        this.fieldSexo = MetodosPublicos.crearCampoTexto(COLOR_AZUL_CORPORATIVO);
        this.fieldFechaN = MetodosPublicos.crearCampoTexto(COLOR_AZUL_CORPORATIVO);
        this.fieldGrupoS = MetodosPublicos.crearCampoTexto(COLOR_AZUL_CORPORATIVO);
        boolean miEstadito = false;
        this.fielNombre.setEditable(miEstadito);
        this.fielNombre.setOpaque(miEstadito);
        this.fieldEdad.setEditable(miEstadito);
        this.fieldEdad.setOpaque(miEstadito);
        this.fieldCorreo.setEditable(miEstadito);
        this.fieldCorreo.setOpaque(miEstadito);
        this.fieldTelefono.setEditable(miEstadito);
        this.fieldTelefono.setOpaque(miEstadito);
        this.fieldSexo.setEditable(miEstadito);
        this.fieldSexo.setOpaque(miEstadito);
        this.fieldFechaN.setEditable(miEstadito);
        this.fieldFechaN.setOpaque(miEstadito);
        this.fieldGrupoS.setEditable(miEstadito);
        this.fieldGrupoS.setOpaque(miEstadito);

        this.fielNombre.setText(nombre);
        this.fieldEdad.setText(edad);
        this.fieldCorreo.setText(correo);
        this.fieldTelefono.setText(telefono);
        this.fieldSexo.setText(sexo);
        this.fieldFechaN.setText(fechan);
        this.fieldGrupoS.setText(grupos);

        LayoutManager miLayout = new GridLayout(2, 1);
        JPanel panel1 = new JPanel(), panel2 = new JPanel(), panel3 = new JPanel(),
                panel4 = new JPanel(), panel5 = new JPanel(), panel6 = new JPanel(),
                panel7 = new JPanel();
        panel1.setLayout(miLayout);
        panel1.setOpaque(miEstadito);
        panel2.setLayout(miLayout);
        panel2.setOpaque(miEstadito);
        panel3.setLayout(miLayout);
        panel3.setOpaque(miEstadito);
        panel4.setLayout(miLayout);
        panel4.setOpaque(miEstadito);
        panel5.setLayout(miLayout);
        panel5.setOpaque(miEstadito);
        panel6.setLayout(miLayout);
        panel6.setOpaque(miEstadito);
        panel7.setLayout(miLayout);
        panel7.setOpaque(miEstadito);

        EmptyBorder miborde = new EmptyBorder(0, 5, 0, 5);
        panel1.setBorder(miborde);
        panel2.setBorder(miborde);
        panel3.setBorder(miborde);
        panel4.setBorder(miborde);
        panel5.setBorder(miborde);
        panel6.setBorder(miborde);
        panel7.setBorder(miborde);

        panel1.add(new JLabel("Nombre *"));
        panel1.add(fielNombre);
        panel2.add(new JLabel("Edad "));
        panel2.add(fieldEdad);
        panel3.add(new JLabel("Correo *"));
        panel3.add(fieldCorreo);
        panel4.add(new JLabel("Telefono "));
        panel4.add(fieldTelefono);
        panel5.add(new JLabel("Sexo Biologico"));
        panel5.add(fieldSexo);
        panel6.add(new JLabel("Fecha Nacimiento "));
        panel6.add(fieldFechaN);
        panel7.add(new JLabel("Puntaje Sisben"));
        panel7.add(fieldGrupoS);

        JPanel panelconboton = new JPanel(new BorderLayout());
        panelconboton.setOpaque(miEstadito);
        panelconboton.setBorder(new EmptyBorder(30, 0, 0, 0));
        this.guardar = new JButton("Guardar", new ImageIcon("iconsP/save.png"));
        this.guardar.setEnabled(miEstadito);
        panelconboton.add(guardar, BorderLayout.EAST);
        MetodosPublicos.estilizarBoton(guardar, (byte) 5);
        panelConField.add(panel1);
        panelConField.add(panel2);
        panelConField.add(panel3);
        panelConField.add(panel4);
        panelConField.add(panel5);
        panelConField.add(panel6);
        panelConField.add(panel7);
        panelConField.add(panelconboton);

        this.cuerpo2.add(panelBotones, BorderLayout.NORTH);
        this.cuerpo2.add(panelConField, BorderLayout.SOUTH);

        this.contenedor2.setBorder(new EmptyBorder(10, 40, 40, 40));
        this.contenedor2.add(encabezado);
        this.contenedor2.add(Box.createVerticalStrut(10));
        this.contenedor2.add(cuerpo1);
        this.contenedor2.add(cuerpo2);
        this.contenedor.add(contenedor2);
    }
}
