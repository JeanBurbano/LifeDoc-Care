package model;

import java.util.regex.Pattern;
import javax.swing.JComponent;

public class MetodosPublicos {

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
