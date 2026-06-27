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

    public static boolean validarId(String id) {
        if (id.matches("[0-9]{8,10}")) {
            return true;
        } else {
            return false;
        }
    }
    
    public static boolean validarQueseanumeros(String id){
        if (!id.matches("[0-9]")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean idValidarLongitud(String id) {
        if (!id.isEmpty() && id.length() < 8 || id.length() > 10) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validarContrasena(String contrasena) {
        if (contrasena.matches("(?=.*[A-Z])(?=.*[/$#?!%])[A-Za-z0-9/$#?!%]{8,}")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean contrasenaCaracteresInvalidos(String contrasena) {
        if (!contrasena.isEmpty() && Pattern.compile("[^A-Za-z0-9/$#?!%]").matcher(contrasena).find()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validarLongitudCt(String contrasena) {
        if (!contrasena.isEmpty() && contrasena.length() < 8) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validarObligatoriedad(String contrasena) {
        if (!contrasena.isEmpty() && !contrasena.matches("!contrasena.matches(\"(?=.*[a-z])(?=.*[A-Z])(?=.*[/$#?!%])(?=.*[0-9]).+\")")) {
           return true;
        }else{
            return false;
        }
    }
}
