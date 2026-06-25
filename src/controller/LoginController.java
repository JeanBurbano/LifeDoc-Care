package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;
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
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.lg.bIngresar) {
            final String user = this.lg.getId();
            final String contrasena = this.lg.getPassword();

            if (!(user.isEmpty() && user.isEmpty())) {
                boolean verificadoru = false;
                if (user.matches("\\d{10}")) {
                    verificadoru = true;
                } else if (user.length() > 10) {
                    JOptionPane.showMessageDialog(lg, "El campo id no cumple con longitud de 10");
                } else {
                    JOptionPane.showMessageDialog(lg, "El campo id tiene caracteres no validos");
                }

                if (contrasena.matches("[a-zA-Z0-9/$#?!%]{8,10}") && verificadoru) {
                    JOptionPane.showMessageDialog(lg, "iniciaste sesion");
                } else if (contrasena.contains(" ") || Pattern.compile("[^a-zA-Z0-9/$#?!%]").matcher(contrasena).find()) {
                    JOptionPane.showMessageDialog(lg, "La contrasena tiene caracteres no validos");
                } else if (contrasena.length() < 8 || contrasena.length() > 10) {
                    JOptionPane.showMessageDialog(lg, "La contrasena debe ser como minimo 8 caracteres y maximo 10");
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
