package model;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import static view.PacienteInterfaz.COLOR_AZUL_CORPORATIVO;

public class MetodosPublicos {

    //Aqui creo un funcion para estilizar el boton
    public static void estilizarBoton(JButton boton, byte estilo) {
        switch (estilo) {
            case 1://Botones del menu cuerpo1
                boton.setFont(new Font("Arial", Font.BOLD, 15));
                boton.setBackground(new Color(232, 249, 248));
                boton.setForeground(new Color(0, 75, 121));
                break;
            case 2://Botones laterales tipo Historial Medico/Historial de Citas
                boton.setFont(new Font("Arial", Font.BOLD, 30));
                boton.setBackground(Color.WHITE);
                boton.setForeground(new Color(0, 75, 121));
                break;
            case 3://Boton Agendar una cita
                boton.setFont(new Font("arial", Font.BOLD, 20));
                boton.setAlignmentX(Component.CENTER_ALIGNMENT);
                boton.setBackground(Color.WHITE);
                boton.setForeground(COLOR_AZUL_CORPORATIVO);
                break;
            case 4://Botones de tipo de consulta / sugerencias / quejas / foro
                boton.setFont(new Font("Arial", Font.BOLD, 30));
                boton.setBackground(Color.WHITE);
                boton.setForeground(new Color(0, 75, 121));
                break;
            case 5://Boton Enviar resaltado en azul solido
                boton.setFont(new Font("Arial", Font.BOLD, 20));
                boton.setBackground(new Color(0, 75, 121));
                boton.setForeground(Color.WHITE);
                boton.setAlignmentX(Component.CENTER_ALIGNMENT);
                break;
        }
    }

    //Aqui creo la funcion para etilizar un Combobox
    public static void estilizarComboBox(JComboBox combo) {
        combo.setFont(new Font("Arial", Font.BOLD, 16));
        combo.setForeground(new Color(0, 75, 121));
    }

    //Aqui creo la funcion para establecerele algunos parametros al boton
    public static void prepararBotonTarjeta(JButton boton, int ancho, int alto) {
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);//Centrado dentro del BoxLayout vertical
        boton.setMaximumSize(new Dimension(ancho, alto));//Evito que el BoxLayout lo estire de mas
        boton.setPreferredSize(new Dimension(ancho, alto));//Mismo ancho para todos los botones de la tarjeta
    }

    //Aqui creo el metodo para refrescar las ventanas
    public static void refrescarVentana(JComponent panel) {
        panel.revalidate();//Recalcula el layout interno del panel
        panel.repaint();//Vuelve a dibujar el panel con los cambios
    }

    //Aqui creo el metodo para baciar panel si ya esta lleno
    public static void vaciarPanel(JComponent componente) {
        if (componente.getComponentCount() > 0) {
            componente.removeAll();
            refrescarVentana(componente);
        }
    }

    public static boolean validarTamano(String cadena, int minimo, int maximo) {
        return (cadena.length() >= minimo && cadena.length() <= maximo);
    }

    public static boolean validarTamano(String cadena, int minimo) {
        return cadena.length() >= minimo;
    }

    public static boolean validarid(String cadena) {
        return cadena.matches("[0-9]+");
    }

    public static boolean validarContrasena(String cadena) {
        return (cadena.matches(".*[0-9].*")
                && cadena.matches(".*[A-Z].*")
                && cadena.matches(".*[a-z].*")
                && cadena.matches(".*(\\$|@|#|%|&|\\*|-|_|!|\\?).*"));
    }
}
