/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
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

    public JButton btnPersonalCentro, btnInventarioMedicamentos, btnregistrarPersonal, btnAñadirMedicamento;
    public JPanel personalC, inventarioM;
    public JTable tablaPersonalR, tablaMedicamentoR;
    private JScrollPane miscrollListaPersonal, miscrollListaMedicamento;
    public DefaultTableModel listaPersonalR, listaMedicamentoR;

    public AdminCentroInterfaz(String nombrePersona, String nombreInterfaz, String url) {
        super(nombrePersona, nombreInterfaz, url);
        this.btnPersonalCentro = new JButton("👥 Personal del Centro");
        this.btnregistrarPersonal = new JButton("👤 Registrar Personal");
        MetodosPublicos.estilizarBoton(btnregistrarPersonal, (byte) 5);
        this.btnInventarioMedicamentos = new JButton("💊 Inventario de Medicamentos");
        this.btnAñadirMedicamento = new JButton("➕ Añadir Medicamento");
        MetodosPublicos.estilizarBoton(btnAñadirMedicamento, (byte) 5);
        super.agregarBotonCuerpo1(btnPersonalCentro);
        super.agregarBotonCuerpo1(btnInventarioMedicamentos);
        this.personalC = new JPanel();
        this.inventarioM = new JPanel();
        this.personalC.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.inventarioM.setLayout(new FlowLayout(FlowLayout.LEFT));
        personalC.setOpaque(false);
        inventarioM.setOpaque(false);

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
        this.personalC.add(btnregistrarPersonal); //se agrega el boton al panel del apartado

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
        miscrollListaPersonal = new JScrollPane(tablaPersonalR);
        //miscrollLista.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        //se visualiza todo el tiempo
        miscrollListaPersonal.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        miscrollListaPersonal.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        //se agrega todo lo anterior al cuerpo dos
        cuerpo2.setLayout(new BorderLayout());
        cuerpo2.add(personalC, BorderLayout.NORTH);
        cuerpo2.add(miscrollListaPersonal, BorderLayout.CENTER);

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
    
    public void mostrarVistaInventarioMedicamento(){
        MetodosPublicos.vaciarPanel(cuerpo2);
        MetodosPublicos.vaciarPanel(inventarioM);

        JLabel tituloMedicamentoRegistrado = new JLabel("Medicamentos Registrados");
        //todo lo del titulo de medicamentos registrados 
        tituloMedicamentoRegistrado.setFont(new Font("arial", Font.BOLD, 20)); //se crea el titulo
        tituloMedicamentoRegistrado.setForeground(PacienteInterfaz.COLOR_AZUL_CORPORATIVO); //se le añade un color
        this.inventarioM.add(tituloMedicamentoRegistrado); //se agrega el titulo al panel del apartado

        //boton de añadir al lado del titulo
        this.inventarioM.add(Box.createHorizontalStrut(10)); //se da un espacio entre el titulo y el boton para separar
        this.inventarioM.add(btnAñadirMedicamento); //se agrega el boton al panel del apartado

        //lista de personal regisrado
        listaMedicamentoR = new DefaultTableModel();
        listaMedicamentoR.addColumn("FOTO MEDICAMENTO");
        listaMedicamentoR.addColumn("NÚMERO REGISTRO SANITARIO");
        listaMedicamentoR.addColumn("NOMBRE");
        listaMedicamentoR.addColumn("DESCRIPCIÓN");
        listaMedicamentoR.addColumn("FECHA VENCIMIETO");
        listaMedicamentoR.addColumn("CANTIDAD");
        listaMedicamentoR.addColumn("STOCK");
        listaMedicamentoR.addColumn("ULTIMA ACTUALIZACIÓN");
        listaMedicamentoR.addColumn("  ");
        //detallesListaPersonalR = new JPanel(); //panel que va a contener la lista
        //grid = new GridLayout(1,1);
        tablaMedicamentoR = new JTable(listaMedicamentoR);
        miscrollListaMedicamento = new JScrollPane(tablaMedicamentoR);
        //miscrollLista.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        //se visualiza todo el tiempo
        miscrollListaMedicamento.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        miscrollListaMedicamento.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        //se agrega todo lo anterior al cuerpo dos
        cuerpo2.setLayout(new BorderLayout());
        cuerpo2.add(inventarioM, BorderLayout.NORTH);
        cuerpo2.add(miscrollListaMedicamento, BorderLayout.CENTER);

        //diseño para las columnas de la tabla
        JTableHeader diseñoColumnaT = tablaMedicamentoR.getTableHeader();
        diseñoColumnaT.setFont(new Font("arial", Font.BOLD, 14));
        diseñoColumnaT.setForeground(Color.WHITE); //color del texto
        diseñoColumnaT.setBackground(PacienteInterfaz.COLOR_VERDE_ACENTO); //color de fondo de la columna

        //diseño para las filas en general
        tablaMedicamentoR.setFont(new Font("Arial", Font.PLAIN, 13));
        tablaMedicamentoR.setForeground(Color.DARK_GRAY);//color del texto filas
        tablaMedicamentoR.setBackground(Color.WHITE);// fondo blanco

        MetodosPublicos.refrescarVentana(inventarioM);
        MetodosPublicos.refrescarVentana(cuerpo2);

    
    }

}
