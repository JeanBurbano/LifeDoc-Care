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

    public static boolean idValidarLongitud(String id) {
        return !id.isEmpty() && (id.length() < 8 || id.length() > 10);
    }

    public static boolean validarId(String id) {
        int c = 0;
        for (int i = 0; i < id.length(); i++) {
            try {
                Integer.parseInt(String.valueOf(id.charAt(i)));
                System.out.println("caracter: "+id.charAt(i));
                c=c+1;
                System.out.println("c: "+c);
            } catch (Exception e) {
                System.out.println("caracter en la posicion " + i + " de la cadena id inavalido \n" + e);
                i=id.length();
            }
        }
        return !idValidarLongitud(id) && c == id.length() ? true : false;
    }

    public static boolean validarLongitudCt(String contrasena) {
        return !contrasena.isEmpty() && contrasena.length() < 8;
    }

    public static boolean validarContrasena(String contrasena) {
        return !contrasena.isEmpty() && (!validarLongitudCt(contrasena) && contrasena.matches("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[/$#?!%])[A-Za-z0-9/$#?!%]"));
    }

    public static boolean contrasenaCaracteresInvalidos(String contrasena) {
        return !contrasena.isEmpty() && Pattern.compile("[^A-Za-z0-9/\\$#?!%]").matcher(contrasena).find();
    }

    public static boolean validarObligatoriedad(String contrasena) {
        return !contrasena.isEmpty() && !contrasena.matches("(?=.*[a-z])(?=.*[A-Z])(?=.*[/$#?!%])(?=.*[0-9]).+");
    }
}
