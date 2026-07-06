/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
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
    JPanel panelLifeDoc;

    public RegistroUsuariosInterfaz(String nombrePersona, String nombreInterfaz, String url){
    super(nombrePersona, nombreInterfaz, url);
    MetodosPublicos.vaciarPanel(encabezado);
    MetodosPublicos.vaciarPanel(cuerpo1);
    this.cuerpo1.setBorder(BorderFactory.createEmptyBorder());
    this.cuerpo1.setLayout(new BoxLayout(cuerpo1, BoxLayout.Y_AXIS)); // <-- clave para que todo quede pegado a la izquierda
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
    btnVolverA.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    this.btnRegistrarse = new JButton("Registrarme");
    MetodosPublicos.estilizarBoton(btnRegistrarse, (byte) 5);

    //Título de Crear una Cuenta
    creacionPerfil.setLayout(new BoxLayout(creacionPerfil, BoxLayout.Y_AXIS));

    this.tituloCreacion = new JLabel("Crear una Cuenta");
    this.tituloCreacion.setFont(new Font("arial", Font.PLAIN, 33));
    this.tituloCreacion.setForeground(Color.BLACK);
    this.tituloCreacion.setAlignmentX(Component.LEFT_ALIGNMENT);
    

    this.panelLifeDoc = new Titulo("LifeDoc", "Care").getPanelTitulo();
    this.panelLifeDoc.setAlignmentX(Component.LEFT_ALIGNMENT);
    this.panelLifeDoc.setMaximumSize(panelLifeDoc.getPreferredSize()); // corrige el desplazamiento hacia la derecha del LifeDoc

    this.creacionPerfil.add(tituloCreacion);
    this.creacionPerfil.add(panelLifeDoc);

    this.volver.setLayout(new FlowLayout());
    this.volver.add(btnVolverA);
    this.encabezado.add(volver, BorderLayout.EAST);
    this.encabezado.add(creacionPerfil, BorderLayout.WEST);
    
    this.cuerpo1.setBorder(new EmptyBorder(0, 0, 30, 0)); 

    // Primera sección de Identificación
    this.identificacion.setLayout(new BorderLayout(8, 0));
    this.identificacion.setAlignmentX(Component.LEFT_ALIGNMENT);
    this.identificacion.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));
    
    
    
    this.tloSeccion1 = new JLabel("Identificación");
    this.tloSeccion1.setAlignmentX(Component.LEFT_ALIGNMENT);
    tloSeccion1.setFont(new Font("Arial", Font.BOLD, 24));
    tloSeccion1.setForeground(PacienteInterfaz.COLOR_AZUL_CORPORATIVO);
    this.tloSeccion1.setMaximumSize(panelLifeDoc.getPreferredSize());

    JSeparator sep = new JSeparator();
    sep.setForeground(PacienteInterfaz.COLOR_VERDE_ACENTO);

    this.identificacion.add(tloSeccion1, BorderLayout.WEST);
    this.identificacion.add(sep, BorderLayout.CENTER);

    this.cuerpo2.add(identificacion);
    
    this.cuerpo2.add(datos);
    this.cuerpo2.add(fotoF);

    MetodosPublicos.refrescarVentana(cuerpo2);
    MetodosPublicos.refrescarVentana(cuerpo1);

    }

}
