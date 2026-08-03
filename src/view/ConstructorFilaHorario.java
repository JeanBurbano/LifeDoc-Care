/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import model.MetodosPublicos;


/**
 * Clase para construir cada fila del formulario de horario
 * (una fila por día de la semana, con checkbox, combos de hora y
 * etiqueta de horas laborales calculadas).
 */
public class ConstructorFilaHorario {

    private ConstructorFilaHorario() {
    }

    
    public static JComboBox construirComboHora(String[] horas) {
        JComboBox combo = new JComboBox<>(horas); //combo con las horas recibidas
        MetodosPublicos.crearComboEstilizado(combo);
        combo.setFont(new Font("Arial", Font.PLAIN, 12)); // fuente pequeña
        combo.setPreferredSize(new Dimension(110, 28)); // tamaño fijo 
        return combo;
    }

    
    public static JPanel construirFilaDia(int indiceDia, String[] diasSemana, String[] horas,
            JCheckBox[] diaSemana, JComboBox[] horaInicio, JComboBox[] horaFin,
            JComboBox[] almuerzoIni, JComboBox[] almuerzoFin, JLabel[] lblHoras) {

        //Panel contenedor de la fila 
        JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 6)); //elementos en fila, 10px horizontal / 6px vertical
        fila.setOpaque(false); //sin fondo
        fila.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PacienteInterfaz.COLOR_VERDE_ACENTO, 1, true), //borde verde
                BorderFactory.createEmptyBorder(2, 8, 2, 8) // espacio interno
        ));
        fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48)); // altura fija para que todas las filas midan lo mismo
        fila.setAlignmentX(Component.LEFT_ALIGNMENT); // alineada a la izquierda

        // Checkbox para activar/desactivar el día
        diaSemana[indiceDia] = new JCheckBox(); // se crea y se guarda en el arreglo recibido
        diaSemana[indiceDia].setSelected(indiceDia < 5); // Lunes a Viernes activos por defecto, Sábado no
        diaSemana[indiceDia].setOpaque(false); // sin fondo propio
        

        // Nombre del día con ancho fijo para que queden alineados entre filas
        JLabel lblDia = new JLabel(diasSemana[indiceDia]);
        lblDia.setFont(new Font("Arial", Font.BOLD, 13));
        lblDia.setForeground(Color.DARK_GRAY);
        lblDia.setPreferredSize(new Dimension(82, 20)); // ancho fijo para alinear todas las filas

        //Separador visual entre el nombre del día y los combos de hora
        JSeparator sep = new JSeparator(JSeparator.VERTICAL);
        sep.setPreferredSize(new Dimension(1, 24));
        sep.setForeground(PacienteInterfaz.COLOR_VERDE_ACENTO);

        //Combos de hora
        horaInicio[indiceDia] = construirComboHora(horas);
        horaFin[indiceDia] = construirComboHora(horas);
        almuerzoIni[indiceDia] = construirComboHora(horas);
        almuerzoFin[indiceDia] = construirComboHora(horas);
        

        // Etiqueta que mostrará las horas laborales calculadas para este día
        lblHoras[indiceDia] = new JLabel("—"); //texto inicial mientras no se calculan las horas
        lblHoras[indiceDia].setFont(new Font("Arial", Font.BOLD, 12));
        lblHoras[indiceDia].setForeground(Color.DARK_GRAY);
        lblHoras[indiceDia].setPreferredSize(new Dimension(54, 20));

        // Se agregan todos los componentes a la fila, con mini etiquetas intercaladas para dar contexto
        fila.add(diaSemana[indiceDia]);
        fila.add(lblDia);
        fila.add(sep);
        fila.add(MetodosPublicos.crearMiniEtiqueta("Inicio"));
        fila.add(horaInicio[indiceDia]);
        fila.add(MetodosPublicos.crearMiniEtiqueta("Fin"));
        fila.add(horaFin[indiceDia]);
        fila.add(MetodosPublicos.crearMiniEtiqueta("Descanso"));
        fila.add(almuerzoIni[indiceDia]);
        fila.add(MetodosPublicos.crearMiniEtiqueta("→"));
        fila.add(almuerzoFin[indiceDia]);
        fila.add(MetodosPublicos.crearMiniEtiqueta("Horas Lab."));
        fila.add(lblHoras[indiceDia]);

        // Si el día está inactivo desde el inicio (ej. Sábado), se inhabilita toda la fila
        if (!diaSemana[indiceDia].isSelected()) {
            atenuarFila(indiceDia, false, horaInicio, horaFin, almuerzoIni, almuerzoFin, lblHoras);
        }

        return fila;
    }

    
    public static void atenuarFila(int indiceDia, boolean activo,
            JComboBox[] horaInicio, JComboBox[] horaFin,
            JComboBox[] almuerzoIni, JComboBox[] almuerzoFin, JLabel[] lblHoras) {
        horaInicio[indiceDia].setEnabled(activo); // habilita/deshabilita el combo de inicio
        horaFin[indiceDia].setEnabled(activo); // habilita/deshabilita el combo de fin
        almuerzoIni[indiceDia].setEnabled(activo); // habilita/deshabilita el combo de inicio de almuerzo
        almuerzoFin[indiceDia].setEnabled(activo); // habilita/deshabilita el combo de fin de almuerzo
        if (!activo) { // si se está desactivando el día
            lblHoras[indiceDia].setText("—"); // se resetea el texto de horas laborales
            lblHoras[indiceDia].setForeground(Color.GRAY); // se pone en gris para indicar que está inactivo
        }
    }
}