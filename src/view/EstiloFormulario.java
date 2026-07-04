/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import view.PacienteInterfaz;

/**
 * Clase con métodos para crear y estilizar los componentes
 * visuales que se repiten en distintos formularios de la aplicación
 * (campos de texto, combos, selectores de fecha, etiquetas, filas, etc.).
 * Todos los métodos son estáticos: no dependen de ninguna interfaz en
 * particular, por lo que cualquier vista puede reutilizarlos.
 */
public class EstiloFormulario {

    // Constructor privado ya esta clase solo se usa por sus métodos estáticos, no se instancia
    private EstiloFormulario() {
    }

    /**
     * Crea un JTextField ya estilizado (fuente y borde verde) listo
     * para usarse en cualquier formulario.
     *
     * @return el campo de texto estilizado
     */
    public static JTextField crearCampoTexto() {
        JTextField campo = new JTextField(15); // campo con ancho de 15 columnas
        campo.setFont(new Font("Arial", Font.PLAIN, 15)); // fuente Arial tamaño 15
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PacienteInterfaz.COLOR_VERDE_ACENTO, 1, true), // borde verde
                BorderFactory.createEmptyBorder(8, 10, 8, 10) //espacio interno para que el texto no quede pegado al borde
        ));
        return campo; // se retorna listo para agregarse al formulario
    }

    /**
     * Aplica estilo (fondo blanco, borde verde, fuente grande, flecha
     * personalizada) a un JComboBox YA EXISTENTE. No define ni modifica
     * las opciones/datos del combo, solo su apariencia — por eso sirve
     * para cualquier combo, tenga o no datos precargados 
     *
     * @param combo el combo (ya creado, puede estar vacío) a estilizar
     */
    public static void crearComboEstilizado(JComboBox combo) {
        combo.setPreferredSize(new Dimension(200, 35)); // tamaño fijo para que combine con los demás campos
        combo.setFont(new Font("Arial", Font.PLAIN, 15)); // misma fuente que los JTextField
        combo.setBackground(Color.WHITE); //fondo blanco para qutarle el gris por defecto
        combo.setFocusable(false); // evita que el combo robe el foco al usar Tab en el teclado
        combo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PacienteInterfaz.COLOR_VERDE_ACENTO, 1, true), // borde verde
                BorderFactory.createEmptyBorder(2, 8, 2, 2) // pequeño espacio interno
        ));
        combo.setUI(new BasicComboBoxUI() { // se reemplaza el UI del sistema (el diseño predeterinado de java) para poder pintar la flecha manualmente
            @Override
            protected JButton createArrowButton() {
                JButton boton = new JButton("▼"); // botón con el ícono de flecha
                boton.setBorder(BorderFactory.createEmptyBorder()); // sin borde, para que no se vea como botón normal
                boton.setBackground(Color.WHITE); // mismo fondo blanco del combo
                boton.setForeground(PacienteInterfaz.COLOR_VERDE_ACENTO); // flecha en color verde
                boton.setFocusPainted(false); // sin marco de foco al hacer clic
                boton.setContentAreaFilled(false); // fondo transparente
                return boton;
            }
        });
        combo.setRenderer(new DefaultListCellRenderer() { // controla el estilo de cada opción en la lista desplegable
            @Override
            //crea el renderizado estándar de esa opción, para después modificarle el estilo encima.
            public Component getListCellRendererComponent(JList list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                c.setBackground(isSelected ? PacienteInterfaz.COLOR_VERDE_ACENTO : Color.WHITE); // resalta la opción seleccionada
                c.setForeground(isSelected ? Color.WHITE : Color.BLACK); // texto blanco si está seleccionada
                c.setFont(new Font("Arial", Font.PLAIN, 15)); // misma fuente del resto del formulario
                return c;
            }
        });
    }

    

    /**
     * Envuelve un campo (JTextField, JComboBox, DatePicker, JScrollPane,
     * etc.) junto con una etiqueta arriba, en un solo JPanel vertical.
     * Así el título del campo siempre queda arriba de él, sin importar
     * el tipo de componente que sea.
     *
     * @param texto texto de la etiqueta (ej. "Nombre:")
     * @param campo el componente que va debajo de la etiqueta
     * @return un JPanel con la etiqueta arriba y el campo abajo
     */
    public static JPanel crearCampoConEtiqueta(String texto, JComponent campo) {
        JPanel panelCampo = new JPanel(); // panel contenedor de la etiqueta + el campo
        panelCampo.setOpaque(false); // sin fondo propio, se ve el fondo del formulario
        panelCampo.setLayout(new BoxLayout(panelCampo, BoxLayout.Y_AXIS)); // apila los elementos verticalmente

        JLabel etiqueta = new JLabel(texto); // etiqueta con el nombre del campo
        etiqueta.setFont(new Font("Arial", Font.BOLD, 17)); // fuente en negrita
        etiqueta.setForeground(new Color(60, 60, 60)); // gris oscuro
        etiqueta.setAlignmentX(Component.LEFT_ALIGNMENT); // alineada a la izquierda dentro del panel

        campo.setAlignmentX(Component.LEFT_ALIGNMENT); // el campo también alineado a la izquierda
        // Si el campo NO es un JScrollPane (como la descripción), se limita su altura
        // para que no se estire de más dentro del BoxLayout vertical
        if (!(campo instanceof JScrollPane)) {
            campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, campo.getPreferredSize().height));
        }

        panelCampo.add(etiqueta); // se agrega la etiqueta arriba
        panelCampo.add(Box.createVerticalStrut(5)); // pequeño espacio entre la etiqueta y el campo
        panelCampo.add(campo); // se agrega el campo debajo de la etiqueta
        return panelCampo;
    }

    /**
     * Crea una fila horizontal simple: una etiqueta en negrita a la
     * izquierda y un componente al lado (usado, por ejemplo, para
     * "Nombre del horario:" en el formulario de creación de horario).
     *
     * @param etiquetaTexto texto de la etiqueta
     * @param componente componente que va al lado de la etiqueta
     * @return el JPanel con la fila lista
     */
    public static JPanel crearFila(String etiquetaTexto, JComponent componente) {
        JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0)); // fila horizontal, 10px de espacio entre elementos
        fila.setOpaque(false); // sin fondo
        fila.setAlignmentX(Component.LEFT_ALIGNMENT); // alineada a la izquierda
        JLabel lbl = new JLabel(etiquetaTexto); // etiqueta de la fila
        lbl.setFont(new Font("Arial", Font.BOLD, 17)); // fuente en negrita
        lbl.setForeground(Color.darkGray); // color gris oscuro
        fila.add(lbl); // se agrega la etiqueta
        fila.add(componente); // se agrega el componente al lado
        return fila;
    }

    /**
     * Crea un separador de sección: un título a la izquierda y una
     * línea horizontal que ocupa el resto del espacio (usado para
     * dividir visualmente las secciones del formulario de horario).
     *
     * @param texto título de la sección
     * @return el JPanel con el título y el separador
     */
    public static JPanel crearSeparador(String texto) {
        JPanel panelSep = new JPanel(new BorderLayout(8, 0)); // título a la izquierda, línea en el resto del espacio
        panelSep.setOpaque(false); // sin fondo
        panelSep.setAlignmentX(Component.LEFT_ALIGNMENT); // alineado a la izquierda
        panelSep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28)); // altura fija para no estirarse de más
        JLabel lbl = new JLabel(texto); // título de la sección
        lbl.setFont(new Font("Arial", Font.BOLD, 17));
        lbl.setForeground(PacienteInterfaz.COLOR_AZUL_CORPORATIVO); // color corporativo azul
        JSeparator sep = new JSeparator(); // línea horizontal
        sep.setForeground(PacienteInterfaz.COLOR_VERDE_ACENTO); // línea en color verde
        panelSep.add(lbl, BorderLayout.CENTER); 
        panelSep.add(sep, BorderLayout.CENTER);
        return panelSep;
    }

    /**
     * Crea una etiqueta pequeña y gris, usada entre los combos de hora
     * del formulario de horario (ej. "Inicio", "Fin", "Almuerzo").
     *
     * @param texto texto de la mini etiqueta
     * @return el JLabel ya estilizado
     */
    public static JLabel crearMiniEtiqueta(String texto) {
        JLabel etiqueta = new JLabel(texto); // etiqueta pequeña
        etiqueta.setFont(new Font("Arial", Font.PLAIN, 11)); // fuente pequeña
        etiqueta.setForeground(Color.GRAY); // color gris
        return etiqueta;
    }

    /**
     * Crea el panel lateral para adjuntar la imagen de un medicamento.
     * Recibe el botón y la etiqueta de previsualización ya creados
     * (normalmente atributos de la interfaz) para ubicarlos dentro del
     * panel con el estilo correspondiente.
     *
     * @param btnSeleccionar botón que abrirá el JFileChooser
     * @param previsualizacionImagen etiqueta donde se mostrará la imagen elegida
     * @return el panel armado, listo para agregarse al formulario
     */
    public static JPanel crearPanelImagen(JButton btnSeleccionar, JLabel previsualizacionImagen) {
        JPanel panelImagen = new JPanel(new BorderLayout(0, 10)); // título arriba, contenido centrado abajo
        panelImagen.setPreferredSize(new Dimension(320, 0)); // ancho fijo, alto libre
        panelImagen.setOpaque(false); // sin fondo
        panelImagen.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PacienteInterfaz.COLOR_VERDE_ACENTO, 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel etiqueta = new JLabel("Imagen del Medicamento:", SwingConstants.CENTER); // título del panel
        etiqueta.setFont(new Font("Arial", Font.BOLD, 20));
        etiqueta.setBorder(new EmptyBorder(90, 0, 0, 0)); // empuja el título hacia abajo para centrar visualmente

        // Estilo de la etiqueta de previsualización (recibida como parámetro)
        previsualizacionImagen.setText("Sin imagen"); // texto inicial mientras no se elija ninguna imagen
        previsualizacionImagen.setHorizontalAlignment(SwingConstants.CENTER);
        previsualizacionImagen.setPreferredSize(new Dimension(180, 180)); // tamaño fijo del recuadro de previsualización
        previsualizacionImagen.setBorder(BorderFactory.createDashedBorder(PacienteInterfaz.COLOR_VERDE_ACENTO)); // borde punteado
        previsualizacionImagen.setOpaque(true); // necesita fondo propio para que se note el color blanco
        previsualizacionImagen.setBackground(Color.WHITE);
        previsualizacionImagen.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnSeleccionar.setAlignmentX(Component.CENTER_ALIGNMENT); // centra el botón dentro del panel vertical

        JPanel centro = new JPanel(); // contenedor de la previsualización + el botón
        centro.setOpaque(false);
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS)); // apilados verticalmente
        centro.add(previsualizacionImagen);
        centro.add(Box.createVerticalStrut(10)); // espacio entre la imagen y el botón
        centro.add(btnSeleccionar);

        panelImagen.add(etiqueta, BorderLayout.NORTH); // título arriba
        panelImagen.add(centro, BorderLayout.CENTER); // contenido centrado

        return panelImagen;
    }
}
