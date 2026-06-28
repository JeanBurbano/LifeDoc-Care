/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import model.MetodosPublicos;

/**
 *
 * @author lunaa
 */
public class AdminCentroInterfaz extends PacienteInterfaz {

    public JButton btnPersonalCentro, btnInventarioMedicamentos, btnregistrarUsuario;
    public JPanel personalC, detallesListaPersonalR, inventarioM;
    public JTable tablaPersonalR;
    private GridLayout grid;
    private JScrollPane miscrollLista;
    public DefaultTableModel listaPersonalR;

    public AdminCentroInterfaz(String nombrePersona, String nombreInterfaz, String url) {
        super(nombrePersona, nombreInterfaz, url);
        this.btnPersonalCentro = new JButton("👥 Personal del Centro");
        this.btnregistrarUsuario = new JButton("Registrar Personal");
        super.estilizarBoton(btnregistrarUsuario, (byte) 5);
        this.btnInventarioMedicamentos = new JButton("💊 Inventario de Medicamentos");
        super.agregarBotonCuerpo1(btnPersonalCentro);
        super.agregarBotonCuerpo1(btnInventarioMedicamentos);
        this.personalC = new JPanel();
        this.personalC.setLayout(new FlowLayout(FlowLayout.LEFT));
        personalC.setOpaque(false);

    }

    public void habilitarBotones(JButton botonActivo) {
        if (botonActivo != btnPersonalCentro && !btnPersonalCentro.isEnabled()) {
            this.btnPersonalCentro.setEnabled(true);
        }
        if (botonActivo != btnInventarioMedicamentos && !btnInventarioMedicamentos.isEnabled()) {
            this.btnInventarioMedicamentos.setEnabled(true);
        }
    }

    public void mostrarVistaPersonalCentro() {
        MetodosPublicos.vaciarPanel(cuerpo2);
        MetodosPublicos.vaciarPanel(personalC);

        JLabel tituloPersonalRegistrado = new JLabel("Personal del Centro Registrado");
        //todo lo del titulo de personal del centro registrado 
        tituloPersonalRegistrado.setFont(new Font("arial", Font.BOLD, 20)); //se crea el titulo
        tituloPersonalRegistrado.setForeground(PacienteInterfaz.COLOR_AZUL_CORPORATIVO); //se le añade un color
        this.personalC.add(tituloPersonalRegistrado); //se agrega el titulo al panel del apartado

        //boton de registro al lado del titulo
        this.personalC.add(Box.createHorizontalStrut(10)); //se da un espacio entre el titulo y el boton para separar
        this.personalC.add(btnregistrarUsuario); //se agrega el boton al panel del apartado

        //lista de personal regisrado
        listaPersonalR = new DefaultTableModel();
        listaPersonalR.addColumn("FOTO DE PERFIL");
        listaPersonalR.addColumn("TIPO DOCUMENTO");
        listaPersonalR.addColumn("NÚMERO DOCUMENTO");
        listaPersonalR.addColumn("NOMBRE COMPLETO");
        listaPersonalR.addColumn("EDAD");
        listaPersonalR.addColumn("SEXO");
        listaPersonalR.addColumn("CORREO");
        listaPersonalR.addColumn("NÚMERO CELULAR");
        listaPersonalR.addColumn("ROL");
        listaPersonalR.addColumn("  ");
        //detallesListaPersonalR = new JPanel(); //panel que va a contener la lista
        //grid = new GridLayout(1,1);
        tablaPersonalR = new JTable(listaPersonalR);
        miscrollLista = new JScrollPane(tablaPersonalR);
        //miscrollLista.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        //se visualiza todo el tiempo
        miscrollLista.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        miscrollLista.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        //se agrega todo lo anterior al cuerpo dos
        cuerpo2.setLayout(new BorderLayout());
        cuerpo2.add(personalC, BorderLayout.NORTH);
        cuerpo2.add(miscrollLista, BorderLayout.CENTER);

        //diseño para las columnas de la tabla
        JTableHeader diseñoColumnaT = tablaPersonalR.getTableHeader();
        diseñoColumnaT.setFont(new Font("arial", Font.BOLD, 14));
        diseñoColumnaT.setForeground(Color.WHITE); //color del texto
        diseñoColumnaT.setBackground(PacienteInterfaz.COLOR_VERDE_ACENTO); //color de fondo de la columna

        //diseño para las filas en general
        tablaPersonalR.setFont(new Font("Arial", Font.PLAIN, 13));
        tablaPersonalR.setForeground(Color.DARK_GRAY);//color del texto filas
        tablaPersonalR.setBackground(Color.WHITE);// fondo blanco

        MetodosPublicos.refrescarVentana(personalC);
        MetodosPublicos.refrescarVentana(cuerpo2);

    }

}
