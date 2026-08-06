package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.EstadisticaCitas;
import model.MetodosPublicos;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import static view.PacienteInterfaz.COLOR_AZUL_CORPORATIVO;

public class ReportesInterfaz extends LayoutView {

    public static final int hign = 406;
    public static final int width = 689;
    public static final ImageIcon imagenOriginal = new ImageIcon("graficos.png");

    JPanel panelContenedorGraficosCitas;
    public JButton btnCerrar, btnUsuSisben, btnInfoCitas, btnAtencionMedica, btnFinancieros, btnDescargarInfoCitasReportes;

    public ReportesInterfaz() {
        super.encabezado.setLayout(new BorderLayout());
        JPanel titulo = new Titulo("Reportes", " Graficos").getPanelTitulo();
        btnCerrar = new JButton("CERRAR", new ImageIcon("iconsP/quejas.png"));
        MetodosPublicos.estilizarBoton(btnCerrar, (byte) 6);
        btnCerrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        encabezado.add(titulo, BorderLayout.WEST);
        encabezado.add(btnCerrar, BorderLayout.EAST);
        btnUsuSisben = new JButton("Sisben", new ImageIcon("iconsP/together.png"));
        btnInfoCitas = new JButton("Citas", new ImageIcon("iconsP/cruzMedico.png"));
        btnAtencionMedica = new JButton("Atencion", new ImageIcon("iconsP/doctor.png"));
        btnFinancieros = new JButton("Finanzas", new ImageIcon("iconsP/money-bag.png"));
        super.cuerpo1.setLayout(new FlowLayout(FlowLayout.LEFT));
        super.cuerpo1.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(20, 40, 0, 40),
                BorderFactory.createMatteBorder(0, 0, 2, 0, COLOR_AZUL_CORPORATIVO)));
        agregarBotonCuerpo1(btnUsuSisben);
        agregarBotonCuerpo1(btnInfoCitas);
        agregarBotonCuerpo1(btnAtencionMedica);
        agregarBotonCuerpo1(btnFinancieros);

        Image imagenEscalada = imagenOriginal.getImage().getScaledInstance(width, hign, Image.SCALE_SMOOTH);
        ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);

        JLabel imagen = new JLabel(iconoEscalado);
        JPanel panelConImagen = new JPanel();
        panelConImagen.setLayout(new BorderLayout());
        panelConImagen.add(imagen, BorderLayout.CENTER);

        JPanel cuerpoAviso = new JPanel();
        JPanel tituloPanel = new Titulo("Graficos Reportes Para El ", " Administrador.", 30).getPanelTitulo();
        cuerpoAviso.setOpaque(false);
        cuerpoAviso.setLayout(new BoxLayout(cuerpoAviso, BoxLayout.Y_AXIS));
        cuerpoAviso.add(Box.createVerticalStrut(90));
        cuerpoAviso.add(tituloPanel);
        cuerpoAviso.add(Box.createVerticalStrut(10));
        cuerpoAviso.add(panelConImagen);

        super.cuerpo2.add(cuerpoAviso, BorderLayout.CENTER);
        panelContenedorGraficosCitas = new JPanel();
        panelContenedorGraficosCitas.setOpaque(false);
        panelContenedorGraficosCitas.setLayout(new BoxLayout(panelContenedorGraficosCitas, BoxLayout.Y_AXIS));
    }

    public JPanel crearPanelConValores(String valor1, String valor2, String valor3, String valor4, String valor5) {
        JPanel panelFinal = new JPanel(new GridLayout(1, 5, 10, 0));
        panelFinal.setOpaque(false);
        panelFinal.setPreferredSize(new Dimension(860, 110));

        panelFinal.add(crearTarjeta("AGENDADAS", valor1, new Color(248, 205, 205)));
        panelFinal.add(crearTarjeta("CONFIRMADAS", valor2, new Color(198, 230, 206)));
        panelFinal.add(crearTarjeta("CANCELADAS", valor3, new Color(232, 249, 248)));
        panelFinal.add(crearTarjeta("REAGENDADAS", valor4, COLOR_VERDE_ACENTO));
        panelFinal.add(crearTarjeta("ATENDIDAS", valor5, new Color(255, 224, 178)));

        return panelFinal;
    }

    private JPanel crearTarjeta(String titulo, String valor, Color color) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(color);

        JLabel labelTitulo = new JLabel(titulo, JLabel.CENTER);
        labelTitulo.setFont(labelTitulo.getFont().deriveFont(Font.BOLD, 12f));
        labelTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JLabel labelValor = new JLabel(valor, JLabel.CENTER);
        labelValor.setFont(labelValor.getFont().deriveFont(Font.BOLD, 26f));

        panel.add(labelTitulo, BorderLayout.NORTH);
        panel.add(labelValor, BorderLayout.CENTER);
        return panel;
    }

    public JPanel crearGraficoCitas(int cA, int cB, int cC, int cD, int cN) {
        DefaultPieDataset datos = new DefaultPieDataset();
        datos.setValue("Grupo A", cA);
        datos.setValue("Grupo B", cB);
        datos.setValue("Grupo C", cC);
        datos.setValue("Grupo D", cD);
        datos.setValue("Sin sisben", cN);

        JFreeChart chart = ChartFactory.createPieChart("Usuarios de LifeDoc Care",//Nombre del diagrama
                datos,//datos
                true,//nombre de la categoria
                true,//herramientas
                false);//generacion url

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setMouseWheelEnabled(true);
        chartPanel.setPreferredSize(new Dimension(648, 416));
        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.add(chartPanel, BorderLayout.CENTER);
        return contenedor;
    }

    public JPanel crearGraficoPorDia(List<EstadisticaCitas> lista) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (EstadisticaCitas e : lista) {
            String fechaStr = e.getFecha().toString();
            dataset.addValue(e.getCitaCancelada(), "Canceladas", fechaStr);
            dataset.addValue(e.getCitaReagendada(), "Reagendadas", fechaStr);
        }

        JFreeChart chart = ChartFactory.createBarChart("Citas Canceladas y Reagendadas por Dia", "Fecha", "Cantidad", dataset);

        ChartPanel chartPanel = new ChartPanel(chart);
        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.add(chartPanel, BorderLayout.CENTER);
        return contenedor;
    }

    @Override
    public void agregarBotonCuerpo1(JButton boton) {
        MetodosPublicos.estilizarBoton(boton, (byte) 1);
        cuerpo1.add(boton);
        cuerpo1.add(Box.createHorizontalStrut(5));
        MetodosPublicos.refrescarVentana(cuerpo1);
    }

    @Override
    public void habilitarBotonesMenu(JButton botonActivo) {
        botonActivo.setEnabled(false);
        if (botonActivo != btnUsuSisben && !btnUsuSisben.isEnabled()) {
            btnUsuSisben.setEnabled(true);
        }
        if (botonActivo != btnInfoCitas && !btnInfoCitas.isEnabled()) {
            btnInfoCitas.setEnabled(true);
        }
        if (botonActivo != btnAtencionMedica && !btnAtencionMedica.isEnabled()) {
            btnAtencionMedica.setEnabled(true);
        }
        if (botonActivo != btnFinancieros && !btnFinancieros.isEnabled()) {
            btnFinancieros.setEnabled(true);
        }
    }

    public void cargarGraficosCitasCuerpo2(JPanel panel, JPanel estaditica) {
        MetodosPublicos.vaciarPanel(cuerpo2);
        MetodosPublicos.vaciarPanel(panelContenedorGraficosCitas);
        estaditica.setOpaque(false);
        panelContenedorGraficosCitas.add(Box.createVerticalStrut(30));
        panelContenedorGraficosCitas.add(new Titulo("Reportes ", "Citas").getPanelTitulo());
        panelContenedorGraficosCitas.add(panel);
        panelContenedorGraficosCitas.add(Box.createVerticalStrut(30));
        panelContenedorGraficosCitas.add(estaditica);
        super.cuerpo2.add(panelContenedorGraficosCitas, BorderLayout.CENTER);
        MetodosPublicos.refrescarVentana(cuerpo2);
        MetodosPublicos.refrescarVentana(panelContenedorGraficosCitas);
    }

    public void cargarGraficoSisben(int cA, int cB, int cC, int cD, int cN) {
        MetodosPublicos.vaciarPanel(cuerpo2);
        JPanel panelContodo = new JPanel();
        panelContodo.setLayout(new BoxLayout(panelContodo,BoxLayout.Y_AXIS));
        panelContodo.setOpaque(false);
        JPanel titulo = new Titulo("Diagrama Circular"," Sisben",30).getPanelTitulo();
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel panel = crearGraficoCitas(cA, cB, cC, cD, cN);
        panelContodo.add(Box.createVerticalStrut(40));
        panelContodo.add(titulo);
        panelContodo.add(Box.createVerticalStrut(40));
        panelContodo.add(panel);
        super.cuerpo2.add(panelContodo, BorderLayout.CENTER);
        MetodosPublicos.refrescarVentana(cuerpo2);
    }

    public void vaciarPanelCuerpo2() {
        MetodosPublicos.vaciarPanel(cuerpo2);
        MetodosPublicos.refrescarVentana(cuerpo2);
    }
}
