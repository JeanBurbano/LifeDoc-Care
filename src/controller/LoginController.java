package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.MetodosPublicos;
import view.Login;

public class LoginController implements ActionListener, MetodosPublicos {
    
    Login lg = new Login();
    
    public LoginController(Login lg) {
        this.lg = lg;
        this.lg.bRegistar.addActionListener(this);
        this.lg.bIngresar.addActionListener(this);
    }

//    @Override
//    public boolean validarCadena(String user, byte n) {
//        
//            int c = 0;
//            for (int i = 0; i < user.length(); i++) {
//                try {
//                    Integer.parseInt(user);
//                    c++;
//                } catch (Exception e) {
//                    break;
//                }
//            }
//            return c == user.length() ? true : false;
//        
//    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.lg.bIngresar) {
            final String user = this.lg.getUser();
            final String contrasena = this.lg.getPassword();
            
            if (!(user.isEmpty() && user.isEmpty())) {
                boolean verificadoru = false;
                if (user.matches("\\d{10}")) {
                    verificadoru = true;
                } else if (user.contains(" ")) {
                    JOptionPane.showMessageDialog(lg, "El campo id contiene espcaios");
                } else if (user.length() > 10) {
                    JOptionPane.showMessageDialog(lg, "El campo id no cumple con longitud de 10");
                } else if (user.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
                    JOptionPane.showMessageDialog(lg, "El campo id contiene contine letras");
                } else if (user.matches(".*[^a-zA-Z0-9\\s].*")) {
                    JOptionPane.showMessageDialog(lg, "El campo id contiene caracteres especiales que no son validos");
                }
                if (contrasena.matches("^[a-zA-Z0-9]+([/\\$#\\?%][a-zA-Z0-9]+)*$")) {
                    if (verificadoru) {
                        JOptionPane.showMessageDialog(lg, "iniciaste sesion");
                    }
                } else {
                    JOptionPane.showMessageDialog(lg, "Por favor Ingresar una contraseña valida");
                }
            } else if (user.isEmpty() && user.isEmpty()) {
                JOptionPane.showMessageDialog(lg, "Los dos campos son obligatorios");
            } else if (user.isEmpty()) {
                JOptionPane.showMessageDialog(lg, "El Campo Id es obligatorio");
            } else {
                JOptionPane.showMessageDialog(lg, "El Campo De Constrasena es obligario");
            }
        }
    }
}
