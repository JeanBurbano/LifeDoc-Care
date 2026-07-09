package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Login extends JFrame {

    private JTextField id;
    private JPasswordField password;
    private ImageIcon imagen1, imagen2;
    private JLabel fondo, titulo1, titulo3, titulo4, descripcion, emoji1, emoji2, correofield, passwordfield;
    public JLabel titulo2;
    public JButton bRegistar, bIngresar;
    private JPanel panel1, panel2, panelbotones, paneltitulo, panelfield, panelTodotitulo, panelLogin;

    public Login() {
        super("Login");//Nombre de la ventana
        this.fondo = new JLabel(new ImageIcon("fondo2.png"));
        this.fondo.setOpaque(true);
        this.fondo.setLayout(new GridBagLayout());
        this.setContentPane(fondo);

        this.panelLogin = new JPanel();
        this.panelLogin.setLayout(new GridLayout(1, 2));
        this.panelLogin.setPreferredSize(new Dimension(750, 420));
        this.panelLogin.setBorder(BorderFactory.createLineBorder(new Color(0, 194, 177)));
        //Primer panel
        this.panel1 = new JPanel();//Creo el panel1
        this.panel1.setBorder(new EmptyBorder(60, 40, 0, 40));//creo padding Arriba,Izquierda,Abajo,Derecha son los valores del padding
        this.panel1.setLayout(new FlowLayout());//Aqui le asigno al panel1 la distribucion FlowLayout
        this.panel1.setBackground(Color.WHITE);//Aqui establesco el color de fondo del panel1
        this.titulo1 = new JLabel(new ImageIcon("logo.png"));//Aqui creo una etiqueta con la imagen
        this.correofield = new JLabel("ID*");//Aqui creo una etiqueta correo electronico
        this.correofield.setOpaque(true);
        this.correofield.setBackground(new Color(230, 247, 246));
        this.id = new JTextField(30);//Creo JTextField para el correo del usuario.
        this.id.setBackground(new Color(230, 247, 246));//Aqui agrego el color de fondo al JTextField
        this.id.setBorder(new EmptyBorder(0, 0, 0, 0));//Aqui le quito los bordes al JTextField
        this.id.setPreferredSize(new Dimension(WIDTH, 27));
        this.passwordfield = new JLabel("Password *");//Aqui creo una etiqueta password
        this.passwordfield.setOpaque(true);
        this.passwordfield.setBackground(new Color(230, 247, 246));
        this.password = new JPasswordField(30);//Creo JPasswordField para la contrasena del usuario.
        this.password.setBackground(new Color(230, 247, 246));//Aqui agrego el color de fondo al JPasswordField
        this.password.setBorder(new EmptyBorder(0, 0, 0, 0));//Aqui le quito los bordes al JPasswordField
        this.password.setPreferredSize(new Dimension(WIDTH, 27));
        this.panelfield = new JPanel();
        this.panelfield.setLayout(new BoxLayout(panelfield, BoxLayout.Y_AXIS));
        this.panelfield.setBackground(Color.WHITE);
        this.panelfield.add(correofield);
        this.panelfield.add(id);
        this.panelfield.add(new JLabel("  "));
        this.panelfield.add(passwordfield);
        this.panelfield.add(password);
        this.titulo2 = new JLabel("¿Olvidaste tu contrasena?", SwingConstants.CENTER);//Aqui creo una etiqueta con el texto olvidaste contrasena que va estar centrado en la etiqueta
        this.titulo2.setForeground(new Color(22, 197, 169));//Asigno color al olvidar contrasena
        this.titulo2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.bRegistar = new JButton("Registrar");//Creo boton registar 
        this.bRegistar.setBackground(new Color(0, 79, 124));//Aqui agrego fondo al boton registar
        this.bRegistar.setForeground(Color.WHITE);//Aqui agrego color a la letra del boton registar
        this.bRegistar.setFont(new Font(("arial"), Font.BOLD, 15));//Aqui le indico que la letra va hacer tipo arial con negrita y tamano 15
        this.bIngresar = new JButton(">  Ingresar");//Creo boton ingresar
        this.bIngresar.setBackground(new Color(0, 79, 124));//Aqui agrego fondo al boton ingresar
        this.bIngresar.setForeground(Color.WHITE);//Aqui agrego color a la letra del boton ingresar
        this.bIngresar.setFont(new Font(("arial"), Font.BOLD, 15));//Aqui le indico que la letra va hacer tipo arial con negrita y tamano 15
        this.panelbotones = new JPanel();//Aqui creo el panel que me va a llevar los botones
        this.panelbotones.setLayout(new FlowLayout());//Aqui establesco la distribucion del panel que me va a llevar los botones
        this.panelbotones.setBackground(Color.WHITE);//Aqui establesco el fondo del panel para que empate con el del panel1
        this.panelbotones.add(bRegistar);//Aqui agrego al panelbotones el boton bRegistar
        this.panelbotones.add(bIngresar);//Aqui agrego al panelbotones el bIngresar
        this.panel1.add(titulo1);//Aqui agrego al primer panel la imagen
        this.panel1.add(panelfield);//Agrego al panel1 el panelfield.
        this.panel1.add(titulo2);//Agrego al panel1 la eqiqueta olvidaste contrasena
        this.panel1.add(panelbotones);//Aqui agrego el panel de botones.
        //Segundo panel
        this.panel2 = new JPanel();//Creo panel2
        this.panel2.setLayout(new FlowLayout());//Asigno distribucion a panel2
        this.panel2.setBackground(new Color(0, 79, 124));//Asigno fondo al panel2
        this.panel2.setBorder(new EmptyBorder(60, 40, 0, 40));//creo padding Arriba,Izquierda,Abajo,Derecha son los valores del padding
        this.emoji1 = new JLabel("♡      ");//Creo el emoji corazon 
        this.emoji1.setOpaque(true);
        this.emoji1.setBackground(new Color(0, 79, 124));
        this.emoji1.setForeground(new Color(0, 194, 177));//Cambio el color del emoji
        this.emoji1.setFont(new Font(emoji1.getText(), Font.BOLD, 60));//Aqui establesco fuente predeterminada negrita y 60 de tamano
        this.emoji2 = new JLabel("________________________");//Aqui establesco el emoji ques es una barra
        this.emoji2.setForeground(new Color(0, 194, 177));//Aqui le cambio el color a la barra
        this.paneltitulo = new JPanel();//creo el paneltitulo
        this.paneltitulo.setBackground(new Color(0, 79, 124));//Asigno fondo al paneltitulo
        this.paneltitulo.setLayout(new FlowLayout());//Asigno distribucion al paneltitulo
        this.titulo3 = new JLabel("LifeDoc");//Creo etiqueta con texto LifeDoc titulo3
        this.titulo3.setFont(new Font(("arial"), Font.BOLD, 30));//A la etiqueta titulo3 le pongo tipo arial con negrita tamano 30
        this.titulo3.setForeground(Color.WHITE);//Aqui pongo la letra de la etiqueta titulo3 en blanco
        this.titulo4 = new JLabel("Care");
        this.titulo4.setForeground(new Color(0, 194, 177));
        this.titulo4.setFont(new Font(("arial"), Font.BOLD, 30));
        this.paneltitulo.add(titulo3);
        this.paneltitulo.add(titulo4);
        this.descripcion = new JLabel("Mas que citas, una experiencia completa de salud.");
        this.descripcion.setForeground(new Color(0, 194, 177));
        this.panelTodotitulo = new JPanel();
        this.panelTodotitulo.setLayout(new BoxLayout(panelTodotitulo, BoxLayout.Y_AXIS));
        this.panelTodotitulo.setBackground(new Color(0, 79, 124));
        this.panelTodotitulo.add(emoji1, BorderLayout.NORTH);
        this.panelTodotitulo.add(paneltitulo);
        this.panel2.add(panelTodotitulo);
        this.panel2.add(emoji2);
        this.panel2.add(descripcion);

        this.panelLogin.add(panel1);
        this.panelLogin.add(panel2);
        this.fondo.add(panelLogin, new GridBagConstraints());
    }

    public String getId() {
        return this.id.getText();
    }

    public String getPassword() {
        return String.valueOf(this.password.getPassword());
    }
}
